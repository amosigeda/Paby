package com.wtwd.sys.innerw.wpetOfUser.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetOfUser.domain.WpetOfUser;





public interface WpetOfUserDao {
	
	
	public List<DataMap> getData(WpetOfUser vo) throws DataAccessException;
	
}
