package com.wtwd.common.bean.devicedown;

import net.sf.json.JSONObject;

import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;

public class CmdEsafeRun implements Runnable {
	String cmdType;
    private String jonStr; 
    boolean flag;
    public CmdEsafeRun(String cmdType, String jonStr, boolean flag) {
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

			String mac = object.optString("bssid").trim();			
			String ssid = object.optString("ssid").trim();			
			int flag = object.optInt("flag");			    		
    		
			String device_imei = ba.getDeviceImeiFromDeviceId(String.valueOf(device_id));	
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯

			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();
			cmd.setCmdName(cmdType);
			cmd.setResponse(null);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setCmdPara1(ssid);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);			
			boolean res = false;
			
			if (flag == 1 )			
				res = cmdDownSetImpl.setSsidEsafe(device_imei, true, mac, ssid,  user_id, lock);
			else
				res = cmdDownSetImpl.setSsidEsafe(device_imei, false, mac, ssid,  user_id, lock);

			if ( !res ) {
				//需要产生推送通知APP操作失败
			}
		
			
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}