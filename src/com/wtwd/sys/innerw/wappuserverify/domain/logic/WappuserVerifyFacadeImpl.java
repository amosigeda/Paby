package com.wtwd.sys.innerw.wappuserverify.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wappuserverify.dao.WappuserVerifyDao;
import com.wtwd.sys.innerw.wappuserverify.domain.WappuserVerify;



public class WappuserVerifyFacadeImpl implements WappuserVerifyFacade {

	private WappuserVerifyDao wappuserVerifyDao;
	


	
	public WappuserVerifyFacadeImpl(){
		
	}

	
	public List<DataMap> getData(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.getData(vo);
	}
	public Integer insertData(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.insertData(vo);
	}

	/**
	 * @param wvDao the wvDao to set
	 */
	public void setWappuserVerifyDao(WappuserVerifyDao wvDao) {
		this.wappuserVerifyDao = wvDao;
	}

	public Integer delData(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.delData(vo);		
	}
	

	public List<DataMap> getDataLevel2(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.getDataLevel2(vo);
	}
	public Integer insertDataLevel2(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.insertDataLevel2(vo);
	}

	public Integer delDataLevel2(WappuserVerify vo) throws SystemException {
		return wappuserVerifyDao.delDataLevel2(vo);		
	}


}
