package com.wtwd.sys.innerw.wfuncinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wfuncinfo.dao.WfuncinfoDao;
import com.wtwd.sys.innerw.wfuncinfo.domain.Wfuncinfo;





public class SqlMapWfuncinfoDao extends SqlMapClientDaoSupport implements WfuncinfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWfuncinfoDao.class);



	public List<DataMap> getData(Wfuncinfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
