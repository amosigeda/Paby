package com.wtwd.sys.innerw.wuserinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wuserinfo.domain.WuserInfo;



public interface WuserInfoFacade {


	public List<DataMap> getData(WuserInfo vo) throws SystemException;

}