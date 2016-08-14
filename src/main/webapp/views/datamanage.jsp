<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/16
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="/views/include/header.jsp"%>
<head>
  <meta charset="UTF-8">
  <title>数据管理</title>
  <script type="text/javascript">
    $(document).ready(function(){
      $("#choosefile").hide();
      var originalType = '${dataInfo.functionType}';
      $("option[id="+originalType+"]").attr("selected","selected");
      $(".nav-sidebar a").hover(function(){
        $(this).css("background-color", "#428bca");
        $(this).css("color","white");
      },function(){
        $(this).css("background-color", "white");
        $(this).css("color","#428bca");
      });
      $("#reupload").click(function(){
        if($(this).is(":checked"))
        {
          $("#choosefile").show();
        }
        else
        {
          $("#choosefile").hide();
        }
      });

      $("#confirmSub").click(function(){
        message();
      });

      function message()
      {
        var dataName = $("#dataName").val();
        var description = $("#description").val();
        var functionType = $(".functionChoose").find("option:selected").attr("about");
        $.ajax({
          url: 'updatedata',
          type: 'POST',
          data: {
                 dataId:'${dataInfo.dataId}',
                 dataName: dataName,
                 description:description,
                 functionType:functionType}
        })
                .done(function() {
                  console.log("success");
                  alert("恭喜！您已成功更新该数据信息");
                })
                .fail(function() {
                  console.log("error");
                })
                .always(function() {
                  console.log("complete");
                });
      }

    });

  </script>
  <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/buttons.css" />
</head>
<body>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span1"></div>
    <div class="span2">
      <%@include file="/views/include/leftmenu.jsp"%>
    </div>
    <div class="span8">
      <form id="detailsForm" method="post">
        <%String userName;
          if(session.getAttribute("username")!=null)
            userName = (String)session.getAttribute("username");
          else
            userName = "";
        %>
        <p id="userName" title="<%=userName%>"></p>
        <div>
          <h2>编辑数据的基本资料</h2>
        </div>

        <fieldset>
          <label class="inp_label w95" for="dataName">数据名称：</label>
          <input id="dataName" value="${dataInfo.dataName}" type="text"  style="height: 30px" name="dataName">
        </fieldset>

        <fieldset>
          <label>数据详情：</label>
          <textarea class="form-control" rows="3" style="width: 400px" id="description" name="description">${dataInfo.description}</textarea>
        </fieldset>

        <fieldset>
          <label>数据分类：</label>
          <input id="functionType" value="${dataInfo.functionType}" type="hidden"  style="height: 30px">
          <select class="functionChoose" name="functionType">
            <option id="nature" >自然科学</option>
            <option about="information" selected="selected">信息科学</option>
            <option about="technology">工程与技术</option>
            <option about="biology">生物医药</option>
            <option about="agriculture">农业科学</option>
            <option about="humanity">人文与社科</option>
            <option about="yearbook">统计年鉴数据</option>
          </select>
        </fieldset>

        <fieldset>
          <input type="checkbox" id="reupload"/>&nbsp;&nbsp;重新上传该数据：
          <br/>
          <input type="file" id="choosefile"/>
        </fieldset>

        <br/>
        <p class="ml105 mt20 mb20"><input id="confirmSub" class="button button-raised button-primary button-pill" value="确认修改" type="button"></p>
      </form>
    </div>
    <div class="span1"></div>
  </div>
</div>
</body>
</html>