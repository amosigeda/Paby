package com.wtwd.sys.innerw.wsportlevel1.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wsportlevel1.domain.WsportLevel1;

public interface WsportLevel1Facade {


	public List<DataMap> getDataByWeight(WsportLevel1 vo) throws SystemException;

	
	

}
