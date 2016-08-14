package com.bigdata.service;

import com.bigdata.domain.UserInfo;
import com.bigdata.tools.PageModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ceix on 2016-04-13.
 */
public interface UserInfoService {
    List<UserInfo> GetUserList(PageModel pageModel);
    int GetUserPageCount(PageModel pageModel);
    UserInfo GetUserInfoByName(String username);
    void InsertUserInfo(UserInfo userInfo);
    void updateUserInfo(UserInfo userInfo);
    String getMd5(String str);
    boolean CheckLogin(HttpServletRequest request);
}
