package com.wtwd.common.bean.devicedown;

//import java.util.Date;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.devicedown.cmdobject.Beattim;
import com.wtwd.common.bean.devicedown.cmdobject.CallRingTone;
import com.wtwd.common.bean.devicedown.cmdobject.DevOffOn;
import com.wtwd.common.bean.devicedown.cmdobject.DevUp;
import com.wtwd.common.bean.devicedown.cmdobject.EcoMode;
import com.wtwd.common.bean.devicedown.cmdobject.FamilyNumber;
import com.wtwd.common.bean.devicedown.cmdobject.FlightMode;
import com.wtwd.common.bean.devicedown.cmdobject.GetHealthStep;
import com.wtwd.common.bean.devicedown.cmdobject.GetLocation;
import com.wtwd.common.bean.devicedown.cmdobject.GetSsidList;
import com.wtwd.common.bean.devicedown.cmdobject.GpsMapMenu;
import com.wtwd.common.bean.devicedown.cmdobject.LctEsafe;
import com.wtwd.common.bean.devicedown.cmdobject.LedState;
import com.wtwd.common.bean.devicedown.cmdobject.PlayRing;
import com.wtwd.common.bean.devicedown.cmdobject.SleepState;
import com.wtwd.common.bean.devicedown.cmdobject.SsidEsafe;
import com.wtwd.common.bean.devicedown.cmdobject.UpdateFirmware;
import com.wtwd.common.bean.devicedown.cmdobject.UrgentMode;
import com.wtwd.common.bean.devicedown.cmdobject.WifiRange;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.handler.ServerHandler;
import com.wtwd.common.http.GetIp;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;

public class CmdDownSetImpl implements CmdDownSet {
	
	private ClientSessionManager cmdClientSessionManager;
	
	private Logger logger = Logger.getLogger(CmdDownSetImpl.class);
		
//	private RespJsonData respJsonData;
	
	public CmdDownSetImpl() {
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");
		//cmdClientSessionManager = (ClientSessionManager) context.getBean("clientSessionManager"); 
		cmdClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
//		respJsonData = new RespJsonData();
	}
	
	public IoSession getCmdDeviceIoSession(String imeiStr) {
		
		IoSession cmdDeviceIoSession; 
		cmdDeviceIoSession = cmdClientSessionManager.getSessionId(imeiStr);
       		
		if (cmdDeviceIoSession != null) {
			logger.debug("session: " + cmdDeviceIoSession.getId() + "dev: " + cmdDeviceIoSession.getAttribute("devId") + " Down cmd IMEI: " + imeiStr);
			logger.info("session: " + cmdDeviceIoSession.getId() + "dev: " + cmdDeviceIoSession.getAttribute("devId") + " Down cmd IMEI: " + imeiStr);
			return cmdDeviceIoSession;
			
		}else {
//			System.out.println("---cmdDeviceIoSession is null!");
			logger.info("dev IMEI: " + imeiStr + " cmd device Session is null!");
			return null;
		}
		
//		return cmdDeviceIoSession;
	}
		
	public boolean baseWrite(String imeiStr, Thread lock)   {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
		boolean res = true;
		try {
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
				Date d1 = new Date();
				
				long tout = 60 * 6;
				
				synchronized(lock){
					lock.wait(1000*tout);
				    //或者wait()
				}
				Date d2 = new Date();
				
				if ( tls.getSecondsBetweenDays(d1, d2) >=  tout   ) 
					ba.insertVisit(null, imeiStr, null, "base_write rec d1:" + 
						d1.getTime() + " d2:" + d2.getTime() +
						", d2 - d1 : " + tls.getSecondsBetweenDays(d1, d2));				
				
				if ( tls.getSecondsBetweenDays(d1, d2) >= tout ) { 
					//命令失败
					//设备下线处理
					//IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
					//if ( deviceIoSession != null )

					logger.info("base_write  rec d1: " + 
							d1.toString() + " d2:" + d2.toString() +
							", d2 - d1 : " + tls.getSecondsBetweenDays(d1, d2));		
					
					logger.info("base_write dev: " + imeiStr + " cmd down timeout!");
					
					//sh.sysOffline(imeiStr);
					IoSession ts = getCmdDeviceIoSession(imeiStr);
					if ( ts != null ) {
						ts.closeNow();
					}
					Thread.sleep(1000);
					ProDevStat(imeiStr, 0);
					
					//abcdd
					sh = null;
					res =  false;
				} 
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
	public void setBeattim(String imeiStr, Integer beatTim)
			throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
		
		if ( deviceIoSession == null )
			return;
		
		Beattim beatTimObj = new Beattim();
		
		beatTimObj.setBeatTim(beatTim.toString());
		
		respJsonData.setSetBeattim(beatTimObj);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
		try {
			
			deviceIoSession.write(resp);
			resp = null;
			beatTimObj = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		//deviceIoSession.write(resp);
	}
	
	public boolean setLedState(String imeiStr, int n, 
			boolean ledFlag)	{
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
		try {
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, 0 );
				sh = null;
				return false;
			}
			
			LedState ledState = new LedState();
			
			ledState.setLedFlag(Boolean.toString(ledFlag));
			ledState.setLedID(Integer.toString(n));
	
			respJsonData.setSetLedState(ledState);
	
	//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
			resp = JSON.toJSONString(respJsonData);
			
				
			deviceIoSession.write(resp);
			resp = null;
			ledState = null;
			respJsonData = null;
			/*if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else*/
			
			res = true;
				
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setLedState exception:");				
		}

		return res;
		
	}

	public boolean setLedState(String imeiStr, int n, 
			boolean ledFlag, Integer userId, Thread lock)
		{
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
        try {		
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
						
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			LedState ledState = new LedState();
			
			ledState.setLedFlag(Boolean.toString(ledFlag));
			ledState.setLedID(Integer.toString(n));
			ledState.setUserId(userId);
	
			respJsonData.setSetLedState(ledState);
	
	//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
			resp = JSON.toJSONString(respJsonData);
		
			
			deviceIoSession.write(resp);
			resp = null;
			ledState = null;
			respJsonData = null;
			
			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
									
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setLedState2 exception:");				
			
		}
        
		return res;        
		
	}

	public void setWIFIRange(String imeiStr, int interval, boolean wifiFlag)
			throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;		
		
		WifiRange wifiRange = new WifiRange();
		
		wifiRange.setWifiFlag(Boolean.toString(wifiFlag));
		wifiRange.setInterval(Integer.toString(interval));
		
		respJsonData.setSetWifiRange(wifiRange);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			
			deviceIoSession.write(resp);
			resp = null;
			wifiRange = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}
	
	public void setUrgentMode(String imeiStr, boolean stateFlag)
			throws SystemException {
		
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		
		UrgentMode urgentMode = new UrgentMode();
		
		urgentMode.setStateFlag(Boolean.toString(stateFlag));
		
		respJsonData.setSetUrgentMode(urgentMode);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			
			deviceIoSession.write(resp);
			resp = null;
			urgentMode = null;
			respJsonData = null;
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}


	public boolean setUrgentModeBk(String imeiStr, boolean stateFlag, String cmdTime, 
			Integer duration, Integer userId, Thread lock) {
		boolean res = false;
			return true;
		
	}

	public boolean setUrgentMode(String imeiStr, boolean stateFlag, String cmdTime, 
			Integer duration, Integer userId, Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
		try { 
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}

			UrgentMode urgentMode = new UrgentMode();
			
			urgentMode.setStateFlag(Boolean.toString(stateFlag));
			urgentMode.setDuration(duration);
			urgentMode.setUserId(userId);
			urgentMode.setCmdTime(cmdTime);
			
			respJsonData.setSetUrgentMode(urgentMode);
	//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
			resp = JSON.toJSONString(respJsonData);
					
			deviceIoSession.write(resp);
			resp = null;
			urgentMode = null;
			respJsonData = null;
			
			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setUrgentMode exception:");							
		}
		
		return res;
		
	}
	
	
	public void setFamilyNumber(String imeiStr, String family_ssid,
			String family_passwd, String host_ssid) throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		
		FamilyNumber familyNumberInfo = new FamilyNumber();
		
		familyNumberInfo.setFamily_ssid(family_ssid);
		familyNumberInfo.setFamily_passwd(family_passwd);
		familyNumberInfo.setHost_ssid(host_ssid);
		
		respJsonData.setSetFamilyNumber(familyNumberInfo);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			
			deviceIoSession.write(resp);
			resp = null;
			familyNumberInfo = null;
			respJsonData = null;
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public void setFlightMode(String imeiStr, boolean modeFlag)
			throws SystemException {
		
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
		
		if ( deviceIoSession == null )
			return;
		
		FlightMode flightMode = new FlightMode();
		
		flightMode.setModeFlag(Boolean.toString(modeFlag));
		
		respJsonData.setSetFlightMode(flightMode);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			
			deviceIoSession.write(resp);
			resp = null;
			flightMode = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
	}

	public void setGpsMap(String imeiStr, boolean gpsMapFlag)
			throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		GpsMapMenu gpsMapMenu = new GpsMapMenu();
		
		gpsMapMenu.setGpsMapFlag(Boolean.toString(gpsMapFlag));
		
		respJsonData.setSetGpsMap(gpsMapMenu);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			
			deviceIoSession.write(resp);
			resp = null;
			gpsMapMenu = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public boolean setEcoMode(String imeiStr, boolean modeFlag, Integer userId, 
			Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		try {
		
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh= null;
				return false;
			}
			
			EcoMode ecoMode = new EcoMode();
			
			ecoMode.setModeFlag(Boolean.toString(modeFlag));
			ecoMode.setUserId(userId);
	
			respJsonData.setSetEcoMode(ecoMode);
	//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
			resp = JSON.toJSONString(respJsonData);
		
			
			deviceIoSession.write(resp);
			resp = null;
			ecoMode = null;
			respJsonData = null;

			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setEcoMode exception:");							
			
		}
		
		return res;
		
	}

	public void setCallRingTone(String imeiStr, int n) throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		CallRingTone callRingTone = new CallRingTone();
		
		callRingTone.setToneNo(Integer.toString(n));
		
		respJsonData.setSetRingTone(callRingTone);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);

        try {
			
			deviceIoSession.write(resp);
			resp = null;
			callRingTone = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}
	
	public void setPlayRing(String imeiStr, int n, boolean playFlag)
			throws SystemException {

		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		PlayRing playRing = new PlayRing();
		playRing.setPlayFlag(Boolean.toString(playFlag));
		playRing.setRingTone(Integer.toString(n));
		
		respJsonData.setSetPlayRing(playRing);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			playRing = null;
//			deviceIoSession = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public void setPlayRing(String imeiStr, int n, boolean playFlag, Integer userId)
			throws SystemException {

		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;		
		
		PlayRing playRing = new PlayRing();
		playRing.setPlayFlag(Boolean.toString(playFlag));
		playRing.setRingTone(Integer.toString(n));
		playRing.setUserId(userId);
		
		respJsonData.setSetPlayRing(playRing);
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			playRing = null;
//			deviceIoSession = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public boolean getLocation(String imeiStr, 
			Integer gps, Integer userId, Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
		try {
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			
			GetLocation getLocation = new GetLocation();
			getLocation.setGps(gps);
			getLocation.setUserId(userId);
			
			respJsonData.setGetLocation(getLocation);
			
			resp = JSON.toJSONString(respJsonData);
		
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			
			ba.insertVisit(null, imeiStr, null, "exception getLocation exception:");										
		}
		
		return res;
	}

	public void getDevState(String imeiStr) throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;		
		
		respJsonData.setGetDevState("ping");
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public void setDevDebug(String imeiStr, boolean debugMode)
			throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		
		respJsonData.setSetDevDebug(Boolean.toString(debugMode));
//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
	}

	public boolean setSleepState(String imeiStr, boolean sleepFlag, 
			Integer userId, Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
        try {		
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			SleepState sleepState = new SleepState();
			sleepState.setSleepFlag(Boolean.toString(sleepFlag));
			sleepState.setUserId(userId);
			
			respJsonData.setSetSleepState(sleepState);
	
			resp = JSON.toJSONString(respJsonData);
		

			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
 			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setSleepState exception:");				
		}
        return res;
	}

	public void setLctEsafe(String imeiStr, boolean lctEsafeFlag, Integer userId)
			throws SystemException {

		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

		if ( deviceIoSession == null )
			return;
		
		LctEsafe lctEsafe = new LctEsafe();
		lctEsafe.setLctEsafeFlag(Boolean.toString(lctEsafeFlag));
		lctEsafe.setUserId(userId);
		
		respJsonData.setSetLctEsafe(lctEsafe);

		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
		}
		
	}

	public boolean getSsidList(String imeiStr, boolean ssidFlag, Integer userId, Thread lock)  {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
        try {
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			GetSsidList getSsidList = new GetSsidList();
			getSsidList.setSsidFlag(Boolean.toString(ssidFlag));
			getSsidList.setUserId(userId);
			
			respJsonData.setGetSsidList(getSsidList);
	
			resp = JSON.toJSONString(respJsonData);
		
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
 			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception getSsidList exception:");				
		}
        
        return res;
		
		
	}

//	public boolean setSsidEsafe(String imeiStr, boolean ssidEsafeFlag, 
//			String bssid, Integer userId, Thread lock) {
	public boolean setSsidEsafe(String imeiStr, boolean ssidEsafeFlag, 
			String bssid, String ssid, Integer userId, Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

        try {

			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}			
			SsidEsafe ssidEsafe = new SsidEsafe();
			ssidEsafe.setSsidFlag(Boolean.toString(ssidEsafeFlag));
			ssidEsafe.setBssid(bssid);
			ssidEsafe.setUserId(userId);
			ssidEsafe.setSsid(ssid);
			
			respJsonData.setSetSsidEsafe(ssidEsafe);
			
			resp = JSON.toJSONString(respJsonData);
		
			deviceIoSession.write(resp);
			resp = null;
			ssidEsafe = null;
			respJsonData = null;
			
 			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setSsidEsafe exception:");				
		}
		
        return res;
		
	}

	public boolean getHealthStep(String imeiStr, boolean upStepFlag, Integer userId, Thread lock)  {

		boolean res = true;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();
		
		
        try {
		
			String resp = "";		
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			
			GetHealthStep getHealthStep = new GetHealthStep();
			getHealthStep.setUpStepFlag(Boolean.toString(upStepFlag));
			getHealthStep.setUserId(userId);
			
			respJsonData.setGetHealthStep(getHealthStep);
	
			resp = JSON.toJSONString(respJsonData);
		
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
 			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
						
			ba.insertVisit(null, imeiStr, null, "exception getHealthStep exception:");
			res = false;
		}
        
        return res;
		
		
	}

	public boolean detectDevUp(String imeiStr, boolean upFlag, Integer userId, Thread lock){
		
		boolean res = false;		
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		
        try {
		
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}
			
			
			DevUp devUp = new DevUp();
			devUp.setUpFlag(Boolean.toString(upFlag));
			devUp.setUserId(userId);
			
			respJsonData.setDetectDevUp(devUp);
	
			resp = JSON.toJSONString(respJsonData);
		
			deviceIoSession.write(resp);
			resp = null;
			respJsonData = null;
 			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception detectDevUp exception:");										
		}
        return res;
	}

	public boolean updateFirmware(String imeiStr, Integer updateFlag,
			String updateVer, Integer userId) throws SystemException {
		String resp = "";
		RespJsonData respJsonData = new RespJsonData();
		IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);
		
		if ( deviceIoSession == null )
			return false;
		
		UpdateFirmware upFirmware = new UpdateFirmware();
		upFirmware.setUpdateFlag(updateFlag);
		upFirmware.setUpdateVer(updateVer);
		upFirmware.setUserId(userId);
		
		respJsonData.setUpdateFirmware(upFirmware);
		
		resp = JSON.toJSONString(respJsonData);
		
        try {
			deviceIoSession.write(resp);
			resp = null;
			upFirmware = null;
			respJsonData = null;
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
//			System.out.println("---deviceIoSession is null!");
		}
	}


	public static ClientSessionManager getClientSessionMangagerInstance() {
		return WTDevHandler.getClientSessionMangagerInstance();
	}

	public boolean setUrgentModeBk(String imeiStr, boolean stateFlag,
			boolean ledFlag, boolean playFlag, String cmdTime,
			Integer duration, Integer userId, Thread lock) {
		
		return true;
		

	}

	public boolean setUrgentMode(String imeiStr, boolean stateFlag,
			boolean ledFlag, boolean playFlag, String cmdTime,
			Integer duration, Integer userId, Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		
		try { 
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}

			UrgentMode urgentMode = new UrgentMode();
			
			urgentMode.setStateFlag(Boolean.toString(stateFlag));
			urgentMode.setDuration(duration);
			urgentMode.setUserId(userId);
			urgentMode.setCmdTime(cmdTime);
			urgentMode.setPlayFlag(Boolean.toString(playFlag));
			urgentMode.setLedFlag(Boolean.toString(ledFlag));
			
			
			respJsonData.setSetUrgentMode(urgentMode);
	//		respJsonData.setResultCode(Constant.SUCCESS_CODE);
			
			resp = JSON.toJSONString(respJsonData);
					
			deviceIoSession.write(resp);
			resp = null;
			urgentMode = null;
			respJsonData = null;
			
			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setUrgentMode2 exception:");							
		}
		
		return res;
		

	}
	
	
	public boolean setDevOffOn(String imeiStr, Integer offOnFlag,
			String offTime, String onTime, Integer userId, Thread lock) {

		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		
		try { 
			String resp = "";
			RespJsonData respJsonData = new RespJsonData();
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh = null;
				return false;
			}

			DevOffOn devOffOn = new DevOffOn();
			
			devOffOn.setOffOnFlag(offOnFlag);
			devOffOn.setOffT(offTime);
			devOffOn.setOnT(onTime);
			devOffOn.setUserId(userId);
			
			respJsonData.setDevOffOn(devOffOn);
		
			resp = JSON.toJSONString(respJsonData);
					
			deviceIoSession.write(resp);
			resp = null;
			devOffOn = null;
			respJsonData = null;
			
			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, imeiStr, null, "exception setDevOffOn exception:");							
		}
		
		return res;
		
	}

	
	public boolean setSsNet(String imeiStr, boolean modeFlag, Integer userId, 
			Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		try {
		
			String resp = "";
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh= null;
				return false;
			}
			
			JSONObject jo = new JSONObject();
			if (modeFlag)
				jo.put("ssNet", 1);
			else
				jo.put("ssNet", 0);
			jo.put("userId", userId);
				
	
			//deviceIoSession.write(jo.toString());

			//if (lock != null )  
			//	res = baseWrite( imeiStr, lock );
			//else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setSsNet exception:");							
			
		}
		
		return res;
		
	}

	public boolean setSsTm(String imeiStr, boolean modeFlag, Integer userId,
			int dur,
			Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		
		
		try {
		
			String resp = "";
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			
			//if ( !"352138064958939".equals(imeiStr) ) {
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh= null;
				return false;
			}
			//}
			
			JSONObject jo = new JSONObject();
			if (modeFlag) {
				jo.put("ssTm", 1);
				jo.put("dur", dur);				
			} else
				jo.put("ssTm", 0);
			

			jo.put("userId", userId);				
	
			deviceIoSession.write(jo.toString());

			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception setSsTm exception:");							
			
		}
		
		return res;
		
	}
	

	public boolean sCp(String imeiStr, String pno, Integer userId,
			Thread lock) {
		boolean res = false;
		WTSigninAction ba = new WTSigninAction();
		ServerHandler sh = new ServerHandler();

		
		
		try {
		
			String resp = "";
			IoSession deviceIoSession = getCmdDeviceIoSession(imeiStr);

			
			//if ( !"352138064958939".equals(imeiStr) ) {
			if ( deviceIoSession == null ) {
				//设备下线处理
				//sh.sysOffline(imeiStr);
				ProDevStat( imeiStr, userId );
				
				sh= null;
				return false;
			}
			//}
			
			JSONObject jo = new JSONObject();
			jo.put("sCp", pno);
			jo.put("userId", userId);				
	
			deviceIoSession.write(jo.toString());

			if (lock != null )  
				res = baseWrite( imeiStr, lock );
			else		
				res = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("---deviceIoSession is null!");
			ba.insertVisit(null, imeiStr, null, "exception sCp exception:");							
			
		}
		
		return res;
		
	}
	
	
	
	private static String this_srv_dn = null;
	
	public String getThis_srv_dn() {
		try {
			if (this_srv_dn == null) {
				this_srv_dn = GetIp.getRealIp();
				return this_srv_dn;				
			} else
				return this_srv_dn;
			
		} catch(Exception e) {
			logger.info("getThis_srv_dn error occured!");
			return null;
		}
	}

	public void ProDevStat(String imei, Integer user_id) {
		try {
			//ababab
			Tools tls =  new Tools();

			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			wdeviceActiveInfo.setCondition("a.device_imei = '"+imei+"' limit 1");
			
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			List<DataMap> list = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
			
			Date d1 = null, d2 = null;
			
			if ( !list.isEmpty() ) {
				String dus_time = list.get(0).getAt("dus_time").toString().trim();
				d1 = tls.getDateFromString(dus_time);
			}
			d2 = tls.getUtcDateStrNowDate();
			
			if ( tls.getSecondsBetweenDays(d1, d2) >= 7 * 30 ) {
				logger.info("imei:" + imei + "deadline. dev offline!");
				ServerHandler sh = new ServerHandler();
				sh.sysOffline(imei);
				sh = null;								
			}
			
			
		} catch (Exception e) {
			logger.info("ProDevStat error occured!");			
		}
	}
	
	
	public void ProDevStatBk170704(String imei, Integer user_id) {
		try {
			JSONObject jo = new JSONObject(); 
			jo.put("imei", imei);
			jo.put("cmd", Constant.SYS_INNER_API_PWD1);
			jo.put("user_id", user_id);
			
			int res = Constant.FAIL_CODE;
			String us = "http://" + Constant.SYS_SERVER_DN1 + ":" +  
				Constant.SYS_SERVER_PORT1 + "/wtpet/doWTDevStat.do";
			res = httpPostInnerProDevStat(us, jo.toString());
			
			if ( res != Constant.SUCCESS_CODE) {
				us = "http://" + Constant.SYS_SERVER_DN2 + ":" +  
						Constant.SYS_SERVER_PORT2 + "/wtpet/doWTDevStat.do";
				res = httpPostInnerProDevStat(us, jo.toString());				
			}

			if ( res != Constant.SUCCESS_CODE) {
				us = "http://" + Constant.SYS_SERVER_DN3 + ":" +  
						Constant.SYS_SERVER_PORT3 + "/wtpet/doWTDevStat.do";
				res = httpPostInnerProDevStat(us, jo.toString());				
			}
						
			if ( res != Constant.SUCCESS_CODE ) {
				logger.info("imei:" + imei + "dist. all offline!");
				ServerHandler sh = new ServerHandler();
				sh.sysOffline(imei);
				sh = null;				
			}

		} catch (Exception e) {
			logger.info("ProDevStat error occured!");			
		}
	}

	//@return
	//非1：设备不在指定服务器连接
	//1: 设备在指定服务器连接
	public int httpPostInnerProDevStat(String urlNameString, String params )  {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";
		int res = 0;

		try {
			String encoding="UTF-8";  
			
	        byte[] data = params.getBytes(encoding);
			BufferedReader in = null;
			StringBuffer sb = new StringBuffer();
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.setRequestMethod("POST");
	        conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
			conn.setRequestProperty("Content-Length", String.valueOf(data.length)); 
			conn.setConnectTimeout(50*1000);    
			OutputStream outStream = conn.getOutputStream();       
			outStream.write(data);    
			outStream.flush();    
			outStream.close();      
			
			int code = conn.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();
				
				String resStr = sb.toString();
				JSONObject jo = JSONObject.fromObject(resStr);
				res = jo.optInt("resultCode");					
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}

	public void ProDevStatTest(String imei, Integer user_id) {
		try {
			JSONObject jo = new JSONObject(); 
			jo.put("imei", imei);
			jo.put("cmd", Constant.SYS_INNER_API_PWD1);
			jo.put("user_id", user_id);
			
			int res = Constant.FAIL_CODE;
			String us = "http://" + Constant.SYS_SERVER_DN1 + ":" +  
				Constant.SYS_SERVER_PORT1 + "/wtpet/doWTDevStat.do";
			res = httpPostInnerProDevStat(us, jo.toString());
			
				us = "http://" + Constant.SYS_SERVER_DN2 + ":" +  
						Constant.SYS_SERVER_PORT2 + "/wtpet/doWTDevStat.do";
				res = httpPostInnerProDevStat(us, jo.toString());				

				us = "http://" + Constant.SYS_SERVER_DN3 + ":" +  
						Constant.SYS_SERVER_PORT3 + "/wtpet/doWTDevStat.do";
				res = httpPostInnerProDevStat(us, jo.toString());				
						

		} catch (Exception e) {
			logger.info("ProDevStat error occured!");			
		}
	}
	
	
	
}
