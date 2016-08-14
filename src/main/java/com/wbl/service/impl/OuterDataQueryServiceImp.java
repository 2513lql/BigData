package com.wbl.service.impl;

import com.mongodb.util.JSON;
import com.wbl.dao.OuterDataDao;
import com.wbl.domain.BookLend;
import com.wbl.domain.Student;
import com.wbl.domain.StudentGrade;
import com.wbl.service.OuterDataQueryService;
import com.wbl.service.OuterDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 2016/7/15.
 */
@Service(value = "outerDataQueryService")
public class OuterDataQueryServiceImp implements OuterDataQueryService{
    private static Logger logger = Logger.getLogger(OuterDataQueryServiceImp.class);
    @Autowired
    private OuterDataDao outerDataDao;


    @Override
    public JSONObject QueryLendBookById(String sId){
        JSONObject object = new JSONObject();
        /*Student student = outerDataDao.getStudentById(sId);
        List<BookLend> bookLendlist = outerDataDao.getBookLendInfo();
        if(student==null)
        {
            logger.error("并没有该同学信息");
            object.put("查询ID",sId);
            object.put("status","error");
            object.put("msg","该同学不存在，请核对输入的ID号");
        }
        else
        {
            logger.debug("query student id is success");
            object.put("查询ID",sId);
            object.put("status","success");
            object.put("所属学院",student.getAcademy());
            object.put("姓名",student.getName());
            if(bookLendlist==null)
            {
                logger.debug("this student didn't lent a book");
                object.put("queryResult","无该同学借阅信息");
            }
            else
            {
                int length = bookLendlist.size();
                logger.debug("this student lent "+length+"books");
                object.put("借书信息",length+"条");
                for(int i = 0;i < length;i++)
                {
                    object.put(bookLendlist.get(i).getBookName(),bookLendlist.get(i).getLentTime());
                }
            }
        }*/
        return object;
    }

    @Override
    public List<JSONObject> QueryGradeById(String sId) {
        //List<StudentGrade> grades = outerDataDao.getGradeById(sId);
        List<JSONObject> results = new ArrayList<>();
        /*JSONObject baseMessage = new JSONObject();
        if(grades == null)
        {
            logger.debug("there is not the student's grade message");
            baseMessage.put("查询ID", sId);
            baseMessage.put("status","none");
            results.add(baseMessage);
        }
        else
        {
            int length = grades.size();
            logger.debug("there are "+length+" the student's grade messages");
            baseMessage.put("查询ID",sId);
            baseMessage.put("status","success");
            results.add(baseMessage);
            StudentGrade grade;
            for(int i = 0;i<length;i++)
            {
                JSONObject object = new JSONObject();
                grade = grades.get(i);
                object.put("编号",i+1);
                object.put("选修年份",grade.getYear());
                object.put("选修课程",grade.getCourseName());
                object.put("课程代码",grade.getCourseCode());
                object.put("课程成绩",grade.getGrade());
                object.put("课程学分",grade.getCredit());
                results.add(object);
            }
        }*/
        return results;
    }

    @Override
    public JSONArray AggressionQueryResult(String sId) {
//        File file1 = new File()

        return null;
    }

}
