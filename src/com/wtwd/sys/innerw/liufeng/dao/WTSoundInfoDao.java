package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WSoundInfo;

public interface WTSoundInfoDao {
	
	public List<DataMap> queryDevicePetByUserId(WSoundInfo ws) throws DataAccessException;
	
	public int insertPetSoundInfo(WSoundInfo ws) throws DataAccessException;
	
}
