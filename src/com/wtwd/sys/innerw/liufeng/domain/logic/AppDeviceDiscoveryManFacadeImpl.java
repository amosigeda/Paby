package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.dao.AppDeviceDiscoveryManDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public class AppDeviceDiscoveryManFacadeImpl implements AppDeviceDiscoveryManFacade {

	private AppDeviceDiscoveryManDao appDeviceDiscoveryManDao;
	public void setAppDeviceDiscoveryManDao(
			AppDeviceDiscoveryManDao appDeviceDiscoveryManDao) {
		this.appDeviceDiscoveryManDao = appDeviceDiscoveryManDao;
	}
	
	public int updateAppDeviceDiscoveryMan(WdeviceActiveInfo wa)
			throws DataAccessException {
		return appDeviceDiscoveryManDao.updateAppDeviceDiscoveryMan(wa);
	}

}
