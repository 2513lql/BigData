package com.wbl.service.impl;

import com.bigdata.domain.DataInfo;
import com.bigdata.tools.MonthDay;
import com.mongodb.util.JSON;
import com.wbl.dao.DataAnalyseDao;
import com.wbl.dao.ProvDao;
import com.wbl.modal.CountModal;
import com.wbl.service.DataAnalyseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 21:19
 */
@Repository
public class DataAnalyseServiceImpl implements DataAnalyseService {
        private static Logger logger = Logger.getLogger(DataAnalyseServiceImpl.class);

        @Autowired
        private DataAnalyseDao dataAnalyseDao;
        @Autowired
        private ProvDao provDao;

        MonthDay monthDay=new MonthDay();

        @Override
        public JSONArray getDataOperateTimesInDay(String dataName, String month, String year) {
                JSONArray result = new JSONArray();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataName == null || month == null || year == null){
                        logger.debug("dataName,month or year is null");
                        return result;
                }
                if (dataInfo == null){
                        logger.debug("data with name " + dataName + " is not exist");
                        return result;
                }
                List<CountModal> queryResult = dataAnalyseDao.getDataOperateTimesByDays(dataInfo.getDataId(), month, year);
                List<CountModal> list = DaySupply(queryResult, year, month);
                if (list.isEmpty())
                        logger.debug("getDataOperateTimesInDay query result is empty");
                for (CountModal modal:list){
                        JSONObject object = new JSONObject();
                        object.put("name",modal.getName());
                        object.put("count",modal.getCount());
                        result.add(object);
                }
                return result;
        }
        public List<CountModal> DaySupply(List<CountModal> queryResult,String year,String month) {
                List<CountModal> list = new ArrayList<CountModal>();
                //MonthDay monthDay = new MonthDay(month,year);
                int key=0;
                for(int i=1;i<=monthDay.CountDay(year, month);i++)
                {
                        String name = year+"-"+month+"-"+i+"";
                        for(CountModal modal:queryResult)
                        {
                                if(modal.getName().equals(name))
                                {
                                        list.add(modal);
                                        key=1;
                                        break;
                                }
                        }
                        if(key==0)
                        {
                                CountModal countModal = new CountModal();
                                countModal.setCount(0);
                                countModal.setName(name);
                                list.add(countModal);
                        }
                        else
                        {
                                key=0;
                                continue;
                        }

                }
                return list;
        }
        @Override
        public JSONArray getDataOperateTimesInMonth(String dataName, String year) {
                JSONArray result = new JSONArray();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataName == null || year == null){
                        logger.debug("dataName or year is null");
                        return result;
                }
                if (dataInfo == null){
                        logger.debug("data with name " + dataName + " is not exist");
                        return result;
                }
                List<CountModal> queryResult = dataAnalyseDao.getDataOperateTimesByMonth(dataInfo.getDataId(), year);
                List<CountModal> list=MonthSupply(year,queryResult);
                //list()
                if (list.isEmpty())
                        logger.debug("getDataOperateTimesInMonth query result is empty");
                for (CountModal modal : list){
                        JSONObject object = new JSONObject();
                        object.put("name",modal.getName());
                        object.put("count",modal.getCount());
                        result.add(object);
                }
                return result;
        }
        public List<CountModal> MonthSupply(String year,List<CountModal> queryResult){
                List<CountModal> list = new ArrayList<CountModal>();
                int key=0;
                for(int i=1;i<=12;i++)
                {
                        String name = year + "-"+i;
                        for(CountModal modal:queryResult)
                        {
                                if(modal.getName().equals(name))
                                {
                                        list.add(modal);
                                        key=1;
                                        break;
                                }
                        }
                        if(key==0)
                        {
                                CountModal countModal = new CountModal();
                                countModal.setCount(0);
                                countModal.setName(name);
                                list.add(countModal);
                        }
                        else
                        {
                                key=0;
                                continue;
                        }

                }
                return list;
        }
        @Override
        public JSONArray getDataOperateTimesInYear(String dataName) {
                JSONArray result = new JSONArray();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataName == null){
                        logger.debug("dataName is null");
                        return result;
                }
                if (dataInfo == null){
                        logger.debug("data with name " + dataName + " is not exist");
                        return result;
                }
                List<CountModal> queryResult = dataAnalyseDao.getDataOperateTimesByYear(dataInfo.getDataId());
                List<CountModal> list = YearSupply(queryResult);
                if (list.isEmpty())
                        logger.debug("getDataOperateTimesInYear query result is empty");
                for (CountModal modal : list){
                        JSONObject object = new JSONObject();
                        object.put("name",modal.getName());
                        object.put("count",modal.getCount());
                        result.add(object);
                }
                return result;
        }
        public List<CountModal> YearSupply(List<CountModal> queryResult) {
                List<CountModal> list = new ArrayList<CountModal>();
                int key=0;
                for(int i=2006;i<=2016;i++)
                {
                        String name = i+"";
                        for(CountModal modal:queryResult)
                        {
                                if(modal.getName().equals(name))
                                {
                                        list.add(modal);
                                        key=1;
                                        break;
                                }
                        }
                        if(key==0)
                        {
                                CountModal countModal = new CountModal();
                                countModal.setCount(0);
                                countModal.setName(name);
                                list.add(countModal);
                        }
                        else
                        {
                                key=0;
                                continue;
                        }

                }
                return list;
        }
        @Override
        public JSONArray getDataOperateTimesByType(String dataName) {
                JSONArray result = new JSONArray();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataName == null){
                        logger.debug("dataName is null");
                        return result;
                }
                if (dataInfo == null){
                        logger.debug("data with name " + dataName + " is not exist");
                        return result;
                }
                List<CountModal> list = dataAnalyseDao.getDataOperateTimesByType(dataInfo.getDataId());
                if (list.isEmpty())
                        logger.debug("getDataOperateTimesByType query result is empty");
                for (CountModal modal : list){
                        JSONObject object = new JSONObject();
                        object.put("name",modal.getName());
                        object.put("count",modal.getCount());
                        result.add(object);
                }
                return result;
        }

        @Override
        public JSONObject getHotData() {
                JSONObject result = new JSONObject();
                List<CountModal> list = dataAnalyseDao.getHotDataByDownloadTimes();
                result.put("data",list);
                return result;
        }

        /*@Override
        public JSONObject getLikeData(String dataName) {
                JSONObject result = new JSONObject();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                List<CountModal> list = dataQueryDao.getLikeData(dataInfo.getDataId(), dataInfo.getCategory().substring(0, 2));
                result.put("data",list);
                return result;
        }

        @Override
        public JSONObject getUserRecommendData(String userName) {
                JSONObject result = new JSONObject();
                List<String> users = dataQueryDao.getLikeUser(userName);
                Map<String,Integer> map = new HashMap<>();
                for (String name:users){
                        List<String> datas = dataQueryDao.getDataIdByUserUsed(name);
                        for (String data:datas){
                                if (map.containsKey(data)){
                                        int count = map.get(data);
                                        count++;
                                        map.put(data,count);
                                }else
                                        map.put(data,1);
                        }
                }
                result.put("data",getSortedMapByValue(map));
                return result;
        }*/

        private List<String> getSortedMapByValue(Map<String,Integer> map){
                List<Map.Entry<String,Integer>> entries = new ArrayList<>(map.entrySet());
                Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                return (o1.getValue() - o2.getValue());
                        }
                });
                List<String> result = new ArrayList<>();
                int count = 0;
                for (Map.Entry<String,Integer> entry:entries){
                        result.add(parseDataName(entry.getKey()));
                        count++;
                        if (count == 6)
                                break;
                }
                return result;
        }

        private String parseDataName(String dataId){
                DataInfo dataInfo = provDao.getDataInfoByDataId(dataId);
                if (dataInfo == null)
                        return null;
                else
                        return dataInfo.getDataName().split("\\.")[0];
        }

        @Override
        public JSONArray getDataOperateType(String dataName) {
                JSONArray result = new JSONArray();
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if(dataInfo==null)
                {
                        logger.error("dataName is not exist");
                        return null;
                }
                List<CountModal> supplys = dataAnalyseDao.getSupplyTimesByDataId(dataInfo.getDataId());
                List<CountModal> uses = dataAnalyseDao.getUseTimesByDataId(dataInfo.getDataId());
                if(supplys==null||uses==null)
                {
                        logger.debug("dataName is not supplied or not used");
                        return null;
                }
                else
                {
                        CountModal supply =  supplys.get(0);
                        CountModal used = uses.get(0);
                        JSONObject objectSupply = new JSONObject();
                        JSONObject objectUsed = new JSONObject();
                        objectSupply.put("name",supply.getName());
                        objectSupply.put("count",supply.getCount());
                        objectUsed.put("name",used.getName());
                        objectUsed.put("count",used.getCount());
                        result.add(objectSupply);
                        result.add(objectUsed);
                        return result;
                }
        }
}
