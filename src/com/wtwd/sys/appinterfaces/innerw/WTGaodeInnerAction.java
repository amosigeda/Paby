package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.other.NetWorkInfoAdr;
import com.wtwd.common.bean.other.WifiInfoAdr;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.http.HttpRequest;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;


public class WTGaodeInnerAction extends BaseAction {


	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTSigninAction.class);

	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			
			
			JSONObject object = JSONObject.fromObject(sb.toString());

			super.logAction(Tools.OneString,1, "WTGaodeInnerAction");			
			
			proQuery(object);
			
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
	
	
	void proQuery(JSONObject object) throws SystemException, InterruptedException {
		Tools tls = new Tools();
		String pwd = object.optString("pwd");
		String imei = object.optString("imei");
		if ( json == null )
			json = new JSONObject();

		if ( !"afdzczb$nvn@mv:mn4259i-0".equals(pwd) ) {
			result = Constant.FAIL_CODE;			
			return;
		}

		ReqJsonData reqWifi = JSON.parseObject(object.optString("wifi"), ReqJsonData.class);
		ReqJsonData reqNet = JSON.parseObject(object.optString("net"), ReqJsonData.class);

		NetWorkInfoAdr netWorkInfo = reqNet.getNetWork();
		String network = netWorkInfo.getNetWork();
		String cdma = netWorkInfo.getCdma();
		String smac = netWorkInfo.getSmac();
		String bts = netWorkInfo.getBts();
		String nearbts = netWorkInfo.getNearbts();
		String serverip = netWorkInfo.getServerip();
		
		WifiInfoAdr wifi = reqWifi.getWifi();
//		String smac = wifi.getSmac();
		String mmac = wifi.getMmac();
		String macs = wifi.getMacs();
//		String serverip = wifi.getServerip();
		
		SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		map.put("accesstype", Tools.ZeroString);
		map.put("network", network);
		map.put("cdma", cdma);
		map.put("imei", imei);
		map.put("smac", smac);
		map.put("bts", bts);
		map.put("nearbts", nearbts);
		map.put("mmac", mmac);
		map.put("macs", macs);
		map.put("key", Constant.KEY);
//		if (serverip != null && !"".equals(serverip)) {
//			map.put("serverip", serverip);
//		} else {
//			map.put("serverip", Constant.SERVER_IP);
//		}
		map.put("serverip", serverip);
		
		try {
			String jsonToString = HttpRequest.sendGetToGaoDe(
					Constant.LOCATION_URL, map);
			json.put( "res", jsonToString);
			result = Constant.SUCCESS_CODE;							
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("WTGaodeInnerAction proQuery exception");
		}
		
		
		//result = Constant.SUCCESS_CODE;				
		//result = Constant.FAIL_CODE;			//不在线		
	}
	

	public static void main(String[] args) throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("wifi", "{\"wifi\":{\"smac\":\"20:72:0d:39:01:56\",\"mmac\":\"\",\"macs\":\"2c:dd:95:45:65:bd,-38,ChinaNet-CNYf|c8:3a:35:8b:e7:31,-46,cafe|c8:3a:35:38:2b:50,-62,slaverouter|30:fc:68:87:97:4a,-75,ziroom15?5A|8c:a6:df:4b:f7:6e,-76,Sylvia|bc:46:99:82:ac:c2,-77,JINJIN|6c:5a:b5:f1:09:04,-80,HHT_10_3_20_175_|50:bd:5f:77:31:0d,-82,tao 2.4g|f4:9e:ef:a2:b8:7f,-85,ChinaNet-bVQ6|bc:46:99:9f:86:ac,-87,CATSAD|0a:25:93:77:fa:ea,-89,GardenCity\",\"serverip\":\"\"}}");
		jo.put("net", "{\"network\":{\"network\":\"15\",\"cdma\":0,\"imei\":\"352138064952635\",\"smac\":\"20:72:0d:39:01:56\",\"bts\":\"460,01,42287,23079661,-108\",\"nearbts\":\"460,01,-1,-1,-101|460,01,-1,-1,-101\",\"serverip\":\"10.224.107.84\"}}" );
		jo.put("pwd", "afdzczb$nvn@mv:mn4259i-0");
		
		WTGaodeInnerAction wgi = new WTGaodeInnerAction();
		
		
		wgi.proQuery(jo);
	}	
	

}
