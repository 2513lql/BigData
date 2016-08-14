/*------------------------------------
控件使用说明：
初始化：调用方法 uc_field_init(fieldID, showLevel) 
获取选择的地址ID使用方法：uc_field_getfieldID()
获取选择的地址名称使用方法：uc_field_getfieldName()
----------------------------------*/

var isIE = !!window.ActiveXObject;
var isIE6 = isIE && !window.XMLHttpRequest;

//公共方法：初始化控件，该方法必须被调用
function uc_field_init(fieldID, showLevel) {
    if (fieldID == undefined || fieldID==0) fieldID = 100000;
    if (showLevel == undefined) showLevel = 3;

    $("#uc_field_showLevel").val(showLevel);
    $("#uc_field_fieldID").val(fieldID);

    $("#uc_field_2").hide();
    $("#uc_field_3").hide();
    _initfieldLevel1(fieldID);

    $("#uc_field_1").change(function () { _changefieldLevel1(); });

    if (showLevel > 1) {
        _initfieldLevel2(fieldID);
        $("#uc_field_2").show().change(function () { _changefieldLevel2(); });
    }
    if (showLevel > 2) {
        _initfieldLevel3(fieldID);
        $("#uc_field_3").show().change(function () { _changefieldLevel3(); });
    }
}

//公共方法：获取领域ID
function uc_field_getfieldID() {
    return $("#uc_field_fieldID").val();
}

//公共方法：获取地址名称，形如："河北省>石家庄市>长安区"
function uc_field_getfieldName() {
    var showLevel = parseInt($("#uc_field_showLevel").val());
    var fieldName = "";
    var splitter = ">";
    var
    fieldName = $("#uc_field_1").find("option:selected").text();

    if (showLevel >= 2 && $("#uc_field_2").val() != "") {
        fieldName += splitter + $("#uc_field_2").find("option:selected").text();
    }

    if (showLevel == 3 && $("#uc_field_3").val() != "") {
        fieldName += splitter + $("#uc_field_3").find("option:selected").text();
    }
    return fieldName;
}

//公共方法：获取地址名称，形如："河北省>石家庄市>长安区"
function uc_field_getfieldPathName(fieldID) {
    var fieldName = "";
    var splitter = ">";

    //先从三级计算
    var parentSubID = fieldID;
    for (var i = 0; i < field_class_3.length; i++) {
        var id = field_class_3[i][1];
        if (id < 5) alert(id);
        if (id == fieldID) {
            parentSubID = field_class_3[i][0];
            fieldName = field_class_3[i][2];
            break;
        }
    }

    //再计算二级学科
    for (var i = 0; i < field_class_2.length; i++) {
        if (field_class_2[i][1] == parentSubID) {
            parentSubID = field_class_2[i][0];
            if (fieldName != "") fieldName = splitter + fieldName;
            fieldName = field_class_2[i][2] + fieldName;

            break;
        }
    }

    //最后计算一级学科
    for (var i = 0; i < field_class_1.length; i++) {
        if (field_class_1[i][0] == parentSubID) {
            if (fieldName != "") fieldName = splitter + fieldName;
            fieldName = field_class_1[i][1] + fieldName;
            break;
        }
    }

    return fieldName;
}

//以下是私有方法，不对外使用
function _changefieldLevel1() {
    $("#uc_field_2").html("");
    var fieldid = $("#uc_field_1").val();
    var html = "";
    for (var i = 0; i < field_class_2.length; i++) {
        if (field_class_2[i][0] == fieldid) {
            html += "<OPTION value='" + field_class_2[i][1] + "'>" + field_class_2[i][2] + "</OPTION>";
        }
    }
    $("#uc_field_2").html(html);
    $("#uc_field_3").html("<OPTION value=''>不选择</OPTION>");

    _setfieldID($("#uc_field_1").val());
}

function _changefieldLevel2() {
    var fieldid = $("#uc_field_2").val();
    $("#uc_field_3").html("");
    var html = "<OPTION value=''>不选择</OPTION>";
    for (var i = 0; i < field_class_3.length; i++) {
        if (field_class_3[i][0] == fieldid) {
            html += "<OPTION value='" + field_class_3[i][1] + "'>" + field_class_3[i][2] + "</OPTION>";
        }
    }
    $("#uc_field_3").html(html)
    _setfieldID($("#uc_field_2").val());
}

function _changefieldLevel3() {
    _setfieldID($("#uc_field_3").val());
}

function _initfieldLevel1(fieldid) {
    $("#uc_field_1").html("");
    var html = "";
    for (var i = 0; i < field_class_1.length; i++) {
        html += "<OPTION value='" + field_class_1[i][0] + "'";
        if (parseInt(fieldid / 10000) * 10000 == field_class_1[i][0])
            html += " SELECTED ";
        html += ">" + field_class_1[i][1] + "</OPTION>";
    }
    $("#uc_field_1").html(html);
}

function _initfieldLevel2(fieldid) {
    $("#uc_field_2").html("");
    var html = "<OPTION value=''>不选择</OPTION>";
    var parentid = parseInt(fieldid / 10000) * 10000;
    for (var i = 0; i < field_class_2.length; i++) {
        if (field_class_2[i][0] == parentid) {
            html += "<OPTION value='" + field_class_2[i][1] + "'";
            if (parseInt(fieldid / 100) * 100 == field_class_2[i][1])
                html += " SELECTED ";
            html += ">" + field_class_2[i][2] + "</OPTION>";
        }
    }
    $("#uc_field_2").html(html);
}

function _initfieldLevel3(fieldid) {
    $("#uc_field_3").html("");
    var html = "<OPTION value=''>不选择</OPTION>";
    var parentid = parseInt(fieldid / 100) * 100;
    for (var i = 0; i < field_class_3.length; i++) {
        if (field_class_3[i][0] == parentid) {
            html += "<OPTION value='" + field_class_3[i][1] + "'";
            if (fieldid == field_class_3[i][1])
                html += " SELECTED ";
            html += ">" + field_class_3[i][2] + "</OPTION>";
        }
    }
    $("#uc_field_3").html(html);
}

function _setfieldID(fieldid) {
    $("#uc_field_fieldID").val(fieldid);
}



