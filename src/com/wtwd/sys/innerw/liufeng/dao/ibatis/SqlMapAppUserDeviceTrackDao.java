package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppUserDeviceTrackDao;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class SqlMapAppUserDeviceTrackDao extends SqlMapClientDaoSupport implements AppUserDeviceTrackDao {

	private Log log = LogFactory.getLog(SqlMapAppUserDeviceTrackDao.class);

	public List<DataMap> getAppUserInfo(WappUsers wu)
			throws DataAccessException {
		log.debug("SqlMapAppUserDeviceTrackDao-->getAppUserInfo");
		return getSqlMapClientTemplate().queryForList("getUserValidationInfo", wu);
	}

	public List<DataMap> getAppUserDeviceTrackList(WappUsers wu)
			throws DataAccessException {
		log.debug("SqlMapAppUserDeviceTrackDao-->getAppUserDeviceTrackList");
		return getSqlMapClientTemplate().queryForList("getDeviceTrackInfo", wu);
	}

	public Integer getAppUserDeviceTrackCountList(WappUsers wu)
			throws DataAccessException {
		log.debug("SqlMapAppUserDeviceTrackDao-->getAppUserDeviceTrackCountList");
		return (Integer) getSqlMapClientTemplate().queryForObject("getDeviceTrackCountInfo", wu);
	}
	

}
