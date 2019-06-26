package com.wtwd.sys.innerw.wdeviceManage.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wdeviceManage.dao.WdeviceManageDao;
import com.wtwd.sys.innerw.wdeviceManage.domain.WdeviceManage;





public class SqlMapWdeviceManageDao extends SqlMapClientDaoSupport implements WdeviceManageDao{
	
	Log logger = LogFactory.getLog(SqlMapWdeviceManageDao.class);



	public List<DataMap> getData(WdeviceManage vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
