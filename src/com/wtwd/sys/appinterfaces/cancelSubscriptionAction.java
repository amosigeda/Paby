package com.wtwd.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Subscription;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class cancelSubscriptionAction extends BaseAction {

	Log logger = LogFactory.getLog(payforAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
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
			logger.info("cancelSubscriptionAction类接收参数为：" + sb.toString());
			String imei = object.getString("imei");

			/*
			 * String userId = object.getString("user_id"); String userToken =
			 * object.getString("user_token");
			 */

			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			// String customerId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("imei='"
					+ imei
					+ "'and pay_status='200' and message='ok' and plan_count='1' and sub_status='1'  order by id desc limit 1");

			List<DataMap> listSer = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getPayForInfo(vo);
			String customerId = "";
			String subScriptionId = "";
			String createTime = "";
			try {
				// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
				// live secret key

				// 下面这个key为测试key
				Stripe.apiKey = Constant.STRIPE_APIKEY;
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

				if (listSer.size() > 0) {
					String description = listSer.get(0).get("description") + "";
					createTime = listSer.get(0).get("create_time") + "";

					int year = Integer.valueOf(sf.format(new Date()).substring(
							0, 4));
					int month = Integer.valueOf(sf.format(new Date())
							.substring(5, 7));
					int day = Integer.valueOf(sf.format(new Date()).substring(
							8, 10));

					int subYear = Integer.valueOf(createTime.substring(0, 4));
					int subMonth = Integer.valueOf(createTime.substring(5, 7));
					int subDay = Integer.valueOf(createTime.substring(8, 10));

					if ("1".equals(description)) {

						createTime = (listSer.get(0).get("create_time") + "")
								.substring(8, 10);

						if (day >= Integer.valueOf(createTime)) {
							// 这个月取消的天数数字大于订阅时候的天数数字
							month = month + 1;
						}

						String monthString = "";
						if (month > 12) {
							year = year + 1;
							month = month - 12;
						}
						System.out.println(month);
						if (month < 10) {
							monthString = "0" + month;
						}
						createTime = year + "-" + monthString + "-"
								+ createTime;
					} else if ("3".equals(description)) {
						createTime = getStopTime(createTime, "3");
					} else if ("6".equals(description)) {
						createTime = getStopTime(createTime, "6");
					} else if ("12".equals(description)) {
						/*
						 * int year=Integer.valueOf(sf.format(new
						 * Date()).substring(0, 4)); int
						 * month=Integer.valueOf(sf.format(new
						 * Date()).substring(5,7)); int
						 * day=Integer.valueOf(sf.format(new
						 * Date()).substring(8, 10));
						 */
						// 现在年的时间

						if (year == subYear) {
							// 如果是一年的直接在创建日期加一年则为到期日期
							subYear = subYear + 1;
							createTime = subYear + createTime.substring(4, 10);
						} else {
							/*
							 * 如果不是一年的，比较创建订阅的月份和现在的月份，现在的月份小于订阅的月份，
							 * 则结束日期就为今年的年加上订阅的日期，如果等于，比较日 如果日小于，依旧是现在的年加月份
							 * 如果等于，则今年的年+1+订阅的月份
							 */
							if (month > subMonth) {
								year = year + 1;
								createTime = year + createTime.substring(4, 10);
							} else if (month < subMonth) {
								createTime = year + createTime.substring(4, 10);
							} else if (subMonth == month) {
								if (day >= subDay) {
									year = year + 1;
									createTime = year
											+ createTime.substring(4, 10);
								} else {
									createTime = year
											+ createTime.substring(4, 10);
								}
							}

						}

					}
					// Map<String, Object> params = new HashMap<String,
					// Object>();
					// params.put("plan", "one_month");
					// one_month在后台自定义
					// customerId = listSer.get(0).get("customer_id") + "";
					subScriptionId = listSer.get(0).get("subscription_id") + "";
					// params.put("customer", customerId);
					Subscription sub = Subscription.retrieve(subScriptionId);
					sub.cancel(null);
					status = 200;
					message = "ok";
					
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
				} else {
					status = 200;
					message = "未查到订阅过";
				}

				// YOUR CODE: Save the customer ID and other info in a database
				// for later.

				// YOUR CODE (LATER): When it's time to charge the customer
				// again, retrieve the customer ID.
				/*
				 * Map<String, Object> chargeParams = new HashMap<String,
				 * Object>(); chargeParams.put("amount", 1500); // $15.00 this
				 * time chargeParams.put("currency", "usd");
				 * chargeParams.put("customer", customerId); Charge charge =
				 * Charge.create(chargeParams);
				 */
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

				SimpleDateFormat sf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				vo.setDeviceImei(imei);
				vo.setCreateTime(sf.format(new Date()));
				vo.setCardStatus(status + "");
				vo.setMessage(message);
				vo.setBrandName(customerId);
				vo.setDeviceAge(subScriptionId);
				vo.setUpdateTime(createTime);
				ServiceBean.getInstance().getDeviceActiveInfoFacade()
						.insertCancelSubSriptionInfo(vo);
				result = 1;

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
	 * public static void main(String[] args) { DeviceActiveInfo vo = new
	 * DeviceActiveInfo(); vo.setCondition("imei='" + "1" +
	 * "'and pay_status='200' order by id desc limit 1");
	 * 
	 * List<DataMap> listSer; try { listSer = ServiceBean.getInstance()
	 * .getDeviceActiveInfoFacade().getPayForInfo(vo); String
	 * createTime=(listSer.get(0).get("create_time")+"").substring(8,10);
	 * System.out.println(createTime); int year=new Date().getYear()+1900; int
	 * month=new Date().getMonth()+1; int day=new Date().getDay()+2;
	 * 
	 * if(day>=Integer.valueOf(createTime)){ //这个月取消的天数数字大于订阅时候的天数数字
	 * month=month+1; }
	 * 
	 * String monthString=""; if(month>12){ year=year+1; month=month-12; }
	 * System.out.println(month); if(month<10){ monthString="0"+month; }
	 * createTime=year+"-"+monthString+"-"+createTime;
	 * System.out.println(createTime); } catch (SystemException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

	/*
	 * public static void main(String[] args) { String createTime="";
	 * createTime=("2017-04-16 12:11:10").substring(8,10);
	 * System.out.println(createTime);
	 * 
	 * SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd");
	 * 
	 * 
	 * System.out.println(sf.format(new Date()).substring(5,7)); int
	 * year=Integer.valueOf(sf.format(new Date()).substring(0, 4)); int
	 * month=Integer.valueOf(sf.format(new Date()).substring(5,7)); int
	 * day=Integer.valueOf(sf.format(new Date()).substring(8, 10));
	 * 
	 * if(day>=Integer.valueOf(createTime)){ //这个月取消的天数数字大于订阅时候的天数数字
	 * month=month+1; }
	 * 
	 * String monthString=""; if(month>12){ year=year+1; month=month-12; }
	 * if(month<10){ monthString="0"+month; }
	 * createTime=year+"-"+monthString+"-"+createTime;
	 * 
	 * System.out.println(createTime); }
	 */
	public static void main(String[] args) throws ParseException {
		// String a="2014-10-12 10:00:01";
		// System.out.println(a.subSequence(5, 7));
		/*
		 * System.out.println(countMonths("2014-11-12 12:12:12",
		 * "2015-11-01 12:12:12", "yyyy-MM-dd"));
		 */
		System.out.println(getStopTime("2014-04-02", "3"));
	}

	public static int countMonths(String date1, String date2, String pattern)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}

		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
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
