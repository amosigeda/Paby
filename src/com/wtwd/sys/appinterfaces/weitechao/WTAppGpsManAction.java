﻿package com.wtwd.sys.appinterfaces.weitechao;

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

import com.alibaba.fastjson.JSONArray;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoFacade;

public class WTAppGpsManAction extends BaseAction {

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
			logger.info("WTAppGpsManAction收到的消息为 =" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			// String cmd = object.optString("cmd");

			int user_id = object.optInt("user_id");

			String app_token = object.optString("app_token");

			if ((result = verifyUserId(String.valueOf(user_id))) == Constant.SUCCESS_CODE) {
				if ((result = verifyAppToken(String.valueOf(user_id), app_token)) == Constant.SUCCESS_CODE) {
					// super.logAction(String.valueOf(user_id),object.optInt("device_id"),
					// "WTAppGpsManAction:" + cmd);
					// 增加日志
					// String app_token = Tools.getSafeStringFromJson(object,
					// "app_token");

					LocationInfo vo = new LocationInfo();
					LocationInfoFacade fd = ServiceBean.getInstance()
							.getLocationInfoFacade();
					vo.setCondition("w.user_id='" + user_id + "' AND w.status='1'");
					List<DataMap> list = fd.SelectIccidInfo(vo);

					JSONArray jsonArr = new JSONArray();
					if (list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							String imei = list.get(i).get("device_imei") + "";
							logger.info("imei=" + imei);
							JSONObject json1 = new JSONObject();
							if (!"".equals(imei) && imei != null
									&& !imei.equals("null") && imei != "") {
								String deviceName = list.get(i).get("nickname") + "";
								String photo = list.get(i).get("photo") + "";
								String iccid = list.get(i).get("iccid") + "";
								String testStatus = list.get(i).get("test_status") + "";
								if (!"".equals(iccid) && iccid != null
										&& !iccid.equals("null") && iccid != "") {

									DeviceActiveInfo dvo = new DeviceActiveInfo();
									dvo.setCondition("iccid='" + iccid + "'");
									List<DataMap> listS = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(dvo);
									if (listS.size() > 0) {
										json1.put("test_status", testStatus);
										json1.put("device_name", deviceName);
										json1.put("photo", photo);
										json1.put("imei", imei);
										json1.put("iccid", iccid);
										String simStatus = listS.get(0).get("card_status") + "";
										logger.info("simStatus=" + simStatus);
										json1.put("card_status", simStatus);
										json1.put("imsi", listS.get(0).get("imsi") + "");
										json1.put("belong_company", listS.get(0).get("belong_company") + "");

										dvo.setCondition("iccid='" + iccid
												+ "'and pay_status='200' order by id desc");

										List<DataMap> listPayForInfo = ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.getPayForInfo(dvo);
										if (listPayForInfo.size() > 0) {
											String payTime = listPayForInfo.get(0).get("create_time") + "";
											json1.put("pay_time", payTime.substring(0, 10));
											json1.put("plan", listPayForInfo.get(0).get("description") + "");
											json1.put("count", listPayForInfo.get(0).get("plan_count") + "");
											json1.put("sub_status",listPayForInfo.get(0).get("sub_status") + "");

											DeviceActiveInfo voo = new DeviceActiveInfo();

											voo.setCondition("iccid ='" + iccid
													+ "' and card_status='200' and message='ok'  ORDER BY id DESC limit 1");
											List<DataMap> cancelList = ServiceBean.getInstance()
													.getDeviceActiveInfoFacade()
													.getcancleImeiInfo(voo);
											if (cancelList.size() > 0) {
												SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
												//String timeStop=sdf.format(new Date());
												String stopTime=cancelList.get(0).get("stop_time") + "";
												
												//Date dt1 = sdf.parse(timeStop);//今天的时间
												//Date dt2 = sdf.parse(stopTime);//到期的时间
												
												Date dt1= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期
												Date dt2= (Date)sdf.parseObject(stopTime);//设置初始日期
												
												if(dt1.getTime() >= dt2.getTime()){
													json1.put("stop_time", "");
												}else{
													json1.put("stop_time", stopTime);
												}
												
												if ("1".equals(testStatus)) {
													json1.put("member_status", "3");
												} else {
													if ("0".equals(simStatus)) {
														json1.put("member_status", "1");
													} else {
														json1.put("member_status", "2");
													}
												}
											}
										} else {
											json1.put("pay_time", "");
											json1.put("plan", "");
											json1.put("count", "");
											json1.put("sub_status", "");
											json1.put("stop_time", "");
											if ("1".equals(testStatus)) {
												json1.put("member_status", "3");
											} else {
												json1.put("member_status", "1");
											}
										}
									} else {
										if ("1".equals(testStatus)) {
											json1.put("member_status", "3");
										} else {
											json1.put("member_status", "1");
										}
										json1.put("test_status", testStatus);
										json1.put("device_name", deviceName);
										json1.put("photo", photo);
										json1.put("imei", imei);
										json1.put("iccid", iccid);
										json1.put("card_status", "3");
									}
								} else {
									if ("1".equals(testStatus)) {
										json1.put("member_status", "3");
									} else {
										json1.put("member_status", "1");
									}
									json1.put("test_status", testStatus);
									json1.put("device_name", deviceName);
									json1.put("photo", photo);
									json1.put("imei", imei);
									json1.put("iccid", "");
									json1.put("card_status", "2");
								}
								System.out.println(i);
								jsonArr.add(i, json1);
							}
						}
						result = Constant.SUCCESS_CODE;
						json.put("iccid_list", jsonArr);
					} else {
						result = Constant.SUCCESS_CODE;
						json.put("iccid_list", jsonArr);
					}
				}
			}
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
		logger.info("WTAppGpsManAction result=" + json.toString());
		response.getWriter().write(json.toString());

		return null;

	}
}
