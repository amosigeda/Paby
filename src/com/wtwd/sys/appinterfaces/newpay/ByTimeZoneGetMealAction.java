﻿package com.wtwd.sys.appinterfaces.newpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;

public class ByTimeZoneGetMealAction extends BaseAction {

	JSONObject json = new JSONObject();

	Log logger = LogFactory.getLog(WTAppGpsManAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
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
			// String cmd = object.optString("cmd");

		//	int user_id = object.optInt("user_id");

			//String app_token = object.optString("app_token");
			String timeZone=object.optString("time_zone");
/*
			if ((result = verifyUserId(String.valueOf(user_id))) == Constant.SUCCESS_CODE) {
				if ((result = verifyAppToken(String.valueOf(user_id), app_token)) == Constant.SUCCESS_CODE) {*/
					// super.logAction(String.valueOf(user_id),object.optInt("device_id"),
					// "WTAppGpsManAction:" + cmd);
					// 增加日志
					// String app_token = Tools.getSafeStringFromJson(object,
					// "app_token");

					/*LocationInfo vo = new LocationInfo();
					LocationInfoFacade fd = ServiceBean.getInstance()
							.getLocationInfoFacade();
					vo.setCondition("w.user_id='" + user_id + "' AND w.status='1'");
					List<DataMap> list = fd.SelectIccidInfo(vo);*/

					/*JSONArray jsonArr = new JSONArray();*/
		
			
			json.put("year", Constant.T_ONTH+"");
					json.put("year_month", Constant.tprice);
					
					json.put("six", Constant.S_ONTH);
					json.put("six_month", Constant.sprice);
					
					json.put("one_month", Constant.O_ONTH);
					result=Constant.SUCCESS_CODE;
					
			/*	}
			}*/
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

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

		return null;

	}
}