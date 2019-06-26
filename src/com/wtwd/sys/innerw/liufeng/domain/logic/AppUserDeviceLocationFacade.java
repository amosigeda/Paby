package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public interface AppUserDeviceLocationFacade {
	
	public List<DataMap> getAppUserDeviceLocationInfo(WappUsers wu) throws SystemException;
	
	public DataList getAppUserDeviceLocation(WappUsers wu) throws SystemException;
	
	
}
