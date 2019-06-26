package com.wtwd.sys.innerw.liufeng.dao;

import org.springframework.dao.DataAccessException;

import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;

public interface WSuggestionDao {
	
	public int insertUserSuggestion(WSuggestion ws) throws DataAccessException;
	
}
