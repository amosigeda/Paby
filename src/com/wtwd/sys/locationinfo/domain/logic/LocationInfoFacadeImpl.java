﻿package com.wtwd.sys.locationinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.locationinfo.dao.LocationInfoDao;
import com.wtwd.sys.locationinfo.domain.LocationInfo;

public class LocationInfoFacadeImpl implements LocationInfoFacade{
	
	private LocationInfoDao locationInfoDao;
	

	public void setLocationInfoDao(LocationInfoDao locationInfoDao) {
		this.locationInfoDao = locationInfoDao;
	}

	public List<DataMap> getLocationInfo(LocationInfo vo)
			throws SystemException {
		return locationInfoDao.getLocationInfo(vo);
	}
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws SystemException {
		return locationInfoDao.getLocationListInfo(vo);
	}

	public int insertLocationInfo(LocationInfo vo) throws SystemException {
		
		int res =  locationInfoDao.insertLocationInfo(vo);
		Thread eTd = new Thread(new ESafeRunnable(vo));
		eTd.start();
		return res;
	}

	public DataList getLocationInfoListByVo(LocationInfo vo)
			throws SystemException {
		DataList list = new DataList(locationInfoDao.getLocationInfo(vo));
		list.setTotalSize(locationInfoDao.getLocationInfoCount(vo));
		return list;
	}

	public int updateLocationInfo(LocationInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return locationInfoDao.updateLocationInfo(vo);
	}

	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return locationInfoDao.getLocationInfoGroupByTime(vo);
	}
	
	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsList(LocationInfo vo)
		throws SystemException {
		return locationInfoDao.wtAppGpsManGetDevsList(vo);		
	}

	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsListMgr(LocationInfo vo)
		throws SystemException {
		return locationInfoDao.wtAppGpsManGetDevsListMgr(vo);		
	}
	
	
	public String getMaxUploadTime(LocationInfo vo) 
			throws SystemException {
		return locationInfoDao.getMaxUploadTime(vo);		
		
	}
	
	

	class ESafeRunnable implements Runnable {  
		private LocationInfo pmi;
		protected ESafeRunnable(LocationInfo rPmi) {
			pmi = rPmi;
	    }  
	    
	    public void run() {
	    	
	    }
	}


	public int insertClickLocationInfo(LocationInfo vo) throws SystemException {
		return locationInfoDao.insertClickLocationInfo(vo);
	}

	public int updateClickLocationInfo(LocationInfo vo) throws SystemException {
		return locationInfoDao.updateClickLocationInfo(vo);
	}

	public List<DataMap> SelectIccidInfo(LocationInfo vo)
			throws SystemException {
		return locationInfoDao.SelectIccidInfo(vo);		
		}
	
	
}