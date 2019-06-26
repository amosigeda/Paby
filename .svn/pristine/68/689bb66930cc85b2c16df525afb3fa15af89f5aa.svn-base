package com.wtwd.sys.appinterfaces.liufeng.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppUserDeviceTrackFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;

public class Common {
	
	private static AppUserDeviceTrackFacade infoFacade = ServiceBean.getInstance().getAppUserDeviceTrackFacade();
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//验证user_id和app_token是否合法
	public static boolean isValidationUserInfo(String user_id,String app_token){
		try {
			if(user_id == null || "".equals(user_id) || app_token == null || "".equals(app_token)){
				return false;
			}
			
			WappUsers wu = new WappUsers();
			wu.setCondition(" user_id = "+user_id+" AND app_token = '"+app_token+"' ");
			List<DataMap> list = infoFacade.getAppUserInfo(wu);
			if(list == null || list.size() <= 0){
				return false;
			}
		} catch (SystemException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//根据user_id和device_id查询是否是主设备
	public static boolean isMasterDevice(String user_id,String device_id){
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		try {
			if(user_id == null || "".equals(user_id) || device_id == null || "".equals(device_id)){
				return false;
			}
			WeFencing wf = new WeFencing();
			wf.setCondition(" user_id = "+user_id+" AND device_id = "+device_id);
			List<DataMap> list = infoFacade.queryMasterDeviceInfo(wf);
			if(list == null || list.size() <= 0){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//速度装换为运动类型
	/**
	 * 0~2 慢跑 >2 快跑
	 * @param speed
	 * @return
	 */
	public static String getMoveType(String speed){
		String moveType = "";
		try {
			if(speed == null || "".equals(speed)){
				moveType = "";
			}else{
				float sp = Float.parseFloat(speed);
				if(sp>2){
					moveType = "快跑";
				}else{
					moveType = "慢跑";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return moveType;
		}
		return moveType;
	}
	
	//求时间差
	public static long getMoveTime(String endTime,String startTime){
		long times = 0;
		try {
			if(endTime == null || "".equals(endTime) || startTime == null || "".equals(endTime)){
				
			}else{
				Date end = df.parse(endTime);
				Date start = df.parse(startTime);
				times = end.getTime() - start.getTime();
				times = times/(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return times;
		}
		return times;
	}
	
}
