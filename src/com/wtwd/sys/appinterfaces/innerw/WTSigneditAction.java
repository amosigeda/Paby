//yonghu create 20160625 label

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

import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.google.gson.Gson;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;


public class WTSigneditAction extends BaseAction{

	
	Log logger = LogFactory.getLog(WTSigneditAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";
	int ts = 0;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}			

			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_id = object.getString("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTSigneditAction");			
			
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if (verifyAppToken(user_id, app_token) != Constant.SUCCESS_CODE )
				result = Constant.ERR_INVALID_TOKEN;
			else {
				String type = tls.getSafeStringFromJson(object, "type"); 
				
				if ( "1".equals(type) ) {
					String old_pwd = tls.getSafeStringFromJson(object, "old_pwd");
					String new_pwd = tls.getSafeStringFromJson(object, "new_pwd");
					ts = object.optInt("ts");
					
					
					proUpPwd(user_id, old_pwd, new_pwd);
				}
				else
					proUpdate(sb.toString());
	
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

	void proUpdate(String jsonStr) throws SystemException {
		Gson gson=new Gson();
	    WappUsers reqUser = gson.fromJson(jsonStr,WappUsers.class);
	    reqUser.setUser_password(null);
	    reqUser.setAct_device_id(null);
	
		WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
		
		reqUser.setCondition("user_id ="+reqUser.getUser_id());
	
		Integer resUpdate = facade.updateWappUsers(reqUser);
		
		if(resUpdate == 1){
			result = Constant.SUCCESS_CODE;				
		}else{				
			result = Constant.FAIL_CODE;				
		}

	}

	void proUpPwd(String user_id, String old_pwd, String new_pwd) throws SystemException {
	    WappUsers reqUser = new WappUsers();
		WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
		
		if (ts > 0)
			reqUser.setCondition("user_id ="+ user_id + " and passmd='" + old_pwd + "'");
		else
			reqUser.setCondition("user_id ="+ user_id + " and passwd='" + old_pwd + "'");
		result = Constant.FAIL_CODE;				

		if ( facade.getWappUsersCount(reqUser) > 0 ) {
		
			if (ts > 0)
				reqUser.setCondition("user_id ="+ user_id + " and passmd='" + old_pwd + "'");
			else
				reqUser.setCondition("user_id ="+ user_id + " and passwd='" + old_pwd + "'");
				
			if (ts > 0)
				reqUser.setPassmd(new_pwd);
			else
				reqUser.setUser_password(new_pwd);

			Integer resUpdate = facade.updateWappUsers(reqUser);
			if(resUpdate == 1){
				result = Constant.SUCCESS_CODE;				
			}
		}
		
	}
	
	
	
	
	
}