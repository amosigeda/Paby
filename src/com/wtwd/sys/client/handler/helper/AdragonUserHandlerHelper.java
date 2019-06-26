package com.wtwd.sys.client.handler.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.other.AppUserInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.wtwd.sys.saleinfo.domain.SaleInfo;
import com.wtwd.sys.userlogininfo.domain.UserLoginInfo;
import com.wtwd.utils.IPSeeker;

public class AdragonUserHandlerHelper {
	
	private static ServiceBean serviceBean = ServiceBean.getInstance();
	
	//注册
	public static RespJsonData userAdrRegister(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;	
		Date date = new Date();
		
		RespJsonData respJsonData = new RespJsonData();
		
		String userName = reqJsonData.getUser_name();
		String password = reqJsonData.getUser_password();
		String userImei = reqJsonData.getUser_imei();
		if(userImei==null || "".equals(userImei)){
			userImei = "000000000000000";
		}	
		String phone_model = reqJsonData.getPhone_model();
		String phone_version = reqJsonData.getPhone_version();
		String app_version = reqJsonData.getApp_version();
		String user_phone = reqJsonData.getUser_phone();
		AppUserInfo userInfo = new AppUserInfo();
		if(userName!=null && !"".equals(userName)){
			userInfo.setCondition("user_name='"+userName+"'");
		}	
		AppUserInfoFacade appUserInfoFacade =serviceBean.getAppUserInfoFacade();
		List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(userInfo);
		if(appUserInfos.size()<=0){
			userInfo.setHead("0");
			userInfo.setAge("2015-12-12");
			userInfo.setNickName(userName);
			userInfo.setPassword(password);						
			userInfo.setCreateTime(date);
			userInfo.setLastLoginTime(date);
			userInfo.setSex("0");
			userInfo.setStatus("1");
			userInfo.setUserHeight("160");
			userInfo.setUserName(userName);
			userInfo.setUserWeight("70");
			userInfo.setBelongProject("1"); //暂时设定为1
			userInfo.setBindCount("0");
			appUserInfoFacade.insertAppUserInfo(userInfo);
						
			List<DataMap> appUserInfo = appUserInfoFacade.getAppUserInfo(userInfo);
			if(!appUserInfo.isEmpty()){
				String user_id = appUserInfo.get(0).getAt("id").toString();
				respJsonData.setUser_id(user_id);
			}
			String ip="";
			String province = "";						
			SaleInfo saleInfo = new SaleInfo();		
			saleInfo.setUserName(userName);		
			saleInfo.setImei(userImei);		
			saleInfo.setImsi(userImei);		
			saleInfo.setPhone(user_phone);		
			saleInfo.setPhoneModel(phone_model);		
			saleInfo.setSysVersion(phone_version);		
			saleInfo.setDateTime(new Date());		
			saleInfo.setIp(ip);		
			saleInfo.setProvince(province);		
			saleInfo.setBelongProject("1");		
			saleInfo.setAppVersion(app_version);		
					
			ServiceBean.getSaleInfoFacade().insertSaleInfo(saleInfo);
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//登录
	public static RespJsonData userAdrLogin(ReqJsonData reqJsonData) throws SystemException{
		int result=0;
		RespJsonData respJsonData = new RespJsonData();
		
		String userName = reqJsonData.getUser_name();
		String password = reqJsonData.getUser_password();
		String belongProject = reqJsonData.getB_g();
		if(belongProject==null||belongProject==""){
			belongProject = "0";
		}
		String userImei = reqJsonData.getUser_imei();
		if(userImei==null||userImei==""){
			userImei = "0";
		}
		String userImsi = reqJsonData.getUser_imsi();
		if(userImsi==null||userImsi==""){
			userImsi = "0";
		}
		String phoneModel = reqJsonData.getPhone_model();
		String phoneVersion = reqJsonData.getPhone_version();
		String appVersion = reqJsonData.getApp_version();
		String type = reqJsonData.getType();
		
		AppUserInfo appUserInfo = new AppUserInfo();
		if(userName!=null && !"".equals(userName)){
			appUserInfo.setCondition("user_name ='"+userName+"' and belong_project=+'"+belongProject+"'");
		}
		AppUserInfoFacade appUserInfoFacade = serviceBean.getAppUserInfoFacade();
		List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
		
		String userId="";
		if(appUserInfos.size()>0){
			appUserInfos.clear();
			appUserInfo.setCondition("user_name='"+userName+"' and password='"+password+"' and belong_project = '"+belongProject+"'");
			appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
			if(appUserInfos.size()>0){
				AppUserInfoAdr appUserInfoAdr = new AppUserInfoAdr();
				List<AppUserInfoAdr> appUserInfoAdrs = new ArrayList<AppUserInfoAdr>();
				userId = appUserInfos.get(0).getAt("id").toString();
				appUserInfoAdr.setUser_id(userId);
				appUserInfoAdr.setUser_nick(appUserInfos.get(0).getAt("nick_name").toString());
				appUserInfoAdr.setUser_sex(appUserInfos.get(0).getAt("sex").toString());
				appUserInfoAdr.setUser_age(appUserInfos.get(0).getAt("age").toString());
				appUserInfoAdr.setUser_height(appUserInfos.get(0).getAt("height").toString());
				appUserInfoAdr.setUser_weight(appUserInfos.get(0).getAt("weight").toString());
				appUserInfoAdr.setUser_head(appUserInfos.get(0).getAt("head").toString());
				appUserInfoAdrs.add(appUserInfoAdr);
				
				respJsonData.setAppUserInfo(appUserInfoAdrs);
				result = Constant.SUCCESS_CODE;
				
				String dir = "";
				String fileName = ""; //找到文件的名字
		 	    IPSeeker ipSeeker = new IPSeeker(fileName,dir);   //ip数据库的地址和要存储的地址  
				String ip ="";
				String province = ipSeeker.getIPLocation(ip).getCountry();
				
				UserLoginInfo userLogin = new UserLoginInfo();
				userLogin.setUserId(Integer.parseInt(userId));
				userLogin.setLoginTime(new Date());
				userLogin.setImei(userImei);
				userLogin.setImsi(userImsi);
				userLogin.setPhoneModel(phoneModel);
				userLogin.setPhoneVersion(phoneVersion);
				userLogin.setAppVersion(appVersion);
				userLogin.setIp(ip);
				userLogin.setProvince(province);
				userLogin.setBelongProject(belongProject);
				serviceBean.getUserLoginInfoFacade().insertUserLoginInfo(userLogin);
			}else{
				result = Constant.FAIL_CODE;
			}		
		}else{
			result = 2;
		}	
		respJsonData.setResultCode(result);
		
		return respJsonData;		
	}
	
	//用户信息修改
	public static RespJsonData modifyAdrUser(ReqJsonData reqJsonData,String serviceName) throws Exception{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		String userId = reqJsonData.getUser_id();
		String userName = reqJsonData.getUser_name();
		String head = reqJsonData.getUser_head();
		String sex = reqJsonData.getUser_sex();
		String age = reqJsonData.getUser_age();
		String height = reqJsonData.getUser_height();
		String weight = reqJsonData.getUser_weight();
		
		AppUserInfo appUserInfo = new AppUserInfo();		
		if(!"0".equals(head)){
			String path = "" + userId;
			Constant.deleteFile(path);  
			
			String fileName = Constant.getUniqueCode(userId) + ".png";		
			Constant.createFileContent(path, fileName, Base64.decodeBase64(head));
			
			String url = "http://" +serviceName +":";
			int port = 0;
			String downloadpath = "" + Constant.USER_SAVE + userId + "/" +fileName;
			head = Constant.getDownloadPath(url, String.valueOf(port), downloadpath);
			appUserInfo.setHead(head);						
		}
		
		userName = Constant.transCodingToUtf(userName); 
		appUserInfo.setNickName(userName);
		appUserInfo.setSex(sex);
		appUserInfo.setAge(age);
		appUserInfo.setUserHeight(height);
		appUserInfo.setUserWeight(weight);	
		
		int num = serviceBean.getAppUserInfoFacade().updateAppUserInfo(appUserInfo);
		if(num > 0){
			result = Constant.SUCCESS_CODE;					
		}else{
			result = Constant.FAIL_CODE;
		}
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//设备验证
	public static RespJsonData verifyAdrDevice(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		
		
		return respJsonData;		
	}
	
	//添加宝贝
	public static RespJsonData addAdragonDevice(ReqJsonData reqJsonData,String serverName) throws IOException, SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String deviceImei = reqJsonData.getDevice_imei();
		String deviceName = reqJsonData.getDevice_name();
		String devicePhone = reqJsonData.getDevice_phone();
		String head = reqJsonData.getUser_head();
		String sex = reqJsonData.getUser_sex();
		String age = reqJsonData.getUser_age();
		String height = reqJsonData.getUser_height();
		String weight = reqJsonData.getUser_weight();
		String belongProject = reqJsonData.getB_g();
		
		if (sex == null) sex = "0";
		if (age == null) age = "2015-12-12";
		if (height == null) height = "170";
		if (weight == null) weight = "100";
		
		if(userId!=null && !"".equals(userId) && deviceImei!=null && !"".equals(deviceImei)){
			
			deviceName = Constant.transCodingToUtf(deviceName);
			
			PhoneInfo phoneInfo = new PhoneInfo();
			phoneInfo.setCondition("serie_no ='"+deviceImei+"'");
			PhoneInfoFacade phoneInfoFacade = serviceBean.getPhoneInfoFacade();
			List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
			if(phoneInfos.size()<=0){
				result = Constant.BOND_FAIL_NO;
			}else{
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+deviceImei+"' and belong_project='"+belongProject+"' and device_disable = '1'");
				DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
				List<DataMap> deviceActiveInfos =deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
				if(deviceActiveInfos.size()>0){
					if(!"0".equals(head)){
						String path ="";
						Constant.deleteFile(path); 
						
						String fileName = Constant.getUniqueCode(deviceImei) + ".png";			
						Constant.createFileContent(path, fileName, Base64.decodeBase64(head));
						
						String url = "http://" +serverName +":";
						deviceActiveInfo.setDeviceHead(head);
					}		
					deviceActiveInfo.setDeviceAge(age);
					deviceActiveInfo.setDeviceHeight(height);
					deviceActiveInfo.setDeviceName(deviceName);
					deviceActiveInfo.setDeviceSex(sex);
					deviceActiveInfo.setDeviceWeight(weight);		
					deviceActiveInfo.setDevicePhone(devicePhone);
					deviceActiveInfoFacade.updateDeviceActiveInfo(deviceActiveInfo);
					phoneInfo.setPhone(devicePhone);
					phoneInfoFacade.updatePhoneInfo(phoneInfo);
					result = Constant.SUCCESS_CODE;  
				}
			}
		}
		
		respJsonData.setResultCode(result);
		
		return respJsonData;
	}
	
	//删除宝贝
	public static RespJsonData deleteAdrDevice(ReqJsonData reqJsonData) throws SystemException{
		int result=0;
		RespJsonData respJsonData = new RespJsonData();
		
		return respJsonData;
	}
}
