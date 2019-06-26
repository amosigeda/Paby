package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppUserWiFiInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WappWiFiInfo;

public class SqlMapAppUserWiFiInfoDao extends SqlMapClientDaoSupport implements AppUserWiFiInfoDao {

	private Log log = LogFactory.getLog(SqlMapAppUserWiFiInfoDao.class);

	public int insertAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException {
		log.debug("SqlMapAppUserWiFiInfoDao-->insertAppWiFiInfo");
		return getSqlMapClientTemplate().update("insertUserWiFiInfo", wi);
	}
	public int delAppWiFiInfo(WappWiFiInfo wi) throws DataAccessException {
		log.debug("SqlMapAppUserWiFiInfoDao-->delAppWiFiInfo");
		return getSqlMapClientTemplate().update("delUserWiFiInfo", wi);
	}

	public List<DataMap> getData(WappWiFiInfo wi) throws DataAccessException {
		log.debug("SqlMapAppUserWiFiInfoDao->getData");
		return getSqlMapClientTemplate().queryForList("getWappWiFiInfo", wi);
	}

}
