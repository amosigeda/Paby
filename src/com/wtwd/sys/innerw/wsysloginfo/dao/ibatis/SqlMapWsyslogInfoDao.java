package com.wtwd.sys.innerw.wsysloginfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wsysloginfo.dao.WsyslogInfoDao;
import com.wtwd.sys.innerw.wsysloginfo.domain.WsyslogInfo;





public class SqlMapWsyslogInfoDao extends SqlMapClientDaoSupport implements WsyslogInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWsyslogInfoDao.class);



	public List<DataMap> getData(WsyslogInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
