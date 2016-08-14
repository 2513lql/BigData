<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="/views/include/header.jsp" %>

<head>
    <title>我的发布</title>
    <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/buttons.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css"/>
    <script>
        $(document).ready(function () {
//            $(".nav-sidebar a").hover(function(){
//                $(this).css("background-color", "#428bca");
//                $(this).css("color","white");
//            },function(){
//                $(this).css("background-color", "white");
//                $(this).css("color","#428bca");
//            });

            $("#mydata").removeClass("label_noton").addClass("label_on");

            $("#btn_policy").click(function () {
                var form1 = document.getElementsByTagName("form").item(0);
                var dataIds = checkSelect();
                if (dataIds.length == 0) {
                    var html = '<span style="color:#337ab7;font-size: large;">请选择数据</span>';
                    $("#dataIdSelectInfo").html(html);
                    return false;
                }
                else {
                    form1.submit();
                }
            });
        });

        function checkSelect() {
            var datalist = document.getElementsByName("datalist");
            var dataIds = new Array();
            for (var i = 0; i < datalist.length; i++) {
                var item = datalist.item(i);
                if (item.checked == true) {
                    dataIds.push(item.value);
                }
            }
            return dataIds;
        }


    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span2">
            <%@include file="/views/include/leftmenu.jsp" %>
        </div>
        <div class="span8">

            <table class="table">
                <tr>
                    <th><input id="datalist" onclick="selectAll()" type="checkbox"></th>
                    <th>数据ID</th>
                    <th>所属用户</th>
                    <th>数据名称</th>
                    <th>数据类型</th>
                    <th>数据功能类型</th>
                    <th>数据描述</th>
                    <th>数据关联</th>
                    <th>数据生成时间</th>
                    <th>数据大小</th>
                    <th>访问控制策略</th>
                </tr>
                <form action="<%=path%>/accontroller/createpolicy.action" method="post">
                    <c:forEach items="${dataList}" var="data">
                        <tr>
                            <td><input type="checkbox" name="datalist" value="${data.dataId}"></td>
                            <td>${data.dataId}</td>
                            <td>${data.ownerName}</td>
                            <td>${data.dataName}</td>
                            <td>${data.type}</td>
                            <td>${data.functionType}</td>
                            <td>${data.description}</td>
                            <td>${data.relation}</td>
                            <td>${data.time}</td>
                            <td>${data.dataSize}</td>
                            <td>${data.policyId}</td>
                        </tr>
                    </c:forEach>
                </form>
                <tr>
                    <td colspan="7"></td>
                    <td colspan="2" id="dataIdSelectInfo" style="text-align: right;padding-top: 15px;">
                    </td>
                    <td colspan="2" style="text-align: right"><input type="button" id="btn_policy"
                                                                     class="btn btn-primary" value="访问策略"></td>
                </tr>
            </table>

            <nav id="pager" data-pageNum="${dataSearch.pageNum}" data-pageCount="${dataSearch.pageCount}"></nav>
        </div>
    </div>
</div>
<script src="<%=path%>/static/js/jquery-1.12.3.min.js"></script>
<script src="<%=path%>/static/js/md5.js"></script>
<script>
    var selectAll = function () {
        var ischecked = $('#datalist')[0].checked;
        var datalist = document.getElementsByName("datalist");
        for (var i = 0; i < datalist.length; i++) {
            if (datalist[i].type == "checkbox") datalist[i].checked = ischecked;
        }
    }
</script>
</body>
</html>
