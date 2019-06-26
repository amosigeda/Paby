package com.wtwd.sys.userinfo.action;

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
import com.godoing.rose.lang.MD5;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.app.LoginUser;
import com.wtwd.common.config.Config;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.CommUtils;
import com.wtwd.sys.roleinfo.domain.RoleInfo;
import com.wtwd.sys.userinfo.domain.UserInfo;
import com.wtwd.sys.userinfo.domain.logic.UserInfoFacade;
import com.wtwd.sys.userinfo.form.UserInfoForm;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class UserInfoAction extends BaseAction {
	Log logger = LogFactory.getLog(UserInfoAction.class);

	public ActionForward queryUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
		String groupCode = loginUser.getGroupCode();
		String userCode = loginUser.getUserCode();
		String href= request.getServletPath();
		Date start = new Date();		
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		UserInfoFacade info = ServiceBean.getInstance().getUserInfoFacade();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			request.setAttribute("e",form.getUserCode());
			request.setAttribute("e2",form.getUserName());
			/* ��ʵ�б�session���ٹ��� */
			List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade()
		    	.getRoleInfo(new RoleInfo());
			request.getSession().setAttribute("roleList", rlist);

			/* ���ó�ʼ��������� */
			form.setOrderBy("groupCode");
			form.setSort("1");
//			if (form.getOrderBy() == null) {
//				form.setOrderBy("createDate");
//				form.setSort("1");
//			}

			UserInfo vo = new UserInfo();
//			RoleInfo roleInfo = new RoleInfo();
			//roleInfo.setCondition("roleType = '��Ӫ��ɫ'");
//			rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleInfo);
//			String condition = "'admin'";
//			for(int i = 0; i < rlist.size(); i++) {
//				condition = condition.concat(",'"+(String)rlist.get(i).getAt("roleCode")+"'");
//			}
//			vo.setCondition(" groupCode  in("+condition+")");
			BeanUtils.copyProperties(vo, form);
			if("����Ա".equals(groupCode)){
				vo.setCondition("userCode ='" + userCode + "'");
			}else if("����Ա".equals(groupCode)){
				vo.setCondition("userCode ='" + userCode +"' OR addUser ='"+ userCode+"'");
			}
			list = info.getDataUserInfoListByVo(vo);
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
		return mapping.findForward("queryUserInfo");
	}

	public ActionForward initInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String groupCode = request.getParameter("groupCode");
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER);
		String roleName = loginUser.getGroupCode();
		// ����ĳ�ȡ��ɫ
		RoleInfo roleinfo = new RoleInfo();
		if("����Ա".equals(roleName)){
			roleinfo.setCondition("roleName !='��������Ա' and roleName !='����Ա'");
		}else if("��������Ա".equals(roleName)){
			roleinfo.setCondition("roleName !='��������Ա'");
		}
		//roleinfo.setCondition("roleType not in('���ý�ɫ')");
		List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleinfo);
			
		request.setAttribute("roleList", CommUtils.getPrintSelect(rlist,"groupCode", "roleName", "roleCode", groupCode, 1));
		
		return mapping.findForward("insertUserInfo");
	}

	public void validateInsert(UserInfoForm form) throws SystemException {
		UserInfo vo = new UserInfo();
		/* ��ݺϷ�����֤ */	
//		vo.setCondition("roleType = 'system' ");
		vo.setCondition("userCode = '"+form.getUserCode()+"'");
		if (ServiceBean.getInstance().getUserInfoFacade().getUserInfo(
				vo).size() > 0) {
			throw new SystemException("fail", "userCodeError");
		}
	}

	public ActionForward insertUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;  //���ύ�ı?��װ���û���form��
			validateInsert(form);    //��֤��������Ƿ�Ϸ�
			UserInfo vo = new UserInfo();
			
			BeanUtils.copyProperties(vo, form);    //�ѱ?��Ϣ���Ƶ��û���Ϣ��
			vo.setCreateDate(new Date());          //���ô���ʱ��͸���ʱ��
			vo.setUpdateDate(new Date());
			vo.setUserName(form.getUserCode());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd())); //���ü��ܵ�����
			vo.setCode("admin");
			ServiceBean.getInstance().getUserInfoFacade()
					.insertUserInfo(vo);         //�����޸ĺ����°��û���Ϣ����ӳ�䵽��ݿ���
			result.setBackPage(HttpTools.httpServletPath(request,  //����ɹ�����ת��ԭ��ҳ��
					"queryUserInfo"));
			result.setResultCode("inserts");    //���ò���Code
			result.setResultType("success");    //���ò���ɹ�
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"initInsert"));
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

	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String code = request.getParameter("userCode");
		UserInfo vo = new UserInfo();
		/* ������Ҫ����vo��ѯ���� һ��Ϊ�� vo.setId(new Integer(row[0])); */
		vo.setCondition("userCode ='"+code+"'");
		List<DataMap> list = ServiceBean.getInstance().getUserInfoFacade()
				.getUserInfo(vo);      //��ȡ����Ϣ��List����
		if (list == null || list.size() == 0) { /* ������ݱ�ɾ��ʱ����,��ͨ����²��ᷢ�� */
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("userInfo", list.get(0));   //�ѻ�ȡ����Ϣ��userInfo�ַ������,����תgetSession�л�ȡuserInfo�е�����ֵ

		/*  */
		String groupCode = request.getParameter("groupCode");
		// ����ĳ�ȡ��ɫ
		List<DataMap> rlist = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(
				new RoleInfo());
		request.setAttribute("roleList", CommUtils.getPrintSelect(rlist,
				"groupCode", "roleName", "roleCode", groupCode, 0));

		return mapping.findForward("updateUserInfo");
	}

	public void validateUpdate(UserInfoForm form) throws SystemException {
		//UserInfo testvo = new UserInfo();
		/* ��ݺϷ�����֤ */
	}

	public ActionForward updateUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			validateUpdate(form);
			UserInfo vo = new UserInfo();
			vo.setCondition("id ='"+form.getId()+"'");
			BeanUtils.copyProperties(vo, form);   //��form���Ƶ�vo
			vo.setTag(1);
			vo.setUpdateDate(new Date());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd()));
			ServiceBean.getInstance().getUserInfoFacade()
					.updateUserInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
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

	public ActionForward initUpdatePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] row = HttpTools.requestArray(request, "crow");
		UserInfo vo = new UserInfo();
		vo.setCondition("id ='"+row[0]+"'");
		request.setAttribute("id", row[0]);
		return mapping.findForward("updateUserPwdInfo");
	}

	public ActionForward updateUserPwdInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		Result result = new Result();
		try {
			UserInfoForm form = (UserInfoForm) actionForm;
			validateUpdate(form);
			UserInfo vo = new UserInfo();
			vo.setCondition("id ='"+form.getId()+"'");
			BeanUtils.copyProperties(vo, form);
			vo.setUpdateDate(new Date());
			vo.setPassWrd1(vo.getPassWrd());
			vo.setPassWrd(MD5.MD5(vo.getPassWrd()));
			ServiceBean.getInstance().getUserInfoFacade()
					.updateUserInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
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

	public ActionForward deleteUserInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		try {
			String userCode = request.getParameter("userCode");
			UserInfo vo = new UserInfo();
				vo.setCondition("userCode ='"+userCode+"'");            //�����û��˻�
				ServiceBean.getInstance().getUserInfoFacade()
						.deleteUserInfo(vo);   //��bean��ɾ���û�
			
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,
					"queryUserInfo"));
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
