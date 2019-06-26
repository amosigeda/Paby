package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wtwd.sys.innerw.liufeng.dao.AppDeviceDiscoveryManDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public class SqlMapAppDeviceDiscoveryManDao extends SqlMapClientDaoSupport implements AppDeviceDiscoveryManDao {

	private Log log = LogFactory.getLog(SqlMapAppDeviceDiscoveryManDao.class);

	public int updateAppDeviceDiscoveryMan(WdeviceActiveInfo wa)
			throws DataAccessException {
		log.debug("SqlMapAppDeviceDiscoveryManDao-->updateAppDeviceDiscoveryMan");
		return getSqlMapClientTemplate().update("updateAppDeviceDiscoveryMan", wa);
	}

}
