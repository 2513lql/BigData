<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/10
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="/views/include/header.jsp"%>
<head>
    <title>修改密码</title>
    <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/buttons.css" />
    <script src="<%=path%>/static/js/md5.js"></script>
    <script>
     $(document).ready(function(){
//         $(".nav-sidebar a").hover(function(){
//             $(this).css("background-color", "#428bca");
//             $(this).css("color","white");
//         },function(){
//             $(this).css("background-color", "white");
//             $(this).css("color","#428bca");
//         });

         $("#changepassword").removeClass("label_noton").addClass("label_on");

       $("#oldpassword").change(function(){
           var oldpassword =  hex_md5($("#oldpassword").val());
            if(oldpassword!='${userInfo.password}')
            {
                $(this).parent().removeClass('has-success').addClass("has-error");
                $("#errorOld").empty().append("原密码不正确，请重新输入");
            }
            else {
                $(this).parent().removeClass().addClass("form-group has-success");
                $("#errorOld").empty();
            }
       });
         $("#newpassword1").change(function(){
             if($(this).val().length<6)
             {
                 $(this).parent().removeClass('has-success').addClass("has-error");
                 $("#errorNew1").empty().append("密码过短,请重新输入(至少6位)");
             }
             else{
                 $(this).parent().removeClass().addClass("form-group has-success");
                 $("#errorNew1").empty();
             }
         });
         $("#newpassword2").change(function(){
             if($(this).val().length<6)
             {
                 $(this).parent().removeClass('has-success').addClass("has-error");
                 $("#errorNew2").empty().append("密码过短,请重新输入(至少6位)");
             }
             else{
                 if($("#newpassword2").val()!=$("#newpassword1").val())
                 {
                     $(this).parent().removeClass('has-success').addClass("has-error");
                     $("#errorNew2").empty().append("前后两次输入不一致，请重新输入");
                 }
                 else{
                     $(this).parent().removeClass().addClass("form-group has-success");
                     $("#errorNew2").empty();
                 }
             }
         });
       $("#confirmSub").click(

               function(){
                 changeit();
               }
       );
          function changeit(){
            var password1 = $("#newpassword1").val();
            var password2 = $("#newpassword2").val();
            var oldpassword = hex_md5($("#oldpassword").val());

            if(oldpassword=='${userInfo.password}')
            {
              if(password2!=password1)
              {
                alert("notSame!");
              }
              else
              {
                $.ajax({
                  url:"query/changepassword",
                  type:"POST",
                  data:{
                    "username":'${userInfo.userName}',
                    "password":hex_md5(password1)
                  }

                }).done(function(response) {
                    $("#newpassword1").attr("value","");
                    $("#newpassword2").attr("value","");
                    $("#oldpassword").attr("value","");
                  alert("恭喜！您已成功更改密码");
                  console.log("success");
                }).fail(function() {

                  console.log("error");
                });
              }
            }
          }
      });
    </script>
</head>
<body>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span1"></div>
    <div class="span2">

        <%@include file="/views/include/leftmenu.jsp"%>

    </div>
    <div class="span8">

        <%String userName;
          if(session.getAttribute("username")!=null)
            userName = (String)session.getAttribute("username");
          else
            userName = "123";
        %>
        <p id="userName" title="<%=userName%>"></p>
        <div>
          <h2>完善个人基本资料</h2>
        </div>

        <div class="form-group has-success">
            <label class="inp_label w95" >请输入原密码：</label>
          <input id="oldpassword" type="password" class="form-control" style="height: 30px;width:206px">
            <p id="errorOld" style="color:red"></p>
        </div>
        <div class="form-group has-success">
          <label  class="inp_label w95">请输入新密码：</label>
          <input id="newpassword1"  type="password" class="form-control" style="height: 30px;width:206px">
            <p id="errorNew1" style="color:red"></p>
        </div>
        <div class="form-group has-success">
          <label class="inp_label w95">再次输入密码：</label>
          <input id="newpassword2" type="password"  class="form-control" style="height: 30px;width:206px">
            <p id="errorNew2" style="color:red"></p>
        </div>
        <p class="ml105 mt20 mb20"><input id="confirmSub" class="button button-raised button-primary button-pill" value="确认修改" type="button"></p>

    </div>
    <div class="span1"></div>
  </div>
</div>
</body>
</html>
