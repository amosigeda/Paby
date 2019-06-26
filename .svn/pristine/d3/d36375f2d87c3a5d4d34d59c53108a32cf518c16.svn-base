package com.wtwd.sys.innerw.wpet.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wpet.domain.Wpet;



public interface WpetDao {
	
	
	public List<DataMap> getDogDataList(Wpet vo) throws DataAccessException;
	
	public List<DataMap> getDogDataListByDevice(Wpet vo) throws DataAccessException;
	
	public Integer getDogCount(Wpet vo) throws DataAccessException;

	public Integer insertDog(Wpet vo) throws DataAccessException;

	public Integer updateDog(Wpet vo) throws DataAccessException;

	public Integer updateDogDeviceId(Wpet vo) throws DataAccessException;
	
	public Integer delPet(Wpet vo) throws DataAccessException;
	public Integer delPetMoveInfo(Wpet vo) throws DataAccessException;
	public Integer delPetInfo(Wpet vo) throws DataAccessException;
	public Integer delPetSleepInfo(Wpet vo) throws DataAccessException;
	
}
