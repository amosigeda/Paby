package com.wtwd.sys.appinterfaces;

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
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.preview.wireless.Sim;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;

public class payforAction extends BaseAction {

	Log logger = LogFactory.getLog(payforAction.class);

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
			logger.info("payforAction接收到的数据：" + sb.toString());
			String imei = object.getString("imei");
			String token = object.getString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.getString("frequency");// 表示月的个数
			String count = object.getString("count");

			String cardName = object.getString("card_name");// 信用卡名字
			String zipCode = object.getString("zip_code");// 邮编

			// 0表示扣一次 1表示自动扣

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
			int frequencyInt = Integer.valueOf(frequency);
			String email = "85988233@qq.com";

			PhoneInfo po = new PhoneInfo();
			po.setCondition("device_imei ='" + imei + "' limit 1");

			List<DataMap> listP = ServiceBean.getInstance()
					.getPhoneInfoFacade().getPWdeviceActiveInfo(po);
			String iccid = "";
			String receiptNumber = "";
			if (listP.size() > 0) {
				iccid = listP.get(0).get("iccid") + "";

				if (!"null".equals(iccid)) {

					try {
						if (userId != null && !"".equals(userId)) {

							AppUserInfo uo = new AppUserInfo();
							uo.setCondition("user_id='" + userId + "'");
							List<DataMap> AppUserList = ServiceBean
									.getInstance().getAppUserInfoFacade()
									.getAppUserInfo(uo);
							if (AppUserList.size() > 0) {
								email = AppUserList.get(0).get("email") + "";
							}
						}

						// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
						// Test apiKey
						Stripe.apiKey = Constant.STRIPE_APIKEY;
						// live scret key
						Map<String, Object> customerParams = new HashMap<String, Object>();
						customerParams.put("email", email);
						customerParams.put("source", token);
						Customer customer = Customer.create(customerParams);
						customerId = customer.getId();
						// Charge the Customer instead of the card:

						if ("0".equals(count)) {
							// 0表示扣一次
							Map<String, Object> chargeParams = new HashMap<String, Object>();
							chargeParams.put("amount", 1* frequencyInt);// 美分
							chargeParams.put("currency", "usd");
							chargeParams.put("customer", customerId);
							Charge charge = Charge.create(chargeParams);
							receive = 50 * frequencyInt;

							receiptNumber = charge.getReceiptNumber();

						} else if ("1".equals(count)) {
							// 1表示自动扣
							/*
							 * Map<String, Object> chargeParams = new
							 * HashMap<String, Object>();
							 * chargeParams.put("amount", 5000);
							 * chargeParams.put("currency", "usd");
							 * chargeParams.put("customer", customerId);
							 */
							// Charge charge = Charge.create(chargeParams);
							// 创建订阅
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
							subscriptionId = Subscription.create(params)
									.getId();
							/* if创建一个订阅计划，将立即向客户开始收取费用 */
							receive = frequencyInt * 500;

						}

						/*
						 * if ("1".equals(frequency)) {
						 * 
						 * Map<String, Object> chargeParams = new
						 * HashMap<String, Object>(); chargeParams.put("amount",
						 * 5000); chargeParams.put("currency", "usd");
						 * chargeParams.put("customer", customerId);
						 * 
						 * // Charge charge = Charge.create(chargeParams); //
						 * 创建订阅 Map<String, Object> params = new HashMap<String,
						 * Object>(); params.put("plan", "one_month"); //
						 * one_month在后台自定义 params.put("customer", customerId);
						 * Subscription.create(params);
						 * 
						 * // 若是订阅则需要保存订阅成功的id subscriptionId =
						 * Subscription.create(params).getId();
						 * if创建一个订阅计划，将立即向客户开始收取费用 receive = 500; } else if
						 * ("2".equals(frequency)) {
						 * 
						 * Map<String, Object> chargeParams = new
						 * HashMap<String, Object>(); chargeParams.put("amount",
						 * 3000);// 美分 chargeParams.put("currency", "usd");
						 * chargeParams.put("customer", customerId); Charge
						 * charge = Charge.create(chargeParams); receive = 3000;
						 * } else if ("3".equals(frequency)) { Map<String,
						 * Object> chargeParams = new HashMap<String, Object>();
						 * chargeParams.put("amount", 6000);
						 * chargeParams.put("currency", "usd");
						 * chargeParams.put("customer", customerId); Charge
						 * charge = Charge.create(chargeParams); receive = 6000;
						 * }
						 */

						// YOUR CODE: Save the customer ID and other info in a
						// database
						// for later.

						// YOUR CODE (LATER): When it's time to charge the
						// customer
						// again, retrieve the customer ID.
						/*
						 * Map<String, Object> chargeParams = new
						 * HashMap<String, Object>(); chargeParams.put("amount",
						 * 1500); // $15.00 this time
						 * chargeParams.put("currency", "usd");
						 * chargeParams.put("customer", customerId); Charge
						 * charge = Charge.create(chargeParams);
						 */
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
						vo.setCardName(cardName);
						vo.setZipCode(zipCode);
						vo.setReceiptNumber(receiptNumber);

						/*
						 * if (listP.size() > 0) { iccid =
						 * listP.get(0).get("iccid") + ""; vo.setIccid(iccid); }
						 * else {
						 */
						vo.setIccid(iccid);
						// }
						vo.setSubStatus("1");

						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
						result = 1;

						if (status == 200) {
							vo.setCondition("imei ='"
									+ imei
									+ "'and iccid='"
									+ iccid
									+ "' and card_status='200' and message='ok'  ORDER BY id DESC limit 1");
							List<DataMap> cancelList = ServiceBean
									.getInstance().getDeviceActiveInfoFacade()
									.getcancleImeiInfo(vo);
							String jihuo = "active";

							if ("0".equals(count)) {
								// 0表示扣一次
								String stopTime = "";
								if (cancelList.size() <= 0) {
									// 2表示六个月
									vo.setDeviceImei(imei);
									vo.setCreateTime(sf.format(new Date()));
									vo.setCardStatus(status + "");
									vo.setMessage(message);
									vo.setBrandName(customerId);
									vo.setDeviceAge("2");

									Calendar c = Calendar.getInstance();// 获得一个日历的实例
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									Date date = new Date();
									stopTime = new Date() + "";
									try {
										date = sdf.parse(stopTime);// 初始日期
									} catch (Exception e) {

									}
									c.setTime(date);// 设置日历时间
									c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

									stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

									vo.setUpdateTime(stopTime);
									vo.setIccid(iccid);
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.insertCancelSubSriptionInfo(vo);

									jihuo = "active";
								} else {
									stopTime = cancelList.get(0).get(
											"stop_time")
											+ "";

									Calendar c = Calendar.getInstance();// 获得一个日历的实例
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									Date date = null;
									try {
										date = sdf.parse(stopTime);// 初始日期
									} catch (Exception e) {

									}
									c.setTime(date);// 设置日历时间
									c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

									stopTime = sdf.format(c.getTime());// 得到6个月后的日期
									String id = cancelList.get(0).get("id")
											+ "";

									vo.setUpdateTime(stopTime);
									vo.setCondition("id='" + id + "'");
									int xiugai = ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.updateCancelSubSriptionInfo(vo);
									jihuo = "active";
								}

							} else if ("1".equals(count)) {
								jihuo = "active";
							} else {
								jihuo = "notActive";
							}

							/*
							 * if ("2".equals(frequency)) { String stopTime =
							 * ""; if (cancelList.size() <= 0) { // 2表示六个月
							 * vo.setDeviceImei(imei);
							 * vo.setCreateTime(sf.format(new Date()));
							 * vo.setCardStatus(status + "");
							 * vo.setMessage(message);
							 * vo.setBrandName(customerId);
							 * vo.setDeviceAge("2");
							 * 
							 * Calendar c = Calendar.getInstance();// 获得一个日历的实例
							 * SimpleDateFormat sdf = new SimpleDateFormat(
							 * "yyyy-MM-dd"); Date date = new Date(); stopTime =
							 * new Date() + ""; try { date =
							 * sdf.parse(stopTime);// 初始日期 } catch (Exception e)
							 * {
							 * 
							 * } c.setTime(date);// 设置日历时间 c.add(Calendar.MONTH,
							 * 6);// 在日历的月份上增加6个月
							 * 
							 * stopTime = sdf.format(c.getTime());// 得到6个月后的日期
							 * 
							 * vo.setUpdateTime(stopTime);
							 * ServiceBean.getInstance()
							 * .getDeviceActiveInfoFacade()
							 * .insertCancelSubSriptionInfo(vo);
							 * 
							 * jihuo = "active"; } else { stopTime =
							 * cancelList.get(0).get("stop_time") + "";
							 * 
							 * Calendar c = Calendar.getInstance();// 获得一个日历的实例
							 * SimpleDateFormat sdf = new SimpleDateFormat(
							 * "yyyy-MM-dd"); Date date = null; try { date =
							 * sdf.parse(stopTime);// 初始日期 } catch (Exception e)
							 * {
							 * 
							 * } c.setTime(date);// 设置日历时间 c.add(Calendar.MONTH,
							 * 6);// 在日历的月份上增加6个月
							 * 
							 * stopTime = sdf.format(c.getTime());// 得到6个月后的日期
							 * String id = cancelList.get(0).get("id") + "";
							 * 
							 * vo.setUpdateTime(stopTime);
							 * vo.setCondition("id='" + id + "'"); int xiugai =
							 * ServiceBean.getInstance()
							 * .getDeviceActiveInfoFacade()
							 * .updateCancelSubSriptionInfo(vo); jihuo =
							 * "active"; }
							 * 
							 * } else if ("3".equals(frequency)) { String
							 * stopTime = ""; if (cancelList.size() <= 0) { //
							 * 3表示一年 vo.setDeviceImei(imei);
							 * vo.setCreateTime(sf.format(new Date()));
							 * vo.setCardStatus(status + "");
							 * vo.setMessage(message);
							 * vo.setBrandName(customerId);
							 * vo.setDeviceAge("3");
							 * 
							 * String sfString = sf.format(new Date()); String
							 * nian = sfString.substring(0, 4); String wei =
							 * sfString.substring(4, 19);
							 * 
							 * int nianInt = Integer.valueOf(nian) + 1; stopTime
							 * = nianInt + wei; vo.setUpdateTime(stopTime);
							 * ServiceBean.getInstance()
							 * .getDeviceActiveInfoFacade()
							 * .insertCancelSubSriptionInfo(vo); jihuo =
							 * "active"; } else {
							 * 
							 * stopTime = cancelList.get(0).get("stop_time") +
							 * "";
							 * 
							 * Calendar c = Calendar.getInstance();// 获得一个日历的实例
							 * SimpleDateFormat sdf = new SimpleDateFormat(
							 * "yyyy-MM-dd"); Date date = null; try { date =
							 * sdf.parse(stopTime);// 初始日期 } catch (Exception e)
							 * {
							 * 
							 * } c.setTime(date);// 设置日历时间 c.add(Calendar.MONTH,
							 * 12);// 在日历的月份上增加6个月
							 * 
							 * stopTime = sdf.format(c.getTime());// 得到6个月后的日期
							 * String id = cancelList.get(0).get("id") + "";
							 * vo.setUpdateTime(stopTime);
							 * vo.setCondition("id='" + id + "'"); int xiugai =
							 * ServiceBean.getInstance()
							 * .getDeviceActiveInfoFacade()
							 * .updateCancelSubSriptionInfo(vo); jihuo =
							 * "active";
							 * 
							 * }
							 * 
							 * } else { jihuo = "cantActive"; }
							 */
							if ("active".equals(jihuo)) {
								// 执行激活sim卡的操作
								// PhoneInfo po = new PhoneInfo();
								// po.setCondition("device_imei ='" + imei +
								// "' limit 1");

								/*
								 * List<DataMap> listP =
								 * ServiceBean.getInstance()
								 * .getPhoneInfoFacade(
								 * ).getPWdeviceActiveInfo(po);
								 */

								if (listP.size() > 0) {
									iccid = listP.get(0).get("iccid") + "";
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
											Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
											ResourceSet<Sim> sims = Sim
													.reader().read();
											for (Sim sim : sims) {
												if (iccid
														.equals(sim.getIccid())) {
													sid = sim.getSid();
													vo1.setSid(sid);
													vo1.setIccid(iccid);
													vo1.setCardStatus("1");
													vo1.setFriendlyName(sim
															.getFriendlyName());
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCreateTime(sim
															.getDateCreated()
															+ "");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.insertSmsInfo(vo1);

													// 激活
													Twilio.init(ACCOUNT_SID,
															AUTH_TOKEN);
													Sim updatedSim = Sim
															.updater(sid)
															.setRatePlan(
																	"data100")
															.setStatus("active")
															.update();
													json.put("card_status",
															"激活成功");
													// break;
												} else {
													vo1.setCondition("iccid ='"
															+ sim.getIccid()
															+ "' limit 1");
													List<DataMap> listSelect = ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.getSsidInfo(vo1);
													if (listSelect.size() <= 0) {
														vo1.setSid(sim.getSid());
														vo1.setIccid(sim
																.getIccid());
														vo1.setCardStatus(sim
																.getStatus()
																.equals("active") ? "1"
																: "0");
														vo1.setFriendlyName(sim
																.getFriendlyName());
														vo1.setRatePlan(sim
																.getRatePlanSid());
														vo1.setCreateTime(sim
																.getDateCreated()
																+ "");
														vo1.setUpdateTime(sim
																.getDateUpdated()
																+ "");
														ServiceBean
																.getInstance()
																.getDeviceActiveInfoFacade()
																.insertSmsInfo(
																		vo1);
													} else {
														vo1.setCondition("iccid ='"
																+ sim.getIccid()
																+ "'");
														vo1.setUpdateTime(sim
																.getDateUpdated()
																+ "");
														vo1.setRatePlan(sim
																.getRatePlanSid());
														vo1.setCardStatus(sim
																.getStatus()
																.equals("active") ? "1"
																: "0");
														ServiceBean
																.getInstance()
																.getDeviceActiveInfoFacade()
																.updateDeviceSmsInfo(
																		vo1);
													}
												}
											}
											result = 1;
										} else {
											sid = list.get(0).get("sid") + "";
											// 激活
											Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
											Sim updatedSim = Sim.updater(sid)
													.setRatePlan("data100")
													.setStatus("active")
													.update();
											json.put("card_status", "激活成功");
											vo1.setCardStatus("1");
											vo1.setCondition("iccid ='" + iccid
													+ "'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo1);
											result = 1;
										}
									} else {
										logger.info("iccid为空");
										result = 2;
										json.put("card_status", "未找到对应的iccid");
									}
								} else {
									json.put("card_status", "未查到对应的IMEI");
									logger.info("未查到对应的IMEI--");
									result = 0;
								}

							}
						} else {
							json.put("card_status", "支付失败");
						}
					}

				} else {
					json.put(Constant.EXCEPTION, "存在IMEI没有iccid");
					json.put("imei", imei);
					json.put(Constant.RESULTCODE, 0);
				}
			} else {
				json.put(Constant.RESULTCODE, 0);
				json.put("imei", imei);
				json.put(Constant.EXCEPTION, "不存在这个IMEI");
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
	 * public static void main(String[] args) { Stripe.apiKey =
	 * "sk_test_Xdypv2QpRlx83iYVPax79XyD";
	 * 
	 * Map<String, Object> chargeParams = new HashMap<String, Object>();
	 * chargeParams.put("limit", 10);
	 * 
	 * try { System.out.println(Charge.list(chargeParams).toString());
	 * System.out.println(1); } catch (AuthenticationException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (InvalidRequestException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (APIConnectionException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (CardException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (APIException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	/*
	 * public static void main(String[] args) { Stripe.apiKey =
	 * "sk_test_Xdypv2QpRlx83iYVPax79XyD";
	 * 
	 * try { DeviceActiveInfo vo = new DeviceActiveInfo(); vo.setCondition(
	 * "pay_status='200' and message='ok' and plan_count='1' and sub_status='1'"
	 * );
	 * 
	 * try { List<DataMap> listSer = ServiceBean.getInstance()
	 * .getDeviceActiveInfoFacade() .getPayForInfo(vo); if(listSer.size()>0){
	 * for(int i=0;i<listSer.size();i++){ String
	 * subscriptionId=listSer.get(i).get("subscription_id")+"";
	 * 
	 * String id=listSer.get(i).get("id")+"";
	 * 
	 * 
	 * if(subscriptionId!=null&&!"".equals(subscriptionId)){ Subscription
	 * s=Subscription.retrieve(subscriptionId); String status=s.getStatus();
	 * if(!"active".equals(status)){ //停止iccid 并更改状态，并修改sim卡的状态
	 * 
	 * String iccid = listSer.get(i).get("iccid") + "";
	 * 
	 * if(iccid!=null&&!"".equals(iccid)){ vo.setCondition("iccid ='" + iccid +
	 * "' limit 1");
	 * 
	 * List<DataMap> listS = ServiceBean.getInstance()
	 * .getDeviceActiveInfoFacade().getSsidInfo(vo); final String ACCOUNT_SID =
	 * "AC08d153f6d0fb9a2135f0edd5614229f6"; final String AUTH_TOKEN =
	 * "5fc4e38b694dfd9a530871996a4a038e"; if (listS.size() > 0) { String sid =
	 * listS.get(0).get("sid") + ""; if(sid!=null&&!"".equals(sid)){
	 * Twilio.init(ACCOUNT_SID, AUTH_TOKEN); Sim updatedSim = Sim.updater(sid)
	 * .setStatus("inactive").update();
	 * 
	 * vo.setCardStatus("0"); vo.setCondition("iccid ='" + iccid + "'");
	 * ServiceBean.getInstance() .getDeviceActiveInfoFacade()
	 * .updateDeviceSmsInfo(vo);
	 * 
	 * if(id!=null&&!"".equals(id)){ vo.setCondition("id='"+id+"'");
	 * vo.setSubStatus("0");
	 * 
	 * ServiceBean.getInstance() .getDeviceActiveInfoFacade()
	 * .updatePayForDeviceInfo(vo); } }
	 * 
	 * } }
	 * 
	 * } } } }
	 * 
	 * Subscription s=Subscription.retrieve("sub_AW0Lpnm7qydmTF");
	 * 
	 * String status=s.getStatus(); // Double taxPercent=s.getTaxPercent(); //
	 * Long trialEnd=s.getTrialEnd(); System.out.println(status); } catch
	 * (SystemException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * String id=s.getId(); String object=s.getObject(); Double
	 * applicationFeePrercent=s.getApplicationFeePercent(); String billing=
	 * s.getBilling(); Boolean cancelAtPeriodEnd=s.getCancelAtPeriodEnd(); Long
	 * cancelAt=s.getCanceledAt(); Long created=s.getCreated(); Long
	 * currentPeriodStart=s.getCurrentPeriodStart(); Long
	 * currentPeriodEnd=s.getCurrentPeriodEnd(); String
	 * customer=s.getCustomer(); Integer dayUntilDue=s.getDaysUntilDue();
	 * Discount discount=s.getDiscount(); long endedA=s.getEndedAt();
	 * 
	 * String planId=s.getPlan().getId(); String
	 * plahObject=s.getPlan().getObject(); Long
	 * planAmount=s.getPlan().getAmount(); Integer quantity=s.getQuantity();
	 * Long start=s.getStart();
	 * 
	 * 
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("limit", 2); SubscriptionCollection subscriptions =
	 * Subscription.list(params); System.out.println(subscriptions); // String
	 * id=subscriptions.getData().get(0).getId(); // String
	 * object=subscriptions.getData().get(0).getObject(); // String } catch
	 * (AuthenticationException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (InvalidRequestException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (APIConnectionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (CardException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } catch (APIException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

}
