package com.wtwd.sys.saleinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.saleinfo.dao.SaleInfoDao;
import com.wtwd.sys.saleinfo.domain.SaleInfo;

public class SqlMapSaleInfoDao extends SqlMapClientDaoSupport implements SaleInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapSaleInfoDao.class);

	public int deleteSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("deleteSaleInfo", vo);
	}

	public List<DataMap> getSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfo", vo);
	}

	public int getSaleInfoCount(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoCount(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSaleInfoCount", vo);
	}

	public List<DataMap> getSaleInfoListByVo(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoListByVo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfoListByVo", vo);
	}

	public int insertSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("insertSaleInfo", vo);
	}

	public int updateSaleInfo(SaleInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateSaleInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("updateSaleInfo", vo);
	}

	public int getSaleInfoCountGroupByProvince(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoCountGroupByProvince(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getSaleInfoCountGroupByProvince", vo);
	}

	public List<DataMap> getSaleInfoListGroupByProvince(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getSaleInfoListGroupByProvince(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSaleInfoListGroupByProvince", vo);
	}

	public List<DataMap> getAddDayGroupByTime(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getAddDayGroupByTime(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAddDayGroupByTime", vo);
	}

	public int getCountAddDayGroupByTime(SaleInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getCountAddDayGroupByTime(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getCountAddDayGroupByTime", vo);
	}

	public List<DataMap> getProductInfoListByVo(SaleInfo vo)
			throws DataAccessException {
		logger.debug("getProductInfoListByVo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getProductInfoListByVo", vo);
	}

	public int getProductInfoCount(SaleInfo vo) throws DataAccessException {
		return (Integer)getSqlMapClientTemplate().queryForObject("getProductInfoCount", vo);
	}

	public int insertProductInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("insertProductInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("insertProductInfo", vo);
	}

	public int getMaxProductId(SaleInfo vo) throws DataAccessException {
		logger.debug("getMaxProductId(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getMaxProductId", vo);
	}

	public int insertPhotoInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("insertPhotoInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("insertPhotoInfo", vo);
	
	}

	public List<DataMap> getProductInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("getProductInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getProductInfo", vo);
	}

	public int deleteProductInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("deleteProductInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("deleteProductInfo", vo);
	}

	public List<DataMap> getPhotoInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("getPhotoInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPhotoInfo", vo);
	}

	public int deletePhotoInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("deletePhotoInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("deletePhotoInfo", vo);
	}

	public int updateProductInfo(SaleInfo vo) throws DataAccessException {
		logger.debug("updateProductInfo(SaleInfo vo)");
		return getSqlMapClientTemplate().update("updateProductInfo", vo);
	}

	public int getPayforInfoCount(SaleInfo sal) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getPayforInfoCount(SaleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getPayforInfoCount", sal);
	}
}
