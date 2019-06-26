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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;


public class WTAppUserGpsManAction extends BaseAction {
	
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

			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppUserGpsManAction");			
			
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if (cmd.equals("upload")) {	//APP用户上传位置数据
						result = Constant.SUCCESS_CODE;													
					} else {
						result = Constant.ERR_INVALID_PARA;													
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
}
