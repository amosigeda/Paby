package com.wtwd.sys.innerw.wroleInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wroleInfo.dao.WroleInfoDao;
import com.wtwd.sys.innerw.wroleInfo.domain.WroleInfo;





public class SqlMapWroleInfoDao extends SqlMapClientDaoSupport implements WroleInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWroleInfoDao.class);



	public List<DataMap> getData(WroleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}