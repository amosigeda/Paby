//yonghu create 20160625 label

package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.CmdUpFirmRun;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.http.EndServlet;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppMsgManFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.innerw.wupfirmware.domain.WupFirmware;
import com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;

public class WTAppDeviceManAction extends BaseAction{
	
	Log logger = LogFactory.getLog(WTAppDeviceManAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";
	JSONObject json;
	
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
			
			logger.info("WTAppDeviceManAction request param:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");
			int device_id = object.optInt("device_id");
			int flag = object.optInt("flag");
			int tflag = object.optInt("tflag");
			int dur = object.optInt("dur");					
			String ton = object.optString("ton");
			String toff = object.optString("toff");		
			String smVer = object.optString("updateVer");
			
			StringBuffer smsb = null;
			if ( smVer.contains("YK902_80M_DE001B_"))
				smsb = new StringBuffer("");
			else
				smsb = new StringBuffer("YK902_80M_DE001B_");
				
			smsb.append(smVer);
			
			//临时处理
			String updateVer = smsb.toString();

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppDeviceManAction cmd:" + cmd);
						
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			String belongProject = ( tls.getSafeStringFromJson(object, "belong_project")
					.equals("") ) ? "1" : tls.getSafeStringFromJson(object, "belong_project") ; 	
						
			WappUsers voAu = new WappUsers();
			WappUsersFacade facadeWauif = ServiceBean.getInstance().getWappUsersFacade();
			
			if (( result = verifyAppToken(String.valueOf(user_id), app_token)) == Constant.SUCCESS_CODE ) {

				if ( cmd.equals("add") ) { //添加设备
					String device_imei = object.optString("device_imei").trim(); //( Tools.getSafeStringFromJson(object, "device_imei");
					Integer pet_id = object .optInt("pet_id");
					if ( pet_id > 0 ) {
						proReqBind2(href, user_id, device_imei, belongProject, pet_id);
					} else {
						//proReqBind(href, user_id, device_imei, belongProject);
						proReqBind3(href, user_id, device_imei, belongProject);
					}
				} else if (cmd.equals("add2")) { 
					String device_imei = object.optString("device_imei").trim(); //( Tools.getSafeStringFromJson(object, "device_imei");
					Integer pet_id = Integer.parseInt(object.optString("pet_id"));
					proReqBind2(href, user_id, device_imei, belongProject, pet_id);					
				
			    } else if (cmd.equals("share")) {	//分享设备
					int share_id = object.getInt("share_id");
					proReqShare(href, share_id, object);
			    } else if (cmd.equals("fastShare")) {
					String device_imei = object.optString("device_imei").trim(); //( Tools.getSafeStringFromJson(object, "device_imei");
					String msgTxt = object.optString("mag_txt").trim();
					proFastShare(user_id, device_imei, belongProject, msgTxt);					
				} else if (cmd.equals("agreeShare")) {	//分享设备
					int share_id = object.getInt("share_id");
					proReqHostAgreeShare(href, user_id, share_id);				
				} else if (cmd.equals("denyShare")) {	//
					int share_id = object.getInt("share_id");
					proReqHostDenyShare(href, user_id, share_id);				
				} else if (cmd.equals("unbind")) {	//解除设备绑定
					WshareInfo shareInfo = new WshareInfo();
					shareInfo.setUser_id(String.valueOf(user_id));
					shareInfo.setDevice_id(device_id);
					proReqUnbind(href,shareInfo);
				} else if (cmd.equals("bindlist")) {	//获取绑定及分享设备列表	
					voAu.setCondition("user_id=" + user_id + " and status ='1'");
					if ( facadeWauif.getWappUsersCount(voAu) == 0 ) {
						result = Constant.ERR_INVALID_USER;					
					} else {
						proQueryBindList(href, user_id, belongProject);					
					}				
				} else if (cmd.equals("unbindlist")) {	//APP用户获取所有宠物资料(未与设备绑定)

			  		/*select distinct a.device_id,b.device_type, c.pet_id, c.nickname as nick_name,c.photo, b.led_on,b.sel_mode,b.gps_on,b.callback_on 
			  		from wunshare_info a left join wdevice_active_info b on a.device_id = b.device_id 
				    left join wpets c on b.device_id = c.device_id*/ 					
					proQueryUnbindList(user_id);
				} else if (cmd.equals("selDev"))	{	//APP用户选择活动设备
					proSelDev(user_id, device_id);								
				} else if (cmd.equals("delShare"))	{	//主人取消分享设备				
					int member_id = Integer.parseInt(object.optString("member_id"));				
					proDelShare(user_id, device_id, member_id);
					//result = Constant.SUCCESS_CODE;
				} else if (cmd.equals("alllist")) {	//获取所有宠物
					proQueryAllList(href, user_id, belongProject);					
				} else if (cmd.equals("allUsers")) {	//获取所有					
					proQueryAllUsers( user_id, device_id );					
				} else if (cmd.equals("qDevFirm")) {	//检测设备是否有新固件					
					proQueryDevFirm( user_id, device_id, response );	
					return null;
				} else if (cmd.equals("uFirm")) {	// 通知设备固件FOTA升级				
					proUpDevFirm( user_id, device_id, 1, updateVer,  response );	
					//return null;
				} else if (cmd.equals("SleepCtl")) {	// 休眠时段接口
					//proUpDevFirm( user_id, device_id, 1, updateVer,  response );	
					//return null;
					proSleepCtl(user_id, device_id, flag, response );
					return null;					
				} else if (cmd.equals("sTPS")) {	// 定时开关机
					//proUpDevFirm( user_id, device_id, 1, updateVer,  response );	
					//return null;
					insertVisit(null, null, String.valueOf(device_id), "tflag:" + tflag +",ton:" + ton + ",toff:" + toff);							

					proTOnOffCtl(user_id, device_id, tflag, ton, toff, response );
					return null;										
				} else if (cmd.equals("tso") ) {	// APP温度监测管理打开					
					insertVisit(null, null, String.valueOf(device_id), "tso, dur:" + dur );							
					proTmCtl(user_id, device_id, dur, true, response );
					return null;
				} else if (cmd.equals("tsf") ) {	// APP温度监测管理打开					
					insertVisit(null, null, String.valueOf(device_id), "tso, dur:" + dur );							
					proTmCtl(user_id, device_id, dur, false, response );
					return null;
				} else if (cmd.equals("rcm")) {	// 电话回拨
					String pno = null;
					String imei = null; 
					imei = this.getDeviceImeiFromDeviceId(String.valueOf(device_id) );				
					WappUsers vo = new WappUsers();
					WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
					vo.setCondition("user_id ="+user_id);
					List<DataMap> phoneInfos = facade.getWappUsers(vo);
										
					if(!phoneInfos.isEmpty()) {
						pno = phoneInfos.get(0).getAt("phone").toString().trim();
					}
					
					insertVisit(null, null, String.valueOf(device_id), " rcm, pno:" + pno );							
					proRcmCtl(user_id, device_id, imei, pno, response );
					return null;

				} else {
					result = Constant.FAIL_CODE;									
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
	
	//解除绑定
	public void proReqUnbind(String href, WshareInfo shareInfo) throws SystemException{
		
		//abcde
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
		//String is_prior = Tools.ZeroString;
		String user_id = shareInfo.getUser_id();
		Integer device_id = shareInfo.getDevice_id();

		vo.setCondition("a.user_id='" + user_id + "' and a.device_id=" +
			device_id );
		List<DataMap> list = fd.getData(vo);
		
		int is_prior = -1;
		
		
		if(list.size() > 0) {
			is_prior = proAddUnshareInfo(user_id, device_id );
		}			

		/*
		vo.setCondition("a.user_id='" + user_id + "' and a.device_id=" +
				device_id  + " and a.is_priority=1");
		List<DataMap> list1 = fd.getData(vo);
		if ( list1 != null && list1.size() > 0) {
			is_prior = Tools.OneString;
		}*/
				
		if ( is_prior > 0 ) {
			vo.setCondition("device_id=" +
				device_id);
		} else {
			vo.setCondition("user_id='" + user_id + "' and device_id=" +
					device_id);			
		}
		
		if ( fd.delData(vo) > 0 )  {
			updateWdevUserCount(device_id);
			result = Constant.SUCCESS_CODE;
		}
		else
			result = Constant.FAIL_CODE;

	}

	//解绑设备的时候把对应的宠物资料和设备信息分离
	/*
	void proDivPetInfo(Integer device_id) {		
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		
		vo.setCondition("");
		
	}*/
		
	int proAddUnshareInfo(String user_id, Integer device_id ) throws SystemException {
		Tools tls = new Tools();
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
		Wpet vo1 = new Wpet();
		WpetFacade fd1 = ServiceBean.getInstance().getWpetFacade();
		//WdeviceActiveInfo vo2 = new WdeviceActiveInfo();
		//WdeviceActiveInfoFacade fd2 = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		Integer is_prior = -1;
		String unshare_date = null;
		//unshare_date = Tools.getStringFromDate(new Date());
		
		if ( Constant.timeUtcFlag )
			unshare_date = tls.getUtcDateStrNow();			
		else
			unshare_date = tls.getStringFromDate(new Date());
					
		Integer pet_id = -1;
		vo1.setCondition("a.device_id="+ device_id);
		List<DataMap> list1 = fd1.getDogDataList(vo1);
		if (list1.size() == 1) {
			pet_id = (Integer) list1.get(0).getAt("pet_id");
		} else {
			pet_id = -1;
		}
		
		vo.setCondition("a.user_id='" + user_id + "' and a.device_id=" +
				device_id + " and a.status=1" );
		List<DataMap> list2 = fd.getData(vo);
		if (list2.size() == 1 ) {
			is_prior = Integer.parseInt(list2.get(0).getAt("is_priority").toString());
			vo.setUser_id(user_id);
			vo.setDevice_id(device_id);
			vo.setShare_date(unshare_date);
			vo.setPet_id(pet_id);
			fd.insertUnshareData(vo);
			//...需要产生消息
			
			vo.setCondition("a.is_priority = 0 and a.status=1 and a.to_user_id=" + user_id + " and a.device_id=" + device_id );
			List<DataMap> list3 = fd.getData(vo);
			for(int i=0; i<list3.size(); i++){
				DataMap druMap = list3.get(i);
				//String app_type = druMap.getAt("app_type").toString().trim();
				//String ios_token = druMap.getAt("ios_token").toString().trim();
				//String device_token = druMap.getAt("device_token").toString().trim();

				String app_type = druMap.getAt("sapp_type").toString().trim();				
				String ios_token = druMap.getAt("sios_token").toString().trim();
				String ios_real = druMap.getAt("sios_real").toString().trim();
				
				String device_token = druMap.getAt("sdevice_token").toString().trim();
				String dest_lang = druMap.getAt("slang").toString().trim();
				
				String member_id = list3.get(i).getAt("user_id").toString().trim(); 
				vo.setUser_id(member_id);
				vo.setDevice_id(device_id);
				vo.setShare_date(unshare_date);
				vo.setPet_id(pet_id);
				fd.insertUnshareData(vo);
				String from_nick = list3.get(i).getAt("to_nick").toString().trim();
				String pet_nick = list3.get(i).getAt("pet_nick").toString().trim();
				//产生消息
				produceDelShareMsg(Integer.parseInt(user_id), device_id, 
						Integer.parseInt(member_id),
						from_nick,
						pet_nick,
						app_type,
						ios_token,
						device_token,
						dest_lang,
						ios_real);				
			}
			
			vo.setCondition("is_priority = 0 and status=1 and to_user_id=" + user_id + " and device_id=" + device_id );
			fd.delData(vo);
			
			//解绑设备的时候把对应的宠物资料和设备信息分离
			if ( is_prior == 1 && pet_id > 0) {
				vo1.setCondition("pet_id="+ pet_id);
				vo1.setPet_id(String.valueOf(pet_id) );
				vo1.setDevice_id(-1);
				fd1.updateDogDeviceId(vo1);
			}
			
			if ( is_prior == 1 ) {
				WdeviceActiveInfo voDa = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade fdDa = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				voDa.setCondition("device_id=" + device_id);
				voDa.setEco_mode(Tools.ZeroString);
				voDa.setEsafe_on(Tools.ZeroString);
				voDa.setEsafe_wifi(Tools.ZeroString);
				voDa.setUrgent_mode(Tools.ZeroString);
				voDa.setLed_on(Tools.ZeroString);
				//voDa.setDev_status(Tools.ZeroString);
				
				fdDa.updateWdeviceActiveInfo(voDa);
				fdDa.updatewDeviceExtra(voDa);

				ClientSessionManager mClientSessionManager =
						WTDevHandler.getClientSessionMangagerInstance();							
		    	IoSession tempSession = mClientSessionManager.getSessionId(getDeviceImeiFromDeviceId(String.valueOf(device_id)));
		    	
		    	if ( tempSession == null ) {
					result = Constant.SUCCESS_CODE;	    		
		    		return is_prior;
		    	}
		    	
				//ServerHandler sh = new ServerHandler();					    	
		    	//sh.sysOffline( tempSession );
			    tempSession.closeNow();				
			}			
		}
			
		return is_prior;
	}
		
	//查询绑定设备列表
	JSONObject proQueryUnbindList(int user_id) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		vo.setCondition("(device_id=-1 or device_id is null) and user_id=" + user_id  );
		List<DataMap> list = facade.queryUnbindList(vo);
		json.put("device_count", list.size());

		if(list != null && list.size() > 0){
			JSONArray jsonArr = new JSONArray();
			
			for(int i=0;i<list.size();i++){
				DataMap dataMap = list.get(i);
				JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
				jsonArr.add(itemObj);					
			}				
			json.put("device_list", jsonArr);
			
		}			
		
		//json.put("device_list", list);
		result = Constant.SUCCESS_CODE;							
				
		return json;
	}

	//查询绑定设备列表
	JSONObject proSelDev(int user_id, int device_id) throws SystemException{
		Tools tls = new Tools();
		
		WappUsers vo = new WappUsers();						
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + user_id  );
		
		List<DataMap> list = fd.getWappUsers(vo);
		if ( list != null && list.size() > 0 ) {
			for(int i=0;i<list.size();i++){
				//String device_id = list.get(i).getAt("device_id").toString().trim();
				//String device_imei = list.get(i).getAt("device_imei").toString().trim();
				String old_device_id = list.get(i).getAt("act_device_id").toString().trim();
				if ( !tls.isNullOrEmpty(old_device_id ) ) {
					EndServlet.setNetCheckOff(Integer.parseInt(old_device_id));
				}
			}
		}
				
		vo.setAct_device_id(device_id);
		if (fd.updateWappUsers(vo) > 0) {

			result = Constant.SUCCESS_CODE;			
		}
		else
			result = Constant.FAIL_CODE;		
		return json;
	}
		
	//查询绑定设备列表
	JSONObject proQueryBindList(String href, int user_id, String belong_project) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		vo.setCondition("a.user_id=" + user_id + " and a.status='1'"  );
		List<DataMap> list = facade.queryBindList(vo);
		json.put("bind_count", list.size());
		json.put("act_device_id", getActDeviceId(String.valueOf(user_id)));
		
		if(list != null && list.size() > 0){
			JSONArray jsonArr = new JSONArray();
			
			for(int i=0;i<list.size();i++){
				DataMap dataMap = list.get(i);
				JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
				jsonArr.add(itemObj);					
			}				
			json.put("device_list", jsonArr);
			
		}				
		//json.put("device_list", list);
		result = Constant.SUCCESS_CODE;									
		return json;
	}
	
	//添加设备
	public JSONObject proReqBind(String href, int user_id, String device_imei, String belong_project) throws SystemException{
		Tools tls = new Tools();
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();

		WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> list = null;
		
		//检索设备ID号
		voDeviceActive.setCondition("device_imei = '" + device_imei + "'" );
		list = facadeDeviceActive.getData(voDeviceActive);
		
		String old_time_zone = null;

		Integer device_id = -1;

		//debug 调试业务，默认开通新imei, 正式上线的时候要去掉这块代码段
		if ( !Constant.STAT_APP_RESTRICT_IMEI  && list.size() == 0 ) {
			voDeviceActive.setDevice_imei(device_imei);
			voDeviceActive.setDevice_name("test");
			//voDeviceActive.setDevice_update_time(new Date());
			/*
			if ( Constant.timeUtcFlag )
				voDeviceActive.setDevice_update_time(tls.getUtcDateStrNowDate());			
			else
				voDeviceActive.setDevice_update_time(new Date());
			*/
			voDeviceActive.setDevice_disable("1");
			voDeviceActive.setBelong_project(1);
			facadeDeviceActive.insertWdeviceActiveInfo(voDeviceActive);
			list = facadeDeviceActive.getData(voDeviceActive);
			device_id = (Integer) list.get(0).getAt("device_id");
			voDeviceActive.setDevice_id(device_id);
			voDeviceActive.setTime_zone(myTimeZone);
			voDeviceActive.setEco_mode(Tools.OneString);
			
			facadeDeviceActive.insertwDeviceExtra(voDeviceActive);
			
		}
		list.clear();
		list = facadeDeviceActive.getData(voDeviceActive);
					
		if ( list.size() > 0 ) {
			device_id = (Integer) list.get(0).getAt("device_id");
			String device_type = (String) list.get(0).getAt("device_type");
			old_time_zone = list.get(0).getAt("time_zone").toString().trim();
				
			//检查设备已经被同一个用户添加过的情况
			//先清除以前未处理完的脏数据
			vo.setCondition("user_id = " + user_id + " and device_id = " + device_id + " and status != '1'"  );
			facade.delData(vo);

			WTAppMsgManAction amma = new WTAppMsgManAction();
			amma.proPushStatusByApplyer(user_id, device_id);
			amma = null;
					
			vo.setCondition("a.user_id = " + user_id + " and a.device_id = " + device_id );			
			list = facade.getData(vo);
		
			String admin_userid = String.valueOf(user_id);
			if (list.size() > 0) {
				//ERR_APPADD_DEVICE_OTHER_BINDED				
				result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;
		    } else {
		    	list.clear();
				vo.setCondition("a.device_id = " + device_id + " and a.is_priority = '1'"  );
				list = facade.getData(vo);
				if ( list.size() > 0 ) { //需要向设备主人申请是否同意绑定
					admin_userid = (String) list.get(0).getAt("user_id");

					vo.setUser_id(String.valueOf( user_id ) );
					vo.setTo_user_id(admin_userid);
					vo.setDevice_id(device_id);
					vo.setIs_priority("0");
					vo.setBelong_project(Integer.parseInt(belong_project));
					String share_time = null;
					//share_time = Tools.getStringFromDate(new Date());
					if ( Constant.timeUtcFlag )
						share_time = tls.getUtcDateStrNow();					
					else
						share_time = tls.getStringFromDate(new Date());
										
					vo.setShare_date(share_time);
					vo.setStatus("0");	//状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',  
					if ( facade.insertData(vo) > 0 ) {	//成功绑定
						list.clear();
						vo.setCondition("a.user_id='" + vo.getUser_id() + "' and a.to_user_id='" +
								vo.getTo_user_id() + "' and a.device_id=" + vo.getDevice_id() +
								" and a.is_priority='" + vo.getIs_priority() + "'" +
								 " and a.status = '" + vo.getStatus() + "'");
						list.clear();
						list = facade.getData(vo);
						DataMap druMap = list.get(0);
						String app_type = druMap.getAt("app_type").toString().trim();
						String ios_token = druMap.getAt("ios_token").toString().trim();
						String device_token = druMap.getAt("device_token").toString().trim();						
						String ios_real = druMap.getAt("ios_real").toString().trim();
						String dest_lang = druMap.getAt("lang").toString().trim();
						String badge = druMap.getAt("badge").toString().trim();
						
						Integer share_id = (Integer) list.get(0).getAt("id");
						json.put(Constant.DEVICE_ID, device_id);													
						json.put(Constant.ADMIN_USERID, admin_userid);
						json.put("admin_email", getEmailFromUserId(admin_userid));
						json.put(Constant.SHARE_ID, String.valueOf(share_id));
						json.put(Constant.DEVICE_TYPE, device_type);
						json.put("user_id", user_id);
						//json.put("pet_id", getPetIdFromDeviceId(String.valueOf(device_id)));
						json.put("pet_id", list.get(0).getAt("pet_id") );
						json.put("from_nick", list.get(0).getAt("from_nick").toString().trim());
						json.put("pet_nick", list.get(0).getAt("pet_nick").toString().trim());
						json.put("app_type", app_type);
						json.put("device_token", device_token);
						json.put("ios_token", ios_token);
						json.put("ios_real", ios_real);
						json.put("dest_lang", dest_lang);
						json.put("badge", badge);
						
						produceShareMsg();
						result = Constant.SUCCESS_CODE;							
					} else {
						result = Constant.FAIL_CODE;							
					}
										
				}
				else {	//设备可以成为申请用户的主设备
					vo.setUser_id(String.valueOf( user_id ) );
					vo.setTo_user_id(null);
					vo.setDevice_id(device_id);
					vo.setIs_priority("1");
					vo.setBelong_project(Integer.parseInt(belong_project));
					String share_time = null;					
					//String share_time = Tools.getStringFromDate(new Date());
					//
					if ( Constant.timeUtcFlag )
						share_time = tls.getUtcDateStrNow();					
					else
						share_time = tls.getStringFromDate(new Date());

					vo.setShare_date(share_time);
					vo.setStatus("1"); 	//状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',
					if ( facade.insertData(vo) > 0 ) {	//成功绑定
						list.clear();    //获取刚刚添加数据的id;
						list = facade.getData(vo);
						//json.put(Constant.SHARE_ID, list.get(0).getAt("id").toString());
						
						vo.setCondition("user_id="+ user_id + " and device_id=" + device_id );
						facade.delUnshareData(vo);
						
						updateWdevUserCountAndTimeZone(device_id,device_imei,old_time_zone);
						json.put(Constant.DEVICE_ID, device_id);
						json.put(Constant.DEVICE_TYPE, device_type);
						json.put(Constant.ADMIN_USERID, admin_userid);		
						result = Constant.SUCCESS_CODE;							
					} else {
						result = Constant.FAIL_CODE;							
					}
				}    	
		    }
		} else {
			result = Constant.ERR_INVALID_DEVICE;
		}
			
		json.put("request", href);
		return json;
				
	}
	
	void updateWdevUserCount(Integer device_id) throws SystemException {
		Integer bind_count = 0;    //单个设备被多少个用户同时绑定
		WdeviceActiveInfo vo = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		WshareInfo voi = new WshareInfo();						
		WshareInfoFacade fdi = ServiceBean.getInstance().getWshareInfoFacade();

		voi.setCondition("device_id =" + device_id + " and status=1");
		bind_count = fdi.getWdevUserCount(voi);
		
		vo.setCondition("device_id="+ device_id);
		vo.setBind_count(String.valueOf(bind_count));
		fd.updateWdeviceActiveInfo(vo);		
	}
	
	//同意分享设备申请
	public JSONObject proReqHostAgreeShare(String href, int user_id, int share_id) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		
		vo.setCondition("a.to_user_id = " + user_id + " and a.status != '1' and a.id=" + share_id  );
		List<DataMap> list = facade.getData(vo);
		
		Integer device_id = 0;
		
		if (list.size() == 0) {
			result = Constant.FAIL_CODE;
	    } else {
	    	device_id = (Integer) list.get(0).getAt("device_id");
	    	
	    	//list.clear();
	    	vo.setStatus("1");

			vo.setCondition("to_user_id = " + user_id + " and status != '1' and id=" + share_id  );	    	
	    	if ( facade.updateData(vo) > 0 ) {	//操作成功
				
				produceHostShareMsg(share_id, 1);
				updateWdevUserCount(Integer.parseInt(list.get(0).getAt("device_id").toString().trim()));
				
				result = Constant.SUCCESS_CODE;		
				WTAppMsgManAction amma = new WTAppMsgManAction();
				amma.proPushStatusByHost(user_id, device_id);
				amma = null;
				
			} else {
				result = Constant.FAIL_CODE;							
			}
	    }
												
		json.put("request", href);
		return json;
						
	}
	
	//主人拒绝分享设备申请
	public JSONObject proReqHostDenyShare(String href, int user_id, int share_id) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
	
		Integer device_id = 0;
		
		vo.setCondition("a.to_user_id = " + user_id + " and a.status != '1' and a.id=" + share_id  );
		List<DataMap> list = facade.getData(vo);
		if (list.size() == 0) {
			result = Constant.FAIL_CODE;
	    } else {

	    	device_id = (Integer) list.get(0).getAt("device_id");	    	
	    	list.clear();
	    	vo.setStatus("-1");		//-1删除标志
	    	
			vo.setCondition("to_user_id = " + user_id + " and status != '1' and id=" + share_id  );
	    	if ( facade.updateData(vo) > 0 ) {	//操作成功
				produceHostShareMsg(share_id, 0);				
				result = Constant.SUCCESS_CODE;		
				WTAppMsgManAction amma = new WTAppMsgManAction();
				amma.proPushStatusByHost(user_id, device_id);
				amma = null;
				
			} else {
				result = Constant.FAIL_CODE;							
			}
	    }			
		json.put("request", href);
		return json;		
	}
		
	//分享设备申请
	JSONObject proReqShare(String href, int share_id, JSONObject object){
		try{			
			//消息推送给设备主人，等待主人是否同意的结果
			//CountDownLatch decryptSignal =new CountDownLatch(1); 
			WshareInfo siVo = new WshareInfo();
						
			produceShareMsg(share_id, object);
			result = Constant.SUCCESS_CODE;					
			
			/*
			siVo.setId(share_id );
			new Thread(new ShareDeviceRunnable(siVo, decryptSignal)).start();//无需拿到新线程句柄，由 CountDownLatch 自行跟踪  
			try {  
			    decryptSignal.await();  

			} catch (InterruptedException e) {    
				result = Constant.ERR_SYSTEM_BUSY;
			} */ 
			
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
		return json;			
	}

	class ShareDeviceRunnable implements Runnable {  
	    private CountDownLatch decryptSignal; 
	    private WshareInfo wShareInfo;
	    protected ShareDeviceRunnable(WshareInfo wsi, CountDownLatch decryptSignal) {  
		        this.decryptSignal = decryptSignal;
		        setwShareInfo(wsi);
		    }  
	    
	    public void run() {  
	        //开始
	    	while(true) {
	    		try {
					if ( queryHostReply() > 0 ) {    			
						break;
					} else {
						try {
							Thread.sleep(1000*50);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
					}
				} catch (SystemException e) {
					e.printStackTrace();
				}    			    		
	    	}
	    	decryptSignal.countDown();//通知所有阻塞的线程  	    	
	    }

	    //return: 1: 表示主人有回复； -1：表示主人还没有回复
	    int queryHostReply() throws SystemException {		//返回1：主人有回复，返回0：主人未回复
	    	int res;
			WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
			wShareInfo.setCondition("id = " + wShareInfo.getId() + " and status != '0'");
			List<DataMap> list = facade.getData(wShareInfo);
			
			if ( list.size() > 0 ) {	//成功绑定	    	
				res = Integer.parseInt( (String) list.get(0).getAt("status") );
				if (res == 3) {
					facade.delData(wShareInfo);
					result = Constant.ERR_APPADD_DEVICE_HOST_DENY;					
				}
				else if (res == 2) {
					wShareInfo.setStatus("1");
					facade.updateData(wShareInfo);
					Integer user_id = (Integer)( list.get(0).getAt("user_id"));
					Integer device_id = (Integer) (list.get(0).getAt("device_id"));
					updateWdevUserCount(device_id);
					
					wShareInfo.setCondition("user_id=" + user_id + " and device_id=" + device_id);;
					facade.delUnshareData(wShareInfo);
					result = Constant.SUCCESS_CODE;					
				} else if (res == 1) {
					result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;										
				}
				return res;
			} else {
				return -1;
			}
			
	    }
	    

		/**
		 * @return the wShareInfo
		 */
		public WshareInfo getwShareInfo() {
			return wShareInfo;
		}

		/**
		 * @param wShareInfo the wShareInfo to set
		 */
		public void setwShareInfo(WshareInfo wShareInfo) {
			this.wShareInfo = wShareInfo;
		}  	
	}
	
	void produceShareMsg(int share_id, JSONObject object) throws SystemException {
		Tools tls = new Tools();

		WshareInfo wsi = new WshareInfo();
		WshareInfoFacade wsiFd = ServiceBean.getInstance().getWshareInfoFacade();
		
		String msg_txt = tls.getSafeStringFromJson(object, "msg_txt");
    	LocationInfoHelper lih = new LocationInfoHelper();
		
		
		wsi.setCondition("a.id=" + share_id);
		List<DataMap> list = wsiFd.getData(wsi);
		if (list.size() != 1) {
			throw new SystemException("invalid para: share_id");
		}
		DataMap dmap = list.get(0);
		
		Integer from_user_id = Integer.parseInt( dmap.getAt("user_id").toString().trim() );
		Integer to_user_id = Integer.parseInt( dmap.getAt("to_user_id").toString().trim() );
		Integer device_id = (Integer) dmap.getAt("device_id");
		String from_nick = dmap.getAt("from_nick").toString().trim();
		String pet_nick = dmap.getAt("pet_nick").toString().trim();
		
		String stat = dmap.getAt("status").toString().trim();
		String app_type = dmap.getAt("app_type").toString().trim();
		String ios_token = dmap.getAt("ios_token").toString().trim();
		String device_token = dmap.getAt("device_token").toString().trim();
		String ios_real = dmap.getAt("ios_real").toString().trim();
		String dest_lang = dmap.getAt("lang").toString().trim();
		
		if ("1".equals(stat)) {
			throw new SystemException("invalid para: share_id");
		}		
		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		WMsgInfo wm = new WMsgInfo();
		
		wm.setFrom_nick(from_nick);
		wm.setPet_nick(pet_nick);
		wm.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
		wm.setMsg_ind_id(Constant.CST_MSG_IND_APPLY_SHARE);
		String msg_dat = null;	//LocationInfoHelper.getDevCurrentTime(device_id);

		if ( Constant.timeUtcFlag )
			msg_dat = tls.getUtcDateStrNow();			
		else 																					
			msg_dat = lih.getDevCurrentTime(device_id);
		

		wm.setMsg_date(msg_dat);		
		wm.setDevice_id(device_id);
		wm.setFrom_usrid(from_user_id);
		wm.setTo_usrid(to_user_id);
		wm.setStatus("1");
		wm.setShare_id(share_id);
		wm.setDest_lang(dest_lang);
		String sum = lih.getMsgContentFromMsg(wm, msg_txt, msg_dat);
		wm.setMsg_content( sum );

		wm.setHide_flag(Tools.ZeroString);		
		wm.setSummary(sum);		
		
		String badgeStr = dmap.getAt("badge").toString();
		Integer badge = 0;
		if (!tls.isNullOrEmpty(badgeStr)  )
			badge = Integer.parseInt(badgeStr);
		
		
		wm.setOld_badge(badge);

		
		wm.setApp_type(app_type);
		wm.setDevice_token(device_token);
		wm.setIos_token(ios_token);
		wm.setIos_real(ios_real);
		
		infoFacade.insertData(wm);
		
	}
	
	//para: flag  0： 拒绝分享消息；  1: 同意分享消息
	void produceHostShareMsg(int share_id,int flag) throws SystemException {
		Tools tls = new Tools();

		WshareInfo wsi = new WshareInfo();
		WshareInfoFacade wsiFd = ServiceBean.getInstance().getWshareInfoFacade();
    	LocationInfoHelper lih = new LocationInfoHelper();

		
		wsi.setCondition("a.id=" + share_id);
		List<DataMap> list = wsiFd.getData(wsi);
		if (list.size() != 1) {
			throw new SystemException("invalid para: share_id");
		}
		DataMap dmap = list.get(0);
		String app_type = dmap.getAt("sapp_type").toString().trim();
		String ios_token = dmap.getAt("sios_token").toString().trim();
		String device_token = dmap.getAt("sdevice_token").toString().trim();
		String ios_real = dmap.getAt("sios_real").toString().trim();
		String dest_lang = dmap.getAt("slang").toString().trim();
		
		Integer from_user_id = Integer.parseInt( dmap.getAt("to_user_id").toString().trim() );
		Integer to_user_id = Integer.parseInt( dmap.getAt("user_id").toString().trim() );
		Integer device_id = (Integer) dmap.getAt("device_id");
		//String stat = dmap.getAt("status").toString().trim();
		String from_nick = dmap.getAt("to_nick").toString().trim();
		String pet_nick = dmap.getAt("pet_nick").toString().trim();
		
/*
		if ("-1".equals(stat)) {
			throw new SystemException("invalid para: share_id");
		}		
*/		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		WMsgInfo wm = new WMsgInfo();

		wm.setFrom_nick(from_nick);
		wm.setPet_nick(pet_nick);
		wm.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
		if ( flag == 0 )
			wm.setMsg_ind_id(Constant.CST_MSG_IND_DENY_SHARE);
		else
			wm.setMsg_ind_id(Constant.CST_MSG_IND_AGREE_SHARE);
		String msg_dat  = null;	//LocationInfoHelper.getDevCurrentTime(device_id);
		if ( Constant.timeUtcFlag )
			msg_dat = tls.getUtcDateStrNow();			
		else 																					
			msg_dat = lih.getDevCurrentTime(device_id);
		
		wm.setMsg_date( msg_dat );
		
		wm.setDevice_id(device_id);
		wm.setFrom_usrid(from_user_id);
		wm.setTo_usrid(to_user_id);
		wm.setStatus("1");
		wm.setShare_id(share_id);
		wm.setDest_lang(dest_lang);
		String sum = lih.getMsgContentFromMsg(wm, null, msg_dat);
		wm.setMsg_content(sum);

		wm.setHide_flag(Tools.ZeroString);		
		wm.setSummary(sum);				
		
		String badgeStr = dmap.getAt("sbadge").toString();
		Integer badge = 0;
		if (!tls.isNullOrEmpty(badgeStr)  )
			badge = Integer.parseInt(badgeStr) ;
		wm.setOld_badge(badge);

		
		wm.setApp_type(app_type);
		wm.setDevice_token(device_token);
		wm.setIos_token(ios_token);		
		wm.setIos_real(ios_real);		
		
		infoFacade.insertData(wm);			
		
	}
	

	//添加设备
	JSONObject proReqBind2(String href, int user_id, String device_imei, String belong_project, Integer pet_id) 
			throws SystemException {
		Tools tls = new Tools();
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();

		WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> list = null;
		
		//检索设备ID号
		voDeviceActive.setCondition("device_imei = '" + device_imei + "'" );
		list = facadeDeviceActive.getData(voDeviceActive);
		String old_time_zone = null;

		Integer device_id = -1;

		//debug 调试业务，默认开通新imei, 正式上线的时候要去掉这块代码段
		if ( !Constant.STAT_APP_RESTRICT_IMEI  && list.size() == 0 ) {			
			voDeviceActive.setDevice_imei(device_imei);
			voDeviceActive.setDevice_name("test");
			//voDeviceActive.setDevice_update_time(new Date());
			/*
			if ( Constant.timeUtcFlag )
				voDeviceActive.setDevice_update_time(tls.getUtcDateStrNowDate());					
			else
				voDeviceActive.setDevice_update_time(new Date());
			*/
			
			voDeviceActive.setDevice_disable("1");
			voDeviceActive.setBelong_project(1);
			facadeDeviceActive.insertWdeviceActiveInfo(voDeviceActive);
			list = facadeDeviceActive.getData(voDeviceActive);
			device_id = (Integer) list.get(0).getAt("device_id");
			voDeviceActive.setDevice_id(device_id);
			voDeviceActive.setTime_zone(myTimeZone);
			voDeviceActive.setEco_mode(Tools.OneString);			
			facadeDeviceActive.insertwDeviceExtra(voDeviceActive);

			list.clear();
			list = facadeDeviceActive.getData(voDeviceActive);					
		}

		if ( list.size() > 0 ) {
			device_id = (Integer) list.get(0).getAt("device_id");
			String device_type = (String) list.get(0).getAt("device_type");
			old_time_zone = list.get(0).getAt("time_zone").toString().trim();
		
			//检查设备已经被同一个用户添加过的情况
			//先清除以前未处理完的脏数据
			vo.setCondition("user_id = " + user_id + " and device_id = " + device_id + " and status != '1'"  );
			facade.delData(vo);

			
			vo.setCondition("a.user_id = " + user_id + " and a.device_id = " + device_id );			
			list = facade.getData(vo);
			if (list.size() > 0) {
				//ERR_APPADD_DEVICE_OTHER_BINDED				
				result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;
				json.put("request", href);				
				return json;			
			}
						
			vo.setCondition("a.device_id = " + device_id + " and a.is_priority = 1 and a.status=1"  );
			list = facade.getData(vo);

			
			String admin_userid = String.valueOf(user_id);

			if (list.size() > 0) {
				String old_userid = list.get(0).getAt("user_id").toString().trim();
				if ( admin_userid.equals(admin_userid) ) {
					String from_nick = list.get(0).getAt("from_nick").toString().trim();
					String from_email = list.get(0).getAt("from_email").toString().trim();
					json.put("admin_nick", from_nick);
					json.put("admin_email", from_email);
					result = Constant.ERR_APPADD_DEVICE_OTHER_BINDED;
					
				} else 
					result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;
		    } else {
		    	
		    	if (verifyPetIsEmptyDev(pet_id) != Constant.SUCCESS_CODE ) {
					super.logAction(String.valueOf(user_id),device_id, "verifyPetIsEmptyDev pet_id:" + pet_id);
		    		
		    		result = Constant.ERR_INVALID_PARA;
		    		return json;
		    	} else {
		    		proPetIsEmptyDev(pet_id, device_id);
		    	}
		    	
				vo.setUser_id(String.valueOf( user_id ) );
				vo.setTo_user_id(null);
				vo.setDevice_id(device_id);
				vo.setIs_priority("1");
				vo.setBelong_project(Integer.parseInt(belong_project));
				//String share_time = Tools.getStringFromDate(new Date());
				String share_time = null;					
				//String share_time = Tools.getStringFromDate(new Date());
				//
				if ( Constant.timeUtcFlag )
					share_time = tls.getUtcDateStrNow();					
				else
					share_time = tls.getStringFromDate(new Date());
				
				vo.setShare_date(share_time);
				vo.setStatus("1"); 	//状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',
				if ( facade.insertData(vo) > 0 ) {	//成功绑定
					list.clear();    //获取刚刚添加数据的id;
					//list = facade.getData(vo);
					//json.put(Constant.SHARE_ID, list.get(0).getAt("id").toString());
					
					vo.setCondition("user_id="+ user_id + " and device_id=" + device_id );
					facade.delUnshareData(vo);
					
					updateWdevUserCountAndTimeZone(device_id,device_imei, old_time_zone);
					
					
					json.put(Constant.DEVICE_ID, device_id);
					json.put(Constant.DEVICE_TYPE, device_type);
					json.put(Constant.ADMIN_USERID, user_id);		
					result = Constant.SUCCESS_CODE;							
				} else {
					result = Constant.FAIL_CODE;							
				}		    	
		    }
		} else {
			result = Constant.ERR_INVALID_DEVICE;
		}
			
		json.put("request", href);
		
		return json;
	}
	
	Integer verifyDeviceInfo(Integer pet_id, Integer device_id) throws SystemException {	
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		
		vo.setCondition("pet_id=" + pet_id + " and a.device_id=" + device_id);
		List<DataMap> list = fd.getDogDataList(vo);
		if ( list != null && list.size() > 0 ) {
			return Constant.SUCCESS_CODE;
		} else
			return Constant.FAIL_CODE;
		
	}
	
	//快速分享设备申请
	void proFastShare(Integer user_id, String device_imei, String belongProject, String msgTxt) throws SystemException {
		Tools tls = new Tools();
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();

		WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> list = null;
		
		//检索设备ID号
		voDeviceActive.setCondition("device_imei = '" + device_imei + "'" );
		list = facadeDeviceActive.getData(voDeviceActive);

		Integer device_id = -1;

		//debug 调试业务，默认开通新imei, 正式上线的时候要去掉这块代码段
		if ( !Constant.STAT_APP_RESTRICT_IMEI  && list.size() == 0 ) {
			voDeviceActive.setDevice_imei(device_imei);
			voDeviceActive.setDevice_name("test");
			//voDeviceActive.setDevice_update_time(new Date());
			//voDeviceActive.setDevice_update_time(new Date());
			/*
			if ( Constant.timeUtcFlag )
				voDeviceActive.setDevice_update_time(tls.getUtcDateStrNowDate());					
			else
				voDeviceActive.setDevice_update_time(new Date());
			*/
			
			voDeviceActive.setDevice_disable("1");
			voDeviceActive.setBelong_project(1);
			facadeDeviceActive.insertWdeviceActiveInfo(voDeviceActive);
			list = facadeDeviceActive.getData(voDeviceActive);
			device_id = (Integer) list.get(0).getAt("device_id");
			voDeviceActive.setDevice_id(device_id);
			voDeviceActive.setTime_zone(myTimeZone);
			voDeviceActive.setEco_mode(Tools.OneString);
			
			facadeDeviceActive.insertwDeviceExtra(voDeviceActive);
		}
		list.clear();
		list = facadeDeviceActive.getData(voDeviceActive);
					
		if ( list.size() > 0 ) {
			device_id = (Integer) list.get(0).getAt("device_id");
			String device_type = (String) list.get(0).getAt("device_type");
			
		
			//检查设备已经被同一个用户添加过的情况
			//先清除以前未处理完的脏数据
			vo.setCondition("is_priority = 0 and user_id = " + user_id + " and device_id = " + device_id + " and status != '1'"  );
			facade.delData(vo);
			
			vo.setCondition("a.is_priority = 0 and a.user_id = " + user_id + " and a.device_id = " + device_id );			
			list = facade.getData(vo);

			
			String admin_userid = String.valueOf(user_id);
			if (list.size() > 0) {
				result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;
		    } else {
		    	list.clear();
				vo.setCondition("a.status=1 and a.device_id = " + device_id + " and a.is_priority = '1'"  );
				list = facade.getData(vo);
				if ( list.size() > 0 ) { //需要向设备主人申请是否同意绑定
					admin_userid = (String) list.get(0).getAt("user_id");

					vo.setUser_id(String.valueOf( user_id ) );
					vo.setTo_user_id(admin_userid);
					vo.setDevice_id(device_id);
					vo.setIs_priority("0");
					vo.setBelong_project(Integer.parseInt(belongProject));
					//String share_time = Tools.getStringFromDate(new Date());
					String share_time = null;					
					//String share_time = Tools.getStringFromDate(new Date());
					//
					if ( Constant.timeUtcFlag )
						share_time = tls.getUtcDateStrNow();					
					else
						share_time = tls.getStringFromDate(new Date());
					
					vo.setShare_date(share_time);
					vo.setStatus("0");	//状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',  
					if ( facade.insertData(vo) > 0 ) {	//成功绑定
						list.clear();
						vo.setCondition("a.user_id='" + vo.getUser_id() + "' and a.to_user_id='" +
								vo.getTo_user_id() + "' and a.device_id=" + vo.getDevice_id() +
								" and a.is_priority='" + vo.getIs_priority() + "'" +
								 " and a.status = '" + vo.getStatus() + "'");
						list.clear();
						list = facade.getData(vo);
						Integer share_id = (Integer) list.get(0).getAt("id");
						json.put(Constant.DEVICE_ID, device_id);													
						json.put(Constant.ADMIN_USERID, admin_userid);		
						json.put(Constant.SHARE_ID, String.valueOf(share_id));
						json.put(Constant.DEVICE_TYPE, device_type);
						result = Constant.SUCCESS_CODE;							
					} else {
						result = Constant.FAIL_CODE;							
					}
					
					
				}
				else {	//设备可以成为申请用户的主设备
					result = Constant.FAIL_CODE;							
				}
		    	
		    }
		} else {
			result = Constant.ERR_INVALID_DEVICE;
		}
			
			
	}

	public void proDelShare(Integer user_id, Integer device_id, Integer member_id) throws SystemException {
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();

		vo.setCondition("a.is_priority=0 and  a.status=1 and a.to_user_id = " + user_id + 
				" and a.user_id = " + member_id +
				" and a.device_id = " + device_id );
		
		List<DataMap> list = facade.getData(vo);
		if (list != null && list.size() > 0) {
			String from_nick = list.get(0).getAt("to_nick").toString().trim();
			String pet_nick = list.get(0).getAt("pet_nick").toString().trim();
			DataMap druMap = list.get(0);
			//String app_type = druMap.getAt("app_type").toString().trim();
			//String ios_token = druMap.getAt("ios_token").toString().trim();
			//String device_token = druMap.getAt("device_token").toString().trim();
			String app_type = druMap.getAt("sapp_type").toString().trim();
			String ios_token = druMap.getAt("sios_token").toString().trim();
			String ios_real = druMap.getAt("sios_real").toString().trim();
			
			String device_token = druMap.getAt("sdevice_token").toString().trim();
			String dest_lang = druMap.getAt("slang").toString().trim();
			
			vo.setCondition("is_priority=0 and  status=1 and to_user_id = " + user_id + 
					" and user_id = " + member_id +
					" and device_id = " + device_id );
	
			if ( facade.delData(vo) > 0 ) {	//操作成功
				updateWdevUserCount(device_id);
				//????? 需要产生消息 ????????
				produceDelShareMsg(user_id, 
						device_id, 
						member_id, 
						from_nick, 
						pet_nick,
						app_type,
						ios_token,
						device_token,
						dest_lang,
						ios_real);
				result = Constant.SUCCESS_CODE;							
			} else {
				result = Constant.FAIL_CODE;							
			}
		} else
			result = Constant.FAIL_CODE;							
	
	}

	void produceShareMsg() throws SystemException {
		
		Integer badge = json.optInt("badge");
    	LocationInfoHelper lih = new LocationInfoHelper();
    	Tools tls = new Tools();
		
		String app_type = json.optString("app_type").trim();
		String ios_token =json.optString("ios_token").trim();
		String device_token = json.optString("device_token").trim();
		String ios_real =json.optString("ios_real").trim();
		
		Integer from_user_id = Integer.parseInt( json.optString("user_id").trim() );
		Integer to_user_id = Integer.parseInt( json.optString("admin_userid").trim() );
		Integer device_id = Integer.parseInt(json.optString("device_id").trim());
		String dest_lang =json.optString("dest_lang").trim();

		String stat = "0";
		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		WMsgInfo wm = new WMsgInfo();
		
		wm.setFrom_nick(json.optString("from_nick"));
		wm.setPet_nick(json.optString("pet_nick"));
		wm.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
		wm.setMsg_ind_id(Constant.CST_MSG_IND_APPLY_SHARE);
		String msg_dat  = null;	//LocationInfoHelper.getDevCurrentTime(device_id);
		if ( Constant.timeUtcFlag )
			msg_dat = tls.getUtcDateStrNow();			
		else 																					
			msg_dat = lih.getDevCurrentTime(device_id);
		

		wm.setMsg_date(msg_dat);
		
		//wm.setMsg_content("");
		wm.setDevice_id(device_id);
		wm.setFrom_usrid(from_user_id);
		wm.setTo_usrid(to_user_id);
		wm.setStatus("1");
		wm.setShare_id(Integer.parseInt(json.optString("share_id").trim()));
		wm.setPet_id(json.optInt("pet_id"));
		wm.setDest_lang(dest_lang);
		String sum = lih.getMsgContentFromMsg(wm, null, msg_dat);
		wm.setMsg_content( sum );
		wm.setHide_flag(Tools.ZeroString);	
		wm.setOld_badge(badge);				
		wm.setSummary(sum);		
		wm.setApp_type(app_type);				
		wm.setDevice_token(device_token);
		wm.setIos_token(ios_token);		
		wm.setIos_real(ios_real);		
		
		infoFacade.insertData(wm);
		
	}

	void produceDelShareMsg(Integer user_id, 
			Integer device_id, 
			Integer member_id, 
			String from_nick, 
			String pet_nick,
			String app_type,
			String ios_token,
			String device_token,
			String dest_lang,
			String ios_real) throws SystemException {

		Tools tls = new Tools();
		Integer from_user_id = user_id;
		Integer to_user_id = member_id;
    	LocationInfoHelper lih = new LocationInfoHelper();

		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		WMsgInfo wm = new WMsgInfo();
		
		wm.setFrom_nick(from_nick);
		wm.setPet_nick(pet_nick);
		wm.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
		wm.setMsg_ind_id(Constant.CST_MSG_IND_HOST_DEL_SHARE);
		String msg_dat = null;	//LocationInfoHelper.getDevCurrentTime(device_id);
		if ( Constant.timeUtcFlag )
			msg_dat = tls.getUtcDateStrNow();			
		else 																					
			msg_dat = lih.getDevCurrentTime(device_id);
		
		wm.setMsg_date(msg_dat);
		
		//wm.setMsg_content("");
		wm.setDevice_id(device_id);
		wm.setFrom_usrid(from_user_id);
		wm.setTo_usrid(to_user_id);
		wm.setStatus("1");
		wm.setDest_lang(dest_lang);
		String sum = lih.getMsgContentFromMsg(wm, null, msg_dat);
		wm.setMsg_content(sum);
		
		wm.setHide_flag(Tools.ZeroString);		
		wm.setSummary(sum);						
		wm.setApp_type(app_type);
		wm.setDevice_token(device_token);
		wm.setIos_token(ios_token);
		wm.setIos_real(ios_real);
		
		infoFacade.insertData(wm);
		
	}
		
	Integer verifyPetIsEmptyDev(Integer pet_id) throws SystemException {	
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		
		vo.setCondition("pet_id=" + pet_id + " and (a.device_id=-1 or a.device_id is null)"  );
		List<DataMap> list = fd.getDogDataList(vo);
		if ( list != null && list.size() > 0 ) {
			myTimeZone = list.get(0).getAt("time_zone").toString().trim();
			return Constant.SUCCESS_CODE;
		} else
			return Constant.FAIL_CODE;
		
	}
	
	Integer proPetIsEmptyDev(Integer pet_id, Integer device_id) throws SystemException {	
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		
		vo.setPet_id(String.valueOf(pet_id));
		vo.setCondition("pet_id=" + pet_id  );
		vo.setDevice_id(device_id);
		
		WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		
		voDeviceActive.setCondition("b.`card_status` IS NOT NULL AND ( NOT (a.iccid IS  NULL)) AND LENGTH(a.iccid) > 0 AND  a.device_id= "+ device_id);
		//getWdevPayInfo
		//abcdd
		List<DataMap> list = null;		
		list = facadeDeviceActive.getWdevPayInfo(voDeviceActive);
		String pay_status = Tools.ZeroString;
		String iccid = "";
		
		
		if ( !list.isEmpty() ) {
			try {
				pay_status = list.get(0).getAt("card_status").toString().trim();
				iccid = list.get(0).getAt("iccid").toString().trim();

				Tools tls = new Tools();
				if ( tls.isNullOrEmpty(pay_status) ) {
					pay_status = Tools.ZeroString;
				}
			} catch(Exception e) {
				pay_status = Tools.ZeroString;				
			}
		} else {
			
			pay_status = Tools.OneString;
		}
				
		//abcde
		
		json.put("pay_status", pay_status);				
		json.put("iccid", iccid);				
		
		//if ( fd.updateDog(vo) > 0 ) {
		if ( fd.updateDogDeviceId(vo) > 0 ) {
			return Constant.SUCCESS_CODE;
		} else
			return Constant.FAIL_CODE;
		
	}

	void updateWdevUserCountAndTimeZone(Integer device_id, 
			String device_imei,
			String old_time_zone) throws SystemException {
		
    	LocationInfoHelper lih = new LocationInfoHelper();
		Tools tls = new Tools();
    	

		if (tls.isNullOrEmpty(old_time_zone))
			return;
		
		Integer bind_count = 0;    //单个设备被多少个用户同时绑定
		WdeviceActiveInfo vo = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		WshareInfo voi = new WshareInfo();						
		WshareInfoFacade fdi = ServiceBean.getInstance().getWshareInfoFacade();

		voi.setCondition("device_id =" + device_id + " and status=1");
		bind_count = fdi.getWdevUserCount(voi);

		vo.setCondition("device_id="+ device_id);
		vo.setBind_count(String.valueOf(bind_count));
		fd.updateWdeviceActiveInfo(vo);
		
		vo.setTime_zone(myTimeZone);
		fd.updateWdeviceActiveInfo(vo);
		/*if ( fd.updateWdeviceActiveInfo(vo) > 0 && */
		/*
				if ( !old_time_zone.equals(myTimeZone) ) {
			ClientSessionManager mClientSessionManager =
					WTDevHandler.getClientSessionMangagerInstance();							
	    	IoSession tempSession = mClientSessionManager.getSessionId(device_imei);
	    	
	    	if ( tempSession == null )
	    		return;
	    	
	    	logger.info("session: " + tempSession.getId() + " dev: " + tempSession.getAttribute("devId") + " change time_zone: " + myTimeZone);
	    	
		    //mClientSessionManager.removeSessionId(serieNo);
			//ServerHandler sh = new ServerHandler();					    	
	    	//sh.sysOffline( tempSession );
		    //tempSession.closeNow();
	    	//LocationInfoHelper lih = new LocationInfoHelper();
	    	//lih.syncDevStatus(tempSession, String.valueOf(device_id), myTimeZone);
	    	
		    tempSession = null;
		    */  
		    
		    
		    //推送更新时区
		    // ....
			JSONObject jon = new JSONObject();
			jon.put("time_zone", myTimeZone);
		    
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEV_ODIFY_TIMEZONE);
			aMsg.setMsg_content(jon.toString());
			aMsg.setDevice_id(device_id );
			if ( Constant.timeUtcFlag )
				aMsg.setMsg_date(tls.getUtcDateStrNow() );
			else {							
				aMsg.setMsg_date(getDeviceNow(device_id));
			}
			aMsg.setHide_flag(Tools.OneString);
			
			lih.proCommonInnerMsg(aMsg, myUserId);		
		
	}
	
	String getEmailFromUserId(String admin_userid) throws SystemException {
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + admin_userid);
		List<DataMap> list = fd.getWappUsers(vo);
		if ( list != null && !list.isEmpty()) {
			return list.get(0).getAt("email").toString().trim();
		} else
			return "";
	}
	
	//查询所有宠物资料
	public JSONObject proQueryAllList(String href, int user_id, String belong_project) throws SystemException{
		
		if ( json == null)
			json = new JSONObject();
		logger.info("查询宠物列表-----");
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		Integer bind_count, unbind_count;
		vo.setCondition("a.user_id=" + user_id + " and a.status='1' ORDER BY a.is_priority DESC, a.share_date DESC"  );
		List<DataMap> list = facade.queryBindList(vo);
		//json.put("bind_count", list.size());
		bind_count = list.size();
		json.put("act_device_id", getActDeviceId(String.valueOf(user_id)));

		JSONArray jsonArr = new JSONArray();		
		
		if(list != null && list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				DataMap dataMap = list.get(i);
				JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));
				System.out.println("item obj pet_id" + itemObj.optString("pet_id"));
				jsonArr.add(itemObj);					
			}
		}
			
		vo.setCondition("(device_id=-1 or device_id is null) and user_id=" + user_id );
		list.clear();
		list = facade.queryUnbindList(vo);
		//json.put("unbind_count", list.size());
		unbind_count = list.size();
		json.put("counts", bind_count + unbind_count);
				
		if(list != null && list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				DataMap dataMap = list.get(i);
				JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));
				System.out.println("item obj1 pet_id" + itemObj.optString("pet_id"));			
				jsonArr.add(itemObj);					
			}						
		}			

		json.put("data_list", jsonArr);
		result = Constant.SUCCESS_CODE;									
		
		return json;
	}

	//获取一个APP用户所有的宠物的数量
	public WappUsers getAllListPetCount(String user_id) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		int count = 0;
		vo.setCondition("a.user_id=" + user_id + " and a.status='1'"  );
		List<DataMap> list = facade.queryBindList(vo);
		// int dev_count, pet_count;
		WappUsers retObj = new WappUsers();
		retObj.setBind_count("0");
		retObj.setPet_count("0");
				
		if(list != null && list.size() > 0){

			count = list.size();
			retObj.setBind_count(String.valueOf(list.size()));
			
		}
		
		vo.setCondition("(device_id=-1 or device_id is null) and user_id=" + user_id  );
		list.clear();
		list = facade.queryUnbindList(vo);

		if(list != null ){
			count += list.size();			
		}			

		retObj.setPet_count(String.valueOf(count));
		
		return retObj;
	}

	
    public int getActDeviceId(String user_id) throws SystemException{
		Tools tls = new Tools();
  		
    	WappUsers vo = new WappUsers();
    	WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
    	vo.setCondition("user_id="+user_id.trim() );
    	List<DataMap> list = fd.getWappUsers(vo);
    	if (list.size() == 1 ) {
    		if( tls.isNullOrEmpty(list.get(0).getAt("act_device_id")) )
    			return -1;
    		else
    			return  (Integer) list.get(0).getAt("act_device_id") ;
    	} else
    		return 0;
    	
    }
	

	//查询绑定设备列表
	JSONObject proQueryAllUsers( int user_id, int device_id) throws SystemException{
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();
		//vo.setCondition("a.to_user_id=" + user_id + " and a.status=1 and a.is_priority=0 and a.device_id=" + device_id  );
		vo.setCondition("a.status=1 and a.is_priority=0 and a.device_id=" + device_id  );

		List<DataMap> list = facade.getAllUsers(vo);
		json.put("counts", list.size());

		JSONArray jsonArr = new JSONArray();		
		
		if(list != null && list.size() > 0){
			
			for(int i=0;i<list.size();i++){
				DataMap dataMap = list.get(i);
				JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
				jsonArr.add(itemObj);					
			}
		}

		json.put("user_list", jsonArr);
		result = Constant.SUCCESS_CODE;									
		
		return json;
	}
    
	//检测设备是否有新固件
	JSONObject proQueryDevFirm( int user_id, int device_id, HttpServletResponse response) 
			throws SystemException{
		
		//result = Constant.SUCCESS_CODE;									
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}*/								
		try {
			if (Constant.cmdDirectResFlag) //true
				devQueryFirmFast(user_id, device_imei, response);			
			else
				devQueryFirm(user_id, device_imei, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	void devQueryFirm(Integer user_id, String  device_imei, HttpServletResponse response) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.detectDevUpRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			if ( !cmdDownSetImpl.detectDevUp(device_imei, true, user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			}
			
		} catch(Exception e) {
			
		}
	}

	//通知设备固件升级
	JSONObject proUpDevFirm( int user_id, int device_id, Integer updateFlag,
			String updateVer, HttpServletResponse response) 
			throws SystemException{
		
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*		
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}
		*/								
		
	    devUpFirm(user_id, device_imei, updateFlag,
				updateVer, response, device_id);
		
	    //if ( result == Constant.SUCCESS_CODE )
	    //	ctlWdevUFirmState(device_id, true);
	    
		return json;
		
	}
	
	void devUpFirm(Integer user_id, String  device_imei, Integer updateFlag,
			String updateVer, HttpServletResponse response, int device_id) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.updateFirmwareRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			boolean res = cmdDownSetImpl.updateFirmware(device_imei, updateFlag, updateVer, user_id);
			if ( res ) {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData rej = new ReqJsonData();
				rej.setUserId(user_id);
				dna.proUpdateStart(device_id, device_imei, null, rej, "");				
			}

			//abcde
			JSONObject jon = new JSONObject();
			jon.put("device_id", device_id);
			//Thread lock1 = new Thread();
			CmdUpFirmRun cpr = new CmdUpFirmRun(jon.toString(), lock, user_id);
			Thread tcpr=new Thread(cpr); 
			tcpr.start();			
						
			/*
			synchronized(lock){
				lock.wait(1000* 60 * 2);
			    //或者wait()
			}*/

			result = Constant.SUCCESS_CODE;
			
		} catch(Exception e) {
			result = Constant.FAIL_CODE;
		}
	}
	
	public void ctlWdevUFirmState(Integer device_id, boolean state) {
		try {
			WdeviceActiveInfo vo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
					
			vo.setCondition("device_id="+ device_id);
			if ( state )
				vo.setUfirm_stat(1);
			else
				vo.setUfirm_stat(0);
			
			fd.updatewDeviceExtra(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//休眠时段控制
	JSONObject proSleepCtl( int user_id, int device_id, Integer flag, HttpServletResponse response) 
			throws SystemException{
		
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}*/								
		
		devSleepCtl(user_id, device_imei, flag, response);
		
		return null;
		
	}
	
	public void devSleepCtl(Integer user_id, String  device_imei, Integer flag, HttpServletResponse response) {
		try {
			Tools tls = new Tools();
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.setSleepStateRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			if ( !cmdDownSetImpl.setSleepState(device_imei, tls.getBooleanFromInt(flag), user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			}
			
		} catch(Exception e) {
			
		}
	}
	
	//定时开关机控制
	JSONObject proTOnOffCtl( int user_id, int device_id, Integer tflag, 
			String ton, String toff, HttpServletResponse response) 
			throws SystemException{
		
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		/*
	    try { 
			WTSigninAction  sa = new WTSigninAction();
			sa.heartBeat(String.valueOf(user_id));	
			sa = null;
			// end
		} catch(Exception e) {		
			return null;
			
		}*/								
		
		devTOnOffCtl(user_id, device_id, device_imei, tflag, ton, toff,  response);
		
		return null;
		
	}

	public void devTOnOffCtl(Integer user_id, Integer device_id, String  device_imei, Integer flag, 
			String ton, String toff, HttpServletResponse response) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.setOffOnRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setTon(ton);
			cmd.setToff(toff);
			cmd.setDevice_id(device_id);
			cmd.setTflag(flag);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);

			if ( Constant.TOFOFF_AUTO_DEBUG ) { // false
		        Timer timer = new Timer();  
		        timer.schedule(new DMATestTimerTask(), 1000* 5 );
			}		
			
			if ( !cmdDownSetImpl.setDevOffOn(device_imei, flag, toff, ton,  user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//APP 温度监测管理
	public JSONObject proTmCtl( int user_id, int device_id, Integer dur, 
			 boolean flag, HttpServletResponse response) 
			throws SystemException{
		
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		int iflag = 0;
		if (flag) iflag = 1;
		devTmCtl(user_id, device_id, device_imei, iflag, dur, response);
		
		return null;
		
	}

	public void devTmCtl(Integer user_id, Integer device_id, String  device_imei, Integer flag, 
			int dur, HttpServletResponse response) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = null;		//new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.ssTmRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setDur(dur);
			cmd.setDevice_id(device_id);
			cmd.setTflag(flag);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);

			boolean bflag = false;
			
			if (flag > 0) bflag = true;
			
			if ( !cmdDownSetImpl.setSsTm(device_imei, bflag, user_id, dur, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			} else {
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}

			
		} catch(Exception e) {
			
		}
	}
	
	//电话回拨
	public JSONObject proRcmCtl( int user_id, 
			int device_id, 
			String imei,
			String pno,
			HttpServletResponse response) 
			throws SystemException{
		
		rcmCtl(user_id, device_id, 
				imei, 
				pno,
				response);
		
		return null;		
	}
	
	public void rcmCtl(Integer user_id, 
			Integer device_id, 
			String  device_imei, 
			String pno,
			HttpServletResponse response) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			Thread lock = null;		//new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.sCpRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setsCp(pno);
			cmd.setDevice_id(device_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			if ( !cmdDownSetImpl.sCp(device_imei, pno, user_id, lock)) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());					
			} else {
				json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
			}
						
		} catch(Exception e) {
			
		}
	}

	public class DMATestTimerTask extends TimerTask {
		
		public DMATestTimerTask() { 
		}
		
		@Override		
		public void run() {
			try {
								
				DevNotifyApp dna = new DevNotifyApp();				
				ReqJsonData reqJsonData = new ReqJsonData();
				reqJsonData.setUserId(1);
				
				dna.proSetTOnOffRes(80, "352138064952338", null, reqJsonData, 1);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void devQueryFirmFast(Integer user_id, String  device_imei, HttpServletResponse response) throws IOException {
		int errorCode = 0;
		int upFlag = 0;
		String upVer = null;
		String devVer = null;
		String devVerBranch = null;
		String devVerArea = null;
		int device_id = 0;
		String old_firm  = null;
		try {
			device_id = getDeviceIdFromDeviceImei(device_imei);
			String version_us = null;
			String vus_f = null;
			String veu_f = null;
			String version_eu = null;

			WupFirmware wupFirmware = new WupFirmware();
			WupFirmwareFacade upFirmFacade = ServiceBean.getInstance().getWupFirmwareFacade();
			wupFirmware.setCondition("project_name = 'YK902'");
			
			List<DataMap> firmwareInfos = upFirmFacade.getWupFirmware(wupFirmware);
			
			if (firmwareInfos.size() > 0) {
				version_us = firmwareInfos.get(0).getAt("nver_us").toString().trim();
				
				version_eu = firmwareInfos.get(0).getAt("nver_eu").toString().trim();
				vus_f = firmwareInfos.get(0).getAt("nver_usf").toString().trim();
				veu_f = firmwareInfos.get(0).getAt("nver_euf").toString().trim();

				Tools tls = new Tools();
				String dummyVer = null;
				String dummyVerEu = null;
				
				String dummySn = tls.getDummySn();
				if ( !tls.isNullOrEmpty(dummySn) && dummySn.equals(device_imei) ) {
					dummyVer = tls.getDummyVer();
					dummyVerEu = tls.getDummyVerEu();
					version_us = dummyVer.substring(17, 21); // YK902_80M_DE001B_V100_US_201703011806				
					vus_f = dummyVer;
					veu_f = dummyVerEu;
				}
				tls = null;
								
			} else {
				version_us = "V102_US";
			}
			
			PhoneInfo phoneInfo = new PhoneInfo();
			
			phoneInfo.setCondition("serie_no = '"+device_imei+"'");
			
			PhoneInfoFacade phoneInfoFacade = ServiceBean.getInstance().getPhoneInfoFacade();
			List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
			if (!phoneInfos.isEmpty()) {  
				old_firm = phoneInfos.get(0).getAt("firmware_edition").toString().trim();
				
				
				devVer = old_firm.substring(17, 24); // YK902_80M_DE001B_V100_US_201703011806
				devVerBranch = old_firm.substring(17, 21); // YK902_80M_DE001B_V100_US_201703011806
				devVerArea = old_firm.substring(22, 24); // YK902_80M_DE001B_V100_US_201703011806

				if (!version_us.equals(devVerBranch)) {
					//upVer = version_us + "_" + devVerArea;
					if ( "US".equals(devVerArea) )
						upVer = vus_f;
					else if ( "EU".equals(devVerArea) )
						upVer = veu_f;						
					upFlag = 1;
					errorCode = 1;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
		json.put("errorCode", errorCode);
		json.put("upFlag", upFlag);
		json.put("upVer", upVer);
		json.put("devVer", old_firm/*devVer*/);
		json.put("user_id", user_id);
		json.put("device_id", device_id);

		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());

	}

	public static void main(String[] args) throws Exception {
		//abab
		WTAppDeviceManAction wdma = new WTAppDeviceManAction();
		wdma.devQueryFirmFast(1, "352138064955679", null);
	}
		
	//添加设备
	//与 proReqBind的区别在于，不允许绑定空设备
	public JSONObject proReqBind3(String href, int user_id, String device_imei, String belong_project) throws SystemException{
		Tools tls = new Tools();
		
		WshareInfo vo = new WshareInfo();						
		WshareInfoFacade facade = ServiceBean.getInstance().getWshareInfoFacade();

		WdeviceActiveInfo voDeviceActive = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade facadeDeviceActive = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> list = null;
		
		//检索设备ID号
		voDeviceActive.setCondition("device_imei = '" + device_imei + "'" );
		list = facadeDeviceActive.getData(voDeviceActive);
		
		String old_time_zone = null;

		Integer device_id = -1;

		//debug 调试业务，默认开通新imei, 正式上线的时候要去掉这块代码段
		if ( !Constant.STAT_APP_RESTRICT_IMEI  && list.size() == 0 ) {
			voDeviceActive.setDevice_imei(device_imei);
			voDeviceActive.setDevice_name("test");
			//voDeviceActive.setDevice_update_time(new Date());
			/*
			if ( Constant.timeUtcFlag )
				voDeviceActive.setDevice_update_time(tls.getUtcDateStrNowDate());			
			else
				voDeviceActive.setDevice_update_time(new Date());
			*/
			
			voDeviceActive.setDevice_disable("1");
			voDeviceActive.setBelong_project(1);
			facadeDeviceActive.insertWdeviceActiveInfo(voDeviceActive);
			list = facadeDeviceActive.getData(voDeviceActive);
			device_id = (Integer) list.get(0).getAt("device_id");
			voDeviceActive.setDevice_id(device_id);
			voDeviceActive.setTime_zone(myTimeZone);
			voDeviceActive.setEco_mode(Tools.OneString);			
			facadeDeviceActive.insertwDeviceExtra(voDeviceActive);

			list.clear();
			list = facadeDeviceActive.getData(voDeviceActive);			
		}
					
		if ( list.size() > 0 ) {
			device_id = (Integer) list.get(0).getAt("device_id");
			String device_type = (String) list.get(0).getAt("device_type");
			old_time_zone = list.get(0).getAt("time_zone").toString().trim();
					
			//检查设备已经被同一个用户添加过的情况
			//先清除以前未处理完的脏数据
			vo.setCondition("user_id = " + user_id + " and device_id = " + device_id + " and status != '1'"  );
			facade.delData(vo);

			WTAppMsgManAction amma = new WTAppMsgManAction();
			amma.proPushStatusByApplyer(user_id, device_id);
			amma = null;
						
			vo.setCondition("a.user_id = " + user_id + " and a.device_id = " + device_id );			
			list = facade.getData(vo);
			
			String admin_userid = String.valueOf(user_id);
			if (list.size() > 0) {
				//ERR_APPADD_DEVICE_OTHER_BINDED				
				result = Constant.ERR_APPADD_DEVICE_HAS_BINDED;
		    } else {
		    	list.clear();
				vo.setCondition("a.device_id = " + device_id + " and a.is_priority = '1'"  );
				list = facade.getData(vo);
				if ( list.size() > 0 ) { //需要向设备主人申请是否同意绑定
					admin_userid = (String) list.get(0).getAt("user_id");

					vo.setUser_id(String.valueOf( user_id ) );
					vo.setTo_user_id(admin_userid);
					vo.setDevice_id(device_id);
					vo.setIs_priority("0");
					vo.setBelong_project(Integer.parseInt(belong_project));
					String share_time = null;
					//share_time = Tools.getStringFromDate(new Date());
					if ( Constant.timeUtcFlag )
						share_time = tls.getUtcDateStrNow();					
					else
						share_time = tls.getStringFromDate(new Date());
					
					
					vo.setShare_date(share_time);
					vo.setStatus("0");	//状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',  
					if ( facade.insertData(vo) > 0 ) {	//成功绑定
						list.clear();
						vo.setCondition("a.user_id='" + vo.getUser_id() + "' and a.to_user_id='" +
								vo.getTo_user_id() + "' and a.device_id=" + vo.getDevice_id() +
								" and a.is_priority='" + vo.getIs_priority() + "'" +
								 " and a.status = '" + vo.getStatus() + "'");
						list.clear();
						list = facade.getData(vo);
						DataMap druMap = list.get(0);
						String app_type = druMap.getAt("app_type").toString().trim();
						String ios_token = druMap.getAt("ios_token").toString().trim();
						String device_token = druMap.getAt("device_token").toString().trim();						
						String ios_real = druMap.getAt("ios_real").toString().trim();
						String dest_lang = druMap.getAt("lang").toString().trim();
						String badge = druMap.getAt("badge").toString().trim();
						
						Integer share_id = (Integer) list.get(0).getAt("id");
						
						if (json == null ) {
							json = new JSONObject();
						}
						json.put(Constant.DEVICE_ID, device_id);													
						json.put(Constant.ADMIN_USERID, admin_userid);
						json.put("admin_email", getEmailFromUserId(admin_userid));
						json.put(Constant.SHARE_ID, String.valueOf(share_id));
						json.put(Constant.DEVICE_TYPE, device_type);
						json.put("user_id", user_id);
						//json.put("pet_id", getPetIdFromDeviceId(String.valueOf(device_id)));
						json.put("pet_id", list.get(0).getAt("pet_id") );
						json.put("from_nick", list.get(0).getAt("from_nick").toString().trim());
						json.put("pet_nick", list.get(0).getAt("pet_nick").toString().trim());
						json.put("app_type", app_type);
						json.put("device_token", device_token);
						json.put("ios_token", ios_token);
						json.put("ios_real", ios_real);
						json.put("dest_lang", dest_lang);
						json.put("badge", badge);
						
						produceShareMsg();
						result = Constant.SUCCESS_CODE;							
					} else {
						result = Constant.FAIL_CODE;							
					}
				}
				else {	//设备可以成为申请用户的主设备， 修改为不允许绑定控设备
					result = Constant.ERR_EMPTY_DEVICE;							
				}		    	
		    }
		} else {
			result = Constant.ERR_INVALID_DEVICE;
		}
			
		json.put("request", href);
		return json;
				
	}
	
}
