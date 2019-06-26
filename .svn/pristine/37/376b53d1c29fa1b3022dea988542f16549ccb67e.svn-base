package com.wtwd.sys.companyinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.companyinfo.dao.CompanyInfoDao;
import com.wtwd.sys.companyinfo.domain.CompanyInfo;

public class CompanyInfoFacadeImpl implements CompanyInfoFacade {
	
	private CompanyInfoDao companyInfoDao;
	
	public CompanyInfoFacadeImpl(){
		
	}

	public void setCompanyInfoDao(CompanyInfoDao companyInfoDao) {
		this.companyInfoDao = companyInfoDao;
	}

	public List<DataMap> getCompanyInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfo(vo);
	}

	public DataList getCompanyInfoListByVo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(companyInfoDao.getCompanyInfoListByVo(vo));
		list.setTotalSize(companyInfoDao.getCompanyInfoListCountByVo(vo));
		return list;
	}

	public int getCompanyInfoListCountByVo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoListCountByVo(vo);
	}

	public int insertCompanyInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.insertCompanyInfo(vo);
	}

	public Integer getCompanyInfoMaxId(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoMaxId(vo);
	}

	public List<DataMap> getRelevanceInfo(CompanyInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getRelevanceInfo(vo);
	}

	public int insertRelevanceInfo(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.insertRelevanceInfo(vo);
	}

	public int getCompanyInfoCount(CompanyInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return companyInfoDao.getCompanyInfoCount(vo);
	}

}
