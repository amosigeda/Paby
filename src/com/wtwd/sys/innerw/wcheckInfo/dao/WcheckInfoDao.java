package com.wtwd.sys.innerw.wcheckInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;





public interface WcheckInfoDao {
	
	
	public List<DataMap> getData(WcheckInfo vo) throws DataAccessException;
	
}
