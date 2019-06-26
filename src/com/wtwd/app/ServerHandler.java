package com.wtwd.app;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;

public class ServerHandler extends IoHandlerAdapter {
	
	private final static Logger logger = Logger.getLogger(ServerHandler.class);
	/*
	 * private final Set<IoSession> sessions = Collections .synchronizedSet(new
	 * HashSet<IoSession>()); private final Set<String> users = Collections
	 * .synchronizedSet(new HashSet<String>()); MsgServer ms = new MsgServer();
	 * Map<IoSession, MINAClientHandler> clientMap = new HashMap<IoSession,
	 * MINAClientHandler>();
	 */

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		MessageManager.sessionSet.remove(session.getId());
		logger.info(session.getId() + "error");
		logger.error("ServerHandler exception caught:", cause);;
		cause.printStackTrace();

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

		String ip = ((InetSocketAddress) session.getRemoteAddress())
				.getAddress().getHostAddress();
		int host = ((InetSocketAddress) session.getRemoteAddress()).getPort();
		logger.info(ip + ":" + host + "sessionId:" + session.getId());
		session.setAttribute("ip", ip);
		MessageManager.sessionSet.put(session.getId(), session);

		ConcurrentHashMap chm = new ConcurrentHashMap();

		// MessageManager.waitingSessions.add(session);
		// Thread th = new Thread(st);
		// sh.start();
		// session.setAttribute("st", st);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		if(!session.isConnected()){
			logger.info(session.getId() + ",," + "not isConnected");
		}
		
		
		PhoneInfo de = (PhoneInfo) session.getAttribute("device");
		if (de != null) {
			logger.info("sessionIdle de is ==================="+de);
//			DeviceServiceI dsi = new DeviceServiceImpl();
//			DeviceBindEntity bde = dsi.getBindByDeviceId(de.getDevice_id());
//			if (bde != null
//					&& MessageManager.playerSet.containsKey(bde
//							.getUser_id())) {
//				JSONObject jsd = new JSONObject();
//				jsd.put("request", "SERVER_DEVICE_NETWORKSTATUS");
//				jsd.put("status", 2);
//				jsd.put("device_id", de.getId());
//				MessageManager.playerSet.get(bde.getUser_id()).write(jsd);
//			}
			session.setAttribute("NetStatus", null);
			session.setAttribute("device", null);
			MessageManager.tzSet.remove(de.getId());
			session.close(true);
		}else{
			AppUserInfo ue = (AppUserInfo) session.getAttribute("user");
			logger.info("sessionIdle ue is ==================="+ue);
			if(ue==null)
				session.close(true);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		// MessageAdapter.appAdapter();
		logger.info(session.getId() + ",," + message);
		logger.debug(session.getId() + ",," + message);
		logger.info("thread is in messagereceived is ==============="+Thread.currentThread().getId());

		// logger.error("error");

		// JSONObject j=JSONObject.fromObject(message.toString());
		// byte b=(Byte) j.get("b");
		// session.write(message);
		try {
			if(message!=null&&!("").equals(message)) {
				System.out.println("message===================="+message);
			new MessageManager(session, message).msgTransfer();
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Logger log = Logger.getLogger(ServerHandler.class);
		 * log.info("received: " + message); JSONObject
		 * js=JSONObject.fromObject(message.toString()); // if(message
		 * instanceof JSONObject) logger.info(js.get("fsdf"));
		 * logger.info(js.get("11")); String theMessage="";
		 * if(js.get("11")!=null) theMessage = js.get("11").toString(); String[]
		 * result = theMessage.split(" ", 2); String theCommand = result[0];
		 */

		/*
		 * MINAClientHandler ch = clientMap.get(session); try { ch.dispatch(js);
		 * }catch (Exception e) { e.printStackTrace(); }
		 */
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("in sessionClosed session ===================="+session);
		logger.info("in sessionClosed isConnected==================="+session.isConnected());
		logger.info("in sessionClosed remoteip======================"+session.getRemoteAddress());
		logger.info("in sessionClosed localip======================="+session.getLocalAddress());
		AppUserInfo us = (AppUserInfo) session.getAttribute("user");
		if (us != null) {
			IoSession use = MessageManager.playerSet.get(us.getId());
			logger.info("in sessionClosed use is==================="+use);
			IoSession ust = MessageManager.sessionSet.get(us.getId());
			logger.info("in sessionClosed ust is==================="+ust);
			IoSession usid = MessageManager.sessionSet.get(session.getId());
			logger.info("in sessionClosed usid is==================="+usid);
			String ip1 = (String) session.getAttribute("ip");
			logger.info("sessionClosed ip is ============"+ip1);
			MessageManager.playerSet.remove(us.getId());
//			MessageManager.sessionSet.remove(us.getUser_id());
			MessageManager.sessionSet.remove(session.getId());
//			usi.AddLoginInfo(us.getUser_id(), ip1, 1, 2);
			// MessageManager.playerSet.remove((String)session.getAttribute("roleName"));
		} else {
			PhoneInfo de = (PhoneInfo) session.getAttribute("device");
			logger.info("sessionClosed de is ==================="+de);
			if (de != null) {
				logger.info("sessionClosed device_id is ========================"+de.getId());
//				DeviceServiceI dsi = new DeviceServiceImpl();
//				DeviceBindEntity bde = dsi.getBindByDeviceId(de.getDevice_id());
//				if (bde != null
//						&& MessageManager.playerSet.containsKey(bde
//								.getUser_id())) {
//					JSONObject jsd = new JSONObject();
//					jsd.put("request", "SERVER_DEVICE_NETWORKSTATUS");
//					jsd.put("status", 2);
//					jsd.put("device_id", de.getDevice_id());
//					MessageManager.playerSet.get(bde.getUser_id()).write(jsd);
//				}
				session.setAttribute("NetStatus", null);
				session.setAttribute("device", null);
				MessageManager.tzSet.remove(de.getId());
			}
		}
		session.close(true);
		logger.info(session.getId() + "closed");
	}

	@Override
	public void messageSent(IoSession session, Object message) {
		System.out.println("SERVER send:" + session.getId() + ","
				+ message.toString());

	}

	public static boolean setMessage(IoSession session, String msg) {
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
