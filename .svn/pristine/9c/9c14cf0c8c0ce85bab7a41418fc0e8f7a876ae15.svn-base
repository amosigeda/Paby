package com.wtwd.common.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;


public class KeepAliveMessageFactoryImp implements KeepAliveMessageFactory {
	
	private static final String HEARTBEATREQ = "0x05";
	private static final String HEARTBEATRESP = "0x06";

	public Object getRequest(IoSession session) {
		return null;
	}

	public Object getResponse(IoSession session, Object message) {
		return HEARTBEATRESP;
	}

	public boolean isRequest(IoSession session	, Object message) {
		if (message.equals(HEARTBEATREQ)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isResponse(IoSession session, Object message) {
		return false;
	}
}
