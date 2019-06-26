package com.wtwd.sys.phoneinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;

public interface PhoneInfoFacade {
	
	public List<DataMap> getPhoneInfo(PhoneInfo vo) throws SystemException;
	
	public int insertPhoneInfo(PhoneInfo vo) throws SystemException;
	
	public int updatePhoneInfo(PhoneInfo vo) throws SystemException;
	
	public DataList getPhoneInfoListByVo(PhoneInfo vo) throws SystemException;
	
	public int getPhoneInfoCount(PhoneInfo vo) throws SystemException;
	
	public int insertPhoneManage(PhoneInfo vo) throws SystemException;
	
	public DataList getPhoneManageListByVo(PhoneInfo vo) throws SystemException;
	
	public List<DataMap> getPhoneManagerInfo(PhoneInfo vo) throws SystemException;
	
	public int getPhoneManageCount(PhoneInfo vo) throws SystemException;
	
	public Integer getPhoneManageMaxId(PhoneInfo vo) throws SystemException;

	public List<DataMap> getPWdeviceActiveInfo(PhoneInfo po)throws SystemException;

	public List<DataMap> selectDeviceEnterServiceActiveInfo(PhoneInfo po)throws SystemException;

	public int updateDeviceServiceActiveInfo(PhoneInfo po)throws SystemException;

	public int insertDeviceServiceActiveInfo(PhoneInfo po)throws SystemException;

}
