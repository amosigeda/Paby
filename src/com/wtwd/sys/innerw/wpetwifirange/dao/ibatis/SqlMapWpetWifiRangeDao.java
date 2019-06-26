package com.wtwd.sys.innerw.wpetwifirange.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetwifirange.dao.WpetWifiRangeDao;
import com.wtwd.sys.innerw.wpetwifirange.domain.WpetWifiRange;

public class SqlMapWpetWifiRangeDao extends SqlMapClientDaoSupport implements WpetWifiRangeDao {
	
	Log logger = LogFactory.getLog(SqlMapWpetWifiRangeDao.class);

	public SqlMapWpetWifiRangeDao() {

	}

	public List<DataMap> getWpetWifiRange(WpetWifiRange vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getWpetWifiRange", vo);
	}

	public int insertWpetWifiRange(WpetWifiRange vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("insertWpetWifiRange", vo);
	}

	public int updateWpetWifiRange(WpetWifiRange vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateWpetWifiRange", vo);
	}

}
