package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public interface AppUserDeviceLocationDao {
	
	public List<DataMap> getAppUserDeviceLocation(WappUsers wu) throws DataAccessException;
	
	public Integer getAppUserDeviceLocationCount(WappUsers wu) throws DataAccessException;
	
}
