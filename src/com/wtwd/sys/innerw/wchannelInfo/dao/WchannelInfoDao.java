package com.wtwd.sys.innerw.wchannelInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wchannelInfo.domain.WchannelInfo;





public interface WchannelInfoDao {
	
	
	public List<DataMap> getData(WchannelInfo vo) throws DataAccessException;
	
}
