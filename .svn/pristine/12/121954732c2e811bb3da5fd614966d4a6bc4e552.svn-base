package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.liufeng.dao.AppUserDeviceLocationDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class AppUserDeviceLocationFacadeImpl implements AppUserDeviceLocationFacade {

	private AppUserDeviceLocationDao appUserDeviceLocationDao;
	
	public void setAppUserDeviceLocationDao(AppUserDeviceLocationDao dlDao){
		this.appUserDeviceLocationDao = dlDao;
	}
	
	public DataList getAppUserDeviceLocation(WappUsers wu)
			throws SystemException {
		DataList list = new DataList(appUserDeviceLocationDao.getAppUserDeviceLocation(wu));
		list.setTotalSize(appUserDeviceLocationDao.getAppUserDeviceLocationCount(wu));
		return list;
	}

	public List<DataMap> getAppUserDeviceLocationInfo(WappUsers wu)
			throws SystemException {
		return appUserDeviceLocationDao.getAppUserDeviceLocation(wu);
	}

}
