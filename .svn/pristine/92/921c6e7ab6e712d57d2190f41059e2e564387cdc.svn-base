package com.wtwd.sys.saleinfo.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.godoing.rose.http.common.HttpTools;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.http.common.Result;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.Config;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.CommUtils;
import com.wtwd.sys.projectinfo.domain.ProjectInfo;
import com.wtwd.sys.saleinfo.domain.SaleInfo;
import com.wtwd.sys.saleinfo.domain.logic.SaleInfoFacade;
import com.wtwd.sys.saleinfo.form.SaleInfoForm;

public class SaleInfoAction extends BaseAction{
	
	Log logger = LogFactory.getLog(SaleInfoAction.class);
	
	public ActionForward querySaleInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//结果集
		PagePys pys = new PagePys();//页面属性
		DataList list = null; //返回页面List  用logic itrate获取。
		StringBuffer sb = new StringBuffer();//创建字符串容器
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();//加载userApp工厂（取得user字典）
		try {
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 
			String userName = request.getParameter("user");
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			
			/*设置化排序字段*/
            form.setOrderBy("id"); 
            form.setSort("1"); 

            if(userName != null && !"".equals(userName)){
				sb.append("user_name like '%"+userName+"%'");
			}
			if(startTime != null && !"".equals(startTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10) >= '"+startTime+"'");
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("substring(date_time,1,10 <= '"+endTime+"'");
			}
			
			
			request.setAttribute("fNow_date", startTime);
		    request.setAttribute("now_date", endTime);
		    request.setAttribute("userName", userName);
		    
		    
			vo.setCondition(sb.toString());
			
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getSaleInfoListByVo(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* 设置化排序字段 */ 
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 这里为管理页面，所以出错后跳转到系统默认页面 */
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("querySaleInfo");
	}
	
	public ActionForward querySaleAreaInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//结果集
		PagePys pys = new PagePys();//页面属性
		DataList list = null; //返回页面List  用logic itrate获取。
		StringBuffer sb = new StringBuffer();//创建字符串容器
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();//加载userApp工厂（取得user字典）
		try {
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 			
			
			/*设置化排序字段*/
//            form.setOrderBy("id"); 
//            form.setSort("1"); 

           
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getSaleInfoListGroupByProvince(vo);  
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* 设置化排序字段 */ 
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 这里为管理页面，所以出错后跳转到系统默认页面 */
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("querySaleAreaInfo");
	}
	
	public ActionForward queryDayAddInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date start = new Date();
		String href= request.getServletPath();		 
		Result result = new Result();//结果集
		PagePys pys = new PagePys();//页面属性
		DataList list = null; //返回页面List  用logic itrate获取。
		StringBuffer sb = new StringBuffer();//创建字符串容器
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();//加载userApp工厂（取得user字典）
		try {
			SaleInfoForm form = (SaleInfoForm) actionForm;
			SaleInfo vo = new SaleInfo(); 			
			
			String startTime = request.getParameter("startTime");
			String endTime   = request.getParameter("endTime");	
			String belongProject = request.getParameter("belongProject");
			/*设置化排序字段*/
            form.setOrderBy("d.add_time"); 
            form.setSort("1"); 
            StringBuffer buffer = new StringBuffer();
			if(startTime == null && endTime == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String time1 = format.format(new Date());
				String time2 = time1.substring(0,8) + "01";
				
				request.setAttribute("fNow_date", time2);
			    request.setAttribute("now_date", time1);
				sb.append("d.add_time >= '"+time2+"' and d.add_time <= '"+time1+"'");
				
				buffer.append("add_time >= '"+time2+"' and add_time <= '"+time1+"'");
			}
			
			if(startTime != null && !"".equals(startTime)){				
				sb.append("d.add_time >= '"+startTime+"'");
				buffer.append("add_time >='"+startTime+"'");
				
				request.setAttribute("fNow_date", startTime);
			}
			if(endTime != null && !"".equals(endTime)){
				if(sb.length() > 0){
					sb.append(" and ");
					buffer.append(" and ");
				}
				sb.append("d.add_time <= '"+endTime+"'");
				buffer.append("add_time <='"+endTime+"'");
				
				request.setAttribute("now_date", endTime);
			}
			if(belongProject != null && !"".equals(belongProject)){
				if(sb.length() > 0){
					sb.append(" and ");
				}
				sb.append("a.belong_project='"+belongProject+"'");
			}
			ProjectInfo pro = new ProjectInfo();
			List<DataMap> pros = ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(pro);
			request.setAttribute("project", pros);
			vo.setAddTime(buffer.toString());
			vo.setCondition(sb.toString());					    
			request.setAttribute("belongProject", belongProject);
         	BeanUtils.copyProperties(vo,form);			
         	list = info.getAddDayGroupByTime(vo); 
			BeanUtils.copyProperties(pys, form); 
			pys.setCounts(list.getTotalSize());   
			/* 设置化排序字段 */ 
			 
		} catch (Exception e) { 
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE); /* 这里为管理页面，所以出错后跳转到系统默认页面 */
			if (e instanceof SystemException) { /* 对已知异常进行解析 */
				result.setResultCode(((SystemException) e).getErrCode());
				result.setResultType(((SystemException) e).getErrType());
			} else { /* 对未知异常进行解析，并全部定义成未知异常 */
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException"); 
			}
		} finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href); 
		return mapping.findForward("queryDayAddInfo");
	}
	public ActionForward queryProductInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String href= request.getServletPath();
		Date start = new Date();
		Result result = new Result();
		PagePys pys = new PagePys();
		DataList list = null;
		String querySql = "";
		SaleInfoFacade info = ServiceBean.getInstance().getSaleInfoFacade();//加载userApp工厂（取得user字典）
		try{
			SaleInfoForm form=(SaleInfoForm)actionForm;
			String productType = request.getParameter("productType");
			request.setAttribute("productType",productType);
			/*设置初始化排序参数*/
			if (form.getOrderBy() == null) {
                form.setOrderBy("date_time");
                form.setSort("1");
            }
			SaleInfo vo=new SaleInfo();
			BeanUtils.copyProperties(vo,form);
			if(productType != null && !"".equals(productType)){
				if(!"".equals(querySql) && querySql != null){
					querySql += " and ";
				}
				querySql += " product_type like '%" + productType + "%' ";
			}	
			vo.setCondition(querySql);
			list = info.getDataProductInfoListByVo(vo);
			BeanUtils.copyProperties(pys,form);
			pys.setCounts(list.getTotalSize());
			/*设置化排序字段*/
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(Config.ABOUT_PAGE);/*这里为管理页面，所以出错后跳转到系统默认页面*/
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
			request.setAttribute("pageList", list);
			request.setAttribute("PagePys", pys);
		}
		CommUtils.getIntervalTime(start, new Date(), href);
		return mapping.findForward("queryProductInfo");
	}
	
	public ActionForward initInsert(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		return mapping.findForward("insertProductInfo");
	}
	public ActionForward insertProductInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)throws SystemException {
		Result result = new Result();
		StringBuffer webPath = new StringBuffer();
		String interPath = "";
		String iconPath = "";
		try{
			SaleInfoForm form = (SaleInfoForm)actionForm;
			SaleInfo vo = new SaleInfo();
			
			Hashtable<?, ?> files = form.getMultipartRequestHandler().getFileElements();
			if (files != null & files.size() > 0) {
				Enumeration<?> enums = files.keys();
				String fileKey = null;
				String path = "";			
				int i = 0;
				String dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getDate();

				while (enums.hasMoreElements()) {
					// 取得key
					fileKey = (String) (enums.nextElement());
					// 初始化每一个FormFile(接口)
					FormFile file = (FormFile) files.get(fileKey);
					// 分别上传
					String name = file.getFileName();
					if (name != null && !"".equals(name.trim())) {
						String ext = name.substring(name.lastIndexOf(".")+1,name.length());
						String randomName = String.valueOf(UUID.randomUUID());
						
						CommUtils.createDateFile(dir);
						InputStream in = file.getInputStream();
						path =  dir + "/" + randomName +"."+ext;   //输出文件路径
						//下载路径接口
						interPath = "http://" + getServerName() +":"+request.getServerPort()+ request.getContextPath() + "/attachement/" +CommUtils.getDate()+"/" + randomName +"."+ext;
						OutputStream out = new FileOutputStream(path);
						out.write(file.getFileData(), 0, file.getFileSize());
						in.close();
						in = null;
						out.close();
						out = null;
						if(i == 0){
							iconPath = interPath;
						}else if(i == 1){
						    webPath.append(interPath );						
						}else{
							webPath.append(","+interPath);
						}
					}
					i++;
				}
			}
			SaleInfoFacade product = ServiceBean.getInstance().getSaleInfoFacade();

			BeanUtils.copyProperties(vo,form);
			vo.setDateTime(new Date());
			vo.setIcon(iconPath);
			product.insertProductInfo(vo);
			
			int id = product.getMaxProductId(vo);
			String[] webP = webPath.toString().split(",");
			for(int i=0;i<webP.length;i++){
				vo.setProductCode("pro"+id);
				vo.setProductPicture(webP[i]);
				
				product.insertPhotoInfo(vo);
			}
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			result.setResultCode("inserts");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}

	public String getServerName() throws Exception {
		String serverName = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName = pros.getProperty("servername");
		return serverName;
	}
	public ActionForward deleteProductInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		String dir  = "";
		try{
			String idP = request.getParameter("id_p");
			SaleInfo vo = new SaleInfo();
			//valiteDelete(salerCode);
			vo.setCondition("id_p ='"+idP+"'");
			vo.setFrom(0);  
			vo.setPageSize(20);
			List<DataMap> proList = ServiceBean.getInstance().getSaleInfoFacade().getProductInfo(vo);
			String delIcon = ""+proList.get(0).getAt("icon");
			dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getSubStr(delIcon,2);
			File f = new File(dir);
			if(f.exists()){
				f.delete();
			}
			
			ServiceBean.getInstance().getSaleInfoFacade().deleteProductInfo(vo);
			vo.setCondition("product_id ='pro"+idP+"'");
			List<DataMap> pictureList = ServiceBean.getInstance().getSaleInfoFacade().getPhotoInfo(vo);
				
			for(int i=0;i<pictureList.size();i++){
				String delDir = ""+pictureList.get(i).getAt("product_picture");
				dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getSubStr(delDir,2);
				
				File file = new File(dir);
				if (file.exists()) {
					file.delete();
				}
			}
			ServiceBean.getInstance().getSaleInfoFacade().deletePhotoInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			result.setResultCode("deletes");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id=request.getParameter("id_p");
		SaleInfo vo=new SaleInfo();
		vo.setCondition("id_p = '"+id+"'");
//		List<DataMap> list = ServiceBean.getInstance().getProductInfoFacade().getProductInfo(vo);
		List<DataMap> list = ServiceBean.getInstance().getSaleInfoFacade().getProductInfo(vo);
		if (list == null || list.size() == 0){/*当此数据被删除时处理,普通情况下不会发生*/
			Result result = new Result();
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			result.setResultCode("rowDel");
			result.setResultType("success");
			return mapping.findForward("result");
		}
		request.setAttribute("productInfo",list.get(0));
		return mapping.findForward("updateProductInfo");
	}
	
	public ActionForward downloadApk(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception{
		boolean isOnLine = false;   //是否线上查看
		try {
			String downloadUrl = request.getParameter("download");

			//String s = CommUtils.getSubStr(downloadUrl,2);
			String dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getSubStr(downloadUrl,2);
			String apkname = downloadUrl.substring(downloadUrl.lastIndexOf("/")+1);
			
			 File f = new File(dir);		 
		        if (!f.exists()) {
		            throw new SystemException("fail","noIconPath");
		        }
		        FileInputStream fin = new FileInputStream(f);
		        BufferedInputStream br = new BufferedInputStream(fin);
		        byte[] buf = new byte[1024];   //每次1MB传送
		        int len = 0;
		        response.reset();   //重置
		        if (!isOnLine) {    //在线预览
		            URL u = new URL("file:///" + dir);
		            response.setContentType(u.openConnection().getContentType());  //打开连接,设置报头
		            response.setHeader("Content-Disposition", "inline; filename=" + apkname);
		            // 文件名应该编码成UTF-8
		        } else { // 纯下载方式
		            response.setContentType("application/x-msdownload");
		            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		        }
		        OutputStream out = response.getOutputStream();   //输出形式
		        while ((len = br.read(buf)) > 0)    //每次1MB输出
		            out.write(buf, 0, len);
		        br.close();
		        out.close();
		        return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward updateProductInfo(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		Result result = new Result();
		String id = request.getParameter("ids");
		String icon = request.getParameter("icons");
		String interPath = "";
		try{
			//ProductInfoForm form = (ProductInfoForm)actionForm;
			SaleInfoForm form=(SaleInfoForm)actionForm;
			//ProductInfo vo = new ProductInfo();
			SaleInfo vo=new SaleInfo();
			Hashtable<?, ?> files = form.getMultipartRequestHandler().getFileElements();
			if (files != null && files.size() > 0) {
				Enumeration<?> enums = files.keys();
				String fileKey = null;
				String path = "";			
				String dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getDate();
				
				while (enums.hasMoreElements()) {
					// 取得key
					fileKey = (String) (enums.nextElement());
					// 初始化每一个FormFile(接口)
					FormFile file = (FormFile) files.get(fileKey);
					// 分别上传
					String name = file.getFileName();
					if (name != null && !"".equals(name.trim())) {
						String delDir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getSubStr(icon,2);

						File file1 = new File(delDir);
						if (file1.exists()) {
							file1.delete();
						}
						
						String ext = name.substring(name.lastIndexOf(".")+1,name.length());
						String randomName = String.valueOf(UUID.randomUUID());
						
						CommUtils.createDateFile(dir);
						InputStream in = file.getInputStream();
						path =  dir + "/" + randomName +"."+ext;   //输出文件路径
						//下载路径接口
						interPath = "http://" + getServerName() +":"+request.getServerPort()+ request.getContextPath() + "/attachement/" +CommUtils.getDate()+"/" + randomName +"."+ext;
						OutputStream out = new FileOutputStream(path);
						out.write(file.getFileData(), 0, file.getFileSize());
						in.close();
						in = null;
						out.close();
						out = null;
					}
				}
			}
			BeanUtils.copyProperties(vo,form);
			if(interPath.trim().equals("")){
				vo.setIcon(icon);
			}else{
				vo.setIcon(interPath);
			}			
			vo.setCondition("id_p ='"+id+"'");
			//ServiceBean.getInstance().getProductInfoFacade().updateProductInfo(vo);
			ServiceBean.getInstance().getSaleInfoFacade().updateProductInfo(vo);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			result.setResultCode("updates");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
	
	public ActionForward iconLook(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws SystemException{
		String id = request.getParameter("id_p");
		List<DataMap> pictureList = new ArrayList<DataMap>();
		SaleInfo productInfo=new SaleInfo();
		//ProductInfo productInfo = new ProductInfo();
//		productInfo.setCondition("id ='"+id+"'");
//		List<DataMap> productList = ServiceBean.getInstance().getProductInfoFacade().getProductInfo(productInfo);
//		
//		String icon = ""+productList.get(0).getAt("icon");
		
		productInfo.setCondition("product_id ='pro"+id+"'");
		productInfo.setSort("0");
		productInfo.setOrderBy("id_o");
		productInfo.setFrom(0);
		productInfo.setPageSize(20);
//		List<DataMap> photoList = ServiceBean.getInstance().getProductInfoFacade().getPhotoInfo(productInfo);
		List<DataMap> photoList = ServiceBean.getInstance().getSaleInfoFacade().getPhotoInfo(productInfo);
//		String flag = ""+productList.get(0).getAt("product_flag");
		for(int i=0;i<photoList.size();i++){
			DataMap dataMap = new DataMap();
			dataMap.put("id", photoList.get(i).getAt("id_o"));
			dataMap.put("icon",photoList.get(i).getAt("product_picture"));
			pictureList.add(dataMap);
		}
		request.setAttribute("product_id", id);
//		request.setAttribute("flag", flag);
		request.setAttribute("pictureList", pictureList);
		return mapping.findForward("iconLook");
	}
public ActionForward updateIcon(ActionMapping mapping, ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws SystemException{
		
		Result result = new Result();
		String id = request.getParameter("id");
		StringBuffer webPath = new StringBuffer();
		String interPath = "";
		//ProductInfoForm form = (ProductInfoForm)actionForm;
		SaleInfoForm form=(SaleInfoForm)actionForm;
		try{
			Hashtable<?, ?> files = form.getMultipartRequestHandler().getFileElements();
			if (files != null & files.size() > 0) {
				Enumeration<?> enums = files.keys();
				String fileKey = null;
				String path = "";
				int i = 0;
				String dir = request.getSession(true).getServletContext().getRealPath("/attachement")+"/"+CommUtils.getDate();

				while (enums.hasMoreElements()) {
					// 取得key
					fileKey = (String) (enums.nextElement());
					// 初始化每一个FormFile(接口)
					FormFile file = (FormFile) files.get(fileKey);
					// 分别上传
					String name = file.getFileName();
					if (name != null && !"".equals(name.trim())) {
						String ext = name.substring(name.lastIndexOf(".")+1,name.length());
						String randomName = String.valueOf(UUID.randomUUID());
						
						CommUtils.createDateFile(dir);    //不存在则创建文件夹
						InputStream in = file.getInputStream();
						path =  dir + "/" + randomName +"."+ext;   //输出文件路径
						//下载路径接口
						interPath = "http://" + getServerName() +":"+request.getServerPort()+ request.getContextPath() + "/attachement/" +CommUtils.getDate()+"/" + randomName +"."+ext;
						
						OutputStream out = new FileOutputStream(path);
						out.write(file.getFileData(), 0, file.getFileSize());
						in.close();
						in = null;
						out.close();
						out = null;
						if(i == 0){
							webPath.append(interPath);
						}else{
							webPath.append(","+interPath);
						}
					}
					i++;
				}
			}
			//ProductInfoFacade product = ServiceBean.getInstance().getProductInfoFacade();
		
			SaleInfoFacade product=ServiceBean.getInstance().getSaleInfoFacade();
			//ProductInfo vo = new ProductInfo();
           SaleInfo vo=new SaleInfo();
			BeanUtils.copyProperties(vo,form);
			String[] webP = webPath.toString().split(",");
			for(int i=0;i<webP.length;i++){
				vo.setProductCode("pro"+id);
				vo.setProductPicture(webP[i]);
				
				product.insertPhotoInfo(vo);
			}
			
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			result.setResultCode("updateIcon");
			result.setResultType("success");
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(request.getQueryString() + "  " + e);
			result.setBackPage(HttpTools.httpServletPath(request,"queryProductInfo"));
			if(e instanceof SystemException){/*对已知异常进行解析*/
				result.setResultCode(((SystemException)e).getErrCode());
				result.setResultType(((SystemException)e).getErrType());
			}else{/*对未知异常进行解析，并全部定义成未知异常*/
				result.setResultCode("noKnownException");
				result.setResultType("sysRunException");
			}
		}finally {
			request.setAttribute("result", result);
		}
		return mapping.findForward("result");
	}
}
