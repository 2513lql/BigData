package com.bigdata.modal.enums;

/**
 * Created by stone on 2016/5/17.
 */
public enum FuctionType {
    NATURE("自然科学","nature"),INFORMATION("信息科学","information"),TECHNOLOGY("工程与技术","technology"),BIOLOGY("生物医药","biology"),AGRICULTURE("农业科学","agriculture"),HUMANITY("人文与社科","humanity"),YEARBOOK("统计年鉴数据","yearbook");
    private String name;
    private String storeName;
    private FuctionType(String name,String storeName)
    {
        this.name = name;
        this.storeName = storeName;
    }

    public String getName() {
        return name;
    }

    public String getStoreName() {
        return storeName;
    }
}
