package com.wtwd.sys.client.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.godoing.rose.lang.DataMap;
import com.google.gson.Gson;
import com.wtwd.common.bean.other.DirectiveInfoAdr;
import com.wtwd.common.bean.other.GPSInfoAdr;
import com.wtwd.common.bean.other.NetWorkInfoAdr;
import com.wtwd.common.bean.other.RelativeCallInfoAdr;
import com.wtwd.common.bean.other.VoiceInfoAdr;
import com.wtwd.common.bean.other.WifiInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.bean.subcri.SubcriJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.HttpRequest;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.wtwd.sys.client.handler.impl.ClientMessageEventImpl;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.directiveinfo.domain.DirectiveInfo;
import com.wtwd.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;

public class AdragonHandler extends ClientMessageEventImpl{

private Logger logger = Logger.getLogger(AdragonHandler.class);	
	
	private static  ServiceBean serviceBean = ServiceBean.getInstance();
	
	private ClientSessionManager mClientSessionManager;  //客户端session的保存



	public void handler(Object message, IoSession session) {

		
		if (message.toString().equals("") )
			return;	//yonghu add

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");
		mClientSessionManager = (ClientSessionManager) context.getBean("clientSessionManager");
		
		String s1 = "Programming";
		String s2 = new String("Programming");
		String s3 = "Program" +"ming";
		//响应的数据
		String resp="";
		
		
		System.out.println(s1 == s2);
		System.out.println(s1 == s3);
		System.out.println(s1 == s1.intern());
		
		final RespJsonData respJsonData =new RespJsonData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sub ="";
		SubcriJsonData subData = new SubcriJsonData();
		System.out.println("message---------"+subData);
		if(message!=null && session!=null){
			System.out.println("message---------"+message.toString());
		logger.info("message---------------"+message.toString());
			try {
				System.out.println("ReqjsonData前--------------");
				  Gson gson=new Gson();
				  logger.info("gson后");
				  logger.info("反序列化前");
			      ReqJsonData reqJsonData = gson.fromJson(message.toString(),ReqJsonData.class);
			      logger.info("reqJsonData------------------"+reqJsonData);
				//ReqJsonData reqJsonData = JSON.parseObject(message.toString(), ReqJsonData.class);
				String cmd = reqJsonData.getCmd();
logger.info("cmd---------------"+cmd);
				System.out.println("cmd---------"+cmd);
				if(cmd==null ||"".equals(cmd)){
					System.out.println("cmd-----字符串传递错误"+cmd);					
					respJsonData.setResultCode(Constant.EXCEPTION_CODE);
					throw new Exception("字符串传递错误");
				}
				StringBuffer sb = new StringBuffer();
				if(cmd.equals(AdragonConfig.reqConnect)){      //上传上链接请求
					String deviceId = reqJsonData.getSerie_no();
					DeviceActiveInfo deviceActive = new DeviceActiveInfo();
					deviceActive.setCondition("device_imei ='"+deviceId+"'");
					deviceActive.setDeviceDisable("1");  //并且属于激活状态
					DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActive);
					if(deviceActiveInfos.size() > 0){
						String  devId = deviceActiveInfos.get(0).getAt("device_imei").toString();
						session.setAttributeIfAbsent("devId", devId);  //每一个通道的id
						session.setAttributeIfAbsent("deviceInfo", deviceActiveInfos.get(0));   //记录设备信息
						
						if(mClientSessionManager.getSessionId(devId) != null){
							mClientSessionManager.removeSessionId(devId);
						}
						mClientSessionManager.addSessionId(devId, session);
						
						session.setAttributeIfAbsent("uDevId", devId);   // user通道						
						respJsonData.setResultCode(Constant.SUCCESS_CODE);
						respJsonData.setHaveUnread("0");
					}else{
						respJsonData.setRet("-1");
						respJsonData.setHaveUnread("0");
					}					
					respJsonData.setMsg("success!");
					respJsonData.setDevTime(dateFormat.format(new Date()));
				}else if(cmd.equals(AdragonConfig.UpDeviceInfo)){    //上传设备信息给服务器
					String serieNo = reqJsonData.getSerie_no();
					String productModel = reqJsonData.getDevice_product_model();
					String firmwareEdition = reqJsonData.getDevice_firmware_edition();
					String devicePhone = reqJsonData.getDevice_phone();
					String belongProject = reqJsonData.getB_g();
					
					DeviceLogin deviceLogin = new DeviceLogin();
					deviceLogin.setDeivceImei(serieNo);
					deviceLogin.setBelongProject(belongProject);
					DeviceLoginFacade deviceLoginFacade = serviceBean.getDeviceLoginFacade();
					
					PhoneInfo phoneInfo = new PhoneInfo();
					if(serieNo!=null && !"".equals(serieNo)){
						if(sb.length()>0){
							sb.append(" and ");
						}
						sb.append("serie_no ='"+serieNo+"'");
					}
					
					if(productModel!=null && !"".equals(productModel)){
						if(sb.length()>0){
							sb.append(" and ");
						}
						sb.append("product_Model ='"+productModel+"'");
					}
					if(firmwareEdition!=null && !"".equals(firmwareEdition)){
						if(sb.length()>0){
							sb.append(" and ");
						}
						sb.append("firmware_edition ='"+firmwareEdition+"'");
					}
					if(devicePhone!=null && !"".equals(devicePhone)){
						if(sb.length()>0){
							sb.append(" and ");
						}
						sb.append("phone ='"+devicePhone+"'");
					}
					if(belongProject!=null && !"".equals(belongProject)){
						if(sb.length()>0){
							sb.append(" and ");
						}
						sb.append("belong_project ='"+belongProject+"'");
					}
					phoneInfo.setCondition(sb.toString());					
					PhoneInfoFacade phoneInfoFacade = serviceBean.getPhoneInfoFacade();
				
					List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
					String phone_string = "";
					String status="1";
					if(phoneInfos.size()<=0){
						phoneInfo = new PhoneInfo();
						phoneInfo.setInputTime(new Date());
						phoneInfo.setStatus("1");
						phoneInfo.setSerieNo(serieNo);
						phoneInfo.setProductModel(productModel);
						phoneInfo.setFirmwareEdition(firmwareEdition);
						phoneInfo.setPhone(devicePhone);
						phoneInfo.setBelongProject(belongProject);
					
						phoneInfoFacade.insertPhoneInfo(phoneInfo);
					}else{					
						phone_string = ""+ phoneInfos.get(0).getAt("phone");
						status = ""+ phoneInfos.get(0).getAt("status");
					}
					
					deviceLogin.setBelongProject(belongProject);
					deviceLogin.setDateTime(new Date());
					deviceLogin.setDeivceImei(serieNo);
					deviceLogin.setDevicePhone(phone_string);
					deviceLogin.setDeviceVersion(firmwareEdition);
					deviceLogin.setDeviceStatus(status);
					deviceLoginFacade.insertDeviceLogin(deviceLogin);
										
					respJsonData.setResultCode(Constant.SUCCESS_CODE);
					respJsonData.setDevTime(dateFormat.format(new Date()));	
					
					DeviceActiveInfo deviceActive = new DeviceActiveInfo();
					deviceActive.setCondition("device_imei ='"+serieNo+"'");
					deviceActive.setDeviceDisable("1");  //并且属于激活状态
					
					
					if (!belongProject.equals("-108"))	//yonghu modify
					{
					DeviceActiveInfoFacade deviceActiveInfoFacade = serviceBean.getDeviceActiveInfoFacade();
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActive);
					session.setAttributeIfAbsent("devId", serieNo);  //每一个通道的id
					session.setAttributeIfAbsent("deviceInfo", deviceActiveInfos.get(0));   //记录设备信息
					}
					else
						session.setAttributeIfAbsent("devId", serieNo);  //每一个通道的id
						
					
					if(mClientSessionManager.getSessionId(serieNo) != null){
						mClientSessionManager.removeSessionId(serieNo);
					}
					mClientSessionManager.addSessionId(serieNo, session);
					
				}else if(cmd.equals(AdragonConfig.reqFamilyNumber)){  //请求亲情号码
					String devId="";
					sb = new StringBuffer();
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");				
					}
				
					List<RelativeCallInfoAdr> family_group = new ArrayList<RelativeCallInfoAdr>();
					respJsonData.setResultCode(Constant.SUCCESS_CODE);  //成功
					
					
					if(!family_group.isEmpty()){
						respJsonData.setFamily_group(family_group); //返回记录
					}
				}else if(cmd.equals(AdragonConfig.uploadLctData)){  //定位
				    String devId="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					Date start = new Date();
					JSONObject json = new JSONObject();
					String listen="0";
					String warn = "0";
					String f_m = "0";
					int y_y = 0;
					String phoneNumber = "";					
					String locationType = reqJsonData.getType(); // 1表示GPS,0表示基站,2表示wifi
					String battery = reqJsonData.getBattery();
					String fall = reqJsonData.getFall();  //1表示戴上，0表示脱落
				
				    LocationInfo locationInfo = new LocationInfo();
					double lng1 = 0;
					double lat1 = 0;
					
					if("1".equals(locationType)) {
						GPSInfoAdr gps = reqJsonData.getGps();
						
						String longitude = gps.getLon(); //经度
						String latitude = gps.getLat(); //纬度
						String accuracy = gps.getAcc(); //精确度
						if (devId != null && !"".equals(devId) && battery != null
							&& !"".equals(battery) && longitude != null
							&& !"".equals(longitude) && latitude != null
							&& !"".equals(latitude) && accuracy != null
							&& !"".equals(accuracy) && locationType != null
							&& !"".equals(locationType)) {

							lng1 = Double.parseDouble(longitude);
							lat1 = Double.parseDouble(latitude);
							if (lng1 != 0 && lat1 != 90) { // 直接过滤
								locationInfo.setCondition("serie_no ='" + devId + "' and belong_project = 1");
								locationInfo.setOrderBy("id");
								locationInfo.setSort("1"); // 按id降序
								locationInfo.setFrom(0);
								locationInfo.setPageSize(1); // 0至1

								List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
								boolean bool_is_update = true;
								String id = "0";
								if (locationInfos.size() > 0) { // 说明有数据
									id = "" + locationInfos.get(0).getAt("id");
									double lng2 = Double.parseDouble(""
											+ locationInfos.get(0).getAt("longitude"));
									double lat2 = Double.parseDouble(""
											+ locationInfos.get(0).getAt("latitude"));									
								}

								locationInfo.setSerieNo(devId);
								locationInfo.setBattery(Integer.parseInt(battery));
								locationInfo.setLongitude(longitude);
								locationInfo.setLatitude(latitude);
								locationInfo.setAccuracy(Float.parseFloat(accuracy));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(new Date());
								locationInfo.setFall(fall);								
								locationInfo.setBelongProject("1");
								
								String result = HttpRequest
										.sendGet(
												"http://restapi.amap.com/v3/assistant/coordinate/convert",
												"locations="
														+ longitude
														+ ","
														+ latitude
														+ "&coordsys=gps&output=json&key=801df1e9132e2151cd9ad435ecc59858");
								if ("-1".equals(result)) {
									respJsonData.setResultCode(Constant.FAIL_CODE);
									locationInfo.setChangeLongitude("0");
									locationInfo.setChangeLatitude("0");
								} else {
									JSONObject object= JSONObject.fromObject(result);;
									
									String location = object.getString("locations");
									String[] str = location.split(",");
									if (str.length == 2) {
										locationInfo.setChangeLongitude(str[0]);
										locationInfo.setChangeLatitude(str[1]);
									}
								}
								if (bool_is_update) { // 有效数据
									serviceBean.getLocationInfoFacade()
											.insertLocationInfo(locationInfo);
								} else {
									locationInfo.setCondition("id ='" + id + "'");
									serviceBean.getLocationInfoFacade()
											.updateLocationInfo(locationInfo);
								}
								respJsonData.setResultCode(Constant.SUCCESS_CODE);
							} else {
								respJsonData.setResultCode(Constant.FAIL_CODE);
							}
						}
					}else if ("0".equals(locationType)){
						NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
						String network = netWorkInfo.getNetWork();
						String cdma = netWorkInfo.getCdma();
						String smac = netWorkInfo.getSmac();
						String bts = netWorkInfo.getBts();
						String nearbts = netWorkInfo.getNearbts();
						String serverip = netWorkInfo.getServerip();

						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
						
						map.put("accesstype", "0");
						map.put("network", network);
						map.put("cdma", cdma);
						map.put("imei", devId);
						map.put("smac", smac);
						map.put("bts", bts);
						map.put("nearbts", nearbts);
						map.put("key", Constant.KEY);
						if (serverip != null && !"".equals(serverip)) {
							map.put("serverip", serverip);
						} else {
							map.put("serverip", Constant.SERVER_IP);
						}

						String jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);
						logger.info("jsonToString++" + jsonToString);
						if ("-1".equals(jsonToString)) {
							respJsonData.setResultCode(Constant.FAIL_CODE);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals("1")) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								if (location != null) {						
									locationInfo.setCondition("serie_no ='" + devId
												+ "' and belong_project = 1 and location_type ='"
												+ locationType + "'");
									

									locationInfo.setOrderBy("id");
									locationInfo.setSort("1"); // 按id降序
									locationInfo.setFrom(0);
									locationInfo.setPageSize(1); // 0至1

									List<DataMap> locationInfos = serviceBean.getLocationInfoFacade().getLocationInfo(locationInfo);
									boolean bool_is_update = true;
									String[] locations = location.split(",");
									String id = "0";
									if (locationInfos.size() > 0) { // 说明有数据
										id = "" + locationInfos.get(0).getAt("id");
										double lng2 = Double.parseDouble(""
												+ locationInfos.get(0)
														.getAt("longitude"));
										double lat2 = Double
												.parseDouble(""
														+ locationInfos.get(0).getAt(
																"latitude"));

										lng1 = Double.parseDouble(locations[0]);
										lat1 = Double.parseDouble(locations[1]);
									}

									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat("10.0"));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(new Date());
									locationInfo.setFall(fall);
									locationInfo.setBelongProject("1");
									
									if (bool_is_update) {
										serviceBean.getLocationInfoFacade().insertLocationInfo(locationInfo);
									} else {
										locationInfo.setCondition("id ='" + id + "'");
										serviceBean.getLocationInfoFacade().updateLocationInfo(locationInfo);
									}
									respJsonData.setResultCode(Constant.SUCCESS_CODE);
								}
							} else if (status.equals("0")) { 
								respJsonData.setResultCode(Constant.FAIL_CODE);
							} else if (status.equals("-1")) {
								respJsonData.setResultCode(Constant.FAIL_CODE);
							}
						}
					}else if ("2".equals(locationType)) { // wifi
						WifiInfoAdr wifi = reqJsonData.getWifi();
						String smac = wifi.getSmac();
						String mmac = wifi.getMmac();
						String macs = wifi.getMacs();
						String serverip = wifi.getServerip();

						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

						map.put("accesstype", "1");
						map.put("imei", devId);
						map.put("smac", smac);
						map.put("mmac", mmac);
						map.put("macs", macs);
						map.put("key", Constant.KEY);
						if (serverip != null && !"".equals(serverip)) {
							map.put("serverip", serverip);
						} else {
							map.put("serverip", Constant.SERVER_IP);
						}

						String jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);
						
						if ("-1".equals(jsonToString)) {
							respJsonData.setResultCode(Constant.FAIL_CODE);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals("1")) { 
								String results = jsons.getString("result");
								JSONObject jsonResult = JSONObject
										.fromObject(results);
								String location = jsonResult.getString("location"); 
								if (location != null) {									
									locationInfo.setCondition("serie_no ='" + devId
												+ "' and belong_project=1 and location_type ='"
												+ locationType + "'");
									
									locationInfo.setOrderBy("id");
									locationInfo.setSort("1"); // 按id降序
									locationInfo.setFrom(0);
									locationInfo.setPageSize(1); // 0至1

									List<DataMap> locationInfos = serviceBean.getLocationInfoFacade()
											.getLocationInfo(locationInfo);
									boolean bool_is_update = true;
									String[] locations = location.split(",");
									String id = "0";
									if (locationInfos.size() > 0) { // 说明有数据
										id = "" + locationInfos.get(0).getAt("id");
										double lng2 = Double.parseDouble(""
												+ locationInfos.get(0).getAt(
														"longitude"));
										double lat2 = Double.parseDouble(""
												+ locationInfos.get(0).getAt(
														"latitude"));

										lng1 = Double.parseDouble(locations[0]);
										lat1 = Double.parseDouble(locations[1]);

										bool_is_update = Constant.getDistance(lat1,
												lng1, lat2, lng2,
												Constant.EFFERT_DATA); // 若为true表示有效数据
									}

									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat("10.0"));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(new Date());
									locationInfo.setFall(fall);
									locationInfo.setBelongProject("1");

									if (bool_is_update) {
										serviceBean
												.getLocationInfoFacade()
												.insertLocationInfo(locationInfo);
									} else {
										locationInfo.setCondition("id ='" + id + "'");
										ServiceBean.getInstance()
												.getLocationInfoFacade()
												.updateLocationInfo(locationInfo);
									}
									respJsonData.setResultCode(Constant.SUCCESS_CODE);
								}
							} else if (status.equals("0")) {
								respJsonData.setResultCode(Constant.FAIL_CODE);
							} else if (status.equals("-1")) {
								respJsonData.setResultCode(Constant.FAIL_CODE);
							}
						}
					}
					
					//获取报警
					PhoneInfoFacade phoneInfoFacade = ServiceBean.getInstance().getPhoneInfoFacade();
					PhoneInfo phoneInfo = new PhoneInfo();
					phoneInfo.setCondition("serie_no ='"+devId+"' and belong_project=1");
					String relativeCallStatus = "0";
					List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
					if(phoneInfos.size()>0){
						String type = (String) phoneInfos.get(0).getAt("alarm_bell_type");
						relativeCallStatus = (String)phoneInfos.get(0).getAt("relative_call_status");
						if (type.equals("1")){// 有警报
							warn = "1";
							phoneInfo.setAlarmBellType("0");
							phoneInfoFacade.updatePhoneInfo(phoneInfo);
						}
					}else{
						phoneInfo.setSerieNo(devId);
						phoneInfo.setStatus("1"); // 上报
						phoneInfo.setUploadTime(new Date());
						phoneInfo.setInputTime(new Date());
						phoneInfo.setAlarmBellType("0"); // 没有警报
						phoneInfo.setBelongProject("1");

						phoneInfoFacade.insertPhoneInfo(phoneInfo);
					}


					
					List<VoiceInfoAdr> voiceInfos = new ArrayList<VoiceInfoAdr>();
					respJsonData.setF_m(f_m);
					respJsonData.setY_y(voiceInfos);										
				}else if(cmd.equals(AdragonConfig.Monitor)){   //倾听
					String phoneNum = "";
					String devId = "";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					String belongProject = reqJsonData.getB_g();
					if(devId!=null && !"".equals(devId)){
						StringBuffer buffer = new StringBuffer();
						buffer.append("device_imei ='"+devId+"'");
						if(belongProject!=null && !"".equals(belongProject)){
							buffer.append(" and belong_project ='"+belongProject+"'");
						}
						buffer.append(" and listen_type='1' and device_disable='1'");
						
						DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
						DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
						deviceActiveInfo.setCondition(buffer.toString());
						List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
						if(deviceActiveInfos.size()>0){
							String userId = (String)deviceActiveInfos.get(0).getAt("user_id");
							AppUserInfo appUserInfo = new AppUserInfo();
							appUserInfo.setCondition("id='"+userId+"'");
							AppUserInfoFacade appUserInfoFacade = ServiceBean.getInstance().getAppUserInfoFacade();
							List<DataMap> appUserInfos = appUserInfoFacade.getAppUserInfo(appUserInfo);
							if(appUserInfos.size()>0){
								phoneNum = appUserInfos.get(0).getAt("user_name").toString();
								respJsonData.setResultCode(Constant.SUCCESS_CODE);
								respJsonData.setPhoneNum(phoneNum);
								deviceActiveInfo.setListenType("0");
								deviceActiveInfoFacade.updateDeviceActiveInfo(deviceActiveInfo);
							}			
						}else{
							respJsonData.setResultCode(Constant.FAIL_CODE);
						}
					}
					/*
					subData.setPhoneNum(phoneNum);
					sub = JSON.toJSONString(subData);
					
					String userId = "";
					if(session.containsAttribute("userId")){
						userId = session.getAttribute("userId").toString();
					}
					IoSession tempSession = mClientSessionManager.getSessionId(userId);
					if(tempSession != null){
						WriteFuture writeFuture = tempSession.write(sub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>() {

							public void operationComplete(IoFuture future) {
								
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("响应倾听成功");
								}else{
									logger.info("响应倾听失败=设备不在线");
								}								
							}							
						});
					}else{
						logger.info("响应倾听失败=设备没有上传心跳");
					}
					*/					
				}else if(cmd.equals(AdragonConfig.findDevice)){   //找手表
					sub = JSON.toJSONString(subData);
					String userId="";
					if(session.containsAttribute("userId")){
						userId =(String)session.getAttribute("userId");
					}
					sub = JSON.toJSONString(subData);
					IoSession tempSession = mClientSessionManager.getSessionId(userId);
					if(tempSession != null){
						WriteFuture writeFuture = tempSession.write(sub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>(){
							
							public void operationComplete(IoFuture future) {
								// TODO Auto-generated method stub
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("推送数据配置文件成功");
									respJsonData.setResultCode(Constant.SUCCESS_CODE);
								}else{
									logger.info("推送数据配置文件失败=设备不在线");
									respJsonData.setResultCode(Constant.FAIL_CODE);
								}
							}								
						});
					}else{
						logger.info("推送数据配置文件失败=设备没有上传心跳");
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}
				}else if(cmd.equals(AdragonConfig.FALLDOWN)){   //防脱落报警
					String devId ="";
					if(session.containsAttribute("devId")){
						devId = session.getAttribute("devId").toString();
					}
					String fall = reqJsonData.getFall();
					String belongProject = reqJsonData.getB_g();
					StringBuffer buffer = new StringBuffer();
					
					if(fall == null){
						fall = "0"; 
					}
					if(devId != null && !"".equals(devId)){
						buffer.append("serie_no = '"+devId+"'");
					}
					if(belongProject != null && !"".equals(belongProject)){
						if(buffer.length() > 0){
							buffer.append(" and ");
						}
						buffer.append("belong_project='"+belongProject+"'");
						
						DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
						deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_project='"+belongProject+"'");
						List<DataMap> deviceActiveInfos = serviceBean.getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
						
						String user_id = "-1";
						boolean isFall = false;  
						if(deviceActiveInfos.size() > 0){
							user_id = ""+deviceActiveInfos.get(0).getAt("user_id");
						}
						if("-1".equals(user_id)){
							String msgContent = "6@" + devId + "@" + "0"; 
							StringBuffer condition = new StringBuffer();
							condition.append("(msg_content ='5@"+devId+"@0'");
							condition.append(" or ");
							condition.append("msg_content ='6@"+devId+"@0')");
						}
					}									
				}else if(cmd.equals(AdragonConfig.batPowerLow)){  //低电量报警
					String devId = "";
					if(session.containsAttribute("devId")){
						devId = session.getAttribute("devId").toString();
					}
					String electricity = reqJsonData.getElectricity();
					String belongProject = reqJsonData.getB_g();
					DirectiveInfo directiveInfo = new DirectiveInfo();
					
					if(devId!=null && !"".equals(devId)){
						directiveInfo.setCondition("serie_no = '"+devId+"'");
					}
					
					if(belongProject != null && !"".equals(belongProject)){
						if(directiveInfo.getCondition()!=null || !"".equals(directiveInfo.getCondition())){
							directiveInfo.setCondition(" and ");
						}
						directiveInfo.setCondition("belong_project = '"+belongProject+"'");
					}
					
					DirectiveInfoFacade directiveInfoFacade = serviceBean.getDirectiveInfoFacade();
				    List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
					if(directiveInfos.size() > 0) {
						directiveInfo.setLowelectricity(electricity);
						directiveInfo.setIsLow("1");
						directiveInfoFacade.updateDirectiveInfo(directiveInfo);
					    respJsonData.setResultCode(Constant.SUCCESS_CODE);
					}else {
						directiveInfo.setSerie_no(devId);
						directiveInfo.setLowelectricity(electricity);
						directiveInfo.setIsLow("1");
						directiveInfo.setBelongProject(belongProject);
						directiveInfoFacade.insertDirectiveInfo(directiveInfo);
						respJsonData.setResultCode(Constant.SUCCESS_CODE);
					}
																	
				}else if(cmd.equals(AdragonConfig.alarms)){     //远程闹钟
					String devId="";
					if(session.containsAttribute("id")){
						devId = (String) session.getAttribute("id");
					}
					DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
					
					DirectiveInfo directiveInfo = new DirectiveInfo();
					directiveInfo.setSerie_no(devId);
					List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
					List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
					for(int i=0;i<directiveInfos.size();i++){
						DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
						directiveInfoAdragon.setMonday((String)directiveInfos.get(i).getAt("mdistime"));
						directiveInfoAdragon.setTuesday((String)directiveInfos.get(i).getAt("tdistime"));
						directiveInfoAdragon.setWednesday((String)directiveInfos.get(i).getAt("wdistime"));
						directiveInfoAdragon.setThursday((String)directiveInfos.get(i).getAt("thdistime"));
						directiveInfoAdragon.setFriday((String)directiveInfos.get(i).getAt("fdistime"));
						directiveInfoAdragon.setSaturday((String)directiveInfos.get(i).getAt("sdistime"));
						directiveInfoAdragon.setSunday((String)directiveInfos.get(i).getAt("sudistime"));
						directiveInfoAdragons.add(directiveInfoAdragon);
					}
					respJsonData.setResultCode(Constant.SUCCESS_CODE); //成功
					if(directiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE);  //失败
					}
				    respJsonData.setAlarm(directiveInfoAdragons);
					
				}else if(cmd.equals(AdragonConfig.DEVICESLEEP)){  //睡眠提醒
					String devId="";
					if(session.containsAttribute("id")){
						devId = (String) session.getAttribute("id");
					}
					DirectiveInfo directiveInfo = new DirectiveInfo();
					directiveInfo.setSerie_no(devId);
					
					DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
					List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
					List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
					for(int i=0;i<directiveInfos.size();i++){
						DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
						directiveInfoAdragon.setMonday((String)directiveInfos.get(i).getAt("mdistime"));
						directiveInfoAdragon.setTuesday((String)directiveInfos.get(i).getAt("tdistime"));
						directiveInfoAdragon.setWednesday((String)directiveInfos.get(i).getAt("wdistime"));
						directiveInfoAdragon.setThursday((String)directiveInfos.get(i).getAt("thdistime"));
						directiveInfoAdragon.setFriday((String)directiveInfos.get(i).getAt("fdistime"));
						directiveInfoAdragon.setSaturday((String)directiveInfos.get(i).getAt("sdistime"));
						directiveInfoAdragon.setSunday((String)directiveInfos.get(i).getAt("sudistime"));
						directiveInfoAdragons.add(directiveInfoAdragon);
					}
					
					respJsonData.setResultCode(Constant.SUCCESS_CODE);  //成功
					if(directiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE); //失败
					}
					respJsonData.setSleep(directiveInfoAdragons);
					
				}else if(cmd.equals(AdragonConfig.GetDisturb)){  //上课防打扰
					String devId="";
					String distrub ="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					DirectiveInfo directiveInfo = new DirectiveInfo();
					directiveInfo.setSerie_no(devId);
					DirectiveInfoFacade directiveInfoFacade = ServiceBean.getInstance().getDirectiveInfoFacade();
					
					List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
					List<DirectiveInfoAdr> directiveInfoAdragons = new ArrayList<DirectiveInfoAdr>();
					if(directiveInfos.size()>0){
						DirectiveInfoAdr directiveInfoAdragon = new DirectiveInfoAdr();
						distrub = directiveInfos.get(0).getAt("distrub").toString();
						directiveInfoAdragon.setMonday((String)directiveInfos.get(0).getAt("mdistime"));
						directiveInfoAdragon.setTuesday((String)directiveInfos.get(0).getAt("tdistime"));
						directiveInfoAdragon.setWednesday((String)directiveInfos.get(0).getAt("wdistime"));
						directiveInfoAdragon.setThursday((String)directiveInfos.get(0).getAt("thdistime"));
						directiveInfoAdragon.setFriday((String)directiveInfos.get(0).getAt("fdistime"));
						directiveInfoAdragon.setSaturday((String)directiveInfos.get(0).getAt("sdistime"));
						directiveInfoAdragon.setSunday((String)directiveInfos.get(0).getAt("sudistime"));
						directiveInfoAdragons.add(directiveInfoAdragon);
						respJsonData.setTimes(directiveInfoAdragons);
						respJsonData.setDisturb(distrub);
					}
					respJsonData.setResultCode(Constant.SUCCESS_CODE);  //成功
					if(directiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE); //失败
					}
									
				}else if(cmd.equals(AdragonConfig.DEVICEPOWOFF)){  //远程关机
					String devId = "";
					String userId = "-1";
					if(session.containsAttribute("devId")){
						devId = session.getAttribute("devId").toString();
					}
					String type = reqJsonData.getType(); //type为0表示手动关机、1表示低电量关机、2表示远程关机
					String belongProject = reqJsonData.getB_g();
					if(devId!=null && !"".equals(devId) && type!=null && !"".equals(type)){
						DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
						deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_project ='"+belongProject+"' and device_disable ='1'");
						List<DataMap> deviceActiveInfos = serviceBean.getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
						if(deviceActiveInfos.size()>0){
							userId = deviceActiveInfos.get(0).getAt("user_id").toString();
						}
						if(!"-1".equals(userId)){
							String content = "8@" + devId + "@" + "0";  
							if(type.equals("0")){
								content = "9@" + devId + "@" + "0";
							}else if(type.equals("1")){
								content = "10@" + devId + "@" + "0";
							}
							respJsonData.setResultCode(Constant.SUCCESS_CODE);
						}else{
							respJsonData.setResultCode(Constant.FAIL_CODE);
						}
					}else{
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}
				}else if(cmd.equals(AdragonConfig.picture)){  //远程拍照
					subData.setCmd(AdragonConfig.U_Picture);
					sub = JSON.toJSONString(subData);
					String userId="";
					if(session.containsAttribute("userId")){
						userId =(String)session.getAttribute("userId");
					}
					sub = JSON.toJSONString(subData);
					IoSession tempSession = mClientSessionManager.getSessionId(userId);
					if(tempSession != null){
						WriteFuture writeFuture = tempSession.write(sub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>() {

							public void operationComplete(IoFuture future) {
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("请求远程拍照成功");
									respJsonData.setResultCode(Constant.SUCCESS_CODE);
								}else{
									logger.info("请求远程拍照失败=设备不在线");
									respJsonData.setResultCode(Constant.FAIL_CODE);
								}
							}							
						});
					}else{
						logger.info("请求远程拍照失败=设备没有上传心跳");
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}
				}else if(cmd.equals(AdragonConfig.DEVICEMSGSETTING)){  //基本的远程设置
					String devId = "";
					if(session.containsAttribute("devId")){
						devId = session.getAttribute("devId").toString();
					}
					String belongProject = reqJsonData.getB_g();
					if(devId !=null && !"".equals(devId) && belongProject!=null && !"".equals(belongProject)){
						StringBuffer buffer = new StringBuffer();
						buffer.append("serie_no='"+devId+"'");
						if(belongProject!=null && !"".equals(belongProject)){
							buffer.append(" and belong_project ='"+belongProject+"'");
						}
					}		
				}else if(cmd.equals(AdragonConfig.falldown)){  //老人跌倒警告
					String devId="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					Date start = new Date();
					String listen="0";
					String warn = "0";
					String f_m = "0";
					String phoneNumber = "";
					JSONArray mediaArr = new JSONArray();					
						
					String locationType = reqJsonData.getType(); // 1表示GPS,0表示基站,2表示wifi
					String battery = reqJsonData.getBattery();
					String fall = reqJsonData.getFall();
					
					LocationInfo locationInfo = new LocationInfo();
					double lng1 = 0;
					double lat1 = 0;
						
					if("1".equals(locationType)){
						GPSInfoAdr gps = reqJsonData.getGps();
							
						String longitude = gps.getLon(); //经度
						String latitude = gps.getLat(); //纬度
						String accuracy = gps.getAcc(); //精确度
						if (devId!=null && !"".equals(devId) && battery!=null
							&& !"".equals(battery) && longitude != null
							&& !"".equals(longitude) && latitude != null
							&& !"".equals(latitude) && accuracy != null
							&& !"".equals(accuracy) && locationType != null
							&& !"".equals(locationType)){

							lng1 = Double.parseDouble(longitude);
							lat1 = Double.parseDouble(latitude);
							if (lng1 != 0 && lat1 != 90) { // 直接过滤
								locationInfo.setCondition("serie_no ='" + devId + "'");
								locationInfo.setOrderBy("id");
								locationInfo.setSort("1"); // 按id降序
								locationInfo.setFrom(0);
								locationInfo.setPageSize(1); // 0至1

								List<DataMap> locationInfos = ServiceBean.getInstance()
											.getLocationInfoFacade().getLocationInfo(locationInfo);
								boolean bool_is_update = true;
									String id = "0";
									if (locationInfos.size() > 0) { // 说明有数据
										id = "" + locationInfos.get(0).getAt("id");
										double lng2 = Double.parseDouble(""
												+ locationInfos.get(0).getAt("longitude"));
										double lat2 = Double.parseDouble(""
												+ locationInfos.get(0).getAt("latitude"));									
									}

									locationInfo.setSerieNo(devId);
									locationInfo.setBattery(Integer.parseInt(battery));
									locationInfo.setLongitude(longitude);
									locationInfo.setLatitude(latitude);
									locationInfo.setAccuracy(Float.parseFloat(accuracy));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(new Date());
									locationInfo.setFall(fall);								
									locationInfo.setBelongProject("1");
									
									String result = HttpRequest
											.sendGet(
													"http://restapi.amap.com/v3/assistant/coordinate/convert",
													"locations="
															+ longitude
															+ ","
															+ latitude
															+ "&coordsys=gps&output=json&key=801df1e9132e2151cd9ad435ecc59858");
									if(!"-1".equals(result)){
										JSONObject object= JSONObject.fromObject(result);;
										
										String location = object.getString("locations");
										String[] str = location.split(",");
										if (str.length == 2) {
											locationInfo.setChangeLongitude(str[0]);
											locationInfo.setChangeLatitude(str[1]);
										}
									}
									if (bool_is_update) { // 有效数据
										ServiceBean.getInstance().getLocationInfoFacade()
												.insertLocationInfo(locationInfo);
									} else {
										locationInfo.setCondition("id ='" + id + "'");
										ServiceBean.getInstance().getLocationInfoFacade()
												.updateLocationInfo(locationInfo);
									}								
								}
							}
						}else if ("0".equals(locationType)){
							NetWorkInfoAdr netWorkInfo = reqJsonData.getNetWork();
							String network = netWorkInfo.getNetWork();
							String cdma = netWorkInfo.getCdma();
							String smac = netWorkInfo.getSmac();
							String bts = netWorkInfo.getBts();
							String nearbts = netWorkInfo.getNearbts();
							String serverip = netWorkInfo.getServerip();

							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							
							map.put("accesstype", "0");
							map.put("network", network);
							map.put("cdma", cdma);
							map.put("imei", devId);
							map.put("smac", smac);
							map.put("bts", bts);
							map.put("nearbts", nearbts);
							map.put("key", Constant.KEY);
							if (serverip != null && !"".equals(serverip)) {
								map.put("serverip", serverip);
							} else {
								map.put("serverip", Constant.SERVER_IP);
							}

							String jsonToString = HttpRequest.sendGetToGaoDe(
									Constant.LOCATION_URL, map);
							logger.info("jsonToString++" + jsonToString);
							if(!"-1".equals(jsonToString)){
								JSONObject jsons = JSONObject.fromObject(jsonToString);
								String status = jsons.getString("status"); 
								if (status.equals("1")) { 
									String results = jsons.getString("result"); 
									JSONObject jsonResult = JSONObject.fromObject(results);
									String location = jsonResult.has("location")?jsonResult.getString("location"):null; // ??��??????
									if (location != null) {										
										locationInfo.setCondition("serie_no ='" + devId
													+ "' and location_type ='"
													+ locationType + "'");
										
										locationInfo.setOrderBy("id");
										locationInfo.setSort("1"); // 按id降序
										locationInfo.setFrom(0);
										locationInfo.setPageSize(1); // 0至1

										List<DataMap> locationList = ServiceBean
												.getInstance().getLocationInfoFacade()
												.getLocationInfo(locationInfo);
										boolean bool_is_update = true;
										String[] locations = location.split(",");
										String id = "0";
										if (locationList.size() > 0) { // 说明有数据
											id = "" + locationList.get(0).getAt("id");
											double lng2 = Double.parseDouble(""
													+ locationList.get(0)
															.getAt("longitude"));
											double lat2 = Double
													.parseDouble(""
															+ locationList.get(0).getAt(
																	"latitude"));

											lng1 = Double.parseDouble(locations[0]);
											lat1 = Double.parseDouble(locations[1]);
										}

										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setLongitude(locations[0]);
										locationInfo.setLatitude(locations[1]);
										locationInfo.setChangeLongitude(locations[0]);
										locationInfo.setChangeLatitude(locations[1]);
										locationInfo.setAccuracy(Float.parseFloat("10.0"));
										locationInfo.setLocationType(locationType);
										locationInfo.setUploadTime(new Date());
										locationInfo.setFall(fall);
										locationInfo.setBelongProject("1");
										
										if (bool_is_update) {
											ServiceBean.getInstance()
													.getLocationInfoFacade()
													.insertLocationInfo(locationInfo);
										} else {
											locationInfo.setCondition("id ='" + id + "'");
											ServiceBean.getInstance()
													.getLocationInfoFacade()
													.updateLocationInfo(locationInfo);
										}						
									}
								}
							}
						}else if ("2".equals(locationType)) { // wifi
							WifiInfoAdr wifi = reqJsonData.getWifi();
							String smac = wifi.getSmac();
							String mmac = wifi.getMmac();
							String macs = wifi.getMacs();
							String serverip = wifi.getServerip();

							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

							map.put("accesstype", "1");
							map.put("imei", devId);
							map.put("smac", smac);
							map.put("mmac", mmac);
							map.put("macs", macs);
							map.put("key", Constant.KEY);
							if (serverip != null && !"".equals(serverip)) {
								map.put("serverip", serverip);
							} else {
								map.put("serverip", Constant.SERVER_IP);
							}

							String jsonToString = HttpRequest.sendGetToGaoDe(
									Constant.LOCATION_URL, map);
							
							if(!"-1".equals(jsonToString)){
								JSONObject jsons = JSONObject.fromObject(jsonToString);
								String status = jsons.getString("status"); 
								if (status.equals("1")){ 
									String results = jsons.getString("result");
									JSONObject jsonResult = JSONObject
											.fromObject(results);
									String location = jsonResult.getString("location"); 
									if (location != null) {									
										locationInfo.setCondition("serie_no ='" + devId
													+ "' and location_type ='"
													+ locationType + "'");
										
										locationInfo.setOrderBy("id");
										locationInfo.setSort("1"); // 按id降序
										locationInfo.setFrom(0);
										locationInfo.setPageSize(1); // 0至1

										List<DataMap> locationList = ServiceBean
												.getInstance().getLocationInfoFacade()
												.getLocationInfo(locationInfo);
										boolean bool_is_update = true;
										String[] locations = location.split(",");
										String id = "0";
										if (locationList.size() > 0) { // 说明有数据
											id = "" + locationList.get(0).getAt("id");
											double lng2 = Double.parseDouble(""
													+ locationList.get(0).getAt(
															"longitude"));
											double lat2 = Double.parseDouble(""
													+ locationList.get(0).getAt(
															"latitude"));

											lng1 = Double.parseDouble(locations[0]);
											lat1 = Double.parseDouble(locations[1]);

											bool_is_update = Constant.getDistance(lat1,
													lng1, lat2, lng2,
													Constant.EFFERT_DATA); // 若为true表示有效数据
										}

										locationInfo.setSerieNo(devId);
										locationInfo.setBattery(Integer.parseInt(battery));
										locationInfo.setLongitude(locations[0]);
										locationInfo.setLatitude(locations[1]);
										locationInfo.setChangeLongitude(locations[0]);
										locationInfo.setChangeLatitude(locations[1]);
										locationInfo.setAccuracy(Float.parseFloat("10.0"));
										locationInfo.setLocationType(locationType);
										locationInfo.setUploadTime(new Date());
										locationInfo.setFall(fall);
										locationInfo.setBelongProject("1");

										if (bool_is_update) {
											ServiceBean.getInstance()
													.getLocationInfoFacade()
													.insertLocationInfo(locationInfo);
										} else {
											locationInfo.setCondition("id ='" + id + "'");
											ServiceBean.getInstance()
													.getLocationInfoFacade()
													.updateLocationInfo(locationInfo);
										}										
									}								
								}
							}
						}
					//获取报警
					PhoneInfoFacade phoneInfoFacade = ServiceBean.getInstance().getPhoneInfoFacade();
					PhoneInfo phoneInfo = new PhoneInfo();
					phoneInfo.setCondition("serie_no ='"+devId+"' and belong_project=1");
					String relativeCallStatus = "0";
					List<DataMap> phoneInfos = phoneInfoFacade.getPhoneInfo(phoneInfo);
					if(phoneInfos.size()>0){
						String type = (String) phoneInfos.get(0).getAt("alarm_bell_type");
						relativeCallStatus = (String)phoneInfos.get(0).getAt("relative_call_status");
						if (type.equals("1")){// 有警报
							warn = "1";
							phoneInfo.setAlarmBellType("0");
							phoneInfoFacade.updatePhoneInfo(phoneInfo);
						}
					}else{
						phoneInfo.setSerieNo(devId);
						phoneInfo.setStatus("1"); // 上报
						phoneInfo.setUploadTime(new Date());
						phoneInfo.setInputTime(new Date());
						phoneInfo.setAlarmBellType("0"); // 没有警报
						phoneInfo.setBelongProject("1");

						phoneInfoFacade.insertPhoneInfo(phoneInfo);
					}
					String user_id = "0";
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					DeviceActiveInfoFacade deviceActiveFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();					
					deviceActiveInfo.setCondition("device_imei='" + devId
								+ "' and belong_project = 1 and listen_type='1' and device_disable='1'");
					
					boolean isBond = true;  //默认绑定
					List<DataMap> deviceActives = deviceActiveFacade.getDeviceActiveInfo(deviceActiveInfo);
					if (deviceActives.size() > 0) {
						user_id = (String) deviceActives.get(0).getAt("user_id");
						AppUserInfo appUser = new AppUserInfo();
						appUser.setCondition("id='" + user_id + "'");
						List<DataMap> appUserList = ServiceBean.getInstance()
								.getAppUserInfoFacade().getAppUserInfo(appUser);
						if (appUserList.size() > 0) {
							phoneNumber = (String) appUserList.get(0).getAt("user_name");
							listen = "1";
							deviceActiveInfo.setListenType("0");
							deviceActiveFacade.updateDeviceActiveInfo(deviceActiveInfo);
						}
					}
					
					deviceActiveInfo.setCondition("device_imei='" + devId+ "' and belong_project = 1 and device_disable='1'");				
					List<DataMap> deviceActiveLists = deviceActiveFacade.getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveLists.size() > 0){
						user_id = (String)deviceActiveLists.get(0).getAt("user_id");
					}else{
						isBond = false; // 解绑了
					}


					
					List<VoiceInfoAdr> voiceInfos = new ArrayList<VoiceInfoAdr>();
					respJsonData.setF_m(f_m);
					respJsonData.setY_y(voiceInfos);						
				}else if(cmd.equals(AdragonConfig.querySport)){   //运动检测
					String devId="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_project='1'");
					DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveHistory(deviceActiveInfo);
					if(deviceActiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}else{
						String sport = deviceActiveInfos.get(0).getAt("sports").toString();
						respJsonData.setResultCode(Constant.SUCCESS_CODE);  
						respJsonData.setSport(sport);	//返回运动检测结果					
					}
					
				}else if(cmd.equals(AdragonConfig.queryTemperature)){  //体温检测
					String devId="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belongProject = '1'");
					DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}else{
						String temperature = deviceActiveInfos.get(0).getAt("temperature").toString();
						respJsonData.setResultCode(Constant.SUCCESS_CODE);
						respJsonData.setTemperature(temperature); //返回体温检测结果
					}
				}else if(cmd.equals(AdragonConfig.reqVoice)){  //长链接语音对讲
					respJsonData.setCmd(AdragonConfig.pushVoice);
					
				}else if(cmd.equals(AdragonConfig.queryHeatrate)){  //心率监测
					String devId="";
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
					}
					String belongProject = reqJsonData.getB_g();
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					deviceActiveInfo.setCondition("device_imei ='"+devId+"' and belong_Project ='"+belongProject+"'");
					DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
					List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveInfos.isEmpty()){
						respJsonData.setResultCode(Constant.FAIL_CODE);
					}else{
						String heatrate = deviceActiveInfos.get(0).getAt("heatrate").toString();
						respJsonData.setResultCode(Constant.SUCCESS_CODE);
						respJsonData.setHeatrate(heatrate);  //返回心率检测结果
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				respJsonData.setResultCode(Constant.EXCEPTION_CODE);  //请求失败、异常
				respJsonData.setDevTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		}
		
		resp = JSON.toJSONString(respJsonData);
		
		session.write(resp);
	}
}
