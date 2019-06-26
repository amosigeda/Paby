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
import com.wtwd.sys.innerw.liufeng.domain.logic.AppDeviceDiscoveryManFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;

/**
 * APP发现模式管理
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppDiscoveryMan.do
 */
public class WTAppDiscoveryManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppDiscoveryManAction.class);
	
	//doWTAppDiscoveryMan	execute
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		AppDeviceDiscoveryManFacade infoFacade = ServiceBean.getInstance().getAppDeviceDiscoveryManFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String device_id = request.getParameter("device_id");

			WdeviceActiveInfo wa = new WdeviceActiveInfo();
			
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				//验证是否主设备
				if(Common.isMasterDevice(user_id, device_id)){
					if(cmd != null && !"".equals(cmd)){
						if(cmd.equals("on")){
							wa.setSel_mode("3");
						}else if(cmd.equals("off")){
							wa.setSel_mode("1");
						}else{
							result = Constant.FAIL_CODE;
						}
						wa.setCondition(" device_id = "+device_id);
						int res = infoFacade.updateAppDeviceDiscoveryMan(wa);
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
