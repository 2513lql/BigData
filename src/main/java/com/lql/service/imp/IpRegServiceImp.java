package com.lql.service.imp;

import com.lql.dao.IpRegMapper;
import com.lql.domain.IpReg;
import com.lql.service.IpRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LQL on 2016/4/18.
 */
@Transactional
@Service("ipRegService")
public class IpRegServiceImp implements IpRegService{


    @Autowired
    private IpRegMapper ipRegMapper;

    @Override
    public List<IpReg> getIpRegByCompany(String company) {

        List<IpReg> ipRegs = ipRegMapper.getIpRegByCompany(company);

        return ipRegs;
    }

    @Override
    public List<IpReg> getIpRegs() {
        List<IpReg> ipRegs = ipRegMapper.getIpRegs();
        return ipRegs;
    }

    @Override
    public String getIpCompany(String ipRegexp) {
        return ipRegMapper.getIpCompany(ipRegexp);
    }
}
