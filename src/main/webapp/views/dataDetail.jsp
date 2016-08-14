<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/4/28
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="/views/include/header.jsp"%>
<head>
    <title>数据详情</title>
  <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/buttons.css">
  <script>
    function sentDataInfo(){
      var dataName = $("#dataName").val();
      var type = $("#type").val();

      $.ajax({
        url:"operate/download",
        type:"POST",
        dataType:"json",
        data:{
          "dataName":dataName,
          "type":type
        },
        success:function(data){
          if(data["message"] == "false"){
            alert("您没有权限下载该数据");
          }
        },
        error:function(data){
          console.log(data["message"]);
        }
      });

      <%--$.ajax({--%>
        <%--url:"operate/download",--%>
        <%--type:"POST",--%>
        <%--data:{--%>
          <%--"dataName":dataName,--%>
          <%--"type":type--%>
        <%--}--%>
      <%--}).done(--%>
              <%--function(){--%>
                <%--location.href("/operate/download?dataName=${dataList[0].dataName}&type=${dataList[0].type}");--%>
                <%--console.log("success");--%>
              <%--}--%>
      <%--).fail(function() {--%>

        <%--console.log("error");--%>
      <%--});--%>

    }



    $(document).ready(function(){
      $("#download").click(function(){
        sentDataInfo();
        //window.onload("operate/download");
      });
    });
  </script>
</head>
<body>
<form action="/operate/download">
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span1"></div>
    <div class="span2">
      <input type="hidden" value="${dataList[0].dataName}" name="dataName"/>
      <input type="hidden" value="${dataList[0].type}" name="type"/>
      <input type="hidden" value="${dataList[0].dataId}" name="dataId"/>
      <h5>数据基本信息</h5>
      <table>
        <tr>
          <td>数据所属分类：</td>
          <td>
            <c:forEach var="p" items="${dataList}">
              <c:if test="${p.functionType=='voice'}">
                语音识别
              </c:if>
              <c:if test="${p.functionType=='care'}">
                医疗健康
              </c:if>
            </c:forEach>
          </td>
        </tr>

        <tr>
          <td>发布者：</td>
          <td>
            <c:forEach var="p" items="${dataList}">
              ${p.ownerName}
            </c:forEach>
          </td>
        </tr>
        <tr>
          <td>下载次数：</td>
          <td>
            已下载${num}次
          </td>
        </tr>
      </table>
    </div>
    <div class="span8">
      <p><h3>
      <c:forEach var="p" items="${dataList}">
        ${p.dataName}
      </c:forEach>
      </h3></p>
      <div class="tabbable" id="tabs-778591">
        <ul class="nav nav-tabs">
          <li class="active">
            <a href="#panel-728329" data-toggle="tab">数据描述</a>
          </li>
          <li>
            <a href="#panel-130421" data-toggle="tab">详细信息</a>
          </li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane active" id="panel-728329">
            <p>
              <c:forEach var="p" items="${dataList}">
                ${p.description}
              </c:forEach>
            </p>
          </div>
          <div class="tab-pane" id="panel-130421">
            <table>
              <tr>
                <td>数据所属分类：</td>
                <td>
                  <c:forEach var="p" items="${dataList}">
                    <c:if test="${p.functionType=='voice'}">
                      语音识别
                    </c:if>
                    <c:if test="${p.functionType=='care'}">
                      医疗健康
                    </c:if>
                  </c:forEach>
                </td>
              </tr>
              <tr>
                <td>数据格式：</td>
                <td>
                  <c:forEach var="p" items="${dataList}">
                    ${p.type}
                    <c:if test="${p.type}=='data'">
                      数据文件
                    </c:if>
                    <c:if test="${p.type}=='image'">
                      图片
                    </c:if>
                    <c:if test="${p.type}=='zip'">
                      压缩文件
                    </c:if>
                  </c:forEach>
                </td>
              </tr>
              <tr>
                <td>数据大小：</td>
                <td>
                  <c:forEach var="p" items="${dataList}">
                    ${p.dataSize}
                  </c:forEach>Mb
                </td>
              </tr>
            </table>
          </div>
          <br/>
          <button  class="button button-raised button-primary button-pill" id="download" >下载</button>
          <br/>
          <div  style='border-bottom: 2px solid #ccc;'>
            <h4>特别声明</h4>
            <p>
              这里是本平台的一些特别声明和特别声明和特别声明
              和特别声明 和特别声明 和特别声明和特别声明和特别声明和特别声明和特别声明和
              特别声明和特别声明和特别声明和特别声明和特别声明和特别声明和特别声明和特别声明和特别声明          </p>
          </div>
          <div  style='border-bottom: 2px solid #ccc;'>
            <h4>版权信息</h4>
            <p>
              本平台所提供的信息版权归本平台所有。
            </p>
          </div>
          <div  style='border-bottom: 2px solid #ccc;'>
            <h4>下载记录</h4>
            <div class="table-responsive">
              <table class="table">
                <tr>
                  <td>标号</td>
                  <td>下载人</td>
                  <td>所属平台</td>
                  <td>下载时间</td>
                </tr>

              <c:forEach  var="q" items="${downList}">
                <tr>
                  <td>${q.pid}</td>
                  <td>${q.agent}</td>
                  <td>${q.prefix}</td>
                  <td>${q.time}</td>
                </tr>
                </c:forEach>
              </table>
            </div>



            <nav id="pager" data-pageNum="${dataDetail.pageNum}" data-pageCount="${dataDetail.pageCount}"></nav>

            <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
          </div>
        </div>
        </div>
    </div>
    <div class="span1"></div>
    </div>
  </div>
  <br/>
  <%@include file="/views/include/footer.jsp"%>
</form>
</body>
</html>
