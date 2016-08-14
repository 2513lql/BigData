package com.bigdata.domain;

/**
 * Created by stone on 2016/7/14.
 */
public class SearchWord {
    private String keyWord;
    private int count;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SearchWord(String keyWord, int count) {
        this.keyWord = keyWord;
        this.count = count;
    }

    public SearchWord() {
    }
}
