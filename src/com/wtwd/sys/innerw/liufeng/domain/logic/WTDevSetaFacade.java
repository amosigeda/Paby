package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public interface WTDevSetaFacade {
	
	public int updateDevSeta(WdeviceActiveInfo wa) throws DataAccessException;
	
}
