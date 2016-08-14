package com.bigdata.dao;

import com.bigdata.domain.UserInfo;
import com.bigdata.tools.PageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ceix on 2016-04-12.
 */
@Repository
public interface UserInfoMapper {
    List<UserInfo> GetUserList(PageModel pageModel);
    int GetUserCount();
    UserInfo GetUserInfoByName(String username);
    void InsertUserInfo(UserInfo userInfo);
    void updateUserInfo(UserInfo userInfo);

}