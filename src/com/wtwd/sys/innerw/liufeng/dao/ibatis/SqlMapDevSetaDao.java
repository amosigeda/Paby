package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wtwd.sys.innerw.liufeng.dao.WTDevSetaDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public class SqlMapDevSetaDao extends SqlMapClientDaoSupport implements WTDevSetaDao {

	private Log log = LogFactory.getLog(SqlMapDevSetaDao.class);

	public int updateDevSeta(WdeviceActiveInfo wa)
			throws DataAccessException {
		log.debug("SqlMapDevSetaDao-->updateDevSeta");
		return getSqlMapClientTemplate().update("updateDevSetaInfo", wa);
	}

}
