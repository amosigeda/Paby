package com.wtwd.sys.innerw.wpetInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetInfo.dao.WpetInfoDao;
import com.wtwd.sys.innerw.wpetInfo.domain.WpetInfo;



public class WpetInfoFacadeImpl implements WpetInfoFacade {

	
	private WpetInfoDao wpetInfoDao;
	


	public WpetInfoDao getWpetInfoDao() {
		return wpetInfoDao;
	}

	public void setWpetInfoDao(WpetInfoDao wpetInfoDao) {
		this.wpetInfoDao = wpetInfoDao;
	}

	public WpetInfoFacadeImpl(){
		
	}

	public List<DataMap> getData(WpetInfo vo) throws SystemException {
		return wpetInfoDao.getData(vo);
	}


}
