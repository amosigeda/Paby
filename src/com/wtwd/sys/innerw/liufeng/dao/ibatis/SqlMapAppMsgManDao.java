﻿package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTAppMsgManDao;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;

public class SqlMapAppMsgManDao extends SqlMapClientDaoSupport implements WTAppMsgManDao {

	private Log log = LogFactory.getLog(SqlMapAppMsgManDao.class);

	public List<DataMap> queryByUserIdMsgInfo(WMsgInfo wm)
			throws DataAccessException {
		log.debug("SqlMapAppMsgManDao-->queryByUserIdMsgInfo");
		return getSqlMapClientTemplate().queryForList("queryByUserIdMsgInfo", wm);
	}

	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException {
		log.debug("SqlMapAppMsgManDao-->queryByUserIdMsgCount");
		return (Integer) getSqlMapClientTemplate().queryForObject("queryByUserIdMsgCount", wm);
	}

	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException {
		log.debug("updateByUserIdMsgInfo");
		return (Integer) getSqlMapClientTemplate().update("updateByUserIdMsgInfo", wm);
	}

	public int insertData(WMsgInfo wm) throws DataAccessException {
		log.debug("insertData");
		return (Integer) getSqlMapClientTemplate().update("insertData", wm);
	}
	
	public int resetMsgInfo(WMsgInfo wm) throws DataAccessException {
		log.debug("resetMsgInfo");
		return (Integer) getSqlMapClientTemplate().update("resetMsgInfo", wm);
	}

	public int resetMsgInfo2(WMsgInfo wm) throws DataAccessException {
		log.debug("resetMsgInfo2");
		return (Integer) getSqlMapClientTemplate().update("resetMsgInfo2", wm);
	}
	
	
	public int delMsgInfoIdLst(WMsgInfo wm) throws DataAccessException {
		log.debug("delMsgInfoIdLst");
		return (Integer) getSqlMapClientTemplate().update("delMsgInfoIdLst", wm);
	}
	
	
	
	public Integer getMsgInfoNextId(WMsgInfo vo)
			throws DataAccessException {
		logger.debug("getMsgInfoNextId(WMsgInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getMsgInfoNextId", vo);
	}
	
	
	
}
