package com.lql.service;

import com.bigdata.domain.DataInfo;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by LQL on 2016/4/21.
 */
public interface ChainAccessControlService {

    //用户是否在链上
    boolean isChainCanBeSeenByRequester(List<DataInfo> dataInfos, Integer requesterId);

    //当前节点是否对用户可见
    boolean isCurrentNodeCanBeSeen(String dataId, Integer requesterId,HttpServletRequest request);

    JSONObject dataMessageCanSeen(String dataId, Integer requesterId);

    /**
     * 数据访问控制
     * @param dataId
     * @param requesterId
     * @param operation
     * @param request
     * @return
     */
    boolean dataAccessControl(String dataId, Integer requesterId,String operation,HttpServletRequest request);

}
