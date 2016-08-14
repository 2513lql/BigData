package com.lql.dao;

import com.bigdata.domain.DataInfo;

import java.util.List;

/**
 * Created by LQL on 2016/4/12.
 */
public interface AcDataInfoMapper {

    public DataInfo getDataInfoById(String dataId);
    List<DataInfo> queryPolicyIdsByDataId(List<String> dataIds);
    DataInfo queryPolicyIdByDataId(String dataId);
}
