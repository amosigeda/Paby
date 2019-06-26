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
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class WTAppSafeAreaManAction extends BaseAction {
	
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppSafeAreaManAction");			

			int device_id = object.getInt("device_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if ( ( result = verifyUserDevice(String.valueOf(user_id), 
							String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {					
						if (cmd.equals("add")) {	//cmd:add,新增电子围栏
							proAdd(object);
						} else if (cmd.equals("update")) {	//cmd:update:修改电子围栏
							proUpdate(object);
						} else if (cmd.equals("del")) {		//cmd:del:删除安全区域
							proDel(object);							
						} else if (cmd.equals("queryList")) {	//cmd:queryList:获取安全区域列表
							proQueryList(object);							
						} else {
							result = Constant.ERR_INVALID_PARA;								
						}
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
	
	
	public synchronized void proAdd(JSONObject object) {
		Tools tls = new Tools();		
		
		WeFencing wf = new WeFencing();
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		//JSONObject object = JSONObject.fromObject(jsonStr);

		int user_id = tls.getSafeIntFromJson(object, "user_id");
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		
		
		
		String lat = tls.getSafeStringFromJson(object, "lat");
		String lon = tls.getSafeStringFromJson(object, "lon");
		//String device_safe_id = Tools.getSafeStringFromJson(object, "device_safe_id");
		String device_safe_range = tls.getSafeStringFromJson(object, "device_safe_range");
		String device_safe_name = tls.getSafeStringFromJson(object, "device_safe_name");
		String device_safe_addr = tls.getSafeStringFromJson(object, "device_safe_addr");
		String device_safe_effect_time = tls.getSafeStringFromJson(object, "device_safe_effect_time");
		String safe_type = tls.getSafeStringFromJson(object, "safe_type");

		wf.setCondition("device_id=" + device_id );
		if ( infoFacade.getWsafeAreaCount(wf) >= Constant.MAX_SAFEAREA_NUMS ) {
			result = Constant.ERR_SAFEAREA_BEYOND_MAX_VAL;
			return;
		}
		
		wf.setDevice_id(device_id);
		if(!tls.isNullOrEmpty(lat)){
			wf.setCenter_gps_la(Double.parseDouble(lat));
		}
		if(!tls.isNullOrEmpty(lon)){
			wf.setCenter_gps_lo(Double.parseDouble(lon));
		}
		if(!tls.isNullOrEmpty(device_safe_addr)){
			wf.setCenter_addr(device_safe_addr);
		}
		if(!tls.isNullOrEmpty(device_safe_range)){
			wf.setRound_distance(Integer.parseInt(device_safe_range));
		}
		if(!tls.isNullOrEmpty(device_safe_name)){
			wf.setDevice_safe_name(device_safe_name);
		}
		if(!tls.isNullOrEmpty(device_safe_effect_time)){
			wf.setDevice_safe_effect_time(device_safe_effect_time);
		}
		if(!tls.isNullOrEmpty(safe_type)){
			wf.setSafe_type(safe_type);
		}
		Integer nextId = infoFacade.getSafeAreaNextId(wf);
		int res = infoFacade.insertAppSafeAreaMan(wf);
		if(res < 0){
			result = Constant.FAIL_CODE;
		}else{
			if ( json == null )
				json = new JSONObject();				
			json.put("device_safe_id", nextId);
			wf.setId(nextId);
			
			SafePushAddRunnable ph = new SafePushAddRunnable(wf);
			ph.run();
			result = Constant.SUCCESS_CODE;
		}
				
	}

	class SafePushDelRunnable implements Runnable {  
		private WeFencing wefg;
		protected SafePushDelRunnable(WeFencing rWefg) {
			wefg = rWefg;
	    }  
	    
	    public void run() {
			Tools tls = new Tools();		
			WTSigninAction ba = new WTSigninAction();
	    	
	    	try {
	    		Integer device_id =  wefg.getDevice_id();
				WMsgInfo aMsg = new WMsgInfo();
				JSONObject jobj = new JSONObject();
				jobj.put("device_safe_id", wefg.getId());
				aMsg.setMsg_content(jobj.toString());
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ESAFE_DEL_NTFY);
				aMsg.setHide_flag(Tools.OneString);
				aMsg.setDevice_id( device_id );
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {															
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}
		    	LocationInfoHelper lih = new LocationInfoHelper();
				
				lih.proCommonInnerMsg(aMsg, 0);
	    	} catch(Exception e) {
	    		e.printStackTrace();
				ba.insertVisit(null, null, String.valueOf(wefg.getDevice_id()), "exception SafePushDelRunnable exception:");					    		
	    	}
	    	
	    }
	}

	
	class SafePushAddRunnable implements Runnable {  
		private WeFencing wefg;
		protected SafePushAddRunnable(WeFencing rWefg) {
			wefg = rWefg;
	    }  
	    
	    public void run() {
    		Tools tls = new Tools();		
    		WTSigninAction ba = new WTSigninAction();

	    	try {
	    		
	    		Integer device_id =  wefg.getDevice_id();
				WMsgInfo aMsg = new WMsgInfo();
				JSONObject jobj = new JSONObject();
				jobj.put("device_safe_id", wefg.getId());
				jobj.put("lat", wefg.getCenter_gps_la() );
				jobj.put("lon", wefg.getCenter_gps_lo());
				jobj.put("safe_range", wefg.getRound_distance());
				jobj.put("safe_name", wefg.getDevice_safe_name());
				jobj.put("safe_addr", wefg.getCenter_addr());
				aMsg.setMsg_content(jobj.toString());
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ESAFE_ADD_NTFY);
				aMsg.setHide_flag(Tools.OneString);
				aMsg.setDevice_id( device_id );
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {															
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}
		    	LocationInfoHelper lih = new LocationInfoHelper();
				
				lih.proCommonInnerMsg(aMsg, 0);
	    	} catch(Exception e) {
	    		e.printStackTrace();
				ba.insertVisit(null, null, String.valueOf(wefg.getDevice_id()), "exception SafePushAddRunnable exception:");					    		
	    	}
	    	
	    }
	}

	
	
	void proUpdate(JSONObject object) {
		Tools tls = new Tools();		
		
		WeFencing wf = new WeFencing();
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		//JSONObject object = JSONObject.fromObject(jsonStr);

		int user_id = tls.getSafeIntFromJson(object, "user_id");
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		String device_safe_id = tls.getSafeStringFromJson(object, "device_safe_id");
		
		String lat = tls.getSafeStringFromJson(object, "lat");
		String lon = tls.getSafeStringFromJson(object, "lon");
		String device_safe_range = tls.getSafeStringFromJson(object, "device_safe_range");
		String device_safe_name = tls.getSafeStringFromJson(object, "device_safe_name");
		String device_safe_addr = tls.getSafeStringFromJson(object, "device_safe_addr");
		String device_safe_effect_time = tls.getSafeStringFromJson(object, "device_safe_effect_time");
		String safe_type = tls.getSafeStringFromJson(object, "safe_type");

		wf.setDevice_id(device_id);
		if(!tls.isNullOrEmpty(lat)){
			wf.setCenter_gps_la(Double.parseDouble(lat));
		}
		if(!tls.isNullOrEmpty(lon)){
			wf.setCenter_gps_lo(Double.parseDouble(lon));
		}
		if(!tls.isNullOrEmpty(device_safe_addr)){
			wf.setCenter_addr(device_safe_addr);
		}
		if(!tls.isNullOrEmpty(device_safe_range)){
			wf.setRound_distance(Integer.parseInt(device_safe_range));
		}
		if(!tls.isNullOrEmpty(device_safe_name)){
			wf.setDevice_safe_name(device_safe_name);
		}
		if(!tls.isNullOrEmpty(device_safe_effect_time)){
			wf.setDevice_safe_effect_time(device_safe_effect_time);
		}
		if(!tls.isNullOrEmpty(safe_type)){
			wf.setSafe_type(safe_type);
		}

		wf.setCondition(" id = "+device_safe_id);
		int res = infoFacade.updateAppSafeAreaMan(wf);
		if(res < 0){
			result = Constant.FAIL_CODE;
		}else{
			result = Constant.SUCCESS_CODE;
		}
		
		
	}
	
	public void proDel(JSONObject object) {
		WeFencing wf = new WeFencing();
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		//JSONObject object = JSONObject.fromObject(jsonStr);

		Integer device_safe_id = object.getInt( "device_safe_id");
		
		wf.setCondition(" id = "+device_safe_id);
		
		wf.setCondition(" id =  "+device_safe_id);
		List<DataMap> list = infoFacade.queryWeFencing(wf);

		if ( list != null && list.size() == 1 ) {
			Integer device_id = (Integer) list.get(0).getAt("device_id");
			int res = infoFacade.deleteAppSafeAreaMan(wf);
			if(res < 0){
				result = Constant.FAIL_CODE;
			}else{
				wf.setId(device_safe_id);
				wf.setDevice_id(device_id);
				SafePushDelRunnable ph = new SafePushDelRunnable(wf);
				ph.run();
				
				result = Constant.SUCCESS_CODE;
			}
		}
		
	}

	void proQueryList(JSONObject object) {
		Tools tls = new Tools();
		
		WeFencing wf = new WeFencing();
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		//JSONObject object = JSONObject.fromObject(jsonStr);

		int user_id = tls.getSafeIntFromJson(object, "user_id");
		int device_id = tls.getSafeIntFromJson(object, "device_id");
		
		wf.setCondition(" device_id =  "+device_id);
		List<DataMap> fenceList = infoFacade.queryWeFencing(wf);
		if(fenceList != null /*&& fenceList.size() > 0*/){
			JSONArray jsonArr = new JSONArray();
			for(int i=0;i<fenceList.size();i++){
				DataMap fenceMap = fenceList.get(i);
				JSONObject listJson = new JSONObject();
				listJson.put("device_safe_id", fenceMap.getAt("id"));
				listJson.put("lat", fenceMap.getAt("center_gps_la"));
				listJson.put("lon", fenceMap.getAt("center_gps_lo"));
				listJson.put("device_safe_range", fenceMap.getAt("round_distance"));
				listJson.put("device_safe_name", fenceMap.getAt("device_safe_name"));
				listJson.put("device_safe_addr", fenceMap.getAt("center_addr"));
				listJson.put("device_safe_effect_time", fenceMap.getAt("device_safe_effect_time"));
				listJson.put("photo", fenceMap.getAt("photo"));
				listJson.put("pst", fenceMap.getAt("pst"));
				
				jsonArr.add(listJson);
			}
			json.put("safe_count", fenceList.size());
			json.put("safe_list", jsonArr);
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
		
	}
	
	
	
}