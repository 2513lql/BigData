package com.lql.dao;

import com.lql.domain.Policy;

import java.util.List;

/**
 * Created by LQL on 2016/4/12.
 */
public interface PolicyMapper {

    public Policy getPolicyInfoById(String pid);
    String getDescriptions(Integer policyId);
}
