package com.wbl.service;

import com.bigdata.domain.DataInfo;
import com.bigdata.domain.ReceivedParam;
import com.bigdata.domain.Source;
import com.bigdata.domain.UserInfo;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 17:36
 */
public interface ProvService {
        static final int DATA_STATUS_NUPUBLISHED = 0;
        static final int DATA_STATUS_PUBLISHED = 1;
        static final int DATA_STATUS_UNUPLOAD = 2;
        static final int DATA_RELATION_EMPTY = 0;
        static final int DATA_RELATION_NOT_EMPTY = 1;
        static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

        JSONObject queryPlatformRelation(String dataName);
        JSONObject queryRelation(String param);

        JSONObject queryProv(String queryId);
        List queryProvByName(String dataName);
        JSONObject getProvByPage(String dataName,String currentPage);

        //JSONObject produceSvgJson(String queryId);
        String produceDotString(String queryId,UserInfo userInfo,HttpServletRequest request);

        String getRelationBetweenPlatform(String dataId);

        boolean requestIsExist(ReceivedParam param);

        void recordDownloadProv(String dataName,UserInfo userInfo);

        void recordDownloadProvFromOtherPlatform(String platform,String url,DataInfo dataInfo);

        void recordUploadProv(String dataName,String description,String type,long size,
                              String category,UserInfo userInfo);

        void recordSplitProv(String fileName,String dataName,UserInfo userInfo);

        void recordAggregation(String fileName,UserInfo userInfo);

        void recordExportProv(String dataName,UserInfo userInfo);

        void recordImportProv(Source source,DataInfo dataInfo);

        JSONObject getDataInfo(String dataName);


        //by stone
        void recordUpload(String dataName,String functionType,String type,long size,String description,UserInfo userInfo);
        void recordSplit(String fileName,String type, String dataName, UserInfo userInfo);
        void recordAggregation(String fileName,String type,UserInfo userInfo,List<String> dataNames);
        void recordExport(String dataName,UserInfo userInfo);
        void recordImport(Source source,DataInfo dataInfo);
}
