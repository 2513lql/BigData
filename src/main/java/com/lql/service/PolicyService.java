package com.lql.service;

import com.lql.domain.Policy;
import com.lql.domain.PolicyDesription;

import java.util.List;

/**
 * Created by LQL on 2016/4/12.
 */
public interface PolicyService {

    public Policy getPolicyInfoById(String pid);
    List<PolicyDesription> getPolicyByDataIds(List<String> dataIds);
    PolicyDesription getPolicyByDataId(String dataId);
    List<String> getHavePolicyDataIds(List<String> dataIds);

    PolicyDesription getAPIPolicy(String apiName);

}
