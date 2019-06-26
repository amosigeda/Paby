package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.google.gson.Gson;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.http.EndServlet;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;
import com.wtwd.sys.userlogininfo.domain.UserLoginInfo;
import com.wtwd.utils.IPSeeker;


public class WTSigninAction extends BaseAction {
	
	private JSONObject json = null;
	private List<DataMap> mList = null;
	public final static String FACEBOOK_USER_TYPE = "2";	//无效参数
	public final static String FACEBOOK_CMD_TYPE = "1";	//无效参数
	public final static String APP_HEART_BEAT_TYPE = "3";	//app心跳参数
	public final static String APP_QUIT_TYPE = "2";	//app心跳参数

	public final static String APP_QUIT_APP = "5";	//退出APP
	public final static String APP_ENTER_APP = "4";	//第一次进入APP
	public final static String APP_CONTEST_APP = "6";	//第一次进入APP

	
	
	
	Log logger = LogFactory.getLog(WTSigninAction.class);	
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	private void proToken(String user_id)
			throws MessagingException, SystemException {
		Random random = new Random();
		StringBuffer randNumber = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			String rand = String.valueOf(random.nextInt(10));
			randNumber.append(rand);
		}

		WappUsers vo = new WappUsers();
		WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + user_id );
		vo.setApp_token(randNumber.toString());
		json.put(Constant.APP_TOKEN, randNumber.toString());
		facade.updateWappUsers(vo);

	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, String jsonStr,HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();		
		
		String href= request.getServletPath();
		Date start = null;	
		//start = new Date();
		if ( Constant.timeUtcFlag )		
			start =  tls.getUtcDateStrNowDate();
		else
			start = new Date();
		
		json = new JSONObject();
		try{
			JSONObject object = JSONObject.fromObject(jsonStr);
			
			logger.info("@@@ <<<"+ jsonStr);
			
			
			/*
			 * 
			 * APNS SAMPLE 
			String p12_file_path = request.getSession(true).getServletContext().getRealPath("/push/") + "pushDevelop.p12";

			String iosPwd = getPropFromFile("server.properties", "iosPushPwd");
			ApnsService service = APNS.newService()
					.withCert(p12_file_path, iosPwd)
					.withSandboxDestination()
					.build();
					
					String payload = APNS.newPayload().alertBody("wtwd REAL server simple message").build();
					String token = "1822cddbfe2ecdcb3f6e46b6d3e0db9a06adca16fefcc9cf792602e11904e4f8";
					service.push(token, payload);			
			*/
			String user_name = object.getString("user_name");
			String phoneMac = tls.getSafeStringFromJson(object, "phone_mac"); 
			String user_password = tls.getSafeStringFromJson(object, "user_password");
			int ts = object.optInt("ts");
			String belongProject = ( tls.getSafeStringFromJson(object, "belong_project").equals("") ) ? "1" : tls.getSafeStringFromJson(object, "belong_project") ; 	
			String imei = tls.getSafeStringFromJson(object, "user_imei");		
			String imsi = tls.getSafeStringFromJson(object, "user_imsi");		
			String phoneModel = tls.getSafeStringFromJson(object, "phone_model");		
			String phoneVersion = tls.getSafeStringFromJson(object, "phone_version");		
			String appVersion = tls.getSafeStringFromJson(object, "app_version");	
			String device_token = object.optString("device_token").trim(); 
			String ios_token = object.optString("ios_token").trim(); 			
			String ios_real = object.optString("ios_real").trim(); 			

			String time_zone = object.optString("time_zone").trim();
			Integer dfg = object.optInt("at");
			
			
			
			
			WappUsers vo = new WappUsers();
			WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
			if(belongProject.equals("0")){
				vo.setCondition("email ='"+user_name+"'");
			}else{
				vo.setCondition("email ='"+user_name+"' and belong_project ='"+belongProject+"'");
			}			
			mList = facade.getWappUsers(vo);
			
			if(mList.size() > 0){
				mList.clear();
				if (ts > 0)
					vo.setCondition("email ='"+user_name+"' and passmd ='"+user_password+"' and belong_project ='"+belongProject+"'");					
				else
					vo.setCondition("email ='"+user_name+"' and passwd ='"+user_password+"' and belong_project ='"+belongProject+"'");
				mList = facade.getWappUsers(vo);
				
				if(mList.size() > 0){
					result = Constant.SUCCESS_CODE;
					String user_id = "" + mList.get(0).getAt("user_id");
					proToken(user_id);
					vo.setCondition("user_id='"+user_id+"'");
					WappUsers ai=new WappUsers();
					ai.setDfg(dfg);
					if ( tls.isNullOrEmpty(device_token) ) {
						ai.setDevice_token(" ");
						ai.setIos_token(ios_token);
						ai.setIos_real(ios_real);
						ai.setExt1("1");		//ios用户
					} else {
						ai.setDevice_token(device_token);
						ai.setIos_token("");
						ai.setIos_real("");
						ai.setExt1("0");		//android用户		
					}
					ai.setTime_zone(time_zone);
					ai.setApp_version(appVersion);
					ai.setCondition("user_id='"+user_id+"'");
					//String shijianchuo=System.currentTimeMillis()+"";
					//String login_time = Tools.getStringFromDate(new Date());
					String login_time = null;
					if ( Constant.timeUtcFlag )		
						login_time =  tls.getUtcDateStrNow();
					else
						login_time = tls.getStringFromDate(new Date());
					
					
					ai.setLastlogin_time(login_time);
					ai.setPhone_mac(phoneMac);

					ai.setLogin_status(Tools.OneString);					
					//ai.setStatus("1");		//默认用户是不禁用的
					facade.updateWappUsers(ai);
					
					//取消用户改了时区相应更改设备时区
					//proRelDevTimeZone(user_id, time_zone);

					//DataMap dm = list.get(0);
					 //java.sql.Date dt = (java.sql.Date) dm.getAt("born_date");
					setUserProps(user_id, login_time);

					
					//insertVisit(href,belongProject,user_id,0,start,new Date());
					if ( Constant.timeUtcFlag )		
						insertVisit(href,belongProject,user_id,0,start,tls.getUtcDateStrNowDate() );
					else
						insertVisit(href,belongProject,user_id,0,start,new Date());
					
				
					String dir = request.getSession(true).getServletContext().getRealPath("/qqrwy"); //找到文件夹
					String fileName = request.getSession(true).getServletContext().getRealPath("QQWry.Dat"); //找到文件的名字
			 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
					String ip = request.getRemoteAddr();
					String province = ipSeeker.getIPLocation(ip).getCountry();
					
					UserLoginInfo userLogin = new UserLoginInfo();
					userLogin.setUserId(Integer.parseInt(user_id));
					//userLogin.setLoginTime(new Date());
					if ( Constant.timeUtcFlag )		
						userLogin.setLoginTime( tls.getUtcDateStrNowDate() );
					else
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
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}

			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			logger.info("@@@ <<<"+ sb.toString());
			
			//super.logAction(object.optString("user_id"), object.optInt("device_id"), "WTSigninAction:" + sb.toString());			
			
			
			String type = object.optString("type").trim();
			
			//String mp = object.optString("mp");
			proHostMp(sb.toString());
			
			if (FACEBOOK_CMD_TYPE.equals(type))
				return executeFacebook(mapping, form,request,  sb.toString(), response );
			else if (APP_ENTER_APP.equals(type)) {
				return proHeartBeat(sb.toString(), response);
				
			} else if (APP_QUIT_APP.equals(type))
				return proAppQuit(sb.toString(), response);
			else if ( APP_HEART_BEAT_TYPE.equals(type) ) {
				//return proAppQuit(sb.toString(), response);
				return proHeartBeatType(sb.toString(), response);
			} else if ( APP_QUIT_TYPE.equals(type) ) {
				//return proAppQuit(sb.toString(), response);
				return proHeartBeatType(sb.toString(), response);
			} else if ( APP_CONTEST_APP.equals(type) ) {
				//ConTest.conTest();
				result = Constant.SUCCESS_CODE;
				String user_id = object.optString("user_id").trim();
				String app_token = object.optString("app_token").trim();				
				result = verifyAppToken(user_id, app_token);
			} else
				return execute(mapping, form,request,  sb.toString(), response );
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
	
	
	public ActionForward executeFacebook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, String jsonStr,HttpServletResponse response)
			throws Exception {
		String href= request.getServletPath();
		json = new JSONObject();
		Tools tls = new Tools();		

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			String user_name = object.optString("user_name").trim();
			String user_password = object.optString("user_password").trim();	
			int ts = object.optInt("ts");
			
			String belongProject = ( tls.getSafeStringFromJson(object, "belong_project").equals("") ) ? "1" : tls.getSafeStringFromJson(object, "belong_project") ;
			String device_token = object.optString("device_token").trim();
			String ios_token = object.optString("ios_token").trim();
			String ios_real = object.optString("ios_real").trim();
			Integer dfg = object.optInt("at");
			
			
			
			WappUsers vo = new WappUsers();
			WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();	
			
			String syspwd = (ts > 0) ? tls.getMd5(Constant.FACEBOOK_USER_FIXED_PASSWORD) :
					Constant.FACEBOOK_USER_FIXED_PASSWORD;
			
			if ( !syspwd.equals(user_password) ) {
				result = Constant.FAIL_CODE;
			} else if ("".equals(user_name)) {
				result = Constant.ERR_INVALID_PARA;
			} else {
				Gson gson=new Gson();
			    vo = gson.fromJson(jsonStr,WappUsers.class);
				//String login_time = Tools.getStringFromDate(new Date());
			    String login_time = null;
			    if ( Constant.timeUtcFlag )		
			    	login_time = tls.getUtcDateStrNow();
				else
					login_time = tls.getStringFromDate(new Date());			    
			    
			    vo.setLastlogin_time(login_time);
			    vo.setUser_type(FACEBOOK_USER_TYPE);
			    vo.setDfg(dfg);
		    	vo.setCondition("email='" + user_name + "' and user_type=" + FACEBOOK_USER_TYPE );

		    	Random random = new Random();
				StringBuffer randNumber = new StringBuffer();
				for (int i = 0; i < 5; i++) {
					String rand = String.valueOf(random.nextInt(10));
					randNumber.append(rand);
				}

				vo.setApp_token(randNumber.toString());
				json.put(Constant.APP_TOKEN, randNumber.toString());
		    	
			    mList = fd.getWappUsers(vo);

				if ( tls.isNullOrEmpty(device_token) ) {
					vo.setDevice_token(" ");
					vo.setIos_token(ios_token);
					vo.setIos_real(ios_real);
					vo.setExt1("1");		//ios用户
				} else {
					vo.setDevice_token(device_token);
					vo.setIos_token("");
					vo.setIos_real("");					
					vo.setExt1("0");		//android用户		
				}
			    
				vo.setLogin_status(Tools.OneString);					
			    
			    if (!mList.isEmpty()) {
			    	if (fd.updateWappUsers(vo) <= 0 )
			    		result = Constant.FAIL_CODE;
			    } else {
			    	if ( fd.insertWappUsers(vo) <= 0 )
			    		result = Constant.FAIL_CODE;
			    }
			    mList.clear();
			    mList = fd.getWappUsers(vo);
			    if ( mList == null || mList.isEmpty())		    	
			    	result = Constant.FAIL_CODE;
			    else {
			    	String user_id = mList.get(0).getAt("user_id").toString().trim();
			    	setUserProps(user_id, login_time);
			    	result = Constant.SUCCESS_CODE;
			    }
				
			}
		} catch (Exception e) {
	    	result = Constant.FAIL_CODE;			
		}

		json.put(Constant.RESULTCODE, result);		
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());			
		
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
			WTSigninAction wa  = new WTSigninAction();
			WappUsers vo = new WappUsers();
			WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
				vo.setCondition("user_id ='"+10+"'");
			wa.mList = facade.getWappUsers(vo);
			if (  wa.json == null )
				wa.json = new JSONObject();
			wa.setUserProps("10", "2017/01/01");
			
	}
	
	
	//ababab
	void setUserProps(String user_id, String login_time) throws Exception {
		Tools tls = new Tools();	
		WTAppDeviceManAction wdma = new WTAppDeviceManAction();
		
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
		String serv_ip = getPropFromFile("server.properties", "servername");
		json.put("userImg_baseDir", "http://" + serv_ip + Constant.WEB_USERIMG_BASEDIR);
		json.put("petImg_baseDir", "http://" + serv_ip + Constant.WEB_PETIMG_BASEDIR);
		json.put("firm_baseDir", "http://" + serv_ip + Constant.WEB_FIRM_BASEDIR);
		json.put("mqtt_srvUrl", "http://" + serv_ip + Constant.MQTT_SRV_URL);
		json.put("mqtt_username", Constant.MQTT_SRV_USERNAME);
		json.put("mqtt_password", Constant.MQTT_SRV_PASSWORD);		
		json.put("petKind_baseDir", "http://" + serv_ip + Constant.WEB_PETKIND_BASEDIR);
		json.put("petef_baseDir", "http://" + serv_ip + Constant.EF_PET_BASEDIR);
		
		WappUsers obj = wdma.getAllListPetCount(user_id);
		
		json.put("bind_count", obj.getBind_count());
		json.put("pet_count", obj.getPet_count() );
		
		wdma = null;
		
	}


	
	public ActionForward proHeartBeat(String jsonStr, HttpServletResponse response)
			throws Exception {
		json = new JSONObject();

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			String user_id = object.optString("user_id").trim();
			
			Tools tls = new Tools();
			if (!tls.StrisNumeric(user_id)) {
				this.insertWMonitor("0", "0", "0", user_id + " is not valid user id para.");
			}
			
			String app_token = object.optString("app_token").trim();
			if ( (result = verifyAppToken(user_id, app_token)) == Constant.SUCCESS_CODE ) {
				String lang = object.optString("lang");
				insertVisit(null, null, "80", "signin lang:" + lang + ", user_id:" + user_id );							
				
				String lang2 = null;
				if ( lang != null && lang.length() >=2 )				
					lang2 = lang.substring(0, 2).toUpperCase();
				if ( lang != null /*&& ( "EN".equals(lang2) || "ZH".equals(lang2) )*/ ) {
					try {
						WappUsers vo = new WappUsers();
						WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
						vo.setCondition("user_id=" + user_id );
						vo.setLang(lang);
						facade.updateWappUsers(vo);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				result = heartBeat(user_id);
			}
		
		} catch (Exception e) {
	    	result = Constant.FAIL_CODE;			
		}

		json.put(Constant.RESULTCODE, result);		
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());			
			
		
		return null;
	}
	
	/*
	public void setNetCheckOffRelAll(String user_id) {
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();

		wsi.setUser_id(user_id);
		try {

			List<DataMap> list = wsi_fd.getUserRelDev(wsi);
			if ( list != null && list.size() > 0 ) {
				for(int i=0;i<list.size();i++){
					String device_id = list.get(i).getAt("device_id").toString().trim();
					//String device_imei = list.get(i).getAt("device_imei").toString().trim();
					if ( !Tools.isNullOrEmpty(device_id ) ) {
						EndServlet.setNetCheckOff(Integer.parseInt(device_id));
					}
				}
			}
		
		} catch (Exception e) {
		}

	}*/


	public int heartBeat(String user_id) {
		Tools tls = new Tools();		
		
		int res;
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		//WappUsers vo = new WappUsers();
		//WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		String belongProject = null;
		//wsi.setUser_id(user_id);

		try {

			updateAppUserStatus(user_id);
			
			wsi.setUser_id(user_id);
			List<DataMap> list = wsi_fd.getUserRelDev(wsi);
			//vo.setCondition("user_id=" + user_id );
			//List<DataMap> list = fd.getWappUsers(vo);
			//List<DataMap> list = fd.getWappUsers(vo);
			
			if ( list != null && list.size() > 0 ) {
				for(int i=0;i<list.size();i++){
					String device_id = list.get(i).getAt("device_id").toString().trim();
					String device_imei = list.get(i).getAt("device_imei").toString().trim();
					//String device_id = list.get(i).getAt("act_device_id").toString().trim();
					//String device_imei = BaseAction.getDeviceImeiFromDeviceId(device_id);

					
					if ( !tls.isNullOrEmpty(device_id ) ) {

						try {
							
						} catch(Exception e) {
							
						}					
						
						EndServlet.setNetCheckOn(Integer.parseInt(device_id));

						StringBuffer sbt = new StringBuffer("imei=");
						sbt.append(device_imei);
						sbt.append(",userId=");
						sbt.append(user_id);
						sbt.append(",devId=");
						sbt.append(device_id);
						sbt.append(",setNetCheckOn");	

						//insertVisit("setNetCheckOn", sbt.toString(), getDeviceNowDate(device_id), null );
						
						sbt = null;
						
						
						//if ( !"1".equals(belongProject) ) {
							updateDevAppStatus(user_id, device_id, device_imei);
						//}
					}
				}
			}
		
		} catch (Exception e) {
	    	res =  Constant.FAIL_CODE;			
		}

    	res =  Constant.SUCCESS_CODE;
    	//wsi = null;
    	
    	return res;
	}
	

	public	void updateDevAppStatus(String user_id, String device_id, String device_imei) {
		WdeviceActiveInfo vo =  new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		Integer oldAppStatus = 0;
		Tools tls = new Tools();		
		

		vo.setCondition("device_id = " + device_id );
		Date now = null;	//new Date();
		//now = new Date();
	    if ( Constant.timeUtcFlag )		
	    	now = tls.getUtcDateStrNowDate();
		else
			now = new Date();			    
		
		
		vo.setDevice_id(Integer.parseInt(device_id) );		
		vo.setApp_timestamp(tls.getStringFromDate(now));
		vo.setApp_status("1");
		

		try
		{
			
			List<DataMap> list = fd.getwDeviceExtra(vo);
			if ( list.size() == 0 )			//保证 wDeviceExtra表对应每个设备有相应数据
			{
				vo.setEco_mode(Tools.OneString);				
				fd.insertwDeviceExtra(vo);
			}
			else
			{
				if ( "1".equals(list.get(0).getAt("app_status").toString().trim()))
					oldAppStatus = 1;
				fd.updatewDeviceExtra(vo);
			} 
			
			//找到设备的网络session， 下发进入GPS地图指令
			if (oldAppStatus != 1 && !"null".equals(device_imei)) {

				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯
				cmdDownSetImpl.setGpsMap(device_imei, true);	
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		
		
	}

	
	public void updateAppUserStatus(String user_id) {
		Tools tls = new Tools();
		
		WappUsers vo =  new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();

		vo.setCondition("user_id = " + user_id );
		Date now = null;	//new Date();
		//now = new Date();
	    if ( Constant.timeUtcFlag )		
	    	now = tls.getUtcDateStrNowDate();
		else
			now = new Date();			    

		vo.setLogin_status("1");
		//vo.setApp_timestamp(Tools.getStringFromDate(now));
		vo.setLastlogin_time(tls.getStringFromDate(now));
		//vo.setBadge(0);		//初始化IOS app的badge
		

		try
		{
			fd.updateWappUsers(vo);						
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		
		
	}

	public ActionForward proAppQuit(String jsonStr, HttpServletResponse response)
			throws Exception {
		json = new JSONObject();

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			String user_id = object.optString("user_id").trim();
			String app_token = object.optString("app_token").trim();
			if ( (result = verifyAppToken(user_id, app_token)) == Constant.SUCCESS_CODE ) {
				result = heartBeatOff(user_id);
			}
		
		} catch (Exception e) {
	    	result = Constant.FAIL_CODE;			
		}

		json.put(Constant.RESULTCODE, result);		
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());			
			
		
		return null;
	}
	
	public int heartBeatOff(String user_id) {
		Tools tls = new Tools();		
		
		int res;
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		//wsi.setUser_id(user_id);
		//WappUsers vo = new WappUsers();
		//WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		String belongProject = null;
		

		try {

			updateAppUserStatusOff(user_id);
			
			wsi.setUser_id(user_id);
			List<DataMap> list = wsi_fd.getUserRelDev(wsi);
			//vo.setCondition("user_id=" + user_id);
			//List<DataMap> list = fd.getWappUsers(vo);
			if ( list != null && list.size() > 0 ) {
				for(int i=0;i<list.size();i++){
					String device_id = list.get(i).getAt("device_id").toString().trim();
					String device_imei = list.get(i).getAt("device_imei").toString().trim();
					//String device_id = list.get(i).getAt("act_device_id").toString().trim();
					
					if ( tls.isNullOrEmpty(device_id ) ) {
				    	res =  Constant.SUCCESS_CODE;
				    	return res;						
					}
					
					//String device_imei = BaseAction.getDeviceImeiFromDeviceId(device_id);
					

					//DataMap dm = (DataMap)cmdDeviceIoSession.getAttribute("wdeviceInfo");
					//belongProject = dm.getAt("belong_project").toString().trim();							
					
					
					EndServlet.setNetCheckOff(Integer.parseInt(device_id));
					StringBuffer sbt = new StringBuffer("imei=");
					sbt.append(device_imei);
					sbt.append(",userId=");
					sbt.append(user_id);
					sbt.append(",devId=");
					sbt.append(device_id);
					sbt.append(",setNetCheckOff");				
					//insertVisit("setNetCheckOff", sbt.toString(), getDeviceNowDate(device_id), null );
					sbt = null;

					updateDevAppStatusOff(user_id, device_id, device_imei);
					
					/*
					if ( !"1".equals(belongProject) ) {
					}*/
				}
			}
		
		} catch (Exception e) {
	    	res =  Constant.FAIL_CODE;			
		}

    	res =  Constant.SUCCESS_CODE;
    	//wsi = null;
    	
    	return res;
	}

	public void updateAppUserStatusOff(String user_id) {
		WappUsers vo =  new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();

		vo.setCondition("user_id = " + user_id );
		//vo.setLogin_status("0");

		try
		{
			//fd.updateWappUsers(vo);						
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		
		
	}

	public	void updateDevAppStatusOff(String user_id, String device_id, String device_imei) {
		WdeviceActiveInfo vo =  new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		Integer oldAppStatus = 0;

		vo.setCondition("device_id = " + device_id );
		vo.setDevice_id(Integer.parseInt(device_id) );		
		//vo.setApp_timestamp(Tools.getStringFromDate(now));
		vo.setApp_status("0");

		try
		{
			/*
			List<DataMap> list = fd.getwDeviceExtra(vo);
			if ( list.size() == 0 )			//保证 wDeviceExtra表对应每个设备有相应数据
				fd.insertwDeviceExtra(vo);
			else
			{
				if ( "1".equals(list.get(0).getAt("app_status").toString().trim()))
					oldAppStatus = 1;
				fd.updatewDeviceExtra(vo);
			}*/
			
			//找到设备的网络session， 下发进入GPS地图指令
			//if (oldAppStatus == 1 && !"null".equals(device_imei)) {

				CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯
				cmdDownSetImpl.setGpsMap(device_imei, false );				
			//}
				
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		
		
	}

	public	boolean proRelDevTimeZone(String user_id, String time_zone) {
		Tools tls = new Tools();
    	LocationInfoHelper lih = new LocationInfoHelper();
		
		boolean res  = true;
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		
		WdeviceActiveInfo dai = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade daiFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		
		
		wsi.setUser_id(user_id);

		try {
			
			List<DataMap> list = wsi_fd.getUserRelDev(wsi);
			if ( list != null && list.size() > 0 ) {
				for(int i=0;i<list.size();i++){
					String device_id = list.get(i).getAt("device_id").toString().trim();
					String auto_time_zone = list.get(i).getAt("auto_time_zone").toString().trim();
					String old_time_zone = list.get(i).getAt("time_zone").toString().trim();

					String is_priority = list.get(i).getAt("is_priority").toString().trim();

					if ("1".equals(auto_time_zone) && "1".equals(is_priority) && !old_time_zone.equals(time_zone)) {
						//String device_imei = list.get(i).getAt("device_imei").toString().trim();

						//try {

							//更改时区，默认关闭紧急模式状态
							//ReqJsonData reqJsonData = new ReqJsonData();
							//reqJsonData.setUrgentFlag(Tools.ZeroString);
							//reqJsonData.setUserId(Integer.parseInt(user_id));
							//WTAppGpsManAction wma = new WTAppGpsManAction();
							//wma.proUrgentModeRes(Integer.parseInt(device_id), device_imei, reqJsonData);

							//WdeviceActiveInfo wda = new WdeviceActiveInfo();
							//WdeviceActiveInfoFacade wdaFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
							//wda.setCondition("device_id = " + device_id);
							//wda.setUrgent_mode("0");
							//wdaFd.updateWdeviceActiveInfo(wda);
							
							// end
							
							//ClientSessionManager mClientSessionManager =
							//		WTDevHandler.getClientSessionMangagerInstance();							
					    	//IoSession tempSession = mClientSessionManager.getSessionId(device_imei);
					    	
					    	//if ( tempSession == null )
					    	//	return false;
					    	
					    	//logger.info("session: " + tempSession.getId() + " dev: " + tempSession.getAttribute("devId") + " userId: " + user_id + " change time_zone: " + time_zone);
					    	
						    //mClientSessionManager.removeSessionId(serieNo);
							//ServerHandler sh = new ServerHandler();					    	
					    	//sh.sysOffline( tempSession );
						    //tempSession.closeNow();
						    
					    	//LocationInfoHelper lih = new LocationInfoHelper();
					    	//lih.syncDevStatus(tempSession, device_id, time_zone);

						    
						    
						    //tempSession = null;
						//} catch ( Exception e ) {
						//	e.printStackTrace();
						//}											
						
						dai.setCondition("device_id=" + device_id);
						dai.setTime_zone(time_zone);
						daiFd.updateWdeviceActiveInfo(dai);	// <= 0 )
						//	res = false;

						JSONObject jon = new JSONObject();
						jon.put("time_zone", time_zone);
					    
						WMsgInfo aMsg = new WMsgInfo();
						aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEV_ODIFY_TIMEZONE);
						aMsg.setMsg_content(jon.toString());
						aMsg.setDevice_id(Integer.parseInt(device_id) );
						if ( Constant.timeUtcFlag )
							aMsg.setMsg_date(tls.getUtcDateStrNow() );
						else {																					
							aMsg.setMsg_date(getDeviceNow(device_id));
						}
						aMsg.setHide_flag(Tools.OneString);
						
			
						lih.proCommonInnerMsg(aMsg, Integer.parseInt(user_id));
						
					}
				}
			}
		
		} catch (Exception e) {
			res = false;
		}

    	wsi = null;
    	return res;
    	
    	
	
	}
	
	public ActionForward proHeartBeatType(String jsonStr, HttpServletResponse response)
			throws Exception {
		json = new JSONObject();

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			String user_id = object.optString("user_id").trim();
			String app_token = object.optString("app_token").trim();
			if ( (result = verifyAppToken(user_id, app_token)) == Constant.SUCCESS_CODE ) {
			}
		
		} catch (Exception e) {
	    	result = Constant.FAIL_CODE;			
		}

		json.put(Constant.RESULTCODE, result);		
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());			
			
		
		return null;
	}
	

	public ActionForward proHostMp(String jsonStr)
		{
		json = new JSONObject();

		try {
			JSONObject object = JSONObject.fromObject(jsonStr);
			String user_id = object.optString("user_id").trim();
			
			Tools tls = new Tools();
			if (!tls.StrisNumeric(user_id)) {
				this.insertWMonitor("0", "0", "0", user_id + " is not valid user id para.");
			}
			
			String app_token = object.optString("app_token").trim();
			if ( (result = verifyAppToken(user_id, app_token)) == Constant.SUCCESS_CODE ) {
				String mp = object.optString("mp");
				if (!tls.isNullOrEmpty(mp)) {
					WappUsers vo =  new WappUsers();
					WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();

					vo.setCondition("user_id = " + user_id );
					vo.setPhone(mp);
					//vo.setLogin_status("0");

					fd.updateWappUsers(vo);						
					
				}
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

			
		
		return null;
	}
	
	
}
