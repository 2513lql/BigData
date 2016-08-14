package com.lql.domain;

/**
 * Created by LQL on 2016/5/17.
 */
public class AcUserRelation {

    private Integer id;
    private String requesterAtrris;
    private String dataOwnerAttris;
    private String relation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequesterAtrris() {
        return requesterAtrris;
    }

    public void setRequesterAtrris(String requesterAtrris) {
        this.requesterAtrris = requesterAtrris;
    }

    public String getDataOwnerAttris() {
        return dataOwnerAttris;
    }

    public void setDataOwnerAttris(String dataOwnerAttris) {
        this.dataOwnerAttris = dataOwnerAttris;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


}
