package com.wtwd.sys.appinterfaces.innerw;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.wtwd.utils.IPSeeker;


public class WQuerySugMove extends BaseAction {
	
	Log logger = LogFactory.getLog(WQuerySugMove.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		try{
			/*ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}*/
			
			String[] jstrarr =   (String[]) request.getParameterMap().get("jsonObject");
			JSONArray jsonarr = JSONArray.fromObject(jstrarr);
			
			JSONObject object = jsonarr.getJSONObject(0);
			String user_name = object.getString("user_name");
			String phoneMac = tls.getSafeStringFromJson(object, "phone_mac"); 
			String user_password = tls.getSafeStringFromJson(object, "user_password");	
			String belongProject = ( tls.getSafeStringFromJson(object, "belong_project").equals("") ) ? "1" : tls.getSafeStringFromJson(object, "belong_project") ; 	
			String imei = tls.getSafeStringFromJson(object, "user_imei");		
			String imsi = tls.getSafeStringFromJson(object, "user_imsi");		
			String phoneModel = tls.getSafeStringFromJson(object, "phone_model");		
			String phoneVersion = tls.getSafeStringFromJson(object, "phone_version");		
			String appVersion = tls.getSafeStringFromJson(object, "app_version");		
			
			WappUsers vo = new WappUsers();
			WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
			if(belongProject.equals("0")){
				vo.setCondition("email ='"+user_name+"'");
			}else{
				vo.setCondition("email ='"+user_name+"' and belong_project ='"+belongProject+"'");
			}			
			List<DataMap> list = facade.getWappUsers(vo);
			
			if(list.size() > 0){
				list.clear();
				vo.setCondition("email ='"+user_name+"' and passwd ='"+user_password+"' and belong_project ='"+belongProject+"'");
				list = facade.getWappUsers(vo);
				
				if(list.size() > 0){
					result = Constant.SUCCESS_CODE;
					String user_id = "" + list.get(0).getAt("user_id");
					vo.setCondition("user_id='"+user_id+"'");
					WappUsers ai=new WappUsers();
					ai.setCondition("user_id='"+user_id+"'");
					//String shijianchuo=System.currentTimeMillis()+"";
					String login_time = tls.getStringFromDate(new Date());
					ai.setLastlogin_time(login_time);
					ai.setPhone_mac(phoneMac);
					facade.updateWappUsers(ai);
					
					//DataMap dm = list.get(0);
					 //java.sql.Date dt = (java.sql.Date) dm.getAt("born_date");
					json.put("user_born",  tls.getUtilDateFromSqlDate(list.get(0).getAt("born_date"))  ) ;
					json.put("user_nick", list.get(0).getAt("nickname"));
					json.put("user_photo", list.get(0).getAt("photo"));
					json.put("user_sex", list.get(0).getAt("sex"));
					json.put("user_id", user_id);
					json.put("user_name", user_name);
					json.put("user_password", user_password);
					json.put("b_g", ""+list.get(0).getAt("belong_project"));
					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					json.put("login_time",login_time);
					
					

					
					insertVisit(href,belongProject,user_id,0,start,new Date());

				
					String dir = request.getSession(true).getServletContext().getRealPath("/qqrwy"); //找到文件夹
					String fileName = request.getSession(true).getServletContext().getRealPath("QQWry.Dat"); //找到文件的名字
			 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
					String ip = request.getRemoteAddr();
					String province = ipSeeker.getIPLocation(ip).getCountry();
					
					UserLoginInfo userLogin = new UserLoginInfo();
					userLogin.setUserId(Integer.parseInt(user_id));
					userLogin.setLoginTime(new Date());
					userLogin.setImei(imei);
					userLogin.setImsi(imsi);
					userLogin.setPhoneModel(phoneModel);
					userLogin.setPhoneVersion(phoneVersion);
					userLogin.setAppVersion(appVersion);
					userLogin.setIp(ip);
					userLogin.setProvince(province);
					userLogin.setBelongProject(belongProject);
					ServiceBean.getInstance().getUserLoginInfoFacade().insertUserLoginInfo(userLogin);
					
					
	              				
					
				}else{
					result = Constant.FAIL_CODE;
				}

			}else{				
				result = Constant.ERR_APPUSER_NOT_EXIST;				
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

