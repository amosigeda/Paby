package com.wtwd.sys.innerw.wprojectInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wprojectInfo.domain.WprojectInfo;



public interface WprojectInfoFacade {


	public List<DataMap> getData(WprojectInfo vo) throws SystemException;

}