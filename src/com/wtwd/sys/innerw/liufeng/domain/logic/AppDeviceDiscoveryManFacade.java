package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public interface AppDeviceDiscoveryManFacade {
	
	public int updateAppDeviceDiscoveryMan(WdeviceActiveInfo wa) throws DataAccessException;
	
}
