﻿package com.wtwd.sys.appinterfaces.liufeng.action;

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
import com.wtwd.sys.innerw.liufeng.domain.WappLocationInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserUploadLocationFacade;

/**
 * APP用户上传位置数据
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppUserGpsMan.do
 */
public class WTAppUserGpsManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppUserGpsManAction.class);
	
	//execute		doWTAppUserGpsMan
	public ActionForward execute(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		WappLocationInfo wi = new WappLocationInfo();
		AppUserUploadLocationFacade infoFacade = ServiceBean.getInstance().getAppUserUploadLocationFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String lon = request.getParameter("lon");
			String lat = request.getParameter("lat");
			String acc = request.getParameter("acc");
			
			//cmd暂时未做判断
			if(Common.isValidationUserInfo(user_id, app_token)){
				//上传用户位置信息
//				StringBuffer sb = new StringBuffer();
//				sb.append("");
				wi.setUser_id(Integer.parseInt(user_id));
				if(lon != null && !"".equals(lon)){
					wi.setLongitude(lon);
				}
				if(lat != null && !"".equals(lat)){
					wi.setLatitude(lat);
				}
				if(acc != null && !"".equals(acc)){
					wi.setAccuracy(Integer.parseInt(acc));
				}
				int res = infoFacade.insertUserLocationInfo(wi);
				log.debug("res:"+res);
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