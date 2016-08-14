package com.lql.service;

import com.lql.domain.ChainControlInfo;

/**
 * Created by LQL on 2016/4/12.
 */
public interface ChainACInfoService {

   ChainControlInfo getChainControlInfoByDataId(String dataId);

   void addChainControlInfo(String dataId, String dataControlInfos);

   void updateChainControlInfoByDataId(ChainControlInfo chainControlInfo);

}
