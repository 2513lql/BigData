package com.wbl.service.impl;

import com.bigdata.domain.*;
import com.lql.service.ChainAccessControlService;
import com.wbl.dao.ProvDao;
import com.wbl.modal.Page;
import com.wbl.modal.PlatformInfo;
import com.wbl.modal.exception.RequestException;
import com.wbl.service.ProvService;
import com.wbl.util.GraphvizUtil;
import com.wbl.util.HttpRequestUtil;
import com.wbl.util.TimeUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wbl.modal.Enum.QueryMessage.*;
import static com.wbl.modal.Enum.Activity.*;
import static com.wbl.modal.Enum.ResultType.*;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 19:39
 */
@Repository
public class ProvServiceImpl implements ProvService {

        private Logger logger = Logger.getLogger(this.getClass());

        @Autowired
        private ProvDao provDao;

        @Autowired
        private ChainAccessControlService accessControlService;

        /**
         *make a request to local query interface
         * @param dataName name with query data
         * @return dataObj contain param {RESULT,MSG,QUERY_ID}
         */
        @Override
        public JSONObject queryPlatformRelation(String dataName) {
                JSONObject dataObj = new JSONObject();
                //DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataInfo == null){
                        logger.error("Query data name with " + dataName +" is not exist");
                        dataObj.put(RESULT, ERROR);
                        dataObj.put(MESSAGE,"Data name with \" + dataName +\" is not exist");
                        return dataObj;
                }
                //SendParam sendParam = makeSendParam(dataInfo.getDataId());
                ReceivedParam receivedParam = makeRequestParam(dataInfo.getDataId());
                //provDao.save(sendParam);
                String param = "receivedParam=" + JSONObject.fromObject(receivedParam).toString();
                JSONObject result = null;
                try {
                        result = JSONObject.fromObject(HttpRequestUtil.doPostRequest(PlatformInfo.PLATFORM_QUERY_URL, param));
                        if (ERROR.equals(result.getString(RESULT.toString()))){
                                dataObj.put(RESULT,ERROR);
                                dataObj.put(MESSAGE,"Query with data name with "+ dataName + " is failed");
                        }else {
                                dataObj.put(RESULT, SUCCESS);
                        }
                } catch (RequestException e) {
                        dataObj.put(RESULT,ERROR);
                        dataObj.put(MESSAGE,"Query with queryId " + dataObj.get(QUERY_ID) + " is failed");
                        logger.error("Query with queryId " + dataObj.get(QUERY_ID) + " is failed");
                        logger.error("Error message : " + e.getMessage());
                }finally {
                        dataObj.put(QUERY_ID,receivedParam.getQueryFrom()+"_" + receivedParam.getQueryFor() + "_" + receivedParam.getRequestId());
                }
                return dataObj;
        }

        /**
         *get relations in local platform and  report result to platform which start this query ,
         * if report success, then make a query request to up stream and down stream
         * @param param receivedParam
         * @return dataObj
         */
        @Override
        public JSONObject queryRelation(String param) {
                JSONObject dataObj = new JSONObject();
                ReceivedParam receivedParam = (ReceivedParam) JSONObject.toBean(JSONObject.fromObject(param), ReceivedParam.class);
                if (!requestIsExist(receivedParam)){
                        //provDao.save(receivedParam);
                        provDao.saveReceivedParam(receivedParam);
                        String relations = getRelationBetweenPlatform(receivedParam.getDataId());
                        String queryId = receivedParam.getQueryFrom()+"_" + receivedParam.getQueryFor()+"_"+receivedParam.getRequestId();
                        JSONObject report = new JSONObject();
                        report.put(QUERY_ID,queryId);
                        report.put(RELATIONS, relations);
                        String requestPram = "report=" + report.toString();
                        JSONObject reportResult = null;
                        try {
                                reportResult = JSONObject.fromObject(HttpRequestUtil.doPostRequest(receivedParam.getReportUrl(), requestPram));
                                if (ERROR.equals(reportResult.getString(RESULT.toString()))){
                                        logger.debug("QueryId with " + queryId + " report to " + receivedParam.getReportUrl() + " is failed");
                                        dataObj.put(RESULT,ERROR);
                                }else {
                                        logger.debug("QueryId with " + queryId + " report to " + receivedParam.getReportUrl() + " is success");
                                        if (!queryAdjacentRelation(receivedParam)){
                                                logger.warn("QueryId with " + queryId + "query adjacent platform relation is failed");
                                                dataObj.put(RESULT,ERROR);
                                        }else{
                                                logger.debug("QueryId with " + queryId + "query adjacent platform relation is success");
                                                dataObj.put(RESULT,SUCCESS);
                                        }
                                }
                        } catch (RequestException e) {
                                logger.error("QueryId with " + queryId + " report to " + receivedParam.getReportUrl() + " is failed");
                                logger.error("Error message: " + e.getMessage());
                                dataObj.put(RESULT,ERROR);
                        }
                }else {
                        logger.debug("Get repeated request");
                        dataObj.put(RESULT, SUCCESS);
                }
                return dataObj;
        }

        @Override
        public JSONObject queryProv(String queryId) {
                JSONObject dataObj = new JSONObject();
                String[] tokens = queryId.split("_");
                if (tokens.length != 3){
                        logger.error("QueryId[" + queryId + "] is illegal");
                        dataObj.put(RESULT,ERROR);
                        return dataObj;
                }else {
                        String queryFrom = tokens[0];
                        String queryFor = tokens[1];
                        String requestId = tokens[2];
                        //List<String> dataIds = provDao.getDataIdFromParam(queryFrom,queryFor,requestId);
                        List<String> dataIds = provDao.getDataIdFromReceivedParam(queryFrom, queryFor, requestId);
                        List<Prov> provs = new ArrayList<>();
                        for (String dataId:dataIds){
                                //provs.addAll(provDao.getProvs(dataId));
                                provs.addAll(provDao.getProvs(dataId));
                        }
                        dataObj.put(PROVS, provs);
                        dataObj.put(RESULT, SUCCESS);
                }
                return dataObj;
        }

        /**
         * According data name to get prov
         * @param dataName name of query data
         * @return prov records of this data
         */
        @Override
        public List queryProvByName(String dataName) {
                //DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataInfo == null){
                        logger.error("The data named" + dataName + " is not exist");
                        return null;
                }
                //return provDao.getProvs(dataInfo.getDataId());
                return provDao.getProvs(dataInfo.getDataId());
        }

        /**
         * Get prov records of data by page
         * @param dataName name of query data
         * @param currentPage current page number
         * @return prov records of data in current page number
         */
        @Override
        public JSONObject getProvByPage(String dataName, String currentPage) {
                JSONObject dataObj = new JSONObject();
                //DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataInfo == null){
                        logger.error("The data named " + dataName + " is not exist");
                        dataObj.put(RESULT,ERROR);
                        return null;
                }
                Page<Prov> result = new Page<>();
                int curr = Integer.parseInt(currentPage);
                int pageSize = result.getPageSize();
                int startRow =  (curr - 1) * result.getPageSize();
                int rowLength =  result.getPageSize();
               /* result.addAll(pageQueryDao.getProvByDataId(dataInfo.getDataId(), startRow, rowLength));
                int total = pageQueryDao.getPagesByDataIdFromProv(dataInfo.getDataId());*/
                result.addAll(provDao.getProvByDataId(dataInfo.getDataId(), startRow, rowLength));
                int total = provDao.getPagesByDataIdFromProv(dataInfo.getDataId());
                int pages = total % pageSize == 0 ? total /pageSize : total /pageSize + 1;
                dataObj.put(RESULT,SUCCESS);
                dataObj.put("Page_result",result);
                dataObj.put("pages",pages);
                return dataObj;
        }

        @Override
        public String produceDotString(String queryId,UserInfo userInfo,HttpServletRequest request) {
                String[] tokens = queryId.split("_");
                if (tokens.length != 4){
                        logger.error("QueryId[" + queryId + "] is illegal");
                        return null;
                }else{
                        String queryFor = tokens[1];
                        /*List<String> datas = getRelatedDataId(queryFor);
                        final List<DataInfo> dataInfos = new ArrayList<>();
                        datas.forEach((name)->dataInfos.add(provDao.getDataInfoByDataId(name)));
                        if (accessControlService.isChainCanBeSeenByRequester(dataInfos,userInfo.getUserId()))
                                return getRelationBetweenData(queryFor);
                        else
                                return null;*/
                        return getRelationBetweenData(queryFor,userInfo.getUserId(),request);
                }
        }

        /**
         * according to dataId,get relation between local platform with other platform
         * @param dataId the id of query data
         * @return according relations to make dot line
         */
        @Override
        public String getRelationBetweenPlatform(String dataId) {
                StringBuilder builder = new StringBuilder();
                List<String> dataIdList = getRelatedDataId(dataId);
                builder.append(PlatformInfo.PLATFORM_NAME );
                builder.append("[URL=" + "\"" + PlatformInfo.PLATFORM_QUERY_PROV_URL + "\"]\n");
                for (String value : dataIdList){
                        for (Next next : provDao.getNext(value)){
                                builder.append(PlatformInfo.PLATFORM_NAME ).append(GraphvizUtil.LINKSIGN).append(next.getNext());
                                builder.append("\n");
                        }
                }
                return builder.toString();
        }

        /**
         *make sure the query request is repeated or not
         * @param param receivedParam
         * @return if request is repeated return true, otherwise return false
         */
        @Override
        public boolean requestIsExist(ReceivedParam param) {
                ReceivedParam tempReceivedParam = provDao.getReceivedParam(param);
                return tempReceivedParam != null;
        }

        @Override
        public void recordDownloadProv(String dataName, UserInfo userInfo) {
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                String url = userInfo.getUrl();
                if (url != null){
                        Next next = new Next(dataInfo.getDataId(),userInfo.getUserName(),url);

                        notifyOtherPlatform(dataInfo,userInfo);
                        logger.debug("Other platform download " + next);
                }
                Prov prov = new Prov();
                prov.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov.setEntity(dataInfo.getDataId());
                prov.setAgent(userInfo.getUserName());
                prov.setActivity(DOWNLOAD.name());
                prov.setUsed(PlatformInfo.PLATFORM_NAME + ":" + dataInfo.getDataId());
                prov.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                //provDao.saveProv(prov);
        }

        @Override
        public void recordDownloadProvFromOtherPlatform(String platform, String url, DataInfo dataInfo) {
                Source source = new Source(dataInfo.getDataId(),platform,url);
                dataInfo.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                dataInfo.setStatus(DATA_STATUS_UNUPLOAD);
                dataInfo.setRelation(0);

                //saveSource(source);
                logger.debug(source);
                logger.debug(dataInfo);
                //provDao.updateDataInfo(dataInfo);
        }

        @Override
        public void recordUploadProv(String dataName, String description, String type, long size, String category, UserInfo userInfo) {
                /*DataInfo dataInfo = new DataInfo(dataName,userInfo.getUserId(),type,size,description);
                dataInfo.setStatus(DATA_STATUS_UNUPLOAD);
                dataInfo.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                dataInfo.setOwnerId(userInfo.getUserId());
                String dataId = makeCurrentDataId();
                dataInfo.setDataId(dataId);

                Source source = new Source(dataId,userInfo.getUserName(),userInfo.getUrl());
                logger.debug("source of upload data: " + source);

                Prov prov = new Prov();
                prov.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov.setEntity(dataId);
                prov.setActivity(UPLOAD.name());
                prov.setAgent(userInfo.getUserName());
                prov.setUsed(PlatformInfo.PLATFORM_NAME + ":" + dataId);
                prov.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                logger.debug("prov of upload data: " + prov);*/
        }

        @Override
        public void recordSplitProv(String fileName, String dataName, UserInfo userInfo) {
                DataInfo origialDataInfo = provDao.getDataInfoByName(dataName);
                if (origialDataInfo.getRelation() == DATA_RELATION_EMPTY){
                        origialDataInfo.setRelation(DATA_RELATION_NOT_EMPTY);
                        logger.debug("update origial dataInfo");
                        //provDao.updateDataInfo(origialDataInfo);
                }

                DataInfo dataInfo = new DataInfo();
                dataInfo.setDataId(makeCurrentDataId());
                dataInfo.setOwnerId(userInfo.getUserId());
                dataInfo.setFunctionType(origialDataInfo.getFunctionType());
                dataInfo.setDataName(fileName + ".txt");
                dataInfo.setDataSize(origialDataInfo.getDataSize());
                dataInfo.setDescription(origialDataInfo.getDescription());
                dataInfo.setType(origialDataInfo.getType());
                dataInfo.setRelation(DATA_RELATION_NOT_EMPTY);
                dataInfo.setStatus(DATA_STATUS_NUPUBLISHED);
                dataInfo.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                //provDao.saveDataInfo(dataInfo);
                logger.debug("export dataInfo: " + dataInfo);

                RelationInfo info = new RelationInfo(origialDataInfo.getDataId(),dataInfo.getDataId());
                //saveRelationInfo(info);
                logger.debug("export relationInfo: " + info);

                Prov prov = new Prov();
                prov.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov.setEntity(dataInfo.getDataId());
                prov.setAgent(userInfo.getUserName());
                prov.setActivity(EXPORT.name());
                prov.setUsed(prov.getPrefix() + ":" + origialDataInfo.getDataId());
                prov.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                logger.debug("export prov: " + prov);
        }

        @Override
        public void recordExportProv(String dataName, UserInfo userInfo) {
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                if (dataInfo == null){
                        logger.error("DataInfo is not exist");
                        return;
                }

                Next next = new Next(dataInfo.getDataId(),userInfo.getUserName(),userInfo.getUrl());
                saveNext(next);
                logger.debug("Export data : save next " + next);

                Prov prov = new Prov();
                prov.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov.setEntity(dataInfo.getDataId());
                prov.setAgent(userInfo.getUserName());
                prov.setActivity(EXPORT.name());
                prov.setUsed(prov.getPrefix() + ":" + prov.getEntity());
                prov.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                provDao.saveProv(prov);
                logger.debug("Export data save prov :" + prov);
        }

        @Override
        public void recordImportProv(Source source, DataInfo dataInfo) {
                Prov prov = new Prov();
                prov.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov.setEntity(dataInfo.getDataId());
                prov.setAgent(PlatformInfo.PLATFORM_NAME);
                prov.setActivity(IMPORT.name());
                prov.setUsed(source.getSource() + ":" + prov.getEntity());
                prov.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));

                dataInfo.setStatus(DATA_STATUS_PUBLISHED);
                dataInfo.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                if (provDao.getDataInfoByDataId(dataInfo.getDataId()) != null)
                        provDao.updateDataInfo(dataInfo);
                else
                        provDao.saveDataInfo(dataInfo);
                logger.debug("import data save dataInfo: " + dataInfo);

                provDao.saveProv(prov);
                logger.debug("import data save prov: " + prov);

                saveSource(source);
                logger.debug("import data save source: " + source);
        }

        @Override
        public void recordAggregation(String fileName, UserInfo userInfo) {
                DataInfo dataInfo = new DataInfo();
                DataInfo ancestor1 = provDao.getDataInfoByName("student");
                if (ancestor1.getRelation() == DATA_RELATION_EMPTY){
                        ancestor1.setRelation(DATA_RELATION_NOT_EMPTY);
                        //provDao.updateDataInfo(ancestor1);
                        logger.debug("update dataInfo");
                }
                DataInfo ancestor2 = provDao.getDataInfoByName("book_lend");
                if (ancestor2.getRelation() == DATA_RELATION_EMPTY){
                        ancestor1.setRelation(DATA_RELATION_NOT_EMPTY);
                        //provDao.updateDataInfo(ancestor2);
                        logger.debug("update dataInfo");
                }
                dataInfo.setDataId(makeCurrentDataId());
                dataInfo.setOwnerId(userInfo.getUserId());
                dataInfo.setOwnerName(userInfo.getUserName());
                dataInfo.setDataName(fileName + ".txt");
                dataInfo.setFunctionType(ancestor1.getFunctionType());
                dataInfo.setDataSize(ancestor1.getDataSize());
                dataInfo.setType(ancestor1.getType());
                dataInfo.setStatus(DATA_STATUS_UNUPLOAD);
                dataInfo.setRelation(DATA_RELATION_NOT_EMPTY);
                dataInfo.setDescription(ancestor1.getDescription() + ancestor2.getDescription());
                dataInfo.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));
                //provDao.saveDataInfo(dataInfo);
                logger.debug("save aggregation dataInfo: " + dataInfo);

                RelationInfo info = new RelationInfo(ancestor1.getDataId(),dataInfo.getDataId());
                //saveRelationInfo(info);
                logger.debug("save aggregation relationInfo: " + info);

                info.setAncestor(ancestor2.getDataId());
                //saveRelationInfo(info);
                logger.debug("save aggregation relationInfo: " + info);


                Prov prov1 = new Prov();
                prov1.setPrefix(PlatformInfo.PLATFORM_NAME);
                prov1.setEntity(dataInfo.getDataId());
                prov1.setAgent(userInfo.getUserName());
                prov1.setActivity(AGGREGATION.name());
                prov1.setUsed(prov1.getPrefix() + ":" + ancestor1.getDataId());
                prov1.setTime(TimeUtil.getCurrentTime(TIME_FORMAT));

                //provDao.saveProv(prov1);
                logger.debug("aggregation prov1: " + prov1);

                prov1.setUsed(prov1.getPrefix() + ":" + ancestor2.getDataId());
                //provDao.saveProv(prov1);
                logger.debug("aggregation prov2: " + prov1);
        }

        @Override
        public JSONObject getDataInfo(String dataName) {
                DataInfo dataInfo = provDao.getDataInfoByName(dataName);
                JSONObject result = new JSONObject();
                result.put("dataInfo",dataInfo);
                return result;
        }

        @Override
        public void recordUpload(String dataName, String functionType, String type, long size, String description, UserInfo userInfo) {

        }

        @Override
        public void recordSplit(String fileName, String type, String dataName, UserInfo userInfo) {

        }

        @Override
        public void recordAggregation(String fileName, String type, UserInfo userInfo, List<String> dataNames) {

        }

        @Override
        public void recordExport(String dataName, UserInfo userInfo) {

        }

        @Override
        public void recordImport(Source source, DataInfo dataInfo) {

        }

        /**
         * According to dataId,get relation between data
         * @param dataId the id of query data
         * @return according to relations to make dot line
         */
        private String getRelationBetweenData(String dataId,int userId,HttpServletRequest request) {
                List<String> dataList = new ArrayList<>();
                StringBuffer buffer = new StringBuffer();
                if (!haveRelationWithOtherData(dataId)){
                        DataInfo dataInfo = provDao.getDataInfoByDataId(dataId);
                        return parseDataName(dataInfo.getDataName());
                }
                Queue<String> queue = new LinkedList<>();
                queue.offer(dataId);
                while (!queue.isEmpty()){
                        String tempId = queue.poll();
                        dataList.add(tempId);
                        DataInfo dataInfo = provDao.getDataInfoByDataId(tempId);
                        String name = dataInfo.getDataName();
                        if (!accessControlService.isCurrentNodeCanBeSeen(dataInfo.getDataId(),userId,request))
                                buffer.append(name).append("[color=red]");
                        for (RelationInfo info: provDao.getAncestor(tempId)){
                                if (!dataList.contains(info.getAncestor())){
                                        String parentName = provDao.getDataInfoByDataId(info.getAncestor()).getDataName();
                                        buffer.append(parseDataName(parentName));
                                        buffer.append(GraphvizUtil.LINKSIGN);
                                        buffer.append(parseDataName(name));
                                        buffer.append("\n");
                                        dataList.add(info.getAncestor());
                                        queue.offer(info.getAncestor());
                                }
                        }


                        for (RelationInfo info : provDao.getSuccessor(tempId)){
                                if (!dataList.contains(info.getSuccessor())){
                                        String childName = provDao.getDataInfoByDataId(info.getSuccessor()).getDataName();
                                        buffer.append(parseDataName(name));
                                        buffer.append(GraphvizUtil.LINKSIGN);
                                        buffer.append(parseDataName(childName));
                                        buffer.append("\n");
                                        dataList.add(info.getSuccessor());
                                        queue.offer(info.getSuccessor());
                                }
                        }
                }
                return buffer.toString();
        }


        /**
         *make a query request  to up stream and down stream
         * @param receivedParam query param
         * @return if query success, return true,otherwise return false
         */
        private boolean queryAdjacentRelation(ReceivedParam receivedParam){
                List<String> dataIdList = getRelatedDataId(receivedParam.getDataId());
                for (String dataId : dataIdList){
                        if (!queryForUpStream(receivedParam, dataId)  || !queryForDownStream(receivedParam, dataId))
                                return false;
                }
                return true;
        }

        /**
         * make a query request to up stream
         * @param param query param
         * @param dataId the id of query data
         * @return if query success, return true,otherwise return false
         */
        private boolean queryForUpStream(ReceivedParam param,String dataId){
                for (Source source: provDao.getSource(dataId)){
                        /*SendParam sendParam = new SendParam(dataId,param.getQueryFrom(),param.getQueryFor(),
                                param.getRequestId(),param.getReportUrl());*/
                        param.setDataId(dataId);
                        String requestParam = "receivedParam=" + JSONObject.fromObject(param).toString();
                        String queryUrl = source.getUrl();
                        if (queryUrl != null){
                                logger.debug("Query for up stream url: " + queryUrl + " and dataId = " + dataId);
                                JSONObject response = null;
                                String queryId = param.getQueryFrom() + "_" + param.getQueryFor() + "_" + param.getRequestId();
                                try {
                                        response = JSONObject.fromObject(HttpRequestUtil.doPostRequest(queryUrl, requestParam));
                                        if (ERROR.equals(response.getString(RESULT.toString()))){
                                                logger.error("QueryId with " + queryId +"  query for upStream[" + source.getSource() +"] is failed" );
                                                return false;
                                        }
                                } catch (RequestException e) {
                                        logger.error("QueryId with " + queryId +"  query for upStream[" + source.getSource() +"] is failed" );
                                        logger.error("Error message: " + e.getMessage());
                                        return false;
                                }

                        }
                }
                return true;
        }

        /**
         * make a query request to down stream
         * @param param query param
         * @param dataId the id of query data
         * @return if query success , return true, otherwise return false
         */
        private boolean queryForDownStream(ReceivedParam param,String dataId){
                for (Next next: provDao.getNext(dataId)){
                        /*SendParam sendParam = new SendParam(dataId,param.getQueryFrom(),param.getQueryFor(),
                                param.getRequestId(),param.getReportUrl());*/
                        param.setDataId(dataId);
                        String requestParam = "receivedParam=" + JSONObject.fromObject(param).toString();
                        String queryUrl = next.getUrl();
                        if (queryUrl != null){
                                logger.debug("Query for down Stream url: " + queryUrl + " and dataId = " + dataId);
                                JSONObject response = null;
                                String queryId = param.getQueryFrom() + "_" + param.getQueryFor() + "_" + param.getRequestId();
                                try {
                                        response = JSONObject.fromObject(HttpRequestUtil.doPostRequest(queryUrl, requestParam));
                                        if (ERROR.equals(response.getString(RESULT.toString()))){
                                                logger.error("QueryId with " + queryId +"  query for downStream["  + next.getNext() +"] is failed" );
                                                return false;
                                        }
                                } catch (RequestException e) {
                                        logger.error("QueryId with " + queryId +"  query for upStream[" + next.getNext() +"] is failed" );
                                        logger.error("Error message: " + e.getMessage());
                                        return false;
                                }
                        }
                }
                return true;
        }

        private String parseDataName(String dataName){
                String[] token = dataName.split("\\.");
                return token[0];
        }

        private static void notifyOtherPlatform(DataInfo dataInfo,UserInfo userInfo){
                String url = userInfo.getUrl();
                url = url.substring(0,url.indexOf("prov"));
                url += "prov/callbackForDownload";
                String param = "platform=" + PlatformInfo.PLATFORM_NAME + "&url=" + PlatformInfo.PLATFORM_QUERY_URL+
                        "&dataInfo=" + JSONObject.fromObject(dataInfo).toString();
                try {
                        HttpRequestUtil.doPostRequest(url,param);
                } catch (RequestException e) {
                        e.printStackTrace();
                        //logger.debug("NotifyOtherPlatform for download fail");
                }
        }

        private synchronized String makeCurrentDataId(){
                DataInfo pre = provDao.getLastDataInfo();
                if (pre == null)
                        return PlatformInfo.PLATFORM_NAME + "-" + 1;
                int pre_id = Integer.parseInt(pre.getDataId().split("-")[1]) + 1;
                return PlatformInfo.PLATFORM_NAME + "-" + pre_id;
        }

        /**
         * get ancestor and successor of data
         * @param dataId the id of data
         * @return ancestor and successor of data
         */
        private List<String> getRelatedDataId(String dataId){
                List<String> dataIdList = new ArrayList<>();
                if (!haveRelationWithOtherData(dataId)){
                        dataIdList.add(dataId);
                        return dataIdList;
                }
                Queue<String> queue = new LinkedList<>();
                queue.offer(dataId);
                while (!queue.isEmpty()){
                        String tempDataId = queue.poll();
                        dataIdList.add(tempDataId);
                        if (haveRelationWithOtherData(tempDataId)){
                                for (RelationInfo info: provDao.getAncestor(tempDataId)){
                                        if (!dataIdList.contains(info.getAncestor())){
                                                dataIdList.add(info.getAncestor());
                                                queue.offer(info.getAncestor());
                                        }
                                }

                                for (RelationInfo info : provDao.getSuccessor(tempDataId)){
                                        if (!dataIdList.contains(info.getSuccessor())){
                                                dataIdList.add(info.getSuccessor());
                                                queue.offer(info.getSuccessor());
                                        }
                                }
                        }
                }
                return dataIdList;
        }

        /**
         * whether data have ancestor or successor
         * @param dataId the id of data
         * @return if data have ancestor or successor,return true,otherwise false
         */
        private boolean haveRelationWithOtherData(String dataId){
                DataInfo dataInfo = provDao.getDataInfoByDataId(dataId);
                return dataInfo.getRelation() != DATA_RELATION_EMPTY;
        }

        private synchronized ReceivedParam makeRequestParam(String dataId){
                ReceivedParam param = new ReceivedParam();
                param.setDataId(dataId);
                param.setQueryFor(dataId);
                param.setQueryFrom(PlatformInfo.PLATFORM_NAME);
                param.setReportUrl(PlatformInfo.PLATFORM_REPORT_URL);
                Format format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                param.setRequestId(format.format(new Date()));
                return param;
        }

        private void saveNext(Next next){
                if (provDao.isNextExist(next) == null)
                        provDao.saveNext(next);
        }

        private void saveSource(Source source){
                if (provDao.isSourceExist(source) == null)
                        provDao.saveSource(source);
        }

        private void saveRelationInfo(RelationInfo info){
                if (provDao.isRelationExist(info) == null)
                        provDao.saveRelationInfo(info);
        }
}
