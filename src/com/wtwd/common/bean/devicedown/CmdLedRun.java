package com.wtwd.common.bean.devicedown;

import net.sf.json.JSONObject;

import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;

public class CmdLedRun implements Runnable {
	String cmdType;
    private String jonStr; 
    boolean flag;
    public CmdLedRun(String cmdType, String jonStr, boolean flag) {
    	this.cmdType = cmdType;
    	this.jonStr = jonStr;
    	this.flag = flag;
	}  
    
    public void run() {
    	try {
			WTSigninAction ba = new WTSigninAction();
    		
    		JSONObject object = JSONObject.fromObject(jonStr);
    		int device_id = object.optInt("device_id");	
    		int user_id = object.optInt("user_id");
			String deviceImei = ba.getDeviceImeiFromDeviceId(String.valueOf(device_id));	
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(cmdType);
			cmd.setResponse(null);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			
			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(deviceImei, cmd);
			
			if ( !cmdDownSetImpl.setLedState( deviceImei, 3, flag, user_id, lock) ) {
				//需要产生推送通知APP操作失败
			}
			
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
