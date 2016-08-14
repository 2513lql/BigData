package com.bigdata.dao;

import com.bigdata.domain.UserInfo;
import com.bigdata.tools.PageModel;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by stone on 2016/4/15.
 */

public class UserInfoMapperImpl extends SqlSessionDaoSupport implements UserInfoMapper {

    public List<UserInfo> GetUserList(PageModel pageModel) {
        return null;
    }


    public int GetUserCount() {
        return this.getSqlSession().selectOne("bigdata.dao.UserInfoMapper.GetUserCount");
    }


    public UserInfo GetUserInfoByName(String username) {
        return this.getSqlSession().selectOne("bigdata.dao.UserInfoMapper.GetUserInfoByName",username);
    }


    public void InsertUserInfo(UserInfo userInfo) {
        this.getSqlSession().selectOne("bigdata.dao.UserInfoMapper.InsertUserInfo",userInfo);
    }

    public void updateUserInfo(UserInfo userInfo){
        this.getSqlSession().update("bigdata.dao.UserInfoMapper.updateUserInfo");
    }
}
