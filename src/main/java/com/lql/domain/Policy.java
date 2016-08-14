package com.lql.domain;

import java.io.Serializable;

/**
 * Created by LQL on 2016/4/11.
 */
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pid;

    private String policyName;

    private Integer ownerId;

    /**
     * 策略所针对的操作类型：查看，删除，修改
     */
    private String operation;


    /**
     * 策略的类型: data(针对数据), chain(针对供应链)，xml(XML类型的策略)
     */
    private String ptype;


    /**
     * 访问策略属性集
     */
    private String attributes;

    /**
     * 访问策略向量
     */
    private String vectors;

    /**
     * 访问策略向量个数
     */
    private int attributesNum;

    /**
     * 访问策略向量长度
     */
    private int vectorsLength;

    private String description;

    /**
     * xml路径

     */
    private String xmlUrl;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getVectors() {
        return vectors;
    }

    public void setVectors(String vectors) {
        this.vectors = vectors;
    }

    public int getAttributesNum() {
        return attributesNum;
    }

    public void setAttributesNum(int attributesNum) {
        this.attributesNum = attributesNum;
    }

    public int getVectorsLength() {
        return vectorsLength;
    }

    public void setVectorsLength(int vectorsLength) {
        this.vectorsLength = vectorsLength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getXmlUrl() {
        return xmlUrl;
    }

    public void setXmlUrl(String xmlUrl) {
        this.xmlUrl = xmlUrl;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }
}
