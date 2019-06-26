package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.liufeng.dao.AppUserDeviceTrackDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class AppUserDeviceTrackFacadeImpl implements AppUserDeviceTrackFacade {

	private AppUserDeviceTrackDao appUserDeviceTrackDao;
	public void setAppUserDeviceTrackDao(AppUserDeviceTrackDao appUserDeviceTrackDao) {
		this.appUserDeviceTrackDao = appUserDeviceTrackDao;
	}
	
	public List<DataMap> getAppUserInfo(WappUsers wu) throws SystemException {
		return appUserDeviceTrackDao.getAppUserInfo(wu);
	}
	
	public List<DataMap> getAppUserDeviceTrackList(WappUsers wu)
			throws SystemException {
		return appUserDeviceTrackDao.getAppUserDeviceTrackList(wu);
	}

	public Integer getAppUserDeviceTrackCountList(WappUsers wu)
			throws SystemException {
		return appUserDeviceTrackDao.getAppUserDeviceTrackCountList(wu);
	}

}
