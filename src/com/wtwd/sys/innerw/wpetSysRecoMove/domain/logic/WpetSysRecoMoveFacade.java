package com.wtwd.sys.innerw.wpetSysRecoMove.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetSysRecoMove.domain.WpetSysRecoMove;


public interface WpetSysRecoMoveFacade {


	public List<DataMap> getData(WpetSysRecoMove vo) throws SystemException;

}
