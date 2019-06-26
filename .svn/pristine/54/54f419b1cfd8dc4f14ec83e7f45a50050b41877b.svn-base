package com.wtwd.sys.saleinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.saleinfo.dao.SaleInfoDao;
import com.wtwd.sys.saleinfo.domain.SaleInfo;

public class SaleInfoFacadeImpl implements SaleInfoFacade{
	
	private SaleInfoDao saleInfoDao;
	
	public SaleInfoFacadeImpl(){
		
	}

	public void setSaleInfoDao(SaleInfoDao saleInfoDao) {
		this.saleInfoDao = saleInfoDao;
	}

	public int deleteSaleInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.deleteSaleInfo(vo);
	}

	public List<DataMap> getSaleInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getSaleInfo(vo);
	}

	public int getSaleInfoCount(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getSaleInfoCount(vo);
	}

	public DataList getSaleInfoListByVo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(saleInfoDao.getSaleInfoListByVo(vo));
		list.setTotalSize(saleInfoDao.getSaleInfoCount(vo));
		return list;
	}

	public int insertSaleInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.insertSaleInfo(vo);
	}

	public int updateSaleInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.updateSaleInfo(vo);
	}

	public int getSaleInfoCountGroupByProvince(SaleInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getSaleInfoCountGroupByProvince(vo);
	}

	public DataList getSaleInfoListGroupByProvince(SaleInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(saleInfoDao.getSaleInfoListGroupByProvince(vo));
		list.setTotalSize(saleInfoDao.getSaleInfoCountGroupByProvince(vo));
		return list;
	}

	public DataList getAddDayGroupByTime(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(saleInfoDao.getAddDayGroupByTime(vo));
		list.setTotalSize(saleInfoDao.getCountAddDayGroupByTime(vo));
		return list;
	}

	public int getCountAddDayGroupByTime(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getCountAddDayGroupByTime(vo);
	}

	public DataList getDataProductInfoListByVo(SaleInfo vo)throws SystemException {
	DataList list = new DataList(saleInfoDao.getProductInfoListByVo(vo));
	list.setTotalSize(saleInfoDao.getProductInfoCount(vo));
	return list;
	}

	public int insertProductInfo(SaleInfo vo) throws SystemException {
		return saleInfoDao.insertProductInfo(vo);
	}

	public int getMaxProductId(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getMaxProductId(vo);
	}

	public int insertPhotoInfo(SaleInfo vo) throws SystemException {
		return saleInfoDao.insertPhotoInfo(vo);
	}

	public List<DataMap> getProductInfo(SaleInfo vo) throws SystemException {
		return saleInfoDao.getProductInfo(vo);
	}

	public int deleteProductInfo(SaleInfo vo) throws SystemException {
		return saleInfoDao.deleteProductInfo(vo);
	}

	public List<DataMap> getPhotoInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getPhotoInfo(vo);
	}

	public int deletePhotoInfo(SaleInfo vo) throws SystemException {
		return saleInfoDao.deletePhotoInfo(vo);
	}

	public int updateProductInfo(SaleInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.updateProductInfo(vo);
	}

	public int getPayforInfoCount(SaleInfo sal) throws SystemException {
		// TODO Auto-generated method stub
		return saleInfoDao.getPayforInfoCount(sal);
	}

}
