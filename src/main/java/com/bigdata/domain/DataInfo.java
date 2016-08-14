package com.bigdata.domain;

//import org.springframework.data.annotation.Id;


import java.sql.Timestamp;

/**
 * Created by stone on 2016/4/15.
 */

public class DataInfo {
    private String dataId;
    private int ownerId;
    private String dataName;
    private String functionType;
    private String type;
    private String description;
    private Integer status;
    private Integer relation;
    private Timestamp time;
    private long dataSize;
    private String policyId;
    private String ownerName;
    private int downloadTimes;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public long getDataSize() {
        return dataSize;
    }

    public void setDataSize(long dataSize) {
        this.dataSize = dataSize;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }



    public DataInfo(){

    }
    public DataInfo(String dataId, String dataName, String type, String description, Integer status, Integer relation, Timestamp time) {
        this.dataId = dataId;
        this.dataName = dataName;
        this.type = type;
        this.description = description;
        this.status = status;
        this.relation = relation;
        this.time = time;
    }

    public DataInfo(String dataName, int ownerId, String type, long dataSize, String description) {
        this.dataName = dataName;
        this.ownerId = ownerId;
        this.type = type;
        this.dataSize = dataSize;
        this.description = description;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }


    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataInfo dataInfo = (DataInfo) o;

        if (dataId != null ? !dataId.equals(dataInfo.dataId) : dataInfo.dataId != null) return false;
        if (dataName != null ? !dataName.equals(dataInfo.dataName) : dataInfo.dataName != null) return false;
        if (description != null ? !description.equals(dataInfo.description) : dataInfo.description != null)
            return false;
        if (relation != null ? !relation.equals(dataInfo.relation) : dataInfo.relation != null) return false;
        if (status != null ? !status.equals(dataInfo.status) : dataInfo.status != null) return false;
        if (time != null ? !time.equals(dataInfo.time) : dataInfo.time != null) return false;
        if (type != null ? !type.equals(dataInfo.type) : dataInfo.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataId != null ? dataId.hashCode() : 0;
        result = 31 * result + (dataName != null ? dataName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (relation != null ? relation.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "dataId='" + dataId + '\'' +
                ", dataName='" + dataName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", relation=" + relation +
                ", time=" + time +
                '}';
    }
}
