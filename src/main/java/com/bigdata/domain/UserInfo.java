package com.bigdata.domain;

import java.util.Date;

/**
 * Created by ceix on 2016-04-12.
 */
public class UserInfo {
    private int userId;
    private String userName;
    private String password;
    private Date insertDate;
    private String email;
    private String apiKey;
    private String hobby;
    private String profession;
    private String company;
    private String department;
    private String type;
    private String plate;

    public String getInsertDataString() {
        return insertDataString;
    }

    public void setInsertDataString(String insertDataString) {
        this.insertDataString = insertDataString;
    }

    private String insertDataString;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
        java.text.DateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        setInsertDataString(f.format(this.insertDate));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
