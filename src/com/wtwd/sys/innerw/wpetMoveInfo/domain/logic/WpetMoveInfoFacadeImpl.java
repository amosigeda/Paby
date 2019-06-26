package com.wtwd.sys.innerw.wpetMoveInfo.domain.logic;

import java.util.List;
import java.util.Map;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.innerw.wpetMoveInfo.dao.WpetMoveInfoDao;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;



public class WpetMoveInfoFacadeImpl implements WpetMoveInfoFacade {

	
	private WpetMoveInfoDao wpetMoveInfoDao;
	


	public WpetMoveInfoDao getWpetMoveInfoDao() {
		return wpetMoveInfoDao;
	}

	public void setWpetMoveInfoDao(WpetMoveInfoDao wpetMoveInfoDao) {
		this.wpetMoveInfoDao = wpetMoveInfoDao;
	}

	public WpetMoveInfoFacadeImpl(){
		
	}

	public List<DataMap> getData(WpetMoveInfo vo) throws SystemException {
		return wpetMoveInfoDao.getData(vo);
	}

	public List<DataMap> getWpetMoveInfo(WpetMoveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.getWpetMoveInfo(vo);
	}

	public Integer insertWpetMoveInfo(WpetMoveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.insertWpetMoveInfo(vo);
	}
	
	public Integer insertWpetMoveInfoBatch(List<WpetMoveInfo> voList)
			throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.insertWpetMoveInfoBatch(voList);
	}

	public Integer updateWpetMoveInfo(WpetMoveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.updateWpetMoveInfo(vo);
	}
	
	public List<DataMap> call_calcSugMove(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.call_calcSugMove(vo);
	}

	public List<DataMap> call_calcSugExec(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.call_calcSugExec(vo);		
	}

	public List<DataMap> call_calcSugExecSe(Map<String, Object> vo) 
			throws SystemException {
		if ( Constant.IS_MYCAT_DBMGR ) {
			try {
				mycatCalcSugExecSe(vo);
			} catch(Exception e) {
				
			}
			return null;
		}
		
		return wpetMoveInfoDao.call_calcSugExecSe(vo);		
	}

	public Integer mycatCalcSugExecSe(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.mycatCalcSugExecSe(vo);		
	}
	
	
	public List<DataMap> call_calcSugExecList(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.call_calcSugExecList(vo);		
	}

	public List<DataMap> call_calcSugExecListSe(Map<String, Object> vo) 
			throws SystemException {
		if ( Constant.IS_MYCAT_DBMGR ) {
			try {
				mycatCalcSugExecListSe(vo);
			} catch(Exception e) {
				
			}
			return null;
		}
			
		return wpetMoveInfoDao.call_calcSugExecListSe(vo);		
	}
	public Integer mycatCalcSugExecListSe(Map<String, Object> vo)
			throws SystemException {
		return wpetMoveInfoDao.mycatCalcSugExecListSe(vo);
	}
	
	
	public List<DataMap> call_calcSugExecMon(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.call_calcSugExecMon(vo);		
	}

	public List<DataMap> call_calcSugExecMonSe(Map<String, Object> vo) 
			throws SystemException {
		if ( Constant.IS_MYCAT_DBMGR ) {
			try {
				mycatCalcSugExecMonSe(vo);
			} catch(Exception e) {
				
			}
			return null;
		}
		
		return wpetMoveInfoDao.call_calcSugExecMonSe(vo);		
	}
	public Integer mycatCalcSugExecMonSe(Map<String, Object> vo) 
			throws SystemException {
		return wpetMoveInfoDao.mycatCalcSugExecMonSe(vo);		
	}

	
	public List<DataMap> getOneSugExecByMon(WpetMoveInfo vo) 
			throws SystemException {
		return wpetMoveInfoDao.getOneSugExecByMon(vo);		
	}

	public List<DataMap> getWpetSleepInfo(WpetSleepInfo vo)
			throws SystemException {
		return wpetMoveInfoDao.getWpetSleepInfo(vo);
	}

	public Integer insertWpetSleepInfo(WpetSleepInfo vo) throws SystemException {
		return wpetMoveInfoDao.insertWpetSleepInfo(vo);
	}

	public Integer updateWpetSleepInfo(WpetSleepInfo vo) throws SystemException {
		return wpetMoveInfoDao.updateWpetSleepInfo(vo);
	}
	
	public Integer insertWpetSleepInfoBatch(List<WpetSleepInfo> voList)
			throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.insertWpetSleepInfoBatch(voList);
	}

	public List<DataMap> getPetMoveMsgStatus(WpetMoveInfo vo) throws SystemException {
		return wpetMoveInfoDao.getPetMoveMsgStatus(vo);		
	}

	public int insertPetMoveMsgStatus(WpetMoveInfo vo) throws SystemException {
		return wpetMoveInfoDao.insertPetMoveMsgStatus(vo);		
	}
	

	public Integer insertWpetMoveInfoBatchMT(List<WpetMoveInfo> voList)
			throws SystemException {
		// TODO Auto-generated method stub
		return wpetMoveInfoDao.insertWpetMoveInfoBatchMT(voList);
	}
	
	
}
