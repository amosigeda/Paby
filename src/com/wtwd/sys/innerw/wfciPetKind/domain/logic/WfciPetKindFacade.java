package com.wtwd.sys.innerw.wfciPetKind.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wfciPetKind.domain.WfciPetKind;



public interface WfciPetKindFacade {


	public List<DataMap> getData(WfciPetKind vo) throws SystemException;

}
