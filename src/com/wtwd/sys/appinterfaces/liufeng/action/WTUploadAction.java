package com.wtwd.sys.appinterfaces.liufeng.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.godoing.rose.lang.DataMap;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.liufeng.util.Common;
import com.wtwd.sys.innerw.liufeng.domain.WSoundInfo;
import com.wtwd.sys.innerw.liufeng.domain.logic.WSoundInfoFacade;

/**
 * APP用户更新唤回声音
 * @author liufeng
 * @date 2016-08-20
 * http://192.168.17.224:8080/wtcell/doWTUpload.do
 */
public class WTUploadAction extends BaseAction {
	
	private Log log = LogFactory.getLog(WTUploadAction.class);
	
	//APP唤回声音管理	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Tools tls = new Tools();
		
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			String cmd = request.getParameter("cmd");
			String app_token = request.getParameter("app_token");
			String user_id = request.getParameter("user_id");
			String pet_id = request.getParameter("pet_id");
			String files = request.getParameter("file");
			
			//验证用户
			if(Common.isValidationUserInfo(user_id, app_token)){
				//验证 pet_id必须是设备的主设备对应的宠物
				WSoundInfoFacade soundFacade = ServiceBean.getInstance().getwSoundInfoFacade();
				WSoundInfo si = new WSoundInfo();
				si.setCondition("ws.user_id = "+user_id+" AND wp.pet_id = "+pet_id);
				List<DataMap> list = soundFacade.queryDevicePetByUserId(si);
				if(list != null && list.size() == 1){
					String fileNames = null;
					DataMap listMap = list.get(0);
					String deviceId = listMap.getAt("device_id").toString();
					//获取上传文件
					String base =	request.getSession().getServletContext().getRealPath("/dev/sound");
					createFolder(base);
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setSizeThreshold(4096);
					ServletFileUpload upload = new ServletFileUpload(factory);
					Long maxFileSize = 524288000l;// 允许上传的最大文件大小(字节)
					upload.setSizeMax(maxFileSize);
					request.setCharacterEncoding("UTF-8");
					List items = upload.parseRequest(request);
					Iterator iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem item = (FileItem) iterator.next();// 源文件
						if (!item.isFormField() && !"".equals(item.getName())) {
							fileNames = item.getName();
							String fileName = item.getName();// 原文件名
							File folder = new File(base);// 文件夹
							File file = new File(folder, fileName);// 新文件
							OutputStream output = new FileOutputStream(file);
							InputStream input = item.getInputStream();
							try {
								byte[] buffer = new byte[1024];
								int i = 0;
								while ((i = input.read(buffer)) != -1) {
									output.write(buffer, 0, i);// 保存文件
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								input.close();
								output.close();
							}
						}
					}
					si.setDevice_id(Integer.parseInt(deviceId));
					si.setCall_file(base+fileNames);
					//si.setUp_time(new Date());
					if ( Constant.timeUtcFlag )		
						si.setUp_time(tls.getUtcDateStrNowDate() );
					else
						si.setUp_time(new Date());		    
					
					
					int res = soundFacade.insertPetSoundInfo(si);
					if(res > 0){
						result = Constant.SUCCESS_CODE;
					}else{
						result = Constant.FAIL_CODE;
					}
				}else{
					result = Constant.FAIL_CODE;
				}
			}else{
				result = Constant.FAIL_CODE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			result = Constant.EXCEPTION_CODE;
		}
		json.put("resultCode", result);
		//json.put("time_stamp", new Date());
		if ( Constant.timeUtcFlag )		
			json.put("time_stamp", tls.getUtcDateStrNowDate() );
		else
			json.put("time_stamp", new Date());		    
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 创建文件夹
	 * @param path
	 */
	private void createFolder(String path) {
		try {
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
