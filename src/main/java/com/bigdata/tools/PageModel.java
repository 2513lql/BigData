package com.bigdata.tools;

/**
 * Created by ceix on 2016-04-13.
 */
public class PageModel {
    //页数
    private int pageNum=1;

    //每页显示多少个
    private int pageSize=15;

    private int pageLimit=0;

    //页数
    private int pageCount=0;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.pageLimit = pageSize * (this.pageNum-1);
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        this.pageLimit = pageSize * (this.pageNum-1);
    }


}
