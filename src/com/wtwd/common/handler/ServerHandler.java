package com.wtwd.common.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.Beattim;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime2;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.client.event.ClientMessageEvent;
//import com.wtwd.sys.client.handler.AdragonHandler;
//import com.wtwd.sys.client.handler.AdragonUserHandler;
import com.wtwd.sys.client.handler.EventHandler;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;
//import org.springframework.context.ApplicationContext;

public class ServerHandler extends IoHandlerAdapter {
	
//	private final static String ADR_U = "ADR_U";
//	private final static String sbeat = "{"+'"'+"sbeat"+'"'+":"+'"'+"ping"+'"'+"}";
//	private final static String sbeat = "0x13";
	
	private Logger logger = Logger.getLogger(ServerHandler.class);
	
//	private ApplicationContext applicationContext;
	
//	private ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
	
	private EventHandler eventHandler;
	
//	JSONObject json = new JSONObject();
	
	public /*static*/ void sysOffline(IoSession session) {
		WTSigninAction ba = new WTSigninAction();
		
		try {
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			
			if(session.containsAttribute("wdeviceInfo")){
				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
				Integer device_id = (Integer) dm.getAt("device_id"); 
				String devIMEI = (String) dm.getAt("device_imei");
				dm = null;		
				
				String devId = (String)session.getAttribute("devId");
				if(mClientSessionManager.getSessionId(devId) != null) {
					mClientSessionManager.removeSessionId(devId);
					if ( session.containsAttribute("wdeviceInfo") )					
						session.removeAttribute("wdeviceInfo");
					if ( session.containsAttribute("devId") )					
						session.removeAttribute("devId");
				}
		    	LocationInfoHelper lih = new LocationInfoHelper();
				
				lih.proSysDevOffline(device_id, devIMEI);
				
			}
		} catch(Exception e) {
			ba.insertVisit(null,null, "80", "ServerHandler sysOffline. exception" );			
		}
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		SocketAddress sa = session.getRemoteAddress();

		//System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " remote ip: "+ sa.toString());
   		logger.info("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " remote ip: "+ sa.toString());
/*   		
        SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();  
        cfg.setReceiveBufferSize(2 * 1024 * 1024);  
        cfg.setReadBufferSize(2 * 1024 * 1024);  
        cfg.setKeepAlive(true);  
        cfg.setSoLinger(0); //这个是根本解决问题的设置     		
   		*/
	}
	
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		super.sessionClosed(session);
		WTSigninAction ba = new WTSigninAction();
		
		//System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " sessionClosed");
		logger.info("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " sessionClosed");

		//ba.insertVisit(null,null, "80", "sessionClosed" );
		
		//sysOffline(session);
		
	}
	
	@Override
	public void sessionIdle(final IoSession session,IdleStatus status) throws Exception{
        super.sessionIdle(session, status);
         
        //System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " sessionIdle: " + status + " Reader idleTime: " + session.getConfig().getReaderIdleTime() + " Writer idleTime: " + session.getConfig().getWriterIdleTime() + " Both IdleTime: " + session.getConfig().getBothIdleTime());
        logger.info("Dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " sessionIdle: " + status + " Reader idleTime: " + session.getConfig().getReaderIdleTime() + " Writer idleTime: " + session.getConfig().getWriterIdleTime() + " Both IdleTime: " + session.getConfig().getBothIdleTime());
        
        if (session.isClosing()) {
        	return;
        }
        
        if (status == IdleStatus.READER_IDLE) {
        	logger.info("Dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " sessionIdle READER_IDLE: " + status + " Reader idleTime: " + session.getConfig().getReaderIdleTime());
//        	sysOffline(session);
//    		session.closeOnFlush();
        	
        	String sn = session.getAttribute("devId").toString();  // 会出现空指针
        	
        	session.closeNow();
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();        	
			mClientSessionManager.removeSessionId(session.getAttribute("devId").toString().trim());

	        CmdDownSetImpl cs = new CmdDownSetImpl();
	        cs.ProDevStat( sn, 0 );
	        cs = null;
			
        	
        	
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
		
		//System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + "\r\n" + "exception: " + sb.toString());
		logger.error("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + "\r\n" + "exception: " + sb.toString());
		
		super.exceptionCaught(session, cause);
	}
		
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();

		//System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " recv: "+ message.toString().trim());
		logger.info("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " recv: "+ message.toString().trim());
		
//		String temp = (String)message;
		
		if ("0x05".equals(message) || "".equals(message)) {
			return;
		}
		
		if ("exit".equals(message)) {
			ServerHandler sh = new ServerHandler();
	    	sh.sysOffline(session); 			
			session.closeNow();
		}

		Integer device_id=-1;
		String devId = null;
		try {
			if(session.containsAttribute("wdeviceInfo")){
				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
				device_id = (Integer) dm.getAt("device_id"); 
				dm = null;
				ba.insertVisit(null, null, String.valueOf(device_id), "recv:" + message.toString().trim());				
			} else {
				if(session.containsAttribute("devId")){
					devId = (String) session.getAttribute("devId");				
					ba.insertVisit(null, devId, "-1", "recv:" + message.toString().trim());					
				} else	{			
					ReqJsonData reqJsonData = JSON.parseObject(message.toString(), ReqJsonData.class);
					String sno = reqJsonData.getSerie_no();			
					if ( tls.isNullOrEmpty(sno) )
						sno = reqJsonData.getImei();
					if ( !tls.isNullOrEmpty(sno) )
						ba.insertVisit(null, sno, null, "login recv:" + message.toString().trim());
					else	
						ba.insertVisit(null, null, "-1", "exception recv:" + message.toString().trim());
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try{
			/*
			Gson gson=new Gson();
		    gson.toJson(temp);
		    System.out.println("gson.toJson---------"+ gson.toJson(temp));
		    */
			JSON.parseObject((String)message);
//			System.out.println(JSON.parseObject((String)message));
		//	logger.info("JSON.parseObject(temp)------"+JSON.parseObject(temp));
		}catch(Exception e){
			
			e.printStackTrace();
			//System.out.println("传递的字符串:"+ message +" 必须是一个JSON格式");
			logger.info("dev 传递的字符串:"+ message +" 必须是一个JSON格式");
//			session.write("{'resultCode':'-1'}");
			session.write("The " + message + " must is JSON!");
			return;
		}
									
		ClientMessageEvent event = new ClientMessageEvent();
		//ClientMessageNotification notification = new ClientMessageNotification();
						
//		logger.info("session的id+"+ message);
		
//		if(temp.contains(ADR_U)){
//			eventHandler = new AdragonUserHandler();			
//		}else{
//			//20160625 lbl
//			eventHandler = new WTDevHandler();
//			
//		}
//			
		eventHandler = new WTDevHandler();
		
		event.setMessage(message);
		event.setIoSession(session);
		
		eventHandler.handler(message, session);		
		event.setIoSession(session);		
		
		//session.write(json);
	}
	@Override
	public void messageSent(IoSession session, Object message) {
		Integer device_id=-1;
		String devId = null;
		WTSigninAction ba = new WTSigninAction();
		
//		if (!("0x06".equals(message))) {
			//System.out.println("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " send: "+ message.toString().trim());
			logger.info("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " send: "+ message.toString().trim());
//		}
		
		try {
			if(session.containsAttribute("wdeviceInfo")){
				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
				device_id = (Integer) dm.getAt("device_id"); 
				dm = null;
				ba.insertVisit(null, null, String.valueOf(device_id), "send:" + message.toString().trim());				
			} else {
				if(session.containsAttribute("devId")){
					devId = (String) session.getAttribute("devId");				
					ba.insertVisit(null, devId, "-1", "send:" + message.toString().trim());					
				} else	{				
					ba.insertVisit(null, null, "-1", "send:" + message.toString().trim());
				}
			} 
			
			try {
				String msgStr = message.toString();
				if ( msgStr.startsWith("{") && msgStr.endsWith("}") ) {
					RespJsonData respJd = JSON.parseObject(message.toString(), RespJsonData.class);
					Beattim setBeattim = respJd.getSetBeattim();
					if ( setBeattim != null ) {
						SynTime ste = respJd.getSynTime();
						SynTime2 ste2 = respJd.getSynTime2();
						Tools tls = new Tools();
						String ut1 = ste.getUtcTime();
						String ut2 = tls.getUtcDateStrNow();
						long ut2long = tls.getLongFromDtStr(ut2);
						if ( ut1 != null && tls.getSecondsBetweenDays(ut1, ut2) >= 40 ) {
							//需要重新校准时间...
							ba.insertVisit(null, devId, "-1", "setBeattim retry!");		
							logger.info("dev ssn: " + session.getId() + " dev: " + session.getAttribute("devId") + " setBeattim retry!");
							ste.setUtcTime(ut2);
							ste2.setUtcTime(ut2long);
							respJd.setSynTime(ste);
							respJd.setSynTime2(ste2);
							respJd.setSyncDevState(null);
							respJd.setUpDeviceInfo(null);
							respJd.setUpdateVer(null);
							String resp = JSON.toJSONString(respJd);
							session.write(resp);
						}
						
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ba = null;
		
	}

	public /*static*/ void sysOffline(String imeiStr){
		
		WTSigninAction ba = new WTSigninAction();
		ba.insertVisit(null,imeiStr, null, "ServerHandler sysOffline start" );

		try {
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			IoSession session = mClientSessionManager.getSessionId(imeiStr);
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			Integer device_id = 0;
			
			if( session != null && session.containsAttribute("wdeviceInfo")){
				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
				device_id = (Integer) dm.getAt("device_id"); 								
			} else
				device_id = ba.getDeviceIdFromDeviceImei(imeiStr);

			if ( session != null ) {
				if ( session.containsAttribute("wdeviceInfo") )
					session.removeAttribute("wdeviceInfo");
				
				if ( session.containsAttribute("devId") )			
					session.removeAttribute("devId");

				mClientSessionManager.removeSessionId(imeiStr);
//				session.closeNow();
				session.closeOnFlush();		
			}	
			
			lih.proSysDevOffline(device_id, imeiStr);	
			
		} catch(Exception e) {
			ba.insertVisit(null,imeiStr, null, "ServerHandler sysOffline3. exception" );
		}
	}


}
