package com.bigdata.tools;

import java.sql.Timestamp;

/**
 * Created by stone on 2016/4/28.
 */
public class ProvInfoModel extends PageModel {
    private Integer Pid=-1;
    private String Prefix;
    private String Entity;
    private String Agent;
    private String Activity;
    private String Used;
    private Timestamp Time;

    public Integer getPid() {
        return Pid;
    }

    public void setPid(Integer pid) {
        Pid = pid;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public String getEntity() {
        return Entity;
    }

    public void setEntity(String entity) {
        Entity = entity;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getUsed() {
        return Used;
    }

    public void setUsed(String used) {
        Used = used;
    }

    public Timestamp getTime() {
        return Time;
    }

    public void setTime(Timestamp time) {
        Time = time;
    }
}
