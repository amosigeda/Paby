package com.wtwd.sys.innerw.wmonitorInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wmonitorInfo.domain.WmonitorInfo;





public interface WmonitorInfoDao {
	
	
	public List<DataMap> getData(WmonitorInfo vo) throws DataAccessException;
	
}
