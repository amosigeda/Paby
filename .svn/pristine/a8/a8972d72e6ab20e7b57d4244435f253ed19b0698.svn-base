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
import com.wtwd.sys.innerw.liufeng.domain.logic.WTCheckVersionFacade;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;

/**
 * APP版本查询
 * @author liufeng
 * @date 2016-08-15
 * http://192.168.17.224:8080/wtcell/doWTDownload.do
 */
public class WTDownloadAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTDownloadAction.class);
	
	//APP版本查询	doWTDownload	execute
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		
		WTCheckVersionFacade infoFacade = ServiceBean.getInstance().getWtCheckVersionFacade();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String package_name = request.getParameter("package_name");
			
			WcheckInfo wi = new WcheckInfo();
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				//验证是否主设备
				if(package_name != null && !"".equals(package_name)){
					wi.setCondition(" package_name = '"+package_name+"'");
					
					List<DataMap> list = infoFacade.queryCheckVersionInfo(wi);
					if(list != null && list.size() > 0){
						for(int i=0;i<list.size();i++){
							DataMap versionMap = list.get(i);
							String version_code = (String) versionMap.getAt("version_code");
							String download_path = (String) versionMap.getAt("download_path");
							json.put("version_code", version_code);
							json.put("download_path", download_path);
						}
						result = Constant.SUCCESS_CODE;
					}else{
						result = Constant.FAIL_CODE;
					}
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
