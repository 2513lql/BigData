<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/4/15
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.bigdata.domain.UserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>
  <base href="<%=basePath%>">


  <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/bootstrap-combined.css">
  <%--<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/layoutit.css">--%>
  <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/style.css">
  <script type="text/javascript" src="<%=path%>/static/js/jquery-1.12.3.min.js"></script>
  <script type="text/javascript" src="<%=path%>/static/js/bootstrap.js"></script>
  <script type="text/javascript" src="<%=path%>/static/js/jquery-ui.js"></script>

</head>
<body style="min-height: 818px; cursor: auto;width: 100%" class="devpreview sourcepreview">
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12">
      <div class="navbar navbar-inverse">

        <div class="navbar-inner">
          <div class="container-fluid">

            <a href="#" class="brand" >大数据平台</a>
            <div class="nav navbar-nav">
              <ul class="nav">
                <li class="active"><a href="/index">首页</a></li>
                <li class="active"><a href="http://10.33.6.199:8080/api/">数据API</a></li>
                <li class="active"><a href="/prov/index">供应链查询</a></li>
                <li class="active dropdown"> <a data-toggle="dropdown" class="dropdown-toggle" href="#">数据应用 <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="/userdata/dataupload">数据上传</a></li>
                    <li><a href="/datasearch">数据查看</a></li>
                  </ul>
                </li>
              </ul>

            </div>

            <!-- /.nav-collapse -->
            <ul  class="nav navbar-nav navbar-right">
              <%if(session.getAttribute("username")==null)
                out.print("<li  style=\"float: right;\"><a href=\"login\">登录</a></li>\n" +
                        "              <li style=\"float: right;\"><a href=\"register\">注册</a></li>");
                else
              {
                String username = (String)session.getAttribute("username");

                out.print("<li  style=\"float: right;\"><a href='"+basePath+"logout'>注销");
                out.print("</a></li>");
                out.print("<li  style=\"float: right;\"><a href='"+basePath+"usermanage'>欢迎您，");
                out.print(username);
                out.print("</a></li>");
              }
              %>
            </ul>
          </div>


        </div>

      </div>
    </div>
  </div>
</div>
</body>


