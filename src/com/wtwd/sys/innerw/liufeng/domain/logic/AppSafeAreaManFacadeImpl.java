package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppSafeAreaManDao;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;

public class AppSafeAreaManFacadeImpl implements AppSafeAreaManFacade {

	private AppSafeAreaManDao appSafeAreaManDao;
	public void setAppSafeAreaManDao(AppSafeAreaManDao appSafeAreaManDao) {
		this.appSafeAreaManDao = appSafeAreaManDao;
	}
	
	public int insertAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		return appSafeAreaManDao.insertAppSafeAreaMan(wf);
	}
	
	public int updateAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		return appSafeAreaManDao.updateAppSafeAreaMan(wf);
	}
	
	public int deleteAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		return appSafeAreaManDao.deleteAppSafeAreaMan(wf);
	}
	
	public List<DataMap> queryWeFencing(WeFencing wf)
			throws DataAccessException {
		return appSafeAreaManDao.queryWeFencing(wf);
	}

	public List<DataMap> queryMasterDeviceInfo(WeFencing wf)
			throws DataAccessException {
		return appSafeAreaManDao.queryMasterDeviceInfo(wf);
	}

	public Integer getWsafeAreaCount(WeFencing vo)
			throws DataAccessException {
		return appSafeAreaManDao.getWsafeAreaCount(vo);		
	}
	
	public Integer getSafeAreaNextId(WeFencing vo)
			throws DataAccessException {
		return appSafeAreaManDao.getSafeAreaNextId(vo);				
	}

}
