package com.wtwd.sys.innerw.wpetSysReco.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetSysReco.domain.WpetSysReco;




public interface WpetSysRecoDao {
	
	
	public List<DataMap> getData(WpetSysReco vo) throws DataAccessException;
	
}
