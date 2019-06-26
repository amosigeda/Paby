package com.wtwd.sys.innerw.wroleFuncInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wroleFuncInfo.domain.WroleFuncInfo;





public interface WroleFuncInfoDao {
	
	
	public List<DataMap> getData(WroleFuncInfo vo) throws DataAccessException;
	
}
