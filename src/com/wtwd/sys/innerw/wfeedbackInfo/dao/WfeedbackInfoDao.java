package com.wtwd.sys.innerw.wfeedbackInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.godoing.rose.lang.DataMap;
import com.wtwd.sys.innerw.wfeedbackInfo.domain.WfeedbackInfo;





public interface WfeedbackInfoDao {
	
	
	public List<DataMap> getData(WfeedbackInfo vo) throws DataAccessException;
	
}
