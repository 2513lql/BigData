<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/10
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="/views/include/header.jsp"%>
<head>
    <title>欢迎界面</title>
  <script>

    $(document).ready(function(){
      var username = $("#username").val();
      $.ajax({
        type: "get",
        async: false,
        //url: "http://10.33.6.199:8080/registerConsumer?name="+input,
        url: "http://10.33.6.199:8080/registerConsumer?name="+username,
        dataType: "jsonp",
        jsonp:"callback",
        jsonpCallback:"handler",
        success: function(data){
          console.log(data.message);
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
          alert(XMLHttpRequest.status);
          alert(XMLHttpRequest.readyState);
          alert(textStatus);
        }
      });
      location.href="index";

    });
  </script>
</head>
<body>
  欢迎您注册本平台，点击确定返回首页。
  <button id="POST" >确定</button>
  <%String username=(String)(session.getAttribute("username"));%>
 <input type="hidden" value="<%=username%>" id="username"/>
<%out.print(username);%>
  <%@include file="/views/include/footer.jsp"%>
</body>
</html>
