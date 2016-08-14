package com.lql.dao;

import com.lql.domain.IpReg;

import java.util.List;

/**
 * Created by LQL on 2016/4/18.
 */
public interface IpRegMapper {

   public List<IpReg> getIpRegByCompany(String company);

   /**
    * 获取网段
    */
   public List<IpReg> getIpRegs();

   //根据网段获取所属公司
   String getIpCompany(String ipRegexp);
}
