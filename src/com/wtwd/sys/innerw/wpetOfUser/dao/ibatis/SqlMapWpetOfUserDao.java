package com.wtwd.sys.innerw.wpetOfUser.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetOfUser.dao.WpetOfUserDao;
import com.wtwd.sys.innerw.wpetOfUser.domain.WpetOfUser;





public class SqlMapWpetOfUserDao extends SqlMapClientDaoSupport implements WpetOfUserDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetOfUserDao.class);



	public List<DataMap> getData(WpetOfUser vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}