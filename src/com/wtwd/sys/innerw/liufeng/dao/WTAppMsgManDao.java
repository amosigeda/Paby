package com.wtwd.sys.innerw.liufeng.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;

public interface WTAppMsgManDao {
	
	public List<DataMap> queryByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException;
	
	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException;

	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException;

	public int insertData(WMsgInfo wm) throws DataAccessException;

	public int resetMsgInfo(WMsgInfo wm) throws DataAccessException;

	public int resetMsgInfo2(WMsgInfo wm) throws DataAccessException;
	
	public int delMsgInfoIdLst(WMsgInfo wm) throws DataAccessException;

	public Integer getMsgInfoNextId(WMsgInfo vo)
			throws DataAccessException ;
	
}
