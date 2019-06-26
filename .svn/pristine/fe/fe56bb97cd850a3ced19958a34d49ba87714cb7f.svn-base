package com.wtwd.sys.deviceactiveinfo.action;

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
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.deviceactiveinfo.form.DeviceActiveInfoForm;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public class DeviceActiveInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(DeviceActiveInfoAction.class);
	
	public ActionForward queryDeviceActiveInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String deviceImei = request.getParameter("deviceImei");
			String serieNo = request.getParameter("serieNo");
			String phoneNumber = request.getParameter("phoneNumber");
			String belongProject = request.getParameter("belongProject");
			String userName = request.getParameter("userName");

			/*���û������ֶ�*/
            form.setOrderBy("device_update_time"); 
            form.setSort("1"); 
            
            sb.append("device_disable = '1'");
			if(startTime != null && !"".equals(startTime)){
				sb.append(" and substring(device_update_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){				
				sb.append(" and substring(device_update_time,1,10) <= '"+endTime+"'");
			}
			if(deviceImei != null && !"".equals(deviceImei)){				
				sb.append(" and device_imei='"+deviceImei+"'");
			}
			if(serieNo != null && !"".equals(serieNo)){
				sb.append(" and device_imei like '%"+serieNo+"%'");
			}
			if(phoneNumber != null && !"".equals(phoneNumber)){				
				sb.append(" and device_phone like '%"+phoneNumber+"%'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				vo.setBelongProject("d.belong_project = '"+belongProject+"'");
			}
			if(userName != null && !"".equals(userName)){
				vo.setUserName("a.user_name like'%"+userName+"%'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("deviceImei", deviceImei);
		    request.setAttribute("serieNo", serieNo);
		    request.setAttribute("phoneNumber", phoneNumber);
		    request.setAttribute("belongProject", belongProject);
		    request.setAttribute("userName", userName);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveInfoListByVo(vo); 
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			 
		} catch (Exception e) { 
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
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveInfo");
	}
	
	public ActionForward queryDeviceActiveCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");		

			/*���û������ֶ�*/
            form.setOrderBy("device_update_time"); 
            form.setSort("1"); 
          
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(device_update_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(device_update_time,1,10) <= '"+endTime+"'");
			}
			
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveInfoGroupByTime(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			 
		} catch (Exception e) { 
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
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveCount");
	}
	
	public ActionForward queryDeviceActiveHistory(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		StringBuffer sb = new StringBuffer();
		DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try {
			DeviceActiveInfoForm form = (DeviceActiveInfoForm) actionForm;
			DeviceActiveInfo vo = new DeviceActiveInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");		
			
            form.setOrderBy("date_time"); 
            form.setSort("1"); 
          
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(date_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) <= '"+endTime+"'");
			}
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getDeviceActiveHistoryListByVo(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);
			if (e instanceof SystemException) {
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else {
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDeviceActiveHistory");
	}

}
