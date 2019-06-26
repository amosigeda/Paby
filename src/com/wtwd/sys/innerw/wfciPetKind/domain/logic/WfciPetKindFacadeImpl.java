package com.wtwd.sys.innerw.wfciPetKind.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wfciPetKind.dao.WfciPetKindDao;
import com.wtwd.sys.innerw.wfciPetKind.domain.WfciPetKind;



public class WfciPetKindFacadeImpl implements WfciPetKindFacade {

	
	private WfciPetKindDao wfciPetKindDao;
	

	public WfciPetKindFacadeImpl(){
		
	}

	public List<DataMap> getData(WfciPetKind vo) throws SystemException {
		return wfciPetKindDao.getData(vo);
	}

	/**
	 * @return the wfciPetKindDao
	 */
	public WfciPetKindDao getWfciPetKindDao() {
		return wfciPetKindDao;
	}

	/**
	 * @param wfciPetKindDao the wfciPetKindDao to set
	 */
	public void setWfciPetKindDao(WfciPetKindDao wfciPetKindDao) {
		this.wfciPetKindDao = wfciPetKindDao;
	}


}
