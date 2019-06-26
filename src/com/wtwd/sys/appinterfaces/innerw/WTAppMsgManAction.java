package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

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
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WTAppMsgManFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;


public class WTAppMsgManAction extends BaseAction {
	
	private JSONObject json = null;
	private boolean old_flag= false;
	Log logger = LogFactory.getLog(WTSigninAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();	
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			logger.info("WTAppMsgManAction params:" + sb.toString());		
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
			
			//super.logAction(String.valueOf(user_id), "WTAppMsgManAction");
		
			if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
					== Constant.SUCCESS_CODE ) {
				if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
					if (cmd.equals("get")) {	//APP消息获取
						old_flag = false;
						proGet(object);
						//result = Constant.SUCCESS_CODE;
					} else if ( cmd.equals("hget")) {
						old_flag = true;
						proGet(object);
					} else if ( cmd.equals("pget")) {
						proPGet(object);
					} else if ( cmd.equals("clrLst")) {
						proClrLst(object);
					} else if ( cmd.equals("clr")) {
						proClrAll(object);
					}else if ( cmd.equals("appHeartBeat")) {
						proAppHB(object);
					} else {
						result = Constant.ERR_INVALID_PARA;								
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
			
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());

		//System.out.println("app log in return json: ---"+json.toString());
	
		return null;
	}
	
	void proGet(JSONObject object) {
		Tools tls = new Tools();
		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		
		String time_stamp = tls.getSafeStringFromJson(object,"time_stamp");
		String from = tls.getSafeStringFromJson(object,"from");
		String pageSize = tls.getSafeStringFromJson(object,"pageSize");
		int user_id = object.getInt("user_id");
		
		//调用此协议认为将同时产生心跳信号
		/*
		try { 
		WTSigninAction  sa = new WTSigninAction();
		sa.heartBeat(String.valueOf(user_id) );	
		sa = null;
		// end
		} catch(Exception e) {
			
		}*/
				
		WMsgInfo wm = new WMsgInfo();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_count = new StringBuffer();

		if  ( old_flag == true ) {
			sb.append(" a.to_usrid = "+user_id + " and a.hide_flag != 1");
			sb_count.append(" to_usrid = "+user_id + " and hide_flag != 1");
		} else {
			sb.append(" a.to_usrid = "+user_id );
			sb_count.append(" to_usrid = "+user_id );			
		}

		if  ( old_flag == true ) {
			sb.append( " and a.status = 1");		//查询历史消息
			sb_count.append( " and status = 1");		//查询历史消息
		}
		else {
			sb.append(" and a.status = 0");
			sb_count.append( " and status = 0");		//查询历史消息
		}
		
		if(time_stamp != null && !"".equals(time_stamp)){
			sb.append(" AND DATE_FORMAT(a.msg_date,'%Y-%m-%d %H:%i:%s') > '"+time_stamp+"'");
			sb_count.append(" AND DATE_FORMAT(msg_date,'%Y-%m-%d %H:%i:%s') > '"+time_stamp+"'");
		}
		if(!tls.isNullOrEmpty(from)){
			if(!tls.isNullOrEmpty(pageSize)){
				//sb.append(" ORDER BY a.order_id desc, a.msg_date DESC LIMIT "+from+","+pageSize);
				sb.append(" ORDER BY a.msg_date DESC LIMIT "+from+","+pageSize);
				//sb_count.append(" LIMIT "+from+","+pageSize);
			}else{
				//sb.append(" ORDER BY a.order_id desc, a.msg_date DESC LIMIT "+from+",500 ");
				sb.append(" ORDER BY a.msg_date DESC LIMIT "+from+",500 ");				
				//sb_count.append(" LIMIT "+from+",500 ");
			}
		}else{
			if(!tls.isNullOrEmpty(pageSize)){
				//sb.append(" ORDER BY a.order_id desc, a.msg_date DESC LIMIT 0,"+pageSize);
				sb.append(" ORDER BY a.msg_date DESC LIMIT 0,"+pageSize);
				//sb_count.append(" LIMIT 0,"+pageSize);
			}else{
				//sb.append(" ORDER BY a.order_id desc, a.msg_date DESC LIMIT 0,500 ");
				sb.append(" ORDER BY a.msg_date DESC LIMIT 0,500 ");
				//sb_count.append(" LIMIT 0,500 ");
			}
		}
		
		wm.setCondition(sb_count.toString());
		int listCount = infoFacade.queryByUserIdMsgCount(wm);
		
		logger.info("inter WTAppMsgManAction proGet() prarm:" + sb.toString());
		wm.setCondition(sb.toString());
		List<DataMap> list = infoFacade.queryByUserIdMsgInfo(wm);
		if(list != null /*&& list.size() > 0*/){
			JSONArray jsonArr = new JSONArray();
			StringBuffer sbUp = new StringBuffer();
			sbUp.append("msg_id in (-1");

			for(int i=0;i<list.size();i++){
				DataMap msgMap = list.get(i);
				JSONObject jsonMsg = new JSONObject();
				JSONObject jsonMsgContent = new JSONObject();
				String msg_id = msgMap.getAt("msg_id").toString();
				sbUp.append("," + msg_id.trim());
				
				String msg_type = msgMap.getAt("msg_type").toString();
				String msg_ind_id = msgMap.getAt("msg_ind_id").toString();
				String msg_date = msgMap.getAt("msg_date").toString();
				String push_status = msgMap.getAt("push_status").toString();

				
				//String msg_content = msgMap.getAt("msg_content").toString();
				String msg_content = msgMap.getAt("summary").toString();

				String device_id = msgMap.getAt("device_id").toString();
				String from_usrid = msgMap.getAt("from_usrid").toString();
				String to_usrid = msgMap.getAt("to_usrid").toString();
				String fence_id = msgMap.getAt("fence_id").toString();
				String share_id = msgMap.getAt("share_id").toString();
				String from_email = msgMap.getAt("from_email").toString().toString();
				String from_nick = msgMap.getAt("from_nick").toString().toString();
				String to_email = msgMap.getAt("to_email").toString().toString();
				String to_nick = msgMap.getAt("to_nick").toString().toString();
				
				
				jsonMsg.put("msg_id", msg_id);
				jsonMsg.put("msg_type", msg_type);
				jsonMsg.put("msg_ind_id", msg_ind_id);
				jsonMsg.put("msg_date", msg_date);
				
				jsonMsgContent.put("device_id", device_id);
				jsonMsgContent.put("from_usrid", from_usrid);
				jsonMsgContent.put("to_usrid", to_usrid);
				jsonMsgContent.put("eference_id", fence_id);
				jsonMsgContent.put("msg_txt", msg_content);
				jsonMsg.put("msg_content", jsonMsgContent);
				jsonMsg.put("share_id", share_id );

				jsonMsg.put("from_email", from_email );
				jsonMsg.put("from_nick", from_nick );
				jsonMsg.put("to_email", to_email );
				jsonMsg.put("to_nick", to_nick );
				
				jsonMsg.put("push_status", push_status);
								
				jsonArr.add(jsonMsg);
				if(i == 0){
					json.put("time_stamp", msg_date);
				}
			}

			sbUp.append(")" );
			
			if (!old_flag && list.size() > 0)	//更新消息未读状态
			{
				proUpMsgStatus( sbUp.toString() );
			}
						
			//更新ios的badge
			WappUsers vo =  new WappUsers();
			WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();

			vo.setCondition("user_id = " + user_id );
			vo.setBadge(0);		//初始化IOS app的badge			
			try
			{
				fd.updateWappUsers(vo);						
			}
			catch (Exception e)
			{
				e.printStackTrace();			
			}	
			
			result = Constant.SUCCESS_CODE;
			json.put("tot_count", listCount);
			json.put("rec_count", list.size());
			json.put("data_list", jsonArr);
		}else{
			result = Constant.FAIL_CODE;
		}
		logger.info("WTAppMsgManAction.proGet() json=" + json);
		
	}
	
	public void proPGet(JSONObject object) {		//推送消息获取
		Tools tls = new Tools();
		
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		
		String time_stamp = tls.getSafeStringFromJson(object,"time_stamp");
		String from = tls.getSafeStringFromJson(object,"from");
		int pageSize = object.optInt("pageSize");
		int user_id = object.getInt("user_id");
		
		
		if (pageSize == 0 || pageSize > 100) 
			pageSize = 100;
		if ( json == null )
			json = new JSONObject();
		
		

		//调用此协议认为将同时产生心跳信号
		/*
		try { 
		WTSigninAction  sa = new WTSigninAction();
		sa.heartBeat(String.valueOf(user_id) );	
		sa = null;
		// end
		} catch(Exception e) {
			
		}*/
		
		
		WMsgInfo wm = new WMsgInfo();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_count = new StringBuffer();

		sb.append(" a.to_usrid = "+user_id + " and a.msg_ind_id=" + Constant.CST_MSG_IND_APPLY_SHARE);
		sb_count.append(" to_usrid = "+user_id + " and msg_ind_id=" + + Constant.CST_MSG_IND_APPLY_SHARE);

		sb.append( " and a.push_status = 0");		
		sb_count.append( " and push_status = 0");		
		
		if(time_stamp != null && !"".equals(time_stamp)){
			sb.append(" AND DATE_FORMAT(a.msg_date,'%Y-%m-%d %H:%i:%s') > '"+time_stamp+"'");
			sb_count.append(" AND DATE_FORMAT(msg_date,'%Y-%m-%d %H:%i:%s') > '"+time_stamp+"'");
		}
		if(!tls.isNullOrEmpty(from)){
			sb.append(" ORDER BY a.msg_date DESC LIMIT "+from+","+pageSize);
		}else{
			sb.append(" ORDER BY a.msg_date DESC LIMIT 0,"+pageSize);
		}
		
		wm.setCondition(sb_count.toString());
		int listCount = infoFacade.queryByUserIdMsgCount(wm);
		wm.setCondition(sb.toString());
		List<DataMap> list = infoFacade.queryByUserIdMsgInfo(wm);
		if(list != null /*&& list.size() > 0*/){
			JSONArray jsonArr = new JSONArray();
			StringBuffer sbUp = new StringBuffer();
			sbUp.append("msg_id in (-1");

			for(int i=0;i<list.size();i++){
				DataMap msgMap = list.get(i);
				JSONObject jsonMsg = new JSONObject();
				JSONObject jsonMsgContent = new JSONObject();
				String msg_id = msgMap.getAt("msg_id").toString();
				sbUp.append("," + msg_id.trim());
				
				String msg_type = msgMap.getAt("msg_type").toString();
				String msg_ind_id = msgMap.getAt("msg_ind_id").toString();
				String msg_date = msgMap.getAt("msg_date").toString();
				
				//String msg_content = msgMap.getAt("msg_content").toString();
				String msg_content = msgMap.getAt("summary").toString();

				String device_id = msgMap.getAt("device_id").toString();
				String from_usrid = msgMap.getAt("from_usrid").toString();
				String to_usrid = msgMap.getAt("to_usrid").toString();
				String fence_id = msgMap.getAt("fence_id").toString();
				String share_id = msgMap.getAt("share_id").toString();
				String from_email = msgMap.getAt("from_email").toString().toString();
				String from_nick = msgMap.getAt("from_nick").toString().toString();
				String to_email = msgMap.getAt("to_email").toString().toString();
				String to_nick = msgMap.getAt("to_nick").toString().toString();
				
				
				jsonMsg.put("msg_id", msg_id);
				jsonMsg.put("msg_type", msg_type);
				jsonMsg.put("msg_ind_id", msg_ind_id);
				jsonMsg.put("msg_date", msg_date);
				
				jsonMsgContent.put("device_id", device_id);
				jsonMsgContent.put("from_usrid", from_usrid);
				jsonMsgContent.put("to_usrid", to_usrid);
				jsonMsgContent.put("eference_id", fence_id);
				jsonMsgContent.put("msg_txt", msg_content);
				jsonMsg.put("msg_content", jsonMsgContent);
				jsonMsg.put("share_id", share_id );

				jsonMsg.put("from_email", from_email );
				jsonMsg.put("from_nick", from_nick );
				jsonMsg.put("to_email", to_email );
				jsonMsg.put("to_nick", to_nick );
								
				jsonArr.add(jsonMsg);
				if(i == 0){
					json.put("time_stamp", msg_date);
				}
			}

			sbUp.append(")" );
			
			if (!old_flag && list.size() > 0)	//更新消息推送未读状态
			{
				proUpMsgPushStatus( sbUp.toString() );
			}
			
			result = Constant.SUCCESS_CODE;
			json.put("tot_count", listCount);
			json.put("rec_count", list.size());
			json.put("data_list", jsonArr);
		}else{
			result = Constant.FAIL_CODE;
		}
		
	}

	void proUpMsgPushStatus(String conditionStr) {
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();		
		WMsgInfo wm = new WMsgInfo();
		
		wm.setPush_status("1");
		
		wm.setCondition( conditionStr );
		infoFacade.updateByUserIdMsgInfo(wm);
	
	}
			
	void proUpMsgStatus(String conditionStr) {
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();		
		WMsgInfo wm = new WMsgInfo();
		
		wm.setStatus("1");
		
		wm.setCondition( conditionStr );
		infoFacade.updateByUserIdMsgInfo(wm);
	
	}

	void proAppHB(JSONObject object) throws SystemException {
		Tools tls = new Tools();
		
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		String user_id = object.optString("iser_id").trim();
		String type = object.optString("type").trim();
		
		
		vo.setCondition("user_id=" + user_id );
		if ("3".equals(type)) {		//心跳包
			String login_time;
			//String login_time = Tools.getStringFromDate(new Date());
			if ( Constant.timeUtcFlag )
				login_time = tls.getUtcDateStrNow();					
			else
				login_time = tls.getStringFromDate(new Date());
			
			vo.setLastlogin_time(login_time);
			fd.updateWappUsers(vo);		
		} else if ("0".equals(type)) {	//APP通知进入, 需要启动另外一个线程去发送下行指令通知相应终端
			vo.setLogin_status("1");
			String login_time = null;
			//String login_time = Tools.getStringFromDate(new Date());
			if ( Constant.timeUtcFlag )
				login_time = tls.getUtcDateStrNow();					
			else
				login_time = tls.getStringFromDate(new Date());
			
			vo.setLastlogin_time(login_time);
			fd.updateWappUsers(vo);					
		} else if ("1".equals(type)) {	//APP通知转入后台, 需要启动另外一个线程去发送下行指令通知相应终端
			vo.setLogin_status("2");
//			String login_time = Tools.getStringFromDate(new Date());
			String login_time = null;
			//String login_time = Tools.getStringFromDate(new Date());
			if ( Constant.timeUtcFlag )
				login_time = tls.getUtcDateStrNow();					
			else
				login_time = tls.getStringFromDate(new Date());
			
			vo.setLastlogin_time(login_time);
			fd.updateWappUsers(vo);					
		} else if ("2".equals(type)) {	//APP通知退出
			//vo.setLogin_status("0");
			//fd.updateWappUsers(vo);					
		}
		
		result = Constant.SUCCESS_CODE;
		
		
		//2.9 APP用户获取绑定及分享设备列表 
		//请求接口: http://server_name:8080/appname/ doWTAppDeviceMan.do
		//处理文件: WTAppDeviceManAction.java
		//{“cmd”,“bindlist”}   String  协议标识
		//找到所有设备， 然后调用
		
	//public void setGpsMap(String imeiStr, boolean gpsMapFlag)
		//CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
		//cmdDownSetImpl.setGpsMap(imeiStr, gpsMapFlag);
		// not finished
		
	}
		
	public void proClrLst(JSONObject object) {
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		Tools tls = new Tools();
		
		Object o= object.get("id_list");
		
		Object[] idLst = (Object[]) ((JSONArray)o).toArray();
		

		//调用此协议认为将同时产生心跳信号
		/*
		try { 
		WTSigninAction  sa = new WTSigninAction();
		sa.heartBeat(String.valueOf(user_id) );	
		sa = null;
		// end
		} catch(Exception e) {
			
		}*/
		
		if  ( idLst.length <= 0 ) {
			result = Constant.FAIL_CODE;
			return;			
		}
		
		WMsgInfo wm = new WMsgInfo();
		StringBuffer sb = new StringBuffer();

		sb.append(" msg_id in ");
		sb.append(tls.getSqlStringFromIntList(idLst) );
		
		wm.setCondition(sb.toString());
		if ( infoFacade.delMsgInfoIdLst(wm) > 0 ) {
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
	}

	public void proClrAll(JSONObject object) {
		WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
		
		int user_id = object.getInt("user_id");
		

		//调用此协议认为将同时产生心跳信号
		/*
		try { 
		WTSigninAction  sa = new WTSigninAction();
		sa.heartBeat(String.valueOf(user_id) );	
		sa = null;
		// end
		} catch(Exception e) {
			
		}*/
		
		
		WMsgInfo wm = new WMsgInfo();
		StringBuffer sb = new StringBuffer();

		sb.append(" to_usrid = ");
		sb.append(user_id );
		
		wm.setCondition(sb.toString());
		if ( infoFacade.delMsgInfoIdLst(wm) > 0 ) {
			result = Constant.SUCCESS_CODE;
		}else{
			result = Constant.FAIL_CODE;
		}
		
	}
		
	//当主人同意或者拒绝设备分享的时候，自动将 wmsg_info 表的相关推送消息的推送状态设置为1
	public void proPushStatusByHost(int user_id, int device_id) {				
		try {
				
			WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
											
			WMsgInfo wm = new WMsgInfo();
			StringBuffer sb = new StringBuffer();
			StringBuffer sb_count = new StringBuffer();
	
			sb_count.append(" to_usrid = "+user_id + " and msg_ind_id=" + + Constant.CST_MSG_IND_APPLY_SHARE);	
			sb_count.append( " and push_status = 0 and device_id=" + device_id);		
						
			//wm.setCondition(sb.toString());
			
			wm.setPush_status("1");
			
			wm.setCondition( sb_count.toString() );
			infoFacade.updateByUserIdMsgInfo(wm);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//当主人同意或者拒绝设备分享的时候，自动将 wmsg_info 表的相关推送消息的推送状态设置为1
	public void proPushStatusByApplyer(int user_id, int device_id) {		
		
		try {
				
			WTAppMsgManFacade infoFacade = ServiceBean.getInstance().getWtAppMsgManFacade();
											
			WMsgInfo wm = new WMsgInfo();
			StringBuffer sb = new StringBuffer();
			StringBuffer sb_count = new StringBuffer();
	
			sb_count.append(" from_usrid = "+user_id + " and msg_ind_id=" + + Constant.CST_MSG_IND_APPLY_SHARE);	
			sb_count.append( " and push_status = 0 and device_id=" + device_id);		
						
			//wm.setCondition(sb.toString());
			
			wm.setPush_status("1");
			
			wm.setCondition( sb_count.toString() );
			infoFacade.updateByUserIdMsgInfo(wm);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}	
	
}
