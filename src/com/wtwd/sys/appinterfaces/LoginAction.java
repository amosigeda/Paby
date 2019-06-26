package com.wtwd.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
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
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.userlogininfo.domain.UserLoginInfo;
import com.wtwd.utils.IPSeeker;

public class LoginAction extends BaseAction{
	
	Log logger = LogFactory.getLog(LoginAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
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
			String user_name = object.getString("user_name");
			String phoneMac = object.getString("phone_mac");
			String user_password = object.getString("user_password");
			String belongProject = object.has("belong_project")?object.getString("belong_project"):"0";
			String imei = object.has("user_imei") ? object.getString("user_imei") : "0";
			String imsi = object.has("user_imsi") ? object.getString("user_imsi") : "0";
			String phoneModel = object.has("phone_model") ? object.getString("phone_model") : "0";
			String phoneVersion = object.has("phone_version") ? object.getString("phone_version") : "0";
			String appVersion = object.has("app_version") ? object.getString("app_version") : "0";
			
			AppUserInfo vo = new AppUserInfo();
			AppUserInfoFacade facade = ServiceBean.getInstance().getAppUserInfoFacade();
//			vo.setUserName(user_name);
//			vo.setPassword(user_password);
			if(belongProject.equals("0")){
				vo.setCondition("user_name ='"+user_name+"'");
			}else{
				vo.setCondition("user_name ='"+user_name+"' and belong_project ='"+belongProject+"'");
			}			
			List<DataMap> list = facade.getAppUserInfo(vo);
			
			if(list.size() > 0){
				//String loginStatus=list.get(0).getAt("login_status")+"";
				//if(loginStatus.equals("0")){
				list.clear();
				vo.setCondition("user_name ='"+user_name+"' and password ='"+user_password+"' and belong_project ='"+belongProject+"'");
				list = facade.getAppUserInfo(vo);//����û����Ƿ�ע��
				
				if(list.size() > 0){
					result = Constant.SUCCESS_CODE;
					String user_id = "" + list.get(0).getAt("id");
					vo.setCondition("id='"+user_id+"'");
					AppUserInfo ai=new AppUserInfo();
					ai.setCondition("id='"+user_id+"'");
					String shijianchuo=System.currentTimeMillis()+"";
					ai.setLoginTime(shijianchuo);
					ai.setPhoneMac(phoneMac);
					facade.updateAppUserInfo(ai);
					
					json.put("user_height", list.get(0).getAt("height"));
					json.put("user_weight", list.get(0).getAt("weight"));
					json.put("user_age", list.get(0).getAt("age"));
					json.put("user_nick", list.get(0).getAt("nick_name"));
					json.put("user_head", list.get(0).getAt("head"));
					json.put("user_sex", list.get(0).getAt("sex"));
					json.put("user_id", user_id);
					json.put("user_name", user_name);
					json.put("user_password", user_password);
					json.put("b_g", ""+list.get(0).getAt("belong_project"));
					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					json.put("login_time",shijianchuo);
					
					
					
					vo.setAge((String)list.get(0).getAt("age"));
					vo.setUserName((String)list.get(0).getAt("user_name"));
					vo.setId((Integer)list.get(0).getAt("id"));
					vo.setUserHeight(String.valueOf(list.get(0).getAt("height")));
					vo.setHead((String)list.get(0).getAt("head"));
					vo.setBelongProject(belongProject);
					
					insertVisit(href,belongProject,user_id,0,start,new Date());
//					new UserHandler().Login(vo, 1, null, 1);
//					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
//					deviceActiveInfo.setCondition("user_id ='"+user_id+"'");
//					List<DataMap> deviceList = deviceFacade.getDeviceActiveInfo(deviceActiveInfo);
//					if(deviceList.size() > 0){
//						String deviceImei = "" + deviceList.get(0).getAt("device_imei");
//						json.put("device_imei", deviceImei);
//					}
				
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
				
				/*vo.setLoginStatus("1");
				vo.setCondition("id='"+user_id+"'");
				int i=facade.updateAppUserInfo(vo);
				if(i>0){
					list.clear();
					vo.setCondition("id='"+user_id+"'");
					list=facade.getAppUserInfo(vo);
					if(list.size()>0){
						json.put("login_status", ""+list.get(0).getAt("login_status"));
					}else{
						logger.info("没获取到");
					}
				}else{
					logger.info("修改失败");
				}*/
              				
				
				}else{
					result = Constant.FAIL_CODE;
				}
			/*}else{
				//登陆失败
				result=3;
			}*/
				}else{
				result = 2;
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
