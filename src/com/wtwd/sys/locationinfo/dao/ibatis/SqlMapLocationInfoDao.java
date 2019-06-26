package com.wtwd.sys.locationinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.locationinfo.dao.LocationInfoDao;
import com.wtwd.sys.locationinfo.domain.LocationInfo;

public class SqlMapLocationInfoDao extends SqlMapClientDaoSupport implements LocationInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapLocationInfoDao.class);
	public SqlMapLocationInfoDao(){
		
	}

	public List<DataMap> getLocationInfo(LocationInfo vo)
			throws DataAccessException {
		logger.debug("getLocationInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("getLocationInfo", vo);
	}
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws DataAccessException {
		logger.debug("getLocationListInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("getLocationListInfo", vo);
	}

	public int insertLocationInfo(LocationInfo vo) throws DataAccessException {
		logger.debug("insertLocationInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().update("insertLocationInfo", vo);
	}

	public int getLocationInfoCount(LocationInfo vo) throws DataAccessException {
		logger.debug("getLocationInfoCount(LocationInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getLocationInfoCount", vo);
	}

	public int updateLocationInfo(LocationInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateLocationInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().update("updateLocationInfo", vo);
	}

	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo)
			throws DataAccessException {
		logger.debug("getLocationInfoGroupByTime(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("getLocationInfoGroupByTime", vo);
	}
	
	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsList(LocationInfo vo) 
		throws DataAccessException {
		logger.debug("wtAppGpsManGetDevsList(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("wtAppGpsManGetDevsList", vo);
		
	}
	
	
	public String getMaxUploadTime(LocationInfo vo) throws DataAccessException {
		logger.debug("getMaxUploadTime(LocationInfo vo)");
		return (String)getSqlMapClientTemplate().queryForObject("getMaxUploadTime", vo);
	}
	
	public int insertClickLocationInfo(LocationInfo vo) throws DataAccessException {
		logger.debug("insertClickLocationInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().update("insertClickLocationInfo", vo);
	}
	
	public int updateClickLocationInfo(LocationInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateClickLocationInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().update("updateClickLocationInfo", vo);
	}

	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsListMgr(LocationInfo vo) 
		throws DataAccessException {
		logger.debug("wtAppGpsManGetDevsListMgr(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("wtAppGpsManGetDevsListMgr", vo);
		
	}

	public List<DataMap> SelectIccidInfo(LocationInfo vo)
			throws DataAccessException {
		logger.debug("SelectIccidInfo(LocationInfo vo)");
		return getSqlMapClientTemplate().queryForList("SelectIccidInfo", vo);
	}
	
	
}
