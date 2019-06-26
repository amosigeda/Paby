package com.wtwd.sys.innerw.wappuserverify.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wappuserverify.domain.WappuserVerify;



public interface WappuserVerifyFacade {


	public List<DataMap> getData(WappuserVerify vo) throws SystemException;
	public Integer insertData(WappuserVerify vo) throws SystemException;
	public Integer delData(WappuserVerify vo) throws SystemException;	
	
	public List<DataMap> getDataLevel2(WappuserVerify vo) throws SystemException;
	public Integer insertDataLevel2(WappuserVerify vo) throws SystemException;
	public Integer delDataLevel2(WappuserVerify vo) throws SystemException;	

}
