﻿package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wtwd.sys.innerw.liufeng.dao.WSuggestionDao;
import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;

public class SqlMapSuggestionDao extends SqlMapClientDaoSupport implements WSuggestionDao {

	private Log log = LogFactory.getLog(SqlMapSuggestionDao.class);

	public int insertUserSuggestion(WSuggestion ws) throws DataAccessException {
		log.debug("SqlMapAppSafeAreaManDao-->insertAppSafeAreaMan");
		return getSqlMapClientTemplate().update("insertUserSuggestion", ws);
	}

}
