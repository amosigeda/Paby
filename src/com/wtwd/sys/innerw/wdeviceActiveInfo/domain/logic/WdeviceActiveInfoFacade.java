package com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic;

import java.util.List;
import java.util.Map;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;




public interface WdeviceActiveInfoFacade {

	public List<DataMap> getData(WdeviceActiveInfo vo) throws SystemException;
	
	public List<DataMap> getWdevPayInfo(WdeviceActiveInfo vo) throws SystemException;	

	public List<DataMap> getWdevPayInfoDyn(WdeviceActiveInfo vo) throws SystemException;	
	
	//public void testCallable( ) throws  SystemException;		
	public Map<String, Object> testCallable(WdeviceActiveInfo vo ) throws  SystemException; 

	public List<DataMap> getWdeviceActiveInfo(WdeviceActiveInfo vo) throws SystemException;
	
    public int insertWdeviceActiveInfo(WdeviceActiveInfo vo)throws SystemException;
	
	public int updateWdeviceActiveInfo(WdeviceActiveInfo vo)throws SystemException;
	
	public int deleteWdeviceActiveInfo(WdeviceActiveInfo vo)throws SystemException;

	public int insertwDeviceExtra(WdeviceActiveInfo vo) throws SystemException;
	
	public int updatewDeviceExtra(WdeviceActiveInfo vo)	throws SystemException;

	public List<DataMap> getwDeviceExtra(WdeviceActiveInfo vo) throws SystemException;

	public List<DataMap> getwdevLogFile(WdeviceActiveInfo vo) throws SystemException; 
	public int insertwdevLogFile(WdeviceActiveInfo vo) throws SystemException;

	public int updatewDeviceExtraAppStatus(WdeviceActiveInfo vo)
			throws SystemException;	
	public int updatewDeviceExtraDevStatus(WdeviceActiveInfo vo)
			throws SystemException;
	public int updatewDeviceExtraEsafeArea(WdeviceActiveInfo vo)
			throws SystemException;	
	
	public int insertDevDiscovery(WdevDiscovery vo) throws SystemException;
	public List<DataMap> getDevDiscovery(WdevDiscovery vo) throws SystemException; 
	public int updateDevDiscovery(WdevDiscovery vo) throws SystemException;
	
}
