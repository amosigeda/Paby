package com.wtwd.sys.appinterfaces.newpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import test.com.wtwd.wtpet.service.AiShiDeIccidApi;
import test.com.wtwd.wtpet.service.AiShiDeIccidApiV2;
import test.com.wtwd.wtpet.service.ShuMiIccidApi;

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
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class specialPayforIccidAction extends BaseAction {

	Log logger = LogFactory.getLog(specialPayforIccidAction.class);

	private static final int coin = 500;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		// long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		// Date start = new Date();
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
			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			DeviceActiveInfo vo = new DeviceActiveInfo();
			logger.info("newpay:specialPayforIccidAction-----" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			// String imei = object.getString("imei");// 以用户确认的为主
			String iccid = object.getString("iccid");
			String promotionCode = object.getString("promotion_code");

			vo1.setCondition("promotion_code ='" + promotionCode + "' limit 1");
			List<DataMap> listspt = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSalesPromotion(vo1);
			String cardType = "1";
			String type="";
			if (listspt.size() > 0) {
				cardType = listspt.get(0).get("card_type") + "";
				// 1表示为优惠卡2表示为代金券
				type=listspt.get(0).get("type") + "";
			}
			logger.info(promotionCode+"优惠支付==cardType="+cardType+"---type="+type);
			String belongCompany = "1";
			String token = object.getString("token");
			String frequency = object.getString("frequency");// 表示月的个数
			String count = object.getString("count"); // 0表示扣一次 1表示自动扣

			String cardName = object.getString("card_name");// 信用卡名字
			String zipCode = object.getString("zip_code");// 邮编

			// String email = object.getString("email");
			// String userId = object.getString("user_id");
			// String userToken = object.getString("app_token");
			vo1.setCondition("iccid ='" + iccid + "'");
			List<DataMap> lsitCard = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSsidInfo(vo1);
			String imsi = "";
			if (lsitCard.size() > 0) {
				imsi = lsitCard.get(0).get("imsi") + "";
				belongCompany = lsitCard.get(0).get("belong_company") + "";
			}
			logger.info(iccid+"imsi======"+imsi+"---belongCompany=="+belongCompany);
			if ("1".equals(type)) {

				// String cardNumber = object.getString("cardNumber");
				// ios获取不到这个卡号
				// iccid 属于哪个公司 1表示爱施德 2表示树米0表示twilio

				Integer status = null;
				String message = "";
				String customerId = "";
				String subscriptionId = "";

				int receive = 0;
				int frequencyInt = Integer.valueOf(frequency);
				String email = object.getString("email");

				String receiptNumber = "";

				if (!"null".equals(iccid)) {

					try {
						Stripe.apiKey = Constant.STRIPE_APIKEY; // live scret key					
						Map<String, Object> customerParams = new HashMap<String, Object>();
						customerParams.put("email", email);
						customerParams.put("source", token);
						Customer customer = Customer.create(customerParams);
						customerId = customer.getId();
						// Charge the Customer instead of the card:
						if ("0".equals(count)) {
							// 0表示扣一次
							Map<String, Object> chargeParams = new HashMap<String, Object>();
							if (listspt.size() > 0) {

								Integer promotionCo = Integer.valueOf(listspt
										.get(0).get("discount_rate") + "");
								int a = 100 - promotionCo;

								if (frequencyInt == 1) {
									receive = Constant.OONTH * a / 100;
									chargeParams.put("amount", receive);// 美分
								} else if (frequencyInt == 6) {
									receive = Constant.SONTH * a / 100;
									chargeParams.put("amount", receive);// 美分
								} else if (frequencyInt == 12) {
									receive = Constant.TONTH * a / 100;
									chargeParams.put("amount", receive);// 美分
								}
								// chargeParams.put("amount", coin *
								// frequencyInt);// 美分
								chargeParams.put("currency", "usd");
								chargeParams.put("customer", customerId);
								chargeParams.put("description", iccid + "支付了"
										+ frequency + "个月的费用");
								Charge charge = Charge.create(chargeParams);
								// receive = coin * frequencyInt;

								receiptNumber = charge.getReceiptNumber();

							} else {
								if (frequencyInt == 1) {
									receive = 799;
									chargeParams.put("amount", 799);// 美分
								} else if (frequencyInt == 6) {
									receive = 4194;
									chargeParams.put("amount", 4194);// 美分
								} else if (frequencyInt == 12) {
									receive = 5988;
									chargeParams.put("amount", 5988);// 美分
								}
								chargeParams.put("currency", "usd");
								chargeParams.put("customer", customerId);
								chargeParams.put("description", iccid + "支付了"
										+ frequency + "个月的费用");
								Charge charge = Charge.create(chargeParams);

								receiptNumber = charge.getReceiptNumber();
							}
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
							params.put("description", iccid + "支付了" + frequency
									+ "个月的费用");
							Subscription.create(params);

							// 若是订阅则需要保存订阅成功的id
							subscriptionId = Subscription.create(params)
									.getId();
							/* if创建一个订阅计划，将立即向客户开始收取费用 */
							receive = frequencyInt * coin;

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
						json.put("iccid", iccid);

						vo.setDeviceImei(iccid);
						vo.setCreateTime(sf.format(new Date()));
						vo.setReceive(receive);
						vo.setCardStatus(status + "");// 200
						vo.setMessage(message);
						vo.setCurrency("usd");
						vo.setDescription(frequency);
						vo.setSource(token);
						vo.setFriendlyName("折扣优惠券");// 全部默认为这个
						vo.setBrandName(customerId);
						vo.setEmail(email);
						vo.setSubscriptionId(subscriptionId);
						vo.setRatePlan(count);
						vo.setCardName(cardName);
						vo.setZipCode(zipCode);
						vo.setReceiptNumber(receiptNumber);
						vo.setPromotionCode(promotionCode);
						vo.setIccid(iccid);
						vo.setSubStatus("1");
						vo.setBelongCompany(belongCompany);

						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
						result = 1;

						if (status == 200) {
							vo.setCondition("imei ='"
									+ iccid
									+ "'and iccid='"
									+ iccid
									+ "' and card_status='200' and message='ok' and belong_company='"
									+ belongCompany + "' limit 1");
							List<DataMap> cancelList = ServiceBean
									.getInstance().getDeviceActiveInfoFacade()
									.getcancleImeiInfo(vo);
							String jihuo = "active";

							if ("0".equals(count)) {
								// 0表示扣一次
								String stopTime = "";
								if (cancelList.size() <= 0) {
									// 2表示六个月
									vo.setDeviceImei(iccid);
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
									
										date = sdf.parse(stopTime);// 初始日期
									
									c.setTime(date);// 设置日历时间
									c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

									stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

									vo.setUpdateTime(stopTime);
									vo.setIccid(iccid);
									vo.setBelongCompany(belongCompany);
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.insertCancelSubSriptionInfo(vo);

									vo.setDeviceImei(iccid);
									vo.setCardStatus(status + "");
									vo.setCreateTime(sf.format(new Date()));
									vo.setUpdateTime(stopTime);
									vo.setBelongCompany(belongCompany);
									vo.setMessage("insert");

									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.insertCancelSubSriptionLogInfo(vo);

									jihuo = "active";
								} else {
									stopTime = cancelList.get(0).get(
											"stop_time")
											+ "";
									logger.info(iccid
											+ "------------查询的日期为="
											+ stopTime);
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									String timeStop = sdf.format(new Date());

									Date dt1 = sdf.parse(timeStop);// 今天的时间
									Date dt2 = sdf.parse(stopTime);// 到期的时间

									Calendar c = Calendar.getInstance();// 获得一个日历的实例

									Date date = null;

									if (dt1.getTime() >= dt2.getTime()) {
										date = sdf.parse(timeStop);// 初始日期
									} else {
										date = sdf.parse(stopTime);// 初始日期
									}
									logger.info("使用的日期为=" + date);
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

									vo.setDeviceImei(iccid);
									vo.setCardStatus(status + "");
									vo.setCreateTime(sf.format(new Date()));
									vo.setUpdateTime(stopTime);
									vo.setBelongCompany(belongCompany);
									vo.setMessage("update");

									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.insertCancelSubSriptionLogInfo(vo);

									jihuo = "active";
								}

							} else if ("1".equals(count)) {
								jihuo = "active";
							} else {
								jihuo = "notActive";
							}

							if ("active".equals(jihuo)) {
								if (iccid.length() > 0 && iccid != null
										&& !"".equals(iccid)) {

									if ("0".equals(belongCompany)) {

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
									} else if ("1".equals(belongCompany)) {
										// 1表示爱施德
										// Activate,Suspend,Deactivate
										/*
										 * vo1.setCondition("iccid ='" + iccid +
										 * "'and belong_company='1'");
										 * List<DataMap> lsitCard=
										 * ServiceBean.getInstance()
										 * .getDeviceActiveInfoFacade()
										 * .getSsidInfo(vo1); String
										 * cardStatus=""; if(lsitCard.size()>0){
										 * cardStatus=lsitCard
										 * .get(0).get("card_status")+"";
										 * //卡片状态：Activate,Suspend,Resume 1 2 3
										 * }
										 */
										String resultResponse = AiShiDeIccidApi
												.selectIccidStatus(iccid, imsi);

										JSONObject resultResponseJson = JSONObject
												.fromObject(resultResponse);
										String data = resultResponseJson
												.getString("data");
										JSONObject dataJson = JSONObject
												.fromObject(data);
										String Status = dataJson
												.getString("status");
										if ("Active".equals(Status)) {

										} else if ("Suspended".equals(Status)) {
											AiShiDeIccidApiV2.setIccidStatus(
													iccid, imsi, "Resume");
										} else if ("Preactive".equals(Status)) {
											AiShiDeIccidApiV2.setIccidStatus(
													iccid, imsi, "Activate");
										}

										json.put("card_status", "激活成功");
										result = 1;
										vo1.setCardStatus("1");
										vo1.setCondition("iccid ='" + iccid
												+ "'and belong_company='1'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);

										/*
										 * if ("4".equals(cardStatus)) {// 4就是新的
										 * resultResponse = AiShiDeIccidApi
										 * .setIccidStatus(iccid, imsi,
										 * "Activate"); } else if
										 * ("2".equals(cardStatus)) {
										 * resultResponse = AiShiDeIccidApi
										 * .setIccidStatus(iccid, imsi,
										 * "Resume"); } else { resultResponse =
										 * AiShiDeIccidApi
										 * .setIccidStatus(iccid, imsi,
										 * "Activate"); } JSONObject
										 * resultResponseJson = JSONObject
										 * .fromObject(resultResponse); String
										 * respCode = resultResponseJson
										 * .getString("resp_code"); if
										 * ("1".equals(respCode)) {
										 * json.put("card_status", "激活成功");
										 * vo1.setCardStatus("1");
										 * vo1.setCondition("iccid ='" + iccid +
										 * "'and belong_company='1'");
										 * ServiceBean.getInstance()
										 * .getDeviceActiveInfoFacade()
										 * .updateDeviceSmsInfo(vo1); result =
										 * 1; } else { json.put("card_status",
										 * "激活失败"); result = 0; }
										 */
									} else if ("2".equals(belongCompany)) {

										String resule = ShuMiIccidApi
												.updateIccidCardStatus(iccid,
														"0");
										JSONObject resuleResponseJson = JSONObject
												.fromObject(resule);
										if ("0".equals(resuleResponseJson
												.get("code"))) {
											String resultt = ShuMiIccidApi
													.IccidSubscription(iccid,
															"00000050001");
											JSONObject resulttResponseJson = JSONObject
													.fromObject(resultt);
											if ("0".equals(resulttResponseJson
													.get("code"))) {
												json.put("card_status", "激活成功");
												vo1.setCardStatus("1");
												vo1.setCondition("iccid ='"
														+ iccid
														+ "'and belong_company='2'");
												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.updateDeviceSmsInfo(
																vo1);
												result = 1;
											}
										} else {
											json.put("card_status", "激活失败");
											result = 0;
										}
									}
								} else {
									logger.info("iccid为空");
									result = 2;
									json.put("card_status", "未找到对应的iccid");
								}
								/*
								 * } else { json.put("card_status",
								 * "未查到对应的IMEI"); logger.info("未查到对应的IMEI--");
								 * result = 0; }
								 */

							}
						} else {
							json.put("card_status", "支付失败");
						}
					}

				} else {
					json.put(Constant.EXCEPTION, "存在IMEI没有iccid");
					json.put("iccid", iccid);
					json.put(Constant.RESULTCODE, 0);
				}
			} else  {
                // 2表示代金券
				
				json.put(Constant.RESULTCODE, 1);
				json.put(Constant.EXCEPTION, "ok");
				json.put("iccid", iccid);

				vo.setDeviceImei(iccid);
				vo.setCreateTime(sf.format(new Date()));
				vo.setReceive(0);
				vo.setCardStatus("200");// 200
				vo.setMessage("ok");
				vo.setCurrency("usd");
				if ("1".equals(cardType)){
					vo.setDescription("12");
					vo.setFriendlyName("使用的是一年代金券");// 全部默认为这个
				}else if ("2".equals(cardType)){
					vo.setDescription("6");
					vo.setFriendlyName("使用的是6个月代金券");// 全部默认为这个
				}else if("3".equals(cardType)){
					vo.setDescription("3");
					vo.setFriendlyName("使用的是3个月代金券");// 全部默认为这个
				}else if("".equals(cardType)){
					vo.setDescription("1");
					vo.setFriendlyName("使用的是1个月代金券");// 全部默认为这个
				}
				vo.setSource(token);
				vo.setBrandName("");
				vo.setEmail("");
				vo.setSubscriptionId("");
				vo.setRatePlan("");
				vo.setCardName("");
				vo.setZipCode("");
				vo.setReceiptNumber(getRandom());
				vo.setPromotionCode(promotionCode);
				/*
				 * if (listP.size() > 0) { iccid = listP.get(0).get("iccid") +
				 * ""; vo.setIccid(iccid); } else {
				 */
				vo.setIccid(iccid);
				// }
				vo.setSubStatus("1");
				vo.setBelongCompany(belongCompany);

				ServiceBean.getInstance().getDeviceActiveInfoFacade()
						.insertPayForInfo(vo);
				result = 1;

				vo.setCondition("imei ='"
						+ iccid
						+ "'and iccid='"
						+ iccid
						+ "' and card_status='200' and message='ok' and belong_company='"
						+ belongCompany + "' limit 1");
				List<DataMap> cancelList = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);
				String jihuo = "active";

				if ("0".equals(count)) {
					// 0表示扣一次
					String stopTime = "";
					if (cancelList.size() <= 0) {
						// 2表示六个月
						vo.setDeviceImei(iccid);
						vo.setCreateTime(sf.format(new Date()));
						vo.setCardStatus("200");
						vo.setMessage("ok");
						vo.setBrandName("");
						vo.setDeviceAge("2");

						Calendar c = Calendar.getInstance();// 获得一个日历的实例
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date date = new Date();
						stopTime = new Date().toString();
						date = sdf.parse(stopTime);// 初始日期

						c.setTime(date);// 设置日历时间
						if ("1".equals(cardType)){
							c.add(Calendar.MONTH, 13);
						}else if ("2".equals(cardType)){
							c.add(Calendar.MONTH, 7);
						}else if("3".equals(cardType)){
							c.add(Calendar.MONTH, 4);
						}else if("4".equals(cardType)){
							c.add(Calendar.MONTH, 2);
						}

						stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

						vo.setUpdateTime(stopTime);
						vo.setIccid(iccid);
						vo.setBelongCompany(belongCompany);
						int tianjia = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.insertCancelSubSriptionInfo(vo);
						logger.info("优惠支付结果=" + tianjia);
						vo.setDeviceImei(iccid);
						vo.setCardStatus("200");
						vo.setCreateTime(sf.format(new Date()));
						vo.setUpdateTime(stopTime);
						vo.setBelongCompany(belongCompany);
						vo.setMessage("insert");

						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertCancelSubSriptionLogInfo(vo);

						jihuo = "active";
					} else {
						stopTime = cancelList.get(0).get("stop_time") + "";
						logger.info("查询出来的到期日期为=" + stopTime);
						 DateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						 Date dt2 = sdf.parse(stopTime);// 到期的时间
						 logger.info("dt2="+dt2);
						 Date dt1 = sdf.parse(sdf.format(new Date()));
						logger.info("dt1="+dt1);
						
						Calendar c = Calendar.getInstance();// 获得一个日历的实例

						Date date;

						if (dt1.getTime() >= dt2.getTime()) {
							date = sdf.parse(sdf.format(new Date()));
						} else {
							date = sdf.parse(stopTime);
						}

						c.setTime(date);// 设置日历时间
						if ("1".equals(cardType)){
							c.add(Calendar.MONTH, 12);
						}else if ("2".equals(cardType)){
							c.add(Calendar.MONTH, 6);
						}else if("3".equals(cardType)){
							c.add(Calendar.MONTH, 3);
						}else if("4".equals(cardType)){
							c.add(Calendar.MONTH, 1);
						}
						stopTime = sdf.format(c.getTime());
						String id = cancelList.get(0).get("id").toString();
						logger.info("最后使用的时间是=" + stopTime);
						vo.setUpdateTime(stopTime);
						vo.setCondition("id='" + id + "'");
						int xiugai = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.updateCancelSubSriptionInfo(vo);

						vo.setDeviceImei(iccid);
						vo.setCardStatus("200");
						vo.setCreateTime(sf.format(new Date()));
						vo.setUpdateTime(stopTime);
						vo.setBelongCompany(belongCompany);
						vo.setMessage("update");

						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertCancelSubSriptionLogInfo(vo);

						jihuo = "active";
					}

				} else if ("1".equals(count)) {
					jihuo = "active";
				} else {
					jihuo = "notActive";
				}
				logger.info("jihuo============" + jihuo
						+ "---------belongCompany--" + belongCompany);
				if ("active".equals(jihuo)) {
					if ("1".equals(belongCompany)) {
						logger.info("开始走激活=-------------------" + iccid + "---"
								+ imsi);
						// 1表示爱施德
						String Status = AiShiDeIccidApi.getStatus(iccid, imsi);
						logger.info(iccid + "此ICCID卡的状态==" + Status);
						if ("Active".equals(Status)) {

						} else if ("Suspended".equals(Status)) {
							AiShiDeIccidApiV2.setIccidStatus(iccid, imsi,
									"Resume");
						} else if ("Preactive".equals(Status)) {
							AiShiDeIccidApiV2.setIccidStatus(iccid, imsi,
									"Activate");
						}

						for (int n = 0; n < 3; n++) {
							Status = AiShiDeIccidApi.getStatus(iccid, imsi);
							logger.info(iccid + "激活后卡的状态为=" + Status);
							if (!"Active".equals(Status)) {
								if ("Suspended".equals(Status)) {
									AiShiDeIccidApiV2.setIccidStatus(iccid, imsi,
											"Resume");
								} else if ("Preactive".equals(Status)) {
									AiShiDeIccidApiV2.setIccidStatus(iccid, imsi,
											"Activate");
								}
							}
							break;
						}

						json.put("card_status", "激活成功");
						result = 1;
						vo1.setCardStatus("1");
						vo1.setCondition("iccid ='" + iccid
								+ "'and belong_company='1'");
						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.updateDeviceSmsInfo(vo1);

					} else if ("2".equals(belongCompany)) {

						String resule = ShuMiIccidApi.updateIccidCardStatus(
								iccid, "0");
						JSONObject resuleResponseJson = JSONObject
								.fromObject(resule);
						if ("0".equals(resuleResponseJson.get("code"))) {
							String resultt = ShuMiIccidApi.IccidSubscription(
									iccid, "00000050001");
							JSONObject resulttResponseJson = JSONObject
									.fromObject(resultt);
							if ("0".equals(resulttResponseJson.get("code"))) {
								json.put("card_status", "激活成功");
								vo1.setCardStatus("1");
								vo1.setCondition("iccid ='" + iccid
										+ "'and belong_company='2'");
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateDeviceSmsInfo(vo1);
								result = 1;
							}
						} else {
							json.put("card_status", "激活失败");
							result = 0;
						}
					}

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

	private static String getRandom() {
		Random rand = new Random();
		int result = rand.nextInt(999999);
		return result + "";
	}
	
	/*public static void main(String[] args) throws ParseException {
		String stopTime = "2017-10-03";
	 DateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	 Date dt2 = sdf.parse(stopTime);// 到期的时间
	 System.out.println(dt2); 
	 Date dt1 = sdf.parse(sdf.format(new Date()));
	System.out.println(dt1); 
	}*/

}