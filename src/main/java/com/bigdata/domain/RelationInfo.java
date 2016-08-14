package com.bigdata.domain;


/**
 * Created by stone on 2016/4/15.
 */

public class RelationInfo {
    private int id;
    private String ancestor;
    private String successor;

    public RelationInfo(){}

    public RelationInfo(String ancestor,String successor){
        this.ancestor = ancestor;
        this.successor = successor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAncestor() {
        return ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public String getSuccessor() {
        return successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationInfo that = (RelationInfo) o;

        if (id != that.id) return false;
        if (ancestor != null ? !ancestor.equals(that.ancestor) : that.ancestor != null) return false;
        if (successor != null ? !successor.equals(that.successor) : that.successor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ancestor != null ? ancestor.hashCode() : 0);
        result = 31 * result + (successor != null ? successor.hashCode() : 0);
        return result;
    }
}
