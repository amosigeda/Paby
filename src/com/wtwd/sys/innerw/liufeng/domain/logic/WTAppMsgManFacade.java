package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;

public interface WTAppMsgManFacade {
	
	public List<DataMap> queryByUserIdMsgInfo(WMsgInfo wi) throws DataAccessException;
	
	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException;

	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException;

	public int insertData(WMsgInfo wm) throws DataAccessException;

	
	public int resetMsgInfo(WMsgInfo wm) throws DataAccessException;

	public int resetMsgInfo2(WMsgInfo wm) throws DataAccessException;
	
	public int delMsgInfoIdLst(WMsgInfo wm) throws DataAccessException;

	public Integer getMsgInfoNextId(WMsgInfo vo)
			throws DataAccessException ;
	
}
