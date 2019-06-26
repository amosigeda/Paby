package com.wtwd.sys.innerw.wroleInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wroleInfo.domain.WroleInfo;





public interface WroleInfoDao {
	
	
	public List<DataMap> getData(WroleInfo vo) throws DataAccessException;
	
}
