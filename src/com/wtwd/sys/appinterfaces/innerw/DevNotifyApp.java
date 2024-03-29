package com.wtwd.sys.appinterfaces.innerw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.godoing.rose.lang.DataMap;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;



public class DevNotifyApp {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getSessionDevTime(IoSession session) {
		String devTime = "";
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
			
			
			if(session.containsAttribute("wdeviceInfo")) {
				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
				//device_id = (Integer) dm.getAt("device_id"); 
				TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
				dateFormat.setTimeZone(devTimeZone);
				//devTime = dateFormat.format(new Date());
				if ( Constant.timeUtcFlag )					
					devTime = tls.getUtcDateStrNow();
				else
					devTime = dateFormat.format(new Date());
				dm = null;
			} else {
				ba.insertVisit(null, null, "80", "exception getSessionDevTime 1" );								
			}
			
		} catch( Exception e) {
			ba.insertVisit(null, null, "80", "exception getSessionDevTime 2" );											
		}
		
		return devTime;
	}
	
	public void proEcoModeRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

			//BaseAction.insertVisit(null, null, String.valueOf(device_id), "proEcoModeRes 6 id " + device_id 
			//		+ " imei:" + devId + " time:" + dev_time + " uid:" + reqJsonData.getUserId() );				
	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
			deviceActiveInfo.setEco_mode(reqJsonData.getEcoFlag());

			
			deviceActiveInfo.setDev_timestamp(dev_time);

			
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);

			if (!Constant.IS_SERV_STAT_CT_ECOMODE) {
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
	
					mClientSessionManager.completeHttpCmdId(devId, 
							AdragonConfig.setEcoModeRes, 
							srcUserId, null );
				}
			}
			if ( Tools.OneString.equals(reqJsonData.getEcoFlag())  )
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ECO_MODE_ON);
			else
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ECO_MODE_OFF);
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else
				aMsg.setMsg_date(ba.getDeviceNow(device_id));
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			
			
			
			deviceActiveInfo = null;
			deviceInfoFacade = null;
			
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proEcoModeRes exception " );				
			
		}
	
			
	}

	
	public void proSsNetRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

			//BaseAction.insertVisit(null, null, String.valueOf(device_id), "proEcoModeRes 6 id " + device_id 
			//		+ " imei:" + devId + " time:" + dev_time + " uid:" + reqJsonData.getUserId() );				
	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
			
			int snetRes = 0;
			snetRes = reqJsonData.getResult();
			
			JSONObject jo = new JSONObject();
			jo.put("result", snetRes);
			
			
			if (!Constant.IS_SERV_STAT_CT_ECOMODE) {		
				
				if ( reqJsonData.getCurNet() == 1 )
					deviceActiveInfo.setEco_mode(Tools.OneString);
				else
					deviceActiveInfo.setEco_mode(Tools.ZeroString);
					
				
				deviceActiveInfo.setDev_timestamp(dev_time);
	
				
				deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
			}
			
			//aaabbb
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	

				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.setEcoModeRes, 
						srcUserId, jo.toString() );
			}
			if ( reqJsonData.getCurNet() == 1 )
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ECO_MODE_ON);
			else
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ECO_MODE_OFF);
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else
				aMsg.setMsg_date(ba.getDeviceNow(device_id));
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			
						
			deviceActiveInfo = null;
			deviceInfoFacade = null;
			
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proEcoModeRes exception " );				
			
		}
	
			
	}
	

	public void proTmRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

	    	LocationInfoHelper lih = null;	//new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			WMsgInfo aMsg = null;	//new WMsgInfo();
			//aMsg.setDevice_id(device_id);
			
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
			
			int sRes = 0;
			//sRes = reqJsonData.getResult();
			
			JSONObject jo = new JSONObject();
			jo.put("result", sRes);
			
			
				
			if ( reqJsonData.getOffOnFlag() == 1 ) {
				deviceActiveInfo.setTm_flag(Tools.OneString);
				deviceActiveInfo.setTm_dur(reqJsonData.getDur());				
			} else
				deviceActiveInfo.setTm_flag(Tools.ZeroString);
				
			
			deviceActiveInfo.setDev_timestamp(dev_time);

			
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
			
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	

				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.ssTmRes, 
						srcUserId, jo.toString() );
			}
			/*
			if ( reqJsonData.getOffOnFlag() == 1 )
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_);
			else
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_);
				*/
			
			
			/*
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else
				aMsg.setMsg_date(ba.getDeviceNow(device_id));
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			*/
						
			deviceActiveInfo = null;
			deviceInfoFacade = null;
			
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proEcoModeRes exception " );				
			
		}
	
			
	}
	
	//aabaa
	public void prouTMD( Integer device_id, 
			String devId, String dev_time, 
			ReqJsonData reqJsonData) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			
			
			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_UT_NTFY);
			JSONObject jon = new JSONObject();
			//jon.put("ET", Integer.parseInt(reqJsonData.getEt()) );
			//jon.put("BT", Integer.parseInt(reqJsonData.getBt()));
			jon.put("ET", (int) (Float.parseFloat(reqJsonData.getEt()) ) );
			jon.put("BT", (int) (Float.parseFloat(reqJsonData.getBt()) ) );
			jon.put("BR", (int) (Float.parseFloat(reqJsonData.getBr()) ) );
			jon.put("MR", (int) (Float.parseFloat(reqJsonData.getMr()) ) );
			

			aMsg.setMsg_content(jon.toString());
			
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else
				aMsg.setMsg_date(ba.getDeviceNow(device_id));
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
											
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception prouTMD exception " );				
			
		}
	
			
	}
	
	
	
	public void proGetHeathRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara ) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
			if ( "1.7".equals(Constant.GHEALTH_PROTOCOL_VER ) ) {	
				ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();

				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.getHealthStepRes, 
						srcUserId, null );
			}
			
			
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_GET_HEALTH_RES);
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {				
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}

			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proGetHeathRes exception " );				
			
		}
	
			
	}
	

	public boolean proUFirmRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara ) {
		Tools tls = new Tools();	
		WTSigninAction ba = new WTSigninAction();
		
		try {
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
	    	LocationInfoHelper lih = new LocationInfoHelper();


			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
			WTAppDeviceManAction wdma = new WTAppDeviceManAction();
	    	wdma.ctlWdevUFirmState(device_id, false);

	    	if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {										
				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.updateFirmwareRes, 
						srcUserId, null );
			}
			//Integer userId = reqJsonData.getUserId();
			//String upVer = reqJsonData.getUpVer();
			
			//jon.put("user_id", userId);
			//jon.put("upResult", reqJsonData.getUpResult());
			//jon.put("updateVer", upVer);
			
			JSONObject object = JSONObject.fromObject(extPara);
			Integer errorCode = object.optInt("errorCode");

	    	
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_UPDATE_FIRM_FINISH);
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {								
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.ZeroString);
			if ( errorCode == 0 ) {
				//aMsg.setSummary("Firmware upgrade successful.");
				aMsg.setError_code(errorCode);
				aMsg.setSummary(lih.getMsgContentFromMsg(aMsg, null, null));
			} else {
				//aMsg.setSummary("Firmware upgrade failed,please check the device.");
				aMsg.setError_code(errorCode);
				aMsg.setSummary(lih.getMsgContentFromMsg(aMsg, null, null));				
			}
			
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			return true;
					
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proUFirmRes exception " );
			return false;
			
		}
	
			
	}
	
	
	public boolean proSsidEsafeState( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara, String summary, String ssidEsafeFlag ) {
		
		
		Tools tls = new Tools();
		
		if (tls.isNullOrEmpty(reqJsonData.getBssid()))
			return true;
		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();


			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
//			deviceActiveInfo.setDevice_id(device_id);
			deviceActiveInfo.setBssid_wifi(reqJsonData.getBssid());
			deviceActiveInfo.setEstat_wifi(reqJsonData.getSsidEsafeFlag());
//			deviceActiveInfo.setDev_timestamp(dateFormat.format(new Date()));
		
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
			
												
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			aMsg.setEflag(ssidEsafeFlag);
			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_WIFI_ESAFE_BRDCST);
			aMsg.setDevice_id(device_id);	

			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {								
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.ZeroString);
			
						
			aMsg.setSummary(summary);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
								
			return true;
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proSsidEsafeState exception " );				
			return false;
		}
			
	}
	
	public boolean proChargingCmd( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara ) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();

			String charging_status = reqJsonData.getPlugFlag();
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
//			deviceActiveInfo.setDevice_id(device_id);
			deviceActiveInfo.setCharging_status(charging_status);
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
														
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);			

			if ( Tools.OneString.equals(charging_status) )
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_CHARGER_ON);
			else
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_CHARGER_OFF);
				
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {							
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
								
			return true;
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proChargingCmd exception " );				
			return false;
		}
	
			
	}

	public boolean proUpdateStart( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara ) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
		    //if ( result == Constant.SUCCESS_CODE )
		    //	ctlWdevUFirmState(device_id, true);
			WTAppDeviceManAction wdma = new WTAppDeviceManAction();
			wdma.ctlWdevUFirmState(device_id, true);
														
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEV_UFIRM_START);
				
			aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {							
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
								
			return true;
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proUpdateStart exception " );				
			return false;
		}
	
			
	}
	
	public boolean proSetSleep( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData ) {
		WTSigninAction ba = new WTSigninAction();
		Tools tls = new Tools();
		
		try {

			Integer srcUserId = 0;
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			srcUserId = reqJsonData.getUserId();

			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
			deviceActiveInfo.setSleep_status(reqJsonData.getSleepStateFlag());		
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);			

			JSONObject jon = new JSONObject();
			jon.put("sleepStateFlag", reqJsonData.getSleepStateFlag());
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	

				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.setSleepStateRes, 
						srcUserId, jon.toString() );
			}			
			
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_SET_SLEEP_RES);							
			aMsg.setDevice_id(device_id);
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {				
				
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(jon.toString());
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			jon = null;

			return true;
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proSetSleep exception " );				
			return false;
		}
	
			
	}


	public boolean proDevBeatNtfy( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			String extPara ) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {

	    	LocationInfoHelper lih = new LocationInfoHelper();

			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
	    	if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {										
				//mClientSessionManager.completeHttpCmdId(devId, 
				//		AdragonConfig.updateFirmwareRes, 
				//		userId, jon.toString() );
			}
			
			JSONObject object = JSONObject.fromObject(extPara);
			//Integer signal = object.optInt("signal");
			//Integer battery = object.optInt("battery");

	    	
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(device_id);
			

			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEVBEAT_NTFY);
			//aMsg.setDevice_id(device_id);	
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {								
				if ( tls.isNullOrEmpty(dev_time)  )  
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				else
					aMsg.setMsg_date( dev_time );
			}
				
			aMsg.setMsg_content(extPara);
			aMsg.setHide_flag(Tools.OneString);
			
			
			lih.proCommonInnerMsg(aMsg, srcUserId);
			return true;
					
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proDevBeatNtfy exception " );
			return false;
			
		}
	
			
	}

	
	public void proSetTOnOffRes( Integer device_id, 
			String devId, String dev_time, ReqJsonData reqJsonData,
			Integer flag ) {
		WTSigninAction ba = new WTSigninAction();
		
		try {


			Integer srcUserId = 0;			
			srcUserId = reqJsonData.getUserId();
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {	
				ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();

				//BaseAction.insertVisit(null, null, String.valueOf(device_id), "proSetTOnOffRes");							
				
				JSONObject jo = new JSONObject();
				jo.put("tflag", flag);
				
				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.setOffOnRes, 
						srcUserId, jo.toString() );
				//BaseAction.insertVisit(null, null, String.valueOf(device_id), "proSetTOnOffRes 1");							

			}
			
			
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proSetTOnOffRes exception " );				
			
		}
	
			
	}

	public boolean proSetTOnOffDbAndMsg( String devId, CmdSync pSync ) {
		Tools tls = new Tools();
		
		boolean res = true;
		Integer tflag = null;
		String ton = null;
		String toff = null;
		WTSigninAction ba = new WTSigninAction();
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			Integer device_id = pSync.getDevice_id();
			if (device_id != null && device_id > 0) {
				WMsgInfo aMsg = new WMsgInfo();
				aMsg.setDevice_id(device_id);
				
	
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_TONOFF_NTFY);
				aMsg.setDevice_id(device_id);
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {									
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}
	
				JSONObject jo = new JSONObject();
				tflag = pSync.getTflag();
				ton = pSync.getTon();
				toff = pSync.getToff();
				jo.put("tflag", tflag);
				jo.put("ton", ton);
				jo.put("toff", toff);
				aMsg.setMsg_content(jo.toString());
				aMsg.setHide_flag(Tools.OneString);				
				lih.proCommonInnerMsg(aMsg, pSync.getUser_id());

				//BaseAction.insertVisit(null, null, String.valueOf(device_id), "proSetTOnOffDbAndMsg proCommonInnerMsg");							
				
				
				WdeviceActiveInfo wai = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade waiFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			
				wai.setAutopdn_status(tflag);
				wai.setTime_pon(ton);
				wai.setTime_poff(toff);
				wai.setCondition("device_id=" + device_id);
				
				if ( waiFd.updatewDeviceExtra(wai) < 1 ) 
					res = false;
					
			} else
				res = false;
			
			
		} catch ( Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, devId, null, "exception proSetTOnOffDbAndMsg exception " );						
		}
		
		return res;
	
			
	}
	
	
	
}
