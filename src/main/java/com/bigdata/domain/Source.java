package com.bigdata.domain;



/**
 * Created by stone on 2016/4/15.
 */

public class Source {
    private int id;
    private String dataId;
    private String source;
    private String url;

    public Source(){};

    public Source(String url, String dataId, String source) {
        this.url = url;
        this.dataId = dataId;
        this.source = source;
    }


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


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Source source1 = (Source) o;

        if (id != source1.id) return false;
        if (dataId != null ? !dataId.equals(source1.dataId) : source1.dataId != null) return false;
        if (source != null ? !source.equals(source1.source) : source1.source != null) return false;
        if (url != null ? !url.equals(source1.url) : source1.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
