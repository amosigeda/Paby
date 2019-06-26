﻿package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import com.wtwd.common.bean.devicedown.CmdSosRun;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppDeviceDiscoveryManFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class WTAppDiscoveryManAction extends BaseAction {
	
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
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");
			int device_id = object.getInt("device_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if ( ( result = verifyUserDevice(String.valueOf(user_id), 
							String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {
						
						if (cmd.equals("on")) {	//开启发现模式
							/*
							try { 
								WTSigninAction  sa = new WTSigninAction();
								sa.heartBeat(String.valueOf(user_id) );	
								sa = null;
								// end
							} catch(Exception e) {							
							}*/								
							proOn(object, response);

							if ( "1.6".equals(Constant.PROTOCOL_VER ) )
								return null;
							
							//result = Constant.SUCCESS_CODE;													
						} else if (cmd.equals("off")) {	//关闭发现模式
							/*
							try { 
								WTSigninAction  sa = new WTSigninAction();
								sa.heartBeat(String.valueOf(user_id) );	
								sa = null;
								// end
							} catch(Exception e) {							
							}*/								
							proOff(object, response);
							if ( "1.6".equals(Constant.PROTOCOL_VER ) )
								return null;
							
							//result = Constant.SUCCESS_CODE;													
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
	
	void proOn(JSONObject object, HttpServletResponse response) throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();
		
		WdeviceActiveInfo wa = new WdeviceActiveInfo();
		AppDeviceDiscoveryManFacade infoFacade = ServiceBean.getInstance().getAppDeviceDiscoveryManFacade();
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯
		
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		String cmd_time = object.optString("cmd_time");		
		int duration = object.optInt("duration");
		int user_id = object.optInt("user_id");


		Integer led_flag = 0;
		Integer play_flag = 0;

		if ( object.containsKey("led_flag") && object.containsKey("play_flag") ) { 
			led_flag = object.getInt("led_flag");
			play_flag = object.getInt("play_flag");
		}

		String deviceImei = null;

		if ( !Constant.cmdDirectResFlag )
			deviceImei = getDeviceImeiFromDeviceId(String.valueOf(device_id));		
		else {
			LocationInfoHelper lih = new LocationInfoHelper();
			deviceImei = getDeviceImeiFromDeviceId(String.valueOf(device_id));		
			
			boolean preCheckRes = lih.preCheckDevStatus(device_id, deviceImei);
			if ( preCheckRes )
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
			else
				json.put(Constant.RESULTCODE, Constant.ERR_DEV_IS_NOT_ONLINE);
	
			json.put("urgent_flag", 1);
			String ntime = tls.getUtcDateStrNow();
			json.put("action_time", ntime );
			json.put("cmd_time", cmd_time);
			json.put("user_id", user_id);
			json.put("duration", duration);
			json.put("device_id", device_id);
			object.put("cmd_time", ntime);
			
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(json.toString());
			CmdSosRun cpr = new CmdSosRun(AdragonConfig.setUrgentModeRes, object.toString(), true);

			if (Constant.IS_SERV_STAT_CT_URGENT) {
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				
				wdeviceActiveInfo.setSos_led_on(String.valueOf(led_flag));
				wdeviceActiveInfo.setCondition("device_id = '"+device_id+"'");
				wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);

				if (play_flag < 1)
					play_flag = 0;
					
				wdeviceActiveInfo.setCallback_on(String.valueOf(play_flag));
				System.out.println("wt hy debug callback_on" + String.valueOf(play_flag));
				wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
				
				
				proUpDevDiscovery(user_id, device_id, ntime, duration);
			}
			
			
			Thread tcpr=new Thread(cpr); 
			tcpr.start();
			return;
			
		}
	
		
		
		if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {		
			try {

				Thread lock = new Thread(); 
				CmdSync cmd = new CmdSync();
				cmd.setCmdName(AdragonConfig.setUrgentModeRes);
				cmd.setResponse(response);
				cmd.setTdLock(lock);
				cmd.setUser_id(user_id);

				CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(deviceImei, cmd);
				
				//cmdDownSetImpl.setGpsMap(deviceImei, true);
				if ( led_flag == null ) {
					if ( !cmdDownSetImpl.setUrgentMode(deviceImei, true, 
							cmd_time, duration, user_id, lock) ) {
						json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
						response.setCharacterEncoding("UTF-8");	
						response.getWriter().write(json.toString());
					}
				} else {
					if ( !cmdDownSetImpl.setUrgentMode(deviceImei, true, 
							tls.getBooleanFromInt(led_flag),
							tls.getBooleanFromInt(play_flag),
							cmd_time, duration, user_id, lock) ) {
						json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
						response.setCharacterEncoding("UTF-8");	
						response.getWriter().write(json.toString());
					}
				}
				
			} catch(Exception e) {
				
			}

		} 
		else {
			try {

				
				//cmdDownSetImpl.setGpsMap(deviceImei, true);
				cmdDownSetImpl.setUrgentMode(deviceImei, true);


				
			} catch(Exception e) {
				
			}
		}
			
				
		
		if ( "1.5".equals(Constant.PROTOCOL_VER ) ) {				
			cmdDownSetImpl.setPlayRing(deviceImei, 1, true);
			cmdDownSetImpl.setLedState(deviceImei, 4, true);
		}
		
		//需要设置下发命令的时长
		//...
		

		/*
		wa.setSel_mode("3");
		wa.setCondition(" device_id = "+device_id);
		int res = infoFacade.updateAppDeviceDiscoveryMan(wa);
		if(res < 0){
			result = Constant.FAIL_CODE;
		}else{
			result = Constant.SUCCESS_CODE;
		}
		*/
		
		if ( "1.5".equals(Constant.PROTOCOL_VER ) ) {		
		
			Thread.sleep(200);
			wa.setCondition(" a.device_id = "+device_id + " and a.urgent_mode=1");
			result  = Constant.FAIL_CODE;		
			int retryCount = 0;
			WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			while(retryCount < 15) {
				List<DataMap> list = fd.getWdeviceActiveInfo(wa);
				if( list != null && list.size() == 1 ) {
					
					//比较设备回复确认的start_time 和 how_long是否和本次设置匹配，
					//如果是，表明本次成功,否则因为类似并发导致本次操作失败，为其它
					//用户引起了设置冲突
					//.....暂时不调用
					//if (proConfirmOn(user_id, device_id, action_time, how_long))
						result = Constant.SUCCESS_CODE;
					break;
				}				
				retryCount++;
				Thread.sleep(1200);				
			}
		}
		
		wa = null;
		cmdDownSetImpl = null;
		deviceImei = null;
		
	}

	void proOff(JSONObject object, HttpServletResponse response) 
			throws SystemException, InterruptedException, IOException {
		Tools tls = new Tools();
		
		WdeviceActiveInfo wa = new WdeviceActiveInfo();
		AppDeviceDiscoveryManFacade infoFacade = ServiceBean.getInstance().getAppDeviceDiscoveryManFacade();
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		int user_id = object.optInt("user_id");
		String cmd_time = object.optString("cmd_time");		
		int duration = object.optInt("duration");

		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯
		
		String deviceImei = null;

		if ( !Constant.cmdDirectResFlag )
			deviceImei = getDeviceImeiFromDeviceId(String.valueOf(device_id));		
		else {
			deviceImei = getDeviceImeiFromDeviceId(String.valueOf(device_id));		

			LocationInfoHelper lih = new LocationInfoHelper();
			boolean preCheckRes = lih.preCheckDevStatus(device_id, deviceImei);
			if ( preCheckRes )
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
			else
				json.put(Constant.RESULTCODE, Constant.ERR_DEV_IS_NOT_ONLINE);

			json.put("urgent_flag", 0);
			json.put("action_time", tls.getUtcDateStrNow());
			json.put("cmd_time", cmd_time);
			json.put("user_id", user_id);
			json.put("duration", duration);
			json.put("device_id", device_id);
			
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(json.toString());
			
			if (Constant.IS_SERV_STAT_CT_URGENT) {
				WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				
				wdeviceActiveInfo.setSos_led_on(Tools.ZeroString);
				wdeviceActiveInfo.setCondition("device_id = '"+device_id+"'");
				wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);

				wdeviceActiveInfo.setCallback_on(Tools.ZeroString);
				wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
				
				
				proUpDevDiscovery(user_id, device_id, cmd_time, -1);
			}
			
			
			CmdSosRun cpr = new CmdSosRun(AdragonConfig.setUrgentModeRes, object.toString(), false);
			Thread tcpr=new Thread(cpr); 
			tcpr.start();			
			return;
		}
		
		
		if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {		
			try {

				Thread lock = new Thread(); 
				CmdSync cmd = new CmdSync();
				cmd.setCmdName(AdragonConfig.setUrgentModeRes);
				cmd.setResponse(response);
				cmd.setTdLock(lock);
				cmd.setUser_id(user_id);

				CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(deviceImei, cmd);
				
				//cmdDownSetImpl.setGpsMap(deviceImei, true);
				if ( !cmdDownSetImpl.setUrgentMode(deviceImei, false, 
						cmd_time, duration, user_id, lock)) {
					json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
					response.setCharacterEncoding("UTF-8");	
					response.getWriter().write(json.toString());					
				}

				

				
			} catch(Exception e) {
				
			}

		} else {		
			try {

				
				//cmdDownSetImpl.setGpsMap(deviceImei, true);
				cmdDownSetImpl.setUrgentMode(deviceImei, false);

								
			} catch(Exception e) {
				
			}
		}
		
		
		if ( "1.5".equals(Constant.PROTOCOL_VER ) ) {				
			cmdDownSetImpl.setPlayRing(deviceImei, 1, false);
			cmdDownSetImpl.setLedState(deviceImei, 4, false);
		}
		
		//wa.setSel_mode("1");
		//wa.setCondition(" device_id = "+device_id);
		//int res = infoFacade.updateAppDeviceDiscoveryMan(wa);
		//if(res < 0){
		//	result = Constant.FAIL_CODE;
		//}else{
		//	result = Constant.SUCCESS_CODE;
		//}		
		if ( !"1.6".equals(Constant.PROTOCOL_VER ) ) {						
			Thread.sleep(200);
			wa.setCondition(" a.device_id = "+device_id + " and a.urgent_mode=0");
			int retryCount = 0;
			WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			result  = Constant.FAIL_CODE;		
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
		wa = null;
		cmdDownSetImpl = null;
		deviceImei = null;
	}
	
	Boolean proConfirmOn(Integer user_id, Integer device_id, String action_time, Integer how_long) 
			throws SystemException {
//		WdeviceActiveInfo vo =  new WdeviceActiveInfo();
		WdevDiscovery vo = new WdevDiscovery();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		
		StringBuffer sb = new StringBuffer("user_id=");
		sb.append(user_id);
		sb.append(" and device_id=");
		sb.append(device_id);
		sb.append(" and date_format(action_time, \"%Y-%m-%d %T\")=");
		sb.append(action_time);
		sb.append(" and how_long=");
		sb.append(how_long);
		vo.setCondition(sb.toString());
		List<DataMap> list = fd.getDevDiscovery(vo);
		
		vo = null;
		sb = null;
		if (list!= null && list.size() > 0 )
			return true;
		else
			return false;
	}

	public void proUpDevDiscovery(Integer user_id, Integer device_id, String action_time, Integer how_long) 
			throws SystemException {
//		WdeviceActiveInfo vo =  new WdeviceActiveInfo();
		Tools tls = new Tools();			
		WdevDiscovery vo = new WdevDiscovery();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		
		vo.setUser_id(user_id);
		vo.setDevice_id(device_id);
		vo.setAction_time(action_time);
		if (Constant.cmdDirectResFlag) {								
			vo.setAction_time_utc(action_time );
		} else {
			vo.setAction_time_utc(tls.timeConvert( action_time, getDeviceTimeZone(String.valueOf(device_id)), "UTC") );			
		}
		
		
		vo.setHow_long(how_long);
		
		StringBuffer sb = new StringBuffer("");
		sb.append("device_id=");
		sb.append(device_id);
		vo.setCondition(sb.toString());
		fd.updateDevDiscovery(vo);
			
		
	}
	
	
	
}
