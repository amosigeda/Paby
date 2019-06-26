package com.wtwd.sys.innerw.wcheckInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;



public interface WcheckInfoFacade {


	public List<DataMap> getData(WcheckInfo vo) throws SystemException;

}
