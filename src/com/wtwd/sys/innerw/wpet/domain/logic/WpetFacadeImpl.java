﻿package com.wtwd.sys.innerw.wpet.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WPetMoveInfoFacade;
import com.wtwd.sys.innerw.wpet.dao.WpetDao;
import com.wtwd.sys.innerw.wpet.domain.Wpet;


public class WpetFacadeImpl implements WpetFacade {
	private WpetDao wpetDao;
	
	public WpetFacadeImpl() {		
	}
	
	public List<DataMap> getDogDataList(Wpet vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetDao.getDogDataList(vo);
	}

	public List<DataMap> getDogDataListByDevice(Wpet vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetDao.getDogDataListByDevice(vo);
	}

	public Integer getDogCount(Wpet vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetDao.getDogCount(vo);
	}

	public Integer insertDog(Wpet vo) throws SystemException {
		return wpetDao.insertDog(vo);
	}

	public Integer updateDog(Wpet vo) throws SystemException {
		Integer res =  wpetDao.updateDog(vo);
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();		
		
		if (res > 0) {
			WPetMoveInfo wmv = new WPetMoveInfo();
			WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
			String pet_id = vo.getPet_id();

			//String deviceNow1 = BaseAction.getDeviceNowFromPetId(pet_id);
			String deviceNow1 = null;
			
			if ( Constant.timeUtcFlag )
				deviceNow1 = tls.getUtcDateStrNow();
			else
				deviceNow1 = ba.getDeviceNowFromPetId(pet_id);				
			

			String d1 = tls.getDateStringAddDay(deviceNow1, -2);
			
			if ( deviceNow1.length() > 11) {
				String day = deviceNow1.substring(0, 10);
			
				if (!tls.isNullOrEmpty(day)) {
					//wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(up_time,'%Y-%m-%d') = '"+day+"'");
					wmv.setCondition(" pet_id = "+pet_id+" AND up_time >= '"+d1+"'");
					wpmFacade.delDaySugMoveManInfo(wmv);
				}
			}
		}
		
		return res;
	}
	
	
	/**
	 * @return the wpetDao
	 */
	public WpetDao getWpetDao() {
		return wpetDao;
	}

	/**
	 * @param wpetDao the wpetDao to set
	 */
	public void setWpetDao(WpetDao wpetDao) {
		this.wpetDao = wpetDao;
	}

	public Integer delPet(Wpet vo) throws SystemException {
		return wpetDao.delPet(vo);		
	}

	public Integer delPetInfo(Wpet vo) throws SystemException {
		return wpetDao.delPetInfo(vo);		
	}
	public Integer delPetMoveInfo(Wpet vo) throws SystemException {
		return wpetDao.delPetMoveInfo(vo);		
	}
	public Integer delPetSleepInfo(Wpet vo) throws SystemException {
		return wpetDao.delPetSleepInfo(vo);		
	}

	public Integer updateDogDeviceId(Wpet vo) throws SystemException {
		return wpetDao.updateDogDeviceId(vo);
	}


}