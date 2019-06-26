package com.wtwd.sys.innerw.wfciPetKind.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wfciPetKind.dao.WfciPetKindDao;
import com.wtwd.sys.innerw.wfciPetKind.domain.WfciPetKind;





public class SqlMapWfciPetKindDao extends SqlMapClientDaoSupport implements WfciPetKindDao{
	
	Log logger = LogFactory.getLog(SqlMapWfciPetKindDao.class);



	public List<DataMap> getData(WfciPetKind vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getWfciPetKindData", vo);
	}






}
