package com.wtwd.sys.innerw.wpetMoveInfo.domain.logic;

import java.util.List;
import java.util.Map;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;



public interface WpetMoveInfoFacade {
	public List<DataMap> getData(WpetMoveInfo vo) throws SystemException;

	public List<DataMap> getWpetMoveInfo(WpetMoveInfo vo) throws SystemException;

	public Integer insertWpetMoveInfo(WpetMoveInfo vo) throws SystemException;
	
	public Integer insertWpetMoveInfoBatch(List<WpetMoveInfo> voList) throws SystemException;

	public Integer updateWpetMoveInfo(WpetMoveInfo vo) throws SystemException;

	public List<DataMap> call_calcSugMove(Map<String, Object> vo) throws SystemException;

	public List<DataMap> call_calcSugExec(Map<String, Object> vo) throws SystemException;
	public List<DataMap> call_calcSugExecSe(Map<String, Object> vo) throws SystemException;
	public Integer mycatCalcSugExecSe(Map<String, Object> vo) throws SystemException;
	
	public List<DataMap> call_calcSugExecList(Map<String, Object> vo) throws SystemException;
	public List<DataMap> call_calcSugExecListSe(Map<String, Object> vo) throws SystemException;
	public Integer mycatCalcSugExecListSe(Map<String, Object> vo)
			throws SystemException;

	public List<DataMap> call_calcSugExecMon(Map<String, Object> vo) throws SystemException;
	public List<DataMap> call_calcSugExecMonSe(Map<String, Object> vo) throws SystemException;
	public Integer mycatCalcSugExecMonSe(Map<String, Object> vo) throws SystemException;

	public List<DataMap> getOneSugExecByMon(WpetMoveInfo vo) throws SystemException;
	
	public List<DataMap> getWpetSleepInfo(WpetSleepInfo vo) throws SystemException;

	public Integer insertWpetSleepInfo(WpetSleepInfo vo) throws SystemException;

	public Integer updateWpetSleepInfo(WpetSleepInfo vo) throws SystemException;
	
	public Integer insertWpetSleepInfoBatch(List<WpetSleepInfo> voList) throws SystemException;

	public List<DataMap> getPetMoveMsgStatus(WpetMoveInfo vo) throws SystemException;
	public int insertPetMoveMsgStatus(WpetMoveInfo vo) throws SystemException;

	
	public Integer insertWpetMoveInfoBatchMT(List<WpetMoveInfo> voList) throws SystemException;
	

}
