package com.bigdata.domain;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Created by stone on 2016/4/15.
 */

public class Prov implements Serializable{
    private int pid;
    private String prefix;
    private String entity;
    private String agent;
    private String activity;
    private String used;
    private Timestamp time;

    public Prov(){};

    public Prov(int pid, String prefix, String entity, String agent, String activity, String used, Timestamp time) {
        this.pid = pid;
        this.prefix = prefix;
        this.entity = entity;
        this.agent = agent;
        this.activity = activity;
        this.used = used;
        this.time = time;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }


    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }


    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }


    public String getUsed() {
        return used;
    }


    public void setUsed(String used) {
        this.used = used;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prov prov = (Prov) o;

        if (pid != prov.pid) return false;
        if (activity != null ? !activity.equals(prov.activity) : prov.activity != null) return false;
        if (agent != null ? !agent.equals(prov.agent) : prov.agent != null) return false;
        if (entity != null ? !entity.equals(prov.entity) : prov.entity != null) return false;
        if (prefix != null ? !prefix.equals(prov.prefix) : prov.prefix != null) return false;
        if (time != null ? !time.equals(prov.time) : prov.time != null) return false;
        if (used != null ? !used.equals(prov.used) : prov.used != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pid;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        result = 31 * result + (agent != null ? agent.hashCode() : 0);
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (used != null ? used.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
