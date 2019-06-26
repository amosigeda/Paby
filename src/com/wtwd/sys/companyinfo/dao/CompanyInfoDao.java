package com.wtwd.sys.companyinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.companyinfo.domain.CompanyInfo;

public interface CompanyInfoDao {
	
	public List<DataMap> getCompanyInfo(CompanyInfo vo) throws DataAccessException;
	
	public List<DataMap> getCompanyInfoListByVo(CompanyInfo vo) throws DataAccessException;
	
	public int getCompanyInfoListCountByVo(CompanyInfo vo) throws DataAccessException;
	
	public int insertCompanyInfo(CompanyInfo vo) throws DataAccessException;
	
	public Integer getCompanyInfoMaxId(CompanyInfo vo) throws DataAccessException;
	
	public int getCompanyInfoCount(CompanyInfo vo) throws DataAccessException;
	
	public int insertRelevanceInfo(CompanyInfo vo) throws DataAccessException;
	
	public List<DataMap> getRelevanceInfo(CompanyInfo vo) throws DataAccessException;

}