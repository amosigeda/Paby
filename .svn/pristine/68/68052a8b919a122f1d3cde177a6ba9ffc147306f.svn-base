package com.wtwd.sys.innerw.wpetMoveInfo.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetMoveInfo.dao.WpetMoveInfoDao;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;





public class SqlMapWpetMoveInfoDao extends SqlMapClientDaoSupport implements WpetMoveInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetMoveInfoDao.class);



	public List<DataMap> getData(WpetMoveInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getWpetMoveInfoData", vo);
	}



	public List<DataMap> getWpetMoveInfo(WpetMoveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("List<DataMap> getWpetMoveInfo(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWpetMoveInfo", vo);
	}



	public int insertWpetMoveInfo(WpetMoveInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertWpetMoveInfo(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().update("insertWpetMoveInfo", vo);
	}



	public int updateWpetMoveInfo(WpetMoveInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateWpetMoveInfo(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().update("updateWpetMoveInfo", vo);
	}
	
	public List<DataMap> call_calcSugMove(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugMove");
		getSqlMapClientTemplate().queryForObject("call_calcSugMove", vo);
		return null;
	}

	public List<DataMap> call_calcSugExec(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExec");
		getSqlMapClientTemplate().queryForObject("call_calcSugExec", vo);
		return null;
	}

	public List<DataMap> call_calcSugExecSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExecSe");
		getSqlMapClientTemplate().queryForObject("call_calcSugExecSe", vo);
		return null;
	}

	public Integer mycatCalcSugExecSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("mycatCalcSugExecSe");
		getSqlMapClientTemplate().queryForObject("mycatCalcSugExecSe", vo);
		return null;
	}
	
	
	public List<DataMap> call_calcSugExecList(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExecList");
		getSqlMapClientTemplate().queryForObject("call_calcSugExecList", vo);
		return null;
	}
	
	public List<DataMap> call_calcSugExecListSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExecListSe");
		getSqlMapClientTemplate().queryForObject("call_calcSugExecListSe", vo);
		return null;
	}
	

	public List<DataMap> call_calcSugExecMon(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExecMon");
		getSqlMapClientTemplate().queryForObject("call_calcSugExecMon", vo);
		return null;
	}

	public List<DataMap> call_calcSugExecMonSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("call_calcSugExecMonSe");
		getSqlMapClientTemplate().queryForObject("call_calcSugExecMonSe", vo);
		return null;
	}

	public Integer mycatCalcSugExecMonSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("mycatCalcSugExecMonSe");
		getSqlMapClientTemplate().queryForObject("mycatCalcSugExecMonSe", vo);
		return null;
	}
	
	
	
	public List<DataMap> getOneSugExecByMon(WpetMoveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("List<DataMap> getOneSugExecByMon(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getOneSugExecByMon", vo);
	}


	public Integer mycatCalcSugExecListSe(Map<String, Object> vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("Integer mycatCalcSugExecListSe(vo)");
		getSqlMapClientTemplate().queryForObject("mycatCalcSugExecListSe", vo);
		return 0;
	}

	

	public List<DataMap> getWpetSleepInfo(WpetSleepInfo vo)
			throws DataAccessException {
		logger.debug("List<DataMap> getWpetSleepInfo(WpetSleepInfo vo)");
		return getSqlMapClientTemplate().queryForList("getWpetSleepInfo", vo);
	}



	public int insertWpetSleepInfo(WpetSleepInfo vo) throws DataAccessException {
		logger.debug("insertWpetSleepInfo(WpetSleepInfo vo)");
		return getSqlMapClientTemplate().update("insertWpetSleepInfo", vo);
	}



	public int updateWpetSleepInfo(WpetSleepInfo vo) throws DataAccessException {
		logger.debug("updateWpetSleepInfo(WpetSleepInfo vo)");
		return getSqlMapClientTemplate().update("updateWpetSleepInfo", vo);
	}

		
	public List<DataMap> getPetMoveMsgStatus(WpetMoveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("List<DataMap> getPetMoveMsgStatus(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPetMoveMsgStatus", vo);
	}

	public int insertPetMoveMsgStatus(WpetMoveInfo vo) throws DataAccessException {
		logger.debug("insertPetMoveMsgStatus(WpetMoveInfo vo)");
		return getSqlMapClientTemplate().update("insertPetMoveMsgStatus", vo);
	}


	public int insertWpetMoveInfoBatch(List<WpetMoveInfo> voList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("insertWpetMoveInfoBatch", voList);
	}
	
	public int insertWpetSleepInfoBatch(List<WpetSleepInfo> voList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("insertWpetSleepInfoBatch", voList);
	}
	

	public int insertWpetMoveInfoBatchMT(List<WpetMoveInfo> voList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("insertWpetMoveInfoBatchMT", voList);
	}
	
	
}
