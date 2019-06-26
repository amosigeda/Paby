package com.wtwd.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class queryExpireDateAction extends BaseAction {

	Log logger = LogFactory.getLog(payforAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		//Date start = new Date();
		JSONObject json = new JSONObject();
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
			logger.info("queryExpireDateAction accept`s parameters is" + sb.toString());
			String imei = object.getString("imei");

			
			// String customerId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("imei='" + imei
					+ "'and pay_status='200' and sub_status='1' order by id desc limit 1");

			List<DataMap> listSer = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getPayForInfo(vo);
		
			json.put("imei", imei);
			try {
				int size = listSer.size();
				if (size > 0) {
					String frequency = listSer.get(0).get("description") + "";
					String createtime=listSer.get(0).get("create_time")+"";
					json.put("frequency", frequency);
					json.put("create_time", createtime);
					vo.setCondition("imei='" + imei+ "'and card_status='200' order by id desc limit 1");
					List<DataMap> listCancel = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);
					if(listCancel.size()>0){
						json.put("stop_time",listCancel.get(0).get("stop_time")+"");
					}else{
						json.put("stop_time","");
					}
				}

			} catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
			}
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

	/*
	 * public static void main(String[] args) { String abc="2017-12-03";
	 * 
	 * 
	 * System.out.println(abc.substring(8, 10)); int year=new
	 * Date().getYear()+1900; System.out.println(year); int month=new
	 * Date().getMonth()+1; String monthString=""; if(month>12){ year=year+1;
	 * month=month-12; } System.out.println(month); if(month<10){
	 * monthString="0"+month; } String
	 * ss=year+"-"+monthString+"-"+abc.substring(8, 10); System.out.println(ss);
	 * }
	 */
}