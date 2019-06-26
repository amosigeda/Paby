package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTCheckVersionDao;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;

public class WTCheckVersionFacadeImpl implements WTCheckVersionFacade {

	private WTCheckVersionDao wtCheckVersionDao;
	public void setWtCheckVersionDao(WTCheckVersionDao wtCheckVersionDao) {
		this.wtCheckVersionDao = wtCheckVersionDao;
	}

	public List<DataMap> queryCheckVersionInfo(WcheckInfo wi)
			throws DataAccessException {
		return wtCheckVersionDao.queryCheckVersionInfo(wi);
	}

}
