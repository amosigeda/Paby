package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;

public interface AppSafeAreaManDao {
	
	public int insertAppSafeAreaMan(WeFencing wf) throws DataAccessException;
	
	public int updateAppSafeAreaMan(WeFencing wf) throws DataAccessException;
	
	public int deleteAppSafeAreaMan(WeFencing wf) throws DataAccessException;
	
	public List<DataMap> queryWeFencing(WeFencing wf) throws DataAccessException;
	
	public List<DataMap> queryMasterDeviceInfo(WeFencing wf) throws DataAccessException;

	public Integer getWsafeAreaCount(WeFencing vo)
			throws DataAccessException;

	public Integer getSafeAreaNextId(WeFencing vo)
			throws DataAccessException;
	
}
