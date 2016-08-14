package com.bigdata.service;

import com.bigdata.dao.UserInfoMapper;
import com.bigdata.dao.UserInfoMapperImpl;
import com.bigdata.domain.UserInfo;
import com.bigdata.tools.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by ceix on 2016-04-13.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements  UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper=new UserInfoMapperImpl();

    public List<UserInfo> GetUserList(PageModel pageModel) {
        return userInfoMapper.GetUserList(pageModel);
    }

    public int GetUserPageCount(PageModel pageModel) {
        double count = userInfoMapper.GetUserCount();
        System.out.println(count);
        return (int)Math.ceil(count/ pageModel.getPageSize());
    }


    public UserInfo GetUserInfoByName(String username) {
        return userInfoMapper.GetUserInfoByName(username);
    }


    public void InsertUserInfo(UserInfo userInfo) {
        userInfoMapper.InsertUserInfo(userInfo);
    }
    public void updateUserInfo(UserInfo userInfo){
        userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public String getMd5(String str) {
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs){
            if((x & 0xff)>>4 == 0 ){
                sb.append("0").append(Integer.toHexString(x & 0xff));
            }
            else
            {
                sb.append(Integer.toHexString((x & 0xff)));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean CheckLogin(HttpServletRequest request) {
        return request.getSession().getAttribute("username")!=null;
    }
}
