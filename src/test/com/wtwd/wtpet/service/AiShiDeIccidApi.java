package test.com.wtwd.wtpet.service;

import net.sf.json.JSONObject;

import com.wtwd.common.lang.Des;
import com.wtwd.sys.appinterfaces.newpay.ComPay;

public class AiShiDeIccidApi {
	
	//private final static String testUrltest ="https://test24.uyou.com/";
    //private final static String testUrl="https://gman.uyou.com/";
    private final static String testUrl2 = "http://uphone.uyou.com/iot/";

    public static void main(String[] args) {
    	
	    try {
	    String result=selectIccidStatus("893107051704919148","204047914919148");
	    System.out.println("结果="+result);
	 	JSONObject resultResponseJson = JSONObject
				.fromObject(result);
		String data=resultResponseJson.getString("data");
		JSONObject dataJson = JSONObject
				.fromObject(data);
		String Status=dataJson.getString("status");
		System.out.println(Status);
		
	     /*		结果={"resp_desc":"OK","resp_code":"1","data":{"status":{"flag":true,"msg":"Suspended"}}}*/
				//String result=setIccidStatus("893107051704919148","204047914919148","Suspend");
	     //Activate,Suspend,Resume
							
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    	/*
	    	 * 
	    	 * https://域名/userver/iotApiController/products/getStatus.do
		try {
			String b=setIccidStatus("11111111111111111111","11111111111111111111","Deactivate");
			System.out.println(b);
			查询sim卡状态
		 * String a = selectIccidStatus("11111111111111111111","11111111111111111111");
			System.out.println(a);
			
			//Activate,Suspend,Deactivate
			String c=selectIccidXiangQing("11111111111111111111","11111111111111111111");
			JSONObject object = JSONObject.fromObject(c);
			System.out.println("啊哈="+object.getString("resp_code"));
			System.out.println(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
  */}

	// 查詢卡片狀態
	public static String selectIccidStatus(String iccid,String imsi) throws Exception {
		//String url = testUrl+"userver/iotApiController/products/getStatus.do";
		String url = testUrl2+"iotApiController/products/getStatus.do";
		
	    //System.out.println(url);
		//https://gman.uyou.com/userver/iotApiController/products/getStatus.do
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		json1.put("iccid", iccid);
		json1.put("imsi", imsi);
		json.put("data", json1);
		json.put("sysPassword", "123456");
		json.put("sysAccount", "SYS_WTWD");
		//System.out.println(json.toString());
		String result = "";
		try {
			String jiami = Des.DesEncrypt(json.toString(),"!@#$%^&*");
		//	System.out.println("加密="+jiami);
			// result = HttpRequest.urlReturnParams(url,jiami);
			//result = Des.sendPost(url, jiami);
			result = Des.sendPostEntity(url, jiami);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Des.DesDecrypt(result,"!@#$%^&*");
	}
	
	//卡片状态设置
	public static String setIccidStatus(String iccid,String imsi,String status) throws Exception{

		String url = testUrl2+"iotApiController/products/setStatus.do";
		System.out.println(url);
		//https://域名/ userver /iotApiController/products/setStatus.do
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
			if ( "Activate".equals(status) /*|| 
					"Resume".equals(status) */ ) {
				opr_stat = true;
				
		         String result1=selectIccidStatus(iccid, imsi);     
		      	JSONObject resultResponseJson1 = JSONObject
		 				.fromObject(result1);
				String data1=resultResponseJson1.getString("data");
				JSONObject dataJson1 = JSONObject
						.fromObject(data1);
				String Status1=dataJson1.getString("status");
				if (Status1.equals("TestReady")) {
					result = setIccidStatusInner(iccid, imsi, "PreActive");
					
				String 	giccid = String.copyValueOf(iccid.toCharArray());
				String	gimsi = String.copyValueOf(imsi.toCharArray());
					
					Thread.sleep(1000 * 5 * 2);
					result = setIccidStatusInner(iccid, imsi, "Activate");
					
					return result;
				} else if (Status1.equals("Suspended")) {
					result = setIccidStatusInner(iccid, imsi, "Resume");
					return result; 
				} else {
					
					result = setIccidStatusInner(iccid, imsi, "Activate");
					return result; 					
				}
					
			} 

			String jiami = Des.DesEncrypt(json.toString(),"!@#$%^&*");
			result = Des.sendPostEntity(url, jiami);
			//yonghu add
			ComPay cp = new ComPay();
			if ( "Activate".equals(status) || "Resume".equals(status) )
				cp.recActFile(iccid, ComPay.ActionEnum.ACTION_ACT_TRAN);
			else
				cp.recActFile(iccid, ComPay.ActionEnum.ACTION_DEACT_TRAN);
			cp = null;

		} catch (Exception e) {
			e.printStackTrace();

			//yonghu add
			ComPay cp = new ComPay();
			if ( opr_stat )
				cp.recActFile(iccid, ComPay.ActionEnum.ACTION_ACT_EXCEPT);
			else
				cp.recActFile(iccid, ComPay.ActionEnum.ACTION_DEACT_EXCEPT);
			cp = null;
			
			
		}
		System.out.println(result);
		return Des.DesDecrypt(result,"!@#$%^&*");
	}
	
	//卡片状态设置
	public static String setIccidStatusInner(String iccid,String imsi,String status) throws Exception{
		//String url = testUrl+"userver/iotApiController/products/setStatus.do";
		String url = testUrl2+"iotApiController/products/setStatus.do";

		System.out.println(url);
		//https://域名/ userver /iotApiController/products/setStatus.do
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
		if ( "Activate".equals(status) || "Resume".equals(status) ) {
			opr_stat = true;
		}
		
		try {
			String jiami = Des.DesEncrypt(json.toString(),"!@#$%^&*");
			result = Des.sendPostEntity(url, jiami);
			//result = Des.sendPostEntity(url, json.toString());

			result = Des.DesDecrypt(result,"!@#$%^&*");
			
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		//return Des.DesDecrypt(result,"!@#$%^&*");
	}
		

	public static String selectIccidXiangQing(String iccid,String imsi) throws Exception {
		//String url = testUrl+"userver/iotApiController/products/search.do";
		String url = testUrl2+"iotApiController/products/search.do";

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
			String jiami = Des.DesEncrypt(json.toString(),"!@#$%^&*");
			// result = HttpRequest.urlReturnParams(url,jiami);
			result = Des.sendPost(url, jiami);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Des.DesDecrypt(result,"!@#$%^&*");
	}
	
	public  static String getStatus(String iccid,String imsi) throws Exception{
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
		return Status;
	} 

}
