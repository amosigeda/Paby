package com.wtwd.sys.innerw.wappuserverify.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wappuserverify.dao.WappuserVerifyDao;
import com.wtwd.sys.innerw.wappuserverify.domain.WappuserVerify;


public class SqlMapWappuserVerifyDao extends SqlMapClientDaoSupport implements WappuserVerifyDao{
	
	Log logger = LogFactory.getLog(SqlMapWappuserVerifyDao.class);

	public List<DataMap> getData(WappuserVerify vo) throws DataAccessException {
		logger.debug("getData(WappuserVerify vo)");
		return getSqlMapClientTemplate().queryForList("getAppuVerifyData", vo);
	}

	public Integer insertData(WappuserVerify vo) throws DataAccessException {
		logger.debug("insertData(WappuserVerify vo)");
		return getSqlMapClientTemplate().update("insertAppuVerify", vo);
	}
	
	public Integer delData(WappuserVerify vo) throws DataAccessException {
		logger.debug("delData(WappuserVerify vo)");
		return getSqlMapClientTemplate().update("deleteAppuVerify", vo);
	}


	public List<DataMap> getDataLevel2(WappuserVerify vo) throws DataAccessException {
		logger.debug("getDataLevel2(WappuserVerify vo)");
		return getSqlMapClientTemplate().queryForList("getAppuVerifyDataLevel2", vo);
	}

	public Integer insertDataLevel2(WappuserVerify vo) throws DataAccessException {
		logger.debug("insertDataLevel2(WappuserVerify vo)");
		return getSqlMapClientTemplate().update("insertAppuVerifyLevel2", vo);
	}
	
	public Integer delDataLevel2(WappuserVerify vo) throws DataAccessException {
		logger.debug("delDataLevel2(WappuserVerify vo)");
		return getSqlMapClientTemplate().update("deleteAppuVerifyLevel2", vo);
	}



}
