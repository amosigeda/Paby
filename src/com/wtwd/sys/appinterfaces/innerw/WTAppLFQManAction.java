package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;

public class WTAppLFQManAction extends BaseAction {
	
	private JSONObject json = null;
	Log logger = LogFactory.getLog(WTAppLFQManAction.class);
	
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
			
			logger.info("WTAppLFQManAction params:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.getString("cmd");
			int user_id = object.getInt("user_id");
			String app_token = tls.getSafeStringFromJson(object, "app_token");
		
			if ( ( result = verifyAppToken(String.valueOf(user_id), 
					app_token)) == Constant.SUCCESS_CODE ) {
				if (cmd.equals("set")) {
					proSet(object);
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
		logger.info("WTAppLFQManAction result:" + json.toString());
		return null;
	}	
	
	// 设置定位频率 用于rembo APP 2018-9-8
	public void proSet(JSONObject object) {
		try {
			// Tools tls = new Tools();
			int device_id = object.getInt("device_id");
			int duration = object.getInt("duration");
			int user_id = object.getInt("user_id");
			
			logger.info("inter to WTAppLFQManAction.proSet()-------");
						
			CmdDownSetImpl cmdDownSetImpl = new CmdDownSetImpl();
	
			IoSession sn = cmdDownSetImpl.getCmdDeviceIoSession(
					this.getDeviceImeiFromDeviceId(
							String.valueOf(device_id)));
			
			if ( sn == null ) {				
				result = Constant.SUCCESS_CODE;
			} else {
				JSONObject jo =  new JSONObject();
				jo.put("dur", duration);
				jo.put("userId", user_id);
	
				JSONObject jo1 =  new JSONObject();
				jo1.put("setLFQ", jo.toString());
									
				sn.write(jo1.toString());
				
				result = Constant.SUCCESS_CODE;	
			}
			cmdDownSetImpl = null;
			
			WdeviceActiveInfo wdeviceActiveInfo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade wdeviceActiveInfoFacade = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			
			wdeviceActiveInfo.setLfq(duration);
			wdeviceActiveInfo.setCondition("device_id = '"+device_id+"'");
			wdeviceActiveInfoFacade.updatewDeviceExtra(wdeviceActiveInfo);
			//  add 更新wdevice_active_info表uLTe字段，定位时间频率
			wdeviceActiveInfo.setuLTe(duration);
			wdeviceActiveInfoFacade.updateWdeviceActiveInfo(wdeviceActiveInfo);
			logger.info("定位设置完成-----!");
			
		} catch (Exception e) {
			logger.info("WTDevLCTOptAction proSet occurred exception!");
			result = Constant.FAIL_CODE;				
		}
		
	}	

}