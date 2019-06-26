﻿package com.wtwd.sys.innerw.wpet.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpet.dao.WpetDao;
import com.wtwd.sys.innerw.wpet.domain.Wpet;



public class SqlMapWpetDao extends SqlMapClientDaoSupport implements WpetDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetDao.class);

	public List<DataMap> getDogDataList(Wpet vo)
			throws DataAccessException {
		logger.debug("getDogDataList(Wpet vo)");
		return getSqlMapClientTemplate().queryForList("getDogDataList", vo);
	}
	
	public List<DataMap> getDogDataListByDevice(Wpet vo)
			throws DataAccessException {
		logger.debug("getDogDataListByDevice(Wpet vo)");
		return getSqlMapClientTemplate().queryForList("getDogDataListByDevice", vo);
	}
	
	public Integer getDogCount(Wpet vo) 
			throws DataAccessException {
		logger.debug("getDogCount(Wpet vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDogCount", vo);
	}

	public Integer insertDog(Wpet vo) throws DataAccessException {
		logger.debug("insertDog(Wpet vo)");
		return getSqlMapClientTemplate().update("insertDog", vo);
		
	}
	
	public Integer updateDog(Wpet vo) throws DataAccessException {
		logger.debug("updateDog(Wpet vo)");
		return getSqlMapClientTemplate().update("updateDog", vo);
		
	}
	
	public Integer updateDogDeviceId(Wpet vo) throws DataAccessException {
		logger.debug("updateDogDeviceId(Wpet vo)");
		return getSqlMapClientTemplate().update("updateDogDeviceId", vo);
	}
	
	public Integer delPet(Wpet vo) throws DataAccessException {
		logger.debug("delPet(Wpet vo)");
		return getSqlMapClientTemplate().update("delPet", vo);
		
	}
	public Integer delPetInfo(Wpet vo) throws DataAccessException {
		logger.debug("delPetInfo(Wpet vo)");
		return getSqlMapClientTemplate().update("delPetInfo", vo);
		
	}
	public Integer delPetMoveInfo(Wpet vo) throws DataAccessException {
		logger.debug("delPetMoveInfo(Wpet vo)");
		return getSqlMapClientTemplate().update("delPetMoveInfo", vo);
		
	}
	public Integer delPetSleepInfo(Wpet vo) throws DataAccessException {
		logger.debug("delPetSleepInfo(Wpet vo)");
		return getSqlMapClientTemplate().update("delPetSleepInfo", vo);
		
	}

	
}
