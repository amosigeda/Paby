package com.wtwd.sys.appinterfaces.newpay;

import java.io.File;
import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class PayBase extends BaseAction  {

	List<DataMap> checkIccid(String iccid) {
		List<DataMap> list = null;
		try {
			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			vo1.setCondition("iccid ='" + iccid
					+ "' limit 1");
			String sid = "";
			list = ServiceBean
					.getInstance()
					.getDeviceActiveInfoFacade()
					.getSsidInfo(vo1);
			
			if (list.size() == 0)
				list = null;

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	

    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }	
}
