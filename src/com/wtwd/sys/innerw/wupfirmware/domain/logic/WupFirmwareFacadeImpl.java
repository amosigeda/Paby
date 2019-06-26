package com.wtwd.sys.innerw.wupfirmware.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wupfirmware.dao.WupFirmwareDao;
import com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware;

public class WupFirmwareFacadeImpl implements WupFirmwareFacade {
	
    private WupFirmwareDao wupFirmwareDao;
	
	public WupFirmwareFacadeImpl(){
		
	}
	
	/**
	 * @return the wupFirmwareDao
	 */
	public WupFirmwareDao getWupFirmwareDao() {
		return wupFirmwareDao;
	}


	/**
	 * @param wupFirmwareDao the wupFirmwareDao to set
	 */
	public void setWupFirmwareDao(WupFirmwareDao wupFirmwareDao) {
		this.wupFirmwareDao = wupFirmwareDao;
	}

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade#getWupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public List<DataMap> getWupFirmware(WupFirmware vo) throws SystemException {
		// TODO Auto-generated method stub
		return wupFirmwareDao.getWupFirmware(vo);
	}

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade#insertWupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public Integer insertWupFirmware(WupFirmware vo) throws SystemException {
		// TODO Auto-generated method stub
		return wupFirmwareDao.insertWupFirmware(vo);
	}

	/* (non-Javadoc)
	 * @see com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade#updateWupFirmware(com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware)
	 */
	public Integer updateWupFirmware(WupFirmware vo) throws SystemException {
		// TODO Auto-generated method stub
		return wupFirmwareDao.updateWupFirmware(vo);
	}
	
}
