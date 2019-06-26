package com.wtwd.sys.innerw.liufeng.domain.logic;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSON;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.liufeng.dao.WTAppMsgManDao;
import com.wtwd.sys.innerw.liufeng.domain.WMsgInfo;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;

public class WTAppMsgManFacadeImpl implements WTAppMsgManFacade {

	
	private static Integer mcount = 0;
	private WTAppMsgManDao wtAppMsgManDao;
	public void setWtAppMsgManDao(WTAppMsgManDao wtAppMsgManDao) {
		this.wtAppMsgManDao = wtAppMsgManDao;
	}

	public List<DataMap> queryByUserIdMsgInfo(WMsgInfo wi)
			throws DataAccessException {
		return wtAppMsgManDao.queryByUserIdMsgInfo(wi);
	}

	public int queryByUserIdMsgCount(WMsgInfo wm) throws DataAccessException {
		return wtAppMsgManDao.queryByUserIdMsgCount(wm);
	}

	public int updateByUserIdMsgInfo(WMsgInfo wm) throws DataAccessException {
		return wtAppMsgManDao.updateByUserIdMsgInfo(wm);
	}

	public synchronized int insertData(WMsgInfo wm) throws DataAccessException {
		Tools tls = new Tools();
		
		int res = 0;
		/*
		if ( Constant.STAT_SYS_DEBUG && ( (mcount % 5000) == 0)  && mcount > 0) {
			wtAppMsgManDao.resetMsgInfo(wm);			
			mcount++;
		} */

		if ( ( (mcount % 5000) == 0)  && mcount > 0) {
			wtAppMsgManDao.resetMsgInfo2(wm);			
			mcount = 0;
		} else
			mcount++;
		
		
		if ( wm.getOrder_id() == null )
			wm.setOrder_id(0);
		if ( Constant.CST_MSG_IND_APPLY_SHARE == wm.getMsg_ind_id() )
			wm.setOrder_id(100);
			
		
		int msgId = getMsgInfoNextId(wm);
		wm.setPush_status(Tools.ZeroString);
		wm.setMsg_date_utc(tls.getUtcDateStrNow());
		wm.setStatus(Tools.OneString);		//已读		

		
		Log logger = LogFactory.getLog(WTAppMsgManFacadeImpl.class);
		
		Integer badge = 0;
		
		String app_type = wm.getApp_type();		
		if ( Tools.OneString.equals(app_type) && !tls.isNullOrEmpty(wm.getSummary())) {
			//wappusers累加 badge 字段
			badge = wm.getOld_badge() + 1;				
			wm.setBadge(badge);					
		}
		
		res =  wtAppMsgManDao.insertData(wm);
		if ( res > 0 ){
			wm.setMsg_id(msgId);			
			String strMsg = JSON.toJSONString(wm);
			//System.out.println("push msg:"+ strMsg);
			wm.setMsg_id(msgId);
			
			//wm.setMsg_date_utc(Tools.getUtcDateStrNow());
			
			//String app_type = wm.getApp_type();
			
			if ( Tools.OneString.equals(app_type) && !tls.isNullOrEmpty(wm.getSummary())) {
				//wappusers累加 badge 字段
				badge = wm.getOld_badge() + 1;				
				//wm.setBadge(badge);
				
				WappUsers vu = new WappUsers();
				WappUsersFacade fu = ServiceBean.getInstance().getWappUsersFacade();

				
				vu.setCondition("user_id=" + wm.getTo_usrid());
				
				try {
					List<DataMap> list = fu.getWappUsers(vu);
					String lstat = list.get(0).getAt("login_status").toString().trim();
					if ( !Tools.OneString.equals(lstat) )
						return res;
					
				} catch(Exception e) {
					e.printStackTrace();					
				}
				
				vu.setBadge(badge);

				strMsg = JSON.toJSONString(wm);				
				
				try {
					fu.updateWappUsers(vu);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
						
			ServiceBean.getInstance().pushMsg(
					Constant.MQTT_INTSYS_TOPIC1, 
					strMsg.getBytes(Charset.forName("UTF-8")));
					
		}
		return res;
	}

	public int resetMsgInfo(WMsgInfo wm) throws DataAccessException {
				
		return wtAppMsgManDao.resetMsgInfo(wm);
	}

	public int resetMsgInfo2(WMsgInfo wm) throws DataAccessException {
		
		return wtAppMsgManDao.resetMsgInfo2(wm);
	}
	
	
	public int delMsgInfoIdLst(WMsgInfo wm) throws DataAccessException {
		return wtAppMsgManDao.delMsgInfoIdLst(wm);
	}

	public Integer getMsgInfoNextId(WMsgInfo vo)
			throws DataAccessException  {
		return wtAppMsgManDao.getMsgInfoNextId(vo);
		
	}
	
}
