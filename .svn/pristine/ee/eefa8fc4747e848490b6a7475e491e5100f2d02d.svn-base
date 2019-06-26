package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.IOException;
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
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;

/**
 * APP设备安全区域增加、删除及修改
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppSafeAreaMan.do
 */
public class WTAppSafeAreaManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppSafeAreaManAction.class);
	
	//doWTAppSafeAreaMan	execute
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		WeFencing wf = new WeFencing();
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String device_id = request.getParameter("device_id");
			
			String lat = request.getParameter("lat");
			String lon = request.getParameter("lon");
			String device_safe_id = request.getParameter("device_safe_id");
			String device_safe_range = request.getParameter("device_safe_range");
			String device_safe_name = request.getParameter("device_safe_name");
			String device_safe_addr = request.getParameter("device_safe_addr");
			String device_safe_effect_time = request.getParameter("device_safe_effect_time");
			String safe_type = request.getParameter("safe_type");
			
			if(cmd != null && !"".equals(cmd)){
				if(Common.isValidationUserInfo(user_id, app_token)){
					//判断cmd类型
					if("add".equals(cmd)){
						if(!Common.isMasterDevice(user_id, device_id)){
							result = Constant.FAIL_CODE;
						}else{
							wf.setDevice_id(Integer.parseInt(device_id));
							if(lat != null && !"".equals(lat)){
								wf.setCenter_gps_la(Double.parseDouble(lat));
							}
							if(lon != null && !"".equals(lon)){
								wf.setCenter_gps_lo(Double.parseDouble(lon));
							}
							if(device_safe_addr != null && !"".equals(device_safe_addr)){
								wf.setCenter_addr(device_safe_addr);
							}
							if(device_safe_range != null && !"".equals(device_safe_range)){
								wf.setRound_distance(Integer.parseInt(device_safe_range));
							}
							if(device_safe_name != null && !"".equals(device_safe_name)){
								wf.setDevice_safe_name(device_safe_name);
							}
							if(device_safe_effect_time != null && !"".equals(device_safe_effect_time)){
								wf.setDevice_safe_effect_time(device_safe_effect_time);
							}
							if(safe_type != null && !"".equals(safe_type)){
								wf.setSafe_type(safe_type);
							}
							int res = infoFacade.insertAppSafeAreaMan(wf);
							if(res < 0){
								result = Constant.FAIL_CODE;
							}else{
								result = Constant.SUCCESS_CODE;
							}
						}
					}else if("del".equals(cmd)){
						//根据device_id和user_id查询是否是主设备
						if(!Common.isMasterDevice(user_id, device_id)){
							result = Constant.FAIL_CODE;
						}else{
							wf.setCondition(" id = "+device_safe_id);
							int res = infoFacade.deleteAppSafeAreaMan(wf);
							if(res < 0){
								result = Constant.FAIL_CODE;
							}else{
								result = Constant.SUCCESS_CODE;
							}
						}
					}else if("update".equals(cmd)){
						if(!Common.isMasterDevice(user_id, device_id)){
							result = Constant.FAIL_CODE;
						}else{
							//wf.setDevice_id(Integer.parseInt(device_id));
							if(lat != null && !"".equals(lat)){
								wf.setCenter_gps_la(Double.parseDouble(lat));
							}
							if(lon != null && !"".equals(lon)){
								wf.setCenter_gps_lo(Double.parseDouble(lon));
							}
							if(device_safe_addr != null && !"".equals(device_safe_addr)){
								wf.setCenter_addr(device_safe_addr);
							}
							if(device_safe_range != null && !"".equals(device_safe_range)){
								wf.setRound_distance(Integer.parseInt(device_safe_range));
							}
							if(device_safe_name != null && !"".equals(device_safe_name)){
								wf.setDevice_safe_name(device_safe_name);
							}
							if(device_safe_effect_time != null && !"".equals(device_safe_effect_time)){
								wf.setDevice_safe_effect_time(device_safe_effect_time);
							}
							if(safe_type != null && !"".equals(safe_type)){
								wf.setSafe_type(safe_type);
							}
							wf.setCondition(" id = "+device_safe_id);
							int res = infoFacade.updateAppSafeAreaMan(wf);
							if(res < 0){
								result = Constant.FAIL_CODE;
							}else{
								result = Constant.SUCCESS_CODE;
							}
						}
						result = Constant.SUCCESS_CODE;
					}else if("queryList".equals(cmd)){
						if(device_id != null && !"".equals(device_id)){
							wf.setCondition(" device_id =  "+device_id);
							List<DataMap> fenceList = infoFacade.queryWeFencing(wf);
							if(fenceList != null && fenceList.size() > 0){
								JSONArray jsonArr = new JSONArray();
								for(int i=0;i<fenceList.size();i++){
									DataMap fenceMap = fenceList.get(i);
									JSONObject listJson = new JSONObject();
									listJson.put("lat", fenceMap.getAt("center_gps_la"));
									listJson.put("lon", fenceMap.getAt("center_gps_lo"));
									listJson.put("device_safe_range", fenceMap.getAt("round_distance"));
									listJson.put("device_safe_name", fenceMap.getAt("device_safe_name"));
									listJson.put("device_safe_addr", fenceMap.getAt("center_addr"));
									listJson.put("device_safe_effect_time", fenceMap.getAt("device_safe_effect_time"));
									jsonArr.add(listJson);
								}
								json.put("safe_count", fenceList.size());
								json.put("safe_list", jsonArr);
								result = Constant.SUCCESS_CODE;
							}else{
								result = Constant.FAIL_CODE;
							}
						}else{
							result = Constant.FAIL_CODE;
						}
					}else{
						result = Constant.FAIL_CODE;
					}
				}else{
					result = Constant.FAIL_CODE;
				}
			}else{
				result = Constant.FAIL_CODE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = Constant.EXCEPTION_CODE;
		}
		json.put("resultCode", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	public static void main(String[] args) {
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		WeFencing wf = new WeFencing();
		wf.setDevice_safe_name("我是MT");
		wf.setDevice_safe_effect_time("123");
		wf.setSafe_type("1");
		wf.setCondition(" id = "+1);
		
		int res = infoFacade.updateAppSafeAreaMan(wf);
		System.out.println("res:"+res);
	}
}
