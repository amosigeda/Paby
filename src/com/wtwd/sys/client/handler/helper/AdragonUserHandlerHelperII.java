package com.wtwd.sys.client.handler.helper;

import java.util.ArrayList;
import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.other.LocationInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.directiveinfo.domain.DirectiveInfo;
import com.wtwd.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;

public class AdragonUserHandlerHelperII {
	
	private static ServiceBean serviceBean = ServiceBean.getInstance();
	
	//添加亲情号码
	public static RespJsonData addAdrDeviceFamily(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String phoneNum = reqJsonData.getFamily_number();
		String nickName = reqJsonData.getFamily_name();
		String relationType = reqJsonData.getRelationType();
		String belongProject = reqJsonData.getB_g();
		
		nickName = Constant.transCodingToUtf(nickName);
		
		return respJsonData;		
	}
	
	//删除亲情号码
	public static RespJsonData deleteAdrDeviceFamily(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		return respJsonData;		
	}
	
	//修改亲情号码
	public static RespJsonData modifyAdrDeviceFamily(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		
		return respJsonData;
	}
	
	//历史轨迹查询
	public static RespJsonData queryDeviceTrack(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String deviceId = reqJsonData.getDevice_imei();
		String startTime = reqJsonData.getStart_time();
		String endTime = reqJsonData.getEnd_time();
		String belongProject = reqJsonData.getB_g();
		
		LocationInfo locationInfo = new LocationInfo();
		locationInfo.setCondition("serie_no= '"+deviceId+"' and belong_project = '"+belongProject+"' and upload_time >= '"+startTime+"' and upload_time <= '"+endTime+"' and longitude !='0.000000' and latituede !='90.000000'");
		locationInfo.setOrderBy("upload_time");
		LocationInfoFacade locationInfoFacade = serviceBean.getLocationInfoFacade();
		List<DataMap> locationInfos = locationInfoFacade.getLocationListInfo(locationInfo);
		List<LocationInfoAdr> locationInfoAdrs = new ArrayList<LocationInfoAdr>();
		for(int i=0;i<locationInfos.size();i++){
			DataMap locationMap =(DataMap)locationInfos.get(i);
			String changeLon = (String) locationMap.getAt("change_longitude");
			String changeLat = (String) locationMap.getAt("change_latitude");
			String lon = (String) locationMap.getAt("longitude");
			String lat=(String)locationMap.getAt("latitude");
			
			int battery = (Integer)locationMap.getAt("battery");
			String type = (String)locationMap.getAt("location_type");
			String uploadtime=locationMap.getAt("upload_time").toString();
			int accuracy = (Integer)locationMap.getAt("accuracy");
			LocationInfoAdr locationInfoAdr = new LocationInfoAdr();
			locationInfoAdr.setChangeLat(Double.valueOf(changeLat));
			locationInfoAdr.setChangeLon(Double.valueOf(changeLon));
			locationInfoAdr.setLon(Double.valueOf(lon));
			locationInfoAdr.setLat(Double.valueOf(lat));
			locationInfoAdr.setBattery(battery);
			locationInfoAdr.setLocationType(type);
			locationInfoAdr.setUpload_time(uploadtime);
			locationInfoAdr.setAccuracy(accuracy);
			locationInfoAdrs.add(locationInfoAdr);
		}
		respJsonData.setLocationInfos(locationInfoAdrs);
		if(locationInfos.isEmpty()){
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		respJsonData.setResultCode(result);
		return respJsonData;
	}
	
	//设备安全区域
	public static RespJsonData modifyDeviceSafeArea(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		return respJsonData;
	}
	
	//设备远程数据设置
	public static RespJsonData setDeviceData(ReqJsonData reqJsonData){
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		return respJsonData;
		
	}
	
	//设备数据下载
	public static RespJsonData downLoadDeviceData(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		return respJsonData;
	}
	
	//版本升级
	public static RespJsonData updateApp(ReqJsonData reqJsonData) throws SystemException{
		return null;
	}
	
	//意见反馈
	public static RespJsonData feedBack(ReqJsonData reqJsonData) throws SystemException{
		return null;
	}
	
	//倾听
	public static RespJsonData listen(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String phone = reqJsonData.getUser_phone();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String belongProject = reqJsonData.getB_g();
		
		DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
		deviceActiveInfo.setCondition("device_imei = '"+serieNo+"' and belong_project='"+belongProject+"'");
		
		DeviceActiveInfoFacade deviceActiveFacade = serviceBean.getDeviceActiveInfoFacade();
		
		List<DataMap> deviceActiveInfos = deviceActiveFacade.getDeviceActiveInfo(deviceActiveInfo);
		if(deviceActiveInfos.size()>0){
			deviceActiveInfo.setListenType("1");
			deviceActiveFacade.updateDeviceActiveInfo(deviceActiveInfo);
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//定位
	public static RespJsonData location(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String belongProject = reqJsonData.getB_g();
		String map = reqJsonData.getMap();
		
		if(userId==null || "".equals(userId)){
			userId="-1";
		}
		
		if("-1".equals(userId)){
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("device_imei ='"+serieNo+"' and belong_project='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(!deviceActiveInfos.isEmpty()){
				userId = ""+deviceActiveInfos.get(0).getAt("user_id");
			}
		}
		
		LocationInfo locationInfo = new LocationInfo();
		locationInfo.setCondition("serie_no = '"+serieNo+"' and belong_project='"+belongProject+"' and longitude != '0.000000' and latitude != '90.000000'");
		locationInfo.setOrderBy("upload_time");
		locationInfo.setSort("1");
		locationInfo.setFrom(0);
		locationInfo.setPageSize(1);
		List<LocationInfoAdr> locationInfoAdrs = new ArrayList<LocationInfoAdr>();
		LocationInfoFacade locationInfoFacade = serviceBean.getLocationInfoFacade();
		List<DataMap> locationInfos = locationInfoFacade.getLocationInfo(locationInfo);
	    for(int i=0;i<locationInfos.size();i++){
	    	LocationInfoAdr locationInfoAdr= new LocationInfoAdr();
	    	DataMap locationMap=(DataMap)locationInfos.get(i);
			String lng=(String)locationMap.getAt("change_longitude");
			String lat=(String)locationMap.getAt("change_latitude");
			
			String no_trans_lng=(String)locationMap.getAt("longitude");
			String no_trans_lat=(String)locationMap.getAt("latitude");
			
			int battery = (Integer)locationMap.getAt("battery");
			String type = (String)locationMap.getAt("location_type");
			String time=locationMap.getAt("upload_time").toString();
			int accuracy = (Integer)locationMap.getAt("accuracy");
			locationInfoAdr.setLat(Double.valueOf(no_trans_lat));
			locationInfoAdr.setLon(Double.valueOf(no_trans_lng));
			locationInfoAdr.setChangeLat(Double.valueOf(lat));
			locationInfoAdr.setChangeLon(Double.valueOf(lng));
			locationInfoAdr.setBattery(battery);
			locationInfoAdr.setLocationType(type);
			locationInfoAdr.setTime(time);
			locationInfoAdr.setAccuracy(accuracy);
			locationInfoAdrs.add(locationInfoAdr);
	    }
	    
	    if(locationInfos.isEmpty()){
	    	result = Constant.FAIL_CODE;
	    }else{
	    	result = Constant.SUCCESS_CODE;
	    }

		
		respJsonData.setResultCode(result);
		respJsonData.setLocationInfos(locationInfoAdrs);
		
		return respJsonData;
	}
	
	//找宝贝
	public static RespJsonData findBaby(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		return respJsonData;
	}
	
	//删除安全区域
	public static RespJsonData deleteDeviceSafeArea(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		return respJsonData;		
	}
	
	//获取安全区域
	public static RespJsonData getDeviceSafeArea(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		return respJsonData;		
	}
	
	//设备分享
	public static RespJsonData shareDeviceHelper(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String deviceImei = reqJsonData.getSerie_no();
		String userPhone = reqJsonData.getUser_phone();
		String message = reqJsonData.getMessage();
		String messageLevel = reqJsonData.getMessageLevel();
		String belongProject = reqJsonData.getB_g();
		message = Constant.transCodingToUtf(message);
		
		
		if(userId!=null && !"".equals(userId) && deviceImei!=null && !"".equals(deviceImei) && message!=null && !"".equals(message)){
			AppUserInfo appUserInfo = new AppUserInfo();
			appUserInfo.setCondition("user_name ='"+userPhone+"' and belongProject = '"+belongProject+"'");
			AppUserInfoFacade appUserInfoFacade = serviceBean.getAppUserInfoFacade();
			List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
			if(appUserInfos.size()>0){
				String toUserId = ""+appUserInfos.get(0).getAt("id");
				String nickName = ""+appUserInfos.get(0).getAt("nick_name");
				String userHead = ""+appUserInfos.get(0).getAt("head");
				String userName = ""+appUserInfos.get(0).getAt("user_name");
				
				if(toUserId.equals(userId)){
					result = -2; //分享失败,不能分享给自己(手机端自己判断)
				}else{
				}					
			}
			
			if(appUserInfos.isEmpty()){
				result = Constant.FAIL_CODE;
			}
		}else{
			result = Constant.FAIL_CODE;
		}
		
		respJsonData.setResultCode(result);
		
		return respJsonData;		
	}
	
	//消息处理
	public static RespJsonData msgHandlerHelper(ReqJsonData reqJsonData) throws SystemException{
		return null;
	}
	
	//消息获取
	public static RespJsonData getMessageHelper(ReqJsonData reqJsonData) throws SystemException{
			return null;
	}
	
	
	//LBS定位(Get请求)
	
	
	//取消分享
	public static RespJsonData deleteShareDeviceHelper(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		return respJsonData;		
	}
	
	//找回密码
	public static RespJsonData findPasswordHelp(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		String userPhone = reqJsonData.getUser_phone();
		String type = reqJsonData.getType();  //0表示验证密码，1表示修改密码  当为1时，新密码必须传递
		String newPassword = reqJsonData.getNew_password();
		String belongProject = reqJsonData.getB_g();
		String userId = "0";
		AppUserInfo appUserInfo = new AppUserInfo();
		AppUserInfoFacade appUserInfoFacade=serviceBean.getAppUserInfoFacade();
		appUserInfo.setCondition("user_name ='"+userPhone+"' and belong_project ='"+belongProject+"'");
		List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
		if(appUserInfos.size()>0){
			userId = appUserInfos.get(0).getAt("id").toString();
			if("1".equals(type)&&(newPassword!=null && !"".equals(newPassword))){
				appUserInfo.setPassword(newPassword);
				appUserInfoFacade.updateAppUserInfo(appUserInfo);
			}
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//远程关机、驱蚊，爱心
	public static RespJsonData remoteHelp(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String type = reqJsonData.getType();   //0表示远程关机   1表示驱蚊，2表示爱心，3表示打开关闭GPS   4 表示远程关机  5 老人跌倒
		String belongProject = reqJsonData.getB_g();
		if(userId==null || "".equals(userId)){
			userId = "-1";	
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("devcie_imei='"+serieNo+"' and belong_project ='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveInfos.size()>0){
				userId = ""+deviceActiveInfos.get(0).getAt("user_id");
			}
		}
		
		PhoneInfo phoneInfo = new PhoneInfo();
		phoneInfo.setCondition("serie_no='"+serieNo+"' and belong_project ='"+belongProject+"'");
		
		if("3".equals(type)) {
			String gpsOn = reqJsonData.getGsp_on();
			result = Constant.SUCCESS_CODE;
		}else if("4".equals(type)){
			String light = reqJsonData.getP_l();
			if(light==null || "".equals(light)){
				light="0";
			}
			result = Constant.SUCCESS_CODE;			
		}else {
			PhoneInfoFacade phoneInfoFacade = serviceBean.getPhoneInfoFacade();
			List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
			if(phoneInfos.size()>0){
				if("0".equals(type)){
					phoneInfo.setShutdown("1");
				}else if("1".equals(type)){
					String repellent = reqJsonData.getRepellent();
					phoneInfo.setRepellent(repellent);
				}else if("2".equals(type)){
					String heart = reqJsonData.getHeart();
					phoneInfo.setHeart(heart);
				}
				phoneInfo.setSerieNo(serieNo);
				phoneInfoFacade.updatePhoneInfo(phoneInfo);
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}
		}
		respJsonData.setResultCode(result);
		return respJsonData;
	}
	
	//上课防打扰提醒
	public static RespJsonData disturbHelp(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String monday = reqJsonData.getMonday();
		String tuesday = reqJsonData.getTuesday();
		String wednesday = reqJsonData.getWednesday();
		String thursday = reqJsonData.getThursday();
		String firday = reqJsonData.getFriday();
		String saturday = reqJsonData.getSaturday();
		String sunday = reqJsonData.getSunday();
		String distrub = reqJsonData.getDistrub();
		String belongProject = reqJsonData.getB_g();
		
		if(userId==null ||"".equals(userId)){
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("device_imei = '"+serieNo+"' and belongProject ='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveInfos.size()>0){
				userId = ""+deviceActiveInfos.get(0).getAt("user_id");
			}			
		}
		DirectiveInfo directiveInfo = new DirectiveInfo();
		directiveInfo.setCondition("serie_no = '"+serieNo+"' and belong_project='"+belongProject+"'");
		
		DirectiveInfoFacade directiveInfoFacade = serviceBean.getDirectiveInfoFacade();
		List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
		directiveInfo.setMdistime(monday);
		directiveInfo.setTdistime(tuesday);
		directiveInfo.setWdistime(wednesday);
		directiveInfo.setThdistime(thursday);
		directiveInfo.setFdistime(firday);
		directiveInfo.setSdistime(saturday);
		directiveInfo.setSudistime(sunday);
		directiveInfo.setDistrub(distrub);
		directiveInfo.setDistrubChange("1");
		
		if(directiveInfos.size()>0){
			directiveInfoFacade.updateDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}else{
			directiveInfo.setSerie_no(serieNo);
			directiveInfo.setBelongProject(belongProject);
			directiveInfoFacade.insertDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}
		
		respJsonData.setResultCode(result);
		return respJsonData;
	}
	
	//低电提醒
	public static RespJsonData lowElectricityHelp(ReqJsonData reqJsonData) throws SystemException{
		int result =0;
		RespJsonData respJsonData= new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String belongProject = reqJsonData.getB_g();
		if(userId==null || "".equals(userId)){
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("device_imei='"+serieNo+"' and belong_project='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveInfos.size()>0){
				userId = ""+deviceActiveInfos.get(0).getAt("user_id");		
			}
		}
		DirectiveInfo directiveInfo = new DirectiveInfo();
		directiveInfo.setCondition("serie_no ='"+serieNo+"' and belong_project ='"+belongProject+"'");
		DirectiveInfoFacade directiveInfoFacade = serviceBean.getDirectiveInfoFacade();
		List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
		if(directiveInfos.size()>0){
			String isLow = directiveInfos.get(0).getAt("isLow").toString();
			String electricity = (String) directiveInfos.get(0).getAt("lowelectricity");
			if("1".equals(isLow)){
				directiveInfo.setIsLow("0");
				directiveInfoFacade.updateDirectiveInfo(directiveInfo);
			}else if("0".equals(isLow)){
				respJsonData.setElectricity(electricity);
			}
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//远程闹钟
	public static RespJsonData setClockHelp(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String clock = reqJsonData.getClock();
		String belongProject = reqJsonData.getB_g();
		if(userId==null || "".equals(userId)){
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("device_imei ='"+serieNo+"' and belong_project ='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveInfos.size()>0){
				userId = ""+deviceActiveInfos.get(0).getAt("user_id");
			}
			
		}
		
		DirectiveInfo directiveInfo = new DirectiveInfo();
		directiveInfo.setCondition("serie_no = '"+serieNo+"' and belong_project ='"+belongProject+"'");
		DirectiveInfoFacade directiveInfoFacade = serviceBean.getDirectiveInfoFacade();
		List<DataMap> directiveInfos =directiveInfoFacade.getDirectiveInfo(directiveInfo);
		if(directiveInfos.size()>0){
			if("".equals(clock)){
				clock=" ";
			}
			
			directiveInfo.setClock(clock);
			directiveInfo.setAlarmChange("1");
			directiveInfoFacade.updateDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}else{
			if("".equals(clock)){
				clock=" ";
			}
			directiveInfo.setClock(clock);
			directiveInfo.setAlarmChange("1");
			directiveInfo.setBelongProject(belongProject);
			directiveInfo.setSerie_no(serieNo);
			directiveInfoFacade.insertDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
}
