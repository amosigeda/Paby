package com.wtwd.sys.innerw.wrpPetMove.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wrpPetMove.dao.WrpPetMoveDao;
import com.wtwd.sys.innerw.wrpPetMove.domain.WrpPetMove;





public class SqlMapWrpPetMoveDao extends SqlMapClientDaoSupport implements WrpPetMoveDao{
	
	Log logger = LogFactory.getLog(SqlMapWrpPetMoveDao.class);



	public List<DataMap> getData(WrpPetMove vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}