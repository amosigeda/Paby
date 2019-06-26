package com.wtwd.sys.innerw.wsportlevel1.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wsportlevel1.domain.WsportLevel1;


public interface WsportLevel1Dao {
	
	
	public List<DataMap> getDataByWeight(WsportLevel1 vo) throws DataAccessException;
	
}
