package com.lql.service.imp;

import com.bigdata.domain.DataInfo;
import com.lql.dao.AcAPIPolicyMapper;
import com.lql.dao.AcDataInfoMapper;
import com.lql.dao.PolicyMapper;
import com.lql.domain.Policy;
import com.lql.domain.PolicyDesription;
import com.lql.service.PolicyService;
import com.lql.util.PolicyDescriptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQL on 2016/4/12.
 */
@Transactional
@Service("policyService")
public class PolicyServiceImp implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private AcDataInfoMapper acDataInfoMapper;
    @Autowired
    private AcAPIPolicyMapper acAPIPolicyMapper;

    @Override
    public Policy getPolicyInfoById(String pid) {

        Policy policy = policyMapper.getPolicyInfoById(pid);
        return policy;
    }

    @Override
    public List<PolicyDesription> getPolicyByDataIds(List<String> dataIds) {

        List<PolicyDesription> policyDesriptions = new ArrayList<PolicyDesription>();
        List<DataInfo> dataInfos = acDataInfoMapper.queryPolicyIdsByDataId(dataIds);
        for (DataInfo dataInfo : dataInfos) {
            if (dataInfo.getPolicyId() != null && !"".equals(dataInfo.getPolicyId())) {
                Integer policyId = Integer.parseInt(dataInfo.getPolicyId().split(",")[0]);
                String description = policyMapper.getDescriptions(policyId);
                PolicyDesription policyDesription = PolicyDescriptionUtil.parseDescription(description);
                policyDesription.setDataId(dataInfo.getDataId());
                policyDesriptions.add(policyDesription);
            }
        }
        return policyDesriptions;
    }

    @Override
    public PolicyDesription getPolicyByDataId(String dataId) {

        DataInfo dataInfo = acDataInfoMapper.queryPolicyIdByDataId(dataId);
        if (dataInfo.getPolicyId() == null || "".equals(dataInfo.getPolicyId())) {
            return null;
        }
        Integer policyId = Integer.parseInt(dataInfo.getPolicyId().split(",")[0]);
        String description = policyMapper.getDescriptions(policyId);
        PolicyDesription policyDesription = PolicyDescriptionUtil.parseDescription(description);
        policyDesription.setDataId(dataInfo.getDataId());
        return policyDesription;
    }

    @Override
    public List<String> getHavePolicyDataIds(List<String> dataIds) {
        List<DataInfo> dataInfos = acDataInfoMapper.queryPolicyIdsByDataId(dataIds);
        List<String> dataIdList = new ArrayList<String>();
        for (DataInfo dataInfo : dataInfos) {
            if (dataInfo.getPolicyId() != null && !"".equals(dataInfo.getPolicyId())) {
                dataIdList.add(dataInfo.getDataId());
            }
        }
        return dataIdList;
    }

    @Override
    public PolicyDesription getAPIPolicy(String apiName) {

        String policyId = acAPIPolicyMapper.queryAPIPolicyId(apiName);
        if (policyId == null || "".equals(policyId)){
            return null;
        }else{
            String description = policyMapper.getDescriptions(Integer.parseInt(policyId));
            PolicyDesription policyDesription = PolicyDescriptionUtil.parseDescription(description);
            policyDesription.setDataId(apiName);
            return policyDesription;
        }
    }
}
