package com.wtwd.sys.innerw.wpetSysRecoMove.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpetSysRecoMove.domain.WpetSysRecoMove;




public interface WpetSysRecoMoveDao {
	
	
	public List<DataMap> getData(WpetSysRecoMove vo) throws DataAccessException;
	
}
