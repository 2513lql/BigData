package com.lql.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQL on 2016/4/13.
 */
public class UserAttributesUtil {


    /**
     * 获取职业属性集
     * @return
     */
    public static List<String> getProfessionsSet(){
        List<String> attributes = new ArrayList<String>();
        attributes.add("职业");
        attributes.add("老师");
        attributes.add("学生");
        attributes.add("医生");
        attributes.add("公司职员");
        return attributes;
    }

    /**
     * 获取学校属性集
     * @return
     */
    public static List<String> getSchoolsSet(){

        List<String> attributes = new ArrayList<String>();
        attributes.add("单位/公司");
        attributes.add("北京邮电大学");
        attributes.add("清华大学");
        attributes.add("北京航空航天大学");
        return attributes;
    }

    /**
     * 获取学校学院
     */
    public static List<String> getDepartmentsSet(){
        List<String> attributes = new ArrayList<String>();
        attributes.add("学院/部门");
        attributes.add("计算机学院");
        attributes.add("信息与通信工程学院");
        attributes.add("电子学院");
        attributes.add("自动化学院");
        attributes.add("教务处");
        attributes.add("财务处");
        attributes.add("后勤处");
        attributes.add("人力资源部");
        attributes.add("产品研发部");
        attributes.add("市场部");
        return attributes;
    }

    /**
     * 获取年级
     * @return
     */
    public static List<String> getGrades(){
        List<String> attributes = new ArrayList<>();
        attributes.add("2009级");
        attributes.add("2010级");
        attributes.add("2011级");
        attributes.add("2012级");
        attributes.add("2013级");
        attributes.add("2014级");
        attributes.add("2015级");
        attributes.add("2016级");
        return attributes;
    }

    /**
     * 获取公司部门
     * @return
     */
    public static List<String> getCompanyDepartsSet(){
        List<String> attributes = new ArrayList<String>();
        attributes.add("部门");
        attributes.add("人力资源部");
        attributes.add("产品研发部");
        attributes.add("市场部");
        return attributes;
    }

    public static void main(String[] args) {
        System.out.println("老师");
        List<String> list = getProfessionsSet();
        for (String s : list){
            System.out.println(s);
        }

    }
}
