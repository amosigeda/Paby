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
 * APP唤回声音管理
 * @author liufeng
 * @date 2016-08-17
 * http://192.168.17.224:8080/wtcell/doWTAppCallbackMan.do
 */
public class WTAppCallbackManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppCallbackManAction.class);
	
	//APP唤回声音管理	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String device_id = request.getParameter("device_id");

			if(cmd == null || "".equals(cmd)){
				result = Constant.FAIL_CODE;
			}else{
				//验证用户
				if(Common.isValidationUserInfo(user_id, app_token)){
					//验证是否主设备
					if(!Common.isMasterDevice(user_id, device_id)){
						result = Constant.FAIL_CODE;
					}else{
						WdeviceActiveInfo wa = new WdeviceActiveInfo();
						if("on".equals(cmd)){
							wa.setCallback_on("1");
						}else if("off".equals(cmd)){
							wa.setCallback_on("0");
						}
						wa.setCondition(" device_id = "+device_id);
						
						WTDevSetaFacade devFacade = ServiceBean.getInstance().getWtDevSetaFacade();
						int res = devFacade.updateDevSeta(wa);
						if(res > 0){
							result = Constant.SUCCESS_CODE;
						}else{
							result = Constant.FAIL_CODE;
						}
					}
				}else{
					result = Constant.ERR_INVALID_TOKEN;
				}
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
	
}
