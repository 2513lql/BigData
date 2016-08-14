package com.wbl.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 2016/7/15.
 */
@Service
public interface OuterDataQueryService {
    JSONObject QueryLendBookById(String sId);
    List<JSONObject> QueryGradeById(String sId);
    JSONArray AggressionQueryResult(String sId);
}
