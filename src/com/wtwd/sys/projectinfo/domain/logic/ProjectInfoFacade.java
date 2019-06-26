package com.wtwd.sys.projectinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public interface ProjectInfoFacade {
	
	public List<DataMap> getProjectInfo(ProjectInfo vo) throws SystemException;
	
	public DataList getProjectInfoListByVo(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoListCountByVo(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoMaxId(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoCount(ProjectInfo vo) throws SystemException;
	
	public int insertProjectInfo(ProjectInfo vo) throws SystemException;

	public int insertRelevanceInfo2(ProjectInfo vo)throws SystemException;


}
