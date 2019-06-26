package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;

public interface WTCheckVersionFacade {
	
	public List<DataMap> queryCheckVersionInfo(WcheckInfo wi) throws DataAccessException;
	
}
