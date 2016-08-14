package com.wbl.service;

import net.sf.json.JSONObject;

/**
 * Created with Simple_love
 * Date: 2016/5/4.
 * Time: 10:18
 */
public interface OuterDataService {
        JSONObject exportTable(String fileName,String dataName,String userName);
        JSONObject aggregation(String fileName,String userName);
        void getReaderName();

        JSONObject exportData(String dataName);
        JSONObject importData(String url,String srcName,String destName,String userName);
}
