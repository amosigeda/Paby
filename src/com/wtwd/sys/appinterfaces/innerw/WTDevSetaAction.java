package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.CmdLedRun;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class WTDevSetaAction extends BaseAction {
	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTSigninAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			
			logger.info("WTDevSetaAction request param:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTDevSetaAction");			
			
			
			int device_id = object.getInt("device_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if ( ( result = verifyUserDevice(String.valueOf(user_id), 
							String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {					
						if (cmd.equals("set")) {	//APP设备远程数据设置,炫酷灯控制
							if ( proSet(object, response) )
							{
								//Thread.sleep(1000* 60 * 2);
								if ( "1.6".equals(Constant.PROTOCOL_VER ) )
									return null;
							}
						} else if ( cmd.equals("ecoCtl")) {	//省电模式开关
							if ( proEcoSet(object, response) )
							{
								//Thread.sleep(1000* 60 * 2);
								if ( "1.6".equals(Constant.PROTOCOL_VER ) )
									return null;
							}
							
						} else if ( cmd.equals("ledSosCtl")) {	//紧急模式灯开关
							if ( proLedSosSet(object, response) )
							{
								//Thread.sleep(1000* 60 * 2);
								if ( "1.6".equals(Constant.PROTOCOL_VER ) )
									return null;
							}							
							
						} else if ( cmd.equals("query")) {
							proQuery(object);
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
	
	
	void proQuery(JSONObject object) throws SystemException, InterruptedException {
		Tools tls = new Tools();
		
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		WdeviceActiveInfo wa = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		wa.setCondition("device_id = "+device_id);

		List<DataMap> list = fd.getData(wa);
		if ( list != null && list.size() == 1)
		{
		//WTDevSetaFacade infoFacade = ServiceBean.getInstance().getWtDevSetaFacade();
			DataMap amap = list.get(0);
			String device_data_mute = amap.getAt("data_mute").toString().trim();
		    String device_data_volume = amap.getAt("data_volume").toString().trim();
		    String device_data_power = amap.getAt("data_power").toString().trim();
		    String device_data_light = amap.getAt("led_on").toString().trim();
			String flight_mode = amap.getAt("flight_mode").toString().trim();
			String wifi_interval = amap.getAt("wifi_interval").toString().trim();
			String wifi_flag = amap.getAt("wifi_flag").toString().trim();
			json.put("device_data_mute", device_data_mute);
			json.put("device_data_volume", device_data_volume);
			json.put("device_data_power", device_data_power);
			json.put("device_data_light", device_data_light);			
			json.put("flight_mode", flight_mode);
			json.put("wifi_interval", wifi_interval);
			json.put("wifi_flag", wifi_flag);			
			result = Constant.SUCCESS_CODE;		
		} else {
			result = Constant.FAIL_CODE;		
		}

		
	}
	
	
	Boolean proSet(JSONObject object, HttpServletResponse response) throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();		
		
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		int user_id = object.optInt("user_id");
		WdeviceActiveInfo wa = new WdeviceActiveInfo();
		//WTDevSetaFacade infoFacade = ServiceBean.getInstance().getWtDevSetaFacade();
		
		String device_data_mute = tls.getSafeStringFromJson(object,"device_data_mute");
		String device_data_volume = tls.getSafeStringFromJson(object,"device_data_volume");
		String device_data_power = tls.getSafeStringFromJson(object,"device_data_power");
		String device_data_light = tls.getSafeStringFromJson(object,"device_data_light");
		String flight_mode = tls.getSafeStringFromJson(object,"flight_mode");
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));

		String device_eco_mode = tls.getSafeStringFromJson(object,"device_eco_mode");
		
		
		String wifi_interval = null;
		String wifi_flag = null;
		JSONObject wifiObj = null;
		if ( object.containsKey("wifi_range") ) {
			wifiObj = JSONObject.fromObject(object.get("wifi_range").toString().trim());
			wifi_interval = wifiObj.getString("interval");
			wifi_flag = wifiObj.getString("wifi_flag");
			wa.setWifir_interval(wifi_interval.trim());
			wa.setWifir_flag(wifi_flag.trim());
			
		}

		
		if(!tls.isNullOrEmpty(flight_mode)){
			wa.setFlight_mode(flight_mode);
		}

		if(!tls.isNullOrEmpty(device_data_mute)){
			wa.setData_mute(device_data_mute);
		}
		if(!tls.isNullOrEmpty(device_data_volume)){
			wa.setData_volume(device_data_volume);
		}
		if(!tls.isNullOrEmpty(device_data_power)){
			wa.setData_power(device_data_power);
		}
		if(!tls.isNullOrEmpty(device_data_light)){
			/*			
			try { 
				WTSigninAction  sa = new WTSigninAction();
				sa.heartBeat(String.valueOf(user_id));	
				sa = null;
				// end
			} catch(Exception e) {							
			}
			*/								
			
			
			wa.setLed_on(device_data_light);	
			
			Integer res = devControlLed(object, user_id, device_imei, device_data_light, response);

			if ( !"1.6".equals(Constant.PROTOCOL_VER ) ) {
			
				result  = Constant.FAIL_CODE;			
				Thread.sleep(200);
				wa.setCondition(" a.device_id = "+device_id + " and a.led_on=" + res);
				int retryCount = 0;
				WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				while(retryCount < 15) {
					List<DataMap> list = fd.getWdeviceActiveInfo(wa);
					if( list != null && list.size() == 1 ) {
						result = Constant.SUCCESS_CODE;
						break;
					}				
					retryCount++;
					Thread.sleep(1200);				
				}
			}
			
			return true;

			
			
		}
		
		
		//wa.setCondition(" device_id = "+device_id);
		//int res = infoFacade.updateDevSeta(wa);
		//if(res < 0){
		//	result = Constant.FAIL_CODE;
		//}else{
		//	result = Constant.SUCCESS_CODE;
		devControlSet(object);			
		//}
		
		return false;
		
			
	}

	
	void devControlSet(JSONObject object) throws SystemException, InterruptedException {
		//CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
		Tools tls = new Tools();
		
		String device_data_light = tls.getSafeStringFromJson(object,"device_data_light");
		String flight_mode = tls.getSafeStringFromJson(object,"flight_mode");

		int device_id = tls.getSafeIntFromJson(object, "device_id");

		String imeiStr = getDeviceImeiFromDeviceId(String.valueOf(device_id) );
		
		/*
		if(!Tools.isNullOrEmpty(device_data_light)){
			devControlLed( cmdDownSetImpl, imeiStr, device_data_light);
		}
		if(!Tools.isNullOrEmpty(flight_mode)){
			devControlFlightMode( cmdDownSetImpl, imeiStr, flight_mode);
		}*/

		String wifi_interval = null;
		String wifi_flag = null;
		JSONObject wifiObj = null;
		if ( object.containsKey("wifi_range") ) {
			wifiObj = JSONObject.fromObject(object.get("wifi_range").toString().trim());
			wifi_interval = wifiObj.getString("interval");
			wifi_flag = wifiObj.getString("wifi_flag");
			devControlWifiRange(imeiStr, wifi_interval, wifi_flag);
		}
		
		
	}


	void devControlWifiRange(String imeiStr, String wifi_interval, String wifi_flag) throws NumberFormatException, SystemException {
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
		cmdDownSetImpl.setWIFIRange(imeiStr, Integer.parseInt(wifi_interval), "1".equals(wifi_flag.trim()));
	}
	
	
	
	Integer devControlLed(JSONObject object, Integer user_id, String imeiStr, String device_data_light, HttpServletResponse response) throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();
		
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

		Thread lock = new Thread(); 
		CmdSync cmd = new CmdSync();
		cmd.setCmdName(AdragonConfig.setLedStateRes);
		cmd.setResponse(response);
		cmd.setTdLock(lock);
		cmd.setUser_id(user_id);
		int device_id = tls.getSafeIntFromJson(object, "device_id");


		if (Constant.cmdDirectResFlag) {	
			LocationInfoHelper lih = new LocationInfoHelper();
			boolean preCheckRes = lih.preCheckDevStatus(device_id, imeiStr);
			if ( preCheckRes )
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
			else
				json.put(Constant.RESULTCODE, Constant.ERR_DEV_IS_NOT_ONLINE);
				
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(json.toString());
			Boolean ledCtlFlag = false;
			if ( Tools.OneString.equals(device_data_light) )
				ledCtlFlag = true;
			
			if (Constant.IS_SERV_STAT_CT) {
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();

				wdeviceActiveInfo.setLed_on(tls.getOneZeroStringFromBool(ledCtlFlag.toString()));
				wdeviceActiveInfo.setCondition("device_imei = '"+imeiStr+"'");
				wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
				wdeviceActiveInfo = null;
			}
			
			CmdLedRun cpr = new CmdLedRun(AdragonConfig.setLedStateRes, object.toString(), ledCtlFlag);
			Thread tcpr=new Thread(cpr); 
			tcpr.start();			
			return 0;
			
		}
		
		
		if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {		
			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);
		}
		
		if( "0".equals(device_data_light) ) {
			if ( !cmdDownSetImpl.setLedState( imeiStr, 3, false, user_id, lock) ) {
				/*
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
					synchronized(lock){
						lock.wait(1000* 60 * 2);
					    //或者wait()
					}
				}*/
	
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}

			return 0;
	
		} else if ("1".equals(device_data_light)) {
			if ( !cmdDownSetImpl.setLedState( imeiStr, 3, true, user_id, lock) ) {
	
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}
			
			/*
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
				synchronized(lock){
					lock.wait(1000* 60 * 2);
				    //或者wait()
				}
			}*/


			return 1;
			
		} else if ("2".equals(device_data_light)) {
			if ( !cmdDownSetImpl.setLedState( imeiStr, 3, true, user_id, lock) ) {
	
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}
			
			return 1;
		}
		
		
		//json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
		//response.setCharacterEncoding("UTF-8");	
		//response.getWriter().write(json.toString());

		throw new SystemException();
	}


	void devControlFlightMode(CmdDownSetImpl cmdDownSetImpl, String imeiStr, String flight_mode ) 
			throws SystemException, InterruptedException {
		if( "1".equals(flight_mode) ) {
			cmdDownSetImpl.setFlightMode(imeiStr, true );	
		} else if ("0".equals(flight_mode)) {
			cmdDownSetImpl.setFlightMode(imeiStr, false );	
		} 
	}
	
	public Integer devControlEco(Integer user_id, String imeiStr, 
			String eco_mode, HttpServletResponse response) throws SystemException, InterruptedException, IOException {
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

		Thread lock = new Thread(); 
		CmdSync cmd = new CmdSync();
		cmd.setCmdName(AdragonConfig.setEcoModeRes);
		cmd.setResponse(response);
		if (Constant.IS_SERV_STAT_CT_ECOMODE) { //true
			cmd.setTdLock(null);
		} else {
			cmd.setTdLock(lock);
		}
		
		cmd.setUser_id(user_id);

		if (!Constant.IS_SERV_STAT_CT_ECOMODE) { //false		
			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);
		} else {
			boolean mv = false;
			if( "1".equals(eco_mode) ) {
				mv = true;
			}
			cmdDownSetImpl.setEcoMode( imeiStr, mv, user_id, null);
			cmdDownSetImpl.setSsNet( imeiStr, mv, user_id, null);			

			DevNotifyApp dna = new DevNotifyApp();
			Tools tls = new Tools();
			String devTime = tls.getUtcDateStrNow();				

			ReqJsonData reqJsonData = new ReqJsonData();
			reqJsonData.setUserId(user_id);
			reqJsonData.setEcoFlag(eco_mode);
			dna.proEcoModeRes(this.getDeviceIdFromDeviceImei(imeiStr), imeiStr, devTime, reqJsonData);

			json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(json.toString());
			
			tls = null;
			reqJsonData = null;
			
			return 1;
			
		}
		
		if( "0".equals(eco_mode) ) {
			
			if ( !cmdDownSetImpl.setEcoMode( imeiStr, false, user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			} else if ( "1".equals(belongProject ) ) {
				CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);				
				cmdDownSetImpl.setSsNet( imeiStr, false, user_id, null);
			}
			return 1;
	
		} else if ("1".equals(eco_mode)) {
			if (!cmdDownSetImpl.setEcoMode( imeiStr, true, user_id, lock) ) {
				cmdDownSetImpl.setSsNet( imeiStr, true, user_id, lock);  

				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			} else if ( "1".equals(belongProject ) ) {
				CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);				
				cmdDownSetImpl.setSsNet( imeiStr, true, user_id, null);
			}
			return 1;
		} 
		throw new SystemException();
	}

	Boolean proEcoSet(JSONObject object, HttpServletResponse response) 
			throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();		
		
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		int user_id = object.optInt("user_id");
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));

		
		String device_eco_mode = tls.getSafeStringFromJson(object,"device_eco_mode");		
		
		
		
		if(!tls.isNullOrEmpty(device_eco_mode)){
			/*			
		    try { 
				WTSigninAction  sa = new WTSigninAction();
				sa.heartBeat(String.valueOf(user_id));	
				sa = null;
				// end
			} catch(Exception e) {		
				return false;
				
			}*/								
			
			devControlEco(user_id, device_imei, device_eco_mode, response);

			return true;
			
			
		}
		
		return false;
		
			
	}

	Boolean proLedSosSet(JSONObject object, HttpServletResponse response) 
			throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();		
		
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		int user_id = object.optInt("user_id");
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		
		String flag = tls.getSafeStringFromJson(object,"flag");		
		
		insertVisit(null, null, String.valueOf(device_id), "proLedSosSet flag" + flag);
		
		
		if(!tls.isNullOrEmpty(flag)){
			/*
		    try { 
				WTSigninAction  sa = new WTSigninAction();
				sa.heartBeat(String.valueOf(user_id));	
				sa = null;
				// end
			} catch(Exception e) {		
				return false;
				
			}*/								
			
		    devControlLedSos(user_id, device_imei, flag, response);

			return true;
			
			
		}
		
		return false;
		
			
	}
	
	Integer devControlLedSos(Integer user_id, String imeiStr, String flag, HttpServletResponse response) throws SystemException, InterruptedException, IOException {
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

		Thread lock = new Thread(); 
		CmdSync cmd = new CmdSync();
		cmd.setCmdName(AdragonConfig.setLedSosRes);
		cmd.setResponse(response);
		cmd.setTdLock(lock);
		cmd.setUser_id(user_id);

		CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);
	
		
		if( "0".equals(flag) ) {
			boolean res = true;
			
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {				
				res = cmdDownSetImpl.setLedState( imeiStr, 4, false, user_id, lock);
			} else {
				res = cmdDownSetImpl.setLedState( imeiStr, 4, false);
			}
			
			if ( !res ) {
	
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}
			
			/*
			synchronized(lock){
				lock.wait(1000* 60 * 2);
			    //或者wait()
			}*/
			
			return 0;
	
		} else if ("1".equals(flag)) {
			boolean res = true;
			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {				
				res = cmdDownSetImpl.setLedState( imeiStr, 4, true, user_id, lock);
			} else {
				res = cmdDownSetImpl.setLedState( imeiStr, 4, true);
			}

			if ( !res )	{			
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}
			
			/*
			synchronized(lock){
				lock.wait(1000* 60 * 2);
			    //或者wait()
			}*/
			
			return 1;
			
		} 
		throw new SystemException();
	}

	
}
