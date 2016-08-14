package com.bigdata.controller;

import com.bigdata.common.Auth;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.Prov;
import com.bigdata.domain.UserInfo;
import com.bigdata.modal.enums.FuctionType;
import com.bigdata.service.DataInfoService;
import com.bigdata.service.ProvService;
import com.bigdata.tools.DataInfoSearchModel;
import com.bigdata.tools.FtpUtil;
import com.bigdata.tools.PageModel;
import com.bigdata.tools.ProvInfoModel;
//import com.wbl.modal.FtpInfo;
import com.bigdata.modal.FtpInfo;
import com.lql.service.ChainAccessControlService;
import com.wbl.modal.PlatformInfo;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

/**
 * Created by stone on 2016/4/19.
 */
@Controller
public class DataSelectController {
    @Autowired
    private DataInfoService dataInfoService;

    @Autowired
    private ProvService provService;

    @Autowired
    private ChainAccessControlService chainAccessControlService;


    @RequestMapping(value="/data/query")
    public @ResponseBody JSONObject dataquery(@RequestParam("type") String type,
                                              @RequestParam("functionType") String functiontype,
                                              @RequestParam("OrderBy") String orderby,
                                              @RequestParam("pageNum") Integer pageNum) throws JSONException {
        if(pageNum==null||pageNum==0) pageNum=1;

        JSONObject result = new JSONObject();
        type = "all".equals(type)?null:type;
        functiontype = "all".equals(functiontype)?null:functiontype;
        orderby = "all".equals(orderby)?null:orderby;
        DataInfoSearchModel pageModel = new DataInfoSearchModel();
        pageModel.setPageSize(3);
        pageModel.setOrderBy(orderby);
        pageModel.setType(type);
        pageModel.setFunctionType(functiontype);
        pageModel.setPageNum(pageNum);
        int pageCount = dataInfoService.GetDataCount(pageModel);
        List<DataInfo> dataInfos;
        dataInfos = dataInfoService.GetDataList(pageModel);


        pageModel.setPageCount(pageCount);

        result.put("datas",dataInfos);
        result.put("pageModel",pageModel);

        return result;
    }

    @RequestMapping(value="/data/querysearch")
    public @ResponseBody JSONObject querysearch(@RequestParam("keys")String keys,@RequestParam("pageNum") Integer pageNum)
    {
        JSONObject result = new JSONObject();
        List<DataInfo> dataInfos = new ArrayList<DataInfo>();
        DataInfoSearchModel pageModel = new DataInfoSearchModel();

        List<String> description=dataInfoService.IKAnalyzer(keys);
        pageModel.setDescription(description);
        pageModel.setPageSize(3);
        pageModel.setPageNum(pageNum);
        int pageCount = dataInfoService.SearchByDescriptionCount(pageModel);

        dataInfos = dataInfoService.SearchByDescription(pageModel);
        pageModel.setPageCount(pageCount);
        result.put("datas",dataInfos);
        result.put("pageModel",pageModel);
        return result;
    }

    @RequestMapping(value = "/datasearch")
    public ModelAndView GetDataSearch(DataInfoSearchModel search,HttpServletRequest request) throws JSONException {

        int pageCount = dataInfoService.GetDataCount(search);
        if(request.getParameter("functiontype")!=null)
            search.setFunctionType(request.getParameter("functiontype"));
        List<DataInfo> dataList;
        dataList = dataInfoService.GetDataList(search);
        ModelAndView mav = new ModelAndView();
        search.setPageCount(pageCount);
        mav.setViewName("datasearch") ;
        mav.addObject("dataList", dataList);
        mav.addObject("dataSearch",search);
        return mav;
    }

    @RequestMapping(value = "/operate/index")
    public ModelAndView GetDataDetail(@RequestParam("dataId") String dataId,ProvInfoModel provInfoModel){

        List<DataInfo> dataInfos;
        List<Prov> provs;
        int num;
        DataInfoSearchModel pageModel = new DataInfoSearchModel();

        pageModel.setDataId(dataId);
        dataInfos = dataInfoService.GetDataList(pageModel);
        provInfoModel.setEntity(dataId);
        provInfoModel.setActivity("DOWNLOAD");
        int pageCount = provService.GetProvPageCount(provInfoModel);
        provInfoModel.setPageCount(pageCount);
        provs = provService.GetProvList(provInfoModel);
        num = provService.GetProvCount(provInfoModel);
        ModelAndView mav = new ModelAndView();
        mav.addObject("downList",provs);
        mav.setViewName("dataDetail");
        mav.addObject("dataList",dataInfos);
        mav.addObject("num",num);
        mav.addObject("dataDetail",provInfoModel);
        return mav;
    }

    @RequestMapping(value = "/operate/download")
    @Auth(validate = true)
    @ResponseBody
    public String download(HttpServletRequest request,
                         HttpServletResponse response, String dataName,String dataId, String type) throws Exception {

        JSONObject jsonObj = new JSONObject();
        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userinfo");
        userInfo.getUserId();
        boolean flag = chainAccessControlService.dataAccessControl(dataId,userInfo.getUserId(),"download",request);
        if (flag == false){
            jsonObj.put("message","false");
            return jsonObj.toString();
        }

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        byte[] data = null;
        dataName = dataName + "."+type;
        try {
            data = FtpUtil.fileDownloadFromPublic(dataName);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + dataName + "\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            jsonObj.put("message","success");
//            更新prov信息表，添加download记录
//            Prov prov = new Prov();
//            prov.setPrefix(PlatformInfo.PLATFORM_NAME);
//            prov.setEntity(dataId);
//            prov.setActivity("DOWNLOAD");
//            prov.setTime(new Timestamp(System.currentTimeMillis()));
//            prov.setAgent((String)(request.getSession().getAttribute("username")));
//            prov.setUsed("platA:"+dataId);
//            provService.InsertProv(prov);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            return jsonObj.toString();
        }
    }

    @RequestMapping(value = "/datamanage",method = RequestMethod.GET)
    @Auth(validate = true)
    public ModelAndView datamanage(@RequestParam("dataId")String dataId)
    {
        DataInfo dataInfo;
        dataInfo = dataInfoService.SearchByDataId(dataId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("datamanage");
        String test = dataInfo.getFunctionType().toUpperCase();
        //System.out.println(FuctionType.valueOf(test).getName());
        mav.addObject("dataInfo",dataInfo);
        return mav;
    }

    @RequestMapping(value = "/updatedata")
    @Auth(validate = true)
    public ModelAndView updatedate(String dataId,String dataName,
                                   String description,String functionType)
    {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setDataId(dataId);
        dataInfo.setDataName(dataName);
        dataInfo.setDescription(description);
        dataInfo.setFunctionType(functionType);
        dataInfoService.UpdateDataInfo(dataInfo);
        return null;

    }
//    下面仅为测试
    @RequestMapping(value="/importdata")
    public void importData()
    {
        dataInfoService.DataImport("E:\\news.csv");
        System.out.println("ok");
    }
}
