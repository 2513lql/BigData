package com.bigdata.dao;

import com.bigdata.domain.DataInfo;
import com.bigdata.tools.DataInfoSearchModel;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by stone on 2016/4/19.
 */

public class DataInfoMapperImpl extends SqlSessionDaoSupport implements DataInfoMapper{
    public int GetDataCount(DataInfoSearchModel pageModel) {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.GetDataCount");
    }

    public List<DataInfo> GetDataList(DataInfoSearchModel pageModel) {
        return (List<DataInfo>)this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.GetDataList");
    }

    @Override
    public List<DataInfo> SearchByDescription(DataInfoSearchModel pageModel) {
        return (List<DataInfo>)this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.SearchByFunctionType",pageModel);
    }

    @Override
    public DataInfo SearchByDataId(String dataId) {
        return (DataInfo)this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.SearchByDataId");
    }

    @Override
    public void UpdateDataInfo(DataInfo dataInfo) {
        this.getSqlSession().update("com.bigdata.dao.DataInfoMapper.UpdateDataInfo");
    }

    @Override
    public int SearchByDescriptionCount(DataInfoSearchModel pageModel) {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.SearchByDescriptionCount");
    }

    @Override
    public String GetLastDataId(String plantName) {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.GetLastDataId");
    }



    @Override
    public int InsertData(DataInfo dataInfo) {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.InsertData");
    }

    @Override
    public List<DataInfo> GetTopDownloadTimes() {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.GetTopDownloadTimes");
    }

    @Override
    public List<DataInfo> GetLastestData() {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.GetLastestData");
    }

    @Override
    public DataInfo SearchByDataName(String dataId) {
        return this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.SearchByDataName");
    }

    @Override
    public void DataImport(String fileName) {
        this.getSqlSession().selectOne("com.bigdata.dao.DataInfoMapper.DataImport");
    }
}
