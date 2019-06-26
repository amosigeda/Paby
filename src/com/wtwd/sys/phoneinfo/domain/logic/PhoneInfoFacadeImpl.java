package com.wtwd.sys.phoneinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.phoneinfo.dao.PhoneInfoDao;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;

public class PhoneInfoFacadeImpl implements PhoneInfoFacade{
	private PhoneInfoDao phoneInfoDao;

	public void setPhoneInfoDao(PhoneInfoDao phoneInfoDao) {
		this.phoneInfoDao = phoneInfoDao;
	}
	public PhoneInfoFacadeImpl(){
		
	}
	public List<DataMap> getPhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.getPhoneInfo(vo);
	}
	public int insertPhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.insertPhoneInfo(vo);
	}
	public int updatePhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.updatePhoneInfo(vo);
	}
	public int getPhoneInfoCount(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneInfoCount(vo);
	}
	public DataList getPhoneInfoListByVo(PhoneInfo vo) throws SystemException {
		DataList list = new DataList(phoneInfoDao.getPhoneInfoListByVo(vo));
		list.setTotalSize(phoneInfoDao.getPhoneInfoCount(vo));
		return list;
	}
	public DataList getPhoneManageListByVo(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(phoneInfoDao.getPhoneManageListByVo(vo));
		list.setTotalSize(phoneInfoDao.getPhoneManageCount(vo));
		return list;
	}
	public List<DataMap> getPhoneManagerInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.getPhoneManageListByVo(vo);
	}
	public int insertPhoneManage(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.insertPhoneManage(vo);
	}
	public int getPhoneManageCount(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneManageCount(vo);
	}
	public Integer getPhoneManageMaxId(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneManageMaxId(vo);
	}
	public List<DataMap> getPWdeviceActiveInfo(PhoneInfo po)
			throws SystemException {
		return phoneInfoDao.getPWdeviceActiveInfo(po);
	}
	public List<DataMap> selectDeviceEnterServiceActiveInfo(PhoneInfo po)
			throws SystemException {
		return phoneInfoDao.selectDeviceEnterServiceActiveInfo(po);
	}
	public int updateDeviceServiceActiveInfo(PhoneInfo po)
			throws SystemException {
		return phoneInfoDao.updateDeviceServiceActiveInfo(po);
	}
	public int insertDeviceServiceActiveInfo(PhoneInfo po)
			throws SystemException {
		return phoneInfoDao.insertDeviceServiceActiveInfo(po);
	}

}
