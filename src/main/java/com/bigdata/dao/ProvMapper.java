package com.bigdata.dao;

import com.bigdata.domain.Prov;
import com.bigdata.tools.ProvInfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by stone on 2016/4/29.
 */
@Repository(value = "provMapper")
public interface ProvMapper {
    int GetProvCount(ProvInfoModel provInfoModel);
    List<Prov> GetProvList(ProvInfoModel provInfoModel);
    void InsertProv(Prov prov);
}
