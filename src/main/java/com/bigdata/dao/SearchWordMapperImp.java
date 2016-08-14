package com.bigdata.dao;

import com.bigdata.domain.SearchWord;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by stone on 2016/7/14.
 */
public class SearchWordMapperImp extends SqlSessionDaoSupport implements SearchWordMapper{
    public SearchWord GetSearchWord(String keyWord){
        return this.getSqlSession().selectOne("com.bigdata.dao.SearchWordMapper.GetSearchWord");
    }
    public void InsertSearchWord(SearchWord searchWord){
        this.getSqlSession().insert("com.bigdata.dao.SearchWordMapper.InsertSearchWord");
    }

    @Override
    public void UpdateSearchWord(SearchWord searchWord) {
        this.getSqlSession().update("om.bigdata.dao.SearchWordMapper.UpdateSearchWord");
    }

}
