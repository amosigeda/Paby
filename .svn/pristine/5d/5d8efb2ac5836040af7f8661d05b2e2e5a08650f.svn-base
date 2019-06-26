package com.wtwd.sys.deviceLogin.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.Config;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.CommUtils;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.deviceLogin.form.DeviceLoginForm;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public class DeviceLoginAction extends BaseAction{

	Log logger = LogFactory.getLog(DeviceLoginAction.class);
	
	public ActionForward queryDeviceLogin(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer("");
		DeviceLoginFacade deviceLogin = ServiceBean.getInstance().getDeviceLoginFacade();
		try {
			DeviceLoginForm form = (DeviceLoginForm)actionForm;
			DeviceLogin vo = new DeviceLogin();
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String belongProject = request.getParameter("belongProject");
			String status = request.getParameter("status");
			
			if(startTime == null && endTime == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String time1 = format.format(new Date());
				
				request.setAttribute("fNow_date", time1);
			    request.setAttribute("now_date", time1);
				sb.append("substring(date_time,1,10) = '" + time1 + "'");
			}
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(date_time,1,10) >= '"+startTime+"'");
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) <= '"+endTime+"'");
				request.setAttribute("now_date", endTime);
			}
			
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("device_imei like'%"+serieNo+"%'");
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("device_status ='"+status+"'");
				request.setAttribute("status", CommUtils.getStatusSelect(
						"status", Integer.parseInt(status)));
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			form.setOrderBy("id"); 
            form.setSort("1"); 
            
            BeanUtils.copyProperties(vo,form);			
            vo.setCondition(sb.toString());
         	list = deviceLogin.getDataDeviceLoginListByVo(vo); 
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
            		    
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("belongProject", belongProject);
		}catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* ����Ϊ����ҳ�棬���Գ������ת��ϵͳĬ��ҳ�� */
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		return mapping.findForward("queryDeviceLogin");
	}
	
}
