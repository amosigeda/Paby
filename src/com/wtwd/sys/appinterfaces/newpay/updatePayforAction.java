package com.wtwd.sys.appinterfaces.newpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class updatePayforAction extends BaseAction {

	Log logger = LogFactory.getLog(updatePayforAction.class);

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
			String imei = object.getString("imei");//这里要改为iccid
			String token = object.getString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.getString("frequency");//

			String count = object.getString("count");

			// String email = object.getString("email");
			String userId = object.getString("user_id");
			String userToken = object.getString("user_token");

			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			String customerId = "";
			String subscriptionId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			int receive = 0;
			int IntegerFreQuency = Integer.valueOf(frequency);
			String email="";
			try {
				
				AppUserInfo uo = new AppUserInfo();
				uo.setCondition("user_id='" + userId + "'");
				List<DataMap> AppUserList = ServiceBean.getInstance()
						.getAppUserInfoFacade().getAppUserInfo(uo);
				if (AppUserList.size() > 0) {
					email = AppUserList.get(0).get("email") + "";
				}
				
				// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
				// 下面的key为Test key
				Stripe.apiKey = Constant.STRIPE_APIKEY;

				Map<String, Object> customerParams = new HashMap<String, Object>();
				customerParams.put("email", email);
				customerParams.put("source", token);
				Customer customer = Customer.create(customerParams);
				customerId = customer.getId();

				// 0表示从自动改为一次性的 1表示从一次性改为自动扣的
				if ("0".equals(count)) {
					Map<String, Object> chargeParams = new HashMap<String, Object>();
					chargeParams.put("amount", 500 * IntegerFreQuency);// 美分
					chargeParams.put("currency", "usd");
					chargeParams.put("customer", customerId);
					Charge charge = Charge.create(chargeParams);

					receive = 500 * IntegerFreQuency;
					
					
					vo.setCondition("imei='"
							+ imei
							+ "'and pay_status='200' and message='ok' and plan_count='1'  and sub_status='1' order by id desc limit 1");

					List<DataMap> listSer = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().getPayForInfo(vo);
					if(listSer.size()>0){
					String	subScriptionId = listSer.get(0).get("subscription_id") + "";
						// params.put("customer", customerId);
						Subscription sub = Subscription.retrieve(subScriptionId);
						sub.cancel(null);
						String id=listSer.get(0).get("id") + "";
						if (id != null && !"".equals(id)) {
							vo.setCondition("id='" + id
									+ "'");
							vo.setSubStatus("0");

							ServiceBean
									.getInstance()
									.getDeviceActiveInfoFacade()
									.updatePayForDeviceInfo(
											vo);
						}
					}
					
				} else if ("1".equals(count)) {

					String plan = "one_month";
					if ("1".equals(frequency)) {
						plan = "one_month";
					} else if ("3".equals(frequency)) {
						plan = "three_month";
					} else if ("6".equals(frequency)) {
						plan = "six_month";
					} else if ("12".equals(frequency)) {
						plan = "twelve_month";
					}

					Map<String, Object> params = new HashMap<String, Object>();

					params.put("plan", plan);
					// one_month在后台自定义
					params.put("customer", customerId);
					Subscription.create(params);

					// 若是订阅则需要保存订阅成功的id
					subscriptionId = Subscription.create(params).getId();
					/* if创建一个订阅计划，将立即向客户开始收取费用 */

					receive = 500 * IntegerFreQuency;
				}

				status = 200;
				message = "ok";
			} catch (AuthenticationException e) {
				status = e.getStatusCode();
				message = e.getMessage();
			} catch (InvalidRequestException e) {
				status = e.getStatusCode();
				message = e.getMessage();
			} catch (APIConnectionException e) {
				status = e.getStatusCode();
				message = e.getMessage();
			} catch (CardException e) {
				status = e.getStatusCode();
				message = e.getMessage();
			} catch (APIException e) {
				status = e.getStatusCode();
				message = e.getMessage();
			} finally {
				if (status == 200) {
					json.put(Constant.RESULTCODE, 1);
				} else {
					json.put(Constant.RESULTCODE, status);
				}
				json.put(Constant.EXCEPTION, message);
				json.put("imei", imei);

				vo.setDeviceImei(imei);
				vo.setCreateTime(sf.format(new Date()));
				vo.setReceive(receive);
				vo.setCardStatus(status + "");// 200
				vo.setMessage(message);
				vo.setCurrency("usd");
				vo.setDescription(frequency);
				vo.setSource(token);
				vo.setFriendlyName("000000000000000");// 全部默认为这个
				vo.setBrandName(customerId);
				vo.setEmail(email);
				vo.setSubscriptionId(subscriptionId);
				vo.setRatePlan(count);

				
			/*	PhoneInfo po = new PhoneInfo();
				po.setCondition("device_imei ='" + imei + "' limit 1");

				List<DataMap> listP = ServiceBean.getInstance()
						.getPhoneInfoFacade().getPWdeviceActiveInfo(po);*/
				String iccid = imei;
				/*if (listP.size() > 0) {
					iccid = listP.get(0).get("iccid") + "";
					vo.setIccid(iccid);
				} else {
					vo.setIccid(iccid);
				}*/
				vo.setIccid(iccid);
				vo.setSubStatus("1");
				
				
				ServiceBean.getInstance().getDeviceActiveInfoFacade()
						.insertPayForInfo(vo);
				result = 1;

				if (status == 200) {
					vo.setCondition("imei ='"
							+ imei
							+ "' and card_status='200' and message='ok'  ORDER BY id DESC limit 1");
					List<DataMap> cancelList = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);

					if ("0".equals(count)) {
						if (cancelList.size() > 0) {
							String id = cancelList.get(0).get("id") + "";
							String stopTime = cancelList.get(0)
									.get("stop_time") + "";

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							Calendar c = Calendar.getInstance();// 获得一个日历的实例
							Date date = new Date();
							try {
								date = sdf.parse(stopTime);// 初始日期
							} catch (Exception e) {

							}
							c.setTime(date);// 设置日历时间
							c.add(Calendar.MONTH, IntegerFreQuency);

							stopTime = sdf.format(c.getTime());

							vo.setUpdateTime(stopTime);
							vo.setCondition("id='" + id + "'");
							ServiceBean.getInstance()
									.getDeviceActiveInfoFacade()
									.updateCancelSubSriptionInfo(vo);
							
							vo.setDeviceImei(iccid);
							vo.setCardStatus(status + "");
							vo.setCreateTime(sf.format(new Date()));
							vo.setUpdateTime(stopTime);
							vo.setBelongCompany(cancelList.get(0).get("belong_company") + "");
							vo.setMessage("update");
						
							ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.insertCancelSubSriptionLogInfo(vo);
							
						} else {
							vo.setCondition("imei='"
									+ imei
									+ "'and pay_status='200' and message='ok' and plan_count='1' and sub_status='1' order by id desc limit 1");

							List<DataMap> listSer = ServiceBean.getInstance()
									.getDeviceActiveInfoFacade()
									.getPayForInfo(vo);

							if (listSer.size() > 0) {
								String createTime = listSer.get(0).get(
										"create_time")
										+ "";

								createTime = getStopTime(createTime, listSer
										.get(0).get("description") + "");

								Calendar c = Calendar.getInstance();// 获得一个日历的实例
								Date date = new Date();
								try {
									date = sf.parse(createTime);// 初始日期
								} catch (Exception e) {

								}
								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, IntegerFreQuency);

								createTime = sf.format(c.getTime());

								vo.setDeviceImei(imei);
								vo.setCreateTime(sf.format(new Date()));
								vo.setCardStatus(status + "");
								vo.setMessage(message);
								vo.setBrandName(customerId);
								vo.setDeviceAge(listSer.get(0).get(
										"subscription_id")
										+ "");
								vo.setUpdateTime(createTime);
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionInfo(vo);
								
								
								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(createTime);
								vo.setBelongCompany(cancelList.get(0).get("belong_company") + "");
								vo.setMessage("insert");
							
								ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.insertCancelSubSriptionLogInfo(vo);
							}
						}
					} else if ("1".equals(count)) {
						if (cancelList.size() > 0) {
							String id = cancelList.get(0).get("id") + "";
							vo.setUpdateTime("1");
							vo.setCondition("id='" + id + "'");
							ServiceBean.getInstance()
									.getDeviceActiveInfoFacade()
									.updateCancelSubSriptionInfo(vo);
						}
					}

					json.put("card_status", "支付成功");
				} else {
					json.put("card_status", "支付失败");
				}
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

	public static String getStopTime(String createTime, String period) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		int subYear = Integer.valueOf(createTime.substring(0, 4));
		int subMonth = Integer.valueOf(createTime.substring(5, 7));
		int subDay = Integer.valueOf(createTime.substring(8, 10));

		int year = Integer.valueOf(sf.format(new Date()).substring(0, 4));
		int month = Integer.valueOf(sf.format(new Date()).substring(5, 7));
		int day = Integer.valueOf(sf.format(new Date()).substring(8, 10));

		int chaNianYue = (year - subYear) * 12;
		int chaYue = month - subMonth;
		int chaDay = day - subDay;
		if (chaDay >= 0) {
			chaYue = chaNianYue + chaYue;
		} else {
			chaYue = chaNianYue + chaYue - 1;
		}
		int zhouqi = Integer.valueOf(period);
		chaYue = chaYue % zhouqi;

		createTime = new Date() + "";
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date date = new Date();
		try {
			date = sf.parse(createTime);// 初始日期
		} catch (Exception e) {

		}
		c.setTime(date);// 设置日历时间
		c.add(Calendar.MONTH, zhouqi - chaYue);
		createTime = sf.format(c.getTime());

		String subDayString = "";
		if (subDay < 10) {
			subDayString = "0" + subDay;
		} else {
			subDayString = subDay + "";
		}
		createTime = createTime.substring(0, 8) + subDayString;

		/*
		 * if ("3".equals(period)) {
		 * 
		 * chaYue = chaYue % 3;
		 * 
		 * createTime = new Date() + ""; Calendar c = Calendar.getInstance();//
		 * 获得一个日历的实例 Date date = new Date(); try { date =
		 * sf.parse(createTime);// 初始日期 } catch (Exception e) {
		 * 
		 * } c.setTime(date);// 设置日历时间 c.add(Calendar.MONTH, 3 - chaYue);
		 * createTime = sf.format(c.getTime());
		 * 
		 * String subDayString = ""; if (subDay < 10) { subDayString = "0" +
		 * subDay; } else { subDayString = subDay + ""; } createTime =
		 * createTime.substring(0, 8) + subDayString;
		 * 
		 * } else if ("6".equals(period)) { chaYue = chaYue % 6;
		 * 
		 * createTime = new Date() + ""; Calendar c = Calendar.getInstance();//
		 * 获得一个日历的实例 Date date = new Date(); try { date =
		 * sf.parse(createTime);// 初始日期 } catch (Exception e) {
		 * 
		 * } c.setTime(date);// 设置日历时间 c.add(Calendar.MONTH, 6 - chaYue);
		 * createTime = sf.format(c.getTime());
		 * 
		 * String subDayString = ""; if (subDay < 10) { subDayString = "0" +
		 * subDay; } else { subDayString = subDay + ""; } createTime =
		 * createTime.substring(0, 8) + subDayString; }
		 */
		return createTime;
	}
	

}
