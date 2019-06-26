package com.wtwd.sys.innerw.wappuserverify.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wappuserverify.domain.WappuserVerify;





public interface WappuserVerifyDao {
	
	
	public List<DataMap> getData(WappuserVerify vo) throws DataAccessException;
	public Integer insertData(WappuserVerify vo) throws DataAccessException;
	public Integer delData(WappuserVerify vo) throws DataAccessException;	

	public List<DataMap> getDataLevel2(WappuserVerify vo) throws DataAccessException;
	public Integer insertDataLevel2(WappuserVerify vo) throws DataAccessException;
	public Integer delDataLevel2(WappuserVerify vo) throws DataAccessException;	
	
}