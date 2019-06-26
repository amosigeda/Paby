package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.dao.WTDevSetaDao;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

public class WTDevSetaFacadeImpl implements WTDevSetaFacade {

	private WTDevSetaDao wtDevSetaDao;
	public void setWtDevSetaDao(WTDevSetaDao wtDevSetaDao) {
		this.wtDevSetaDao = wtDevSetaDao;
	}

	public int updateDevSeta(WdeviceActiveInfo wa)
			throws DataAccessException {
		return wtDevSetaDao.updateDevSeta(wa);
	}

}
