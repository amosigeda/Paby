package com.wtwd.sys.innerw.wsysloginfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wsysloginfo.domain.WsyslogInfo;





public interface WsyslogInfoDao {
	
	
	public List<DataMap> getData(WsyslogInfo vo) throws DataAccessException;
	
}