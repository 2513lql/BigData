package com.lql.domain;

/**
 * Created by LQL on 2016/6/1.
 */
public class PolicyDesription {

    private String dataId;
    private String policyName;
    private String description;
    private String effect;
    private String security;
    private String userAttris;
    private String operations;
    private String network;
    private String time;
    private String ip;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getUserAttris() {
        return userAttris;
    }

    public void setUserAttris(String userAttris) {
        this.userAttris = userAttris;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PolicyDesription{" +
                "dataId='" + dataId + '\'' +
                ", policyName='" + policyName + '\'' +
                ", description='" + description + '\'' +
                ", effect='" + effect + '\'' +
                ", security='" + security + '\'' +
                ", userAttris='" + userAttris + '\'' +
                ", operations='" + operations + '\'' +
                ", network='" + network + '\'' +
                ", time='" + time + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

}
