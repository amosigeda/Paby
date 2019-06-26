package com.wtwd.sys.innerw.wefencing.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wefencing.domain.Wefencing;




public interface WefencingFacade {


	public List<DataMap> getData(Wefencing vo) throws SystemException;

}
