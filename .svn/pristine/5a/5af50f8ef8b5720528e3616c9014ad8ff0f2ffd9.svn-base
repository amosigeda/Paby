package com.wtwd.sys.locationinfo.action;

import java.util.Date;

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
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.Config;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.CommUtils;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.wtwd.sys.locationinfo.form.LocationInfoForm;

public class LocationInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(LocationInfoAction.class);
	
	public ActionForward queryLocationInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		LocationInfoFacade info = ServiceBean.getInstance().getLocationInfoFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			LocationInfoForm form = (LocationInfoForm) actionForm;
			LocationInfo vo = new LocationInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String serieNo = request.getParameter("serieNo");
			String status = request.getParameter("status");

			/*���û������ֶ�*/
            form.setOrderBy("upload_time"); 
            form.setSort("1"); 
         
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(upload_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(upload_time,1,10) <= '"+endTime+"'");
			}
			if(serieNo != null && !"".equals(serieNo)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("serie_no like '%"+serieNo+"%'");
			}
			if(status != null && !"".equals(status)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("location_type ='"+status+"'");
				request.setAttribute("status", CommUtils.getSelect(
						"status", Integer.parseInt(status)));
			}
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("serieNo", serieNo);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getLocationInfoListByVo(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* ���û������ֶ� */ 
			 
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
		return mapping.findForward("queryLocationInfo");
	}

}
