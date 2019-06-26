package com.wtwd.sys.appinterfaces.innerw;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;

//http://blog.csdn.net/zengxx1989/article/details/49886703
//JAVA 实现 IMEI校验码算法
public class IMEIPreICCID {
	  
  
    
	
	/** 
     * @param args 
	 * @throws SystemException 
     */  
    public static void main(String[] args) throws SystemException {  
    	//proHandCancelSim();
    }  
      
    public static void upDb(String code) {
    	try {
			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			wdeviceActiveInfo.setCondition("a.device_imei = '"+ code+"'");

			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			List<DataMap> wdeviceActiveInfos = wdeviceActiveInfoFacade.getWdeviceActiveInfo(wdeviceActiveInfo);
			wdeviceActiveInfo.setCondition("device_imei = '"+code+"'");

			Tools tls = new Tools();	
			int device_id = 0;

			
			if (wdeviceActiveInfos.isEmpty()) {
				wdeviceActiveInfo.setDevice_imei(code);
				wdeviceActiveInfo.setDevice_phone("paby");
				wdeviceActiveInfo.setDevice_name("paby");
				wdeviceActiveInfo.setDevice_update_time(tls.getUtcDateStrNowDate());
				wdeviceActiveInfo.setDevice_disable(Tools.OneString);
				wdeviceActiveInfo.setBelong_project(1);
				wdeviceActiveInfoFacade.insertWdeviceActiveInfo(wdeviceActiveInfo);
				wdeviceActiveInfo.setCondition("device_imei = '"+code+"'");						
				wdeviceActiveInfos = wdeviceActiveInfoFacade.getData(wdeviceActiveInfo);
				device_id = (Integer) wdeviceActiveInfos.get(0).getAt("device_id");
				wdeviceActiveInfo.setDevice_id(device_id);
				wdeviceActiveInfo.setTime_zone("Asia/Shanghai");
				wdeviceActiveInfo.setEco_mode(Tools.OneString);				
				wdeviceActiveInfoFacade.insertwDeviceExtra(wdeviceActiveInfo);
			} else {
				device_id = (Integer) wdeviceActiveInfos.get(0).getAt("device_id");
				wdeviceActiveInfo.setCondition("device_imei = '"+code+"'");
				//wdeviceActiveInfo.setDevice_imei(serieNo);
				wdeviceActiveInfo.setUlfq(100);		
				wdeviceActiveInfo.setuLTe(0);
				wdeviceActiveInfo.setTest_status(0);
					
				wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
				
			}
			
			tls = null;
			wdeviceActiveInfo = null;
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
  
}
