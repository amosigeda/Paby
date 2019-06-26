package com.wtwd.sys.projectinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.projectinfo.dao.ProjectInfoDao;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public class ProjectInfoFacadeImpl implements ProjectInfoFacade{
	
	private ProjectInfoDao projectInfoDao;

	public ProjectInfoFacadeImpl(){
		
	}
	
	public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
		this.projectInfoDao = projectInfoDao;
	}

	public List<DataMap> getProjectInfo(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfo(vo);
	}

	public DataList getProjectInfoListByVo(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(projectInfoDao.getProjectInfoListByVo(vo));
		list.setTotalSize(projectInfoDao.getProjectInfoListCountByVo(vo));
		return list;
	}

	public int getProjectInfoListCountByVo(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoListCountByVo(vo);
	}

	public int getProjectInfoMaxId(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoMaxId(vo);
	}

	public int insertProjectInfo(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.insertProjectInfo(vo);
	}

	public int insertRelevanceInfo2(ProjectInfo vo) throws SystemException {
		return projectInfoDao.insertRelevanceInfo2(vo);
	}

	public int getProjectInfoCount(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoCount(vo);
	}

}
