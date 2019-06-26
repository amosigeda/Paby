package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTDevSetaFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

/**
 * APP设备远程数据设置
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTDevSeta.do
 */
public class WTDevSetaAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTDevSetaAction.class);
	
	//execute doWTDevSeta
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		
		WTDevSetaFacade infoFacade = ServiceBean.getInstance().getWtDevSetaFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String device_id = request.getParameter("device_id");
			
			String device_data_mute = request.getParameter("device_data_mute");
			String device_data_volume = request.getParameter("device_data_volume");
			String device_data_power = request.getParameter("device_data_power");
			String device_data_light = request.getParameter("device_data_light");

			WdeviceActiveInfo wa = new WdeviceActiveInfo();
			
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				//验证是否主设备
				if(Common.isMasterDevice(user_id, device_id)){
					if(device_data_mute != null && !"".equals(device_data_mute)){
						wa.setData_mute(device_data_mute);
					}
					if(device_data_volume != null && !"".equals(device_data_volume)){
						wa.setData_volume(device_data_volume);
					}
					if(device_data_power != null && !"".equals(device_data_power)){
						wa.setData_power(device_data_power);
					}
					if(device_data_light != null && !"".equals(device_data_light)){
						wa.setLed_on(device_data_light);
					}
					if(device_id != null && !"".equals(device_id)){
						wa.setCondition(" device_id = "+device_id);
					}
					int res = infoFacade.updateDevSeta(wa);
					if(res < 0){
						result = Constant.FAIL_CODE;
					}else{
						result = Constant.SUCCESS_CODE;
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
		
//		int res = infoFacade.updateAppSafeAreaMan(wf);
//		System.out.println("res:"+res);
	}
}
