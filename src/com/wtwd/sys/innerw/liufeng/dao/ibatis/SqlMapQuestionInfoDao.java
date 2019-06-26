package com.wtwd.sys.innerw.liufeng.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTAppQuestionInfoManDao;
import com.wtwd.sys.innerw.liufeng.domain.QuestionInfo;

public class SqlMapQuestionInfoDao extends SqlMapClientDaoSupport implements WTAppQuestionInfoManDao {

	private Log log = LogFactory.getLog(SqlMapQuestionInfoDao.class);

	public List<DataMap> queryAllQuestionList(QuestionInfo qi)
			throws DataAccessException {
		log.debug("SqlMapQuestionInfoDao-->queryAllQuestionList");
		return getSqlMapClientTemplate().queryForList("getAllQuestionList", qi);
	}

//	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException {
//		log.debug("SqlMapAppMsgManDao-->queryByUserIdMsgCount");
//		return (Integer) getSqlMapClientTemplate().queryForObject("queryByUserIdMsgCount", wm);
//	}
//
//	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException {
//		log.debug("updateByUserIdMsgInfo");
//		return (Integer) getSqlMapClientTemplate().update("updateByUserIdMsgInfo", wm);
//	}
//
//	public int insertData(WMsgInfo wm) throws DataAccessException {
//		log.debug("insertData");
//		return (Integer) getSqlMapClientTemplate().update("insertData", wm);
//	}
	
	
}
