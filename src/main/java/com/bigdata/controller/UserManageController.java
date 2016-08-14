package com.bigdata.controller;

import com.bigdata.common.Auth;
import com.bigdata.domain.UserInfo;
import com.bigdata.service.LoginService;
import com.bigdata.service.UserInfoService;
import com.bigdata.tools.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ceix on 2016-04-13.
 */
@Controller
public class UserManageController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/usermanage/index", method = RequestMethod.GET)
    @Auth(validate = true)
    public ModelAndView usermanage(PageModel page) {
        int pageCount = userInfoService.GetUserPageCount(page);
        List<UserInfo> newsList = userInfoService.GetUserList(page);
        ModelAndView mav = new ModelAndView();
        page.setPageCount(pageCount);
        mav.setViewName("usermanage/index");
        mav.addObject("userList", newsList);
        mav.addObject("pager", page);

        return mav;
    }
//    @RequestMapping(value = "/index")
//    public ModelAndView index(){
//        return new ModelAndView("index");
//    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,String returnUrl)
    {
        returnUrl = returnUrl==null?"index":returnUrl;
        if(returnUrl.charAt(0)=='\\')
            returnUrl = returnUrl.substring(1);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("returnUrl",returnUrl);
        return mav;
    }


    @RequestMapping(value = "/loginCheck")
    public ModelAndView loginCheck(String returnUrl,HttpServletRequest request,HttpSession httpSession, HttpServletResponse response) throws IOException {
        String username;
        String password,error;
        UserInfo userInfo;
        ModelAndView mav= new ModelAndView();
        if(request.getParameter("username")!=null&&request.getParameter("username")!="")
        {
            username= request.getParameter("username");
            userInfo = userInfoService.GetUserInfoByName(username);
            if(userInfo==null)
            {
                error="不存在该用户，请注册";
                mav.setViewName("login");
                mav.addObject("error",error);
                return mav;
            }
        }
        else{
            error="用户名不能为空";
            mav.setViewName("login");
            mav.addObject("error",error);
            return mav;
        }
        if(request.getParameter("password")!=null) {
            password = request.getParameter("password");
        }
        else
        {
            error="密码不能为空";
            mav.setViewName("login");
            mav.addObject("error",error);
            return mav;
        }
        //此处为密码进行MD5加密
        password = userInfoService.getMd5(password);
        if(password.compareTo(userInfo.getPassword())==0)
        {
            if(returnUrl!=null&&returnUrl!="") {
                mav.setViewName("redirect:"+returnUrl);
            }
            else
                mav.setViewName("index");
            mav.addObject("userInfo",userInfo);
            httpSession.setAttribute("username",userInfo.getUserName());
            httpSession.setAttribute("userinfo",userInfo);
        }
        else
        {
            error="用户名或密码错误，请重新输入";
            mav.setViewName("login");
            mav.addObject("error",error);
        }
        return mav;
    }
    @RequestMapping(value = "/register")
    public ModelAndView register()
    {
        return new ModelAndView("register");
    }
    @RequestMapping(value = "/registercheck" , method = RequestMethod.POST)
    public ModelAndView registerCheck(HttpServletRequest request,HttpSession httpSession)
    {
        String error;
        UserInfo userInfo=new UserInfo();
        UserInfo userHas;
        ModelAndView mav= new ModelAndView();
        if(request.getParameter("email")!=null&&request.getParameter("email")!="")
            userInfo.setEmail(request.getParameter("email"));
        else
        {
            error = "注册邮箱不能为空";
            mav.setViewName("register");
            mav.addObject("error",error);
            return mav;
        }
        if(request.getParameter("password")!=null&&request.getParameter("password")!="") {
            String encrypt = userInfoService.getMd5(request.getParameter("password"));
            userInfo.setPassword(encrypt);
        }
        else
        {
            error = "注册密码不能为空";
            mav.setViewName("register");
            mav.addObject("error",error);
            return mav;
        }
        if(request.getParameter("username")!=null&&request.getParameter("username")!="") {
            userHas = userInfoService.GetUserInfoByName(request.getParameter("username"));
            if(userHas!=null)
            {
                error = "该用户名已被注册";
                mav.setViewName("register");
                mav.addObject("error",error);
                return mav;
            }
            userInfo.setUserName(request.getParameter("username"));
        }
        else{
            error = "注册用户名不能为空";
            mav.setViewName("register");
            mav.addObject("error",error);
            return mav;
        }
        userInfoService.InsertUserInfo(userInfo);
        httpSession.setAttribute("username",userInfo.getUserName());
        httpSession.setAttribute("userinfo",userInfo);
        return new ModelAndView("welcome");
    }
    @RequestMapping(value = "/usermanage")
    @Auth(validate = true)
    public ModelAndView usermanage(HttpServletRequest request){
        UserInfo userInfo;
        String userName = request.getSession().getAttribute("username").toString();
        userInfo=userInfoService.GetUserInfoByName(userName);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("usermanage");
        mav.addObject("userInfo",userInfo);
        return mav;
    }

    @RequestMapping(value = "/query/user")
    @Auth(validate = true)
    public ModelAndView queryUser(@RequestParam("username") String userName,
                                  @RequestParam("profession") String profession,@RequestParam("company")String company ,
                                  @RequestParam("department") String department,@RequestParam("hobby")String hobby){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setProfession(profession);
        userInfo.setCompany(company);
        userInfo.setDepartment(department);
        userInfo.setHobby(hobby);
        userInfoService.updateUserInfo(userInfo);
        return null;
    }
    @RequestMapping(value="/changepassword")
    @Auth(validate = true)
    public ModelAndView changepassword(HttpServletRequest request)
    {
        UserInfo userInfo;
        String username = request.getSession().getAttribute("username").toString();
        userInfo = userInfoService.GetUserInfoByName(username);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("changepassword");
        mav.addObject("userInfo",userInfo);
        return mav;
    }
    @RequestMapping(value = "/query/changepassword")
    @Auth(validate = true)
    public ModelAndView querychange(@RequestParam("username")String userName,@RequestParam("password") String password)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        userInfoService.updateUserInfo(userInfo);
        return null;
    }

    @RequestMapping(value = "/welcome")
    @Auth(validate = true)
    public ModelAndView welcome(){

        return new ModelAndView("welcome");
    }
    @RequestMapping(value = "/apiplat")
    public @ResponseBody
    String returnUserName(HttpServletRequest request,@RequestParam("callback") String callback){
        String username;
        if(request.getSession().getAttribute("username")!=null)
        {
            username = request.getSession().getAttribute("username").toString();
            return callback+"( { \"name\":\""+username+"\",\"staus\":1} )";
        }
        else
        {
            return callback + "( { \"staus\":0} )";
        }
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        loginService.RemoveSession(request);
        response.sendRedirect("/index");
    }

}
