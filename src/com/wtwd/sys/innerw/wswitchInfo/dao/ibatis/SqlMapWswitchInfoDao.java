package com.wtwd.sys.innerw.wswitchInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wswitchInfo.dao.WswitchInfoDao;
import com.wtwd.sys.innerw.wswitchInfo.domain.WswitchInfo;





public class SqlMapWswitchInfoDao extends SqlMapClientDaoSupport implements WswitchInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWswitchInfoDao.class);



	public List<DataMap> getData(WswitchInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
