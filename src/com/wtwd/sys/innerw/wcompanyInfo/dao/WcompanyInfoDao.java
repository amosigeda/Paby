package com.wtwd.sys.innerw.wcompanyInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wcompanyInfo.domain.WcompanyInfo;





public interface WcompanyInfoDao {
	
	
	public List<DataMap> getData(WcompanyInfo vo) throws DataAccessException;
	
}
