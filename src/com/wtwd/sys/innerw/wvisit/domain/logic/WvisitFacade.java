package com.wtwd.sys.innerw.wvisit.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wvisit.domain.Wvisit;



public interface WvisitFacade {


	public List<DataMap> getData(Wvisit vo) throws SystemException;

}
