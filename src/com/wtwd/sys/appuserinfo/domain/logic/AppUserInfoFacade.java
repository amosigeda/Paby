package com.wtwd.sys.appuserinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;

public interface AppUserInfoFacade {

	public List<DataMap> getAppUserInfo(AppUserInfo vo) throws SystemException;

	public Integer insertAppUserInfo(AppUserInfo vo) throws SystemException;

	public Integer updateAppUserInfo(AppUserInfo vo) throws SystemException;

	public Integer getAppUserInfoCount(AppUserInfo vo) throws SystemException;
	
	public DataList getAppUserInfoListByVo(AppUserInfo vo) throws SystemException;
	
	public DataList getAppUserInfoGroupByTime(AppUserInfo vo) throws SystemException;
	
	public Integer getAppUserInfoCountGroupByTime(AppUserInfo vo) throws SystemException;
}
