package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.dao.AppUserUploadLocationDao;
import com.wtwd.sys.innerw.liufeng.domain.WappLocationInfo;

public class AppUserUploadLocationFacadeImpl implements AppUserUploadLocationFacade {

	private AppUserUploadLocationDao appUserUploadLocationDao;
	public void setAppUserUploadLocationDao(
			AppUserUploadLocationDao appUserUploadLocationDao) {
		this.appUserUploadLocationDao = appUserUploadLocationDao;
	}

	public int insertUserLocationInfo(WappLocationInfo wi)
			throws DataAccessException {
		return appUserUploadLocationDao.insertUserLocationInfo(wi);
	}

}
