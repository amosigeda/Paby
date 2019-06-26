package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppUserDeviceLocationDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class SqlMapAppUserDeviceLocationDao extends SqlMapClientDaoSupport implements AppUserDeviceLocationDao {

	private Log log = LogFactory.getLog(SqlMapAppUserDeviceLocationDao.class);
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getAppUserDeviceLocation(WappUsers wu)
			throws DataAccessException {
		log.debug("SqlMapAppUserDeviceLocationDao-->getAppUserDeviceLocation");
		return getSqlMapClientTemplate().queryForList("getDeviceLocationInfo", wu);
	}

	public Integer getAppUserDeviceLocationCount(WappUsers wu)
			throws DataAccessException {
		log.debug("SqlMapAppUserDeviceLocationDao-->getAppUserDeviceLocationCount");
		return (Integer) getSqlMapClientTemplate().queryForObject("getDeviceLocationInfoCount", wu);
	}

}
