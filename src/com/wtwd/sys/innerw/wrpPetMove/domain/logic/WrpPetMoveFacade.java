package com.wtwd.sys.innerw.wrpPetMove.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wrpPetMove.domain.WrpPetMove;



public interface WrpPetMoveFacade {


	public List<DataMap> getData(WrpPetMove vo) throws SystemException;

}
