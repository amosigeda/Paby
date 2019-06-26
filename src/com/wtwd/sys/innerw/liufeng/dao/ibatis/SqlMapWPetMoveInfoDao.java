package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WPetMoveInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;

public class SqlMapWPetMoveInfoDao extends SqlMapClientDaoSupport implements WPetMoveInfoDao {

	private Log log = LogFactory.getLog(SqlMapWPetMoveInfoDao.class);

	public List<DataMap> getDaySugMoveManInfo(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getDaySugMoveManInfo");
		return getSqlMapClientTemplate().queryForList("getDaySugMoveManInfo", wm);
	}

	public List<DataMap> getOneSugExec(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getOneSugExec");
		return getSqlMapClientTemplate().queryForList("getOneSugExec", wm);
	}
	

	public List<DataMap> getOneSugExecDataList(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getOneSugExecDataList");
		return getSqlMapClientTemplate().queryForList("getOneSugExecDataList", wm);
	}

	public List<DataMap> getOneSleepExecDataList(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getOneSleepExecDataList");
		return getSqlMapClientTemplate().queryForList("getOneSleepExecDataList", wm);
	}
	
	
	public List<DataMap> getMoveList(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getMoveList");
		return getSqlMapClientTemplate().queryForList("getMoveList", wm);
	}
	
	public List<DataMap> getMoveListDataList(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getMoveListDataList");
		return getSqlMapClientTemplate().queryForList("getMoveListDataList", wm);
	}

	public List<DataMap> getMoveDataListSum(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("getMoveDataListSum");
		return getSqlMapClientTemplate().queryForList("getMoveDataListSum", wm);
	}
	
	public Integer delDaySugMoveManInfo(WPetMoveInfo wm)
			throws DataAccessException {
		log.debug("delDaySugMoveManInfo");
		return getSqlMapClientTemplate().update("delDaySugMoveManInfo", wm);
	}
	
	
}
