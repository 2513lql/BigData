<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/5/23
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
  <ul class="nav nav-sidebar">
    <li ><a href="/usermanage"class="label_noton choosen" id="usermanage">个人资料</a><i></i></li>
    <li><a href="/userdata/datalist" class="label_noton choosen" id="mydata">我的发布</a><i></i></li>
    <li><a href="/changepassword" class="label_noton choosen" id="changepassword">密码修改</a><i></i></li>
  </ul>

  <input type="hidden" value="<%=path%>" id="path"/>
</div>
