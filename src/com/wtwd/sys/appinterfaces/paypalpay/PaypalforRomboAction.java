package com.wtwd.sys.appinterfaces.paypalpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import test.com.wtwd.wtpet.service.AiShiDeIccidApi;
import test.com.wtwd.wtpet.service.AiShiDeIccidApiV2;
import test.com.wtwd.wtpet.service.ShuMiIccidApi;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.preview.wireless.Sim;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class PaypalforRomboAction extends BaseAction {

	Log logger = LogFactory.getLog(PaypalforRomboAction.class);
	private final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
	private final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
	JSONObject json = null;
	SimpleDateFormat ymdThms = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String href = request.getServletPath();
		JSONObject object = paramParse(request);
		logger.info("PaypalforRomboAction params: " + object);
				
		try {			
			String userId = object.getString("user_id");
			String userToken = object.getString("app_token");
			String frequency = object.getString("frequency"); // 表示月的个数 
			String pay_id = object.getString("pay_id");  //支付编号
			// String imei = object.getString("imei");
			String iccid = object.getString("iccid");
			
			if ( (result = verifyAppToken(userId, userToken)) == Constant.SUCCESS_CODE ) {
				if (null != iccid && iccid.length() > 0) {
					//查询交易详情
					Payment payment = queryPaymentDetails(pay_id);
					String email = payment.getPayer().getPayerInfo().getEmail();
					String zipCode = payment.getPayer().getPayerInfo().getShippingAddress().getPostalCode(); // 邮编
					String payerId = payment.getPayer().getPayerInfo().getPayerId(); //customerId,  "6EF59T746A6QE",
					String payState = payment.getState(); // 支付状态  "approved"
					String createTime = payment.getCreateTime();
					String message = payment.getFailureReason();
					String amount = null;
					String currency = null;
					List<Transaction> transactions = payment.getTransactions();
					for (Transaction transaction : transactions) {
						String money = transaction.getAmount().getTotal();
						amount = money.replace(".", "");
						currency = transaction.getAmount().getCurrency().toLowerCase();
					}
					if (message == null) {
						message = "ok";
					}
					if ("approved".equals(payState)) {
						payState = "200";
					}								
					Date time2date = ymdThms.parse(createTime);
					createTime = ymdhms.format(time2date);

					logger.info("payment信息" + iccid + ":金额" + amount + currency
							+ ",时间：" + createTime + "支付状态：" + payState + ",异常信息："+ message);
				
					boolean flag = true;
					String belongCompany = queryIMSI(iccid, false);
					String imsi = queryIMSI(iccid, flag);
				
					DeviceActiveInfo vo = new DeviceActiveInfo();
					vo.setDeviceImei(iccid);
					vo.setIccid(iccid);
					vo.setCreateTime(createTime); // 时间可能需要转换？？
					vo.setReceive(Integer.valueOf(amount));
					vo.setCardStatus(payState); // 支付状态
					vo.setMessage(message);
					vo.setCurrency(currency);
					vo.setDescription(frequency);
					vo.setFriendlyName("paypal支付"); //全部默认为这个
					vo.setBrandName(payerId);
					vo.setEmail(email);
					//vo.setRatePlan(count);
					vo.setCardName("paypal");
					vo.setZipCode(zipCode);
					vo.setReceiptNumber(pay_id);
					vo.setSubStatus("1");
					vo.setBelongCompany(belongCompany);
		
					int insertCount = ServiceBean.getInstance().getDeviceActiveInfoFacade()
							.insertPayForInfo(vo);
					logger.info("添加支付结果insertCount=" + insertCount);
		
					result = Constant.SUCCESS_CODE;
						
					// 支付成功激活卡流程
					if ("200".equals(payState)) {
						logger.info("支付成功开始走激活---------");
						json.put(Constant.RESULTCODE, result);
						json.put(Constant.EXCEPTION, "ok");
						vo.setCondition("imei ='" + iccid + "'and iccid='" + iccid
								+ "' and card_status='200' and message='ok' and belong_company='"
								+ belongCompany + "' limit 1");
						List<DataMap> cancelList = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);
						boolean activeStatus = false;
						if (cancelList.size() <= 0) {
							// 以前无支付记录设置到期时间
							noPayLogInsert(iccid, message, payerId, belongCompany, frequency);								
							activeStatus = true;
						} else {
							String stoptime = cancelList.get(0).get("stop_time") + "";
							String id = cancelList.get(0).get("id") + "";
							// 以前有支付记录续期
							havedPayLogInsert(stoptime, id, iccid, message, payerId, belongCompany, frequency);
							activeStatus = true;
						}
		
						logger.info("激活状态=" + activeStatus);
						if (activeStatus) {						
							if ("0".equals(belongCompany)) {
								// 0表示Twilio，激活...
								twilioCardActivate(iccid, json);						
							} else if ("1".equals(belongCompany)) {
								// 1表示爱施德，激活...
								aiShiDeCardActivate(iccid, imsi, json);							
							} else if ("2".equals(belongCompany)) {
								// 2表示树米，实际数据库并没有。激活...
								shuMiCardActivate(iccid, json);
							}
						}
					} else {
						logger.info(iccid + "支付失败,不激活---------");
						json.put(Constant.RESULTCODE, payState);
						json.put("pay_status", "支付失败");
					}
					
					json.put("request", href);
					json.put("message", message);
					
				}else {
					logger.info("请求参数iccid为空");
					result = 2;
					json.put("card_status", "请求参数iccid为空");
				}
			} else {
				json.put("request", href);
				json.put("message", "verify token failed");
			}			
		} catch (Exception e) {	
			e.getMessage();
			logger.info("Exception result=" + result + "," + e.getMessage());
		}
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		logger.info("json result=" + json.toString());
		return null;
	}
	
	private JSONObject paramParse(HttpServletRequest request) {
		JSONObject object = null;
		ServletInputStream input;
		try {
			input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			object = JSONObject.fromObject(sb.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	private String queryIMSI(String iccid, boolean flag) {
		DeviceActiveInfo deviceinfo = new DeviceActiveInfo();
		deviceinfo.setCondition("iccid ='" + iccid + "'");
		List<DataMap> listCard;
		String imsi = null;
		String belongCompany = null;
		try {
			listCard = ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.getSsidInfo(deviceinfo);
			if (listCard != null && listCard.size() > 0) {
				imsi = listCard.get(0).get("imsi") + "";
				belongCompany = listCard.get(0).get("belong_company") + "";
			}
			logger.info(iccid + ":imsi=" + imsi + ",belongCompany=" + belongCompany);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return flag == true ? imsi : belongCompany;
	}
	
	private void noPayLogInsert(String iccid, String message, String payerId, 
			String belongCompany, String frequency) throws ParseException {
		logger.info(iccid + "以前无支付记录设置到期时间-----");
		DeviceActiveInfo deviceinfo = new DeviceActiveInfo();
		// 2表示六个月
		deviceinfo.setDeviceImei(iccid);
		deviceinfo.setIccid(iccid);
		deviceinfo.setCreateTime(ymdhms.format(new Date()));
		deviceinfo.setCardStatus("200");
		deviceinfo.setMessage(message);
		deviceinfo.setBrandName(payerId);
		deviceinfo.setDeviceAge("2"); //字段subscription_id
		
		int frequencyInt = Integer.valueOf(frequency);
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date date = (Date) ymd.parseObject(ymd.format(new Date()));// 设置初始日期
		c.setTime(date);// 设置日历时间
		c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月
		String stopTime = ymd.format(c.getTime());// 得到frequencyInt个月后的日期
		logger.info("初始日期=" + stopTime);
		deviceinfo.setUpdateTime(stopTime);								
		deviceinfo.setBelongCompany(belongCompany);
		try {
			ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.insertCancelSubSriptionInfo(deviceinfo);
		
			deviceinfo.setMessage("insert");

			ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.insertCancelSubSriptionLogInfo(deviceinfo);
		
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	private void havedPayLogInsert(String stoptime, String id, String iccid, String message,
			String payerId, String belongCompany, String frequency) throws ParseException {
		logger.info(iccid + "上一次支付后到期日期stopTime=" + stoptime);
		DeviceActiveInfo vo = new DeviceActiveInfo();
		
		int frequencyInt = Integer.valueOf(frequency);
		Date dt1 = (Date) ymd.parseObject(ymd .format(new Date()));// 设置初始日期
		Date dt2 = (Date) ymd.parseObject(stoptime);// 设置初始日期
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		Date date = null;
		// 设置初始日期
		if (dt1.getTime() >= dt2.getTime()) {
			date = dt1;// 初始日期
		} else {
			date = dt2;// 初始日期
		}
		c.setTime(date);// 设置日历时间
		c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月
		
		stoptime = ymd.format(c.getTime());// 得到6个月后的日期
			
		vo.setUpdateTime(stoptime);
		vo.setCondition("id='" + id + "'");
		try {
			ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.updateCancelSubSriptionInfo(vo);
			
			vo.setDeviceImei(iccid);
			vo.setCardStatus("200");
			vo.setCreateTime(ymdhms.format(new Date()));
			vo.setBelongCompany(belongCompany);
			vo.setMessage("update");
			
			ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.insertCancelSubSriptionLogInfo(vo);
		} catch (SystemException e) {
			e.printStackTrace();
		}		
	}
	
	private void aiShiDeCardActivate(String iccid, String imsi, JSONObject json) {
		logger.info("爱施德卡激活开始--------");
		String status = null;
		try {
			status = AiShiDeIccidApi.getStatus(iccid, imsi);		
			logger.info("第一次查询卡的状态=" + status);
			if ("Suspended".equals(status)) {
				AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "Resume");
			} else if ("PreActive".equals(status)) {
				AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "Activate");
			} else if ("TestReady".equals(status)) {
				AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "PreActive");
			}
			
			Thread.sleep(1000 * 60);
			status = AiShiDeIccidApi.getStatus(iccid, imsi);
			logger.info("激活后第二次查询=" + status);
			if (!"Active".equals(status)) {
				if ("Suspended".equals(status)) {
					AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "Resume");
				} else if ("PreActive".equals(status)) {
					AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "Activate");
				} else if ("TestReady".equals(status)) {
					AiShiDeIccidApiV2.setIccidStatus(iccid, imsi, "PreActive");
				}
			}
			json.put("card_status", "激活成功");
			DeviceActiveInfo deviceinfo = new DeviceActiveInfo();
			deviceinfo.setCardStatus("1");
			deviceinfo.setCondition("iccid ='" + iccid + "'and belong_company='1'");
			ServiceBean.getInstance().getDeviceActiveInfoFacade()
					.updateDeviceSmsInfo(deviceinfo);
			logger.info("爱施德卡激活完成--------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void twilioCardActivate(String iccid, JSONObject json) {
		DeviceActiveInfo deviceinfo = new DeviceActiveInfo();
		deviceinfo.setCondition("iccid ='" + iccid + "' limit 1");
		String sid = null;
		List<DataMap> list;
		try {
			list = ServiceBean.getInstance().getDeviceActiveInfoFacade().getSsidInfo(deviceinfo);
			if (list == null || list.size() <= 0) {
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				ResourceSet<Sim> sims = Sim.reader().read();
				for (Sim sim : sims) {
					if (iccid.equals(sim.getIccid())) {
						sid = sim.getSid();
						deviceinfo.setSid(sid);
						deviceinfo.setIccid(iccid);
						deviceinfo.setCardStatus("1");
						deviceinfo.setFriendlyName(sim.getFriendlyName());
						deviceinfo.setRatePlan(sim.getRatePlanSid());
						deviceinfo.setCreateTime(sim.getDateCreated() + "");
						deviceinfo.setUpdateTime(sim.getDateUpdated() + "");
						ServiceBean.getInstance().getDeviceActiveInfoFacade().insertSmsInfo(deviceinfo);						
						// 激活
						Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
						Sim.updater(sid).setRatePlan("data100").setStatus("active").update();
						json.put("card_status", "激活成功");
					} else {
						deviceinfo.setCondition("iccid ='" + sim.getIccid() + "' limit 1");
						List<DataMap> sids;						
						sids = ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.getSsidInfo(deviceinfo);
						if (list == null || sids.size() <= 0) {
							deviceinfo.setSid(sim.getSid());
							deviceinfo.setIccid(sim.getIccid());
							deviceinfo.setCardStatus(sim.getStatus().equals("active") ? "1" : "0");
							deviceinfo.setFriendlyName(sim.getFriendlyName());
							deviceinfo.setRatePlan(sim.getRatePlanSid());
							deviceinfo.setCreateTime(sim.getDateCreated() + "");
							deviceinfo.setUpdateTime(sim.getDateUpdated() + "");
							ServiceBean.getInstance().getDeviceActiveInfoFacade()
									.insertSmsInfo(deviceinfo);
						} else {
							deviceinfo.setCondition("iccid ='" + sim.getIccid() + "'");
							deviceinfo.setUpdateTime(sim.getDateUpdated() + "");
							deviceinfo.setRatePlan(sim.getRatePlanSid());
							deviceinfo.setCardStatus(sim.getStatus().equals("active") ? "1" : "0");
							ServiceBean.getInstance().getDeviceActiveInfoFacade()
									.updateDeviceSmsInfo(deviceinfo);
						}										
					}
				}
			} else {
				sid = list.get(0).get("sid") + "";
				// 激活
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				Sim.updater(sid).setRatePlan("data100").setStatus("active").update();
				json.put("card_status", "激活成功");
				deviceinfo.setCardStatus("1");
				deviceinfo.setCondition("iccid ='" + iccid + "'");
				ServiceBean.getInstance().getDeviceActiveInfoFacade()
						.updateDeviceSmsInfo(deviceinfo);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}			
	}
	 
	private void shuMiCardActivate(String iccid, JSONObject json) {
		DeviceActiveInfo deviceinfo = new DeviceActiveInfo();
		String resule = ShuMiIccidApi.updateIccidCardStatus(iccid, "0");
		JSONObject responseJson = JSONObject.fromObject(resule);
		if ("0".equals(responseJson.get("code"))) {
			String res = ShuMiIccidApi.IccidSubscription(iccid, "00000050001");
			JSONObject resResponseJson = JSONObject.fromObject(res);
			if ("0".equals(resResponseJson.get("code"))) {
				json.put("card_status", "激活成功");
				deviceinfo.setCardStatus("1");
				deviceinfo.setCondition("iccid ='" + iccid + "'and belong_company='2'");
				try {
					ServiceBean.getInstance().getDeviceActiveInfoFacade()
							.updateDeviceSmsInfo(deviceinfo);
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		} else {
			json.put("card_status", "激活失败");
		}
	}

	private Payment queryPaymentDetails(String pay_id) {
		APIContext apiContext = new APIContext(Constant.clientID, Constant.clientSecret, Constant.mode);
		Payment payment = null;
		try {
			payment = Payment.get(apiContext, pay_id);
		} catch (PayPalRESTException e) {
			e.getMessage();
			logger.info(e.getMessage());
		}
		return payment;
	}
}
