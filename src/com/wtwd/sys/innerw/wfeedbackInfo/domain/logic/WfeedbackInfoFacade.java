package com.wtwd.sys.innerw.wfeedbackInfo.domain.logic;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.sys.innerw.wfeedbackInfo.domain.WfeedbackInfo;



public interface WfeedbackInfoFacade {


	public List<DataMap> getData(WfeedbackInfo vo) throws SystemException;

}
