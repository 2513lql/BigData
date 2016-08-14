package com.lql.util;

import com.lql.domain.IpReg;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by LQL on 2016/4/18.
 */
public class JSONUtil {


    /**
     * 将访问控制策略List转为JSON
     * @param list
     * @return
     */
    public static JSONObject convertListToJson(List<String> list){

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("policyName",list.get(0));
        jsonObj.put("description",list.get(1));
        jsonObj.put("ptype",list.get(2));
        jsonObj.put("userAttris",list.get(3));
        jsonObj.put("operations",list.get(4));
        jsonObj.put("environments",list.get(5));
        jsonObj.put("dataIds",list.get(6));
        jsonObj.put("ownerId",list.get(7));
        return jsonObj;
    }

    /**
     * 将ipregs转json
     * @return
     */
    public static JSONArray convertIpGresToJson(List<IpReg> ipRegs){

        JSONArray jsonArr = new JSONArray();
        for (int i = 0 ; i < ipRegs.size() ; i++){
            JSONObject jsonObj = new JSONObject();
            IpReg ipReg = ipRegs.get(i);
            jsonObj.put("ipreg",ipReg);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }
}
