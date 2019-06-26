package com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic;

import java.util.List;
import java.util.Map;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wdeviceActiveInfo.dao.WdeviceActiveInfoDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

//20160625 lbl yonghu

public class WdeviceActiveInfoFacadeImpl implements WdeviceActiveInfoFacade {

	private WdeviceActiveInfoDao wdeviceActiveInfoDao;
	
	public WdeviceActiveInfoDao getWdeviceActiveInfoDao() {
		return wdeviceActiveInfoDao;
	}

	public void setWdeviceActiveInfoDao(WdeviceActiveInfoDao wdeviceActiveInfoDao) {
		this.wdeviceActiveInfoDao = wdeviceActiveInfoDao;
	}

	public List<DataMap> getData(WdeviceActiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.getData(vo);
		
	}

	public List<DataMap> getWdevPayInfo(WdeviceActiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.getWdevPayInfo(vo);		
	}
	
	public List<DataMap> getWdevPayInfoDyn(WdeviceActiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.getWdevPayInfoDyn(vo);		
	}
		
	
	//public void testCallable( ) throws  SystemException {	
	public Map<String, Object> testCallable(WdeviceActiveInfo vo ) throws  SystemException {
		return wdeviceActiveInfoDao.testCallable(vo);
	}
	
	public List<DataMap> getWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.getWdeviceActiveInfo(vo);
	}

	public int insertWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.insertWdeviceActiveInfo(vo);
	}

	public int updateWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.updateWdeviceActiveInfo(vo);
	}

	public int deleteWdeviceActiveInfo(WdeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wdeviceActiveInfoDao.deleteWdeviceActiveInfo(vo);
	}
	
	public int insertwDeviceExtra(WdeviceActiveInfo vo) 
			throws SystemException {
		return wdeviceActiveInfoDao.insertwDeviceExtra(vo);		
	}
	
	public int updatewDeviceExtra(WdeviceActiveInfo vo)	
			throws SystemException {
		return wdeviceActiveInfoDao.updatewDeviceExtra(vo);		
	}

	public List<DataMap> getwDeviceExtra(WdeviceActiveInfo vo)
		throws SystemException {
		return wdeviceActiveInfoDao.getwDeviceExtra(vo);		
	}

	
	public List<DataMap> getwdevLogFile(WdeviceActiveInfo vo) 
			throws SystemException {
		return wdeviceActiveInfoDao.getwdevLogFile(vo);				
	}
	
	public int insertwdevLogFile(WdeviceActiveInfo vo)
			throws SystemException {
		return wdeviceActiveInfoDao.insertwdevLogFile(vo);						
	}

	public int insertDevDiscovery(WdevDiscovery vo) 
			throws SystemException {
		return wdeviceActiveInfoDao.insertDevDiscovery(vo);								
	}
	public List<DataMap> getDevDiscovery(WdevDiscovery vo) 
			throws SystemException {
		return wdeviceActiveInfoDao.getDevDiscovery(vo);						
	}
	public int updateDevDiscovery(WdevDiscovery vo) 
			throws SystemException {
		return wdeviceActiveInfoDao.updateDevDiscovery(vo);								
	}

	public int updatewDeviceExtraAppStatus(WdeviceActiveInfo vo)
			throws SystemException {
		return wdeviceActiveInfoDao.updatewDeviceExtraAppStatus(vo);
	}
	public int updatewDeviceExtraDevStatus(WdeviceActiveInfo vo)
			throws SystemException {
		return wdeviceActiveInfoDao.updatewDeviceExtraDevStatus(vo);		
	}
	public int updatewDeviceExtraEsafeArea(WdeviceActiveInfo vo)
			throws SystemException {
		return wdeviceActiveInfoDao.updatewDeviceExtraEsafeArea(vo);		
	}
	

}
