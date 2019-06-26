package com.wtwd.common.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class KeepAliveRequestTimeoutHandlerImp implements KeepAliveRequestTimeoutHandler {
	
	private Logger logger = Logger.getLogger(KeepAliveRequestTimeoutHandlerImp.class);

	public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session)
			throws Exception {

		logger.info("session: " + session.getId() + " not received keepAliveReq within: " + filter.getRequestTimeout());
		
        session.closeNow();
	}
	
}
