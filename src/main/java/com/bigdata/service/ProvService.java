package com.bigdata.service;

import com.bigdata.domain.Prov;
import com.bigdata.tools.ProvInfoModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stone on 2016/4/29.
 */
@Service
public interface ProvService {
    int GetProvCount(ProvInfoModel provInfoModel);
    int GetProvPageCount(ProvInfoModel provInfoModel);
    List<Prov> GetProvList(ProvInfoModel provInfoModel);
    void InsertProv(Prov prov);
}
