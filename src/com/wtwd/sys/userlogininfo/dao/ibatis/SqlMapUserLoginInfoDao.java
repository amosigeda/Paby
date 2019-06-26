package com.wtwd.sys.userlogininfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.userlogininfo.dao.UserLoginInfoDao;
import com.wtwd.sys.userlogininfo.domain.UserLoginInfo;

public class SqlMapUserLoginInfoDao extends SqlMapClientDaoSupport implements
		UserLoginInfoDao {
	
	Log logger = LogFactory.getLog(SqlMapUserLoginInfoDao.class);

	public List<DataMap> getUserLoginInfo(UserLoginInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getUserLoginInfo(UserLoginInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUserLoginInfo", vo);
	}

	public int getUserLoginInfoCount(UserLoginInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getUserLoginInfoCount(UserLoginInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getUserLoginInfoCount", vo);
	}

	public List<DataMap> getUserLoginInfoListByVo(UserLoginInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getUserLoginInfoListByVo(UserLoginInfo vo)");
		return getSqlMapClientTemplate().queryForList("getUserLoginInfoListByVo", vo);
	}

	public int insertUserLoginInfo(UserLoginInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertUserLoginInfo(UserLoginInfo vo)");
		return getSqlMapClientTemplate().update("insertUserLoginInfo", vo);
	}

	//20160625 label
	public int delUserLoginInfo(UserLoginInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("delUserLoginInfo(UserLoginInfo vo)");
		return getSqlMapClientTemplate().update("delUserLoginInfo", vo);
	}
	
	
}
