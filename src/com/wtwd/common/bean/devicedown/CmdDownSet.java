package com.wtwd.common.bean.devicedown;

import com.godoing.rose.lang.SystemException;

public interface CmdDownSet {
	
	public void setBeattim(String imeiStr, Integer beatTim) throws SystemException;
	
	public boolean setLedState(String imeiStr, int n, boolean ledFlag);
	
	public boolean setLedState(String imeiStr, int n, boolean ledFlag, Integer userId, Thread lock);
	
	public void setWIFIRange(String imeiStr, int interval, boolean wifiFlag) throws SystemException;
	
	public void setUrgentMode(String imeiStr, boolean stateFlag) throws SystemException;
	
	public boolean setUrgentMode(String imeiStr, boolean stateFlag, String cmdTime, Integer duration, Integer userId, Thread lock);

	public boolean setUrgentMode(String imeiStr, boolean stateFlag,boolean ledFlag, boolean playFlag,  String cmdTime, Integer duration, Integer userId, Thread lock);
	
	public void setFamilyNumber(String imeiStr, String family_ssid, String family_passwd, String host_ssid) throws SystemException;
	
	public void setFlightMode(String imeiStr, boolean modeFlag) throws SystemException;

	public void setGpsMap(String imeiStr, boolean gpsMapFlag) throws SystemException;
	
	public boolean setEcoMode(String imeiStr, boolean modeFlag, Integer userId, Thread lock);
	
	public void setCallRingTone(String imeiStr, int n) throws SystemException;
	
	public void setPlayRing(String imeiStr, int n, boolean playFlag) throws SystemException;
	
	public void setPlayRing(String imeiStr, int n, boolean playFlag, Integer userId) throws SystemException;
	
	public boolean getLocation(String imeiStr, Integer gps, Integer userId, Thread lock);
	
	public void getDevState(String imeiStr) throws SystemException;
	
	public void setDevDebug(String imeiStr, boolean debugMode) throws SystemException;

	public boolean setSleepState(String imeiStr, boolean sleepFlag, Integer userId, Thread lock) ;
	public void setLctEsafe(String imeiStr, boolean lctEsafeFlag, Integer userId) throws SystemException;
	public boolean getSsidList(String imeiStr, boolean ssidFlag, Integer userId, Thread lock) ;
	public boolean setSsidEsafe(String imeiStr, boolean ssidEsafeFlag, String bssid, String ssid, Integer userId, Thread lock) ;
	public boolean getHealthStep(String imeiStr, boolean upStepFlag, Integer userId, Thread lock); 
	public boolean detectDevUp(String imeiStr, boolean upFlag, Integer userId, Thread lock);
	public boolean updateFirmware(String imeiStr, Integer updateFlag, String updateVer, Integer userId) throws SystemException;
	
	public boolean setDevOffOn(String imeiStr, Integer offOnFlag, String offTime, String onTime, Integer userId, Thread lock);
}
