<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/4
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
  <base href="<%=basePath%>">
  <meta charset="UTF-8">
  <title>登录</title>
  <link rel="stylesheet" href="<%=path%>/static/css/signin.css" />
  <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css" />
</head>
<body>
<div class="wrapper">
  <h1>登&nbsp;录</h1>
  <form id="loginForm" action="loginCheck" method="post">
    <div class="form-group">
      <input type="text" class="form-control" id="userName" placeholder="用户名" name="username">
      <i class="icon glyphicon glyphicon-user">
      </i>
    </div>
    <div class="form-group">
      <input type="password" class="form-control" id="userPasswd" placeholder="密码" name="password">
      <i class="icon glyphicon glyphicon-lock">
      </i>
    </div>
    <input type="submit" value="登录"  class="btn btn-blue form-control"/>
    <input type="hidden" name="returnUrl" value="${returnUrl}">
    <c:if test="${!empty error}">
      <font color="red"><c:out value="${error}" /></font>
    </c:if>
  </form>

</div>
</body>
</html>
