package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WappWiFiInfo;

public interface AppUserWiFiInfoFacade {
	
	public int insertAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException;
	public int delAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException;
	public List<DataMap> getData(WappWiFiInfo wi) throws DataAccessException;
	
}
