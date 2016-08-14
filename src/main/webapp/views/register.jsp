<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/4
  Time: 10:21
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
    <title></title>
  <link rel="stylesheet" href="<%=path%>/static/css/signin.css" />
  <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/bootstrap.min.css" />
</head>
<body>
<div class="wrapper">
  <h1>注&nbsp;册</h1>
  <form id="regForm" action="/registercheck" method="post">
    <div class="form-group">
      <input type="text" class="form-control" id="email" placeholder="邮箱" name="email">
      <i class="icon glyphicon glyphicon-envelope">
      </i>
    </div>
    <div class="form-group">
      <input type="text" class="form-control" id="englishname" placeholder="英文用户名" name="username">
      <i class="icon glyphicon glyphicon-globe">
      </i>
    </div>
    <div class="form-group">
      <input type="text" class="form-control" id="chinesename" placeholder="中文用户名">
      <i class="icon glyphicon glyphicon-user">
      </i>


    </div>

    <div class="form-group">
      <input type="password" class="form-control" id="password" placeholder="密码" name="password">
      <i class="icon glyphicon glyphicon-lock">
      </i>
    </div>
    <input type="submit" class="btn btn-blue form-control" value="立即注册"/>
    <div class="checkbox no-padding clearfix">
      <a href="/login" class="a text-center">已经注册？</a>
    </div>
  </form>
</div>
</body>
</html>
