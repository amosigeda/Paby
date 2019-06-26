package com.wtwd.sys.innerw.wallkinds.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wallkinds.dao.WfciDetailAllDao;
import com.wtwd.sys.innerw.wallkinds.domain.WfciDetailAll;


public class SqlMapWfciDetalAllDao extends SqlMapClientDaoSupport implements WfciDetailAllDao{
	
	Log logger = LogFactory.getLog(SqlMapWfciDetalAllDao.class);

	@SuppressWarnings("unchecked")
	public List<DataMap> getDatas()
			throws DataAccessException {
		logger.debug("getDatas()");
		return getSqlMapClientTemplate().queryForList("getDatas",null);
	}

	public Integer insertRec(WfciDetailAll vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer updateRecById(WfciDetailAll vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getDataCount() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DataMap> getDataById(WfciDetailAll vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer deleteRecById(WfciDetailAll vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
