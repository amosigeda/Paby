package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WPetMoveInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;

public class WPetMoveInfoFacadeImpl implements WPetMoveInfoFacade {

	private WPetMoveInfoDao wPetMoveInfoDao;
	public void setwPetMoveInfoDao(WPetMoveInfoDao wPetMoveInfoDao) {
		this.wPetMoveInfoDao = wPetMoveInfoDao;
	}

	public List<DataMap> getDaySugMoveManInfo(WPetMoveInfo wm)
			throws DataAccessException {
		return wPetMoveInfoDao.getDaySugMoveManInfo(wm);
	}
	
	public List<DataMap> getOneSugExec(WPetMoveInfo wm) throws DataAccessException {
		return wPetMoveInfoDao.getOneSugExec(wm);
	}
	public List<DataMap> getOneSugExecDataList(WPetMoveInfo wm) throws DataAccessException {
		return wPetMoveInfoDao.getOneSugExecDataList(wm);
	}
	public List<DataMap> getOneSleepExecDataList(WPetMoveInfo wm) throws DataAccessException {
		return wPetMoveInfoDao.getOneSleepExecDataList(wm);
	}

	
	public List<DataMap> getMoveList(WPetMoveInfo wm) throws DataAccessException {
			return wPetMoveInfoDao.getMoveList(wm);			
	}
	
	public List<DataMap> getMoveListDataList(WPetMoveInfo wm) throws DataAccessException {
		return wPetMoveInfoDao.getMoveListDataList(wm);
	}

	public List<DataMap> getMoveDataListSum(WPetMoveInfo wm) throws DataAccessException {
		return wPetMoveInfoDao.getMoveDataListSum(wm);
	}
	
	public Integer delDaySugMoveManInfo(WPetMoveInfo wm)
			throws DataAccessException {
		return wPetMoveInfoDao.delDaySugMoveManInfo(wm);
	}

}
