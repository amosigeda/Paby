package com.wtwd.sys.innerw.liufeng.dao;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public interface WTDevSetaDao {
	
	public int updateDevSeta(WdeviceActiveInfo wa) throws DataAccessException;
	
}
