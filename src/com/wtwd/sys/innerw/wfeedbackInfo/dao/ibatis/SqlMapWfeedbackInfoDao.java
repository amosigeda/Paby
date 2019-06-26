package com.wtwd.sys.innerw.wfeedbackInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wfeedbackInfo.dao.WfeedbackInfoDao;
import com.wtwd.sys.innerw.wfeedbackInfo.domain.WfeedbackInfo;





public class SqlMapWfeedbackInfoDao extends SqlMapClientDaoSupport implements WfeedbackInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWfeedbackInfoDao.class);



	public List<DataMap> getData(WfeedbackInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
