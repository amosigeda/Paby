package com.wtwd.sys.innerw.wsaleInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wsaleInfo.dao.WsaleInfoDao;
import com.wtwd.sys.innerw.wsaleInfo.domain.WsaleInfo;





public class SqlMapWsaleInfoDao extends SqlMapClientDaoSupport implements WsaleInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWsaleInfoDao.class);



	public List<DataMap> getData(WsaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
