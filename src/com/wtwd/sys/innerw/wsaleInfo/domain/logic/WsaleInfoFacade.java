package com.wtwd.sys.innerw.wsaleInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wsaleInfo.domain.WsaleInfo;



public interface WsaleInfoFacade {


	public List<DataMap> getData(WsaleInfo vo) throws SystemException;

}
