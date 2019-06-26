package com.wtwd.sys.appinterfaces.newpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Iterator;
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
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;

public class validIccidAction extends PayBase {

	
	Log logger = LogFactory.getLog(validIccidAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		//long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		//Date start = new Date();
		if (getLrjo() == null) {			
			setLrjo(new JSONObject());
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tools tls = new Tools();
		try {			
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			setLjo(JSONObject.fromObject(sb.toString()));
			
			proMaster();
			
			getLrjo().put("request", href);
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
			getLrjo().put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);			
			result = Constant.EXCEPTION_CODE;
			getLrjo().put(Constant.EXCEPTION, sb.toString());
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getLrjo().toString());
		return null;
	}

	
	void proMaster() throws Exception {	
		
		Tools tls = new Tools();		
		if (getLrjo() == null)
			setLrjo(new JSONObject());
		if (getLjo() == null)
			setLjo(new JSONObject());		
		
		try {			
			
			String iccid = getLjo().optString("iccid");	
			//String userId = ljo.optString("user_id");	
			//String appToken = ljo.optString("app_token");
			String imei=getLjo().optString("imei");
			String cstat = null;
			
			if ( !tls.isNullOrEmpty(iccid)) {

				DeviceActiveInfo vo1 = new DeviceActiveInfo();
				//vo1.setCondition("iccid ='" + iccid
				//		+ "' limit 1");
				String sid = "";
				/*
				List<DataMap> list = ServiceBean
						.getInstance()
						.getDeviceActiveInfoFacade()
						.getSsidInfo(vo1);
						*/
				List<DataMap> list = checkIccid(iccid);

				if (list != null) {
					getLrjo().put(Constant.RESULTCODE, Constant.FAIL_CODE);
					getLrjo().put(ComPay.TXT_MSG,ComPay.TXT_FAULT_ICCID);
				} else {
					cstat = list.get(0).get("card_status")+"";
					String belongCompany=list.get(0).get("belong_company")+"";
				
					vo1.setCondition("iccid ='" + iccid + "' limit 1");
					List<DataMap> lsitimei = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().getDeviceActiveInfo(vo1);
					if(lsitimei.size()<=0){
						vo1.setCondition("device_imei ='" + imei+"'");
						vo1.setIccid(iccid);
						vo1.setBelongCompany(belongCompany);
						
						ServiceBean.getInstance()
						.getDeviceActiveInfoFacade()
						.updateDeviceActiveInfo(vo1);
						
						getLrjo().put("imsi", list.get(0).get("imsi")+"");
						getLrjo().put("belong_company",belongCompany );
						getLrjo().put("card_status", cstat);
						getLrjo().put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
						getLrjo().put("imei","");
						getLrjo().put(ComPay.TXT_MSG,"和设备绑定成功");
					}else{
						getLrjo().put("imsi", list.get(0).get("imsi")+"");
						getLrjo().put("belong_company",belongCompany );
						getLrjo().put("card_status", cstat);
						getLrjo().put(Constant.RESULTCODE, Constant.INVALID_DATA);
						getLrjo().put("imei", lsitimei.get(0).get("device_imei")+"");
						getLrjo().put(ComPay.TXT_MSG,"已和设备绑定过");
					}
				}
			} else {
				getLrjo().put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);
				logger.info("iccid为空");
				getLrjo().put(ComPay.TXT_MSG,ComPay.TXT_EMPTY_ICCID);
				
				result = 2;
				getLrjo().put("card_status", "未找到对应的iccid");
			}
		
			getLrjo().put("iccid", iccid);
			
		} catch (Exception e) {
			logger.info( ComPay.TXT_PAY_LOG_TAG + "validIccidAction proMaster Exception");			
			throw e;
		}
		
	}
	
    public static void protest2() {
    	
    	String os = System.getProperty("os.name");
    	System.out.println("os.name=" + os);
        Map<String, String> map = System.getenv();
        for(Iterator<String> itr = map.keySet().iterator();itr.hasNext();){
            String key = itr.next();
            System.out.println(key + "=" + map.get(key));
        }   
    }	
	
    
    public static void proMakeMd5Users() {
    	Tools tls = null;
    	try {
    		tls = new Tools();
	    	WappUsers vo = new WappUsers();
	    	WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
	    	vo.setCondition("user_id>0");
	    	List<DataMap> list = fd.getWappUsers(vo);

	    	for (Iterator it = list.iterator(); it.hasNext();) {  
                DataMap e = (DataMap) it.next();  
	    		String pwd = e.getAt("passwd").toString().trim();
	    		if ( !tls.isNullOrEmpty(pwd) ) {
		    		Integer user_id = (Integer)e.getAt("user_id");
		    		String md5 = tls.getMd5(pwd);
			    	vo.setCondition("user_id=" + user_id);
			    	vo.setPassmd(md5);
			    	fd.updateWappUsers(vo);
	    		}

            }  
	    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public static void proTest1() throws Exception {
		//proMakeMd5Users();		
		Tools tls = new Tools();
		String res = tls.getMd5("352138064955869");
		System.out.println("352138064953575 md5 is " + res);

		String res1 = tls.getMd5("352138064991203");
		System.out.println("352138064991203 md5 is " + res1);

		String res2 = tls.getMd5("352138064969365");
		System.out.println("352138064969365 md5 is " + res2);
		
		
		validIccidAction va = new validIccidAction();
		
		JSONObject jo = new JSONObject();
		jo.put("iccid", "893107061704935485");
		jo.put("imei", "352138064985734");  // 352138064985734
		va.setLjo(jo);
		//va.proMaster();
		
	}
	
	static void protest3() {
		WTDevHandler wdh = new WTDevHandler();

		String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"6\",\"battery\":" +
				"\"100\",\"stepNumber\":\"23562\",\"lctTime\":\"2017-11-23 07:55:09\"," +
				"\"wifi\":{\"smac\":\"20:72:0d:39:01:56\",\"mmac\":\"\",\"macs\":" +
				"\"2c:dd:95:45:65:bd,-38,ChinaNet-CNYf|c8:3a:35:8b:e7:31,-46,cafe|c8:3a:35:38:2b:50," +
				"-62,slaverouter|30:fc:68:87:97:4a,-75,ziroom15?5A|8c:a6:df:4b:f7:6e,-76," +
				"Sylvia|bc:46:99:82:ac:c2,-77,JINJIN|6c:5a:b5:f1:09:04," + 
				"-80,HHT_10_3_20_175_|50:bd:5f:77:31:0d,-82,tao 2.4g|f4:9e:ef:a2:b8:7f," + 
				"-85,ChinaNet-bVQ6|bc:46:99:9f:86:ac,-87,CATSAD|0a:25:93:77:fa:ea," + 
				"-89,GardenCity\",\"serverip\":\"\"},\"network\":{\"network\":\"15\","+ 
				"\"cdma\":0,\"imei\":\"352138064952635\",\"smac\":\"20:72:0d:39:01:56\","+ 
				"\"bts\":\"460,01,42287,23079661,-108\",\"nearbts\":\"460,01,-1,-1,-101|460," + 
				"01,-1,-1,-101\",\"serverip\":\"10.224.107.84\"}}"; 
				
		/*String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"75\",\"stepNumber\":\"1319\",\"lctTime\":"
				+ "\"2017-02-21 14:46:44\",\"wifi\":{\"smac\":"
				+ "\"00:08:22:f2:df:fb\",\"mmac\":\"\",\"macs\":"
				+ "\"9c:21:6a:73:3e:70,-59,6ben|fc:d7:33:2b:4f:5c,-63,WTWD18|58:1f:28:82:bd:a5,-65,waterworld1|12:52:cb:61:3f:b3,-72,jigang\","
				+ "\"serverip\":\"\"}}";*/

		wdh.processCmd(msg, null);
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//protest3();
		
		//protest2();
		
		proTest1();
		
		DeviceActiveInfo vo1 = new DeviceActiveInfo();
		String imei="352138064955869";
		vo1.setCondition("device_imei ='" + imei+"'");
		vo1.setIccid("1");
		vo1.setBelongCompany("1");
		
		try {
			
			ServiceBean.getInstance()
			.getDeviceActiveInfoFacade()
			.updateDeviceActiveInfo(vo1);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
