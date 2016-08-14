package com.bigdata.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by stone on 2016/5/27.
 */
@Service(value = "loginService")
public class LoginServiceImp implements LoginService {
    @Override
    public void RemoveCookie(HttpServletResponse response) {

    }

    @Override
    public void RemoveSession(HttpServletRequest request) {
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("userinfo");
    }
}
