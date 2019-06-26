package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import com.wtwd.sys.innerw.liufeng.domain.WSuggestion;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSuggestionFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;

public class WTAppFeedAction extends BaseAction {
	
	private JSONObject json = null;
	private Date time_stamp = null;
	Log logger = LogFactory.getLog(WTAppFeedAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		try {
			// 判断提交上来的数据是否是上传表单的数据
			if( ServletFileUpload.isMultipartContent(request)) { //有图片
				String uploadDir = request.getSession(true).getServletContext().getRealPath("/images/");
				logger.info("have photo:" + uploadDir);
				File fileDir = new File(uploadDir);
				if (!fileDir.exists() && !fileDir.isDirectory() ) {
					fileDir.mkdirs();
				}
					
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				factory.setSizeThreshold(5000*1024*1024);
				upload.setHeaderEncoding("UTF-8");
				
				List<FileItem> list = upload.parseRequest(request);
				logger.info("parseRequest list:" + list);
				ProFileItem(uploadDir, list);
			}else{ //无图片
				SaveSuggestionInfo(request);
			}
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
		
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
		
		return null;
	}	
	
	// 无图片请求
	private void SaveSuggestionInfo(HttpServletRequest request) throws Exception {
		Tools tls = new Tools();

		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		logger.info("no photo params:" + sb.toString());
		JSONObject object = JSONObject.fromObject(sb.toString());
		String cmd = object.getString("cmd");
		int user_id = object.getInt("user_id");
		String app_token = tls.getSafeStringFromJson(object, "app_token");

		if ((result = verifyUserId(String.valueOf(user_id))) == Constant.SUCCESS_CODE) {
			if ((result = verifyAppToken(String.valueOf(user_id), app_token)) == Constant.SUCCESS_CODE) {				
				if (cmd.equals("add")) { // APP增加用户反馈
					proAdd(object);
				} else {
					result = Constant.ERR_INVALID_PARA;
				}
			}
		}
	}
	
	private void proAdd(JSONObject object) {
		Tools tls = new Tools();
		
		int user_id = object.getInt("user_id");
		String app_name = tls.getSafeStringFromJson(object, "app_name");
		String msg = tls.getSafeStringFromJson(object, "msg");
		
		WSuggestionFacade suggestFacade = ServiceBean.getInstance().getwSuggestionFacade();
		WSuggestion ws = new WSuggestion();
		ws.setUser_id(String.valueOf(user_id));
		if ( Constant.timeUtcFlag ) // true
			ws.setDate_time(tls.getUtcDateStrNowDate());					
		else
			ws.setDate_time(new Date());
		
		if(msg != null && !"".equals(msg)){
			ws.setMsg(msg);
		}
		int res = suggestFacade.insertUserSuggestion(ws);
		
		if(res > 0){
			result = Constant.SUCCESS_CODE;
			try {
				sendEmail(user_id, app_name, msg, null);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}else{
			result = Constant.FAIL_CODE;
		}
	}
		
	//获取用户属性map
	private Map<String, Object> getUserProps(int user_id) throws SystemException {
    	WappUsers vo = new WappUsers();
    	WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
    	vo.setCondition("user_id="+String.valueOf(user_id).trim() );
    	List<DataMap> list = fd.getWappUsers(vo);
    	if (list.size() == 1 ) {
			HashMap<String, Object> res = new HashMap<String, Object>();
    		res.put("dfg", list.get(0).getAt("dfg"));
    		res.put("email", list.get(0).getAt("email"));    		
    		return res;
    	} else
    		return null;
	}
	
	//有图片
	private void ProFileItem(String uploadDir, List<FileItem> list) throws Exception {
		String cmd = null;
		String app_token = null;
		int user_id = -1;
		String msg = null;
		String app_name = null;
		Tools tls = new Tools();
		// 每一个FileItem对应一个Form表单的输入项
		for (FileItem item: list) {
			if (item.isFormField()) { //普通表单,只赋值
				String name = item.getFieldName();				
				if ("app_token".equals(name)) {
					app_token = item.getString("UTF-8");							
				} else if ("user_id".equals(name)) {
					user_id = Integer.parseInt(item.getString("UTF-8").trim());
				} else if ("msg".equals(name)) {
					msg = item.getString("UTF-8");
				} else if ("cmd".equals(name)) {
					cmd = item.getString("UTF-8");
				} else if ("app_name".equals(name)) {
					app_name = item.getString("UTF-8");
				}				
			} else { //fileitem中封装的是上传文件
				time_stamp = tls.getUtcDateStrNowDate();	//new Date(); 
				if ("add".equals(cmd)) {					
					String fileName = proAppHeadFileItem(uploadDir + "/app/msg/", app_token, user_id, item);
					logger.info("user_id=" + user_id + ",app_token" + app_token + ",fileName:" + fileName);
					WSuggestionFacade suggestFacade = ServiceBean.getInstance().getwSuggestionFacade();					
					WSuggestion ws = new WSuggestion();
					ws.setUser_id(String.valueOf(user_id));
					if (Constant.timeUtcFlag)  // true
						ws.setDate_time(tls.getUtcDateStrNowDate());					
					else
						ws.setDate_time(new Date());
					
					if(msg != null && !"".equals(msg)){
						ws.setMsg(msg);
					}
					ws.setPhoto(fileName);
					int res = suggestFacade.insertUserSuggestion(ws);
					if(res > 0){
						result = Constant.SUCCESS_CODE;
						String addon = uploadDir + "/app/msg/" + fileName;
						logger.info("addon=" + addon);
						sendEmail(user_id, app_name, msg, addon);
					}else{
						result = Constant.FAIL_CODE;
					}					
				} else {
					result = Constant.ERR_INVALID_PARA;
				}
				return;
			}
		}		
		result = Constant.ERR_INVALID_PARA;
	}
	
	private String proAppHeadFileItem(String uploadDir, String app_token,
			int user_id, FileItem item) throws Exception {
		String filename = null;
		if ( verifyUserRight(user_id, app_token) != Constant.SUCCESS_CODE )			
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName(); //得到上传的文件名称
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);		
			String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);
			
			filename = new String ("msg" + time_stamp.getTime() + "." + sufixname);

			StringBuffer sbRealPath = new StringBuffer();  
            sbRealPath.append(uploadDir).append(filename);
            
            //写入文件  
            File file = new File(sbRealPath.toString());  
            item.write(file);
			
			result = Constant.SUCCESS_CODE;
		}
		return filename;
	}
	
	private int verifyUserRight(int user_id, String token) throws SystemException {
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + user_id + " and app_token='" + token + "'");
		List<DataMap> list = fd.getWappUsers(vo);
		if (list.size() == 1)
		{
			return Constant.SUCCESS_CODE;
		}
		return Constant.ERR_USER_RIGHT;
	}
	
	// 发邮件
	private void sendEmail(int user_id, String app_name, String msg, String addon) throws IOException, SystemException {
		WTFindPasswordAction wfpa = new WTFindPasswordAction();
		Map<String, Object> userProps = getUserProps(user_id);
		if ( userProps != null ) {				
			String useremail = userProps.get("email").toString().trim();   //用户邮箱
			String dfg = userProps.get("dfg").toString().trim();
			wfpa.setDfg(Integer.parseInt(dfg));	 //无意义			
			
			String dest_email = null;    //目标邮箱
			if ("rombo".equals(app_name)) {
				dest_email = Constant.ROMBO_SERVER_EMAIL;
			}else {
				dest_email = Constant.DREAMCARE_SERVER_EA;
			}
			
			if (Tools.OneString.equals(dfg)) {
				Properties pros = new Properties();
				pros.load(this.getClass().getClassLoader()
						.getResourceAsStream("server.properties"));		
				dest_email = pros.getProperty("dreamemail");
			}
			logger.info("destination email:" + dest_email);
			
			StringBuffer sbt = new StringBuffer();
			sbt.append("Customer's feedback. USER_ID: ");
			sbt.append(user_id);
			sbt.append("(from: ");
			sbt.append(useremail);
			sbt.append(")");
			wfpa.initPara(useremail, dest_email, sbt.toString(), msg, addon);
			try {
				wfpa.CreateMessageIn();
			} catch (MessagingException e) {
				logger.info("MessagingException:" + e);
			}
		}
	}
	
}
