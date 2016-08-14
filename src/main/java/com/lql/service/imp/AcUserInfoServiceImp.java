package com.lql.service.imp;

import com.lql.dao.AcUserInfoMapper;
import com.lql.dao.AcUserRelationMapper;
import com.bigdata.domain.UserInfo;
import com.lql.service.AcUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LQL on 2016/4/12.
 */
@Transactional
@Service("acUserInfoService")
public class AcUserInfoServiceImp implements AcUserInfoService {

    @Autowired
    private AcUserInfoMapper acUserInfoMapper;

    @Autowired
    private AcUserRelationMapper acUserRelationMapper;

    @Override
    public UserInfo getUserInfoById(Integer userId) {
        UserInfo userInfo = acUserInfoMapper.getUserInfoById(userId);
        return userInfo;
    }

    @Override
    public String getRelationByUserAttris(Integer requesterId, Integer dataOwnerId) {

        UserInfo requester = acUserInfoMapper.getUserInfoById(requesterId);
        UserInfo dataOwner = acUserInfoMapper.getUserInfoById(dataOwnerId);
        String requesterAttris = getUserAttris(requester);
        String dataOwnerAttris = getUserAttris(dataOwner);
        String relation = acUserRelationMapper.getRelationByUserAttris(requesterAttris,dataOwnerAttris);
        if (relation == null || "".equals(relation)){
            relation = "norelation";
        }
        return relation;
    }

    public static String getUserAttris(UserInfo userInfo){

        StringBuffer userAttris = new StringBuffer();
        userAttris.append(userInfo.getProfession());
        userAttris.append(";"+userInfo.getCompany());
        userAttris.append(";"+userInfo.getDepartment());
        return userAttris.toString();
    }
}
