package com.wtwd.sys.innerw.wswitchInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wswitchInfo.domain.WswitchInfo;



public interface WswitchInfoFacade {


	public List<DataMap> getData(WswitchInfo vo) throws SystemException;

}
