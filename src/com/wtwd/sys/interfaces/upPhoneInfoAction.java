package com.wtwd.sys.interfaces;
/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceLogin.domain.DeviceLogin;
import com.wtwd.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;


public class upPhoneInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(upPhoneInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		JSONObject json = new JSONObject();
		String href= request.getServletPath();
		try{			
			
			String serieNo = request.getParameter("serie_no");
			String productModel = request.getParameter("device_product_model");
			String firmwareEdition = request.getParameter("device_firmware_edition");
			String phone = request.getParameter("device_phone");
			String belongProject = request.getParameter("b_g");
			DeviceLogin deviceLogin = new DeviceLogin();
			
			PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
			DeviceLoginFacade deviceLoginFacade = ServiceBean.getInstance().getDeviceLoginFacade();
			
			if(serieNo != null && !serieNo.equals("") && productModel != null && !productModel.equals("")
					&& firmwareEdition != null && !firmwareEdition.equals("") && phone != null && !phone.equals("")){
				PhoneInfo vo = new PhoneInfo();
				StringBuffer sb = new StringBuffer();
				if(serieNo != null && !"".equals(serieNo)){
					sb.append("serie_no = '"+serieNo+"'");
				}
				if(belongProject != null && !"".equals(belongProject)){
					if(sb.length() > 0){
						sb.append(" and ");
					}
					sb.append("belong_project='"+belongProject+"'");
				}
				vo.setCondition(sb.toString());
				
				vo.setSerieNo(serieNo);
				vo.setBelongProject(belongProject);
				vo.setProductModel(productModel);
				vo.setFirmwareEdition(firmwareEdition);
				vo.setPhone(phone);
				vo.setBelongProject(belongProject);
				List<DataMap> list = facade.getPhoneInfo(vo);
				String phone_string = "13524854512";
				String status = "1";
				if(list.size() <= 0){
					vo.setInputTime(new Date());
					vo.setStatus("1");
					facade.insertPhoneInfo(vo);
				}else{
					phone_string = ""+ list.get(0).getAt("phone");
					status = ""+ list.get(0).getAt("status");
				}
				
				deviceLogin.setBelongProject(belongProject);
				deviceLogin.setDateTime(new Date());
				deviceLogin.setDeivceImei(serieNo);
				deviceLogin.setDevicePhone(phone_string);
				deviceLogin.setDeviceVersion(firmwareEdition);
				deviceLogin.setDeviceStatus(status);
				deviceLoginFacade.insertDeviceLogin(deviceLogin);
				
				result = Constant.SUCCESS_CODE;
			}					
			
			insertVisit(href, belongProject, serieNo, 1,start,new Date());
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
//			json.put("result", -1);
		}
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		json.put("d_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
