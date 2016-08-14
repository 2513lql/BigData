package com.wbl.domain;

/**
 * Created by stone on 2016/7/18.
 */
public class StudentGrade {
    private String sId;
    private String year;
    private String term;
    private String courseCode;
    private String courseName;
    private String credit;
    private String grade;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public StudentGrade() {
    }

    public StudentGrade(String sId, String year, String term, String courseCode, String courseName, String credit, String grade) {
        this.sId = sId;
        this.year = year;
        this.term = term;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.grade = grade;
    }
}
