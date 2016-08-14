package com.lql.util;

/**
 * Created by LQL on 2016/4/14.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 此类负责解析所制定的数据访问控制策略字符串
 * AJax封装的数据访问控制策略参数格式如下:我们需要对其进行解析并保存都数据库中。
 * <p/>
 * policyName:policy1
 * policyDescription: this is data access policy
 * policyEffect:Permit
 * resource:[security:none;operationMsg:yes;]
 * user:[公司职员|1;北京邮电大学|1;计算机学院|1;#]     user:[#]
 * operation:[read,update,]
 * environment:[;;]   environment:[wired,;00:00-6:00,;]
 *
 */
public class PolicyParserUtil {

    public static List<String> parsePolicyData(List<String> list) {

        if (list == null) {
            return null;
        }

        List<String> newList = new ArrayList<String>();

        String policyName = UUIDUtil.generatePolicyName();
        if (parseNDEPolicyData(list.get(0)) != null) {
            policyName = parseNDEPolicyData(list.get(0));
        }
        newList.add(policyName.trim());

        String policyDescription = "";
        if (parseNDEPolicyData(list.get(1)) != null) {
            policyDescription = parseNDEPolicyData(list.get(1));
        }
        newList.add(policyDescription.trim());

        String policyEffect = parseNDEPolicyData(list.get(2));
        newList.add(policyEffect);

        String resourceAttris = parseResourceAttris(list.get(3));
        newList.add(resourceAttris);

        String userAttris = parseUserAttris(list.get(4));
        newList.add(userAttris);

        String operaAttris = parseOperation(list.get(5));
        newList.add(operaAttris);

        String environmentsAttris = parseEnvironment(list.get(6));
        newList.add(environmentsAttris);
        return newList;
    }

    /**
     * 解析
     * policyName:policy1
     * policyDescription: this is data access policy
     * policyEffect:Permit
     * 风格的字符串
     *
     * @param str
     */
    public static String parseNDEPolicyData(String str) {
        String[] tmp = str.split(":");
        if (tmp.length <= 1) {
            return null;
        }
        if ("".equals(tmp[1].trim())) {
            return null;
        }
        return tmp[1];
    }


    /**
     * 解析
     * resource:[security:none;operationMsg:yes;]
     *
     * @return   security;yes
     */
    public static String parseResourceAttris(String resStr) {
        int index = resStr.indexOf("[");
        String tmp = resStr.substring(index + 1, resStr.length() - 1);
        String[] resources = tmp.split(";");
        String security = resources[0].substring(9);
        String operationMsg = resources[1].substring(13);
        if (security.equals("none")) {
            security = "policy";
        }

        return security + ";" + operationMsg;
    }

    /**
     * 解析用户属性字符串
     * user:[公司职员|1;北京邮电大学|1;计算机学院|1;#]
     *
     * @param userStr
     * @return 公司职员|1;北京邮电大学|1;计算机学院|1;3  如果没有用户属性策略 0
     */
    public static String parseUserAttris(String userStr) {
        int index = userStr.indexOf("[");
        String tmp = userStr.substring(index + 1, userStr.length() - 1);
        tmp = tmp.trim();      //将用户门限数字后用户可能误输入的空格字符去除
        char cTmp = tmp.charAt(tmp.length() - 1); //取出用户属性门限值
        tmp = tmp.substring(0, tmp.length() - 1); //将门限值去除，然后对属性进行处理
        tmp = tmp.trim();     //将用户门限数字前用户可能误输入的空格字符去除
        if("".equals(tmp)){
            return "0";  //用户没有选择属性策略
        }
        String[] attris = tmp.split(";");
        Integer threshold = 0;
        if (!Character.isDigit(cTmp)) { //首先判断用户提交的门限值是否是数字，不是数字则按属性个数为门限值
            threshold = attris.length;
        } else {//是数字在判断用户的输入的门限值是否超过属性个数,超过则为属性个数
            int n = Integer.parseInt(String.valueOf(cTmp));
            threshold = n < attris.length ? n : attris.length;
        }
        tmp = tmp.substring(0,tmp.length() - 1) + "#";
        tmp = tmp + String.valueOf(threshold);
        return tmp;
    }

    /**
     * 判断字符串str中含有字符c的个数
     * @param str
     * @param c
     * @return
     */
    public static int containsCharNum(String str , char c){
        int n = 0;
        if(str == null || "".equals(str)){
            return  0;
        }
        for (int i = 0 ; i < str.length() ;i++){
            if(str.charAt(i) == c){
                n++;
            }
        }
        return n;
    }

    /**
     * 解析 操作属性
     * @param operaStr  operation:[read,update,]
     * @return  read,update  没选 read
     */
    public static String parseOperation(String operaStr){

        int index = operaStr.indexOf("[");
        String tmp = operaStr.substring(index + 1, operaStr.length() - 1);
        if("".equals(tmp)){
            return "read";
        }
        return tmp.substring(0,tmp.length() - 1);
    }

    /**
     *解析环境属性
     * "environment:[wired,;00:00-6:00,;]"      environment:[;;]
     * @param environmentStr
     * @return  wired,1;00:00-6:00,1;2   如果无环境属性 0
     */
    public static String parseEnvironment(String environmentStr){

        int index = environmentStr.indexOf("[");
        String tmp = environmentStr.substring(index + 1, environmentStr.length() - 1);
        String[] environments = tmp.split(";");
        int threshold = environments.length;
        String result = "";
        for (int i = 0 ; i  < environments.length ; i++){
            environments[i] = environments[i].substring(0, environments[i].length() - 1);
            environments[i] += "#1";
            if (i < environments.length - 1) {
                result += environments[i] + ";";
            }
            if (i == environments.length - 1){
                result += environments[i] + "#";
            }
        }
        result += threshold +"";
        return result;
    }


    public static void main(String[] args) {
        System.out.println(parseEnvironment("environment:[wired,no-wired,;00:00-6:00,;]"));
    }


}
