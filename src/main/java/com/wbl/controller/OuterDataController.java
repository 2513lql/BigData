package com.wbl.controller;

import com.bigdata.common.Auth;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.UserInfo;
import com.bigdata.service.DataInfoService;
import com.bigdata.tools.FtpUtil;
import com.wbl.aop.ProvActivity;
import com.wbl.aop.ProvAnnotation;
import com.wbl.modal.PlatformInfo;
import com.wbl.modal.exception.FTPException;
import com.wbl.modal.exception.RequestException;
import com.wbl.service.OuterDataQueryService;
import com.wbl.service.ProvService;
import com.wbl.util.HttpRequestUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wbl.modal.Enum.Activity.EXPORT;
import static com.wbl.modal.Enum.Activity.SPLIT;
import static com.wbl.modal.Enum.Activity.UPLOAD;

/**
 * Created by stone on 2016/7/15.
 */
@Controller
@RequestMapping("/studentQuery")
public class OuterDataController {
    private Logger logger = Logger.getLogger(OuterDataController.class);
    @Autowired
    private OuterDataQueryService outerDataQueryService;

    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private ProvService provService;

    @RequestMapping("/queryLendBook")
    @ResponseBody
    public JSONObject lendBookQueryById(@RequestParam("sId")String sId)
    {
        DataInfo dataInfo = dataInfoService.SearchByDataName(sId);
        if(dataInfo==null)
        {
            logger.debug("didn't query the data,now build the data");
            return outerDataQueryService.QueryLendBookById(sId);
        }
        else
        {
            logger.debug("query the data,there will not product new data");
            return null;
        }

    }

    @Auth(validate = true)
    @RequestMapping("/queryGrade")
    @ResponseBody
    public List<JSONObject> queryGrade(@RequestParam("sId")String sId,HttpServletRequest request) throws IOException {
        List<JSONObject> results = outerDataQueryService.QueryGradeById(sId);
        String fileName = "s"+sId+"_grade.json";
        FileOutputStream fos=new FileOutputStream("D://"+fileName);
        fos.write(results.toString().getBytes("UTF-8"));
        fos.flush();
        fos.close();
        logger.debug("the new data in D:,which name is "+fileName);
        UserInfo currentUser = (UserInfo)  request.getSession().getAttribute("userinfo");
        //文件上传
        File dataFile = new File("D://"+fileName);
        InputStream ins =  new FileInputStream(dataFile);
        String[] strs = dataFile.getName().split("\\.");
        String type = strs[1];
        try {

            FtpUtil.fileUploadToPrivate(ins, strs[0], dataFile.getName(), currentUser.getUserName());
        } catch (Exception e) {
            logger.debug("upload is error");
        }
        queryGradeRecordData(fileName,type,"student_grade",request);
        return results;
    }

    public void queryGradeRecordData(String fileName,String type,String originalDataName,HttpServletRequest request){
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
        provService.recordSplit(fileName,type,originalDataName,userInfo);
    }

    @RequestMapping("/aggressionquery")
    public void AggressionQueryResult(HttpServletRequest request,HttpServletResponse response,String sId) throws FTPException, IOException {
//        1.下载本地文件(搞定)
//        2.跨平台下载第二个文件
//        3.融合，记录
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
        byte[] data1 = null;
//        byte[] data2 = null;
        String dataName1= "s"+sId+"_grade";
//        以后可能会改
        String dataName2 = "s"+sId+"_course";
        System.out.println(dataName1);
        data1 = FtpUtil.fileDownloadFromPublic(dataName1+".json");
//        data2 = FtpUtil.fileDownloadFromPublic(dataName2+".json");
        FileOutputStream outputStream = new FileOutputStream("D://"+"s"+sId+"_aggr.json");
        outputStream.write(data1);
        outputStream.flush();

        String url = PlatformInfo.PLATFORM_QUERY_URL;
        url = url.substring(0,url.indexOf("prov"));
        url += "studentQuery/download";
        String param = "dataName="+dataName2;
        try {
            HttpRequestUtil.doPostRequest(url, param);
        } catch (RequestException e) {
            e.printStackTrace();
            //logger.debug("NotifyOtherPlatform for download fail");
        }

        outputStream.close();


//        实现文件下载到指定D盘 但不记录下载 就记录aggresion操作
//        List<String> dataNames = new ArrayList<>();
//        dataNames.add(dataName1);
//        dataNames.add(dataName2);
//        provService.recordAggregation("s"+sId+"_aggr","json",userInfo,dataNames);
//        outputStream.write(data2);


        //将文件上传上去
        File dataFile = new File("D://"+"s"+sId+"_aggr.json");
        InputStream ins =  new FileInputStream(dataFile);
        String[] strs = dataFile.getName().split("\\.");
        String type = strs[1];
        try {

            FtpUtil.fileUploadToPrivate(ins, strs[0], dataFile.getName(), userInfo.getUserName());
        } catch (Exception e) {
            logger.debug("upload is error");
        }

    }
    @RequestMapping("/download")
    public void DownloadDataFromOtherPlat(@RequestParam("dataName")String dataName,HttpServletRequest request) throws FTPException, IOException {
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userinfo");
        DataInfo dataInfo = dataInfoService.SearchByDataName(dataName);
        byte[] data = null;
        data = FtpUtil.fileDownloadFromPublic(dataInfo.getDataName()+"."+dataInfo.getType());
        FileOutputStream outputStream = new FileOutputStream("F:\\projectSum\\BigDataPlatform\\BigData\\temp\\"+dataInfo.getDataName()+"."+dataInfo.getType());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        provService.recordDownloadProv(dataName,userInfo);
        System.out.println("OK");
    }

}
