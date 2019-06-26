package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.liufeng.domain.QuestionInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppQuestionInfoManFacade;

/**
 * 获取系统内置问题接口
 * @author liufeng
 * @date 2016-10-24
 * http://192.168.17.250:8080/wtcell/doWTAppQuestionMan.do
 */
public class WTAppQuestionManAction extends BaseAction {

	private Log log = LogFactory.getLog(WTAppQuestionManAction.class);
	JSONObject json;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		
		WTAppQuestionInfoManFacade infoFacade = ServiceBean.getInstance().getWtAppQuestionInfoManFacade();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}

			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
				if(cmd.equals("getQuestionList")){
					QuestionInfo qi = new QuestionInfo();
					
					List<DataMap> list = infoFacade.queryAllQuestionList(qi);
					if(list != null && list.size() > 0){
						JSONArray jsonArr = new JSONArray();
						for(int i=0;i<list.size();i++){
							DataMap queMap = list.get(i);
							JSONObject questionObj = JSONObject.fromObject(hashMapToJson(queMap));
							jsonArr.add(questionObj);
						}
						json.put("question_count", list.size());
						json.put("questoin_list", jsonArr);
					}
					result = Constant.SUCCESS_CODE;
				}else{
					result = Constant.FAIL_CODE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
			log.error("==WTAppQuestionManAction-->execute error:", e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, "get data exception");
		}
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		
		log.debug("==WTAppQuestionManAction in return json: ---"+json.toString());
		response.getWriter().write(json.toString());
		return null;
	}
	
	public static void main(String[] args) {
		WTAppQuestionInfoManFacade infoFacade = ServiceBean.getInstance().getWtAppQuestionInfoManFacade();
		QuestionInfo qi = new QuestionInfo();
		List<DataMap> list = infoFacade.queryAllQuestionList(qi);
		
		System.out.println("num:"+list.size());
	}
	
}
