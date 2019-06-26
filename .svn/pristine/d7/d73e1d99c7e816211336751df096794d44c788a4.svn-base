package com.wtwd.sys.innerw.wupfirmware.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.sys.innerw.wupfirmware.dao.WupFirmwareDao;
import com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware;

public class SqlMapWupFirmwareDao extends SqlMapClientDaoSupport implements WupFirmwareDao {
	
	Log logger = LogFactory.getLog(SqlMapWupFirmwareDao.class);

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.dao.WupFirmwareDao#getupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public List<DataMap> getWupFirmware(WupFirmware vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("List<DataMap> getWupFirmware(WupFirmware vo)");
		return getSqlMapClientTemplate().queryForList("getWupFirmware", vo);
	}

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.dao.WupFirmwareDao#insertupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public Integer insertWupFirmware(WupFirmware vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("Integer insertWupFirmware(WupFirmware vo)");
		return getSqlMapClientTemplate().update("insertWupFirmware", vo);
	}

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.dao.WupFirmwareDao#updateupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public Integer updateWupFirmware(WupFirmware vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("Integer updateWupFirmware(WupFirmware vo)");
		return getSqlMapClientTemplate().update("updateWupFirmware", vo);
	}
}
