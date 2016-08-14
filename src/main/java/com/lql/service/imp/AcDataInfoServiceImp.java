package com.lql.service.imp;

import com.lql.dao.AcDataInfoMapper;
import com.bigdata.domain.DataInfo;
import com.lql.service.AcDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LQL on 2016/4/12.
 */
@Transactional
@Service("acDataInfoService")
public class AcDataInfoServiceImp implements AcDataInfoService {

    @Autowired
    private AcDataInfoMapper dataInfoMapper;

    @Override
    public DataInfo getDataInfoById(String dataId) {
        DataInfo dataInfo = dataInfoMapper.getDataInfoById(dataId);
        return dataInfo;
    }

}
