package com.wtwd.sys.innerw.wpetMoveInfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;


public interface WpetMoveInfoDao {
	
	public List<DataMap> getData(WpetMoveInfo vo) throws DataAccessException;
	public List<DataMap> getWpetMoveInfo(WpetMoveInfo vo) throws DataAccessException;
	public int insertWpetMoveInfo(WpetMoveInfo vo) throws DataAccessException;
	public int updateWpetMoveInfo(WpetMoveInfo vo) throws DataAccessException;
	
	public int insertWpetMoveInfoBatch(List<WpetMoveInfo> voList) throws DataAccessException;

	public List<DataMap> call_calcSugMove(Map<String, Object> vo) throws DataAccessException;

	public List<DataMap> call_calcSugExec(Map<String, Object> vo) throws DataAccessException;
	public List<DataMap> call_calcSugExecSe(Map<String, Object> vo) throws DataAccessException;
	public Integer mycatCalcSugExecSe(Map<String, Object> vo) throws DataAccessException;

	public List<DataMap> call_calcSugExecList(Map<String, Object> vo) throws DataAccessException;
	public List<DataMap> call_calcSugExecListSe(Map<String, Object> vo) throws DataAccessException;
	public Integer mycatCalcSugExecListSe(Map<String, Object> vo)
			throws DataAccessException;

	public List<DataMap> call_calcSugExecMon(Map<String, Object> vo) throws DataAccessException;
	public List<DataMap> call_calcSugExecMonSe(Map<String, Object> vo) throws DataAccessException;
	public Integer mycatCalcSugExecMonSe(Map<String, Object> vo) throws DataAccessException;

	public List<DataMap> getOneSugExecByMon(WpetMoveInfo vo) throws DataAccessException;
	
	public List<DataMap> getWpetSleepInfo(WpetSleepInfo vo) throws DataAccessException;
	public int insertWpetSleepInfo(WpetSleepInfo vo) throws DataAccessException;
	public int updateWpetSleepInfo(WpetSleepInfo vo) throws DataAccessException;
	public int insertWpetSleepInfoBatch(List<WpetSleepInfo> voList) throws DataAccessException;
		
	public List<DataMap> getPetMoveMsgStatus(WpetMoveInfo vo) throws DataAccessException;
	public int insertPetMoveMsgStatus(WpetMoveInfo vo) throws DataAccessException;


	
	public int insertWpetMoveInfoBatchMT(List<WpetMoveInfo> voList) throws DataAccessException;
	
}
