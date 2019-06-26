package com.wtwd.sys.innerw.wdeviceManage.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wdeviceManage.domain.WdeviceManage;





public interface WdeviceManageDao {
	
	
	public List<DataMap> getData(WdeviceManage vo) throws DataAccessException;
	
}
