package com.wtwd.sys.innerw.wuserinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wuserinfo.dao.WuserInfoDao;
import com.wtwd.sys.innerw.wuserinfo.domain.WuserInfo;





public class SqlMapWuserInfoDao extends SqlMapClientDaoSupport implements WuserInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWuserInfoDao.class);



	public List<DataMap> getData(WuserInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
