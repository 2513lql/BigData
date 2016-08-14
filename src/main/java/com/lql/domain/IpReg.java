package com.lql.domain;

/**
 * Created by LQL on 2016/4/18.
 */
public class IpReg {

    private Integer ipId;
    private String company;
    private String ipName;
    private String ipRegexp;
    private String description;

    public Integer getIpId() {
        return ipId;
    }

    public void setIpId(Integer ipId) {
        this.ipId = ipId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIpRegexp() {
        return ipRegexp;
    }

    public void setIpRegexp(String ipRegexp) {
        this.ipRegexp = ipRegexp;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "company:"+company +", ipRegex:"+ipRegexp;
    }
}
