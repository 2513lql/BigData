package com.lql.service.imp;

import com.lql.dao.ChainACInfoMapper;
import com.lql.domain.ChainControlInfo;
import com.lql.service.ChainACInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LQL on 2016/4/12.
 */
@Transactional
@Service("chainACInfoService")
public class ChainACInfoServiceImp implements ChainACInfoService{


    @Autowired
    private ChainACInfoMapper chainACInfoMapper;

    @Override
    public ChainControlInfo getChainControlInfoByDataId(String dataId) {
        ChainControlInfo chainControlInfo = chainACInfoMapper.getChainControlInfoByDataId(dataId);
        return chainControlInfo;
    }

    @Override
    public void addChainControlInfo(String dataId, String dataControlInfos) {

        String[] dataIds = dataId.split(";");
        String[] controlMsgs = dataControlInfos.split(";");
        ChainControlInfo chainControlInfo = new ChainControlInfo();
        chainControlInfo.setSecurity(controlMsgs[0]);
        chainControlInfo.setDataMsgVisibility(controlMsgs[1]+ ";" + controlMsgs[1]);
        for(int i = 0 ; i < dataIds.length;i++){
            chainControlInfo.setDataId(dataIds[i]);
            ChainControlInfo chainControlInfo2 = chainACInfoMapper.getChainControlInfoByDataId(dataIds[i]);
            if (chainControlInfo2 != null){
                chainACInfoMapper.updateChainControlInfoByDataId(chainControlInfo);
            }else {
                chainACInfoMapper.addChainControlInfo(chainControlInfo);
            }
        }
    }

    @Override
    public void updateChainControlInfoByDataId(ChainControlInfo chainControlInfo) {
        chainACInfoMapper.updateChainControlInfoByDataId(chainControlInfo);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        ChainACInfoService chainACInfoService = context.getBean("chainACInfoService", ChainACInfoService.class);
//        chainACInfoService.addChainControlInfo("数据1;数据2","privacy;yes");
        ChainControlInfo chainControlInfo = chainACInfoService.getChainControlInfoByDataId("data2");
        chainControlInfo.setUpVisibility("yes");
        chainACInfoService.updateChainControlInfoByDataId(chainControlInfo);
    }
}
