package com.wtwd.sys.innerw.wfuncinfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wfuncinfo.domain.Wfuncinfo;




public interface WfuncinfoFacade {


	public List<DataMap> getData(Wfuncinfo vo) throws SystemException;

}