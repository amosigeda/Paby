package com.wtwd.sys.appinterfaces.liufeng.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceLocationFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceTrackFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

/**
 * APP用户获取所有设备当前位置数据列表
 * APP获取单个设备历史轨迹数据
 * @author Administrator
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppGpsMan.do
 */
public class WTAppGpsManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppGpsManAction.class);
	
	//doWTAppGpsMan		execute
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		WappUsers wu = new WappUsers();
		
		AppUserDeviceLocationFacade infoFacade = ServiceBean.getInstance().getAppUserDeviceLocationFacade();
		AppUserDeviceTrackFacade trackFacade = ServiceBean.getInstance().getAppUserDeviceTrackFacade();
		try {
			String cmd = request.getParameter("cmd");
			String user_id = request.getParameter("user_id");
			String app_token = request.getParameter("app_token");
			
			if(cmd != null && !"".equals(cmd)){
				if(cmd.equals("getDevsList")){
					if(user_id != null && !"".equals(user_id)){
						sb.append(" wau.user_id = "+user_id);
					}
					if(app_token != null && !"".equals(app_token)){
						sb.append(" AND wau.app_token = '"+app_token+"'");
					}
					wu.setCondition(sb.toString());

					List<DataMap> list = infoFacade.getAppUserDeviceLocationInfo(wu);
					System.out.println("list:"+list.size());
					
					if(list != null && list.size() > 0){
						JSONArray jsonArr = new JSONArray();
						for(int i=0;i<list.size();i++){
							DataMap locationMap = list.get(i);
							JSONObject locationJson = new JSONObject();
							int device_id = (Integer) locationMap.getAt("device_id");
							String type = (String) locationMap.getAt("device_type");
							String battery = locationMap.getAt("battery").toString();
							String fall = (String) locationMap.getAt("fall");
							String lon = (String) locationMap.getAt("longitude");
							String lat = (String) locationMap.getAt("latitude");
							String acc = locationMap.getAt("accuracy").toString();
							String led_on = (String) locationMap.getAt("led_on");
							String sel_mode = (String) locationMap.getAt("sel_mode");
							String gps_on = (String) locationMap.getAt("gps_on");
							
							locationJson.put("device_id", device_id);
							locationJson.put("type", type);
							locationJson.put("battery", battery);
							locationJson.put("fall", fall);
							locationJson.put("lon", lon);
							locationJson.put("lat", lat);
							locationJson.put("acc", acc);
							locationJson.put("led_on", led_on);
							locationJson.put("sel_mode", sel_mode);
							locationJson.put("gps_on", gps_on);
							jsonArr.add(locationJson);
						}
						json.put("device_list", jsonArr);
						result = Constant.SUCCESS_CODE;
					}else{
						result = Constant.FAIL_CODE;
					}
					
					json.put("device_count", list.size());
					
				}else if(cmd.equals("getDevTrack")){
					int num = 0;
					int numCount = 0;
					String device_id = request.getParameter("device_id");
					String start_time = request.getParameter("start_time");
					String end_time = request.getParameter("end_time");
					String from = request.getParameter("from");
					String pageSize = request.getParameter("pageSize");
					
					JSONArray jsonArr = new JSONArray();
					
					if(app_token != null && !"".equals(app_token)){
						if(user_id != null && !"".equals(user_id)){
							if(Common.isValidationUserInfo(user_id,app_token)){
								sb.append(" ( wsi.user_id = "+user_id+" OR wsi.to_user_id = "+user_id+" ) ");
								if(device_id != null && !"".equals(device_id)){
									sb.append(" AND wsi.device_id = "+device_id);
								}
								if(start_time != null && !"".equals(start_time) && end_time != null && !"".equals(end_time)){
									sb.append(" AND upload_time BETWEEN '"+start_time+"' AND '"+end_time+"' ");
								}
								wu.setCondition(sb.toString());
								numCount = trackFacade.getAppUserDeviceTrackCountList(wu);
								
								if(from != null && !"".equals(from)){
									if(pageSize != null && !"".equals(pageSize)){
										sb.append(" LIMIT "+from+","+pageSize);
									}else{
										sb.append(" LIMIT "+from+",2000 ");
									}
								}else{
									if(pageSize != null && !"".equals(pageSize)){
										sb.append(" LIMIT 0,"+pageSize);
									}else{
										sb.append(" LIMIT 0,2000 ");
									}
								}
								
								wu.setCondition(sb.toString());
								List<DataMap> trackList = trackFacade.getAppUserDeviceTrackList(wu);
								if(trackList != null && trackList.size() > 0){
									num = trackList.size();
									for(int i=0;i<trackList.size();i++){
										DataMap trackMap = trackList.get(i);
										JSONObject trackJson = new JSONObject();
										String type = (String) trackMap.getAt("location_type");
										String battery = trackMap.getAt("battery").toString();
										String fall = (String) trackMap.getAt("fall");
										String longitude = (String) trackMap.getAt("longitude");
										String latitude = (String) trackMap.getAt("latitude");
										String accuracy = trackMap.getAt("accuracy").toString();
										String led_on = (String) trackMap.getAt("led_on");
										String sel_mode = (String) trackMap.getAt("sel_mode");
										String gps_on = (String) trackMap.getAt("gps_on");
										
										trackJson.put("device_id", device_id);
										trackJson.put("type", type);
										trackJson.put("battery", battery);
										trackJson.put("fall", fall);
										trackJson.put("lon", longitude);
										trackJson.put("lat", latitude);
										trackJson.put("acc", accuracy);
										trackJson.put("led_on", led_on);
										trackJson.put("sel_mode", sel_mode);
										trackJson.put("gps_on", gps_on);
										jsonArr.add(trackJson);
									}
									result = Constant.SUCCESS_CODE;
								}else{
									result = Constant.FAIL_CODE;
								}
							}else{
								result = Constant.FAIL_CODE;
							}
						}else{
							result = Constant.ERR_INVALID_USER;
						}
					}else{
						result = Constant.ERR_INVALID_TOKEN;
					}
					
					json.put("tot_count", numCount);
					json.put("rec_count", num);
					json.put("data_list", jsonArr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("queryDeviceLocationList:"+e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}
		
		json.put(Constant.RESULTCODE, result);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
}
