package com.bigdata.tools;

import java.util.List;

/**
 * Created by ceix on 2016-04-20.
 */
public class DataInfoSearchModel extends PageModel {
    private String DataId;
    private String UserName;
    private Integer OwnerId;
    private String DataName;
    private String Type;
    private String FunctionType;
    private Integer DataStatus = -1;
    private String OrderBy;
    private List<String> Description;



    public Integer getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(Integer ownerId) {
        OwnerId = ownerId;
    }


    public String getDataId() {
        return DataId;
    }

    public void setDataId(String dataId) {
        DataId = dataId;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }



    public String getFunctionType() {
        return FunctionType;
    }

    public void setFunctionType(String functionType) {
        FunctionType = functionType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDataName() {
        return DataName;
    }

    public void setDataName(String dataName) {
        DataName = dataName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getDataStatus() {
        return DataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        DataStatus = dataStatus;
    }

    public List<String> getDescription() {
        return Description;
    }

    public void setDescription(List<String> description) {
        Description = description;
    }
}
