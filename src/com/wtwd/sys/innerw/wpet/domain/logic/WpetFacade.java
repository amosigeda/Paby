﻿package com.wtwd.sys.innerw.wpet.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpet.domain.Wpet;


public interface WpetFacade {


	public List<DataMap> getDogDataList(Wpet vo) throws SystemException;

	public List<DataMap> getDogDataListByDevice(Wpet vo) throws SystemException;

	public Integer getDogCount(Wpet vo) throws SystemException;	

	public Integer insertDog(Wpet vo) throws SystemException;

	public Integer updateDog(Wpet vo) throws SystemException;

	public Integer updateDogDeviceId(Wpet vo) throws SystemException;
	
	public Integer delPet(Wpet vo) throws SystemException;
	public Integer delPetInfo(Wpet vo) throws SystemException;
	public Integer delPetMoveInfo(Wpet vo) throws SystemException;
	public Integer delPetSleepInfo(Wpet vo) throws SystemException;
	
}