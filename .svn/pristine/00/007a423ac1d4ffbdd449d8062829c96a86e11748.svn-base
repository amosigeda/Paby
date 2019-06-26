package com.wtwd.sys.innerw.wappusers.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wappusers.dao.WappUsersDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class SqlMapWappUsersDao extends SqlMapClientDaoSupport implements WappUsersDao{
	
	Log logger = LogFactory.getLog(SqlMapWappUsersDao.class);

	public List<DataMap> getWappUsers(WappUsers vo)
			throws DataAccessException {
		logger.debug("getWappUsers(WappUsers vo)");
		return getSqlMapClientTemplate().queryForList("getWappUsers", vo);
	}

	public Integer insertWappUsers(WappUsers vo) throws DataAccessException {
		logger.debug("insertWappUsers(WappUsers vo)");
		return getSqlMapClientTemplate().update("insertWappUsers", vo);
	}

	public Integer deleteWappUsers(WappUsers vo) throws DataAccessException {
		logger.debug("deleteWappUsers(WappUsers vo)");
		return getSqlMapClientTemplate().update("deleteUsers", vo);
	}
	
	
	public Integer updateWappUsers(WappUsers vo) throws DataAccessException {
		logger.debug("updateWappUsers(WappUsers vo)");
		return getSqlMapClientTemplate().update("updateWappUsers", vo);
	}

	public Integer clearToken(WappUsers vo) throws DataAccessException {
		logger.debug("clearToken(WappUsers vo)");
		return getSqlMapClientTemplate().update("clearToken", vo);
	}
	
	
	public Integer getWappUsersCount(WappUsers vo)
			throws DataAccessException {
		logger.debug("getWappUsersCount(WappUsers vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getWappUsersCount", vo);
	}

	public List<DataMap> getWappUsersListByVo(WappUsers vo)
			throws DataAccessException {
		logger.debug("getWappUsersListByVo(WappUsers vo)");
		return getSqlMapClientTemplate().queryForList("getWappUsersListByVo", vo);
	}

	public Integer getWappUsersCountGroupByTime(WappUsers vo)
			throws DataAccessException {
		logger.debug("getWappUsersCountGroupByTime(WappUsers vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getWappUsersCountGroupByTime", vo);
	}

	public List<DataMap> getWappUsersGroupByTime(WappUsers vo)
			throws DataAccessException {
		logger.debug("getWappUsersGroupByTime(WappUsers vo)");
		return getSqlMapClientTemplate().queryForList("getWappUsersGroupByTime", vo);
	}
}
