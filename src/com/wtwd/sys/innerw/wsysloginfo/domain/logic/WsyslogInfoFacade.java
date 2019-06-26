package com.wtwd.sys.innerw.wsysloginfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wsysloginfo.domain.WsyslogInfo;



public interface WsyslogInfoFacade {


	public List<DataMap> getData(WsyslogInfo vo) throws SystemException;

}
