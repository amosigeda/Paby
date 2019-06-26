package com.wtwd.sys.projectinfo.action;

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
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.companyinfo.domain.CompanyInfo;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;
import com.wtwd.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.wtwd.sys.projectinfo.form.ProjectInfoForm;

public class ProjectInfoAction extends BaseAction {

	Log logger = LogFactory.getLog(ProjectInfoAction.class);

	public ActionForward queryProjectInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href = request.getServletPath();
		Date start = new Date();
		Result result = new Result();// ���
		PagePys pys = new PagePys();// ҳ������
		DataList list = null; // ����ҳ��List ��logic itrate��ȡ��
		StringBuffer sb = new StringBuffer();// �����ַ�����
		ProjectInfoFacade info = ServiceBean.getInstance()
				.getProjectInfoFacade();// ����userApp������ȡ��user�ֵ䣩
		try {
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			ProjectInfo vo = new ProjectInfo();
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String companyId = request.getParameter("companyId");
			String projectName = request.getParameter("projectName");
			String userId = request.getParameter("userId");

			/* ���û������ֶ� */
			form.setOrderBy("add_time");
			form.setSort("1");
			if (startTime != null && !"".equals(startTime)) {
				sb.append("substring(add_time,1,10) >= '" + startTime + "'");
			}
			if (endTime != null && !"".equals(endTime)) {
				if (sb.length() > 0) {
					sb.append(" and ");
				}
				sb.append("substring(add_time,1,10) <= '" + endTime + "'");
			}
			if(companyId != null && !"".equals(companyId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("company_id='"+companyId+"'");
			}
			if(projectName != null && !"".equals(projectName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("project_name like '%" + projectName + "%'");
			}
			if(userId != null && !"".equals(userId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("company_id='"+userId+"'");
			}

			request.setAttribute("fNow_date", startTime);
			request.setAttribute("now_date", endTime);
			request.setAttribute("companyId", companyId);
			request.setAttribute("projectName", projectName);
			request.setAttribute("userId", userId);

			vo.setCondition(sb.toString());

			BeanUtils.copyProperties(vo, form);
			list = info.getProjectInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form);
			pys.setCounts(list.getTotalSize());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /*
													 * ����Ϊ����ҳ�棬���Գ������ת��ϵ
													 * ͳĬ��ҳ��
													 */
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
		return mapping.findForward("queryProjectInfo");
	}

	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String company_name = request.getParameter("company_name");
		CompanyInfo vo = new CompanyInfo();
		List<DataMap> Clist = ServiceBean.getInstance().getCompanyInfoFacade()
				.getCompanyInfo(vo);
		request.setAttribute("companyList", CommUtils
				.getPrintSelect(Clist, "companyName", "company_name",
						"id", company_name, 1));


		return mapping.findForward("addProjectInfo");
	}

	public ActionForward insertProjectInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		
		String companyId = request.getParameter("companyName");
//		String channelIds = request.getParameter("channel_name");

		Result result = new Result();
		try {
			
			ProjectInfoForm form = (ProjectInfoForm) actionForm;
			ProjectInfoFacade facade = ServiceBean.getInstance()
					.getProjectInfoFacade();
			ProjectInfo vo = new ProjectInfo();
			int num = ServiceBean.getInstance().getProjectInfoFacade()
					.getProjectInfoCount(vo) + 1;
			BeanUtils.copyProperties(vo, form);
			vo.setId(num);
			vo.setProjectNo(form.getProjectNo() + Constant.SPLITE + num);
			vo.setCompanyId(Integer.parseInt(companyId));
			vo.setAddTime(new Date());
			vo.setStatus("");
			facade.insertProjectInfo(vo);

			result.setBackPage(HttpTools.httpServletPath(request, // ����ɹ�����ת��ԭ��ҳ��
					"queryProjectInfo"));
			result.setResultCode("inserts"); // ���ò���Code
			result.setResultType("success"); // ���ò���ɹ�
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request, "initInsert"));
			if (e instanceof SystemException) { /* ����֪�쳣���н��� */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* ��δ֪�쳣���н�������ȫ�������δ֪�쳣 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

}
