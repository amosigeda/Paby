﻿package com.wtwd.sys.locationinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.locationinfo.domain.LocationInfo;

public interface LocationInfoFacade {
	
	public List<DataMap> getLocationInfo(LocationInfo vo) throws SystemException;
	
	public int insertLocationInfo(LocationInfo vo) throws SystemException;
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws SystemException;
	
	public DataList getLocationInfoListByVo(LocationInfo vo) throws SystemException;

	public int updateLocationInfo(LocationInfo vo) throws SystemException;
	
	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo) throws SystemException;

	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsList(LocationInfo vo) throws SystemException;

	//<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsListMgr(LocationInfo vo) throws SystemException;
	
	
	public String getMaxUploadTime(LocationInfo vo) throws SystemException;
	
	public int insertClickLocationInfo(LocationInfo vo) throws SystemException;
	
	public int updateClickLocationInfo(LocationInfo vo) throws SystemException;

	public List<DataMap> SelectIccidInfo(LocationInfo vo)throws SystemException;
	
}
