package com.bigdata.service;

import com.bigdata.domain.DataInfo;
import com.bigdata.tools.DataInfoSearchModel;
import org.springframework.stereotype.Service;

import java.util.List;

//import org.json.JSONObject;
//import org.json.JSONException;
//import org.json.JSONObject;

/**
 * Created by stone on 2016/4/19.
 */
@Service
public interface DataInfoService {
    int GetDataCount(DataInfoSearchModel pageModel);
    List<DataInfo> GetDataList(DataInfoSearchModel pageModel);
    List<DataInfo> SearchByDescription(DataInfoSearchModel pageModel);
    DataInfo SearchByDataId(String dataId);
    void UpdateDataInfo(DataInfo dataInfo);
    List<String> IKAnalyzer(String str);
    void InsetKeyWord(List<String> keywordlist);
    int SearchByDescriptionCount(DataInfoSearchModel pageModel);
    List<DataInfo> GetTopDownloadTimes();
    List<DataInfo> GetLastestData();
    int  GetAllDataCount(DataInfoSearchModel pageModel);
    DataInfo SearchByDataName(String dataName);
    void DataImport(String fileName);


    //xiechao
    String GetLastDataId(String plantName);
    int InsertData(DataInfo dataInfo);
}
