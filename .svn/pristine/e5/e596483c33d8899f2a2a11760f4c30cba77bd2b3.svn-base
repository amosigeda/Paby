package com.wtwd.common.bean.deviceup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.deviceup.cmdobject.SleepData;
import com.wtwd.common.bean.deviceup.cmdobject.StepData;
import com.wtwd.common.bean.other.GPSInfoAdr;
import com.wtwd.common.bean.other.Geolocation;
import com.wtwd.common.bean.other.NetWorkInfoAdr;
import com.wtwd.common.bean.other.WifiInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.HttpRequest;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTAppGpsManAction;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetSleepInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.StepCountTools;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;


public class CmdUpPostImpl implements CmdUpPost {
	
	private RespJsonData respJson;
	
    private Logger logger = Logger.getLogger(CmdUpPostImpl.class);

	public CmdUpPostImpl() {
		respJson = new RespJsonData();
	}

	public RespJsonData stepDatasPost(ReqJsonData reqJson, IoSession session) throws SystemException {
		Tools tls = new Tools();
		
		String devId = "";
	    Integer device_id = 0;
	    Integer pet_id = 0;
//	    Integer userId = reqJson.getUserId();
	    String kindId = "";
	    String shoulder_height = "";	//肩高
	    String weight = "";   //
	    
	    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if ( session == null ) {
			devId = "352138064952338";
			device_id = 80;
		}
	    
	    //更新设备心跳时间
		if(session != null && session.containsAttribute("wdeviceInfo")){
			DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
			device_id = (Integer) dm.getAt("device_id");
			TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
			dft.setTimeZone(devTimeZone);
			dm = null;
		}
		
		if(session != null && session.containsAttribute("devId")){
			devId = (String) session.getAttribute("devId");
		}
		
		
		WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
		Wpet wpetInfo = new Wpet();
//		wpetInfo.setCondition("device_id = '" + device_id + "'");
		
        wpetInfo.setCondition("b.device_imei ='" + devId + "'");
		
		List<DataMap> wpetInfos = wpetFacade.getDogDataListByDevice(wpetInfo);
		
		if (wpetInfos.size() > 0) {
			pet_id = Integer.parseInt(wpetInfos.get(0).getAt("pet_id").toString());
			
			kindId = wpetInfos.get(0).getAt("fci_detail_all_catid").toString().trim();
			
			if ( "0".equals(kindId) || "F003".equals(kindId) || "F009".equals(kindId) ) {
				shoulder_height = "0.23";
			} else {
				shoulder_height = "0.48";
			}
			weight = wpetInfos.get(0).getAt("weight").toString();
			

			List<StepData>  stepDataInfos = reqJson.getStepDatas();
				
			long secs = 0;
			
//			for (int j = 0; j < stepDataInfos.size(); j++ ) {
//				System.out.println("stepDatas: " + j);
//				System.out.println("stepNum: " + stepDataInfos.get(j).getStepNumber());
//				System.out.println("startTime " + stepDataInfos.get(j).getStartTime());
//				System.out.println("endTime" + stepDataInfos.get(j).getEndTime());		
//			}
			
			List<WpetMoveInfo> wpetMoveInfos = new ArrayList<WpetMoveInfo>();
			
			//smile add
			WpetMoveInfo pmif = null;

			double maxSpeed = 0.0d;			
			
			for (int i = 0; i < stepDataInfos.size(); i++) {
				
				
				WpetMoveInfo wo = new WpetMoveInfo();
				StepData stepD = stepDataInfos.get(i);
				Float stepN = stepD.getStepNumber();
				
				wo.setPets_pet_id(pet_id);
				wo.setStep_number(stepN);
				//wo.setUp_time(dft.format(new Date()));				
				wo.setUp_time(tls.getUtcDateStrNow());
				wo.setStart_time(stepD.getStartTime());
				wo.setEnd_time(stepD.getEndTime());
				double route = stepN* (Double.parseDouble(shoulder_height))/3.0;
				wo.setRoute((float)route);
				wo.setCalories(stepN*Double.parseDouble(weight)*route);
				secs = tls.getSecondsBetweenDays(stepD.getStartTime(), stepD.getEndTime());
				double speed = stepN*60.0/secs;
				wo.setSpeed(speed);
				
				if ( speed > maxSpeed )
					maxSpeed = speed;
				
				wpetMoveInfos.add(wo);

				if (  i == 0 ) {
					pmif = wo; 
				}
				
				
			}
			
			ServiceBean.getInstance().getWpetMoveInfoFacade().insertWpetMoveInfoBatch(wpetMoveInfos);
			
			respJson.setUpStepDatas(Constant.SUCCESS_CODE);

			//smile add
			StepCountTools sct = new StepCountTools();
			if ( pmif != null) {
				pmif.setSpeed(maxSpeed);
				
				if (session != null) {
		        	synchronized(session) {						
		        		sct.proMoveDoMsgSe(pmif, device_id);
		        		sct = null;
		        	}
				} else {
	        		sct.proMoveDoMsgSe(pmif, device_id);
	        		sct = null;					
				}
			}
        	//smile add end
			
			
			stepDataInfos.clear();
			stepDataInfos = null;
			
		} else {
			respJson.setUpStepDatas(Constant.FAIL_CODE);
		}
	    
		wpetInfo = null;
		wpetInfos.clear();
		wpetInfos = null;
		
		return respJson;

	}

	public RespJsonData sleepDatasPost(ReqJsonData reqJson, IoSession session)
			throws SystemException {

		String devId = "";
	    Integer device_id = 0;
	    Integer pet_id = 0;
	    Tools tls = new Tools();
//	    Integer userId = reqJson.getUserId();
//	    String kindId = "";
//	    String shoulder_height = "";	//肩高
//	    String weight = "";   //
	    
	    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    //更新设备心跳时间
		if(session.containsAttribute("wdeviceInfo")){
			DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
			device_id = (Integer) dm.getAt("device_id");
			TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
			dft.setTimeZone(devTimeZone);
			dm = null;
		}
		
		if(session.containsAttribute("devId")){
			devId = (String) session.getAttribute("devId");
		}
		
		
		WpetFacade wpetFacade = ServiceBean.getInstance().getWpetFacade();
		Wpet wpetInfo = new Wpet();
//		wpetInfo.setCondition("device_id = '" + device_id + "'");
		
        wpetInfo.setCondition("b.device_imei ='" + devId + "'");
		
		List<DataMap> wpetInfos = wpetFacade.getDogDataListByDevice(wpetInfo);
		
		if (wpetInfos.size() > 0) {
			pet_id = Integer.parseInt(wpetInfos.get(0).getAt("pet_id").toString());
//			
//			kindId = wpetInfos.get(0).getAt("fci_detail_all_catid").toString().trim();
//			
//			if ( "0".equals(kindId) || "F003".equals(kindId) || "F009".equals(kindId) ) {
//				shoulder_height = "0.23";
//			} else {
//				shoulder_height = "0.48";
//			}
//			weight = wpetInfos.get(0).getAt("weight").toString();
			

			List<SleepData>  sleepDataInfos = reqJson.getSleepDatas();
				
			long secs = 0;
			
//			for (int j = 0; j < sleepDataInfos.size(); j++ ) {
//				System.out.println("stepDatas: " + j);
//				System.out.println("stepNum: " + sleepDataInfos.get(j).getStepNumber());
//				System.out.println("startTime " + sleepDataInfos.get(j).getStartTime());
//				System.out.println("endTime" + sleepDataInfos.get(j).getEndTime());		
//			}
			
			List<WpetSleepInfo> wpetSleepInfos = new ArrayList<WpetSleepInfo>();
			
			for (int i = 0; i < sleepDataInfos.size(); i++) {
				WpetSleepInfo wo = new WpetSleepInfo();
				SleepData sleepD = sleepDataInfos.get(i);
				Float stepN = sleepD.getStepNumber();

				wo.setPets_pet_id(pet_id);
				wo.setStep_number(stepN);
				wo.setUp_time(new Date());
				try {
					wo.setStart_time(dft.parse(sleepD.getStartTime()));
					wo.setEnd_time(dft.parse(sleepD.getEndTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				secs = tls.getSecondsBetweenDays(sleepD.getStartTime(), sleepD.getEndTime());
				double speed = stepN*60.0/secs;
				wo.setSpeed(speed);
				
				wpetSleepInfos.add(wo);
				
			}
			
			ServiceBean.getInstance().getWpetMoveInfoFacade().insertWpetSleepInfoBatch(wpetSleepInfos);
			
			respJson.setUpSleepDatas(Constant.SUCCESS_CODE);
			
			sleepDataInfos.clear();
			sleepDataInfos = null;
			
		} else {
			respJson.setUpSleepDatas(Constant.FAIL_CODE);
		}
	    
		wpetInfo = null;
		wpetInfos.clear();
		wpetInfos = null;
		
		return respJson;
		
	}

	public RespJsonData lctDatasPost(ReqJsonData reqJson, IoSession session)
			throws SystemException {
		Tools tls = new Tools();	
		
		String devId = "";
	    Integer device_id = 0;
//	    Integer userId = reqJson.getUserId();
	    
	    String cmd = reqJson.getCmd();
		
		  //定位
//	    DataMap deviceInfo = null;
		boolean bool_is_update = true;  // move here
		String locationType = reqJson.getType(); // 1 gaode GPS; 2 gaode lbs; 3 gaode wifi; 4 google gps; 5 google lbs/wifi; 6 gaode lbs/wifi;
		String battery = reqJson.getBattery();
		String fall = Tools.OneString;  //reqJsonData.getFall();  //1表示戴上，0表示脱落
		double lng1 = 0;
		double lat1 = 0;
		int stepCount = 0;
		
		if (!"".equals(reqJson.getStepNumber()) && reqJson.getStepNumber() != null) {
			stepCount = Integer.parseInt(reqJson.getStepNumber().trim());
		} else if (reqJson.getStepCount() != null) {
			stepCount = reqJson.getStepCount();
		}
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    //更新设备心跳时间
		if(session.containsAttribute("wdeviceInfo")){
			DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
			device_id = (Integer) dm.getAt("device_id");
			TimeZone devTimeZone = TimeZone.getTimeZone(dm.getAt("time_zone").toString()); 
			dft.setTimeZone(devTimeZone);
			dm = null;
		}
		
		if(session.containsAttribute("devId")){
			devId = (String) session.getAttribute("devId");
		}
		
		LocationInfo locationInfo = new LocationInfo();
		

//	    if (session.containsAttribute("wdeviceInfo")) {
//	    	deviceInfo = (DataMap) session.getAttribute("wdeviceInfo");
//	    }			    


	    //2016.12.17,不再需要设备心跳时间戳记录
	    //LocationInfoHelper.updateDeviceStatus(device_id, 0, null);
		locationInfo.setDevice_id(device_id);
		
		if(Tools.OneString.equals(locationType)) {
			
			try {
				GPSInfoAdr gps = reqJson.getGps();
				
				String longitude = gps.getLon(); //经度
				String latitude = gps.getLat(); //纬度
				String accuracy = gps.getAcc(); //精确度
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJson.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJson.getLctTime()));
				String result = "";
				JSONObject object;
				String location = "";
				String[] str;
				
				if (devId != null && !"".equals(devId) && longitude != null
					&& !"".equals(longitude) && latitude != null
					&& !"".equals(latitude) && accuracy != null
					&& !"".equals(accuracy) && locationType != null
					&& !"".equals(locationType)) {

					lng1 = Double.parseDouble(longitude);
					lat1 = Double.parseDouble(latitude);
					longitude = String.format("%.7f", lng1);
					latitude = String.format("%.7f", lat1);
					lng1 = Double.parseDouble(longitude);
					lat1 = Double.parseDouble(latitude);
					
					if (lng1 != 0 && lat1 != 90) { // 直接过滤
						locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
						locationInfo.setOrderBy("upload_time");
						locationInfo.setSort(Tools.OneString); // 按upload_time降序
						locationInfo.setFrom(0);
						locationInfo.setPageSize(1); // 0至1

						List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
						//boolean bool_is_update = true;
						
						locationInfo.setSerieNo(devId);
						locationInfo.setBattery(Integer.parseInt(battery));
						locationInfo.setLongitude(longitude);
						locationInfo.setLatitude(latitude);
						locationInfo.setAccuracy(Float.parseFloat(accuracy));
						locationInfo.setLocationType(locationType);
						locationInfo.setUploadTime(lctUpDate);
						locationInfo.setFall(fall);								
						locationInfo.setBelongProject(Tools.OneString);
						locationInfo.setStepCount(stepCount);
						
						if (locationInfos.size() > 0) { 
							String id = "" + locationInfos.get(0).getAt("id");
							double lng2 = Double.parseDouble(""
									+ locationInfos.get(0).getAt("longitude"));
							double lat2 = Double.parseDouble(""
									+ locationInfos.get(0).getAt("latitude"));	
							
//							if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
							if (Constant.getDistance(lat1, lng1, lat2, lng2, Constant.GPS_DIFF_DIST_GAP) == false) {
								locationInfo.setCondition("id ='" + id + "'");
								
								locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
								locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
								locationInfo.setUploadTime(lctUpDate);
								
								ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
								bool_is_update = false;
								respJson.setLctGps(Constant.SUCCESS_CODE);
							} else {
								result = HttpRequest
										.sendGet(
												"http://restapi.amap.com/v3/assistant/coordinate/convert",
												"locations="
														+ longitude
														+ ","
														+ latitude
														+ "&coordsys=gps&output=json&key=" + Constant.KEY_1);
								if ("-1".equals(result)) {

									respJson.setLctGps(Constant.FAIL_CODE);
//									locationInfo.setChangeLongitude(Tools.ZeroString);
//									locationInfo.setChangeLatitude(Tools.ZeroString);
									locationInfo = null;
									bool_is_update = false;
								} else {
									object= JSONObject.fromObject(result);;
									
									location = object.getString("locations");
									str = location.split(",");
									//if (str.length > 0) {
										locationInfo.setChangeLongitude(str[0]);
										locationInfo.setChangeLatitude(str[1]);
									//}
									ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	
									respJson.setLctGps(Constant.SUCCESS_CODE);
									bool_is_update = true;
								}
							}
						} else {

							result = HttpRequest
									.sendGet(
											"http://restapi.amap.com/v3/assistant/coordinate/convert",
											"locations="
													+ longitude
													+ ","
													+ latitude
													+ "&coordsys=gps&output=json&key=" + Constant.KEY_1);
							if ("-1".equals(result)) {

								respJson.setLctGps(Constant.FAIL_CODE);
//								locationInfo.setChangeLongitude(Tools.ZeroString);
//								locationInfo.setChangeLatitude(Tools.ZeroString);
								locationInfo = null;
								bool_is_update = false;
							} else {
								object= JSONObject.fromObject(result);;
								
								location = object.getString("locations");
								str = location.split(",");
								//if (str.length > 0) {
									locationInfo.setChangeLongitude(str[0]);
									locationInfo.setChangeLatitude(str[1]);
								//}
								ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	

								respJson.setLctGps(Constant.SUCCESS_CODE);
								bool_is_update = true;
							}
						}
						
						locationInfos.clear();
						locationInfos = null;
							
						//yonghu start
						LocationInfoHelper lih = new LocationInfoHelper();
						lih.proLctInfo(0, bool_is_update, locationInfo, cmd.equals(AdragonConfig.uploadLctData));
						lih = null;
						//yonghu end							

					} else {
						locationInfo = null;
						respJson.setLctGps(Constant.FAIL_CODE);
					}
				}
				
				longitude = null;
				latitude = null;
				accuracy = null;
				sdtf = null;
				lctUpDate = null;
				result = null;
				object = null;
				location = null;
				str = null;
				
			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println("Gaode gps location Exception!!");
				logger.error("Gaode gps location Exception!" + "\r\n" + e.toString());
				locationInfo = null;
				respJson.setLctGps(Constant.EXCEPTION_CODE);
			}
			
			//Gaode map lbs api
		} else if ("2".equals(locationType)){    
			
			try {
				
				NetWorkInfoAdr netWorkInfo = reqJson.getNetWork();
				String network = netWorkInfo.getNetWork();
				String cdma = netWorkInfo.getCdma();
				String smac = netWorkInfo.getSmac();
				String bts = netWorkInfo.getBts();
				String nearbts = netWorkInfo.getNearbts();
				String serverip = netWorkInfo.getServerip();
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				String id = "";
				String jsonToString = "";
				double lng2 = 0.0;
				double lat2 = 0.0;
				double distDiff = 0.0;
				int stepDiff = 0;
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJson.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJson.getLctTime()));
				
				map.put("accesstype", Tools.ZeroString);
				map.put("network", network);
				map.put("cdma", cdma);
				map.put("imei", devId);
				map.put("smac", smac);
				map.put("bts", bts);
				map.put("nearbts", nearbts);
				map.put("key", Constant.KEY);
//				if (serverip != null && !"".equals(serverip)) {
//					map.put("serverip", serverip);
//				} else {
//					map.put("serverip", Constant.SERVER_IP);
//				}
				map.put("serverip", serverip);
				
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
				
				locationInfo.setSerieNo(devId);
				locationInfo.setBattery(Integer.parseInt(battery));
				locationInfo.setLocationType(locationType);
				locationInfo.setUploadTime(lctUpDate);
				locationInfo.setFall(fall);
				locationInfo.setBelongProject(Tools.OneString);
				locationInfo.setStepCount(stepCount);
				
				if (locationInfos.size() > 0) {
					
					id = locationInfos.get(0).getAt("id").toString();
					lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
					lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
					Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
					
					String preLctType = locationInfos.get(0).getAt("location_type").toString();
					
					stepDiff = tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount);
					
					long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
					
					if (stepDiff < Constant.LBS_DIFF_STEPS && timeDiff < 5*60) {
						locationInfo.setCondition("id ='" + id + "'");
						
						locationInfo.setLongitude(locationInfos.get(0).getAt("longitude").toString());
						locationInfo.setLatitude(locationInfos.get(0).getAt("latitude").toString());
						locationInfo.setAccuracy(Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString()));
						locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
						locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
						locationInfo.setLocationType(locationInfos.get(0).getAt("location_type").toString());
						
						locationInfo.setUploadTime(lctUpDate);
						ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);	
						
						bool_is_update = false;
						
						//yonghu start
						LocationInfoHelper lih = new LocationInfoHelper();								
						lih.proLctInfo(0, false, locationInfo, 
								cmd.equals(AdragonConfig.uploadLctData));
						lih = null;
						//yonghu end	
						
						respJson.setLctLbs(Constant.SUCCESS_CODE);
						
					} else {
						jsonToString = HttpRequest.sendGetToGaoDe(
								Constant.LOCATION_URL, map);

						if ("-1".equals(jsonToString)) {
							locationInfo = null;
							respJson.setLctLbs(Constant.FAIL_CODE);
						} else {
							JSONObject jsons = JSONObject.fromObject(jsonToString);
							String status = jsons.getString("status"); 
							if (status.equals(Tools.OneString)) { 
								String results = jsons.getString("result"); 
								JSONObject jsonResult = JSONObject.fromObject(results);
								String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
								String radius = jsonResult.getString("radius"); 
								if (location != null) {	
									String[] locations = location.split(",");

									locationInfo.setLongitude(locations[0]);
									locationInfo.setLatitude(locations[1]);
									locationInfo.setChangeLongitude(locations[0]);
									locationInfo.setChangeLatitude(locations[1]);
									locationInfo.setAccuracy(Float.parseFloat(radius));
									locationInfo.setLocationType(locationType);
									locationInfo.setUploadTime(lctUpDate);
		
									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);
									
									distDiff = Constant.getDistance(lat1, lng1, lat2, lng2);
									
									if (distDiff > Constant.LBS_INVALID_DIST_GAP && stepDiff < Constant.LBS_DIFF_STEPS && !("2".equals(preLctType))) {
										locationInfo = null;
										respJson.setLctLbs(Constant.FAIL_CODE);
									} else {
//										if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
										if (distDiff < Constant.LBS_DIFF_DIST_GAP) { 
											locationInfo.setCondition("id ='" + id + "'");
											locationInfo.setUploadTime(lctUpDate);
											ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);	
											bool_is_update = false;
										} else {
											ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);
											bool_is_update = true;
										}

										//yonghu start
										LocationInfoHelper lih = new LocationInfoHelper();								
										lih.proLctInfo(0, false, locationInfo, 
												cmd.equals(AdragonConfig.uploadLctData));
										lih = null;
										//yonghu end	
										
										respJson.setLctLbs(Constant.SUCCESS_CODE);

									}
									
								} else {
									locationInfo = null;
									respJson.setLctLbs(Constant.FAIL_CODE);
								}
							} else if (status.equals(Tools.ZeroString)) { 
								locationInfo = null;
								respJson.setLctLbs(Constant.FAIL_CODE);
							} else if (status.equals("-1")) {
								locationInfo = null;
								respJson.setLctLbs(Constant.EXCEPTION_CODE);
							}
						}
						
					}
				} else {
					
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);

					if ("-1".equals(jsonToString)) {
						locationInfo = null;
						respJson.setLctLbs(Constant.FAIL_CODE);
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals(Tools.OneString)) { 
							String results = jsons.getString("result"); 
							JSONObject jsonResult = JSONObject.fromObject(results);
							String location = jsonResult.has("location")?jsonResult.getString("location"):null; 
							String radius = jsonResult.getString("radius"); 
							if (location != null) {	
								String[] locations = location.split(",");

								locationInfo.setLongitude(locations[0]);
								locationInfo.setLatitude(locations[1]);
								locationInfo.setChangeLongitude(locations[0]);
								locationInfo.setChangeLatitude(locations[1]);
								locationInfo.setAccuracy(Float.parseFloat(radius));
								locationInfo.setLocationType(locationType);
								locationInfo.setUploadTime(lctUpDate);

								ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);
								bool_is_update = true;
								
								//yonghu start
								LocationInfoHelper lih = new LocationInfoHelper();								
								lih.proLctInfo(0, false, locationInfo, 
										cmd.equals(AdragonConfig.uploadLctData));
								lih = null;
								//yonghu end	
								
								respJson.setLctLbs(Constant.SUCCESS_CODE);
							} else {
								locationInfo = null;
								respJson.setLctLbs(Constant.FAIL_CODE);
							}
						} else if (status.equals(Tools.ZeroString)) { 
							locationInfo = null;
							respJson.setLctLbs(Constant.FAIL_CODE);
						} else if (status.equals("-1")) {
							locationInfo = null;
							respJson.setLctLbs(Constant.EXCEPTION_CODE);
						}
					}
					
				}
				
				netWorkInfo = null;
				network = null;
				cdma = null;
				smac = null;
				bts = null;
				nearbts = null;
				serverip = null;
				sdtf = null; 
				lctUpDate = null;
				jsonToString = null;
				map.clear();
				map = null;
				locationInfos.clear();
				locationInfos = null;
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Gaode lbs location Exception!" + "\r\n" + e.toString());
				locationInfo = null;
				respJson.setLctLbs(Constant.EXCEPTION_CODE);
			}
			
		} else if ("3".equals(locationType)) { // wifi gaode map api
			
			Boolean lctWIFIFlag = false;
			
			WifiInfoAdr wifi = reqJson.getWifi();
			String smac = wifi.getSmac();
			String mmac = wifi.getMmac();
			String macs = wifi.getMacs();
			String serverip = wifi.getServerip();
			
			SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

			map.put("accesstype", Tools.OneString);
			map.put("imei", devId);
			map.put("smac", smac);
			map.put("mmac", mmac);
			map.put("macs", macs);
			map.put("key", Constant.KEY);
//			if (serverip != null && !"".equals(serverip)) {
//				map.put("serverip", serverip);
//			} else {
//				map.put("serverip", Constant.SERVER_IP);
//			}
			map.put("serverip", serverip);
			
		try {
			
			Date lctUpDate = reqJson.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJson.getLctTime()));
			
			String jsonToString = HttpRequest.sendGetToGaoDe(
					Constant.LOCATION_URL, map);

			if ("-1".equals(jsonToString)) {
				locationInfo = null;
				lctWIFIFlag = false;
				respJson.setUploadlctWIFI(Constant.EXCEPTION_CODE);
			} else {
				JSONObject jsons = JSONObject.fromObject(jsonToString);
				String status = jsons.getString("status"); 
				
				Boolean isValidWIFI = false;
				
				if (status.equals(Tools.OneString)) { 
					String results = jsons.getString("result");
					JSONObject jsonResult = JSONObject
							.fromObject(results);
					
					String type = jsonResult.optString("type");
					
					if ("0".equals(type)) {
						locationInfo = null;
						lctWIFIFlag = false;
						respJson.setUploadlctWIFI(Constant.FAIL_CODE);
					} else {
						
						String location = jsonResult.getString("location"); 
						String radius = jsonResult.getString("radius");
						String[] locations = location.split(",");
						
						locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
						
						locationInfo.setOrderBy("upload_time");
						locationInfo.setSort(Tools.OneString); // 按upload_time降序
						locationInfo.setFrom(0);
						locationInfo.setPageSize(1); // 0至1

						List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
						bool_is_update = true;	
						
						String id = "";
						if (locationInfos.size() > 0) { // 说明有数据
							id = locationInfos.get(0).getAt("id").toString();
							double lng2 = Double.parseDouble(locationInfos.get(0).getAt("longitude").toString());
							double lat2 = Double.parseDouble(locationInfos.get(0).getAt("latitude").toString());
							int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
							
							String lctType = locationInfos.get(0).getAt("location_type").toString();
							
							lng1 = Double.parseDouble(locations[0]);
							lat1 = Double.parseDouble(locations[1]);
							
							if (tls.getDiffSteps(stepCountPre, stepCount) > Constant.WIFI_DIFF_STEPS) {
								bool_is_update = true;
							} else {
								bool_is_update = false;
							}
							
							if (Constant.getDistance(lat1, lng1, lat2, lng2, Constant.IS_VALID_WIFI) && tls.getDiffSteps(stepCountPre, stepCount) < Constant.WIFI_DIFF_STEPS && !("2".equals(lctType))) {
								isValidWIFI = false;
								
							} else {
								isValidWIFI = true;
							}

//							bool_is_update = Constant.getDistance(lat1,
//									lng1, lat2, lng2,
//									Constant.EFFERT_DATA); // 若为true表示有效数据
						} else {
							bool_is_update = true;
							isValidWIFI = true;
						}

						locationInfo.setSerieNo(devId);
						locationInfo.setBattery(Integer.parseInt(battery));
						locationInfo.setLongitude(locations[0]);
						locationInfo.setLatitude(locations[1]);
						locationInfo.setChangeLongitude(locations[0]);
						locationInfo.setChangeLatitude(locations[1]);
						locationInfo.setAccuracy(Float.parseFloat(radius));
						locationInfo.setLocationType(locationType);
						locationInfo.setUploadTime(lctUpDate);
						locationInfo.setFall(fall);
						locationInfo.setBelongProject(Tools.OneString);
						locationInfo.setStepCount(stepCount);
						
						locationInfos.clear();
						locationInfos = null;

						if (isValidWIFI) {
							if (bool_is_update) {
								ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);
							} else {
								locationInfo.setCondition("id ='" + id + "'");
								locationInfo.setUploadTime(lctUpDate);
								ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
							}
							
							//yonghu start
							LocationInfoHelper lih = new LocationInfoHelper();
							//BaseAction.insertVisit("-1", null, null, "proLctInfo");
							lih.proLctInfo(0, bool_is_update, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							//yonghu end	
							
							lctWIFIFlag = true;
							respJson.setUploadlctWIFI(Constant.SUCCESS_CODE);
						} else {	
							locationInfo = null;
							lctWIFIFlag = false;
							respJson.setUploadlctWIFI(Constant.INVALID_DATA);
						}

					} 
					
				} else if (status.equals(Tools.ZeroString)) {
					locationInfo = null;
					lctWIFIFlag = false;
					respJson.setUploadlctWIFI(Constant.FAIL_CODE);
				} else if (status.equals("-1")) {
					locationInfo = null;
					lctWIFIFlag = false;
					respJson.setUploadlctWIFI(Constant.EXCEPTION_CODE);
				}
			}
			
			
			if (false) {  //(cmd.equals(AdragonConfig.getLocationRes) && lctWIFIFlag == false && locationInfo == null) {
				
				locationInfo = new LocationInfo();
				locationInfo.setCondition("serie_no = '" + devId + "' and belong_project=1 ");
				
				locationInfo.setOrderBy("upload_time");
				locationInfo.setSort(Tools.OneString); // 按upload_time降序
				locationInfo.setFrom(0);
				locationInfo.setPageSize(1); // 0至1

				List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
				
				if (locationInfos.size() > 0) {
					int stepCountPre = Integer.parseInt(locationInfos.get(0).getAt("step_count").toString());
					
					if (tls.getDiffSteps(stepCountPre, stepCount) < Constant.WIFI_LCT_FAIL_STEPS) {
						
						locationInfo.setSerieNo(devId);
						locationInfo.setBattery(Integer.parseInt(battery));
						locationInfo.setLongitude(locationInfos.get(0).getAt("longitude").toString());
						locationInfo.setLatitude(locationInfos.get(0).getAt("latitude").toString());
						locationInfo.setAccuracy(Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString()));
						locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
						locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
						locationInfo.setLocationType(locationInfos.get(0).getAt("location_type").toString());
						locationInfo.setUploadTime(lctUpDate);
						locationInfo.setFall(fall);
						locationInfo.setBelongProject(Tools.OneString);
						locationInfo.setStepCount(stepCount);
						
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Gaode WIFI location Exception!!");
			logger.error("Gaode WIFI location Exception!" + "\r\n" + e.toString());
			locationInfo = null;
			lctWIFIFlag = false;
			respJson.setUploadlctWIFI(Constant.EXCEPTION_CODE);
		}
		
			wifi = null;
			smac = null;
			mmac = null;
			macs = null;
			serverip = null;
			sdtf = null; 
//			lctUpDate = null;
//			jsonToString = null;
			map.clear();
			map = null;

		} else if("4".equals(locationType)) {     //Googel Map gps
			
			GPSInfoAdr gps = reqJson.getGps();
			String longitude = gps.getLon(); //经度
			String latitude = gps.getLat(); //纬度
			String accuracy = gps.getAcc(); //精确度
			
			try {
				
				SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date lctUpDate = reqJson.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJson.getLctTime()));
				
				if (devId != null && !"".equals(devId) &&  longitude != null
						&& !"".equals(longitude) && latitude != null
						&& !"".equals(latitude) && accuracy != null
						&& !"".equals(accuracy) && locationType != null
						&& !"".equals(locationType)) {

						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						longitude = String.format("%.7f", lng1);
						latitude = String.format("%.7f", lat1);
						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						
					if (lng1 != 0 && lat1 != 90) { // 直接过滤
						
						locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
						locationInfo.setOrderBy("upload_time");
						locationInfo.setSort(Tools.OneString); // 按upload_time降序
						locationInfo.setFrom(0);
						locationInfo.setPageSize(1); // 0至1

						List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
						
						locationInfo.setSerieNo(devId);
						locationInfo.setBattery(Integer.parseInt(battery));
						locationInfo.setLongitude(longitude);
						locationInfo.setLatitude(latitude);
						locationInfo.setAccuracy(Float.parseFloat(accuracy));
						locationInfo.setLocationType(locationType);
						locationInfo.setChangeLongitude(longitude);
						locationInfo.setChangeLatitude(latitude);
						locationInfo.setUploadTime(lctUpDate);
						locationInfo.setFall(fall);								
						locationInfo.setBelongProject(Tools.OneString);
						locationInfo.setStepCount(stepCount);
							
						if (locationInfos.size() > 0) { 
							    String id = "" + locationInfos.get(0).getAt("id");
								double lng2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("longitude"));
								double lat2 = Double.parseDouble(""
										+ locationInfos.get(0).getAt("latitude"));	
								
//							if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
							if (Constant.getDistance(lat1, lng1, lat2, lng2, Constant.GPS_DIFF_DIST_GAP) == false) {
								locationInfo.setCondition("id ='" + id + "'");
								locationInfo.setUploadTime(lctUpDate);
								ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
								bool_is_update = false;
							} else {
								locationInfo.setUploadTime(lctUpDate);
								ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	
								bool_is_update = true;
							}
						} else {
							locationInfo.setUploadTime(lctUpDate);
							ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	
							bool_is_update = true;
						}
						
						locationInfos.clear();
						locationInfos = null;
							
						//yonghu start
						LocationInfoHelper lih = new LocationInfoHelper();																
						lih.proLctInfo(0, bool_is_update, locationInfo, 
								cmd.equals(AdragonConfig.uploadLctData));
						lih = null;
						//yonghu end							

						respJson.setLctGGps(Constant.SUCCESS_CODE);
							
						} else {
							locationInfo = null;
							respJson.setLctGGps(Constant.FAIL_CODE);
						}
					}
				
				gps = null;
				longitude = null;
				latitude = null;
				accuracy = null;
				sdtf = null;
				lctUpDate = null;
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Google gps location Exception!" + "\r\n" + e.toString());
				locationInfo = null;
				respJson.setLctGGps(Constant.EXCEPTION_CODE);
			}
			
		 } else if ("5".equals(locationType)) {     //Google geolocation API   
			 
			 try {
				    String id = "";
					String strGeolocation = "";
					
					SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date lctUpDate = reqJson.getLctTime().equals("")?(new Date()):(sdtf.parse(reqJson.getLctTime()));
					
					Geolocation geolocation = reqJson.getGeolocation();
					JSONObject gjsons = JSONObject.fromObject(geolocation);
					
					String param2 = gjsons.toString();
			
//					System.out.println("---before call google API json param: " + param2);
					
					locationInfo.setCondition("serie_no = '" + devId + "' and belong_project = 1 ");
					locationInfo.setOrderBy("upload_time");
					locationInfo.setSort(Tools.OneString); // 按upload_time降序
					locationInfo.setFrom(0);
					locationInfo.setPageSize(1); // 0至1

					List<DataMap> locationInfos = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
					
					locationInfo.setSerieNo(devId);
					locationInfo.setBattery(Integer.parseInt(battery));
					locationInfo.setLocationType(locationType);
					locationInfo.setUploadTime(lctUpDate);
					locationInfo.setFall(fall);
					locationInfo.setBelongProject(Tools.OneString);
					locationInfo.setStepCount(stepCount);
					
					if (locationInfos.size() > 0) { 
						id = locationInfos.get(0).getAt("id").toString();
						double lng2 = Double.parseDouble(""
								+ locationInfos.get(0).getAt("longitude"));
						double lat2 = Double.parseDouble(""
								+ locationInfos.get(0).getAt("latitude"));	
						
						Date preLctDate = sdtf.parse(locationInfos.get(0).getAt("upload_time").toString());
								
						long timeDiff = tls.getSecondsBetweenDays(preLctDate, lctUpDate);
						
						if (tls.getDiffSteps(Integer.parseInt(locationInfos.get(0).getAt("step_count").toString()), stepCount) < Constant.GEO_DIFF_STEPS && timeDiff < 5*60) {
							locationInfo.setCondition("id ='" + id + "'");
							
							locationInfo.setLongitude(locationInfos.get(0).getAt("longitude").toString());
							locationInfo.setLatitude(locationInfos.get(0).getAt("latitude").toString());
							locationInfo.setAccuracy(Float.parseFloat(locationInfos.get(0).getAt("accuracy").toString()));
							locationInfo.setChangeLongitude(locationInfos.get(0).getAt("change_longitude").toString());
							locationInfo.setChangeLatitude(locationInfos.get(0).getAt("change_latitude").toString());
							locationInfo.setLocationType(locationInfos.get(0).getAt("location_type").toString());
							
							locationInfo.setUploadTime(lctUpDate);
							ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
							bool_is_update = false;
							
							//yonghu start
							LocationInfoHelper lih = new LocationInfoHelper();																
							lih.proLctInfo(0, bool_is_update, locationInfo, 
									cmd.equals(AdragonConfig.uploadLctData));
							lih = null;
							//yonghu end	
							
							respJson.setLctGMap(Constant.SUCCESS_CODE);
							
						} else {
							
							try {
								
								strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
								
//								System.out.println("--- cmd process call google API return : " + strGeolocation);
								
								if (strGeolocation != null && !strGeolocation.equals("")) {
									
									JSONObject geoJson = JSONObject.fromObject(strGeolocation);
									
									String error = geoJson.has("error")?geoJson.getString("error"):null;
									
									if (error == null) {
										String location = geoJson.getString("location");
										JSONObject locationJson = JSONObject.fromObject(location);
										double lat = locationJson.getDouble("lat");
										double lng = locationJson.getDouble("lng");
										double accuracy = geoJson.getDouble("accuracy");

										locationInfo.setLongitude(Double.toString(lng));
										locationInfo.setLatitude(Double.toString(lat));
										locationInfo.setChangeLongitude(Double.toString(lng));
										locationInfo.setChangeLatitude(Double.toString(lat));
										locationInfo.setAccuracy((float) accuracy);
										locationInfo.setUploadTime(lctUpDate);
										
										//if (Double.compare(lng1, lng2)==0 && Double.compare(lat1, lat2)==0) {
										if (Constant.getDistance(lat1, lng1, lat2, lng2, Constant.GEO_DIFF_DSIT_GAP) == false) {
											locationInfo.setCondition("id ='" + id + "'");
//											locationInfo.setUploadTime(lctUpDate);
											ServiceBean.getInstance().getLocationInfoFacade().updateLocationInfo(locationInfo);
											bool_is_update = false;
										} else {
											ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	
											bool_is_update = true;
										}
										
										//yonghu start
										LocationInfoHelper lih = new LocationInfoHelper();																
										lih.proLctInfo(0, bool_is_update, locationInfo, 
												cmd.equals(AdragonConfig.uploadLctData));
										lih = null;
										//yonghu end							

										respJson.setLctGMap(Constant.SUCCESS_CODE);
									} else {
										locationInfo = null;
										JSONObject errorJson = JSONObject.fromObject(error);
										String errorCode = errorJson.getString("code");	
										respJson.setLctGMap(Integer.parseInt(errorCode));
									}
								} else {
									locationInfo = null;
									respJson.setLctGMap(Constant.FAIL_CODE);
								}
								
							} catch(Exception e){
								e.printStackTrace();
//								System.out.println("Google Map Geolocation Exception!!");
								logger.error("Google Map Geolocation Exception ---1--- " + "\r\n" + e.toString());
								locationInfo = null;
								respJson.setLctGMap(Constant.EXCEPTION_CODE);
							}
							
						}
						
					} else {
						
						try {
							
							strGeolocation = HttpRequest.sendPost(Constant.GOOGLE_MAP_GEOLOCATION_URL, param2, null, "UTF-8");
							
//							System.out.println("--- cmd process call google API return : " + strGeolocation);
							
							if (strGeolocation != null && !strGeolocation.equals("")) {
								
								JSONObject geoJson = JSONObject.fromObject(strGeolocation);
								
								String error = geoJson.has("error")?geoJson.getString("error"):null;
								
								if (error == null) {
									String location = geoJson.getString("location");
									JSONObject locationJson = JSONObject.fromObject(location);
									double lat = locationJson.getDouble("lat");
									double lng = locationJson.getDouble("lng");
									double accuracy = geoJson.getDouble("accuracy");

									locationInfo.setLongitude(Double.toString(lng));
									locationInfo.setLatitude(Double.toString(lat));
									locationInfo.setChangeLongitude(Double.toString(lng));
									locationInfo.setChangeLatitude(Double.toString(lat));
									locationInfo.setAccuracy((float) accuracy);
									locationInfo.setUploadTime(lctUpDate);
									
									ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);	
									bool_is_update = true;
									
									//yonghu start
									LocationInfoHelper lih = new LocationInfoHelper();																
									lih.proLctInfo(0, bool_is_update, locationInfo, 
											cmd.equals(AdragonConfig.uploadLctData));
									lih = null;
									//yonghu end							

									respJson.setLctGMap(Constant.SUCCESS_CODE);
								} else {
									locationInfo = null;
									JSONObject errorJson = JSONObject.fromObject(error);
									String errorCode = errorJson.getString("code");									
									respJson.setLctGMap(Integer.parseInt(errorCode));
								}
							} else {
								locationInfo = null;
								respJson.setLctGMap(Constant.FAIL_CODE);
							}
							
						} catch(Exception e){
							e.printStackTrace();
//							System.out.println("Google Map Geolocation Exception!!");
							logger.error("Google Map Geolocation Exception ---2--- " + "\r\n" + e.toString());
							locationInfo = null;
							respJson.setLctGMap(Constant.EXCEPTION_CODE);
						}
						
					}
					
					sdtf = null;
					lctUpDate = null;
					geolocation = null;
					gjsons = null;
					param2 = null;
					locationInfos.clear();
					locationInfos = null;
				 
			 } catch (Exception e) {
				e.printStackTrace();
//				System.out.println("Google Map Geolocation Exception!!");
				logger.error("Google Map Geolocation Exception ---3--- " + "\r\n" + e.toString());
				locationInfo = null;
				respJson.setLctGMap(Constant.EXCEPTION_CODE);
			 }
			
		}  //Google geolocation API
		
		try {
			if (cmd.equals(AdragonConfig.getLocationRes)) { 
				
//				if (devId != null && !"".equals(devId)) {
				if (locationInfo != null && (!"".equals(locationInfo.getSerieNo()) && locationInfo.getSerieNo() != null )
						&& (!"".equals(locationInfo.getChangeLatitude()) && locationInfo.getChangeLatitude() != null )
					    && (!"".equals(locationInfo.getChangeLongitude()) && locationInfo.getChangeLongitude() != null )) {
					
					ServiceBean.getInstance().getLocationInfoFacade().insertClickLocationInfo(locationInfo);	
					
					if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
						int userId = reqJson.getUserId();
						//inform app user get update location 
						WTAppGpsManAction wgma = new WTAppGpsManAction();
						wgma.proGetDevLoc17(userId, device_id, devId);
						wgma = null;
					}

				} else {
					if ( "1.6".equals(Constant.PROTOCOL_VER ) ) {						
						int userId = reqJson.getUserId();
						//inform app user get update location fail 
						WTDevHandler.getClientSessionMangagerInstance().completeFailHttpCmdId(userId, device_id, devId, AdragonConfig.getLocationRes);
					}
					
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("getLocationRes exception!" + "\r\n" + e.toString());
		}

		devId = null;
		locationType = null;
		battery = null;
		fall = null;
	    locationInfo = null;

	    return respJson;
		
	}
	
}
