package com.bigdata.controller;

import com.bigdata.common.Auth;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.UserInfo;
import com.bigdata.service.DataInfoService;
import com.bigdata.tools.DataInfoSearchModel;
import com.bigdata.tools.FtpUtil;
import com.wbl.modal.PlatformInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ceix on 2016-05-17.
 */
@Controller
@RequestMapping(value = "/userdata")
public class UserDataController {

    @Autowired
    private DataInfoService dataInfoService;

    @RequestMapping(value = "/datalist", method = RequestMethod.GET)
    @Auth(validate = true)
    public ModelAndView DataList(DataInfoSearchModel search,HttpServletRequest request){
        UserInfo currentUser =(UserInfo) request.getSession().getAttribute("userinfo");
        search.setOwnerId(currentUser.getUserId());
        search.setDataStatus(1);
        int pageCount = dataInfoService.GetDataCount(search);

        List<DataInfo> dataList = dataInfoService.GetDataList(search);
        ModelAndView mav = new ModelAndView();
        search.setPageCount(pageCount);
        mav.setViewName("userdata/datalist") ;
        mav.addObject("dataList", dataList);
        mav.addObject("datasearch",search);
        return mav;
    }

    @RequestMapping(value = "/dataupload",method = RequestMethod.GET)
    @Auth(validate = true)
    public String DataUpload(){
        return "userdata/dataupload";
    }

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/dataupload",method = RequestMethod.POST)
    @Auth(validate = true)
    public ModelAndView DataUpload(DataInfo dataInfo, @RequestParam("dataFile") MultipartFile dataFile){
        UserInfo currentUser = (UserInfo)  request.getSession().getAttribute("userinfo");

        JSONObject json = new JSONObject();
        String[] strs = dataFile.getOriginalFilename().split("\\.");

        //insert
        String lastdata=dataInfoService.GetLastDataId(PlatformInfo.PLATFORM_NAME);

        if(lastdata==null ||lastdata.equals("")) lastdata="platA-0";
        Pattern p = Pattern.compile("([0-9]+$)");
        Matcher m =  p.matcher(lastdata);
        int id=0;
        String prefix;
        if(m.find()){
            id=Integer.parseInt(m.group());
        }else{
            json.put("Success",false);
            json.put("Message","上传数据失败，请联系管理员");
        }
        prefix =lastdata.replace(String.valueOf(id),"");
        id++;

        dataInfo.setDataId(prefix+id);
        dataInfo.setOwnerId(currentUser.getUserId());
        dataInfo.setDataSize((int) dataFile.getSize());
        dataInfo.setTime(new Timestamp(new Date().getTime()));
        dataInfo.setType(strs[strs.length-1]);

        try {

            FtpUtil.fileUploadToPrivate(dataFile.getInputStream(),dataInfo.getDataName(),dataFile.getOriginalFilename(),currentUser.getUserName());
            int result = dataInfoService.InsertData(dataInfo);
            if(result>0){
                json.put("Success",true);
            }
            else  {
                json.put("Success",false);
            }
        } catch (Exception e) {
            json.put("Success",false);
            json.put("Message","上传数据失败，请重试");
        }


        ModelAndView mav = new ModelAndView();
        mav.addObject("viewMsg", json);
        mav.setViewName("userdata/dataupload") ;
        return mav;
    }

}
