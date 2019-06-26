package com.wtwd.sys.appuserinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.appuserinfo.dao.AppUserInfoDao;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;

public class SqlMapAppUserInfoDao extends SqlMapClientDaoSupport implements AppUserInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapAppUserInfoDao.class);

	public List<DataMap> getAppUserInfo(AppUserInfo vo)
			throws DataAccessException {
		logger.debug("getAppUserInfo(AppUserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAppUserInfo", vo);
	}

	public Integer insertAppUserInfo(AppUserInfo vo) throws DataAccessException {
		logger.debug("insertAppUserInfo(AppUserInfo vo)");
		return getSqlMapClientTemplate().update("insertAppUserInfo", vo);
	}

	public Integer updateAppUserInfo(AppUserInfo vo) throws DataAccessException {
		logger.debug("updateAppUserInfo(AppUserInfo vo)");
		return getSqlMapClientTemplate().update("updateAppUserInfo", vo);
	}

	public Integer getAppUserInfoCount(AppUserInfo vo)
			throws DataAccessException {
		logger.debug("getAppUserInfoCount(AppUserInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getAppUserInfoCount", vo);
	}

	public List<DataMap> getAppUserInfoListByVo(AppUserInfo vo)
			throws DataAccessException {
		logger.debug("getAppUserInfoListByVo(AppUserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAppUserInfoListByVo", vo);
	}

	public Integer getAppUserInfoCountGroupByTime(AppUserInfo vo)
			throws DataAccessException {
		logger.debug("getAppUserInfoCountGroupByTime(AppUserInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getAppUserInfoCountGroupByTime", vo);
	}

	public List<DataMap> getAppUserInfoGroupByTime(AppUserInfo vo)
			throws DataAccessException {
		logger.debug("getAppUserInfoGroupByTime(AppUserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAppUserInfoGroupByTime", vo);
	}
}
