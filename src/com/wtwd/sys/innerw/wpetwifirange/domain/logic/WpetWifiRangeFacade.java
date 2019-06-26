package com.wtwd.sys.innerw.wpetwifirange.domain.logic;
import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetwifirange.domain.WpetWifiRange;



public interface WpetWifiRangeFacade {

	public List<DataMap> getWpetWifiRange(WpetWifiRange vo) throws SystemException;
	
	public Integer insertWpetWifiRange(WpetWifiRange vo) throws SystemException;
	
	public Integer updateWpetWifiRange(WpetWifiRange vo) throws SystemException; 
}
