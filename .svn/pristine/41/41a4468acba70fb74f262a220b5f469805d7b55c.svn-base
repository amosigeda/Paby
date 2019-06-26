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
import com.wtwd.sys.innerw.liufeng.domain.WappWiFiInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserWiFiInfoFacade;

/**
 * APP主设备WIFI源信息设置
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppDevWifiSrcMan.do
 */
public class WTAppDevWifiSrcManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppDevWifiSrcManAction.class);
	
	//doWTAppDevWifiSrcMan
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		WappWiFiInfo wi = new WappWiFiInfo();
		AppUserWiFiInfoFacade infoFacade = ServiceBean.getInstance().getAppUserWiFiInfoFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String ssid = request.getParameter("ssid");
			String pwd = request.getParameter("pwd");
			
			//cmd暂时未做判断
			if(Common.isValidationUserInfo(user_id, app_token)){
				//上传用户位置信息
				wi.setUser_id(Integer.parseInt(user_id));
				if(ssid != null && !"".equals(ssid)){
					wi.setSsid(ssid);
				}
				if(pwd != null && !"".equals(pwd)){
					wi.setPwd(pwd);
				}
				int res = infoFacade.insertAppWiFiInfo(wi);
				if(res > 0){
					result = Constant.SUCCESS_CODE;
				}
			}else{
				result = Constant.FAIL_CODE; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = Constant.EXCEPTION_CODE;
		}
		JSONObject json = new JSONObject();
		json.put("resultCode", result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
}
