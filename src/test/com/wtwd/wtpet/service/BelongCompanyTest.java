package test.com.wtwd.wtpet.service;

import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class BelongCompanyTest {
	
	public static void main(String[] args) throws SystemException {
		DeviceActiveInfo vo = new DeviceActiveInfo();
		String iccid="123";
		vo.setCondition("imei ='" + iccid + "'and iccid='" + iccid
				+ "' and card_status='200' and message='ok' and belong_company='"
				+ 1 + "' limit 1");
		List<DataMap> cancelList = ServiceBean.getInstance()
				.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);
		System.out.println("111="+cancelList.size());
	}

}
