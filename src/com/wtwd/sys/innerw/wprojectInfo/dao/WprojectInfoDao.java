package com.wtwd.sys.innerw.wprojectInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wprojectInfo.domain.WprojectInfo;





public interface WprojectInfoDao {
	
	
	public List<DataMap> getData(WprojectInfo vo) throws DataAccessException;
	
}
