package com.wtwd.sys.deviceLogin.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;

public interface DeviceLoginDao {

public Integer getDeviceLoginCount(DeviceLogin vo) throws DataAccessException;
	
	public List<DataMap> getDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public List<DataMap> getDataDeviceLoginListByVo(DeviceLogin vo) throws DataAccessException;

	public int updateDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public int insertDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public int deleteDeviceLogin(DeviceLogin vo) throws DataAccessException;
	
	public List<DataMap> getDeviceLoginGroupByProject(DeviceLogin vo) throws DataAccessException;
	
	public int getDeviceLoginCountGroupByProject(DeviceLogin vo) throws DataAccessException;
}
