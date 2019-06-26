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

import com.alibaba.fastjson.JSONObject;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WPetMoveInfoFacade;

/**
 * APP用户查询设备对应宠物的运动建议数据
 * @author liufeng
 * @date 2016-08-17
 * 
 */
public class WTSugMoveManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTSugMoveManAction.class);
	
	//APP版本查询	
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
			String day = request.getParameter("day");
			
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
				if(cmd == null || "".equals(cmd)){
					result = Constant.FAIL_CODE;
				}else{
					//新增 add
					if("getOneSug".equals(cmd)){
						
						WPetMoveInfo wmv = new WPetMoveInfo();
						if(device_id == null || "".equals(device_id) || day == null || "".equals(day)){
							result = Constant.FAIL_CODE;
						}else{
							wmv.setCondition(" AND ws.device_id = "+device_id+" AND DATE_FORMAT(wm.up_time,'%Y-%m-%d') = '"+day+"'");
							
							List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);
							if(list != null && list.size() > 0){
								for(int i=0;i<list.size() && i<1;i++){
									DataMap dMap = list.get(i);
									String speed = dMap.getAt("speed").toString();
									String start_time = dMap.getAt("start_time").toString();
									String end_time = dMap.getAt("end_time").toString();
									
									json.put("recMoveType", Common.getMoveType(speed));
									json.put("recMoveTime", Common.getMoveTime(end_time, start_time));
									
								}
								
							}
							
						}
						
						
					}else if("getOneSugExec".equals(cmd)){
						
						
					}else if("getSugExecList".equals(cmd)){
						
					}else if("getMoveList".equals(cmd)){
						
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
