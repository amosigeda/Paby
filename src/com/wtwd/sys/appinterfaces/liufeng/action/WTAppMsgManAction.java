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
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppMsgManFacade;

/**
 * APP消息获取
 * @author liufeng
 * @date 2016-08-19
 * http://192.168.17.224:8080/wtcell/doWTAppMsgMan.do
 */
public class WTAppMsgManAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTAppMsgManAction.class);
	
	//doWTAppMsgMan	execute
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String time_stamp = request.getParameter("time_stamp");
			String from = request.getParameter("from");
			String pageSize = request.getParameter("pageSize");
			
			WMsgInfo wm = new WMsgInfo();
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				StringBuffer sb = new StringBuffer();
				sb.append(" to_usrid = "+user_id);
				
				if(time_stamp != null && !"".equals(time_stamp)){
					sb.append(" AND DATE_FORMAT(msg_date,'%Y-%m-%d %H:%i:%s') > '"+time_stamp+"'");
				}
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
				wm.setCondition(sb.toString());
				int listCount = infoFacade.queryByUserIdMsgCount(wm);
				List<DataMap> list = infoFacade.queryByUserIdMsgInfo(wm);
				if(list != null && list.size() > 0){
					JSONArray jsonArr = new JSONArray();
					for(int i=0;i<list.size();i++){
						DataMap msgMap = list.get(i);
						JSONObject jsonMsg = new JSONObject();
						JSONObject jsonMsgContent = new JSONObject();
						String msg_id = msgMap.getAt("msg_id").toString();
						String msg_type = msgMap.getAt("msg_type").toString();
						String msg_ind_id = msgMap.getAt("msg_ind_id").toString();
						String msg_date = msgMap.getAt("msg_date").toString();
						String msg_content = msgMap.getAt("msg_content").toString();
						String device_id = msgMap.getAt("device_id").toString();
						String from_usrid = msgMap.getAt("from_usrid").toString();
						String to_usrid = msgMap.getAt("to_usrid").toString();
						String fence_id = msgMap.getAt("fence_id").toString();
						
						jsonMsg.put("msg_id", msg_id);
						jsonMsg.put("msg_type", msg_type);
						jsonMsg.put("msg_ind_id", msg_ind_id);
						jsonMsg.put("msg_date", msg_date);
						
						jsonMsgContent.put("device_id", device_id);
						jsonMsgContent.put("from_usrid", from_usrid);
						jsonMsgContent.put("to_usrid", to_usrid);
						jsonMsgContent.put("eference_id", fence_id);
						jsonMsgContent.put("msg_txt", msg_content);
						jsonMsg.put("msg_content", jsonMsgContent);
						jsonArr.add(jsonMsg);
						if(i == 0){
							json.put("time_stamp", msg_date);
						}
					}
					result = Constant.SUCCESS_CODE;
					json.put("tot_count", listCount);
					json.put("rec_count", list.size());
					json.put("data_list", jsonArr);
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
	
}