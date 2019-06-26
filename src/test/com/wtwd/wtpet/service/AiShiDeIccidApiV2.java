package test.com.wtwd.wtpet.service;

import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.lang.Des;
import com.wtwd.sys.appinterfaces.newpay.ComPay;

public class AiShiDeIccidApiV2 {
	
	static Log logger = LogFactory.getLog(AiShiDeIccidApiV2.class);

	// private final static String testUrltest = "https://test24.uyou.com/";
	// private final static String testUrl="https://gman.uyou.com/";
	private final static String testUrl2 = "http://uphone.uyou.com/iot/";

	public static void main(String[] args) {

		try {
			String result = selectIccidStatus("893107051704919148",
					"204047914919148");

			JSONObject resultResponseJson = JSONObject.fromObject(result);
			String data = resultResponseJson.getString("data");
			JSONObject dataJson = JSONObject.fromObject(data);
			String Status = dataJson.getString("status");
			System.out.println(Status);

			/*
			 * 结果={"resp_desc":"OK","resp_code":"1","data":{"status":{"flag":true
			 * ,"msg":"Suspended"}}}
			 */
			// String
			// result=setIccidStatus("893107051704919148","204047914919148","Suspend");
			// Activate,Suspend,Resume

			System.out.println("结果=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * 
		 * https://域名/userver/iotApiController/products/getStatus.do try {
		 * String
		 * b=setIccidStatus("11111111111111111111","11111111111111111111",
		 * "Deactivate"); System.out.println(b); 查询sim卡状态 String a =
		 * selectIccidStatus("11111111111111111111","11111111111111111111");
		 * System.out.println(a);
		 * 
		 * //Activate,Suspend,Deactivate String
		 * c=selectIccidXiangQing("11111111111111111111"
		 * ,"11111111111111111111"); JSONObject object =
		 * JSONObject.fromObject(c);
		 * System.out.println("啊哈="+object.getString("resp_code"));
		 * System.out.println(c); } catch (Exception e) {
		 * catch block e.printStackTrace(); }
		 */}

	// 查詢卡片狀態
	public static String selectIccidStatus(String iccid, String imsi)
			throws Exception {
		// String url =
		// testUrl+"userver/iotApiController/products/getStatus.do";
		String url = testUrl2 + "iotApiController/products/getStatus.do";
		System.out.println(url);
		// https://gman.uyou.com/userver/iotApiController/products/getStatus.do
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("iccid", iccid);
		json1.put("imsi", imsi);
		json.put("data", json1);
		json.put("sysPassword", "123456");
		json.put("sysAccount", "SYS_WTWD");
		System.out.println(json.toString());
		String result = "";
		try {
			String jiami = Des.DesEncrypt(json.toString(), "!@#$%^&*");
			System.out.println("加密=" + jiami);
			// result = HttpRequest.urlReturnParams(url,jiami);
			// result = Des.sendPost(url, jiami);
			result = Des.sendPostEntity(url, jiami);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String res = Des.DesDecrypt(result, "!@#$%^&*");
		System.out.println("结果解密=" + res);
		return res;
	}

	// 卡片状态设置Inner
	public static String setIccidStatusInner(String iccid, String imsi, String status) throws Exception {
		String url = testUrl2 + "iotApiController/products/setStatus.do";
		System.out.println(url);
		// https://域名/ userver /iotApiController/products/setStatus.do
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("iccid", iccid);
		json1.put("imsi", imsi);
		json1.put("status", status);
		json.put("data", json1);
		json.put("sysPassword", "123456");
		json.put("sysAccount", "SYS_WTWD");
		System.out.println(json.toString());
		String result = "";
		boolean opr_stat = false;
		if ("Activate".equals(status) || "Resume".equals(status)) {
			opr_stat = true;
		}
		// the "// yonghu add" what code?????
		try {
			String jiami = Des.DesEncrypt(json.toString(), "!@#$%^&*");
			result = Des.sendPostEntity(url, jiami);
			
			// yonghu add
			ComPay cp = new ComPay();
			if (opr_stat)
				// 空方法
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_ACT_TRAN);
			else
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_DEACT_TRAN);
			cp = null;

		} catch (Exception e) {
			e.printStackTrace();

			// yonghu add
			ComPay cp = new ComPay();
			if (opr_stat)
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_ACT_EXCEPT);
			else
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_DEACT_EXCEPT);
			cp = null;

		}
		logger.info("setIccidStatusInner请求 卡片状态设置结果=" + result);
		return Des.DesDecrypt(result, "!@#$%^&*");
	}

	public static String giccid, gimsi;

	// 卡片状态设置
	public static String setIccidStatus(String iccid, String imsi, String status) throws Exception {

		// String url = testUrl+"userver/iotApiController/products/setStatus.do";
		String url = testUrl2 + "iotApiController/products/setStatus.do";
		// https://域名/ userver /iotApiController/products/setStatus.do
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("iccid", iccid);
		json1.put("imsi", imsi);
		json1.put("status", status);
		json.put("data", json1);
		json.put("sysPassword", "123456");
		json.put("sysAccount", "SYS_WTWD");
		System.out.println(json.toString());
		String result = "";

		boolean opr_stat = false;
		try {
			if ("Activate".equals(status) ) {
				opr_stat = true;

				String result1 = selectIccidStatus(iccid, imsi);
				JSONObject resultResponseJson1 = JSONObject.fromObject(result1);
				String data1 = resultResponseJson1.getString("data");
				JSONObject dataJson1 = JSONObject.fromObject(data1);
				String status1 = dataJson1.getString("status");
				logger.info("激活时查询卡片状态=" + status1);
				if (status1.equals("TestReady")) {
					result = setIccidStatusInner(iccid, imsi, "PreActive");
					giccid = String.copyValueOf(iccid.toCharArray());
					gimsi = String.copyValueOf(imsi.toCharArray());

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							try {
								setIccidStatusInner(giccid, gimsi, "Activate");
								this.cancel();

							} catch (Exception e) {
							}

						}
					}, 1000 * 60 * 3 // 3 minutes
					);

					return result;
				} else if (status1.equals("Suspended")) {
					result = setIccidStatusInner(iccid, imsi, "Resume");
					return result;
				} else {
					// 如果是Preactive 等
					result = setIccidStatusInner(iccid, imsi, "Activate");
					return result;
				}
			}
			String jiami = Des.DesEncrypt(json.toString(), "!@#$%^&*");
			result = Des.sendPostEntity(url, jiami);
			
			// yonghu add
			ComPay cp = new ComPay();
			if ("Activate".equals(status) || "Resume".equals(status))
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_ACT_TRAN);
			else
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_DEACT_TRAN);
			cp = null;

		} catch (Exception e) {
			e.printStackTrace();

			// yonghu add
			ComPay cp = new ComPay();
			if (opr_stat)
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_ACT_EXCEPT);
			else
				cp.recActFile(iccid, ComPay.ActActionEnum.ACTION_DEACT_EXCEPT);
			cp = null;

		}
		logger.info("setIccidStatus请求 卡片状态设置结果=" + result);
		return Des.DesDecrypt(result, "!@#$%^&*");
	}

	public static String selectIccidXiangQing(String iccid, String imsi)
			throws Exception {
		String url = testUrl2 + "iotApiController/products/search.do";
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("iccid", iccid);
		json1.put("imsi", imsi);
		json.put("data", json1);
		json.put("sysPassword", "123456");
		json.put("sysAccount", "SYS_WTWD");
		System.out.println(json.toString());
		String result = "";
		try {
			String jiami = Des.DesEncrypt(json.toString(), "!@#$%^&*");
			// result = HttpRequest.urlReturnParams(url,jiami);
			result = Des.sendPost(url, jiami);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Des.DesDecrypt(result, "!@#$%^&*");
	}

	public static String getStatus(String iccid, String imsi) throws Exception {
		String resultResponse = AiShiDeIccidApiV2
				.selectIccidStatus(iccid, imsi);
		JSONObject resultResponseJson = JSONObject.fromObject(resultResponse);
		String data = resultResponseJson.getString("data");
		JSONObject dataJson = JSONObject.fromObject(data);
		String Status = dataJson.getString("status");
		return Status;
	}

}
