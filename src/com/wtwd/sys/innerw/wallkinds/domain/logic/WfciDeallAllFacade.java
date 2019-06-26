package com.wtwd.sys.innerw.wallkinds.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wallkinds.domain.WfciDetailAll;

public interface WfciDeallAllFacade {

	public List<DataMap> getDatas() throws SystemException;

	public Integer insertRec(WfciDetailAll vo) throws SystemException;

	public Integer updateRec(WfciDetailAll vo) throws SystemException;

	public Integer getDataCount(WfciDetailAll vo) throws SystemException;
	
	public DataList getDataGroupByTime(WfciDetailAll vo) throws SystemException;
	
}
