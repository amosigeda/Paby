package com.wtwd.sys.innerw.wpetInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetInfo.domain.WpetInfo;





public interface WpetInfoDao {
	
	
	public List<DataMap> getData(WpetInfo vo) throws DataAccessException;
	
}
