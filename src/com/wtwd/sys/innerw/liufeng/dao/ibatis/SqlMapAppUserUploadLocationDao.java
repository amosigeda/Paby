package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wtwd.sys.innerw.liufeng.dao.AppUserUploadLocationDao;
import com.wtwd.sys.innerw.liufeng.domain.WappLocationInfo;

public class SqlMapAppUserUploadLocationDao extends SqlMapClientDaoSupport implements AppUserUploadLocationDao {

	private Log log = LogFactory.getLog(SqlMapAppUserUploadLocationDao.class);

	public int insertUserLocationInfo(WappLocationInfo wi) throws DataAccessException {
		log.debug("SqlMapAppUserUploadLocationDao-->insertUserLocationInfo");
		return getSqlMapClientTemplate().update("insertUserLocationInfo", wi);
	}


}
