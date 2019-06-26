﻿package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTSoundInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WSoundInfo;

public class SqlMapWSoundInfoDao extends SqlMapClientDaoSupport implements WTSoundInfoDao {

	private Log log = LogFactory.getLog(SqlMapWSoundInfoDao.class);

	public List<DataMap> queryDevicePetByUserId(WSoundInfo ws)
			throws DataAccessException {
		log.debug("SqlMapWSoundInfoDao-->queryDevicePetByUserId");
		return getSqlMapClientTemplate().queryForList("queryDevicePetByUserId", ws);
	}

	public int insertPetSoundInfo(WSoundInfo ws) throws DataAccessException {
		log.debug("SqlMapWSoundInfoDao-->insertPetSoundInfo");
		return getSqlMapClientTemplate().update("insertPetSoundInfo", ws);
	}

}