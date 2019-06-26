package com.wtwd.sys.innerw.wpetSysReco.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetSysReco.dao.WpetSysRecoDao;
import com.wtwd.sys.innerw.wpetSysReco.domain.WpetSysReco;



public class SqlMapWpetSysRecoDao extends SqlMapClientDaoSupport implements WpetSysRecoDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetSysRecoDao.class);



	public List<DataMap> getData(WpetSysReco vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
