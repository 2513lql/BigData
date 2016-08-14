package com.lql.service;

import com.lql.domain.IpReg;

import java.util.List;

/**
 * Created by LQL on 2016/4/18.
 */
public interface IpRegService {

    public List<IpReg> getIpRegByCompany(String company);

    public List<IpReg> getIpRegs();

    String getIpCompany(String ipRegexp);

}
