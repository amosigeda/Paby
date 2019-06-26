package com.wtwd.sys.appinterfaces.newpay;

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
import com.wtwd.sys.saleinfo.domain.SaleInfo;

public class validPromotionCode extends BaseAction {

	Log logger = LogFactory.getLog(validPromotionCode.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
			logger.info("优惠券接口调用="+sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());

			String promotionCode = object.getString("promotion_code");

			String appToken = object.getString("app_token");

			if (!"".equals(promotionCode)) {

				DeviceActiveInfo vo1 = new DeviceActiveInfo();
				vo1.setCondition("promotion_code ='" + promotionCode
						+ "' limit 1");
				List<DataMap> list = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getSalesPromotion(vo1);

				if (list.size() <= 0) {
					json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				} else {
					int useCount = Integer.valueOf(list.get(0).get("use_count")
							+ "");
					logger.info("配置的使用次数是=" + useCount);
					
					if (useCount == 0) {
						json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
						logger.info("优惠码有效");
						result = 2;
						json.put("card_type", list.get(0).get("card_type") + "");
						json.put("discount_rate",
								list.get(0).get("discount_rate") + "");
						json.put("type", list.get(0).get("type") + "");
					} else {
						SaleInfo sal = new SaleInfo();
						sal.setCondition("promotion_code ='" + promotionCode
								+ "' and pay_status='200' and message='ok'");
						@SuppressWarnings("static-access")
						int countUse = ServiceBean.getInstance()
								.getSaleInfoFacade().getPayforInfoCount(sal);
						logger.info("已经使用的次数是=" + countUse);
						
						if (useCount > countUse) {
							json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
							logger.info("优惠码有效");
							result = 2;
							json.put("card_type", list.get(0).get("card_type")
									+ "");
							json.put("discount_rate",
									list.get(0).get("discount_rate") + "");
							json.put("type", list.get(0).get("type") + "");
						} else {
							json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
						}
					}
				}

			} else {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
			}
			json.put("promotionCode", promotionCode);
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

	public static void main(String[] args) throws SystemException {
		DeviceActiveInfo vo1 = new DeviceActiveInfo();
		String promotionCode = "PD1US20103MON";
		vo1.setCondition("promotion_code ='" + promotionCode + "' limit 1");
		String sid = "";
		List<DataMap> list = ServiceBean.getInstance()
				.getDeviceActiveInfoFacade().getSalesPromotion(vo1);
		if (list.size() > 0) {
			System.out.println(list.get(0).get("card_type") + "");
			System.out.println(list.get(0).get("discount_rate") + "");
			System.out.println(list.get(0).get("type") + "");
		}

		/*
		 * int a=1515; int b=(int) (a*(1-0.15)); System.out.println(b);
		 */
		/*
		 * int a=5; String b="0."+a; System.out.println(b);
		 */
	}

}
