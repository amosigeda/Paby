package com.wtwd.sys.innerw.wdeviceManage.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wdeviceManage.domain.WdeviceManage;



public interface WdeviceManageFacade {


	public List<DataMap> getData(WdeviceManage vo) throws SystemException;

}
