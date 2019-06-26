package com.wtwd.sys.innerw.wsportlevel1.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wsportlevel1.dao.WsportLevel1Dao;
import com.wtwd.sys.innerw.wsportlevel1.domain.WsportLevel1;


public class SqlMapWsportLevel1Dao extends SqlMapClientDaoSupport implements WsportLevel1Dao{
	
	Log logger = LogFactory.getLog(SqlMapWsportLevel1Dao.class);

	public List<DataMap> getDataByWeight(WsportLevel1 vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}



}
