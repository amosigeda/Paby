package com.wtwd.sys.innerw.wvisit.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wvisit.dao.WvisitDao;
import com.wtwd.sys.innerw.wvisit.domain.Wvisit;





public class SqlMapWvisitDao extends SqlMapClientDaoSupport implements WvisitDao{
	
	Log logger = LogFactory.getLog(SqlMapWvisitDao.class);



	public List<DataMap> getData(Wvisit vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
