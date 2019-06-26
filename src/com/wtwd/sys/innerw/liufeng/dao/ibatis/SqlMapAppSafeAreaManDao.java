package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.AppSafeAreaManDao;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;

public class SqlMapAppSafeAreaManDao extends SqlMapClientDaoSupport implements AppSafeAreaManDao {

	private Log log = LogFactory.getLog(SqlMapAppSafeAreaManDao.class);

	public int insertAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->insertAppSafeAreaMan");
		return getSqlMapClientTemplate().update("insertAppSafeAreaMan", wf);
	}

	public int updateAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->updateAppSafeAreaMan");
		return getSqlMapClientTemplate().update("updateAppSafeAreaMan", wf);
	}

	public int deleteAppSafeAreaMan(WeFencing wf) throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->deleteAppSafeAreaMan");
		return getSqlMapClientTemplate().delete("deleteAppSafeAreaMan", wf);
	}

	public List<DataMap> queryWeFencing(WeFencing wf)
			throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->queryWeFencing");
		return getSqlMapClientTemplate().queryForList("queryWeFencingInfo", wf);
	}
	
	public List<DataMap> queryMasterDeviceInfo(WeFencing wf)
			throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->queryMasterDeviceInfo");
		return getSqlMapClientTemplate().queryForList("queryMasterDevice", wf);
	}

	public Integer getWsafeAreaCount(WeFencing vo)
			throws DataAccessException {
		logger.debug("getWsafeAreaCount(WeFencing vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getWsafeAreaCount", vo);
	}

	public Integer getSafeAreaNextId(WeFencing vo)
			throws DataAccessException {
		logger.debug("getSafeAreaNextId(WeFencing vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSafeAreaNextId", vo);
	}
	
	
	
}
