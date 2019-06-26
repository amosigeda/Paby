package com.wtwd.sys.appinterfaces.innerw;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.bean.devicedown.cmdobject.CmdSync;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.client.handler.AdragonConfig;
import com.wtwd.sys.client.handler.WTDevHandler;
import com.wtwd.sys.client.manager.ClientSessionManager;
import com.wtwd.sys.innerw.liufeng.domain.WPetMoveInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WPetMoveInfoFacade;
import com.wtwd.sys.innerw.wpetMoveInfo.domain.WpetMoveInfo;


public class WTSugMoveManAction extends BaseAction {

	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTSugMoveManAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();		
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}

			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");
			//String user_ids = String.valueOf(user_id);

			String device_id = getDeviceIdFromPetId(object.optString("pet_id"));
			
			//super.logAction(String.valueOf(user_id), Integer.parseInt(device_id), "WTSugMoveManAction");			
			
			int pet_id = object.optInt("pet_id");
			String pet_ids = String.valueOf(pet_id);
			
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if ( ( result = verifyUserPet(String.valueOf(user_id), 
							String.valueOf(pet_id))) == Constant.SUCCESS_CODE ) {										
						if (cmd.equals("getOneSug")) {	//查询具体某天							                            //的运动建议数据
							proGetOneSug(object);
							//result = Constant.SUCCESS_CODE;													
						} else if ( cmd.equals("getOneSugExec")) {   //查询具体某天的
							if ( Constant.timeUtcFlag )
								return null;
							proGetOneSugExec(object);																	//运动建议数据的达标情况
							//result = Constant.SUCCESS_CODE;																				
						} else if ( cmd.equals("getOneSugExecSe")) {   //查询具体某天的

							ClientSessionManager csm = WTDevHandler.getClientSessionMangagerInstance();
							String petLk = csm.useUserLock(pet_ids);
							if ( tls.isNullOrEmpty(petLk) ) {							
								proGetOneSugExecSe(object);																	//运动建议数据的达标情况
							} else {
								synchronized(petLk) {
									proGetOneSugExecSe(object);																	//运动建议数据的达标情况									
								}
							}
								
							//result = Constant.SUCCESS_CODE;																				
						
						} else if ( cmd.equals("getSugExecListByDay")) {   //查询具体某
														//个时间段内的运动建议数据的达标情况
							if ( Constant.timeUtcFlag )
								return null;
							proGetSugExecList(object);
							//result = Constant.SUCCESS_CODE;																				
						} else if ( cmd.equals("getSugExecListByDaySe")) {   //查询具体某
							//个时间段内的运动建议数据的达标情况
							ClientSessionManager csm = WTDevHandler.getClientSessionMangagerInstance();
							String petLk = csm.useUserLock(pet_ids);
							if ( tls.isNullOrEmpty(petLk) ) {							
								proGetSugExecListSe(object);																//运动建议数据的达标情况
							} else {
								synchronized(petLk) {
									proGetSugExecListSe(object);																//运动建议数据的达标情况									
								}
							}
							
							
							
							//result = Constant.SUCCESS_CODE;																				
						} else if (cmd.equals("getSugExecListByMon") || cmd.equals("getSugExecListByMonth")) {	//查询一年中的月度统计
							if ( Constant.timeUtcFlag )
								return null;
							proGetSugExecListByMon(object);							
						} else if (cmd.equals("getSugExecListByMonSe") ) {	//查询一年中的月度统计
							ClientSessionManager csm = WTDevHandler.getClientSessionMangagerInstance();
							String petLk = csm.useUserLock(pet_ids);
							if ( tls.isNullOrEmpty(petLk) ) {							
								proGetSugExecListByMonSe(object);																//运动建议数据的达标情况
							} else {
								synchronized(petLk) {
									proGetSugExecListByMonSe(object);																//运动建议数据的达标情况									
								}
							}
																				

						} else if ( cmd.equals("getMoveList")) {   //查询具体某天的
											//运动数据详细清单
							proGetMoveList(object);
							result = Constant.SUCCESS_CODE;																				
						} else if ( cmd.equals("gHealthStep")) {   //
							if ( !Constant.timeUtcFlag )
								return null;

							if ( "1.7".equals(Constant.GHEALTH_PROTOCOL_VER ) ) {
								proGHealthStepFast(object, response);
								return null;
							} else {
								if ( proGHealthStep(object) )
									result = Constant.SUCCESS_CODE;
								else
									result = Constant.FAIL_CODE;								
							}
						} else if ( cmd.equals("gHealthStepSe")) {   //
							if ( "1.7".equals(Constant.GHEALTH_PROTOCOL_VER ) ) {
								
								proGHealthStepFastSe(object, response);
								return null;
							} 
						} else {
							result = Constant.ERR_INVALID_PARA;								
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			
			logger.debug(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());


		
		return null;
	}	
	
	
	void proGetOneSug(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		String day = tls.getSafeStringFromJson(object, "day");

		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(day)){
			return;
		}else{
			wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+day+"'");
			
			List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);

			if (list.size() == 0) {
				Map<String, Object> p = new HashMap<String, Object>();  
				p.put("rpet_id", String.valueOf(pet_id) ) ;
				p.put("rup_time", day.trim() + " 00:00:00" ) ;				
				p.put("rrecMoveType", "");
				p.put("rrecMoveTime", "");				
				fd.call_calcSugMove(p);

				json.put("recMoveType", p.get("rrecMoveType").toString());
				json.put("recMoveTime", String.valueOf(Integer.parseInt(p.get("rrecMoveTime")
						.toString() ) * 60	) );
				
				//需要插入到运动建议信息表 wPetSugMove ...
				result = Constant.SUCCESS_CODE;													
				return;
			}
			
			
			
			if(list != null /*&& list.size() > 0*/){
				
				if (list.size() == 0) {
					result = Constant.ERR_RES_NOT_EXIST;
					return;
				}
				
				json.put("recMoveType", "");
				json.put("recMoveTime", "");

				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String move_type = dMap.getAt("recMoveType").toString();
					String move_time = dMap.getAt("recMoveTime").toString();
					
					json.put("recMoveType", move_type);
					json.put("recMoveTime", move_time);
					
				}
				
				result = Constant.SUCCESS_CODE;													
				
			}			
		}		
		
	}

	void proGetOneSugExec(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		int user_id = object.optInt("user_id");
		String device_id = myDeviceId;//BaseAction.getDeviceIdFromPetId(object.optString("pet_id"));

		if ( myDeviceId == null ) {
			super.logAction(String.valueOf(user_id), -101, "proGetOneSugExec myDeviceId is null");
			return ;
		}

		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();

		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		//String day = Tools.getSafeStringFromJson(object, "day");
		String day = object.getString("day");

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();
		super.logAction(String.valueOf(user_id), Integer.parseInt(device_id), "rpet_id:" 
				+ pet_id + ",rup_time:" + day.trim() );			
		
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rup_time", day.trim()  ) ;
		try {
			fd.call_calcSugExec(p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call_calcSugExec rpet_id:"+ pet_id +",rup_time:" + day.trim());
			throw new SystemException("proGetOneSugExec call_calcSugExec error");
		}

		
		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(day)){
			return;
		}else{
			
			wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+day+"'");
			
			List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);
			
			if(list != null /*&& list.size() > 0*/){
				
				if (list.size() == 0) {
					result = Constant.ERR_RES_NOT_EXIST;
					return;
				}
				
				json.put("sug_type", "");
				json.put("sug_time", "");

				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String sug_type = dMap.getAt("recMoveType").toString();
					String sug_time = dMap.getAt("recMoveTime").toString();
					
					json.put("sug_type", sug_type);
					json.put("sug_time", sug_time);
					
				}
				
			}			
			
			
			wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+day+"'");			
			list.clear();
			list = wpmFacade.getOneSugExec(wmv);
			if(list != null/* && list.size() > 0*/){
				if (list.size() == 0 ) {
					result = Constant.ERR_RES_NOT_EXIST;
					return;
				}

				json.put("per_move", "");
				json.put("route", "");
				json.put("step_move", "");
				json.put("calories", "");
				json.put("exec_time", "");

				json.put("dexec_time", "");
				json.put("lexec_time", "");
				json.put("tot_sleep", "");
				json.put("tot_dsleep", "");
				json.put("tot_lsleep", "");
				json.put("max_unit_steps", "");				
				
				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String per_move = dMap.getAt("per_move").toString();
					String route = dMap.getAt("route").toString();
					String step_move = dMap.getAt("step_move").toString();
					String calories = dMap.getAt("calories").toString();
					String exec_time = dMap.getAt("exec_time").toString();
					
					String dexec_time = dMap.getAt("dexec_time").toString();
					String lexec_time = dMap.getAt("lexec_time").toString();
					String tot_sleep = dMap.getAt("tot_sleep").toString();
					String tot_dsleep = dMap.getAt("tot_dsleep").toString();
					String tot_lsleep = dMap.getAt("tot_lsleep").toString();
					String max_unit_steps = dMap.getAt("max_unit_steps").toString();
					
					
					json.put("per_move", per_move);
					json.put("route", route);
					json.put("step_move", step_move);
					json.put("calories", calories);
					json.put("exec_time", exec_time);

					json.put("dexec_time", dexec_time);
					json.put("lexec_time", lexec_time);
					json.put("tot_sleep", tot_sleep);
					json.put("tot_dsleep", tot_dsleep);
					json.put("tot_lsleep", tot_lsleep);
					json.put("max_unit_steps", max_unit_steps);				
					
				}
				
			}			
			
			//wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(b.up_time,'%Y-%m-%d') = '"+day+"'");			
			wmv.setCondition(" pet_id = "+pet_id+" AND TIMESTAMPDIFF(DAY, start_time, '"+day+"') = 0 ");			

			List<DataMap> list1 = wpmFacade.getOneSugExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				json.put("mlist", jsonArr);
				
			}
				

			list1.clear();
			list1 = wpmFacade.getOneSleepExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				json.put("slist", jsonArr);
				
			}
			
			
		}		

		result = Constant.SUCCESS_CODE;
		
		
	}

	public JSONObject extGetOneSugExec(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		JSONObject jsonRes = new JSONObject();
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();

		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		String day = tls.getSafeStringFromJson(object, "day");

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();  
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rup_time", day.trim()  ) ;				
		fd.call_calcSugExec(p);

		
		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(day)){
			return null;
		}else{
			
			wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+day+"'");
			
			List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);
			
			if(list != null /*&& list.size() > 0*/){
				
				if (list.size() == 0) {
					result = Constant.ERR_RES_NOT_EXIST;
					return null;
				}
				
				jsonRes.put("sug_type", "");
				jsonRes.put("sug_time", "");

				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String sug_type = dMap.getAt("recMoveType").toString();
					String sug_time = dMap.getAt("recMoveTime").toString();
					
					jsonRes.put("sug_type", sug_type);
					jsonRes.put("sug_time", sug_time);
					
				}
				
			}			
			
			
			wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+day+"'");			
			list.clear();
			list = wpmFacade.getOneSugExec(wmv);
			if(list != null/* && list.size() > 0*/){
				if (list.size() == 0 ) {
					result = Constant.ERR_RES_NOT_EXIST;
					return null;
				}

				jsonRes.put("per_move", "");
				jsonRes.put("route", "");
				jsonRes.put("step_move", "");
				jsonRes.put("calories", "");
				jsonRes.put("exec_time", "");

				jsonRes.put("dexec_time", "");
				jsonRes.put("lexec_time", "");
				jsonRes.put("tot_sleep", "");
				jsonRes.put("tot_dsleep", "");
				jsonRes.put("tot_lsleep", "");
				jsonRes.put("max_unit_steps", "");				
				
				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String per_move = dMap.getAt("per_move").toString();
					String route = dMap.getAt("route").toString();
					String step_move = dMap.getAt("step_move").toString();
					String calories = dMap.getAt("calories").toString();
					String exec_time = dMap.getAt("exec_time").toString();
					
					String dexec_time = dMap.getAt("dexec_time").toString();
					String lexec_time = dMap.getAt("lexec_time").toString();
					String tot_sleep = dMap.getAt("tot_sleep").toString();
					String tot_dsleep = dMap.getAt("tot_dsleep").toString();
					String tot_lsleep = dMap.getAt("tot_lsleep").toString();
					String max_unit_steps = dMap.getAt("max_unit_steps").toString();
					
					
					jsonRes.put("per_move", per_move);
					jsonRes.put("route", route);
					jsonRes.put("step_move", step_move);
					jsonRes.put("calories", calories);
					jsonRes.put("exec_time", exec_time);

					jsonRes.put("dexec_time", dexec_time);
					jsonRes.put("lexec_time", lexec_time);
					jsonRes.put("tot_sleep", tot_sleep);
					jsonRes.put("tot_dsleep", tot_dsleep);
					jsonRes.put("tot_lsleep", tot_lsleep);
					jsonRes.put("max_unit_steps", max_unit_steps);				
					
				}
				
			}			
			
			//wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(b.up_time,'%Y-%m-%d') = '"+day+"'");			
			wmv.setCondition(" pet_id = "+pet_id+" AND TIMESTAMPDIFF(DAY, start_time, '"+day+"') = 0 ");			

			List<DataMap> list1 = wpmFacade.getOneSugExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				jsonRes.put("mlist", jsonArr);
				
			}
				

			list1.clear();
			list1 = wpmFacade.getOneSleepExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				jsonRes.put("slist", jsonArr);
				
			}
			
			
		}		

		return jsonRes;
		
		
	}
	
	
	void proGetSugExecList(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		
		int pet_id = object.getInt("pet_id");
		String startDay = tls.getSafeStringFromJson(object, "startDay");
		String endDay = tls.getSafeStringFromJson(object, "endDay");
		String frmo = tls.getSafeStringFromJson(object, "from");
		String pageSize = tls.getSafeStringFromJson(object, "pageSize");

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();  
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rstart_day", startDay.trim()  ) ;				
		p.put("rend_day", endDay.trim()  ) ;	
		
		try {
			fd.call_calcSugExecList(p);
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("proGetSugExecList rpet_id:"+ pet_id +",rstart_day:" + startDay.trim()  + 
					", rend_day:" + endDay.trim());
			throw new SystemException("proGetOneSugExec call_calcSugExec error");			
		}
		
		
		result = Constant.FAIL_CODE;		
		if( tls.isNullOrEmpty(startDay) || tls.isNullOrEmpty(endDay)){
			result = Constant.ERR_INVALID_CALL;
			return;
		}
		
		if( tls.isNullOrEmpty(frmo)){
			frmo = "0";
		}
		if( tls.isNullOrEmpty(pageSize)){
			pageSize = "2000";
		}
		

		/*wmv.setCondition(" a.pet_id = "+pet_id+
				" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') >='"+startDay+"' and DATE_FORMAT(a.up_time,'%Y-%m-%d') <= '" +
				endDay + "'"	);
		*/
		wmv.setCondition(" a.pet_id = "+pet_id+
				" AND a.up_time >='" + p.get("rstart_day").toString().trim() + 
				"' and a.up_time <= '" + p.get("rend_day").toString().trim() +
				"'"	);


		List<DataMap> list = wpmFacade.getOneSugExec(wmv);
		json.put("tot_count", list.size());		
		
		wmv.setCondition(" a.pet_id = "+pet_id+
				" AND a.up_time >='" + p.get("rstart_day").toString().trim() + 
				"' and a.up_time <= '" + p.get("rend_day").toString().trim() +
				"' order by up_time limit " + frmo +"," + pageSize );
		
		list.clear();
		list = wpmFacade.getOneSugExec(wmv);
		if(list != null /*&& list.size() > 0*/ ){
			json.put("rec_count", list.size());

			JSONArray jsonArroot = new JSONArray();

			JSONObject itemObjRoot = new JSONObject();

			for(int i=0;i<list.size();i++){
				itemObjRoot.clear();
				DataMap dMap = list.get(i);
				String step_move = dMap.getAt("step_move").toString();
				
				itemObjRoot.put("step_move", step_move);

				String day = tls.getUtilDayFromSqlDatetime(dMap.getAt("up_time") );
				itemObjRoot.put("day", day);

				
				
				
				/************one day's data list******************/
				/*
				wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(b.up_time,'%Y-%m-%d') = '"+day+"'");			
				List<DataMap> list1 = wpmFacade.getOneSugExecDataList(wmv);
				
				if(list1 != null && list1.size() > 0){
					JSONArray jsonArr = new JSONArray();
					
					for(int i1=0;i1<list1.size();i1++){
						DataMap dataMap = list1.get(i1);
						JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
						jsonArr.add(itemObj);					
					}				
					itemObjRoot.put("data_list", jsonArr);
					list1.clear();
					
				}
				*/			
				/******************************/
				
				jsonArroot.add( itemObjRoot );
				
				
			}

			json.put("mlist", jsonArroot);
			
			result = Constant.SUCCESS_CODE;
			
		}			
		
		
		
		
	}

	void proGetMoveList(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();		
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		String beginTime = object.optString("beginTime");
		String endTime = object.optString("endTime");
		String frmo = tls.getSafeStringFromJson(object, "from");
		String pageSize = tls.getSafeStringFromJson(object, "pageSize");
		
		if( tls.isNullOrEmpty(frmo)){
			frmo = "0";
		}
		if( tls.isNullOrEmpty(pageSize)){
			pageSize = "2000";
		}
		
		
		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(beginTime) || tls.isNullOrEmpty(endTime)){
			return;
		}
		
		wmv.setCondition(" pets_pet_id = "+pet_id+" AND start_time > '"+ 
				beginTime + "' and start_time < '" + endTime + "'");			
		List<DataMap> list = wpmFacade.getMoveListDataList(wmv);
		if(list != null && list.size() == 0){
			result = Constant.ERR_RES_NOT_EXIST;
			return;
		}			
		json.put("tot_count", String.valueOf(list.size()) );
		wmv.setCondition(" pets_pet_id = "+pet_id+" AND start_time > '"+ 
				beginTime + "' and start_time < '" + endTime + 
				"' limit " + frmo + ","+ pageSize );
		list.clear();
		list = wpmFacade.getMoveListDataList(wmv);
		json.put("rec_count", String.valueOf(list.size()) );
		if (list.size() == 0)
			return;

		JSONArray jsonArr = new JSONArray();
		
		for(int i=0;i<list.size();i++){
			DataMap dataMap = list.get(i);
			JSONObject itemObj = JSONObject.fromObject(toJsonFromMoveDB(dataMap));			
			jsonArr.add(itemObj);					
		}			
		
		json.put("data_list", jsonArr);
		
		wmv.setCondition(" pets_pet_id = "+pet_id+" AND start_time > '"+ 
				beginTime + "' and start_time < '" + endTime + 
				"' limit " + frmo + ","+ pageSize );
		list.clear();
		list = wpmFacade.getMoveDataListSum(wmv);
		if(list.size() > 0) {
			json.put("tot_move", list.get(0).getAt("tot_move").toString());
			json.put("tot_energy", list.get(0).getAt("tot_move").toString());
			json.put("tot_route", list.get(0).getAt("tot_move").toString());
			json.put("tot_time", list.get(0).getAt("tot_time").toString());			
		}
		
		
	}

    public String toJsonFromMoveDB(DataMap map) {  

    	StringBuilder str = new StringBuilder();
    	str.append("{");
		String step_number = map.getAt("step_number").toString();
		str.append("\"step_move\":\"" + step_number + "\",");
		String calories = map.getAt("calories").toString();
		str.append("\"calories\":\"" + calories + "\",");
		String speed = map.getAt("speed").toString();
		str.append("\"speed\":\"" + speed + "\",");
		String route = map.getAt("route").toString();
		str.append("\"route\":\"" + route + "\",");
		String start_time = map.getAt("start_time").toString();
		str.append("\"start_time\":\"" + start_time + "\",");
		String end_time = map.getAt("end_time").toString();
		str.append("\"end_time\":\"" + end_time + "\",");
		
		float fspeed = Float.valueOf(speed);
		if ( fspeed > 0.021 && fspeed < 0.041 ) {
			str.append("\"move_type\":\"1\"");
		} else if ( fspeed > 0.041 && fspeed < 0.061 ) {
			str.append("\"move_type\":\"2\"");
		} else if ( fspeed > 0.061 && fspeed < 0.081 ) {
			str.append("\"move_type\":\"3\"");
		} else if ( fspeed > 0.081 ) {
			str.append("\"move_type\":\"4\"");
		}
		str.append("}");
		String res = str.toString();
        return res;  
    }     
	
	
	void proGetSugExecListByMon(JSONObject object) throws SystemException {
		Tools tls = new Tools();		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
		WpetMoveInfo vo =  new WpetMoveInfo();
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		
		int pet_id = object.getInt("pet_id");
		String startMon = tls.getSafeStringFromJson(object, "startMon");
		String endMon = tls.getSafeStringFromJson(object, "endMon");
		String frmo = tls.getSafeStringFromJson(object, "from");
		String pageSize = tls.getSafeStringFromJson(object, "pageSize");

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();  
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rstart_mon", startMon.trim()  ) ;				
		p.put("rend_mon", endMon.trim()) ;				
		fd.call_calcSugExecMon(p);
		
		result = Constant.FAIL_CODE;		
		if( tls.isNullOrEmpty(startMon) || tls.isNullOrEmpty(endMon)){
			result = Constant.ERR_INVALID_CALL;
			return;
		}
		
		if( tls.isNullOrEmpty(frmo)){
			frmo = "0";
		}
		if( tls.isNullOrEmpty(pageSize)){
			pageSize = "2000";
		}
		

		/*wmv.setCondition(" a.pet_id = "+pet_id+
				" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') >='"+startDay+"' and DATE_FORMAT(a.up_time,'%Y-%m-%d') <= '" +
				endDay + "'"	);
		*/
		vo.setCondition(" pet_id = "+pet_id+
				" AND monStr >='" + p.get("rstart_mon").toString().trim() + 
				"-01' and monStr <= '" + p.get("rend_mon").toString().trim() +
				"-01'"	);


		List<DataMap> list = fd.getOneSugExecByMon(vo);
		json.put("tot_count", list.size());		
		
		vo.setCondition(" pet_id = "+pet_id+
				" AND monStr >='" + p.get("rstart_mon").toString().trim() + 
				"-01' and monStr <= '" + p.get("rend_mon").toString().trim() +
				"-01' order by monStr" + 
				" limit " + frmo +"," + pageSize );
		
		list.clear();
		list = fd.getOneSugExecByMon(vo);
		if(list != null /*&& list.size() > 0*/ ){
			json.put("rec_count", list.size());

			JSONArray jsonArroot = new JSONArray();

			JSONObject itemObjRoot = new JSONObject();

			for(int i=0;i<list.size();i++){
				itemObjRoot.clear();
				DataMap dMap = list.get(i);
				String step_move = dMap.getAt("step_move").toString();
				
				itemObjRoot.put("step_move", step_move);

				String day = dMap.getAt("mon").toString() ;
				itemObjRoot.put("mon", day);

				
				
				jsonArroot.add( itemObjRoot );
				
				
			}

			json.put("mlist", jsonArroot);
			
			result = Constant.SUCCESS_CODE;
			
		}			
		
		
		
		
	}

	
	boolean proGHealthStepFast(JSONObject object, HttpServletResponse response) {
		try {
			Tools tls = new Tools();		
			WTSigninAction ba = new WTSigninAction();
			
			Integer user_id  = object.optInt("user_id");	
			String device_imei = getDeviceImeiFromDeviceId(String.valueOf(myDeviceId));
			Integer pet_id = object.optInt("pet_id");

			if ( myDeviceId == null ) {
				super.logAction(String.valueOf(user_id), -101, "proGHealthStepFast myDeviceId is null");
				return false;
			}
			
			//String day = BaseAction.getDeviceNow(myDeviceId).substring(0, 10);
			
			String day = null;
			if ( Constant.timeUtcFlag )
				day = tls.getUtcDateStrNow().substring(0, 10);			
			else 																					
				day = ba.getDeviceNow(myDeviceId).substring(0, 10);
			
			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();			
			cmd.setCmdName(AdragonConfig.getHealthStepRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setPet_id(pet_id);
			cmd.setDay(day);					

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			if ( !cmdDownSetImpl.getHealthStep(device_imei, true, user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());	
				
				logger.info("proGHealthStepFast error!");

				ba.insertVisit(null, device_imei, null, "proGHealthStepFast:" + json.toString()   );											
				
			}
			
			
			return true; 
			
		} catch (Exception e) {
			return false;
		}
	}
	
	boolean proGHealthStep(JSONObject object)  {
		try  {
			Integer user_id  = object.optInt("user_id");
			//Integer updateFlag = object.optInt("flag");
			String device_imei = getDeviceImeiFromDeviceId(String.valueOf(myDeviceId));
			//WTSigninAction  sa = new WTSigninAction();
			//sa.heartBeat(String.valueOf(user_id));	
			//sa = null;
			// end
			
			if ( myDeviceId == null ) {
				super.logAction(String.valueOf(user_id), -101, "proGHealthStep myDeviceId is null");
				return false;
			}
			
		
			if ( devGHealth(user_id, device_imei ) )
				return true;
			else
				return false;
	    	

		} catch(Exception e) {		
			return false;

		}
	    
		
		
	}
	
	boolean devGHealth(Integer user_id, String  device_imei) {
		try {
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();

			cmdDownSetImpl.getHealthStep(device_imei, true, user_id, null);

			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	

	public void proGetOneSugExecSe(JSONObject object) throws SystemException {
		int user_id = object.optInt("user_id");
		Tools tls = new Tools();		


		if ( myDeviceId == null ) {
			myDeviceId = "577"; 
		}

		String device_id = myDeviceId;	//BaseAction.getDeviceIdFromPetId(object.optString("pet_id"));

		
		if ( myDeviceId == null ) {
			super.logAction(String.valueOf(user_id), -101, "proGetOneSugExecSe myDeviceId is null");
			return ;
		}

		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();

		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		String tz = null;	//object.getString("tz");
		String retime_i = null;	//object.getString("et").trim();

		if (Constant.UNI_HEALTH_COMPUTE_SAME_FLAG) {		
			tz = getDeviceTimeZone(device_id);
//			String retime_is = LocationInfoHelper.getDevCurrentDay(Integer.parseInt(device_id));
			//tz = object.getString("tz");
			retime_i = object.getString("et").trim();			
		} else {
			tz = object.getString("tz");
			retime_i = object.getString("et").trim();			
		}
		
		
		//2017-03-14 23:00:00
		if ( retime_i.length() != 19 )
			return;		
		
		String retimeDev = tls.timeConvert(retime_i, "UTC", tz);
		String deveDay = retimeDev.substring(0, 10);
		String rstimeDev = deveDay + " 00:00:00";
		String rstime = tls.timeConvert(rstimeDev, tz, "UTC");
		String retime = tls.timeConvert(deveDay + " 23:59:59", tz, "UTC");

		//String retime = retime_i;

		String devDay = rstime.substring(0, 10);

		
		
		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();
		super.logAction(String.valueOf(user_id), Integer.parseInt(device_id), "rpet_id:" 
				+ pet_id + "retime_i:" + retime_i + ",etime:" + retime + ",time zone:" + tz +
				",devDay:" + devDay );			
		
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rstime", rstime);
		p.put("retime", retime);
		try {
			fd.call_calcSugExecSe(p);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("call_calcSugExecSe rpet_id:"+ pet_id + ",stime:" + rstime + ",etime:" + retime );
			throw new SystemException("call_calcSugExecSe error");
		}

		
		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(tz) || tls.isNullOrEmpty(devDay)){
			return;
		}else{
			
			wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+devDay+"'");
			
			List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);
			
			if(list != null /*&& list.size() > 0*/){

				if ( json == null )
					json = new JSONObject();
				
				
				if (list.size() == 0) {
					result = Constant.ERR_RES_NOT_EXIST;
					return;
				}
				
				json.put("sug_type", "");
				json.put("sug_time", "");

				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String sug_type = dMap.getAt("recMoveType").toString();
					String sug_time = dMap.getAt("recMoveTime").toString();
					
					json.put("sug_type", sug_type);
					json.put("sug_time", sug_time);
					
				}
				
			}			
			
			
			wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+devDay+"'");			
			list.clear();
			list = wpmFacade.getOneSugExec(wmv);
			if(list != null/* && list.size() > 0*/){
				if (list.size() == 0 ) {
					result = Constant.ERR_RES_NOT_EXIST;
					return;
				}

				json.put("per_move", "");
				json.put("route", "");
				json.put("step_move", "");
				json.put("calories", "");
				json.put("exec_time", "");

				json.put("dexec_time", "");
				json.put("lexec_time", "");
				json.put("tot_sleep", "");
				json.put("tot_dsleep", "");
				json.put("tot_lsleep", "");
				json.put("max_unit_steps", "");				
				
				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String per_move = dMap.getAt("per_move").toString();
					String route = dMap.getAt("route").toString();
					String step_move = dMap.getAt("step_move").toString();
					String calories = dMap.getAt("calories").toString();
					String exec_time = dMap.getAt("exec_time").toString();
					
					String dexec_time = dMap.getAt("dexec_time").toString();
					String lexec_time = dMap.getAt("lexec_time").toString();
					String tot_sleep = dMap.getAt("tot_sleep").toString();
					String tot_dsleep = dMap.getAt("tot_dsleep").toString();
					String tot_lsleep = dMap.getAt("tot_lsleep").toString();
					String max_unit_steps = dMap.getAt("max_unit_steps").toString();
					
					
					json.put("per_move", per_move);
					json.put("route", route);
					json.put("step_move", step_move);
					json.put("calories", calories);
					json.put("exec_time", exec_time);

					json.put("dexec_time", dexec_time);
					json.put("lexec_time", lexec_time);
					json.put("tot_sleep", tot_sleep);
					json.put("tot_dsleep", tot_dsleep);
					json.put("tot_lsleep", tot_lsleep);
					json.put("max_unit_steps", max_unit_steps);				
					
				}
				
			}			
			
			//wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(b.up_time,'%Y-%m-%d') = '"+day+"'");			
			wmv.setCondition(" pet_id = "+pet_id+" AND start_time >= '" + rstime +  "' and end_time <='" + retime + "'");			

			List<DataMap> list1 = wpmFacade.getOneSugExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();

				if ( list1.size() > 50 ) {
					result = Constant.FAIL_CODE;
					return;
				}
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				

				
				
				json.put("mlist", jsonArr);
				
			}
				

			list1.clear();
			list1 = wpmFacade.getOneSleepExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				json.put("slist", jsonArr);
				
			}
			
			
		}		

		result = Constant.SUCCESS_CODE;
		
		
	}
	

	boolean proGHealthStepFastSe(JSONObject object, HttpServletResponse response) {
		Tools tls = new Tools();	
		
		try {
			Integer user_id  = object.optInt("user_id");
			if ( myDeviceId == null )
				myDeviceId ="577";
			String device_imei = getDeviceImeiFromDeviceId(String.valueOf(myDeviceId));
			
			if ( myDeviceId == null ) {
				super.logAction(String.valueOf(user_id), -101, "proGHealthStepFastSe myDeviceId is null");
				return false;
			}
			
			
			Integer pet_id = object.optInt("pet_id");
			String tz = null;	//object.getString("tz");

			//String day = BaseAction.getDeviceNow(myDeviceId).substring(0, 10);
			
			String retime_i = tls.getUtcDateStrNow();

			String retimeDev = null;	//Tools.timeConvert(retime_i, "UTC", tz);

			if (Constant.UNI_HEALTH_COMPUTE_SAME_FLAG) {		
				tz = getDeviceTimeZone(myDeviceId);
				retimeDev = tls.timeConvert(retime_i, "UTC", tz);		
			} else {
				tz = object.getString("tz");
				retimeDev = tls.timeConvert(retime_i, "UTC", tz);		
			}

			super.logAction(String.valueOf(user_id), Integer.parseInt(myDeviceId), "WTSugMoveManAction + retimeDev: " + retimeDev + ", pet_id:" + pet_id);			
			if ( tls.isNullOrEmpty(tz) ) {
				super.logAction(String.valueOf(user_id), Integer.parseInt(myDeviceId), "WTSugMoveManAction error+ retimeDev: " + retimeDev + ", pet_id:" + pet_id);							
			}
			
			
			String devDay = retimeDev.substring(0, 10);
			String rstimeDev = devDay + " 00:00:00";
			String rstime = tls.timeConvert(rstimeDev, tz, "UTC");
			String retime = tls.timeConvert(devDay + " 23:59:59", tz, "UTC");
			devDay = rstime.substring(0, 10);
			
			
			Thread lock = new Thread(); 
			CmdSync cmd = new CmdSync();			
			cmd.setCmdName(AdragonConfig.getHealthStepRes);
			cmd.setResponse(response);
			cmd.setTdLock(lock);
			cmd.setUser_id(user_id);
			cmd.setPet_id(pet_id);
			cmd.setRstime(rstime);
			cmd.setRetime(retime);
			cmd.setDevDay(devDay);

			CmdDownSetImpl.getClientSessionMangagerInstance().setHttpCmdId(device_imei, cmd);
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
			
			if ( !cmdDownSetImpl.getHealthStep(device_imei, true, user_id, lock) ) {
				json.put(Constant.RESULTCODE, Constant.FAIL_CODE);
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());	
				
				logger.info("proGHealthStepFast error!");

				insertVisit(null, device_imei, null, "proGHealthStepFast:" + json.toString()   );											
				
			}
			
			
			return true; 
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public JSONObject extGetOneSugExecSe(JSONObject object) throws SystemException {
		JSONObject jsonRes = new JSONObject();
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
		Tools tls = new Tools();
		
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		int pet_id = object.getInt("pet_id");
		String rstime = object.optString("rstime");
		String retime = object.optString("retime");
		String devDay = object.optString("devDay");
		

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();  
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rstime", rstime);
		p.put("retime", retime);
		fd.call_calcSugExecSe(p);
		
		result = Constant.FAIL_CODE;		
		
		if( tls.isNullOrEmpty(rstime) || tls.isNullOrEmpty(retime) ){
			return null;
		}else{
			
			wmv.setCondition(" pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+devDay+"'");
			
			List<DataMap> list = wpmFacade.getDaySugMoveManInfo(wmv);
			
			if(list != null /*&& list.size() > 0*/){
				
				if (list.size() == 0) {
					result = Constant.ERR_RES_NOT_EXIST;
					return null;
				}
				
				jsonRes.put("sug_type", "");
				jsonRes.put("sug_time", "");

				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String sug_type = dMap.getAt("recMoveType").toString();
					String sug_time = dMap.getAt("recMoveTime").toString();
					
					jsonRes.put("sug_type", sug_type);
					jsonRes.put("sug_time", sug_time);
					
				}
				
			}			
			
			
			wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') = '"+devDay+"'");			
			list.clear();
			list = wpmFacade.getOneSugExec(wmv);
			if(list != null/* && list.size() > 0*/){
				if (list.size() == 0 ) {
					result = Constant.ERR_RES_NOT_EXIST;
					return null;
				}

				jsonRes.put("per_move", "");
				jsonRes.put("route", "");
				jsonRes.put("step_move", "");
				jsonRes.put("calories", "");
				jsonRes.put("exec_time", "");

				jsonRes.put("dexec_time", "");
				jsonRes.put("lexec_time", "");
				jsonRes.put("tot_sleep", "");
				jsonRes.put("tot_dsleep", "");
				jsonRes.put("tot_lsleep", "");
				jsonRes.put("max_unit_steps", "");				
				
				for(int i=0;i<list.size() && i < 1;i++){
					DataMap dMap = list.get(i);
					String per_move = dMap.getAt("per_move").toString();
					String route = dMap.getAt("route").toString();
					String step_move = dMap.getAt("step_move").toString();
					String calories = dMap.getAt("calories").toString();
					String exec_time = dMap.getAt("exec_time").toString();
					
					String dexec_time = dMap.getAt("dexec_time").toString();
					String lexec_time = dMap.getAt("lexec_time").toString();
					String tot_sleep = dMap.getAt("tot_sleep").toString();
					String tot_dsleep = dMap.getAt("tot_dsleep").toString();
					String tot_lsleep = dMap.getAt("tot_lsleep").toString();
					String max_unit_steps = dMap.getAt("max_unit_steps").toString();
					
					
					jsonRes.put("per_move", per_move);
					jsonRes.put("route", route);
					jsonRes.put("step_move", step_move);
					jsonRes.put("calories", calories);
					jsonRes.put("exec_time", exec_time);

					jsonRes.put("dexec_time", dexec_time);
					jsonRes.put("lexec_time", lexec_time);
					jsonRes.put("tot_sleep", tot_sleep);
					jsonRes.put("tot_dsleep", tot_dsleep);
					jsonRes.put("tot_lsleep", tot_lsleep);
					jsonRes.put("max_unit_steps", max_unit_steps);				
					
				}
				
			}			
			
			//wmv.setCondition(" a.pet_id = "+pet_id+" AND DATE_FORMAT(b.up_time,'%Y-%m-%d') = '"+day+"'");			
			wmv.setCondition(" pet_id = "+pet_id+" AND start_time >= '" + rstime +  "' and end_time <='" + retime + "'");			

			List<DataMap> list1 = wpmFacade.getOneSugExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				jsonRes.put("mlist", jsonArr);
				
			}
				

			list1.clear();
			list1 = wpmFacade.getOneSleepExecDataList(wmv);
			
			if(list1 != null && list1.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list1.size();i++){
					DataMap dataMap = list1.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				jsonRes.put("slist", jsonArr);
				
			}
			
			
		}		

		return jsonRes;
		
		
	}
	
	public void proGetSugExecListSe(JSONObject object){
		try {
			Tools tls = new Tools();
			
			int user_id = object.optInt("user_id");
			String device_id = myDeviceId;	//BaseAction.getDeviceIdFromPetId(object.optString("pet_id"));
			
			if ( myDeviceId == null ) {
				super.logAction(String.valueOf(user_id), -101, "proGetSugExecListSe myDeviceId is null");
				return ;
			}

			
			int pet_id = object.getInt("pet_id");
			//String day = object.getString("day");
			String tzd = object.getString("tz");
			String tz = null;
			String retime = null;	//object.getString("et").trim();

			if (Constant.UNI_HEALTH_COMPUTE_SAME_FLAG) {		
				tz = getDeviceTimeZone(device_id);
				retime = object.getString("et").trim();			
			} else {
				tz = tzd;
				retime = object.getString("et").trim();			
			}

			
			//2017-03-14 23:00:00
			if ( retime.length() != 19 )
				return;		

			
			
			String retimeDev = tls.timeConvert(retime, "UTC", tz);
			String deveDay = retimeDev.substring(0, 10);
			String deveDayEnd = deveDay + " 23:59:59";
			String utcetime = tls.timeConvert(deveDayEnd, tz, "UTC");

			String rstimeDev = deveDay + " 00:00:00";
			String rstime = tls.timeConvert(rstimeDev, tz, "UTC");
			String devDay = rstime.substring(0, 10);
			String devDayFull = devDay + " 00:00:00";

			String devSDay = tls.getDateStringAddDay(rstime, -6);
			String devSDayFull = devSDay.substring(0, 10) + " 00:00:00";

			
			super.logAction(String.valueOf(user_id), Integer.parseInt(device_id), "proGetSugExecListSe rpet_id:" 
					+ pet_id + "devDay:" + devDay +  ", retime:" + retime +
					",utcetime:" + utcetime + ",time zone:" + tz +
					",devDay:" + devDay );			


			
			WPetMoveInfo wmv = new WPetMoveInfo();
			WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
			com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
			
			//int pet_id = object.getInt("pet_id");
			String startDay = tls.getSafeStringFromJson(object, "startDay");
			String endDay = tls.getSafeStringFromJson(object, "endDay");
			String frmo = tls.getSafeStringFromJson(object, "from");
			String pageSize = tls.getSafeStringFromJson(object, "pageSize");
	
			//首先后台需要检查并生成报告数据
			Map<String, Object> p = new HashMap<String, Object>();  
			p.put("rpet_id", String.valueOf(pet_id) ) ;
			p.put("rend_day", utcetime  ) ;	
		
			try {
				fd.call_calcSugExecListSe(p);
			} catch(Exception e) {
				e.printStackTrace();
				logger.info("proGetSugExecListSe rpet_id:"+ pet_id + 
						", rend_day:" + utcetime );
				throw new SystemException("call_calcSugExecListSe error");			
			}
		
		
			result = Constant.FAIL_CODE;		
			
			if( tls.isNullOrEmpty(frmo)){
				frmo = "0";
			}
			if( tls.isNullOrEmpty(pageSize)){
				pageSize = "2000";
			}
		

			/*wmv.setCondition(" a.pet_id = "+pet_id+
					" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') >='"+startDay+"' and DATE_FORMAT(a.up_time,'%Y-%m-%d') <= '" +
					endDay + "'"	);
			*/
			wmv.setCondition(" a.pet_id = "+pet_id+
					" AND a.up_time >='" + devSDayFull + 
					"' and a.up_time <= '" + devDayFull +
					"' order by a.up_time"	);


			List<DataMap> list = wpmFacade.getOneSugExec(wmv);
			if ( json == null )
				json = new JSONObject();
			
			json.put("tot_count", list.size());		
			
			/*
			wmv.setCondition(" a.pet_id = "+pet_id+
					" AND a.up_time >='" + devSDay + 
					"' and a.up_time <= '" + devDay +
					"' order by up_time limit " + frmo +"," + pageSize );
			
			list.clear();
			list = wpmFacade.getOneSugExec(wmv);
			 */
			
			if(list != null /*&& list.size() > 0*/ ){
				json.put("rec_count", list.size());
	
				JSONArray jsonArroot = new JSONArray();
	
				JSONObject itemObjRoot = new JSONObject();

				for(int i=0;i<list.size();i++){
					itemObjRoot.clear();
					DataMap dMap = list.get(i);
					String step_move = dMap.getAt("step_move").toString();
					
					itemObjRoot.put("step_move", step_move);
	
					String day = tls.getUtilDayFromSqlDatetime(dMap.getAt("up_time") );
					itemObjRoot.put("day", day);
	
					
					
					
					
					jsonArroot.add( itemObjRoot );
					
					
				}

				json.put("mlist", jsonArroot);
				System.out.println(json.toString() );
			
			
				result = Constant.SUCCESS_CODE;
			} 
		} catch(Exception e) {
				e.printStackTrace(); 
		}		
		
		
	}

	
	public void proGetSugExecListByMonSe(JSONObject object) throws SystemException {
		int user_id = object.optInt("user_id");
		Tools tls = new Tools();		
		
		WPetMoveInfo wmv = new WPetMoveInfo();
		WPetMoveInfoFacade wpmFacade = ServiceBean.getInstance().getwPetMoveInfoFacade();
		WpetMoveInfo vo =  new WpetMoveInfo();
		com.wtwd.sys.innerw.wpetMoveInfo.domain.logic.WpetMoveInfoFacade fd = ServiceBean.getInstance().getWpetMoveInfoFacade();
		
		//String startMon = Tools.getSafeStringFromJson(object, "startMon");
		//String endMon = Tools.getSafeStringFromJson(object, "endMon");

		int pet_id = object.getInt("pet_id");
		String device_id = myDeviceId;	//BaseAction.getDeviceIdFromPetId(String.valueOf(pet_id));

		if ( myDeviceId == null ) {
			super.logAction(String.valueOf(user_id), -101, "proGetSugExecListByMonSe myDeviceId is null");
			return ;
		}

		
		String tz = null;	//object.getString("tz");
		String retime_i = null;		//object.getString("et").trim();

		if (Constant.UNI_HEALTH_COMPUTE_SAME_FLAG) {		
			tz = getDeviceTimeZone(device_id);
			retime_i = object.getString("et").trim();			
		} else {
			tz = object.getString("tz");
			retime_i = object.getString("et").trim();			
		}
		
		String retimeDev = tls.timeConvert(retime_i, "UTC", tz);
		String devDay = retimeDev.substring(0, 7) + "-01 00:00:00";
		String dayNum = devDay.substring(5, 7);
		String devEndDay_t =  tls.getDateStringAddDay(tls.getDateStringAddMon(devDay, 1), -1);
		String devEndDay =  devEndDay_t.substring(0, 10) + " 23:59:59";

		String rstartMonUtc = tls.timeConvert(devDay, tz, "UTC");
		String rendMonUtc = tls.timeConvert(devEndDay, tz, "UTC");
		StringBuffer sbStartMonUtc = new StringBuffer(rstartMonUtc);
		StringBuffer sbEndMonUtc = new StringBuffer(rendMonUtc);

		
		super.logAction(String.valueOf(user_id), 80, "proGetSugExecListByMonSe rpet_id:" 
				+ pet_id + "retime_i :" + retime_i + ",time zone:" + tz +
				",devDay:" + devDay );			
		
		
		StringBuffer sbStartMonUtcCon = new StringBuffer("('");
		sbStartMonUtcCon.append(rstartMonUtc);
		sbStartMonUtcCon.append("'");				
		
		String _temp = null;
		while ( !"01".equals(dayNum) ) {
			sbStartMonUtc.append(",");
			sbEndMonUtc.append(",");
			sbStartMonUtcCon.append(",'");

			_temp = devDay;
			devDay = tls.getDateStringAddMon(devDay, -1); 
			dayNum = devDay.substring(5, 7);
			devEndDay_t =  tls.getDateStringAddDay(_temp, -1);
			devEndDay =  devEndDay_t.substring(0, 10) + " 23:59:59";

			rstartMonUtc = tls.timeConvert(devDay, tz, "UTC");
			rendMonUtc = tls.timeConvert(devEndDay, tz, "UTC");
			
			sbStartMonUtc.append(rstartMonUtc);
			sbEndMonUtc.append(rendMonUtc);
			sbStartMonUtcCon.append(rstartMonUtc);			
			sbStartMonUtcCon.append("'");
			
		}

		sbStartMonUtcCon.append(")");
		
		
		System.out.println("proGetSugExecListByMonSe startp:" + sbStartMonUtcCon.toString());
		//System.out.println("proGetSugExecListByMonSe startp:" + sbEndMonUtc.toString());
		String startMon = sbStartMonUtc.toString();
		String endMon = sbEndMonUtc.toString();				
		
		String frmo = tls.getSafeStringFromJson(object, "from");
		String pageSize = tls.getSafeStringFromJson(object, "pageSize");

		//首先后台需要检查并生成报告数据
		Map<String, Object> p = new HashMap<String, Object>();  
		p.put("rpet_id", String.valueOf(pet_id) ) ;
		p.put("rstart_mon", startMon  ) ;				
		p.put("rend_mon", endMon) ;				
		fd.call_calcSugExecMonSe(p);
		
		result = Constant.FAIL_CODE;		
		if( tls.isNullOrEmpty(startMon) || tls.isNullOrEmpty(endMon)){
			result = Constant.ERR_INVALID_CALL;
			return;
		}
		
		if( tls.isNullOrEmpty(frmo)){
			frmo = "0";
		}
		if( tls.isNullOrEmpty(pageSize)){
			pageSize = "2000";
		}
		

		/*wmv.setCondition(" a.pet_id = "+pet_id+
				" AND DATE_FORMAT(a.up_time,'%Y-%m-%d') >='"+startDay+"' and DATE_FORMAT(a.up_time,'%Y-%m-%d') <= '" +
				endDay + "'"	);
		*/
		vo.setCondition(" pet_id = "+pet_id+
				" AND monStr in " + sbStartMonUtcCon.toString() + " order by monStr" ); 

		if ( json == null )
			json = new JSONObject();		
		
		List<DataMap> list = fd.getOneSugExecByMon(vo);
		json.put("tot_count", list.size());		
		
		
		if(list != null /*&& list.size() > 0*/ ){
			

			
			json.put("rec_count", list.size());

			JSONArray jsonArroot = new JSONArray();

			JSONObject itemObjRoot = new JSONObject();

			for(int i=0;i<list.size();i++){
				itemObjRoot.clear();
				DataMap dMap = list.get(i);
				String step_move = dMap.getAt("step_move").toString();
				
				itemObjRoot.put("step_move", step_move);

				String day = dMap.getAt("mon").toString() ;
				itemObjRoot.put("mon", day);

				
				
				jsonArroot.add( itemObjRoot );
				
				
			}

			json.put("mlist", jsonArroot);
			
			result = Constant.SUCCESS_CODE;
			
		}			
		
		
		
		
	}
	
	
	
	
}
