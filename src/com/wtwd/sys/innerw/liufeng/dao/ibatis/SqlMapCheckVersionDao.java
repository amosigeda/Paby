package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTCheckVersionDao;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;

public class SqlMapCheckVersionDao extends SqlMapClientDaoSupport implements WTCheckVersionDao {

	private Log log = LogFactory.getLog(SqlMapCheckVersionDao.class);

	public List<DataMap> queryCheckVersionInfo(WcheckInfo wi) throws DataAccessException {
		log.debug("SqlMapDevSetaDao-->updateDevSeta");
		return getSqlMapClientTemplate().queryForList("getCheckVersionInfo", wi);
	}

}
