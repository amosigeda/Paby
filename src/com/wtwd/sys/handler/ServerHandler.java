package com.wtwd.sys.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wtwd.sys.client.core.ClientMessageNotification;
import com.wtwd.sys.client.event.ClientMessageEvent;
import com.wtwd.sys.client.handler.AdragonHandler;
import com.wtwd.sys.client.handler.AdragonUserHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;


@Controller
@Scope("prototype")
public class ServerHandler extends IoHandlerAdapter {

	private final static String TAG = ServerHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(ServerHandler.class);
	/**
	 * 设备端和客户端
	 */
	private final static String DEVICE = "D_";
	private final static String USER = "ADR_U";
	
	private final static String WEIKE = "watch_id";
	
	@Autowired
	private ApplicationContext applicationContext;   //接收到客户端的消息进行处理
	
	private ClientSessionManager mClientSessionManager;  //客户端session的保存

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("sessionCreated"+session.getId());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		super.sessionClosed(session);
		logger.info(",sessionClosed"+session.getId());
		logger.info("通道关闭");	
		String key = (String)session.getAttribute("id");   //先把保存的属性值拿出
		removeKey(key);
	}
	
	@Override
	public void sessionIdle(final IoSession session,final IdleStatus status) throws Exception{
        super.sessionIdle(session, status);
        
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.info("exceptionCaught"+session.getId());
		StringBuffer sb = new StringBuffer();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		cause.printStackTrace(printWriter);
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);		
		logger.error("异常编码" + sb.toString());
		session.close(true);   //报异常时,删除客户端的登陆
		super.exceptionCaught(session, cause);
	}
		
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{		
		String temp = (String)message;
		ClientMessageEvent event = new ClientMessageEvent();
		
		//ClientMessageNotification notification = (ClientMessageNotification)applicationContext.getBean("mainNotification");
		//ClientMessageNotification notification = (ClientMessageNotification) context.getBean("mainNotification");
		ClientMessageNotification notification = new ClientMessageNotification();
		logger.info("session的id+"+temp);
		if(temp.contains(USER)){
			notification.setEventHandler(new AdragonUserHandler());
		}else{
			notification.setEventHandler(new AdragonHandler());
		}
		
		event.setMessage(temp);
		event.setIoSession(session);
		
		applicationContext.publishEvent(event);
		
	}
	/**
	 * 发送成功回调
	 */
	@Override
	public void messageSent(IoSession session, Object message) {
		//System.out.println("发送成功="+session.getId()+"发送内容="+(String)message);
	}
	
	@Override
	public void inputClosed(IoSession session) throws Exception{
		super.inputClosed(session);
	}
	
	public String getServerName() throws Exception {
		StringBuffer serverName = new StringBuffer();
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName.append(pros.getProperty("server"))
		          .append(",").append(pros.getProperty("port"))
		          .append(",").append(pros.getProperty("serverName"));
		return serverName.toString();
	}
	
	private void removeKey(String key){
		if(key != null){
			mClientSessionManager.removeSessionId(key);
		}
	}
}