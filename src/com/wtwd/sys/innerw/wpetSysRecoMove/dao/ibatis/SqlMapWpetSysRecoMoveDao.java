package com.wtwd.sys.innerw.wpetSysRecoMove.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wpetSysRecoMove.dao.WpetSysRecoMoveDao;
import com.wtwd.sys.innerw.wpetSysRecoMove.domain.WpetSysRecoMove;



public class SqlMapWpetSysRecoMoveDao extends SqlMapClientDaoSupport implements WpetSysRecoMoveDao{
	
	Log logger = LogFactory.getLog(SqlMapWpetSysRecoMoveDao.class);



	public List<DataMap> getData(WpetSysRecoMove vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
