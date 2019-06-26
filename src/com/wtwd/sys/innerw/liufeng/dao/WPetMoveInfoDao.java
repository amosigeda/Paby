package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;

public interface WPetMoveInfoDao {
	
	public List<DataMap> getDaySugMoveManInfo(WPetMoveInfo wm) throws DataAccessException;

	public List<DataMap> getOneSugExec(WPetMoveInfo wm) throws DataAccessException;	
	public List<DataMap> getOneSugExecDataList(WPetMoveInfo wm) throws DataAccessException;
	public List<DataMap> getOneSleepExecDataList(WPetMoveInfo wm) throws DataAccessException;
		
	public List<DataMap> getMoveList(WPetMoveInfo wm) throws DataAccessException;
	public List<DataMap> getMoveListDataList(WPetMoveInfo wm) throws DataAccessException;
	public List<DataMap> getMoveDataListSum(WPetMoveInfo wm) throws DataAccessException;

	public Integer delDaySugMoveManInfo(WPetMoveInfo wm) throws DataAccessException;
	
}
