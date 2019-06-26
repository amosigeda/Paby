package com.wtwd.sys.innerw.wshareInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;



public interface WshareInfoFacade {


	public List<DataMap> getData(WshareInfo vo) throws SystemException;
	public Integer insertData(WshareInfo vo) throws SystemException;
	public Integer delData(WshareInfo vo) throws SystemException;	
	public Integer updateData(WshareInfo vo) throws SystemException;	
	public List<DataMap> queryBindList(WshareInfo vo) throws SystemException;

	public Integer insertUnshareData(WshareInfo vo) throws SystemException;
	public Integer delUnshareData(WshareInfo vo) throws SystemException;	
	public List<DataMap> queryUnbindList(WshareInfo vo) throws SystemException;

	public Integer getWdevUserCount(WshareInfo vo) throws SystemException;
	public List<DataMap> getAllUsers(WshareInfo vo) throws SystemException;
	public List<DataMap> getDevRelUser(WshareInfo vo) throws SystemException;
	public List<DataMap> getUserRelDev(WshareInfo vo) throws SystemException;
	public Integer getDevRelUserOnlineCount(WshareInfo vo) throws SystemException;
	
}
