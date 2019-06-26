package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.IOException;
import java.util.Date;

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
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSuggestionFacade;

/**
 * APP用户意见反馈
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTAppFeed.do
 */
public class WTAppFeedAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppFeedAction.class);
	
	//APP版本查询	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Tools tls = new Tools();
		
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String msg = request.getParameter("msg");
			
			
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				if(cmd == null || "".equals(cmd)){
					result = Constant.FAIL_CODE;
				}else{
					//新增 add
					if("add".equals(cmd)){
						WSuggestionFacade suggestFacade = ServiceBean.getInstance().getwSuggestionFacade();
						WSuggestion ws = new WSuggestion();
						ws.setUser_id(user_id);
						//ws.setDate_time(new Date());
						if ( Constant.timeUtcFlag )		
							ws.setDate_time(tls.getUtcDateStrNowDate() );
						else
							ws.setDate_time(new Date());		    
						
						
						
						if(msg != null && !"".equals(msg)){
							ws.setMsg(msg);
						}
						int res = suggestFacade.insertUserSuggestion(ws);
						if(res > 0){
							result = Constant.SUCCESS_CODE;
						}else{
							result = Constant.FAIL_CODE;
						}
					}
					
				}
			}else{
				result = Constant.ERR_INVALID_TOKEN;
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
