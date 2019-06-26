//yonghu create 20160625 label

package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class WTDevGWAction extends BaseAction{

	Log logger = LogFactory.getLog(WTDevGWAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		//Date start = new Date();
		JSONObject json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}			

			String res;
			logger.info("WTDevGWAction params:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_id = object.getString("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTDevGWAction:" + sb.toString() );			
			
			res = proCom(sb.toString());
			
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(res);
			logger.info("WTDevGWAction res:" + res);
			return null;

		}catch(Exception e){
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

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
	
		return null;
	}	

	public String proCom(String paras) {
		String res = "{\"resulctCode\":0}";
    	String urlNameString = null;            	                
    	String params = null;  
    	
    	try {
    		JSONObject jon = JSONObject.fromObject(paras);
    		    		
			PhoneInfo po = new PhoneInfo();
			po.setCondition("device_imei ='" + jon.getString("imei") + "'order by id desc  limit 1");

			List<DataMap> list = ServiceBean.getInstance().getPhoneInfoFacade()
					.selectDeviceEnterServiceActiveInfo(po);
    		
			String si = "";
			String hp = "";
			
			if ( list.isEmpty() ) {
				po.setCondition("serie_no ='" + jon.getString("imei") + "'order by id desc  limit 1");

				List<DataMap> list1 = ServiceBean.getInstance()
						.getPhoneInfoFacade()
						.getPhoneInfo(po);
				
				if ( !list1.isEmpty() ) {
					String pm = list1.get(0).getAt("product_model").toString().trim();
					if ("A907".equals(pm)) {
						si = Constant.SYS_SERVER_DN2;
						hp = "8087"; //换之前是8080
					} else {
						si = Constant.SYS_SERVER_DN1;
						hp = "8087";
					}	
				}
				
			} else {
				si = list.get(0).getAt("service_ip").toString().trim();
				hp = list.get(0).getAt("h_port").toString().trim();
			}

    		urlNameString = "http://" + si + ":" + hp + "/wtpet/" + jon.getString("lbl");
    		params = paras;
    		
    		res = httpPostInner(urlNameString, params);
    		return res;
    	} catch(Exception e) {
    		e.printStackTrace();
    		return res;
    	}

	}
	
	/* reference : http://www.cnblogs.com/linjiqin/archive/2011/09/19/2181634.html */
	public String httpPostInner(String urlNameString, String params ) throws IOException {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";

		String res = "{\"resultCode\":0}";
		String encoding="UTF-8";    

        byte[] data = params.getBytes(encoding);
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
	
		URL url =new URL(urlNameString);        
		HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		conn.setRequestMethod("POST");
        conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
		conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
		conn.setRequestProperty("Content-Length", String.valueOf(data.length)); 
		conn.setConnectTimeout(50*1000);    
		OutputStream outStream = conn.getOutputStream();       
		outStream.write(data);    
		outStream.flush();    
		outStream.close();      
		
		int code = conn.getResponseCode();
		if(code == 200){
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				sb.append(line);
			}				
			in.close();
			
			res = sb.toString();
			
		}
		
		return res;
		
	}
	
}
