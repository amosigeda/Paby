package com.wtwd.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.wtwd.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.wtwd.sys.phoneinfo.domain.PhoneInfo;
import com.wtwd.sys.phoneinfo.domain.logic.PhoneInfoFacade;

public class BondBaoBeiAction extends BaseAction {

	Log logger = LogFactory.getLog(BondBaoBeiAction.class);

	private final static int BOND_FAIL_NO = -2;  //�豸���Ϸ�
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href= request.getServletPath();
		
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			PhoneInfo phoneInfo = new PhoneInfo(); // �����豸����Ϣ��
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			
			PhoneInfoFacade mPhoneInfoFacade = ServiceBean.getInstance().getPhoneInfoFacade();
			DeviceActiveInfoFacade mDeviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
			
			// ����Ϣ
			String user_id = object.getString("user_id");
			String device_imei = object.getString("device_imei");
			String device_name = object.getString("device_name");
			String device_phone = object.getString("device_phone");
			String device_head = object.getString("device_head");
			String device_sex = object.getString("device_sex");
			String device_age = object.getString("device_age");
			String device_height = object.getString("device_height");
			String device_weight = object.getString("device_weight");
			String belongProject = object.getString("belong_project");
			

			if (device_imei == null)
				device_imei = "";
			if (device_head == null)
				device_head = "";
			if (device_sex == null)
				device_sex = "0";
			if (device_age == null)
				device_age = "2015-12-12";
			if (device_height == null)
				device_height = "170";
			if (device_weight == null)
				device_weight = "100";
			if (device_name == null ) 
				device_name = "test1";
			
			if (user_id == null || device_imei == null) {

			}else{
				device_name = Constant.transCodingToUtf(device_name);
				phoneInfo.setCondition("serie_no = '"+device_imei+"'");
				List<DataMap> phoneList = mPhoneInfoFacade.getPhoneInfo(phoneInfo);
				deviceActiveInfo.setCondition("device_imei ='"+device_imei+"' and belong_project='"+belongProject+"' and device_disable = '1'");  //���Ҽ���
				List<DataMap> deviceList = mDeviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
				if(phoneList.size() <= 0){  //�豸���Ϸ�
					result = BOND_FAIL_NO;  
				}else if(deviceList.size() > 0){  //�豸�ѱ���
					
					if(!"0".equals(device_head)){
						String path = request.getSession(true).getServletContext().getRealPath(Constant.DEVICE_SAVE) + device_imei;
						Constant.deleteFile(path);  //ɾ��֮ǰ��
						
						String fileName = Constant.getUniqueCode(device_imei) + ".png";			
						Constant.createFileContent(path, fileName, Base64.decodeBase64(device_head));
						
						String url = "http://" +getServerName() +":";
						int port = request.getServerPort();
						String downloadpath = request.getContextPath() + Constant.DEVICE_SAVE + device_imei + "/" +fileName;
						device_head = Constant.getDownloadPath(url, String.valueOf(port), downloadpath);
						deviceActiveInfo.setDeviceHead(device_head);
						json.put("device_head",device_head);
					}			
					deviceActiveInfo.setDeviceAge(device_age);
					deviceActiveInfo.setDeviceHeight(device_height);
					deviceActiveInfo.setDeviceName(device_name);
					deviceActiveInfo.setDeviceSex(device_sex);
					deviceActiveInfo.setDeviceWeight(device_weight);		
					deviceActiveInfo.setDevicePhone(device_phone);
					mDeviceActiveInfoFacade.updateDeviceActiveInfo(deviceActiveInfo);
					
					phoneInfo.setPhone(device_phone);
					mPhoneInfoFacade.updatePhoneInfo(phoneInfo);
					result = Constant.SUCCESS_CODE;  
				}
			}
			insertVisit(href,belongProject,user_id,0,start,new Date());
		} catch (Exception e) {
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
		json.put(Constant.RESULTCODE, result);
		json.put("request", href);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	public String getServerName() throws Exception {
		String serverName = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName = pros.getProperty("servername");
		return serverName;
	}
}
