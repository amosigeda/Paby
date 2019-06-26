package com.wtwd.sys.companyinfo.action;

import java.util.Date;

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
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.Config;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.CommUtils;
import com.wtwd.sys.channelinfo.domain.ChannelInfo;
import com.wtwd.sys.companyinfo.domain.CompanyInfo;
import com.wtwd.sys.companyinfo.domain.logic.CompanyInfoFacade;
import com.wtwd.sys.companyinfo.form.CompanyInfoForm;

public class CompanyInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(CompanyInfoAction.class);
	
	public ActionForward queryCompanyInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();//结果集
		PagePys pys = new PagePys();//页面属性
		DataList list = null; //返回页面List  用logic itrate获取。
		StringBuffer sb = new StringBuffer();//创建字符串容器
		CompanyInfoFacade info = ServiceBean.getInstance().getCompanyInfoFacade();//加载userApp工厂（取得user字典）
		try {
			CompanyInfoForm form = (CompanyInfoForm) actionForm;
			CompanyInfo vo = new CompanyInfo(); 
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");
			String companyId = request.getParameter("companyId");
			String companyName = request.getParameter("companyName");
			
			/*设置化排序字段*/
            form.setOrderBy("add_time"); 
            form.setSort("1");           
			if(startTime != null && !"".equals(startTime)){
				sb.append("substring(add_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(add_time,1,10) <= '"+endTime+"'");
			}
			if(companyId != null && !"".equals(companyId)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("id='"+companyId+"'");
			}
			if(companyName != null && !"".equals(companyName)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("company_name like '%" + companyName + "%'");
			}
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("companyId", companyId);
		    request.setAttribute("companyName", companyName);
		   
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getCompanyInfoListByVo(vo);
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* 设置化排序字段 */ 
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.error(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 这里为管理页面，所以出错后跳转到系统默认页面 */
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryCompanyInfo");
	}	
	
	public ActionForward initInsert(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ChannelInfo vo = new ChannelInfo();
		DataList list = ServiceBean.getInstance().getChannelInfoFacade().getChannelInfoListByVo(vo);
		request.setAttribute("list", list);
		
		return mapping.findForward("addCompanyInfo");
	}
	
	public ActionForward insertCompanyInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String[] channelIds = HttpTools.requestArray(request, "channel");
		Result result = new Result();
		try {
			CompanyInfoForm form = (CompanyInfoForm) actionForm;  //把提交的表单封装到用户的form中
			CompanyInfoFacade facade = ServiceBean.getInstance().getCompanyInfoFacade();
			CompanyInfo vo = new CompanyInfo();
			Integer maxId = ServiceBean.getInstance().getCompanyInfoFacade().getCompanyInfoMaxId(vo);
			int num ;
			if(maxId == null){
				num = 1;
			}else{
				num = maxId + 1;
			}
			BeanUtils.copyProperties(vo, form);    //把表单信息复制到用户信息中
			vo.setId(num);
			vo.setCompanyNo(form.getCompanyNo() + num);									
			vo.setAddTime(new Date());
			vo.setStatus("");
			facade.insertCompanyInfo(vo);        //设置修改后，重新把用户信息重新映射到数据库中
			
			CompanyInfo vo2 = new CompanyInfo();
			vo2.setCompanyId(num);
			for(int i=0;i<channelIds.length; i++){
				String channelId = channelIds[i];
				vo2.setChannelId(Integer.parseInt(channelId));
				facade.insertRelevanceInfo(vo2);
			}
			result.setBackPage(HttpTools.httpServletPath(request,  //插入成功后，跳转到原先页面
					"queryCompanyInfo"));
			result.setResultCode("inserts");    //设置插入Code
			result.setResultType("success");    //设置插入成功
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"initInsert"));
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		} finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}


}
