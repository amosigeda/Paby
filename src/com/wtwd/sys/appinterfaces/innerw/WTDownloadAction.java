//yonghu create 20160625 label

package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wtwd.sys.innerw.liufeng.domain.logic.WTCheckVersionFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wcheckInfo.domain.WcheckInfo;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;

public class WTDownloadAction extends BaseAction{

	public int result = Constant.FAIL_CODE; 
	private JSONObject json = null;
	private Date time_stamp = null; 
	
	Log logger = LogFactory.getLog(WTAppDeviceManAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";

	
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
			logger.info("WTDownloadAction params:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			
			if ("petQueryHead".equals(cmd)) {
				String app_token = object.optString("app_token");
				int user_id = object.optInt("user_id");
				int pet_id = object.optInt("pet_id");
				String pet_type = tls.getSafeStringFromJsonD0(object, "pet_type");
				proPetQueryHead(pet_type, user_id, app_token, pet_id);
			} else if ("petDownHead".equals(cmd)) {
				String app_token = object.optString("app_token");
				int user_id = object.optInt("user_id");
				int pet_id = object.optInt("pet_id");
				String pet_type = tls.getSafeStringFromJsonD0(object, "pet_type");
				proPetDownHead(request, response, pet_type, user_id, app_token, pet_id);
				return null;
			} else if ("appQueryHead".equals(cmd)) {
				String app_token = object.optString("app_token");
				int user_id = object.optInt("user_id");
				proAppQueryHead(user_id, app_token);
 			} else if ("appDownHead".equals(cmd)) {
				String app_token = object.optString("app_token");
				int user_id = object.optInt("user_id");
				proAppDownHead(request, response, user_id, app_token);
				return null;
 			} else if ("getAppVer".equals(cmd)) {
 				int user_id = object.optInt("user_id"); 				
				//String app_token = object.getString("app_token");
 				String app_token = tls.getSafeStringFromJson(object, "app_token");
				if ( ( result = verifyUserId(String.valueOf(user_id)) ) 
						== Constant.SUCCESS_CODE ) {
					if ( ( result = verifyAppToken(String.valueOf(user_id), 
						app_token)) == Constant.SUCCESS_CODE ) {
						    proGetAppVer(object);
							//result = Constant.SUCCESS_CODE;													
					}
				}				
 			} else {
				result = Constant.ERR_INVALID_PARA;								
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

		
		return null;
	}

    void proGetAppVer(JSONObject object) {
		Tools tls = new Tools();		
    	
		WTCheckVersionFacade infoFacade = ServiceBean.getInstance().getWtCheckVersionFacade();
		WcheckInfo wi = new WcheckInfo();
    	
		String package_name = tls.getSafeStringFromJson(object, "package_name");
    	
		if(!tls.isNullOrEmpty("package_name")){
			wi.setCondition(" package_name = '"+package_name+"' order by upload_time desc");
			
			List<DataMap> list = infoFacade.queryCheckVersionInfo(wi);
			if(list != null/* && list.size() > 0 */){
				json.put("version_code", "");
				json.put("download_path", "");

				for(int i=0;i<list.size()&& i< 1;i++){
					DataMap versionMap = list.get(i);
					String version_code = versionMap.getAt("version_code").toString().trim();
					String download_path = versionMap.getAt("download_path").toString().trim();
					String function_cap = versionMap.getAt("function_cap").toString().trim();
					json.put("version_code", version_code);
					json.put("function_cap", function_cap);
					json.put("download_path", download_path);
				}
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}
		} else {
			result = Constant.ERR_INVALID_PARA;
		}
    	
    }

	
	void proPetDownHead(HttpServletRequest request, HttpServletResponse response, String pet_type, int user_id, String token, int pet_id) throws SystemException, IOException {
		/*
		if( !"0".equals(pet_type) ) {
			response.setStatus(500);			
			return;
		}*/
		
		if ( verifyUserRight(user_id, pet_id, token) == Constant.SUCCESS_CODE ) {
			String photoFile = queryPetHeadFile(request, pet_id);
			download(request, response, photoFile, "pet.jpg" );
		} else {
			response.setStatus(500);

			
		}
	}

	void proAppDownHead(HttpServletRequest request, HttpServletResponse response, int user_id, String token) throws SystemException, IOException {
		
		if ( verifyAppToken(String.valueOf(user_id), token) == Constant.SUCCESS_CODE ) {
			String photoFile = queryAppHeadFile(request, user_id);
			download(request, response, photoFile, "pet.jpg" );
		} else {
			response.setStatus(500);						
		}
	}
	
	

	void proPetQueryHead(String pet_type, int user_id, String token, int pet_id) throws SystemException {
		/*
		if( !"0".equals(pet_type) ) {
			result = Constant.ERR_INVALID_PARA;
			return;
		}*/
		
		if ( ( result = verifyUserRight(user_id, pet_id, token) ) == Constant.SUCCESS_CODE ) {
			String photo_time_stamp = queryPetHeadTimeStamp(pet_id);
			logger.info("phone name=" + photo_time_stamp);
			json.put("time_stamp", photo_time_stamp);
			result = Constant.SUCCESS_CODE;
		}
	}

	
	void proAppQueryHead(int user_id, String token) throws SystemException {
		
		if ( ( result = verifyAppToken(String.valueOf(user_id), token)) == Constant.SUCCESS_CODE ) {
			String photo_time_stamp = queryAppHeadTimeStamp(user_id);
			json.put("time_stamp", photo_time_stamp);
			result = Constant.SUCCESS_CODE;
		}
	}
	
	
	private String queryPetHeadFile(HttpServletRequest request, int pet_id) throws SystemException {
		Tools tls = new Tools();		
		
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		vo.setCondition("pet_id="+pet_id);
		List<DataMap> list = fd.getDogDataList(vo);
		if (list.size() == 1 && !tls.isNullOrEmpty(list.get(0).getAt("photo"))) {
			 String dlDir = request.getSession(true).getServletContext().getRealPath("/images/");
			 StringBuffer sb = new StringBuffer();
			 sb.append(dlDir);
			 sb.append("pet/head/");
			 sb.append( list.get(0).getAt("photo"));
			 return sb.toString();
		} else
			return null;
		
		
	}

	private String queryAppHeadFile(HttpServletRequest request, int user_id) throws SystemException {
		Tools tls = new Tools();		
		
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id="+user_id);
		List<DataMap> list = fd.getWappUsers(vo);
		if (list.size() == 1 && !tls.isNullOrEmpty(list.get(0).getAt("photo"))) {
			 String dlDir = request.getSession(true).getServletContext().getRealPath("/images/");
			 StringBuffer sb = new StringBuffer();
			 sb.append(dlDir);
			 sb.append("app/head/");
			 sb.append( list.get(0).getAt("photo"));
			 return sb.toString();
		} else
			return null;
		
		
	}
	
	
	private String queryPetHeadTimeStamp(int pet_id) throws SystemException {
		Tools tls = new Tools();		
		
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		vo.setCondition("pet_id="+pet_id);
		List<DataMap> list = fd.getDogDataList(vo);
		if (list.size() == 1) {
			if ( !tls.isNullOrEmpty( list.get(0).getAt("photo_time_stamp") ) ) {			
				time_stamp = (Date) list.get(0).getAt("photo_time_stamp");
				return tls.getStringFromDate(time_stamp);
			} else 
				return null;
		} else
			return null;
		
	}

	
	private String queryAppHeadTimeStamp(int user_id) throws SystemException {
		Tools tls = new Tools();		
		
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id="+user_id);
		List<DataMap> list = fd.getWappUsers(vo);
		if (list.size() == 1) {
			if ( !tls.isNullOrEmpty( list.get(0).getAt("photo_time_stamp") ) ) {
				time_stamp = (Date) list.get(0).getAt("photo_time_stamp");
				return tls.getStringFromDate(time_stamp);
			} else
				return null;
		} else
			return null;
		
	}
	
	
	private int verifyUserRight(int user_id, int pet_id, String token) throws SystemException {
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + user_id + " and app_token='" + token + "'");
		List<DataMap> list = fd.getWappUsers(vo);
		if (list.size() == 1)
		{	
			WshareInfo sVo = new WshareInfo();
			WshareInfoFacade sFd = ServiceBean.getInstance().getWshareInfoFacade();
			sVo.setCondition("a.user_id="+user_id + " and c.pet_id="+pet_id);
			List<DataMap> sList = sFd.queryBindList(sVo);
			if (sList.size() < 1)
				return Constant.ERR_USER_RIGHT;			
			return Constant.SUCCESS_CODE;
		}
		return Constant.ERR_USER_RIGHT;
	}
	
	
	private void download(HttpServletRequest request, HttpServletResponse response,String fileUrl,String fileName) throws IOException {  
        java.io.BufferedInputStream bis = null;  
        java.io.BufferedOutputStream bos = null;  
        
        try {  
            // 客户使用保存文件的对话框：  
  
		    fileUrl = new String(fileUrl.getBytes("utf-8"), "utf-8");  
            response.setContentType("application/x-msdownload");  
            response.setCharacterEncoding("utf-8");  
            response.setHeader("Content-disposition", "attachment; filename="  
                    + fileName);  
            // 通知客户文件的MIME类型：  
            bis = new java.io.BufferedInputStream(new java.io.FileInputStream(  
                    (fileUrl)));  
            bos = new java.io.BufferedOutputStream(response.getOutputStream());  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            int i = 0;  
  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
                i++;  
            }  
            bos.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
			response.setStatus(500);			            
        } finally {  
            if (bis != null) {  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                bis = null;  
            }  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                bos = null;  
            }  
        }  
		  
	}  
	

	
		
}




