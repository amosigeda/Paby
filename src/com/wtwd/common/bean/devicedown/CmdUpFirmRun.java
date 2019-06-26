package com.wtwd.common.bean.devicedown;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

public class CmdUpFirmRun implements Runnable {
    private String jonStr; 
    Thread lock;
    int user_id;
    public CmdUpFirmRun(String jonStr, Thread lock, int user_id) {
    	this.user_id = user_id;
    	this.jonStr = jonStr;
    	this.lock = lock;
	}  
    
    public void run() {
    	try {
    		Tools tls = new Tools();
			WTSigninAction ba = new WTSigninAction();
    		
    		JSONObject object = JSONObject.fromObject(jonStr);
	    	LocationInfoHelper lih = new LocationInfoHelper();
    		
    		int device_id = object.optInt("device_id");	
    		//int user_id = object.optInt("user_id");
			String deviceImei = ba.getDeviceImeiFromDeviceId(String.valueOf(device_id));	
			//CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();	//控制闪灯

			//CmdSync cmd = new CmdSync();

			Date d1 = new Date();			
			long tout = 60 * 50;
			
			synchronized(lock){
				lock.wait(1000*tout);
			    //或者wait()
			}
			Date d2 = new Date();
			
			if ( tls.getSecondsBetweenDays(d1, d2) >=  tout   ) 
				ba.insertVisit(null, deviceImei, null, "CmdUpFirmRun rec d1:" + 
					d1.getTime() + " d2:" + d2.getTime() +
					", d2 - d1 : " + tls.getSecondsBetweenDays(d1, d2));				
			
			Logger logger = Logger.getLogger(CmdUpFirmRun.class);
			
			if ( tls.getSecondsBetweenDays(d1, d2) >= tout ) { 
				logger.info("CmdUpFirmRun  rec d1: " + 
						d1.toString() + " d2:" + d2.toString() +
						", d2 - d1 : " + tls.getSecondsBetweenDays(d1, d2));	
				
				WdeviceActiveInfo vo = new WdeviceActiveInfo();
				WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
						
				vo.setCondition("device_id="+ device_id);
				vo.setUfirm_stat(0);
				
				fd.updatewDeviceExtra(vo);
				
				WMsgInfo aMsg = new WMsgInfo();
				aMsg.setDevice_id(device_id);
				

				
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_UPDATE_FIRM_FINISH);
				aMsg.setDevice_id(device_id);	
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {								
					aMsg.setMsg_date(ba.getDeviceNow(device_id));
				}
					
				aMsg.setMsg_content("{\"errorCode\":1609}");
				aMsg.setHide_flag(Tools.ZeroString);
				aMsg.setError_code(1609);
				aMsg.setSummary(lih.getMsgContentFromMsg(aMsg, null, null));
				
				
				lih.proCommonInnerMsg(aMsg, user_id);
				

				
			} 
		
			
			
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
