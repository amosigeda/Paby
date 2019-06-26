package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public interface AppUserDeviceTrackDao {
	
	public List<DataMap> getAppUserInfo(WappUsers wu) throws DataAccessException;
	
	public List<DataMap> getAppUserDeviceTrackList(WappUsers wu) throws DataAccessException;
	
	public Integer getAppUserDeviceTrackCountList(WappUsers wu) throws DataAccessException;
	
}
