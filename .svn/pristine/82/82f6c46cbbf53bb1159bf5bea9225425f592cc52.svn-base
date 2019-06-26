package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppUserWiFiInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WappWiFiInfo;

public class AppUserWiFiInfoFacadeImpl implements AppUserWiFiInfoFacade {

	private AppUserWiFiInfoDao appUserWiFiInfoDao;
	public void setAppUserWiFiInfoDao(AppUserWiFiInfoDao appUserWiFiInfoDao) {
		this.appUserWiFiInfoDao = appUserWiFiInfoDao;
	}

	public int insertAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException {
		return appUserWiFiInfoDao.insertAppWiFiInfo(wi);
	}

	public int delAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException {
		return appUserWiFiInfoDao.delAppWiFiInfo(wi);
	}

	public List<DataMap> getData(WappWiFiInfo wi) throws DataAccessException {
		return appUserWiFiInfoDao.getData(wi);
	}
	
	
}
