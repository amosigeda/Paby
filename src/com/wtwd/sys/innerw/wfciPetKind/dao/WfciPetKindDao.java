package com.wtwd.sys.innerw.wfciPetKind.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wfciPetKind.domain.WfciPetKind;





public interface WfciPetKindDao {
	
	
	public List<DataMap> getData(WfciPetKind vo) throws DataAccessException;
	
}
