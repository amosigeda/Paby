package com.wtwd.sys.innerw.wprojectInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wprojectInfo.dao.WprojectInfoDao;
import com.wtwd.sys.innerw.wprojectInfo.domain.WprojectInfo;





public class SqlMapWprojectInfoDao extends SqlMapClientDaoSupport implements WprojectInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWprojectInfoDao.class);



	public List<DataMap> getData(WprojectInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}