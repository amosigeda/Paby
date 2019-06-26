package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTSoundInfoDao;
import com.wtwd.sys.innerw.liufeng.domain.WSoundInfo;

public class WSoundInfoFacadeImpl implements WSoundInfoFacade {

	private WTSoundInfoDao wtSoundInfoDao;
	public void setWtSoundInfoDao(WTSoundInfoDao wtSoundInfoDao) {
		this.wtSoundInfoDao = wtSoundInfoDao;
	}

	public List<DataMap> queryDevicePetByUserId(WSoundInfo ws)
			throws DataAccessException {
		return wtSoundInfoDao.queryDevicePetByUserId(ws);
	}

	public int insertPetSoundInfo(WSoundInfo ws) throws DataAccessException {
		return wtSoundInfoDao.insertPetSoundInfo(ws);
	}

}
