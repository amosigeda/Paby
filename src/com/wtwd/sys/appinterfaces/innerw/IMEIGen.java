package com.wtwd.sys.appinterfaces.innerw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;

//http://blog.csdn.net/zengxx1989/article/details/49886703
//JAVA 实现 IMEI校验码算法
public class IMEIGen {
	  
    /** 
     * @param args 
     */  
    public static void mainBkk(String[] args) {  
        String code = "35213806535234";  
        String newCode = genCode(code);        	

        System.out.println("======end" +  code + newCode);

        
    }  

    static void proHandCancelSim() throws SystemException {
    	//history 893107061704934506 
    	//        893107061704934501 
    	//        893107061704934508
    	String iccid = "893107061704935074";
    	String stopTime = "2018-01-29";
    	String belongCompany = "1";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		
		String cardStatus = "200";
		String message = "ok";
		String customerId="admin";
		
    	
    	DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("imei ='"
				+ iccid
				+ "'and iccid='"
				+ iccid
				+ "' and card_status='200' and message='ok' and belong_company='"
				+ "1" + "' limit 1");
		List<DataMap> cancelList = ServiceBean.getInstance()
				.getDeviceActiveInfoFacade()
				.getcancleImeiInfo(vo);

		if (cancelList.size() <= 0) {
			System.out.println(iccid + "以前没有支付信息直接insert-----");
			// 2表示六个月
			vo.setDeviceImei(iccid);
			vo.setCreateTime(sf.format(new Date()));
			vo.setCardStatus( cardStatus );
			vo.setMessage(message);
			vo.setBrandName(customerId);
			vo.setDeviceAge("2");

			Calendar c = Calendar.getInstance();// 获得一个日历的实例
		
			//Date date= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期
			vo.setUpdateTime(stopTime);
			vo.setIccid(iccid);
			vo.setBelongCompany(belongCompany);
			ServiceBean.getInstance()
					.getDeviceActiveInfoFacade()
					.insertCancelSubSriptionInfo(vo);

			System.out.println(iccid + "finish insertCancelSubSriptionInfo");
		} else {
			String id = cancelList.get(0).get("id") + "";

			vo.setUpdateTime(stopTime);
			vo.setCondition("id='" + id + "'");
			int xiugai = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade()
					.updateCancelSubSriptionInfo(vo);
			
			System.out.println(iccid + "finish updateCancelSubSriptionInfo");
			
		}
    	
    }

    
    static void proHandSimInfo() throws SystemException {
    	//history 893107061704934506 
    	//        893107061704934501 
    	//        893107061704934508
    	String iccid = "893107061704934470";
    	String stopTime = "2017-12-11";
    	String belongCompany = "1";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd");
		
		DeviceActiveInfo vo1 = new DeviceActiveInfo();
		
		
		String cardStatus = "200";
		String message = "ok";
		String customerId="admin";
		
    	
    	DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("iccid ='"
				+ iccid
				+ "'");
		List<DataMap> cancelList = ServiceBean.getInstance()
				.getDeviceActiveInfoFacade()
				.getSsidInfo(vo);

		if (cancelList.size() <= 0) {
			System.out.println(iccid + "以前没有信息直接insert-----");
			// 2表示六个月
			vo.setIccid(iccid);
			vo1.setCardStatus("1");
			vo1.setCreateTime("2017-11-11");
			vo1.setUpdateTime("2017-11-11");
			ServiceBean
					.getInstance()
					.getDeviceActiveInfoFacade()
					.insertSmsInfo(vo1);

		} else {
		}
    	
    }
    
    
    
    void proImei() {
        String code = "35213806";  
        int il = 990;
        String codel = null;
        String newCode = null;
        String fCode = null;
        for (il = 499000 ; il <= 501429; il++) {
        	codel = code + il;
            newCode = genCode(codel);        	
            fCode = codel + newCode;
            System.out.println(fCode);
            upDb(fCode);
        }

        System.out.println("======end");

    	
    }
	
	/** 
     * @param args 
	 * @throws SystemException 
     */  
    public static void main(String[] args) throws SystemException {  
    	proHandCancelSim();
    	
    	//proHandSimInfo();
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
				wdeviceActiveInfo.setUlfq(0);		
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
    
    public static String genCode(String code){  
        int total=0,sum1=0,sum2 =0;  
        int temp=0;  
        char [] chs = code.toCharArray();  
        for (int i = 0; i < chs.length; i++) {             
            int num = chs[i] - '0';     // ascii to num  
            //System.out.println(num);  
            /*(1)将奇数位数字相加(从1开始计数)*/  
            if (i%2==0) {  
                sum1 = sum1 + num;  
            }else{  
                /*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/  
                temp=num * 2 ;  
                if (temp < 10) {  
                    sum2=sum2+temp;  
                }else{  
                    sum2 = sum2 + temp + 1 -10;  
                }  
            }  
        }  
        total = sum1+sum2;  
        /*如果得出的数个位是0则校验位为0,否则为10减去个位数 */  
        if (total % 10 ==0) {  
            return "0";  
        }else{  
            return (10 - (total %10))+"";  
        }  
          
    }  
  
}
