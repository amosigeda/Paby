package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;

public interface WTCheckVersionDao {
	
	public List<DataMap> queryCheckVersionInfo(WcheckInfo wi) throws DataAccessException;
	
}
