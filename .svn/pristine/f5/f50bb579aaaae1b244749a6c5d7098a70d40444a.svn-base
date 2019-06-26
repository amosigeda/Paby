package com.wtwd.sys.deviceactiveinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.deviceactiveinfo.dao.DeviceActiveInfoDao;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class SqlMapDeviceActiveInfoDao extends SqlMapClientDaoSupport implements DeviceActiveInfoDao{

  private transient Log logger = LogFactory.getLog(SqlMapDeviceActiveInfoDao.class);
	
	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfo", vo);
	}

	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoCount(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveInfoCount", vo);
	}

	public int insertDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("insertDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertDeviceActiveInfo", vo);
	}

	public int updateDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("updateDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateDeviceActiveInfo", vo);
	}

	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("deleteDeviceActiveInfo", vo);
	}

	public List<DataMap> getDeviceActiveInfoListByVo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoListByVo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfoListByVo", vo);
	}

	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveInfoCountGroupByTime", vo);
	}

	public List<DataMap> getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfoGroupByTime", vo);
	}

	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveHistory", vo);
	}

	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistoryCount(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveHistoryCount", vo);
	}

	public List<DataMap> getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveHistoryListByVo", vo);
	}

	public int insertDeviceActiveHistory(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().update("insertDeviceActiveHistory", vo);
	}

	public List<DataMap> getSsidInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getSsidInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSsidInfo", vo);
	}

	public int insertSmsInfo(DeviceActiveInfo vo) throws DataAccessException {
		logger.debug("insertSmsInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertSmsInfo", vo);
	}

	public int updateDeviceSmsInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("updateDeviceSmsInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateDeviceSmsInfo", vo);
	}

	public int insertFlowInfo(DeviceActiveInfo vo) throws DataAccessException {
		logger.debug("insertFlowInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertFlowInfo", vo);
	}

	public int insertPayForInfo(DeviceActiveInfo vo) throws DataAccessException {
		logger.debug("insertPayForInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertPayForInfo", vo);
	}

	public List<DataMap> getPayForInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getPayForInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPayForInfo", vo);
	}

	public int insertCancelSubSriptionInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("insertCancelSubSriptionInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertCancelSubSriptionInfo", vo);
	}

	public List<DataMap> getcancleImeiInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getcancleImeiInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getcancleImeiInfo", vo);
	}

	public int updateCancelSubSriptionInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("updateCancelSubSriptionInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateCancelSubSriptionInfo", vo);
	}

	public int updatePayForDeviceInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("updatePayForDeviceInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updatePayForDeviceInfo", vo);
	}

	public int insertCancelSubSriptionLogInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("insertCancelSubSriptionLogInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertCancelSubSriptionLogInfo", vo);
	}

	public List<DataMap> getSalesPromotion(DeviceActiveInfo vo1)
			throws DataAccessException {
		logger.debug("getSalesPromotion(DeviceActiveInfo vo1)");
		return getSqlMapClientTemplate().queryForList("getSalesPromotion", vo1);
	}

	public int getPromotionCodePayForSuccessCount(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getPromotionCodePayForSuccessCount(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getPromotionCodePayForSuccessCount", vo);
	}

}
