package com.bigdata.common;

import com.bigdata.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * Created by stone on 2016/5/16.
 */
public class AuthInterceptor implements HandlerInterceptor{
    @Autowired
    UserInfoService userInfoService;
    //
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof DefaultServletHttpRequestHandler) return true;

        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Auth annotation = method.getAnnotation(Auth.class);
        //若注解无需验证，则直接返回
        if(annotation==null){return true;}

        //如果已经登陆
        if(userInfoService.CheckLogin(request)){return true;}

        //重定向登陆页面
        String query=request.getQueryString();
        String retrunUrl = request.getRequestURI()+(query==null?"":"?"+query);
        String temp[] = retrunUrl.split("[?]");
        if(temp[0].equals("/operate/download"))
            retrunUrl ="/operate/index?"+temp[1];
        if(retrunUrl.contains("/login")) return true;
        response.sendRedirect("/login?returnUrl="+URLEncoder.encode(retrunUrl,"UTF-8"));
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
