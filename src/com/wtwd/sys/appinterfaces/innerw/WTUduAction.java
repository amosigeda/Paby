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
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;


public class WTUduAction extends BaseAction {


	
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
			int user_id = object.getInt("user_id");
			
			int udu = object.getInt("udu");

			logger.info("set udu user_id:" + String.valueOf(user_id) + ",udu:" + udu  );

			WappUsers vo = new WappUsers();
			WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
			
			vo.setCondition("user_id=" + user_id );
			vo.setUdu(String.valueOf(udu));

			facade.updateWappUsers(vo);
			
			
		} catch (Exception e) {
			logger.info("WTUduAction proSet occurred exception!");
			result = Constant.FAIL_CODE;				
		}

		
	}
	
	

}
