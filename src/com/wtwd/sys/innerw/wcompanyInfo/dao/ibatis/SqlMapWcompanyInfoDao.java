package com.wtwd.sys.innerw.wcompanyInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wcompanyInfo.dao.WcompanyInfoDao;
import com.wtwd.sys.innerw.wcompanyInfo.domain.WcompanyInfo;





public class SqlMapWcompanyInfoDao extends SqlMapClientDaoSupport implements WcompanyInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWcompanyInfoDao.class);



	public List<DataMap> getData(WcompanyInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
