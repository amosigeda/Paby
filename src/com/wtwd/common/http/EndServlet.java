package com.wtwd.common.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.Scanner;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
//import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.godoing.rose.lang.DataMap;
import com.wtwd.app.DaemonMain;
import com.wtwd.common.bean.other.SsidEsafeInfo;
import com.wtwd.common.bean.other.Ssids;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.codec.textline.TextLineCodecFactoryII;
import com.wtwd.common.handler.KeepAliveMessageFactoryImp;
import com.wtwd.common.handler.KeepAliveRequestTimeoutHandlerImp;
//import com.wtwd.common.charset.CharsetCodecFactoryII;
import com.wtwd.common.handler.ServerHandler;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.pet.app.notify.App;
import com.wtwd.sys.appinterfaces.innerw.DevNotifyApp;
import com.wtwd.sys.appinterfaces.innerw.WTAppDevWifiSrcManAction;
import com.wtwd.sys.appinterfaces.innerw.WTAppDeviceManAction;
import com.wtwd.sys.appinterfaces.innerw.WTAppGpsManAction;
import com.wtwd.sys.appinterfaces.innerw.WTDevSetaAction;
import com.wtwd.sys.appinterfaces.innerw.WTFindPasswordAction;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.StepCountTools;
import com.wtwd.sys.locationinfo.domain.LocationInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

/**
 * ϵͳ�����ࡣ��Ҫ����ϵͳ�̼߳����
 * 
 * @author Administrator
 * 
 */
public class EndServlet extends HttpServlet {

	//public static String dirBase  = ;
	
	public static boolean dummyTestFlag = false; 
	public static Boolean initFlag = false;

	private static final long serialVersionUID = -5075963146474427895L;
	//private static final int PORT = 6688;
	private static /*final*/ int PORT = 6690;	//6688
	private static final String IP_HOST = "0.0.0.0";   //"172.31.4.240";   //"121.196.232.11";   //"127.0.0.1";   //"121.196.232.11";
	private static Logger logger = Logger.getLogger(EndServlet.class);
	private static final String HEARTBEATREQUEST = "0x11";   //"0x11\r\n"/*"0x01"*/;  
    private static final String HEARTBEATRESPONSE = "0x12";  
	private static ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();

    private static Map<Integer, Object> devNetMap  = new HashMap<Integer, Object>();  
    public static Map<Integer, Object> devChkConfig  = new HashMap<Integer, Object>();  
    
    
    private static final int IDELTIMEOUT = 390;	//330;   //720;   //390;  //450;  //390;  //330;  //300;  
    /** 15秒发送一次心跳包 */  
    private static final int HEARTBEATRATE = 390;	//330;  //720;   //390;   //300;  //390;  //300;  //152/*40*/;  	
    private static final int HEARTIDLETIMEOUT = 30;   //90;   //420;  //390;  //330;  //154;
    
	public String getDevPort() throws Exception {
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream("server.properties"));
		String pt = pros.getProperty("devPort");
		return pt;
	}
    
	public void init(ServletConfig servletConfig) {
		// DefaultIoSessionDataStructureFactory
		try {
			PORT = Integer.parseInt(getDevPort());
			String dirBase = servletConfig.getServletContext().getRealPath("/p12/");
			App.setDirBase(dirBase);

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss.SSS");
		System.out.println("---server start time:" + df.format(new Date()));

		// ////////////////////�������߳�////////////////////////
		DaemonMain daemon = new DaemonMain();
		daemon.setFlag(true);
		daemon.start();

		// IoAcceptor acceptor = null;
		NioSocketAcceptor acceptor = null;
		try {

			// TextLineCodecFactory textLineCodeFactory = new
			// TextLineCodecFactory(Charset.forName("utf-8"),LineDelimiter.CRLF.getValue(),LineDelimiter.CRLF.getValue());
			// textLineCodeFactory.setDecoderMaxLineLength(Integer.MAX_VALUE);
			// textLineCodeFactory.setEncoderMaxLineLength(Integer.MAX_VALUE);

			// 创建一个非阻塞的server端的Socket
			acceptor = new NioSocketAcceptor(Runtime.getRuntime()
					.availableProcessors() + 1);

			// acceptor = new NioSocketAcceptor();

			acceptor.setReuseAddress(true);

			// acceptor.getFilterChain().addLast(
			// "codec",
			// new ProtocolCodecFilter(textLineCodeFactory));

			acceptor.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new TextLineCodecFactoryII(Charset
							.forName("utf-8"), LineDelimiter.CRLF.getValue(),
							LineDelimiter.CRLF.getValue())));

			// 设置过滤器（使用Mina提供的文本换行符编解码器�?
			/*
			 * acceptor.getFilterChain().addLast( "codec", new
			 * ProtocolCodecFilter(new
			 * CharsetCodecFactoryII(Charset.forName("utf-8"))));
			 */
			// LoggingFilter loggingFilter = new LoggingFilter();
			// loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);
			// loggingFilter.setSessionCreatedLogLevel(LogLevel.DEBUG);
			// loggingFilter.setSessionOpenedLogLevel(LogLevel.INFO);
			// acceptor.getFilterChain().addLast("logger", loggingFilter);

			DefaultIoFilterChainBuilder filterChainBuilder = acceptor
					.getFilterChain();
			filterChainBuilder.addLast("threadPool", new ExecutorFilter(
					Executors.newCachedThreadPool()));

			if (Constant.STAT_SERV_HEARBEAT) {//false
				KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();

				KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();

				KeepAliveFilter heartBeat = new KeepAliveFilter(
						heartBeatFactory, IdleStatus.READER_IDLE,
						heartBeatHandler);

				// 设置是否forward到下一个filter
				heartBeat.setForwardEvent(true);

				// 设置心跳频率
				heartBeat.setRequestInterval(HEARTBEATRATE);

				heartBeat.setRequestTimeout(HEARTIDLETIMEOUT);
				acceptor.getFilterChain().addLast("heartbeat", heartBeat);
			}

			KeepAliveMessageFactory keepAliveFactory = new KeepAliveMessageFactoryImp();

			KeepAliveRequestTimeoutHandlerImp keepAliveReqTimeOutHandle = new KeepAliveRequestTimeoutHandlerImp();

			// KeepAliveFilter keepAliveFilter = new
			// KeepAliveFilter(keepAliveFactory,IdleStatus.READER_IDLE);
			KeepAliveFilter keepAliveFilter = new KeepAliveFilter(
					keepAliveFactory, IdleStatus.READER_IDLE,
					keepAliveReqTimeOutHandle, HEARTBEATRATE, HEARTIDLETIMEOUT);
			keepAliveFilter.setForwardEvent(true);
			// keepAliveFilter.setRequestInterval(HEARTBEATRATE);
			// keepAliveFilter.setRequestTimeout(HEARTIDLETIMEOUT);
			acceptor.getFilterChain().addLast("keepAlive", keepAliveFilter);

			// 设置读取数据的缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(32 * 1024); // 16*1024
																		// //128*1024
			// 读写通道5分钟内无操作进入空闲状�?
			acceptor.getSessionConfig().setTcpNoDelay(true);
			acceptor.getSessionConfig().setSendBufferSize(32 * 1024);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, /* 290 */
					IDELTIMEOUT); // 300
			// 绑定逻辑处理�?

			acceptor.setHandler(new ServerHandler());
			// 绑定端口

			// acceptor.bind(new InetSocketAddress(PORT));

			acceptor.bind(new InetSocketAddress(IP_HOST, PORT));

			// Scanner sc = new Scanner(System.in);
			logger.info("---StartServer Success--- IP_HOST: " + IP_HOST
					+ " PORT: " + PORT);
			// logger.debug("---StartServer Success--- IP_HOST: " + IP_HOST +
			// " PORT: " + PORT);
			System.out.println("---StartServer Success--- IP_HOST: " + IP_HOST
					+ " PORT: " + PORT);

			Timer timer = new Timer();
			timer.schedule(new TestTimerTask(), 1000 * 7);

			// Timer timer1 = new Timer();
			// timer1.schedule(new TestTimerTaskMqNml(), 1000* 60* 5, 1000* 60 *
			// 5 );

			// Timer timer1 = new Timer();
			// timer1.schedule(new TestTimerTask1(), 5600 );

			// TTmrUFirmRes
			// Timer timer1 = new Timer();
			// timer1.schedule(new TTmrUFirmRes(), 4500 );

			/*
			 * Timer timer1 = new Timer(); timer1.schedule(new SleepCtlApiTmr(),
			 * 4500 ); Timer timer2 = new Timer(); timer2.schedule(new
			 * SleepCtlTmr(), 5000 );
			 */

			Calendar date = Calendar.getInstance();
			// 设置时间为 xx-xx-xx 00:00:00
			date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
					date.get(Calendar.DATE), 0, 0, 0);
			// 一天的毫秒数
			long daySpan = 24 * 60 * 60 * 1000;
			// 得到定时器实例
			Timer t = new Timer();
			// 使用匿名内方式进行方法覆盖
			CheckSimDateDue taskLocationInfo = new CheckSimDateDue();
			t.schedule(taskLocationInfo, date.getTime(), daySpan);// 每天凌晨去执行

		} catch (Exception e) {
			logger.error("---Server start exception---", e);
			e.printStackTrace();
			acceptor.unbind();
			acceptor.dispose(true);
		}
	}

	public class TestTimerTask extends TimerTask {
		
		public TestTimerTask() { 
		}
		
		@Override		
		public void run() {
			try {
				/*
				WTAppSafeAreaManAction w2 = new WTAppSafeAreaManAction();
				JSONObject jo = new JSONObject();
				
				jo.put("device_safe_id", 198);
				jo.put("device_safe_range", 250);
				jo.put("device_safe_name", "safe name");
				jo.put("device_safe_addr", "safe addr");
				jo.put("device_id", 80);
				jo.put("lat", "113.231234143");
				jo.put("lon", "22.131234143");
																		
				w2.proDel(jo);
				*/
				//WTFindPasswordAction wtFpa = new WTFindPasswordAction();
				//wtFpa.CreateMessage("wwwqingtian@126.com,abcd", "123456", "461261532@qq.com");
				//wtFpa.CreateMessage("wwwqingtian@126.com,abcd", "123456", "waterworldsmart@gmail.com");
				
				
				//yonghu start
				/*
				WappUsers wa = new WappUsers();
				WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
				wa.setCondition("user_id > 0");
				wa.setApp_token("#@asdasdfasd2_fa#II31IIIISsas1234134dfaaefa");
				fd.updateWappUsers(wa);
				*/
				/*
				LocationInfo locationInfo = new LocationInfo();
				
				locationInfo.setBattery(40);
				locationInfo.setAccuracy(40.0f);
				locationInfo.setChangeLatitude("22.4989817");
				locationInfo.setChangeLongitude("113.9211323");
				locationInfo.setDevice_id(80);
				locationInfo.setUploadTime(Tools.getDateFromString("2017-02-15 14:30:21") );
				
				LocationInfoHelper lih = new LocationInfoHelper();								
				lih.proLctInfo(true, locationInfo, 
						false);
				*/
				
				/*
				WTAppPetManAction wpma = new WTAppPetManAction();
				Wpet voWpet = new Wpet();
				voWpet.setPet_id("558");
				voWpet.setIs_healthy("1");
				
				wpma.proReqUpdatePet(null, voWpet);
				*/

				//WTDevHandler wdh = new WTDevHandler();

				//String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"75\",\"stepNumber\":\"1319\",\"lctTime\":"
				//		+ "\"2017-02-21 14:46:44\",\"wifi\":{\"smac\":"
				//		+ "\"00:08:22:f2:df:fb\",\"mmac\":\"\",\"macs\":"
				//		+ "\"9c:21:6a:73:3e:70,-59,6ben|fc:d7:33:2b:4f:5c,-63,WTWD18|58:1f:28:82:bd:a5,-65,Mate 7|e0:b9:4d:c6:1d:1a,-65,PABY|64:9e:f3:87:ea:55,-69,waterworld1|12:52:cb:61:3f:b3,-72,jigang\","
				//		+ "\"serverip\":\"\"}}";
						
				/*String msg = "{\"cmd\":\"uploadLctData\",\"type\":\"3\",\"battery\":\"75\",\"stepNumber\":\"1319\",\"lctTime\":"
						+ "\"2017-02-21 14:46:44\",\"wifi\":{\"smac\":"
						+ "\"00:08:22:f2:df:fb\",\"mmac\":\"\",\"macs\":"
						+ "\"9c:21:6a:73:3e:70,-59,6ben|fc:d7:33:2b:4f:5c,-63,WTWD18|58:1f:28:82:bd:a5,-65,waterworld1|12:52:cb:61:3f:b3,-72,jigang\","
						+ "\"serverip\":\"\"}}";*/

				//wdh.processCmd(msg, null);
				
				//WTAppDeviceManAction wadma = new WTAppDeviceManAction();
				//wadma.proDelShare(18, 388, 1);
				
				
				/*
				WdeviceActiveInfo wda = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade wdaFd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
				wda.setCondition("device_id > 0");
				wda.setUrgent_mode("0");
				wdaFd.updateWdeviceActiveInfo(wda);
				
				wda.setSos_led_on("0");
				wda.setDev_status("0");
				wda.setCondition("id > 0");
				wdaFd.updatewDeviceExtra(wda);
				*/
				synchronized(initFlag) {
					initFlag = true;
				}

				/*
				WTAppDeviceManAction wdma = new WTAppDeviceManAction();
				wdma.proQueryAllList(null, 42, "1");
				*/
				

				
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}

		//@Override		
		public void runEffence() {
			// TODO Auto-generated method stub
			//testControlEco();
			try {
				
				//BaseAction.getDevStatus(80);
				//yonghu start
				LocationInfoHelper lih = new LocationInfoHelper();	
				LocationInfo location_info = new LocationInfo();
				location_info.setLongitude("113.9210357");
				location_info.setLatitude("22.4993441");
				location_info.setDevice_id(393);
				
				//location_info.setSerieNo("352138064952338");
				location_info.setSerieNo("352138064952874");

				location_info.setAccuracy(38.0f);
				location_info.setBattery(30);
				location_info.setLocationType("3");
				location_info.setUploadTime(new Date());
				
				
				lih.proLctInfo(0, true, location_info, 
						true);
				lih = null;
				
				// 113.923
				// 22.5032
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public void runBk() {		//测试运动完成情况推送
			// TODO Auto-generated method stub
			//testControlEco();
			try {
				
				StepCountTools sct = new StepCountTools();
				
				WpetMoveInfo wmi = new WpetMoveInfo();
				wmi.setSpeed(8.0d);
				wmi.setEnd_time("2016-12-28 11:30:30");
				wmi.setPets_pet_id(605);
				if ( Constant.timeUtcFlag  )
					sct.proMoveDoMsgSe(wmi, 80);
				else
					sct.proMoveDoMsg(wmi, 80);
				// 113.923
				// 22.5032
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
		
		
	  	private void testSsidEsafeInfo() {
	  		Tools tls = new Tools();	  		
			try {
				ReqJsonData reqJsonData = new ReqJsonData();
				WTAppGpsManAction wma = new WTAppGpsManAction();
				reqJsonData.setUrgentFlag("1");
				
				String now = tls.getStringFromDate( new Date() );
				reqJsonData.setActionTime(now);
				System.out.println("sos start at:" + now);
				reqJsonData.setDuration(1);
				reqJsonData.setCmdTime("2016-12-15 15:22:28");
				
				//wma.proUrgentModeRes(80, "352138064952338", reqJsonData);				
				//wma.proGetDevsList(1);
				
				Ssids ssids  = new Ssids();
				List<SsidEsafeInfo> ssidList = new ArrayList<SsidEsafeInfo>();
				SsidEsafeInfo s1 = new SsidEsafeInfo();
				s1.setMac("1233");
				s1.setSignal("3.0");
				s1.setSsid("i-shekou");
				SsidEsafeInfo s2 = new SsidEsafeInfo();
				s2.setMac("1234");
				s2.setSignal("2.0");
				s2.setSsid("i-shekou2");
				SsidEsafeInfo s3 = new SsidEsafeInfo();
				s3.setMac("1233");
				s3.setSignal("1.0");
				s3.setSsid("i-shekou3");
				ssidList.add(s1);
				ssidList.add(s2);
				ssidList.add(s3);
				
				ssids.setSsidList(ssidList);
				reqJsonData.setSsids(ssids);
				WTAppDevWifiSrcManAction wdwsm = new WTAppDevWifiSrcManAction();
				wdwsm.proGetSsidListRes(80, "352138064952338", reqJsonData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}

	  	private void testControlEco() {
	  		Tools tls = new Tools();
	  		
			try {
				ReqJsonData reqJsonData = new ReqJsonData();
				WTAppGpsManAction wma = new WTAppGpsManAction();
				reqJsonData.setUrgentFlag("1");
				
				String now = tls.getStringFromDate( new Date() );
				reqJsonData.setActionTime(now);
				System.out.println("sos start at:" + now);
				reqJsonData.setDuration(1);
				reqJsonData.setCmdTime("2016-12-15 15:22:28");
				
				WTDevSetaAction wdsa = new WTDevSetaAction();
				wdsa.devControlEco(1, "352138064952338", "1", null );
				
				//wma.proUrgentModeRes(80, "352138064952338", reqJsonData);				
				//wma.proGetDevsList(1);
				
				//wdwsm.proGetSsidListRes(80, "352138064952338", reqJsonData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
		
	public class TestTimerTask1 extends TimerTask {
		
		public TestTimerTask1() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUser_id("1");
				reqJsonData.setEcoFlag("1");
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
		
		
		
	
	
	 /** 
     * @ClassName KeepAliveMessageFactoryImpl 
     * @Description 内部类，实现KeepAliveMessageFactory（心跳工厂） 
     * @author cruise 
     * 
     */  
    private static class KeepAliveMessageFactoryImpl implements  
            KeepAliveMessageFactory {  
  
        public boolean isRequest(IoSession session, Object message) {
        	
      	  Integer device_id = -1;
      	
  	      if ( Constant.STAT_SERV_HEART ) {
  	    	  
  				if(session.containsAttribute("wdeviceInfo")){
  					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
  					device_id = (Integer) dm.getAt("device_id");
  					
  				} else if(session.containsAttribute("devId")){
  					
					String devId = (String) session.getAttribute("devId");
		    		try {
		    			WTSigninAction ba = new WTSigninAction();
						device_id = ba.getDeviceIdFromDeviceImei(devId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  				}
        	
  				if ( device_id  > 0 ) {
  					Integer onoff = (Integer) devChkConfig.get(device_id);
  				
  					if ( onoff == null )
  						return false;
  					else if (onoff > 0 &&  message.equals(HEARTBEATREQUEST) )
  						return true;
  					else
  						return false;
  				}
        		
  	      }      	
        	
            if (message.equals(HEARTBEATREQUEST))  
                return true;  
            return false;  
        }  
  
        public boolean isResponse(IoSession session, Object message) {  
//          LOG.info("响应心跳包信息: " + message);  
			WTSigninAction ba = new WTSigninAction();
        	
          if(message.equals(HEARTBEATRESPONSE)) {
        	  Integer device_id = -1;
        	  String devId = null;
    	      if ( Constant.STAT_SERV_HEART ) {
        	  
    			if(session.containsAttribute("wdeviceInfo")){
    				DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
    				device_id = (Integer) dm.getAt("device_id"); 
    				dm = null;
    				try {
						ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart res");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
    			} else {
    				if(session.containsAttribute("devId")){
    					devId = (String) session.getAttribute("devId");	
    		    		try {
    		    			
							device_id = ba.getDeviceIdFromDeviceImei(devId);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    					
    					try {
							ba.insertVisit(null, devId, "-1", "minaHeart res");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
    				} else	{				
    					try {
							ba.insertVisit(null, null, "-1", "exception minaHeart res");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
    				}
    			}
    			
    		    if (  device_id > 0 )
    				devNetMap.put(device_id, 0);
    	      }

    		  
              return true;
          } else
            return false;  
        }  
  
        public Object getRequest(IoSession session) {  
            /** 返回预设语句 */
    	  Integer device_id = -1;
			WTSigninAction ba = new WTSigninAction();
        	
  	      if ( Constant.STAT_SERV_HEART ) {
  	    	  
  				if(session.containsAttribute("wdeviceInfo")){
  					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
  					device_id = (Integer) dm.getAt("device_id");
  					
  				} else if(session.containsAttribute("devId")){
  					
					String devId = (String) session.getAttribute("devId");
		    		try {
						device_id = ba.getDeviceIdFromDeviceImei(devId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  				}
        	
  				if ( device_id  > 0 ) {
  					Integer onoff = (Integer) devChkConfig.get(device_id);
  				
  					if ( onoff == null )
  						return null;
  					else if(onoff > 0 )
  						return HEARTBEATREQUEST;
  					else
  						return null;
  				}
        		
  	      }
            return HEARTBEATREQUEST;  
        }  
  
        public Object getResponse(IoSession session, Object request) {  
            /** 返回预设语句 */  
            return HEARTBEATRESPONSE;  
//          return null;  
        }

  
    }  
    
    public synchronized static void setNetCheckOn(Integer device_id) {
    	devChkConfig.put(device_id, 1);
    }
    public synchronized static void setNetCheckOff(Integer device_id) {
    	devChkConfig.put(device_id, 0);
    }   
    
      
    /** 
     * 对应上面的注释 
     * KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE,heartBeatHandler) 
     * 心跳超时处理 
     * KeepAliveFilter 在没有收到心跳消息的响应时，会报告给的KeepAliveRequestTimeoutHandler。 
     * 默认的处理是 KeepAliveRequestTimeoutHandler.CLOSE 
     * （即如果不给handler参数，则会使用默认的从而Close这个Session） 
     * @author cruise 
     * 
     */  
  
  private static class KeepAliveRequestTimeoutHandlerImpl implements  
          KeepAliveRequestTimeoutHandler {  
  
    
      public void keepAliveRequestTimedOut(KeepAliveFilter filter,  
              IoSession session) throws Exception {  
          //心跳超时;
    	  Integer device_id = -1;
    	  String devId = null;
			WTSigninAction ba = new WTSigninAction();
    	  

  	      if ( Constant.STAT_SERV_HEART ) {
  	    	  
  				if(session.containsAttribute("wdeviceInfo")){
  					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
  					device_id = (Integer) dm.getAt("device_id");
  					
  				} else if(session.containsAttribute("devId")){
  					
					devId = (String) session.getAttribute("devId");
		    		try {
						device_id = ba.getDeviceIdFromDeviceImei(devId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  				}
        	
  				if ( device_id  > 0 ) {
  					Integer onoff = (Integer) devChkConfig.get(device_id);
  				
  					if ( onoff == null )
  						return;
  					else if(onoff < 1)
  						return;
  				}
        		
  	      }
    	  
    	  
	      if ( Constant.STAT_SERV_HEART ) {
				if(session.containsAttribute("wdeviceInfo")){
					DataMap dm = (DataMap)session.getAttribute("wdeviceInfo");
					device_id = (Integer) dm.getAt("device_id");
					
					 
					
					
					//心跳超时次数累计
					//session.setAttributeIfAbsent("wdeviceInfo", dm);  //记录设备信息
	
					
					ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart timeout");				
					dm = null;
	
				} else {
					if(session.containsAttribute("devId")){
						devId = (String) session.getAttribute("devId");
			    		try {
							device_id = ba.getDeviceIdFromDeviceImei(devId);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						ba.insertVisit(null, devId, "-1", "minaHeart timeout");				
					} else	{				
						ba.insertVisit(null, null, "-1", "exception minaHeart timeout");				
					}
				}
				
				if ( device_id  > 0 ) {
					Integer count = (Integer) devNetMap.get(device_id);
					if (count == null) {
						count = 1;
						ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart lia count" + count);				
	
						devNetMap.put(device_id, count);
					} else {
						count++;
						
						ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart " + count);				
						
						if ( count == 3) {
					    	LocationInfoHelper lih = new LocationInfoHelper();
							
							try {
	
								devId = (String)session.getAttribute("devId");
								ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart " + count + " devId" + devId);				
								
								if(mClientSessionManager.getSessionId(devId) != null) {
									mClientSessionManager.removeSessionId(devId);
									session.removeAttribute("wdeviceInfo");
									session.removeAttribute("devId");
	
									ba.insertVisit(null,null, String.valueOf(device_id), "minaHeart " + count + " proSysDevOffline");				
	
									lih.proSysDevOffline(device_id, devId);
								}
								else {
									ba.insertVisit(null, null, String.valueOf(device_id), "minaHeart " + count + " proSysDevOffline ali");												
									lih.proSysDevOffline(device_id, devId);
									
								}
									
	
							} catch (Exception e) {
								ba.insertVisit(null, null, String.valueOf(device_id), "exception minaHeart exception ali");				
	
								e.printStackTrace();
							}
							ServerHandler sh = new ServerHandler();
					    	sh.sysOffline( session ); 							
							session.closeNow();
							devNetMap.put(device_id, 0);						
							
						}
						else
							devNetMap.put(device_id, count);						
					}
						
				}
					
				
	    	 }
    	  
      }  
  
  }  	

	public class TTmrUFirmRes extends TimerTask {
		
		public TTmrUFirmRes() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUserId(1);
				reqJsonData.setEcoFlag("1");
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				dna.proUFirmRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData, "{\'errorCode\":0}");
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
		

	
	public class SsidEsafeBrd extends TimerTask {
		
		public SsidEsafeBrd() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUserId(1);
				reqJsonData.setSsidEsafeFlag("1");
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				dna.proSsidEsafeState(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData, "{\'ssidEsafeFlag\":1}", "your pet left fence", "1");
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}		
		
	}
	

	public class UpdateStartTmr extends TimerTask {
		
		public UpdateStartTmr() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUserId(1);
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				dna.proUpdateStart(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData, "");
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
				
	}

	public class SleepCtlApiTmr extends TimerTask {
		
		public SleepCtlApiTmr() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUserId(1);
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				//dna.proSetSleep(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);

				WTAppDeviceManAction wdma = new WTAppDeviceManAction();
				wdma.devSleepCtl(1,"352138064952338", 1, null );
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
				
	}
	
	
	public class SleepCtlTmr extends TimerTask {
		
		public SleepCtlTmr() { 
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testAfter();			
		}


	  	private void testAfter() {
			try {
				DevNotifyApp dna = new DevNotifyApp();
				ReqJsonData reqJsonData = new ReqJsonData();
				
				reqJsonData.setUserId(1);
				reqJsonData.setSleepStateFlag("1");
				//dna.proEcoModeRes(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				dna.proSetSleep(80, "352138064952338", "2016-12-15 15:22:28", reqJsonData);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
			
			
		}
				
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Tools tls = new Tools();
		
		try {
	    	LocationInfoHelper lih = new LocationInfoHelper();
			
			WMsgInfo aMsg = new WMsgInfo();
			aMsg.setDevice_id(0);
			aMsg.setTo_usrid(0);
			
	
			aMsg.setMsg_ind_id(Constant.CST_MSG_IND_SERVER_STOP_NTFY);
			aMsg.setDevice_id(0);	
			
			//aMsg.setMsg_date(Tools.getStringFromDate(new Date()));
			if ( Constant.timeUtcFlag )					
				aMsg.setMsg_date(tls.getUtcDateStrNow());
			else
				aMsg.setMsg_date(tls.getStringFromDate(new Date()));							
			
			aMsg.setMsg_date_utc(tls.getUtcDateStrNow());
			
				
			aMsg.setMsg_content("");
			aMsg.setHide_flag(Tools.OneString);
			
			
			lih.proBroadcastMsg(aMsg, 0);
			logger.info("pet service is stopped!");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		super.destroy();
	}



	public class TestTimerTaskMqNml extends TimerTask {
		
		public TestTimerTaskMqNml() {			
		}
		
		@Override		
		public void run() {
			try {
                String host = "http://appserver1.paby.com:8161/";
				
                WhttpPostAs whp = WhttpPostAs.getInstance();
                whp.httpPostInner(host, null);
				
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
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
					
					Logger.getLogger(EndServlet.class).info("mqSver:is stop");
	
					WTFindPasswordAction wpa = new WTFindPasswordAction();
					wpa.CreateMessage(wpa.getServerName(), "mqSvrStop", "461261532@qq.com");
					wpa = null;
				} else {
					Logger.getLogger(EndServlet.class).info("mqSver:is normal");
				}
	        } catch(Exception e) {
				e.printStackTrace();
								
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



