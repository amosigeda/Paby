package com.wtwd.sys.innerw.wpetwifirange.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetwifirange.dao.WpetWifiRangeDao;
import com.wtwd.sys.innerw.wpetwifirange.domain.WpetWifiRange;


public class WpetWifiRangeFacadeImpl implements WpetWifiRangeFacade {

	private WpetWifiRangeDao wpetWifiRangeDao;

	public WpetWifiRangeDao getWpetWifiRangeDao() {
		return wpetWifiRangeDao;
	}

	public void setWpetWifiRangeDao(WpetWifiRangeDao wpetWifiRangeDao) {
		this.wpetWifiRangeDao = wpetWifiRangeDao;
	}

	public List<DataMap> getWpetWifiRange(WpetWifiRange vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return wpetWifiRangeDao.getWpetWifiRange(vo);
	}

	public Integer insertWpetWifiRange(WpetWifiRange vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetWifiRangeDao.insertWpetWifiRange(vo);
	}

	public Integer updateWpetWifiRange(WpetWifiRange vo) throws SystemException {
		// TODO Auto-generated method stub
		return wpetWifiRangeDao.updateWpetWifiRange(vo);
	}
}
