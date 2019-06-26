package com.wtwd.app;

import net.sf.json.JSONObject;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;



/**
 * 包含会话和消息及处理函数申明的抽象消息处理器
 */
public abstract class AbstractHandler {
	/** 会话 */
	public IoSession session;

	/** 消息 */
	public JSONObject message;
	

	// public String system_path = "/home/apache-tomcat-7.0.57/webapps/ROOT";

	// public String file_ip="121.40.85.27:8080";

	public boolean issend = true;

	public AbstractHandler() {
	}

	public AbstractHandler(IoSession session, JSONObject message) {
		this.session = session;
		this.message = message;
	}

	public abstract void handle();

	public static boolean sendMessage(IoSession session, String msg) {
		if (session != null) {
			WriteFuture wf = session.write(msg);
			wf.join();
			if (wf.isWritten()) {
				return true;
			}
		}
		return false;
	}
}
