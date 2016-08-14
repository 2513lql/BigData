<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/6
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="/views/include/header.jsp"%>
<head>
  <meta charset="UTF-8">
  <title>大数据平台</title>
  <script type="text/javascript">
    $(document).ready(function(){

      $("#usermanage").removeClass("label_noton").addClass("label_on");

      $(function () {
        uc_field_init('602000');
        $("#detailsForm").validate({
          focusInvalid: true,
          onkeyup: false,
          success: function (label) {
            label.addClass("valid").text("");
          }
        });
      });


      $(".sex").click(function(event) {
        $(this).siblings().removeAttr('checked');
        $(this).attr('checked', 'checked');
      });

      $("#confirmSub").click(function(){
        message();
      });

      function message(){
        var sex = $(":radio:checked").val();
        var profession = $("#profession").val();
        var company = $("#company").val();
        var department = $("#department").val();
        var hobby1 = $("#uc_field_1").find("option:selected").text();
        var hobby2 = $("#uc_field_2").find("option:selected").text();
        var hobby3 = $("#uc_field_3").find("option:selected").text();
        if(hobby2=="不选择")
          hobby2="";
        if(hobby3=="不选择")
          hobby3="";
        var hobby = hobby1+"/"+hobby2+"/"+hobby3;
        var username = $("#userName").attr("title");
        $.ajax({
          url: 'query/user',
          type: 'post',
          data: {
            "profession":profession,
            "company":company,
            "department":department,
            "hobby":hobby,
            "username":username
          }
        })
        .done(function() {
          console.log("success");
                  alert("已成功修改用户信息");
                  location.href="/usermanage?username=${userInfo.userName}";
        })
        .fail(function() {
          console.log("error");
        });
      }
    });

  </script>
  <script src="${pageContext.request.contextPath}/static/js/all_resfield.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/static/js/jquery_002.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/static/js/ctrl_resfield.js" type="text/javascript"></script>
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
          <h2>完善个人基本资料</h2>
        </div>

        <fieldset>
          <label class="inp_label w95" for="profession">职业：</label>
          <input id="profession" placeholder="${userInfo.profession}" type="text"  style="height: 30px">
        </fieldset>

        <fieldset>
          <label>学校/单位/机构：</label>
          <input id="company" placeholder="${userInfo.company}" type="text" style="height: 30px">
        </fieldset>

        <fieldset>
          <label>所属部门：</label>
          <input id="department" placeholder="${userInfo.department}" type="text"  style="height: 30px">
        </fieldset>


        <fieldset>
          <p><strong>您的研究领域为：</strong>${userInfo.hobby},如需更改，请单击下面选框</p>
          <label class="inp_label w95">研究领域：</label>

          <select id="uc_field_1" name="uc_field_1" style="width:100px;">
            <option value="100000">自然科学</option>
            <option value="600000" selected="selected">信息科学</option>
            <option value="650000">工程与技术</option>
            <option value="700000">生物医药</option>
            <option value="750000">农业科学</option>
            <option value="800000">人文与社科</option>
            <option value="900000">统计年鉴数据</option>
          </select>
          <select id="uc_field_2" name="uc_field_2" style="width: 120px; margin-left: 3px; display: inline-block;">
            <option value="">不选择</option>
            <option value="601000">电子与通信技术</option>
            <option value="602000" selected="selected">计算机科学</option>
            <option value="603000">信息与系统科学</option>
            <option value="604000">图书馆、情报与文献学</option>
          </select>
          <select name="uc_field_3" id="uc_field_3" style="width: 160px; margin-left: 3px; display: inline-block;">
            <option selected="selected" value="">不选择</option>
            <option value="602001">机器学习</option>
            <option value="602002">数据挖掘</option>
            <option value="602003">人工智能</option>
            <option value="602004">并行计算</option>
            <option value="602005">信息检索</option>
            <option value="602006">算法理论</option>
            <option value="602007">数据库</option>
            <option value="602008">计算机图形</option>
            <option value="602009">人机交互</option>
            <option value="602010">自然语言处理</option>
            <option value="602011">网络和通讯</option>
            <option value="602012">操作系统</option>
            <option value="602014">视频处理</option>
            <option value="602015">程序设计语言</option>
            <option value="602016">嵌入式系统</option>
            <option value="602017">科学计算</option>
            <option value="602018">安全与隐私</option>
            <option value="602019">计算机仿真</option>
            <option value="602020">软件工程</option>
            <option value="602021">万维网</option>
            <option value="602022">计算生物学</option>
            <option value="602023">计算机教育</option>
            <option value="602024">硬件体系结构</option>
            <option value="602025">语音处理</option>
            <option value="602026">图像处理</option>
            <option value="602030">知识库</option>
            <option value="602099">计算机科学其它学科</option>
          </select>

        </fieldset>



        <p class="ml105 mt20 mb20"><input id="confirmSub" class="button button-raised button-primary button-pill" value="确认修改" type="button"></p>
      </form>
    </div>
    <div class="span1"></div>
  </div>
</div>
</body>
</html>
