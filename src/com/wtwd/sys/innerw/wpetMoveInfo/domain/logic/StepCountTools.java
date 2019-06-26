package com.wtwd.sys.innerw.wpetMoveInfo.domain.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.EndServlet;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WPetMoveInfoFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

public class StepCountTools {
	private String shoulder_height = null ;
	private String weight = null;
	public String getShoulder_height() {
		return shoulder_height;
	}

	public void setShoulder_height(String shoulder_height) {
		this.shoulder_height = shoulder_height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public WpetMoveInfo proPetMoveInfo(WpetMoveInfo pobj) {
		Tools tls = new Tools();	
		
		long secs = tls.getSecondsBetweenDays(pobj.getStart_time(), pobj.getEnd_time());
		double speed = pobj.getStep_number()  * 60.0 / secs;
		WpetMoveInfo retObj = new WpetMoveInfo();
		retObj.setStep_number(pobj.getStep_number());
		retObj.setPets_pet_id(pobj.getPets_pet_id());
		retObj.setStart_time(pobj.getStart_time());
		retObj.setEnd_time(pobj.getEnd_time());
		retObj.setUp_time(pobj.getUp_time());
		double route = pobj.getStep_number() * Double.parseDouble(shoulder_height) / 3.0;
		retObj.setRoute((float)route);
		retObj.setCalories(pobj.getStep_number() * Double.parseDouble(weight) * route);
		retObj.setSpeed(speed);
			
		return retObj;
		
	}

	
	
	public StepCountTools() {
	} 
	
    public void proMoveDoMsg(WpetMoveInfo info, Integer device_id) {
    	WTSigninAction ba = new WTSigninAction();
    	
    	try {
			Tools tls = new Tools();
    		
	    	LocationInfoHelper lih = new LocationInfoHelper();
    		
	    	Double speed = info.getSpeed();
	    	
	    	Boolean done_flag = false;
	    	if ( speed < 7.1111 ) {
	    		return;
	    	}
	    	Integer pet_id = info.getPets_pet_id();
	    	WpetMoveInfo vo = new WpetMoveInfo();
	    	WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
	    	WPetMoveInfo vo1 = new WPetMoveInfo();
	    	WPetMoveInfoFacade fd2 = ServiceBean.getInstance().getwPetMoveInfoFacade();
			//final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	    	
			String cDay = lih.getDevCurrentDay(device_id);
			StringBuffer sb = new StringBuffer("pet_id=");
			//String day = info.getEnd_time().substring(0, 10);
			String devZone = ba.getDeviceTimeZone(String.valueOf(device_id));
			String devUtcDateStr = null;			
//			if (Constant.cmdDirectResFlag) {													
				devUtcDateStr = info.getEnd_time();
//			} else {
//				devUtcDateStr = Tools.timeConvert(info.getEnd_time(), "UTC", devZone );				
//			}
				
			String day = devUtcDateStr.substring(0, 10);
//			String stime = day + " 00:00:00";
//			String etime = info.getEnd_time();
			
			
			if (!day.equals(cDay) )	{	//只对当天的数据产生运动提醒消息 
				ba.insertVisit(null, null, String.valueOf(device_id), "proMoveDoMsg not current day");								
				return;
			}
			
			sb.append(pet_id);
			sb.append(" and up_time='");
			sb.append(day);
			sb.append(" ' and ( msg_type=0 or msg_type=1 ) order by msg_type");
	
			vo.setCondition(sb.toString());
			List<DataMap> list = fd.getPetMoveMsgStatus(vo);
			Integer lsize = list.size();
			
			if ( lsize < 2 ) {	//还有可能需要产生已完成运动情况的消息
										//一共产生两次消息，一次是提示完成50%以上,一次是100%以上
				//如果 lsize == 0. 有可能需要产生完成50%以上的消息，否则可能需要产生
				//   完成100%以上的消息
				Map<String, Object> p = new HashMap<String, Object>();  
				p.put("rpet_id", String.valueOf(pet_id) ) ;
				p.put("rup_time", day.trim()  ) ;
				//p.put("rup_time", day.trim() );		
				//p.put("rstime", stime);
				//p.put("retime", etime);
				fd.call_calcSugExec(p);
				//fd.call_calcSugExecSe(p);
				
				sb.delete(0, sb.length() );
				sb.append("a.pet_id=");
				sb.append(pet_id);
				sb.append(" and a.up_time='");
				sb.append(day);
				sb.append(" '");
				
				vo1.setCondition(sb.toString());
				List<DataMap> list2 = fd2.getOneSugExec(vo1);
				if(list2 != null/* && list.size() > 0*/){
					if (list2.size() == 0 || tls.isNullOrEmpty(list2.
							get(0).getAt("per_move")) ) {
						return;
					}
					Double per_move = Double.parseDouble(list2.
							get(0).getAt("per_move").toString().trim());
	
					if ( EndServlet.dummyTestFlag )
						per_move = 100.02d;
					
					if (lsize == 0 && per_move > 50.0000 ) {
						//产生完成50%以上的消息
						if ( per_move > 100.0000 ) 
							lih.proMoveDoMsg(1, device_id, info.getEnd_time());						
						else
							lih.proMoveDoMsg(0, device_id, info.getEnd_time());
							
					}
	
					
					if (lsize == 1 && "0".equals(list.get(0).getAt("msg_type").toString()) && per_move > 100.0000 ) {
						//产生完成100%以上的消息
						lih.proMoveDoMsg(1, device_id, info.getEnd_time());
					}
				
				}
				
				
			}
			    	
	    	
	    	vo = null;
	    	vo1 = null;
	    	sb = null;
    	} catch (Exception e) {
    		e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proMoveDoMsg exception:");				
    		
    	}
    	
    }

    
    public void proMoveDoMsgSe(WpetMoveInfo info, Integer device_id) {
    	WTSigninAction ba = new WTSigninAction();
    	
    	try {
			Tools tls = new Tools();
    		
	    	LocationInfoHelper lih = new LocationInfoHelper();
    		
	    	Double speed = info.getSpeed();
	    	
	    	Boolean done_flag = false;
	    	if ( speed < 7.1111 ) {
	    		return;
	    	}
	    	Integer pet_id = info.getPets_pet_id();
	    	WpetMoveInfo vo = new WpetMoveInfo();
	    	WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
	    	WPetMoveInfo vo1 = new WPetMoveInfo();
	    	WPetMoveInfoFacade fd2 = ServiceBean.getInstance().getwPetMoveInfoFacade();
			//final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String cDay = lih.getDevCurrentDay(device_id);
			StringBuffer sb = new StringBuffer("pet_id=");
			//String day = info.getEnd_time().substring(0, 10);
			String devZone = ba.getDeviceTimeZone(String.valueOf(device_id));
			String devUtcDateStr = null;			
			devUtcDateStr = info.getEnd_time();

			String devUtcDateStr1 = info.getStart_time();
			String upDay1 = tls.timeConvert(devUtcDateStr1, "UTC", devZone ).substring(0,10);				
			

			
			String upDay = tls.timeConvert(devUtcDateStr, "UTC", devZone ).substring(0,10);				

			if (!upDay.equals(upDay1) )	{	//只对当天的数据产生运动提醒消息 
				ba.insertVisit(null, null, String.valueOf(device_id), "proMoveDoMsgSe begin and end time in diff day");								
				return;
			}
			
			
			
			String stime = cDay + " 00:00:00";
			String etime = cDay + " 23:59:59";
			String rstime = tls.timeConvert(stime, devZone, "UTC");				
			String retime = tls.timeConvert(etime, devZone, "UTC");				
					
			String day = rstime.substring(0, 10);

			
			
			if (!upDay.equals(cDay) )	{	//只对当天的数据产生运动提醒消息 
				ba.insertVisit(null, null, String.valueOf(device_id), "proMoveDoMsgSe not current day");								
				return;
			}
			
			sb.append(pet_id);
			sb.append(" and up_time='");
			sb.append(day);
			sb.append(" ' and ( msg_type=0 or msg_type=1 ) order by msg_type");
	
			vo.setCondition(sb.toString());
			List<DataMap> list = fd.getPetMoveMsgStatus(vo);
			Integer lsize = list.size();
			
			if ( lsize < 2 ) {	//还有可能需要产生已完成运动情况的消息
										//一共产生两次消息，一次是提示完成50%以上,一次是100%以上
				//如果 lsize == 0. 有可能需要产生完成50%以上的消息，否则可能需要产生
				//   完成100%以上的消息
				Map<String, Object> p = new HashMap<String, Object>();  
				p.put("rpet_id", String.valueOf(pet_id) ) ;
				p.put("rstime", rstime);
				p.put("retime", retime);
				fd.call_calcSugExecSe(p);
				//fd.call_calcSugExecSe(p);
				
				sb.delete(0, sb.length() );
				sb.append("a.pet_id=");
				sb.append(pet_id);
				sb.append(" and a.up_time='");
				sb.append(day);
				sb.append(" '");
				
				vo1.setCondition(sb.toString());
				List<DataMap> list2 = fd2.getOneSugExec(vo1);
				if(list2 != null/* && list.size() > 0*/){
					if (list2.size() == 0 || tls.isNullOrEmpty(list2.
							get(0).getAt("per_move")) ) {
						return;
					}
					Double per_move = Double.parseDouble(list2.
							get(0).getAt("per_move").toString().trim());
	
					if ( EndServlet.dummyTestFlag )
						per_move = 100.02d;
					
					if (lsize == 0 && per_move > 50.0000 ) {
						//产生完成50%以上的消息
						if ( per_move > 100.0000 ) 
							lih.proMoveDoMsg(1, device_id, day);						
						else
							lih.proMoveDoMsg(0, device_id, day);
							
					}
	
					
					if (lsize == 1 && "0".equals(list.get(0).getAt("msg_type").toString()) && per_move > 100.0000 ) {
						//产生完成100%以上的消息
						lih.proMoveDoMsg(1, device_id, day);
					}
				
				}
				
				
			}
			    	
	    	
	    	vo = null;
	    	vo1 = null;
	    	sb = null;
    	} catch (Exception e) {
    		e.printStackTrace();
			ba.insertVisit(null, null, String.valueOf(device_id), "exception proMoveDoMsg exception:");				
    		
    	}
    	
    }
    

    
    
}
