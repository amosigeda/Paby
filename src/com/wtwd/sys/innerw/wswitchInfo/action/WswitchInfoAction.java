package com.wtwd.sys.innerw.wswitchInfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.http.BaseAction;

public class WswitchInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(WswitchInfoAction.class);
	
	public ActionForward queryData(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
		
	}
	
}