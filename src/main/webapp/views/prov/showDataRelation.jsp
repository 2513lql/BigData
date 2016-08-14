<%--
  Created by IntelliJ IDEA.
  User: simple_love
  Date: 2016/5/3
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
        <base href="<%=basePath%>">
        <link rel="stylesheet" href="<%=path%>/static/css/bootstrap.css">
        <script type="text/javascript" src="<%=path%>/static/js/jquery-1.12.3.min.js"></script>
        <script type="text/javascript" src="<%=path%>/static/js/bootstrap.js"></script>
        <script type="text/javascript" src="<%=path%>/static/js/prov/jquery.graphviz.svg.js"></script>
        <title></title>
</head>
<body>
<input type="hidden" id="url" value="${url}">
<input type="hidden" id="queryId" value="${queryId}">
<input type="hidden" id="userName" value="<%=session.getAttribute("username")%>">
<div class="container">
        <div class="row">
                <span class="glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="top" title="shift +  ↑/↓: 向上(向下)移动图片
                        shift + 滚轮 ：对图片进行放大缩小">温馨提示</span>
                <div class="btn-group">
                        <button id="zoom-in" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top" title="放大">
                                <span class="glyphicon glyphicon-zoom-in"></span>
                        </button>
                        <button id="zoom-out" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top" title="缩小">
                                <span class="glyphicon glyphicon-zoom-out" ></span>
                        </button>
                        <button  id="move-left" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top"  title="左移">
                                <span class="glyphicon glyphicon-arrow-left"></span>
                        </button>
                        <button id="move-right" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top" title="右移">
                                <span  class="glyphicon glyphicon-arrow-right"></span>
                        </button>
                        <button id="move-up" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top" title="上移">
                                <span class="glyphicon glyphicon-arrow-up"></span>
                        </button>
                        <button id="move-down" type="button" class="btn btn-lg btn-default" data-toggle="tooltip" data-placement="top" title="下移">
                                <span class="glyphicon glyphicon-arrow-down"></span>
                        </button>
                </div>
        </div>
        <div class="row">
                <div id="show_svg">

                </div>
        </div>
</div>

</body>
<script type="text/javascript">
        $(function(){
                var bath = $("base").attr("href");
                $('[data-toggle="tooltip"]').tooltip();
                var url = $("#url").val();
                var queryUrl = url.substr(0,url.lastIndexOf("/")) + "/getProvByPage";
                var queryId = $("#queryId").val();
                var userName = $("#userName").val();
                $("#show_svg").graphviz({
                        url: url+"?queryId=" +queryId + "&userName=" + userName,
                        /*url:"resources/provImage/test1.svg",*/
                        ready:function(){
                                $('text').attr("fill","black");
                                //$("ellipse").attr("fill","#2ecc71").attr("stroke","none");
                                var svg = $('#show_svg').find('svg');
                                var viewBoxValues = svg[0].getAttribute("viewBox").split(" ");
                                $("#zoom-in").click(function(){
                                        zoomIn(svg,viewBoxValues)
                                });
                                $("#zoom-out").click(function(){
                                        zoomOut(svg,viewBoxValues)
                                });
                                $("#move-left").click(function(){
                                        moveLeft(svg,viewBoxValues);
                                });
                                $("#move-right").click(function(){
                                        moveRight(svg,viewBoxValues);
                                });
                                $("#move-up").click(function(){
                                        moveUp(svg,viewBoxValues);
                                });
                                $("#move-down").click(function(){
                                        moveDown(svg,viewBoxValues);
                                });
                                $(".node").css("cursor","pointer").click(function(){
                                        var dataName = $(this).attr("data-name");
                                        window.location.href = bath + "prov/showDataInfo?queryUrl=" + queryUrl +"&dataName=" + dataName;
                                })
                        }
                })
        })
        function zoomIn(svg,viewBoxValues){
                var zoomRate = 1.1;
                viewBoxValues[2] = parseFloat(viewBoxValues[2]);
                viewBoxValues[3] = parseFloat(viewBoxValues[3]);
                viewBoxValues[2] = viewBoxValues[2]/zoomRate;
                viewBoxValues[3] = viewBoxValues[2]/zoomRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }
        function zoomOut(svg,viewBoxValues){
                var zoomRate = 1.1;
                viewBoxValues[2] = parseFloat(viewBoxValues[2]);
                viewBoxValues[3] = parseFloat(viewBoxValues[3]);
                viewBoxValues[2] = viewBoxValues[2]*zoomRate;
                viewBoxValues[3] = viewBoxValues[2]*zoomRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }
        function moveLeft(svg,viewBoxValues){
                var panRate = 100.00;
                viewBoxValues[0] = parseFloat(viewBoxValues[0]);
                viewBoxValues[0] += panRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }
        function moveRight(svg,viewBoxValues){
                var panRate = 100.00;
                viewBoxValues[0] = parseFloat(viewBoxValues[0]);
                viewBoxValues[0] -= panRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }

        function moveUp(svg,viewBoxValues){
                var panRate = 100.00;
                viewBoxValues[1] = parseFloat(viewBoxValues[1]);
                viewBoxValues[1] += panRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }

        function moveDown(svg,viewBoxValues){
                var panRate = 100.00;
                viewBoxValues[1] = parseFloat(viewBoxValues[1]);
                viewBoxValues[1] -= panRate;
                svg[0].setAttribute("viewBox",viewBoxValues.join(" "));
        }
</script>
</html>
