package com.wtwd.sys.innerw.wshareInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wshareInfo.dao.WshareInfoDao;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;





public class SqlMapWshareInfoDao extends SqlMapClientDaoSupport implements WshareInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWshareInfoDao.class);



	public List<DataMap> getData(WshareInfo vo) throws DataAccessException {
		logger.debug("getData(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWshareInfo", vo);
	}

	public List<DataMap> queryBindList(WshareInfo vo) throws DataAccessException {
		logger.debug("queryBindlist(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getBindList", vo);
	}
	
	
	public Integer insertData(WshareInfo vo) throws DataAccessException {
		logger.debug("insertData(WshareInfo vo)");
		return getSqlMapClientTemplate().update("insertWshareInfo", vo);
	}

	public Integer insertUnshareData(WshareInfo vo) throws DataAccessException {
		logger.debug("insertUnshareData(WshareInfo vo)");
		return getSqlMapClientTemplate().update("insertWunshareInfo", vo);
	}
	
	
	public Integer delData(WshareInfo vo) throws DataAccessException {
		logger.debug("delData(WshareInfo vo)");
		return getSqlMapClientTemplate().update("delWshareInfo", vo);
	}

	public Integer updateData(WshareInfo vo) throws DataAccessException {
		logger.debug("updateData(WshareInfo vo)");
		return getSqlMapClientTemplate().update("updateWshareInfo", vo);
	}

	public Integer delUnshareData(WshareInfo vo) throws DataAccessException {
		logger.debug("delUnshareData(WshareInfo vo)");
		return getSqlMapClientTemplate().update("delWunshareInfo", vo);
	}

	public List<DataMap> queryUnbindList(WshareInfo vo) throws DataAccessException {
		logger.debug("queryUnbindList(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUnbindList", vo);
	}

	public Integer getWdevUserCount(WshareInfo vo) throws DataAccessException {
		logger.debug("getWdevUserCount(WshareInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getWdevUserCount", vo);		
	}

	public List<DataMap> getAllUsers(WshareInfo vo) throws DataAccessException {
		logger.debug("getAllUsers(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAllUsers", vo);
	}
	
	public List<DataMap> getDevRelUser(WshareInfo vo) throws DataAccessException {
		logger.debug("getDevRelUser(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDevRelUser", vo);
	}
	
	public List<DataMap> getUserRelDev(WshareInfo vo) throws DataAccessException {
		logger.debug("getUserRelDev(WshareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUserRelDev", vo);
	}

	public Integer getDevRelUserOnlineCount(WshareInfo vo)
			throws DataAccessException {
		logger.debug("getDevRelUserOnlineCount(WshareInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDevRelUserOnlineCount", vo);
	}
	
	

}
