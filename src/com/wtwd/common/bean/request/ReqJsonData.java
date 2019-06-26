package com.wtwd.common.bean.request;

import java.util.Date;
import java.util.List;

import com.wtwd.common.bean.deviceup.cmdobject.SleepData;
import com.wtwd.common.bean.deviceup.cmdobject.StepData;
import com.wtwd.common.bean.other.GPSInfoAdr;
import com.wtwd.common.bean.other.Geolocation;
import com.wtwd.common.bean.other.LocationInfoAdr;
import com.wtwd.common.bean.other.NetWorkInfoAdr;
import com.wtwd.common.bean.other.SsidEsafeInfo;
import com.wtwd.common.bean.other.Ssids;
import com.wtwd.common.bean.other.WifiInfoAdr;



public class ReqJsonData {

	private String cmd;
	private String sub;
	private Date test_born_date;		//	yonghu add for test
	public Date getTest_born_date() {
		return test_born_date;
	}
	public void setTest_born_date(Date test_born_date) {
		this.test_born_date = test_born_date;
	}
	private String serie_no;		//设备imei号
	private String user_id;    		//String 类型
	private String user_name;  		//手机号码
	private String user_password;   //密码
	private String user_head;       //用户图像
	private String user_sex;        //用户性别 0 男 1女
	private String user_age;        //用户年龄
	private String user_height;     //身高
	private String user_weight;     //体重
	private String user_imei;       //用户手机的imei
	private String user_imsi;       //用户手机的imsi
	private String device_imei;     //设备imei
	private String device_name;     //设备名称
	private String phone_model;     //手机型号
	private String phone_version;   //手机版本
	private String app_version;     //app版本
	private String user_phone;      //用户手机号
	private String family_number;   //设备亲情号码
	private String family_name;     //亲情号昵称
	private String relationType;    //0表示亲友 1表示好友
	private String relative_id;     //亲情号码id
	private String start_time;      //开始时间ms,当为0时表示最后一次位置
	private String end_time;        //结束时间ms
	private String version_code;    //版本
	private String feedbackContent; //意见反馈
	private String message;         //分享消息
	private String messageLevel;    //分享优先级别
	private String map;
	private String to_userId;       //分享用户
	private String new_password;    //新密码
	private String electricity;	    //低电提醒
	private String gsp_on;
	private String p_l;       
	private String repellent;
	private String heart;
	private String clock;
	private String device_product_model; //产品型号
	private String device_firmware_edition;  //固件版本
	private String device_phone;
	private String b_g;
	private String lat;
	private String lng;
	private String locationType; //
	private String device_safeId; //电子围栏id
	private String device_safeRange; //设备安全区域
	private String device_safeName; //设备安全区域名称
	private String device_safeAddr; //中心地点
	private String device_safeEffectTime; // 设备安全区域有效时间  周一、	周二…分别用1,2…表示(逗号分隔符)
	private String safe_type;
	private String LbsInfoAdragon;
	private Integer batPower; //上传定位数据和时间同步
	private String type; //定位类型 1表示GPS  0表示基站定位  2表示wifi定位
	private String battery; //电量
	private String fall; //0表示戴上 1表示脱落
	private GPSInfoAdr gps; //gps定位
	private NetWorkInfoAdr netWork; //netWork
	private WifiInfoAdr wifi; //wifi
	private List<LocationInfoAdr> lctDatas;
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	private String distrub;  //0为关闭 1为打开
//	private String upDeviceFlag;		//yonghu add
	
	//lchengling added 20160804
	private String flightModeFlag;
	private String urgentFlag;
	
	private String wifiRadius;
	private String hostSsid;
	private String stepNumber;   //2 minutes walk steps
	private String route;        //2 minutes walk mills?
	private String calories;     //burn calories
	private String speed;        //step speed mill/s
	//private String duration;     //step move duration s
	private String starttime;     //step start date time
	private String endtime;     //step end date time

	private String lctTime;   //gps location data upload time
	private String signal;    //device 2G/3G signal level;
	private String plugFlag;   //device charging status plugin / plugout;
	private String ledFlag;    //led Flag turn on or off
	private String ledID;     //which led
	private String ecoFlag;   //eco mode flag
	private String devDebug;   //
	private String ssidEsafeFlag;   //ssid eSafe set flag and alarm 
	private String bssid;
	private String ssid;
	
	private String sleepStateFlag;   //sleep period flag
	private String lctEsafeFlag;
	private SsidEsafeInfo ssidEsafeInfo;
	private String upStepFlag;
	private String upFlag;
	private String upVer;
	private String devVer;

	private Integer userId;
	private Integer duration;
	private String cmdTime;
	private String actionTime;
	
	private String playFlag;
	private Integer errorCode;
	private Integer upResult;
	private Integer upStepCode;
	private Integer stepCount;
	
	private Integer offOnFlag;
	
	private Ssids ssids;
	private List<StepData> stepDatas;
	private List<SleepData> sleepDatas;
	
	//for better upcmd protocal
	private String imei;
	private String model;;
	private String firm;
	private String telno;
	private Integer result;
	private Integer curNet;
	private Integer nettype;
	
	
	
	
	//Google maps geolocation api
	private Geolocation geolocation;
	
	private String eSafeFlag;	//老协议兼容
	private String iccid;
	private Integer dur;
	private String et;		//环境温度,单位摄氏度
	private String bt;		//宠物体温,
	private String br;		//宠物心率,
	private String mr;		//宠物呼吸频率,
	
	
	
	
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getSerie_no() {
		return serie_no;
	}
	public void setSerie_no(String serie_no) {
		this.serie_no = serie_no;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	public String getUser_head() {
		return user_head;
	}
	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_age() {
		return user_age;
	}
	public void setUser_age(String user_age) {
		this.user_age = user_age;
	}
	public String getUser_height() {
		return user_height;
	}
	public void setUser_height(String user_height) {
		this.user_height = user_height;
	}
	public String getUser_weight() {
		return user_weight;
	}
	public void setUser_weight(String user_weight) {
		this.user_weight = user_weight;
	}
	public String getUser_imei() {
		return user_imei;
	}
	public void setUser_imei(String user_imei) {
		this.user_imei = user_imei;
	}
	
	public String getUser_imsi() {
		return user_imsi;
	}
	public void setUser_imsi(String user_imsi) {
		this.user_imsi = user_imsi;
	}	
	public String getDevice_imei() {
		return device_imei;
	}
	public void setDevice_imei(String device_imei) {
		this.device_imei = device_imei;
	}	
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getPhone_model() {
		return phone_model;
	}
	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}
	public String getPhone_version() {
		return phone_version;
	}
	public void setPhone_version(String phone_version) {
		this.phone_version = phone_version;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	public String getFamily_number() {
		return family_number;
	}
	public void setFamily_number(String family_number) {
		this.family_number = family_number;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}		
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public String getRelative_id() {
		return relative_id;
	}
	public void setRelative_id(String relative_id) {
		this.relative_id = relative_id;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}	
	public String getVersion_code() {
		return version_code;
	}
	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}
	
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
		
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public String getMessageLevel() {
		return messageLevel;
	}
	public void setMessageLevel(String messageLevel) {
		this.messageLevel = messageLevel;
	}
	public String getTo_userId() {
		return to_userId;
	}
	public void setTo_userId(String to_userId) {
		this.to_userId = to_userId;
	}
	
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getElectricity() {
		return electricity;
	}
	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}
	public String getGsp_on() {
		return gsp_on;
	}
	public void setGsp_on(String gsp_on) {
		this.gsp_on = gsp_on;
	}
	
	public String getP_l() {
		return p_l;
	}
	public void setP_l(String p_l) {
		this.p_l = p_l;
	}
	
	public String getRepellent() {
		return repellent;
	}
	public void setRepellent(String repellent) {
		this.repellent = repellent;
	}
	
	public String getHeart() {
		return heart;
	}
	public void setHeart(String heart) {
		this.heart = heart;
	}
	
	public String getClock() {
		return clock;
	}
	public void setClock(String clock) {
		this.clock = clock;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getDevice_product_model() {
		return device_product_model;
	}
	public void setDevice_product_model(String device_product_model) {
		this.device_product_model = device_product_model;
	}
	public String getDevice_firmware_edition() {
		return device_firmware_edition;
	}
	public void setDevice_firmware_edition(String device_firmware_edition) {
		this.device_firmware_edition = device_firmware_edition;
	}
	
	public String getB_g() {
		return b_g;
	}
	public void setB_g(String b_g) {
		this.b_g = b_g;
	}
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getDevice_phone() {
		return device_phone;
	}
	public void setDevice_phone(String device_phone) {
		this.device_phone = device_phone;
	}
	
	public String getDevice_safeId() {
		return device_safeId;
	}
	public void setDevice_safeId(String device_safeId) {
		this.device_safeId = device_safeId;
	}
	public String getDevice_safeRange() {
		return device_safeRange;
	}
	public void setDevice_safeRange(String device_safeRange) {
		this.device_safeRange = device_safeRange;
	}
	public String getDevice_safeName() {
		return device_safeName;
	}
	public void setDevice_safeName(String device_safeName) {
		this.device_safeName = device_safeName;
	}
	public String getDevice_safeAddr() {
		return device_safeAddr;
	}
	public void setDevice_safeAddr(String device_safeAddr) {
		this.device_safeAddr = device_safeAddr;
	}
	public String getDevice_safeEffectTime() {
		return device_safeEffectTime;
	}
	public void setDevice_safeEffectTime(String device_safeEffectTime) {
		this.device_safeEffectTime = device_safeEffectTime;
	}
		
	public String getSafe_type() {
		return safe_type;
	}
	public void setSafe_type(String safe_type) {
		this.safe_type = safe_type;
	}
	public String getLbsInfoAdragon() {
		return LbsInfoAdragon;
	}
	
	public Integer getBatPower() {
		return batPower;
	}
	public void setBatPower(Integer batPower) {
		this.batPower = batPower;
	}
	public void setLbsInfoAdragon(String lbsInfoAdragon) {
		LbsInfoAdragon = lbsInfoAdragon;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
		
	public GPSInfoAdr getGps() {
		return gps;
	}
	public void setGps(GPSInfoAdr gps) {
		this.gps = gps;
	}
	
	public NetWorkInfoAdr getNetWork() {
		return netWork;
	}
	public void setNetWork(NetWorkInfoAdr netWork) {
		this.netWork = netWork;
	}
	
	public WifiInfoAdr getWifi() {
		return wifi;
	}
	public void setWifi(WifiInfoAdr wifi) {
		this.wifi = wifi;
	}
	public List<LocationInfoAdr> getLctDatas() {
		return lctDatas;
	}
	public void setLctDatas(List<LocationInfoAdr> lctDatas) {
		this.lctDatas = lctDatas;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getWednesday() {
		return wednesday;
	}
	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	}
	public String getDistrub() {
		return distrub;
	}
	public void setDistrub(String distrub) {
		this.distrub = distrub;
	}
	public String getFlightModeFlag() {
		return flightModeFlag;
	}
	public void setFlightModeFlag(String flightModeFlag) {
		this.flightModeFlag = flightModeFlag;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}
	public String getWifiRadius() {
		return wifiRadius;
	}
	public void setWifiRadius(String wifiRadius) {
		this.wifiRadius = wifiRadius;
	}
	public String getHostSsid() {
		return hostSsid;
	}
	public void setHostSsid(String hostSsid) {
		this.hostSsid = hostSsid;
	}
	public String getStepNumber() {
		return stepNumber;
	}
	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Geolocation getGeolocation() {
		return geolocation;
	}
	public void setGeolocation(Geolocation geolocation) {
		this.geolocation = geolocation;
	}
	public String getLctTime() {
		return lctTime;
	}
	public void setLctTime(String lctTime) {
		this.lctTime = lctTime;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getPlugFlag() {
		return plugFlag;
	}
	public void setPlugFlag(String plugFlag) {
		this.plugFlag = plugFlag;
	}
	public String getLedFlag() {
		return ledFlag;
	}
	public void setLedFlag(String ledFlag) {
		this.ledFlag = ledFlag;
	}
	public String getLedID() {
		return ledID;
	}
	public void setLedID(String ledID) {
		this.ledID = ledID;
	}
	public String getEcoFlag() {
		return ecoFlag;
	}
	public void setEcoFlag(String ecoFlag) {
		this.ecoFlag = ecoFlag;
	}
	public String getDevDebug() {
		return devDebug;
	}
	public void setDevDebug(String devDebug) {
		this.devDebug = devDebug;
	}
	
	public String getSsidEsafeFlag() {
		return ssidEsafeFlag;
	}
	public void setSsidEsafeFlag(String ssidEsafeFlag) {
		this.ssidEsafeFlag = ssidEsafeFlag;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getSleepStateFlag() {
		return sleepStateFlag;
	}
	public void setSleepStateFlag(String sleepStateFlag) {
		this.sleepStateFlag = sleepStateFlag;
	}
	public String getLctEsafeFlag() {
		return lctEsafeFlag;
	}
	public void setLctEsafeFlag(String lctEsafeFlag) {
		this.lctEsafeFlag = lctEsafeFlag;
	}
	public SsidEsafeInfo getSsidEsafeInfo() {
		return ssidEsafeInfo;
	}
	public void setSsidEsafeInfo(SsidEsafeInfo ssidEsafeInfo) {
		this.ssidEsafeInfo = ssidEsafeInfo;
	}
	public String getUpStepFlag() {
		return upStepFlag;
	}
	public void setUpStepFlag(String upStepFlag) {
		this.upStepFlag = upStepFlag;
	}
	public String getUpFlag() {
		return upFlag;
	}
	public void setUpFlag(String upFlag) {
		this.upFlag = upFlag;
	}
	public String getUpVer() {
		return upVer;
	}
	public void setUpVer(String upVer) {
		this.upVer = upVer;
	}
	public String getDevVer() {
		return devVer;
	}
	public void setDevVer(String devVer) {
		this.devVer = devVer;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getCmdTime() {
		return cmdTime;
	}
	public void setCmdTime(String cmdTime) {
		this.cmdTime = cmdTime;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getPlayFlag() {
		return playFlag;
	}
	public void setPlayFlag(String playFlag) {
		this.playFlag = playFlag;
	}
	public Integer getUpResult() {
		return upResult;
	}
	public void setUpResult(Integer upResult) {
		this.upResult = upResult;
	}
	public Integer getUpStepCode() {
		return upStepCode;
	}
	public void setUpStepCode(Integer upStepCode) {
		this.upStepCode = upStepCode;
	}
	public List<StepData> getStepDatas() {
		return stepDatas;
	}
	public void setStepDatas(List<StepData> stepDatas) {
		this.stepDatas = stepDatas;
	}
	public List<SleepData> getSleepDatas() {
		return sleepDatas;
	}
	public void setSleepDatas(List<SleepData> sleepDatas) {
		this.sleepDatas = sleepDatas;
	}
	public Ssids getSsids() {
		return ssids;
	}
	public void setSsids(Ssids ssids) {
		this.ssids = ssids;
	}
	public Integer getStepCount() {
		return stepCount;
	}
	public void setStepCount(Integer stepCount) {
		this.stepCount = stepCount;
	}
	/**
	 * @return the eSafeFlag
	 */
	public String geteSafeFlag() {
		return eSafeFlag;
	}
	/**
	 * @param eSafeFlag the eSafeFlag to set
	 */
	public void seteSafeFlag(String eSafeFlag) {
		this.eSafeFlag = eSafeFlag;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	/**
	 * @return the ssid
	 */
	public String getSsid() {
		return ssid;
	}
	/**
	 * @param ssid the ssid to set
	 */
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public Integer getOffOnFlag() {
		return offOnFlag;
	}
	public void setOffOnFlag(Integer offOnFlag) {
		this.offOnFlag = offOnFlag;
	}
	/**
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}
	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	/**
	 * @return the result
	 */
	public Integer getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Integer result) {
		this.result = result;
	}
	/**
	 * @return the curNet
	 */
	public Integer getCurNet() {
		return curNet;
	}
	/**
	 * @param curNet the curNet to set
	 */
	public void setCurNet(Integer curNet) {
		this.curNet = curNet;
	}
	/**
	 * @return the nettype
	 */
	public Integer getNettype() {
		return nettype;
	}
	/**
	 * @param nettype the nettype to set
	 */
	public void setNettype(Integer nettype) {
		this.nettype = nettype;
	}
	/**
	 * @return the dur
	 */
	public Integer getDur() {
		return dur;
	}
	/**
	 * @param dur the dur to set
	 */
	public void setDur(Integer dur) {
		this.dur = dur;
	}
	/**
	 * @return the et
	 */
	public String getEt() {
		return et;
	}
	/**
	 * @param et the et to set
	 */
	public void setEt(String et) {
		this.et = et;
	}
	/**
	 * @return the bt
	 */
	public String getBt() {
		return bt;
	}
	/**
	 * @param bt the bt to set
	 */
	public void setBt(String bt) {
		this.bt = bt;
	}
	/**
	 * @return the br
	 */
	public String getBr() {
		return br;
	}
	/**
	 * @param br the br to set
	 */
	public void setBr(String br) {
		this.br = br;
	}
	/**
	 * @return the mr
	 */
	public String getMr() {
		return mr;
	}
	/**
	 * @param mr the mr to set
	 */
	public void setMr(String mr) {
		this.mr = mr;
	}
	
}
