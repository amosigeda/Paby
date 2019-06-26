package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;


public class WTDevLCTOptAction extends BaseAction {


	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTDevLCTOptAction.class);

	
	
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
			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");			
			String imei = object.getString("imei");

			
			if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
				if ( cmd.equals("set") ) { //添加设备
					proSet(object);
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
	
	
	public void proSet(JSONObject object)  {
		try {
			Tools tls = new Tools();
			String imei = object.getString("imei");
			int user_id = object.getInt("user_id");
			
			int uLFq = object.getInt("uLFq");
			int uLFqMode = object.getInt("uLFqMode");
			int uLSosWifi = object.getInt("uLSosWifi");

			logger.info("WTDevLCTOptAction user_id:" + String.valueOf(user_id) + ",imei:" + imei + ",WTDevLCTOptAction" + 
				"uLFq:" + uLFq + ",uLFqMode:" + uLFqMode +",uLSosWifi:" + uLSosWifi );
			
			
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
	
			IoSession sn = cmdDownSetImpl.getCmdDeviceIoSession(imei);
			
			if ( sn == null ) {
				JSONObject jo =  new JSONObject();
				jo.put("beatTim", 5);
	
				JSONObject jo1 =  new JSONObject();
				jo1.put("setBeattim", jo.toString());
				jo1.put("uLFq", uLFq);
				jo1.put("uLFqMode", uLFqMode);
				jo1.put("uLSosWifi", uLSosWifi);
				String lstr = jo1.toString();
				logger.info(lstr);
				
				result = Constant.FAIL_CODE;
			}
			else {
				JSONObject jo =  new JSONObject();
				jo.put("beatTim", 5);
	
				JSONObject jo1 =  new JSONObject();
				jo1.put("setBeattim", jo.toString());
				jo1.put("uLFq", uLFq);
				jo1.put("uLFqMode", uLFqMode);
				jo1.put("uLSosWifi", uLSosWifi);
									
				sn.write(jo1.toString());
				
				result = Constant.SUCCESS_CODE;	
			}
			cmdDownSetImpl = null;
		} catch (Exception e) {
			logger.info("WTDevLCTOptAction proSet occurred exception!");
			result = Constant.FAIL_CODE;				
		}

		
	}
	
	

}
