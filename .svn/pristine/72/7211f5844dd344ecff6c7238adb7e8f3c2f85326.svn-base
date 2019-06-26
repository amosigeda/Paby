package com.wtwd.sys.client.handler.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.directiveinfo.domain.DirectiveInfo;
import com.wtwd.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;

public class AdragonUserHandlerHelperIII {
	
	private static ServiceBean serviceBean = ServiceBean.getInstance();

	//睡眠提醒
	public static RespJsonData setSleepHelp(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSafe_type();
		String clock = reqJsonData.getClock();
		String belongProject = reqJsonData.getB_g();
		if(userId==null ||"".equals(userId)){
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			deviceActiveInfo.setCondition("device_imei ='"+serieNo+"' and belong_project ='"+belongProject+"'");
			DeviceActiveInfoFacade deviceActiveInfoFacade=serviceBean.getDeviceActiveInfoFacade();
			List<DataMap> deviceActiveInfos = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveInfos.size()>0){
				userId = deviceActiveInfos.get(0).getAt("user_id").toString();
			}
		}
		
		DirectiveInfo directiveInfo = new DirectiveInfo();
		directiveInfo.setCondition("device_imei ='"+serieNo+"' and belong_project ='"+belongProject+"'");
		DirectiveInfoFacade directiveInfoFacade = serviceBean.getDirectiveInfoFacade();
		List<DataMap> directiveInfos = directiveInfoFacade.getDirectiveInfo(directiveInfo);
		if(directiveInfos.size()>0){
			if("".equals(clock)){
				clock=" ";
			}
			directiveInfo.setSleep(clock);
			directiveInfo.setSleepChange("1");
			directiveInfoFacade.updateDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}else{
			if("".equals(clock)){
				clock=" ";
			}
			directiveInfo.setSleep(clock);
			directiveInfo.setSleepChange("1");
			directiveInfo.setSerie_no(serieNo);
			directiveInfo.setBelongProject(belongProject);
			directiveInfoFacade.insertDirectiveInfo(directiveInfo);
			result = Constant.SUCCESS_CODE;
		}
		respJsonData.setResultCode(result);
		
		return respJsonData;	
	}
	
	//轨迹查询
	public static RespJsonData queryTraceCount(ReqJsonData reqJsonData) throws SystemException{
		int result = 0;
		RespJsonData respJsonData = new RespJsonData();
		String userId = reqJsonData.getUser_id();
		String serieNo = reqJsonData.getSerie_no();
		String belongProject = reqJsonData.getB_g();
		JSONArray jsonArray = new JSONArray();
		JSONObject dateTimeJson = new JSONObject();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		String time = format.format(c.getTime());
		LocationInfo locationInfo = new LocationInfo();
		locationInfo.setCondition("serie_no ='"+serieNo+"' and belong_project='"+belongProject+"' and subString(upload_time,1,10)>='"+time+"'");
		LocationInfoFacade locationInfoFacade = serviceBean.getLocationInfoFacade();
		List<DataMap> locationInfos = locationInfoFacade.getLocationInfo(locationInfo);
		for(int i=0;i<locationInfos.size();i++){
			Date d = (Date) locationInfos.get(i).getAt("upload_time");
			dateTimeJson.accumulate("d"+i, format.format(d));
			jsonArray.add(dateTimeJson);
			dateTimeJson.clear();
		}
		
		result = Constant.SUCCESS_CODE;
		respJsonData.setDate_time(jsonArray.toString());
		respJsonData.setResultCode(result);
		
		return respJsonData;	
	}
}
