package com.wtwd.pet.app.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wtwd.pet.app.entity.WMsgInfo;

public class MyThread extends Thread {
	private WMsgInfo curMsg;
	private static final Log log = LogFactory.getLog(MyThread.class);
	
	public MyThread() {
	}

	public MyThread(WMsgInfo data) {
		curMsg = data;
	}
	public void run() {  
		
		Integer toUsrId = curMsg.getTo_usrid();
		
		if ( toUsrId > 0 ) {
			String app_type = curMsg.getApp_type();
			if (  !App.isNullOrEmpty(curMsg.getMsg_content()) ) {
				if ("1".equals(app_type) && !App.isNullOrEmpty(curMsg.getIos_token()))
					try {
						log.info("push thread app ios enter::"+curMsg.getIos_real());					
						App ap=  new App();
						ap.proSend(curMsg.getIos_real(), curMsg, curMsg.getIos_token(), true);
						ap = null;
					} catch (Exception e) {
						log.error("push thread app ios:", e);
					}
				if (!"1".equals(app_type) &&  !App.isNullOrEmpty(curMsg.getDevice_token()))
					try {
						App ap=  new App();
						ap.proSend(curMsg.getIos_real(), curMsg, curMsg.getDevice_token(), false);
						ap = null;
					} catch (Exception e) {
						log.error("push thread app android:", e);
					}
			}
		} else {
			App.proBrdAllDevice(curMsg);
		}

	}  
} 
