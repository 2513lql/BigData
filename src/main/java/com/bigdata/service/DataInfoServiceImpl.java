package com.bigdata.service;

import com.bigdata.dao.DataInfoMapper;
import com.bigdata.dao.SearchWordMapper;
import com.bigdata.domain.DataInfo;
import com.bigdata.domain.SearchWord;
import com.bigdata.tools.DataInfoSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import org.json.JSONException;
//import org.json.JSONObject;

/**
 * Created by stone on 2016/4/19.
 */
@Service(value = "dataInfoService")
public class DataInfoServiceImpl implements DataInfoService{
    @Autowired
    private DataInfoMapper dataInfoMapper;
    @Autowired
    private SearchWordMapper searchWordMapper;

    public int GetDataCount(DataInfoSearchModel pageModel) {
        int count = dataInfoMapper.GetDataCount(pageModel);
        double temp = (double)count;
        return (int)Math.ceil(temp/ pageModel.getPageSize());
    }

    public List<DataInfo> GetDataList(DataInfoSearchModel pageModel) {
        return dataInfoMapper.GetDataList(pageModel);
    }

    public List<String> IKAnalyzer(String str) {
        List<String> keywordList = new ArrayList<String>();
        try {
            byte[] bt = str.getBytes();
            InputStream ip = new ByteArrayInputStream(bt);
            Reader read = new InputStreamReader(ip);
            IKSegmentation iks = new IKSegmentation(read, true);//true开启只能分词模式，如果不设置默认为false，也就是细粒度分割
            Lexeme t;
            while ((t = iks.next()) != null) {
                keywordList.add(t.getLexemeText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(keywordList);
        if(keywordList!=null)
        {
            InsetKeyWord(keywordList);
        }
        return keywordList;
    }

    @Override
    public void InsetKeyWord(List<String> keywordlist) {
        int length = keywordlist.size();
        for(int i =0 ;i< length;i++)
        {
            //SearchWord searchWord =new SearchWord();
            SearchWord searchWord = searchWordMapper.GetSearchWord(keywordlist.get(i));
            String keyWord = keywordlist.get(i);
            if(searchWord==null)
            {
                if(keyWord.length()>=2)
                {
                    searchWord =new SearchWord();
                    searchWord.setKeyWord(keyWord);
                    searchWord.setCount(1);
                    searchWordMapper.InsertSearchWord(searchWord);
                }

            }
            else
            {
                searchWord.setCount(searchWord.getCount()+1);
                searchWordMapper.UpdateSearchWord(searchWord);
            }
        }
    }

    @Override
    public int SearchByDescriptionCount(DataInfoSearchModel pageModel) {
        int count = dataInfoMapper.SearchByDescriptionCount(pageModel);
        double temp = (double)count;
        return (int)Math.ceil(temp/ pageModel.getPageSize());
    }

    @Override
    public List<DataInfo> GetTopDownloadTimes() {
        return dataInfoMapper.GetTopDownloadTimes();
    }

    @Override
    public List<DataInfo> GetLastestData() {
        return dataInfoMapper.GetLastestData();
    }

    @Override
    public int GetAllDataCount(DataInfoSearchModel pageModel) {
        return dataInfoMapper.GetDataCount(pageModel);
    }

    @Override
    public DataInfo SearchByDataName(String dataName) {
        return dataInfoMapper.SearchByDataName(dataName);
    }

    @Override
    public void DataImport(String fileName) {
        dataInfoMapper.DataImport(fileName);
    }

    @Override
    public List<DataInfo> SearchByDescription(DataInfoSearchModel pageModel) {
        return dataInfoMapper.SearchByDescription(pageModel);
    }

    @Override
    public DataInfo SearchByDataId(String dataId) {
        return dataInfoMapper.SearchByDataId(dataId);
    }

    @Override
    public void UpdateDataInfo(DataInfo dataInfo) {
        dataInfoMapper.UpdateDataInfo(dataInfo);
    }

    @Override
    public String GetLastDataId(String plantName) {
        return dataInfoMapper.GetLastDataId(plantName);
    }

    @Override
    public int InsertData(DataInfo dataInfo) {
        return dataInfoMapper.InsertData(dataInfo);
    }
}
