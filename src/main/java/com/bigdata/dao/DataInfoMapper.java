package com.bigdata.dao;

import com.bigdata.domain.DataInfo;
import com.bigdata.tools.DataInfoSearchModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by stone on 2016/4/19.
 */
@Repository(value = "dataInfoMapper")
public interface DataInfoMapper {

    int GetDataCount(DataInfoSearchModel pageModel);
    List<DataInfo> GetDataList(DataInfoSearchModel pageModel);
    List<DataInfo> SearchByDescription(DataInfoSearchModel pageModel);
    DataInfo SearchByDataId(String dataId);
    void UpdateDataInfo(DataInfo dataInfo);
    int SearchByDescriptionCount(DataInfoSearchModel pageModel);

    String GetLastDataId(String plantName);
    int InsertData(DataInfo dataInfo);
    List<DataInfo> GetTopDownloadTimes();
    List<DataInfo> GetLastestData();

    DataInfo SearchByDataName(String dataId);
    void DataImport(String fileName);
}
