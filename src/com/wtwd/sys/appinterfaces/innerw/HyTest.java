package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.deviceup.CmdUpPostImpl;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.EndServlet;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdevDiscovery;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

public class HyTest {

	private static  ServiceBean serviceBean = ServiceBean.getInstance();
	//private static  ServiceBean serviceBean = null;
	
	 public static void executeNewFlow() {
		          Runtime run = Runtime.getRuntime();
		          File wd = new File("/bin");
		          System.out.println(wd);
		          Process proc = null;
		          try {
		              proc = run.exec("/bin/bash", null, wd);
		          } catch (IOException e) {
		              e.printStackTrace();
		         }
		         if (proc != null) {
		             BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
		             out.println("cd /mnt/data/dump");
		             out.println("mysqldump -uroot -p'szdbcpet@p!tep' dbdog > dbdog.dump");
		             out.println("tar zcvf dbdog1.tar.gz dbdog.dump");
		             out.println("rm dbdog.dump");
		             out.println("exit");
		             try {
		                 String line;
		                 while ((line = in.readLine()) != null) {
		                     System.out.println(line);
		                 }
		                 proc.waitFor();
		                 in.close();
		                 out.close();
		                 proc.destroy();
		             } catch (Exception e) {
		                 e.printStackTrace();
		             }
		         }
		     }
	
	
		public static void testDbDump() {
			try {
				//String command = "/mnt/petDbDump.sh";
				//Runtime.getRuntime().exec(command);
				executeNewFlow();

				/*
				
				command = "mysqldump -uroot -p'szdbcpet@p!tep' dbdog > dbdog.dump";
				Process proc = Runtime.getRuntime().exec(command);
				System.out.print(proc.getInputStream().toString());

				System.out.print(proc.getErrorStream().toString());
				Thread.sleep(1000 * 300);
				command = "tar zcvf /mnt/data/dump/dbdog1.tar.gz /mnt/data/dump/dbdog.dump";
				Runtime.getRuntime().exec(command);				

				Thread.sleep(1000 * 300);
				
				command = "rm /mnt/data/dump/dbdog.dump";
				Runtime.getRuntime().exec(command);
				*/				

				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
		
		//abcd
		public static void main(String[] args) throws MessagingException, Exception {
		
			String abc1 = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"54\",\"stepNumber\":\"60855\",\"lctTime\":\"2017-12-15 14:02:44\",\"geolocation\":{\"homeMobileCountryCode\":230,\"homeMobileNetworkCode\":3,\"radioType\":\"gsm\",\"carrier\":\"Vodafone CZ\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":38633,\"locationAreaCode\":31015,\"mobileCountryCode\":230,\"mobileNetworkCode\":3,\"age\":0,\"signalStrength\":-107}],\"wifiAccessPoints\":[{\"macAddress\":\"50:c7:bf:12:55:2c\",\"signalStrength\":-55,\"age\":0,\"channel\":6},{\"macAddress\":\"a0:f3:c1:b9:8f:ec\",\"signalStrength\":-76,\"age\":0,\"channel\":11},{\"macAddress\":\"f8:8e:85:70:00:c5\",\"signalStrength\":-83,\"age\":0,\"channel\":11}]}}";
			 WTDevHandler sss2 = new WTDevHandler();
			 sss2.processCmd(abc1, null);

			WTAppDeviceManAction wt11 = new WTAppDeviceManAction();
			wt11.proTmCtl(1, 922, 30, true, null );
			wt11.proRcmCtl(1, 922, "352138064958939", "1234567", null);
			 
			/*
			 String abc = "{\"cmd\":\"uTMD\",\"ET\":\"23.2\",\"BT\":\"26.8\",\"stepNumber\":\"228\",\"lctTime\":\"2017-03-16 10:33:50\",\"wifi\":{\"smac\":\"20:72:0d:39:01:a4\",\"mmac\":\"\",\"macs\":\"b8:f8:83:fa:e1:a2,-33,CCS|c8:3a:35:2a:ec:e0,-51,office 4|d4:ee:07:15:39:fe,-57,SJYW|9c:21:6a:73:3e:70,-60,6ben|12:52:cb:61:3f:b3,-65,jigang|ec:88:8f:55:25:84,-66,LEIFAYU|3c:46:d8:13:71:5a,-66,TP-LINK_7S|d8:15:0d:c8:4a:52,-68,TP-LINK1111111|cc:81:da:01:b6:b8,-69,ajshin|64:09:80:66:a1:ab,-71,Xiaomi_2012-|00:87:46:17:d3:54,-71,xiaow|fc:d7:33:2b:4f:5c,-72,WTWD18\",\"serverip\":\"\"},\"network\":{\"network\":\"10\",\"cdma\":0,\"imei\":\"352138064952338\",\"smac\":\"20:72:0d:39:01:a4\",\"bts\":\"460,01,42287,23093706,-102\",\"nearbts\":\"460,01,-1,-1,-69\",\"serverip\":\"10.18.119.173\"}}";
			 WTDevHandler sss1 = new WTDevHandler();
			 sss1.processCmd(abc, null);*/
			
			LocationInfoHelper lih = new LocationInfoHelper();
			boolean ress = lih.IsAmericaDev(null);
			System.out.print(ress);
			lih.getGeoFromLatLng( "39.20277","-86.09219");
			
			
			testDbDump();
			
			WTAppGpsManAction sgma = new WTAppGpsManAction();
			String sgmaReq = "{\"device_id\":565, \"start_time\":\"2017-07-26 00:00:01\", \"end_time\":\"2017-07-26 04:00:01\", \"user_id\":1}";
			sgma.proGetDevTrackRoad(sgmaReq);
			
			String msg = "{\"cmd\":\"getLocationRes\",\"type\":\"5\",\"battery\":\"87\",\"userId\":169,\"stepNumber\":\"226\",\"lctTime\":\"2017-04-19 00:47:34\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"gsm\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":160071380,\"locationAreaCode\":26261,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-59}],\"wifiAccessPoints\":[{\"macAddress\":\"58:b6:33:95:c0:58\",\"signalStrength\":-55,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:95:cb:88\",\"signalStrength\":-56,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:55:cb:88\",\"signalStrength\":-57,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:15:cb:88\",\"signalStrength\":-58,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:15:c0:c8\",\"signalStrength\":-63,\"age\":0,\"channel\":1},{\"macAddress\":\"58:b6:33:15:c0:58\",\"signalStrength\":-63,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:55:c0:58\",\"signalStrength\":-63,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:55:c0:c8\",\"signalStrength\":-63,\"age\":0,\"channel\":1},{\"macAddress\":\"18:4f:32:9a:1e:c8\",\"signalStrength\":-65,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:15:bf:78\",\"signalStrength\":-66,\"age\":0,\"channel\":1},{\"macAddress\":\"58:b6:33:55:b2:88\",\"signalStrength\":-67,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:15:b2:88\",\"signalStrength\":-67,\"age\":0,\"channel\":6}]}}";
			//String msg = "{\"cmd\":\"getLocationRes\",\"type\":\"4\",\"battery\":\"86\",\"userId\":169,\"stepNumber\":\"302\",\"lctTime\":\"2017-04-19 00:48:17\",\"gps\":{\"lon\":\"-118.19751833333333\",\"lat\":\"33.77153333333333\",\"acc\":\"16.7\"}}";
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"86\",\"stepNumber\":\"324\",\"lctTime\":\"2017-04-19 00:48:29\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"gsm\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":160071380,\"locationAreaCode\":26261,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-59}],\"wifiAccessPoints\":[{\"macAddress\":\"86:2a:a8:0d:9c:af\",\"signalStrength\":-69,\"age\":0,\"channel\":11},{\"macAddress\":\"38:ff:36:0e:57:b8\",\"signalStrength\":-70,\"age\":0,\"channel\":8},{\"macAddress\":\"38:ff:36:4e:57:b8\",\"signalStrength\":-70,\"age\":0,\"channel\":8},{\"macAddress\":\"58:b6:33:55:b8:f8\",\"signalStrength\":-72,\"age\":0,\"channel\":1},{\"macAddress\":\"06:18:0a:79:da:8a\",\"signalStrength\":-73,\"age\":0,\"channel\":6},{\"macAddress\":\"0a:18:0a:79:da:8a\",\"signalStrength\":-74,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:95:b8:f8\",\"signalStrength\":-75,\"age\":0,\"channel\":1},{\"macAddress\":\"30:b5:c2:66:57:54\",\"signalStrength\":-75,\"age\":0,\"channel\":1},{\"macAddress\":\"64:ae:0c:ed:19:10\",\"signalStrength\":-75,\"age\":0,\"channel\":4},{\"macAddress\":\"58:b6:33:55:c2:a8\",\"signalStrength\":-77,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:15:c2:a8\",\"signalStrength\":-77,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:95:b2:88\",\"signalStrength\":-78,\"age\":0,\"channel\":6}]}}";
			//String msg = "{\"cmd\":\"getLocationRes\",\"type\":\"4\",\"battery\":\"96\",\"userId\":18,\"stepNumber\":\"39006\",\"lctTime\":\"2017-04-19 00:12:12\",\"gps\":{\"lon\":\"-118.19300333333334\",\"lat\":\"33.77001\",\"acc\":\"6.7\"}}";
			//String msg = "{\"cmd\":\"getLocationRes\",\"type\":\"5\",\"battery\":\"89\",\"userId\":169,\"stepNumber\":\"25\",\"lctTime\":\"2017-04-19 00:12:16\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"gsm\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":160071380,\"locationAreaCode\":26261,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-62}],\"wifiAccessPoints\":[{\"macAddress\":\"58:b6:33:95:bd:88\",\"signalStrength\":-36,\"age\":0,\"channel\":1},{\"macAddress\":\"58:b6:33:55:bd:88\",\"signalStrength\":-37,\"age\":0,\"channel\":1},{\"macAddress\":\"58:b6:33:15:bd:88\",\"signalStrength\":-37,\"age\":0,\"channel\":1},{\"macAddress\":\"b0:5a:da:60:a3:19\",\"signalStrength\":-60,\"age\":0,\"channel\":1},{\"macAddress\":\"58:b6:33:95:bc:78\",\"signalStrength\":-61,\"age\":0,\"channel\":11},{\"macAddress\":\"48:f8:b3:f3:f2:ca\",\"signalStrength\":-63,\"age\":0,\"channel\":11},{\"macAddress\":\"8a:15:04:f8:9f:41\",\"signalStrength\":-64,\"age\":0,\"channel\":11},{\"macAddress\":\"8a:15:04:f8:9f:40\",\"signalStrength\":-66,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:16:23:48\",\"signalStrength\":-67,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:56:23:48\",\"signalStrength\":-67,\"age\":0,\"channel\":6},{\"macAddress\":\"58:b6:33:15:bc:78\",\"signalStrength\":-68,\"age\":0,\"channel\":11},{\"macAddress\":\"58:b6:33:55:bc:78\",\"signalStrength\":-68,\"age\":0,\"channel\":11}]}}";
			//String msg = "{\"cmd\":\"getLocationRes\",\"type\":\"1\",\"battery\":\"85\",\"userId\":58,\"stepNumber\":\"2311\",\"lctTime\":\"2017-04-20 09:53:41\",\"gps\":{\"lon\":\"113.92175\",\"lat\":\"22.502506666666665\",\"acc\":\"2.8\"}}";
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"99\",\"stepNumber\":\"2083\",\"lctTime\":\"2017-04-25 03:43:15\",\"wifi\":{\"smac\":\"20:72:0d:39:01:a4\",\"mmac\":\"\",\"macs\":\"34:96:72:60:98:2d,-57,nanshan1#|a4:71:74:c5:32:a4,-63,HUAWEI-WY3FKE|6c:59:40:53:bf:de,-63,MERCURY_BFDE|34:b3:54:ac:ba:c4,-65,HUAWEI-JSAn|d8:32:5a:1e:1d:c4,-66,ChinaNet-arWA|56:96:72:00:04:d9,-66,yyzcool|64:09:80:77:70:4a,-67,Xiaomi_lilin|fc:d7:33:5d:5b:0e,-67,TP-706|d0:0f:6d:9c:1d:a7,-67,ChinaNet-an6H|b8:f8:83:39:ca:bd,-69,TP-LINK_yms2|78:a1:06:97:bc:16,-69,dink88|28:2c:b2:b1:71:dc,-70,401\",\"serverip\":\"\"},\"network\":{\"network\":\"15\",\"cdma\":0,\"imei\":\"352138064952338\",\"smac\":\"20:72:0d:39:01:a4\",\"bts\":\"460,01,42287,23092406,-68\",\"nearbts\":\"460,01,-1,-1,-77\",\"serverip\":\"10.78.251.153\"}}";
			//String msg = "{\"cmd\":\"upStepDatas\",\"stepDatas\":[{\"stepNumber\":482,\"starttime\":\"2017-04-26 05:33:33\",\"endtime\":\"2017-04-26 05:38:00\"},{\"stepNumber\":69,\"starttime\":\"2017-04-26 05:38:00\",\"endtime\":\"2017-04-26 05:39:00\"}]}";
			//String msg = "{\"cmd\":\"upDeviceInfo\",\"serie_no\":\"352138064958939\",\"device_product_model\":\"A907\",\"device_firmware_edition\":\"A907_V7.0\",\"device_phone\":\"\",\"b_g\":\"1\"}";
			//String msg = "{\"cmd\":\"ssidEsafeState\",\"ssidEsafeFlag\":\"0\",\"bssid\":\"58:2a:f7:f2:47:56\"}";
			
			ReqJsonData rjd =
			 JSON.parseObject(msg.toString(), ReqJsonData.class);
			
			
			
			WTDevHandler whlr = new WTDevHandler();
			whlr.proLctMgrBtr(rjd, "352138064952338", 80, "getLocationRes", null);
			//whlr.processCmd(msg, null);
			
			
//			String msg1 = "{\"considerIp\": \"false\", \"wifiAccessPoints\": [{ \"macAddress\": \"00:25:9c:cf:1c:ac\",\"signalStrength\": -43, \"signalToNoiseRatio\": 0 }, { \"macAddress\": \"00:25:9c:cf:1c:ad\", \"signalStrength\": -55, \"signalToNoiseRatio\": 0  }]}";
			
			
			
			DevNotifyApp dna = new DevNotifyApp();
			ReqJsonData reqJsonData = new ReqJsonData();
			
			reqJsonData.setUserId(1);
			reqJsonData.setSsidEsafeFlag("1");
			reqJsonData.setBssid("ad-as-as-ds-dd");
			//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
			dna.proSsidEsafeState(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData, "{\'ssidEsafeFlag\":0}", "your pet left fence", "0");
			
			
			WTAppDeviceManAction wdma = new WTAppDeviceManAction();
			wdma.proReqBind3("", 2, "352138064952338", "1");
			
			CmdDownSetImpl csi = new CmdDownSetImpl();
			csi.ProDevStat("352138064955414", 0);
			
			
			WTAppDeviceManAction ada1 = new WTAppDeviceManAction();
			ada1.proQueryAllList("", 13, "" );
			
			Integer play_flag = -1;			
			
			String ttt = String.valueOf(play_flag);
			System.out.println(ttt);
			
			
			
			WTFindPasswordAction wpa1 = new WTFindPasswordAction();
			wpa1.CreateMessage("59.167.239.62,12341", "413414", "hyong@waterworld.com.cn");
			
			Date dtt = new Date();
			System.out.print("dtt:" + dtt.toString());
			System.out.print("dtt long:" + dtt.getTime());
			

			try {
				
				CmdUpPostImpl cmdUpPostImpl = new CmdUpPostImpl();
				String msgu = "{\"cmd\":\"upStepDatas\",\"stepDatas\":[{\"stepNumber\":83,\"starttime\":\"2017-05-05 07:31:00\",\"endtime\":\"2017-05-05 07:32:00\"},{\"stepNumber\":74,\"starttime\":\"2017-05-05 07:32:00\",\"endtime\":\"2017-05-05 07:33:00\"},{\"stepNumber\":105,\"starttime\":\"2017-05-05 07:33:00\",\"endtime\":\"2017-05-05 07:34:00\"},{\"stepNumber\":95,\"starttime\":\"2017-05-05 07:34:00\",\"endtime\":\"2017-05-05 07:35:00\"},{\"stepNumber\":112,\"starttime\":\"2017-05-05 07:35:00\",\"endtime\":\"2017-05-05 07:36:00\"},{\"stepNumber\":110,\"starttime\":\"2017-05-05 07:36:00\",\"endtime\":\"2017-05-05 07:37:00\"},{\"stepNumber\":109,\"starttime\":\"2017-05-05 07:37:00\",\"endtime\":\"2017-05-05 07:38:00\"},{\"stepNumber\":104,\"starttime\":\"2017-05-05 07:38:00\",\"endtime\":\"2017-05-05 07:39:00\"}]}";
				
				RespJsonData respJson = cmdUpPostImpl.stepDatasPost(reqJsonData, null);
				
				
				WpetMoveInfo wmv = new WpetMoveInfo();			
				WpetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getWpetMoveInfoFacade();
				//首先后台需要检查并生成报告数据
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("rpet_id", "1181");
				p.put("rend_day", "2017-06-05 23:59:59");
				wpmFacade.mycatCalcSugExecListSe(p);
			} catch(Exception e) {
				
			}
			
			
			System.out.print("dtt long:" + dtt.getTime());

			WTAppGpsManAction wt1 = new WTAppGpsManAction();
			wt1.proGetDevsList(1);
			
			
			//CmdDownSetImpl csi = new CmdDownSetImpl();
			//csi.ProDevStatTest("352138064955489", 0);
			
			//String abc = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"100\",\"stepNumber\":\"228\",\"lctTime\":\"2017-03-16 10:33:50\",\"wifi\":{\"smac\":\"20:72:0d:39:01:a4\",\"mmac\":\"\",\"macs\":\"b8:f8:83:fa:e1:a2,-33,CCS|c8:3a:35:2a:ec:e0,-51,office 4|d4:ee:07:15:39:fe,-57,SJYW|9c:21:6a:73:3e:70,-60,6ben|12:52:cb:61:3f:b3,-65,jigang|ec:88:8f:55:25:84,-66,LEIFAYU|3c:46:d8:13:71:5a,-66,TP-LINK_7S|d8:15:0d:c8:4a:52,-68,TP-LINK1111111|cc:81:da:01:b6:b8,-69,ajshin|64:09:80:66:a1:ab,-71,Xiaomi_2012-|00:87:46:17:d3:54,-71,xiaow|fc:d7:33:2b:4f:5c,-72,WTWD18\",\"serverip\":\"\"},\"network\":{\"network\":\"10\",\"cdma\":0,\"imei\":\"352138064952338\",\"smac\":\"20:72:0d:39:01:a4\",\"bts\":\"460,01,42287,23093706,-102\",\"nearbts\":\"460,01,-1,-1,-69\",\"serverip\":\"10.18.119.173\"}}";
			 //WTDevHandler sss = new WTDevHandler();
			 //sss.processCmd(abc, null);
			
			
			
			
			
			/*
			WTAppMsgManAction amma = new WTAppMsgManAction();
			JSONObject jo = new JSONObject();
			jo.put("user_id", 1);
			amma.proPGet(jo);
			*/
			WTDevGWAction wdloa = new WTDevGWAction();
			JSONObject jo1 = new JSONObject();
			
			jo1.put("imei", "352138064958939");
			jo1.put("app_token", "33008");
			jo1.put("user_id", 1);
			jo1.put("cmd", "set");
			jo1.put("device_id", 922);
			jo1.put("cmd_time", "2017-05-12 10:00:00");
			jo1.put("duration", "30");
			jo1.put("lbl", "doWTDevLCTOpt.do");
			jo1.put("uLFq", 0);
			jo1.put("uLFqMode", 1);
			jo1.put("uLSosWifi", 0);
			
			wdloa.proCom(jo1.toString());
			
			WTDevGWAction wdga = new WTDevGWAction();
			JSONObject jo = new JSONObject();
        	//String urlNameString = "http://appserver1.paby.com/wtpet/doWTDevGW.do";            	                
        	//String params = "{\"imei\":\"352138064955414\",
			//\"app_token\":\"58489\",
			//\"user_id\":\"1\",
			//\"cmd\":\"on\",\"device_id\":\"570\",
			//\"cmd_time\":\"2017-05-12 9:07:01\",
			//\"duration\":\"30\"}";            	
			
			jo.put("imei", "352138064955414");
			jo.put("app_token", "07234");
			jo.put("user_id", 1);
			jo.put("cmd", "on");
			jo.put("device_id", 570);
			jo.put("cmd_time", "2017-05-12 10:00:00");
			jo.put("duration", "30");
			jo.put("lbl", "doWTAppDiscoveryMan.do");
			
			
			
			
			wdga.proCom(jo.toString());
			
		    WdevDiscovery devDisc = new WdevDiscovery();
			devDisc.setCondition("device_id = 922");
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = serviceBean.getWdeviceActiveInfoFacade();
			
			List<DataMap> devDiscs = wdeviceActiveInfoFacade.getDevDiscovery(devDisc);
			String abcC = devDiscs.get(0).
							getAt("action_time").toString().trim();
			System.out.println(abcC);
			
			
			
			
			String dir = "D:/resin-4.0.48/webapps/DogManage/";
			dir = dir.substring(0, dir.length() -1);
			dir = dir.substring(0,dir.lastIndexOf('/'));
			dir = dir.substring(0, dir.length() -1);			
			/*
			WTAppDeviceManAction wda = new WTAppDeviceManAction(); 
			wda.proReqHostAgreeShare("", 1, 1845);
			*/
			
			Tools tls = new Tools();
			if (tls.StrisNumeric("-asdfasf"))
				System.out.println("is  number");

			if (tls.StrisNumeric("-2"))
				System.out.println("is  number2");
			
			if (tls.StrisNumeric("1234"))
				System.out.println("is  number3");
			
			if (tls.StrisNumeric(""))
				System.out.println("is  number4");

			if (tls.StrisNumeric(" "))
				System.out.println("is  number5");
			String usr = null;
			if (tls.StrisNumeric(usr))
				System.out.println("is  number6");
			
			
			String msgStr = "{adfadfa}";
			if ( msgStr.startsWith("{") && msgStr.endsWith("}") ) {
				System.out.println("abc");				
			}
			msgStr = "{adfadfa";
			if ( msgStr.startsWith("{") && msgStr.endsWith("}") ) {
				System.out.println("cba");				
			}
			
			
			double dLo = -118.1893752;
			double dLa = 33.7665686;

			double cLo = -118.1937047;
			double cLa = 33.7653439;
			
			
			//WhttpPostAs wp = new WhttpPostAs();
			//wp.httpPostInner("http://appserver1.paby.com:8161", null);
			
			
			//计算本次移动后与所有电子围栏中心点的距离
			//Integer dist = Integer.parseInt( Distribution.distanceGaode(key, origins, destination).trim() );
			//double dist = Constant.getDistance(cLa, cLo, dLa, dLo);
			//LocationInfoHelper lih = new LocationInfoHelper();
			//lih.getGeoFromLatLng( "33.770653","-118.19705");
			
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"57\",\"stepNumber\":\"10730\",\"lctTime\":\"2017-04-15 01:17:20\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"gsm\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":160071379,\"locationAreaCode\":26261,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-64}],\"wifiAccessPoints\":[{\"macAddress\":\"ac:84:c9:82:e0:7e\",\"signalStrength\":-87,\"age\":0,\"channel\":1},{\"macAddress\":\"18:d6:c7:4d:6c:86\",\"signalStrength\":-89,\"age\":0,\"channel\":9},{\"macAddress\":\"40:c7:29:4c:b9:e2\",\"signalStrength\":-89,\"age\":0,\"channel\":11},{\"macAddress\":\"04:a1:51:9b:c4:ea\",\"signalStrength\":-91,\"age\":0,\"channel\":3}]}}";
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"57\",\"stepNumber\":\"10730\",\"lctTime\":\"2017-04-15 01:17:20\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"gsm\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":160071379,\"locationAreaCode\":26261,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-64}]}}";\
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"74\",\"stepNumber\":\"29846\",\"lctTime\":\"2017-04-17 01:16:40\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":173155713,\"locationAreaCode\":26259,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-76}],\"wifiAccessPoints\":[{\"macAddress\":\"f0:ab:54:60:ad:13\",\"signalStrength\":-90,\"age\":0,\"channel\":11}]}} ";
			
			//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"5\",\"battery\":\"74\",\"stepNumber\":\"29846\",\"lctTime\":\"2017-04-17 01:16:40\",\"geolocation\":{\"homeMobileCountryCode\":\"310\",\"homeMobileNetworkCode\":\"26\",\"radioType\":\"cdma\",\"carrier\":\"Twilio\",\"considerIp\":\"false\",\"cellTowers\":[{\"cellId\":173155713,\"locationAreaCode\":26259,\"mobileCountryCode\":\"310\",\"mobileNetworkCode\":\"26\",\"age\":0,\"signalStrength\":-76}]}} ";
			
			
			//WTFindPasswordAction wpa = new WTFindPasswordAction();
			//wpa.CreateMessage(wpa.getServerName(), "mqSvrStop", "461261532@qq.com");
			//wpa.ProForgetPassword("461261532@qq.com", "1");


			
			
			 
			
			
		}
		
}


class WhttpPostAs {
	public WhttpPostAs() {
		
	}
	
	private static WhttpPostAs aIns= null;
	
	public static WhttpPostAs getInstance() {
		if (aIns == null) {
			aIns = new WhttpPostAs();
			return aIns;
		} else
			return aIns;
			
	}
	public /*staticsynchronized*/ void httpPostInner( String urlNameString,  String params ) {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";
		try {
			Logger.getLogger(EndServlet.class).info("mqSver:httpPostInner");
			
			String encoding="UTF-8";    
			
			
	        byte[] data = null;
	        if (params != null )
	        	data = params.getBytes(encoding);

			//BufferedReader in = null;
			//StringBuffer sb = new StringBuffer();
			
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			//conn.setRequestMethod("POST");
	        //conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
	        if (params != null )
	        	conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	        else
	        	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0");
	        	
	        	
			conn.setConnectTimeout(120*1000);
	        if (params != null ) {			
				OutputStream outStream = conn.getOutputStream();       
				outStream.write(data);    
				outStream.flush();    
				outStream.close();    
	        }
	        try {
				int code = conn.getResponseCode();
				if(code >= 400){
					/*
					WMsgInfo wi = new WMsgInfo();
					wi.setApp_type("1");
					wi.setBadge(0);
					wi.setDevice_id(80);
					wi.setHide_flag("0");
					wi.setIos_real("5272e4539717fbfa4b409b894161a66afb02917f720d8f56c8948b64564be195");
					wi.setSummary("mqtt server stopped!");
					wi.setTo_usrid(1);
					String strMsg = JSON.toJSONString(wi);
					ServiceBean.getInstance().pushMsg(
							Constant.MQTT_INTSYS_TOPIC1, 
							strMsg.getBytes(Charset.forName("UTF-8")));
					
					*/
					Logger.getLogger(EndServlet.class).info("mqSver:is stop");
	
					WTFindPasswordAction wpa = new WTFindPasswordAction();
					wpa.CreateMessage(wpa.getServerName(), "mqSvrStop", "461261532@qq.com");
					wpa = null;
				} else {
					Logger.getLogger(EndServlet.class).info("mqSver:is normal");
				}
	        } catch(Exception e) {
				e.printStackTrace();
				WMsgInfo wi = new WMsgInfo();
				wi.setApp_type("1");
				wi.setBadge(0);
				wi.setDevice_id(80);
				wi.setHide_flag("0");
				wi.setIos_real("5272e4539717fbfa4b409b894161a66afb02917f720d8f56c8948b64564be195");
				wi.setSummary("mqtt server stopped!");
				wi.setTo_usrid(1);
				String strMsg = JSON.toJSONString(wi);
				ServiceBean.getInstance().pushMsg(
						Constant.MQTT_INTSYS_TOPIC1, 
						strMsg.getBytes(Charset.forName("UTF-8")));
				
				
				Logger.getLogger(EndServlet.class).info("mqSver:is stop");

				WTFindPasswordAction wpa = new WTFindPasswordAction();
				wpa.CreateMessage(wpa.getServerName(), "mqSvrStop", "461261532@qq.com");
				wpa = null;
	        	
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
