package com.wtwd.sys.innerw.wcheckInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wcheckInfo.dao.WcheckInfoDao;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;





public class SqlMapWcheckInfoDao extends SqlMapClientDaoSupport implements WcheckInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWcheckInfoDao.class);



	public List<DataMap> getData(WcheckInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
