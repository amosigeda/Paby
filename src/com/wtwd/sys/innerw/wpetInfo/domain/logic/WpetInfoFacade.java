package com.wtwd.sys.innerw.wpetInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetInfo.domain.WpetInfo;



public interface WpetInfoFacade {


	public List<DataMap> getData(WpetInfo vo) throws SystemException;

}