package com.wtwd.sys.client.handler;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.wtwd.common.bean.request.ReqJsonData;
import com.wtwd.common.bean.response.RespJsonData;
import com.wtwd.common.bean.subcri.SubcriJsonData;
import com.wtwd.sys.client.handler.helper.AdragonUserHandlerHelper;
import com.wtwd.sys.client.handler.helper.AdragonUserHandlerHelperII;
import com.wtwd.sys.client.handler.helper.AdragonUserHandlerHelperIII;
import com.wtwd.sys.client.handler.impl.ClientMessageEventImpl;
import com.wtwd.sys.client.manager.ClientSessionManager;

public class AdragonUserHandler extends ClientMessageEventImpl{
	
	private final static String TAG = AdragonUserHandler.class.getSimpleName();
	
	private Logger logger = Logger.getLogger(AdragonUserHandler.class);	
	
	private ClientSessionManager mClientSessionManager;

	public void handler(Object message, IoSession session) {
					
		RespJsonData respJsonData = new RespJsonData();
		SubcriJsonData subData = new SubcriJsonData();
		String resp ="";
		String sub ="";
		if(message!=null && session !=null){
			ReqJsonData reqJsonData = JSON.parseObject((String)message,ReqJsonData.class);
			String uid="";
			String did="";
			String b_g="";
			String cmd="";
			cmd = reqJsonData.getCmd();
			logger.info("AdragonUserHandler请求接口="+cmd+",uid="+uid+",did="+did+",b_g="+b_g);
			
			if(uid==null||did==null||b_g==null|cmd==null||"0".equals(uid)||"0".equals(did)||"0".equals(b_g)){
				respJsonData.setResultCode(-2);
				throw new NullPointerException();
			}
			
			respJsonData.setResp(cmd+"_RE");
			
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-local.xml");
			mClientSessionManager = (ClientSessionManager) context.getBean("clientSessionManager");
			
			try {
				if(cmd.equals(AdragonConfig.ADR_U_REGISTER)) { //注册

					respJsonData = AdragonUserHandlerHelper.userAdrRegister(reqJsonData);
					String userImei = reqJsonData.getUser_imei();
					if(userImei!=null && !"".equals(userImei)){
						session.setAttributeIfAbsent("userImei", userImei);  //每一个通道的id
						
				        if(mClientSessionManager.getSessionId(userImei) != null){
					        mClientSessionManager.removeSessionId(userImei);
				        }
						mClientSessionManager.addSessionId(userImei, session);
					}
					
				}else if (cmd.equals(AdragonConfig.ADR_U_LOGIN)) { //登录

					respJsonData = AdragonUserHandlerHelper.userAdrLogin(reqJsonData);

				}else if (cmd.equals(AdragonConfig.ADR_U_MODIFYUSER)) { //用户信息修改

					respJsonData = AdragonUserHandlerHelper.modifyAdrUser(reqJsonData, this.getServerName());

				}else if (cmd.equals(AdragonConfig.ADR_U_VERIFYDEVICE)) { //设备验证

					respJsonData = AdragonUserHandlerHelper.verifyAdrDevice(reqJsonData);

				}else if(cmd.equals(AdragonConfig.ADR_U_ADDDEVICE)){  //添加宝贝
					
					respJsonData = AdragonUserHandlerHelper.addAdragonDevice(reqJsonData, this.getServerName());
					
				}else if(cmd.equalsIgnoreCase(AdragonConfig.ADR_U_DELETEDEVICE)){  //删除宝贝
					
					respJsonData = AdragonUserHandlerHelper.deleteAdrDevice(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_ADDDEVICEFAMILY)){  //添加亲情号码
					
					respJsonData = AdragonUserHandlerHelperII.addAdrDeviceFamily(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_DELETEDEVICEFAMILY)){ //删除亲情号码
					
					respJsonData = AdragonUserHandlerHelperII.deleteAdrDeviceFamily(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_MODIFYDEVICEFAMILY)){   //修改亲情号码
					
					respJsonData = AdragonUserHandlerHelperII.modifyAdrDeviceFamily(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_QUERYDEVICETRACK)){  //历史轨迹查询
					
					respJsonData = AdragonUserHandlerHelperII.queryDeviceTrack(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_MODIFYDEVICESAFEAREA)){  //设备安全区域
					
					respJsonData = AdragonUserHandlerHelperII.modifyDeviceSafeArea(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_UPDATEAPP)){  //版本升级
					
					respJsonData = AdragonUserHandlerHelperII.updateApp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_FEEDBACK)){  //意见反馈
					
					respJsonData =AdragonUserHandlerHelperII.feedBack(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_LISTEN)){  //倾听
					
					respJsonData = AdragonUserHandlerHelperII.listen(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_LOCATION)){  //定位
					
					respJsonData = AdragonUserHandlerHelperII.location(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_FINDBABY)){  //找宝贝
					
					respJsonData = AdragonUserHandlerHelperII.findBaby(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_DELETESAFEAREA)){  //删除安全区域
					
					respJsonData = AdragonUserHandlerHelperII.deleteDeviceSafeArea(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_GETDEVICESAFEAREA)){  //获取安全区域
					
					respJsonData = AdragonUserHandlerHelperII.getDeviceSafeArea(reqJsonData);
				
				}else if(cmd.equals(AdragonConfig.ADR_U_SHAREDEVICE)){  //设备分享
					
					respJsonData =AdragonUserHandlerHelperII.shareDeviceHelper(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_SHAREDEVICE)){  //消息处理
					
					respJsonData = AdragonUserHandlerHelperII.msgHandlerHelper(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_GETMSG)){       //获取消息
					
					respJsonData = AdragonUserHandlerHelperII.getMessageHelper(reqJsonData);
				
				}else if(cmd.equals(AdragonConfig.ADR_U_DELETESHAREDEVICE)){  //取消分享
					
					respJsonData = AdragonUserHandlerHelperII.deleteShareDeviceHelper(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_FINDPASSWORD)){    //找回密码
					
					respJsonData = AdragonUserHandlerHelperII.findPasswordHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_REMOTE)){         //远程关机、驱蚊、爱心
					
					respJsonData = AdragonUserHandlerHelperII.remoteHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_DISTURB)){       //上课防打扰
					
					respJsonData = AdragonUserHandlerHelperII.disturbHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_LOWELECTRICITY)){  //低电提醒
					
					respJsonData = AdragonUserHandlerHelperII.lowElectricityHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_SETCLOCK)){    //远程闹钟
					
					respJsonData =AdragonUserHandlerHelperII.setClockHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_SETSLEEP)){  //睡眠提醒
					
					respJsonData = AdragonUserHandlerHelperIII.setSleepHelp(reqJsonData);
					
				}else if(cmd.equals(AdragonConfig.ADR_U_QUERYTRACECOUNT)){  //轨迹查询
					
					respJsonData = AdragonUserHandlerHelperIII.queryTraceCount(reqJsonData);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
			}
		}		
	}	
	
	public String getServerName() throws Exception {
		String serverName = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName = pros.getProperty("servername");
		return serverName;
	}
}
