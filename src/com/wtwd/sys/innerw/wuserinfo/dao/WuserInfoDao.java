package com.wtwd.sys.innerw.wuserinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wuserinfo.domain.WuserInfo;





public interface WuserInfoDao {
	
	
	public List<DataMap> getData(WuserInfo vo) throws DataAccessException;
	
}
