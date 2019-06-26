package com.wtwd.common.bean.devicedown;

import net.sf.json.JSONObject;

import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;

public class CmdSosRun implements Runnable {
	String cmdType;
    private String jonStr; 
    boolean flag;
    public CmdSosRun(String cmdType, String jonStr, boolean flag) {
    	this.cmdType = cmdType;
    	this.jonStr = jonStr;
    	this.flag = flag;
	}  
    
    public void run() {
    	try {
    		Tools tls = new Tools();
			WTSigninAction ba = new WTSigninAction();
    		
    		JSONObject object = JSONObject.fromObject(jonStr);
    		int device_id = object.optInt("device_id");	
    		String cmd_time = object.optString("cmd_time");		
    		int duration = object.optInt("duration");
    		int user_id = object.optInt("user_id");
			int led_flag = object.optInt("led_flag");
			int play_flag = object.optInt("play_flag"); 
			String deviceImei = ba.getDeviceImeiFromDeviceId(String.valueOf(device_id));	
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(cmdType);
			cmd.setResponse(null);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(deviceImei, cmd);			
			
			if ( !flag ) {
				
				//cmdDownSetImpl.setGpsMap(deviceImei, true);
				cmdDownSetImpl.setUrgentMode(deviceImei, false, 
						cmd_time, duration, user_id, lock);
				
			} else {
				cmdDownSetImpl.setUrgentMode(deviceImei, true, 
						tls.getBooleanFromInt(led_flag),
						tls.getBooleanFromInt(play_flag),
						cmd_time, duration, user_id, lock );							
			}
			
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
