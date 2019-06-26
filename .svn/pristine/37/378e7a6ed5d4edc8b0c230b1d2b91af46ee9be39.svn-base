package com.wtwd.sys.innerw.wappusers.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wappusers.dao.WappUsersDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class WappUsersFacadeImpl implements WappUsersFacade{
	
	private WappUsersDao wappUsersDao;
	

	public void setWappUsersDao(WappUsersDao appUserInfoDao) {
		this.wappUsersDao = appUserInfoDao;
	}
	
	public WappUsersFacadeImpl(){
		
	}

	public List<DataMap> getWappUsers(WappUsers vo) throws SystemException {
		return wappUsersDao.getWappUsers(vo);
	}

	public Integer insertWappUsers(WappUsers vo) throws SystemException {
		return wappUsersDao.insertWappUsers(vo);
	}

	public Integer deleteWappUsers(WappUsers vo) throws SystemException {
		return wappUsersDao.deleteWappUsers(vo);
	}
	
	
	public Integer updateWappUsers(WappUsers vo) throws SystemException {
		return wappUsersDao.updateWappUsers(vo);
	}

	public Integer clearToken(WappUsers vo) throws SystemException {
		return wappUsersDao.clearToken(vo);
	}
	
	public Integer getWappUsersCount(WappUsers vo) throws SystemException {
		// TODO Auto-generated method stub
		return wappUsersDao.getWappUsersCount(vo);
	}

	public Integer getWappUsersCountGroupByTime(WappUsers vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wappUsersDao.getWappUsersCountGroupByTime(vo);
	}

	public DataList getWappUsersGroupByTime(WappUsers vo)
			throws SystemException {
		DataList list = new DataList(wappUsersDao.getWappUsersGroupByTime(vo));
		list.setTotalSize(wappUsersDao.getWappUsersCount(vo));
		return list;
	}

}
