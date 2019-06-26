package com.wtwd.sys.innerw.wshareInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wshareInfo.dao.WshareInfoDao;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;



public class WshareInfoFacadeImpl implements WshareInfoFacade {

	WshareInfoDao wshareInfoDao;
	public WshareInfoDao getWshareInfoDao() {
		return wshareInfoDao;
	}
	public void setWshareInfoDao(WshareInfoDao wshareInfoDao) {
		this.wshareInfoDao = wshareInfoDao;
	}
	public List<DataMap> getData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getData(vo);
	}
	public Integer insertData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.insertData(vo);
	}

	public Integer delData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.delData(vo);		
	}
	public Integer updateData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.updateData(vo);		
	}
	
	public List<DataMap> queryBindList(WshareInfo vo) throws SystemException {
		return wshareInfoDao.queryBindList(vo);		
	}

	
	public Integer insertUnshareData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.insertUnshareData(vo);
	}

	public Integer delUnshareData(WshareInfo vo) throws SystemException {
		return wshareInfoDao.delUnshareData(vo);		
	}

	public List<DataMap> queryUnbindList(WshareInfo vo) throws SystemException {
		return wshareInfoDao.queryUnbindList(vo);		
	}

	public Integer getWdevUserCount(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getWdevUserCount(vo);		
	}
	
	public List<DataMap> getAllUsers(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getAllUsers(vo);		
	}
	public List<DataMap> getDevRelUser(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getDevRelUser(vo);		
	}

	public List<DataMap> getUserRelDev(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getUserRelDev(vo);		
	}
	
	public Integer getDevRelUserOnlineCount(WshareInfo vo) throws SystemException {
		return wshareInfoDao.getDevRelUserOnlineCount(vo);				
	}
	
	
	
}