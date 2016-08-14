<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="/views/include/header.jsp"%>
<head>
    <title>testtest</title>
    <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/index.css"/>
</head>
<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <div class="carousel slide" id="carousel-18983">
                <ol class="carousel-indicators">
                    <li class="active" data-slide-to="0" data-target="#carousel-18983">
                    </li>
                    <li data-slide-to="1" data-target="#carousel-18983">
                    </li>
                    <li data-slide-to="2" data-target="#carousel-18983">
                    </li>
                </ol>
                <div class="carousel-inner">
                    <div class="item active">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/1.jpg" />
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/2.png" />
                        <div class="carousel-caption">

                        </div>
                    </div>
                    <div class="item">
                        <img alt="" src="${pageContext.request.contextPath}/static/img/4.jpg" />
                        <div class="carousel-caption">

                        </div>
                    </div>
                </div> <a data-slide="prev" href="#carousel-18983" class="left carousel-control">‹</a> <a data-slide="next" href="#carousel-18983" class="right carousel-control">›</a>
            </div>
        </div>
        <div class="span1"></div>
    </div>
    <div class="row-fluid">
        <div class="span1">
        </div>
        <div class="span3" style="height: 250px">

            <h4 class="title">已发布数据</h4>
            <br/>
            <div class="ad_datatext">
                <span class="">${nums}</span>
                　组
            </div>
            <br/>
            <br/>
            <p>
                <a class="btn btn-primary" href="/datasearch">查看数据</a>&nbsp;&nbsp;
                &nbsp;
                <a class="btn" href="/userdata/dataupload">发布数据</a>
            </p>

        </div>
        <div class="span3">
            <img alt="100*50" src="${pageContext.request.contextPath}/static/img/6.jpg" style="max-height:240px;"/>
        </div>
        <div class="span3">

            <div class="caption">
                <h2>
                    欢迎您的加入
                </h2>
                <p>
                    本平台有大量的数据资源，API资源。衷心的希望您能加入我们，了解到更多。
                </p>
                <p>
                    <%
                        if(session.getAttribute("username")!=null)
                            out.print("<a class=\"btn btn-primary\" href=\"/userdata/dataupload\">加入我们 »</a>");
                        else
                            out.print("<a class=\"btn btn-primary\" href=\"/register\">加入我们 »</a>");
                    %>
                </p>
            </div>

        </div>
        <div class="span1"></div>

    </div>
    <br/>
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span10">
            <ul class="thumbnails">
                <li class="span4">
                    <div class="thumbnail">
                        <div class="tabbable caption" id="tabs-291040">
                            <h3 class="title">精品数据</h3>
                            <ul class="nav nav-tabs">
                                <li class="active">
                                    <a href="#panel-979228" data-toggle="tab">语音数据</a>
                                </li>
                                <li>
                                    <a href="#panel-78658" data-toggle="tab">图像数据</a>
                                </li>
                                <li>
                                    <a href="#panel-78659" data-toggle="tab">文本数据</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="panel-979228">
                                    <p>
                                        语音数据内容.
                                        <br/>
                                        <c:forEach var="p" items="${voiceData}">
                                            数据名称：${p.dataName}<br/>
                                            数据描述：${p.description}<br/>
                                            <br/>
                                        </c:forEach>
                                    </p>
                                </div>
                                <div class="tab-pane" id="panel-78658">
                                    <p>
                                        图像数据内容.
                                        <br/>
                                        <c:forEach var="p" items="${imageData}">
                                            数据名称：${p.dataName}<br/>
                                            数据描述：${p.description}<br/>
                                            <br/>
                                        </c:forEach>
                                    </p>
                                </div>
                                <div class="tab-pane" id="panel-78659">
                                    <p>
                                        文本数据内容
                                        <br/>
                                        <c:forEach var="p" items="${textData}" end="2">
                                            数据名称：${p.dataName}<br/>
                                            数据描述：${p.description}<br/>
                                            <br/>
                                        </c:forEach>
                                    </p>
                                </div>
                            </div>
                        </div>

                    </div>
                </li>

                <li class="span4">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3>下载量排行</h3>

                            <table>
                                <tr>
                                    <th style="width: 50px">排名</th>
                                    <th style="width: 120px">名称</th>
                                    <th>下载量</th>
                                </tr>
                                <c:set var="index" value="0" />
                                <c:forEach items="${topCountData}" var="p">
                                    <c:set var="index" value="${index+1}"></c:set>
                                    <tr>
                                        <c:if test="${index<=3}">
                                            <td class="number1">${index}</td>
                                        </c:if>
                                        <c:if test="${index>3}">
                                            <td class="number4">${index}</td>
                                        </c:if>

                                        <td><a href="/operate/index?dataName=${p.dataId}">${p.dataName}</a></td>
                                        <td>${p.downloadTimes}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br />
                        </div>
                    </div>
                </li>
                <li class="span4">
                    <div class="thumbnail">
                        <div class="caption">
                            <h3>最新发布数据</h3>

                            <table>
                                <tr>
                                    <th style="width: 50px">排名</th>
                                    <th style="width: 120px">名称</th>
                                    <th>下载量</th>
                                </tr>
                                <c:set var="index" value="0" />
                                <c:forEach items="${topCountData}" var="p">
                                    <c:set var="index" value="${index+1}"></c:set>
                                    <tr>
                                        <c:if test="${index<=3}">
                                            <td class="number1">${index}</td>
                                        </c:if>
                                        <c:if test="${index>3}">
                                            <td class="number4">${index}</td>
                                        </c:if>
                                        <td><a href="/operate/index?dataName=${p.dataId}" >${p.dataName}</a></td>
                                        <td>${p.downloadTimes}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br />
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="span1"></div>
    </div>

</div>
<%@include file="/views/include/footer.jsp"%>
</body>
</html>