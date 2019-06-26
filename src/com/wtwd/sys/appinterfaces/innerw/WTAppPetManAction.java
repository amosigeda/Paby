//yonghu create 20160625 label
package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import com.google.gson.Gson;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wfciPetKind.domain.WfciPetKind;
import com.wtwd.sys.innerw.wfciPetKind.domain.logic.WfciPetKindFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.locationinfo.domain.logic.LocationInfoHelper;

public class WTAppPetManAction extends BaseAction{

	Log logger = LogFactory.getLog(WTAppDeviceManAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";
	JSONObject json = null;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		Tools tls = new Tools();		
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}			

			logger.info("WTAppPetManAction request param:" + sb.toString());
			JSONObject object = JSONObject.fromObject(sb.toString());
			String cmd = object.optString("cmd");
			int user_id = object.optInt("user_id");

			super.logAction(String.valueOf(user_id),object.optInt("device_id"), "WTAppPetManAction");
			
			Wpet voWp = null; 
			Gson gson=new Gson();			
			
			if (cmd.equals("query")) {	//获取宠物资料
				voWp = gson.fromJson(sb.toString(), Wpet.class); 
				proReqQueryPet(href, voWp);				
			} else  if (cmd.equals("kindlist")) {	//获取宠物狗种类
				proReqKindlist(href);
			} else { // 增 删 改 		
				logger.info("enter into no query------");
				voWp = gson.fromJson(sb.toString(), Wpet.class);
				WshareInfo shareInfo = new WshareInfo();
				WshareInfoFacade shareInfoFacade = ServiceBean.getInstance().getWshareInfoFacade();				
				//WfciPetKind petKind = new WfciPetKind();
				//WfciPetKindFacade petKindFacade = ServiceBean.getInstance().getWfciPetKindFacade();
				
				//List<DataMap> listPetKind = null;
				//Boolean petKindParaValid = true;
				//检查参数
				if (voWp.getFci_detail_all_catid() == null) {
					voWp.setFci_detail_all_catid("0");
				}
				/*} else {
					//判断fci_detail_all_catid 参数是否合法
					petKind.setOrderBy("fci_group_catid, catid");
					petKind.setCondition("catid='" + voWp.getFci_detail_all_catid() + "'");
					listPetKind = petKindFacade.getData(petKind); // id="getWfciPetKindData"
					if ( listPetKind.size() == 0 )
						petKindParaValid = false;
				}*/
				//if (!petKindParaValid || "1".equals(voWp.getPet_type())) {
				//	result = Constant.ERR_INVALID_PARA;														
				//} else

				if ("1".equals(voWp.getPet_type())) {
					voWp.setFci_detail_all_catid("C000");    //"C000" : Cat
				}
				if ("2".equals(voWp.getPet_type())) {
					voWp.setFci_detail_all_catid("other");
				}
				
				logger.info("Fci_detail_all_catid = " + voWp.getFci_detail_all_catid());
				if (cmd.equals("add2")) {
					//??????  后续修改，需要根据宠物重量获得宠物重量等级id ????????
					if( tls.isNullOrEmpty(voWp.getWeight_level_1_catid()) )
						voWp.setWeight_level_1_catid("Z001");
					proReqAddPet2(href, voWp);
				} else if (cmd.equals("del")) {	//删除宠物资料
					String pet_id = object.optString("pet_id");
					String userid_str = object.optString("user_id");
					if ( ( result = verifyUserHostPet(userid_str, pet_id ) ) == Constant.SUCCESS_CODE )
					{
						proDelPet(voWp);
					}
				} else if (cmd.equals("update")) {	//更新宠物资料
					logger.info("update start");
					int device_id = object.optInt("device_id");
					
					String pet_id = object.optString("pet_id");
					String userid_str = object.optString("user_id");

					super.logAction(String.valueOf(user_id),object.optInt("device_id"), 
							"WTAppPetManAction update pet_type=" + object.optString("pet_type"));			

					if ( ( result = verifyUserPet(userid_str, pet_id ) ) == Constant.SUCCESS_CODE )
					{
						logger.info("result=" + result);
						proReqUpdatePet(href, voWp);				
						if ( result == Constant.SUCCESS_CODE  && device_id > 0) {
							//proUpdateEsafeOnAndTimeZone(device_id, voWp.getEsafe_on(), voWp.getTime_zone());
							proUpdateEsafeOnAndTimeZone(device_id, voWp.getEsafe_on(), null);
						}
						logger.info("update over");
					}
				} else { //添加宠物资料
					
					int device_id = object.optInt("device_id");
					logger.info("device_id:" + device_id);
					if ( device_id < 1 ) {
						if ( cmd.equals("add") ) {
							//??????  后续修改，需要根据宠物重量获得宠物重量等级id ????????
							if( tls.isNullOrEmpty(voWp.getWeight_level_1_catid()) )
								voWp.setWeight_level_1_catid("Z001");
							proReqAddPet2(href, voWp);						
						}
					} else {
						shareInfo.setCondition("a.user_id=" + user_id + " and a.device_id=" 
								+ device_id + " and a.status='1' and a.is_priority = 1");
						List<DataMap> listShareInfo = shareInfoFacade.getData(shareInfo);	// id="getWshareInfo"							
						if (listShareInfo.size() == 0 ) {
							result = Constant.ERR_INVALID_PARA;									
						} else {						
							//??????  后续修改，需要根据宠物重量获得宠物重量等级id ????????
							if( tls.isNullOrEmpty(voWp.getWeight_level_1_catid()) )
								voWp.setWeight_level_1_catid("Z001");					
							if ( cmd.equals("add") ) { //添加宠物资料				
								proReqAddPet(href, voWp);
								if ( result == Constant.SUCCESS_CODE ) {
									//proUpdateEsafeOnAndTimeZone(device_id, voWp.getEsafe_on(), voWp.getTime_zone());
									proUpdateEsafeOnAndTimeZone(device_id, voWp.getEsafe_on(), null);
								}
							} else {
								result = Constant.ERR_INVALID_PARA;									
							}
						}
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
		
		return null;
	}	
		
	//添加宠物资料
	void proReqAddPet(String href, Wpet voWpet) throws SystemException {
		Tools tls = new Tools();		
		
		String name = voWpet.getNickname();
		if  (!tls.isNullOrEmpty(name)) {
			String nickname = name.replaceAll("\r|\n", "");
			voWpet.setNickname(nickname);
		}
		
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();		
		voWpet.setCondition("device_id=" + voWpet.getDevice_id() );
		if ( facade.getDogCount(voWpet) > 0 ) 
			result = Constant.ERR_DEV_PET_HAS_EXIST;
		else {
			if ( facade.insertDog(voWpet) > 0) {
				voWpet.setCondition("a.device_id=" + voWpet.getDevice_id() );
				List<DataMap> list = facade.getDogDataListByDevice(voWpet);
				if (list.size()> 0)
					json.put("pet_id", list.get(0).getAt("pet_id").toString());
			
				result = Constant.SUCCESS_CODE;
			} else
				result = Constant.FAIL_CODE;
			
		}
	}
	
	//更新宠物资料
	public void proReqUpdatePet(String href, Wpet voWpet) throws SystemException {
		Tools tls = new Tools();		
		
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();
		String name = voWpet.getNickname();
		if  (!tls.isNullOrEmpty(name)) {
			String nickname = name.replaceAll("\r|\n", "");
			voWpet.setNickname(nickname);
		}
		voWpet.setCondition("pet_id=" + voWpet.getPet_id() );
		if ( facade.getDogCount(voWpet) == 0 ) 
			result = Constant.FAIL_CODE;
		else {
			if ( facade.updateDog(voWpet) > 0)
				result = Constant.SUCCESS_CODE;
			else
				result = Constant.FAIL_CODE;
		}
	}

	//获取宠物资料
	void proReqQueryPet(String href, Wpet voWpet) throws SystemException {
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();
		voWpet.setCondition("pet_id=" + voWpet.getPet_id() );		
		List<DataMap> list = facade.getDogDataList(voWpet);
		if ( list.size() == 0 ){ 
			result = Constant.FAIL_CODE;
		} else {
			//json.put("pet_obj", list.get(0).toString());
			json.put("pet_id", list.get(0).getAt("pet_id").toString());
			json.put("photo", list.get(0).getAt("photo").toString());
			json.put("nickname", list.get(0).getAt("nickname").toString());
			json.put("born_date", list.get(0).getAt("born_date").toString());
			json.put("sex", list.get(0).getAt("sex").toString());
			json.put("is_healthy", list.get(0).getAt("is_healthy").toString());
			json.put("weight", list.get(0).getAt("weight").toString());
			json.put("fat_level", list.get(0).getAt("fat_level").toString());
			json.put("fci_detail_all_catid", list.get(0).getAt("fci_detail_all_catid").toString());
			json.put("photo_time_stamp", list.get(0).getAt("photo_time_stamp").toString());
			json.put("sheight", list.get(0).getAt("sheight").toString());
			json.put("device_id", list.get(0).getAt("device_id").toString());
			json.put("time_zone", list.get(0).getAt("time_zone").toString());
			json.put("other_desc", list.get(0).getAt("other_desc").toString());
			json.put("device_imei", list.get(0).getAt("device_imei").toString());
									
			result = Constant.SUCCESS_CODE;
		}
	}
	
	//获取宠物狗种类
	void proReqKindlist(String href) throws SystemException {
		WfciPetKind vo = new WfciPetKind();
		WfciPetKindFacade facade = ServiceBean.getInstance().getWfciPetKindFacade();
		List<DataMap> list = facade.getData(vo);
		if ( list.size() == 0 ) 
			result = Constant.FAIL_CODE;
		else {			
			if(list != null && list.size() > 0){
				JSONArray jsonArr = new JSONArray();
				
				for(int i=0;i<list.size();i++){
					DataMap dataMap = list.get(i);
					JSONObject itemObj = JSONObject.fromObject(hashMapToJson(dataMap));			
					jsonArr.add(itemObj);					
				}				
				json.put("kind_list", jsonArr);				
			}					
			//json.put("kind_list", list.toString());
			result = Constant.SUCCESS_CODE;
		}
	}	

	//添加宠物资料(新UI)
	void proReqAddPet2(String href, Wpet voWpet) throws SystemException {
		Tools tls = new Tools();		
	
		String name = voWpet.getNickname();
		if  (!tls.isNullOrEmpty(name)) {
			String nickname = name.replaceAll("\r|\n", "");
			voWpet.setNickname(nickname);
		}
		
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();
		voWpet.setCondition("nickname=\"" + voWpet.getNickname().trim() + 
				"\" and user_id=" + voWpet.getUser_id() );
		if ( facade.getDogCount(voWpet) > 0 ) 
			result = Constant.ERR_PET_NICKNAME_EXIST;
		else {
			if ( tls.isNullOrEmpty(voWpet.getTime_zone())) {
				voWpet.setTime_zone(myTimeZone);
			}
			if ( facade.insertDog(voWpet) > 0) {
				List<DataMap> list = facade.getDogDataList(voWpet);
				if (list != null && list.size() > 0) {					
					json.put("pet_id", list.get(0).getAt("pet_id").toString());			
					result = Constant.SUCCESS_CODE;
				}			
			} else
				result = Constant.FAIL_CODE;		
		}
	}

	//删除宠物
	void proDelPet(Wpet voWpet) throws SystemException {
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();

		voWpet.setCondition("pets_pet_id=" + voWpet.getPet_id().trim()  );
		// 没什么意义，暂注释
		/*if ( facade.delPetInfo(voWpet) < 1 ) {
			result = Constant.FAIL_CODE;
		}*/			
		if ( facade.delPetMoveInfo(voWpet) < 1 ) {
			result = Constant.FAIL_CODE;
		}
		if ( facade.delPetSleepInfo(voWpet) < 1 ) {
			result = Constant.FAIL_CODE;
		}
				
		voWpet.setCondition("pet_id=" + voWpet.getPet_id().trim()  );
		if ( facade.delPet(voWpet) < 1 ) 
			result = Constant.FAIL_CODE;
		else {
			try {
				WshareInfo shareInfo = new WshareInfo();
				shareInfo.setUser_id(String.valueOf(voWpet.getUser_id()));
				shareInfo.setDevice_id(Integer.parseInt(myDeviceId) );
				WTAppDeviceManAction adma = new WTAppDeviceManAction();
				adma.proReqUnbind("inner",shareInfo);
			} catch ( Exception e) {
				
			}		
			result = Constant.SUCCESS_CODE;			
		}
	}
		
	public void proUpdateEsafeOnAndTimeZone(int device_id, String esafe_on, String time_zone) throws SystemException {
		Tools tls = new Tools();				
		
		WdeviceActiveInfo vo = new WdeviceActiveInfo();
		WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
		List<DataMap> list = null;
		vo.setCondition("device_id=" + device_id);
		vo.setEsafe_on(esafe_on);
		vo.setTime_zone(time_zone);

		
		if ( !tls.isNullOrEmpty(esafe_on) ) {
			int res = 0;
			list = fd.getwDeviceExtra(vo);
			if ( list == null )
				throw new SystemException();

			vo.setDevice_id(device_id);
						
			if ( list.size()  > 0) {
				res = fd.updatewDeviceExtra(vo);
					
			} else {
				vo.setEco_mode(Tools.OneString);
				
				res = fd.insertwDeviceExtra(vo);
			}
			if ( res > 0 ) {
				SafePushCtlRunnable ph = new SafePushCtlRunnable(vo);
				ph.run();				
			}
		}
		if (!tls.isNullOrEmpty(time_zone)) {
			fd.updateWdeviceActiveInfo(vo);
		}
		
	}
	
	class SafePushCtlRunnable implements Runnable {  
		private WdeviceActiveInfo dai;
		public SafePushCtlRunnable(WdeviceActiveInfo rDai) {
			dai = rDai;
	    }  	    
	    public void run() {
			Tools tls = new Tools();		
			//WTSigninAction ba = new WTSigninAction();	    	
	    	try {
	    		Integer device_id =  dai.getDevice_id();
				WMsgInfo aMsg = new WMsgInfo();
				JSONObject jobj = new JSONObject();
				jobj.put("flag", dai.getEsafe_on());
				aMsg.setMsg_content(jobj.toString());
				aMsg.setMsg_ind_id(Constant.CST_MSG_IND_ESAFE_FLAG_RES);
				aMsg.setHide_flag(Tools.OneString);
				aMsg.setDevice_id( device_id );
				if ( Constant.timeUtcFlag )
					aMsg.setMsg_date(tls.getUtcDateStrNow() );
				else {											
					aMsg.setMsg_date(getDeviceNow(device_id));
				}
		    	LocationInfoHelper lih = new LocationInfoHelper();
				
				lih.proCommonInnerMsg(aMsg, 0);
	    	} catch(Exception e) {
	    		e.printStackTrace();
				insertVisit(null, null, String.valueOf(dai.getDevice_id()), "exception SafePushDelRunnable exception:");					    		
	    	}	    	
	    }
	}
		
}
