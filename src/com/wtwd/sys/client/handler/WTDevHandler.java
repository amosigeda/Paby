﻿package com.wtwd.sys.client.handler;

// WTWD CREATED AT 20160625 lbl

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.Beattim;
import com.wtwd.common.bean.devicedown.cmdobject.Location;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime2;
import com.wtwd.common.bean.devicedown.cmdobject.SyncDevState;
import com.wtwd.common.bean.deviceup.CmdUpPostImpl;
import com.wtwd.common.bean.other.DirectiveInfoAdr;
import com.wtwd.common.bean.other.GPSInfoAdr;
import com.wtwd.common.bean.other.Geolocation;
import com.wtwd.common.bean.other.NetWorkInfoAdr;
import com.wtwd.common.bean.other.StepParameters;
import com.wtwd.common.bean.other.WifiInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.handler.ServerHandler;
import com.wtwd.common.http.EndServlet;
import com.wtwd.common.http.HttpRequest;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.DevNotifyApp;
import com.wtwd.sys.appinterfaces.innerw.WTAppDevWifiSrcManAction;
import com.wtwd.sys.appinterfaces.innerw.WTAppGpsManAction;
import com.wtwd.sys.appinterfaces.innerw.WTDevGWAction;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.client.handler.impl.ClientMessageEventImpl;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.directiveinfo.domain.DirectiveInfo;
import com.wtwd.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.StepCountTools;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade;
import com.wtwd.sys.innerw.wpetwifirange.domain.WpetWifiRange;
import com.wtwd.sys.innerw.wpetwifirange.domain.logic.WpetWifiRangeFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware;
import com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;

public class WTDevHandler extends ClientMessageEventImpl{

	private Logger logger = Logger.getLogger(WTDevHandler.class);	
	
	private static  ServiceBean serviceBean = ServiceBean.getInstance();
	
	//private ClientSessionManager mClientSessionManager;  //客户端session的保存
	private static ClientSessionManager mClientSessionManager;  //客户端session的保存

	public static ClientSessionManager getClientSessionMangagerInstance() {

		if (mClientSessionManager == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");
			mClientSessionManager = (ClientSessionManager) context.getBean("clientSessionManager");
		}
			
		return mClientSessionManager;
	}

	public void handler(Object message, IoSession session) {
		
		String resp="";		
		if (message.toString().equals("") )
			return;	//yonghu add

		logger.info("WTDevHandler.handler() params=" + message.toString());
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");
//		mClientSessionManager = (ClientSessionManager) context.getBean("clientSessionManager");
		mClientSessionManager = getClientSessionMangagerInstance();
				
		final RespJsonData respJsonData = processCmd(message.toString(), session );
		
		resp = JSON.toJSONString(respJsonData);
		
		logger.info("WTDevHandler.handler() result=" + resp);
		if (resp != null && !"".equals(resp) && !"{}".equals(resp)) {
			session.write(resp);
		}
	}
	
	public RespJsonData processUpmoveCmd(ReqJsonData reqJsonData, IoSession session){
		final RespJsonData respJsonData = new RespJsonData();
		return respJsonData;		
	}
		
	public RespJsonData processConnectCmd(ReqJsonData reqJsonData, IoSession session){
		
		final RespJsonData respJsonData = new RespJsonData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//SqlSession sqlSession = MyBatisUtil.getSqlSession();

//		String sub ="";
//		SubcriJsonData subData = new SubcriJsonData();
		
		try{
			String deviceId = reqJsonData.getSerie_no();
			WdeviceActiveInfo wdeviceActive = new WdeviceActiveInfo();
			wdeviceActive.setCondition("device_imei ='"+deviceId+"'");
			wdeviceActive.setDevice_imei(deviceId);
			wdeviceActive.setDevice_disable(Tools.OneString);  //并且属于激活状态
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
//			Map<String, Object> testMap =  wdeviceActiveInfoFacade.testCallable(wdeviceActive);
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getData(wdeviceActive);
			if(wdeviceActiveInfos.size() > 0){
				String  devId = wdeviceActiveInfos.get(0).getAt("device_imei").toString();
				session.setAttributeIfAbsent("devId", devId);  //每一个通道的id
				session.setAttributeIfAbsent("deviceInfo", wdeviceActiveInfos.get(0));   //记录设备信息
				
				if(mClientSessionManager.getSessionId(devId) != null){
					mClientSessionManager.removeSessionId(devId);
				}
				mClientSessionManager.addSessionId(devId, session);
				
				session.setAttributeIfAbsent("uDevId", devId);   // user通道	
				devId = "";
				devId = (String) session.getAttribute("uDevId");
				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				respJsonData.setHaveUnread(Tools.ZeroString);
			}else{
				respJsonData.setRet("-1");
				respJsonData.setHaveUnread(Tools.ZeroString);
			}					
			respJsonData.setMsg("success!");
			respJsonData.setDevTime(dateFormat.format(new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			respJsonData.setResultCode(Constant.EXCEPTION_CODE);  //请求失败、异常
			respJsonData.setDevTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		
		return respJsonData;
		
	}
	
	private void updateDeviceUpdateTime(String sn) {
		try {
			Tools tls = new Tools();
			WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			voDeviceActive.setCondition("device_imei = '" + sn + "'" );

			if ( Constant.timeUtcFlag )
				voDeviceActive.setDevice_update_time(tls.getUtcDateStrNowDate());			
			else
				voDeviceActive.setDevice_update_time(new Date());

			facadeDeviceActive.updateWdeviceActiveInfo(voDeviceActive);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public RespJsonData processCmd(String msg, IoSession session)
	{
		logger.info("inter WTDevHandler.processCmd() msg=" + msg + ",sessin=" + session);
		RespJsonData respJsonData = new RespJsonData();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	LocationInfoHelper lih = new LocationInfoHelper();
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
/*		SocketAddress sa = session.getRemoteAddress();
		System.out.println("yonghu processCmd remote ipaddress"+sa.toString());
		respJsonData.setMsg(sa.toString());
*/		
		//String sub ="";
		//SubcriJsonData subData = new SubcriJsonData();
		
		try { 
//			System.out.println("----Handle ReqjsonData  package---");
			/*
			  Gson gson=new Gson();
			  
			  logger.info("gson后");
			  logger.info("反序列化前");
		      ReqJsonData reqJsonData = gson.fromJson(msg,ReqJsonData.class);
		      logger.info("reqJsonData------------------"+reqJsonData);
		      */
			ReqJsonData reqJsonData = JSON.parseObject(msg.toString(), ReqJsonData.class);
			logger.info("processCmd------------------" + reqJsonData);
			String devId = "";
			Integer device_id = null;
			if ( session == null ) {
				devId = "352138064981386";
				device_id = 3167;
			} else {
				if (session.containsAttribute("devId")) {
					devId = (String)session.getAttribute("devId");
					//device_id = BaseAction.getDeviceIdFromDeviceImei(devId);
				}
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					device_id = (Integer) dm.getAt("device_id");
					dm = null;
				} else {
					
	//				device_id = BaseAction.getDeviceIdFromDeviceImei(devId);
					
				}
				
				/*
				if ( device_id != null && device_id > 0 ) {	//smile add
					String host_time_zone = BaseAction.getHostTimeZoneByDeviceId(device_id);
			    	LocationInfoHelper lih = new LocationInfoHelper();
			    	lih.syncDevStatus(session, String.valueOf(device_id), host_time_zone);					
				}*/
			}
			
			String cmd = reqJsonData.getCmd();
			if(cmd==null || "".equals(cmd)){
				System.out.println("cmd-----字符串传递错误"+cmd);					
				respJsonData.setResultCode(Constant.EXCEPTION_CODE);
				throw new Exception("字符串传递错误");
			}
			StringBuffer sb = new StringBuffer();
			
//			if(cmd.equals(AdragonConfig.reqWConnect)) {		//上传链接请求
//				return processConnectCmd(reqJsonData, session);
//			} else if (cmd.equals(AdragonConfig.reqWUpmove)) {
//				return processUpmoveCmd(reqJsonData, session);				
//			}
						
			if (session != null && !session.containsAttribute("devId") && !(cmd.equals(AdragonConfig.UpDeviceInfo) || cmd.equals(AdragonConfig.login))) {
				ba.insertVisit(null,null, "80", "connect. exception session " + msg);
				
				//respJsonData.setResultCode(Constant.BOND_FAIL_NO);
				//session.closeNow();
				
				logger.info("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " cmd abnormally: " + msg);
				
				respJsonData.setPing(0);
		
				return respJsonData;
			}
			
			synchronized(EndServlet.initFlag) {
				if ( session != null && EndServlet.initFlag == false ) {
					respJsonData.setResultCode(Constant.ERR_SYSTEM_BUSY);
					session.closeNow();
			
					return respJsonData;					
				}
			}
			
			if(cmd.equals(AdragonConfig.UpDeviceInfo)  || cmd.equals(AdragonConfig.login) || cmd.equals(AdragonConfig.setBeattimRes)){    //上传设备信息给服务器			
				;	//BaseAction.insertVisit(null, reqJsonData.getSerie_no(), "-1", msg);
				//ba.insertWMonitor("0", null, "0", " updevice login");				
			} else {
				try {
					WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
					wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
					
					WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
					List<DataMap> list = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
					if ( tls.isNullOrEmpty(devId) || list.isEmpty()) {
						logger.info("server out dev case1:" + devId);						
						session.closeNow();
						return null;						
					} else {
						String devOl = list.get(0).getAt("dev_status").toString().trim();
						if ( !Tools.OneString.equals(devOl) ) {
							//logger.info("server out dev case2:" + devId + ":dev_status:" + devOl + ":");
							//session.closeNow();
							wdeviceActiveInfo.setCondition("device_id = "+device_id);							
							wdeviceActiveInfo.setDev_status(Tools.OneString);
							wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
							wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");

							//return null;							
						} else {
							updateDeviceUpdateTime(devId);							
						}
					}						
				} catch(Exception e) {
					e.printStackTrace(); 	//logger.info("server out dev case3:" + devId);
					//session.closeNow();
					//return null;
				}
				
			}
							
			if(cmd.equals(AdragonConfig.UpDeviceInfo) || cmd.equals(AdragonConfig.login)){    //上传设备信息给服务器
				
				String serieNo = null;
				String productModel = null;
				String firmwareEdition = null;
				String devicePhone = null;
				String iccid = null;
				
				if (cmd.equals(AdragonConfig.UpDeviceInfo)) {
					serieNo = reqJsonData.getSerie_no();
					productModel = reqJsonData.getDevice_product_model();
					firmwareEdition = reqJsonData.getDevice_firmware_edition();
					devicePhone = reqJsonData.getDevice_phone();
					logger.info("上传设备信息给服务器UpDeviceInfo：" 
							+ serieNo + productModel + firmwareEdition + devicePhone);
				} else if (cmd.equals(AdragonConfig.login)) {
					serieNo = reqJsonData.getImei();
					productModel = reqJsonData.getModel();
					firmwareEdition = reqJsonData.getFirm();
					devicePhone = reqJsonData.getTelno();
					iccid = reqJsonData.getIccid();
					logger.info("上传设备信息给服务器login："
							+ serieNo + productModel + firmwareEdition + devicePhone);
				}
				
				String belongProject = reqJsonData.getB_g();
				String devNo;
				Integer beattim = 0;
				Integer ulfq = 100;
				Integer uLTe = 30;
				
				if (serieNo == null || "".equals(serieNo)) {

					session.closeNow();
	
					serieNo = null;
					productModel = null;
					firmwareEdition = null;
					devicePhone = null;
					belongProject = null;
					
					respJsonData.setResultCode(Constant.BOND_FAIL_NO);
					
					return respJsonData;
				}
				
				if(mClientSessionManager.getSessionId(serieNo) != null) {
					
					try {
					ba.insertVisit(null,serieNo, null, "connect. exception repeat login " );					
			    	IoSession tempSession = mClientSessionManager.getSessionId(serieNo);
			    	
				    //mClientSessionManager.removeSessionId(serieNo);
					ServerHandler sh = new ServerHandler();			    	
			    	//sh.sysOffline( tempSession );  //设备这种情况下不认为需要推送下线消息
			    	
				    tempSession.closeNow();
				    tempSession = null;
					} catch ( Exception e ) {
						e.printStackTrace();
						logger.error(e.toString());
					}
					
					logger.info("[WARNING] IMEI: " + serieNo + " Device login out abnormally! Now repeat login pets system!");
					
					/*if (false) {
						session.closeNow();
						
						//不允许重复登录
					    respJsonData.setResultCode(Constant.FAIL_CODE);					
						return respJsonData;
					    //不允许重复登录 end	
					} else {
//						session.write("[WARNING] Device login out abnormally! Now device repeat login pets system!");
					}*/	
			    }
							
				PhoneInfo phoneInfo = new PhoneInfo();				
				phoneInfo.setCondition("serie_no = '"+serieNo+"'");				
				PhoneInfoFacade phoneInfoFacade = serviceBean.getPhoneInfoFacade();			
				List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
				
				logger.info("updatePhoneInfo-----------");
				if(phoneInfos.isEmpty()) {
					phoneInfo.setInputTime(new Date());
//					phoneInfo.setUploadTime(new Date());
					phoneInfo.setStatus(Tools.OneString);
					phoneInfo.setSerieNo(serieNo);
					phoneInfo.setProductModel(productModel);
					phoneInfo.setFirmwareEdition(firmwareEdition);
					phoneInfo.setPhone(devicePhone);
					phoneInfo.setBelongProject(belongProject);
					phoneInfoFacade.insertPhoneInfo(phoneInfo);
				}else{
//					phoneInfo.setSerieNo(serieNo);
//					phoneInfo.setProductModel(productModel);
					phoneInfo.setFirmwareEdition(firmwareEdition);
					phoneInfo.setPhone(devicePhone);
//					phoneInfo.setBelongProject(belongProject);
					phoneInfoFacade.updatePhoneInfo(phoneInfo);
				}
				
				DeviceLogin deviceLogin = new DeviceLogin();
				deviceLogin.setDeivceImei(serieNo);
				deviceLogin.setBelongProject(belongProject);
				deviceLogin.setBelongProject(belongProject);
				deviceLogin.setDateTime(new Date());
				deviceLogin.setDeivceImei(serieNo);
				deviceLogin.setDevicePhone(devicePhone);
				deviceLogin.setDeviceVersion(firmwareEdition);
				
				DeviceLoginFacade deviceLoginFacade = serviceBean.getDeviceLoginFacade();
				//deviceLogin.setDeviceStatus(status);
				//deviceLoginFacade.insertDeviceLogin(deviceLogin);									
				//respJsonData.setResultCode(Constant.SUCCESS_CODE);				
				//respJsonData.setDevTime(dateFormat.format(new Date()));					
                //replace table from device_active_info to wdevice _active_info 20160810 lchengling
				/*
				DeviceActiveInfo deviceActive = new DeviceActiveInfo();
				deviceActive.setCondition("device_imei ='"+serieNo+"'");
				deviceActive.setDeviceDisable(Tools.OneString);  //并且属于激活状态
				
				DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
				List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActive);
				if (deviceActiveInfos.isEmpty()) {	
					deviceActiveInfos.clear();
					respJsonData.setResultCode(Constant.BOND_FAIL_NO);     //IMEI not exist or deactive tabel: device_active_info
					respJsonData.setDevTime(dateFormat.format(new Date()));	
					//session.close(true);
				}
				else {
				session.setAttributeIfAbsent("devId", serieNo);  //每一个通道的id
				session.setAttributeIfAbsent("deviceInfo", deviceActiveInfos.get(0));   //记录设备信息
				    
				    if(mClientSessionManager.getSessionId(serieNo) != null){
						mClientSessionManager.removeSessionId(serieNo);
				}
					mClientSessionManager.addSessionId(serieNo, session);
				}
				*/
				
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				wdeviceActiveInfo.setCondition("a.device_imei = '"+serieNo+"'");
				wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				wdeviceActiveInfo.setCondition("device_imei = '"+serieNo+"'");

				logger.info("updateWdeviceActiveInfo start-----------");
				if (!Constant.STAT_DEV_RESTRICT_IMEI) {  //false  dead code!  s b code, who write this code?
					if (wdeviceActiveInfos.isEmpty()) {
						wdeviceActiveInfo.setDevice_imei(serieNo);
						wdeviceActiveInfo.setDevice_phone(devicePhone);
						wdeviceActiveInfo.setDevice_name("test");
						wdeviceActiveInfo.setDevice_update_time(tls.getUtcDateStrNowDate());
						wdeviceActiveInfo.setDevice_disable(Tools.OneString);
						wdeviceActiveInfo.setBelong_project(1);
						wdeviceActiveInfoFacade.insertWdeviceActiveInfo(wdeviceActiveInfo);
						wdeviceActiveInfo.setCondition("device_imei = '"+serieNo+"'");						
						wdeviceActiveInfos = wdeviceActiveInfoFacade.getData(wdeviceActiveInfo);
						device_id = (Integer) wdeviceActiveInfos.get(0).getAt("device_id");
						wdeviceActiveInfo.setDevice_id(device_id);
						wdeviceActiveInfo.setTime_zone("Asia/Shanghai");
						wdeviceActiveInfo.setEco_mode(Tools.OneString);
						wdeviceActiveInfoFacade.insertwDeviceExtra(wdeviceActiveInfo);
					} else {
						device_id = (Integer) wdeviceActiveInfos.get(0).getAt("device_id");
						beattim = (Integer) wdeviceActiveInfos.get(0).getAt("beattim");
						wdeviceActiveInfo.setCondition("device_imei = '"+serieNo+"'");
						//wdeviceActiveInfo.setDevice_imei(serieNo);
						wdeviceActiveInfo.setDevice_phone(devicePhone);
						//String ml = reqJsonData.getDevice_product_model().trim();
						if ( "A907".equals(productModel) )
							wdeviceActiveInfo.setBelong_project(2);		//表示1部的功能机项目
						else
							wdeviceActiveInfo.setBelong_project(1);
							
						wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
					}
										
					deviceLogin.setDeviceStatus(Tools.OneString);
					deviceLoginFacade.insertDeviceLogin(deviceLogin);
										
//				    if(mClientSessionManager.getSessionId(serieNo) != null){
//				    	IoSession tempSession = mClientSessionManager.getSessionId(serieNo);
//					    mClientSessionManager.removeSessionId(serieNo);
//					    tempSession.closeNow();
//					    tempSession = null;
//				    }				    
                    session.setAttributeIfAbsent("devId", serieNo);    //每一个通道的id
					
					session.setAttributeIfAbsent("wdeviceInfo", wdeviceActiveInfos.get(0));  //记录设备信息
					devNo = String.valueOf(device_id);
					//session.setAttributeIfAbsent("wDevNo", devNo);    //保存device id 属性 到 devNo
				    
				    mClientSessionManager.addSessionId(serieNo, session);
				    
//				    respJsonData.setResultCode(Constant.SUCCESS_CODE);
				    respJsonData.setUpDeviceInfo(Constant.SUCCESS_CODE);
				    logger.info("!Constant.STAT_DEV_RESTRICT_IMEI:" + respJsonData);
				    
				}else{
					
					if (wdeviceActiveInfos.isEmpty()) {
						deviceLogin.setDeviceStatus("3");   //invalid IMEI;
						deviceLoginFacade.insertDeviceLogin(deviceLogin);
						
						respJsonData.setResultCode(Constant.BOND_FAIL_NO);
						respJsonData.setDevTime(dateFormat.format(new Date()));
						
//						if (mClientSessionManager.getSessionId(serieNo) != null) {
//							mClientSessionManager.removeSessionId(serieNo);
//						}
					
						session.closeNow();
								
						serieNo = null;
						productModel = null;
						firmwareEdition = null;
						devicePhone = null;
						belongProject = null;
						
						deviceLogin = null;
						wdeviceActiveInfo = null;
						wdeviceActiveInfos.clear();
						wdeviceActiveInfos = null;
						deviceLoginFacade = null;
						wdeviceActiveInfoFacade = null;
						
//						respJsonData.setResultCode(Constant.BOND_FAIL_NO);
						respJsonData.setUpDeviceInfo(Constant.BOND_FAIL_NO); //-2
						logger.info("wdeviceActiveInfos.isEmpty():" + respJsonData);
						return respJsonData;
					
					} else {
						// 正真的代码
						device_id = (Integer) wdeviceActiveInfos.get(0).getAt("device_id");

						EndServlet.setNetCheckOff(device_id);					
						
						beattim = (Integer) wdeviceActiveInfos.get(0).getAt("beattim");
						//abcdd
						ulfq = (Integer) wdeviceActiveInfos.get(0).getAt("ulfq");
						uLTe = (Integer) wdeviceActiveInfos.get(0).getAt("uLTe");
						int lfq = (Integer) wdeviceActiveInfos.get(0).getAt("lfq");
						respJsonData.setLfq(lfq); // rombo APP 操作不影响此值
						
						logger.info("Real code:ulfq=" + ulfq + ",uLTe=" + uLTe + ",lfq=" + lfq);
						String test_status = Tools.OneString;
						
						try {
							test_status = ((String) wdeviceActiveInfos.get(0).getAt("test_status")).trim();
							
							int iccid_auto = (Integer) wdeviceActiveInfos.get(0).getAt("iccid_auto");
							if (iccid_auto > 0)
								wdeviceActiveInfo.setDynIccid(iccid);
						} catch(Exception e) {
							logger.info("WTDevHandler.java iccid_auto pro exception occur!");
						}
						
						if (Constant.SYS_STAT_DYN_DEV_ULFQ) {   //false  dead code!  s b code, who write this code?
							try {
								logger.info("Constant.SYS_STAT_DYN_DEV_ULFQ-----");
								respJsonData.setuLTe(uLTe);															
								
								DeviceActiveInfo  dai = new DeviceActiveInfo();
								dai.setCondition("iccid='" + iccid + "'");
								DeviceActiveInfoFacade daiFd = ServiceBean.getInstance().getDeviceActiveInfoFacade();
								List<DataMap> listDF = daiFd.getSsidInfo(dai);
								if ( Tools.OneString.equals(test_status) ) {
									respJsonData.setuLFq(ulfq);		
									respJsonData.setuLTe(uLTe);										
								} else {
									if ( listDF.isEmpty() ) {
										ulfq = 0;
										wdeviceActiveInfo.setUlfq(ulfq);
										respJsonData.setuLFq(ulfq);		
										respJsonData.setuLTe(uLTe);		
										
									} else {
										String card_status = listDF.get(0).getAt("card_status").toString().trim();
										if ( tls.isNullOrEmpty(card_status) || 
												Tools.ZeroString.equals(card_status)) {
											ulfq = 0;
											wdeviceActiveInfo.setUlfq(ulfq);									
										} else {
											if ( ulfq <= 0 ) {
												ulfq = 100;
												wdeviceActiveInfo.setUlfq(ulfq);										
											}												
										}
										respJsonData.setuLFq(ulfq);
										respJsonData.setuLTe(uLTe);												
									}
								}
																
								if ( ulfq == 0 ) {
									respJsonData.setuLFqMode(Constant.DEV_LCT_GPS_TYPE);
									respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_OFF);
								} else {
									respJsonData.setuLFqMode(Constant.DEV_LCT_WIFI_GPS_TYPE);
									respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_ON);					
								}
							} catch(Exception e) {
								logger.info("dev:" + devId + " set uLFq error!");
								respJsonData.setuLFq(100);	
								respJsonData.setuLTe(0);		
								
								respJsonData.setuLFqMode(Constant.DEV_LCT_WIFI_GPS_TYPE);
								respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_ON);					
							}
						} else {
							
							if ( ulfq == 0 ) {
								respJsonData.setuLFqMode(Constant.DEV_LCT_GPS_TYPE);
								respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_OFF);
							} else {
								respJsonData.setuLFq(ulfq);								
								respJsonData.setuLFqMode(Constant.DEV_LCT_WIFI_GPS_TYPE);
								respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_ON);					
							}	
							logger.info("!Constant.SYS_STAT_DYN_DEV_ULFQ-----");
						}
												
						if (Tools.OneString.equals(test_status)) {
							// ulfq = 100;
							// uLTe = 0; // 为啥搞0?
							//uLTe = selectBrand(serieNo);
							respJsonData.setuLFq(100);
							// respJsonData.setuLTe(0);	
							respJsonData.setuLTe(uLTe);									
							respJsonData.setuLFqMode(Constant.DEV_LCT_WIFI_GPS_TYPE);
							respJsonData.setuLSosWifi(Constant.DEV_SOS_LCT_WIFI_TYPE_STATUS_ON);
							logger.info("Tools.OneString.equals(test_status)-----");
						}
												
						deviceLogin.setDeviceStatus(Tools.OneString);
						deviceLoginFacade.insertDeviceLogin(deviceLogin);
						
						wdeviceActiveInfo.setCondition("device_imei = '"+serieNo+"'");
						//wdeviceActiveInfo.setDevice_imei(serieNo);
						wdeviceActiveInfo.setDevice_phone(devicePhone);
						//wdeviceActiveInfo.setBelong_project(1);
						if ( "A907".equals(productModel) )
							wdeviceActiveInfo.setBelong_project(2);		//表示1部的功能机项目
						else
							wdeviceActiveInfo.setBelong_project(1);
						
						wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
					    
					    session.setAttributeIfAbsent("devId", serieNo);    //每一个通道的id
						devNo = String.valueOf(device_id);						
						session.setAttributeIfAbsent("wdeviceInfo", wdeviceActiveInfos.get(0));  //记录设备信息
						//session.setAttributeIfAbsent("wDevNo", devNo);    //保存device id 属性 到 devNo
						
					    mClientSessionManager.addSessionId(serieNo, session);

//					    respJsonData.setResultCode(Constant.SUCCESS_CODE);
					    respJsonData.setUpDeviceInfo(Constant.SUCCESS_CODE);
					    
					    logger.info("updateWdeviceActiveInfo  over-----------");
					}
					
				}
				
				String ts = wdeviceActiveInfos.get(0).getAt("test_status").toString().trim();
				int tsi = ( Tools.OneString.equals(ts) ) ? 1 : 0;
				respJsonData.setTs(tsi);
				
				String devTimeZoneID = wdeviceActiveInfos.get(0).getAt("time_zone").toString();				
				TimeZone timeZoneNY = TimeZone.getTimeZone(wdeviceActiveInfos.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
				//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
				if (Constant.timeUtcFlag) {
					TimeZone timeZone1 = TimeZone.getTimeZone("UTC");					
					dateFormat.setTimeZone(timeZone1);					
				} else {
					dateFormat.setTimeZone(timeZoneNY);
				}
				Date date = new Date(System.currentTimeMillis());
				respJsonData.setSynDevTime(dateFormat.format(date));
//				respJsonData.setDevTime(outputFormat.format(date));
				
				
				SimpleDateFormat utcTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
				
				SynTime synTime = new SynTime();
				utcTimeFormat.setTimeZone(utcTimeZone);
				synTime.setUtcTime(utcTimeFormat.format(date));
				synTime.setTimeZone(devTimeZoneID);

				if (Constant.timeUtcFlag) {
					respJsonData.setSynDevTime(utcTimeFormat.format(date));
					//synTime.setTimeZone("Europe/London");    //Europe/London   //UTC
					synTime.setTimeZone("UTC");    //Europe/London   //UTC					
				}
				
				respJsonData.setSynTime(synTime);
				
				SynTime2 synTime2 = new SynTime2();
				long utcTimeStamp = System.currentTimeMillis();
				synTime2.setTimeZone("UTC");
				synTime2.setUtcTime(utcTimeStamp);
				respJsonData.setSynTime2(synTime2);
				
				Beattim setBeattim = new Beattim();
				setBeattim.setBeatTim(Integer.toString(beattim));
				//setBeattim.setBeatTim("30");

				respJsonData.setSetBeattim(setBeattim);
				
								
				// 返回如下状态给设备：
				//coolLedOn 设备炫酷模式是否启用灯开关
				//sosLedOn  设备紧急模式是否启用灯开关
				//sosOn 设备紧急模式是否启用
				//sosSoundOn 设备紧急模式是否启用声音开关
				//esafeOn 设备启用位置电子围栏开关 
				//ssidEsafeState wifi电子围栏开关
				//mac         设备启用家庭WIFI的mac地址
				//ecoMode     省电模式
				//debugMode   设备调试模式
				//fotaNewest  设备固件最新的可升级FOTA版本号
				wdeviceActiveInfo.setCondition("a.device_imei = '"+serieNo+"'");										
				wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				SyncDevState syncDev = new SyncDevState(); 
				
				syncDev.setCoolLedOn(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("led_on").toString().trim()));
				syncDev.setSosLedOn(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("sos_led_on").toString().trim()));
				syncDev.setSosOn(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("urgent_mode").toString().trim()));
				syncDev.setSosSoundOn(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("callback_on").toString().trim()));
				syncDev.setEsafeOn(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("esafe_on").toString().trim()));
				syncDev.setSsidEsafeState(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("ssid_esafe_state").toString().trim()));
				syncDev.setMac(wdeviceActiveInfos.get(0).
						getAt("mac").toString().trim());
				syncDev.setEcoMode(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("eco_mode").toString().trim()));
				
				//ababab
				/*
				if ( Tools.OneString.equals(wdeviceActiveInfos.get(0).
						getAt("eco_mode").toString().trim()) ) {
					if (lih.IsAmericaDev(wdeviceActiveInfos.get(0).getAt("time_zone").toString())) 
						session.write("{\"ssNet\":0,\"userId\":0,\"pg\":0}");															
					else
						session.write("{\"ssNet\":1,\"userId\":0,\"pg\":0}");															
				} else {
					session.write("{\"ssNet\":0,\"userId\":0,\"pg\":0}");															
				}
				*/
						
				syncDev.setDebugMode(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("debug_mode").toString().trim()));								
				syncDev.setFotaNewest("");
				syncDev.setOffT(wdeviceActiveInfos.get(0).
						getAt("time_poff").toString().trim());
				syncDev.setOnT(wdeviceActiveInfos.get(0).
						getAt("time_pon").toString().trim());
				syncDev.setOffOnFlag(tls.getSafeIntFromString(wdeviceActiveInfos.get(0).
						getAt("autopdn_status").toString().trim()));
				
			    WdevDiscovery devDisc = new WdevDiscovery();
				devDisc.setCondition("device_id = "+device_id);
				List<DataMap> devDiscs = wdeviceActiveInfoFacade.getDevDiscovery(devDisc);
				
				if (devDiscs.size() > 0) {
					syncDev.setSosDura(tls.getSafeIntFromString(devDiscs.get(0).
							getAt("how_long").toString().trim())); 
					syncDev.setSosActionTime(devDiscs.get(0).
							getAt("action_time").toString().trim());
					//syncDev.setSosActionTime(dateFormat.format(devDiscs.get(0).getAt("action_time")));
//					syncDev.setSosActionTime(dateFormat.format(devDiscs.get(0).getAt("action_time")));
				} else {
					syncDev.setSosDura(0); 
					syncDev.setSosActionTime(dateFormat.format(date));   //default SosActinTime String
				}
				
				respJsonData.setSyncDevState(syncDev);
				
				String[] firmVerInfo = firmwareEdition.split("_");
				logger.info("firmwareEdition:" + firmwareEdition);
				try {
					if ( Constant.IS_APPSERVER_6688 ) {
						PhoneInfo po = new PhoneInfo();
						po.setCondition("device_imei ='" + serieNo + "'");
						po.setCountNum("59.167.239.62");
						po.setPort(6688);
						po.sethPort(8087);
		
						ServiceBean.getInstance().getPhoneInfoFacade()
								.updateDeviceServiceActiveInfo(po);
					}
				} catch (Exception e) {
					
				}
								
				WupFirmware wupFirmware = new WupFirmware();
				WupFirmwareFacade upFirmFacade = serviceBean.getWupFirmwareFacade();
				wupFirmware.setCondition("project_name = '" + firmVerInfo[0].trim() + "'");
				
				List<DataMap> firmwareInfos = upFirmFacade.getWupFirmware(wupFirmware);
				logger.info("firmwareInfos:" + firmwareInfos);
				if (firmwareInfos.size() > 0 && "1".equals(firmwareInfos.get(0).getAt("fup").toString().trim())) {
					String version_us = firmwareInfos.get(0).getAt("version_us").toString();
					String version_eu = firmwareInfos.get(0).getAt("version_eu").toString();
					String version_cn = firmwareInfos.get(0).getAt("version_cn").toString();
				
					//System.out.println("IMEI: " + serieNo + " version info---US: " + version_us + " EU: " + version_eu + " CN: " + version_cn);
					logger.info("IMEI: " + serieNo + " version info---US: " + version_us + " EU: " + version_eu + " CN: " + version_cn);
					//Example version format: YK902_80M_DE001B_V100_US
					if ("US".equals(firmVerInfo[4]) && version_us != null && !"".equals(version_us)) {
						respJsonData.setUpdateVer(version_us);
					} else if ("EU".equals(firmVerInfo[4]) && version_eu != null && !"".equals(version_eu)) {
						respJsonData.setUpdateVer(version_eu);
					} else if ("CN".equals(firmVerInfo[4]) && version_cn != null && !"".equals(version_cn)) {
						respJsonData.setUpdateVer(version_cn);
					} else {
						respJsonData.setUpdateVer(null);
					}
					
				} else {
					respJsonData.setUpdateVer(null);
				}

				/*
				String dummySn = tls.getDummySn();
				if ( !tls.isNullOrEmpty(dummySn) && dummySn.equals(serieNo) ) {
					String dummyVer = tls.getDummyVer();
					respJsonData.setUpdateVer(dummyVer);					
				}*/

				//更新设备心跳时间 
				//LocationInfoHelper.updateDeviceStatus(device_id, 1, dateFormat.format(date));
				
				if ( Constant.timeUtcFlag )//true
					lih.updateDeviceStatus(device_id, 1, tls.getUtcDateStrNow());
				else 																					
					lih.updateDeviceStatus(device_id, 1, dateFormat.format(date));

				//appserver.paby.com 8080需要执行如下代码
				/*
				try {
					PhoneInfo po = new PhoneInfo();
					po.setCondition("device_imei ='" + serieNo + "' limit 1");
	
					List<DataMap> listP = ServiceBean.getInstance()
							.getPhoneInfoFacade()
							.selectDeviceEnterServiceActiveInfo(po);
					String ip = Constant.SYS_SERVER_DN1;
					int port = 6690;	//6688;	//6690
					int httpPort=8080;	//8080;	//8087
					if (listP.size() > 0) {				
						po.setCondition("device_imei ='" + serieNo + "'");
						po.setCountNum(ip);
						po.setPort(port);
						po.sethPort(httpPort);
		
						ServiceBean.getInstance().getPhoneInfoFacade()
								.updateDeviceServiceActiveInfo(po);
					}
				} catch(Exception e) {
					
				}
				*/			
				
				//如果设备登录的时候发现有此设备相关 的用户，对应的APP在前台，需要下发“下行设备指令-----”	
				proPwronDevGpsStatus(device_id, serieNo);
								
				serieNo = null;
				productModel = null;
				firmwareEdition = null;
				devicePhone = null;
				belongProject = null;
				
				deviceLogin = null;
				wdeviceActiveInfo = null;
				wdeviceActiveInfos.clear();
				wdeviceActiveInfos = null;
				deviceLoginFacade = null;
				wdeviceActiveInfoFacade = null;
				
				date = null;
				timeZoneNY = null;
				utcTimeFormat = null;
				utcTimeZone = null;
				synTime = null;
				setBeattim = null;
//				outputFormat = null;
				devDisc = null;
				devDiscs.clear();
				devDiscs = null;
				
			}  else if(cmd.equals(AdragonConfig.heartbeat)){  //heartbeat case
				
				
				String sig_level = reqJsonData.getSignal();
				String battery = reqJsonData.getBattery();
				String charging_status = reqJsonData.getPlugFlag();
				charging_status = tls.getOneZeroStringFromBool(charging_status);
				
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					//device_id = (Integer) dm.getAt("device_id"); 
					TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
					dateFormat.setTimeZone(devTimeZone);
					dm = null;
				}
				
//				System.out.println("---device_id : "+ device_id);
			
//				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
//
//				deviceActiveInfo.setCondition("device_id = " + device_id);
////				deviceActiveInfo.setDevice_id(device_id);
//				deviceActiveInfo.setDev_status(Tools.OneString);
//				deviceActiveInfo.setSig_level(sig_level);
//				deviceActiveInfo.setBattery(battery);
//				deviceActiveInfo.setCharging_status(charging_status);
//				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
				
				//update table: wDeviceExtra.sig_level charging_status
				if (sig_level != null && !"".equals(sig_level)) {

					WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
					deviceActiveInfo.setCondition("device_id = " + device_id);
					deviceActiveInfo.setSig_level(sig_level);
					deviceActiveInfo.setCharging_status(charging_status);
					deviceActiveInfo.setDev_status(Tools.OneString);
					deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
					serviceBean.getWdeviceActiveInfoFacade().updatewDeviceExtra(deviceActiveInfo);
					deviceActiveInfo = null;
				}
				
				//update table: wDeviceExtra.dev_status dev_timestamp 
//			    serviceBean.getWdeviceActiveInfoFacade().updatewDeviceExtraDevStatus(deviceActiveInfo);
				//update table: wdevice_active_info.battery
				if (battery != null && !"".equals(battery)) {
					WdeviceActiveInfo devInfo = new WdeviceActiveInfo();
					devInfo.setCondition("device_id = " + device_id);
					devInfo.setBattery(battery);
					serviceBean.getWdeviceActiveInfoFacade().updateWdeviceActiveInfo(devInfo);
					devInfo = null;
				}
				
				

				
				DevNotifyApp dna = new DevNotifyApp();
				boolean res = false;
				
				String devTime = dna.getSessionDevTime(session);
				
				JSONObject extPara = new JSONObject();
				Integer signalInt = Integer.parseInt(sig_level);
				Integer battInt = Integer.parseInt(battery);
				
				//jon.put("user_id", userId);
				extPara.put("signal", signalInt);
				extPara.put("battery", battInt);
								
				res = dna.proDevBeatNtfy(device_id, devId, devTime, reqJsonData, extPara.toString());
					
				if ( res )
					respJsonData.setAck(Tools.OneString);
					//respJsonData.setResultCode(Constant.SUCCESS_CODE);

				//ababab
				/*
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				ServiceBean serviceBean = ServiceBean.getInstance();
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();

				
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");										
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);

				if ( Tools.OneString.equals(wdeviceActiveInfos.get(0).
						getAt("eco_mode").toString().trim()) ) {
					if ( reqJsonData.getNettype() != 2 ) {
						if (lih.IsAmericaDev(wdeviceActiveInfos.get(0).getAt("time_zone").toString()))
							session.write("{\"ssNet\":0,\"userId\":0,\"pg\":1}");
						else
							session.write("{\"ssNet\":1,\"userId\":0,\"pg\":1}");
					}
				} else {
					if ( reqJsonData.getNettype() != 3 )					
						session.write("{\"ssNet\":0,\"userId\":0,\"pg\":1}");															
				}
				*/
				
				devTime = null;
				dna = null;
				extPara = null;								
				
				sig_level = null;
				battery = null;

			
          }  else if(cmd.equals(AdragonConfig.ping)){  //device ping case
				
				respJsonData.setPing(1);

		  } else if(cmd.equals(AdragonConfig.getDevStateRes)) {  
				
				
//				String sig_level = reqJsonData.getSignal();
				
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					//device_id = (Integer) dm.getAt("device_id"); 
					TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
					dateFormat.setTimeZone(devTimeZone);
					dm = null;
				}
				

//				System.out.println("---device_id : "+ device_id);
			
				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
//				WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				deviceActiveInfo.setCondition("device_id = " + device_id);
//				deviceActiveInfo.setDevice_id(device_id);
				deviceActiveInfo.setDev_status(Tools.OneString);
//				deviceActiveInfo.setSig_level(sig_level);
				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
				
//				List<DataMap> list = deviceInfoFacade.getwDeviceExtra(deviceActiveInfo);
				
//				if (list.size() > 0) {
//					deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
//				} else {
//					deviceActiveInfo.setDevice_id(device_id);
//					deviceInfoFacade.insertwDeviceExtra(deviceActiveInfo);
//				}
				
				serviceBean.getWdeviceActiveInfoFacade().updatewDeviceExtraDevStatus(deviceActiveInfo);
				
				respJsonData.setAck(Tools.OneString);
//				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				
//				sig_level = null;
				deviceActiveInfo = null;
//				deviceInfoFacade = null;

			}  else if(cmd.equals(AdragonConfig.signal)){  //signal bar strength case
				
				
				String sig_level = reqJsonData.getSignal();
				
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					//device_id = (Integer) dm.getAt("device_id"); 
					TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
					dateFormat.setTimeZone(devTimeZone);
					dm = null;					
				}
				
				System.out.println("---device_id : "+ device_id);
			
				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				deviceActiveInfo.setCondition("device_id = " + device_id);
//				deviceActiveInfo.setDevice_id(device_id);
//				deviceActiveInfo.setDev_status(Tools.OneString);
				deviceActiveInfo.setSig_level(sig_level);
				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
				
//				List<DataMap> list = deviceInfoFacade.getwDeviceExtra(deviceActiveInfo);
				
//				if (list.size() > 0) {
//					deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
//				} else {
//					deviceActiveInfo.setDevice_id(device_id);
//					deviceInfoFacade.insertwDeviceExtra(deviceActiveInfo);
//				}
				
				deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
				
				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				
				sig_level = null;
				deviceActiveInfo = null;
				deviceInfoFacade = null;

			} else if (cmd.equals(AdragonConfig.offline)) {
				
				respJsonData.setOffline(Constant.SUCCESS_CODE);
			
				String dev_imei = reqJsonData.getSerie_no().toString();
				
				String battery = reqJsonData.getBattery();
				
				if ( !devId.equals(dev_imei) ) {
					ba.insertVisit(null,null, "80", "sysOffline. exception para dev_imei wrong" );								
				}
					
				
				/*
				if (dev_imei != null && !"".equals(dev_imei)) {
					
					WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
					WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
					deviceActiveInfo.setCondition("a.device_imei = " + dev_imei);
					
					List<DataMap> deviceInfos = deviceInfoFacade.getWdeviceActiveInfo(deviceActiveInfo);
					
					if (deviceInfos.size() > 0) {
						device_id = (Integer) deviceInfos.get(0).getAt("device_id");
						TimeZone devTimeZone = TimeZone.getTimeZone(deviceInfos.get(0).getAt("time_zone").toString()); 
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						df.setTimeZone(devTimeZone);
						deviceActiveInfo.setCondition("device_id = " + device_id);
						deviceActiveInfo.setDev_status(Tools.ZeroString);
						deviceActiveInfo.setDev_timestamp(df.format(new Date()));
						deviceInfoFacade.updatewDeviceExtraDevStatus(deviceActiveInfo);
						
						
						DeviceLogin deviceLogin = new DeviceLogin();
						deviceLogin.setDeivceImei(dev_imei);
						deviceLogin.setDeviceStatus(Tools.ZeroString);
						deviceLogin.setDateTime(new Date());
						deviceLogin.setBelongProject(Tools.OneString);
						DeviceLoginFacade deviceLoginFacade = ServiceBean.getInstance().getDeviceLoginFacade();
						deviceLoginFacade.insertDeviceLogin(deviceLogin);

						//产生设备离线消息
						LocationInfoHelper.proDevOfflineMsg(device_id);
					}
					
					if(mClientSessionManager.getSessionId(dev_imei) != null) {
						mClientSessionManager.removeSessionId(dev_imei);
						session.removeAttribute("wdeviceInfo");
						session.removeAttribute("devId");
					}
					
					
				
					session.closeNow();
				}
				*/
				
				if (battery != null && !"".equals(battery)) {
					WdeviceActiveInfo devInfo = new WdeviceActiveInfo();
					devInfo.setCondition("device_id = " + device_id);
					devInfo.setBattery(battery);
					serviceBean.getWdeviceActiveInfoFacade().updateWdeviceActiveInfo(devInfo);
					devInfo = null;
				}
				
				logger.info("session: " + session.getId() + " dev: " + session.getAttribute("devId") + " normally offline!");

				ServerHandler sh = new ServerHandler();				
				sh.sysOffline(session);
				session.closeNow();
///				session.closeOnFlush();
		
				
//				
//				if(session.containsAttribute("wdeviceInfo")){
//					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
//					Integer device_id = (Integer) dm.getAt("device_id"); 
//					String devIMEI = (String) dm.getAt("device_imei");
//					dm = null;		
//
//					session.removeAttribute("wdeviceInfo");
//					session.removeAttribute("devId");
//					session.closeNow();					
//					
//					WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
//					WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
//					
//					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					deviceActiveInfo.setCondition("device_id = " + device_id);
//					deviceActiveInfo.setDev_status(Tools.ZeroString);
//					deviceActiveInfo.setDev_timestamp(df.format(new Date()));
//					deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
//					
//					DeviceLogin deviceLogin = new DeviceLogin();
//					deviceLogin.setDeivceImei(devIMEI);
//					deviceLogin.setDeviceStatus(Tools.ZeroString);
//					deviceLogin.setDateTime(new Date());
//					deviceLogin.setBelongProject(Tools.OneString);
//					DeviceLoginFacade deviceLoginFacade = ServiceBean.getInstance().getDeviceLoginFacade();
//					deviceLoginFacade.insertDeviceLogin(deviceLogin);
//
//					//产生设备离线消息
//					LocationInfoHelper.proDevOfflineMsg(device_id);
//					
//					String devId = (String)session.getAttribute("devId");
//					if(mClientSessionManager.getSessionId(devId) != null) {
//						mClientSessionManager.removeSessionId(devId);
//					}
//					
//				} else				
//					session.closeNow();
//				
//				
			} else if (cmd.equals(AdragonConfig.charging)) {
				
				String battery = reqJsonData.getBattery();
				
				Boolean res = false;
				
				DevNotifyApp dna = new DevNotifyApp();

				String devTime = dna.getSessionDevTime(session);
				
				JSONObject extPara = new JSONObject();
				String charging_status = reqJsonData.getPlugFlag();
				
				extPara.put("plugFlag", charging_status);
				
				res = dna.proChargingCmd(device_id, devId, devTime, reqJsonData, extPara.toString());

				if ( res )
					respJsonData.setCharging(Constant.SUCCESS_CODE);
				
				
				if (battery != null && !"".equals(battery)) {
					WdeviceActiveInfo devInfo = new WdeviceActiveInfo();
					devInfo.setCondition("device_id = " + device_id);
					devInfo.setBattery(battery);
					serviceBean.getWdeviceActiveInfoFacade().updateWdeviceActiveInfo(devInfo);
					devInfo = null;
				}
				
				devTime = null;
				dna = null;
				extPara = null;
								
				charging_status = null;

			} else if(cmd.equals(AdragonConfig.reqFamilyNumber)){
				
			} else if(cmd.equals(AdragonConfig.uploadLctData) || cmd.equals(AdragonConfig.getLocationRes)){  //定位					
				respJsonData = proLctMgrBtr(reqJsonData, devId, device_id, cmd, session);
				
			// 又是一段垃圾代码
			} else if(cmd.equals(AdragonConfig.uploadLctData) || cmd.equals(AdragonConfig.getLocationRes)){  //定位
//			    DataMap deviceInfo = null;
								
				boolean bool_is_update = true;  // move here
				String locationType = reqJsonData.getType(); // 1 gaode GPS; 2 gaode lbs; 3 gaode wifi; 4 google gps; 5 google lbs/wifi
				String battery = reqJsonData.getBattery();
				String lctTime = reqJsonData.getLctTime();
				String fall = Tools.OneString;  //reqJsonData.getFall();  //1表示戴上，0表示脱落
				double lng1 = 0;
				double lat1 = 0;
				int stepCount = 0;
				
				Location respLct = new Location();
				
				if (!"".equals(reqJsonData.getStepNumber()) && reqJsonData.getStepNumber() != null) {
					stepCount = Integer.parseInt(reqJsonData.getStepNumber().trim());
				} else if (reqJsonData.getStepCount() != null) {
					stepCount = reqJsonData.getStepCount();
				}
				
				//System.out.println("stepCount: " + stepCount);
				
				LocationInfo locationInfo = new LocationInfo();
				locationInfo.setSerieNo(devId);		//smile add
				

//			    if (session.containsAttribute("wdeviceInfo")) {
//			    	deviceInfo = (DataMap) session.getAttribute("wdeviceInfo");
//			    }			    


			    //2016.12.17,不再需要设备心跳时间戳记录
			    //LocationInfoHelper.updateDeviceStatus(device_id, 0, null);
				locationInfo.setDevice_id(device_id);
				
				if(Tools.OneString.equals(locationType)) {
					GPSInfoAdr gps = reqJsonData.getGps();
					
					String longitude = gps.getLon(); //经度
					String latitude = gps.getLat(); //纬度
					String accuracy = gps.getAcc(); //精确度
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
					String result = "";
					JSONObject object;
					String location = "";
					String[] str;
					
					if (devId != null && !"".equals(devId) && longitude != null
						&& !"".equals(longitude) && latitude != null
						&& !"".equals(latitude) && accuracy != null
						&& !"".equals(accuracy) && locationType != null
						&& !"".equals(locationType)) {

						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						longitude = String.format("%.12f", lng1);
						latitude = String.format("%.12f", lat1);
						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						
						if (lng1 != 0 && lat1 != 90) { // 直接过滤
							locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
							locationInfo.setOrderBy("upload_time");
							locationInfo.setSort(Tools.OneString); // 按upload_time降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1

							List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
							//boolean bool_is_update = true;
							
							
							
							if (locationInfos.size() > 0) { 
								String id = "" + locationInfos.get(0).getAt("id");
								double lng2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("longitude"));
								double lat2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("latitude"));	
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								double diffDist1 = Constant.getDistance(lat1, lng1, lat2, lng2);
								
//								if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
								if ( diffDist1 < Constant.GPS_DIFF_DIST_GAP && "1".equals(preLctType)) {
									locationInfo.setCondition("id ='" + id + "'");								
//									locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
//									locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
									//locationInfo.SetLatitude(latitude);		//smile add
									//locationInfo.setLongitude(longitude);	//smile add
									locationInfo.setAccuracy(Float.parseFloat(accuracy));	//smile add
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setUploadTime(lctUpDate);
									
									serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
									bool_is_update = false;
									respJsonData.setLctGps(Constant.SUCCESS_CODE);
									respJsonData.setLctTime(lctTime);
								} else {
									result = HttpRequest
											.sendGet(
													"http://restapi.amap.com/v3/assistant/coordinate/convert",
													"locations="
															+ longitude
															+ ","
															+ latitude
															+ "&coordsys=gps&output=json&key=" + Constant.KEY_1);
									if ("-1".equals(result)) {

										respJsonData.setLctGps(Constant.FAIL_CODE);
										respJsonData.setLctTime(lctTime);
//										locationInfo.setChangeLongitude(Tools.ZeroString);
//										locationInfo.setChangeLatitude(Tools.ZeroString);
										locationInfo = null;
										bool_is_update = false;
									} else {
										object= JSONObject.fromObject(result);;
										
										location = object.getString("locations");
										str = location.split(",");
										//if (str.length > 0) {
											locationInfo.setChangeLongitude(str[0]);
											locationInfo.setChangeLatitude(str[1]);
										//}
											
											
										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setLongitude(longitude);
										locationInfo.setLatitude(latitude);
										locationInfo.setAccuracy(Float.parseFloat(accuracy));
										locationInfo.setLocationType(locationType);
										locationInfo.setUploadTime(lctUpDate);
										locationInfo.setFall(fall);								
										locationInfo.setBelongProject(Tools.OneString);
										locationInfo.setStepCount(stepCount);
											
										serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
										respJsonData.setLctGps(Constant.SUCCESS_CODE);
										respJsonData.setLctTime(lctTime);
										bool_is_update = true;
									}
								}
							} else {

								result = HttpRequest
										.sendGet(
												"http://restapi.amap.com/v3/assistant/coordinate/convert",
												"locations="
														+ longitude
														+ ","
														+ latitude
														+ "&coordsys=gps&output=json&key=" + Constant.KEY_1);
								if ("-1".equals(result)) {

									respJsonData.setLctGps(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
//									locationInfo.setChangeLongitude(Tools.ZeroString);
//									locationInfo.setChangeLatitude(Tools.ZeroString);
									locationInfo = null;
									bool_is_update = false;
								} else {
									object= JSONObject.fromObject(result);;
									
									location = object.getString("locations");
									str = location.split(",");
									//if (str.length > 0) {
										locationInfo.setChangeLongitude(str[0]);
										locationInfo.setChangeLatitude(str[1]);
									//}
									
										
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(longitude);
									locationInfo.setLatitude(latitude);
									locationInfo.setAccuracy(Float.parseFloat(accuracy));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									locationInfo.setFall(fall);								
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
										
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	

									respJsonData.setLctGps(Constant.SUCCESS_CODE);
									respJsonData.setLctTime(lctTime);
									bool_is_update = true;
								}
							}
							
							locationInfos.clear();
							locationInfos = null;
								
							//yonghu start
							/*
							LocationInfoHelper lih = new LocationInfoHelper();
							
							lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
								cmd.equals(AdragonConfig.uploadLctData));
							lih = null; */
							//yonghu end							

						} else {
							locationInfo = null;
							respJsonData.setLctGps(Constant.FAIL_CODE);
						}
					}
					
					longitude = null;
					latitude = null;
					accuracy = null;
					sdtf = null;
					lctUpDate = null;
					result = null;
					object = null;
					location = null;
					str = null;
					
					//Gaode map lbs api
				} else if ("2".equals(locationType)){      
					NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
					String network = netWorkInfo.getNetWork();
					String cdma = netWorkInfo.getCdma();
					String smac = netWorkInfo.getSmac();
					String bts = netWorkInfo.getBts();
					String nearbts = netWorkInfo.getNearbts();
					String serverip = netWorkInfo.getServerip();
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					String id = "";
					String jsonToString = "";
					double lng2 = 0.0;
					double lat2 = 0.0;
					double distDiff2 = 0.0;
					int stepDiff = 0;
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
					
					map.put("accesstype", Tools.ZeroString);
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("key", Constant.KEY);
//					if (serverip != null && !"".equals(serverip)) {
//						map.put("serverip", serverip);
//					} else {
//						map.put("serverip", Constant.SERVER_IP);
//					}
					map.put("serverip", serverip);
					
					locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
					locationInfo.setOrderBy("upload_time");
					locationInfo.setSort(Tools.OneString); // 按upload_time降序
					locationInfo.setFrom(0);
					locationInfo.setPageSize(1); // 0至1

					List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
					
					if (locationInfos.size() > 0) {
						
						id = locationInfos.get(0).getAt("id").toString();
						lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
						lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
						Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
						Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
						
						String preLctType = locationInfos.get(0).getAt("location_type").toString();
						
						stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
						
						long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
						
						if (stepDiff < Constant.LBS_DIFF_STEPS && "2".equals(preLctType)) {
							locationInfo.setCondition("id ='" + id + "'");
							
//							locationInfo.setLongitude(locationInfos.get(0).getAt("longitude").toString());
//							locationInfo.setLatitude(locationInfos.get(0).getAt("latitude").toString());
//							locationInfo.setAccuracy(Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString()));
//							locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
//							locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
//							locationInfo.setLocationType(locationInfos.get(0).getAt("location_type").toString());
							
							locationInfo.setUploadTime(lctUpDate);
							locationInfo.setStepCount(stepCount);
							//locationInfo.setLatitude(String.valueOf(lat2));    //smile add
							//locationInfo.setLongitude(String.valueOf(lng2));    //smile add
							locationInfo.setBattery(Integer.parseInt(battery));							
							Float acc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
							locationInfo.setAccuracy(acc);   //smile add
							serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);	
							
							bool_is_update = false;
							
							//yonghu start
							/*
							
							LocationInfoHelper lih = new LocationInfoHelper();								
							lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							*/
							//yonghu end	
							
							respJsonData.setLctLbs(Constant.SUCCESS_CODE);
							respJsonData.setLctTime(lctTime);
							
						} else {
							jsonToString = HttpRequest.sendGetToGaoDe(
									Constant.LOCATION_URL, map);

							if ("-1".equals(jsonToString)) {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
							} else {
								JSONObject jsons = JSONObject.fromObject(jsonToString);
								String status = jsons.getString("status"); 
								if (status.equals(Tools.OneString)) { 
									String results = jsons.getString("result"); 
									JSONObject jsonResult = JSONObject.fromObject(results);
									String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
									String radius = jsonResult.getString("radius"); 
									if (location != null) {	
										String[] locations = location.split(",");

										locationInfo.setLongitude(locations[0]);
										locationInfo.setLatitude(locations[1]);
										locationInfo.setChangeLongitude(locations[0]);
										locationInfo.setChangeLatitude(locations[1]);
										locationInfo.setAccuracy(Float.parseFloat(radius));
										locationInfo.setLocationType(locationType);
										locationInfo.setUploadTime(lctUpDate);
										
										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setFall(fall);
										locationInfo.setBelongProject(Tools.OneString);
										locationInfo.setStepCount(stepCount);
										
										respLct.setLon(locations[0]);
										respLct.setLat(locations[1]);
										respLct.setAcc(radius);
			
										lng1 = Double.parseDouble(locations[0]);
										lat1 = Double.parseDouble(locations[1]);
										
										distDiff2 = Constant.getDistance(lat1, lng1, lat2, lng2);
										
										if (distDiff2 > Constant.LBS_INVALID_DIST_GAP && stepDiff < Constant.LBS_DIFF_STEPS && !("2".equals(preLctType))) {
											locationInfo = null;
											respJsonData.setLctLbs(Constant.FAIL_CODE);
										} else {
//											if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
											if (distDiff2 < Constant.LBS_DIFF_DIST_GAP || (stepDiff < Constant.LBS_DIFF_STEPS && Float.parseFloat(radius) > preLctAcc)) { 
												locationInfo.setCondition("id ='" + id + "'");
												locationInfo.setUploadTime(lctUpDate);
												locationInfo.setStepCount(stepCount);
												serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);	
												bool_is_update = false;
											} else {
												serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
												bool_is_update = true;
											}

											//yonghu start\
											/*
											
											LocationInfoHelper lih = new LocationInfoHelper();								
											lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
													cmd.equals(AdragonConfig.uploadLctData));
											lih = null;
											*/
											//yonghu end	
											
											respJsonData.setLctLbs(Constant.SUCCESS_CODE);
											respJsonData.setLocation(respLct);
											respJsonData.setLctTime(lctTime);

										}
										
									} else {
										locationInfo = null;
										respJsonData.setLctLbs(Constant.FAIL_CODE);
										respJsonData.setLctTime(lctTime);
									}
								} else if (status.equals(Tools.ZeroString)) { 
									locationInfo = null;
									respJsonData.setLctLbs(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								} else if (status.equals("-1")) {
									locationInfo = null;
									respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
									respJsonData.setLctTime(lctTime);
								}
							}
							
						}
					} else {
						
						jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);

						if ("-1".equals(jsonToString)) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals(Tools.OneString)) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								String radius = jsonResult.getString("radius"); 
								if (location != null) {	
									String[] locations = location.split(",");

									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
		
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									bool_is_update = true;
									
									//yonghu start
									/*
									
									LocationInfoHelper lih = new LocationInfoHelper();								
									lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
											cmd.equals(AdragonConfig.uploadLctData));
									lih = null;
									*/
									//yonghu end	
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius);
									
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									locationInfo = null;
									respJsonData.setLctLbs(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								}
							} else if (status.equals(Tools.ZeroString)) { 
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							} else if (status.equals("-1")) {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
								respJsonData.setLctTime(lctTime);
							}
						}
						
					}
					
					netWorkInfo = null;
					network = null;
					cdma = null;
					smac = null;
					bts = null;
					nearbts = null;
					serverip = null;
					sdtf = null; 
					lctUpDate = null;
					jsonToString = null;
					map.clear();
					map = null;
					locationInfos.clear();
					locationInfos = null;
					
				} else if ("3".equals(locationType)) { // wifi gaode map api
					
					Boolean lctWIFIFlag = false;
					
					WifiInfoAdr wifi = reqJsonData.getWifi();
					String smac = wifi.getSmac();
					String mmac = wifi.getMmac();
					String macs = wifi.getMacs();
					String serverip = wifi.getServerip();
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));

					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

					map.put("accesstype", Tools.OneString);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("mmac", mmac);
					map.put("macs", macs);
					map.put("key", Constant.KEY);
//					if (serverip != null && !"".equals(serverip)) {
//						map.put("serverip", serverip);
//					} else {
//						map.put("serverip", Constant.SERVER_IP);
//					}
					map.put("serverip", serverip);
					
				try {
					String jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					
					if ("-1".equals(jsonToString)) {
//						locationInfo = null;
						lctWIFIFlag = false;
						respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
						respJsonData.setLctTime(lctTime);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						
						Boolean isValidWIFI = false;
						
						if (status.equals(Tools.OneString)) { 
							String results = jsons.getString("result");
							JSONObject jsonResult = JSONObject
									.fromObject(results);
							
							String type = jsonResult.optString("type");
							
							if ("0".equals(type)) {
//								locationInfo = null;
								lctWIFIFlag = false;
								respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							} else {
								
								String location = jsonResult.getString("location"); 
								String radius = jsonResult.getString("radius");
								String[] locations = location.split(",");
								
								locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
								
								locationInfo.setOrderBy("upload_time");
								locationInfo.setSort(Tools.OneString); // 按upload_time降序
								locationInfo.setFrom(0);
								locationInfo.setPageSize(1); // 0至1

								List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
								bool_is_update = true;	
								
								String id = "";
								if (locationInfos.size() > 0) { // 说明有数据
									id = locationInfos.get(0).getAt("id").toString();
									double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
									double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
									int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
									
									Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
									
									String preLctType = locationInfos.get(0).getAt("location_type").toString();
									
									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);
									
									double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
									int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);
										
									if (diffStep2 > Constant.WIFI_DIFF_STEPS || (diffDist2 > Constant.WIFI_DIFF_DIST_GAP || Float.parseFloat(radius) < preLctAcc)) {
										bool_is_update = true;
									} else {
										bool_is_update = false;
									}
									
									if (diffDist2 > Constant.IS_VALID_WIFI && diffStep2 < Constant.WIFI_DIFF_STEPS && ("3".equals(preLctType))) {
										isValidWIFI = false;
										
									} else {
										isValidWIFI = true;
									}

//									bool_is_update = Constant.getDistance(lat1,
//											lng1, lat2, lng2,
//											Constant.EFFERT_DATA); // 若为true表示有效数据
								} else {
									bool_is_update = true;
									isValidWIFI = true;
								}

								respLct.setLon(locations[0]);
								respLct.setLat(locations[1]);
								respLct.setAcc(radius);
								
								locationInfos.clear();
								locationInfos = null;

								if (isValidWIFI) {
									if (bool_is_update) {
										
										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setLongitude(locations[0]);
										locationInfo.setLatitude(locations[1]);
										locationInfo.setChangeLongitude(locations[0]);
										locationInfo.setChangeLatitude(locations[1]);
										locationInfo.setAccuracy(Float.parseFloat(radius));
										locationInfo.setLocationType(locationType);
										locationInfo.setUploadTime(lctUpDate);
										locationInfo.setFall(fall);
										locationInfo.setBelongProject(Tools.OneString);
										locationInfo.setStepCount(stepCount);
										
										serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									} else {
											
										locationInfo.setCondition("id ='" + id + "'");
										locationInfo.setUploadTime(lctUpDate);
										locationInfo.setStepCount(stepCount);
										ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
									}
									
									//yonghu start
									/*
									
									LocationInfoHelper lih = new LocationInfoHelper();
									//BaseAction.insertVisit("-1", null, null, "proLctInfo");
									lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
											cmd.equals(AdragonConfig.uploadLctData));
									lih = null;
									*/
									//yonghu end	
									
									lctWIFIFlag = true;
									respJsonData.setUploadlctWIFI(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {	
//									locationInfo = null;
									lctWIFIFlag = false;
									respJsonData.setUploadlctWIFI(Constant.INVALID_DATA);
									respJsonData.setLctTime(lctTime);
								}

							} 
							
						} else if (status.equals(Tools.ZeroString)) {
//							locationInfo = null;
							lctWIFIFlag = false;
							respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else if (status.equals("-1")) {
//							locationInfo = null;
							lctWIFIFlag = false;
							respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Gaode WIFI location Exception!!");
//					locationInfo = null;
					lctWIFIFlag = false;
					respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				}
				
				
				if (lctWIFIFlag == false) {
					
					NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
					
					if (null != netWorkInfo) {
						
						String network = netWorkInfo.getNetWork();
						String cdma = netWorkInfo.getCdma();
						smac = netWorkInfo.getSmac();
						String bts = netWorkInfo.getBts();
						String nearbts = netWorkInfo.getNearbts();
						serverip = netWorkInfo.getServerip();
//						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						map.clear();

						String jsonToString = "";
						
//						SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//						Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
						
						map.put("accesstype", Tools.ZeroString);
						map.put("network", network);
						map.put("cdma", cdma);
						map.put("imei", devId);
						map.put("smac", smac);
						map.put("bts", bts);
						map.put("nearbts", nearbts);
						map.put("key", Constant.KEY);
//						if (serverip != null && !"".equals(serverip)) {
//							map.put("serverip", serverip);
//						} else {
//							map.put("serverip", Constant.SERVER_IP);
//						}
						map.put("serverip", serverip);
						
						jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);

						if ("-1".equals(jsonToString)) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals(Tools.OneString)) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								String radius = jsonResult.getString("radius"); 
								if (location != null) {	
									String[] locations = location.split(",");

									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
		
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									bool_is_update = true;
									
									//yonghu start
									/*
									LocationInfoHelper lih = new LocationInfoHelper();								
									lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
											cmd.equals(AdragonConfig.uploadLctData));
									lih = null;
									*/
									//yonghu end	
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius);
									
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									locationInfo = null;
									respJsonData.setLctLbs(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								}
							} else { 
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							} 
						}
						
					}
					
				}
				
					
					wifi = null;
					smac = null;
					mmac = null;
					macs = null;
					serverip = null;
					sdtf = null; 
					lctUpDate = null;
//					jsonToString = null;
					map.clear();
					map = null;

				} else if("4".equals(locationType)) {     //Googel Map gps
					GPSInfoAdr gps = reqJsonData.getGps();
					String longitude = gps.getLon(); //经度
					String latitude = gps.getLat(); //纬度
					String accuracy = gps.getAcc(); //精确度
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
					
					if (devId != null && !"".equals(devId) &&  longitude != null
							&& !"".equals(longitude) && latitude != null
							&& !"".equals(latitude) && accuracy != null
							&& !"".equals(accuracy) && locationType != null
							&& !"".equals(locationType)) {

							lng1 = Double.parseDouble(longitude);
							lat1 = Double.parseDouble(latitude);
							longitude = String.format("%.12f", lng1);
							latitude = String.format("%.12f", lat1);
							lng1 = Double.parseDouble(longitude);
							lat1 = Double.parseDouble(latitude);
							
						if (lng1 != 0 && lat1 != 90) { // 直接过滤
							
							locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
							locationInfo.setOrderBy("upload_time");
							locationInfo.setSort(Tools.OneString); // 按upload_time降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1

							List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
							
							if (locationInfos.size() > 0) { 
								    String id = "" + locationInfos.get(0).getAt("id");
									double lng2 = Double.parseDouble(""
											+ locationInfos.get(0).getAt("longitude"));
									double lat2 = Double.parseDouble(""
											+ locationInfos.get(0).getAt("latitude"));	
									
									String preLctType = locationInfos.get(0).getAt("location_type").toString();
									
//									Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
//									int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
									
									double diffDist4 = Constant.getDistance(lat1, lng1, lat2, lng2);
									
//								if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
								if ( diffDist4 < Constant.GPS_DIFF_DIST_GAP && "4".equals(preLctType)) {
									locationInfo.setCondition("id ='" + id + "'");
									locationInfo.setUploadTime(lctUpDate);
									locationInfo.setStepCount(stepCount);
									
									//locationInfo.setLatitude(String.valueOf(lat2));    //smile add
									//locationInfo.setLongitude(String.valueOf(lng2));    //smile add
									locationInfo.setBattery(Integer.parseInt(battery));									
									Float acc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
									locationInfo.setAccuracy(acc);   //smile add
									
									
									serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
									bool_is_update = false;
								} else {

									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(longitude);
									locationInfo.setLatitude(latitude);
									locationInfo.setAccuracy(Float.parseFloat(accuracy));
									locationInfo.setLocationType(locationType);
									locationInfo.setChangeLongitude(longitude);
									locationInfo.setChangeLatitude(latitude);
									locationInfo.setUploadTime(lctUpDate);
									locationInfo.setFall(fall);								
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
									bool_is_update = true;
								}
							} else {
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(longitude);
								locationInfo.setLatitude(latitude);
								locationInfo.setAccuracy(Float.parseFloat(accuracy));
								locationInfo.setLocationType(locationType);
								locationInfo.setChangeLongitude(longitude);
								locationInfo.setChangeLatitude(latitude);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);								
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
								bool_is_update = true;
							}
							
							locationInfos.clear();
							locationInfos = null;
								
							//yonghu start
							/*
							
							LocationInfoHelper lih = new LocationInfoHelper();																
							lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							*/
							//yonghu end							

							respJsonData.setLctGGps(Constant.SUCCESS_CODE);
							respJsonData.setLctTime(lctTime);
								
							} else {
								locationInfo = null;
								respJsonData.setLctGGps(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
						}
						
						gps = null;
						longitude = null;
						latitude = null;
						accuracy = null;
						sdtf = null;
						lctUpDate = null;
						
				 } else if ("5".equals(locationType)) {     //Google geolocation API   

					String id = "";
					String strGeolocation = "";
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
					
					Geolocation geolocation = reqJsonData.getGeolocation();
					JSONObject gjsons = JSONObject.fromObject(geolocation);
					
					String param2 = gjsons.toString();
			
//					System.out.println("---before call google API json param: " + param2);
					
					locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
					locationInfo.setOrderBy("upload_time");
					locationInfo.setSort(Tools.OneString); // 按upload_time降序
					locationInfo.setFrom(0);
					locationInfo.setPageSize(1); // 0至1

					List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
					
					if (locationInfos.size() > 0) { 
						id = locationInfos.get(0).getAt("id").toString();
						double lng2 = Double.parseDouble(""
								+ locationInfos.get(0).getAt("longitude"));
						double lat2 = Double.parseDouble(""
								+ locationInfos.get(0).getAt("latitude"));	
						
						Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
						
						Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
						
						int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
						
						String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
						long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
						
						int diffStep5 = tls.getDiffSteps(stepCountPre, stepCount);
						
						
						if ((diffStep5 < Constant.GEO_DIFF_STEPS && "5".equals(preLctType) && timeDiff < 15*60*60)) {
							locationInfo.setCondition("id ='" + id + "'");
							
//							locationInfo.setLongitude(locationInfos.get(0).getAt("longitude").toString());
//							locationInfo.setLatitude(locationInfos.get(0).getAt("latitude").toString());
//							locationInfo.setAccuracy(Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString()));
//							locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
//							locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
//							locationInfo.setLocationType(locationInfos.get(0).getAt("location_type").toString());

							//locationInfo.setLatitude(String.valueOf(lat2));    //smile add
							//locationInfo.setLongitude(String.valueOf(lng2));    //smile add
							locationInfo.setBattery(Integer.parseInt(battery));							
							Float acc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
							locationInfo.setAccuracy(acc);   //smile add

							
							locationInfo.setUploadTime(lctUpDate);
							locationInfo.setStepCount(stepCount);
							serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
							bool_is_update = false;
							
							//yonghu start
							/*
							
							LocationInfoHelper lih = new LocationInfoHelper();																
							lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							*/
							//yonghu end	
							
							respJsonData.setLctGMap(Constant.SUCCESS_CODE);
							respJsonData.setLctTime(lctTime);
							
						} else {
							
							try {
								
								strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
								
//								System.out.println("--- cmd process call google API return : " + strGeolocation);
								
								if (strGeolocation != null && !strGeolocation.equals("")) {
									
									JSONObject geoJson = JSONObject.fromObject(strGeolocation);
									
									String error = geoJson.has("error")?geoJson.getString("error"):null;
									
									if (error == null) {
										String location = geoJson.getString("location");
										JSONObject locationJson = JSONObject.fromObject(location);
										double lat = locationJson.getDouble("lat");
										double lng = locationJson.getDouble("lng");
										double accuracy = geoJson.getDouble("accuracy");

										double diffDist5 = Constant.getDistance(lat, lng, lat2, lng2);
										
										//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
										if (diffDist5 < Constant.GEO_DIFF_DSIT_GAP && ("5".equals(preLctType) || accuracy > preLctAcc)) {
											locationInfo.setCondition("id ='" + id + "'");
											locationInfo.setUploadTime(lctUpDate);
											locationInfo.setBattery(Integer.parseInt(battery));
											locationInfo.setStepCount(stepCount);
											serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
											bool_is_update = false;
										} else {
											locationInfo.setLongitude(Double.toString(lng));
											locationInfo.setLatitude(Double.toString(lat));
											locationInfo.setChangeLongitude(Double.toString(lng));
											locationInfo.setChangeLatitude(Double.toString(lat));
											locationInfo.setAccuracy((float) accuracy);
											locationInfo.setUploadTime(lctUpDate);
											
											locationInfo.setSerieNo(devId);
											locationInfo.setBattery(Integer.parseInt(battery));
											locationInfo.setLocationType(locationType);
											locationInfo.setFall(fall);
											locationInfo.setBelongProject(Tools.OneString);
											locationInfo.setStepCount(stepCount);
											serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
											bool_is_update = true;
										}
										
										//yonghu start
										/*
										
										LocationInfoHelper lih = new LocationInfoHelper();																
										lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
												cmd.equals(AdragonConfig.uploadLctData));
										lih = null;
										*/
										//yonghu end							

										respLct.setLon(Double.toString(lng));
										respLct.setLat(Double.toString(lat));
										respLct.setAcc(Double.toString(accuracy));
										
										respJsonData.setLctGMap(Constant.SUCCESS_CODE);
										respJsonData.setLocation(respLct);
										respJsonData.setLctTime(lctTime);
									} else {
										locationInfo = null;
										JSONObject errorJson = JSONObject.fromObject(error);
										String errorCode = errorJson.getString("code");	
										respJsonData.setLctGMap(Integer.parseInt(errorCode));
										respJsonData.setLctTime(lctTime);
									}
								} else {
									locationInfo = null;
									respJsonData.setLctGMap(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								}
								
							} catch(Exception e){
								e.printStackTrace();
//								System.out.println("Google Map Geolocation Exception!!");
								logger.error("Google Map Geolocation Exception ---1---" + "\r\n" + e.toString());
								locationInfo = null;
								respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
								respJsonData.setLctTime(lctTime);
							}
							
						}
						
					} else {
						
						try {
							
							strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
							
//							System.out.println("--- cmd process call google API return : " + strGeolocation);
							
							if (strGeolocation != null && !strGeolocation.equals("")) {
								
								JSONObject geoJson = JSONObject.fromObject(strGeolocation);
								
								String error = geoJson.has("error")?geoJson.getString("error"):null;
								
								if (error == null) {
									String location = geoJson.getString("location");
									JSONObject locationJson = JSONObject.fromObject(location);
									double lat = locationJson.getDouble("lat");
									double lng = locationJson.getDouble("lng");
									double accuracy = geoJson.getDouble("accuracy");

									locationInfo.setLongitude(Double.toString(lng));
									locationInfo.setLatitude(Double.toString(lat));
									locationInfo.setChangeLongitude(Double.toString(lng));
									locationInfo.setChangeLatitude(Double.toString(lat));
									locationInfo.setAccuracy((float) accuracy);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLocationType(locationType);
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
									bool_is_update = true;
									
									//yonghu start
									/*
									
									LocationInfoHelper lih = new LocationInfoHelper();																
									lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
											cmd.equals(AdragonConfig.uploadLctData));
									lih = null;
									*/
									//yonghu end							

									respLct.setLon(Double.toString(lng));
									respLct.setLat(Double.toString(lat));
									respLct.setAcc(Double.toString(accuracy));
									
									respJsonData.setLctGMap(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									locationInfo = null;
									JSONObject errorJson = JSONObject.fromObject(error);
									String errorCode = errorJson.getString("code");									
									respJsonData.setLctGMap(Integer.parseInt(errorCode));
									respJsonData.setLctTime(lctTime);
								}
							} else {
								locationInfo = null;
								respJsonData.setLctGMap(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
							
						} catch(Exception e) {
							e.printStackTrace();
//							System.out.println("Google Map Geolocation Exception!!");
							logger.error("Google Map Geolocation Exception ---2---" + "\r\n" + e.toString());
							locationInfo = null;
							respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
						
					}
					
					
					sdtf = null;
					lctUpDate = null;
					geolocation = null;
					gjsons = null;
					param2 = null;
					locationInfos.clear();
					locationInfos = null;

				//Google geolocation API
				
			} else if ("6".equals(locationType)) {   //Gaode map lbs/wifi location case
					
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				
				WifiInfoAdr wifi = reqJsonData.getWifi();
//				String smac = wifi.getSmac();
				String mmac = wifi.getMmac();
				String macs = wifi.getMacs();
//				String serverip = wifi.getServerip();
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));

				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

				map.put("accesstype", Tools.ZeroString);
				map.put("network", network);
				map.put("cdma", cdma);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("bts", bts);
				map.put("nearbts", nearbts);
				map.put("mmac", mmac);
				map.put("macs", macs);
				map.put("key", Constant.KEY);
//				if (serverip != null && !"".equals(serverip)) {
//					map.put("serverip", serverip);
//				} else {
//					map.put("serverip", Constant.SERVER_IP);
//				}
				map.put("serverip", serverip);
				
			try {
				String jsonToString = HttpRequest.sendGetToGaoDe(
						Constant.LOCATION_URL, map);

				if ("-1".equals(jsonToString)) {
					locationInfo = null;
					respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				} else {
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status"); 
					
					if (status.equals(Tools.OneString)) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject
								.fromObject(results);
						
						String type = jsonResult.optString("type");
						
						if ("0".equals(type)) {
							locationInfo = null;
							respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							
							String location = jsonResult.getString("location"); 
							String radius = jsonResult.getString("radius");
							String[] locations = location.split(",");
							
							locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
							
							locationInfo.setOrderBy("upload_time");
							locationInfo.setSort(Tools.OneString); // 按upload_time降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1

							List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
							bool_is_update = true;	
							
							String id = "";
							if (locationInfos.size() > 0) { // 说明有数据
								id = locationInfos.get(0).getAt("id").toString();
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								String lctType = locationInfos.get(0).getAt("location_type").toString();
								
								lng1 = Double.parseDouble(locations[0]);
								lat1 = Double.parseDouble(locations[1]);
								
								double diffDist6 = Constant.getDistance(lat1, lng1, lat2, lng2);
								int diffStep6 = tls.getDiffSteps(stepCountPre, stepCount);
								
								if ( diffStep6 > Constant.WIFI_DIFF_STEPS || diffDist6 > Constant.WIFI_DIFF_DIST_GAP || (Float.parseFloat(radius) < preLctAcc)) {
									bool_is_update = true;
								} else {
									bool_is_update = false;
								}
							} else {
								bool_is_update = true;
							}

							locationInfos.clear();
							locationInfos = null;


							if (bool_is_update) {
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
							} else {
								locationInfo.setCondition("id ='" + id + "'");

								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								
								//locationInfo.setLatitude(String.valueOf(lat2));    //smile add
								//locationInfo.setLongitude(String.valueOf(lng2));    //smile add
								locationInfo.setBattery(Integer.parseInt(battery));								
								Float acc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								locationInfo.setAccuracy(acc);   //smile add

								
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setStepCount(stepCount);
								ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
							}
								
							//yonghu start
							/*
							
							LocationInfoHelper lih = new LocationInfoHelper();
							//BaseAction.insertVisit("-1", null, null, "proLctInfo");
							lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							*/
							//yonghu end	
							
							respLct.setLon(locations[0]);
							respLct.setLat(locations[1]);
							respLct.setAcc(radius);
							
							respJsonData.setLctLbsWifi(Constant.SUCCESS_CODE);
							respJsonData.setLocation(respLct);
							respJsonData.setLctTime(lctTime);
						} 
						
					} else {
						locationInfo = null;
						respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println("Gaode LBS and WIFI location Exception!!");
				logger.error("Gaode LBS and WIFI location Exception!" + "\r\n" + e.toString());
				locationInfo = null;
				respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
				respJsonData.setLctTime(lctTime);
			}
			
			netWorkInfo = null;
			network = null;
			cdma = null;
			smac = null;
			bts = null;
			nearbts = null;
			serverip = null;
			wifi = null;
			mmac = null;
			macs = null;
			sdtf = null; 
			lctUpDate = null;
			map.clear();
			map = null;
					 
//		} //Gaode lbs and wifi location case end
			} else if ("7".equals(locationType)) { //update lctTime
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					
					Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
					
					locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
					
					locationInfo.setOrderBy("upload_time");
					locationInfo.setSort(Tools.OneString); // 按upload_time降序
					locationInfo.setFrom(0);
					locationInfo.setPageSize(1); // 0至1

					List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
					String id = "";
					if (locationInfos.size() > 0) { 
						
						id = locationInfos.get(0).getAt("id").toString();
						locationInfo.setCondition("id ='" + id + "'");

						String lonStr = locationInfos.get(0).getAt("longitude").toString();
						String latStr = locationInfos.get(0).getAt("latitude").toString();
						String accStr = locationInfos.get(0).getAt("accuracy").toString();
						
						locationInfo.setBattery(Integer.parseInt(battery));								
						Float acc = Float.parseFloat(accStr);
						locationInfo.setAccuracy(acc);
						locationInfo.setUploadTime(lctUpDate);
						ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
						
						respLct.setLon(lonStr);
						respLct.setLat(latStr);
						respLct.setAcc(accStr);
						
						//yonghu start
						/*
						LocationInfoHelper lih = new LocationInfoHelper();
						lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
								cmd.equals(AdragonConfig.uploadLctData));
						lih = null;
						*/
						//yonghu end	
						
						respJsonData.setLctUpdate(Constant.SUCCESS_CODE);
						respJsonData.setLocation(respLct);
						respJsonData.setLctTime(lctTime);
						
						lonStr = null;
						latStr = null;
						accStr = null;

					} else {
						respJsonData.setLctUpdate(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					}

					sdtf = null; 
					lctUpDate = null;
					id = null;

			 }		
				
				//yonghu start
				if (locationInfo != null) {				
					//BaseAction.insertVisit("-1", null, null, "proLctInfo");
					lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
							cmd.equals(AdragonConfig.uploadLctData));
				}
				//yonghu end	
								
				try {
					if (cmd.equals(AdragonConfig.getLocationRes)) { 
						
//						if (devId != null && !"".equals(devId)) {
//						if (locationInfo != null && (!"".equals(locationInfo.getSerieNo()) && locationInfo.getSerieNo() != null )
//								&& (!"".equals(locationInfo.getChangeLatitude()) && locationInfo.getChangeLatitude() != null )
//							    && (!"".equals(locationInfo.getChangeLongitude()) && locationInfo.getChangeLongitude() != null )) {
//							
//							serviceBean.getLocationInfoFacade().insertClickLocationInfo(locationInfo);	
						
						if (locationInfo != null) {
							
							if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
								Integer userId = reqJsonData.getUserId();
								//inform app user get update location
								WTAppGpsManAction wgma = new WTAppGpsManAction();								
								wgma.proGetDevLoc17(userId, device_id, devId);
								wgma = null;
							}

						} else {
							if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
								Integer userId = reqJsonData.getUserId();
								//inform app user get update location fail 
								mClientSessionManager.completeFailHttpCmdId(userId, device_id, devId, AdragonConfig.getLocationRes);
							}
							
						}
						
					}
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("getLocationRes exception!" + "\r\n" + e.toString());
				}

				devId = null;
				locationType = null;
				battery = null;
				fall = null;
			    locationInfo = null;
			    
			}else if(cmd.equals(AdragonConfig.Monitor)){   //倾听
				String phoneNum = "";
				String belongProject = reqJsonData.getB_g();
				if(devId!=null && !"".equals(devId)){
					StringBuffer buffer = new StringBuffer();
					buffer.append("device_imei ='"+devId+"'");
					if(belongProject!=null && !"".equals(belongProject)){
						buffer.append(" and belong_project ='"+belongProject+"'");
					}
					buffer.append(" and listen_type='1' and device_disable='1'");
					
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
					deviceActiveInfo.setCondition(buffer.toString());
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveInfos.size()>0){
						String userId = (String)deviceActiveInfos.get(0).getAt("user_id");
						AppUserInfo appUserInfo = new AppUserInfo();
						appUserInfo.setCondition("id='"+userId+"'");
						AppUserInfoFacade appUserInfoFacade = ServiceBean.getInstance().getAppUserInfoFacade();
						List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
						if(appUserInfos.size()>0){
							phoneNum = appUserInfos.get(0).getAt("user_name").toString();
							respJsonData.setResultCode(Constant.SUCCESS_CODE);
							respJsonData.setPhoneNum(phoneNum);
							deviceActiveInfo.setListenType(Tools.ZeroString);
							deviceActiveInfoFacade.updateDeviceActiveInfo(deviceActiveInfo);
						}			
					}else{
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}
				}
				/*
				subData.setPhoneNum(phoneNum);
				sub = JSON.toJSONString(subData);
				
				String userId = "";
				if(session.containsAttribute("userId")){
					userId = session.getAttribute("userId").toString();
				}
				IoSession tempSession = mClientSessionManager.getSessionId(userId);
				if(tempSession != null){
					WriteFuture writeFuture = tempSession.write(sub.toString());
					writeFuture.addListener(new IoFutureListener<IoFuture>() {

						public void operationComplete(IoFuture future) {
							
							if(((WriteFuture)future).isWritten()){   //发送成功
								logger.info("响应倾听成功");
							}else{
								logger.info("响应倾听失败=设备不在线");
							}								
						}							
					});
				}else{
					logger.info("响应倾听失败=设备没有上传心跳");
				}
				*/					
			}else if(cmd.equals(AdragonConfig.findDevice)){   //找手表
				/*
				sub = JSON.toJSONString(subData);
				String userId="";
				if(session.containsAttribute("userId")){
					userId =(String)session.getAttribute("userId");
				}
				sub = JSON.toJSONString(subData);
				IoSession tempSession = mClientSessionManager.getSessionId(userId);
				if(tempSession != null){
					WriteFuture writeFuture = tempSession.write(sub.toString());
					writeFuture.addListener(new IoFutureListener<IoFuture>(){
						
						public void operationComplete(IoFuture future) {
							// TODO Auto-generated method stub
							if(((WriteFuture)future).isWritten()){   //发送成功
								logger.info("推送数据配置文件成功");
								respJsonData.setResultCode(Constant.SUCCESS_CODE);
							}else{
								logger.info("推送数据配置文件失败=设备不在线");
								respJsonData.setResultCode(Constant.FAIL_CODE);
							}
						}								
					});
				}else{
					logger.info("推送数据配置文件失败=设备没有上传心跳");
					respJsonData.setResultCode(Constant.FAIL_CODE);
				} */
			} else if(cmd.equals(AdragonConfig.FALLDOWN)){
				
			} else if(cmd.equals(AdragonConfig.batPowerLow)){  //低电量报警
				
				//产生低电报警消息
				lih.proLowBatteryMsg(device_id);
				
				if ( device_id > 0) {
					WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
					
					wdeviceActiveInfo.setCondition("a.device_id = " + device_id);
					
					WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
					List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
					if (wdeviceActiveInfos.size() > 0) {
						wdeviceActiveInfo.setIs_lowbat(Tools.OneString);
						//wdeviceActiveInfo.setBattery(reqJsonData.getBattery());
						wdeviceActiveInfo.setCondition("device_id = " + device_id);
						wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
//						respJsonData.setResultCode(Constant.SUCCESS_CODE);
						respJsonData.setLowBattery(Constant.SUCCESS_CODE);
					} else {
//						respJsonData.setResultCode(Constant.FAIL_CODE);
						respJsonData.setLowBattery(Constant.FAIL_CODE);
					}
					
				}
											
			}else if(cmd.equals(AdragonConfig.alarms)){     //远程闹钟
				if(session.containsAttribute("id")){
					devId = (String) session.getAttribute("id");
				}
				DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
				
				DirectiveInfo directiveInfo = new DirectiveInfo();
				directiveInfo.setSerie_no(devId);
				List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
				List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
				for(int i=0;i<directiveInfos.size();i++){
					DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
					directiveInfoAdragon.setMonday((String)directiveInfos.get(i).getAt("mdistime"));
					directiveInfoAdragon.setTuesday((String)directiveInfos.get(i).getAt("tdistime"));
					directiveInfoAdragon.setWednesday((String)directiveInfos.get(i).getAt("wdistime"));
					directiveInfoAdragon.setThursday((String)directiveInfos.get(i).getAt("thdistime"));
					directiveInfoAdragon.setFriday((String)directiveInfos.get(i).getAt("fdistime"));
					directiveInfoAdragon.setSaturday((String)directiveInfos.get(i).getAt("sdistime"));
					directiveInfoAdragon.setSunday((String)directiveInfos.get(i).getAt("sudistime"));
					directiveInfoAdragons.add(directiveInfoAdragon);
				}
				respJsonData.setResultCode(Constant.SUCCESS_CODE); //成功
				if(directiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE);  //失败
				}
			    respJsonData.setAlarm(directiveInfoAdragons);
				
			}else if(cmd.equals(AdragonConfig.DEVICESLEEP)){  //睡眠提醒
				DirectiveInfo directiveInfo = new DirectiveInfo();
				directiveInfo.setSerie_no(devId);
				
				DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
				List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
				List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
				for(int i=0;i<directiveInfos.size();i++){
					DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
					directiveInfoAdragon.setMonday((String)directiveInfos.get(i).getAt("mdistime"));
					directiveInfoAdragon.setTuesday((String)directiveInfos.get(i).getAt("tdistime"));
					directiveInfoAdragon.setWednesday((String)directiveInfos.get(i).getAt("wdistime"));
					directiveInfoAdragon.setThursday((String)directiveInfos.get(i).getAt("thdistime"));
					directiveInfoAdragon.setFriday((String)directiveInfos.get(i).getAt("fdistime"));
					directiveInfoAdragon.setSaturday((String)directiveInfos.get(i).getAt("sdistime"));
					directiveInfoAdragon.setSunday((String)directiveInfos.get(i).getAt("sudistime"));
					directiveInfoAdragons.add(directiveInfoAdragon);
				}
				
				respJsonData.setResultCode(Constant.SUCCESS_CODE);  //成功
				if(directiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE); //失败
				}
				respJsonData.setSleep(directiveInfoAdragons);
				
			}else if(cmd.equals(AdragonConfig.GetDisturb)){  //上课防打扰
				String distrub ="";
				DirectiveInfo directiveInfo = new DirectiveInfo();
				directiveInfo.setSerie_no(devId);
				DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
				
				List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
				List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
				if(directiveInfos.size()>0){
					DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
					distrub = directiveInfos.get(0).getAt("distrub").toString();
					directiveInfoAdragon.setMonday((String)directiveInfos.get(0).getAt("mdistime"));
					directiveInfoAdragon.setTuesday((String)directiveInfos.get(0).getAt("tdistime"));
					directiveInfoAdragon.setWednesday((String)directiveInfos.get(0).getAt("wdistime"));
					directiveInfoAdragon.setThursday((String)directiveInfos.get(0).getAt("thdistime"));
					directiveInfoAdragon.setFriday((String)directiveInfos.get(0).getAt("fdistime"));
					directiveInfoAdragon.setSaturday((String)directiveInfos.get(0).getAt("sdistime"));
					directiveInfoAdragon.setSunday((String)directiveInfos.get(0).getAt("sudistime"));
					directiveInfoAdragons.add(directiveInfoAdragon);
					respJsonData.setTimes(directiveInfoAdragons);
					respJsonData.setDisturb(distrub);
				}
				respJsonData.setResultCode(Constant.SUCCESS_CODE);  //成功
				if(directiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE); //失败
				}
								
			}else if(cmd.equals(AdragonConfig.DEVICEPOWOFF)){  //远程关机
				/*
				String userId = "-1";
				String type = reqJsonData.getType(); //type为0表示手动关机、1表示低电量关机、2表示远程关机
				String belongProject = reqJsonData.getB_g();
				if(devId!=null && !"".equals(devId) && type!=null && !"".equals(type)){
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_project ='"+belongProject+"' and device_disable ='1'");
					List<DataMap> deviceActiveInfos = serviceBean.getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveInfos.size()>0){
						userId = deviceActiveInfos.get(0).getAt("user_id").toString();
					}
					if(!"-1".equals(userId)){
						String content = "8@" + devId + "@" + Tools.ZeroString;  
						if(type.equals(Tools.ZeroString)){
							content = "9@" + devId + "@" + Tools.ZeroString;
						}else if(type.equals(Tools.OneString)){
							content = "10@" + devId + "@" + Tools.ZeroString;
						}
						MsgInfo msgInfo = new MsgInfo();
						
						msgInfo.setToId(userId);
						msgInfo.setFromId(userId);
						msgInfo.setIsHandler(Tools.ZeroString); 
						msgInfo.setMsgLevel(Tools.OneString); 
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setMsgContent(content);
						msgInfo.setBelongProject(belongProject);
						
						ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
						respJsonData.setResultCode(Constant.SUCCESS_CODE);
					}else{
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}
				}else{
					respJsonData.setResultCode(Constant.FAIL_CODE);
				}
				*/
			}else if(cmd.equals(AdragonConfig.picture)){  //远程拍照
				/*
				subData.setCmd(AdragonConfig.U_Picture);
				sub = JSON.toJSONString(subData);
				String userId="";
				if(session.containsAttribute("userId")){
					userId =(String)session.getAttribute("userId");
				}
				sub = JSON.toJSONString(subData);
				IoSession tempSession = mClientSessionManager.getSessionId(userId);
				if(tempSession != null){
					WriteFuture writeFuture = tempSession.write(sub.toString());
					writeFuture.addListener(new IoFutureListener<IoFuture>() {

						public void operationComplete(IoFuture future) {
							if(((WriteFuture)future).isWritten()){   //发送成功
								logger.info("请求远程拍照成功");
								respJsonData.setResultCode(Constant.SUCCESS_CODE);
							}else{
								logger.info("请求远程拍照失败=设备不在线");
								respJsonData.setResultCode(Constant.FAIL_CODE);
							}
						}							
					});
				}else{
					logger.info("请求远程拍照失败=设备没有上传心跳");
					respJsonData.setResultCode(Constant.FAIL_CODE);
				} */
			}else if(cmd.equals(AdragonConfig.DEVICEMSGSETTING)){  //基本的远程设置
				String belongProject = reqJsonData.getB_g();
				if(devId !=null && !"".equals(devId) && belongProject!=null && !"".equals(belongProject)){
					StringBuffer buffer = new StringBuffer();
					buffer.append("serie_no='"+devId+"'");
					if(belongProject!=null && !"".equals(belongProject)){
						buffer.append(" and belong_project ='"+belongProject+"'");
					}
				}		
			} else if(cmd.equals(AdragonConfig.querySport)){   //运动检测
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_project='1'");
				DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
				List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveHistory(deviceActiveInfo);
				if(deviceActiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE);
				}else{
					String sport = deviceActiveInfos.get(0).getAt("sports").toString();
					respJsonData.setResultCode(Constant.SUCCESS_CODE);  
					respJsonData.setSport(sport);	//返回运动检测结果					
				}
				
			} else if(cmd.equals(AdragonConfig.queryTemperature)){  //体温检测
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belongProject = '1'");
				DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
				List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
				if(deviceActiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE);
				}else{
					String temperature = deviceActiveInfos.get(0).getAt("temperature").toString();
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
					respJsonData.setTemperature(temperature); //返回体温检测结果
				}
			} else if(cmd.equals(AdragonConfig.reqVoice)){  //长链接语音对讲
				respJsonData.setCmd(AdragonConfig.pushVoice);
				
			} else if(cmd.equals(AdragonConfig.queryHeatrate)){  //心率监测
				String belongProject = reqJsonData.getB_g();
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_Project ='"+belongProject+"'");
				DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
				List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
				if(deviceActiveInfos.isEmpty()){
					respJsonData.setResultCode(Constant.FAIL_CODE);
				}else{
					String heatrate = deviceActiveInfos.get(0).getAt("heatrate").toString();
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
					respJsonData.setHeatrate(heatrate);  //返回心率检测结果
				}
				
			} else if (cmd.equals(AdragonConfig.reqFlightMode)) {
				
				
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
				wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				
				if (wdeviceActiveInfos.isEmpty()) {
					
					respJsonData.setResultCode(Constant.EXCEPTION_CODE);
					respJsonData.setDevTime(dateFormat.format(new Date()));	
					
				} else {
					//wdeviceActiveInfos.get(0).getAt("flight_mode");
					wdeviceActiveInfo.setFlight_mode(reqJsonData.getFlightModeFlag());
					wdeviceActiveInfo.setCondition("device_imei = '"+devId+"'");					
					wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
					
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
					respJsonData.setDevTime(dateFormat.format(new Date()));	
				}
				
			} else if (cmd.equals(AdragonConfig.reqUrgentMode)) {
				
				
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
				wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				
				if (wdeviceActiveInfos.isEmpty()) {
					
					respJsonData.setResultCode(Constant.EXCEPTION_CODE);
					respJsonData.setDevTime(dateFormat.format(new Date()));	
					
				} else {
					
					wdeviceActiveInfo.setUrgent_mode(reqJsonData.getUrgentFlag());
					wdeviceActiveInfo.setCondition("device_imei = '"+devId+"'");					
					wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
					
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
					respJsonData.setDevTime(dateFormat.format(new Date()));	
				}
			
			} else if (cmd.equals(AdragonConfig.setUrgentModeRes)) {
				WTAppGpsManAction wma = new WTAppGpsManAction();
				wma.proUrgentModeRes(device_id, devId, reqJsonData);
				
			} else if (cmd.equals(AdragonConfig.setLedStateRes)) {
				Integer srcUserId = 0;
				
				srcUserId = reqJsonData.getUserId();				
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
//				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
//				wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
//				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				
				Integer ledId = Integer.parseInt(reqJsonData.getLedID());
				if ("".equals(devId)) {
//					respJsonData.setResultCode(Constant.EXCEPTION_CODE);
//					respJsonData.setDevTime(dateFormat.format(new Date()));	
					
				} else {
					WMsgInfo ledMsg = new WMsgInfo();

					if ( ledId == 3 ) {	
						if (!Constant.IS_SERV_STAT_CT) {
							wdeviceActiveInfo.setLed_on(reqJsonData.getLedFlag());
							wdeviceActiveInfo.setCondition("device_imei = '"+devId+"'");
							wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
						}
						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
							mClientSessionManager.completeHttpCmdId(devId, 
									AdragonConfig.setLedStateRes, 
									srcUserId, null );
						}
						if ( Tools.OneString.equals(reqJsonData.getLedFlag())  )
							ledMsg.setMsg_ind_id(Constant.CST_MSG_IND_COOL_LED_ON);
						else
							ledMsg.setMsg_ind_id(Constant.CST_MSG_IND_COOL_LED_OFF);
						ledMsg.setDevice_id(device_id);
						
						if ( Constant.timeUtcFlag )
							ledMsg.setMsg_date(tls.getUtcDateStrNow());			
						else 																					
							ledMsg.setMsg_date(ba.getDeviceNow(device_id));
						
						
						lih.proCommonInnerMsg(ledMsg, srcUserId);
						
					} else if( ledId == 4) {
						if (!Constant.IS_SERV_STAT_CT_URGENT) {						
							wdeviceActiveInfo.setSos_led_on(reqJsonData.getLedFlag());
							wdeviceActiveInfo.setCondition("device_id = '"+device_id+"'");
							wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
						}

						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
							mClientSessionManager.completeHttpCmdId(devId, 
									AdragonConfig.setLedSosRes, 
									srcUserId, null );
						}
						if ( Tools.OneString.equals(reqJsonData.getLedFlag())  )
							ledMsg.setMsg_ind_id(Constant.CST_MSG_IND_DISCOVERY_LEDON);
						else
							ledMsg.setMsg_ind_id(Constant.CST_MSG_IND_DISCOVERY_LEDOFF);
						ledMsg.setDevice_id(device_id);
						if ( Constant.timeUtcFlag )
							ledMsg.setMsg_date(tls.getUtcDateStrNow() );
						else
							ledMsg.setMsg_date(ba.getDeviceNow(device_id));						
						
						lih.proCommonInnerMsg(ledMsg, srcUserId);
					}
					
//					respJsonData.setResultCode(Constant.SUCCESS_CODE);
//					respJsonData.setDevTime(dateFormat.format(new Date()));	
				}
			
			} else if (cmd.equals(AdragonConfig.setPlayRingRes)) {
				Integer srcUserId = 0;
				WMsgInfo aMsg = new WMsgInfo();
				
				srcUserId = reqJsonData.getUserId();				
				
				if (!Constant.IS_SERV_STAT_CT_URGENT) {		
					WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
					wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
	//				wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
					
					WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
	
					wdeviceActiveInfo.setCallback_on(reqJsonData.getPlayFlag());
					wdeviceActiveInfo.setCondition("device_imei = '"+devId+"'");
					wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
				}
				
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
					mClientSessionManager.completeHttpCmdId(devId, 
							AdragonConfig.setPlayRingRes, 
							srcUserId, null );
				}
				if ( Tools.OneString.equals(reqJsonData.getPlayFlag())  )
					aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DISCOVERY_SNDON);
				else
					aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DISCOVERY_SNDOFF);
				aMsg.setDevice_id(device_id);	
//				aMsg.setMsg_date(BaseAction.getDeviceNow(device_id));

				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {									
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}
				
				lih.proCommonInnerMsg(aMsg, srcUserId);
				

			} else if (cmd.equals(AdragonConfig.wifiDistance)) {
				
				String pet_id = "";
				
				WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
				Wpet wpetInfo = new Wpet();
				
				wpetInfo.setCondition("b.device_imei ='"+devId+"'");
				
				List<DataMap> wpetinfos = wpetFacade.getDogDataListByDevice(wpetInfo);
				if (wpetinfos.size() > 0) {		
					pet_id = wpetinfos.get(0).getAt("pet_id").toString();
					
					WpetWifiRange wpetWifiRange = new WpetWifiRange();
					
					WpetWifiRangeFacade wpetWifiRangeFacade = ServiceBean.getInstance().getWpetWifiRangeFacade();
					
					wpetWifiRange.setPet_id(Integer.parseInt(pet_id));
					wpetWifiRange.setWifi_radius(Float.parseFloat(reqJsonData.getWifiRadius()));
					wpetWifiRange.setHost_ssid(reqJsonData.getHostSsid());
					wpetWifiRange.setUp_time(new Date());
					
					wpetWifiRangeFacade.insertWpetWifiRange(wpetWifiRange);
					
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
				} else {
					respJsonData.setResultCode(Constant.FAIL_CODE);
				}
				
				/*
				WpetWifiRange wpetWifiRange = new WpetWifiRange();
				WpetWifiRangeFacade wpetWifiRangeFacade = ServiceBean.getInstance().getWpetWifiRangeFacade();
				
				wpetWifiRange.setDevice_imei(devId);
				wpetWifiRange.setWifi_radius(Float.parseFloat(reqJsonData.getWifiRadius()));
				wpetWifiRange.setHost_ssid(reqJsonData.getHostSsid());
				wpetWifiRange.setUp_time(new Date());
				
				wpetWifiRangeFacade.insertWpetWifiRange(wpetWifiRange);

				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				respJsonData.setDevTime(dateFormat.format(new Date()));	
				*/
			}else if (cmd.equals(AdragonConfig.getStepParameters)) {//返回：{"stepParameters":{"sensitivity":"3","stepLength":"30","weight":"15"}}
				//sb = new StringBuffer();
				sb.setLength(0);
			
				WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
				Wpet wpetInfo = new Wpet();
				if (devId!=null && "".equals(devId)) {
					if (sb.length() > 0) {
						sb.append(" and ");
					}
					sb.append("a.device_id ='"+devId+"'");
				}
				
				wpetInfo.setCondition(sb.toString());
				
				List<DataMap> wpetinfos = wpetFacade.getDogDataListByDevice(wpetInfo);
				if (wpetinfos.size() > 0) {
				    StepParameters stepParameters = new StepParameters();
				    
				    stepParameters.setSensitivity(wpetinfos.get(0).getAt("sensitivity").toString());   //sensitivity is default is 3
				    stepParameters.setWeight(wpetinfos.get(0).getAt("weight").toString());
				    stepParameters.setStepLength(wpetinfos.get(0).getAt("sheight").toString());
				    
				    respJsonData.setStepParameters(stepParameters);
//				    respJsonData.setResultCode(Constant.SUCCESS_CODE);
				} else {
//					respJsonData.setResultCode(Constant.FAIL_CODE);
					respJsonData.setStepParameters(null);
				}
				
			}else if (cmd.equals(AdragonConfig.upStepData)) {
						
				String weight = "";
			    //2016.12.17,不再需要设备心跳时间戳记录
			    //更新设备心跳时间
				/*
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					//device_id = (Integer) dm.getAt("device_id");
					TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
					dateFormat.setTimeZone(devTimeZone);
					dm = null;
				}
			    
				LocationInfoHelper.updateDeviceStatus(device_id, 0, null);
				*/
				String pet_id = "";
				
				WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
				Wpet wpetInfo = new Wpet();
				
				wpetInfo.setCondition("b.device_imei ='"+devId+"'");
				
				List<DataMap> wpetinfos = wpetFacade.getDogDataListByDevice(wpetInfo);
				String shoulder_height = null;	//肩高
				String kindId = null;
				if (wpetinfos.size() > 0) {		
					pet_id = wpetinfos.get(0).getAt("pet_id").toString();
					//shoulder_height = wpetinfos.get(0).getAt("sheight").toString();
					kindId = wpetinfos.get(0).getAt("fci_detail_all_catid").toString().trim();
					if ( Tools.ZeroString.equals(kindId) || "F003".equals(kindId) || "F009".equals(kindId) ) {
						shoulder_height = "0.23";
					} else {
						shoulder_height = "0.48";
					}
					
					weight = wpetinfos.get(0).getAt("weight").toString();
					
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					WpetMoveInfo wpetMoveInfo = new WpetMoveInfo();
					WpetMoveInfoFacade wpetMoveInfoFacade = ServiceBean.getInstance().getWpetMoveInfoFacade();
					
					wpetMoveInfo.setPets_pet_id(Integer.parseInt(pet_id));
					wpetMoveInfo.setStep_number(Float.parseFloat(reqJsonData.getStepNumber()));
//					wpetMoveInfo.setRoute(Float.parseFloat(reqJsonData.getRoute()));
//					wpetMoveInfo.setCalories(Double.parseDouble(reqJsonData.getCalories()));
//					wpetMoveInfo.setSpeed(Double.parseDouble(reqJsonData.getSpeed()));
					
					
					//wpetMoveInfo.setUp_time(dateFormat.format(new Date()));
					if ( Constant.timeUtcFlag )
						wpetMoveInfo.setUp_time(tls.getUtcDateStrNow());					
					else 																					
						wpetMoveInfo.setUp_time(dateFormat.format(new Date()));
					
					wpetMoveInfo.setStart_time(reqJsonData.getStarttime());
					wpetMoveInfo.setEnd_time(reqJsonData.getEndtime());
					wpetMoveInfo.setDevice_imei(devId);
					wpetMoveInfo.setDevice_id(device_id);
					
					
					//yonghu add
					StepCountTools sct = new StepCountTools();
					sct.setWeight(weight);
					sct.setShoulder_height(shoulder_height);
					wpetMoveInfo = sct.proPetMoveInfo(wpetMoveInfo);
					//yonghu add end
					
//					respJsonDa  a.setResultCode(Constant.SUCCESS_CODE);
										
					if ( Constant.timeUtcFlag ) {
						if ( session == null ) {
							wpetMoveInfoFacade.insertWpetMoveInfo(wpetMoveInfo);						
							sct.proMoveDoMsgSe(wpetMoveInfo, device_id);							
						} else {
				        	synchronized(session) {						
								wpetMoveInfoFacade.insertWpetMoveInfo(wpetMoveInfo);						
								sct.proMoveDoMsgSe(wpetMoveInfo, device_id);
				        	}
			        	}
					} else {
						
						if (tls.getSecondsBetweenDays(reqJsonData.getStarttime(), reqJsonData.getEndtime())  > 0 )  {						
							List<WpetMoveInfo> moveArr = lih.mkTinyMoveInfoSe(wpetMoveInfo);
	
					        Iterator<WpetMoveInfo> it1 = moveArr.iterator();
					        while(it1.hasNext()){
					        	WpetMoveInfo iMove = it1.next();
								wpetMoveInfoFacade.insertWpetMoveInfo(iMove);						
	
						    	if ( wpetMoveInfo.getSpeed() > 7.11109 ) {
						        	synchronized(session) {
										//yonghu add
										if ( Constant.timeUtcFlag )
											sct.proMoveDoMsgSe(iMove, device_id);							
										else
											sct.proMoveDoMsg(iMove, device_id);
										//yonghu add end						
									}
						    	}
					        }						
							
						} else {
							wpetMoveInfoFacade.insertWpetMoveInfo(wpetMoveInfo);						
						}
					}

					respJsonData.setUpStepData(Constant.SUCCESS_CODE);
					
					
				} else {
//					respJsonData.setResultCode(Constant.FAIL_CODE);
					respJsonData.setUpStepData(Constant.FAIL_CODE);
			    }
				
			} else if (cmd.equals(AdragonConfig.upSleepData)) {	
				String pet_id = "";
				
				
				WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
				Wpet wpetInfo = new Wpet();
				
				wpetInfo.setCondition("b.device_imei ='"+devId+"'");
				
				List<DataMap> wpetinfos = wpetFacade.getDogDataListByDevice(wpetInfo);
				
				if (wpetinfos.size() > 0) {		
					pet_id = wpetinfos.get(0).getAt("pet_id").toString();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					WpetSleepInfo wpetSleepInfo = new WpetSleepInfo();
					WpetMoveInfoFacade wpetMoveInfoFacade = ServiceBean.getInstance().getWpetMoveInfoFacade();
					
					wpetSleepInfo.setPets_pet_id(Integer.parseInt(pet_id));
					wpetSleepInfo.setStep_number(Float.parseFloat(reqJsonData.getStepNumber()));					
					//wpetSleepInfo.setUp_time(new Date());
					if ( Constant.timeUtcFlag )
						wpetSleepInfo.setUp_time(tls.getUtcDateStrNowDate());			
					else 																					
						wpetSleepInfo.setUp_time(new Date());
					
					wpetSleepInfo.setStart_time(sdf.parse(reqJsonData.getStarttime()));
					wpetSleepInfo.setEnd_time(sdf.parse(reqJsonData.getEndtime()));

					//wpetSleepInfo.setSpeed(Double.parseDouble(reqJsonData.getSpeed()));
					wpetSleepInfo.setSpeed(lih.getSpeedFromDevSleepInfo(wpetSleepInfo));					
					wpetMoveInfoFacade.insertWpetSleepInfo(wpetSleepInfo);
//					respJsonData.setResultCode(1151);
					respJsonData.setUpSleepData(Constant.SUCCESS_CODE);
				} else {
//					respJsonData.setResultCode(1150);
					respJsonData.setUpSleepData(Constant.FAIL_CODE);
				}
				
			} else if (cmd.equals(AdragonConfig.getLogToken)) {
				
				String app_token = "";
			
				WappUsers appUsers = new WappUsers();
				WappUsersFacade wappUsersFacade = ServiceBean.getInstance().getWappUsersFacade();
				appUsers.setCondition("user_id = -1");
				
				List<DataMap> wappUsers = wappUsersFacade.getWappUsers(appUsers);
				
				if (wappUsers.size() > 0) {
					app_token = wappUsers.get(0).getAt("app_token").toString();
					
					respJsonData.setUserId("-1");
					respJsonData.setAppToken(app_token);
					respJsonData.setCmd("devUpLog");
//					respJsonData.setResultCode(1161);
					respJsonData.setGetLogToken(Constant.SUCCESS_CODE);
				} else {
//					respJsonData.setResultCode(1160);
					respJsonData.setGetLogToken(Constant.FAIL_CODE);
				}
				
			} else if (cmd.equals(AdragonConfig.setBeattimRes)) {
				//empty not handle setBeattimRes
			
			}else if (cmd.equals(AdragonConfig.setGpsMapRes)) {
				//empty not handle setGpsMapRes
			
			}else if (cmd.equals(AdragonConfig.setEcoModeRes)) {
				DevNotifyApp dna = new DevNotifyApp();				
				String devTime = dna.getSessionDevTime(session);				

				if (!Constant.IS_SERV_STAT_CT_ECOMODE) {
					dna.proEcoModeRes(device_id, devId, devTime, reqJsonData);
				}
				
				dna = null;
				
			}else if (cmd.equals(AdragonConfig.ssNetRes)) {
				session.write("{\"ssNetResAck\":1}");
				DevNotifyApp dna = new DevNotifyApp();				
				String devTime = dna.getSessionDevTime(session);				
								
				dna.proSsNetRes(device_id, devId, devTime, reqJsonData);

				
				/*
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				ServiceBean serviceBean = ServiceBean.getInstance();
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();

				
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");										
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);

				if ( Tools.OneString.equals(wdeviceActiveInfos.get(0).
						getAt("eco_mode").toString().trim()) ) {
					if ( reqJsonData.getCurNet() != 1 ) {
						if (lih.IsAmericaDev(wdeviceActiveInfos.get(0).getAt("time_zone").toString()))
							session.write("{\"ssNet\":0,\"userId\":0,\"pg\":2}");
						else
							session.write("{\"ssNet\":1,\"userId\":0,\"pg\":2}");
					}
				} else {
					if ( reqJsonData.getCurNet() != 0 )					
						session.write("{\"ssNet\":0,\"userId\":0,\"pg\":2}");															
				}
				*/
				
				//ababab
				dna = null;
			}else if (cmd.equals(AdragonConfig.ssTmRes)) {
				//session.write("{\"ssTmResAck\":1}");
				
				DevNotifyApp dna = new DevNotifyApp();				
				String devTime = dna.getSessionDevTime(session);				
								
				dna.proTmRes(device_id, devId, devTime, reqJsonData);
				
				dna = null;

			//aabaa
			}else if (cmd.equals(AdragonConfig.uTMD)) {
				//session.write("{\"ssTmResAck\":1}");
				
				DevNotifyApp dna = new DevNotifyApp();				
				String devTime = dna.getSessionDevTime(session);				
								
				dna.prouTMD(device_id, devId, devTime, reqJsonData);

				
				dna = null;
				

			} else if (cmd.equals(AdragonConfig.setDevDebugRes)) {

				if(session.containsAttribute("wdeviceInfo")) {
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					//device_id = (Integer) dm.getAt("device_id"); 
					TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
					dateFormat.setTimeZone(devTimeZone);
					dm = null;
				}
				
				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				deviceActiveInfo.setCondition("device_id = " + device_id);
//				deviceActiveInfo.setDevice_id(device_id);
				deviceActiveInfo.setDebug_mode(reqJsonData.getDevDebug());
				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
			
				deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
				
//				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				
				deviceActiveInfo = null;
				deviceInfoFacade = null;
				
			}else if (cmd.equals(AdragonConfig.ssidEsafeState)) {	
								
				Boolean res = false;
				
				DevNotifyApp dna = new DevNotifyApp();
				
				String devTime = dna.getSessionDevTime(session);
				
				JSONObject extPara = new JSONObject();
				String ssidEsafeFlag = reqJsonData.getSsidEsafeFlag();
				
				
				if ( tls.isNullOrEmpty(ssidEsafeFlag) )
					ssidEsafeFlag = reqJsonData.geteSafeFlag();
				
				
				String pet_nick = ba.getPetNickFromDeviceId(device_id);
				String wifi_ctr =  ba.getSSidWifiFromDeviceId(device_id);
				
				
				//aaabbb
				
				if (!tls.isNullOrEmpty(wifi_ctr)) {
					
					String txt = null;
					/*
					if ( ssidEsafeFlag != null && Tools.OneString.equals(ssidEsafeFlag) )
						txt = lih.getWifiEsafePushMsg(pet_nick, wifi_ctr, devTime, true);
					else
						txt = lih.getWifiEsafePushMsg(pet_nick, wifi_ctr, devTime, false);
					*/
					
					extPara.put("ssidEsafeFlag", ssidEsafeFlag);
					extPara.put("txt", txt);
	
					ba.insertVisit(null, null, String.valueOf(device_id), "ssidEsafeState extPara " + extPara.toString() );							
					
					res = dna.proSsidEsafeState(device_id, devId, devTime, reqJsonData, extPara.toString(), txt, ssidEsafeFlag);
	
					if ( res )
						respJsonData.setSsidEsafeState(Constant.SUCCESS_CODE);					
				}
				devTime = null;
				dna = null;
				extPara = null;
								
				
				
			} else if (cmd.equals(AdragonConfig.setSleepStateRes)) {	
				

				DevNotifyApp dna = new DevNotifyApp();
				boolean res = false;				
				String devTime = dna.getSessionDevTime(session);
				
				//JSONObject extPara = new JSONObject();				
				res = dna.proSetSleep(device_id, devId, devTime, reqJsonData);
				
				//if ( res )	respJsonData.setUpdateStart(1);

				devTime = null;
				dna = null;
				//extPara = null;
				
				
			} else if (cmd.equals(AdragonConfig.setLctEsafeRes)) {	

				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				deviceActiveInfo.setCondition("device_id = " + device_id);
//				deviceActiveInfo.setDevice_id(device_id);
				deviceActiveInfo.setEsafe_on(reqJsonData.getLctEsafeFlag());
//				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
			
				deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
				
			} else if (cmd.equals(AdragonConfig.getSsidListRes)) {	
				WTAppDevWifiSrcManAction wdwsm = new WTAppDevWifiSrcManAction();
				wdwsm.proGetSsidListRes(device_id, devId, reqJsonData);

				
			}  else if (cmd.equals(AdragonConfig.upStepDatas)) {
				
				CmdUpPostImpl cmdUpPostImpl = new CmdUpPostImpl();
				RespJsonData respJson = cmdUpPostImpl.stepDatasPost(reqJsonData, session);
				
				respJsonData.setUpStepDatas(respJson.getUpStepDatas());
				
				cmdUpPostImpl = null;
				respJson = null;
			
            }  else if (cmd.equals(AdragonConfig.upSleepDatas)) {
				
				CmdUpPostImpl cmdUpPostImpl = new CmdUpPostImpl();
				RespJsonData respJson = cmdUpPostImpl.sleepDatasPost(reqJsonData, session);
				
				respJsonData.setUpSleepDatas(respJson.getUpSleepDatas());
				
				cmdUpPostImpl = null;
				respJson = null;
					
			} else if (cmd.equals(AdragonConfig.setSsidEsafeRes)) {	
				Integer userId = reqJsonData.getUserId();
				String restrstr = null;

				
				WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade deviceInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				deviceActiveInfo.setCondition("device_id = " + device_id);
//				deviceActiveInfo.setDevice_id(device_id);
				deviceActiveInfo.setEsafe_wifi(reqJsonData.getSsidEsafeFlag());
				deviceActiveInfo.setBssid_wifi(reqJsonData.getBssid());
				deviceActiveInfo.setSsid_wifi(reqJsonData.getSsid());
//				deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
			
				deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
				WMsgInfo aMsg = new WMsgInfo();
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_SEL_ESAFE_WIFI);
				aMsg.setDevice_id(device_id);
				
				JSONObject jon = new JSONObject();
				jon.put("device_id", device_id);
				jon.put("user_id", userId);
				jon.put("ssidEsafeFlag", reqJsonData.getSsidEsafeFlag());
				jon.put("bssid", reqJsonData.getBssid());
				//jon.put("ssid", reqJsonData.getSsid());
				
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {										
					restrstr = mClientSessionManager.completeHttpCmdId(devId, 
							AdragonConfig.setSsidEsafeRes, 
							userId, jon.toString() );
					JSONObject obj = JSONObject.fromObject(restrstr);
					deviceActiveInfo.setCondition("device_id = " + device_id);
					deviceActiveInfo.setSsid_wifi(obj.optString("ssid"));				
					deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);

				}
				
				jon = null;
				
//				aMsg.setMsg_date(BaseAction.getDeviceNow(device_id));				
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {									
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}

				JSONObject jon1 = new JSONObject();
				jon1.put("flag", reqJsonData.getSsidEsafeFlag());
				jon1.put("rel_ssid", reqJsonData.getSsid());	//getSsid()				
				jon1.put("rel_mac", reqJsonData.getBssid());					
				
				aMsg.setMsg_content(jon1.toString());
				aMsg.setHide_flag(Tools.OneString);
				
				lih.proCommonInnerMsg(aMsg, userId);
					
			} else if (cmd.equals(AdragonConfig.getHealthStepRes)) {	
				
				//Integer userId = reqJsonData.getUserId();
				Integer upStepCode = reqJsonData.getUpStepCode();

				DevNotifyApp dna = new DevNotifyApp();
								
				String devTime = dna.getSessionDevTime(session);
				
				JSONObject extPara = new JSONObject();
				extPara.put("upStepCode", upStepCode);
				dna.proGetHeathRes(device_id, devId, devTime, reqJsonData, extPara.toString());
				
				devTime = null;
				dna = null;
				
			} else if (cmd.equals(AdragonConfig.detectDevUpRes)) {	
				
				Integer userId = reqJsonData.getUserId();
				Integer errorCode = reqJsonData.getErrorCode();
				String upVer = reqJsonData.getUpVer();
				String devVer = reqJsonData.getDevVer();

				
				
				JSONObject jon = new JSONObject();
				jon.put("device_id", device_id);
				jon.put("user_id", userId);
				jon.put("errorCode", errorCode);
				jon.put("upVer", upVer);
				jon.put("devVer", devVer);
				
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {										
					mClientSessionManager.completeHttpCmdId(devId, 
							AdragonConfig.detectDevUpRes, 
							userId, jon.toString() );
				}
				
				jon = null;
				
				
				
			}else if (cmd.equals(AdragonConfig.updateFirmwareRes)) {
				
				DevNotifyApp dna = new DevNotifyApp();
				boolean res = false;
				
				String devTime = dna.getSessionDevTime(session);
				
				JSONObject extPara = new JSONObject();
				Integer errorCode = reqJsonData.getErrorCode();
				String upVer = reqJsonData.getUpVer();
				Integer upResult = reqJsonData.getUpResult();
				
				//jon.put("user_id", userId);
				extPara.put("errorCode", errorCode);
				extPara.put("upVer", upVer);
				
				
				if ( 2 == upResult )
					res = dna.proUpdateStart(device_id, devId, devTime, reqJsonData, "");
				else
					res = dna.proUFirmRes(device_id, devId, devTime, reqJsonData, extPara.toString());
					
				if ( res )
					respJsonData.setUpdateFirmwareRes(1);

				devTime = null;
				dna = null;
				extPara = null;
				
				
			} else if (cmd.equals(AdragonConfig.updateStart)) {	
								
				DevNotifyApp dna = new DevNotifyApp();
				boolean res = true;	//false;				
				String devTime = dna.getSessionDevTime(session);
				
				//JSONObject extPara = new JSONObject();				
				//res = dna.proUpdateStart(device_id, devId, devTime, reqJsonData, "");
				
				if ( res )
					respJsonData.setUpdateStart(1);

				devTime = null;
				dna = null;
				//extPara = null;

			
			} else if (cmd.equals(AdragonConfig.setOffOnRes)) {	
				
				//Integer userId = reqJsonData.getUserId();
				Integer offOnFlag = reqJsonData.getOffOnFlag();
				//System.out.println("userId: " + userId + " offOnFlag: " + offOnFlag);

				
				DevNotifyApp dna = new DevNotifyApp();				
				
				String devTime = dna.getSessionDevTime(session);
				
				dna.proSetTOnOffRes(device_id, devId, devTime, reqJsonData, offOnFlag);
				
				devTime = null;
				dna = null;
				
				
				
			} else if (cmd.equals(AdragonConfig.test)) {
				
				System.out.println("device imei no is :  " + devId);
				
				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
				
				cmdDownSetImpl.setBeattim(devId, 6);
				cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
				cmdDownSetImpl.setFlightMode(devId, true);
				cmdDownSetImpl.setLedState(devId, 4, true, 121, null);
				cmdDownSetImpl.setUrgentMode(devId, true, "", 10, 121, null);
				cmdDownSetImpl.setWIFIRange(devId, 3, true);
				
//				respJsonData.setResultCode(Constant.SUCCESS_CODE);

			} else if (cmd.equals(AdragonConfig.testC01)) {
				
				System.out.println("device imei no is :  " + devId);
				
				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
				
//				cmdDownSetImpl.setBeattim(devId, 6);
				//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
				//cmdDownSetImpl.setFlightMode(devId, true);
				//cmdDownSetImpl.setLedState(devId, 4, true);
				//cmdDownSetImpl.setUrgentMode(devId, true);
				//cmdDownSetImpl.setWIFIRange(devId, 3, true);
				
				cmdDownSetImpl.setEcoMode(devId, true, 121, null);
				cmdDownSetImpl.setSleepState(devId, true, 121, null);
				cmdDownSetImpl.setLctEsafe(devId, true, 121);
				cmdDownSetImpl.getSsidList(devId, true, 121, null);
				cmdDownSetImpl.setSsidEsafe(devId, true, "1c:67:58:fe:0d:4d", "empty ssid", 121, null);
				cmdDownSetImpl.getHealthStep(devId, true, 121, null);
				cmdDownSetImpl.detectDevUp(devId, true, 121, null);
				cmdDownSetImpl.updateFirmware(devId, 1, "V12.3.68", 121);
				
//				respJsonData.setResultCode(Constant.SUCCESS_CODE);
				
				
			} else if (cmd.equals(AdragonConfig.testC02)) {
				
				System.out.println("device imei no is :  " + devId);
				
				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
				
				//cmdDownSetImpl.setBeattim(devId, 6);
//				cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
				//cmdDownSetImpl.setFlightMode(devId, true);
				//cmdDownSetImpl.setLedState(devId, 4, true);
				cmdDownSetImpl.setUrgentMode(devId, true, "", 10, 121, null);
				//cmdDownSetImpl.setWIFIRange(devId, 3, true);
//				cmdDownSetImpl.setCallRingTone(devId, 4);
				

				cmdDownSetImpl.setDevOffOn(devId, 1, "22:00", "07:30", 121, null);
				
			} else if (cmd.equals(AdragonConfig.testC03)) {
			
				System.out.println("device imei no is :  " + devId);
				
				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
				
				//cmdDownSetImpl.setBeattim(devId, 6);
				//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
//				cmdDownSetImpl.setFlightMode(devId, true);
				//cmdDownSetImpl.setLedState(devId, 4, true);
				//cmdDownSetImpl.setUrgentMode(devId, true);
				//cmdDownSetImpl.setWIFIRange(devId, 3, true);
				
//				cmdDownSetImpl.setPlayRing(devId, 1, true);
				cmdDownSetImpl.getLocation(devId, 1, 121, null);
				
//				respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
		} else if (cmd.equals(AdragonConfig.testC04)) {
			//session.getRemoteAddress()
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			cmdDownSetImpl.setLedState(devId, 4, true, 121, null);
			//cmdDownSetImpl.setUrgentMode(devId, true);
			//cmdDownSetImpl.setWIFIRange(devId, 3, true);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
		} else if (cmd.equals(AdragonConfig.testC05)) {
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			//cmdDownSetImpl.setLedState(devId, 4, true);
//			cmdDownSetImpl.setUrgentMode(devId, true);
			//cmdDownSetImpl.setWIFIRange(devId, 3, true);
			
			cmdDownSetImpl.getDevState(devId);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
	    
		} else if (cmd.equals(AdragonConfig.testC06)) {
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			//cmdDownSetImpl.setLedState(devId, 4, true);
			//cmdDownSetImpl.setUrgentMode(devId, true);
//			cmdDownSetImpl.setWIFIRange(devId, 3, true);
			cmdDownSetImpl.setDevDebug(devId, true);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
		} else if (cmd.equals(AdragonConfig.testC07)) {
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			//cmdDownSetImpl.setLedState(devId, 4, true);
			//cmdDownSetImpl.setUrgentMode(devId, true);
			//cmdDownSetImpl.setWIFIRange(devId, 3, true);
//			cmdDownSetImpl.setGpsMap(devId, true);
			cmdDownSetImpl.setDevDebug(devId, false);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
		} else if (cmd.equals(AdragonConfig.testC08)) {
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			//cmdDownSetImpl.setLedState(devId, 4, true);
			//cmdDownSetImpl.setUrgentMode(devId, true);
			//cmdDownSetImpl.setWIFIRange(devId, 3, true);
			cmdDownSetImpl.setGpsMap(devId, true);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
		} else if (cmd.equals(AdragonConfig.testC09)) {
			
			System.out.println("device imei no is :  " + devId);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			//cmdDownSetImpl.setBeattim(devId, 6);
			//cmdDownSetImpl.setFamilyNumber(devId, "water01", "test1", "Crusion");
			//cmdDownSetImpl.setFlightMode(devId, true);
			//cmdDownSetImpl.setLedState(devId, 4, true);
			//cmdDownSetImpl.setUrgentMode(devId, true);
			//cmdDownSetImpl.setWIFIRange(devId, 3, true);
			cmdDownSetImpl.setGpsMap(devId, true);
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
		} else if (cmd.equals(AdragonConfig.testC10)) {
			
			System.out.println("device imei no is :  " + devId);
		//Just test TimeZone DST case:	
			String inputDate = "2016-03-13 14:59:59";
			String inputDate1 = "2016-03-13 15:00:00";
			TimeZone timeZoneSH = TimeZone.getTimeZone("Asia/Shanghai");      
			TimeZone timeZoneNY = TimeZone.getTimeZone("America/New_York");  
			
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			inputFormat.setTimeZone(timeZoneSH);
			Date date = null;
			Date date1 = null;
			try {
				date = inputFormat.parse(inputDate);
				date1 = inputFormat.parse(inputDate1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			
			SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.US);
			
			outputFormat.setTimeZone(timeZoneSH);
			System.out.println("Asia/Shanghai:" + outputFormat.format(date));
			outputFormat.setTimeZone(timeZoneNY);
			System.out.println("America/New_York:" + outputFormat.format(date));
			
			outputFormat.setTimeZone(timeZoneSH);
			System.out.println("Asia/Shanghai:" + outputFormat.format(date1));
			outputFormat.setTimeZone(timeZoneNY);
			System.out.println("America/New_York:" + outputFormat.format(date1));

			//TimeZone timeZoneNY = TimeZone.getTimeZone("America/New_York");
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4");
			outputFormat.setTimeZone(timeZoneNY);
			Date dateNow = new Date(System.currentTimeMillis());
			respJsonData.setDevTime(outputFormat.format(dateNow));
			
//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
						
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			TimeZone timeZone = cal.getTimeZone();
			System.out.println("local time zone : " + timeZone);
			String s[] = TimeZone.getAvailableIDs();
			for (int i = 0; i < s.length; i++) {
				System.out.println(s[i]);
			}
			
		}
				
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("wtdevhandler err:session: " + session.getId() + " msg: "+ msg + " exception: " + e.toString());
			respJsonData.setResultCode(Constant.EXCEPTION_CODE);  //请求失败、异常
			respJsonData.setDevTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
		}
		
		return  respJsonData;
	}

	//如果设备登录的时候发现有此设备相关 的用户，对应的APP在前台，需要下发“下行设备指令-----”	
	public static void proPwronDevGpsStatus(Integer device_id, String device_imei) throws SystemException {
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();

		wsi.setDevice_id(device_id);
		if (wsi_fd.getDevRelUserOnlineCount(wsi) > 0)	{
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯
			cmdDownSetImpl.setGpsMap(device_imei, true);				
		}
			
	}
		
	public RespJsonData proLctMgr(ReqJsonData reqJsonData, String devId, 
			Integer device_id, String cmd) {
		RespJsonData respJsonData = new RespJsonData();
		Tools tls = new Tools();
		WTSigninAction ba = new WTSigninAction();
		
		try {			
			
			boolean bool_is_update = true;  // move here
			String locationType = reqJsonData.getType(); // 1 gaode GPS; 2 gaode lbs; 3 gaode wifi; 4 google gps; 5 google lbs/wifi
			String battery = reqJsonData.getBattery();
			String lctTime = reqJsonData.getLctTime();
			String fall = Tools.OneString;  //reqJsonData.getFall();  //1表示戴上，0表示脱落
			double lng1 = 0;
			double lat1 = 0;
			int stepCount = 0;
			
			Location respLct = new Location();
			
			if (!"".equals(reqJsonData.getStepNumber()) && reqJsonData.getStepNumber() != null) {
				stepCount = Integer.parseInt(reqJsonData.getStepNumber().trim());
			} else if (reqJsonData.getStepCount() != null) {
				stepCount = reqJsonData.getStepCount();
			}
			
			LocationInfo locationInfo = new LocationInfo();
			locationInfo.setSerieNo(devId);		//smile add
			locationInfo.setBattery(Integer.parseInt(battery));	//smile add
			

		    //2016.12.17,不再需要设备心跳时间戳记录
			locationInfo.setDevice_id(device_id);
			
			if(Tools.OneString.equals(locationType)) {
				respJsonData = proLctGaodeGps(reqJsonData, devId, device_id, cmd);
				if ( respJsonData.getLctUpdate() == 1 ) {
					bool_is_update = true;
					locationInfo.setChangeLatitude(respJsonData.getLocation().getLat());
					locationInfo.setChangeLatitude(respJsonData.getLocation().getLon());
					locationInfo.setAccuracy(Float.parseFloat(respJsonData.getLocation().getAcc()));
					locationInfo.setLocationType(Tools.OneString);

				} else
					bool_is_update = false;
					
			} else if ("2".equals(locationType)){      
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				String id = "";
				String jsonToString = "";
				double lng2 = 0.0;
				double lat2 = 0.0;
				double distDiff2 = 0.0;
				int stepDiff = 0;
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				map.put("accesstype", Tools.ZeroString);
				map.put("network", network);
				map.put("cdma", cdma);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("bts", bts);
				map.put("nearbts", nearbts);
				map.put("key", Constant.KEY);
//				if (serverip != null && !"".equals(serverip)) {
//					map.put("serverip", serverip);
//				} else {
//					map.put("serverip", Constant.SERVER_IP);
//				}
				map.put("serverip", serverip);
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				if (locationInfos.size() > 0) {
					
					id = locationInfos.get(0).getAt("id").toString();
					lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
					lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
					Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
					Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
					
					stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
					
					long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
					Long interval = (lctUpDate.getTime() - sdtf.parse(
							locationInfos.get(0).get("upload_time")
									.toString()).getTime()) / 1000;

					
					{
						jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);

						if ("-1".equals(jsonToString)) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals(Tools.OneString)) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								String radius = jsonResult.getString("radius"); 
								if (location != null) {	
									String[] locations = location.split(",");

									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius); 
		
									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);
									
									distDiff2 = Constant.getDistance(lat1, lng1, lat2, lng2);
									
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									//如果上次为非LBS, 则本次时间间隔一定大于5分钟
									if (tls.checkIfAbandonLct(distDiff2, interval, stepDiff, preLctType, 
											locationType, preLctAcc, Float.parseFloat(radius))) {												
										
										serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
										bool_is_update = true;
									}  else {															
										bool_is_update = false;
										locationInfo.setChangeLongitude(null);
										locationInfo.setChangeLatitude(null);
										locationInfo.setAccuracy(null);										
									}

										
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);

									
								} else {
									locationInfo = null;
									respJsonData.setLctLbs(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								}
							} else if (status.equals(Tools.ZeroString)) { 
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							} else if (status.equals("-1")) {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
								respJsonData.setLctTime(lctTime);
							}
						}
						
					}
				} else {
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					if ("-1".equals(jsonToString)) {
						locationInfo = null;
						respJsonData.setLctLbs(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals(Tools.OneString)) { 
							String results = jsons.getString("result"); 
							JSONObject jsonResult = JSONObject.fromObject(results);
							String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
							String radius = jsonResult.getString("radius"); 
							if (location != null) {	
								String[] locations = location.split(",");

								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
	
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								bool_is_update = true;
																
								respLct.setLon(locations[0]);
								respLct.setLat(locations[1]);
								respLct.setAcc(radius);
								
								respJsonData.setLctLbs(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
						} else if (status.equals(Tools.ZeroString)) { 
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else if (status.equals("-1")) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
					}
					
				}
				
				netWorkInfo = null;
				network = null;
				cdma = null;
				smac = null;
				bts = null;
				nearbts = null;
				serverip = null;
				sdtf = null; 
				lctUpDate = null;
				jsonToString = null;
				map.clear();
				map = null;
				locationInfos.clear();
				locationInfos = null;
				
			} else if ("3".equals(locationType)) { // wifi gaode map api

				NetWorkInfoAdr netWorkInfo1 = reqJsonData.getNetWork();
				String network1 = netWorkInfo1.getNetWork();
				String bts1 = netWorkInfo1.getBts();
				String nearbts1 = netWorkInfo1.getNearbts();
				
				
				Boolean lctWIFIFlag = false;
				
				WifiInfoAdr wifi = reqJsonData.getWifi();
				String smac = wifi.getSmac();
				String mmac = wifi.getMmac();
				String macs = wifi.getMacs();
				String serverip = wifi.getServerip();
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));

				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				
				
				map.put("accesstype", Tools.OneString);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("mmac", mmac);
				map.put("macs", macs);
				map.put("bts", bts1);
				map.put("nearbts", nearbts1);
				
				map.put("key", Constant.KEY);
				map.put("serverip", serverip);
				
			try {
				String jsonToString = HttpRequest.sendGetToGaoDe(
						Constant.LOCATION_URL, map);

				
				if ("-1".equals(jsonToString)) {
//					locationInfo = null;
					lctWIFIFlag = false;
					respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				} else {
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status"); 
					
					Boolean isValidWIFI = false;


					
					
					if (status.equals(Tools.OneString)) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject
								.fromObject(results);
						
						String type = jsonResult.optString("type");
						
						if ("0".equals(type)) {
//							locationInfo = null;
							lctWIFIFlag = false;
							respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							
							String location = jsonResult.getString("location"); 
							String radius = jsonResult.getString("radius");
							String[] locations = location.split(",");
							
							bool_is_update = true;	
							lctWIFIFlag = true;
							
							
							String id = "";
							if (locationInfos.size() > 0) { // 说明有数据
								id = locationInfos.get(0).getAt("id").toString();
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
								
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								lng1 = Double.parseDouble(locations[0]);
								lat1 = Double.parseDouble(locations[1]);
								
								double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
								int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);

								Long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time")
												.toString()).getTime()) / 1000;
								
								//距离一定是小于最大距离临界值本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								
								///BaseAction.insertVisit(user_id, device_imei, device_id, href);
								
								//if (   (diffDist2 < interval && diffDist2 < (double)diffStep2) || preLctAcc > 170.0f   ) {
								if ( tls.checkIfAbandonLct(diffDist2, interval, 
										diffStep2, preLctType, locationType, 
										preLctAcc, Float.parseFloat(radius) ) ) {
									bool_is_update = true;
									isValidWIFI = true;

								} else {
									bool_is_update = false;
									isValidWIFI	= true;		//aha
								}
								
								//if (diffDist2 > Constant.IS_VALID_WIFI && diffStep2 < Constant.WIFI_DIFF_STEPS && ("3".equals(preLctType))) {
								//	isValidWIFI = false;
									
								//} else {
								//	isValidWIFI = true;
								//}

//								bool_is_update = Constant.getDistance(lat1,
//										lng1, lat2, lng2,
//										Constant.EFFERT_DATA); // 若为true表示有效数据
							} else {
								bool_is_update = true;
								isValidWIFI = true;
							}

							respLct.setLon(locations[0]);
							respLct.setLat(locations[1]);
							respLct.setAcc(radius);
							
							//locationInfos.clear();
							//locationInfos = null;

							if (isValidWIFI) {
								if (bool_is_update) {
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								} else {
									//locationInfo.setCondition("id ='" + id + "'");
									//locationInfo.setUploadTime(lctUpDate);
									//locationInfo.setStepCount(stepCount);
									//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
								}
								
								
								lctWIFIFlag = true;
								respJsonData.setUploadlctWIFI(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {	
//								locationInfo = null;
								lctWIFIFlag = false;
								respJsonData.setUploadlctWIFI(Constant.INVALID_DATA);
								respJsonData.setLctTime(lctTime);
							}

						} 
						
					} else if (status.equals(Tools.ZeroString)) {
//						locationInfo = null;
						lctWIFIFlag = false;
						respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else if (status.equals("-1")) {
//						locationInfo = null;
						lctWIFIFlag = false;
						respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
						respJsonData.setLctTime(lctTime);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Gaode WIFI location Exception!!");
//				locationInfo = null;
				lctWIFIFlag = false;
				respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
				respJsonData.setLctTime(lctTime);
			}
			
			
			if (lctWIFIFlag == false) {
				
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				
				if (null != netWorkInfo) {
					
					String network = netWorkInfo.getNetWork();
					String cdma = netWorkInfo.getCdma();
					smac = netWorkInfo.getSmac();
					String bts = netWorkInfo.getBts();
					String nearbts = netWorkInfo.getNearbts();
					serverip = netWorkInfo.getServerip();
					map.clear();

					String jsonToString = "";
										
					map.put("accesstype", Tools.ZeroString);
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("key", Constant.KEY);
//					if (serverip != null && !"".equals(serverip)) {
//						map.put("serverip", serverip);
//					} else {
//						map.put("serverip", Constant.SERVER_IP);
//					}
					map.put("serverip", serverip);
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					if ("-1".equals(jsonToString)) {
						locationInfo = null;
						respJsonData.setLctLbs(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals(Tools.OneString)) { 
							
							
							String results = jsons.getString("result"); 
							JSONObject jsonResult = JSONObject.fromObject(results);
							String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
							String radius = jsonResult.getString("radius"); 
							if (location != null  ) {	
								
								
								if ( locationInfos.isEmpty() ) {
									bool_is_update = true;
								} else {
									double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
									double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
									int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
									Long interval = (lctUpDate.getTime() - sdtf
											.parse(locationInfos.get(0)
													.get("upload_time")
													.toString()).getTime()) / 1000;
									
									double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
									int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);
									String preLctType = locationInfos.get(0).getAt("location_type").toString();
									Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());

													
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									//如果上次为非LBS, 则本次时间间隔一定大于5分钟
									if ( tls.checkIfAbandonLct(diffDist2, interval, 
											diffStep2, preLctType, locationType, 
											preLctAcc, Float.parseFloat(radius) ) ) {
										bool_is_update = true;
									} else {
										bool_is_update = false;										
									}
									
								}
								if ( bool_is_update ) {
									String[] locations = location.split(",");
	
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
		
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius);
									
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									//String id = locationInfos.get(0).getAt("id").toString();

									//locationInfo.setCondition("id ='" + id + "'");
									//locationInfo.setUploadTime(lctUpDate);
									//locationInfo.setStepCount(stepCount);
									//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
									
								}
									
							} else {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
						} else { 
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} 
					}
					
				}
				
			}
			
				
				wifi = null;
				smac = null;
				mmac = null;
				macs = null;
				serverip = null;
				sdtf = null; 
				lctUpDate = null;
//				jsonToString = null;
				map.clear();
				map = null;

			} else if("4".equals(locationType)) {     //Googel Map gps
				GPSInfoAdr gps = reqJsonData.getGps();
				String longitude = gps.getLon(); //经度
				String latitude = gps.getLat(); //纬度
				String accuracy = gps.getAcc(); //精确度
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				if (devId != null && !"".equals(devId) &&  longitude != null
						&& !"".equals(longitude) && latitude != null
						&& !"".equals(latitude) && accuracy != null
						&& !"".equals(accuracy) && locationType != null
						&& !"".equals(locationType)) {

						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						longitude = String.format("%.12f", lng1);
						latitude = String.format("%.12f", lat1);
						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						
					if (lng1 != 0 && lat1 != 90) { // 直接过滤
						
						locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
						//		" and accuracy < 40.0 ");
						locationInfo.setOrderBy("upload_time");
						locationInfo.setSort(Tools.OneString); // 按upload_time降序
						locationInfo.setFrom(0);
						locationInfo.setPageSize(1); // 0至1

						List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
						
						if (locationInfos.size() > 0) { 
							    String id = "" + locationInfos.get(0).getAt("id");
								double lng2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("longitude"));
								double lat2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("latitude"));	
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								
								double diffDist4 = Constant.getDistance(lat1, lng1, lat2, lng2);
								long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time").toString())
										.getTime()) / 1000;
								
								int stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
								
								//距离一定是小于最大距离法制本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								if ( tls.checkIfAbandonLct(diffDist4, interval, 
										stepDiff, preLctType, locationType, 
										preLctAcc, Float.parseFloat(accuracy) ) ) {

								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(longitude);
								locationInfo.setLatitude(latitude);
								locationInfo.setAccuracy(Float.parseFloat(accuracy));
								locationInfo.setLocationType(locationType);
								locationInfo.setChangeLongitude(longitude);
								locationInfo.setChangeLatitude(latitude);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);								
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
								bool_is_update = true;

								
							} else {
								//locationInfo.setCondition("id ='" + id + "'");
								//locationInfo.setUploadTime(lctUpDate);
								//locationInfo.setStepCount(stepCount);
								
								//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
								bool_is_update = false;
								
							}
						} else {
							
							locationInfo.setSerieNo(devId);
							locationInfo.setBattery(Integer.parseInt(battery));
							locationInfo.setLongitude(longitude);
							locationInfo.setLatitude(latitude);
							locationInfo.setAccuracy(Float.parseFloat(accuracy));
							locationInfo.setLocationType(locationType);
							locationInfo.setChangeLongitude(longitude);
							locationInfo.setChangeLatitude(latitude);
							locationInfo.setUploadTime(lctUpDate);
							locationInfo.setFall(fall);								
							locationInfo.setBelongProject(Tools.OneString);
							locationInfo.setStepCount(stepCount);
							
							serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
							bool_is_update = true;
						}
						

						respJsonData.setLctGGps(Constant.SUCCESS_CODE);
						respJsonData.setLctTime(lctTime);
							
						} else {
							locationInfo = null;
							respJsonData.setLctGGps(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						}
					}
					
					gps = null;
					longitude = null;
					latitude = null;
					accuracy = null;
					sdtf = null;
					lctUpDate = null;
					
			 } else if ("5".equals(locationType)) {     //Google geolocation API   

				String id = "";
				String strGeolocation = "";
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				Geolocation geolocation = reqJsonData.getGeolocation();
				JSONObject gjsons = JSONObject.fromObject(geolocation);
				
				String param2 = gjsons.toString();
		
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				if (locationInfos.size() > 0) { 
					id = locationInfos.get(0).getAt("id").toString();
					double lng2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("longitude"));
					double lat2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("latitude"));	
					
					Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
					
					Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
					
					int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
							
					long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
					
					int diffStep5 = tls.getDiffSteps(stepCountPre, stepCount);
					//double distDiff5 = Constant.getDistance(lat1, lng1, lat2, lng2);
					
					long interval = (lctUpDate.getTime() - sdtf.parse(
							locationInfos.get(0).get("upload_time")
									.toString()).getTime()) / 1000;
					
					
					{
						
						try {
							
							strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
							
//							System.out.println("--- cmd process call google API return : " + strGeolocation);
							
							if (strGeolocation != null && !strGeolocation.equals("")) {
								
								JSONObject geoJson = JSONObject.fromObject(strGeolocation);
								
								String error = geoJson.has("error")?geoJson.getString("error"):null;
								
								if (error == null) {
									String location = geoJson.getString("location");
									JSONObject locationJson = JSONObject.fromObject(location);
									double lat = locationJson.getDouble("lat");
									double lng = locationJson.getDouble("lng");
									double accuracy = geoJson.getDouble("accuracy");

									double diffDist5 = Constant.getDistance(lat, lng, lat2, lng2);
									
									//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									if ( tls.checkIfAbandonLct(diffDist5, interval, 
											diffStep5, preLctType, locationType, 
											preLctAcc, (float)accuracy ) ) {										locationInfo.setLongitude(Double.toString(lng));
										locationInfo.setLatitude(Double.toString(lat));
										locationInfo.setChangeLongitude(Double.toString(lng));
										locationInfo.setChangeLatitude(Double.toString(lat));
										locationInfo.setAccuracy((float) accuracy);
										locationInfo.setUploadTime(lctUpDate);
										
										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setLocationType(locationType);
										locationInfo.setFall(fall);
										locationInfo.setBelongProject(Tools.OneString);
										locationInfo.setStepCount(stepCount);
										serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
										bool_is_update = true;
										
									} else {
										//locationInfo.setCondition("id ='" + id + "'");
										//locationInfo.setUploadTime(lctUpDate);
										//locationInfo.setStepCount(stepCount);
										//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
										bool_is_update = false;
										
									}
									
									respLct.setLon(Double.toString(lng));
									respLct.setLat(Double.toString(lat));
									respLct.setAcc(Double.toString(accuracy));
									
									respJsonData.setLctGMap(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									locationInfo = null;
									JSONObject errorJson = JSONObject.fromObject(error);
									String errorCode = errorJson.getString("code");	
									respJsonData.setLctGMap(Integer.parseInt(errorCode));
									respJsonData.setLctTime(lctTime);
								}
							} else {
								locationInfo = null;
								respJsonData.setLctGMap(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
							
						} catch(Exception e){
							e.printStackTrace();
//							System.out.println("Google Map Geolocation Exception!!");
							logger.error("Google Map Geolocation Exception ---1---" + "\r\n" + e.toString());
							locationInfo = null;
							respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
						
					}
					
				} else {
					
					try {
						
						strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
						
//						System.out.println("--- cmd process call google API return : " + strGeolocation);
						
						if (strGeolocation != null && !strGeolocation.equals("")) {
							
							JSONObject geoJson = JSONObject.fromObject(strGeolocation);
							
							String error = geoJson.has("error")?geoJson.getString("error"):null;
							
							if (error == null) {
								String location = geoJson.getString("location");
								JSONObject locationJson = JSONObject.fromObject(location);
								double lat = locationJson.getDouble("lat");
								double lng = locationJson.getDouble("lng");
								double accuracy = geoJson.getDouble("accuracy");

								locationInfo.setLongitude(Double.toString(lng));
								locationInfo.setLatitude(Double.toString(lat));
								locationInfo.setChangeLongitude(Double.toString(lng));
								locationInfo.setChangeLatitude(Double.toString(lat));
								locationInfo.setAccuracy((float) accuracy);
								locationInfo.setUploadTime(lctUpDate);
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLocationType(locationType);
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
								bool_is_update = true;
								
								respLct.setLon(Double.toString(lng));
								respLct.setLat(Double.toString(lat));
								respLct.setAcc(Double.toString(accuracy));
								
								respJsonData.setLctGMap(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {
								locationInfo = null;
								JSONObject errorJson = JSONObject.fromObject(error);
								String errorCode = errorJson.getString("code");									
								respJsonData.setLctGMap(Integer.parseInt(errorCode));
								respJsonData.setLctTime(lctTime);
							}
						} else {
							locationInfo = null;
							respJsonData.setLctGMap(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						}
						
					} catch(Exception e) {
						e.printStackTrace();
//						System.out.println("Google Map Geolocation Exception!!");
						logger.error("Google Map Geolocation Exception ---2---" + "\r\n" + e.toString());
						locationInfo = null;
						respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
						respJsonData.setLctTime(lctTime);
					}
					
				}
				
				
				sdtf = null;
				lctUpDate = null;
				geolocation = null;
				gjsons = null;
				param2 = null;

			//Google geolocation API
			
		} else if ("6".equals(locationType)) {   //Gaode map lbs/wifi location case
			try {
			
				String jsonToString = null;
				String mmac = null;
				String macs = null;
				
				SimpleDateFormat sdtf = null; 
				Date lctUpDate = null;
	
				
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				
				WifiInfoAdr wifi = reqJsonData.getWifi();
			
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				String ts = ((String) wdeviceActiveInfos.get(0).getAt("test_status")).trim();

				sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));

				
				if ( Tools.OneString.equals(ts) ) {
					WTDevGWAction wdga = new WTDevGWAction();
					StringBuffer sbb = new StringBuffer("{\"pwd\":\"afdzczb$nvn@mv:mn4259i-0\",\"imei\":\"");
					sbb.append(devId);
					sbb.append("\",\"net\":{\"network\":");
					sbb.append(JSON.toJSONString(netWorkInfo));
					sbb.append("},\"wifi\":{\"wifi\":");
					sbb.append(JSON.toJSONString(wifi));
					sbb.append("}}");
					
					jsonToString = wdga.httpPostInner(Constant.GAODE_SRV, sbb.toString());
					JSONObject jo = JSONObject.fromObject(jsonToString);
					if (jo.optInt("resultCode") == 1)
						jsonToString = jo.optString("res");
					else
						jsonToString = jo.optString("res");
					
					wdga = null;
				}
			
				else {
				
					
		//			String smac = wifi.getSmac();
					mmac = wifi.getMmac();
					macs = wifi.getMacs();
		//			String serverip = wifi.getServerip();
					
		
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
					map.put("accesstype", Tools.ZeroString);
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("mmac", mmac);
					map.put("macs", macs);
					map.put("key", Constant.KEY);
		//			if (serverip != null && !"".equals(serverip)) {
		//				map.put("serverip", serverip);
		//			} else {
		//				map.put("serverip", Constant.SERVER_IP);
		//			}
					map.put("serverip", serverip);
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);
				}
			
				if ("-1".equals(jsonToString)) {
					locationInfo = null;
					respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				} else {
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status"); 
					
					if (status.equals(Tools.OneString)) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject
								.fromObject(results);
						
						String type = jsonResult.optString("type");
						
						if ("0".equals(type)) {
							locationInfo = null;
							respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							
							String location = jsonResult.getString("location"); 
							String radius = jsonResult.getString("radius");
							String[] locations = location.split(",");
							
							locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
							
							locationInfo.setOrderBy("upload_time");
							locationInfo.setSort(Tools.OneString); // 按upload_time降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1
	
							List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
							bool_is_update = true;	
							
							String id = "";
							if (locationInfos.size() > 0) { // 说明有数据
								id = locationInfos.get(0).getAt("id").toString();
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								lng1 = Double.parseDouble(locations[0]);
								lat1 = Double.parseDouble(locations[1]);
								
								double diffDist6 = Constant.getDistance(lat1, lng1, lat2, lng2);
								int diffStep6 = tls.getDiffSteps(stepCountPre, stepCount);
								long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time")
												.toString()).getTime()) / 1000;							
								//if ( diffStep6 > Constant.WIFI_DIFF_STEPS || diffDist6 > Constant.WIFI_DIFF_DIST_GAP || (Float.parseFloat(radius) < preLctAcc)) {
								//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
	
								//距离一定是小于最大距离临界值本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								if ( tls.checkIfAbandonLct(diffDist6, interval, 
										diffStep6, preLctType, locationType, 
										preLctAcc, Float.parseFloat(radius) ) ) {					
									bool_is_update = true;
								} else {
									bool_is_update = false;
								}
							} else {
								bool_is_update = true;
							}
	
							if (bool_is_update) {
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
							} else {
								locationInfo.setCondition("id ='" + id + "'");
	
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								
	
								
								//locationInfo.setUploadTime(lctUpDate);
								//locationInfo.setStepCount(stepCount);
								//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
							}
								
							
							respLct.setLon(locations[0]);
							respLct.setLat(locations[1]);
							respLct.setAcc(radius);
							
							respJsonData.setLctLbsWifi(Constant.SUCCESS_CODE);
							respJsonData.setLocation(respLct);
							respJsonData.setLctTime(lctTime);
						} 
						
					} else {
						locationInfo = null;
						respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} 
				}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Gaode LBS and WIFI location Exception!!");
			logger.error("Gaode LBS and WIFI location Exception!" + "\r\n" + e.toString());
			locationInfo = null;
			respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
			respJsonData.setLctTime(lctTime);
		}
		
				 
//	} //Gaode lbs and wifi location case end
		} else if ("7".equals(locationType)) { //update lctTime
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				

				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				String id = "";
				if (locationInfos.size() > 0) { 
					
					id = locationInfos.get(0).getAt("id").toString();
					locationInfo.setCondition("id ='" + id + "'");

					String lonStr = locationInfos.get(0).getAt("longitude").toString();
					String latStr = locationInfos.get(0).getAt("latitude").toString();
					String accStr = locationInfos.get(0).getAt("accuracy").toString();
					
					//locationInfo.setBattery(Integer.parseInt(battery));								
					Float acc = Float.parseFloat(accStr);
					locationInfo.setAccuracy(acc);
					locationInfo.setUploadTime(lctUpDate);
					locationInfo.setStepCount(stepCount);

					ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
					
					respLct.setLon(lonStr);
					respLct.setLat(latStr);
					respLct.setAcc(accStr);
					
					//yonghu start
					/*
					LocationInfoHelper lih = new LocationInfoHelper();
					lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
							cmd.equals(AdragonConfig.uploadLctData));
					lih = null;
					*/
					//yonghu end	
					
					respJsonData.setLctUpdate(Constant.SUCCESS_CODE);
					respJsonData.setLocation(respLct);
					respJsonData.setLctTime(lctTime);
					
					lonStr = null;
					latStr = null;
					accStr = null;

				} else {
					respJsonData.setLctUpdate(Constant.FAIL_CODE);
					respJsonData.setLctTime(lctTime);
				}

				sdtf = null; 
				lctUpDate = null;
				id = null;

		 }		
			
			//yonghu start
			if (locationInfo != null) {				
				LocationInfoHelper lih = new LocationInfoHelper();
				//BaseAction.insertVisit("-1", null, null, "proLctInfo");
				lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
						cmd.equals(AdragonConfig.uploadLctData));
				lih = null;
			}
			//yonghu end	
						
			try {
				if (cmd.equals(AdragonConfig.getLocationRes)) { 
					
//					if (devId != null && !"".equals(devId)) {
//					if (locationInfo != null && (!"".equals(locationInfo.getSerieNo()) && locationInfo.getSerieNo() != null )
//							&& (!"".equals(locationInfo.getChangeLatitude()) && locationInfo.getChangeLatitude() != null )
//						    && (!"".equals(locationInfo.getChangeLongitude()) && locationInfo.getChangeLongitude() != null )) {
//						
//						serviceBean.getLocationInfoFacade().insertClickLocationInfo(locationInfo);	
					
					if (locationInfo != null) {
						
						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
							Integer userId = reqJsonData.getUserId();
							//inform app user get update location
							WTAppGpsManAction wgma = new WTAppGpsManAction();							
							wgma.proGetDevLoc17(userId, device_id, devId);
							wgma = null;
						}

					} else {
						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
							Integer userId = reqJsonData.getUserId();
							//inform app user get update location fail 
							mClientSessionManager.completeFailHttpCmdId(userId, device_id, devId, AdragonConfig.getLocationRes);
						}
						
					}
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("getLocationRes exception!" + "\r\n" + e.toString());
			}

			devId = null;
			locationType = null;
			battery = null;
			fall = null;
		    locationInfo = null;
		    					
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return respJsonData;
	}
	
	//abcde 1234
	public RespJsonData proLctGaodeGps(ReqJsonData reqJsonData, String devId, Integer device_id, String cmd) throws ParseException, SystemException {
		Tools tls = new Tools();		
		
		RespJsonData respJsonData = new RespJsonData();
		boolean bool_is_update = true;  // move here
		String locationType = reqJsonData.getType(); // 1 gaode GPS; 2 gaode lbs; 3 gaode wifi; 4 google gps; 5 google lbs/wifi
		String battery = reqJsonData.getBattery();
		String lctTime = reqJsonData.getLctTime();
		String fall = Tools.OneString;  //reqJsonData.getFall();  //1表示戴上，0表示脱落
		double lng1 = 0;
		double lat1 = 0;
		int stepCount = 0;
		
		
		if (!"".equals(reqJsonData.getStepNumber()) && reqJsonData.getStepNumber() != null) {
			stepCount = Integer.parseInt(reqJsonData.getStepNumber().trim());
		} else if (reqJsonData.getStepCount() != null) {
			stepCount = reqJsonData.getStepCount();
		}
		
		LocationInfo locationInfo = new LocationInfo();
		locationInfo.setSerieNo(devId);		//smile add
		

	    //2016.12.17,不再需要设备心跳时间戳记录
		locationInfo.setDevice_id(device_id);
		locationInfo.setBattery(Integer.parseInt(battery));								
		respJsonData.setLctUpdate(0);

		GPSInfoAdr gps = reqJsonData.getGps();
		
		String longitude = gps.getLon(); //经度
		String latitude = gps.getLat(); //纬度
		String accuracy = gps.getAcc(); //精确度
		
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
		String result = "";
		JSONObject object;
		String location = "";
		String[] str;
		
		if (devId != null && !"".equals(devId) && longitude != null
			&& !"".equals(longitude) && latitude != null
			&& !"".equals(latitude) && accuracy != null
			&& !"".equals(accuracy) && locationType != null
			&& !"".equals(locationType)) {

			lng1 = Double.parseDouble(longitude);
			lat1 = Double.parseDouble(latitude);
			longitude = String.format("%.12f", lng1);
			latitude = String.format("%.12f", lat1);
			lng1 = Double.parseDouble(longitude);
			lat1 = Double.parseDouble(latitude);
			
			if (lng1 != 0 && lat1 != 90) { // 直接过滤
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				//boolean bool_is_update = true;
				
				
				
				if (locationInfos.size() > 0) { 
					String id = "" + locationInfos.get(0).getAt("id");
					double lng2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("longitude"));
					double lat2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("latitude"));	
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
					
					double diffDist1 = Constant.getDistance(lat1, lng1, lat2, lng2);
					
					Long interval = (lctUpDate.getTime() - sdtf
							.parse(locationInfos.get(0)
									.get("upload_time").toString())
							.getTime()) / 1000;					
					
					if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
						//locationInfo.setCondition("id ='" + id + "'");								
						//locationInfo.setAccuracy(Float.parseFloat(accuracy));	//smile add
						//locationInfo.setBattery(Integer.parseInt(battery));
						//locationInfo.setUploadTime(lctUpDate);
						//locationInfo.setStepCount(stepCount);	//smile add
						
						
						//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
						bool_is_update = false;
						respJsonData.setLctGps(Constant.SUCCESS_CODE);
						respJsonData.setLctTime(lctTime);
						
					} else {
						result = HttpRequest
								.sendGet(
										"http://restapi.amap.com/v3/assistant/coordinate/convert",
										"locations="
												+ longitude
												+ ","
												+ latitude
												+ "&coordsys=gps&output=json&key=" + Constant.KEY/*KEY_1)*/);
						if ("-1".equals(result)) {

							respJsonData.setLctGps(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
//							locationInfo.setChangeLongitude(Tools.ZeroString);
//							locationInfo.setChangeLatitude(Tools.ZeroString);
							locationInfo = null;
							bool_is_update = false;
						} else {

							int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
							double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
							int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);
							Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
							
							//距离一定是小于最大距离临界值本次定位才有效
							//或者每步的距离一定小于1米，
							//或者上次定位为LBS,但本次为非LBS
							if ( tls.checkIfAbandonLct(diffDist2, interval, 
									diffStep2, preLctType, locationType, 
									preLctAcc, Float.parseFloat(accuracy) ) ) {		
							
							
								
								object= JSONObject.fromObject(result);;
								
								location = object.getString("locations");
								str = location.split(",");
								//if (str.length > 0) {
									locationInfo.setChangeLongitude(str[0]);
									locationInfo.setChangeLatitude(str[1]);
								//}
									
									
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(longitude);
								locationInfo.setLatitude(latitude);
								locationInfo.setAccuracy(Float.parseFloat(accuracy));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);								
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
									
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								bool_is_update = true;

								Location loc = new Location();
								loc.setLon(str[0]);
								loc.setLat(str[1]);
								loc.setAcc(accuracy);
								respJsonData.setLocation(loc);
								respJsonData.setLctUpdate(1);
							
							} else {
								//locationInfo.setCondition("id ='" + id + "'");								
								//locationInfo.setAccuracy(Float.parseFloat(accuracy));	//smile add
								//locationInfo.setUploadTime(lctUpDate);
								//locationInfo.setStepCount(stepCount);	//smile add
								//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
								bool_is_update = false;
							}
															
							
							respJsonData.setLctGps(Constant.SUCCESS_CODE);
							respJsonData.setLctTime(lctTime);
							bool_is_update = true;
						}
					}
				} else {

					result = HttpRequest
							.sendGet(
									"http://restapi.amap.com/v3/assistant/coordinate/convert",
									"locations="
											+ longitude
											+ ","
											+ latitude
											+ "&coordsys=gps&output=json&key=" + Constant.KEY_1);
					if ("-1".equals(result)) {

						respJsonData.setLctGps(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
//						locationInfo.setChangeLongitude(Tools.ZeroString);
//						locationInfo.setChangeLatitude(Tools.ZeroString);
						locationInfo = null;
						bool_is_update = false;
					} else {
						object= JSONObject.fromObject(result);;
						
						location = object.getString("locations");
						str = location.split(",");
						//if (str.length > 0) {
							locationInfo.setChangeLongitude(str[0]);
							locationInfo.setChangeLatitude(str[1]);
						//}
						
							
						locationInfo.setSerieNo(devId);
						locationInfo.setBattery(Integer.parseInt(battery));
						locationInfo.setLongitude(longitude);
						locationInfo.setLatitude(latitude);
						locationInfo.setAccuracy(Float.parseFloat(accuracy));
						locationInfo.setLocationType(locationType);
						locationInfo.setUploadTime(lctUpDate);
						locationInfo.setFall(fall);								
						locationInfo.setBelongProject(Tools.OneString);
						locationInfo.setStepCount(stepCount);
							
						serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
						respJsonData.setLctUpdate(1);

						respJsonData.setLctGps(Constant.SUCCESS_CODE);
						Location loc = new Location();
						loc.setLon(str[0]);
						loc.setLat(str[1]);
						loc.setAcc(accuracy);
						
						respJsonData.setLocation(loc);
						respJsonData.setLctTime(lctTime);
						bool_is_update = true;
					}
				}
				
				//locationInfos.clear();
				//locationInfos = null;
					
				//yonghu start
				/*
				LocationInfoHelper lih = new LocationInfoHelper();
				
				lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
					cmd.equals(AdragonConfig.uploadLctData));
				lih = null; */
				//yonghu end							

			} else {
				locationInfo = null;
				respJsonData.setLctGps(Constant.FAIL_CODE);
			}
		}
		
		longitude = null;
		latitude = null;
		accuracy = null;
		sdtf = null;
		lctUpDate = null;
		result = null;
		object = null;
		location = null;
		str = null;
		
		//Gaode map lbs api
		return respJsonData;
	}
		
	//better proLctMgr
	public RespJsonData proLctMgrBtr(ReqJsonData reqJsonData, String devId, 
			Integer device_id, String cmd, IoSession session) {
		RespJsonData respJsonData = new RespJsonData();
		Tools tls = new Tools();
		WTSigninAction ba = new WTSigninAction();
		
		try {			
			
			boolean bool_is_update = true;  // move here
			String locationType = reqJsonData.getType(); // 1 gaode GPS; 2 gaode lbs; 3 gaode wifi; 4 google gps; 5 google lbs/wifi
			String battery = reqJsonData.getBattery();
			String lctTime = reqJsonData.getLctTime();
			String fall = Tools.OneString;  //reqJsonData.getFall();  //1表示戴上，0表示脱落
			double lng1 = 0;
			double lat1 = 0;
			int stepCount = 0;
			
			Location respLct = new Location();
			
			if (!"".equals(reqJsonData.getStepNumber()) && reqJsonData.getStepNumber() != null) {
				stepCount = Integer.parseInt(reqJsonData.getStepNumber().trim());
			} else if (reqJsonData.getStepCount() != null) {
				stepCount = reqJsonData.getStepCount();
			}
			
			LocationInfo locationInfo = new LocationInfo();
			locationInfo.setSerieNo(devId);		//smile add
			locationInfo.setBattery(Integer.parseInt(battery));	//smile add
			

		    //2016.12.17,不再需要设备心跳时间戳记录
			locationInfo.setDevice_id(device_id);
			
			if(Tools.OneString.equals(locationType)) {
				respJsonData = proLctGaodeGps(reqJsonData, devId, device_id, cmd);
				if ( respJsonData.getLctUpdate() == 1 ) {
					bool_is_update = true;
					locationInfo.setChangeLatitude(respJsonData.getLocation().getLat());
					locationInfo.setChangeLongitude(respJsonData.getLocation().getLon());
					locationInfo.setAccuracy(Float.parseFloat(respJsonData.getLocation().getAcc()));
					locationInfo.setLocationType(Tools.OneString);
					locationInfo.setUploadTime(tls.getUtcDateStrNowDate());

				} else
					bool_is_update = false;
					
			} else if ("2".equals(locationType)){      
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				String id = "";
				String jsonToString = "";
				double lng2 = 0.0;
				double lat2 = 0.0;
				double distDiff2 = 0.0;
				int stepDiff = 0;
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(tls.getUtcDateStrNowDate()):(sdtf.parse(reqJsonData.getLctTime()));				//Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				map.put("accesstype", Tools.ZeroString);
				map.put("network", network);
				map.put("cdma", cdma);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("bts", bts);
				map.put("nearbts", nearbts);
				map.put("key", Constant.KEY);
//				if (serverip != null && !"".equals(serverip)) {
//					map.put("serverip", serverip);
//				} else {
//					map.put("serverip", Constant.SERVER_IP);
//				}
				map.put("serverip", serverip);
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				//locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
				//		" and accuracy < 40.0 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				if (locationInfos.size() > 0) {
					
					id = locationInfos.get(0).getAt("id").toString();
					lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
					lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
					Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
					
					stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
					
					Long interval = (lctUpDate.getTime() - sdtf.parse(
							locationInfos.get(0).get("upload_time")
									.toString()).getTime()) / 1000;

					
					{
						jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);

						if ("-1".equals(jsonToString)) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals(Tools.OneString)) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								String radius = jsonResult.getString("radius"); 
								if (location != null) {	
									String[] locations = location.split(",");

									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius);
		
									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);
									
									distDiff2 = Constant.getDistance(lat1, lng1, lat2, lng2);
									
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									//如果上次为非LBS, 则本次时间间隔一定大于5分钟
									
									int resLct = tls.checkIfAbandonLctBtr(distDiff2, interval, stepDiff, preLctType, 
											locationType, preLctAcc, Float.parseFloat(radius)); 
									if ( Constant.LCT_IS_VALID  == resLct   ) {												
										locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
												" and location_type = '1' and accuracy < 20.0 ");
										
										locationInfo.setOrderBy("upload_time");
										locationInfo.setSort(Tools.OneString); // 按upload_time降序
										locationInfo.setFrom(0);
										locationInfo.setPageSize(1); // 0至1

										List<DataMap> locationInfos1 = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
										
										if ( !locationInfos1.isEmpty()) { 
											lng2 = Double.parseDouble(locationInfos1.get(0).getAt("longitude").toString());
											lat2 = Double.parseDouble(locationInfos1.get(0).getAt("latitude").toString());
											preLctAcc = Float.parseFloat(locationInfos1.get(0).getAt("accuracy").toString());
											
											preLctType = locationInfos1.get(0).getAt("location_type").toString();
											
											stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos1.get(0).getAt("step_count").toString()), stepCount);
											
											interval = (lctUpDate.getTime() - sdtf.parse(
													locationInfos1.get(0).get("upload_time")
															.toString()).getTime()) / 1000;
											distDiff2 = Constant.getDistance(lat1, lng1, lat2, lng2);
											resLct = tls.checkIfAbandonLctBtr(distDiff2, interval, stepDiff, preLctType, 
													locationType, preLctAcc, Float.parseFloat(radius)); 
											if ( Constant.LCT_IS_VALID  == resLct   ) {										
												serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
												bool_is_update = true;
											} else if( Constant.LCT_IS_REPEATED  == resLct   ) {
												if ( session != null )
													logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());
												
												bool_is_update = false;
												locationInfo.setChangeLongitude(null);
												locationInfo.setChangeLatitude(null);
												locationInfo.setAccuracy(null);																					
											} else if( Constant.LCT_IS_INVALID  == resLct   ) {
												if ( session != null )												
													logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );

												locationInfo = null;
												bool_is_update = false;
												if ( session != null && Constant.serverCtlDevLct)												
													session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");										
											}
										} else {
											serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
											bool_is_update = true;											
										}
									}  else if( Constant.LCT_IS_INVALID  == resLct   ) {
										if ( session != null )										
											logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );

										locationInfo = null;
										bool_is_update = false;
										if ( session != null && Constant.serverCtlDevLct)																						
											session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");																				
									}  else {	
										if ( session != null )										
											logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());

										bool_is_update = false;
										locationInfo.setChangeLongitude(null);
										locationInfo.setChangeLatitude(null);
										locationInfo.setAccuracy(null);										
									}
										
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);

									
								} else {
									locationInfo = null;
									respJsonData.setLctLbs(Constant.FAIL_CODE);
									respJsonData.setLctTime(lctTime);
								}
							} else if (status.equals(Tools.ZeroString)) { 
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							} else if (status.equals("-1")) {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
								respJsonData.setLctTime(lctTime);
							}
						}
						
					}
				} else {
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					if ("-1".equals(jsonToString)) {
						locationInfo = null;
						respJsonData.setLctLbs(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals(Tools.OneString)) { 
							String results = jsons.getString("result"); 
							JSONObject jsonResult = JSONObject.fromObject(results);
							String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
							String radius = jsonResult.getString("radius"); 
							if (location != null) {	
								String[] locations = location.split(",");

								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
	
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								bool_is_update = true;
																
								respLct.setLon(locations[0]);
								respLct.setLat(locations[1]);
								respLct.setAcc(radius);
								
								respJsonData.setLctLbs(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
						} else if (status.equals(Tools.ZeroString)) { 
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else if (status.equals("-1")) {
							locationInfo = null;
							respJsonData.setLctLbs(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
					}
					
				}
				
				netWorkInfo = null;
				network = null;
				cdma = null;
				smac = null;
				bts = null;
				nearbts = null;
				serverip = null;
				sdtf = null; 
				lctUpDate = null;
				jsonToString = null;
				map.clear();
				map = null;
				locationInfos.clear();
				locationInfos = null;
				
			} else if ("3".equals(locationType)) { // wifi gaode map api
				//abcde 1234
				NetWorkInfoAdr netWorkInfo1 = reqJsonData.getNetWork();
				String network1 = netWorkInfo1.getNetWork();
				String bts1 = netWorkInfo1.getBts();
				String nearbts1 = netWorkInfo1.getNearbts();
				
				//Boolean isEmptyDb = false;
				Boolean lctWIFIFlag = false;
				
				WifiInfoAdr wifi = reqJsonData.getWifi();
				String smac = wifi.getSmac();
				String mmac = wifi.getMmac();
				String macs = wifi.getMacs();
				String serverip = wifi.getServerip();
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
				//locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
				//		" and accuracy < 40.0 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				
				
				map.put("accesstype", Tools.OneString);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("mmac", mmac);
				map.put("macs", macs);
				map.put("bts", bts1);
				map.put("nearbts", nearbts1);
				
				map.put("key", Constant.KEY);
				map.put("serverip", serverip);
				
			try {
				String jsonToString = HttpRequest.sendGetToGaoDe(
						Constant.LOCATION_URL, map);

				
				if ("-1".equals(jsonToString)) {
//					locationInfo = null;
					lctWIFIFlag = false;
					respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				} else {
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status"); 
					
					Boolean isValidWIFI = false;
					
					if (status.equals(Tools.OneString)) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject
								.fromObject(results);
						
						String type = jsonResult.optString("type");
						
						if ("0".equals(type)) {
//							locationInfo = null;
							lctWIFIFlag = false;
							respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
							bool_is_update = false;
							locationInfo = null;
							if ( session != null )											
								logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") + " gaode type 0 ");
							
						} else {
							
							String location = jsonResult.getString("location"); 
							String radius = jsonResult.getString("radius");
							String[] locations = location.split(",");
							
							bool_is_update = true;	
							lctWIFIFlag = true;
														
							String id = "";
							if (locationInfos.size() > 0) { // 说明有数据
								
								id = locationInfos.get(0).getAt("id").toString();
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
								
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								lng1 = Double.parseDouble(locations[0]);
								lat1 = Double.parseDouble(locations[1]);
								
								double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
								int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);

								Long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time")
												.toString()).getTime()) / 1000;
								
								//距离一定是小于最大距离临界值本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								
								///BaseAction.insertVisit(user_id, device_imei, device_id, href);
								
								//if (   (diffDist2 < interval && diffDist2 < (double)diffStep2) || preLctAcc > 170.0f   ) {

								int resLct = tls.checkIfAbandonLctBtr(diffDist2, interval, 
										diffStep2, preLctType, locationType, 
										preLctAcc, Float.parseFloat(radius) ); 								
								if ( Constant.LCT_IS_VALID  == resLct  ) {
									locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
											" and location_type = '1' and accuracy < 20.0 ");
									
									locationInfo.setOrderBy("upload_time");
									locationInfo.setSort(Tools.OneString); // 按upload_time降序
									locationInfo.setFrom(0);
									locationInfo.setPageSize(1); // 0至1

									List<DataMap> locationInfos1 = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
									if ( !locationInfos1.isEmpty()) {

										lng2 = Double.parseDouble(locationInfos1.get(0).getAt("longitude").toString());
										lat2 = Double.parseDouble(locationInfos1.get(0).getAt("latitude").toString());
										stepCountPre = Integer.parseInt(locationInfos1.get(0).getAt("step_count").toString());
										
										preLctAcc = Float.parseFloat(locationInfos1.get(0).getAt("accuracy").toString());
										
										preLctType = locationInfos1.get(0).getAt("location_type").toString();
										
										
										diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
										diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);
	
										interval = (lctUpDate.getTime() - sdtf
												.parse(locationInfos1.get(0)
														.get("upload_time")
														.toString()).getTime()) / 1000;
										
										resLct = tls.checkIfAbandonLctBtr(diffDist2, interval, 
												diffStep2, preLctType, locationType, 
												preLctAcc, Float.parseFloat(radius) ); 								
										if ( Constant.LCT_IS_VALID  == resLct  ) {									
											bool_is_update = true;
											isValidWIFI = true;
										} else if ( Constant.LCT_IS_INVALID  == resLct  ) {
											if ( session != null )											
												logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );

											bool_is_update = false;
											isValidWIFI = false;	
											locationInfo = null;
											if ( session != null && Constant.serverCtlDevLct)																							
												session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");
										} else {
											if ( session != null )											
												logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());

											bool_is_update = false;
											locationInfo.setChangeLongitude(null);
											locationInfo.setChangeLatitude(null);
											
											isValidWIFI	= true;		//aha										
										}
									} else {
										bool_is_update = true;
										isValidWIFI = true;										
									}
										
										
								} else if ( Constant.LCT_IS_INVALID  == resLct  ) {
									if ( session != null )											
										logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );
									
									bool_is_update = false;
									isValidWIFI = false;
									locationInfo = null;
									if ( session != null && Constant.serverCtlDevLct)												
										session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");
								} else {
									if ( session != null )											
										logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());
									
									bool_is_update = false;
									isValidWIFI	= true;		//aha
								}
								
								//if (diffDist2 > Constant.IS_VALID_WIFI && diffStep2 < Constant.WIFI_DIFF_STEPS && ("3".equals(preLctType))) {
								//	isValidWIFI = false;
									
								//} else {
								//	isValidWIFI = true;
								//}

//								bool_is_update = Constant.getDistance(lat1,
//										lng1, lat2, lng2,
//										Constant.EFFERT_DATA); // 若为true表示有效数据
							} else {
								
								
								bool_is_update = true;
								isValidWIFI = true;
							}

							respLct.setLon(locations[0]);
							respLct.setLat(locations[1]);
							respLct.setAcc(radius);
							
							//locationInfos.clear();
							//locationInfos = null;

							if (isValidWIFI) {
								if (bool_is_update) {
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
									
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								} else {
										//abcde
									//locationInfo.setCondition("id ='" + id + "'");
									//locationInfo.setUploadTime(lctUpDate);
									//locationInfo.setStepCount(stepCount);
									//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
								}
																
								lctWIFIFlag = true;
								respJsonData.setUploadlctWIFI(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {	
//								locationInfo = null;
								lctWIFIFlag = false;
								respJsonData.setUploadlctWIFI(Constant.INVALID_DATA);
								respJsonData.setLctTime(lctTime);
							}

						} 
						
					} else if (status.equals(Tools.ZeroString)) {
//						locationInfo = null;
						lctWIFIFlag = false;
						respJsonData.setUploadlctWIFI(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else if (status.equals("-1")) {
//						locationInfo = null;
						lctWIFIFlag = false;
						respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
						respJsonData.setLctTime(lctTime);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Gaode WIFI location Exception!!");
//				locationInfo = null;
				lctWIFIFlag = false;
				respJsonData.setUploadlctWIFI(Constant.EXCEPTION_CODE);
				respJsonData.setLctTime(lctTime);
			}
			
			//屏蔽此段处理
			if (lctWIFIFlag == false && lctWIFIFlag == true) {
				
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				
				if (null != netWorkInfo) {
					
					String network = netWorkInfo.getNetWork();
					String cdma = netWorkInfo.getCdma();
					smac = netWorkInfo.getSmac();
					String bts = netWorkInfo.getBts();
					String nearbts = netWorkInfo.getNearbts();
					serverip = netWorkInfo.getServerip();
					map.clear();

					String jsonToString = "";
										
					map.put("accesstype", Tools.ZeroString);
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("key", Constant.KEY);
//					if (serverip != null && !"".equals(serverip)) {
//						map.put("serverip", serverip);
//					} else {
//						map.put("serverip", Constant.SERVER_IP);
//					}
					map.put("serverip", serverip);
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					if ("-1".equals(jsonToString)) {
						locationInfo = null;
						respJsonData.setLctLbs(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals(Tools.OneString)) { 
							
							
							String results = jsons.getString("result"); 
							JSONObject jsonResult = JSONObject.fromObject(results);
							String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
							String radius = jsonResult.getString("radius"); 
							if (location != null  ) {	
								
								
								if ( locationInfos.isEmpty() ) {
									bool_is_update = true;
								} else {
									double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
									double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
									int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
									Long interval = (lctUpDate.getTime() - sdtf
											.parse(locationInfos.get(0)
													.get("upload_time")
													.toString()).getTime()) / 1000;
									
									double diffDist2 = Constant.getDistance(lat1, lng1, lat2, lng2);
									int diffStep2 = tls.getDiffSteps(stepCountPre, stepCount);
									String preLctType = locationInfos.get(0).getAt("location_type").toString();
									Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());

													
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									//如果上次为非LBS, 则本次时间间隔一定大于5分钟
									if ( tls.checkIfAbandonLct(diffDist2, interval, 
											diffStep2, preLctType, locationType, 
											preLctAcc, Float.parseFloat(radius) ) ) {
										bool_is_update = true;
									} else {
										bool_is_update = false;										
									}
									
								}
								if ( bool_is_update ) {
									String[] locations = location.split(",");
	
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
									
									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setFall(fall);
									locationInfo.setBelongProject(Tools.OneString);
									locationInfo.setStepCount(stepCount);
		
									serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									
									respLct.setLon(locations[0]);
									respLct.setLat(locations[1]);
									respLct.setAcc(radius);
									
									respJsonData.setLctLbs(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									//String id = locationInfos.get(0).getAt("id").toString();

									//locationInfo.setCondition("id ='" + id + "'");
									//locationInfo.setUploadTime(lctUpDate);
									//locationInfo.setStepCount(stepCount);
									//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
									
								}
									
							} else {
								locationInfo = null;
								respJsonData.setLctLbs(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
						} else { 
							locationInfo = null;
							respJsonData.setLctLbs(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} 
					}
					
				}
				
			}
			//屏蔽此段处理 over
							
				wifi = null;
				smac = null;
				mmac = null;
				macs = null;
				serverip = null;
				sdtf = null; 
				lctUpDate = null;
//				jsonToString = null;
				map.clear();
				map = null;

			} else if("4".equals(locationType)) {     //Googel Map gps
				GPSInfoAdr gps = reqJsonData.getGps();
				String longitude = gps.getLon(); //经度
				String latitude = gps.getLat(); //纬度
				String accuracy = gps.getAcc(); //精确度
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				if (devId != null && !"".equals(devId) &&  longitude != null
						&& !"".equals(longitude) && latitude != null
						&& !"".equals(latitude) && accuracy != null
						&& !"".equals(accuracy) && locationType != null
						&& !"".equals(locationType)) {

						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						longitude = String.format("%.12f", lng1);
						latitude = String.format("%.12f", lat1);
						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						
					if (lng1 != 0 && lat1 != 90) { // 直接过滤
						
						locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
						//		" and accuracy < 40.0 ");
						locationInfo.setOrderBy("upload_time");
						locationInfo.setSort(Tools.OneString); // 按upload_time降序
						locationInfo.setFrom(0);
						locationInfo.setPageSize(1); // 0至1

						List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
						
						if (locationInfos.size() > 0) { 
							    String id = "" + locationInfos.get(0).getAt("id");
								double lng2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("longitude"));
								double lat2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("latitude"));	
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								
								double diffDist4 = Constant.getDistance(lat1, lng1, lat2, lng2);
								long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time").toString())
										.getTime()) / 1000;
								
								int stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
								
								//距离一定是小于最大距离法制本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								if ( tls.checkIfAbandonLct(diffDist4, interval, 
										stepDiff, preLctType, locationType, 
										preLctAcc, Float.parseFloat(accuracy) ) ) {

								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(longitude);
								locationInfo.setLatitude(latitude);
								locationInfo.setAccuracy(Float.parseFloat(accuracy));
								locationInfo.setLocationType(locationType);
								locationInfo.setChangeLongitude(longitude);
								locationInfo.setChangeLatitude(latitude);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);								
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
								bool_is_update = true;

								
							} else {
								//locationInfo.setCondition("id ='" + id + "'");
								//locationInfo.setUploadTime(lctUpDate);
								//locationInfo.setStepCount(stepCount);
								
								//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
								
								if ( session != null )											
									logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());
								
								bool_is_update = false;
								locationInfo.setChangeLongitude(null);
								locationInfo.setChangeLatitude(null);
								
							}
						} else {
							
							locationInfo.setSerieNo(devId);
							locationInfo.setBattery(Integer.parseInt(battery));
							locationInfo.setLongitude(longitude);
							locationInfo.setLatitude(latitude);
							locationInfo.setAccuracy(Float.parseFloat(accuracy));
							locationInfo.setLocationType(locationType);
							locationInfo.setChangeLongitude(longitude);
							locationInfo.setChangeLatitude(latitude);
							locationInfo.setUploadTime(lctUpDate);
							locationInfo.setFall(fall);								
							locationInfo.setBelongProject(Tools.OneString);
							locationInfo.setStepCount(stepCount);
							
							serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
							bool_is_update = true;
						}
						
						respJsonData.setLctGGps(Constant.SUCCESS_CODE);
						respJsonData.setLctTime(lctTime);
							
						} else {
							locationInfo = null;
							respJsonData.setLctGGps(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						}
					}
					
					gps = null;
					longitude = null;
					latitude = null;
					accuracy = null;
					sdtf = null;
					lctUpDate = null;
					
			 } else if ("5".equals(locationType)) {     //Google geolocation API   

				String id = "";
				String strGeolocation = "";
				
				
				//abcdd
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				Geolocation geolocation = reqJsonData.getGeolocation();
				JSONObject gjsons = JSONObject.fromObject(geolocation);

				//ReqJsonData reqJsonData = JSON.parseObject(msg.toString(), ReqJsonData.class);
				
				
				String param2 = gjsons.toString();

				/*
				if (!"gsm".equals(geolocation.getRadioType())) {
				
					GeolocationCdma geoLocationCdma = JSON.parseObject(param2, GeolocationCdma.class);
					JSONObject gjsonsCdma = JSONObject.fromObject(geoLocationCdma);
					String param2Cdma = gjsonsCdma.toString();
					param2 = param2Cdma;
				}
				*/
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				
				//locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
				//		" and accuracy < 40.0 ");
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				
				if (locationInfos.size() > 0) { 
					id = locationInfos.get(0).getAt("id").toString();
					double lng2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("longitude"));
					double lat2 = Double.parseDouble(""
							+ locationInfos.get(0).getAt("latitude"));	
					
					Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
					
					Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
					
					int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
							
					long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
					
					int diffStep5 = tls.getDiffSteps(stepCountPre, stepCount);
					//double distDiff5 = Constant.getDistance(lat1, lng1, lat2, lng2);
					
					long interval = (lctUpDate.getTime() - sdtf.parse(
							locationInfos.get(0).get("upload_time")
									.toString()).getTime()) / 1000;
										
					{
						
						try {
							
							strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
							
//							System.out.println("--- cmd process call google API return : " + strGeolocation);
							
							if (strGeolocation != null && !strGeolocation.equals("")) {
								
								JSONObject geoJson = JSONObject.fromObject(strGeolocation);
								
								String error = geoJson.has("error")?geoJson.getString("error"):null;
								
								if (error == null) {
									String location = geoJson.getString("location");
									JSONObject locationJson = JSONObject.fromObject(location);
									double lat = locationJson.getDouble("lat");
									double lng = locationJson.getDouble("lng");
									double accuracy = geoJson.getDouble("accuracy");

									double diffDist5 = Constant.getDistance(lat, lng, lat2, lng2);
									
									//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
									//距离一定是小于最大距离临界值本次定位才有效
									//或者每步的距离一定小于1米，
									//或者上次定位为LBS,但本次为非LBS
									int resLct = tls.checkIfAbandonLctBtr(diffDist5, interval, 
											diffStep5, preLctType, locationType, 
											preLctAcc, (float)accuracy );
									if ( Constant.LCT_IS_VALID  == resLct   ) {		

										locationInfos = null;
										
										locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 " +
												" and location_type = '4' and accuracy < 20.0 ");
										locationInfo.setOrderBy("upload_time");
										locationInfo.setSort(Tools.OneString); // 按upload_time降序
										locationInfo.setFrom(0);
										locationInfo.setPageSize(1); // 0至1

										List<DataMap> locationInfos1 = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
										
										if (locationInfos1.size() > 0) { 
											lng2 = Double.parseDouble(""
													+ locationInfos1.get(0).getAt("longitude"));
											lat2 = Double.parseDouble(""
													+ locationInfos1.get(0).getAt("latitude"));	
											
											preLctAcc = Float.parseFloat(locationInfos1.get(0).getAt("accuracy").toString());
											
											preLctDate = sdtf.parse(locationInfos1.get(0).getAt("upload_time").toString());
											
											stepCountPre = Integer.parseInt(locationInfos1.get(0).getAt("step_count").toString());
											

											preLctType = locationInfos1.get(0).getAt("location_type").toString();
													
											timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
											
											diffStep5 = tls.getDiffSteps(stepCountPre, stepCount);
											
											interval = (lctUpDate.getTime() - sdtf.parse(
													locationInfos1.get(0).get("upload_time")
															.toString()).getTime()) / 1000;
																				
											diffDist5 = Constant.getDistance(lat, lng, lat2, lng2);
											
											resLct = tls.checkIfAbandonLctBtr(diffDist5, interval, 
													diffStep5, preLctType, locationType, 
													preLctAcc, (float)accuracy );
											if ( Constant.LCT_IS_VALID  == resLct   ) {
												locationInfo.setLongitude(Double.toString(lng));
												locationInfo.setLatitude(Double.toString(lat));
												locationInfo.setChangeLongitude(Double.toString(lng));
												locationInfo.setChangeLatitude(Double.toString(lat));
												locationInfo.setAccuracy((float) accuracy);
												locationInfo.setUploadTime(lctUpDate);
												
												locationInfo.setSerieNo(devId);
												locationInfo.setBattery(Integer.parseInt(battery));
												locationInfo.setLocationType(locationType);
												locationInfo.setFall(fall);
												locationInfo.setBelongProject(Tools.OneString);
												locationInfo.setStepCount(stepCount);
												serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
												bool_is_update = true;
												
											} else if (Constant.LCT_IS_INVALID == resLct ) {
												if ( session != null )												
													logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );
												
												locationInfo = null;
												bool_is_update = false;	
												if ( session != null && Constant.serverCtlDevLct)																								
													session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");
											} else {
												if ( session != null )												
													logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());

												//locationInfo.setCondition("id ='" + id + "'");
												//locationInfo.setUploadTime(lctUpDate);
												//locationInfo.setStepCount(stepCount);
												//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
												bool_is_update = false;

												locationInfo.setChangeLongitude(null);
												locationInfo.setChangeLatitude(null);
												
											}
										
										} else {
											locationInfo.setLongitude(Double.toString(lng));
											locationInfo.setLatitude(Double.toString(lat));
											locationInfo.setChangeLongitude(Double.toString(lng));
											locationInfo.setChangeLatitude(Double.toString(lat));
											locationInfo.setAccuracy((float) accuracy);
											locationInfo.setUploadTime(lctUpDate);
											
											locationInfo.setSerieNo(devId);
											locationInfo.setBattery(Integer.parseInt(battery));
											locationInfo.setLocationType(locationType);
											locationInfo.setFall(fall);
											locationInfo.setBelongProject(Tools.OneString);
											locationInfo.setStepCount(stepCount);
											serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
											bool_is_update = true;
										}
										
									} else if (Constant.LCT_IS_INVALID == resLct ) {
										if ( session != null )										
											logger.info("lct LCT_IS_INVALID: " + session.getId() + " dev: " + session.getAttribute("devId") );

										locationInfo = null;
										bool_is_update = false;		
										if ( session != null && Constant.serverCtlDevLct)																						
											session.write("{\"getLocation\":{\"gps\":1,\"userId\":0}}");										
									} else {
										if ( session != null )
											logger.info("lct LCT_IS_REPEATED: " + session.getId() + " dev: " + session.getAttribute("devId") + " locationInfo: "+  locationInfo.toString());

										//locationInfo.setCondition("id ='" + id + "'");
										//locationInfo.setUploadTime(lctUpDate);
										//locationInfo.setStepCount(stepCount);
										//serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
										bool_is_update = false;
										locationInfo.setChangeLongitude(null);
										locationInfo.setChangeLatitude(null);
										
									}
									
									respLct.setLon(Double.toString(lng));
									respLct.setLat(Double.toString(lat));
									respLct.setAcc(Double.toString(accuracy));
									
									respJsonData.setLctGMap(Constant.SUCCESS_CODE);
									respJsonData.setLocation(respLct);
									respJsonData.setLctTime(lctTime);
								} else {
									locationInfo = null;
									JSONObject errorJson = JSONObject.fromObject(error);
									String errorCode = errorJson.getString("code");	
									respJsonData.setLctGMap(Integer.parseInt(errorCode));
									respJsonData.setLctTime(lctTime);
								}
							} else {
								locationInfo = null;
								respJsonData.setLctGMap(Constant.FAIL_CODE);
								respJsonData.setLctTime(lctTime);
							}
							
						} catch(Exception e){
							e.printStackTrace();
//							System.out.println("Google Map Geolocation Exception!!");
							logger.error("Google Map Geolocation Exception ---1---" + "\r\n" + e.toString());
							locationInfo = null;
							respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
							respJsonData.setLctTime(lctTime);
						}
						
					}
					
				} else {
					
					try {
						
						strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
						
//						System.out.println("--- cmd process call google API return : " + strGeolocation);
						
						if (strGeolocation != null && !strGeolocation.equals("")) {
							
							JSONObject geoJson = JSONObject.fromObject(strGeolocation);
							
							String error = geoJson.has("error")?geoJson.getString("error"):null;
							
							if (error == null) {
								String location = geoJson.getString("location");
								JSONObject locationJson = JSONObject.fromObject(location);
								double lat = locationJson.getDouble("lat");
								double lng = locationJson.getDouble("lng");
								double accuracy = geoJson.getDouble("accuracy");

								locationInfo.setLongitude(Double.toString(lng));
								locationInfo.setLatitude(Double.toString(lat));
								locationInfo.setChangeLongitude(Double.toString(lng));
								locationInfo.setChangeLatitude(Double.toString(lat));
								locationInfo.setAccuracy((float) accuracy);
								locationInfo.setUploadTime(lctUpDate);
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLocationType(locationType);
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);	
								bool_is_update = true;
								
								respLct.setLon(Double.toString(lng));
								respLct.setLat(Double.toString(lat));
								respLct.setAcc(Double.toString(accuracy));
								
								respJsonData.setLctGMap(Constant.SUCCESS_CODE);
								respJsonData.setLocation(respLct);
								respJsonData.setLctTime(lctTime);
							} else {
								locationInfo = null;
								JSONObject errorJson = JSONObject.fromObject(error);
								String errorCode = errorJson.getString("code");									
								respJsonData.setLctGMap(Integer.parseInt(errorCode));
								respJsonData.setLctTime(lctTime);
							}
						} else {
							locationInfo = null;
							respJsonData.setLctGMap(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						}
						
					} catch(Exception e) {
						e.printStackTrace();
//						System.out.println("Google Map Geolocation Exception!!");
						logger.error("Google Map Geolocation Exception ---2---" + "\r\n" + e.toString());
						locationInfo = null;
						respJsonData.setLctGMap(Constant.EXCEPTION_CODE);
						respJsonData.setLctTime(lctTime);
					}
					
				}
				
				
				sdtf = null;
				lctUpDate = null;
				geolocation = null;
				gjsons = null;
				param2 = null;

			//Google geolocation API
			
		} else if ("6".equals(locationType)) {   //Gaode map lbs/wifi location case

			try {			
				String jsonToString = null;
				String mmac = null;
				String macs = null;
				
				SimpleDateFormat sdtf = null; 
				Date lctUpDate = null;
				
				NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				
				WifiInfoAdr wifi = reqJsonData.getWifi();
				
				//abced
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
				
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
				List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
				String ts = ((String) wdeviceActiveInfos.get(0).getAt("test_status")).trim();

				sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				
				if ( Tools.OneString.equals(ts) ) {
					WTDevGWAction wdga = new WTDevGWAction();
					StringBuffer sbb = new StringBuffer("{\"pwd\":\"afdzczb$nvn@mv:mn4259i-0\",\"imei\":\"");
					sbb.append(devId);
					sbb.append("\",\"net\":{\"network\":");
					sbb.append(JSON.toJSONString(netWorkInfo));
					sbb.append("},\"wifi\":{\"wifi\":");
					sbb.append(JSON.toJSONString(wifi));
					sbb.append("}}");
					
					jsonToString = wdga.httpPostInner(Constant.GAODE_SRV, sbb.toString());
					JSONObject jo = JSONObject.fromObject(jsonToString);
					if (jo.optInt("resultCode") == 1)
						jsonToString = jo.optString("res");
					else
						jsonToString = jo.optString("res");
						
					wdga = null;
				}
			
				else {
					
		//			String smac = wifi.getSmac();
					mmac = wifi.getMmac();
					macs = wifi.getMacs();
		//			String serverip = wifi.getServerip();
				
	
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
					map.put("accesstype", Tools.ZeroString);
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", devId);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("mmac", mmac);
					map.put("macs", macs);
					map.put("key", Constant.KEY);
		//			if (serverip != null && !"".equals(serverip)) {
		//				map.put("serverip", serverip);
		//			} else {
		//				map.put("serverip", Constant.SERVER_IP);
		//			}
					map.put("serverip", serverip);
				
					jsonToString = HttpRequest.sendGetToGaoDe(
						Constant.LOCATION_URL, map);
				}

				if ("-1".equals(jsonToString)) {
					locationInfo = null;
					respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
					respJsonData.setLctTime(lctTime);
				} else {
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status"); 
					
					if (status.equals(Tools.OneString)) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject
								.fromObject(results);
						
						String type = jsonResult.optString("type");
						
						if ("0".equals(type)) {
							locationInfo = null;
							respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
							respJsonData.setLctTime(lctTime);
						} else {
							
							String location = jsonResult.getString("location"); 
							String radius = jsonResult.getString("radius");
							String[] locations = location.split(",");
							
							locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
							
							locationInfo.setOrderBy("upload_time");
							locationInfo.setSort(Tools.OneString); // 按upload_time降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1
	
							List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
							bool_is_update = true;	
							
							String id = "";
							if (locationInfos.size() > 0) { // 说明有数据
								id = locationInfos.get(0).getAt("id").toString();
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
								Float preLctAcc = Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString());
								
								String preLctType = locationInfos.get(0).getAt("location_type").toString();
								
								lng1 = Double.parseDouble(locations[0]);
								lat1 = Double.parseDouble(locations[1]);
								
								double diffDist6 = Constant.getDistance(lat1, lng1, lat2, lng2);
								int diffStep6 = tls.getDiffSteps(stepCountPre, stepCount);
								long interval = (lctUpDate.getTime() - sdtf
										.parse(locationInfos.get(0)
												.get("upload_time")
												.toString()).getTime()) / 1000;							
								//if ( diffStep6 > Constant.WIFI_DIFF_STEPS || diffDist6 > Constant.WIFI_DIFF_DIST_GAP || (Float.parseFloat(radius) < preLctAcc)) {
								//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
	
								//距离一定是小于最大距离临界值本次定位才有效
								//或者每步的距离一定小于1米，
								//或者上次定位为LBS,但本次为非LBS
								if ( tls.checkIfAbandonLct(diffDist6, interval, 
										diffStep6, preLctType, locationType, 
										preLctAcc, Float.parseFloat(radius) ) ) {					
									bool_is_update = true;
								} else {
									bool_is_update = false;
								}
							} else {
								bool_is_update = true;
							}
	
							if (bool_is_update) {
								
								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);
								locationInfo.setFall(fall);
								locationInfo.setBelongProject(Tools.OneString);
								locationInfo.setStepCount(stepCount);
								
								serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
							} else {
								locationInfo.setCondition("id ='" + id + "'");
	
								double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
								double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
								
								//locationInfo.setUploadTime(lctUpDate);
								//locationInfo.setStepCount(stepCount);
								//ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
							}
															
							respLct.setLon(locations[0]);
							respLct.setLat(locations[1]);
							respLct.setAcc(radius);
							
							respJsonData.setLctLbsWifi(Constant.SUCCESS_CODE);
							respJsonData.setLocation(respLct);
							respJsonData.setLctTime(lctTime);
						} 
						
					} else {
						locationInfo = null;
						respJsonData.setLctLbsWifi(Constant.FAIL_CODE);
						respJsonData.setLctTime(lctTime);
					} 
			}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Gaode LBS and WIFI location Exception!!");
			logger.error("Gaode LBS and WIFI location Exception!" + "\r\n" + e.toString());
			locationInfo = null;
			respJsonData.setLctLbsWifi(Constant.EXCEPTION_CODE);
			respJsonData.setLctTime(lctTime);
		}
						 
//	} //Gaode lbs and wifi location case end
		} else if ("7".equals(locationType)) { //update lctTime
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				
				Date lctUpDate = reqJsonData.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJsonData.getLctTime()));
				

				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
				String id = "";
				if (locationInfos.size() > 0) { 
					
					id = locationInfos.get(0).getAt("id").toString();
					locationInfo.setCondition("id ='" + id + "'");

					String lonStr = locationInfos.get(0).getAt("longitude").toString();
					String latStr = locationInfos.get(0).getAt("latitude").toString();
					String accStr = locationInfos.get(0).getAt("accuracy").toString();
					
					//locationInfo.setBattery(Integer.parseInt(battery));								
					Float acc = Float.parseFloat(accStr);
					locationInfo.setAccuracy(acc);
					locationInfo.setUploadTime(lctUpDate);
					locationInfo.setStepCount(stepCount);

					ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
					
					respLct.setLon(lonStr);
					respLct.setLat(latStr);
					respLct.setAcc(accStr);
					
					//yonghu start
					/*
					LocationInfoHelper lih = new LocationInfoHelper();
					lih.proLctInfo(reqJsonData.getUserId(), false, locationInfo, 
							cmd.equals(AdragonConfig.uploadLctData));
					lih = null;
					*/
					//yonghu end	
					
					respJsonData.setLctUpdate(Constant.SUCCESS_CODE);
					respJsonData.setLocation(respLct);
					respJsonData.setLctTime(lctTime);
					
					lonStr = null;
					latStr = null;
					accStr = null;

				} else {
					respJsonData.setLctUpdate(Constant.FAIL_CODE);
					respJsonData.setLctTime(lctTime);
				}

				sdtf = null; 
				lctUpDate = null;
				id = null;

		 }		
			
			//yonghu start
			if (locationInfo != null) {				
				LocationInfoHelper lih = new LocationInfoHelper();
				//BaseAction.insertVisit("-1", null, null, "proLctInfo");
				lih.proLctInfo(reqJsonData.getUserId(), bool_is_update, locationInfo, 
						cmd.equals(AdragonConfig.uploadLctData));
				lih = null;
			}
			//yonghu end	
						
			try {
				if (cmd.equals(AdragonConfig.getLocationRes)) { 
					
//					if (devId != null && !"".equals(devId)) {
//					if (locationInfo != null && (!"".equals(locationInfo.getSerieNo()) && locationInfo.getSerieNo() != null )
//							&& (!"".equals(locationInfo.getChangeLatitude()) && locationInfo.getChangeLatitude() != null )
//						    && (!"".equals(locationInfo.getChangeLongitude()) && locationInfo.getChangeLongitude() != null )) {
//						
//						serviceBean.getLocationInfoFacade().insertClickLocationInfo(locationInfo);	
					
					if (locationInfo != null) {
						
						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
							Integer userId = reqJsonData.getUserId();
							//inform app user get update location
							WTAppGpsManAction wgma = new WTAppGpsManAction();							
							wgma.proGetDevLoc17(userId, device_id, devId);
							wgma = null;
						}

					} else {
						if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
							Integer userId = reqJsonData.getUserId();
							//inform app user get update location fail 
							mClientSessionManager.completeFailHttpCmdId(userId, device_id, devId, AdragonConfig.getLocationRes);
						}
						
					}
					
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("getLocationRes exception!" + "\r\n" + e.toString());
			}

			devId = null;
			locationType = null;
			battery = null;
			fall = null;
		    locationInfo = null;
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return respJsonData;
	}
	
	// 根据imei号判断客户端，paby走原来的流程，rembo的设置wdevice_active_info.uLTe字段为APP传入的值，传入的值已保存在数据库
	// 暂时不用
	private static int selectBrand(String imei) {
		DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("device_imei ='" + imei + "'");
		List<DataMap> list;
		String brandname = null;
		Integer uLTe = 0;
		try {
			list = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
			if (list != null && list.size() > 0) {
				brandname = list.get(0).get("brandname") + "";
				if ("rombo".equals(brandname)) {
					uLTe = (Integer) list.get(0).get("uLTe");
					return uLTe;
				}
			}
		} catch (SystemException e) {			
			e.printStackTrace();
		}
		return uLTe;
	}
		
}