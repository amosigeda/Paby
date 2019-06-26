package com.wtwd.sys.appinterfaces.innerw;

//wappusers yonghu
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
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
import com.wtwd.sys.saleinfo.domain.SaleInfo;
import com.wtwd.utils.IPSeeker;

public class WTSignupAction extends BaseAction {
	Log logger = LogFactory.getLog(WTSignupAction.class);
	Integer dfg = 0;

	@SuppressWarnings("finally")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = null;	//new Date();
		//start = new Date();
	    if ( Constant.timeUtcFlag )		
	    	start = tls.getUtcDateStrNowDate();
		else
			start = new Date();			    
		
		JSONObject json = new JSONObject();
		ServiceBean mIntances = ServiceBean.getInstance();
		try {
			WappUsersFacade mWappUsersFacade = mIntances
					.getWappUsersFacade();
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}		
			
			JSONObject object = JSONObject.fromObject(sb.toString());

			dfg = object.optInt("at");
			
			String email = object.getString("user_name");
			if (email.contains("@")) {
				
				String belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
				String type = object.has("type") ? object.getString("type") : "1";// 0验证用户名或者邮箱是否存在，1注册
				WappUsers userInfo = new WappUsers();
				if(belongProject.equals("0")){  //动态项目获取
					userInfo.setCondition("email='" + email +"'");
				}else{
					userInfo.setCondition("email='" + email
							+ "' and belong_project ='" + belongProject + "'");
				}
				List<DataMap> list = mWappUsersFacade.getWappUsers(userInfo);				

				if (type.equals("0")) {		//申请验证码
					if (list.size() > 0 && !ifUniverseTest(email)) {
						result = Constant.ERR_APPUSER_HAS_EXIST;

					} else {
						result = proGetVerifyCode(email,belongProject); 
						//result = Constant.FAIL_CODE;						
					}

				} else if (type.equals("1")) {		//用户注册

					if ( ifUniverseTest(email) && list.size() > 0  ) {
						mWappUsersFacade.deleteWappUsers(userInfo);
					}
					
					if (list.size() == 0 || ifUniverseTest(email)) {
						String verify_code = object.getString("verify_code");
						WTFindPasswordAction wtFpa = new WTFindPasswordAction();
						result = wtFpa.ProVerifyForget(email, belongProject, verify_code, 0);
						if ( result == Constant.SUCCESS_CODE ) {
							
							logger.info(email);
							String user_password = object.getString("user_password");
							int ts = object.optInt("ts");
							
							String user_imei = getFieldValueFromJSONSafe("user_imei", object);
		//						String user_sdk = object.getString("user_sdk");
							String user_imsi = getFieldValueFromJSONSafe("user_imsi", object);
							String user_phone = getFieldValueFromJSONSafe("user_phone", object);	//object.getString("user_phone");
							String phone_version = getFieldValueFromJSONSafe("phone_version", object);	//object.getString("phone_version");  
							String phone_model = getFieldValueFromJSONSafe("phone_model", object);	//object.getString("phone_model");
							String app_version = getFieldValueFromJSONSafe("app_version", object);	//object.getString("app_version");
							String device_token = object.optString("device_token");
							String time_zone = object.optString("time_zone");
							
		
							userInfo.setUser_photo("");
							userInfo.setUser_born("2015-12-12");
							if ( ts > 0)
								userInfo.setPassmd(user_password);
							else
								userInfo.setUser_password(user_password);

							userInfo.setDevice_token(device_token);
							userInfo.setTime_zone(time_zone);
							userInfo.setDfg(dfg);
							//String cur_time = Tools.getStringFromDate(new Date());
						    String cur_time = null;
						    
							if ( Constant.timeUtcFlag )		
								cur_time = tls.getUtcDateStrNow();
							else
								cur_time = tls.getStringFromDate(new Date());		    
							
							
							userInfo.setCreate_time(cur_time);
							userInfo.setLastlogin_time(cur_time);
							userInfo.setUser_sex("0"); 
							userInfo.setStatus("1");
							userInfo.setUser_name(email);
							userInfo.setBelong_project(belongProject);    //动态项目时,为0
							userInfo.setBind_count("0");
							
							String app_token = getVerifyCode();
							userInfo.setApp_token(app_token);
							//String login_time = Tools.getStringFromDate(new Date());
							String login_time = null;
							if ( Constant.timeUtcFlag )		
								login_time = tls.getUtcDateStrNow();
							else
								login_time = tls.getStringFromDate(new Date());		    
							
							userInfo.setLastlogin_time(login_time);
							
							mWappUsersFacade.insertWappUsers(userInfo);
							
							userInfo.setCondition("email='" + email + "'");
							
							List<DataMap> mList = mWappUsersFacade.getWappUsers(userInfo);
							String user_id = ""+mList.get(0).getAt("user_id");
							json.put("user_id", user_id);
							json.put("app_token", mList.get(0).getAt("app_token"));
							json.put("user_born",  tls.getUtilDateFromSqlDate(mList.get(0).getAt("born_date"))  ) ;
							json.put("user_nick", mList.get(0).getAt("nickname"));
							json.put("user_photo", mList.get(0).getAt("photo"));
							json.put("user_sex", mList.get(0).getAt("sex"));
							json.put("user_id", user_id);
							json.put("b_g", ""+mList.get(0).getAt("belong_project"));
							SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							json.put("login_time",login_time);
							json.put("act_device_id", mList.get(0).getAt("act_device_id"));
							json.put("firstname", mList.get(0).getAt("firstname"));
							json.put("lastname", mList.get(0).getAt("lastname"));
							json.put("signtext", mList.get(0).getAt("signtext"));
							json.put("photo_time_stamp", mList.get(0).getAt("photo_time_stamp"));
//							json.put("userImg_baseDir", Constant.WEB_USERIMG_BASEDIR);
//							json.put("petImg_baseDir", Constant.WEB_PETIMG_BASEDIR);
//							json.put("firm_baseDir", Constant.WEB_FIRM_BASEDIR);
//							json.put("mqtt_srvUrl", Constant.MQTT_SRV_URL);
							json.put("mqtt_username", Constant.MQTT_SRV_USERNAME);
							json.put("mqtt_password", Constant.MQTT_SRV_PASSWORD);							
							String serv_ip = getPropFromFile("server.properties", "servername");
							json.put("userImg_baseDir", "http://" + serv_ip + Constant.WEB_USERIMG_BASEDIR);
							json.put("petImg_baseDir", "http://" + serv_ip + Constant.WEB_PETIMG_BASEDIR);
							json.put("firm_baseDir", "http://" + serv_ip + Constant.WEB_FIRM_BASEDIR);
							json.put("mqtt_srvUrl", "http://" + serv_ip + Constant.MQTT_SRV_URL);
							json.put("petKind_baseDir", "http://" + serv_ip + Constant.WEB_PETKIND_BASEDIR);
							json.put("petef_baseDir", "http://" + serv_ip + Constant.EF_PET_BASEDIR);
							json.put("udu", Tools.ZeroString);							

							String udu = mList.get(0).getAt("udu").toString();
							if( !Tools.OneString.equals(udu) )
								udu = Tools.ZeroString;

							json.put("udu", udu);							
							
							String dir = request.getSession(true).getServletContext().getRealPath("/qqrwy"); //找到文件夹
							String fileName = request.getSession(true).getServletContext().getRealPath("QQWry.Dat"); //找到文件的名字
					 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
							String ip = request.getRemoteAddr();
							String province = ipSeeker.getIPLocation(ip).getCountry();
							 
							SaleInfo saleInfo = new SaleInfo();
							saleInfo.setUserName(email);
							saleInfo.setImei(user_imei);
							saleInfo.setImsi(user_imsi);
							saleInfo.setPhone(user_phone);
							saleInfo.setPhoneModel(phone_model);
							saleInfo.setSysVersion(phone_version);
							//saleInfo.setDateTime(new Date());
							if ( Constant.timeUtcFlag )		
								saleInfo.setDateTime( tls.getUtcDateStrNowDate() ); 
							else
								saleInfo.setDateTime(new Date());		    
							
							saleInfo.setIp(ip);
							saleInfo.setProvince(province);
							saleInfo.setBelongProject(belongProject);
							saleInfo.setAppVersion(app_version);
							
							ServiceBean.getInstance().getSaleInfoFacade().insertSaleInfo(saleInfo);
							
							//insertVisit(href,belongProject,user_id,0,start,new Date());
							if ( Constant.timeUtcFlag )		
								insertVisit(href,belongProject,user_id,0,start,tls.getUtcDateStrNowDate() ); 
							else
								insertVisit(href,belongProject,user_id,0,start,new Date());		    
							
							result = Constant.SUCCESS_CODE;

							//WTSigninAction wtSia = new WTSigninAction();
							//result = Constant.SUCCESS_CODE;
							//StringBuffer sbAuto = new StringBuffer();							
							//sb.append("{\"user_name\":\"" + email + "\", ");
							//sb.append("\"user_password\":\"" + user_password + "\"}");
							
							//return wtSia.execute(mapping, form, request, sbAuto.toString().trim(),response);
			
						}
					} else {
						result = Constant.ERR_APPUSER_HAS_EXIST;						
					}
					
				}
			
			}
		} catch (Exception e) {
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
			
		}  finally {
			json.put("request", href);
			json.put(Constant.RESULTCODE, result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
			return null;
		}
		
	
	}

	
	boolean ifUniverseTest(String email) {		//无限次注册用户
		if("2259171993@qq.com".equals(email)) {
			return true;
		} else if ("tchaoren@waterworld.com.cn".equals(email)) {
			return true;
		} else if ("461261532@qq.com".equals(email)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	int proGetVerifyCode(String email, String belongProject) throws MessagingException, Exception {
		WTFindPasswordAction wtFpa = new WTFindPasswordAction();		
		wtFpa.setDfg(dfg);
		wtFpa.ProForgetPassword(email,belongProject);
		return Constant.SUCCESS_CODE;
	}
	
}