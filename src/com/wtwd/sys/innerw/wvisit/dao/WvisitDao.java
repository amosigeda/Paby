package com.wtwd.sys.innerw.wvisit.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wvisit.domain.Wvisit;





public interface WvisitDao {
	
	
	public List<DataMap> getData(Wvisit vo) throws DataAccessException;
	
}
