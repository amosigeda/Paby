package com.wtwd.sys.innerw.wpetOfUser.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wpetOfUser.domain.WpetOfUser;



public interface WpetOfUserFacade {


	public List<DataMap> getData(WpetOfUser vo) throws SystemException;

}
