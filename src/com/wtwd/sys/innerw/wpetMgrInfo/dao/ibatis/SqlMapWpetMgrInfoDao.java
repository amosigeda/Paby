package com.wtwd.sys.innerw.wpetMgrInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpetMgrInfo.dao.WpetMgrInfoDao;



public class SqlMapWpetMgrInfoDao extends SqlMapClientDaoSupport implements WpetMgrInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetMgrInfoDao.class);



	public List<DataMap> getDataById(Wpet vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}



}
