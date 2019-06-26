package com.wtwd.sys.innerw.wpetSysReco.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetSysReco.domain.WpetSysReco;


public interface WpetSysRecoFacade {


	public List<DataMap> getData(WpetSysReco vo) throws SystemException;

}
