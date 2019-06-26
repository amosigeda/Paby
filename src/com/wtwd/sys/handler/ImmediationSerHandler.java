package com.wtwd.sys.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.sys.client.core.ClientMessageNotification;
import com.wtwd.sys.client.event.ClientMessageEvent;
import com.wtwd.sys.client.handler.AdragonHandler;
import com.wtwd.sys.client.handler.AdragonUserHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;



@Controller
@Scope("prototype")
public class ImmediationSerHandler extends IoHandlerAdapter {

	private final static String TAG = ImmediationSerHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(ImmediationSerHandler.class);	
	private ClientSessionManager mClientSessionManager;  //客户端session的保存
	
	private final static String ADRAGON="ADR";
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("sessionCreated"+session.getId());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		super.sessionClosed(session);
		logger.info("sessionClosed"+session.getId());
		if(mClientSessionManager.getCurrentSessionIdCount() != 0){   //说明有值
			String key = (String)session.getAttribute("id");   //先把保存的属性值拿出
			logger.info("sessionClosed====关闭sessionId="+session.getId()+"=key="+key);
			removeKey(key);
		}
	}
	
	@Override
	public void sessionIdle(final IoSession session,final IdleStatus status) throws Exception{
        super.sessionIdle(session, status);
        if(status == IdleStatus.BOTH_IDLE){
        	WriteFuture writeFuture = session.write("heartbeat");
        	writeFuture.addListener(new IoFutureListener<IoFuture>() {

				public void operationComplete(IoFuture future) {
					// TODO Auto-generated method stub							
					if(((WriteFuture)future).isWritten()){   //发送成功							
						String key = (String)session.getAttribute("id");						
						logger.info("发送成功====sessionId="+session.getId()+"=key="+key);						
					}else{							
						//空闲,通讯不上,剔除session						
						String key = (String)session.getAttribute("id");						
						logger.info("sessionIdle====关闭sessionId="+session.getId()+"=key="+key);						
						removeKey(key);						
						session.close(true);						
					}							
				}				
			});
        }else{
        	String key = (String)session.getAttribute("id");
			logger.info("sessionIdle====关闭isConnected="+session.getId()+"=key="+key);
			removeKey(key);
        }
        
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
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
		String key = (String)session.getAttribute("id");
		logger.error("exceptionCaught异常sessionId"+session.getId()+",保存的key="+key);
		//removeKey(key);
		session.close(true);   //报异常时,删除客户端的登陆
		super.exceptionCaught(session, cause);
	}
		
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{		
		String temp = (String)message;
		ReqJsonData reqJsonData = JSON.parseObject(temp, ReqJsonData.class);
		String cmd = reqJsonData.getCmd();
		ClientMessageEvent event = new ClientMessageEvent();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");

		ClientMessageNotification notification = (ClientMessageNotification) context.getBean("mainNotification");
		logger.info("session的messageReceived收到的消息="+temp);
		
		if(cmd.startsWith(ADRAGON)){
			notification.setEventHandler(new AdragonHandler());
		}else{
			notification.setEventHandler(new AdragonUserHandler());
		}
		event.setMessage(message);
		event.setIoSession(session);
		
		/*
		String temp = (String)message;
		ClientMessageEvent event = new ClientMessageEvent();
		ClientMessageNotification notification = (ClientMessageNotification)applicationContext.getBean("mainNotification");
		
		logger.info("session的messageReceived收到的消息="+temp);
		if(temp.contains(WEIKE)){    //威科自定义协议
			notification.setEventHandler(deviceHandler);
		}else if(temp.contains(DEVICE)){  //设备端(我们自己拟定的)
			notification.setEventHandler(immediationHandler);
		}else if(temp.contains(ALPHA)){
			notification.setEventHandler(alphaHandler);
		}else if(temp.contains(ALPHA_U)){       //一定要注意顺序
			notification.setEventHandler(alphaUserHandler);
		}else if(temp.contains(USER)){ //客户端的(app)
			notification.setEventHandler(userHanlder);
		}
		event.setMessage(temp);
		event.setIoSession(session);
		
		applicationContext.publishEvent(event);
		*/
	}
	/**
	 * 发送成功回调
	 */
	@Override
	public void messageSent(IoSession session, Object message) {
		logger.info("发送成功="+session.getId()+"发送内容="+(String)message);
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
