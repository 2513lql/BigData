package com.bigdata.service;

import com.bigdata.dao.ProvMapper;
import com.bigdata.domain.Prov;
import com.bigdata.tools.ProvInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stone on 2016/4/29.
 */
@Service(value = "provService")
public class ProvServiceImpl implements ProvService {
    @Autowired
    private ProvMapper provMapper;
    @Override
    public int GetProvCount(ProvInfoModel provInfoModel) {
        return provMapper.GetProvCount(provInfoModel);
    }

    @Override
    public int GetProvPageCount(ProvInfoModel provInfoModel) {

        int count = provMapper.GetProvCount(provInfoModel);

        double temp = (double)count;
        return (int)Math.ceil(temp/ provInfoModel.getPageSize());
    }

    @Override
    public List<Prov> GetProvList(ProvInfoModel provInfoModel) {

        return provMapper.GetProvList(provInfoModel);
    }

    @Override
    public void InsertProv(Prov prov) {
        provMapper.InsertProv(prov);
    }
}
