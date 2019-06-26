package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.liufeng.dao.WTAppQuestionInfoManDao;
import com.wtwd.sys.innerw.liufeng.domain.QuestionInfo;

public class WTAppQuestionInfoManFacadeImpl implements WTAppQuestionInfoManFacade {

	private WTAppQuestionInfoManDao wtAppQuestionInfoManDao;
	public void setWtAppQuestionInfoManDao(
			WTAppQuestionInfoManDao wtAppQuestionInfoManDao) {
		this.wtAppQuestionInfoManDao = wtAppQuestionInfoManDao;
	}

	public List<DataMap> queryAllQuestionList(QuestionInfo qi)
			throws DataAccessException {
		return wtAppQuestionInfoManDao.queryAllQuestionList(qi);
	}

//	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException {
//		return wtAppMsgManDao.queryByUserIdMsgCount(wm);
//	}
//
//	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException {
//		return wtAppMsgManDao.updateByUserIdMsgInfo(wm);
//	}
//
//	public int insertData(WMsgInfo wm) throws DataAccessException {
//		return wtAppMsgManDao.insertData(wm);
//	}
	
	
}
