package com.wtwd.sys.innerw.wchannelInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wchannelInfo.domain.WchannelInfo;



public interface WchannelInfoFacade {


	public List<DataMap> getData(WchannelInfo vo) throws SystemException;

}
