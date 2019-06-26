package com.wtwd.sys.innerw.wpetwifirange.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.http.BaseAction;

public class WpetWifiRangeAction extends BaseAction{
	
	Log logger = LogFactory.getLog(WpetWifiRangeAction.class);
	
	public ActionForward queryStepData(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return null;
		
	}	
}
