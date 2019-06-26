﻿package com.wtwd.common.config;

import java.nio.charset.Charset;

import org.springframework.beans.factory.BeanFactory;

import com.godoing.rose.spring.SpringUtils;
import com.google.gson.Gson;
import com.wtwd.pet.app.entity.WMsgInfo;
import com.wtwd.pet.app.notify.MyThread;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.channelinfo.domain.logic.ChannelInfoFacade;
import com.wtwd.sys.companyinfo.domain.logic.CompanyInfoFacade;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.wtwd.sys.funcinfo.domain.logic.FuncInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppDeviceDiscoveryManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceLocationFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceTrackFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserUploadLocationFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserWiFiInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WPetMoveInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSoundInfoFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSuggestionFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppMsgManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppQuestionInfoManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTCheckVersionFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTDevSetaFacade;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wappuserverify.domain.logic.WappuserVerifyFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wfciPetKind.domain.logic.WfciPetKindFacade;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade;
import com.wtwd.sys.innerw.wpetwifirange.domain.logic.WpetWifiRangeFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.innerw.wupfirmware.domain.logic.WupFirmwareFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.wtwd.sys.monitorinfo.domain.logic.MonitorInfoFacade;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.wtwd.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.wtwd.sys.rolefuncinfo.domain.logic.RoleFuncInfoFacade;
import com.wtwd.sys.roleinfo.domain.logic.RoleInfoFacade;
import com.wtwd.sys.saleinfo.domain.logic.SaleInfoFacade;
import com.wtwd.sys.sysloginfo.domain.logic.SysLogInfoFacade;
import com.wtwd.sys.userinfo.domain.logic.UserInfoFacade;
import com.wtwd.sys.userlogininfo.domain.logic.UserLoginInfoFacade;



public class ServiceBean {

	static BeanFactory factory;
	private static ServiceBean instance;
	
	static UserInfoFacade userInfoFacade;
	static FuncInfoFacade  funcInfoFacade;
    static RoleInfoFacade roleInfoFacade;
    static RoleFuncInfoFacade roleFuncInfoFacade;
    static SysLogInfoFacade sysLogInfoFacade;
    static MonitorInfoFacade monitorInfoFacade;
	static PhoneInfoFacade phoneInfoFacade;
	static AppUserInfoFacade appUserInfoFacade;
	static LocationInfoFacade locationInfoFacade;
	static DeviceActiveInfoFacade deviceActiveInfoFacade;
	static DirectiveInfoFacade directiveInfoFacade;
	static CompanyInfoFacade companyInfoFacade;
    static ChannelInfoFacade channelInfoFacade;
    static ProjectInfoFacade projectInfoFacade;
    static SaleInfoFacade saleInfoFacade;
    static UserLoginInfoFacade userLoginInfoFacade;
    static DeviceLoginFacade deviceLoginFacade;

	static WappUsersFacade wappUsersFacade;
	static WdeviceActiveInfoFacade wdeviceActiveInfoFacade;		//20150625 label
	static WpetMoveInfoFacade wpetMoveInfoFacade;		//20150625 label
    static WfciPetKindFacade wfciPetKindFacade;
    static WappuserVerifyFacade wappuserVerifyFacade;
    static WshareInfoFacade wshareInfoFacade;
    static WpetFacade wpetFacade;
    
    static WpetWifiRangeFacade wpetWifiRangeFacade;
    static WupFirmwareFacade wupFirmwareFacade;
    
    // liufeng
    static AppUserDeviceLocationFacade appUserDeviceLocationFacade;
    static AppUserDeviceTrackFacade appUserDeviceTrackFacade;
    static AppUserUploadLocationFacade appUserUploadLocationFacade;
    static AppUserWiFiInfoFacade appUserWiFiInfoFacade;
    static AppSafeAreaManFacade appSafeAreaManFacade;
    static AppDeviceDiscoveryManFacade appDeviceDiscoveryManFacade;
    static WTDevSetaFacade wtDevSetaFacade;
    static WTCheckVersionFacade wtCheckVersionFacade;
    static WSuggestionFacade wSuggestionFacade;
    static WPetMoveInfoFacade wPetMoveInfoFacade;
    static WTAppMsgManFacade wtAppMsgManFacade;
    static WSoundInfoFacade wSoundInfoFacade;
    static WTAppQuestionInfoManFacade wtAppQuestionInfoManFacade;


	public static ServiceBean getInstance() {
		if (instance == null) {
			instance = new ServiceBean();
			factory = SpringUtils.getBeanFactory();  //���ع���ģʽ
			/*
			if ( MqttClient.mqtt == null ) 
				MqttClient.Init();*/			
			
		}		
		userInfoFacade = (UserInfoFacade)factory.getBean("userInfoFacade");
		funcInfoFacade = (FuncInfoFacade)factory.getBean("funcInfoFacade");
		roleInfoFacade = (RoleInfoFacade)factory.getBean("roleInfoFacade");
		roleFuncInfoFacade = (RoleFuncInfoFacade)factory.getBean("roleFuncInfoFacade");
		sysLogInfoFacade = (SysLogInfoFacade)factory.getBean("sysLogInfoFacade");
		monitorInfoFacade = (MonitorInfoFacade)factory.getBean("monitorInfoFacade");
		phoneInfoFacade = (PhoneInfoFacade)factory.getBean("phoneInfoFacade");
		appUserInfoFacade = (AppUserInfoFacade)factory.getBean("appUserInfoFacade");
		locationInfoFacade = (LocationInfoFacade)factory.getBean("locationInfoFacade");
		deviceActiveInfoFacade = (DeviceActiveInfoFacade)factory.getBean("deviceActiveInfoFacade");
		directiveInfoFacade = (DirectiveInfoFacade)factory.getBean("directiveInfoFacade");
		monitorInfoFacade = (MonitorInfoFacade)factory.getBean("monitorInfoFacade");
		companyInfoFacade = (CompanyInfoFacade)factory.getBean("companyInfoFacade");
		channelInfoFacade = (ChannelInfoFacade)factory.getBean("channelInfoFacade");
		projectInfoFacade = (ProjectInfoFacade)factory.getBean("projectInfoFacade");
		saleInfoFacade = (SaleInfoFacade)factory.getBean("saleInfoFacade");
		userLoginInfoFacade = (UserLoginInfoFacade)factory.getBean("userLoginInfoFacade");
		deviceLoginFacade = (DeviceLoginFacade)factory.getBean("deviceLoginFacade");

		wappUsersFacade = (WappUsersFacade)factory.getBean("wappUsersFacade");
		wdeviceActiveInfoFacade = (WdeviceActiveInfoFacade)factory.getBean("wdeviceActiveInfoFacade");
		wpetMoveInfoFacade = (WpetMoveInfoFacade) factory.getBean("wpetMoveInfoFacade");
		wfciPetKindFacade = (WfciPetKindFacade) factory.getBean("wfciPetKindFacade");
		wappuserVerifyFacade = (WappuserVerifyFacade) factory.getBean("wappuserVerifyFacade");
		wshareInfoFacade = (WshareInfoFacade) factory.getBean("wshareInfoFacade");
		wpetFacade = (WpetFacade) factory.getBean("wpetFacade");

		wpetWifiRangeFacade = (WpetWifiRangeFacade) factory.getBean("wpetWifiRangeFacade");
		appUserDeviceLocationFacade = (AppUserDeviceLocationFacade) factory.getBean("appUserDeviceLocationFacade");
		appUserDeviceTrackFacade = (AppUserDeviceTrackFacade) factory.getBean("appUserDeviceTrackFacade");
		appUserUploadLocationFacade = (AppUserUploadLocationFacade) factory.getBean("appUserUploadLocationFacade");
		appUserWiFiInfoFacade = (AppUserWiFiInfoFacade) factory.getBean("appUserWiFiInfoFacade");
		appSafeAreaManFacade = (AppSafeAreaManFacade) factory.getBean("appSafeAreaManFacade"); 
		appDeviceDiscoveryManFacade = (AppDeviceDiscoveryManFacade) factory.getBean("appDeviceDiscoveryManFacade");
		wtDevSetaFacade = (WTDevSetaFacade) factory.getBean("wtDevSetaFacade");
		wtCheckVersionFacade = (WTCheckVersionFacade) factory.getBean("wtCheckVersionFacade");

		wSuggestionFacade = (WSuggestionFacade) factory.getBean("wSuggestionFacade");
		wPetMoveInfoFacade = (WPetMoveInfoFacade) factory.getBean("wPetMoveInfoFacade");
		wtAppMsgManFacade = (WTAppMsgManFacade) factory.getBean("wtAppMsgManFacade");
		wSoundInfoFacade = (WSoundInfoFacade) factory.getBean("wSoundInfoFacade");
		wtAppQuestionInfoManFacade = (WTAppQuestionInfoManFacade) factory.getBean("wtAppQuestionInfoManFacade");
		
		wupFirmwareFacade = (WupFirmwareFacade)factory.getBean("wupFirmwareFacade");
		
		return instance;
	}
	
	public void pushMsg(String topic, byte[] payload ) {
/*		
		if ( MqttClient.mqtt == null ) 
			MqttClient.Init();
		
		try {
			MqttClient.pushMsg(topic, payload);
		} catch(Exception e) {
			e.printStackTrace();
		}
*/
		   String msg = new String(payload, Charset.forName("UTF-8"));
		   WMsgInfo vo = null;
		   Gson gson=new Gson();	
		   
		   vo = gson.fromJson(msg, WMsgInfo.class);
					//					String ios_real = vo.getIos_real();
					//					//if ( !App.isNullOrEmpty(ios_real) )
					//						System.out.println("ios_real:" + ios_real);
			
			
			MyThread myThread1 = new MyThread(vo);
			myThread1.start();  

	}
	
	
	public WpetMoveInfoFacade getWpetMoveInfoFacade() {
		return wpetMoveInfoFacade;
	}
	
	public WdeviceActiveInfoFacade getWdeviceActiveInfoFacade() {
		return wdeviceActiveInfoFacade;
	}

	public UserInfoFacade getUserInfoFacade() {
		return userInfoFacade;
	}
	public FuncInfoFacade getFuncInfoFacade() {
		return funcInfoFacade;
	}
	public RoleFuncInfoFacade getRoleFuncInfoFacade() {
		return roleFuncInfoFacade;
	}
	public RoleInfoFacade getRoleInfoFacade() {
		return roleInfoFacade;
	}
	public SysLogInfoFacade getSysLogInfoFacade() {
		return sysLogInfoFacade;
	}
	public MonitorInfoFacade getMonitorInfoFacade() {
		return monitorInfoFacade;
	}
	public PhoneInfoFacade getPhoneInfoFacade() {
		return phoneInfoFacade;
	}
	public AppUserInfoFacade getAppUserInfoFacade() {
		return appUserInfoFacade;
	}
	public LocationInfoFacade getLocationInfoFacade() {
		return locationInfoFacade;
	}
	public DeviceActiveInfoFacade getDeviceActiveInfoFacade() {
		return deviceActiveInfoFacade;
	}
	public DirectiveInfoFacade getDirectiveInfoFacade() {
		return directiveInfoFacade;
	}
	public CompanyInfoFacade getCompanyInfoFacade() {
		return companyInfoFacade;
	}
	public ChannelInfoFacade getChannelInfoFacade() {
		return channelInfoFacade;
	}
	public ProjectInfoFacade getProjectInfoFacade() {
		return projectInfoFacade;
	}
	public static SaleInfoFacade getSaleInfoFacade() {
		return saleInfoFacade;
	}
	public UserLoginInfoFacade getUserLoginInfoFacade() {
		return userLoginInfoFacade;
	}
	public DeviceLoginFacade getDeviceLoginFacade() {
		return deviceLoginFacade;
	}

	public WappUsersFacade getWappUsersFacade() {
		return wappUsersFacade;
	}

	public WfciPetKindFacade getWfciPetKindFacade() {
		return wfciPetKindFacade;
	}
	
	public WappuserVerifyFacade getWappuserVerifyFacade() {
		return wappuserVerifyFacade;
	}

	public WshareInfoFacade getWshareInfoFacade() {
		return wshareInfoFacade;
	}

	public WpetFacade getWpetFacade() {
		return wpetFacade;
	}
	
	public WpetWifiRangeFacade getWpetWifiRangeFacade() {
		return wpetWifiRangeFacade;
	}
	
	public AppUserDeviceLocationFacade getAppUserDeviceLocationFacade() {
		return appUserDeviceLocationFacade;
	}

	public AppUserDeviceTrackFacade getAppUserDeviceTrackFacade() {
		return appUserDeviceTrackFacade;
	}

	public AppUserUploadLocationFacade getAppUserUploadLocationFacade() {
		return appUserUploadLocationFacade;
	}

	public AppUserWiFiInfoFacade getAppUserWiFiInfoFacade() {
		return appUserWiFiInfoFacade;
	}

	public AppSafeAreaManFacade getAppSafeAreaManFacade() {
		return appSafeAreaManFacade;
	}

	public AppDeviceDiscoveryManFacade getAppDeviceDiscoveryManFacade() {
		return appDeviceDiscoveryManFacade;
	}

	public WTDevSetaFacade getWtDevSetaFacade() {
		return wtDevSetaFacade;
	}

	public WTCheckVersionFacade getWtCheckVersionFacade() {
		return wtCheckVersionFacade;
	}

	public void setWtCheckVersionFacade(
			WTCheckVersionFacade wtCheckVersionFacade) {
		ServiceBean.wtCheckVersionFacade = wtCheckVersionFacade;
	}

	public WSuggestionFacade getwSuggestionFacade() {
		return wSuggestionFacade;
	}

	public WPetMoveInfoFacade getwPetMoveInfoFacade() {
		return wPetMoveInfoFacade;
	}

	public WTAppMsgManFacade getWtAppMsgManFacade() {
		return wtAppMsgManFacade;
	}

	public static WSoundInfoFacade getwSoundInfoFacade() {
		return wSoundInfoFacade;
	}

	public static WTAppQuestionInfoManFacade getWtAppQuestionInfoManFacade() {
		return wtAppQuestionInfoManFacade;
	}

	/**
	 * @return the wupFirmwareFacade
	 */
	public static WupFirmwareFacade getWupFirmwareFacade() {
		return wupFirmwareFacade;
	}
	
	
}
