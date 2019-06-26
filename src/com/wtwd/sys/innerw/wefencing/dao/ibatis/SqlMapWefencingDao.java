package com.wtwd.sys.innerw.wefencing.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wefencing.dao.WefencingDao;
import com.wtwd.sys.innerw.wefencing.domain.Wefencing;







public class SqlMapWefencingDao extends SqlMapClientDaoSupport implements WefencingDao{
	
	Log logger = LogFactory.getLog(SqlMapWefencingDao.class);



	public List<DataMap> getData(Wefencing vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
