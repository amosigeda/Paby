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
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;

public class selectBindIccidAction extends BaseAction {

	Log logger = LogFactory.getLog(payforAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		JSONObject json = new JSONObject();
		//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			String imei = object.getString("imei");

			PhoneInfo po = new PhoneInfo();
			po.setCondition("device_imei ='" + imei + "' limit 1");

			List<DataMap> listP = ServiceBean.getInstance()
					.getPhoneInfoFacade().getPWdeviceActiveInfo(po);
			if (listP.size() > 0) {
				String iccid = listP.get(0).get("iccid") + "";
				if (iccid.length() > 0 && iccid != null && !"".equals(iccid)) {
					json.put(Constant.RESULTCODE, 1);
				} else {
					json.put(Constant.RESULTCODE, 0);
				}
			} else {
				json.put(Constant.RESULTCODE, 2);
			}
			json.put("imei", imei);
			json.put("request", href);
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb1 = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb1.append(resultSb);

			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb1.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
}