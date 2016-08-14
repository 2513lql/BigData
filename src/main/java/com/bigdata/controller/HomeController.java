package com.bigdata.controller;

import com.bigdata.domain.DataInfo;
import com.bigdata.service.DataInfoService;
import com.bigdata.tools.DataInfoSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceix on 2016-03-29.
 */
@Controller
public class HomeController {
    @Autowired
    private DataInfoService dataInfoService;

    @RequestMapping(value = {"/","/index"})
    public ModelAndView index(HttpServletRequest request)  {
        List<DataInfo> topCountData = new ArrayList<DataInfo>();
        List<DataInfo> lastestData = new ArrayList<DataInfo>();
        List<DataInfo> imagData = new ArrayList<DataInfo>();
        List<DataInfo> textData = new ArrayList<DataInfo>();
        List<DataInfo> voiceData = new ArrayList<DataInfo>();
        DataInfoSearchModel pageModel=new DataInfoSearchModel();
        DataInfoSearchModel getImagData = new DataInfoSearchModel();
        DataInfoSearchModel getTextData = new DataInfoSearchModel();
        DataInfoSearchModel getVoiceData = new DataInfoSearchModel();
        getImagData.setType("image");
        getTextData.setType("pdf");
        getVoiceData.setType("mp3");
        topCountData = dataInfoService.GetTopDownloadTimes();
        lastestData = dataInfoService.GetLastestData();
        imagData = dataInfoService.GetDataList(getImagData);
        textData = dataInfoService.GetDataList(getTextData);
        voiceData = dataInfoService.GetDataList(getVoiceData);
        int nums = dataInfoService.GetAllDataCount(pageModel);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        mav.addObject("nums",nums);
        mav.addObject("topCountData",topCountData);
        mav.addObject("lastestData",lastestData);
        mav.addObject("imageData",imagData);
        mav.addObject("textData",textData);
        mav.addObject("voiceData",voiceData);
        return mav;
    }

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest request){
        List<DataInfo> topCountData = new ArrayList<DataInfo>();
        List<DataInfo> lastestData = new ArrayList<DataInfo>();
        List<DataInfo> imagData = new ArrayList<DataInfo>();
        List<DataInfo> textData = new ArrayList<DataInfo>();
        List<DataInfo> voiceData = new ArrayList<DataInfo>();
        DataInfoSearchModel pageModel=new DataInfoSearchModel();
        DataInfoSearchModel getImagData = new DataInfoSearchModel();
        DataInfoSearchModel getTextData = new DataInfoSearchModel();
        DataInfoSearchModel getVoiceData = new DataInfoSearchModel();
        getImagData.setType("image");
        getTextData.setType("pdf");
        getVoiceData.setType("mp3");
        topCountData = dataInfoService.GetTopDownloadTimes();
        lastestData = dataInfoService.GetLastestData();
        imagData = dataInfoService.GetDataList(getImagData);
        textData = dataInfoService.GetDataList(getTextData);
        voiceData = dataInfoService.GetDataList(getVoiceData);
        int nums = dataInfoService.GetAllDataCount(pageModel);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("nums",nums);
        mav.addObject("topCountData",topCountData);
        mav.addObject("lastestData",lastestData);
        mav.addObject("imageData",imagData);
        mav.addObject("textData",textData);
        mav.addObject("voiceData",voiceData);
        return mav;
    }
    @RequestMapping("/header")
    public String header(){
        return "include/header";
    }
}
