package com.lql.service;

import com.bigdata.domain.UserInfo;

/**
 * Created by LQL on 2016/4/12.
 */
public interface AcUserInfoService {


    UserInfo getUserInfoById(Integer userId);

    String getRelationByUserAttris(Integer requesterId,Integer dataOwnerId);
}
