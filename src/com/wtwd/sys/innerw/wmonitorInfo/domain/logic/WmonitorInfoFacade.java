package com.wtwd.sys.innerw.wmonitorInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wmonitorInfo.domain.WmonitorInfo;



public interface WmonitorInfoFacade {


	public List<DataMap> getData(WmonitorInfo vo) throws SystemException;

}
