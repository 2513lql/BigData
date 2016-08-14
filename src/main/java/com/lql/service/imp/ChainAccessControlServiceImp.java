package com.lql.service.imp;

import access.controller.AccessController;
import com.lql.domain.ChainControlInfo;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.UserInfo;
import com.lql.service.ChainACInfoService;
import com.lql.service.ChainAccessControlService;
import com.lql.service.AcDataInfoService;
import com.lql.service.AcUserInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQL on 2016/4/21.
 */
@Service("chainAccessControlService")
public class ChainAccessControlServiceImp implements ChainAccessControlService{

    private static final String USER_TYPE_COMMON = "普通用户";
    private static final String USER_TYPE_ADMIN = "管理员";

    private static final String DATA_SECURITY_PRIVICY = "privacy";
    private static final String DATA_SECURITY_POLICY= "policy";
    private  static final String DATA_SECURITY_PUBLIC = "public";

    @Autowired
    private ChainACInfoService chainACInfoService;
    @Autowired
    private AcDataInfoService acDataInfoService;
    @Autowired
    private AcUserInfoService acUserInfoService;

    AccessController accessController = new AccessController();
    /**
     * 当前请求用户能否查看数据某个数据供应链
     *
     * @param dataInfos   : 数据供应链上的节点集合
     * @param requesterId : 当前请求用户ID
     * @return
     */
    @Override
    public boolean isChainCanBeSeenByRequester(List<DataInfo> dataInfos, Integer requesterId) {
        UserInfo userInfo = acUserInfoService.getUserInfoById(requesterId);
        if (userInfo == null) {//无此用户，数据供应链不可见
            return false;
        }
        //管理员可见数据供应链
        if (USER_TYPE_ADMIN.equals(userInfo.getType()) || USER_TYPE_ADMIN == userInfo.getType()) {
            return true;
        }
        for (DataInfo dataInfo : dataInfos) {
            if (dataInfo.getOwnerId() == requesterId) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前节点是否可被当前访问用户可见
     *
     * @param dataId      : 当前数据ID
     * @param requesterId ： 请求用户ID
     * @return :
     */
    @Override
    public boolean isCurrentNodeCanBeSeen(String dataId, Integer requesterId,HttpServletRequest request) {
        ChainControlInfo chainControlInfo = chainACInfoService.getChainControlInfoByDataId(dataId);
        DataInfo dataInfo = acDataInfoService.getDataInfoById(dataId);
        UserInfo userInfo = acUserInfoService.getUserInfoById(requesterId);

        //数据为空或者当前访问用户为空
        if(dataInfo == null || userInfo == null){
            return false;
        }
        //管理员可见当前节点
        if (USER_TYPE_ADMIN.equals(userInfo.getType())) {
            return true;
        }
        //个人数据
        if (dataInfo.getOwnerId() == requesterId){
            return true;
        }
        //私密数据
        if(chainControlInfo != null && chainControlInfo.getSecurity() !=null && chainControlInfo.getSecurity().equals(DATA_SECURITY_PRIVICY)){
            if(dataInfo.getOwnerId() == requesterId){
                return true;
            }else{
                return false;
            }
        }
        //公开数据
        if(chainControlInfo != null && chainControlInfo.getSecurity() !=null && chainControlInfo.getSecurity().equals(DATA_SECURITY_PUBLIC)){
            return true;
        }

        return  accessController.chainAccessControl(requesterId,dataId,"read",request);
    }

    @Override
    public JSONObject dataMessageCanSeen(String dataId, Integer requesterId) {

        JSONObject jsonObj = new JSONObject();
        Integer result;
        Integer ownerId =  acDataInfoService.getDataInfoById(dataId).getOwnerId();
        UserInfo requester = acUserInfoService.getUserInfoById(requesterId);
        if(ownerId == requesterId || USER_TYPE_ADMIN.equals(requester.getType())){
            jsonObj.put("result",3);
            return jsonObj;
        }
        String relation = acUserInfoService.getRelationByUserAttris(requesterId, ownerId);
        if ("collaboration".equals(relation)){
            result = 3;
        }else if ("competition".equals(relation)){
            result = 1;
        }else {
            result = 2;
        }
        jsonObj.put("result",result);
        return jsonObj;
    }

    @Override
    public boolean dataAccessControl(String dataId, Integer requesterId, String operation, HttpServletRequest request) {
        return accessController.accessControl(requesterId,dataId,operation,request);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ChainAccessControlService chainAccessControlService = context.getBean("chainAccessControlService",ChainAccessControlService.class);
//        System.out.println(chainAccessControlService.dataMessageCanSeen("data5",30));

//        System.out.println(chainAccessControlService.isCurrentNodeCanBeSeen("data5",27));

        List<DataInfo> dataInfos = new ArrayList<>();
        DataInfo dataInfo = new DataInfo();
        dataInfo.setDataId("data5");
        dataInfo.setOwnerId(26);
        dataInfos.add(dataInfo);
        System.out.println(chainAccessControlService.isChainCanBeSeenByRequester(dataInfos,27));
    }
}
