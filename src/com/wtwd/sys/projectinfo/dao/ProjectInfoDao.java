package com.wtwd.sys.projectinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public interface ProjectInfoDao {
	
	public List<DataMap> getProjectInfo(ProjectInfo vo) throws DataAccessException;
	
	public List<DataMap> getProjectInfoListByVo(ProjectInfo vo) throws DataAccessException;
	
	public int getProjectInfoListCountByVo(ProjectInfo vo) throws DataAccessException;
	
	public int getProjectInfoMaxId(ProjectInfo vo) throws DataAccessException;
	
	public int getProjectInfoCount(ProjectInfo vo)throws DataAccessException;
	
	public int insertProjectInfo(ProjectInfo vo) throws DataAccessException;

	public int insertRelevanceInfo2(ProjectInfo vo)throws DataAccessException;

}
