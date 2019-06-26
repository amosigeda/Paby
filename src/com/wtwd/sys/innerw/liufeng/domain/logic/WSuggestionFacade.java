package com.wtwd.sys.innerw.liufeng.domain.logic;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;

public interface WSuggestionFacade {
	
	public int insertUserSuggestion(WSuggestion ws) throws DataAccessException;

}
