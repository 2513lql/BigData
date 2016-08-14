package com.lql.util;

import com.lql.domain.PolicyDesription;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LQL on 2016/6/1.
 */
public class PolicyDescriptionUtil {

    public static Map<String,Object> getPolicyDescription(List<String> list) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("policyName", list.get(0));
        jsonObj.put("description", list.get(1));
        jsonObj.put("effect", handlePolicyEffect(list.get(2)));
        String userAttris = handleUserAttris(list.get(3));
        userAttris = userAttris.replaceAll(";","and");
        userAttris = userAttris.replaceAll(",","or");
        jsonObj.put("userAttris", userAttris);
        jsonObj.put("operations",list.get(4));
        String[] environments = list.get(5).split(";");
        String network = getEnvironment(environments[0]);
        network = network.replaceAll(",","或者");
        jsonObj.put("network", network);
        jsonObj.put("time",getEnvironment(environments[1]));
        String ipRegexp = getEnvironment(environments[2]);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("jsonObj",jsonObj);
        map.put("ipRegexp",ipRegexp);
        return map;
    }

    //handle policy effect
    public static String handlePolicyEffect(String tmp) {
        Integer integer = Integer.parseInt(tmp);
        String result = integer % 2 == 0 ? "deny" : "permit";
        return result;
    }

    public static String handleUserAttris(String tmp) {

        StringBuffer result = new StringBuffer("");
        String[] attris = tmp.split(";");
        for (int i = 0, len = attris.length; i < len; i++) {
            int index = attris[i].indexOf("#");
            result.append(attris[i].substring(0, index));
            if (i < attris.length - 1) {
                result.append(";");
            }
        }
        return result.toString();
    }

    //{"policyName":"dak","description":"aahdl","effect":"permit","userAttris":"teaordocandbupt","operations":"read","network":"wired,wireless","time":"00-24","ip":"low"}
    public static PolicyDesription parseDescription(String description){
        PolicyDesription policyDesription = new PolicyDesription();
        JSONObject jsonObj = JSONObject.fromObject(description);
        policyDesription.setPolicyName(String.valueOf(jsonObj.get("policyName")));
        policyDesription.setDescription(String.valueOf(jsonObj.get("description")));
        policyDesription.setEffect(String.valueOf(jsonObj.get("effect")));
        policyDesription.setSecurity(String.valueOf(jsonObj.get("security")));
        policyDesription.setUserAttris(String.valueOf(jsonObj.get("userAttris")));
        policyDesription.setOperations(String.valueOf(jsonObj.get("operations")));
        policyDesription.setNetwork(String.valueOf(jsonObj.get("network")));
        policyDesription.setTime(String.valueOf(jsonObj.get("time")));
        policyDesription.setIp(String.valueOf(jsonObj.get("ip")));
        return policyDesription;
    }

    public static String getEnvironment(String str){
        int index = str.indexOf("#");
        return str.substring(0,index);
    }

    public static void main(String[] args) {
        String des = "{\"policyName\":\"dak\",\"description\":\"aahdl\",\"effect\":\"permit\",\"userAttris\":\"teaordocandbupt\",\"operations\":\"read\",\"network\":\"wired,wireless\",\"time\":\"00-24\",\"ip\":\"low\"}";
        PolicyDesription policyDesription = parseDescription(des);
        System.out.println(policyDesription);
    }

}
