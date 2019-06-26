package com.wtwd.sys.innerw.wappusers.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.http.common.HttpTools;
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
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wappusers.form.WappUsersForm;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;

public class WappUsersAction extends BaseAction{
	
	Log logger = LogFactory.getLog(WappUsersAction.class);
	
	public ActionForward queryWappUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		//DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		List<DataMap> list = null;
		StringBuffer sb = new StringBuffer();//�����ַ�����
		WappUsersFacade info = ServiceBean.getInstance().getWappUsersFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			WappUsersForm form = (WappUsersForm) actionForm;
			WappUsers vo = new WappUsers(); 
			String email = request.getParameter("email");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String user_id = request.getParameter("user_id");
			String belongProject = request.getParameter("belongProject");

			/*���û������ֶ�*/
            form.setOrderBy("create_time"); 
            form.setSort("1"); 

            
			if(startTime != null && !"".equals(startTime)){				
				sb.append("substring(create_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(create_time,1,10) <= '"+endTime+"'");
			}
			if(email != null && !"".equals(email)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.email like '%" + email + "%'");
			}
			if(user_id != null && !"".equals(user_id)){				
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.user_id = '" + user_id + "'");
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("user", email);
		    request.setAttribute("user_id", user_id);
		    request.setAttribute("belongProject", belongProject);
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getWappUsers(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.size());   
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
		return mapping.findForward("queryWappUsers");
	}
	
	public ActionForward queryWappUsersCount(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//���
		PagePys pys = new PagePys();//ҳ������
		DataList list = null; //����ҳ��List  ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();//�����ַ�����
		WappUsersFacade info = ServiceBean.getInstance().getWappUsersFacade();//����userApp������ȡ��user�ֵ䣩
		try {
			WappUsersForm form = (WappUsersForm) actionForm;
			WappUsers vo = new WappUsers(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	

			/*���û������ֶ�*/
            form.setOrderBy("create_time"); 
            form.setSort("1"); 
           
			if(startTime != null && !"".equals(startTime)){				
				sb.append("substring(create_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(create_time,1,10) <= '"+endTime+"'");
			}			
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);		    
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getWappUsersGroupByTime(vo); 
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
		return mapping.findForward("queryAppUserCount");
	}

	
	public ActionForward downloadApk(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		boolean isOnLine = true;
		try {
			String downloadUrl = request.getParameter("download");
			
			String dir = request.getSession(true).getServletContext().getRealPath("/upload");
			String apkname = downloadUrl.substring(downloadUrl.lastIndexOf("/")+1);
			
			 File f = new File(dir+"/"+apkname);		 
		        if (!f.exists()) {
		            throw new SystemException("fail","noIconPath");
		        }
		        FileInputStream fin = new FileInputStream(f);
		        BufferedInputStream br = new BufferedInputStream(fin);
		        byte[] buf = new byte[1024*8];
		        int len = 0;

		        response.reset(); 
		        if (!isOnLine) { 
		            URL u = new URL("file:///" + dir);
		            response.setContentType(u.openConnection().getContentType());
		            response.setHeader("Content-Disposition", "inline; filename=" + apkname);
		            // �ļ���Ӧ�ñ����UTF-8
		        } else { // �����ط�ʽ
		            response.setContentType("application/x-msdownload");
		            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		        }
		        OutputStream out = response.getOutputStream();
		        while ((len = br.read(buf)) > 0)
		            out.write(buf, 0, len);
		        br.close();
		        out.close();
		        return null;
		}catch (Exception e) {
			e.printStackTrace();
			result.setBackPage(HttpTools.httpServletPath(request,"queryWappUsers"));
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);				
			}
		return mapping.findForward("result");
	}
	
	
}
