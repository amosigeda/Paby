package com.wtwd.sys.innerw.wroleFuncInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wroleFuncInfo.dao.WroleFuncInfoDao;
import com.wtwd.sys.innerw.wroleFuncInfo.domain.WroleFuncInfo;





public class SqlMapWroleFuncInfoDao extends SqlMapClientDaoSupport implements WroleFuncInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWroleFuncInfoDao.class);



	public List<DataMap> getData(WroleFuncInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
