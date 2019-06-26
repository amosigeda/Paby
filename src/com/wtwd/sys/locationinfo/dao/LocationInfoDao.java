package com.wtwd.sys.locationinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.locationinfo.domain.LocationInfo;

public interface LocationInfoDao {
	
	public int insertLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws DataAccessException;
	
	public int getLocationInfoCount(LocationInfo vo) throws DataAccessException;

	public int updateLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo) throws DataAccessException;
	
	//	<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsList(LocationInfo vo) throws DataAccessException;
	
	//	<!-- yonghu label 20160625-->
	public List<DataMap> wtAppGpsManGetDevsListMgr(LocationInfo vo) throws DataAccessException;

	
	public String getMaxUploadTime(LocationInfo vo) throws DataAccessException;
	
	public int insertClickLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public int updateClickLocationInfo(LocationInfo vo) throws DataAccessException;

	public List<DataMap> SelectIccidInfo(LocationInfo vo)throws DataAccessException;
	
}
