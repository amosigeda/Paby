package com.wtwd.pet.app.notify;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidResponse;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSResponse;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.pet.app.entity.WMsgInfo;
import com.wtwd.pet.app.util.PropDo;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

/**
 * 消息启动类
 * @author liufeng
 * @date 2016-09-13
 */
public class App {
	
	private static final Log log = LogFactory.getLog(App.class);
	public static String dirBase = "D:\\Workspaces\\wtwd_notify_liu\\";

	public static String PROTOCOL_VER  = "1.6";
		
	private static ThreadPoolTaskExecutor threadPool;
		
	public final static String iosP12FileName = "pushDevelop.p12";
	public final static String iosP12FileNameOnline = "pushOnline.p12";
	public final static String iosP12FileNameDream = "dreamPushDev.p12";
	public final static String iosP12FileNameOnlineDream = "dreamPush.p12";
	public final static String iosP12FileNameZh = "pabyCnDev.p12";
	public final static String iosP12FileNameOnlineZh = "pabyCnOnline.p12";	
	public final static String iosP12FileRomboDev = "pabyCnDev.p12";
	public final static String iosP12FileRomboPro = "pabyCnOnline.p12";
	
	public final static String certificatePassword_rombo = "123456";
	
	//	public final static String iosToken = "1822cddbfe2ecdcb3f6e46b6d3e0db9a06adca16fefcc9cf792602e11904e4f8";
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
		
	public final static String CST_MSG_TYPE_OTHER = "0";		//其它
	public final static String CST_MSG_TYPE_HEALTH = "1";		//健康
	public final static String CST_MSG_TYPE_SOIAL = "2";		//社区
	public final static String CST_MSG_TYPE_SPORT = "3";		//运动
	public final static String CST_MSG_TYPE_EFENCE = "4";		//电子围栏
	public final static String CST_MSG_TYPE_REL_DEVICE = "5";		//设备相关
	
	public static void setDirBase(String dir_base) {
		dirBase = dir_base;		
	}
	
	public static void start(String dir_base) {
		dirBase = dir_base;
		
		try {
			//if ( !"1.6".equals(PROTOCOL_VER) ) 
				//MqttClient.Init();
				//log.info("push thread start dirBase::"+dirBase);					
				
	        Timer timer1 = new Timer();  
	        timer1.schedule(new TestTimerTaskMqNml(), 1000* 60* 2, 1000* 60 * 5 );
			
			//startAtonce();			
			
			while (true) {
				Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("== App start error:", e);
			log.info("== App start error:", e);
			return;
		}
/*
		synchronized (App.class) {
			while(true){
				try {
					//App.class.wait();
					Thread.sleep(2000);
				} catch (Exception e) {
					log.error("== synchronized error:", e);
				}
			}
		}
*/		
	}
	
	public static void main(String[] args) {
		try {
			try {
				WMsgInfo wi = new WMsgInfo();
				wi.setApp_type("1");
				wi.setBadge(0);
				wi.setDevice_id(596);
				wi.setDevice_token("");
				wi.setHide_flag("0");
				wi.setFence_id(-1);
				wi.setFrom_usrid(0);
				wi.setHide_flag("1");
				wi.setMsg_ind_id(46);
				wi.setIos_real("c5b74e5f739f6f18b2d233dfbdafc47b35aed7eef68fe96be1d8322790d8a26c");
				wi.setIos_token("a88e474babb94478af48e15a7f022a2b");
				wi.setMsg_content("{\"signal\":3,\"battery\":42}");
				wi.setMsg_date("2017-11-30 03:16:03");
				wi.setMsg_date_utc("2017-11-30 03:16:03");
				wi.setSummary(null);
				wi.setMsg_id(1069315);
				wi.setOld_badge(31);
				wi.setShare_id(-1);
				wi.setTo_usrid(1);
				wi.setStatus("1");
				wi.setOrderSort("ID");
				wi.setOrder_id(0);
				wi.setPush_status("0");
				   
				String wis = JSON.toJSONString(wi);
				System.out.println(wis);
				
				String msg = "{\"app_type\":\"1\",\"badge\":32,\"dest_lang\":\"zh-Hans-CN\",\"device_id\":596,\"device_token\":\"\",\"fence_id\":0,\"from_nick\":\"test2\",\"from_usrid\":2,\"hide_flag\":\"0\",\"ios_real\":\"c5b74e5f739f6f18b2d233dfbdafc47b35aed7eef68fe96be1d8322790d8a26c\",\"ios_token\":\"a88e474babb94478af48e15a7f022a2b\",\"msg_content\":\"\\\"test2\\\" has requested to share access with \\\"PPP\\\".To approve,confirm below\",\"msg_date\":\"2017-11-30 07:38:57\",\"msg_date_utc\":\"2017-11-30 07:38:57\",\"msg_id\":1072238,\"msg_ind_id\":5,\"msg_type\":\"5\",\"old_badge\":31,\"orderSort\":\"ID\",\"order_id\":100,\"pet_id\":1774,\"pet_nick\":\"PPP\",\"push_status\":\"0\",\"share_id\":5161,\"status\":\"1\",\"summary\":\"\\\"test2\\\" has requested to share access with \\\"PPP\\\".To approve,confirm below\",\"to_usrid\":1}";
								
				//MqttClient.pushMsg("INSYS2", msg.getBytes());
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
			try {
				   WMsgInfo wi = new WMsgInfo();
					wi.setApp_type("1");
					wi.setBadge(0);
					wi.setDevice_id(80);
					wi.setHide_flag("0");
					wi.setMsg_ind_id(8);
					wi.setIos_real("c5b74e5f739f6f18b2d233dfbdafc47b35aed7eef68fe96be1d8322790d8a26c");
					wi.setIos_token("e405d8969f744814b6e8622bf87e5607");
					wi.setMsg_content("mqtt server stopped!");
					wi.setSummary("mqtt server stopped!");
					wi.setTo_usrid(1);

					PropDo pd = new PropDo();
					
					String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");
					/*ApnsService service = APNS.newService()							
							.withCert(App.dirBase + App.iosP12FileNameDream, iosPwd)
							.withSandboxDestination()
							.build(); 
					service.push("c5b74e5f739f6f18b2d233dfbdafc47b35aed7eef68fe96be1d8322790d8a26c", payload);
					//service = null;

							*
							*/
					
					//MqttClient.pushMsg("INSYS2", "AHA".getBytes());
						
					String payload = APNS.newPayload().alertBody("mqtt server stopped!").build();
				} catch(Exception e) {
					e.printStackTrace();
					Logger.getLogger(App.class).info(e);				
					
				}
					//MyThread myThread1 = new MyThread(wi);
					//myThread1.start();  
			
			//startAtonce();
			while(true){
				try {
					//App.class.wait();
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			log.error("== App start error:", e);
			return;
		}
	}
	
	static int i = 0;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void startThread(){
		//log.info("startThread");		
		(new Runnable() {
			public void run() {
					while(true){
						// 如果当前活动线程等于最大线程，那么不执行
						int act_thrd_count = threadPool.getActiveCount();
						int max_thrd_count = threadPool.getMaxPoolSize(); 
						if( act_thrd_count < max_thrd_count){
							i++;

							try {

								proSendUnPush();

							} catch (Exception e) {
								log.error("系统异常", e);
								e.printStackTrace();
							}
							
							try {
								//proDevStatus();
							} catch (Exception e) {
								log.error("系统异常", e);
								e.printStackTrace();
							}
														
							try {
								Thread.sleep(800*1);//3分钟执行一次
								//getDataByDB();
							} catch (Exception e) {
								log.error("系统异常", e);
								e.printStackTrace();
							}
							
						}
					}
			}
		}).run();
	}
	
	//public static void startAtonce(){}
	
	/**
	 *  从数据库获取数据
	 */
	//public static void getDataByDB(){}
	
	/**
	 *  从数据库获取数据
	 * @throws Exception 
	 */
	//public void proDevStatus() throws Exception{}
	
	//public static void proEfence() {}
	
	public static void proSendUnPush() throws Exception {}
	
	// ios push code
	public void proIosSendReal(String ios_real, String msg, String ios_token, Boolean remind, String content, Integer badge) {
		try {
			if ( isNullOrEmpty(msg)) {
				return;
			}
			if ( "1.6".equals(PROTOCOL_VER) ) {
				proIosSendRealAli(ios_real, msg, ios_token, remind, content, badge );
			} else {//下面是测试
				PropDo pd = new PropDo();
				//String p12_file_path = request.getSession(true).getServletContext().getRealPath("/push/") + "pushDevelop.p12";
				//File directory = new File(iosP12FileName); 
				//String p12_file_path = directory.getAbsolutePath();    //得到的是C:/test/abc 
				String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");
				ApnsService service = APNS.newService()
						.withCert(dirBase + iosP12FileName, iosPwd)
						.withSandboxDestination()
						.build();
				
				//MqttClient.pushMsg("WTDEV_DUMMY", "AHA".getBytes());
					
				String payload = APNS.newPayload().alertBody(msg).build();
				service.push(ios_real, payload);
				service = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.error("proIosSendReal:", e);
		}
	}
	
	public static void proBrdAllDevice(WMsgInfo data) {
		RespJsonData  resp = new RespJsonData(); 

		resp.setMsg_id(data.getMsg_id());
		resp.setMsg_type( data.getMsg_type());
		resp.setMsg_ind_id( data.getMsg_ind_id());
		resp.setMsg_date( data.getMsg_date());
		resp.setMsg_txt( data.getMsg_content());
		resp.setDevice_id( data.getDevice_id());
		resp.setFrom_usrid( data.getFrom_usrid());
		resp.setTo_usrid( data.getTo_usrid());
		resp.setEference_id( data.getFence_id());
		resp.setShare_id( data.getShare_id());
		resp.setPet_id( data.getPet_id());
		resp.setFrom_nick( data.getFrom_nick());
		//resp.setFrom_email( data.getFrom_nick());
		resp.setTo_nick( "");
		resp.setTo_email( "");
		resp.setSummary( data.getSummary() );
		
		String respStr = null;
		respStr = JSON.toJSONString(resp);
		
		proBrdMsgIos(respStr);
		proBrdMsgAdr(respStr);
		proBrdMsgIosDream(respStr);
		proBrdMsgAdrDream(respStr);
		proBrdMsgIosZh(respStr);
		proBrdMsgAdrZh(respStr);
		
		//添加
		boolean isRombo2 = selectBrand(respStr);
		if (isRombo2) {
			proBrdMsgIosRombo(respStr);
		} 
	}
	
	//ios_real ： ios自己苹果的token
	//dev_token 阿里的消息IOS token
	public void proSend(String ios_real, WMsgInfo data, String dev_token, boolean ios_flag) {
		
		try {
			if ( dev_token == null || "".equals(dev_token) )
				return;
			RespJsonData  resp = new RespJsonData(); 
			//JSONObject json = new JSONObject();
			resp.setMsg_id(data.getMsg_id());
			resp.setMsg_type( data.getMsg_type());
			resp.setMsg_ind_id( data.getMsg_ind_id());
			resp.setMsg_date( data.getMsg_date());
			resp.setMsg_date_utc( data.getMsg_date_utc());			
			resp.setMsg_txt( data.getMsg_content());
			resp.setDevice_id( data.getDevice_id());
			resp.setFrom_usrid( data.getFrom_usrid());
			resp.setTo_usrid( data.getTo_usrid());
			resp.setEference_id( data.getFence_id());
			resp.setShare_id( data.getShare_id());
			resp.setPet_id( data.getPet_id());
			resp.setFrom_nick( data.getFrom_nick());
			//resp.setFrom_email( data.getFrom_nick());
			resp.setTo_nick( "");
			resp.setTo_email( "");
			resp.setSummary( data.getSummary() );
			
			String respStr = null;
			respStr = JSON.toJSONString(resp);
					
			if (ios_flag) {
				if ((data.getMsg_ind_id() == 5) || (data.getMsg_ind_id() == 6) || (data.getMsg_ind_id() == 7)) {
					proIosSendReal(ios_real, respStr, dev_token, true, data.getMsg_content(), data.getBadge() );
				} else {
					proIosSendReal(ios_real, respStr, dev_token, false, null, data.getBadge() );
				}	
			}
			else
				proArSendReal("WTDEV" + data.getDevice_id(),  respStr, dev_token);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("prosend:",e);
		}		
	}

    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
              
		if ( "".equals(obj.toString()) )
				  return true;

		if ( " ".equals(obj.toString()) )
				  return true;
		
        if ( "\"null\"".equals(obj.toString()) )
            return true;  
		  
        if ( "null".equals(obj.toString()) )
            return true;  	        	
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }  

	public void proArSendReal(String topic, String msg, String device_token) throws Exception {
		  if( isNullOrEmpty(msg) ) {
			  log.info("device_token" + device_token);
			  return;		
		  }
		  
		if ( "1.6".equals(PROTOCOL_VER) ) {
			proArSendRealAli(msg, device_token);
		} else {
			//MqttClient.pushMsg(topic, msg.getBytes());
		}
	}
	
	private static String accessKeyId = "LTAITqdUfjVdRTn2";
	private static String accessKeySecret = "n1XcuIaaE2nibutsArudP9mquWA9dE";

	private static Long appkey = 23560482l; // paby APP
	private static String secret = "c20bb3955a373b2de5604b377875cb28";

	private static Long appkeyDream = 24712529l; // 23560482l;
	private static String secretDream = "08adf0d3484ada5c7f919fccc347e869";

	private static Long appkeyZh = 24831428l; // 23560482l;
	private static String secretZh = "67b888637c9d1ff4a3c5de71ff560bd8";
	
	private static Long appkeyRombo = 25106596l; // rombo APP
	//private static String appSecretRombo = "605890d29d8bcdb275e0a4f4b8900bb5";

	private static String url = "http://gw.api.taobao.com/router/rest";

	// andriod push code
	public void proArSendRealAli(String msg, String dev_token) {
		try {
			if (isNullOrEmpty(msg) || isNullOrEmpty(dev_token))
				return;
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);
			PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
			androidRequest.setAppKey(appkey);
			androidRequest.setTarget("device");

			androidRequest.setTargetValue(dev_token);

			androidRequest.setMessage(msg);

			PushMessageToAndroidResponse pushNoticeToAndroidResponse = client
					.getAcsResponse(androidRequest);

			androidRequest.setAppKey(appkeyDream);
			androidRequest.setTarget("device");

			androidRequest.setTargetValue(dev_token);

			androidRequest.setMessage(msg);

			client.getAcsResponse(androidRequest);

			androidRequest.setAppKey(appkeyZh);
			androidRequest.setTarget("device");

			androidRequest.setTargetValue(dev_token);

			androidRequest.setMessage(msg);

			client.getAcsResponse(androidRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void proIosSendRealAli(String ios_real, String msg, String dev_token, Boolean remind, String content, Integer badge) {
		try {
			if (isNullOrEmpty(msg) && isNullOrEmpty(content)) {
				// log.info("ios_real" + ios_real);
				return;
			}
			log.info("msg:" + msg + ";dev_token:" + dev_token + ";content:" + content + ";badge:" + badge);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);
			PushMessageToiOSRequest req = new PushMessageToiOSRequest();

			req.setAppKey(appkey);
			req.setTarget("device");
			req.setTargetValue(dev_token);
			req.setMessage(msg);

			try {
				/* PushMessageToiOSResponse resp = */client.getAcsResponse(req);
			} catch (Exception e) {
				e.printStackTrace();
			}

			req.setAppKey(appkeyDream);
			req.setTarget("device");
			req.setTargetValue(dev_token);
			req.setMessage(msg);

			try {
				/* PushMessageToiOSResponse resp = */client.getAcsResponse(req);
			} catch (Exception e) {
				e.printStackTrace();
			}

			req.setAppKey(appkeyZh);
			req.setTarget("device");
			req.setTargetValue(dev_token);
			req.setMessage(msg);

			try {
				/* PushMessageToiOSResponse resp = */client.getAcsResponse(req);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//添加
			boolean isRombo = selectBrand(msg);
			if (isRombo) {
				req.setAppKey(appkeyRombo);
				req.setTarget("device");
				req.setTargetValue(dev_token);
				req.setMessage(msg);
				try {
					client.getAcsResponse(req);
				} catch (Exception e) {
					e.printStackTrace();
					log.info("rombo app push---" + e);
				}
			}

			JSONObject jo = null;
			String m = null;

			if (msg.endsWith("}")) {
				jo = JSONObject.fromObject(msg);
				m = jo.optString("summary");
			}

			if (isNullOrEmpty(m) || m.equals("device status changed!"))
				return;

			int ret = proIosSendRealTest(ios_real, m, badge);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTest(ios_real, m, badge);
			} // else

			if ((ret == 0) && (apnsSrv != null)) {
				synchronized (apnsSrv) {
					apnsSrv.stop();
					apnsSrv = null;
				}
			}

			ret = proIosSendRealTestDream(ios_real, m, badge);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTestDream(ios_real, m, badge);
			} // else

			if ((ret == 0) && (apnsSrvDream != null)) {
				synchronized (apnsSrvDream) {
					apnsSrvDream.stop();
					apnsSrvDream = null;
				}
			}

			ret = proIosSendRealTestZh(ios_real, m, badge);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTestZh(ios_real, m, badge);
			} // else

			if ((ret == 0) && (apnsSrvZh != null)) {
				synchronized (apnsSrvZh) {
					apnsSrvZh.stop();
					apnsSrvZh = null;
				}
			}
			
			//添加
			boolean isRombo1 = selectBrand(msg);
			if (isRombo1) {
				/*ret = proIosSendRealRomboDev(ios_real, m, badge);
				if (ret == 0) {
					Thread.sleep(200);
					ret = proIosSendRealRomboDev(ios_real, m, badge);
				}

				if ((ret == 0) && (apnsSrvRombo != null)) {
					synchronized (apnsSrvRombo) {
						apnsSrvRombo.stop();
						apnsSrvRombo = null;
					}
				}*/
				
				ret = proIosSendRealRomboPro(ios_real, m, badge);
				log.info("proIosSendRealRomboPro ret=" + ret);
				if (ret == 0) {
					Thread.sleep(200);
					ret = proIosSendRealRomboPro(ios_real, m, badge);
				} else {
					return;
				}
				if ((ret == 0) && (apnsSrvOnlineRombo != null)) {
					synchronized (apnsSrvOnlineRombo) {
						apnsSrvOnlineRombo.stop();
						apnsSrvOnlineRombo = null;
					}
				}
			}

			//此处有返回
			ret = proIosSendRealTestOnline(ios_real, m, badge);
			log.info("proIosSendRealTestOnline ret=" + ret);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTestOnline(ios_real, m, badge);
				log.info("if: ret=" + ret);
			} else
				return; // ret=1 返回

			if ((ret == 0) && (apnsSrvOnline != null)) {
				synchronized (apnsSrvOnline) {
					apnsSrvOnline.stop();
					apnsSrvOnline = null;
				}
			}

			//下面两个不执行
			ret = proIosSendRealTestOnlineDream(ios_real, m, badge);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTestOnlineDream(ios_real, m, badge);
			} else
				return;

			if ((ret == 0) && (apnsSrvOnlineDream != null)) {
				synchronized (apnsSrvOnlineDream) {
					apnsSrvOnlineDream.stop();
					apnsSrvOnlineDream = null;
				}
			}

			ret = proIosSendRealTestOnlineZh(ios_real, m, badge);
			if (ret == 0) {
				Thread.sleep(200);
				ret = proIosSendRealTestOnlineZh(ios_real, m, badge);
			} else
				return;

			if ((ret == 0) && (apnsSrvOnlineZh != null)) {
				synchronized (apnsSrvOnlineZh) {
					apnsSrvOnlineZh.stop();
					apnsSrvOnlineZh = null;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealAli", e);
			log.info("msg:" + msg + ", content:" + content);
		}

	}
	
	//判断imei是rombo的话就推送给rombo app
	private static boolean selectBrand(String msg){
		JSONObject json = JSONObject.fromObject(msg);
		String device_id = json.getString("device_id");
		DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("device_id ='" + device_id + "'");
		List<DataMap> list;
		String brandname = null;
		try {
			list = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
			if (list != null && list.size() > 0) {
				brandname = list.get(0).get("brandname") + "";
				if ("rombo".equals(brandname)) {
					return true;
				}
			}else {
				return false;
			}
		} catch (SystemException e) {			
			e.printStackTrace();
		}
		return false;
	}
	  
	private static ApnsService apnsSrv = null;
	private static ApnsService apnsSrvOnline = null;
	private static ApnsService apnsSrvDream = null;
	private static ApnsService apnsSrvOnlineDream = null;
	private static ApnsService apnsSrvZh = null;
	private static ApnsService apnsSrvOnlineZh = null;
	//添加
	private static ApnsService apnsSrvRombo = null;
	private static ApnsService apnsSrvOnlineRombo = null;
	  
	private static void initApnsService() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrv != null)
				return;
			apnsSrv = APNS.newService()
					.withCert(dirBase + iosP12FileName, iosPwd)
					.withSandboxDestination().build();

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrv = null;
		}
	}
	  
	private static void initApnsServiceOnline() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrvOnline != null)
				return;
			apnsSrvOnline = APNS.newService()
					.withCert(dirBase + iosP12FileNameOnline, iosPwd)
					.withProductionDestination().build();// .withSandboxDestination()

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvOnline = null;
		}
	}

	private static void initApnsServiceDream() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrvDream != null)
				return;
			apnsSrvDream = APNS.newService()
					.withCert(dirBase + iosP12FileNameDream, iosPwd)
					.withSandboxDestination().build();

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvDream = null;
		}
	}

	private static void initApnsServiceOnlineDream() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrvOnlineDream != null)
				return;
			apnsSrvOnlineDream = APNS.newService()
					.withCert(dirBase + iosP12FileNameOnlineDream, iosPwd)
					.withProductionDestination().build();// .withSandboxDestination()

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvOnlineDream = null;
		}
	}

	private static void initApnsServiceZh() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrvZh != null)
				return;
			apnsSrvZh = APNS.newService()
					.withCert(dirBase + iosP12FileNameZh, iosPwd)
					.withSandboxDestination().build();

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvZh = null;
		}
	}

	private static void initApnsServiceOnlineZh() {
		try {
			PropDo pd = new PropDo();
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");

			if (apnsSrvOnlineZh != null)
				return;
			apnsSrvOnlineZh = APNS.newService()
					.withCert(dirBase + iosP12FileNameOnlineZh, iosPwd)
					.withProductionDestination().build();// .withSandboxDestination()

		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvOnlineZh = null;
		}
	}
	
	private static void initApnsServiceRomboDev() {
		try {
			if (apnsSrvRombo != null)
				return;
			apnsSrvRombo = APNS.newService()
					.withCert(dirBase + iosP12FileRomboDev, certificatePassword_rombo)
					.withSandboxDestination().build();
		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvRombo = null;
		}
	}
	
	private static void initApnsServiceRomboPro() {
		try {
			if (apnsSrvOnlineRombo != null)
				return;
			apnsSrvOnlineRombo = APNS.newService()
					.withCert(dirBase + iosP12FileRomboPro, certificatePassword_rombo)
					.withProductionDestination().build();
		} catch (Exception e) {
			e.printStackTrace();
			apnsSrvOnlineRombo = null;
		}
	}
	
	//无调用
	public static void proIosSendReal1(String ios_real, String msg,
			String ios_token, Boolean remind, String content) throws Exception {
		if (isNullOrEmpty(msg)) {
			return;
		}

		PropDo pd = new PropDo();
		// String p12_file_path =
		// request.getSession(true).getServletContext().getRealPath("/push/")
		// + "pushDevelop.p12";
		// File directory = new File(iosP12FileName);
		// String p12_file_path = directory.getAbsolutePath();
		// //得到的是C:/test/abc

		// String path =
		// this.getClass().getClassLoader().getResource("/").getPath();

		String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");
		ApnsService service = APNS.newService()
				.withCert(dirBase + iosP12FileName, iosPwd)
				.withSandboxDestination().build();

		// MqttClient.pushMsg("WTDEV_DUMMY", "AHA".getBytes());

		String payload = APNS.newPayload().alertBody(msg).build();
		service.push(ios_real, payload);
		service = null;

	}

	public int proIosSendRealTest(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTest ios_real::" + ios_real + " msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsService();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge)
					.sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrv != null) {
				Map<String, Date> invTokens = apnsSrv.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrv) {
						anf = apnsSrv.push(ios_real, payload);
					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTest", e);
			return 0;
		}

	}

	public int proIosSendRealTestDream(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTestDream ios_real::" + ios_real + " msg:"
					+ msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceDream();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge)
					.sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrvDream != null) {
				Map<String, Date> invTokens = apnsSrvDream.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvDream) {
						anf = apnsSrvDream.push(ios_real, payload);

					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTestDream", e);
			return 0;
		}

	}
	
	public int proIosSendRealTestZh(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTestZh ios_real::" + ios_real + " msg:"
					+ msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceZh();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge)
					.sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrvZh != null) {
				Map<String, Date> invTokens = apnsSrvZh.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvZh) {
						anf = apnsSrvZh.push(ios_real, payload);

					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTestZh", e);
			return 0;
		}

	}
	
	public int proIosSendRealTestOnline(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTest online ios_real::" + ios_real
					+ " msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceOnline();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge).sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrvOnline != null) {
				log.info("apnsSrvOnline is not null");
				Map<String, Date> invTokens = apnsSrvOnline.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvOnline) {
						anf = apnsSrvOnline.push(ios_real, payload);
					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTestOnline", e);
			return 0;
		}

	}
	
	// 无记录
	public int proIosSendRealTestOnlineDream(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTest onlineDream  ios_real::" + ios_real
					+ " msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceOnlineDream();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge)
					.sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrvOnlineDream != null) {
				Map<String, Date> invTokens = apnsSrvOnlineDream
						.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvOnlineDream) {
						anf = apnsSrvOnlineDream.push(ios_real, payload);

					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTestOnlineDream", e);
			return 0;
		}

	}

	// 无记录
	public int proIosSendRealTestOnlineZh(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealTest onlineZh  ios_real::" + ios_real
					+ " msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceOnlineZh();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge)
					.sound("default").build();
			ApnsNotification anf = null;
			if (apnsSrvOnlineZh != null) {
				Map<String, Date> invTokens = apnsSrvOnlineZh
						.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvOnlineZh) {
						anf = apnsSrvOnlineZh.push(ios_real, payload);

					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealTestOnlineZh", e);
			return 0;
		}

	}		
	
	//添加rombo
	public int proIosSendRealRomboDev(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealRomboDev ios_real::" + ios_real + ", msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceRomboDev();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge).sound("default").build();
			if (apnsSrvRombo != null) {
				Map<String, Date> invTokens = apnsSrvRombo.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvRombo) {
						apnsSrvRombo.push(ios_real, payload);
					}
					return 1;
				} else
					return -1;
			} else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealRomboDev", e);
			return 0;
		}

	}

	public int proIosSendRealRomboPro(String ios_real, String msg, Integer badge) {
		try {
			log.info("proIosSendRealRomboPro ios_real::" + ios_real + ", msg:" + msg);

			if (isNullOrEmpty(msg) || isNullOrEmpty(ios_real)) {
				return -1;
			}

			initApnsServiceRomboPro();

			if (badge == null || badge <= 0)
				badge = 1;

			String payload = APNS.newPayload().alertBody(msg).badge(badge).sound("default").build();
			if (apnsSrvOnlineRombo != null) {
				Map<String, Date> invTokens = apnsSrvOnlineRombo.getInactiveDevices();
				if (!invTokens.containsKey(ios_real)) {
					synchronized (apnsSrvOnlineRombo) {
						apnsSrvOnlineRombo.push(ios_real, payload);
					}
					return 1;
				} else
					return -1;
			} else
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("proIosSendRealRomboPro", e);
			return 0;
		}

	}
	
	public static void proBrdMsgIos(String msg) {
		try {
			log.info("Ios all" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToiOSRequest reqNt = new PushMessageToiOSRequest();

			if (isNullOrEmpty(msg))
				return;

			reqNt.setAppKey(appkey);
			reqNt.setTarget("all");
			reqNt.setTargetValue("all");
			reqNt.setMessage(msg);

			PushMessageToiOSResponse respNt = client.getAcsResponse(reqNt);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proBrdMsgIos", e);
		}

	}
	
	public static void proBrdMsgAdr(String msg) {
		try {
			log.info("Adr all" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
			androidRequest.setAppKey(appkey);
			androidRequest.setTarget("all");
			androidRequest.setTargetValue("all");

			androidRequest.setMessage(msg);

			PushMessageToAndroidResponse pushNoticeToAndroidResponse = client
					.getAcsResponse(androidRequest);

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void proBrdMsgAdrDream(String msg) {
		try {
			log.info("AdrDream" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
			androidRequest.setAppKey(appkeyDream);
			androidRequest.setTarget("all");
			androidRequest.setTargetValue("all");

			androidRequest.setMessage(msg);

			PushMessageToAndroidResponse pushNoticeToAndroidResponse = client
					.getAcsResponse(androidRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void proBrdMsgIosDream(String msg) {
		try {
			log.info("IosDream" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToiOSRequest reqNt = new PushMessageToiOSRequest();

			if (isNullOrEmpty(msg))
				return;

			reqNt.setAppKey(appkeyDream);
			reqNt.setTarget("all");
			reqNt.setTargetValue("all");
			reqNt.setMessage(msg);

			PushMessageToiOSResponse respNt = client.getAcsResponse(reqNt);

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
			log.error("proBrdMsgIosDream", e);
		}

	}

	public static void proBrdMsgIosZh(String msg) {
		try {
			log.info("IosZh:" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToiOSRequest reqNt = new PushMessageToiOSRequest();

			if (isNullOrEmpty(msg))
				return;

			reqNt.setAppKey(appkeyZh);
			reqNt.setTarget("all");
			reqNt.setTargetValue("all");
			reqNt.setMessage(msg);

			PushMessageToiOSResponse respNt = client.getAcsResponse(reqNt);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proBrdMsgIosZh", e);
		}

	}
	
	public static void proBrdMsgAdrZh(String msg) {
		try {
			log.info("AdrZh:" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
			androidRequest.setAppKey(appkeyZh);
			androidRequest.setTarget("all");
			androidRequest.setTargetValue("all");

			androidRequest.setMessage(msg);

			PushMessageToAndroidResponse pushNoticeToAndroidResponse = client
					.getAcsResponse(androidRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void proBrdMsgIosRombo(String msg) {
		try {
			log.info("Ios Rombo all" + msg);
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
					accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			PushMessageToiOSRequest reqNt = new PushMessageToiOSRequest();

			if (isNullOrEmpty(msg))
				return;

			reqNt.setAppKey(appkeyRombo);
			reqNt.setTarget("all");
			reqNt.setTargetValue("all");
			reqNt.setMessage(msg);

			client.getAcsResponse(reqNt);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("proBrdMsgIosRombo", e);
		}

	}

	public static class TestTimerTaskMqNml extends TimerTask {

		public TestTimerTaskMqNml() {
		}

		@Override
		public void run() {
			try {
				String host = "http://appserver1.paby.com:8161/";

				WhttpPostAs whp = WhttpPostAs.getInstance();
				whp.httpPostInner(host, null);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

class WhttpPostAs {
	public WhttpPostAs() {
		
	}
	
	private static WhttpPostAs aIns= null;
	
	public static WhttpPostAs getInstance() {
		if (aIns == null) {
			aIns = new WhttpPostAs();
			return aIns;
		} else
			return aIns;
			
	}
	public /*staticsynchronized*/ void httpPostInner( String urlNameString,  String params ) {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";
		try {
			Logger.getLogger(App.class).info("mqSverPetMsger:httpPostInner");
			
			String encoding="UTF-8";
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			//conn.setRequestMethod("POST");
	        //conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
        	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0");
	        		        	
			conn.setConnectTimeout(120*1000);

	        try {
				int code = conn.getResponseCode();
				if(code >= 400){
					Logger.getLogger(App.class).info("mqSverPetMsger: stop");				
					WarnAdminMgr();																			
				
				} else {
				}
	        } catch(Exception e) {
				e.printStackTrace();
				Logger.getLogger(App.class).info(e);				

				Logger.getLogger(App.class).info("mqSverPetMsger: stop");				
				WarnAdminMgr();

	        }
		 
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(App.class).info(e);				
			
		}
		
	}
	
	public void WarnAdminMgr() {
		try {
		   WMsgInfo wi = new WMsgInfo();
			wi.setApp_type("1");
			wi.setBadge(0);
			wi.setDevice_id(80);
			wi.setHide_flag("0");
			wi.setMsg_ind_id(8);
			wi.setIos_real("5272e4539717fbfa4b409b894161a66afb02917f720d8f56c8948b64564be195");
			wi.setIos_token("e405d8969f744814b6e8622bf87e5607");
			wi.setMsg_content("mqtt server stopped!");
			wi.setSummary("mqtt server stopped!");
			wi.setTo_usrid(1);

			PropDo pd = new PropDo();
			
			String iosPwd = pd.getPropFromFile("mq.properties", "iosPushPwd");
			ApnsService service = APNS.newService()
					.withCert(App.dirBase + App.iosP12FileName, iosPwd)
					.withSandboxDestination()
					.build();
			
			//MqttClient.pushMsg("WTDEV_DUMMY", "AHA".getBytes());
			String payload = APNS.newPayload().alertBody("mqtt server stopped!").build();
			service.push(/*"045587774d1e9a8dd7afb75481165c02c35efbc6c8ed084f5199579acfa2ddb3"*/"5272e4539717fbfa4b409b894161a66afb02917f720d8f56c8948b64564be195", payload);
			service = null;
		} catch(Exception e) {
			e.printStackTrace();
			Logger.getLogger(App.class).info(e);				
			
		}
			//MyThread myThread1 = new MyThread(wi);
			//myThread1.start();  
		
	}
	
}
