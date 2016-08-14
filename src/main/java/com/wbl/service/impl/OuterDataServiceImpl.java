package com.wbl.service.impl;

import com.bigdata.dao.UserInfoMapper;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.Source;
import com.bigdata.domain.UserInfo;
import com.wbl.dao.OuterDataDao;
import com.wbl.dao.ProvDao;
import com.wbl.domain.BookLend;
import com.wbl.domain.Student;
import com.wbl.domain.StudentBook;
import com.wbl.modal.Enum.ResultType;
import com.wbl.modal.MultipleDataSource;
import com.wbl.modal.PlatformInfo;
import com.wbl.modal.exception.FTPException;
import com.wbl.modal.exception.RequestException;
import com.wbl.service.OuterDataService;
import com.wbl.service.ProvService;
import com.wbl.util.FtpUtil;
import com.wbl.util.HttpRequestUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 10:19
 */
@Repository
public class OuterDataServiceImpl implements OuterDataService {
        private Logger logger = Logger.getLogger(OuterDataService.class);

        @Autowired
        private OuterDataDao outerDataDao;

        @Autowired
        private ProvDao provDao;

        @Autowired
        private UserInfoMapper userInfoMapper;

        @Autowired
        private ProvService provService;

        public void getReaderName(){
                System.out.println(outerDataDao.getReaderName());
        }

        public JSONObject exportTable(String fileName,String dataName,String userName){
                JSONObject result = new JSONObject();
                fileName = parseName(fileName);
                dataName = parseName(dataName);
                userName = parseName(userName);
                if ("book_lend".equals(dataName)){
                        List<BookLend> bookLends = outerDataDao.getBookLendInfo();
                        result.put("data",bookLends);
                        writeToFile(bookLends,fileName,dataName,userName);
                }
                if ("student".equals(dataName)){
                        List<Student> students = outerDataDao.getStudentBasicInfo();
                        result.put("data",students);
                        writeToFile(students,fileName,dataName,userName);
                }

                return result;
        }

        public JSONObject aggregation(String fileName,String userName){
                JSONObject result = new JSONObject();
                fileName = parseName(fileName);
                userName = parseName(userName);
                List<StudentBook> list = outerDataDao.getStudentBookLend();
                writeToFile(list,fileName,fileName,userName);
                result.put("data",list);
                return result;
        }

        @Override
        public JSONObject exportData(String dataName) {
                JSONObject result = new JSONObject();
                try {
                        Method method = OuterDataDao.class.getDeclaredMethod("get" + IniStr(dataName));
                        Object data = method.invoke(outerDataDao);
                        result.put("data", data);
                        result.put(ResultType.RESULT, ResultType.SUCCESS);

                        MultipleDataSource.setDataSourceKey("dataSource");
                        DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                        result.put("dataId",dataInfo.getDataId());
                        result.put("type",dataInfo.getType());
                        result.put("description",dataInfo.getDescription());
                        result.put("dataSize",dataInfo.getDataSize());
                        result.put("functionType",dataInfo.getFunctionType());


                        result.put("platform", PlatformInfo.PLATFORM_NAME);
                        result.put("query_url",PlatformInfo.PLATFORM_QUERY_URL);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        result.put(ResultType.RESULT,ResultType.ERROR);
                }
                return result;
        }

        @Override
        public JSONObject importData(String url, String srcName, String destName, String userName) {
                JSONObject result = new JSONObject();
                String param = "userName=" + PlatformInfo.PLATFORM_NAME + "&dataName=" + srcName;
                JSONObject response;
                File out = null;
                try {
                        response = JSONObject.fromObject(HttpRequestUtil.doPostRequest(url, param));
                        out = File.createTempFile("temp", ".txt");
                        FileOutputStream os = new FileOutputStream(out);
                        os.write(response.get("data").toString().getBytes());
                        FileInputStream in = new FileInputStream(out);
                        FtpUtil.fileUploadToPrivate(in, destName, destName + ".txt", userName);
                        FtpUtil.fileMoveFromPrivateToPublic(destName+".txt");

                        UserInfo userInfo = userInfoMapper.GetUserInfoByName(userName);
                        DataInfo dataInfo = new DataInfo();
                        dataInfo.setDataId(response.getString("dataId"));
                        dataInfo.setDataName(destName + ".txt");
                        dataInfo.setOwnerId(userInfo.getUserId());
                        dataInfo.setOwnerName(userInfo.getUserName());
                        dataInfo.setDescription(response.getString("description"));
                        dataInfo.setFunctionType(response.getString("functionType"));
                        dataInfo.setType(response.getString("type"));
                        dataInfo.setDataSize(Long.parseLong(response.getString("dataSize")));
                        Source source = new Source();
                        source.setDataId(dataInfo.getDataId());
                        source.setSource(response.getString("platform"));
                        source.setUrl(response.getString("query_url"));
                        provService.recordImportProv(source, dataInfo);
                } catch (RequestException | FTPException | IOException e) {
                        e.printStackTrace();
                        result.put(ResultType.RESULT, ResultType.ERROR);
                        logger.error("importData from :" + url + " fail");
                }
                return result;
        }

        private void writeToFile(List list,String fileName,String dataName,String userName){
                File out = null;
                try {
                        out = File.createTempFile("temp",".txt");
                        FileOutputStream os = new FileOutputStream(out);
                        for (Object object:list)
                                os.write(object.toString().getBytes());
                        FileInputStream is = new FileInputStream(out);
                        FtpUtil.fileUploadToPrivate(is, fileName, dataName + ".txt", userName);
                } catch (FTPException | IOException e) {
                        e.printStackTrace();
                }
        }

        private String parseName(String name){
                try {
                        return new String(name.getBytes("ISO-8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                }
                return name;
        }

        private String IniStr(String dataName){
                return dataName.substring(0,1).toUpperCase() + dataName.substring(1);
        }



}
