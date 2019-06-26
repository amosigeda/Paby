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
import com.wtwd.sys.innerw.liufeng.domain.WeFencing;
import com.wtwd.sys.innerw.liufeng.domain.logic.AppSafeAreaManFacade;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;



public class WTSFMnAction extends BaseAction{

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
		
		try {
			
			String uploadDir = request.getSession(true).getServletContext().getRealPath("./");
			//uploadDir = "D:\\Workspaces\\MyEclipse 2015\\.metadata\\.me_tcat7\\webapps\\wtpet\\";
			
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
				ProFileItem(uploadDir, list); 
				json.put(Constant.RESULTCODE, result);				
				response.setCharacterEncoding("UTF-8");	
				response.getWriter().write(json.toString());
				
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
		String cmd = null;
		int device_id = 0;
		double lat = 0.0d;
		double lon = 0.0d;
		int device_safe_range = 0;
		String device_safe_name = null;
		String device_safe_addr = null;
		String dset = null;
		Integer device_safe_id = null;
		int safe_type = 0;
		
		boolean eFd = false;
		
		
		WeFencing wefg = new WeFencing();
		
		

		for (FileItem item:list) {	
		
			if (item.isFormField()) {
				String name = item.getFieldName();
				
				if ("app_token".equals(name)) {
					app_token = item.getString("UTF-8");							
				} else if ("user_id".equals(name)) {
					user_id = Integer.parseInt(item.getString("UTF-8").trim());
				} else if ("device_safe_range".equals(name)) {
					device_safe_range = Integer.parseInt(item.getString("UTF-8").trim());
					wefg.setRound_distance(device_safe_range);
				} else if ("device_id".equals(name)) {
					device_id = Integer.parseInt(item.getString("UTF-8").trim());
					wefg.setDevice_id(device_id);
				} else if ("lon".equals(name)) {
					lon = Double.parseDouble(item.getString("UTF-8").trim());
					wefg.setCenter_gps_lo(lon);
				} else if ("lat".equals(name)) {
					lat = Double.parseDouble(item.getString("UTF-8").trim());
					wefg.setCenter_gps_la(lat);
				} else if ("device_safe_name".equals(name)) {
					device_safe_name = item.getString("UTF-8").trim();
					wefg.setDevice_safe_name(device_safe_name);
				} else if ("device_safe_addr".equals(name)) {
					device_safe_addr = item.getString("UTF-8").trim();
					wefg.setCenter_addr(device_safe_addr);
				} else if ("dset".equals(name)) {
					dset = item.getString("UTF-8").trim();
					wefg.setDevice_safe_effect_time(dset);
				} else if ("safe_type".equals(name)) {
					safe_type = Integer.parseInt(item.getString("UTF-8").trim());
					wefg.setSafe_type(String.valueOf(safe_type));
				} else if ("device_safe_id".equals(name)) {
					device_safe_id = Integer.parseInt(item.getString("UTF-8").trim());
					wefg.setCondition("id=" + device_safe_id);
				} else if ("cmd".equals(name)) {
					cmd = item.getString("UTF-8").trim();
				}
				
			} else {
				
				eFd = true;
				if ( Constant.timeUtcFlag )		
					time_stamp = tls.getUtcDateStrNowDate();
				else
					time_stamp = new Date();		    
				
						
				if("add".equals(cmd)) {
					proEFFileItem(uploadDir + "images/petef/", app_token, user_id,
							item, wefg, 0);					
				} else if ("update".equals(cmd)) {
					proEFFileItem(uploadDir + "images/petef/", app_token, user_id,
							item, wefg, 1);										
				} else {
					result = Constant.ERR_INVALID_PARA;
				}

				return;

			}
			
			
		}

		
		
		result = Constant.ERR_INVALID_PARA;
	}

	private void proEFFileItem(String uploadDir, String app_token,
			int user_id, FileItem item, WeFencing pefg, int flag) throws SystemException,
			IOException, FileNotFoundException {
		Tools tls = new Tools();	
		
		if ( verifyUserRight(user_id, app_token) != Constant.SUCCESS_CODE )								
			result = Constant.ERR_USER_RIGHT;	        
		else {					
			String fileparaname = item.getName();
			fileparaname = fileparaname.substring(fileparaname.lastIndexOf("/") + 1);
			
			String sufixname = fileparaname.substring(fileparaname.lastIndexOf(".") + 1);
			String filename = getSysAppNewFilename(sufixname);

			if ( writeServFile(uploadDir, item, filename) > 0) 
				pefg.setPhoto(filename);
			else
				pefg.setPhoto(null);
				
			pefg.setPst(time_stamp.getTime());
			updateEfInfo(user_id, pefg, flag);
			json.put("photo", filename);
			json.put("pst", time_stamp.getTime());
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

	


	private int writeServFile(String uploadDir, FileItem item, String filename)
			throws IOException, FileNotFoundException {
		//filename = filename.substring(filename.lastIndexOf("/") + 1);
		InputStream ina = item.getInputStream();
								
		
		FileOutputStream outa = new FileOutputStream(uploadDir + filename);
		byte buffera[] = new byte[1024];
		int res = 0;
		int lena = 0;
		while((lena = ina.read(buffera)) > 0) {
			outa.write(buffera, 0, lena);
			res += lena;
		}
		ina.close();
		outa.close();
		item.delete();
		return res;
	} 

	
	

	
	private String getSysAppNewFilename(String suffixname)
	{
		//time_stamp = new Date();
		String res = new String ("app" + time_stamp.getTime() + "." + suffixname);
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
	
	
	
	private void updateEfInfo(int user_id, WeFencing pef, int flag) throws SystemException {
		AppSafeAreaManFacade infoFacade = ServiceBean.getInstance().getAppSafeAreaManFacade();
		if ( flag == 0 ) {
			infoFacade.insertAppSafeAreaMan(pef);
		} else {
			infoFacade.updateAppSafeAreaMan(pef);			
		}
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
	
			
}




