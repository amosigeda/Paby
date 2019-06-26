﻿package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.CmdEsafeRun;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.bean.other.SsidEsafeInfo;
import com.wtwd.common.bean.other.Ssids;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.WappWiFiInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserWiFiInfoFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class WTAppDevWifiSrcManAction extends BaseAction {
	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTSigninAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd").trim();
			String bssid = object.optString("bssid").trim();			
			String ssid = object.optString("ssid").trim();			
			int flag = object.optInt("flag");			

			int user_id = object.optInt("user_id");
			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppDevWifiSrcManAction," + cmd);		

			String app_token = tls.getSafeStringFromJson(object, "app_token");
			int device_id = object.optInt("device_id");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
						if ( ( result = verifyUserDevice(String.valueOf(user_id), 
								String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {						
							if (cmd.equals("upload")) {	//APP主设备WIFI源信息设置
								proUpload(object);
								//result = Constant.SUCCESS_CODE;													
							} else if (cmd.equals("query")) {
								proQuery(object);
							} else if (cmd.equals("gSsidList")) {
								proSsidList(user_id, device_id, response);
								return null;

							} else if (cmd.equals("sSsidAct")) {	//3.35 APP选择WIFI当前有效热点
								prosSsidAct(object, user_id, device_id,bssid, flag, ssid, response);
								return null;

							} else {
								result = Constant.ERR_INVALID_PARA;													
							}
						}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());


		
		return null;
	}	

	void proUpload(JSONObject object) {
		Tools tls = new Tools();
		
		WappWiFiInfo wi = new WappWiFiInfo();
		AppUserWiFiInfoFacade infoFacade = ServiceBean.getInstance().getAppUserWiFiInfoFacade();

		int user_id = object.optInt("user_id");
		int device_id = object.optInt("device_id");
		if ( user_id <=0 || device_id <=0 ) {
			result = Constant.ERR_INVALID_PARA;
			return;
		}
		wi.setCondition("user_id=" + user_id + " and device_id=" + device_id);
		infoFacade.delAppWiFiInfo(wi);
		
		String ssid = tls.getSafeStringFromJson(object, "ssid");
		String pwd = tls.getSafeStringFromJson(object, "pwd");
		String wifi_on = tls.getSafeStringFromJson(object, "wifi_on");

		wi.setUser_id(user_id);
		wi.setDevice_id(device_id);
		if( !tls.isNullOrEmpty(ssid)){
			wi.setSsid(ssid);
		}
		if( !tls.isNullOrEmpty(pwd)){
			wi.setPwd(pwd);
		}
		if( !tls.isNullOrEmpty(wifi_on)){
			wi.setWifi_on(wifi_on);
		}
		
		int res = infoFacade.insertAppWiFiInfo(wi);
		if(res > 0){
			result = Constant.SUCCESS_CODE;
		} else {
			result = Constant.FAIL_CODE;
		}
		
	}

	void proQuery(JSONObject object) {
		WappWiFiInfo wi = new WappWiFiInfo();
		AppUserWiFiInfoFacade infoFacade = ServiceBean.getInstance().getAppUserWiFiInfoFacade();

		int user_id = object.optInt("user_id");
		int device_id = object.optInt("device_id");
		if ( user_id <=0 || device_id <=0 ) {
			result = Constant.ERR_INVALID_PARA;
			return;
		}
		wi.setCondition("user_id=" + user_id + " and device_id=" + device_id);
		List<DataMap> list = infoFacade.getData(wi);
		if(list != null) {
			if ( !list.isEmpty()) { 
				json.put("ssid", list.get(0).getAt("ssid").toString().trim());
				json.put("pwd", list.get(0).getAt("pwd").toString().trim());
				json.put("wifi_on", list.get(0).getAt("wifi_on").toString().trim());			
				result = Constant.SUCCESS_CODE;
			} else {
				result = Constant.ERR_RES_NOT_EXIST;
			}
				
		} else {
			
			result = Constant.FAIL_CODE;
		}
		
	}

	//获取设备扫描到的WIFI热点列表
	JSONObject proSsidList( int user_id, int device_id, HttpServletResponse response) 
			throws SystemException{
		
		//result = Constant.SUCCESS_CODE;									
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}*/								
		
		devSsidList(user_id, device_imei, response);
		
		return null;
		
	}
	
	void devSsidList(Integer user_id, String  device_imei, HttpServletResponse response) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.getSsidListRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			if ( !cmdDownSetImpl.getSsidList(device_imei, true, user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			}

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取设备扫描到的WIFI热点列表
	JSONObject prosSsidAct( JSONObject object, int user_id, int device_id,String mac, Integer flag, String ssid,  HttpServletResponse response) 
			throws SystemException{
		
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}*/								
		//abcde
		
	    devsSsidAct(object, user_id, device_id, device_imei, mac, flag, ssid, response);
		
		return null;
		
	}

	void updateSsidDbAndNotify(Integer user_id, Integer device_id, String flag, String mac, String ssid) {
		Tools tls = new Tools();
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			deviceActiveInfo.setCondition("device_id = " + device_id);
			deviceActiveInfo.setEsafe_wifi(flag);
			deviceActiveInfo.setBssid_wifi(mac);
			deviceActiveInfo.setSsid_wifi(ssid);
		
			deviceInfoFacade.updatewDeviceExtra(deviceActiveInfo);
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_SEL_ESAFE_WIFI);
			aMsg.setDevice_id(device_id);
			
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {							
				aMsg.setMsg_date(getDeviceNow(device_id));
			}
			
	
			JSONObject jon1 = new JSONObject();
			jon1.put("flag", flag);
			jon1.put("rel_ssid", ssid);	//getSsid()				
			jon1.put("rel_mac", mac);					
			
			aMsg.setMsg_content(jon1.toString());
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void devsSsidAct(JSONObject object, Integer user_id, Integer device_id, String  device_imei, String mac, Integer flag, String ssid, HttpServletResponse response) {
		try {
			Tools tls = new Tools();
			
			insertVisit(null, device_imei, null, "devsSsidAct ssid:" + ssid);
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.setSsidEsafeRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setCmdPara1(ssid);
			
			if ( flag == 1 && tls.isNullOrEmpty(mac) ) {
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
				
				//需要马上更新WIFI电子围栏的状态为开
				updateSsidDbAndNotify( user_id, device_id, Tools.OneString, "", "");				
				return;				
			}

			if ( flag == 0 && cmdDownSetImpl.getCmdDeviceIoSession(device_imei) == null ) {
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
				
				//需要马上更新WIFI电子围栏的状态为关
				updateSsidDbAndNotify( user_id, device_id, Tools.ZeroString, "", "");				
				return;				
			}
			
			
			if (Constant.cmdDirectResFlag) { // true	
				LocationInfoHelper lih = new LocationInfoHelper();
				boolean preCheckRes = lih.preCheckDevStatus(device_id, device_imei);
				if ( preCheckRes )
					json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
				else
					json.put(Constant.RESULTCODE, Constant.ERR_DEV_IS_NOT_ONLINE);
					
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
				boolean pflag = false;
				if ( flag == 1 )
					pflag = true;
				CmdEsafeRun cpr = new CmdEsafeRun(AdragonConfig.setSsidEsafeRes, object.toString(), pflag);
				Thread tcpr=new Thread(cpr); 
				tcpr.start();			
				return;				
			}
			
			
			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			boolean res;
			
			if (flag == 1 )
				res = cmdDownSetImpl.setSsidEsafe(device_imei, true, mac, ssid,  user_id, lock);
			else
				res = cmdDownSetImpl.setSsidEsafe(device_imei, false, mac, ssid,  user_id, lock);

			if ( !res ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void proGetSsidListRes(  Integer device_id, 
			String devId, ReqJsonData reqJsonData) {
		
		try {
			Tools tls = new Tools();
			
			insertVisit(null, null, String.valueOf(device_id), "proGetSsidListRes start" );				
			
			Integer userId = reqJsonData.getUserId();
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();

			Ssids ssids = reqJsonData.getSsids();
			JSONObject ssidsJson = JSONObject.fromObject(ssids);
			String ssidListStr = ssidsJson.toString();

			JSONArray jsonArr = new JSONArray();
			JSONObject jObj = null;
			
			
			List<SsidEsafeInfo> ssidListInfos = ssids.getSsidList();
			//just debug:
			System.out.println("ssidListStr "+ ssidListStr);
			
			WdeviceActiveInfo wa = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade fd = ServiceBean.
					getInstance().getWdeviceActiveInfoFacade();
			wa.setCondition("device_id=" + device_id);
			List<DataMap> list = fd.getwDeviceExtra(wa);

			String selMac = null;
			String selSsid = null;
			String selSignal = String.valueOf(Integer.MAX_VALUE);

			String esafe_on = "0";
			if (list != null && !list.isEmpty() ) {
				esafe_on = list.get(0).getAt("esafe_wifi").toString();
				
				if ( !tls.isNullOrEmpty(esafe_on) && "1".equals(esafe_on)   ) {
					esafe_on = "1";
					selMac = list.get(0).getAt("bssid_wifi").toString();
					selSsid = list.get(0).getAt("ssid_wifi").toString();
					jObj = new JSONObject();
					jObj.put("mac", selMac);
					jObj.put("ssid", selSsid);
				} else
					esafe_on = "0";
				
				
			}
			JSONObject jObjLoop = new JSONObject();

			if ( selMac != null ) {				
				for (int i = 0; i < ssidListInfos.size(); i++) {
					if (selMac.equals(ssidListInfos.get(i).getMac()) ) {
						selSignal = ssidListInfos.get(i).getSignal();
						break;
					}
						
				}
				jObj.put("signal", selSignal);
				jsonArr.add(jObjLoop);						

			}
			
			JSONObject jObjArr[] = null;
			if ( ssidListInfos.size() > 0 )
				jObjArr = new JSONObject[ssidListInfos.size()];  
			for (int i = 0; i < ssidListInfos.size(); i++) {
				if (selMac == null || !selMac.equals(ssidListInfos.get(i).getMac()) ) {
					jObjArr[i] = new  JSONObject();
					jObjArr[i].put("mac", ssidListInfos.get(i).getMac());
					jObjArr[i].put("ssid", ssidListInfos.get(i).getSsid());
					jObjArr[i].put("signal", ssidListInfos.get(i).getSignal());
					jsonArr.add(jObjArr[i]);						
				}
					
			}

			if ( jObj == null )
				jObj = new JSONObject();
			else
				jObj.clear();
			jObj.put("flag", esafe_on);
			jObj.put("s_list", jsonArr);
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {										
				mClientSessionManager.completeHttpCmdId(devId, 
						AdragonConfig.getSsidListRes, 
						userId, jObj.toString() );
			}
			
			ssidListInfos.clear();
			ssidListInfos = null;
			jObjArr = null;

		} catch( Exception e) {
			e.printStackTrace();
			
			insertVisit(null, null, String.valueOf(device_id), "exception proGetSsidListRes excpetion" );				
			
		}
	
	}
}