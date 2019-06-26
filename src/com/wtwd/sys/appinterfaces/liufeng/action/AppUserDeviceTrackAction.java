package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceTrackFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

/**
 * 可删除文件
 * @author Administrator
 *
 */
public class AppUserDeviceTrackAction extends BaseAction {
	
	private Log log = LogFactory.getLog(AppUserDeviceTrackAction.class);
	private static AppUserDeviceTrackFacade infoFacade = ServiceBean.getInstance().getAppUserDeviceTrackFacade();
	
	public ActionForm queryAppDeviceTrackList(ActionMapping mapping,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		JSONObject trackJson = new JSONObject();
		StringBuffer sb = new StringBuffer();
		List<DataMap> trackList = null;
		WappUsers wu = new WappUsers();
		int num = 0;
		int numCount = 0; 
		try {
			request.setCharacterEncoding("UTF-8");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String device_id = request.getParameter("device_id");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String from = request.getParameter("from");
			String pageSize = request.getParameter("pageSize");
			
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
						numCount = infoFacade.getAppUserDeviceTrackCountList(wu);
						
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
						trackList = infoFacade.getAppUserDeviceTrackList(wu);
						if(trackList != null && trackList.size() > 0){
							num = trackList.size();
							for(int i=0;i<trackList.size();i++){
								DataMap trackMap = trackList.get(i);
								String type = (String) trackMap.getAt("location_type");
								String battery = (String) trackMap.getAt("battery");
								String fall = (String) trackMap.getAt("fall");
								String longitude = (String) trackMap.getAt("longitude");
								String latitude = (String) trackMap.getAt("latitude");
								String accuracy = (String) trackMap.getAt("accuracy");
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
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = Constant.EXCEPTION_CODE;
		}
		json.put("tot_count", numCount);
		json.put("rec_count", num);
		json.put("data_list", trackJson);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}
	
}
