package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
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
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.userlogininfo.domain.UserLoginInfo;
import com.wtwd.sys.userlogininfo.domain.logic.UserLoginInfoFacade;

//20160625 label

public class WTSignquitAction extends BaseAction {
	
	Log logger = LogFactory.getLog(WTSignquitAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
					
			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			Integer user_id = object.getInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTSignquitAction");			
			
			
			//String belongProject = ( Tools.getSafeStringFromJson(object, "belong_project").equals("") ) ? "1" : Tools.getSafeStringFromJson(object, "belong_project") ; 	
			
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if (app_token.isEmpty() || verifyAppToken(String.valueOf(user_id), app_token) != Constant.SUCCESS_CODE )
				result = Constant.ERR_INVALID_TOKEN;
			else {

				WappUsers vo = new WappUsers();
				WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
				UserLoginInfoFacade uliFacade = ServiceBean.getInstance().getUserLoginInfoFacade();
				UserLoginInfo uliVo = new UserLoginInfo();
				
				vo.setCondition("user_id ="+user_id);
				List<DataMap> list = facade.getWappUsers(vo);
				
				if(list.size() > 0){
					list.clear();
					facade.clearToken(vo);
					uliVo.setCondition("user_id =" + user_id );
					if ( uliFacade.delUserLoginInfo(uliVo) > 0) {

						WappUsers vo1 = new WappUsers();
						WappUsersFacade fd1 = ServiceBean.getInstance().getWappUsersFacade();
						
						vo1.setLogin_status("1");
						String login_time = null;
						if ( Constant.timeUtcFlag )
							login_time = tls.getUtcDateStrNow();					
						else
							login_time = tls.getStringFromDate(new Date());
						
						vo1.setLastlogin_time(login_time);
						vo1.setLogin_status(Tools.ZeroString);					
						
						fd1.updateWappUsers(vo1);
						
						
						result = Constant.SUCCESS_CODE;					
					}else{
						result = Constant.FAIL_CODE;
					}
	
				}else{				
					result = Constant.ERR_APPUSER_NOT_EXIST;				
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
