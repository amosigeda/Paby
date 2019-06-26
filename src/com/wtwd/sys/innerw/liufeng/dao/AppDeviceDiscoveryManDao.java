package com.wtwd.sys.innerw.liufeng.dao;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public interface AppDeviceDiscoveryManDao {
	
	public int updateAppDeviceDiscoveryMan(WdeviceActiveInfo wa) throws DataAccessException;
	
}
