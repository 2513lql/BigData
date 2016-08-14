<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@include file="/views/include/header.jsp"%>
<head>

    <title>策略制定</title>

    <style type="text/css">
        .divStyle {
            width: 99%;
            min-height: 200px;
            border: 1px solid #555555
        }

        span:hover {
            cursor: pointer;
        }

        .header {
            background-color: #d9edf7;
            text-align: center;
            padding-top: 5px;
        }
        .policyHintMessage{
            font-size: 1em;
            color: #A9A9A9;
            display: block;
        }
    </style>

    <script type="text/javascript">

        var index = 0;
        $(document).ready(function () {
            $("#policyPanel").hide();
            $("#policyHintPanel").hide();
            if('${haveOldPolicy}' == "no"){
                $("#oldPolicyPanel").hide();
            }
            getApiData();
            getAttributes();
            getIpRegexs();

            $("#addColUser").click(function () {
                getAttributes();
            });
            $("#removeColUser").click(function () {
                if (index > 0) {
                    index--;
                    $("#" + "attribute" + index).remove(
                    );
                }
            });

            //  提交表单
            $("#confirmSub").click(function () {
                confirmSubmit();
            });
        });

        //将表单数据封装成字符串提交
        function confirmSubmit() {

            var result = confirm("确认保存访问策略?");

            if (result == false) {
                return;
            }
            var arr = new Array();
            //策略名
            var policyNameTxt = document.getElementById("policyName");
            var policyName = "policyName:" + policyNameTxt.value;
            arr.push(policyName);

            //策略描述
            var policyDescriptionTxt = document.getElementById("description");
            var policyDescription = "policyDescription:" + policyDescriptionTxt.value;
            arr.push(policyDescription);

            //规则效果
            var policyEffect = "policyEffect:";
            if ($('#ruleEffect option:selected').val() == "permit") {
                policyEffect += "3";
            }
            else {
                policyEffect += "4";
            }
            arr.push(policyEffect);

            //资源属性
            var resources = "resource:[security:policy;operationMsg:yes;]";
            arr.push(resources);

            //用户属性
            var users = packUserAttris();
            arr.push(users);

            //操作属性
            var operations = packageOperationAttris();
            arr.push(operations);

            var environment = packEnvironmentAttris();
            arr.push(environment);

            var apiData = getApiData();
            var dataIdStr = apiData[0];
            arr.push(dataIdStr);

            var userId = apiData[1];
            arr.push(userId);

            //所有数据封装后使用Ajax提交
            postData(arr);
        }

        //get url's api data
        function getApiData(){
            var url = window.location.href;
            var regexp = /apiName=/;
            var matches = regexp.exec(url);
            var apiNameIndexStart = matches.index + 8;

            var regexp2 = /&id/;
            var match2 = regexp2.exec(url);
            var apiNameIndexEnd = match2.index;
            var apiName = url.substring(apiNameIndexStart,apiNameIndexEnd);

            var regexp3 = /id=/;
            var match3 = regexp3.exec(url);
            var userIdIndexStart = match3.index + 3;
            var userId = url.substring(userIdIndexStart);

            var apiData = new Array();
            apiData.push(apiName);
            apiData.push(userId);
            return apiData;
        }

        function packEnvironmentAttris() {
            var environment = "environment:[";
            //网络环境
            var networkEnvironment = "";
            var wired = document.getElementById("wired");
            var wireless = document.getElementById("wireless");
            if(!wired.checked && !wireless.checked){
                networkEnvironment += wired.value + "," + wireless.value + ",";
            }
            if(wired.checked){
                networkEnvironment += wired.value + ",";
            }
            if(wireless.checked){
                networkEnvironment += wireless.value + ",";
            }

            //访问时间
            var beginTimeSelect = document.getElementById("beginTime");
            var endTimeSelect = document.getElementById("endTime");
            var timeEnvironment = "";
            var begin = beginTimeSelect.value;
            var end = endTimeSelect.value;
            if(begin == -1 && end == -1){
                timeEnvironment +="00-24,";
            }else if(end == -1){
                timeEnvironment += begin +"-" + "24,";
            }else {
                end = Number(end);
                begin = Number(begin);
                if(end < begin){

                    timeEnvironment += end +"-"+begin+",";
                }
                else{

                    timeEnvironment += begin +"-" + end+",";
                }
            }

            //访问IP
            var ipEnvironmentSelect = document.getElementById("ipEnvironment");
            var ipEnvironment = "";
            ipEnvironment += ipEnvironmentSelect.value +",";

            environment += networkEnvironment +";";
            environment += timeEnvironment +";";
            environment += ipEnvironment + ";";
            environment += "]";
            return environment;
        }

        function packUserAttris() {
            //对用户属性集封装
            var users = "user:[";
            var userAttributes = document.getElementsByName("userAttributes");
            for (var i = 0; i < userAttributes.length; i++) {
                var tmp = "";
                var current = userAttributes[i];
                for (var j = 0; j < current.options.length; j++) {
                    if (current.options[j].selected) {
                        tmp += String(current.options[j].value + ",");
                    }
                }
                if (tmp.length > 0) {
                    tmp = tmp.substring(0, tmp.length - 1);
                    tmp = tmp + "#1;";
                }
                users += tmp;
            }
            var thresholdTxt = document.getElementById("attrisThreshold");
            var threshold = "#";
            if (thresholdTxt.value != "") {
                threshold = thresholdTxt.value;
            }
            users += threshold;
            users += "]";
            return users;
        }

        //对操作属性进行封装
        function packageOperationAttris() {

            var operation = "operation:[read,]";
//            var read = document.getElementById("read");
//            if (read.checked) {
//                operation += read.value + ",";
//            }
//            var update = document.getElementById("update");
//            if (update.checked) {
//                operation += update.value + ",";
//            }
//            var mydelete = document.getElementById("delete");
//            if (mydelete.checked) {
//                operation += mydelete.value + ",";
//            }
//            operation += "read,]";
            return operation;
        }

        //提交数据
        function postData(arr) {
            $.ajax({
                async: false,
                url: "/accontroller/registeApiPolicy.action",
                data: JSON.stringify(arr),
                type: "POST",
                dataType: "json",//服务器返回数据类型
                contentType: "application/json",//发送信息到服务器时，内容编码类型
                mimeType: "application/json",
                success: function (data) {
                    $('#successMsg').html('<h3>策略制定成功</h3>');
                    document.getElementById("confirmSub").disabled = true;
                    window.close();
                },
                error: function (data) {
                    $('#successMsg').html('<h3 style="color: #a94442;">策略制定失败</h3>');
                }
            });
        }

        function getAttributes() {
            if (index > 2) {
                return;
            }
            $.ajax({
                async: false,
                url: "/accontroller/attributes.action",
                type: "GET",
                data: {'attrIndex': index + ""},
                dataType: "json",//服务器返回数据类型
                success: function (data) {
                    var options = parseAttributesJson(data);
                    generateSelect(options);
                },
                error: function (data) {
                    alert("error：" + data.msg);
                },
            });
        }


        //获取IP网段
        function getIpRegexs() {
            $.ajax({
                async: false,
                url: "/accontroller/ipregs.action",
                type: "GET",
                dataType: "json",//服务器返回数据类型
                success: function (data) {
                    parseIpRegs(data);
                },
            });
        }

        /*解析JSON属性数据*/
        function parseAttributesJson(data) {
            var jsonObj = data.attributesSet;
            var options = String(jsonObj).split(",");
            return options;
        }
        function generateSelect(options) {
            var html = iterateOptions(options);
            if (index == 0) {
                $("#thresholdUser").before("<td id='attribute" + index++ + "'" + "><select class='form-control' name='userAttributes' multiple size='2'>" + html + "</select><td>"
                );
            } else {
                $("#thresholdUser").before("<td id='attribute" + index++ + "'" + "style='padding-left: 10px'><select class='form-control' name='userAttributes' multiple size='2'>" + html + "</select><td>"
                );
            }
        }

        function parseIpRegs(data) {
            var jsonObj = eval(data.msg);

            var html = '<td style="padding-left: 10px !important; padding-right: 0px !important"> <select class="form-control" id="ipEnvironment" name="ipEnvironment">';

            for (var i = 0; i < jsonObj.length; i++) {
                var opt = '<option value="' + jsonObj[i]['ipRegexp'] + '">' + jsonObj[i]['company'] + '</option>';
                html += opt;
            }
            html += '</select> </td>';
            console.log(html);
            $("#time").after(html);

        }

        function iterateOptions(options) {
            var html = "";
            for (var i = 0; i < options.length; i++) {
                if (i == 0) {
                    var tmp = "<optgroup label='" + options[i] + "'></optgroup>";
                } else {
                    var tmp = "<option value='" + options[i] + "'>" + options[i] + "</option>";
                }
                html += tmp;
            }
            return html;
        }
    </script>
</head>

<body>

<div class="container">
    <div class="page-header header">
        <h3>创建API访问策略</h3>
    </div>

    <div class="panel panel-default" id="oldPolicyPanel">
        <div class="panel-heading" style="cursor:pointer;" id="policyPanelBtn" >
            <h4 class="trigger" style="color: blue;">API现有访问策略</h4>
        </div>
        <div class="panel-body" id="policyPanel">
            <div class="row">
                <div class="col-md-8">
                    <form class="form-horizontal">

                        <div class="form-group">
                            <label for="dataIds" class="col-sm-3 control-label">策略对应API</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="dataList" onchange="selectDataId()" style="height: 35px;"  id="dataIds"
                                        name="dataIds">
                                    <c:forEach items="${apiName}" var="item">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>

                                <%--<input type="text" disabled class="form-control" style="height: 35px;"  id="dataIds"--%>
                                <%--placeholder="dataIds" value="${dataIds}"/>--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="policyName1" class="col-sm-3 control-label">策略名称</label>

                            <div class="col-sm-8">
                                <input type="text" disabled class="form-control" style="height: 35px;" id="policyName1"
                                       placeholder="policyName" value="${policyDescription.policyName}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description1" class="col-sm-3 control-label">策略描述</label>

                            <div class="col-sm-8">
                                <input type="text" style="height: 35px;" disabled class="form-control" id="description1"
                                       placeholder="description" value="${policyDescription.description}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="effect1" class="col-sm-3 control-label">策略效果</label>

                            <div class="col-sm-8">
                                <input type="text" style="height: 35px;" disabled class="form-control"
                                       id="effect1" value="${policyDescription.effect}" placeholder="effect1">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="userAttris1" class="col-sm-3 control-label">用户属性规则</label>

                            <div class="col-sm-8">
                                <input type="text" value="${policyDescription.userAttris}" style="height: 35px;" disabled class="form-control"
                                       id="userAttris1"
                                       placeholder="用户属性">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="network1" class="col-sm-3 control-label">网络</label>

                            <div class="col-sm-8">
                                <input type="text" style="height: 35px;" value="${policyDescription.network}" disabled class="form-control"
                                       id="network1"
                                       placeholder="网络">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="time1" class="col-sm-3 control-label">访问时间</label>

                            <div class="col-sm-8">
                                <input type="text" value="${policyDescription.time}" style="height: 35px;" disabled class="form-control"
                                       id="time1"
                                       placeholder="访问时间">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ipRegexp" class="col-sm-3 control-label">网段</label>

                            <div class="col-sm-8">
                                <input type="text" style="height: 35px;" value="${policyDescription.ip}" disabled class="form-control"
                                       id="ipRegexp"
                                       placeholder="网段">
                            </div>
                        </div>
                    </form>

                </div>
                <div class="col-md-4">
                </div>
            </div>
        </div>
    </div>

    <div class="panel panel-default" >
        <div class="panel-heading" style="cursor: pointer;"  id="policyHintBtn">
            <h4 class="trigger" style="color: blue;">策略制定提示</h4>
        </div>
        <div class="panel-body" id="policyHintPanel">
            <span class="policyHintMessage">
                您可以不指定策略名称，我们会为您提供一个默认的策略名;
            </span>
            <span class="policyHintMessage">
                您可以在策略描述中简单的说明一下您的制定的策略;

            </span>
            <span class="policyHintMessage">
                规则效果规定了当用户满足您制定的规则时允许访问还是拒绝访问您的数据;
            </span>
            <span class="policyHintMessage">
                用户属性可多选，表示用户需满足的属性要求;
            </span>
            <span class="policyHintMessage">
                操作表示本策略针对的操作权限;
            </span>
            <span class="policyHintMessage">
                环境表示本策略对于用户访问环境的限制;
            </span>
        </div>
    </div>

    <div class="divStyle form-ground" style="margin: auto;">
        <form id="policyDataForm" name="policyDataForm" method="post">
            <table id="mainTable" class="noBorders" style="border-collapse:separate; border-spacing:10px;">

                <tbody>
                <tr>
                    <td width="80px;">
                        <h4 class="trigger" style="color: blue;">策略定义</h4>
                    </td>
                    <td id="successMsg" style="color: #3c763d;">

                    </td>
                </tr>
                <tr>
                    <td width="80px;" style="padding-bottom: 20px;text-align: center;">&nbsp;&nbsp;<h5>策略名称</h5></td>
                    <td><input name="policyName" id="policyName" placeholder="策略名称" class="form-control" type="text"
                               style="height: 30px; width: 350px"></td>
                </tr>


                <tr>
                    <td width="80px;" style="padding-bottom: 20px;text-align: center;">&nbsp;&nbsp;<h5>策略描述</h5></td>
                    <td>
                        <div class="controls">
                            <div class="textarea">
                                <textarea id="description" class="form-control" style="width: 350px"
                                          name="description"> </textarea>
                            </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="margin-top: 10px;">
                        <div style="display: block;" id="newRuleLinkRow">
                            <table id="ruleTable">
                                <tbody>
                                <tr>
                                    <td>
                                        <table class="normal" cellspacing="0"
                                               style="border-collapse:separate; border-spacing:10px;">
                                            <tbody>
                                            <tr>
                                                <td><h5>规则效果</h5></td>
                                                <td style="padding-left: 0px !important; padding-right: 0px !important">
                                                    <select style="width: 105px;" class="form-control" id="ruleEffect"
                                                            name="ruleEffect">

                                                        <option selected="selected" value="permit">允许</option>

                                                        <option value="deny">拒绝</option>

                                                    </select></td>
                                            </tr>
                                            <tr>
                                                <td class="leftCol-small"><h5>用户</h5></td>
                                                <td>
                                                    <table>
                                                        <tbody>
                                                        <tr>
                                                            <td id="thresholdUser"
                                                                style="padding-left: 10px !important; padding-right: 0px !important">

                                                                <input class="form-control" name="attrisThreshold"
                                                                       id="attrisThreshold"
                                                                       placeholder="属性门限"
                                                                       style="height: 30px;width: 90px;" type="text">
                                                            </td>
                                                            <td id="addOrRemoveUser"
                                                                style="padding-left: 20px !important; padding-right: 0px !important;">
                                                                <span class="glyphicon glyphicon-plus"
                                                                      id="addColUser"></span>
																					<span id="removeColUser"
                                                                                          class="glyphicon glyphicon-remove"
                                                                                          style="margin-left: 10px"
                                                                                            ></span>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td class="leftCol-small"><h5>环境</h5></td>
                                                <td>
                                                    <table>
                                                        <tbody>
                                                        <tr>
                                                            <td
                                                                    style="padding-left: 0px !important; padding-right: 0px !important">

                                                                <input type="checkbox" name="wired"  id="wired"
                                                                       value="wired" checked>有线</input>
                                                                <input type="checkbox" name="wireless" id="wireless"
                                                                       value="wireless"
                                                                       style="margin-left: 5px;" checked>无线</input>
                                                            </td>
                                                            <td
                                                                    style="padding-left: 10px !important; padding-right: 0px !important">
                                                                <select class="form-control" id="beginTime"
                                                                        name="timeEnvironment">
                                                                    <option value="-1">
                                                                        起始时间
                                                                    </option>
                                                                    <option value="00">
                                                                        00:00
                                                                    </option>
                                                                    <option value="02">
                                                                        02:00
                                                                    </option>
                                                                    <option value="04">
                                                                        04:00
                                                                    </option>
                                                                    <option value="06">
                                                                        06:00
                                                                    </option>
                                                                    <option value="08">
                                                                        08:00
                                                                    </option>
                                                                    <option value="10">
                                                                        10:00
                                                                    </option>
                                                                    <option value="12">
                                                                        12:00
                                                                    </option>
                                                                    <option value="14">
                                                                        14:00
                                                                    </option>
                                                                    <option value="16">
                                                                        16:00
                                                                    </option>
                                                                    <option value="18">
                                                                        18:00
                                                                    </option>
                                                                    <option value="20">
                                                                        20:00
                                                                    </option>
                                                                    <option value="22">
                                                                        22:00
                                                                    </option>
                                                                </select>
                                                            </td>

                                                            <td
                                                                    id="time"
                                                                    style="padding-left: 10px !important; padding-right: 0px !important">
                                                                <select class="form-control" id="endTime"
                                                                        name="timeEnvironment">
                                                                    <option value="-1">
                                                                        终止时间
                                                                    </option>
                                                                    <option value="02">
                                                                        02:00
                                                                    </option>
                                                                    <option value="04">
                                                                        04:00
                                                                    </option>
                                                                    <option value="06">
                                                                        06:00
                                                                    </option>
                                                                    <option value="08">
                                                                        08:00
                                                                    </option>
                                                                    <option value="10">
                                                                        10:00
                                                                    </option>
                                                                    <option value="12">
                                                                        12:00
                                                                    </option>
                                                                    <option value="14">
                                                                        14:00
                                                                    </option>
                                                                    <option value="16">
                                                                        16:00
                                                                    </option>
                                                                    <option value="18">
                                                                        18:00
                                                                    </option>
                                                                    <option value="20">
                                                                        20:00
                                                                    </option>
                                                                    <option value="22">
                                                                        22:00
                                                                    </option>
                                                                    <option value="24">
                                                                        24:00
                                                                    </option>
                                                                </select>
                                                            </td>

                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="buttonRow" colspan="2">
                        <input
                                value="完成" id="confirmSub"
                                class="btn btn-primary btn-sm" style="width:200px;" type="button"></td>
                </tr>

                </tbody>
            </table>
        </form>
    </div>
</div>
<script>

    $(function(){
        $("#policyPanelBtn").click(function(){
            $("#policyPanel").toggle(1000);
        });
    });
    $(function(){
        $("#policyHintBtn").click(function(){
            $("#policyHintPanel").toggle(1000);
        });
    });

</script>

</body>
</html>