package com.wtwd.sys.innerw.wfuncinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wfuncinfo.domain.Wfuncinfo;





public interface WfuncinfoDao {
	
	
	public List<DataMap> getData(Wfuncinfo vo) throws DataAccessException;
	
}
