package com.wtwd.common.bean.response;

import java.util.List;

import com.wtwd.common.bean.devicedown.cmdobject.Beattim;
import com.wtwd.common.bean.devicedown.cmdobject.CallRingTone;
import com.wtwd.common.bean.devicedown.cmdobject.DevOffOn;
import com.wtwd.common.bean.devicedown.cmdobject.DevUp;
import com.wtwd.common.bean.devicedown.cmdobject.EcoMode;
import com.wtwd.common.bean.devicedown.cmdobject.FamilyNumber;
import com.wtwd.common.bean.devicedown.cmdobject.FlightMode;
import com.wtwd.common.bean.devicedown.cmdobject.GetHealthStep;
import com.wtwd.common.bean.devicedown.cmdobject.GetLocation;
import com.wtwd.common.bean.devicedown.cmdobject.GetSsidList;
import com.wtwd.common.bean.devicedown.cmdobject.GpsMapMenu;
import com.wtwd.common.bean.devicedown.cmdobject.LctEsafe;
import com.wtwd.common.bean.devicedown.cmdobject.LedState;
import com.wtwd.common.bean.devicedown.cmdobject.Location;
import com.wtwd.common.bean.devicedown.cmdobject.PlayRing;
import com.wtwd.common.bean.devicedown.cmdobject.SleepState;
import com.wtwd.common.bean.devicedown.cmdobject.SsidEsafe;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime;
import com.wtwd.common.bean.devicedown.cmdobject.SynTime2;
import com.wtwd.common.bean.devicedown.cmdobject.SyncDevState;
import com.wtwd.common.bean.devicedown.cmdobject.UpdateFirmware;
import com.wtwd.common.bean.devicedown.cmdobject.UrgentMode;
import com.wtwd.common.bean.devicedown.cmdobject.WifiRange;
import com.wtwd.common.bean.other.AppUserInfoAdr;
import com.wtwd.common.bean.other.CheckInfoAdr;
import com.wtwd.common.bean.other.DirectiveInfoAdr;
import com.wtwd.common.bean.other.LocationInfoAdr;
import com.wtwd.common.bean.other.MsgInfoAdr;
import com.wtwd.common.bean.other.RelativeCallInfoAdr;
import com.wtwd.common.bean.other.SafeAreaAdr;
import com.wtwd.common.bean.other.ShareInfoAdr;
import com.wtwd.common.bean.other.StepParameters;
import com.wtwd.common.bean.other.VoiceInfoAdr;


public class RespJsonData {

	private String cmd;
	
	private String ret;
	
	private String msg;
	
	//心跳响应的和时间同步响应得
	private String devTime;
	
	private String haveUnread;

	//闹钟响应的
	private String msgId;
	
	//响应小孩信息
	private String name;
		
	private Integer sex;
		
	private String birthday;
		
	//响应动态验证码
	private String captcha;
	
	//响应话费查询
	private String operator;
	
	//监听返回电话号码
	private String phoneNum;
	
	//对客户端的响应操作
	private String resp;
	
//	private int resultCode;
	private Integer resultCode;
	
	//运动检测
	private String sport;
	
	//体温检测
	private String temperature;
	
	//心率检测
	private String heatrate;
	
	//注册用户id
	private String user_id;
	
	//推送定位数据
	private List<Double> position;
	
	private String user_head;
	
	//电池百分比
	private String batPower;
	
	//查询话费短信指令
	private String smscont;
	
	private String warn;  //1表示有警报，0表示没有警报
	
	private String listen; //0表示无监听，否则返回电话号码
	
	private String f_m; //为1时表示有变化，0表示没有变化(有变化,则请求亲情号码接口)
	
	private String relative_id; //围栏id
	
	private String device_SafeId;
	
	private List<AppUserInfoAdr> appUserInfo;
	
	private CheckInfoAdr checkInfo;
	
	private List<LocationInfoAdr> locationInfos;
	
	private Integer safe_count;
	
	private List<SafeAreaAdr> safeAreas;
	
	private List<ShareInfoAdr> shareInfos;
	
	private List<MsgInfoAdr> msgInfos;
	
	private String electricity;
	
	private String date_time;
	
	private String volume;
	
	private String light;
	
	private String auto_mute;
	
	private String power_off;
	
	private String GPS_ON;
	
	private String light_sensor;
	
	private List<VoiceInfoAdr> y_y; //语音
		
	private List<RelativeCallInfoAdr> family_group;  //请求亲情号码
	
	private String disturb;  //上课放打扰
	
	private List<DirectiveInfoAdr> times; //上课防打扰
	
	private List<DirectiveInfoAdr> sleep;  //睡眠提醒
	
	private List<DirectiveInfoAdr> alarm;  //远程闹钟
	
	private StepParameters stepParameters;     //计步参数
	
	private String ack;    //beat response
	private Integer ping;   //ping response
	
	private String userId;    //get log debug info iserId -1;
	private String appToken;     //get log debug info app token
	
	//Kevin added 20160827 for device cmd down
	private Beattim setBeattim;
	private FamilyNumber setFamilyNumber;
	private FlightMode setFlightMode;
	private LedState setLedState;
	private UrgentMode setUrgentMode;
	private WifiRange setWifiRange;
	private GpsMapMenu setGpsMap;
    private String synDevTime;
    private EcoMode setEcoMode;
    private CallRingTone setRingTone;
    private PlayRing setPlayRing;
    private GetLocation getLocation;
    private String getDevState;
    private String setDevDebug;
    private SleepState setSleepState;
    private LctEsafe setLctEsafe;
    private GetSsidList getSsidList;
    private SsidEsafe setSsidEsafe;
    private GetHealthStep getHealthStep;
    private DevUp detectDevUp;
    private UpdateFirmware updateFirmware;
    private String updateVer;
    private SynTime synTime;
    private SynTime2 synTime2;
    private Location location;
    private String lctTime;
    
    private Integer upDeviceInfo;
    private Integer lctGps;
    private Integer lctLbs;
    private Integer uploadlctWIFI;
    private Integer lctLbsWifi;
    private Integer lctGGps;
    private Integer lctGMap;
    private Integer lowBattery;
    private Integer upStepData;
    private Integer upStepDatas;
    private Integer charging;
    private Integer offline;
    private Integer upSleepData;
    private Integer upSleepDatas;
    private Integer getLogToken;
    private Integer ssidEsafeState;
    private Integer updateFirmwareRes;
    private Integer updateStart;
    private Integer lctUpdate;
    
    
    private SyncDevState syncDevState;
    private DevOffOn devOffOn;
    
    private Integer uLFq;
    private Integer uLFqMode;
    private Integer uLSosWifi;
    
    private Integer uLTe;       
    private Integer ts;
    private Integer lfq;
    
	
	public String getSmscont() {
		return smscont;
	}

	public void setSmscont(String smscont) {
		this.smscont = smscont;
	}
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDevTime() {
		return devTime;
	}

	public void setDevTime(String devTime) {
		this.devTime = devTime;
	}

	public String getHaveUnread() {
		return haveUnread;
	}

	public void setHaveUnread(String haveUnread) {
		this.haveUnread = haveUnread;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
		
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
		
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getHeatrate() {
		return heatrate;
	}

	public void setHeatrate(String heatrate) {
		this.heatrate = heatrate;
	}
	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public List<Double> getPosition() {
		return position;
	}

	public void setPosition(List<Double> position) {
		this.position = position;
	} 
	
	public String getUser_head() {
		return user_head;
	}

	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}

	public String getBatPower() {
		return batPower;
	}

	public void setBatPower(String batPower) {
		this.batPower = batPower;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getListen() {
		return listen;
	}

	public void setListen(String listen) {
		this.listen = listen;
	}

	public String getF_m() {
		return f_m;
	}

	public void setF_m(String f_m) {
		this.f_m = f_m;
	}		
	
	public String getRelative_id() {
		return relative_id;
	}

	public void setRelative_id(String relative_id) {
		this.relative_id = relative_id;
	}
	
	public String getDevice_SafeId() {
		return device_SafeId;
	}

	public void setDevice_SafeId(String device_SafeId) {
		this.device_SafeId = device_SafeId;
	}

	public List<LocationInfoAdr> getLocationInfos() {
		return locationInfos;
	}

	public void setLocationInfos(List<LocationInfoAdr> locationInfos) {
		this.locationInfos = locationInfos;
	}		
	
	public Integer getSafe_count() {
		return safe_count;
	}

	public void setSafe_count(Integer safe_count) {
		this.safe_count = safe_count;
	}

	public List<SafeAreaAdr> getSafeAreas() {
		return safeAreas;
	}

	public void setSafeAreas(List<SafeAreaAdr> safeAreas) {
		this.safeAreas = safeAreas;
	}
	
	public List<ShareInfoAdr> getShareInfos() {
		return shareInfos;
	}

	public void setShareInfos(List<ShareInfoAdr> shareInfos) {
		this.shareInfos = shareInfos;
	}	

	public List<MsgInfoAdr> getMsgInfos() {
		return msgInfos;
	}

	public void setMsgInfos(List<MsgInfoAdr> msgInfos) {
		this.msgInfos = msgInfos;
	}

	public List<AppUserInfoAdr> getAppUserInfo() {
		return appUserInfo;
	}

	public void setAppUserInfo(List<AppUserInfoAdr> appUserInfo) {
		this.appUserInfo = appUserInfo;
	}	

	public CheckInfoAdr getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(CheckInfoAdr checkInfo) {
		this.checkInfo = checkInfo;
	}
	
	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}

	public List<VoiceInfoAdr> getY_y() {
		return y_y;
	}

	public void setY_y(List<VoiceInfoAdr> y_y) {
		this.y_y = y_y;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getAuto_mute() {
		return auto_mute;
	}

	public void setAuto_mute(String auto_mute) {
		this.auto_mute = auto_mute;
	}

	public String getPower_off() {
		return power_off;
	}

	public void setPower_off(String power_off) {
		this.power_off = power_off;
	}

	public String getGPS_ON() {
		return GPS_ON;
	}

	public void setGPS_ON(String gPS_ON) {
		GPS_ON = gPS_ON;
	}

	public String getLight_sensor() {
		return light_sensor;
	}

	public void setLight_sensor(String light_sensor) {
		this.light_sensor = light_sensor;
	}

	public List<RelativeCallInfoAdr> getFamily_group() {
		return family_group;
	}

	public void setFamily_group(List<RelativeCallInfoAdr> family_group) {
		this.family_group = family_group;
	}
	
	public String getDisturb() {
		return disturb;
	}

	public void setDisturb(String disturb) {
		this.disturb = disturb;
	}	
	
	public List<DirectiveInfoAdr> getTimes() {
		return times;
	}

	public void setTimes(List<DirectiveInfoAdr> times) {
		this.times = times;
	}

	public List<DirectiveInfoAdr> getSleep() {
		return sleep;
	}

	public void setSleep(List<DirectiveInfoAdr> sleep) {
		this.sleep = sleep;
	}

	public List<DirectiveInfoAdr> getAlarm() {
		return alarm;
	}

	public void setAlarm(List<DirectiveInfoAdr> alarm) {
		this.alarm = alarm;
	} 
	
	public StepParameters getStepParameters() {
		return stepParameters;
	}

	public void setStepParameters(StepParameters stepParameters) {
		this.stepParameters = stepParameters;
	}

	public Beattim getSetBeattim() {
		return setBeattim;
	}

	public void setSetBeattim(Beattim setBeattim) {
		this.setBeattim = setBeattim;
	}

	public FamilyNumber getSetFamilyNumber() {
		return setFamilyNumber;
	}

	public void setSetFamilyNumber(FamilyNumber setFamilyNumber) {
		this.setFamilyNumber = setFamilyNumber;
	}

	public FlightMode getSetFlightMode() {
		return setFlightMode;
	}

	public void setSetFlightMode(FlightMode setFlightMode) {
		this.setFlightMode = setFlightMode;
	}

	public LedState getSetLedState() {
		return setLedState;
	}

	public void setSetLedState(LedState setLedState) {
		this.setLedState = setLedState;
	}

	public UrgentMode getSetUrgentMode() {
		return setUrgentMode;
	}

	public void setSetUrgentMode(UrgentMode setUrgentMode) {
		this.setUrgentMode = setUrgentMode;
	}

	public WifiRange getSetWifiRange() {
		return setWifiRange;
	}

	public void setSetWifiRange(WifiRange setWifiRange) {
		this.setWifiRange = setWifiRange;
	}

	public GpsMapMenu getSetGpsMap() {
		return setGpsMap;
	}

	public void setSetGpsMap(GpsMapMenu setGpsMap) {
		this.setGpsMap = setGpsMap;
	}

	public String getSynDevTime() {
		return synDevTime;
	}

	public void setSynDevTime(String synDevTime) {
		this.synDevTime = synDevTime;
	}

	public EcoMode getSetEcoMode() {
		return setEcoMode;
	}

	public void setSetEcoMode(EcoMode setEcoMode) {
		this.setEcoMode = setEcoMode;
	}

	public CallRingTone getSetRingTone() {
		return setRingTone;
	}

	public void setSetRingTone(CallRingTone setRingTone) {
		this.setRingTone = setRingTone;
	}

	public PlayRing getSetPlayRing() {
		return setPlayRing;
	}

	public void setSetPlayRing(PlayRing setPlayRing) {
		this.setPlayRing = setPlayRing;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}


	public GetLocation getGetLocation() {
		return getLocation;
	}

	public void setGetLocation(GetLocation getLocation) {
		this.getLocation = getLocation;
	}

	public String getGetDevState() {
		return getDevState;
	}

	public void setGetDevState(String getDevState) {
		this.getDevState = getDevState;
	}

	public String getSetDevDebug() {
		return setDevDebug;
	}

	public void setSetDevDebug(String setDevDebug) {
		this.setDevDebug = setDevDebug;
	}


	public SleepState getSetSleepState() {
		return setSleepState;
	}

	public void setSetSleepState(SleepState setSleepState) {
		this.setSleepState = setSleepState;
	}

	public LctEsafe getSetLctEsafe() {
		return setLctEsafe;
	}

	public void setSetLctEsafe(LctEsafe setLctEsafe) {
		this.setLctEsafe = setLctEsafe;
	}

	public GetSsidList getGetSsidList() {
		return getSsidList;
	}

	public void setGetSsidList(GetSsidList getSsidList) {
		this.getSsidList = getSsidList;
	}

	public SsidEsafe getSetSsidEsafe() {
		return setSsidEsafe;
	}

	public void setSetSsidEsafe(SsidEsafe setSsidEsafe) {
		this.setSsidEsafe = setSsidEsafe;
	}

	public GetHealthStep getGetHealthStep() {
		return getHealthStep;
	}

	public void setGetHealthStep(GetHealthStep getHealthStep) {
		this.getHealthStep = getHealthStep;
	}

	public DevUp getDetectDevUp() {
		return detectDevUp;
	}

	public void setDetectDevUp(DevUp detectDevUp) {
		this.detectDevUp = detectDevUp;
	}

	public UpdateFirmware getUpdateFirmware() {
		return updateFirmware;
	}

	public void setUpdateFirmware(UpdateFirmware updateFirmware) {
		this.updateFirmware = updateFirmware;
	}

	public Integer getUpDeviceInfo() {
		return upDeviceInfo;
	}

	public void setUpDeviceInfo(Integer upDeviceInfo) {
		this.upDeviceInfo = upDeviceInfo;
	}

	public Integer getCharging() {
		return charging;
	}

	public void setCharging(Integer charging) {
		this.charging = charging;
	}

	/**
	 * @return the syncDevState
	 */
	public SyncDevState getSyncDevState() {
		return syncDevState;
	}

	/**
	 * @param syncDevState the syncDevState to set
	 */
	public void setSyncDevState(SyncDevState syncDevState) {
		this.syncDevState = syncDevState;
	}


	public Integer getSsidEsafeState() {
		return ssidEsafeState;
	}

	public void setSsidEsafeState(Integer ssidEsafeState) {
		this.ssidEsafeState = ssidEsafeState;
	}

	public Integer getLctGps() {
		return lctGps;
	}

	public void setLctGps(Integer lctGps) {
		this.lctGps = lctGps;
	}

	public Integer getLctLbs() {
		return lctLbs;
	}

	public void setLctLbs(Integer lctLbs) {
		this.lctLbs = lctLbs;
	}

	public Integer getUploadlctWIFI() {
		return uploadlctWIFI;
	}

	public void setUploadlctWIFI(Integer uploadlctWIFI) {
		this.uploadlctWIFI = uploadlctWIFI;
	}

	public Integer getLctGGps() {
		return lctGGps;
	}

	public void setLctGGps(Integer lctGGps) {
		this.lctGGps = lctGGps;
	}

	public Integer getLctGMap() {
		return lctGMap;
	}

	public void setLctGMap(Integer lctGMap) {
		this.lctGMap = lctGMap;
	}

	public Integer getLowBattery() {
		return lowBattery;
	}

	public void setLowBattery(Integer lowBattery) {
		this.lowBattery = lowBattery;
	}

	public Integer getUpStepData() {
		return upStepData;
	}

	public void setUpStepData(Integer upStepData) {
		this.upStepData = upStepData;
	}

	public Integer getOffline() {
		return offline;
	}

	public void setOffline(Integer offline) {
		this.offline = offline;
	}

	public Integer getUpSleepData() {
		return upSleepData;
	}

	public void setUpSleepData(Integer upSleepData) {
		this.upSleepData = upSleepData;
	}

	public Integer getGetLogToken() {
		return getLogToken;
	}

	public void setGetLogToken(Integer getLogToken) {
		this.getLogToken = getLogToken;
	}

	public Integer getUpdateFirmwareRes() {
		return updateFirmwareRes;
	}

	public void setUpdateFirmwareRes(Integer updateFirmwareRes) {
		this.updateFirmwareRes = updateFirmwareRes;
	}

	public Integer getUpStepDatas() {
		return upStepDatas;
	}

	public void setUpStepDatas(Integer upStepDatas) {
		this.upStepDatas = upStepDatas;
	}

	public Integer getUpdateStart() {
		return updateStart;
	}

	public void setUpdateStart(Integer updateStart) {
		this.updateStart = updateStart;
	}

	public String getUpdateVer() {
		return updateVer;
	}

	public void setUpdateVer(String updateVer) {
		this.updateVer = updateVer;
	}

	public Integer getLctLbsWifi() {
		return lctLbsWifi;
	}

	public void setLctLbsWifi(Integer lctLbsWifi) {
		this.lctLbsWifi = lctLbsWifi;
	}

	public DevOffOn getDevOffOn() {
		return devOffOn;
	}

	public void setDevOffOn(DevOffOn devOffOn) {
		this.devOffOn = devOffOn;
	}

	public SynTime getSynTime() {
		return synTime;
	}

	public void setSynTime(SynTime synTime) {
		this.synTime = synTime;
	}

	public Integer getPing() {
		return ping;
	}

	public void setPing(Integer ping) {
		this.ping = ping;
	}

	public Integer getUpSleepDatas() {
		return upSleepDatas;
	}

	public void setUpSleepDatas(Integer upSleepDatas) {
		this.upSleepDatas = upSleepDatas;
	}

	public Integer getLctUpdate() {
		return lctUpdate;
	}

	public void setLctUpdate(Integer lctUpdate) {
		this.lctUpdate = lctUpdate;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLctTime() {
		return lctTime;
	}

	public void setLctTime(String lctTime) {
		this.lctTime = lctTime;
	}

	public SynTime2 getSynTime2() {
		return synTime2;
	}

	public void setSynTime2(SynTime2 synTime2) {
		this.synTime2 = synTime2;
	}

	/**
	 * @return the uLFqMode
	 */
	public Integer getuLFqMode() {
		return uLFqMode;
	}

	/**
	 * @param uLFqMode the uLFqMode to set
	 */
	public void setuLFqMode(Integer uLFqMode) {
		this.uLFqMode = uLFqMode;
	}

	/**
	 * @return the uLSosWifi
	 */
	public Integer getuLSosWifi() {
		return uLSosWifi;
	}

	/**
	 * @param uLSosWifi the uLSosWifi to set
	 */
	public void setuLSosWifi(Integer uLSosWifi) {
		this.uLSosWifi = uLSosWifi;
	}

	/**
	 * @return the uLFq
	 */
	public Integer getuLFq() {
		return uLFq;
	}

	/**
	 * @param uLFq the uLFq to set
	 */
	public void setuLFq(Integer uLFq) {
		this.uLFq = uLFq;
	}

	/**
	 * @return the uLTe
	 */
	public Integer getuLTe() {
		return uLTe;
	}

	/**
	 * @param uLTe the uLTe to set
	 */
	public void setuLTe(Integer uLTe) {
		this.uLTe = uLTe;
	}

	public Integer getTs() {
		return ts;
	}

	public void setTs(Integer ts) {
		this.ts = ts;
	}

	public Integer getLfq() {
		return lfq;
	}

	public void setLfq(Integer lfq) {
		this.lfq = lfq;
	}



}
