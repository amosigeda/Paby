package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.dao.WSuggestionDao;
import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;

public class WSuggestionFacadeImpl implements WSuggestionFacade {

	private WSuggestionDao wSuggestionDao;
	public void setwSuggestionDao(WSuggestionDao wSuggestionDao) {
		this.wSuggestionDao = wSuggestionDao;
	}
	
	public int insertUserSuggestion(WSuggestion ws) throws DataAccessException {
		return wSuggestionDao.insertUserSuggestion(ws);
	}
	

}
