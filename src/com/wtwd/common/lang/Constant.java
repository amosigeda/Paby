package com.wtwd.common.lang;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.godoing.rose.lang.SystemException;

public class Constant {
	public final static int TONTH=3588;
	public final static int SONTH=2094;
	public final static int OONTH=399;
	public final static String tprice="2.99";
	public final static String sprice="3.49";
	public final static String oprice="3.99";
	public final static String T_ONTH="35.88";
	public final static String S_ONTH="20.94";
	public final static String O_ONTH="3.99";

	public final static String GAODE_SRV = "http://121.196.232.11:8080/wtpet/doWTGaodeInner.do";
	
	public final static boolean IS_APPSERVER_8087_MYSQL_DUMP = true;
	
	public final static boolean IS_GOOGLE_ROADS_API_USED = false;	

	public final static boolean IS_SERV_STAT_CT_ECOMODE = true;	
	public final static boolean IS_SERV_STAT_CT_URGENT = true;	
	public final static boolean IS_SERV_STAT_CT = true;
	
	
	public final static boolean IS_APPSERVER_6688 = false;
	
	
	public final static boolean IS_MYCAT_DBMGR = false;
	
	public final static boolean SYS_STAT_DYN_DEV_ULFQ = false;
	
	
	public final static String SYS_INNER_API_PWD1 = "gds@i_c-DssWcvvM";
	
	public final static String SYS_SERVER_DN1 = "59.167.239.62";
	public final static String SYS_SERVER_DN2 = "59.167.239.62";
	public final static String SYS_SERVER_DN3 = "59.167.239.62";

	public final static int SYS_SERVER_PORT1 = 8087;
	public final static int SYS_SERVER_PORT2 = 8087;
	public final static int SYS_SERVER_PORT3 = 8087;


	public final static String SYS_SERVER_INETIP1 = "172.31.1.222";
	public final static String SYS_SERVER_INETIP2 = "172.31.15.28";
		
	
	public final static Integer DEV_LCT_WIFI_TYPE = 0;
	public final static Integer DEV_LCT_GPS_TYPE = 1;
	public final static Integer DEV_LCT_WIFI_GPS_TYPE = 2;
	
	public final static Integer DEV_SOS_LCT_WIFI_TYPE_STATUS_ON = 1;
	public final static Integer DEV_SOS_LCT_WIFI_TYPE_STATUS_OFF = 0;
	
	// public final static String 	STRIPE_APIKEY = "sk_test_Xdypv2QpRlx83iYVPax79XyD";
	public final static String 	STRIPE_APIKEY = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
	
	// REMBO ACCOUNT
	// public final static String 	STRIPE_REMBO_TEST_APIKEY = "sk_test_RqTU7MSCrC3V3K4fX2s43t29";
	public final static String 	STRIPE_REMBO_APIKEY = "sk_live_BAweAUyVFbqQ256wczQghUqd";
	
	// rombo paypal支付
	//public static final String clientID = "ATkDFGs9tvV5a1L5F7dH3DYnMA9M_G1eJcg9JwbkQIUe4FLWOo2DWZcFmfs5zgrSqmBf6rUaY4dJXfcJ";
	//public static final String clientSecret = "EN1kFegabtfU3m6uCd1yC_7bSsAgCCgsUVSmz6nNL-F9VljZ9n6ErC-YRqbrxTbuEJ0ZBpT2m8Wy0tXl";
	//public static final String mode = "sandbox";
	public static final String clientID = "AYTjWzxYcBuZ5yQ8A854HguORomxAZOHbr4Vi5PL4zfs9IHRUXZLrQTOexIqHbTkd912fVh3z9UXXNzM";
	public static final String clientSecret = "EMkTjy3e-X5Spd2NbqLBUfiJ_L3zwJWI8FobcMPbAGgCulk8FwzefiimiiFsyHtp0Bybn56iTQzHJTCZ";
	public static final String mode = "live";
	// rombo Braintree支付
	//public static final String BT_ENVIRONMENT = "sandbox";
	//public static final String BT_MERCHANT_ID = "rj4mctn3yy7t67fk";
	//public static final String BT_PUBLIC_KEY = "y8yft8xv8jf7vhq4";
	//public static final String BTE_PRIVATE_KEY = "e3967fe789bcd648c8a0ebe7441bc478";
	public static final String BT_ENVIRONMENT = "production";
	public static final String BT_MERCHANT_ID = "tm44yfy75hvzmhky";
	public static final String BT_PUBLIC_KEY = "9rfxt5wnkfjhmz4m";
	public static final String BTE_PRIVATE_KEY = "26cbd803a158d76e39faee264584efd8";
	
	// 以服务器为中心控制设备发送GPS定位等等
	public final static boolean serverCtlDevLct = false;

	
	public final static int  LCT_IS_VALID = 0;
	public final static int  LCT_IS_REPEATED = 1;
	public final static int  LCT_IS_INVALID = -1;
	
	public final static String CST_EMAIL_SERVER_EA = "service@paby.com";		//paby 邮箱
	public final static String ROMBO_SERVER_EMAIL = "support@therombogroup.com";  //rembo app 意见反馈邮箱
	public final static String DREAMCARE_SERVER_EA = "Atic.au@QQ.com";		//Dream Care 邮箱
	
	public final static String GEONAMEORG_USERNAME = "wwwqingtian";
	public final static String GEONAMEORG_URL = "http://api.geonames.org/timezoneJSON";
	public final static String GEONAMEORG_TZFLD = "timezoneId";
	public final static String GEONAMEORG_CC = "countryCode";

	
	public final static String EMPTY_STRING = "";
	
	public final static String PROTOCOL_VER = "1.6";

	public final static String GHEALTH_PROTOCOL_VER = "1.7";
	
	public final static boolean TOFOFF_AUTO_DEBUG = false;
	
	public final static boolean timeUtcFlag = true;	
	public final static boolean UNI_HEALTH_COMPUTE_SAME_FLAG = true;	

	public final static boolean cmdDirectResFlag = true;	
	
	public final static Boolean STAT_SERV_HEART =true;	

	public final static Boolean STAT_SYS_DEBUG = true;	
	public final static Boolean STAT_SYS_DEBUG_FULL = false;	
	public final static Boolean STAT_APP_RESTRICT_IMEI = true;	
	public final static Boolean STAT_DEV_RESTRICT_IMEI = true;	
	public final static Boolean STAT_SERV_HEARBEAT = false;	

	public final static String STAT_ESAFE_STYLE = "1";	


	/** The default maxstep number. Default to 65535. */
    public final static int MAXSTEP = 65535; 
	

    
    
	public final static String RESULTCODE = "resultCode";

	public final static String EXCEPTION = "exception";

	public final static int EXCEPTION_CODE = -1; // 异常

	public final static int MAX_SAFEAREA_NUMS = 10;  
	
	
	public final static int INVALID_DATA = -2;  //invalid data location info

	public final static int SUCCESS_CODE = 1; // 成功

	public final static int FAIL_CODE = 0; //用户已经注册、失败
	
	public final static int BOND_FAIL_NO = -2; //imei号不合法
	
	public final static int BOND_FAIL_ALREADY = -3; //该宝贝已经被绑定

	//wappusers yonghu
	public final static int ERR_APPUSER_HAS_EXIST = -4;	//APP注册用户时候，用户的邮箱已经注册过
	public final static int ERR_APPUSER_NOT_EXIST = -5;	//APP用户登录时候，用户的邮箱账号不存在

	//20160625 label
	public final static int ERR_APPADD_DEVICE_HOST_DENY = -6;	//APP用户申请绑定设备时，设备主人拒绝
	public final static int ERR_APPADD_DEVICE_HOST_TIMEOUT = -7;	//APP用户申请绑定设备时，等待主人同意超时
	public final static int ERR_APPADD_DEVICE_HAS_BINDED = -8;	//APP用户申请绑定设备时，设备被重复绑定
	public final static int ERR_INVALID_DEVICE = -9;	//无效IMEI号，在系统内没有登记过
	public final static int ERR_INVALID_PARA = -10;	//无效参数
	public final static int ERR_SYSTEM_BUSY = -11;	//系统忙	
	public final static int ERR_INVALID_USER = -12;	//无效用户
	public final static int ERR_NOT_SUPPORTED = -13;	//系统不支持
	public final static int ERR_USER_RIGHT = -14;	//用户权限不够
	public final static int ERR_INVALID_TOKEN = -15;	//无效或者失效TOKEN, 可能需要通知其它用户已经在别处登录，被迫下线
	public final static int ERR_RES_NOT_EXIST = -16;	//请求资源不存在，比如下载用户头像或者宠物头像不存在
	public final static int ERR_DEV_PET_HAS_EXIST = -17;	//设备已经对应填写了宠物资料，
	             //无法为该设备继续添加宠物资料，可以选择更新宠物资料
	public final static int ERR_HOST_DEV_OF_USER = -18;	//设备非用户的主设备
	public final static int ERR_DEV_OF_USER = -19;	//设备非用户的设备，无论是主设备还是分享设备
	public final static int ERR_INVALID_CALL = -20;	//无效调用协议，因为缺少必填的参数或者其它原因。
	public final static int ERR_PET_NICKNAME_EXIST = -21;	//用户添加宠物资料的时候，发现已经该用户添加的昵称重复，
	public final static int ERR_APPADD_DEVICE_OTHER_BINDED = -22;	//APP用户申请绑定设备时，设备已经被别的用户使用
	public final static int ERR_DEVICE_REFUSE_UPGRADE = -23;	//APP用户申请绑定设备时，设备已经被别的用户使用
	public final static int ERR_DEVICE_NO_NEWEST_SPORT_DATA = -24;	//设备没有更新的运动数据需要上传
	public final static int ERR_DEVICE_CMD_INTERRUPTTED = -25;	//设备命令被中断
	public final static int ERR_SAFEAREA_BEYOND_MAX_VAL = -26;	//设备命令被中断
	public final static int ERR_DEV_IS_NOT_ONLINE = -27;	//设备不在线
	public final static int ERR_EMPTY_DEVICE = -28;	//设备没有绑定任何宠物资料
	
	
	public final static String CST_MSG_TYPE_OTHER = "0";		//其它
	public final static String CST_MSG_TYPE_HEALTH = "1";		//健康
	public final static String CST_MSG_TYPE_SOIAL = "2";		//社区
	public final static String CST_MSG_TYPE_SPORT = "3";		//运动
	public final static String CST_MSG_TYPE_EFENCE = "4";		//电子围栏
	public final static String CST_MSG_TYPE_REL_DEVICE = "5";		//设备相关消息类型
	
	public final static int CST_MSG_IND_APPLY_SHARE = 5;		//设备分享消息
	public final static int CST_MSG_IND_AGREE_SHARE = 6;		//同意分享设备消息
	public final static int CST_MSG_IND_DENY_SHARE = 7;		//拒绝分享设备消息
	public final static int CST_MSG_IND_HOST_DEL_SHARE = 13;		//主人取消设备分享

	public final static int CST_MSG_IND_DEV_OUT_EFENCE = 1;		//离开电子围栏
	public final static int CST_MSG_IND_DEV_IN_EFENCE = 2;		//进入电子围栏
	public final static int CST_MSG_IND_DEV_LOW_BATTERY = 4;		//设备低电报警
	public final static int CST_MSG_IND_DEV_LOGOUT = 8;		//设备退出登录
	
	public final static int CST_MSG_IND_DEV_LOGIN = 9;		//设备上线，即联网	
	public final static int CST_MSG_IND_APP_LOGOUT = 10;		//app用户退出登录
	public final static int CST_MSG_IND_APP_LOGIN = 11;		//app用户登录
	public final static int CST_MSG_IND_APP_BACK = 14;		//app转入后台
	public final static int CST_MSG_IND_APP_QUIT = 15;		//app退出

	public final static int CST_MSG_IND_SPORT_HALF_PERCENT = 16;	//宠物完成运动建议数据的50%以上	
	public final static int CST_MSG_IND_SPORT_FULL_PERCENT = 17;	//宠物完成运动建议数据的100%以上
	
	public final static int CST_MSG_IND_COOL_LED_ON = 18;	//18 设备炫酷灯打开
	public final static int CST_MSG_IND_COOL_LED_OFF = 19;	//19：设备炫酷灯关闭
	
	public final static int CST_MSG_IND_ECO_MODE_ON = 20;	  //20： 设备省电模式打开
	public final static int CST_MSG_IND_ECO_MODE_OFF = 21;	//21：设备省电模式关闭
	
	public final static int CST_MSG_IND_DISCOVERY_ON = 22;	  //22：设备紧急模式打开， 启动时间参照msg_date字段
	public final static int CST_MSG_IND_DISCOVERY_OFF = 23;	  //23： 设备紧急模式关闭
	
	public final static int CST_MSG_IND_CHARGER_ON = 24;	  //充电器连接
	public final static int CST_MSG_IND_CHARGER_OFF = 25;	  //25：  充电器断开

	//28： 设备固件升级开始，	
	public final static int CST_MSG_IND_DEV_UFIRM_START = 28;	  

	
	public final static int CST_MSG_IND_DISCOVERY_LEDON = 31;	  //：设备紧急模式灯打开，
	public final static int CST_MSG_IND_DISCOVERY_LEDOFF = 32;	  //： 设备紧急模式灯关闭

	public final static int CST_MSG_IND_DISCOVERY_SNDON = 33;	  //：设备紧急模式声音打开
	public final static int CST_MSG_IND_DISCOVERY_SNDOFF = 34;	  //： 设备紧急模式声音关闭

	public final static int CST_MSG_IND_UPDATE_FIRM_FINISH = 35;	  //： 升级完成
	public final static int CST_MSG_IND_SEL_ESAFE_WIFI = 36;	  //： 设备选择WIFI热点控制命令完成
	public final static int CST_MSG_IND_DEV_REP_LCT = 37;	  //： 设备主动上报位置消息
	public final static int CST_MSG_IND_DEV_URGENT_STAT_BRDCAST = 38;	  //： 紧急模式开关状态广播
	public final static int CST_MSG_IND_ESAFE_WIFI_STATUS_REP = 39;	  //： WIFI电子围栏报警状态上报
	public final static int CST_MSG_IND_GET_HEALTH_RES = 40;	  //： 计步数据完成情况消息

	public final static int CST_MSG_IND_WIFI_ESAFE_BRDCST = 41;	  //： WIFI电子围栏推送消息报警
	
	public final static int CST_MSG_IND_SET_SLEEP_RES= 42;	  //： 设置休眠时段命令设备返回结果
	public final static int CST_MSG_IND_ESAFE_FLAG_RES= 43;	  //： 物理电子围栏开关状态推送消息	
	public final static int CST_MSG_IND_ESAFE_ADD_NTFY= 44;	  //： 物理电子围栏增加消息推送
	public final static int CST_MSG_IND_ESAFE_DEL_NTFY= 45;	  //： 物理电子围栏删除消息推送
	public final static int CST_MSG_IND_DEVBEAT_NTFY= 46;	      //： 设备心跳通知，包括电量及信号信息

	public final static int CST_MSG_IND_DEV_ODIFY_TIMEZONE= 47;	      //： 设备更改时区
	
	public final static int CST_MSG_IND_SERVER_STOP_NTFY= 48;	      //： 服务器停止服务
	public final static int CST_MSG_IND_TONOFF_NTFY= 49;	      //： 设备设置定时开关机通知
	public final static int CST_MSG_IND_UT_NTFY= 50;	      //： 设备定时上报检测到的温度，包括宠物体温和外温通知

	//public final static int CST_MSG_IND_UPDATE_FIRM_FINISH_OK = 50;	  //： 升级成功
	//public final static int CST_MSG_IND_UPDATE_FIRM_FINISH_FAIL = 51;	  //： 升级失败

	
	
	
	
	
	//public final static String WEB_USERIMG_BASEDIR = "http://121.196.232.11:8080/wtpet/images/user/head/"; // 服务器上用户头像目录位置
	//public final static String WEB_PETIMG_BASEDIR = "http://121.196.232.11:8080/wtpet/images/pet/head/"; // 服务器上用户头像目录位置
	//public final static String WEB_FIRM_BASEDIR = "http://121.196.232.11:8080/wtpet/firmware/"; // 服务器上用户头像目录位置
	public final static String WEB_USERIMG_BASEDIR = ":8087/wtpet/images/app/head/"; // 服务器上用户头像目录位置
	public final static String WEB_PETIMG_BASEDIR = ":8087/wtpet/images/pet/head/"; // 服务器上用户头像目录位置
	public final static String WEB_FIRM_BASEDIR = ":8087/wtpet/firmware/"; // 服务器上用户头像目录位置
	public final static String WEB_PETKIND_BASEDIR = ":8087/wtpet/petkind/"; // 服务器上用户头像目录位置
	public final static String EF_PET_BASEDIR = ":8087/wtpet/images/petef/"; // 服务器上电子围栏图片位置

	
	public final static String MQTT_INTSYS_TOPIC1 = "INSYS2";
	
	public final static String MQTT_SRV_URL = ":1883"; // mqtt 服务器url
	public final static String MQTT_SRV_USERNAME = "wtpet@amx@3278"; // 
	public final static String MQTT_SRV_PASSWORD = "YiTxe2xvAQm8"; // 
	
	public final static String FACEBOOK_USER_FIXED_PASSWORD = "ej0Md28_aasdf_acDevrCREvsNI4"; // 

	
	public final static String LOCATION_URL = "http://apilocate.amap.com/position"; // 锟斤拷位锟斤拷锟杰碉拷址

	public final static String SERVER_IP = "";  //"10.2.166.4";  

	public static final String KEY = "1c1c642bd81c287fd16239ddc0eb85c6";      //"bcfbf9ebf25a4d0bf86fe9f416b62264";
	
	public static final String KEY_1 = "622b99b7cf7146e1243a0ad4fb3afbbe";      //"801df1e9132e2151cd9ad435ecc59858";  
	
	//Googel Map Geolocation API Section:
	//public static final String GOOGLE_MAP_GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyC8JUrcz5irYaeQabjQAvlVO4U_sJPt5yc";
	public static final String GOOGLE_MAP_GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyDZFXwz3WM0YxlBiJOdH32tqZtcYk6sVL8";		//AIzaSyDPVC038AMaU_4HiDWA7H7uTUGrk8jXyN8";
	//Google Maps Geolocation API
	
	// shua new key public static final String GOOGLE_MAP_GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-UsTdtrtMZtwkBqzykU1vFQ0hKDn2cJI";
	//public static final String WEB_SERVER_KEY = "AIzaSyC8JUrcz5irYaeQabjQAvlVO4U_sJPt5yc";
	//AIzaSyDPVC038AMaU_4HiDWA7H7uTUGrk8jXyN8      app@paby.com
	//AIzaSyDPVC038AMaU_4HiDWA7H7uTUGrk8jXyN8
	
	// 鐢ㄦ埛澶村儚鍦板潃
	public final static String USER_SAVE = "/upload/user/";

	// 璁惧澶村儚鍦板潃
	public final static String DEVICE_SAVE = "/upload/device/";

	// 鍦扮悆鍗婂緞
	public final static double EARTH_RADIUS = 6378.137;

	//鏈夋晥鏁版嵁
	public final static double EFFERT_DATA = 5.0;
	
	
	public final static double GPS_DIFF_DIST_GAP = 6;   //3.0;
	public final static double LBS_DIFF_DIST_GAP = 350.0;   //200.0;
	public final static double WIFI_DIFF_DIST_GAP = 50;	//20;  //15.0;  //6.0;
	public final static double GEO_DIFF_DSIT_GAP = 20;   //15.0;   //6.0;
	public final static double IS_VALID_WIFI = 500;    //550.0;
	public final static int LBS_DIFF_STEPS = 25;   //15;  //35;  //20;
	public final static int WIFI_DIFF_STEPS = 10;  //3;  //6;   //move 4:1=10:2.5   4 steps = 1 m;
	public final static int GEO_DIFF_STEPS = 10;   //3;   //6;
	public final static int WIFI_LCT_FAIL_STEPS = 20;   //35;   20 steps move 500m is invalid data fail;
	
	public final static double LBS_INVALID_DIST_GAP = 2000.0;
	
	public final static String SPLITE = "_";
	
	//服务器存储地址
	public final static String UPLOAD = "/upload/";  
	
	//服务器项目根地址(linux)
    public final static String ROOT_NAME = "/usr/local/resin-3.1.8/webapps"; 
    
    //window
    //public final static String ROOT_NAME = "D:\\resin-3.1.10\\webapps\\ADragon";
	//服务器地址
	public final static String SERVER = "112.74.130.196";
	
	public final static String PORT = "8087";
	
	public final static String ROOT = "/ADragon";
	
	
	//20160625 label
	public final static String VERIFY_CODE = "verify_code";
	public final static String DEVICE_ID = "device_id";
	public final static String DEVICE_TYPE = "device_type";
	public final static String ADMIN_USERID = "admin_userid";
	public final static String SHARE_ID = "share_id";
	public final static String APP_TOKEN = "app_token";
	
	public final static int DEF_STEP_SENSITIVITY = 3;
	public final static int DEF_UPMOVE_TIME_UNIT = 2;		//计步数据上传时间单位，默认2分钟上报一次计步数据
	
	
	
	
	/**
	 * 锟斤拷锟絠d,锟街革拷
	 * 
	 * @param userId
	 */
	public static String getUserIdToApp(String userId) {
		String tmp = userId.substring(userId.lastIndexOf("_") + 1);

		return tmp;
	}

	/**
	 * 转锟斤拷
	 */
	public static String transCodingToUtf(String transString) {
		String tmp = "";
		try {
			tmp = URLDecoder.decode(transString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * 锟斤拷取锟斤拷前锟斤拷锟节碉拷前锟斤拷锟斤拷(前锟斤拷锟角革拷锟斤拷,-7锟斤拷示前锟斤拷锟斤拷)
	 * 
	 * @param daysAgo
	 * @return
	 */
	public static String getDaysAgoCondition(String filed, int daysAgo) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();

		c.add(Calendar.DATE, daysAgo);
		Date day = c.getTime();
		return filed + " between '" + dateFormat.format(day)
				+ " 00:00:00' and '" + dateFormat.format(now) + " 23:59:59'";
	}

	/**
	 * 锟侥硷拷锟斤拷锟斤拷
	 * 
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public static void createFileContent(String path, String fileName,
			byte[] content) throws IOException {
		createFile(path);
		FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(content);
		bos.flush();
		bos.close();		
	}

	/**
	 * 锟侥硷拷路锟斤拷锟斤拷锟斤拷锟斤拷,锟津创斤拷
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteFile(String deletePath) {
		File file = new File(deletePath);
		if (file.exists() && file.isDirectory()) {
			String[] arr = file.list();
			for (String tmp : arr) {
				new File(deletePath + "/" + tmp).delete();
			}
		}
	}

	public static String getUniqueCode(String unique) {
		Calendar c = Calendar.getInstance();
		long now = c.getTime().getTime(); // 锟斤拷时锟斤拷锟絤s锟斤拷确锟斤拷唯一锟斤拷
		return unique + "_" + String.valueOf(now);
	}

	public static String getDownloadPath(String url, String port, String path) {
		String tmp = url + port + path;
		return tmp;
	}

	/**
	 * 缁忕含搴﹀�璁＄畻璺濈
	 */
	public  synchronized static boolean getDistance(double lat1, double lng1, double lat2,
			double lng2,double range) {

		double radlat1 = getRadian(lat1);
		double radlat2 = getRadian(lat2);

		double lat_distance = radlat1 - radlat2; // 绾害宸�
		double lng_distance = getRadian(lng1) - getRadian(lng2); // 缁忓害宸�

		double distance = Math.asin(Math.sqrt(Math.pow(
				Math.sin(lat_distance / 2), 2)
				+ Math.cos(radlat1)
				* Math.cos(radlat2)
				* Math.pow(Math.sin(lng_distance / 2), 2)));

		distance = 2 * distance * EARTH_RADIUS * 1000;
		
//		distance = Math.round(distance);
//		System.out.println("距离为"+distance);
		if(distance >= range){
			return true;
		}
		return false;
	}
	
	public synchronized  static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {

		double radlat1 = getRadian(lat1);
		double radlat2 = getRadian(lat2);

		double lat_distance = radlat1 - radlat2; // 绾害宸�
		double lng_distance = getRadian(lng1) - getRadian(lng2); // 缁忓害宸�

		double distance = Math.asin(Math.sqrt(Math.pow(
				Math.sin(lat_distance / 2), 2)
				+ Math.cos(radlat1)
				* Math.cos(radlat2)
				* Math.pow(Math.sin(lng_distance / 2), 2)));

		distance = 2 * distance * EARTH_RADIUS * 1000;
		
		return distance;
	}

	private synchronized static double getRadian(double degree) {
		return degree * Math.PI / 180;

	}
//	public static void main(String[] args) {
//		System.out.println("褰撳墠鐨勮窛绂�+getDistance(22.554262,113.941146,22.554277,113.941752));
//	}
	
	public static void main(String[] args) throws SystemException {
		//String abc = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"100\",\"stepNumber\":\"228\",\"lctTime\":\"2017-03-16 10:33:50\",\"wifi\":{\"smac\":\"20:72:0d:39:01:a4\",\"mmac\":\"\",\"macs\":\"b8:f8:83:fa:e1:a2,-33,CCS|c8:3a:35:2a:ec:e0,-51,office 4|d4:ee:07:15:39:fe,-57,SJYW|9c:21:6a:73:3e:70,-60,6ben|12:52:cb:61:3f:b3,-65,jigang|ec:88:8f:55:25:84,-66,LEIFAYU|3c:46:d8:13:71:5a,-66,TP-LINK_7S|d8:15:0d:c8:4a:52,-68,TP-LINK1111111|cc:81:da:01:b6:b8,-69,ajshin|64:09:80:66:a1:ab,-71,Xiaomi_2012-|00:87:46:17:d3:54,-71,xiaow|fc:d7:33:2b:4f:5c,-72,WTWD18\",\"serverip\":\"\"},\"network\":{\"network\":\"10\",\"cdma\":0,\"imei\":\"352138064952338\",\"smac\":\"20:72:0d:39:01:a4\",\"bts\":\"460,01,42287,23093706,-102\",\"nearbts\":\"460,01,-1,-1,-69\",\"serverip\":\"10.18.119.173\"}}";
		 //WTDevHandler sss = new WTDevHandler();
		 //sss.processCmd(abc, null);
		/*
		WTAppMsgManAction amma = new WTAppMsgManAction();
		JSONObject jo = new JSONObject();
		jo.put("user_id", 1);
		amma.proPGet(jo);
		*/
		
		
		/*
		WTAppDeviceManAction wda = new WTAppDeviceManAction(); 
		wda.proReqHostAgreeShare("", 1, 1845);
		*/
		
		double dLo = -118.17171;
		double dLa = 33.94826;

		double cLo = -118.192688333333;
		double cLa = 33.769206666667;
		
		
		//计算本次移动后与所有电子围栏中心点的距离
		double dist = Constant.getDistance(cLa, cLo, dLa, dLo);
		
		System.out.println(dist);
		//LocationInfoHelper lih = new LocationInfoHelper();
		//lih.getGeoFromLatLng( "33.770653","-118.19705");
				 
		
		
	}
	
	
}
