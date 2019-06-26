package com.wtwd.sys.appinterfaces.weitechao;

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

import test.com.wtwd.wtpet.service.AiShiDeIccidApi;
import test.com.wtwd.wtpet.service.AiShiDeIccidApiV2;
import test.com.wtwd.wtpet.service.ShuMiIccidApi;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
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

public class payforIccidAction extends BaseAction {

	Log logger = LogFactory.getLog(payforIccidAction.class);

	private static final int coin = 500;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		//long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		JSONObject json = new JSONObject();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			logger.info("-----weitechao：payforIccidAction-----");
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			String sbstring = sb.toString();
			logger.info("w_p accept's parameters is：" + sbstring);
			JSONObject object = JSONObject.fromObject(sbstring);
			String imei = object.getString("imei");// 以用户确认的为主
			String iccid = object.getString("iccid");
			String token = object.getString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.getString("frequency");// 表示月的个数
			String count = object.getString("count");// 0表示扣一次 1表示自动扣
			// String imsi = object.getString("imsi");
			String cardName = object.getString("card_name");// 信用卡名字
			String zipCode = object.getString("zip_code");// 邮编
			
			// String email = object.getString("email");
			// String userId = object.getString("user_id");
			// String userToken = object.getString("app_token");

			// iccid 属于哪个公司 1表示爱施德 2表示树米0表示twilio
			String belongCompany = "1";

			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			/*
			 * vo1.setCondition("iccid ='" + iccid + "' limit 1"); List<DataMap>
			 * lsitimei = ServiceBean.getInstance()
			 * .getDeviceActiveInfoFacade().getDeviceActiveInfo(vo1); if
			 * (lsitimei.size() <= 0) {
			 */
			vo1.setCondition("iccid ='" + iccid + "'");
			List<DataMap> lsitCard = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSsidInfo(vo1);
			String imsi = "";
			// String cardStatus="";
			// String cardStatus = object.getString("card_status");
			if (lsitCard.size() > 0) {
				imsi = lsitCard.get(0).get("imsi") + "";
				belongCompany = lsitCard.get(0).get("belong_company") + "";
				// cardStatus=lsitCard.get(0).get("card_status") + "";
				// 卡片状态：Activate,Suspend,Resume 1 2 3
			}
			logger.info("w_p 此时的imsi=" + imsi + ",belongCompany=" + belongCompany);
			/*
			 * vo1.setCondition("device_imei ='" + imei + "'");
			 * vo1.setIccid(iccid); vo1.setBelongCompany(belongCompany); 
			 * ServiceBean.getInstance().getDeviceActiveInfoFacade()
			 * .updateDeviceActiveInfo(vo1);
			 */
			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			String customerId = "";
			String subscriptionId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			int receive = 0;
			int frequencyInt = Integer.valueOf(frequency);
			String email = object.getString("email");
			String receiptNumber = "";

			if (!"null".equals(iccid)) {
				
				logger.info("w_p 开始支付------------------------");
				try {
					// if (userId != null && !"".equals(userId)) {
					/*
					 * AppUserInfo uo = new AppUserInfo();
					 * uo.setCondition("user_id='" + userId + "'");
					 * List<DataMap> AppUserList = ServiceBean.getInstance()
					 * .getAppUserInfoFacade().getAppUserInfo(uo); if
					 * (AppUserList.size() > 0) { email =
					 * AppUserList.get(0).get("email") + ""; }
					 */
					// }					
					Stripe.apiKey = selectAccount(imei);//选择账户
					Map<String, Object> customerParams = new HashMap<String, Object>();
					customerParams.put("email", email);
					customerParams.put("source", token);
					Customer customer = Customer.create(customerParams);
					customerId = customer.getId();
					logger.info("w_p 支付用户id==" + customerId);
					// Charge the Customer instead of the card:
					/*
					 * WTSigninAction ba = new WTSigninAction();
					 * ba.insertWMonitor("0", "0", "0",
					 * iccid+": new 支付了"+frequency+"个月的费用"); ba = null;
					 */
					if ("0".equals(count)) { // 0表示扣一次						
						Map<String, Object> chargeParams = new HashMap<String, Object>();
						if (frequencyInt == 1) {
							receive = Constant.OONTH;
							chargeParams.put("amount", Constant.OONTH);// 美分
						} else if (frequencyInt == 6) {
							receive = Constant.SONTH;
							chargeParams.put("amount", Constant.SONTH);// 美分
						} else if (frequencyInt == 12) {
							receive = Constant.TONTH;
							chargeParams.put("amount", Constant.TONTH);// 美分
						}
						// chargeParams.put("amount", coin * frequencyInt);// 美分
						chargeParams.put("currency", "usd");
						chargeParams.put("customer", customerId);
						chargeParams.put("description", iccid + " pay for "
								+ frequency + " months");

						Charge charge = Charge.create(chargeParams);
						// receive = coin * frequencyInt;
						receiptNumber = charge.getReceiptNumber();
						logger.info("w_p count=" + count + ",receive=" + receive + ",收据编号为：" + receiptNumber);

					} else if ("1".equals(count)) {
						// 1表示自动扣
						/*
						 * Map<String, Object> chargeParams = new
						 * HashMap<String, Object>(); chargeParams.put("amount",
						 * 5000); chargeParams.put("currency", "usd");
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
						params.put("description", iccid + " pay for "
								+ frequency+ " months");
						Subscription.create(params);

						// 若是订阅则需要保存订阅成功的id
						subscriptionId = Subscription.create(params).getId();
						/* if创建一个订阅计划，将立即向客户开始收取费用 */
						receive = frequencyInt * coin;
						logger.info("w_p count=" + count + ",receive=" + receive + ",订阅id=" + subscriptionId);
					}

					status = 200;
					message = "ok";
				} catch (AuthenticationException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logger.info("支付异常状态和message========" + status + "---"
							+ message);
				} catch (InvalidRequestException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logger.info("支付异常状态和message========" + status + "---"
							+ message);
				} catch (APIConnectionException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logger.info("支付异常状态和message========" + status + "---"
							+ message);
				} catch (CardException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logger.info("支付异常状态和message========" + status + "---"
							+ message);
				} catch (APIException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logger.info("支付异常状态和message========" + status + "---"
							+ message);
				} finally {
					logger.info("w_p 支付状态为-----" + status);
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
					vo.setFriendlyName("000000000000000");// 全部默认为这个
					vo.setBrandName(customerId);
					vo.setEmail(email);
					vo.setSubscriptionId(subscriptionId);
					vo.setRatePlan(count);
					vo.setCardName(cardName);
					vo.setZipCode(zipCode);
					vo.setReceiptNumber(receiptNumber);
					vo.setIccid(iccid);
					vo.setSubStatus("1");
					vo.setBelongCompany(belongCompany);

					int insertCount = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().insertPayForInfo(vo);
					logger.info("w_p 增加支付记录结果insertCount=" + insertCount);
					if (insertCount != 1) {
						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
					}
					result = 1;

					// 309-646
					if (status == 200) {
						logger.info("w_p 状态为200,支付成功开始走激活---------");
						vo.setCondition("imei ='" + iccid
								+ "'and iccid='" + iccid
								+ "' and card_status='200' and message='ok' and belong_company='"
								+ belongCompany + "' limit 1");
						List<DataMap> cancelList = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.getcancleImeiInfo(vo);
						String jihuo = null;

						if ("0".equals(count)) {
							// 0表示扣一次
							String stopTime = "";
							logger.info("--------------count=0-------------)");
							if (cancelList.size() <= 0) {
								logger.info(iccid + "以前无支付记录设置到期时间-----");
								// 2表示六个月
								vo.setDeviceImei(iccid);
								vo.setCreateTime(sf.format(new Date()));
								vo.setCardStatus(status + "");
								vo.setMessage(message);
								vo.setBrandName(customerId);
								vo.setDeviceAge("2");

								Calendar c = Calendar.getInstance();// 获得一个日历的实例

								Date date = (Date) sdf.parseObject(sdf
										.format(new Date()));// 设置初始日期
								logger.info("w_p 初始日期=" + date);
								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

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
								stopTime = cancelList.get(0).get("stop_time") + "";
								logger.info(iccid + "上一次支付后到期日期stopTime=" + stopTime);

								Date dt1 = (Date) sdf.parseObject(sdf.format(new Date()));// 今天时间
								Date dt2 = (Date) sdf.parseObject(stopTime);// 上次到期时间
								Calendar c = Calendar.getInstance();// 获得一个日历的实例
								Date date = null;
								// 设置初始日期
								if (dt1.getTime() >= dt2.getTime()) {
									date = dt1; 
								} else {
									date = dt2;
								}
								c.setTime(date); //设置日历时间
								c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());
								String id = cancelList.get(0).get("id") + "";
								vo.setUpdateTime(stopTime);
								vo.setCondition("id='" + id + "'");
								int updateResult = ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateCancelSubSriptionInfo(vo);
								logger.info("w_p 修改到期日期结果" + updateResult);
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
							logger.info("---------------count=1----------------");
						} else {
							jihuo = "notActive";
						}
						
						logger.info("w_p 激活状态为：" + jihuo);
						if ("active".equals(jihuo)) {
							
							if (iccid.length() > 0 && iccid != null) {
								
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
										ResourceSet<Sim> sims = Sim.reader()
												.read();
										for (Sim sim : sims) {
											if (iccid.equals(sim.getIccid())) {
												sid = sim.getSid();
												vo1.setSid(sid);
												vo1.setIccid(iccid);
												vo1.setCardStatus("1");
												vo1.setFriendlyName(sim
														.getFriendlyName());
												vo1.setRatePlan(sim
														.getRatePlanSid());
												vo1.setCreateTime(sim
														.getDateCreated() + "");
												vo1.setUpdateTime(sim
														.getDateUpdated() + "");
												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.insertSmsInfo(vo1);
												logger.info("sim:" + sim);
												// 激活
												Twilio.init(ACCOUNT_SID,
														AUTH_TOKEN);
												Sim updatedSim = Sim
														.updater(sid)
														.setRatePlan("data100")
														.setStatus("active")
														.update();
												json.put("card_status", "激活成功");
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
													vo1.setIccid(sim.getIccid());
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
															.insertSmsInfo(vo1);
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
												.setStatus("active").update();
										json.put("card_status", "激活成功");
										vo1.setCardStatus("1");
										vo1.setCondition("iccid ='" + iccid
												+ "'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);
										result = 1;
									}
								} else if ("1".equals(belongCompany)) {
									// 1表示爱施德
									logger.info("w_p 爱施德=====");
									String Status = AiShiDeIccidApi.getStatus(
											iccid, imsi);
									logger.info(iccid + "此ICCID卡的状态==" + Status);
									if ("Active".equals(Status)) {

									} else if ("Suspended".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Resume");
									} else if ("PreActive".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Activate");
									} else if ("TestReady".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "PreActive");
									}
								
									if (!"Active".equals(Status)) {
										Thread.sleep(1000 * 60);
										Status = AiShiDeIccidApi.getStatus(
												iccid, imsi);
										logger.info(iccid + "w_p 状态设置后调用接口查询状态=" + Status);
										if ("Suspended".equals(Status)) {
											AiShiDeIccidApiV2.setIccidStatus(
													iccid, imsi, "Resume");
										} else if ("PreActive".equals(Status)) {
											AiShiDeIccidApiV2.setIccidStatus(
													iccid, imsi, "Activate");
										}
									}

									json.put("card_status", "激活成功");
									result = 1;
									vo1.setCardStatus("1");
									vo1.setCondition("iccid ='" + iccid
											+ "'and belong_company='1'");
									logger.info("w_p 爱施德激活成功=============");
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.updateDeviceSmsInfo(vo1);
									/*
									 * if ("4".equals(cardStatus)) {// 4就是新的
									 * resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Activate");
									 * } else if ("2".equals(cardStatus)) {
									 * resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Resume"); }
									 * else { resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Activate");
									 * } JSONObject resultResponseJson =
									 * JSONObject .fromObject(resultResponse);
									 * String respCode = resultResponseJson
									 * .getString("resp_code"); if
									 * ("1".equals(respCode)) {
									 * json.put("card_status", "激活成功");
									 * vo1.setCardStatus("1");
									 * vo1.setCondition("iccid ='" + iccid +
									 * "'and belong_company='1'");
									 * ServiceBean.getInstance()
									 * .getDeviceActiveInfoFacade()
									 * .updateDeviceSmsInfo(vo1); result = 1; }
									 * else { json.put("card_status", "激活失败");
									 * result = 0; }
									 */
								} else if ("2".equals(belongCompany)) {
									logger.info("w_p belongCompany2=============");
									String resule = ShuMiIccidApi
											.updateIccidCardStatus(iccid, "0");
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
											vo1.setCondition("iccid ='" + iccid
													+ "'and belong_company='2'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo1);
											result = 1;
										}
									} else {
										json.put("card_status", "激活失败");
										logger.info("激活失败=============");
										result = 0;
									}
								}
							} else {
								logger.info("w_p iccid为空==========");
								result = 2;
								json.put("card_status", "未找到对应的iccid");
							}

						}
					} else {
						json.put("card_status", "支付失败");
						logger.info("w_p 支付失败=============");
					}
				}

			} else {
				json.put(Constant.EXCEPTION, "存在IMEI没有iccid");
				json.put("iccid", iccid);
				json.put(Constant.RESULTCODE, 0);
				logger.info("w_p 存在IMEI没有iccid=============");
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
			logger.info("w_p result=============" + result);
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	// 废弃，使用Constant.STRIPE_APIKEY
	private static String selectAccount(String imei){
		//判断imei是属于哪个品牌paby or rembo
		DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("device_imei ='" + imei + "'");
		List<DataMap> list;
		String brandname = null;
		try {
			list = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
			if (list != null && list.size() > 0) {
				brandname = list.get(0).get("brandname") + "";
				if ("rembo".equals(brandname)) {
					return Constant.STRIPE_REMBO_APIKEY;
				}
			}else {
				return Constant.STRIPE_APIKEY;
			}
		} catch (SystemException e) {			
			e.printStackTrace();
		}
		return Constant.STRIPE_APIKEY;
	}

	
	/*public static void main(String[] args) throws SystemException {
		DeviceActiveInfo vo = new DeviceActiveInfo();	  
		String imei="123456789012345"; 
		vo.setCondition("device_imei ='" + imei + "'"); 
		List<DataMap> list = ServiceBean.getInstance()
				.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
		String brandname = null;
		if (list.size() > 0) {
			brandname = list.get(0).get("brandname") + "";
			if ("rembo".equals(brandname)) {
				System.out.println("rembo");
			}
			System.out.println("paby");
		}else {
			System.out.println("paby");
		}
	}*/
			
}
