package com.wtwd.sys.innerw.wpetwifirange.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetwifirange.domain.WpetWifiRange;


public interface WpetWifiRangeDao {
	
	public List<DataMap> getWpetWifiRange(WpetWifiRange vo) throws DataAccessException;
	
	public int insertWpetWifiRange(WpetWifiRange vo) throws DataAccessException;
	
	public int updateWpetWifiRange(WpetWifiRange vo) throws DataAccessException;
	
}