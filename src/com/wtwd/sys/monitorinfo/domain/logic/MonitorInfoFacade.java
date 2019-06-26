package com.wtwd.sys.monitorinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.monitorinfo.domain.MonitorInfo;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface MonitorInfoFacade {

	public List<DataMap> getMonitorInfo(MonitorInfo vo) throws SystemException;

	public DataList getDataMonitorInfoListByVo(MonitorInfo vo) throws SystemException;

	public int updateMonitorInfo(MonitorInfo vo) throws SystemException;

	public int insertMonitorInfo(MonitorInfo vo) throws SystemException;

	public int insertWMonitor(MonitorInfo vo) throws SystemException;
	
	public List<DataMap> getVisitInfo(MonitorInfo vo) throws SystemException;

	public DataList getVisitInfoListByVo(MonitorInfo vo) throws SystemException;

	public int updateVisitInfo(MonitorInfo vo) throws SystemException;

	public int insertVisitInfo(MonitorInfo vo) throws SystemException;
	
	public List<DataMap> getSwitchInfo(MonitorInfo vo)throws SystemException;
	
	public int updateSwitchInfo(MonitorInfo vo) throws SystemException;

	public int resetMonitorInfo(MonitorInfo vo)throws SystemException;
	
}
