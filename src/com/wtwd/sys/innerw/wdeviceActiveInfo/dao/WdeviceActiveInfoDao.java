package com.wtwd.sys.innerw.wdeviceActiveInfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;




public interface WdeviceActiveInfoDao {
	
	
	public List<DataMap> getData(WdeviceActiveInfo vo) throws DataAccessException;
	public List<DataMap> getWdevPayInfo(WdeviceActiveInfo vo) throws DataAccessException;
	public List<DataMap> getWdevPayInfoDyn(WdeviceActiveInfo vo) throws DataAccessException;
	
	
	//public void testCallable( ) throws  DataAccessException;	
	public Map<String, Object> testCallable(WdeviceActiveInfo vo ) throws  DataAccessException; 	
	
	public List<DataMap> getWdeviceActiveInfo(WdeviceActiveInfo vo)throws DataAccessException;
	
	public int insertWdeviceActiveInfo(WdeviceActiveInfo vo) throws DataAccessException;
	
	public int updateWdeviceActiveInfo(WdeviceActiveInfo vo) throws DataAccessException;
	
	public int deleteWdeviceActiveInfo(WdeviceActiveInfo vo) throws DataAccessException;

	public int insertwDeviceExtra(WdeviceActiveInfo vo) throws DataAccessException;
	
	public int updatewDeviceExtra(WdeviceActiveInfo vo)	throws DataAccessException;
	
	public List<DataMap> getwDeviceExtra(WdeviceActiveInfo vo) throws DataAccessException;

	public List<DataMap> getwdevLogFile(WdeviceActiveInfo vo) throws DataAccessException; 
	public int insertwdevLogFile(WdeviceActiveInfo vo) throws DataAccessException;

	public int insertDevDiscovery(WdevDiscovery vo) throws DataAccessException;
	public List<DataMap> getDevDiscovery(WdevDiscovery vo) throws DataAccessException; 
	public int updateDevDiscovery(WdevDiscovery vo) throws DataAccessException;

	public int updatewDeviceExtraAppStatus(WdeviceActiveInfo vo)
			throws DataAccessException;
	
	public int updatewDeviceExtraDevStatus(WdeviceActiveInfo vo)
			throws DataAccessException;

	public int updatewDeviceExtraEsafeArea(WdeviceActiveInfo vo)
			throws DataAccessException;
	
	
}
