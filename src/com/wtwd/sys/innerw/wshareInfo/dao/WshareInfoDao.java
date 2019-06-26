package com.wtwd.sys.innerw.wshareInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;





public interface WshareInfoDao {
	
	
	public List<DataMap> getData(WshareInfo vo) throws DataAccessException;
	public List<DataMap> queryBindList(WshareInfo vo) throws DataAccessException;
	public Integer insertData(WshareInfo vo) throws DataAccessException;
	public Integer delData(WshareInfo vo) throws DataAccessException;	
	public Integer updateData(WshareInfo vo) throws DataAccessException;	

	public Integer insertUnshareData(WshareInfo vo) throws DataAccessException;
	public Integer delUnshareData(WshareInfo vo) throws DataAccessException;	
	public List<DataMap> queryUnbindList(WshareInfo vo) throws DataAccessException;

	public Integer getWdevUserCount(WshareInfo vo) throws DataAccessException;
	public List<DataMap> getAllUsers(WshareInfo vo) throws DataAccessException;
	public List<DataMap> getDevRelUser(WshareInfo vo) throws DataAccessException;
	
	public List<DataMap> getUserRelDev(WshareInfo vo) throws DataAccessException;
	public Integer getDevRelUserOnlineCount(WshareInfo vo) throws DataAccessException;
	
	
}
