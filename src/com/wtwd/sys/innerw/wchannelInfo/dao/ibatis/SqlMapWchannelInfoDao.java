package com.wtwd.sys.innerw.wchannelInfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wchannelInfo.dao.WchannelInfoDao;
import com.wtwd.sys.innerw.wchannelInfo.domain.WchannelInfo;





public class SqlMapWchannelInfoDao extends SqlMapClientDaoSupport implements WchannelInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapWchannelInfoDao.class);



	public List<DataMap> getData(WchannelInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}






}
