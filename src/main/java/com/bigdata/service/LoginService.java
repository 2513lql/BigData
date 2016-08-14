package com.bigdata.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by stone on 2016/5/27.
 */
@Service
public interface LoginService {
    void RemoveCookie(HttpServletResponse response);
    void RemoveSession(HttpServletRequest request);
}
