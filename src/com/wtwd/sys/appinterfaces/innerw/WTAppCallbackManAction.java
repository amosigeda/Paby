package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTDevSetaFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;


public class WTAppCallbackManAction extends BaseAction {
	
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

			super.logAction(String.valueOf(user_id), object.optInt("device_id"), "appCallbackManAction");		
			
			int device_id = object.getInt("device_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if ( ( result = verifyUserDevice(String.valueOf(user_id), 
							String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {					

						/*
						try { 
							WTSigninAction  sa = new WTSigninAction();
							sa.heartBeat(String.valueOf(user_id));	
							sa = null;
							// end
						} catch(Exception e) {							
						}
						*/								
						
						if (cmd.equals("on")) {	//开启唤回声音
							//result = Constant.SUCCESS_CODE;
							
							/*
							Distribution a = new Distribution();
							Distribution b = new Distribution();
							a.setLongitude(116.493381);
							a.setDimensionality(39.978437270);
							b.setLongitude(116.493441);
							b.setDimensionality(39.978887);
							json.put("distance1", String.valueOf(a.getDistance(a, b) ));
							String distanceGaode = Distribution.distanceGaode(Constant.KEY_1, "116.493381,39.978437270", "116.493441,39.978887" );
							json.put("distanceGaode", distanceGaode);
							*/
							
							result = Constant.SUCCESS_CODE;																				
							proControl(object, 1, response, user_id);
							if ( "1.6".equals(Constant.PROTOCOL_VER ) )
								return null;
							
						} else if ( cmd.equals("off")) {   //关闭唤回声音
							proControl(object, 0, response, user_id);

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

	//para:status 0: off, 1: on
	void proControl(JSONObject object, int status, 
			HttpServletResponse response, Integer user_id) {
		try {
			int device_id = object.getInt("device_id");
			String imeiStr = getDeviceImeiFromDeviceId(String.valueOf(device_id) );
			
			if ( !"1.6".equals(Constant.PROTOCOL_VER ) ) {
			
				WdeviceActiveInfo wa = new WdeviceActiveInfo();
				wa.setCondition(" device_id = "+device_id);
		
				if ( status == 1 )
					wa.setCallback_on("1");
				else
					wa.setCallback_on("0");
		
					WTDevSetaFacade devFacade = ServiceBean.getInstance().getWtDevSetaFacade();
				int res = devFacade.updateDevSeta(wa);
				if(res > 0){
					result = Constant.SUCCESS_CODE;
				}else{
					result = Constant.FAIL_CODE;
				}
			} else {
				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
	
				Thread lock = new Thread(); 
				CmdSync cmd = new CmdSync();
				cmd.setCmdName(AdragonConfig.setPlayRingRes);
				cmd.setResponse(response);
				cmd.setTdLock(lock);
				cmd.setUser_id(user_id);
				
				CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(imeiStr, cmd);
				
				if ( status == 1 )
					cmdDownSetImpl.setPlayRing(imeiStr, 1, true, user_id);
				else
					cmdDownSetImpl.setPlayRing(imeiStr, 1, false, user_id);
				
				
				if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
					synchronized(lock){
						lock.wait(1000* 60 * 2);
					    //或者wait()
					}
				}

				
			}
		} catch ( Exception e) {
			
		}
		
	}


}
