package com.wtwd.sys.innerw.wefencing.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wefencing.domain.Wefencing;






public interface WefencingDao {
	
	
	public List<DataMap> getData(Wefencing vo) throws DataAccessException;
	
}
