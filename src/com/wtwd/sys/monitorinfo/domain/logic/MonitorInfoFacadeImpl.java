package com.wtwd.sys.monitorinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.monitorinfo.dao.MonitorInfoDao;
import com.wtwd.sys.monitorinfo.domain.MonitorInfo;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class MonitorInfoFacadeImpl implements MonitorInfoFacade {

	private MonitorInfoDao monitorInfoDao;
	private static Integer mcount = 0;

	
	public MonitorInfoFacadeImpl() {
	}

	public void setMonitorInfoDao(MonitorInfoDao monitorInfoDao) {
		this.monitorInfoDao = monitorInfoDao;
	}

	public List<DataMap> getMonitorInfo(MonitorInfo vo) throws SystemException {
		return monitorInfoDao.getMonitorInfo(vo);
	}
	
	public DataList getDataMonitorInfoListByVo(MonitorInfo vo)
			throws SystemException {
		DataList list = new DataList(monitorInfoDao.getMonitorInfoListByVo(vo));
		list.setTotalSize(monitorInfoDao.getMonitorInfoCount(vo));
		return list;
	}

	public int updateMonitorInfo(MonitorInfo vo) throws SystemException {
		return monitorInfoDao.updateMonitorInfo(vo);
	}

	public int insertMonitorInfo(MonitorInfo vo) throws SystemException {
		if ( Constant.STAT_SYS_DEBUG && (mcount % 30000) == 0 && mcount > 0 ) {
			monitorInfoDao.resetMonitorInfo(vo);
			mcount = 0;
		} else
			mcount++;
		
		
		return monitorInfoDao.insertMonitorInfo(vo);
	}

	public int insertWMonitor(MonitorInfo vo) throws SystemException {
		
		return monitorInfoDao.insertWMonitor(vo);
	}
	
	
	public List<DataMap> getVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.getVisitInfo(vo);
	}

	public DataList getVisitInfoListByVo(MonitorInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(monitorInfoDao.getVisitInfoListByVo(vo));
		list.setTotalSize(monitorInfoDao.getVisitInfoCount(vo));
		return list;
	}

	public int updateVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.updateVisitInfo(vo);
	}

	public int insertVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.insertVisitInfo(vo);
	}

	public List<DataMap> getSwitchInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.getSwitchInfo(vo);
	}

	public int updateSwitchInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.updateSwitchInfo(vo);
	}

	public int resetMonitorInfo(MonitorInfo vo)throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.resetMonitorInfo(vo);
	}
	
	
}
