package com.wtwd.sys.innerw.wpetInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetInfo.dao.WpetInfoDao;
import com.wtwd.sys.innerw.wpetInfo.domain.WpetInfo;





public class SqlMapWpetInfoDao extends SqlMapClientDaoSupport implements WpetInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetInfoDao.class);



	public List<DataMap> getData(WpetInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getWpetInfoData", vo);
	}






}