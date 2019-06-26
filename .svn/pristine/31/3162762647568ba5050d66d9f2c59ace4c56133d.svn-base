package com.wtwd.sys.saleinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.saleinfo.domain.SaleInfo;

public interface SaleInfoFacade {
	
	public List<DataMap> getSaleInfo(SaleInfo vo) throws SystemException;
	
	public int insertSaleInfo(SaleInfo vo) throws SystemException;
	
	public int updateSaleInfo(SaleInfo vo) throws SystemException;
	
	public int deleteSaleInfo(SaleInfo vo) throws SystemException;
	
	public DataList getSaleInfoListByVo(SaleInfo vo) throws SystemException;
	
	public int getSaleInfoCount(SaleInfo vo) throws SystemException;
	
	public DataList getSaleInfoListGroupByProvince(SaleInfo vo) throws SystemException;
	
	public int getSaleInfoCountGroupByProvince(SaleInfo vo) throws SystemException;
	
	public DataList getAddDayGroupByTime(SaleInfo vo) throws SystemException;
	
	public int getCountAddDayGroupByTime(SaleInfo vo) throws SystemException;

	public DataList getDataProductInfoListByVo(SaleInfo vo)throws SystemException;

	public int insertProductInfo(SaleInfo vo)throws SystemException;

	public int getMaxProductId(SaleInfo vo)throws SystemException;

	public int insertPhotoInfo(SaleInfo vo)throws SystemException;

	public List<DataMap> getProductInfo(SaleInfo vo)throws SystemException;

	public int deleteProductInfo(SaleInfo vo)throws SystemException;

	public List<DataMap> getPhotoInfo(SaleInfo vo)throws SystemException;

	public int deletePhotoInfo(SaleInfo vo)throws SystemException;

	public int updateProductInfo(SaleInfo vo)throws SystemException;

	public int getPayforInfoCount(SaleInfo sal)throws SystemException;


}
