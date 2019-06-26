//yonghu create 20160625 label

package com.wtwd.sys.appinterfaces.innerw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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
import com.wtwd.sys.innerw.liufeng.domain.WSoundInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSoundInfoFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

public class WTUploadAction extends BaseAction{

	public int result = Constant.FAIL_CODE; 
	private JSONObject json = null;
	private Date time_stamp = null; 
	
	Log logger = LogFactory.getLog(WTAppDeviceManAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		String href= request.getServletPath();
		json = new JSONObject();
		int verifyRes = Constant.ERR_INVALID_PARA;
		logger.info("WTUploadAction href:" + href);
		try {			
			String uploadDir = request.getSession(true).getServletContext().getRealPath("./");
			//uploadDir = "D:\\Workspaces\\MyEclipse 2015\\.metadata\\.me_tcat7\\webapps\\wtpet\\";
			logger.info("WTUploadAction uploadDir:" + uploadDir);
			File fileDir = new File(uploadDir);
			if (!fileDir.exists() && !fileDir.isDirectory() ) {
				fileDir.mkdirs();
			}
				
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setSizeThreshold(5000*1024*1024);
			upload.setHeaderEncoding("UTF-8");
			
			if( ServletFileUpload.isMultipartContent(request) ) {
				
				List<FileItem> list = upload.parseRequest(request);
				logger.info("WTUploadAction FileItem:" + list);
				ProFileItem(uploadDir, list); 
				json.put(Constant.RESULTCODE, result);				
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
				logger.info("upload result:" + json);
				return null;
				
			} else {
				writeInitInputStream(uploadDir,request);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		}
        return null;
	}

	private void ProFileItem(String uploadDir, List<FileItem> list)
			throws UnsupportedEncodingException, SystemException, IOException,
			FileNotFoundException {
		Tools tls = new Tools();
		
		String app_token = null;
		int user_id = -1;
		int pet_id = -1;
		String cmd = null;
		String device_imei = null;

		for (FileItem item:list) {	
		
			if (item.isFormField()) {
				String name = item.getFieldName();
				
				if ("app_token".equals(name)) {
					app_token = item.getString("UTF-8");							
				} else if ("user_id".equals(name)) {
					user_id = Integer.parseInt(item.getString("UTF-8").trim());
				} else if ("pet_id".equals(name)) {
					pet_id = Integer.parseInt(item.getString("UTF-8").trim());
				} else if ("imei".equals(name)) {
					device_imei = item.getString("UTF-8").trim();
				} else if ("cmd".equals(name)) {
					cmd = item.getString("UTF-8").trim();
				}
				
			} else {
				//time_stamp = new Date();
				if ( Constant.timeUtcFlag )		
					time_stamp = tls.getUtcDateStrNowDate();
				else
					time_stamp = new Date();		    
										
				if("petUpHead".equals(cmd)) {
					proPetHeadFileItem(uploadDir + "images/pet/head/", app_token, user_id, pet_id,
							item);
				} else if ("appUpHead".equals(cmd)) {
					proAppHeadFileItem(uploadDir + "images/app/head/", app_token, user_id,
							item);					
				} else if ("upCallbackVoice".equals(cmd)) {
					proPetCallbackFileItem(uploadDir + "images/dev/sound/", app_token, user_id,pet_id,
							item);					
				} else if ("devUpLog".equals(cmd)) {	//上传日志文件
					proDevLogFileItem(uploadDir + "firmware/log/", app_token, user_id, device_imei,
							item);					
				} else {
					result = Constant.ERR_INVALID_PARA;
				}

				return;
			}
		}
		
		result = Constant.ERR_INVALID_PARA;
	}

	private void proAppHeadFileItem(String uploadDir, String app_token,
			int user_id, FileItem item) throws SystemException,
			IOException, FileNotFoundException {
		Tools tls = new Tools();	
		
		if ( verifyUserRight(user_id, app_token) != Constant.SUCCESS_CODE )								
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName();
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);
			
			String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);
			String filename = getSysAppNewFilename(sufixname);

			writeServFile(uploadDir, item, filename);
			updateAppUserInfo(user_id, filename);
			json.put("file_name", filename);
			json.put("time_stamp", tls.getStringFromDate(time_stamp));
			result = Constant.SUCCESS_CODE;
		}
	}
	
	private Integer getDeviceIdFromPetId(int pet_id) throws SystemException {
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		
		vo.setCondition("pet_id=" + pet_id );
		List<DataMap> list = fd.getDogDataList(vo);
		if ( list.size() == 1 )
			return (Integer) list.get(0).getAt("device_id");
		else
			return -1;
		
	}
	
	private void proPetCallbackFileItem(String uploadDir, String app_token,
			int user_id,  int pet_id, FileItem item) throws SystemException,
			IOException, FileNotFoundException {
		Tools tls = new Tools();
		
		if ( verifyUserRight(user_id, pet_id, app_token) != Constant.SUCCESS_CODE )								
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName();
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);
			
			String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);
			String filename = getSysCallbackNewFilename(sufixname);

			writeServFile(uploadDir, item, filename);
			//updateAppUserInfo(user_id, filename);
			WSoundInfoFacade soundFacade = ServiceBean.getInstance().getwSoundInfoFacade();
			WSoundInfo si = new WSoundInfo();

			si.setDevice_id(getDeviceIdFromPetId(pet_id));
			si.setCall_file(filename);
			si.setUp_time(time_stamp);
			int res = soundFacade.insertPetSoundInfo(si);

			json.put("time_stamp", tls.getStringFromDate(time_stamp));
			result = Constant.SUCCESS_CODE;
		}
	}	
	
	private void proPetHeadFileItem(String uploadDir, String app_token,
			int user_id, int pet_id, FileItem item) throws SystemException,
			IOException, FileNotFoundException {
		Tools tls = new Tools();
		
		if ( verifyUserRight(user_id, pet_id, app_token) != Constant.SUCCESS_CODE )								
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName();
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);
			
			String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);
			String filename = getSysNewFilename(sufixname);

			writeServFile(uploadDir, item, filename);
			updatePetInfo(pet_id, filename);
			json.put("file_name", filename);			
			json.put("time_stamp", tls.getStringFromDate(time_stamp));
			result = Constant.SUCCESS_CODE;
		}
	}

	private void writeServFile(String uploadDir, FileItem item, String filename)
			throws IOException, FileNotFoundException {
		//filename = filename.substring(filename.lastIndexOf("/") + 1);
		InputStream ina = item.getInputStream();
		
		FileOutputStream outa = new FileOutputStream(uploadDir + filename);
		byte buffera[] = new byte[1024];
		int lena = 0;
		while((lena = ina.read(buffera)) > 0) {
			outa.write(buffera, 0, lena);
		}
		ina.close();
		outa.close();
		item.delete();
	} 
	
	private String getSysCallbackNewFilename(String suffixname)
	{
		//time_stamp = new Date();
		String res = new String ("call" + time_stamp.getTime() + "." + suffixname);
		return res;
	}
	
	private String getSysAppNewFilename(String suffixname)
	{
		//time_stamp = new Date();
		String res = new String ("app" + time_stamp.getTime() + "." + suffixname);
		return res;
	}
	
	private String getSysNewFilename(String suffixname)
	{
		//time_stamp = new Date();
		String res = new String ("dog" + time_stamp.getTime() + "." + suffixname);
		return res;
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
		
	private int verifyUserRight(int user_id, int pet_id, String token) throws SystemException {
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		vo.setCondition("user_id=" + user_id + " and app_token='" + token + "'");
		List<DataMap> list = fd.getWappUsers(vo);
		if (list.size() == 1)
		{				
			return verifyUserHostPet(String.valueOf(user_id), String.valueOf(pet_id));
		}
		return Constant.ERR_USER_RIGHT;
	}
	
	private void updateAppUserInfo(int user_id, String filename) throws SystemException {
		WappUsers vo = new WappUsers();
		WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
		Tools tls = new Tools();
		
		vo.setUser_photo(filename);
		vo.setPhoto_time_stamp(tls.getStringFromDate(time_stamp));
		vo.setCondition("user_id="+user_id);
		fd.updateWappUsers(vo);
	}
	
	private void updatePetInfo(int pet_id, String filename) throws SystemException {
		Wpet vo = new Wpet();
		WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
		Tools tls = new Tools();
		
		vo.setPet_id(String.valueOf(pet_id) );
		vo.setPhoto(filename);
		vo.setPhoto_time_stamp(tls.getStringFromDate(time_stamp));
		vo.setCondition("pet_id="+pet_id);
		insertVisit(null, null, "80", "pet_id" + pet_id + 
				",file:" + filename +
				",photo stamp:" +
				tls.getStringFromDate(time_stamp));
		
		fd.updateDog(vo);
	}
	
	private void writeInitInputStream(String uploadDir, HttpServletRequest request) throws IOException {
        File dirPath = new File(uploadDir);
    	
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        ServletInputStream input = request.getInputStream();

        OutputStream bos = new FileOutputStream(uploadDir + "test.bin");
        int bytesRead;
        byte[] buffer = new byte[8192];
        Long len = 0L;
        while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
            len += bytesRead;
        }

        Enumeration it = request.getHeaderNames();
        while(it.hasMoreElements()) {
        	String fld = (String )it.nextElement();
        	StringBuffer sb = new StringBuffer(fld);
        	sb.append(":");
        	sb.append(request.getHeader(fld));
        	sb.append("\n");
        	bos.write(sb.toString().getBytes(), 0, sb.length());
        }

    	StringBuffer sb1 = new StringBuffer("vLEN");
    	sb1.append(":");
    	sb1.append(len);
    	bos.write(sb1.toString().getBytes(), 0, sb1.length());
     
        bos.close();
        input.close(); 

		result = Constant.SUCCESS_CODE;	        
	
	}
	
	private void proDevLogFileItem(String uploadDir, String app_token,
			int user_id, String device_imei, FileItem item) throws SystemException,
			IOException, FileNotFoundException {
    	LocationInfoHelper lih = new LocationInfoHelper();
    	Tools tls = new Tools();
		
		if ( verifyUserRight(user_id, app_token) != Constant.SUCCESS_CODE )								
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName();
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);
			
			//String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);

			writeServFile(uploadDir, item, fileparaname);
			
			//数据库记录本次操作，为具体设备于发生日期上传了一次日志文件
			WdeviceActiveInfo vo = new WdeviceActiveInfo();
			WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
			Integer device_id = getDeviceIdFromDeviceImei(device_imei); 
			vo.setDevice_id(device_id);
			vo.setLog_file(fileparaname);
			//vo.setUp_time(LocationInfoHelper.getDevCurrentTime(device_id));
			if ( Constant.timeUtcFlag )
				vo.setUp_time(tls.getUtcDateStrNow());			
			else 																					
				vo.setUp_time(lih.getDevCurrentTime(device_id));
			
			if ( fd.insertwdevLogFile(vo) > 0 )			
				result = Constant.SUCCESS_CODE;
		}
	}	
			
}
