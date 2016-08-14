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
    <link type="text/css" rel="stylesheet" href="<%=path%>/static/css/buttons.css" />
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span1"></div>
        <div class="span2">
        </div>
        <div class="span8">
            <form id="detailsForm" action="/userdata/dataupload" enctype="multipart/form-data" method="post">
                <div>
                    <h2>上传数据</h2>
                </div>

                <fieldset>
                    <label class="inp_label w95" for="dataName">数据名称：</label>
                    <input id="dataName" name="dataName" type="text"  style="height: 30px">
                </fieldset>

                <fieldset>
                    <label>数据详情：</label>
                    <textarea class="form-control" name="description" rows="3" style="width: 400px" id="description"></textarea>
                </fieldset>

                <fieldset>
                    <label>数据分类：</label>
                    <select name="functionType" class="functionChoose">
                        <option value="nature" selected="selected">自然科学</option>
                        <option value="information" >信息科学</option>
                        <option value="technology">工程与技术</option>
                        <option value="biology">生物医药</option>
                        <option value="agriculture">农业科学</option>
                        <option value="humanity">人文与社科</option>
                        <option value="yearbook">统计年鉴数据</option>
                    </select>
                </fieldset>
                <fieldset>
                    <input type="file" name="dataFile" onchange="FilesChanged()" id="datafile"/>
                    <span id="datasize" class="ft-l" name="datasize" autocomplete="off"></span>
                </fieldset>

                <br/>
                <p class="ml105 mt20 mb20">
                    <input id="upload"
                           class="button button-raised button-primary button-pill"
                           value="上传"
                           type="submit"
                           onclick="return CheckData()">
                </p>
            </form>
        </div>
        <div class="span1"></div>
    </div>
</div>
<input type="hidden" id="Success" value="${viewMsg.get("Success")}" autocomplete="off">
<input type="hidden" id="Message" value="${viewMsg.get("Message")}" autocomplete="off">

<script type="text/javascript">

    window.onload=function(){
        document.getElementById('datafile').value='';
        var success = document.getElementById('Success').value;
        if(success == 'true') alert('上传文件成功');
        if(success == 'false') alert(document.getElementById('Message').value)
    };
    var FilesChanged = function(){
        var fileinput = document.getElementById('datafile');
        var f = fileinput.files;
        var s="Kb";
        var size = f[0].size/1024;
        if(size>1024) { size=size/1024; s="MB";}
        document.getElementById('datasize').innerText=size.toFixed(2) + s;
    }

    var CheckData = function(){
        var dataName = document.getElementById('dataName').value
        var dataFile = document.getElementById('datafile').value
        if(dataName==null ||dataName=='') {
            alert('请输入数据名称')
            return false;
        }
        if(dataFile==null ||dataFile=='') {
            alert('请选择要上传的文件')
            return false;
        }
        return true;
    }
</script>
</body>
</html>