package com.wtwd.sys.innerw.wdeviceActiveInfo.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wdeviceActiveInfo.dao.WdeviceActiveInfoDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;





public class SqlMapWdeviceActiveInfoDao extends SqlMapClientDaoSupport implements WdeviceActiveInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWdeviceActiveInfoDao.class);



	public List<DataMap> getData(WdeviceActiveInfo vo) throws DataAccessException {
		logger.debug("getData(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getData", vo);
	}
	
	
	public List<DataMap> getWdevPayInfo(WdeviceActiveInfo vo) throws DataAccessException {
		logger.debug("getWdevPayInfo(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWdevPayInfo", vo);
	}

	public List<DataMap> getWdevPayInfoDyn(WdeviceActiveInfo vo) throws DataAccessException {
		logger.debug("getWdevPayInfoDyn(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWdevPayInfoDyn", vo);
	}
	
	
	public Map<String, Object> testCallable(WdeviceActiveInfo vo ) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer res = new Integer(1);
		 map.put("sex_id", "1");
		 map.put("user_count", res);		 
		 getSqlMapClientTemplate().queryForObject("getTestCount", map);
		 Integer abc = (Integer) map.get("user_count");
		 logger.debug(abc.toString());
		 return map;
		 
	}

	public List<DataMap> getWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getWdeviceActiveInfo(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWdeviceActiveInfo", vo);
	}

	public int insertWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertWdeviceActiveInfo(WdeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertWdeviceActiveInfo", vo);
	}

	public int updateWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateWdeviceActiveInfo(WdeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateWdeviceActiveInfo", vo);
	}

	public int deleteWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteWdeviceActiveInfo(WdeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("deleteWdeviceActiveInfo", vo);
	}

	public int insertwDeviceExtra(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertwDeviceExtra");
		return (Integer)getSqlMapClientTemplate().update("insertwDeviceExtra", vo);
	}

	public int updatewDeviceExtra(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updatewDeviceExtra");
		return (Integer)getSqlMapClientTemplate().update("updatewDeviceExtra", vo);
	}

	public List<DataMap> getwDeviceExtra(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getwDeviceExtra(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getwDeviceExtra", vo);
	}
	
	public List<DataMap> getwdevLogFile(WdeviceActiveInfo vo) throws DataAccessException {
		logger.debug("getwdevLogFile(WdeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getwdevLogFile", vo);
	}

	public int insertwdevLogFile(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertwdevLogFile");
		return (Integer)getSqlMapClientTemplate().update("insertwdevLogFile", vo);
	}
	

	public List<DataMap> getDevDiscovery(WdevDiscovery vo) throws DataAccessException {
		logger.debug("getDevDiscovery(WdevDiscovery vo)");
		return getSqlMapClientTemplate().queryForList("getDevDiscovery", vo);
	}
	
	public int insertDevDiscovery(WdevDiscovery vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertDevDiscovery");
		return (Integer)getSqlMapClientTemplate().update("insertDevDiscovery", vo);
	}
	public int updateDevDiscovery(WdevDiscovery vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateDevDiscovery");
		return (Integer)getSqlMapClientTemplate().update("updateDevDiscovery", vo);
	}

	public int updatewDeviceExtraAppStatus(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updatewDeviceExtraAppStatus");
		return (Integer)getSqlMapClientTemplate().update("updatewDeviceExtraAppStatus", vo);
	}
	
	public int updatewDeviceExtraDevStatus(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updatewDeviceExtraDevStatus");
		return (Integer)getSqlMapClientTemplate().update("updatewDeviceExtraDevStatus", vo);
	}

	public int updatewDeviceExtraEsafeArea(WdeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updatewDeviceExtraEsafeArea");
		return (Integer)getSqlMapClientTemplate().update("updatewDeviceExtraEsafeArea", vo);
	}
	
	
	
}
