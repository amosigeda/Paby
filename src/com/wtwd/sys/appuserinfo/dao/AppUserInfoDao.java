package com.wtwd.sys.appuserinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;

public interface AppUserInfoDao {
	
	public List<DataMap> getAppUserInfo(AppUserInfo vo) throws DataAccessException;
	
	public Integer insertAppUserInfo(AppUserInfo vo) throws DataAccessException;
	
	public Integer updateAppUserInfo(AppUserInfo vo) throws DataAccessException;

	public Integer getAppUserInfoCount(AppUserInfo vo) throws DataAccessException;
	
	public List<DataMap> getAppUserInfoListByVo(AppUserInfo vo) throws DataAccessException;
	
	public List<DataMap> getAppUserInfoGroupByTime(AppUserInfo vo) throws DataAccessException;
	
	public Integer getAppUserInfoCountGroupByTime(AppUserInfo vo) throws DataAccessException;

}
