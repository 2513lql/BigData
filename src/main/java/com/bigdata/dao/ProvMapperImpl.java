package com.bigdata.dao;

import com.bigdata.domain.Prov;
import com.bigdata.tools.ProvInfoModel;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by stone on 2016/4/29.
 */
public class ProvMapperImpl extends SqlSessionDaoSupport implements ProvMapper{
    @Override
    public int GetProvCount(ProvInfoModel provInfoModel) {
        return this.getSqlSession().selectOne("com.bigdata.dao.ProvMapper.GetProvCount",provInfoModel);
    }

    @Override
    public List<Prov> GetProvList(ProvInfoModel provInfoModel) {
        return (List<Prov>)this.getSqlSession().selectOne("com.bigdata.dao.ProvMapper.GetProvCount",provInfoModel);
    }

    @Override
    public void InsertProv(Prov prov) {
        this.getSqlSession().selectOne("com.bigdata.dao.ProvMapper.InsertProv",prov);
    }
}
