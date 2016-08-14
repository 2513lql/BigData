package com.bigdata.dao;

import com.bigdata.domain.SearchWord;

/**
 * Created by stone on 2016/7/14.
 */
public interface SearchWordMapper {
    SearchWord GetSearchWord(String keyWord);
    void InsertSearchWord(SearchWord searchWord);
    void UpdateSearchWord(SearchWord searchWord);

}
