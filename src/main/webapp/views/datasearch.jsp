<%--
  Created by IntelliJ IDEA.
  User: stone
  Date: 2016/4/21
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<%@include file="/views/include/header.jsp"%>
<head>
  <title>数据筛选</title>
  <link href="${pageContext.request.contextPath}/static/css/style.css"/>
  <script>
    function page(pageNum){
      var condition = [];

      $('.label_on').each(function (key,value) {
        condition.push($(value).attr("about"));
      });
      if(condition[0]==null)
        condition[0]="all";
      if(condition[1]==null)
        condition[1]="all";
      if(condition[2]==null)
        condition[2]="all";

      $.ajax({
        url:"data/query",
        type:"POST",
        data:{
          "functionType":condition[0],
          "type":condition[1],
          "OrderBy":condition[2],
          "pageNum":pageNum
        }
      }).done(function(response) {
        var data = response.datas;
        var pagemodel = response.pageModel;
        //重置分页控件
        ResetPager(pagemodel.pageNum,pagemodel.pageCount)

        var datainfo = $("#dataInfo");
        datainfo.empty();

        for(var i =0 ;i<data.length;i++){
          var name = data[i].dataName.split(".")[0];

          datainfo.append("<div  style='border-bottom: 2px solid #ccc;'> " +
          "<a href=operate/index?dataId="+data[i].dataId+"><h4>"+data[i].dataName+"</h4></a><br/>" +
          "<p style='color:grey'>"+data[i].description+"一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍</p><p><strong>发布者："+data[i].ownerName+"</p></div>");
        }

      }).fail(function() {

        console.log("error");
      });
    }

    function searchPage(pageNum){
      var keys = $("#search").val();
      $.ajax({
        url:"data/querysearch",
        type:"POST",
        data:{
          "keys":keys,
          "pageNum":pageNum
        }
      }).done(function(response) {
        var data = response.datas;
        var pagemodel = response.pageModel;
        //重置分页控件
        ResetPager(pagemodel.pageNum,pagemodel.pageCount);
        var datainfo = $("#dataInfo");
        datainfo.empty();

        for(var i =0 ;i<data.length;i++){
          var name = data[i].dataName.split(".")[0];

          datainfo.append("<div  style='border-bottom: 2px solid #ccc;'> " +
          "<a href=operate/index?dataId="+data[i].dataId+"><h4>"+data[i].dataName+"</h4></a><br/>" +
          "<p style='color:grey'>"+data[i].description+"一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍一堆介绍</p><p><strong>发布者："+data[i].ownerName+"</p></div>");
        }

      }).fail(function() {

        console.log("error");
      });
    }
    //页面加载完成时，请求第一页
    $(document).ready(function () {
      page(1);
    });

    $(document).ready(function () {
      $(".dataclass a").click(function(){
        $(this).parent().siblings().children().removeClass("label_on").addClass("label_noton");
        $(this).removeClass().addClass("label_on");

        page(1);
        $("#search").val("");
      });
      $(".condition a").hover(function(){
        $(this).css("background-color", "#0e90d2")
      },function(){
        $(this).css("background-color", "white");
      });
      $(".condition").click(function(){
        $(this).siblings().removeClass("label_on").addClass("label_noton");
        $(this).removeClass().addClass("condition label_on");

        page(1);
        $("#search").val("");
      });
      $("#date a").click(function () {
        $(this).siblings().removeClass("label_on").addClass("label_noton");
        $(this).removeClass().addClass("label_on");
        $("#size a").removeClass().addClass(" label_noton");
        page(1);
      });
      $("#size a").click(function(){
        $(this).siblings().removeClass("label_on").addClass("label_noton");
        $(this).removeClass().addClass(" label_on");
        $("#date a").removeClass().addClass(" label_noton");
        page(1);
      });
      $("#btn").click(function(){
        searchPage(1);
      });
    });

  </script>
  <script src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
  <script src="${pageContext.request.contextPath}/static/js/pagination_ajax.js"></script>

</head>
<body>

<form class="form-search" >
  <div class="container-fluid">
    <div class="row-fluid">

      <div class="span1"></div>
      <div class="span2">
        <ul class="nav nav-sidebar">
          <li  class="dataclass"><a class="  label_on" about="all" ><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>&nbsp;&nbsp;全部品类</a></li>
          <li  class="dataclass"><a class="label_noton" about="nature" ><span class="glyphicon glyphicon-tree-conifer" aria-hidden="true"></span>&nbsp;&nbsp;自然科学</a></li>
          <li class="dataclass"><a  class=" label_noton" about="information"><span class="glyphicon glyphicon-file" aria-hidden="true"></span>&nbsp;&nbsp;信息科学</a></li>
          <li  class="dataclass"><a class="label_noton" about="technology" ><span class="glyphicon glyphicon-phone" aria-hidden="true"></span>&nbsp;&nbsp;工程技术</a></li>
          <li class="dataclass"><a  class=" label_noton" about="biology"><span class="glyphicon glyphicon-briefcase" aria-hidden="true"></span>&nbsp;&nbsp;生物医药</a></li>
          <li  class="dataclass"><a class="label_noton" about="agriculture" ><span class="glyphicon glyphicon-cloud" aria-hidden="true"></span>&nbsp;&nbsp;农业科学</a></li>
          <li class="dataclass"><a  class=" label_noton" about="humanity"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;人文社科</a></li>
          <li class="dataclass"><a  class=" label_noton" about="yearbook"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span>&nbsp;&nbsp;统计年鉴</a></li>

        </ul>

      </div>
      <div class="span8">
        <br/>

        <input id ="search" class="input-medium search-query" type="text" width="200px" height="30px" style="width: 200px;height: 30px;" /> <input type="button" id="btn" value="查找"/>
        <br/>
        <br/>
        <div class="sx_box">
          <div class="sx_body">
            <div class="sx_inline bb_t1d clearfix">
              <strong class="l">数据类型： </strong>
              <div id="type">

                <a class="condition label_on" about="all">全部</a>
                <a class="condition label_noton"  about="data">数据文件</a>
                <a class="condition label_noton"  about="image">图片</a>
                <a class="condition label_noton"  about="zip">压缩文件</a>

              </div>
            </div>
          </div>
        </div>

        <div class="sx_box">
          <div class="sx_body">
            <div class="sx_inline bb_t1d clearfix">
              <strong class="l">数据大小： </strong>
              <div class="r sx_btn" id="size">
                <a class=" label_noton" about="size0">从大到小</a>
                <a class=" label_noton" about="size1">从小到大</a>

              </div>
            </div>
          </div>
        </div>

        <div class="sx_box">
          <div class="sx_body">
            <div class="sx_inline bb_t1d clearfix">
              <strong class="l">发布时间： </strong>
              <div class="r sx_btn" id ="date">
                <a class=" label_on" about="date1">从前到后</a>
                <a class=" label_noton" about="date0">从后到前</a>

              </div>
            </div>
          </div>
        </div>
        <div id ="dataInfo">
          <c:forEach var="p" items="${dataList}">
            <div  style="border-bottom: 2px solid #ccc;">
              <a href="operate/index?dataId=${p.dataId}"><h4>
                  ${p.dataName}<br/>
              </h4></a>
              <p style="color:grey">
                  ${p.description}
                ，本部分是一系列数据描述，本部分是一系列数据描述，本部分是一系列数据描述，本部分是一系列数据描述本部分是一系列数据描述，本部分是一系列数据描述，本部分是一系列数据描述本部分是一系列数据描述本部分是一系列数据描述本部分是一系列数据描述
              </p>
              <p><strong>数据来源：</strong>${p.ownerName}</p>
            </div>
          </c:forEach>
        </div>


        <nav id="pager" data-pageNum="${dataSearch.pageNum}" data-pageCount="${dataSearch.pageCount}"></nav>
      </div>

      <div class="span1"></div>
    </div>
  </div>
</form>
</body>
</html>

