package com.wtwd.sys.appinterfaces.weitechao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class validIccidAction extends BaseAction {

	Log logger = LogFactory.getLog(validIccidAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			
			String iccid = object.getString("iccid");	
			String userId = object.getString("user_id");	
			String appToken = object.getString("app_token");
			String imei=object.getString("imei");

			
			if (iccid.length() > 0 && iccid != null
					&& !"".equals(iccid)) {

				DeviceActiveInfo vo1 = new DeviceActiveInfo();
				vo1.setCondition("iccid ='" + iccid
						+ "' limit 1");
				String sid = "";
				List<DataMap> list = ServiceBean
						.getInstance()
						.getDeviceActiveInfoFacade()
						.getSsidInfo(vo1);

				if (list.size() <= 0) {
					json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
					json.put("msg","不是我们公司的卡");
				} else {
					String belongCompany=list.get(0).get("belong_company")+"";
				
					vo1.setCondition("iccid ='" + iccid + "' limit 1");
					List<DataMap> lsitimei = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo1);
					if(lsitimei.size()<=0){
						vo1.setCondition("device_imei ='" + imei+"'");
						vo1.setIccid(iccid);
						vo1.setBelongCompany(belongCompany);
						
						ServiceBean.getInstance()
						.getDeviceActiveInfoFacade()
						.updateDeviceActiveInfo(vo1);
						
						json.put("imsi", list.get(0).get("imsi")+"");
						json.put("belong_company",belongCompany );
						json.put("card_status", list.get(0).get("card_status")+"");
						json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
						json.put("imei","");
						json.put("msg","和设备绑定成功");
					}else{
						json.put("imsi", list.get(0).get("imsi")+"");
						json.put("belong_company",belongCompany );
						json.put("card_status", list.get(0).get("card_status")+"");
						json.put(Constant.RESULTCODE, Constant.INVALID_DATA);
						json.put("imei", lsitimei.get(0).get("device_imei")+"");
						json.put("msg","已和设备绑定过");
					}
				}
			} else {
				json.put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);
				logger.info("iccid为空");
				result = 2;
				json.put("card_status", "未找到对应的iccid");
			}
		
			json.put("iccid", iccid);
			
			json.put("request", href);
		} catch (Exception e) {
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
			json.put(Constant.EXCEPTION, sb.toString());
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	public static void main(String[] args) {
		DeviceActiveInfo vo1 = new DeviceActiveInfo();
		String imei="352138064953575";
		vo1.setCondition("device_imei ='" + imei+"'");
		vo1.setIccid("1");
		vo1.setBelongCompany("1");
		
		try {
			ServiceBean.getInstance()
			.getDeviceActiveInfoFacade()
			.updateDeviceActiveInfo(vo1);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
