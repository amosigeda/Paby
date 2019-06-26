package com.wtwd.sys.client.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.DevNotifyApp;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.appinterfaces.innerw.WTSugMoveManAction;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;

public class ClientSessionManager {

	private Logger logger = Logger.getLogger(ClientSessionManager.class);	
	
	
	/**
	 * 管理设备端session的id
	 */
	private final ConcurrentMap<String, IoSession> managerSession = new ConcurrentHashMap<String, IoSession>();

	//炫酷模式
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrLightCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//紧急模式
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrUrgentCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//手动定位
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrLctCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//省电模式控制
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrEcoCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//检测设备新固件
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrqFirmCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//通知设备固件升级
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgruFirmCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//获取计步数据
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrgSportCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//获取WIFI热点列表
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrgWifiListCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//设置WIFI热点列表
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrgsWifiListCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//休眠时段控制
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrSlpCtlListCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();
	//定时开关机控制
	private final ConcurrentMap<String, Map<Integer, CmdSync>> mgrTOnOffCmds = new ConcurrentHashMap<String, Map<Integer, CmdSync>>();


	//定时开关机控制
	private final ConcurrentMap<String, String> mgrUserLock = new ConcurrentHashMap<String, String>();

	
	//ping 状态控制
	//private final ConcurrentMap<String, CmdSync> ds = new ConcurrentHashMap<String, CmdSync>();
	
	public ConcurrentMap<String, IoSession> getManagerSession() {
		return managerSession;
	}	
	
	public int getCurrentSessionIdCount() {
		return managerSession.size();
	}
	/**
	 * 用户id和IoSession关联
	 * @param key
	 * @param id
	 */
	public synchronized void addSessionId(String key, IoSession ioSession){
		//managerSession.putIfAbsent(key, ioSession);
		managerSession.put(key, ioSession);
	}
	/**
	 * 移除key
	 * @param key
	 */
	public synchronized void removeSessionId(String key){
		managerSession.remove(key);
	}
	/**
	 * 通过key,获取到session通道
	 * @param key
	 */
	public IoSession getSessionId(String key){
		return managerSession.get(key);
	}
	

	public synchronized void setUserLock(String key, String para){
		mgrUserLock.put(key, para);
	}

	public synchronized String useUserLock(String key){
		Tools tls = new Tools();	
		
		String res =  mgrUserLock.get(key);
		if ( tls.isNullOrEmpty(res) ) {
			setUserLock(key, key);
		}
		res =  mgrUserLock.get(key);
		
		return res;
	}	
	

	public void setHttpCmdId(String key, CmdSync para){
		
		Integer user_id = para.getUser_id();
		String paraCmdName = para.getCmdName();
							
		ConcurrentMap<String, Map<Integer, CmdSync>> mgrCmds = null;
		
		try {
		
			if ( AdragonConfig.setLedStateRes.equals(paraCmdName) )
				mgrCmds = mgrLightCmds;
			else if ( AdragonConfig.setUrgentModeRes.equals(paraCmdName) )
				mgrCmds = mgrUrgentCmds;
			else if ( AdragonConfig.getLocationRes.equals(paraCmdName) )
				mgrCmds = mgrLctCmds;
			else if ( AdragonConfig.getSsidListRes.equals(paraCmdName) )
				mgrCmds = mgrgWifiListCmds;
			else if ( AdragonConfig.getHealthStepRes.equals(paraCmdName) )
				mgrCmds = mgrgSportCmds;
			else if ( AdragonConfig.updateFirmwareRes.equals(paraCmdName) )
				mgrCmds = mgruFirmCmds;
			else if ( AdragonConfig.detectDevUpRes.equals(paraCmdName) )
				mgrCmds = mgrqFirmCmds;
			else if ( AdragonConfig.setEcoModeRes.equals(paraCmdName) )
				mgrCmds = mgrEcoCmds;			
			else if ( AdragonConfig.setSsidEsafeRes.equals(paraCmdName) )
				mgrCmds = mgrgsWifiListCmds;	
			else if ( AdragonConfig.setSleepStateRes.equals(paraCmdName) )
				mgrCmds = mgrSlpCtlListCmds;
			else if ( AdragonConfig.setOffOnRes.equals(paraCmdName) )
				mgrCmds = mgrTOnOffCmds;
			
			
			if ( mgrCmds == null)
				return;
	
			Map<Integer, CmdSync> userMap = mgrCmds.get(key);		
			
			if ( userMap == null ) {
				HashMap<Integer, CmdSync> hm = new HashMap<Integer, CmdSync>();
				hm.put(user_id, para);
				synchronized (mgrCmds) {
					mgrCmds.put(key, hm);
				}
				return;
			} 
			
			CmdSync cmd = userMap.get(user_id);
			
			if ( cmd != null ) {
				synchronized(cmd.getTdLock()){
					try {
						JSONObject json = new JSONObject();
						json.put(Constant.RESULTCODE, Constant.ERR_DEVICE_CMD_INTERRUPTTED);
						if ( cmd.getResponse() != null ) {							
							cmd.getResponse().setCharacterEncoding("UTF-8");	
							cmd.getResponse().getWriter().write(json.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					cmd.getTdLock().notify();
				}			
			}
	
			userMap.put(user_id, para);
			synchronized(mgrCmds) {
				mgrCmds.put(key, userMap);
			}
		} catch (Exception e) {
			
		}

		
	}	

	
	//单个设备手动定位
	public Boolean completeFailHttpCmdId(Integer user_id, Integer device_id, String devId, String cmdType) { 
		WTSigninAction ba = new WTSigninAction();
		
		try {
			ba.insertVisit(null, null, String.valueOf(device_id), "completeFailHttpCmdId");				
			
			ClientSessionManager mClientSessionManager = WTDevHandler.getClientSessionMangagerInstance();
			

			mClientSessionManager.completeHttpCmdIdFailInner(devId, 
					cmdType, 
					user_id );
			
			
		} catch (Exception e ) {
			
			e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception completeFailHttpCmdId exception:" );				
			
		}
		
		return true;
	}

	public String completeHttpCmdIdFailInner(String key, String cmdType, Integer userId){
		WTSigninAction ba = new WTSigninAction();

		
		ConcurrentMap<String, Map<Integer, CmdSync>> mgrCmds = null;

		String res = "";

		try {
			
			if ( AdragonConfig.setLedStateRes.equals(cmdType) )
				mgrCmds = mgrLightCmds;
			else if ( AdragonConfig.setUrgentModeRes.equals(cmdType) )
				mgrCmds = mgrUrgentCmds;
			else if ( AdragonConfig.getLocationRes.equals(cmdType) )
				mgrCmds = mgrLctCmds;
			else if ( AdragonConfig.getSsidListRes.equals(cmdType) )
				mgrCmds = mgrgWifiListCmds;
			else if ( AdragonConfig.getHealthStepRes.equals(cmdType) )
				mgrCmds = mgrgSportCmds;
			else if ( AdragonConfig.updateFirmwareRes.equals(cmdType) )
				mgrCmds = mgruFirmCmds;
			else if ( AdragonConfig.detectDevUpRes.equals(cmdType) )
				mgrCmds = mgrqFirmCmds;
			else if ( AdragonConfig.setEcoModeRes.equals(cmdType) )
				mgrCmds = mgrEcoCmds;
			else if ( AdragonConfig.setSsidEsafeRes.equals(cmdType) )
				mgrCmds = mgrgsWifiListCmds;			
			else if ( AdragonConfig.setSleepStateRes.equals(cmdType) )
				mgrCmds = mgrSlpCtlListCmds;
			else if ( AdragonConfig.setOffOnRes.equals(cmdType) )
				mgrCmds = mgrTOnOffCmds;

			
			Map<Integer, CmdSync> userMap = mgrCmds.get(key);					
			
			if ( userMap == null ) {
				return res;
			} 


			CmdSync cmd = userMap.get(userId);
			

			JSONObject json = null;
			
			if ( cmd != null ) {
				
				synchronized(cmd.getTdLock()){
					try {
						
						HttpServletResponse resp = cmd.getResponse();
						
						json = new JSONObject();
						
						json.put("cmd", cmdType);
						
						json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
						
						if (resp != null)						
							resp.setCharacterEncoding("UTF-8");
						
						
						if (resp != null)
							resp.getWriter().write(json.toString());
						
						logger.info("completeHttpCmdIdFailInner" + json.toString());
						
						res = json.toString();
					} catch (Exception e) {
						ba.insertVisit(null, key, null, "exception completeHttpCmdIdFailInner exception 1" );				
						
					}
					
					userMap.remove(userId);
					
					synchronized(mgrCmds) {
						mgrCmds.put(key, userMap);
					}
					
					cmd.getTdLock().notify();
					
					
				}			
			}
			
			return res;
			
			
		} catch(Exception e) {
			ba.insertVisit(null, key, null, "exception completeHttpCmdIdFailInner exception 2" );							
			return res;
		}
	
		
	}
	
	
	
	public String completeHttpCmdId(String key, String cmdType, Integer userId, 
			String ext_str){
		WTSigninAction ba = new WTSigninAction();
		Tools tls = new Tools();		

		
		if ( userId == null || userId < 1 )
			return null;
		
		ConcurrentMap<String, Map<Integer, CmdSync>> mgrCmds = null;

		String res = "";

		try {
			
			if ( AdragonConfig.setLedStateRes.equals(cmdType) )
				mgrCmds = mgrLightCmds;
			else if ( AdragonConfig.setUrgentModeRes.equals(cmdType) )
				mgrCmds = mgrUrgentCmds;
			else if ( AdragonConfig.getLocationRes.equals(cmdType) )
				mgrCmds = mgrLctCmds;
			else if ( AdragonConfig.getSsidListRes.equals(cmdType) )
				mgrCmds = mgrgWifiListCmds;
			else if ( AdragonConfig.getHealthStepRes.equals(cmdType) )
				mgrCmds = mgrgSportCmds;
			else if ( AdragonConfig.updateFirmwareRes.equals(cmdType) )
				mgrCmds = mgruFirmCmds;
			else if ( AdragonConfig.detectDevUpRes.equals(cmdType) )
				mgrCmds = mgrqFirmCmds;
			else if ( AdragonConfig.setEcoModeRes.equals(cmdType) )
				mgrCmds = mgrEcoCmds;
			else if ( AdragonConfig.setSsidEsafeRes.equals(cmdType) )
				mgrCmds = mgrgsWifiListCmds;			
			else if ( AdragonConfig.setSleepStateRes.equals(cmdType) )
				mgrCmds = mgrSlpCtlListCmds;
			else if ( AdragonConfig.setOffOnRes.equals(cmdType) )
				mgrCmds = mgrTOnOffCmds;

			
			if ( mgrCmds == null || key == null ) {
				ba.insertVisit(null, null, null, "completeHttpCmdId mgrCmds is null or key is null" );
				return null;
			}
				
			Map<Integer, CmdSync> userMap = mgrCmds.get(key);		

			

			//BaseAction.insertVisit(null, null, null, "completeHttpCmdId -1:" + key + ", userId:" + userId);							

			
			if ( userMap == null ) {
				//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 0");							
				return res;
			} 

			//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 0.5");							


			CmdSync cmd = userMap.get(userId);
			

			JSONObject json = null;
			
			if ( cmd != null ) {

				//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 0.6");							
				
				
				synchronized(cmd.getTdLock()){
					try {
						
						HttpServletResponse resp = cmd.getResponse();

						//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 1");							
						
						
						if (ext_str == null)
							json = new JSONObject();
						else
							json = JSONObject.fromObject(ext_str);

						//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 2:" + ext_str );							
						
						if ( AdragonConfig.updateFirmwareRes.equals(cmdType) )  {
							cmd.getTdLock().notify();
							ba.insertVisit(null, key, null, "updateFirmwareRes notify" );
							return res;
						}
						
						
						json.put("cmd", cmdType);
						if ( AdragonConfig.setEcoModeRes.equals(cmdType) ) {
							int snetRes = 0;
							if ( json.containsKey("result") ) {
								snetRes = json.optInt("result");
							}
							
							if (snetRes == 0) 
								json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
							else
								json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
								
						} else if ( AdragonConfig.setSsidEsafeRes.equals(cmdType) ) {
							json.put("ssid", cmd.getCmdPara1());
							json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);							
						} else if ( !Constant.timeUtcFlag && AdragonConfig.getHealthStepRes.equals(cmdType)  ) {
							try {
								WTSugMoveManAction smma = new WTSugMoveManAction();
								JSONObject json1 = new JSONObject();
								json1.put("pet_id", cmd.getPet_id());
								json1.put("day", cmd.getDay());
								json = smma.extGetOneSugExec(json1); 
								json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
							} catch(Exception e) {
								json.put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);								
							}
							
						} else if ( AdragonConfig.getHealthStepRes.equals(cmdType) && Constant.timeUtcFlag ) {
							try {
								WTSugMoveManAction smma = new WTSugMoveManAction();
								JSONObject json1 = new JSONObject();
								json1.put("pet_id", cmd.getPet_id());
								json1.put("rstime", cmd.getRstime());
								json1.put("retime", cmd.getRetime());
								json1.put("devDay", cmd.getDevDay());							
								
								ClientSessionManager csm = WTDevHandler.getClientSessionMangagerInstance();
								String pet_ids = String.valueOf(cmd.getPet_id());
								String petLk = csm.useUserLock(pet_ids);
								if ( tls.isNullOrEmpty(petLk) ) {							
									json = smma.extGetOneSugExecSe(json1);																//运动建议数据的达标情况
								} else {
									synchronized(petLk) {
										json = smma.extGetOneSugExecSe(json1);																//运动建议数据的达标情况									
									}
								}
								
								 
								json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
							} catch(Exception e) {
								json.put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);								
							}
							

						} else if ( AdragonConfig.setOffOnRes.equals(cmdType) ) {
							try {
								DevNotifyApp dna = new DevNotifyApp();
								Integer resTflag = json.optInt("tflag");
								if ( resTflag != cmd.getTflag() )
									json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
								else {				
									//BaseAction.insertVisit(null, key, null, "completeHttpCmdId 3");							
									
									boolean dmRes = dna.proSetTOnOffDbAndMsg(key, cmd);
									if (dmRes)
										json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
									else
										json.put(Constant.RESULTCODE, Constant.FAIL_CODE);										
								}
							} catch(Exception e) {
								json.put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);								
							}						
							
						} else
							json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
							
						//json.put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
						
						if (resp != null)						
							resp.setCharacterEncoding("UTF-8");
						
						if (Constant.cmdDirectResFlag) {			
							if (resp != null)
								resp.getWriter().write(json.toString());
						}
						logger.info("completeHttpCmdId" + json.toString());
						
						res = json.toString();
					} catch (Exception e) {
						ba.insertVisit(null, key, null, "exception completeHttpCmdId exception 1" );				
						
					}
					
					userMap.remove(userId);
					
					synchronized(mgrCmds) {
						mgrCmds.put(key, userMap);
					}
					
					cmd.getTdLock().notify();
					
					
				}			
			}
			
			return res;
			
			
		} catch(Exception e) {
			e.printStackTrace();
			ba.insertVisit(null, key, null, "exception completeHttpCmdId exception 2" );							
			return res;
		}
	
		
	}

	/**
	 * @return the mgrLightCmds
	 */
	public ConcurrentMap<String, Map<Integer, CmdSync>> getMgrLightCmds() {
		return mgrLightCmds;
	}

	/**
	 * @return the mgrUrgentCmds
	 */
	public ConcurrentMap<String, Map<Integer, CmdSync>> getMgrUrgentCmds() {
		return mgrUrgentCmds;
	}

	/**
	 * @return the mgrLctCmds
	 */
	public ConcurrentMap<String, Map<Integer, CmdSync>> getMgrLctCmds() {
		return mgrLctCmds;
	}

	/**
	 * @return the mgrgsWifiListCmds
	 */
	public ConcurrentMap<String, Map<Integer, CmdSync>> getMgrgsWifiListCmds() {
		return mgrgsWifiListCmds;
	}

	/**
	 * @return the mgrUserLock
	 */
	public ConcurrentMap<String, String> getMgrUserLock() {
		return mgrUserLock;
	}

	
}
