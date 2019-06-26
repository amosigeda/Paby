package com.wtwd.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
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

import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.sys.appuserinfo.domain.AppUserInfo;

public class updateUserAction extends BaseAction{
	
	Log logger = LogFactory.getLog(updateUserAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		JSONObject json = new JSONObject();
		String href= request.getServletPath();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String userId = object.getString("user_id");
			String head = object.getString("user_head");
			String user_name = object.getString("user_name");
			String sex = object.getString("user_sex");
			String age = object.getString("user_age");
			String height = object.getString("user_height");
			String weight = object.getString("user_weight");
			String belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
			String email=object.getString("email");
			AppUserInfo vo = new AppUserInfo();			
			vo.setCondition("id='"+userId+"'");
			
			if(!"0".equals(head)){
				String path = request.getSession(true).getServletContext().getRealPath(Constant.USER_SAVE) + userId;
				Constant.deleteFile(path);  //��ΪҪ�޸���,���԰�֮ǰ�ĸ�ɾ��
				
				String fileName = Constant.getUniqueCode(userId) + ".png";		
				Constant.createFileContent(path, fileName, Base64.decodeBase64(head));
				
				String url = "http://" +getServerName() +":";
				int port = request.getServerPort();
				String downloadpath = request.getContextPath() + Constant.USER_SAVE + userId + "/" +fileName;
				head = Constant.getDownloadPath(url, String.valueOf(port), downloadpath);
				vo.setHead(head);
				json.put("user_head", head);
			}		
			user_name = Constant.transCodingToUtf(user_name);  //��Ҫת��
			vo.setNickName(user_name);
			vo.setSex(sex);
			vo.setAge(age);
			vo.setUserHeight(height);
			vo.setUserWeight(weight);
			vo.setEmail(email);
				
			int num = ServiceBean.getInstance().getAppUserInfoFacade().updateAppUserInfo(vo);
			if(num > 0){
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}
			json.put("request", href);
			insertVisit(href,belongProject,userId,0,start,new Date());
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
		json.put(Constant.RESULTCODE, result);
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
