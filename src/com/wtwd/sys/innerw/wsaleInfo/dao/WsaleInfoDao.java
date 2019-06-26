package com.wtwd.sys.innerw.wsaleInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wsaleInfo.domain.WsaleInfo;





public interface WsaleInfoDao {
	
	
	public List<DataMap> getData(WsaleInfo vo) throws DataAccessException;
	
}