package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.domain.WappLocationInfo;


public interface AppUserUploadLocationFacade {
	
	public int insertUserLocationInfo(WappLocationInfo wi) throws DataAccessException;
	
}
