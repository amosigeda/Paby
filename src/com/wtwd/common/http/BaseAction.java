package com.wtwd.common.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.funcinfo.domain.FuncInfo;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.WdeviceActiveInfo;
import com.wtwd.sys.innerw.wdeviceActiveInfo.domain.logic.WdeviceActiveInfoFacade;
import com.wtwd.sys.innerw.wpet.domain.Wpet;
import com.wtwd.sys.innerw.wpet.domain.logic.WpetFacade;
import com.wtwd.sys.innerw.wshareInfo.domain.WshareInfo;
import com.wtwd.sys.innerw.wshareInfo.domain.logic.WshareInfoFacade;
import com.wtwd.sys.monitorinfo.domain.MonitorInfo;
public abstract class BaseAction extends DispatchAction {

	public int result = Constant.FAIL_CODE;  //0
	private JSONObject ljo = null;
	private JSONObject lrjo = null;
	
    public void setServlet(ActionServlet actionServlet) {

        /*���������ҳ���date�����Զ�ת��*/
        ConvertUtils.register(new SqlDateConverter(null), Date.class);
//        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
//        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
//        ConvertUtils.register(new FloatConverter(null), Float.class);
//        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
//        ConvertUtils.register(new ShortConverter(null), Short.class);
//        ConvertUtils.register(new SqlDateConverter(null), Date.class);
//        ConvertUtils.register(new SqlTimeConverter(null), Time.class);
//        ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
//        runThreads();
    }

    /*���м̳���ִ�з���ʱ���ᾭ��˷���*/
    protected ActionForward dispatchMethod(ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           String name) throws Exception {
//        Date dt = DateFormatTools.getDateByStr("2014-07-10 22:00:00");
//        if(System.currentTimeMillis() > dt.getTime()){
//            Result result = new Result();
//            result.setBackPage(Config.INDEX_PAGE);
//            result.setResultCode("timeout");
//            result.setResultType("fail");
//            request.setAttribute("result", null);
//            return mapping.findForward("result");
//        }
//        if(isTimeOut()){
//            LoginUser user = (LoginUser) request.getSession().getAttribute(
//                Config.SystemConfig.LOGINUSER);
//            if (user == null) { /*�ж��Ƿ�ʱ*/
//                Result result = new Result();
//                result.setBackPage(Config.INDEX_PAGE);
//                result.setResultCode("timeout");
//                result.setResultType("fail");
//                request.setAttribute("result", null);
//                return mapping.findForward("result");
//            }
//        }
//        this.myUserName = user.getUserName();
//        this.myUserCode = user.getUserCode();
//        this.myUserId = user.getUserId();
//        String lgs = getLogs();
//        if(lgs == null) lgs = "ϵͳ����";
//        if(name != null){
//            if(name.indexOf("query") > -1)lgs = lgs + "(��ѯ����)";
//            if(name.indexOf("insert") > -1)lgs = lgs + "(��������)";
//            if(name.indexOf("update") > -1)lgs = lgs + "(���²���)";
//            if(name.indexOf("delete") > -1)lgs = lgs + "(ɾ�����)";
//        }
//        LogsInfo l = new LogsInfo();
//        l.setByDate(new Date());
//        l.setUserId(this.myUserId);
//        l.setUserName(this.myUserName);
//        l.setDescs(lgs);
//        getLogsInfoFacade().insertLogsInfo(l);
//        response.setContentType("text/html; charset=gb2312");
//        request.setCharacterEncoding("gb2312");
//        response.setContentType("text/html; charset=UTF8");
//        request.setCharacterEncoding("UTF8");
        return super.dispatchMethod(mapping, form, request, response, name);
    }

    //0表示访问开关,1表示监听开关
    private String getSwitchTag(int type) throws SystemException{
    	List<DataMap> swi = ServiceBean.getInstance().getMonitorInfoFacade().getSwitchInfo(new MonitorInfo());
    	String tag = "visit_s";   //默认值
    	switch (type) {
		case 0:
			tag = "visit_s";
			break;
		case 1:
			tag = "device_s";
			break;
        case 2:
        	tag = "moni_s";
			break;
		default:
			break;
		}
    	return ""+swi.get(0).getAt(tag);
    }
    
    public void insertVisit(String href,String belongProject,String user_id,int type,Date start,Date end) throws SystemException{
    	Tools tls = new Tools();
    	String tags = getSwitchTag(type);
    	
    	if(tags.equals("1")){  //1表示开启
    		String function = "";
    		FuncInfo vo = new FuncInfo();
    		vo.setCondition("funcDo = '" + href + "'");
    		List<DataMap> list = ServiceBean.getInstance().getFuncInfoFacade().getFuncInfo(vo);
    		if(list.size() > 0){
    			function = (String)list.get(0).getAt("funcName");
    		}
    		
    		MonitorInfo monitorInfo = new MonitorInfo();
    		//monitorInfo.setStartTime(new Date());
			if ( Constant.timeUtcFlag )
				monitorInfo.setStartTime(tls.getUtcDateStrNowDate() );
			else
	    		monitorInfo.setStartTime(new Date());
				
    		
    		Long startTime = start.getTime();
			Long endTime = end.getTime();
			Long between = endTime - startTime;
			int costTime = between.intValue();
			
    		monitorInfo.setFunctionHref(href);
    		monitorInfo.setBelongProject(belongProject);
    		monitorInfo.setPhone(user_id);
    		monitorInfo.setFunction(function);
    		monitorInfo.setStartTime(start);
    		monitorInfo.setEndTime(end);
    		monitorInfo.setCostTime(costTime);
    		monitorInfo.setType(String.valueOf(type));
    		try {
    			ServiceBean.getInstance().getMonitorInfoFacade().insertVisitInfo(monitorInfo);
    		} catch (SystemException e) {
    			e.printStackTrace();
    		}   //工具
    	}
    	
    }

    public int verifyUserId(String user_id) throws SystemException{
  		
    	WappUsers vo = new WappUsers();
    	WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
    	vo.setCondition("user_id="+user_id.trim() );
    	List<DataMap> list = fd.getWappUsers(vo);
    	if (list.size() == 1 ) {
    		return Constant.SUCCESS_CODE;
    	}
    	else
    		return Constant.ERR_APPUSER_NOT_EXIST;
    	
    }

    //检验设备是否为用户的主设备或者分享设备逻辑不对
    public int verifyUserDevice(String user_id, String device_id) throws SystemException{  		
    	WshareInfo vo = new WshareInfo();
    	WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
    	vo.setCondition("a.user_id="+user_id.trim() + " and a.device_id=" + device_id.trim() + 
    			" and a.status='1'" );
    	List<DataMap> list = fd.getData(vo);
    	if (list.size() > 0 )
    		return Constant.SUCCESS_CODE;
    	else
    		return Constant.ERR_DEV_OF_USER;
    	
    }

    //检验宠物是否为用户的主设备或者分享设备对应的宠物
    public int verifyUserPet(String user_id, String pet_id) throws SystemException{  		
    	WshareInfo vo = new WshareInfo();
    	WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
    	vo.setCondition("a.user_id="+user_id.trim() + " and b.pet_id=" + pet_id.trim() + 
    			" and a.status='1'" );
    	List<DataMap> list = fd.getData(vo);
    	if (list.size() > 0 ) {
    		myDeviceId = list.get(0).getAt("device_id").toString().trim(); 
    		return Constant.SUCCESS_CODE;
    	}
    	Wpet voWpet = new Wpet();
		WpetFacade facade = ServiceBean.getInstance().getWpetFacade();
		voWpet.setCondition("user_id=" + user_id );
		if ( facade.getDogCount(voWpet) > 0 )
    		return Constant.SUCCESS_CODE;
		else
    		return Constant.ERR_DEV_OF_USER;
    	
    }

    //检验宠物是否为用户的主设备或者分享设备对应的宠物
    public int verifyUserHostPet(String user_id, String pet_id) throws SystemException{  		
    	//WshareInfo vo = new WshareInfo();
    	//WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
    	Wpet vo = new Wpet();
    	WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
    	vo.setCondition("a.user_id="+user_id.trim() + " and a.pet_id=" + pet_id.trim()  );
    	List<DataMap> list = fd.getDogDataList(vo);
    	if (list.size() > 0 ) {
    		myDeviceId = list.get(0).getAt("device_id").toString().trim();
    		return Constant.SUCCESS_CODE;
    	}
    	else
    		return Constant.ERR_DEV_OF_USER;
    	
    }

    //检验设备是否为用户的主设备
    public int verifyUserHostDevice(String user_id, String device_id) throws SystemException{
    			
    	
    	WshareInfo vo = new WshareInfo();
    	WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();
    	vo.setCondition("a.user_id="+user_id.trim() + " and a.device_id=" + device_id.trim() + 
    			" and a.is_priority = '1' and a.status='1'" );
    	List<DataMap> list = fd.getData(vo);
    	if (list.size() == 1 )
    		return Constant.SUCCESS_CODE;
    	else
    		return Constant.ERR_HOST_DEV_OF_USER;
    	
    }
       
    public int verifyAppToken(String user_id,String app_token) throws SystemException{
   		
    	myUserId = Integer.parseInt(user_id);

		synchronized(EndServlet.initFlag) {
			if ( EndServlet.initFlag == false ) {
	    		return Constant.ERR_SYSTEM_BUSY;
			}
		}
    	
    	WappUsers vo = new WappUsers();
    	WappUsersFacade fd = ServiceBean.getInstance().getWappUsersFacade();
    	vo.setCondition("status = 1 and user_id="+user_id + " and app_token='" + app_token.trim() + "'");
    	List<DataMap> list = fd.getWappUsers(vo);
    	if (list.size() == 1 )
    	{
    		myTimeZone = list.get(0).getAt("time_zone").toString().trim();
    		Tools tls = new Tools();
			String login_stat = list.get(0).getAt("login_status").toString().trim();
			if ( tls.isNullOrEmpty(login_stat) || Tools.ZeroString.equals(login_stat) ) {
				vo.setLogin_status(Tools.OneString);
				fd.updateWappUsers(vo);									
			}			
    		
    		return Constant.SUCCESS_CODE;
    	}
    	else
    		return Constant.ERR_INVALID_TOKEN;
    	
    }
    
    
    public void getMessageFrom(JSONObject json,String user_id,String belongProject){}
    public String getLogs(){return null;}

    protected String myUserName;
    protected String myUserCode;
    protected Integer myUserId;
    protected String myTimeZone;
    protected String myDeviceId;
    protected String belongProject;
 
    protected boolean isTimeOut(){
        return true;
    }
    
    protected String getFieldValueFromJSONSafe(String fieldName, JSONObject obj)
    {
    	return obj.has(fieldName)?obj.getString(fieldName):"";
    }

    public String hashMapToJson(DataMap map) {  
    	
    	StringBuilder str = new StringBuilder();
        str.append( "{" );  
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {  
            Entry e = (Entry) it.next();  
            str.append( "\"" + e.getKey() + "\":" );  
            str.append( "\"" + e.getValue() + "\"," );  
        }  
        String res = str.toString().substring(0, str.toString().lastIndexOf(","));  
        res += "}";  
        return res.replace("\n", "<br>"); 
    }     
    
    public String getDeviceImeiFromDeviceId(String devId) throws SystemException {
    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
    	vo.setCondition("device_id=" + devId.trim());
    	List<DataMap> list = fd.getData(vo);
    	if (list.size() != 1 )
    		throw new SystemException("invalid device id");
    	belongProject = list.get(0).getAt("belong_project").toString().trim();
    	
    	return list.get(0).getAt("device_imei").toString().trim();
    	
    }

    public String getPetIdFromDeviceId(String devId) throws SystemException {
    	Wpet vo = new Wpet();
    	WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
    	vo.setCondition("a.device_id=" + devId.trim());
    	List<DataMap> list = fd.getDogDataList(vo);
    	if (list!=null && !list.isEmpty() ) {
    		return list.get(0).getAt("pet_id").toString().trim();
    	} else {
    		return "-1";
    	}
    }   

    public String getDeviceIdFromPetId(String petId) {
    	try {
			Wpet vo = new Wpet();
			WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
			vo.setCondition("a.pet_id=" + petId.trim());
			List<DataMap> list = fd.getDogDataList(vo);
			if (list!=null && !list.isEmpty() ) {
				if ("null".equals(list.get(0).getAt("device_id").toString().trim() ) )
						return "0";
				else
					return list.get(0).getAt("device_id").toString().trim();
			} else {
				return "0";
			}
    	} catch (Exception e) {
    		return "0";
    	}
    }
        
	public String getPropFromFile(String pFile, String pKey) throws Exception {
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream(pFile));
		return pros.getProperty(pKey);
	}

	protected String getVerifyCode()
	{
		Random random = new Random();
		StringBuffer randNumber = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			randNumber.append(rand);
		}
		return randNumber.toString();
	}

    public String getPetSexFromDeviceId(Integer devId) {
    	String res = "";
    	try {
	    	Wpet vo = new Wpet();
	    	WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
	    	vo.setCondition("a.device_id=" + devId);
	    	List<DataMap> list = fd.getDogDataList(vo);
	    	if (list!=null && !list.isEmpty() ) {
	    		vo = null;
	    		return list.get(0).getAt("sex").toString().trim();
	    	} else {
	    		vo = null;
	    		return res;
	    	}
    	} catch (Exception e) {
    		return res;
    	}
    }
		
    public String getPetNickFromDeviceId(Integer devId) {
    	String res = "";
    	try {
	    	Wpet vo = new Wpet();
	    	WpetFacade fd = ServiceBean.getInstance().getWpetFacade();
	    	vo.setCondition("a.device_id=" + devId);
	    	List<DataMap> list = fd.getDogDataList(vo);
	    	if (list!=null && !list.isEmpty() ) {
	    		vo = null;
	    		return list.get(0).getAt("nickname").toString().trim();
	    	} else {
	    		vo = null;
	    		return res;
	    	}
    	} catch (Exception e) {
    		return res;
    	}
    }

    public String getSSidWifiFromDeviceId(Integer device_id) {
    	String res = "";
    	try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_id=" + device_id );
	    	List<DataMap> list = fd.getwDeviceExtra(vo);
	    	if (list.size() != 1 )
	    		return res;
    		return list.get(0).getAt("ssid_wifi").toString().trim();
    	} catch( Exception e ) {
    		return res;
    	}
    	
    	
    }
    	
    public Integer getDeviceIdFromDeviceImei(String devImei){
    	try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_imei='" + devImei.trim() + "'");
	    	List<DataMap> list = fd.getData(vo);
	    	if (list.size() != 1 )
	    		return null;	//throw new SystemException("invalid device imei");
	    	else
	        	return (Integer )list.get(0).getAt("device_id");
	    		
    	} catch(Exception e) {
    		return null;
    	}
    	//return (Integer )list.get(0).getAt("device_id");
    	
    }

    
    public Integer getDevStatus(String device_id) throws SystemException {
    	return getDevStatus(Integer.parseInt(device_id));
    }
       
    public Integer getDevStatus(Integer device_id) throws SystemException {
		Tools tls = new Tools();	
		
    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
    	vo.setCondition("device_id=" + device_id);
    	List<DataMap> list = fd.getwDeviceExtra(vo);
    	if (list.size() < 1 )
    		return 0;
    	
    	String dev_status = list.get(0).getAt("dev_status").toString().trim();
    	if ( tls.isNullOrEmpty(dev_status ) )
    		return 0;
    	else if ( "1".equals(dev_status) ) 
    		return 1;
    	else 
    		return 0;
    	/// getwDeviceExtra
    }
    
    public void insertVisit(String function,String href,Date start,Date end) throws SystemException{
    		Tools tls = new Tools();	
		
    		MonitorInfo monitorInfo = new MonitorInfo();
//    		monitorInfo.setStartTime(new Date());
    		//monitorInfo.setStartTime(new Date());
			if ( Constant.timeUtcFlag )
				monitorInfo.setStartTime(tls.getUtcDateStrNowDate() );
			else
	    		monitorInfo.setStartTime(new Date());
    		
    		Long startTime = start.getTime();
    		Integer costTime = null;
    		Long endTime = null;
    		Long between = null;
    		if ( !tls.isNullOrEmpty(end)) {
				endTime = end.getTime();
				between = endTime - startTime;
				costTime = between.intValue();
    		}
			
    		monitorInfo.setFunctionHref(href);
    		monitorInfo.setFunction(function);
    		monitorInfo.setStartTime(start);
//    		monitorInfo.setServerTime(new Date());
    		//monitorInfo.setStartTime(new Date());
			if ( Constant.timeUtcFlag )
				monitorInfo.setStartTime(tls.getUtcDateStrNowDate() );
			else
	    		monitorInfo.setStartTime(new Date());
    		
    		if ( !tls.isNullOrEmpty(end)) {    		
	    		monitorInfo.setEndTime(end);
	    		monitorInfo.setCostTime(costTime);
    		}
    		try {
    			ServiceBean.getInstance().getMonitorInfoFacade().insertMonitorInfo(monitorInfo);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}   //工具
    	
    }

    public Date getDeviceNowDateFromImei(String device_imei) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_imei=" + device_imei );
	    	List<DataMap> list = fd.getData(vo);
			
			TimeZone timeZoneNY = TimeZone.getTimeZone(list.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
			dateFormat.setTimeZone(timeZoneNY);
			Date date = new Date(System.currentTimeMillis());
			return date;
		} catch (Exception e) {
    		return new Date();			
		}
    }    
       
    public String getDeviceNow(String device_id) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_id=" + device_id );
	    	List<DataMap> list = fd.getData(vo);
			
			TimeZone timeZoneNY = TimeZone.getTimeZone(list.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
			dateFormat.setTimeZone(timeZoneNY);
			Date date = new Date(System.currentTimeMillis());
			return dateFormat.format(date);
		} catch (Exception e) {
    		return dateFormat.format( new Date() );			
		}
    }    
    
    public String getDeviceNow(Integer device_id) {
    	return getDeviceNow(String.valueOf(device_id));
    }

    //abcde
    public String getDeviceNowFromPetId(String pet_id) {
		Tools tls = new Tools();	    	
		// final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
	    	Wpet voWpet = new Wpet();
			WpetFacade facade = ServiceBean.getInstance().getWpetFacade();
			voWpet.setCondition("pet_id=" + pet_id );
	    	List<DataMap> list = facade.getDogDataList(voWpet);
	    	String device_id = list.get(0).getAt("device_id").toString().trim();
	    	
	    	if ( tls.isNullOrEmpty(device_id)  )
	    		return "";
			
			return getDeviceNow(device_id);

		} catch (Exception e) {
    		return "";			
		}
    }
   
    public Date getDeviceNowDate(String device_id) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date dateRes = null;
    	try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_id=" + device_id );
	    	List<DataMap> list = fd.getData(vo);
			
			TimeZone timeZoneNY = TimeZone.getTimeZone(list.get(0).getAt("time_zone").toString());     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
			dateFormat.setTimeZone(timeZoneNY);
			Date date = new Date(System.currentTimeMillis());
			String dTemp = dateFormat.format(date);   
			
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateRes = sdf.parse(dTemp);
    	} catch (Exception e) {
    		return new Date();
    	}
		return dateRes;
	
    }
    

    public String getDeviceTimeZone(String device_id) {
    	try {
	    	WdeviceActiveInfo vo = new WdeviceActiveInfo();
	    	WdeviceActiveInfoFacade fd = ServiceBean.getInstance().getWdeviceActiveInfoFacade();
	    	vo.setCondition("device_id=" + device_id );
	    	List<DataMap> list = fd.getData(vo);
			
			return list.get(0).getAt("time_zone").toString();     //America/Los_Angeles   GMT-8 ;  America/New_York GMT-4
			//TimeZone timeZoneNY = TimeZone.getTimeZone("UTC-4"); //"GMT-4" "UTC-4"
    	} catch (Exception e) {
    		return "";
    	}
	
    }
    
    
    public void insertVisit(String user_id, String device_imei, String device_id, String href) 	{
		Tools tls = new Tools();		
    	
    	if ( Constant.STAT_SYS_DEBUG_FULL) {  // dead code
			MonitorInfo monitorInfo = new MonitorInfo();
	
			try {
		    	if ( !tls.isNullOrEmpty(device_imei) )
		    		device_id = String.valueOf(getDeviceIdFromDeviceImei(device_imei));

				
		    	//if ( !"80".equals(device_id ) ) 
		    	//	return ;
		    		
		    	
				if ( !tls.isNullOrEmpty(device_id) && Integer.parseInt(device_id) > 0 ) 
				{
					//monitorInfo.setStartTime(getDeviceNowDate(device_id));
					if ( Constant.timeUtcFlag )					
						monitorInfo.setStartTime(tls.getUtcDateStrNowDate());
					else
						monitorInfo.setStartTime(getDeviceNowDate(device_id));						
				} else {
					if ( Constant.timeUtcFlag )					
						monitorInfo.setStartTime(tls.getUtcDateStrNowDate());
					else
						monitorInfo.setStartTime(getDeviceNowDate(device_id));						
				}

				monitorInfo.setUser_id(user_id);
				
	    		//monitorInfo.setServerTime(new Date());
				if ( Constant.timeUtcFlag )
					monitorInfo.setServerTime(tls.getUtcDateStrNowDate() );
				else
		    		monitorInfo.setServerTime(new Date());
				
				if ( href.length() > 640 )		
					monitorInfo.setFunctionHref(device_imei + ":"+ device_id + ":" + href.substring(0, 640));
				else
					monitorInfo.setFunctionHref(device_imei + ":"+ device_id + ":" + href);
					
				monitorInfo.setFunction("minaMsg");
				monitorInfo.setDevice_id(device_id);
				ServiceBean.getInstance().getMonitorInfoFacade().insertMonitorInfo(monitorInfo);
			} catch (Exception e) {
				e.printStackTrace();
				//monitorInfo.setStartTime(new Date());
				if ( Constant.timeUtcFlag )					
					monitorInfo.setStartTime(tls.getUtcDateStrNowDate());
				else
					monitorInfo.setStartTime(getDeviceNowDate(device_id));						
				
				monitorInfo.setFunctionHref("logError");
				monitorInfo.setFunction("minaMsg");
				monitorInfo.setDevice_id(device_id);
				monitorInfo.setUser_id(user_id);

				//monitorInfo.setServerTime(new Date());
				if ( Constant.timeUtcFlag )					
					monitorInfo.setServerTime(tls.getUtcDateStrNowDate());
				else
					monitorInfo.setServerTime(getDeviceNowDate(device_id));						
				
				try {			
				ServiceBean.getInstance().getMonitorInfoFacade().insertMonitorInfo(monitorInfo);
				} catch (Exception e1) {
					
				}
	
			}  
		
    	}
	
    }

    public void insertWMonitor(String user_id, String device_imei, String device_id, String href) 	{
    	
    	/*if ( Constant.STAT_SYS_DEBUG_FULL)*/ {
    		Tools tls = new Tools();	    		
			MonitorInfo monitorInfo = new MonitorInfo();
	
			try {
		    	if ( !tls.isNullOrEmpty(device_imei) )
		    		device_id = String.valueOf(getDeviceIdFromDeviceImei(device_imei));

				
		    	//if ( !"80".equals(device_id ) ) 
		    	//	return ;
		    		
		    	
				monitorInfo.setStartTime(tls.getUtcDateStrNowDate());

				monitorInfo.setUser_id(user_id);
				
	    		//monitorInfo.setServerTime(new Date());
				if ( Constant.timeUtcFlag )
					monitorInfo.setServerTime(tls.getUtcDateStrNowDate() );
				else
		    		monitorInfo.setServerTime(new Date());
				
				if ( href.length() > 840 )		
					monitorInfo.setFunctionHref(device_imei + ":"+ device_id + ":" + href.substring(0, 840));
				else
					monitorInfo.setFunctionHref(device_imei + ":"+ device_id + ":" + href);
					
				monitorInfo.setFunction("minaMsg");
				monitorInfo.setDevice_id(device_id);
				ServiceBean.getInstance().getMonitorInfoFacade().insertWMonitor(monitorInfo);
			} catch (Exception e) {
				e.printStackTrace();	
			}  
		
    	}
	
    }
        

	public void logAction(String user_id, Integer device_id, String str) throws SystemException {
		try {
			
			//insertVisit( catgory, str, null, null );
			insertVisit(user_id, null, String.valueOf(device_id), user_id + ":" + str);			
		} catch (Exception e) {
			
		}
	}
    
	
	public String getHostTimeZoneByDeviceId(Integer device_id) {
		Tools tls = new Tools();			
		try {
			
			String res = null;
			WshareInfo vo = new WshareInfo();						
			WshareInfoFacade fd = ServiceBean.getInstance().getWshareInfoFacade();

			vo.setCondition("a.is_priority = 1 and a.status=1 and a.device_id=" + device_id );
			List<DataMap> list = fd.getData(vo);
			
	    	if (list != null && list.size() == 1 )
	    	{
	    		res = list.get(0).getAt("time_zone").toString().trim();
	    		if (tls.isNullOrEmpty(res) )
	    			res = null;
	    	} 	    	
	    	return res;
		} catch(Exception e) {
			return null;
		}
	}

	public JSONObject getLjo() {
		return ljo;
	}

	public void setLjo(JSONObject ljo) {
		this.ljo = ljo;
	}

	public JSONObject getLrjo() {
		return lrjo;
	}

	public void setLrjo(JSONObject lrjo) {
		this.lrjo = lrjo;
	}
}


