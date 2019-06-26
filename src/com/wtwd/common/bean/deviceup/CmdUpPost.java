package com.wtwd.common.bean.deviceup;

import org.apache.mina.core.session.IoSession;

import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;

public interface CmdUpPost {
	
	public RespJsonData stepDatasPost(ReqJsonData reqJson, IoSession session) throws SystemException;
	public RespJsonData sleepDatasPost(ReqJsonData reqJson, IoSession session)throws SystemException;
	
	public RespJsonData lctDatasPost(ReqJsonData reqJson, IoSession session) throws SystemException;

}
