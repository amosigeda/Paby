package com.wtwd.sys.innerw.wroleFuncInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wroleFuncInfo.domain.WroleFuncInfo;



public interface WroleFuncInfoFacade {


	public List<DataMap> getData(WroleFuncInfo vo) throws SystemException;

}
