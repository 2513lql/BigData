package com.lql.domain;

import java.io.Serializable;

/**
 * Created by LQL on 2016/4/11.
 */
public class ChainControlInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;

    private String dataId;

    /**
     * 数据节点上游可视性
     */
    private String upVisibility;

    /**
     * 数据节点下游可视性
     */
    private String downVisibility;

    /**
     * 数据安全级别
     */
    private String security;

    /**
     * 数据信息可视性
     */
    private String dataMsgVisibility;

    private String policyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getUpVisibility() {
        return upVisibility;
    }

    public void setUpVisibility(String upVisibility) {
        this.upVisibility = upVisibility;
    }

    public String getDownVisibility() {
        return downVisibility;
    }

    public void setDownVisibility(String downVisibility) {
        this.downVisibility = downVisibility;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getDataMsgVisibility() {
        return dataMsgVisibility;
    }

    public void setDataMsgVisibility(String dataMsgVisibility) {
        this.dataMsgVisibility = dataMsgVisibility;
    }
}
