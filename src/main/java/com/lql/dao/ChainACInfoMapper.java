package com.lql.dao;

import com.lql.domain.ChainControlInfo;

/**
 * Created by LQL on 2016/4/12.
 */
public interface ChainACInfoMapper {


    public ChainControlInfo getChainControlInfoByDataId(String dataId);

    public void addChainControlInfo(ChainControlInfo chainControlInfo);

    public void updateChainControlInfoByDataId(ChainControlInfo chainControlInfo);

}
