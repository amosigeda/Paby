package com.wtwd.sys.innerw.wswitchInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wswitchInfo.domain.WswitchInfo;





public interface WswitchInfoDao {
	
	
	public List<DataMap> getData(WswitchInfo vo) throws DataAccessException;
	
}
