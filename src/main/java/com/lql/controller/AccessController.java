package com.lql.controller;

import access.controller.PolicyGenerator;
import com.bigdata.domain.UserInfo;
import com.bigdata.domain.DataInfo;
import com.lql.domain.IpReg;
import com.lql.domain.PolicyDesription;
import com.lql.service.ChainACInfoService;
import com.lql.service.AcDataInfoService;
import com.lql.service.IpRegService;
import com.lql.service.PolicyService;
import com.lql.util.JSONUtil;
import com.lql.util.PolicyDescriptionUtil;
import com.lql.util.PolicyParserUtil;
import com.lql.util.UserAttributesUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LQL on 2016/4/13.
 */
@Controller
@RequestMapping(value = "/accontroller")
public class AccessController {

    @Autowired
    private ChainACInfoService chainACInfoService;

    @Autowired
    private IpRegService ipRegService;

    @Autowired
    private AcDataInfoService acDataInfoService;

    @Autowired
    private PolicyService policyService;

    //策略生成
    private PolicyGenerator policyGenerator = new PolicyGenerator();


    @RequestMapping(value = "/attributes",method = {RequestMethod.GET})
    @ResponseBody
    public String professionHandler(String attrIndex){
        List<String> attributes = null;
        char attrisIndex = attrIndex.charAt(0);
        switch (attrisIndex){
            case '0':
                attributes = UserAttributesUtil.getProfessionsSet();
                break;
            case '1':
                attributes = UserAttributesUtil.getSchoolsSet();
                break;
            case '2':
                attributes = UserAttributesUtil.getDepartmentsSet();
                break;
            default:
                break;
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("attributesSet",attributes);
        return jsonObj.toString();
    }

    /**
     * policyName:policy1
     * policyDescription: this is data access policy
     * policyEffect:Permit
     * resource:[security:none;operationMsg:yes;]
     * user:[公司职员,;北京邮电大学,;计算机学院,;#]     user:[#]
     * operation:[read,update,]
     * environment:[;;]   environment:[wired,;00:00-6:00,;]
     * data1;data2;data3
     * userId
     * @param list
     * @return
     */
    @RequestMapping(value = "/registePolicy",method = {RequestMethod.POST})
    @ResponseBody
    public String registePolicyHanlder(@RequestBody List<String> list){

        String dataIds = list.get(7);//将dataId取出
        String userId = list.get(8);//将userId取出
        /**
         * 将数据供应链控制信息
         */
        list = PolicyParserUtil.parsePolicyData(list);
        chainACInfoService.addChainControlInfo(dataIds,list.get(3));
        String security = list.remove(3);
        list.add(dataIds);
        list.add(userId);
        Map<String,Object> map = PolicyDescriptionUtil.getPolicyDescription(list);
        String ipRegexp = map.get("ipRegexp").toString();
        String ip = ipRegService.getIpCompany(ipRegexp);
        JSONObject jsonObject =(JSONObject) map.get("jsonObj");
        jsonObject.put("ip",ip);
        jsonObject.put("security",security);
        list.set(1,jsonObject.toString());
        JSONObject jsonObj = JSONUtil.convertListToJson(list);
        policyGenerator.generateAccesPolicy(jsonObj);
        JSONObject jsonObj2 = new JSONObject();
        jsonObj2.put("msg","success");
        return jsonObj.toString();
    }


    @RequestMapping(value = "/registeApiPolicy",method = {RequestMethod.POST})
    @ResponseBody
    public String registeApiPolicyHandler(@RequestBody List<String> list){

        String apiIds = list.get(7);
        String userId = list.get(8);
        list = PolicyParserUtil.parsePolicyData(list);
        list.remove(3);
        list.add(apiIds);
        list.add(userId);
        Map<String,Object> map = PolicyDescriptionUtil.getPolicyDescription(list);
        String ipRegexp = map.get("ipRegexp").toString();
        String ip = ipRegService.getIpCompany(ipRegexp);
        JSONObject jsonObject =(JSONObject) map.get("jsonObj");
        jsonObject.put("ip",ip);
        list.set(1,jsonObject.toString());
        JSONObject jsonStr = JSONUtil.convertListToJson(list);
        policyGenerator.generateApiAccessPolicy(jsonStr);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("msg","success");
        return jsonObj.toString();
    }

    @RequestMapping(value = "/ipregs" , method = {RequestMethod.GET})
    @ResponseBody
    public String getIpRegs(){
        List<IpReg> ipRegs = ipRegService.getIpRegs();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("msg",ipRegs);
        return jsonObj.toString();
    }

    /**
     * 创建数据访问控制策略之前对数据处理
     * @param request
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/createpolicy",method = {RequestMethod.POST})
    public ModelAndView createPolicyHandler(HttpServletRequest request,ModelAndView modelAndView){
        String[] datalist = request.getParameterValues("datalist");
        HttpSession session = request.getSession();
        UserInfo userInfo =(UserInfo) session.getAttribute("userinfo");
        Integer userId = userInfo.getUserId();
        List<String> dataList = new ArrayList<String>();
        StringBuffer dataIds = new StringBuffer("");
        int len = datalist.length;
        for (int i = 0 ;i < len;i++){
            if (i < len - 1) {
                dataIds.append(datalist[i] + ";");
                dataList.add(datalist[i]);
            }else{
                dataIds.append(datalist[i]);
                dataList.add(datalist[i]);
            }
        }
        dataList = policyService.getHavePolicyDataIds(dataList);
        PolicyDesription policyDesription = new PolicyDesription();
        if (dataList.size() > 0) {
            policyDesription = policyService.getPolicyByDataId(dataList.get(0));
            modelAndView.addObject("haveOldPolicy","yes");
        }else{
            modelAndView.addObject("haveOldPolicy","no");
        }
        modelAndView.addObject("policyDescription",policyDesription);
        modelAndView.addObject("datalist",dataList);
        modelAndView.addObject("dataIds",dataIds);
        modelAndView.addObject("userId",userId);
        modelAndView.setViewName("acviews/createPolicy");
        return modelAndView;
    }


    /**
     * http://10.103.240.194:8080/accontroller/createapipolicy.action?apiName=pressure_test&id=37
     * 创建API访问控制策略之前对数据处理
     * @param
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/createapipolicy",method = {RequestMethod.GET})
    public ModelAndView createAPIPolicyHandler(String apiName,String id,ModelAndView modelAndView){

        PolicyDesription policyDesription = policyService.getAPIPolicy(apiName);
        if (policyDesription == null){
            modelAndView.addObject("haveOldPolicy","no");
        }else{
            modelAndView.addObject("haveOldPolicy","yes");
        }
        modelAndView.addObject("policyDescription",policyDesription);
        modelAndView.addObject("apiName",apiName);
        modelAndView.addObject("userId",id);
        modelAndView.setViewName("acviews/createApiPolicy");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/policydescription",method = {RequestMethod.GET})
    public String getPolicyDescription(String dataId){
        PolicyDesription policyDesription = policyService.getPolicyByDataId(dataId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("policyDescription",policyDesription);
        return jsonObj.toString();
    }



    /**
     * judge api can be access
     * @param apiName
     * @param userId
     * @return
     */
    @RequestMapping(value = "/apiAccessControl",method = RequestMethod.GET)
    @ResponseBody
    public String apiAccessHandle(String apiName,Integer userId,HttpServletRequest request){
        access.controller.AccessController controller  = new access.controller.AccessController();
        boolean result = controller.apiAccessControl(userId,apiName,request);
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result",result);
        return jsonObj.toString();
    }


}
