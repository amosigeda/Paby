﻿package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
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
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceTrackFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTCheckVersionFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class WTAppGpsManAction extends BaseAction {
	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTSigninAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	
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
			
			logger.info("WTAppGpsManAction params:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppGpsManAction:" + cmd);			
			
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
								
					if (cmd.equals("getDevsList")) {	//APP用户获取所有设备当前位置数据列表
						int deviceId = tls.getSafeIntFromJson(object, "device_id");
						logger.info("deviceId= " + deviceId);
						if(deviceId > 0){
							if (proGetDev(user_id,deviceId))
								result = Constant.SUCCESS_CODE;																	
						}else{
							proGetDevsList(user_id);
							result = Constant.SUCCESS_CODE;																	
						}
												
					} else if (cmd.equals("getDevLct")) {	//单个设备手动定位
						int deviceId = tls.getSafeIntFromJson(object, "device_id");
						if(deviceId > 0){
							if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {
								proGetDevLoc16(user_id,deviceId, response);								
								return null;
							} else {
								proGetDevLoc15(user_id,deviceId);								
							}
							 
						}else{
							result = Constant.FAIL_CODE;																	
						}
												
					} else if (cmd.equals("getDevTrack")) {	//APP获取单个设备历史轨迹数据
						int device_id = object.optInt("device_id");
						if ( ( result = verifyUserDevice(String.valueOf(user_id), 
							String.valueOf(device_id))) == Constant.SUCCESS_CODE ) {
							
							if (Constant.IS_GOOGLE_ROADS_API_USED)
								proGetDevTrackRoad(sb.toString());								
							else
								proGetDevTrack(sb.toString());
						}
						//result = Constant.SUCCESS_CODE;																	
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
	
	public void proGetDevsList(int user_id) throws Exception {
		Tools tls = new Tools();
		
		if ( json == null ) 
			json = new JSONObject();
		
		//调用此协议认为将同时产生心跳信号
		//WTSigninAction  sa = new WTSigninAction();
		//sa.heartBeat(String.valueOf(user_id) );	
		//sa = null;
		// end
		
		LocationInfo vo = new LocationInfo();
		LocationInfoFacade fd = ServiceBean.getInstance().getLocationInfoFacade();
		vo.setCondition("a.user_id=" + user_id + " and a.status='1' order by a.is_priority desc, e.order_by desc, e.dev_status desc, a.share_date desc");
		List<DataMap> list = null;

		if ( ( user_id == 1) || (user_id == 7) ) 
			list =  fd.wtAppGpsManGetDevsListMgr(vo);
		else
			list = fd.wtAppGpsManGetDevsList(vo);
		vo = null;

		JSONArray jsonArr = new JSONArray();

		json.put("device_count", list.size());
		
		for(int i=0;i<list.size();i++) {
			DataMap trackMap = list.get(i);
			JSONObject trackJson = JSONObject.fromObject(hashMapToJson(trackMap));
			
			try {
				//if ( !"1".equals(trackJson.optString("device_online").trim() ) ) {
					int device_id = trackJson.optInt("device_id");
					String device_imei = trackJson.optString("device_imei").toString().trim();
					String iccid = trackJson.optString("iccid").toString().trim();
					String test_status = trackJson.optString("test_status");

					if ( tls.isNullOrEmpty(test_status) )
						test_status = Tools.OneString;
					//String dynIccid = trackJson.optString("dynIccid").toString().trim();

					/*
					if (tls.isNullOrEmpty(iccid)) {
						iccid = dynIccid;
					}*/
					
					trackJson.put("iccid", iccid);
					
					try {
			
						if ( tls.isNullOrEmpty(iccid) ) {
							trackJson.put("isBwtSim", 0);
							trackJson.put("bwtDevStat", 0);						
						} else {						
							DeviceActiveInfo  dai = new DeviceActiveInfo();
							dai.setCondition("iccid='" + iccid + "'");
							DeviceActiveInfoFacade daiFd = ServiceBean.getInstance().getDeviceActiveInfoFacade();
							List<DataMap> listDF = daiFd.getSsidInfo(dai);
							if ( listDF.isEmpty() ) {
								trackJson.put("isBwtSim", 0);
								trackJson.put("bwtDevStat", 0);						
							} else {
								String card_status = listDF.get(0).getAt("card_status").toString().trim();

								if ( tls.isNullOrEmpty(card_status) || 
										Tools.ZeroString.equals(card_status)) {
									trackJson.put("isBwtSim", 1);
									trackJson.put("bwtDevStat", 0);						
								} else {
									trackJson.put("isBwtSim", 1);
									trackJson.put("bwtDevStat", 1);																
								}
							}
							
						}
					} catch(Exception e) {
						logger.info("dev:" + device_imei + " set app isBwtSim error!");
					}
										
					if ( Tools.OneString.equals(test_status)) {
						trackJson.put("isBwtSim", 1);
						trackJson.put("bwtDevStat", 1);																						
					}
					
					if (device_id == 570) {
						//logger.info("hyong imei: " + device_imei + " return status offline");
					} 						
				//}
			} catch(Exception e) {
				logger.info("proGetDevsList exception case 1");
			}
			
			jsonArr.add(trackJson);
		}

		//json.put("time", Tools.getStringFromDate(new Date()));
		if ( Constant.timeUtcFlag )		
			json.put("time", tls.getUtcDateStrNow());
		else
			json.put("time", tls.getStringFromDate(new Date()));
			
		json.put("act_device_id", getActDeviceId(String.valueOf(user_id)));
		json.put("device_list", jsonArr);
		
		try {
			WTCheckVersionFacade infoFacade = ServiceBean.getInstance().getWtCheckVersionFacade();
			WcheckInfo wi = new WcheckInfo();
	    		    	
			wi.setCondition(" package_name = 'androidPetDog' or package_name='iosPetDog'");
			List<DataMap> lp = infoFacade.queryCheckVersionInfo(wi);
			if(list != null/* && list.size() > 0 */){

				for(int i=0;i<lp.size()&& i< 2;i++){
					DataMap versionMap = lp.get(i);
					String version_code = versionMap.getAt("version_code").toString().trim();
					//String download_path = versionMap.getAt("download_path").toString().trim();
					String pn = versionMap.getAt("package_name").toString().trim();
					if ( "androidPetDog".equals(pn)  )  
						json.put("av", version_code);
					else if ( "iosPetDog".equals(pn) )
						json.put("iv", version_code);					
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
		logger.info("proGetDevsList json=" + json);
	}
	
	//获取单个设备当前位置数据信息
	Boolean proGetDev(int user_id,int device_id) throws Exception {
		logger.info("inter proGetDev():" + user_id + "," + device_id);
		LocationInfo vo = new LocationInfo();
		LocationInfoFacade fd = ServiceBean.getInstance().getLocationInfoFacade();

		List<DataMap> list = null;

		vo.setCondition("a.user_id=" + user_id + " and a.device_id = "+
				device_id+" and a.status='1' order by a.is_priority desc, a.share_date desc");
		list = fd.wtAppGpsManGetDevsList(vo);
		if( list != null && list.size() > 0 ) {
			result = Constant.SUCCESS_CODE;
		}				
		
		JSONArray jsonArr = new JSONArray();

		DataMap trackMap = list.get(0);
		JSONObject trackJson = JSONObject.fromObject(hashMapToJson(trackMap));
		
		String test_status = Tools.OneString;
		
		Tools tls = new Tools();
		try {
			//if ( !"1".equals(trackJson.optString("device_online").trim() ) ) {
				String device_imei = trackJson.optString("device_imei").toString().trim();
				String iccid = trackJson.optString("iccid").toString().trim();

				test_status = trackJson.optString("test_status");

				if ( tls.isNullOrEmpty(test_status) )
					test_status = Tools.OneString;								
				try {
		
					if ( tls.isNullOrEmpty(iccid) ) {
						trackJson.put("isBwtSim", 0);
						trackJson.put("bwtDevStat", 0);						
					} else {						
						DeviceActiveInfo  dai = new DeviceActiveInfo();
						dai.setCondition("iccid='" + iccid + "'");
						DeviceActiveInfoFacade daiFd = ServiceBean.getInstance().getDeviceActiveInfoFacade();
						List<DataMap> listDF = daiFd.getSsidInfo(dai);
						if ( listDF.isEmpty() ) {
							trackJson.put("isBwtSim", 0);
							trackJson.put("bwtDevStat", 0);						
						} else {
							String card_status = listDF.get(0).getAt("card_status").toString().trim();
							if ( tls.isNullOrEmpty(card_status) || 
									Tools.ZeroString.equals(card_status)) {
								trackJson.put("isBwtSim", 1);
								trackJson.put("bwtDevStat", 0);						
							} else {
								trackJson.put("isBwtSim", 1);
								trackJson.put("bwtDevStat", 1);																
							}
						}
						
					}
				} catch(Exception e) {
					logger.info("dev:" + device_imei + " set app isBwtSim error case 2!");
				}
								
			//}
		} catch(Exception e) {
			logger.info("proGetDevList exception case 2");
		}
		
		if ( Tools.OneString.equals(test_status)) {
			trackJson.put("isBwtSim", 1);
			trackJson.put("bwtDevStat", 1);																						
		}
				
		jsonArr.add(trackJson);
		
		//json.put("device_count", list.size());
		//json.put("time", Tools.getStringFromDate(new Date()));
		json.put("act_device_id", getActDeviceId(String.valueOf(user_id)));
		json.put("device_list", jsonArr);
		
		return true;
	}

	//单个设备手动定位
	Boolean proGetDevLoc15(int user_id,int device_id) throws Exception {
		Tools tls = new Tools();
		
		//调用此协议认为将同时产生心跳信号
		WTSigninAction sa = new WTSigninAction();
		//sa.heartBeat(String.valueOf(user_id) );	
		// end
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));

		String timeBefore = null;
		if ( Constant.timeUtcFlag )
			timeBefore = tls.getUtcDateStrNow();
		else {						
			timeBefore = getDeviceNow(String.valueOf(device_id));
		}
		
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
		cmdDownSetImpl.getLocation( device_imei, 1, user_id, null );
		
		LocationInfo vo = new LocationInfo();
		LocationInfoFacade fd = ServiceBean.getInstance().getLocationInfoFacade();

		List<DataMap> list = null;

		Thread.sleep(200);
		vo.setCondition("b.device_update_time > '"+ timeBefore + 
				"' and a.user_id=" + user_id + " and a.device_id = "+
				device_id+" and a.status='1' order by a.is_priority desc, a.share_date desc");
		int retryCount = 0;
		result  = Constant.FAIL_CODE;
		while(retryCount < 15) {
			list = fd.wtAppGpsManGetDevsList(vo);
			if( list != null && list.size() > 0 ) {
				result = Constant.SUCCESS_CODE;
				break;
			}				
			retryCount++;
			Thread.sleep(1200);				
		}
		
		
		if (result != Constant.SUCCESS_CODE )
			return false;
		
		//vo.setCondition("a.user_id=" + user_id + " and a.device_id = "+device_id+" and a.status='1' order by a.is_priority desc, a.share_date desc");
		//list = fd.wtAppGpsManGetDevsList(vo);

		JSONArray jsonArr = new JSONArray();

		for(int i=0;i<list.size();i++){
			DataMap trackMap = list.get(i);
			JSONObject trackJson = JSONObject.fromObject(hashMapToJson(trackMap));			
			jsonArr.add(trackJson);
		}
		
		//json.put("device_count", list.size());
		//json.put("time", Tools.getStringFromDate(new Date()));
		json.put("act_device_id", getActDeviceId(String.valueOf(user_id)));
		json.put("device_list", jsonArr);
		
		return true;
	}
	
	//单个设备手动定位
	Boolean proGetDevLoc16(int user_id,int device_id,
			HttpServletResponse response) throws Exception {
		
		//调用此协议认为将同时产生心跳信号
		WTSigninAction sa = new WTSigninAction();
		//sa.heartBeat(String.valueOf(user_id) );	
		// end
		String device_imei = getDeviceImeiFromDeviceId(String.valueOf(device_id));
		
		
		CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
	
		try {

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(AdragonConfig.getLocationRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			
			if ( !cmdDownSetImpl.getLocation( device_imei, 1, user_id, lock ) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());		

				insertVisit(null, device_imei, null, "proGetDevLoc16:" + json.toString()   );											
				
			}
		
		} catch(Exception e) {
			
		}
		
		return true;
	}

	
	void proGetDevTrack(String jsonStr) throws SystemException {
		Tools tls = new Tools();
		logger.info("inter proGetDevTrack():" + jsonStr);
		JSONObject object = JSONObject.fromObject(jsonStr.toString());
		int device_id = object.getInt("device_id");
		String start_time = object.getString("start_time");
		String end_time = object.getString("end_time");
		int from = tls.getSafeIntFromJson(object, "from");
		if (from < 0 ) 
			from = 0;
		int pageSize = tls.getSafeIntFromJson(object, "pageSize");
		if ( pageSize < 0 )
			pageSize = 2000;
		int user_id = object.getInt("user_id");
		WappUsers wu = new WappUsers();
		int numCount = 0;
		int num = 0;
		JSONArray jsonArr = new JSONArray();
		AppUserDeviceTrackFacade trackFacade = ServiceBean.getInstance().getAppUserDeviceTrackFacade();
	
		StringBuffer sb = new StringBuffer();

		sb.append(" ( wsi.user_id = "+user_id+" OR wsi.to_user_id = "+user_id+" ) ");
		sb.append(" AND wsi.device_id = "+device_id);
		sb.append(" AND loi.accuracy < "+170);
		if(start_time != null && !"".equals(start_time) && end_time != null && !"".equals(end_time)){
			sb.append(" AND upload_time BETWEEN '"+start_time+"' AND '"+end_time+"' order by upload_time");
		}
		wu.setCondition(sb.toString());
		numCount = trackFacade.getAppUserDeviceTrackCountList(wu);
		
	    sb.append(" LIMIT "+from+","+pageSize);
		
		wu.setCondition(sb.toString());
		List<DataMap> trackList = trackFacade.getAppUserDeviceTrackList(wu);
		if(trackList != null ){
			num = trackList.size();
			for(int i=0;i<trackList.size();i++){
				DataMap trackMap = trackList.get(i);
				JSONObject trackJson = new JSONObject();
				String type = (String) trackMap.getAt("location_type");
				//String battery = trackMap.getAt("battery").toString();
				//String fall = (String) trackMap.getAt("fall");
				String longitude = (String) trackMap.getAt("longitude");
				String latitude = (String) trackMap.getAt("latitude");
				String accuracy = trackMap.getAt("accuracy").toString();
				String upload_time = trackMap.getAt("upload_time").toString();
				//String led_on = (String) trackMap.getAt("led_on");
				//String sel_mode = (String) trackMap.getAt("sel_mode");
				//String gps_on = (String) trackMap.getAt("gps_on");
				
				trackJson.put("device_id", device_id);
				trackJson.put("type", type);
				//trackJson.put("battery", battery);
				//trackJson.put("fall", fall);
				trackJson.put("lon", longitude);
				trackJson.put("lat", latitude);
				trackJson.put("acc", accuracy);
				trackJson.put("upload_time", upload_time);
				//trackJson.put("led_on", led_on);
				//trackJson.put("sel_mode", sel_mode);
				//trackJson.put("gps_on", gps_on);
				jsonArr.add(trackJson);
			}
			json.put("tot_count", numCount);
			json.put("rec_count", num);
			json.put("data_list", jsonArr);

			
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
	}

	public void proGetDevTrackRoad(String jsonStr) throws SystemException {
		Tools tls = new Tools();
		logger.info("inter proGetDevTrackRoad():" + jsonStr);
		if (json== null )
			json = new JSONObject();
		
		JSONObject object = JSONObject.fromObject(jsonStr.toString());
		int device_id = object.getInt("device_id");
		String start_time = object.getString("start_time");
		String end_time = object.getString("end_time");
		int from = tls.getSafeIntFromJson(object, "from");
		if (from < 0 ) 
			from = 0;
		int pageSize = tls.getSafeIntFromJson(object, "pageSize");
		if ( pageSize < 0 )
			pageSize = 2000;
		int user_id = object.getInt("user_id");
		WappUsers wu = new WappUsers();
		int numCount = 0;
		int num = 0;
		JSONArray jsonArr = new JSONArray();
		AppUserDeviceTrackFacade trackFacade = ServiceBean.getInstance().getAppUserDeviceTrackFacade();
				
		StringBuffer sb = new StringBuffer();

		sb.append(" ( wsi.user_id = "+user_id+" OR wsi.to_user_id = "+user_id+" ) ");
		sb.append(" AND wsi.device_id = "+device_id);
		sb.append(" AND loi.accuracy < "+170);
		if(start_time != null && !"".equals(start_time) && end_time != null && !"".equals(end_time)){
			sb.append(" AND upload_time BETWEEN '"+start_time+"' AND '"+end_time+"' order by upload_time");
		}
		wu.setCondition(sb.toString());
		numCount = trackFacade.getAppUserDeviceTrackCountList(wu);
		
	    sb.append(" LIMIT "+from+","+pageSize);
		
		wu.setCondition(sb.toString());
		List<DataMap> trackList = trackFacade.getAppUserDeviceTrackList(wu);
		if(trackList != null ){
			num = trackList.size();
			
			StringBuffer rapiStr = new StringBuffer("https://roads.googleapis.com/v1/snapToRoads?path=");
			int il = 0;
			for(int i=0;i<trackList.size();i++){
				if ( il > 0 ) rapiStr.append("|");
				DataMap trackMap = trackList.get(i);
				JSONObject trackJson = new JSONObject();
				String type = (String) trackMap.getAt("location_type");
				String longitude = (String) trackMap.getAt("longitude");
				String latitude = (String) trackMap.getAt("latitude");
				rapiStr.append(latitude);
				rapiStr.append(",");
				rapiStr.append(longitude);
				
				String accuracy = trackMap.getAt("accuracy").toString();
				String upload_time = trackMap.getAt("upload_time").toString();
				
				il++;				
			}
			
			rapiStr.append("&interpolate=true&key=AIzaSyDPVC038AMaU_4HiDWA7H7uTUGrk8jXyN8");

			StringBuffer rapiRes = httpGetInner(rapiStr.toString());
			
			if (rapiRes == null) {
				result = Constant.FAIL_CODE;
				return;
			}
			
			JSONObject joRes = JSONObject.fromObject(rapiRes.toString());
			JSONArray snappedPoints = joRes.getJSONArray("snappedPoints");
			
			Iterator<Object> it = snappedPoints.iterator();
			while(it.hasNext()) {
				JSONObject trackJson = new JSONObject();
				DataMap trackMap = trackList.get(0);
				
				JSONObject snappedPoint = (JSONObject) it.next();
				JSONObject location = snappedPoint.getJSONObject("location");
				String latitude = location.getString("latitude");
				String longitude = location.getString("longitude");
				
				String type = "4";
				String accuracy = "20";
				String upload_time = trackMap.getAt("upload_time").toString();
				
				trackJson.put("device_id", device_id);
				trackJson.put("type", type);
				trackJson.put("lon", longitude);
				trackJson.put("lat", latitude);
				trackJson.put("acc", accuracy);
				trackJson.put("upload_time", upload_time);
				jsonArr.add(trackJson);				
				
			}
			
			json.put("tot_count", numCount);
			json.put("rec_count", num);
			json.put("data_list", jsonArr);			
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
	}

	//单个设备手动定位
	public Boolean proGetDevLoc17(Integer user_id, Integer device_id, String devId) { 
		
		try {
			
			if ( user_id == null )
				return false;
			
			insertVisit(null, null, String.valueOf(device_id), "proGetDevLoc17");				
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			
			JSONObject ljson = new JSONObject();
			LocationInfo vo = new LocationInfo();
			LocationInfoFacade fd = ServiceBean.getInstance().getLocationInfoFacade();
	
			List<DataMap> list = null;

				//smile add
//			LocationInfoHelper lih  = new LocationInfoHelper();
//			lih.updateDeviceBattery(devId, Tools.getUtcDateStrNowDate(), null, null, null, null, null, device_id);
			
	
			//Thread.sleep(200);
			//vo.setCondition("a.device_id = "+device_id);
			vo.setCondition( "a.device_id=" +
					device_id + " and a.user_id=" + user_id + " and a.status='1' order by a.is_priority desc, e.order_by desc, e.dev_status desc, a.share_date desc");
			

			list = fd.wtAppGpsManGetDevsList(vo);
						
			JSONArray jsonArr = new JSONArray();
			for(int i=0;i<list.size() && i < 1;i++){
				DataMap trackMap = list.get(i);
				JSONObject trackJson = JSONObject.fromObject(hashMapToJson(trackMap));			
				jsonArr.add(trackJson);
			}
			
			//ljson.put("act_device_id", getActDeviceId(String.valueOf(user_id)));
			ljson.put("device_list", jsonArr);
			mClientSessionManager.completeHttpCmdId(devId, 
					AdragonConfig.getLocationRes, 
					user_id, ljson.toString() );
			
		} catch (Exception e ) {
			
			e.printStackTrace();
			insertVisit(null, null, String.valueOf(device_id), "exception proGetDevLoc17 exception:" );				
			
		}
		
		return true;
	}
	
	public void proUrgentModeRes(  Integer device_id, 
			String devId, ReqJsonData reqJsonData) {
		Tools tls = new Tools();	
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			insertVisit(null, null, String.valueOf(device_id), "proUrgentModeRes start" );				
			
			Integer userId = reqJsonData.getUserId();
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			String time_zone = null;
						
			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			wdeviceActiveInfo.setCondition("a.device_imei = '"+devId+"'");
			wdeviceActiveInfo.setDevice_disable("1");   //enable device is active status
			
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
			
			if (wdeviceActiveInfos.isEmpty()) {
				
	//			respJsonData.setResultCode(Constant.EXCEPTION_CODE);
	//			respJsonData.setDevTime(dateFormat.format(new Date()));	
				
			} else {
				
				time_zone = wdeviceActiveInfos.get(0).getAt("time_zone").toString().trim();		//abcd
				
				if (!Constant.IS_SERV_STAT_CT_URGENT) {				
					wdeviceActiveInfo.setUrgent_mode(reqJsonData.getUrgentFlag());
					if ( "1.5".equals(Constant.PROTOCOL_VER ) ) {							
						wdeviceActiveInfo.setCallback_on(reqJsonData.getUrgentFlag());					
						wdeviceActiveInfo.setSos_led_on(reqJsonData.getUrgentFlag());
					}
					
					wdeviceActiveInfo.setCondition("device_imei = '"+devId+"'");
					wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
		
					wdeviceActiveInfo.setCondition("device_id ="+device_id);
					if ( "1.5".equals(Constant.PROTOCOL_VER ) ) {							
						wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
					}
				}
	//			respJsonData.setResultCode(Constant.SUCCESS_CODE);
	//			respJsonData.setDevTime(dateFormat.format(new Date()));	
			}


			if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {
				if (!Constant.IS_SERV_STAT_CT_URGENT) {				
					
					WdevDiscovery devDisc = new WdevDiscovery();
					devDisc.setUser_id(userId);
					devDisc.setDevice_id(device_id);
		
					if ( "1".equals( reqJsonData.getUrgentFlag()) ) {						
						devDisc.setAction_time(reqJsonData.getActionTime());
						if (Constant.cmdDirectResFlag) {						
							devDisc.setAction_time_utc(reqJsonData.getActionTime() );
						} else {
							devDisc.setAction_time_utc(tls.timeConvert( reqJsonData.getActionTime(), time_zone, "UTC") );						
						}
						devDisc.setHow_long(reqJsonData.getDuration());
	
						devDisc.setCondition("device_id = " + device_id );
						
						wdeviceActiveInfoFacade.updateDevDiscovery(devDisc);
					} else {
						devDisc.setHow_long(-1);						
						//devDisc.setAction_time(reqJsonData.getActionTime());
						devDisc.setCondition("device_id = " + device_id );					
						wdeviceActiveInfoFacade.updateDevDiscovery(devDisc);
						
						wdeviceActiveInfo.setSos_led_on(Tools.ZeroString);
						wdeviceActiveInfo.setCondition("device_id = '"+device_id+"'");
						wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
						
						wdeviceActiveInfo.setCallback_on(Tools.ZeroString);									
						wdeviceActiveInfo.setCondition("device_id = "+device_id);
						wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);					
						
					}
									
					devDisc = null;
				}
	
	
				JSONObject jon = new JSONObject();
				jon.put("device_id", device_id);
				jon.put("user_id", userId);
				jon.put("cmd_time", reqJsonData.getCmdTime());
				if ( "1".equals( reqJsonData.getUrgentFlag()) ) {									
					jon.put("duration", reqJsonData.getDuration());
				} else {
					jon.put("duration", -1);					
				}
				jon.put("action_time", reqJsonData.getActionTime());
				if (Constant.cmdDirectResFlag) {										
					jon.put("action_time_utc",  reqJsonData.getActionTime());
				} else {
					jon.put("action_time_utc", tls.timeConvert( reqJsonData.getActionTime(), time_zone, "UTC"));					
				}
					
				//jon.put("action_time", Tools.timeConvert( reqJsonData.getActionTime(), time_zone, "UTC"));

				jon.put("urgent_flag", reqJsonData.getUrgentFlag());
				
				
				/*
				if ( "1".equals(reqJsonData.getUrgentFlag() ))
					lih.proSosOpenTimer(device_id, 
						reqJsonData.getActionTime(),
						reqJsonData.getDuration() );
						*/
				
				mClientSessionManager.completeHttpCmdId(devId, 
					AdragonConfig.setUrgentModeRes, 
					userId, jon.toString() );
	
				WMsgInfo aMsg = new WMsgInfo();
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEV_URGENT_STAT_BRDCAST);
				aMsg.setMsg_content(jon.toString());
				aMsg.setDevice_id(device_id);
				aMsg.setMsg_date(reqJsonData.getActionTime());
				aMsg.setHide_flag(Tools.OneString);
					
				lih.proCommonInnerMsg(aMsg, userId);
				
	            aMsg = null;
				
				jon = null;					
            
			}
		} catch ( Exception e) {
			e.printStackTrace();
			insertVisit(null, null, String.valueOf(device_id), "exception proUrgentModeRes exception:" );				
		}
				
	}
	
	/* reference : http://www.cnblogs.com/linjiqin/archive/2011/09/19/2181634.html */
	public StringBuffer httpGetInner(String urlNameString)  {

		String encoding="UTF-8";    

		BufferedReader in = null;
		StringBuffer sb = null;
		
		try {
			sb = new StringBuffer();		
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.setRequestMethod("GET");
	        conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
			conn.setRequestProperty("Content-Length", String.valueOf(0)); 
			conn.setConnectTimeout(5*1000);    
			
			int code = conn.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();				
			}else{
				sb = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			sb = null;
			
		}
		return sb;
		
	}	
	
}