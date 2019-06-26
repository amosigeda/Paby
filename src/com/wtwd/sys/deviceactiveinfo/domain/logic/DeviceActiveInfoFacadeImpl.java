package com.wtwd.sys.deviceactiveinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.deviceactiveinfo.dao.DeviceActiveInfoDao;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class DeviceActiveInfoFacadeImpl implements DeviceActiveInfoFacade{

	private DeviceActiveInfoDao deviceActiveInfoDao;
	
	public void setDeviceActiveInfoDao(DeviceActiveInfoDao deviceActiveInfoDao){
		this.deviceActiveInfoDao = deviceActiveInfoDao;
	}
	
	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfo(vo);
	}

	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfoCount(vo);
	}

	public int insertDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertDeviceActiveInfo(vo);
	}

	public int updateDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.updateDeviceActiveInfo(vo);
	}

	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.deleteDeviceActiveInfo(vo);
	}

	public DataList getDeviceActiveInfoListByVo(DeviceActiveInfo vo)
			throws SystemException {
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveInfoListByVo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveInfoCount(vo));
		return list;
	}

	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfoCountGroupByTime(vo);
	}

	public DataList getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)
			throws SystemException {
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveInfoGroupByTime(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveInfoCountGroupByTime(vo));
		return list;
	}

	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveHistory(vo);
	}

	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveHistoryCount(vo);
	}

	public DataList getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveHistoryListByVo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}

	public int insertDeviceActiveHistory(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertDeviceActiveHistory(vo);
	}

	public List<DataMap> getSsidInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.getSsidInfo(vo);
	}

	public int insertSmsInfo(DeviceActiveInfo vo) throws SystemException {
		return deviceActiveInfoDao.insertSmsInfo(vo);
	}

	public int updateDeviceSmsInfo(DeviceActiveInfo vo) throws SystemException {
		return deviceActiveInfoDao.updateDeviceSmsInfo(vo);
	}

	public int insertFlowInfo(DeviceActiveInfo vo) throws SystemException {
		return deviceActiveInfoDao.insertFlowInfo(vo);
	}

	public int insertPayForInfo(DeviceActiveInfo vo) throws SystemException {
		return deviceActiveInfoDao.insertPayForInfo(vo);
	}

	public List<DataMap> getPayForInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.getPayForInfo(vo);
	}

	public int insertCancelSubSriptionInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.insertCancelSubSriptionInfo(vo);
	}

	public List<DataMap> getcancleImeiInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.getcancleImeiInfo(vo);
	}

	public int updateCancelSubSriptionInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.updateCancelSubSriptionInfo(vo);
	}

	public int updatePayForDeviceInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.updatePayForDeviceInfo(vo);
	}

	public int insertCancelSubSriptionLogInfo(DeviceActiveInfo vo)
			throws SystemException {
		return deviceActiveInfoDao.insertCancelSubSriptionLogInfo(vo);
		}

	public List<DataMap> getSalesPromotion(DeviceActiveInfo vo1)
			throws SystemException {
		return deviceActiveInfoDao.getSalesPromotion(vo1);
	}

	public int getPromotionCodePayForSuccessCount(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getPromotionCodePayForSuccessCount(vo);
	}

}
