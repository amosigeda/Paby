﻿package com.wtwd.sys.locationinfo.domain.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.Beattim;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.HttpRequest;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppMsgManFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;

public class LocationInfoHelper {

	private static  ServiceBean serviceBean = ServiceBean.getInstance();

	
	public void updateDeviceBattery(String device_imei, Date para_time, String battery, 
			String longitude, String latitude, String acc, String locType, Integer device_id) 
			throws SystemException {
		//LocationInfo vo =  new LocationInfo();
		//LocationInfoFacade fd = ServiceBean.getInstance().getLocationInfoFacade();
		//String maxTime = null;
		/* TIMESTAMPDIFF( para_time,  */
		//vo.setCondition("serie_no = '" + device_imei + "'");
		//maxTime = fd.getMaxUploadTime(vo);
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			//Date d2 = df.parse(maxTime);
			//long diff = para_time.getTime() - d2.getTime();
			//if ( diff >= 0) {		//更新电池电量及最新位置数据
				WdeviceActiveInfo vo1 = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade fd1 = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				//final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				

//				vo1.setCondition("device_imei = '" + device_imei + "'" );
				vo1.setCondition("device_id=" + device_id );

				vo1.setBattery(battery);
				
				//... debug 后续需要根据 wdevice_active_info 中的timezone字段来操作
				//TimeZone timeZoneNY = TimeZone.getTimeZone("Asia/Shanghai");     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
				//dateFormat.setTimeZone(timeZoneNY);
				//Date date = new Date(System.currentTimeMillis());
				vo1.setDevice_update_time(para_time);
				vo1.setLongitude(longitude);
				vo1.setLatitude(latitude);
				vo1.setAcc(acc);
				vo1.setLct_type(locType);
				fd1.updateWdeviceActiveInfo(vo1);
				vo1.setDevice_id(device_id);
				fd1.updatewDeviceExtra(vo1);
				
				
				/*
				MonitorInfo mi = new MonitorInfo();
				MonitorInfoFacade mif = ServiceBean.getInstance().getMonitorInfoFacade();
				mi.setStartTime(para_time);
				mi.setFunction("battery");
				StringBuffer sbb = new StringBuffer(" imei=");
				sbb.append(device_imei);
				sbb.append(", battery=");
				sbb.append(battery);
				sbb.append(", devId=");
				sbb.append(device_id);
				mi.setFunctionHref(sbb.toString());
				mif.insertMonitorInfo(mi);
				*/
				


				String tz = getGeoFromLatLng(latitude, longitude);
				if ( !Constant.EMPTY_STRING.equals(tz)) {
				WdeviceActiveInfo dai = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade daiFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				
				dai.setCondition("device_id=" + device_id);
				dai.setTime_zone(tz);
				daiFd.updateWdeviceActiveInfo(dai);	// <= 0 )
				dai = null;
				
				}
				
				vo1 = null;
			//}

		}
		catch (Exception e)
		{
		}		
		//vo = null;
		//df = null;
		
	}
	public void updateDeviceStatus(Integer device_id, Integer login_flag, String dev_time) {
		WdeviceActiveInfo vo =  new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		Integer oldDeviceStatus = 0;
		WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		Tools tls = new Tools();		
		
		int res;
		/* TIMESTAMPDIFF( para_time,  */
		vo.setCondition("device_id = " + device_id );
		Date now = new Date();
		vo.setDevice_id(device_id);
		vo.setDev_timestamp(tls.getStringFromDate(now));
		vo.setDev_status(Tools.OneString);
		try
		{
			List<DataMap> list = fd.getwDeviceExtra(vo);
			if ( list.size() == 0 )	{		//保证 wDeviceExtra表对应每个设备有相应数据
				vo.setEco_mode("1");
				fd.insertwDeviceExtra(vo);
			} else	{
				
				if ( Tools.OneString.equals(list.get(0).getAt("dev_status").toString().trim()))
					oldDeviceStatus = 1;
				//if (oldDeviceStatus != 1)  不管上一个状态设备是否在线， 都需要更新设备登录时间戳
					fd.updatewDeviceExtraDevStatus(vo);
			}

			//找到所有设备的相关用户， 产生设备登录消息，写入表wmsg_info
			if (oldDeviceStatus != 1 && login_flag > 0) {
			
				wsi.setDevice_id(device_id);
				List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
				if ( listDru != null && listDru.size() > 0 ) {	
					StringBuffer sb = new StringBuffer("");
					for(int i=0;i<listDru.size();i++){
						DataMap druMap = listDru.get(i);
						
						String app_type = druMap.getAt("app_type").toString().trim();
						String ios_token = druMap.getAt("ios_token").toString().trim();
						String device_token = druMap.getAt("device_token").toString().trim();
						String ios_real = druMap.getAt("ios_real").toString().trim();

						msg_ent.setDevice_id(device_id);
						msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
						msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_LOGIN);
						msg_ent.setMsg_date(dev_time);
						msg_ent.setHide_flag(Tools.OneString);

						sb.delete(0, sb.length());
						//sb.append("The device of '");
						//sb.append(druMap.getAt("pet_nick").toString().trim());
						//sb.append("' is online!");
						sb.append("\"");
						sb.append(druMap.getAt("pet_nick").toString().trim());
						sb.append("\" is online");

						msg_ent.setMsg_content(sb.toString());
						msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
						msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
						msg_ent.setFence_id(-1);
						msg_ent.setStatus(Tools.OneString);		//
						msg_ent.setShare_id(-1);
						msg_ent.setHide_flag(Tools.OneString);		
						//msg_ent.setSummary(sb.toString());
						msg_ent.setApp_type(app_type);
						msg_ent.setDevice_token(device_token);
						msg_ent.setIos_token(ios_token);
						msg_ent.setIos_real(ios_real);

						int pet_id = -1;
						if (!tls.isNullOrEmpty(druMap.getAt("pet_id"))) {							
							pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
						}
						msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
						//System.out.println("msg_ent:"+ msg_ent.toString());
						
						infoFacade.insertData(msg_ent);
							
					}
				}
				
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		vo = null;
		now = null;		
		msg_ent = null;
		wsi = null;
	}

	public void proDevOfflineMsg(Integer device_id) throws SystemException {
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		Tools tls = new Tools();		
		
		
		try {
			wsi.setDevice_id(device_id);
			List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
			if ( listDru != null && listDru.size() > 0 ) {
				StringBuffer sb = new StringBuffer("");
				for(int i=0;i<listDru.size();i++){
					DataMap druMap = listDru.get(i);
					String app_type = druMap.getAt("app_type").toString().trim();
					String ios_token = druMap.getAt("ios_token").toString().trim();
					String device_token = druMap.getAt("device_token").toString().trim();
					String ios_real = druMap.getAt("ios_real").toString().trim();
					
					msg_ent.setDevice_id(device_id);
					msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
					msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_APP_LOGOUT);
					//msg_ent.setMsg_date(getDevCurrentTime(device_id));
					if ( Constant.timeUtcFlag )
						msg_ent.setMsg_date(tls.getUtcDateStrNow() ); 			
					else 																					
						msg_ent.setMsg_date(getDevCurrentTime(device_id));
					
					
					msg_ent.setHide_flag(Tools.OneString);
					sb.delete(0, sb.length());
					//sb.append("The device of '");
					//sb.append(druMap.getAt("pet_nick").toString().trim());
					//sb.append("' is offline!");		
					sb.append("Sorry,\"");
					sb.append(druMap.getAt("pet_nick").toString().trim());
					sb.append("\" is currently offline");		
					
					msg_ent.setMsg_content(sb.toString());
					msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
					msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
					msg_ent.setFence_id(-1);
					msg_ent.setStatus(Tools.OneString);		//默认消息未读
					msg_ent.setShare_id(-1);

					msg_ent.setHide_flag(Tools.OneString);		
					//msg_ent.setSummary(sb.toString());
					msg_ent.setApp_type(app_type);
					msg_ent.setDevice_token(device_token);
					msg_ent.setIos_token(ios_token);					
					msg_ent.setIos_real(ios_real);
					
					int pet_id = -1;
					if (!tls.isNullOrEmpty(druMap.getAt("pet_id"))) {							
						pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
					}
					msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
					//System.out.println("msg_ent:"+ msg_ent.toString());
					
					infoFacade.insertData(msg_ent);
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		msg_ent = null;
		wsi = null;
		
		
	}	

	
	//判断本次经纬度和数据库中最后一次的经纬度相比是否认为有变化
	public boolean isLctChanged(LocationInfo paraInfo) throws SystemException {
	    boolean flag = true;
		WdeviceActiveInfo voDai = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fdDai = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		voDai.setCondition("device_imei='" + paraInfo.getSerieNo().trim() + 
				"' and longitude='" + paraInfo.getChangeLongitude().trim() + 
				"' and latitude='" + paraInfo.getChangeLatitude() + "'" );
		List<DataMap> list = fdDai.getData(voDai);
		if ( list != null && list.size() > 0 )
			flag = false; 
		
		return flag;
	}
	
	
	public void proLctInfo(Integer user_id, boolean lct_is_update, 
			LocationInfo location_info,
			Boolean pushMsgFlag) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
			
			if ( location_info == null ) 
				return;
			
			String device_imei = location_info.getSerieNo();
			String batt = String.valueOf(location_info.getBattery());
			Date lctTime = location_info.getUploadTime();
			
			if ( Constant.timeUtcFlag )
				lctTime = tls.getUtcDateStrNowDate();
			else
				lctTime = ba.getDeviceNowDateFromImei(device_imei);
				
			
			String longitude = null;	
			String latitude = null;	

			if (lct_is_update) {
				latitude = location_info.getChangeLatitude().trim();
				longitude = location_info.getChangeLongitude().trim();
			}
			
			
			if ( !tls.isNullOrEmpty(latitude)  ) {
				try {
					Float latf = Float.parseFloat(latitude);
					if ( latf > 90.0f) {
						ba.insertWMonitor(String.valueOf(user_id), device_imei, null, "location latitude > 90");
						return;
					}
					if ( Constant.EMPTY_STRING.equals(latitude) || Constant.EMPTY_STRING.equals(longitude) ) {
						ba.insertWMonitor(String.valueOf(user_id), device_imei, null, "latitude or longitude is null");
						return;
					}

				
				} catch(Exception e) {
					
				}
			}
			
			String acc  = null;

			if (lct_is_update) {	
				Float accFloat = location_info.getAccuracy(); 
				if ( accFloat != null )
					acc = accFloat.toString();
			}

			/*
			Float accInt = 0;
			if ( !Tools.isNullOrEmpty(acc) )
				accInt = Float.parseFloat(acc);
				*/
			
			String lctType = location_info.getLocationType();
			Integer device_id = location_info.getDevice_id();

			
			updateDeviceBattery(device_imei, lctTime, batt, longitude, latitude, acc, lctType, device_id);
			
			/*if ( accInt < 170.000) */ {
				WMsgInfo aMsg = new WMsgInfo();
				JSONObject jobj = new JSONObject();
				jobj.put("device_id", device_id);
				jobj.put("type", location_info.getLocationType() );
				jobj.put("battery", batt);
				jobj.put("lon", longitude);
				jobj.put("lat", latitude);
				jobj.put("acc", acc);
				aMsg.setMsg_content(jobj.toString());
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_DEV_REP_LCT);
				aMsg.setHide_flag(Tools.OneString);
				aMsg.setDevice_id(device_id);
				//smile add
				lctTime = tls.getUtcDateStrNowDate();
				/*lih.*/updateDeviceBattery(device_imei, lctTime, null, null, null, null, null, device_id);
				
				aMsg.setMsg_date(tls.getStringFromDate(lctTime));	
				if ( user_id != null )
					aMsg.setFrom_usrid(user_id);
				
				
				proCommonInnerMsg(aMsg, user_id);
			}
			
			if ( lct_is_update /*|| isLctChanged(location_info)*/ )
				proDeviceEffence(location_info);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	//将根据设备的移动产生电子围栏越界或者进入等的消息,等等
	public void proDeviceEffence(LocationInfo paraInfo) throws SystemException {
		Integer device_id = paraInfo.getDevice_id();
		WdeviceActiveInfo voDai = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fdDai = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		WeFencing voEfg = new WeFencing();
		AppSafeAreaManFacade efgFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		double cLo,cLa,old_cLo = 0.0001d, old_cLa = 0.0001f;
		Integer rd = 0, old_eid = -1, eid = -1, cur_eid= -1;
		//long old_etime = 0;
		WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();

		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();

		Tools tls = new Tools();		
		
		//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 1 " + device_id );

		
		//读取主人用户电子围栏开关，如果围栏开关为关，处理结束
		voDai.setCondition("device_id=" + device_id);
		List<DataMap> list = fdDai.getwDeviceExtra(voDai);		//wDeviceExtra 表
		if (list == null || list.size() == 0)
			return;
		
		String eOn = list.get(0).getAt("esafe_on").toString().trim();
		if (tls.isNullOrEmpty(eOn) || !Tools.OneString.equals(eOn))
			return;

		//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 2 " + device_id);
		if ( Tools.ZeroString.equals(Constant.STAT_ESAFE_STYLE) ) {  
		
			//读取设备最后的电子围栏数据
			if ( !tls.isNullOrEmpty(list.get(0).getAt("prev_eid").toString() ) ) {
				old_eid = Integer.parseInt(list.get(0).getAt("prev_eid").toString().trim());				
			}
			
			//读取出所有电子围栏的数据
			voEfg.setCondition("device_id =  "+device_id);
			List<DataMap> fenceList = efgFacade.queryWeFencing(voEfg);
			if(fenceList != null /*&& fenceList.size() > 0*/){
				for(int i=0;i<fenceList.size();i++){
					DataMap fenceMap = fenceList.get(i);
					if ( !tls.isNullOrEmpty( fenceMap.getAt("center_gps_lo"))  && 
							!tls.isNullOrEmpty( fenceMap.getAt("center_gps_la")  ) && 
							!tls.isNullOrEmpty( fenceMap.getAt("round_distance") ) ) {
						cLo = Double.valueOf(fenceMap.getAt("center_gps_lo").toString().trim());
						cLa = Double.valueOf(fenceMap.getAt("center_gps_la").toString().trim());
						rd = Integer.parseInt(fenceMap.getAt("round_distance").toString().trim());
						eid = Integer.parseInt(fenceMap.getAt("id").toString().trim());
						//Integer eflag = Integer.parseInt(fenceMap.getAt("flag").toString().trim()); 
	
						String key = "622b99b7cf7146e1243a0ad4fb3afbbe";
						String origins = cLo + "," + cLa;
						String destination = paraInfo.getChangeLongitude() + "," + paraInfo.getChangeLatitude();
						
						double dLo = Double.valueOf(paraInfo.getChangeLongitude());
						double dLa = Double.valueOf(paraInfo.getChangeLatitude());
						
						//计算本次移动后与所有电子围栏中心点的距离
						//Integer dist = Integer.parseInt( Distribution.distanceGaode(key, origins, destination).trim() );
						double dist = Constant.getDistance(cLa, cLo, dLa, dLo);

						if ( dist < rd ) {
							cur_eid = eid;
							//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 3 " + device_id);						
							break;
						}
							
						
					}
				}
			}
	
			
			//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 3.1 " + device_id +
			//		"old_eid :" + old_eid + " cur_eid:" + cur_eid );						
	
			
			if ( old_eid != cur_eid && old_eid >0  &&cur_eid > 0 ) {
				//更新设备电子围栏状态
				voDai.setCondition("device_id=" + device_id);
				voDai.setPrev_eid(cur_eid);
				voDai.setPrev_time(tls.getStringFromDate(paraInfo.getUploadTime()));
				fdDai.updatewDeviceExtraEsafeArea(voDai);
				//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 4 " + device_id);						
	
			}
			
			//和前以状态相比， 如果走出安全区域或者进入安全区域， 产生系统消息
			if ( old_eid != cur_eid && (old_eid <0 || cur_eid <0) ) {
				//更新设备电子围栏状态
				voDai.setCondition("device_id=" + device_id);
				voDai.setPrev_eid(cur_eid);
				voDai.setPrev_time(tls.getStringFromDate(paraInfo.getUploadTime()));
				fdDai.updatewDeviceExtraEsafeArea(voDai);		//wDeviceExtra 表
	
				//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 5 " + device_id);						
				
				if ( old_eid > 0 ) 								
					proEffenceMsg( device_id, old_eid, paraInfo.getUploadTime(), false );
				else
					proEffenceMsg( device_id, cur_eid, paraInfo.getUploadTime(), true );
						

				/*
				wsi.setDevice_id(device_id);
				List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
				if ( listDru != null && listDru.size() > 0 ) {
					for(int i=0;i<listDru.size();i++){
						DataMap druMap = listDru.get(i);
				
						msg_ent.setDevice_id(device_id);
						msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
						if ( old_eid > 0 ) {				
							msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_OUT_EFENCE);
							//msg_ent.setMsg_content("device is out of safe area");
						} else {				
							msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_IN_EFENCE);
							//msg_ent.setMsg_content("device enter into safe area");
						}				
						
						String msg_dat = Tools.getStringFromDate(paraInfo.getUploadTime()) ;
						msg_ent.setMsg_date(msg_dat);
						msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
						msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
						msg_ent.setFence_id( ( cur_eid > 0) ? cur_eid : old_eid );
						msg_ent.setStatus(Tools.OneString);		//
						msg_ent.setShare_id(-1);
			
						int pet_id = -1;
						if (!Tools.isNullOrEmpty(druMap.getAt("pet_id"))) {							
							pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
						}
						msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
						msg_ent.setPet_nick(druMap.getAt("pet_nick").toString() );
						msg_ent.setFence_name(getFenceNameFromId(msg_ent.getFence_id()));
						
						String sum = getMsgContentFromMsg(msg_ent, null, msg_dat);
						msg_ent.setMsg_content(sum);
						
						//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 6 " + device_id);						
						msg_ent.setHide_flag(Tools.ZeroString);		
						msg_ent.setSummary(sum);
						
						infoFacade.insertData(msg_ent);	
						druMap = null;
					}
				}  */
				
			}	
		}


		
		
		
		
		else {
			//读取设备最后的电子围栏数据
			//if ( !Tools.isNullOrEmpty(list.get(0).getAt("prev_eid").toString() ) ) {
			//	old_eid = Integer.parseInt(list.get(0).getAt("prev_eid").toString().trim());				
			//}
			
			//读取出所有电子围栏的数据
			voEfg.setCondition("device_id =  "+device_id);
			List<DataMap> fenceList = efgFacade.queryWeFencing(voEfg);
			if(fenceList != null /*&& fenceList.size() > 0*/){
				for(int i=0;i<fenceList.size();i++){
					DataMap fenceMap = fenceList.get(i);
					if ( !tls.isNullOrEmpty( fenceMap.getAt("center_gps_lo"))  && 
							!tls.isNullOrEmpty( fenceMap.getAt("center_gps_la")  ) && 
							!tls.isNullOrEmpty( fenceMap.getAt("round_distance") ) ) {
						cLo = Double.valueOf(fenceMap.getAt("center_gps_lo").toString().trim());
						cLa = Double.valueOf(fenceMap.getAt("center_gps_la").toString().trim());
						rd = Integer.parseInt(fenceMap.getAt("round_distance").toString().trim());
						eid = Integer.parseInt(fenceMap.getAt("id").toString().trim());
						Integer eflag = Integer.parseInt(fenceMap.getAt("flag").toString().trim()); 
	
						String key = "622b99b7cf7146e1243a0ad4fb3afbbe";
						String origins = cLo + "," + cLa;
						String destination = paraInfo.getLongitude() + "," + paraInfo.getLatitude();
						

						double dLo = Double.valueOf(paraInfo.getChangeLongitude());
						double dLa = Double.valueOf(paraInfo.getChangeLatitude());
						
						//计算本次移动后与所有电子围栏中心点的距离
						//Integer dist = Integer.parseInt( Distribution.distanceGaode(key, origins, destination).trim() );
						double dist = Constant.getDistance(cLa, cLo, dLa, dLo);

						
						Integer flagRes = 0;
						if ( eflag == 0 ) {  
							if ( dist < rd ) {
								flagRes = 1;
								cur_eid = eid;																
							} else {
								flagRes = 2;
								cur_eid = -1;							
								
							}
						} else {
							if ( eflag == 1 && dist >= rd ) {
								flagRes = 2;
								cur_eid = -1;																
							}
							if ( eflag == 2 && dist < rd ) {
								flagRes = 1;
								cur_eid = eid;																
							}
						}
						
						if ( flagRes == 1 || flagRes == 2 ) {
							voEfg.setCondition("id="+ eid);
							voEfg.setFlag(flagRes);
							efgFacade.updateAppSafeAreaMan(voEfg);
							
							if ( flagRes == 1 ) {
								proEffenceMsg( device_id, eid, paraInfo.getUploadTime(), true );
							}
							if ( flagRes == 2 ) {
								proEffenceMsg( device_id, eid, paraInfo.getUploadTime(), false );								
							}

						}						
						
					}
				}
			}
	
			
			//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 3.1 " + device_id +
			//		"old_eid :" + old_eid + " cur_eid:" + cur_eid );						
	
			
			if ( old_eid != cur_eid ) {
				//更新设备电子围栏状态
				voDai.setCondition("device_id=" + device_id);
				voDai.setPrev_eid(cur_eid);
				voDai.setPrev_time(tls.getStringFromDate(paraInfo.getUploadTime()));
				fdDai.updatewDeviceExtraEsafeArea(voDai);
				//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 4 " + device_id);							
			}
			
			
			
			
			
		}
		
	}
	
	public void proEffenceMsg(Integer pdevice_id, 
			Integer peid, Date ptime, boolean pflag ) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
			WshareInfo  wsi = new WshareInfo();
			WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();

			WMsgInfo msg_ent = new WMsgInfo();
			WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
			
			wsi.setDevice_id(pdevice_id);
			List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
			if ( listDru != null && listDru.size() > 0 ) {
				for(int i=0;i<listDru.size();i++){
					DataMap druMap = listDru.get(i);
					String app_type = druMap.getAt("app_type").toString().trim();
					String ios_token = druMap.getAt("ios_token").toString().trim();
					String device_token = druMap.getAt("device_token").toString().trim();
					String ios_real = druMap.getAt("ios_real").toString().trim();
					String dest_lang = druMap.getAt("lang").toString().trim();
					
					msg_ent.setDevice_id(pdevice_id);
					msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
					if ( !pflag ) {				
						msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_OUT_EFENCE);
						//msg_ent.setMsg_content("device is out of safe area");
					} else {				
						msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_IN_EFENCE);
						//msg_ent.setMsg_content("device enter into safe area");
					}				
					
					String msg_dat = tls.getStringFromDate(ptime) ;
					msg_ent.setMsg_date(msg_dat);
					msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
					msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
					msg_ent.setFence_id( peid ) ;
					msg_ent.setStatus(Tools.OneString);		//
					msg_ent.setShare_id(-1);
		
					int pet_id = -1;
					if (!tls.isNullOrEmpty(druMap.getAt("pet_id")) ) {							
						pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
					}
					msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
					msg_ent.setPet_nick(druMap.getAt("pet_nick").toString() );
					msg_ent.setFence_name(getFenceNameFromId(peid));
					msg_ent.setDest_lang(dest_lang);
					
					
					String sum = getMsgContentFromMsg(msg_ent, null, msg_dat);
					msg_ent.setMsg_content(sum);
					
					//BaseAction.insertVisit("-1", null, null, "proDeviceEffence 6 " + device_id);						
					msg_ent.setHide_flag(Tools.ZeroString);		
					msg_ent.setSummary(sum);
					msg_ent.setApp_type(app_type);

					String badgeStr = druMap.getAt("badge").toString();
					Integer badge = 0;
					if (!tls.isNullOrEmpty(badgeStr)  )
						badge = Integer.parseInt(badgeStr) ;
					msg_ent.setOld_badge(badge);

					
					msg_ent.setDevice_token(device_token);
					msg_ent.setIos_token(ios_token);
					msg_ent.setIos_real(ios_real);
					
					infoFacade.insertData(msg_ent);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(pdevice_id), "exception proEffenceMsg exception:");				
			
		}
	}
	
	public String getDevCurrentTime(Integer device_id) throws SystemException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		WdeviceActiveInfo wdeviceActive = new WdeviceActiveInfo();
		wdeviceActive.setCondition("device_id ="+device_id);
		WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getData(wdeviceActive);
		TimeZone timeZoneNY = TimeZone.getTimeZone(wdeviceActiveInfos.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
		//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
		dateFormat.setTimeZone(timeZoneNY);
		Date date = new Date(System.currentTimeMillis());
		
		wdeviceActive = null;
		return dateFormat.format(date);
		
	}

	public String getDevCurrentDay(Integer device_id) throws SystemException {
		try {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
			WdeviceActiveInfo wdeviceActive = new WdeviceActiveInfo();
			wdeviceActive.setCondition("device_id ="+device_id);
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getData(wdeviceActive);
			TimeZone timeZoneNY = TimeZone.getTimeZone(wdeviceActiveInfos.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
			dateFormat.setTimeZone(timeZoneNY);
			Date date = new Date(System.currentTimeMillis());
			
			wdeviceActive = null;
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	//@memo_txt: 备注消息
	public String getMsgContentFromMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		String lang = data.getDest_lang();
		String lang2 = null;
		String lang3 = null;
		/*
		if ( lang != null && lang.length() >=2 )
			lang2 = lang.substring(0, 2).toUpperCase();
		if ( lang != null && lang2 != null && ( "ES".equals(lang2) )   ) {
			msgPrefix = "[es]:";
		}
		*/		
		if ( lang != null && lang.length() >=3 )
			lang2 = lang.substring(0, 3).toUpperCase();
		
		if ( lang != null && lang.length() >= 2 )
			lang3 = lang.substring(0, 2).toUpperCase();
		
		if ( lang != null && lang2 != null && ( "FR-".equals(lang2) )   ) {
			return getMsgContentFromFrMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "RO-".equals(lang2) )   ) {
			return getMsgContentFromRoMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "CS-".equals(lang2) )   ) {
			return getMsgContentFromCsMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "PL-".equals(lang2) )   ) {
			return getMsgContentFromPlMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "DE-".equals(lang2) )   ) {
			return getMsgContentFromDeMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "IT-".equals(lang2) )   ) {
			return getMsgContentFromItMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang2 != null && ( "RU-".equals(lang2) )   ) {
			return getMsgContentFromRuMsg(data, memo_txt, msg_date);
		}

		if ( lang != null && lang3 != null && ( "ZH".equals(lang3) )   ) {
			return getMsgContentFromZhMsg(data, memo_txt, msg_date);
		}
		if ( lang != null && lang3 != null && ( "ES".equals(lang3) )   ) {
			return getMsgContentFromESMsg(data, memo_txt, msg_date);
		}

		if ( lang != null && lang2 != null && ( "JA-".equals(lang2) )   ) {
			return getMsgContentFromJaMsg(data, memo_txt, msg_date);
		}

		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append(msgPrefix);
				sb.append("\"" + from_nick);
				sb.append("\" has requested to share access with \"");
				sb.append(pet_nick + "\".To approve,confirm below");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append(msgPrefix);
				sb.append("\"" + from_nick + "\" ");
				sb.append("has granted your request to share access with \"");
				sb.append(from_nick + "\"");
				
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append(msgPrefix);				
				sb.append("\"" + from_nick  + "\" ");
				sb.append("has declined your request to share access with \"");
				sb.append(pet_nick + "\"");
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append(msgPrefix);				
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" ");
				sb.append("has removed your shared access with \"");
				sb.append( pet_nick + "\"");				
				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append(msgPrefix);				
				//sb.append("\" has exited the virtual fence \"");
				sb.append("Is \"" + pet_nick + "\" in a safe location?");
				sb.append("They left your virtual fence \"");
				sb.append(data.getFence_name() + "\"");	// at " + Tools.getHourMinFromDateString(msg_date) );				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append(msgPrefix);				
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Don't worry!\"" + pet_nick + "\" is safe.");
				sb.append("They entered your virtual fence \"");				
				sb.append(data.getFence_name() + "\"");	// at " + Tools.getHourMinFromDateString(msg_date) );
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append(msgPrefix);				
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("\"" + pet_nick);
				sb.append("\"'s battery is low.Please charge their device");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				sb.append(msgPrefix);
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//Your firmware upgrade was successful
					sb.append("Your firmware upgrade was successful");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Sorry,your firmware upgrade has failed.Please check your device");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					sb.append(msgPrefix);
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Almost there! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" has reached 50% of their fitness goal today");
						
					} else {
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Almost there! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" has reached 50% of their fitness goal today");						
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					sb.append(msgPrefix);
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Congratulations！\"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" has reached 100% of their fitness goal today");
						
					} else {
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Congratulations！\"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" has reached 100% of their fitness goal today");						
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}

	//@memo 中文翻译
	public String getMsgContentFromJaMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append("\"" + from_nick);
				sb.append("\" さんは \"");
				sb.append(pet_nick + "\". へのアクセスを共有できるようリクエストしました。 承認するには、以下を確認してください");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append("\"" + from_nick + "\" が ");
				sb.append("\"");
				sb.append(pet_nick + "\"への共有アクセスのリクエストを許可しました");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append("\"" + from_nick + "\" が ");
				sb.append("\"");
				sb.append(pet_nick + "\"への共有アクセスのリクエストを拒否しました");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append("\"" + from_nick + "\" が ");
				sb.append("\"");
				sb.append(pet_nick + "\"への共有アクセスを削除しました");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append("\"" + pet_nick + "\" は安全な場所にいますか？彼らはWi-Fiの仮想フェンス");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"から離れました");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append("ご心配はいりません! \"" + pet_nick + "\" は無事です。彼らはWi-Fiの仮想フェンス");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"に入りました");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append("\"" + pet_nick);
				sb.append("\"のバッテリーが少ないです。 デバイスを充電してください");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					sb.append("ファームウェアのアップグレードに成功しました");
					
				} else {
					sb.append("申し訳ありませんが、ファームウェアのアップグレードに失敗しました。 デバイスを確認してください");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						sb.append("もうすぐです！\"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" は、今日フィットネスの目標を50% に達していました");
						
					} else {
						sb.append("もうすぐです！\"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" は、今日フィットネスの目標を50% に達していました");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						sb.append("おめでとうございます! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\"  は、今日フィットネスの目標を100%に達していました");
						
					} else {
						sb.append("おめでとうございます! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\"  は、今日フィットネスの目標を100%に達していました");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	
	
	//@memo 中文翻译
	public String getMsgContentFromZhMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append("\"" + from_nick);
				sb.append("\" 请求分享宠物 \"");
				sb.append(pet_nick + "\". 如果同意，请点击下面的确认 ");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append("\"" + from_nick + "\" 同意您分享他的宠物 ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append("\"" + from_nick + "\" 拒绝您分享她的宠物 ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append("\"" + from_nick + "\" 删除你分享她的宠物 ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append("\"" + pet_nick + "\" 离开虚拟电子安全区域");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append("亲，恭喜! \"" + pet_nick + "\" 进入虚拟电子安全区域");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append("\"" + pet_nick);
				sb.append("\"的设备电量低，请及时充电");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					sb.append("升级成功");
					
				} else {
					sb.append("升级失败，请检查您的设备");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						sb.append("恭喜您! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" 已经完成了今天建议运动量的50%或者更多");
						
					} else {
						sb.append("恭喜您!  \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" 已经完成了今天建议运动量的50%或者更多");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						sb.append("恭喜您！ \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" 已经完成了今天建议运动量的100%或者更多");
						
					} else {
						sb.append("恭喜您！ \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" 已经完成了今天建议运动量的100%或者更多");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	

	//@memo 西班牙语翻译
	public String getMsgContentFromESMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append("\"" + from_nick);
				sb.append("\" ha solicitado compartir el acceso \"");
				sb.append(pet_nick + "\". Para aprobar, confirmar a continuación.");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append("\"" + from_nick + "\" accedió a su solicitud para compartir acceso a ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append("\"" + from_nick + "\" rechazó su pedido de acceso compartido a ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append("\"" + from_nick + "\" ha eliminado su acceso compartido a ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append("¿Está \"" + pet_nick + "\" en un lugar seguro? Dejó su cerca virtual de");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append("¡No te preocupes! \"" + pet_nick + "\" ingresó a su cerca virtual de");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append("La batería del dispositivo de \"" + pet_nick);
				sb.append("\" está baja. Por favor cárguelo.");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					sb.append("La actualización del firmware del dispositivo fue exitosa!");
					
				} else {
					sb.append("Lo sentimos, la actualización del firmware del dispositivo ha fallado. Por favor inténtalo nuevamente.");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						sb.append("¡Casi lo logra! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" alcanzó hoy el 50% de su objetivo de actividad!");
						
					} else {
						sb.append("¡Casi lo logra! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" alcanzó hoy el 50% de su objetivo de actividad!");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						sb.append("¡Felicitaciones! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" alcanzó hoy el 100% de su objetivo de actividad!");
						
					} else {
						sb.append("¡Felicitaciones! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" alcanzó hoy el 100% de su objetivo de actividad!");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	
	//@memo 意大利翻译
	public String getMsgContentFromRuMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append("\"" + from_nick);
				sb.append("\" has requested to share access with \"");
				sb.append(pet_nick + "\". To approve, confirm below");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append("\"" + from_nick + "\" предоставил вам общий доступ к ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append("\"" + from_nick + "\" отклонил общий доступ к ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append("\"" + from_nick + "\" отключил общий доступ к ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append("\"" + pet_nick + "\" находится в безопасном месте? Питомец покинул пределы виртуального забора");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append("Не волнуйтесь! \"" + pet_nick + "\" в безопасности. Питомец вернулся в пределы виртуального забора");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append("\"" + pet_nick);
				sb.append("\" низкий уровень заряда. Зарядите устройство питомца");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					sb.append("Микропрограмма успешно обновлена");
					
				} else {
					sb.append("К сожалению, не удалось обновить микропрограмму. Проверьте ваше устройство");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						sb.append("Уже почти! Для \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" достигнуто 50% от поставленной на сегодня фитнес-цели");
						
					} else {
						sb.append("Уже почти! Для \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" достигнуто 50% от поставленной на сегодня фитнес-цели");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						sb.append("Поздравляем! Для \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" достигнуто100% от поставленной на сегодня фитнес-цели");
						
					} else {
						sb.append("Поздравляем! Для \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" достигнуто100% от поставленной на сегодня фитнес-цели");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	

	//@memo 意大利翻译
	public String getMsgContentFromItMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				sb.append("\"" + from_nick);
				sb.append("\" has requested to share access with \"");
				sb.append(pet_nick + "\". To approve, confirm below");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				sb.append("\"" + from_nick + "\" ha approvato la richiesta di condividere l'accesso con ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				sb.append("\"" + from_nick + "\" ha declinato la richiesta di condividere l'accesso con ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				sb.append("\"" + from_nick + "\" ha rimosso il tuo accesso condiviso con ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				sb.append("\"" + pet_nick + "\" è in un luogo sicuro? Ha lasciato il recinto vituale");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				sb.append("Non preoccuparti! \"" + pet_nick + "\" è al sicuro. È rientrato nel recinto virtuale");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				sb.append("La batteria \"" + pet_nick);
				sb.append("\". Cambiare il dispositivo");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					sb.append("L'aggiornamento del firmware è avvenuto con successo");
					
				} else {
					sb.append("Spiacenti, l'aggiornamento del firmware è fallito. Controllare il dispositivo");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						sb.append("Quasi fatto! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" ha raggiunto il 50% dell'obbiettivo fitness di oggi");
						
					} else {
						sb.append("Quasi fatto! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" ha raggiunto il 50% dell'obbiettivo fitness di oggi");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						sb.append("Congratulazioni! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" ha raggiunto il 100% dell'obbiettivo di fitness oggi");
						
					} else {
						sb.append("Congratulazioni! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" ha raggiunto il 100% dell'obbiettivo di fitness oggi");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	

	//@memo 德文翻译
	public String getMsgContentFromDeMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				//sb.append("'" + from_nick);
				//sb.append("' requests to share access with '");
				//sb.append(pet_nick + "',please confirm");
				sb.append("\"" + from_nick);
				sb.append("\" has requested to share access with \"");
				sb.append(pet_nick + "\". To approve, confirm below");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' confirmed your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" Ihnen den Antrag auf gemeinsamen Zugriff auf ");
				sb.append("\"");
				sb.append(pet_nick + "\" gewährt");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' denied your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" hat den Antrag auf gemeinsamen Zugriff auf ");
				sb.append("\"");
				sb.append(pet_nick + "\" abgelehnt");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' cancelled your shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" hat Ihren gemeinsamen Zugriff auf ");
				sb.append("\"");
				sb.append(pet_nick + "\" abgelehnt");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has exited the virtual fence \"");
				//ababab
				sb.append("Ist \"" + pet_nick + "\" an einem sicheren Standort? Es hat Ihren virtuellen Zaun");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\" verlassen");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Machen sie sich keine Sorgen! \"" + pet_nick + "\" ist sicher. Es hat Ihren virtuellen Zaun");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\" betreten");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				//sb.append("The battery of '" + pet_nick);
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("Die Batterie von \"" + pet_nick);
				sb.append("\" ist gering. Bitte wechseln");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//sb.append("Firmware upgrade successful.");
					//Your firmware upgrade was successful
					sb.append("Ihr Firmware-Upgrade war erfolgreich");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Verzeihung, Ihr Firmware-Upgrade ist fehlgeschlagen. Bitte prüfen Sie Ihr Gerät");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Fast da! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" hat 50% seines Fitness-Ziels heute erreicht");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Fast da! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" hat 50% seines Fitness-Ziels heute erreicht");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Glückwunsch! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" hat 100% seines Fitness-Ziels heute erreicht");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Glückwunsch! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" hat 100% seines Fitness-Ziels heute erreicht");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	
	
	//@memo 波兰文翻译
	public String getMsgContentFromPlMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				//sb.append("'" + from_nick);
				//sb.append("' requests to share access with '");
				//sb.append(pet_nick + "',please confirm");
				sb.append("\"" + from_nick);
				sb.append("\" has requested to share access with \"");
				sb.append(pet_nick + "\". To approve, confirm below");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' confirmed your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" zgadza się na udzielenie dostępu do ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' denied your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" nie zgadza się na udzielenie dostępu do ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' cancelled your shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Decyzją ");
				sb.append("\"" + from_nick + "\" dostęp do ");
				sb.append("\"");
				sb.append(pet_nick + "\" został cofnięty");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has exited the virtual fence \"");
				//ababab
				sb.append("Czy \"" + pet_nick + "\" jest w bezpiecznej lokalizacji? Poza płotem wirtualnym");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Nie martw się! \"" + pet_nick + "\" jest bezpieczny/a. Za płotem wirtualnym");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				//sb.append("The battery of '" + pet_nick);
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("Bateria \"" + pet_nick);
				sb.append("\" bliska rozładowania. Naładuj urządzenie");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//sb.append("Firmware upgrade successful.");
					//Your firmware upgrade was successful
					sb.append("Uaktualniono oprogramowanie układowe");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Niestety, nie uaktualniono oprogramowania układowego. Sprawdź urządzenie");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Już prawie na miejscu! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" udało się wykonać 50% planu aktywności fizycznej na dziś");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Już prawie na miejscu! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" udało się wykonać 50% planu aktywności fizycznej na dziś");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Gratulacje! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" udało się wykonać 100% planu aktywności fizycznej na dziś");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Gratulacje! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" udało się wykonać 100% planu aktywności fizycznej na dziś");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	
	
	
	//@memo_txt: 备注消息
	public String getMsgContentFromFrMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				//sb.append("'" + from_nick);
				//sb.append("' requests to share access with '");
				//sb.append(pet_nick + "',please confirm");
				sb.append("\"" + from_nick);
				sb.append("\" a demandé de partager l'accès avec l'appareil \"");
				sb.append(pet_nick + "\". Approuver, confirmer ci-dessous");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' confirmed your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Le propriétaire de ");
				sb.append("\"" + from_nick + "\" a accordé votre demande de partage d'accès avec l'appareil ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' denied your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Le propriétaire de ");
				sb.append("\"" + from_nick + "\" a refusé votre demande d'accès partagé avec l'appareil ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' cancelled your shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Le propriétaire de ");
				sb.append("\"" + from_nick + "\" a supprimé votre accès partagé avec l'appareil ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has exited the virtual fence \"");
				//ababab
				sb.append("Est-ce que \"" + pet_nick + "\" est dans un endroit sûr? Ils ont laissé votre clôture virtuelle");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Ne vous inquiétez pas! \"" + pet_nick + "\" est sécurisé. Ils ont entré votre clôture virtuelle");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				//sb.append("The battery of '" + pet_nick);
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("La batterie de \"" + pet_nick);
				sb.append("\" est faible. Chargez leur appareil");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//sb.append("Firmware upgrade successful.");
					//Your firmware upgrade was successful
					sb.append("La mise à niveau de votre firmware a réussi");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Désolé, la mise à niveau de votre firmware a échoué. Vérifiez votre appareil");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Presque là! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atteint aujourd'hui 50% de son objectif de condition physique");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Presque là! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atteint aujourd'hui 50% de son objectif de condition physique");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Félicitations! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atteint 100% de son objectif de condition physique aujourd'hui");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Félicitations! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atteint 100% de son objectif de condition physique aujourd'hui");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}

	
	
	
	//@memo_txt: 备注消息
	//罗马尼亚语翻译
	public String getMsgContentFromRoMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				//sb.append("'" + from_nick);
				//sb.append("' requests to share access with '");
				//sb.append(pet_nick + "',please confirm");
				sb.append("\"" + from_nick);
				sb.append("\" dorește acces la dispozitivul lui \"");
				sb.append(pet_nick + "\". Pentru aprobare, confimați");
				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' confirmed your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Stăpânul lui ");
				sb.append("\"" + from_nick + "\" a permis accesul la dispozitiul lui ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' denied your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Stăpânul lui ");
				sb.append("\"" + from_nick + "\" nu a permis accesul la dispozitiul lui ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' cancelled your shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("Stăpânul lui ");
				sb.append("\"" + from_nick + "\" a sters accesul dvs. la dispozitiul lui ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has exited the virtual fence \"");
				//ababab
				sb.append("\"" + pet_nick + "\" este în siguranță? A părăsit gardul virtual");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Stai liniștit! \"" + pet_nick + "\" este în siguranță! A intrat în gardul virtual");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				//sb.append("The battery of '" + pet_nick);
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("Baterie descărcată. Încărcați dispozitivul lui ");
				sb.append("\"" + pet_nick + "\"");
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//sb.append("Firmware upgrade successful.");
					//Your firmware upgrade was successful
					sb.append("Firmware actualizat cu success");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Actualizare firmware eșuată. Verificați dispozitivul dvs");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Aproape acolo! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atins 50% din obiectivul fitness de astăzi");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Aproape acolo! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atins 50% din obiectivul fitness de astăzi");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Felicitări! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atins 100% din obiectivul fitness de astăzi");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Felicitări! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" a atins 100% din obiectivul fitness de astăzi");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}
	

	//@memo_txt: 备注消息
	//捷克翻译
	public String getMsgContentFromCsMsg(WMsgInfo data, String memo_txt, String msg_date) {
		String from_nick = data.getFrom_nick();
		int msg_ind_id = data.getMsg_ind_id();
		String pet_nick = data.getPet_nick();
		StringBuffer sb = new StringBuffer("");
		String msgPrefix = "";
		int device_id = data.getDevice_id();
		
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		
		
		switch (msg_ind_id) {
			case Constant.CST_MSG_IND_APPLY_SHARE:
				//sb.append("'" + from_nick);
				//sb.append("' requests to share access with '");
				//sb.append(pet_nick + "',please confirm");
				sb.append("\"" + from_nick);
				sb.append("\" požádal o sdílení přístupu s \"");
				sb.append(pet_nick + "\". Pro potvrzení, souhlaste níže");				
				break;
			case Constant.CST_MSG_IND_AGREE_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' confirmed your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" majitel Vám povolil sdílet přístup s ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				break;
			case Constant.CST_MSG_IND_DENY_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' denied your request for shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" majitel Vám zamítl sdílet přístup s ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			
				
				break;
			case Constant.CST_MSG_IND_HOST_DEL_SHARE:
				//sb.append("The owner '" + from_nick);
				//sb.append("' cancelled your shared access with '");
				//sb.append(pet_nick + "'");
				sb.append("\"" + from_nick + "\" majitel odstranil váš sdílený přístup s ");
				sb.append("\"");
				sb.append(pet_nick + "\"");			

				break;

			case Constant.CST_MSG_IND_DEV_OUT_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has exited the virtual fence \"");
				//ababab
				sb.append("Je \"" + pet_nick + "\" v bezpečné lokalitě? Opustili tvůj neviditelný plot");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_IN_EFENCE:
				//sb.append("Your pet \"" + pet_nick);
				//sb.append("\" has entered the virtual fence \"");
				sb.append("Bez obav! \"" + pet_nick + "\" je v bezpečí. Vstoupili do tvého neviditelného plotu");
				sb.append(" \"");
				sb.append(data.getFence_name() + "\"");				
				break;
			case Constant.CST_MSG_IND_DEV_LOW_BATTERY:
				//sb.append("The battery of '" + pet_nick);
				//sb.append("'s device is low, please re-charge it immediately");
				sb.append("\"" + pet_nick + "\"");
				sb.append(" baterie je vybitá. Prosím nabijte zařízení");
				
				
				break;
			case Constant.CST_MSG_IND_UPDATE_FIRM_FINISH:
				Integer ec = data.getError_code();
				if ( ec == 0 ) { 				
					//sb.append("Firmware upgrade successful.");
					//Your firmware upgrade was successful
					sb.append("Váš firmware byl úspěšně aktualizován");
					
				} else {
					//sb.append("Firmware upgrade failed,please check the device");
					sb.append("Pardon, aktualizace vašeho firmwaru selhala. Prosím zkontrolujte Vaše zařízení");					
				}
					
				break;				
			case Constant.CST_MSG_IND_SPORT_HALF_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {						
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of her fitness goal today");
						sb.append("Skoro hotovo! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" dnes dosáhl 50% svého fitness plánu");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						//sb.append("\" has reached 50% of his fitness goal today");
						sb.append("Skoro hotovo! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" dnes dosáhl 50% svého fitness plánu");
					}
						
				} 
				break;
			case Constant.CST_MSG_IND_SPORT_FULL_PERCENT:
				{
					
					if ( Tools.OneString.equals( ba.getPetSexFromDeviceId(device_id) ) ) {   
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of her fitness goal today");
						sb.append("Gratuluji! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" dnes dosáhl 100% svého fitness plánu");
						
					} else {
						//sb.append("Congratulations！\"");
						//sb.append(ba.getPetNickFromDeviceId(device_id)); 
						//sb.append("\" has reached 100% of his fitness goal today");
						sb.append("Gratuluji! \"");
						sb.append( ba.getPetNickFromDeviceId(device_id) ); 
						sb.append("\" dnes dosáhl 100% svého fitness plánu");
					}
				}				
				break;
			default:
				break;
		}		
		
		if  (!tls.isNullOrEmpty(memo_txt)) {
			sb.append("\n" + memo_txt);
		}
		
		return sb.toString();
	}


    public String getFenceNameFromId(int fence_id) throws SystemException {
		WeFencing vo = new WeFencing();
		AppSafeAreaManFacade fd = ServiceBean.getInstance().getAppSafeAreaManFacade();

    	vo.setCondition("id=" + fence_id);
    	List<DataMap> list = fd.queryWeFencing(vo);
    	if (list.size() != 1 )
    		throw new SystemException("invalid efence id");
    	return list.get(0).getAt("device_safe_name").toString().trim();
    	
    }
    
    public void proLowBatteryMsg(Integer device_id) throws SystemException {
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		Tools tls = new Tools();
		
		try {
			
			wsi.setDevice_id(device_id);
			String msg_dat = null;	//getDevCurrentTime(device_id);
			if ( Constant.timeUtcFlag )
				msg_dat = tls.getUtcDateStrNow();			
			else 																					
				msg_dat = getDevCurrentTime(device_id);
			
						
			List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
			if ( listDru != null && listDru.size() > 0 ) {
				for(int i=0;i<listDru.size();i++){
					DataMap druMap = listDru.get(i);
					String app_type = druMap.getAt("app_type").toString().trim();
					String ios_token = druMap.getAt("ios_token").toString().trim();
					String device_token = druMap.getAt("device_token").toString().trim();
					String ios_real = druMap.getAt("ios_real").toString().trim();
					String dest_lang = druMap.getAt("lang").toString().trim();
					
					msg_ent.setDevice_id(device_id);
					msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
					msg_ent.setMsg_ind_id(Constant.CST_MSG_IND_DEV_LOW_BATTERY);
					msg_ent.setMsg_date(msg_dat);
					//msg_ent.setMsg_content("device offline");
					msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
					msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
					msg_ent.setFence_id(-1);
					msg_ent.setStatus(Tools.OneString);		//默认消息未读
					msg_ent.setShare_id(-1);

					int pet_id = -1;
					if (!tls.isNullOrEmpty(druMap.getAt("pet_id"))) {							
						pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
					}
					msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
			    	//String pet_nick = BaseAction.getPetNickFromDeviceId(device_id);
			    	String pet_nick = druMap.getAt("pet_nick").toString().trim();
					msg_ent.setPet_nick(pet_nick);
					msg_ent.setDest_lang(dest_lang);
			    	String msg_content = getMsgContentFromMsg(msg_ent, null, msg_dat);
			    	msg_ent.setMsg_content(msg_content);

					msg_ent.setHide_flag(Tools.ZeroString);		
					msg_ent.setSummary(msg_content);

					String badgeStr = druMap.getAt("badge").toString();
					Integer badge = 0;
					if (!tls.isNullOrEmpty(badgeStr)  )
						badge = Integer.parseInt(badgeStr) ;
					msg_ent.setOld_badge(badge);
					
					
					msg_ent.setApp_type(app_type);
					msg_ent.setDevice_token(device_token);
					msg_ent.setIos_token(ios_token);
					msg_ent.setIos_real(ios_real);
					
					infoFacade.insertData(msg_ent);
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		msg_ent = null;
		wsi = null;
		
		
		
    }
	
    
    public Double getSpeedFromDevSleepInfo(WpetSleepInfo info) {
    	
        Date dt1=info.getStart_time();  
        Date dt2=info.getEnd_time();  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(dt1);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(dt2);    
        long time2 = cal.getTimeInMillis();
        Float between_mins=(float) ((time2-time1)/(1000*60));    
        Double res = (double) (info.getStep_number()/between_mins);
    	return res;
    }

	public void proMoveDoMsg(Integer type,Integer device_id, String msg_time) throws SystemException {
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();

		Tools tls = new Tools();
		
		int pet_id = -1;		
		
		try {
			wsi.setDevice_id(device_id);
			List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
			if ( listDru != null && listDru.size() > 0 ) {
				for(int i=0;i<listDru.size();i++){
					DataMap druMap = listDru.get(i);
					String app_type = druMap.getAt("app_type").toString().trim();
					String ios_token = druMap.getAt("ios_token").toString().trim();
					String device_token = druMap.getAt("device_token").toString().trim();
					String ios_real = druMap.getAt("ios_real").toString().trim();
					
					msg_ent.setDevice_id(device_id);
					msg_ent.setMsg_type(Constant.CST_MSG_TYPE_SPORT);
					msg_ent.setMsg_ind_id((type == 0) ? Constant.CST_MSG_IND_SPORT_HALF_PERCENT:Constant.CST_MSG_IND_SPORT_FULL_PERCENT);
					//msg_ent.setMsg_date(getDevCurrentTime(device_id));
					//msg_ent.setMsg_date(msg_time);
					msg_ent.setMsg_date(tls.getUtcDateStrNow());
					
					
					//String msgStr = null;
					
					String msgStr = getMsgContentFromMsg(msg_ent, null, null);					
					
					/*
					if ( type == 0 ) {
						if ( Tools.OneString.equals( BaseAction.getPetSexFromDeviceId(device_id) ) )   
							msgStr = "Congratulations！\"" + BaseAction.getPetNickFromDeviceId(device_id) + 
								"\" has reached 50% of her fitness goal today.";
						else
							msgStr = "Congratulations！\"" + BaseAction.getPetNickFromDeviceId(device_id) + 
							"\" has reached 50% of his fitness goal today.";
							
					} else {
						if ( Tools.OneString.equals( BaseAction.getPetSexFromDeviceId(device_id) ) )   
							msgStr = "Congratulations！\"" + BaseAction.getPetNickFromDeviceId(device_id) + 
								"\" has reached 100% of her fitness goal today.";
						else
							msgStr = "Congratulations！\"" + BaseAction.getPetNickFromDeviceId(device_id) + 
							"\" has reached 100% of his fitness goal today.";
					}
					
					*/
						
					msg_ent.setMsg_content(msgStr);
					msg_ent.setFrom_usrid(0);	//0暂时定义为系统发起
					msg_ent.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() ));	   //获取所有设备相关用户的ID
					msg_ent.setFence_id(-1);
					msg_ent.setStatus(Tools.OneString);		//
					msg_ent.setShare_id(-1);

					pet_id = -1;
					if (!tls.isNullOrEmpty(druMap.getAt("pet_id"))) {							
						pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
					}
					msg_ent.setPet_id(pet_id);	//获取设备对应的宠物

					msg_ent.setHide_flag(Tools.ZeroString);		
					msg_ent.setSummary(msgStr);
					
					
					String badgeStr = druMap.getAt("badge").toString();
					Integer badge = 0;
					if (!tls.isNullOrEmpty(badgeStr)  )
						badge = Integer.parseInt(badgeStr) ;
					msg_ent.setOld_badge(badge);

					msg_ent.setApp_type(app_type);
					msg_ent.setDevice_token(device_token);
					msg_ent.setIos_token(ios_token);
					msg_ent.setIos_real(ios_real);
					
					infoFacade.insertData(msg_ent);
				}
			}
			
			
			//需要插入表wPetMoveMsgStatus。。。记录
	    	WpetMoveInfo vo = new WpetMoveInfo();
	    	WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();

	    	vo.setPet_id(pet_id);
	    	vo.setMsg_type(String.valueOf(type));
//	    	vo.setUp_time(Tools.getDateFromString(msg_time));
	    	vo.setUp_time(msg_time);
	    	fd.insertPetMoveMsgStatus(vo);
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		msg_ent = null;
		wsi = null;
		
		
	}	
    
	
	public void proSysDevOffline(Integer device_id, String devIMEI) throws SystemException {
		WTSigninAction ba = new WTSigninAction();
		
		if ( ba.getDevStatus(device_id) == 0 )
			return;
		
		
		//BaseAction.insertVisit(null,null, String.valueOf(device_id), "inner proSysDevOffline");				
		
		WdeviceActiveInfo deviceActiveInfo = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade deviceInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		deviceActiveInfo.setCondition("device_id = " + device_id);
		deviceActiveInfo.setDev_status(Tools.ZeroString);
		deviceActiveInfo.setDev_timestamp(df.format(new Date()));
		deviceInfoFacade.updatewDeviceExtraDevStatus(deviceActiveInfo);
		
		DeviceLogin deviceLogin = new DeviceLogin();
		deviceLogin.setDeivceImei(devIMEI);
		deviceLogin.setDeviceStatus(Tools.ZeroString);
		deviceLogin.setDateTime(new Date());
		deviceLogin.setBelongProject(Tools.OneString);
		DeviceLoginFacade deviceLoginFacade = ServiceBean.getInstance().getDeviceLoginFacade();
		deviceLoginFacade.insertDeviceLogin(deviceLogin);

		//产生设备离线消息
		proDevOfflineMsg(device_id);
		
	}
	
	
	public void proCommonInnerMsg(WMsgInfo para, Integer srcUser) throws SystemException {
		Tools tls = new Tools();
		
		WshareInfo  wsi = new WshareInfo();
		WshareInfoFacade wsi_fd = ServiceBean.getInstance().getWshareInfoFacade();
		//WMsgInfo msg_ent = new WMsgInfo();
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();

		int device_id = para.getDevice_id();
				
		try {
			wsi.setDevice_id(device_id);
			List<DataMap> listDru = wsi_fd.getDevRelUser(wsi);
			if ( listDru != null && listDru.size() > 0 ) {
				StringBuffer sb = new StringBuffer("");
				for(int i=0;i<listDru.size();i++){
					DataMap druMap = listDru.get(i);

					String app_type = druMap.getAt("app_type").toString().trim();
					String ios_token = druMap.getAt("ios_token").toString().trim();
					String device_token = druMap.getAt("device_token").toString().trim();
					String ios_real = druMap.getAt("ios_real").toString().trim();
					String dest_lang = druMap.getAt("lang").toString().trim();
					
					//msg_ent.setDevice_id(device_id);
					//msg_ent.setMsg_type(Constant.CST_MSG_TYPE_REL_DEVICE);
					//msg_ent.setMsg_ind_id(msg_ind_id);
					//msg_ent.setMsg_date(timestamp);
					//para.setHide_flag(Tools.OneString);
					para.setStatus(Tools.OneString);		//已读
					
					String sum = para.getMsg_content();
					if (  sum == null || "".equals(sum) || "null".equals(sum) || "\"null\"".equals(sum) ) {
						para.setMsg_content("device status changed!");
						para.setHide_flag(Tools.OneString);		
						para.setSummary("");						
					}
					
					//aaabbb
					if ( para.getMsg_ind_id() == Constant.CST_MSG_IND_WIFI_ESAFE_BRDCST ) {
						WTSigninAction ba = new WTSigninAction();
				    	LocationInfoHelper lih = new LocationInfoHelper();					
						String devTime = tls.getUtcDateStrNow();
						
						String pet_nick = ba.getPetNickFromDeviceId(device_id);
						String wifi_ctr =  ba.getSSidWifiFromDeviceId(device_id);
						
						
						if (!tls.isNullOrEmpty(wifi_ctr)) {
							
							String txt = null;
							String eflag = para.getEflag();
							if (  eflag != null && Tools.OneString.equals(eflag) )
								txt = lih.getWifiEsafePushMsg(pet_nick, wifi_ctr, devTime, true, dest_lang);
							else
								txt = lih.getWifiEsafePushMsg(pet_nick, wifi_ctr, devTime, false, dest_lang);
							
							para.setSummary(txt);
						}
					}
					//aaabbb end
					
					if ( srcUser == null || srcUser < 0 )
						para.setFrom_usrid(0);	//0暂时定义为系统发起
					else
						para.setFrom_usrid(srcUser);
					para.setTo_usrid(Integer.parseInt( druMap.getAt( "from_usrid").toString() )); //获取所有设备相关用户的ID

					/* if ( Integer.parseInt( druMap.getAt( "from_usrid").toString() ) == para.getFrom_usrid() &&
							Constant.CST_MSG_IND_DEV_REP_LCT == para.getMsg_ind_id()	)
						continue;
						*/

					String badgeStr = druMap.getAt("badge").toString();
					Integer badge = 0;
					if (!tls.isNullOrEmpty(badgeStr)  )
						badge = Integer.parseInt(badgeStr) ;
					para.setOld_badge(badge);
																			
					para.setFence_id(-1);
					para.setShare_id(-1);

					//int pet_id = -1;
					//if (!Tools.isNullOrEmpty(druMap.getAt("pet_id"))) {							
					//	pet_id =  Integer.parseInt( druMap.getAt("pet_id").toString() );
					//}
					//msg_ent.setPet_id(pet_id);	//获取设备对应的宠物
					if ( para.getMsg_date() == null || "".equals(para.getMsg_date()) ) {				
						//para.setMsg_date(getDevCurrentTime(device_id));
						if ( Constant.timeUtcFlag )
							para.setMsg_date(tls.getUtcDateStrNow());			
						else 																					
							para.setMsg_date(getDevCurrentTime(device_id));
						
					}
					
					para.setApp_type(app_type);
					para.setDevice_token(device_token);
					para.setIos_token(ios_token);
					para.setIos_real(ios_real);
					
					infoFacade.insertData(para);
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		wsi = null;
		
	}	
	
	public void proBroadcastMsg(WMsgInfo para, Integer srcUser) throws SystemException {
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
				
		try {
					
			para.setStatus(Tools.OneString);		//已读
				
			String sum = para.getMsg_content();
			if (  sum == null ||
				"".equals(sum) ||
				"null".equals(sum) ||
				"\"null\"".equals(sum) ) {
				para.setMsg_content("");
				para.setHide_flag(Tools.OneString);		
				para.setSummary("");						
			}
			para.setFence_id(-1);
			para.setShare_id(-1);
			
			
			infoFacade.insertData(para);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}		
		
		
	}	
	

	
	public class SosTimerTask extends TimerTask {
		Integer ldev_id;
		String lact_time;
		Integer lduration;
		
		
		public SosTimerTask(Integer device_id, String action_time, Integer duration) { 
			ldev_id = device_id;
			lact_time = action_time;
			lduration = duration;
		}
		
		
		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			Tools tls = new Tools();
			WTSigninAction ba = new WTSigninAction();
			
			try {
				
			String now = tls.getStringFromDate( new Date() );
			//System.out.println("sos end at:" + now);
			
			
			/*
			BaseAction.insertVisit(null,null,
					String.valueOf(ldev_id), 
					"sos act.time:" + lact_time + 
					",dura." + 
					lduration + 
					",end:" + 
					BaseAction.getDeviceNow(String.valueOf(ldev_id)) );				
			*/
            //System.out.println("-------设定要指定任务--------");  
            
			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			wdeviceActiveInfo.setCondition("a.device_id = '"+ldev_id+"'");
			wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
			
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
			
			if (!wdeviceActiveInfos.isEmpty()) {				
				wdeviceActiveInfo.setUrgent_mode(Tools.ZeroString);
				wdeviceActiveInfo.setCallback_on(Tools.ZeroString);					
				wdeviceActiveInfo.setSos_led_on(Tools.ZeroString);
				
				wdeviceActiveInfo.setCondition("device_id ="+ldev_id);
				wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);

				wdeviceActiveInfo.setCondition("device_id ="+ldev_id);
				wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
			}
			} catch (Exception e) {
				e.printStackTrace();
				ba.insertVisit(null, null, String.valueOf(ldev_id), "exception SosTimerTask exception:" + e.getMessage());				
				
			}
			
		}
		
	}
	
	public void proSosOpenTimer(Integer device_id, String action_time, Integer duration) {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();

		
		try {
			long leftSecs = 0;
			if ( Constant.timeUtcFlag )
				leftSecs = tls.getSecondsBetweenDays(					
						tls.getUtcDateStrNow(),
						tls.getDateStringAddMin(action_time, duration )
						);				
			else
				leftSecs = tls.getSecondsBetweenDays(					
					ba.getDeviceNow(String.valueOf(device_id)),
					tls.getDateStringAddMin(action_time, duration )
					);
			if ( leftSecs <= 0 ) {
				leftSecs = 1;
			}
					
			Timer timer = new Timer();  
			timer.schedule(new SosTimerTask(device_id, action_time, duration), leftSecs * 1000);// 设定指定的时间time,此处为2000毫秒
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	
	// 1:离开WIFI电子围栏， 0:进入WIFI电子围栏 
	public String getWifiEsafePushMsg(String pet_nick, String ctr, String msg_date, boolean flag, String dest_lang) {
		
		String lang = dest_lang;
		String lang2 = null;
		String lang3 = null;

		if ( lang != null && lang.length() >=3 )
			lang2 = lang.substring(0, 3).toUpperCase();

		if ( lang != null && lang.length() >=2 )
			lang3 = lang.substring(0, 2).toUpperCase();
		
		
		if ( lang != null && lang2 != null && ( "FR-".equals(lang2) )   ) {
			return getWifiEsafePushFrMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "RO-".equals(lang2) )   ) {
			return getWifiEsafePushRoMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "CS-".equals(lang2) )   ) {
			return getWifiEsafePushCsMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "PL-".equals(lang2) )   ) {
			return getWifiEsafePushPlMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "DE-".equals(lang2) )   ) {	//德文
			return getWifiEsafePushDeMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "IT-".equals(lang2) )   ) {	//德文
			return getWifiEsafePushItMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang2 != null && ( "RU-".equals(lang2) )   ) {	//德文
			return getWifiEsafePushRuMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang3 != null && ( "ZH".equals(lang3) )   ) {	//中文
			return getWifiEsafePushZhMsg(pet_nick, ctr, msg_date, flag);
		}	
		if ( lang != null && lang3 != null && ( "ES".equals(lang3) )   ) {	//中文
			return getWifiEsafePushESMsg(pet_nick, ctr, msg_date, flag);
		}	
		
		if ( lang != null && lang2 != null && ( "JA-".equals(lang2) )   ) {	//中文
			return getWifiEsafePushJaMsg(pet_nick, ctr, msg_date, flag);
		}	

		
		StringBuffer sb = new StringBuffer("");
		//sb.append("Your pet ");
		//sb.append(pet_nick);
		
		if ( flag ) {
			//sb.append("\" has exited the virtual fence \"");
			sb.append("Is \"" + pet_nick + "\" in a safe location?");			
			sb.append("They left your Wi-Fi Virtual Leash \"");			
		} else {
			//sb.append("\" has entered the virtual fence \"");
			sb.append("Don't worry!\"" + pet_nick + "\" is safe.");
			sb.append("They entered your Virtual Leash \"");							
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}
	
	public String getWifiEsafePushRuMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("\"" + pet_nick + "\" находится в безопасном месте? Питомец покинул пределы виртуального забора Wi-Fi");
			sb.append(" \"");
		} else {
			sb.append("Не волнуйтесь! \"" + pet_nick + "\" в безопасности. Питомец вернулся в пределы виртуального забора Wi-Fi");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		
		return sb.toString();		
	}

	public String getWifiEsafePushJaMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("\"" + pet_nick + "\" は安全な場所にいますか？彼らはWi-Fiの仮想リーシュ");
			sb.append(" \"");
		} else {
			sb.append("ご心配はいりません! \"" + pet_nick + "\" は無事です。彼らはWi-Fiの仮想リーシュ");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		
		if ( flag ) {
			sb.append("から離れました");
		}else
			sb.append("に入りました");
		
		return sb.toString();		
	}

	public String getWifiEsafePushESMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("¿Está \"" + pet_nick + "\" en un lugar seguro? Dejó la cerca virtual de seguridad WiFi ");
			sb.append(" \"");
		} else {
			sb.append("¡No te preocupes! \"" + pet_nick + "\" ingresó a su cerca virtual de seguridad WiFI ");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		
		return sb.toString();		
	}
	
	
	public String getWifiEsafePushZhMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("\"" + pet_nick + "\" 离开了Wi-Fi安全区域，请确认他是否安全");
			sb.append(" \"");
		} else {
			sb.append("恭喜您! \"" + pet_nick + "\" 进入了Wi-Fi安全区域");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		
		return sb.toString();		
	}
	

	public String getWifiEsafePushItMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("\"" + pet_nick + "\" è in un luogo sicuro? Si è allontanato dal guinzaglio virtuale Wi-Fi");
			sb.append(" \"");
		} else {
			sb.append("Non preoccuparti! \"" + pet_nick + "\" è al sicuro. È rientrato nel guinzaglio virtuale Wi-Fi");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		
		return sb.toString();		
	}
	
	public String getWifiEsafePushPlMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		if ( flag ) {
			sb.append("Czy \"" + pet_nick + "\" jest w bezpiecznej lokalizacji? Nie ma w zasięgu wirtualnej smyczy Wi-Fi");
			sb.append(" \"");
		} else {
			sb.append("Nie martw się! \"" + pet_nick + "\" jest bezpieczny/a. Jest w zasięgu wirtualnej smyczy Wi-Fi");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}
	
	public String getWifiEsafePushDeMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		
		 
		if ( flag ) {
			sb.append("Ist \"" + pet_nick + "\" an einem sicheren Standort? Es hat Ihre virtuelle WiFi-Leine");
			sb.append(" \"");
		} else {
			sb.append("Machen Sie sich keine Sorgen! \"" + pet_nick + "\" ist sicher. Es hat Ihre virtuelle WiFi-Leine");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\" verlassen");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}	
	
	public String getWifiEsafePushFrMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("Your pet \"");
		//sb.append(pet_nick);
		//aaabbb
		
		if ( flag ) {
			sb.append("Est-ce que \"" + pet_nick + "\" est dans un endroit sûr? Ils ont laissé votre clôture virtuelle Wi-Fi");
			sb.append(" \"");
		} else {
			sb.append("Ne vous inquiétez pas! \"" + pet_nick + "\" est sécurisé. Ils ont entré votre clôture virtuelle Wi-Fi");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}

	public String getWifiEsafePushRoMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("Your pet \"");
		//sb.append(pet_nick);
		//aaabbb
		
		if ( flag ) {
			sb.append("\"" + pet_nick + "\" este ?n siguran???A p?r?sit gardul virtual WI-FI");
			sb.append(" \"");
		} else {
			//sb.append("Ne vous inquiétez pas!\"" + pet_nick + "\" est sécurisé.Ils ont entré votre clôture virtuelle Wi-Fi");
			//sb.append(" \"");
			sb.append("Stai lini?tit! \"" + pet_nick + "\" este ?n siguran??!A intrat ?n gardul virtual WI-FI");
			sb.append(" \"");			
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}

	public String getWifiEsafePushCsMsg(String pet_nick, String ctr, String msg_date, boolean flag) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("Your pet \"");
		//sb.append(pet_nick);
		//aaabbb
		
		if ( flag ) {
			sb.append("Je \"" + pet_nick + "\" v bezpečné lokalitě? Opustili tvůj neviditelný Wi-fi plot");
			sb.append(" \"");
		} else {
			//sb.append("Ne vous inquiétez pas!\"" + pet_nick + "\" est sécurisé.Ils ont entré votre clôture virtuelle Wi-Fi");
			//sb.append(" \"");
			sb.append("Bez obav! \"" + pet_nick + "\" je v bezpečí. Vstoupili do tvého neviditelného Wi-fi plotu");
			sb.append(" \"");
		}
		
		sb.append(ctr);
		sb.append("\"");	// at ");
		//sb.append(Tools.getHourMinFromDateString(msg_date));
		
		return sb.toString();		
	}
	
	
	
	// 
	public void syncDevStatus(IoSession session,String device_id, String host_time_zone) {
		try {
			Tools tls = new Tools();
			
			if  ( session == null || tls.isNullOrEmpty(device_id) || tls.isNullOrEmpty(host_time_zone) )
				return;
			
			final RespJsonData respJsonData = new RespJsonData();
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Integer beattim = 0;

			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			wdeviceActiveInfo.setCondition("a.device_id = '"+device_id+"'");
			wdeviceActiveInfo.setDevice_disable(Tools.OneString);   //enable device is active status
			
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
			//wdeviceActiveInfo.setCondition("device_imei = '"+serieNo+"'");

			
			String devTimeZoneID = wdeviceActiveInfos.get(0).getAt("time_zone").toString();
			String timeZone = wdeviceActiveInfos.get(0).getAt("time_zone").toString();
			if ( host_time_zone.equals(timeZone) )
				return;

			WdeviceActiveInfo vo = new WdeviceActiveInfo();

			vo.setCondition("device_id="+ device_id);
			vo.setTime_zone(host_time_zone);
			wdeviceActiveInfoFacade.updateWdeviceActiveInfo(vo);
			
			TimeZone timeZoneNY = TimeZone.getTimeZone(wdeviceActiveInfos.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
			dateFormat.setTimeZone(timeZoneNY);
			Date date = new Date(System.currentTimeMillis());
			respJsonData.setSynDevTime(dateFormat.format(date));
	//		respJsonData.setDevTime(outputFormat.format(date));
	
			SimpleDateFormat utcTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
			
			SynTime synTime = new SynTime();
			utcTimeFormat.setTimeZone(utcTimeZone);
			synTime.setUtcTime(utcTimeFormat.format(date));
			synTime.setTimeZone(devTimeZoneID);
			
			if (Constant.timeUtcFlag)
				respJsonData.setSynDevTime(utcTimeFormat.format(date));
			
			respJsonData.setSynTime(synTime);
			
			beattim = (Integer) wdeviceActiveInfos.get(0).getAt("beattim");
			Beattim setBeattim = new Beattim();			
			setBeattim.setBeatTim(Integer.toString(beattim));
			//setBeattim.setBeatTim("30");
	
			respJsonData.setSetBeattim(setBeattim);
			
			String resp = JSON.toJSONString(respJsonData);
			
			if (resp != null && !"".equals(resp) && !"{}".equals(resp)) {
				session.write(resp);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public ArrayList<WpetMoveInfo> checkMoveInfo( WpetMoveInfo pmove_info ) {
		ArrayList<WpetMoveInfo> list = new ArrayList<WpetMoveInfo>();
		WTSigninAction ba = new WTSigninAction();
		Tools tls = new Tools();
		
		try {
			
			
			//WpetMoveInfo res = new WpetMoveInfo();
			Integer device_id= pmove_info.getDevice_id();
			String sn = pmove_info.getDevice_imei();
			String st = pmove_info.getStart_time().trim();
			String et = pmove_info.getEnd_time().trim();
			
			if (tls.getSecondsBetweenDays(et, st)  < 370 ) {
				list.add(pmove_info);
				return list;
			}
			
			String tz = ba.getDeviceTimeZone(String.valueOf(device_id));
			String stDev = null;
			if (Constant.cmdDirectResFlag) {									
				stDev = st;
			} else {
				stDev = tls.timeConvert(st, "UTC", tz);				
			}
			String etDevDayEnd = stDev.substring(0, 10) + " 23:59:59";
			
			String etDayEnd = null;
			
			if (Constant.cmdDirectResFlag) {						
				etDayEnd = etDevDayEnd;
			} else {
				etDayEnd = tls.timeConvert(etDevDayEnd, tz, "UTC");
			}
			
			
			long secs = tls.getSecondsBetweenDays(pmove_info.getStart_time(), pmove_info.getEnd_time());
			//double speed = pmove_info.getStep_number()  * 60.0 / secs;
			

			if ( tls.getSecondsBetweenDays(etDayEnd, et) > 0 ) {
				WpetMoveInfo wm_1 =  (WpetMoveInfo) pmove_info.clone();
				if ( wm_1 != null ) {
					wm_1.setEnd_time(etDayEnd);
					long secs_1 = tls.getSecondsBetweenDays(st, etDayEnd);
					float bl = secs_1/secs;
					//float st_1 = pmove_info.getStep_number() * bl;
					wm_1.setStep_number(pmove_info.getStep_number() * bl);
					wm_1.setRoute(pmove_info.getRoute() * bl);
					wm_1.setCalories(pmove_info.getCalories() * bl);					
					list.add(wm_1);
				}
				WpetMoveInfo wm_2 =  (WpetMoveInfo) pmove_info.clone();
				if ( wm_2 != null ) {
					wm_2.setStart_time(etDayEnd);
					long secs_2 = tls.getSecondsBetweenDays(etDayEnd, et);
					float bl_2 = secs_2/secs;
					//float st_1 = pmove_info.getStep_number() * bl;
					wm_2.setStep_number(pmove_info.getStep_number() * bl_2);
					wm_2.setRoute(pmove_info.getRoute() * bl_2);
					wm_2.setCalories(pmove_info.getCalories() * bl_2);					
					list.add(wm_2);
				}
				
				return list;				
			} else {
				list.add(pmove_info);
				return list;				
			}
		} catch(Exception e) {
			e.printStackTrace();
			list.add(pmove_info);
			return list;
		}
	}
	
	
	public boolean preCheckDevStatus(Integer device_id, String device_imei) {
		boolean res = true;
		try {
			ClientSessionManager cmdClientSessionManager
				= WTDevHandler.getClientSessionMangagerInstance();
			IoSession deviceIoSession = cmdClientSessionManager.getSessionId(device_imei);
			
			if ( deviceIoSession == null ) {
				CmdDownSetImpl cdsl = new CmdDownSetImpl();
				cdsl.ProDevStat( device_imei, 0 );
				
				cdsl = null;
				
				return false;
			}
	
			WdeviceActiveInfo wda = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade wdaFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			wda.setCondition("device_id =" + device_id);
			List<DataMap> list = wdaFd.getwDeviceExtra(wda);
			if ( list.isEmpty() )
				return false;
			else {
				String dev_status = list.get(0).getAt("dev_status").toString().trim();
				if ( !Tools.OneString.equals(dev_status) ) {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			res = true;
		}
		
		return res;
	}

	
	public List<WpetMoveInfo> mkTinyMoveInfoSe( WpetMoveInfo pmove_info ) {
		ArrayList<WpetMoveInfo> list = new ArrayList<WpetMoveInfo>();
		Tools tls = new Tools();		

		try {
			//WpetMoveInfo res = new WpetMoveInfo();
			Integer device_id= pmove_info.getDevice_id();
			String sn = pmove_info.getDevice_imei();
			String st = pmove_info.getStart_time().trim();
			String et = pmove_info.getEnd_time().trim();
			
			if (tls.getSecondsBetweenDays(st, et)  <= 370 ) {
				list.add(pmove_info);
				return list;
			}
			
			long secs = tls.getSecondsBetweenDays(st, et);
			//double speed = pmove_info.getStep_number()  * 60.0 / secs;
			
			String et_i = tls.getDateStringAddSecond(st, 370);
			
			while( tls.getSecondsBetweenDays(et, et_i) < 0 ){
				WpetMoveInfo wm_1 =  (WpetMoveInfo) pmove_info.clone();
				if ( wm_1 != null ) {
					wm_1.setStart_time(st);
					wm_1.setEnd_time(et_i);					
					//long secs_1 = Tools.getSecondsBetweenDays(st, et_i);
					double bl = (double)370/(double)secs;
					//float st_1 = pmove_info.getStep_number() * bl;
					wm_1.setStep_number((float)(pmove_info.getStep_number() * bl));
					wm_1.setRoute((float)(pmove_info.getRoute() * bl));
					wm_1.setCalories(pmove_info.getCalories() * bl);					
					list.add(wm_1);
				}		
				
				st = et_i;
				et_i = tls.getDateStringAddSecond(st, 370);
			}
			
			if ( tls.getSecondsBetweenDays(st, et) > 0 && tls.getSecondsBetweenDays(et_i, et) < 0 ) {
				WpetMoveInfo wm_1 =  (WpetMoveInfo) pmove_info.clone();
				if ( wm_1 != null ) {
					wm_1.setStart_time(st);
					wm_1.setEnd_time(et);					
					long secs_1 = tls.getSecondsBetweenDays(st, et);
					double bl = (double)secs_1/(double)secs;
					//float st_1 = pmove_info.getStep_number() * bl;
					wm_1.setStep_number((float)(pmove_info.getStep_number() * bl));
					wm_1.setRoute((float)(pmove_info.getRoute() * bl));
					wm_1.setCalories(pmove_info.getCalories() * bl);					
					list.add(wm_1);
				}		
				
			}
						
				
			return list;				
		} catch(Exception e) {
			e.printStackTrace();
			list.add(pmove_info);
			return list;
		}
	}
	
	public String getGeoFromLatLng(String lat, String lng) {
		String res = Constant.EMPTY_STRING;
		try {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("lat", lat);
			map.put("lng", lng);
			map.put("username", Constant.GEONAMEORG_USERNAME);
			String resJonStr  = HttpRequest.sendGetToGeo(
					Constant.GEONAMEORG_URL, map);	
			JSONObject jo = JSONObject.fromObject(resJonStr);
			res = jo.optString(Constant.GEONAMEORG_TZFLD);
			return res;
		} catch(Exception e) {
			e.printStackTrace();
			return res;
		}
	}


	public String getCountryCodeFromLatLng(String lat, String lng) {
		String res = Constant.EMPTY_STRING;
		try {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("lat", lat);
			map.put("lng", lng);
			map.put("username", Constant.GEONAMEORG_USERNAME);
			String resJonStr  = HttpRequest.sendGetToGeo(
					Constant.GEONAMEORG_URL, map);	
			JSONObject jo = JSONObject.fromObject(resJonStr);
			res = jo.optString(Constant.GEONAMEORG_CC);
			return res;
		} catch(Exception e) {
			e.printStackTrace();
			return res;
		}
	}
	

	public boolean IsAmericaDev(String tz) {
		if ( tz == null )
			return false;
		if (tz.length() >= 7 && "America".equals(tz.substring(0,7)) ) {
			return true;
		} else
			return false;
	}

	
}
