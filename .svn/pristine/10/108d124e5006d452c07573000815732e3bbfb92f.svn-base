package com.wtwd.sys.projectinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.projectinfo.dao.ProjectInfoDao;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public class SqlMapProjectInfoDao extends SqlMapClientDaoSupport implements ProjectInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapProjectInfoDao.class);
	
	public List<DataMap> getProjectInfo(ProjectInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getProjectInfo(ProjectInfo vo)");
		return getSqlMapClientTemplate().queryForList("getProjectInfo", vo);
	}

	public List<DataMap> getProjectInfoListByVo(ProjectInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getProjectInfoListByVo(ProjectInfo vo)");
		return getSqlMapClientTemplate().queryForList("getProjectInfoListByVo", vo);
	}

	public int getProjectInfoListCountByVo(ProjectInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getProjectInfoListCountByVo(ProjectInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getProjectInfoListCountByVo", vo);
	}

	public int getProjectInfoMaxId(ProjectInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getProjectInfoMaxId(ProjectInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getProjectInfoMaxId", vo);
	}

	public int insertProjectInfo(ProjectInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertProjectInfo(ProjectInfo vo)");
		return getSqlMapClientTemplate().update("insertProjectInfo", vo);
	}

	public int insertRelevanceInfo2(ProjectInfo vo) throws DataAccessException {
		logger.debug("insertRelevanceInfo2(ProjectInfo vo)");
		return getSqlMapClientTemplate().update("insertRelevanceInfo2", vo);
	}

	public int getProjectInfoCount(ProjectInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getProjectInfoCount(ProjectInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getProjectInfoCount", vo);
	}

}
