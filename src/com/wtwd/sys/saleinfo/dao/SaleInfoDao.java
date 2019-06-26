package com.wtwd.sys.saleinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.saleinfo.domain.SaleInfo;

public interface SaleInfoDao {
	
	public List<DataMap> getSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int insertSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int updateSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public int deleteSaleInfo(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getSaleInfoListByVo(SaleInfo vo) throws DataAccessException;
	
	public int getSaleInfoCount(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getSaleInfoListGroupByProvince(SaleInfo vo) throws DataAccessException;
	
	public int getSaleInfoCountGroupByProvince(SaleInfo vo) throws DataAccessException;
	
	public List<DataMap> getAddDayGroupByTime(SaleInfo vo) throws DataAccessException;
	
	public int getCountAddDayGroupByTime(SaleInfo vo) throws DataAccessException;

	public List<DataMap> getProductInfoListByVo(SaleInfo vo)throws DataAccessException;

	public int getProductInfoCount(SaleInfo vo)throws DataAccessException;

	public int insertProductInfo(SaleInfo vo)throws DataAccessException;

	public int getMaxProductId(SaleInfo vo)throws DataAccessException;

	public int insertPhotoInfo(SaleInfo vo)throws DataAccessException;

	public List<DataMap> getProductInfo(SaleInfo vo)throws DataAccessException;

	public int deleteProductInfo(SaleInfo vo)throws DataAccessException;

	public List<DataMap> getPhotoInfo(SaleInfo vo)throws DataAccessException;

	public int deletePhotoInfo(SaleInfo vo)throws DataAccessException;

	public int updateProductInfo(SaleInfo vo)throws DataAccessException;

	public int getPayforInfoCount(SaleInfo sal)throws DataAccessException;

}
