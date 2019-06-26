package com.wtwd.sys.appinterfaces.newpay;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import test.com.wtwd.wtpet.service.AiShiDeIccidApi;
import test.com.wtwd.wtpet.service.AiShiDeIccidApiV2;
import test.com.wtwd.wtpet.service.ShuMiIccidApi;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.preview.wireless.Sim;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

//获取输入
//检查iccd
//iccid不合法，返回错误
//获取imsi及belong company信息等
//stripe 支付
//if 支付失败，返回错误
//新建支付记录文件，文件存放位置 ComPay.DIR_HOST_PAY_REC,文件名为日期时间
//+ "_wticcid_" + preceiptNumber + "_" + piccid, 文件内容为piccid
//插入支付记录
//if 插入支付记录失败，记录此支付记录失败到文件ComPay.DIR_HOST_PAY_REC, 
//		文件名为：日期时间 + "_fticcid_" + preceiptNumber + "_" + piccid,，
//      文件内容为 piccid
//      成功后新建线程发送邮件到管理员通知插入支付记录失败事件，继续执行
// 查询卡状态，如果没有激活, 激活SIM卡api调用
//更新卡到期信息
//删除支付记录文件，返回成功



//舍弃掉的逻辑
//查询卡是否为激活状态 rs_stat 1: active,  0: not active.
//if rs_stat=1, 更新卡到期信息，否则，跳转到 <<1>>
//删除支付记录文件，返回成功	
//<<1>> if rs_stat=0, 激活SIM卡api调用, 更新卡到期信息

public class payforIccidAction extends PayBase {

	public Log logger = LogFactory.getLog(payforIccidAction.class);
	final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
	final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";

	private static final int coin = 500;
	
	public enum FILEEnum { FILE_PAYTRAN, FILE_DBTRAN }
	
	private String pptranFileName = null;
	private Integer pstatus = null;
	private String pmessage = "";
	private String pcustomerId = "";
	private String psubscriptionId = "";
	private int preceive;
	private String pplan = null;
	
	private String piccid = null;
	public String getPiccid() {
		return piccid;
	}


	public void setPiccid(String piccid) {
		this.piccid = piccid;
	}


	private String ptoken = null;
	private String pfrequency = null;// 表示月的个数
	private String pcount = null;

	private String pcardName = null;// 信用卡名字
	private String pzipCode = null;// 邮编

	private String puserId = null;
	private String puserToken = null;
	private String pemail = null;
	private String pimsi = null;

	private String pbelongCompany = null;
	private String preceiptNumber = null;
	
	
	public static void proTest2() {
		try {
			// 1表示爱施德
			payforIccidAction fa = new payforIccidAction(); 
			String Status = AiShiDeIccidApi.getStatus(
					"893107051704919153", "204047914919153");
			fa.logger.info("893107051704919153" + "此ICCID卡的状态==" + Status);
			if ("Active".equals(Status)) {
				//Suspend
				AiShiDeIccidApiV2.setIccidStatus("893107051704919153",
						"204047914919153", "Suspend");				
			} else if ("Suspended".equals(Status)) {
				AiShiDeIccidApiV2.setIccidStatus("893107051704919153",
						"204047914919153", "Resume");
			} else if ("Preactive".equals(Status)) {
				AiShiDeIccidApiV2.setIccidStatus("893107051704919153",
						"204047914919153", "Activate");
			}
			fa.logger.info("893107051704919153" + "此ICCID卡的状态==" + Status);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//abcd
	public static void proTest1() throws Exception {
		payforIccidAction va = new payforIccidAction();
		
		JSONObject jo = new JSONObject();
		jo.put("iccid", "893107051704919153");
		jo.put("imei", "352138064955679");  // 352138064985734
		va.setLjo(jo);
		va.proMaster();		
	}
	
	
	void proMaster() throws Exception {
		Tools tls = null;
		
		if (getLrjo() == null)
			setLrjo(new JSONObject());
		if (getLjo() == null)
			setLjo(new JSONObject());		

		try {	
			tls = new Tools();
			List<DataMap> list = null;
			getInput();
			if ( !tls.isNullOrEmpty(piccid)  
					) 
				list = checkIccid(piccid);
			if (list == null) {				
				getLrjo().put(Constant.RESULTCODE, Constant.EXCEPTION_CODE);
				logInInfo("iccid为空,或者iccid不合法");
				getLrjo().put(ComPay.TXT_MSG,ComPay.TXT_EMPTY_ICCID + ",or is wrong!");				
				getLrjo().put("card_status", "iccid为空，或者未找到对应的iccid");				
			} else {
				
				pimsi = list.get(0).getAt("imsi") + "";				
				
				logInInfo("stripe pay begin----------");				
				payStripe();
				if (result != Constant.SUCCESS_CODE) return;
				logInInfo("stripe pay end------------");

				logInInfo("rec pay File begin------------");				
				recPayFile();		//写入支付记录文件，
									//如果后续操作顺利进行，此文件将被删除								
				updatePayDb();		//插入支付记录
				if ( result != Constant.SUCCESS_CODE ) {				
					recPayDbFile();
					emailNotify();
				}
				
				logInInfo("rec pay File end----------");												
				
				logInInfo("aishide sim activate begin------------");
				simActivate();
				if (result != Constant.SUCCESS_CODE) return;
				logInInfo("aishide sim activate end, clrPayFile begin------------");
				clrPayFile();
				logInInfo("clrPayFile end------------");
				
			}
			
		} catch(Exception e) {
			logInInfo( "payforIccidAction proMaster Exception");			
			throw e;
			
		} finally {
			tls = null;
		}
		
	}
	
	void emailNotify() {
		
	}
	
	void updatePayDb() {
		Tools tls = null;
		DeviceActiveInfo vo = null;
		try {
			tls = new Tools();
			vo = new DeviceActiveInfo();
			vo.setDeviceImei(piccid);
			vo.setCreateTime(tls.getUtcDateStrNow());	//sf.format(new Date()));
			vo.setReceive(preceive);
			vo.setCardStatus(pstatus + "");// 200
			vo.setMessage(pmessage);
			vo.setCurrency(ComPay.TXT_USD);
			vo.setDescription(pfrequency);
			vo.setSource(ptoken);
			vo.setFriendlyName(ComPay.TXT_FRIENDLY_NAME);// 全部默认为这个
			vo.setBrandName(pcustomerId);
			vo.setEmail(pemail);
			vo.setSubscriptionId(psubscriptionId);
			vo.setRatePlan(pcount);
			vo.setCardName(pcardName);
			vo.setZipCode(pzipCode);
			vo.setReceiptNumber(preceiptNumber);
			vo.setIccid(piccid);
			// }
			vo.setSubStatus(Tools.OneString);
			vo.setBelongCompany(pbelongCompany);

			int insertCount = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().insertPayForInfo(vo);
			
			result = (insertCount > 0 ) ? Constant.SUCCESS_CODE : Constant.FAIL_CODE;			
			
		} catch(Exception e) {
			
		} finally {
			tls = null;
			vo = null;
		}
	}
	
	String getFileCom(FILEEnum popt) {
		Tools tls = new Tools();
		String midfixt = (popt == FILEEnum.FILE_PAYTRAN) ?  
				ComPay.TXT_LOGFILENAME_MIDFIXWT :
				ComPay.TXT_LOGFILENAME_MIDFIXFT;
		//TXT_OS_WINDOWS_7
		String sdir = (System.getProperty("os.name").
				 equals(ComPay.TXT_OS_WINDOWS_7)) ? 
						ComPay.DIR_PAY_REC :
						ComPay.DIR_HOST_PAY_REC;												

		StringBuffer fname = new StringBuffer(); 
		fname.append(sdir)
			.append(tls.getStringFromDate_nmc(new Date()))
			.append(midfixt)
			.append(preceiptNumber)
			.append("_")
			.append(piccid);					
				/*	
				sdir + 
				tls.getStringFromDate_nmc(new Date()) +
				midfixt +
				preceiptNumber +
				"_" + piccid;
				*/
		tls = null;
		return fname.toString();
		
	}

	void recFileCom(FILEEnum popt) {
		String fname = null;

		String stran = (popt == FILEEnum.FILE_PAYTRAN) ?  
				ComPay.TXT_REC_PAY_TRAN_FILE :
				ComPay.TXT_REC_PAY_DB_FILE;
		
		preceiptNumber = (preceiptNumber == null) ? "rtest" : preceiptNumber;
		piccid = (piccid == null) ? "itest" : piccid;
		
		try {			
			fname = getFileCom(popt);
			if (popt == FILEEnum.FILE_PAYTRAN) 				
				pptranFileName = fname;
			
			createFile(fname, piccid.getBytes("UTF-8"));		
		} catch(Exception e) {
			e.printStackTrace();
			logInInfo(stran + " error------------");			
		}
		
	}

	
	
	//if 插入支付记录失败，记录此支付记录失败到文件ComPay.DIR_HOST_PAY_REC, 
    //	文件名为：日期时间 + "_fticcid_" + preceiptNumber + "_" + piccid,
	//   内容为 piccid.	
	void recPayDbFile() {
		recFileCom(FILEEnum.FILE_DBTRAN);
	}

	
	
	private static void protest1() throws Exception {
		payforIccidAction pfia = new payforIccidAction();
		pfia.setPreceiptNumber("test");
		pfia.setPiccid("test");
		//pfia.getFileCom(FILEEnum.FILE_PAYTRAN);
		
		pfia.deleteFile("D:\\Workspaces\\pay\\20171103173958_fticcid_rtest_itest");
		//pfia.recPayDbFile();
		JSONObject jo = new JSONObject();
		jo.put("iccid", "893107061704935485");
		jo.put("imei", "352138064985734");  // 352138064985734
		jo.put("frequency", "1");
		jo.put("count", "0");
/*		
		piccid = ljo.optString("iccid");	
		ptoken = ljo.optString("token");
		pfrequency = ljo.optString("frequency");// 表示月的个数
		pcount = ljo.optString("count");

		pcardName = ljo.optString("card_name");// 信用卡名字
		pzipCode = ljo.optString("zip_code");// 邮编

		puserId = ljo.optString("user_id");
		puserToken = ljo.optString("app_token");
		pemail = ljo.optString("email");
		
		pbelongCompany = "1";
*/		
		
		pfia.setLjo(jo);
		pfia.proMaster();		
		
		
	}
	
	public String getPreceiptNumber() {
		return preceiptNumber;
	}


	public void setPreceiptNumber(String preceiptNumber) {
		this.preceiptNumber = preceiptNumber;
	}


	public static void main(String[] args) throws Exception {
		//protest1();
		//204047914935255   893107061704935255
		//simQuery("893107061704935255", "204047914935255");
		
		proTest2();
	}	
	
	//新建支付记录文件，文件存放位置 ComPay.DIR_HOST_PAY_REC,文件名为日期时间
	//+ "_wticcid_" + preceiptNumber + "_" + piccid, 文件内容为iccid	
	void recPayFile() {
		recFileCom(FILEEnum.FILE_PAYTRAN);
	}
	
	void clrPayFile() {
		try {
			deleteFile(pptranFileName);
			
		} catch(Exception e) {
			e.printStackTrace();
			logInInfo("clrPayFile error------------");			
		}		
	}
	
	
	void getInput() {
		
		if (getLjo() == null) 
			logInInfo("payforIccidAction input object is null(ljo is null)------");
		piccid = getLjo().optString("iccid");	
		ptoken = getLjo().optString("token");
		pfrequency = getLjo().optString("frequency");// 表示月的个数
		pcount = getLjo().optString("count");

		pcardName = getLjo().optString("card_name");// 信用卡名字
		pzipCode = getLjo().optString("zip_code");// 邮编

		puserId = getLjo().optString("user_id");
		puserToken = getLjo().optString("app_token");
		pemail = getLjo().optString("email");
		
		pbelongCompany = "1";
		
	}
	
	void payStripe() {
		pstatus = null;
		pmessage = "";
		pcustomerId = "";
		psubscriptionId = "";
		preceive = 0;
		int frequencyInt = 0;

		try {
			//vo = new DeviceActiveInfo();
			frequencyInt = Integer.valueOf(pfrequency);
			Stripe.apiKey = Constant.STRIPE_APIKEY;
			// live scret key
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("email", pemail);
			customerParams.put("source", ptoken);
			Customer customer = Customer.create(customerParams);
			pcustomerId = customer.getId();

			logInInfo("支付用户id==" + pcustomerId);

			if ("0".equals(pcount)) {
				// 0表示扣一次
				Map<String, Object> chargeParams = new HashMap<String, Object>();
				if (frequencyInt == 1) {
					preceive = ComPay.OONTH;
					chargeParams.put("amount", ComPay.OONTH);// 美分
				} else if (frequencyInt == 6) {
					preceive = ComPay.SONTH;
					chargeParams.put("amount", ComPay.SONTH);// 美分
				} else if (frequencyInt == 12) {
					preceive = ComPay.TONTH;
					chargeParams.put("amount", ComPay.TONTH);// 美分
				}
				// chargeParams.put("amount", coin * frequencyInt);// 美分
				chargeParams.put("currency", "usd");
				chargeParams.put("customer", pcustomerId);
				chargeParams.put("description", piccid + "支付了"
						+ pfrequency + "个月的费用");

				Charge charge = Charge.create(chargeParams);
				// receive = coin * frequencyInt;

				preceiptNumber = charge.getReceiptNumber();
				logInInfo("支付记录的number=" + preceiptNumber);

			} else if ("1".equals(pcount)) {
				pplan = "one_month";
				if ("1".equals(pfrequency)) {
					pplan = "one_month";
				} else if ("3".equals(pfrequency)) {
					pplan = "three_month";
				} else if ("6".equals(pfrequency)) {
					pplan = "six_month";
				} else if ("12".equals(pfrequency)) {
					pplan = "twelve_month";
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("plan", pplan);
				// one_month在后台自定义
				params.put("customer", pcustomerId);
				params.put("description", piccid + "支付了" + pfrequency
						+ "个月的费用");
				Subscription.create(params);

				// 若是订阅则需要保存订阅成功的id
				psubscriptionId = Subscription.create(params).getId();
				/* if创建一个订阅计划，将立即向客户开始收取费用 */
				preceive = frequencyInt * coin;

			}

			result = Constant.SUCCESS_CODE;
			
			pstatus = 200;
			pmessage = "ok";					
			
		}  catch (AuthenticationException e) {
			pstatus = e.getStatusCode();
			pmessage = e.getMessage();
			result = ( pstatus == Constant.SUCCESS_CODE ) ? Constant.FAIL_CODE : pstatus;
			logInInfo("支付异常状态和message========" + pstatus + "---"
					+ pmessage);
			
		} catch (InvalidRequestException e) {
			pstatus = e.getStatusCode();
			pmessage = e.getMessage();
			result = ( pstatus == Constant.SUCCESS_CODE ) ? Constant.FAIL_CODE : pstatus;
			logInInfo("支付异常状态和message========" + pstatus + "---"
					+ pmessage);
		} catch (APIConnectionException e) {
			pstatus = e.getStatusCode();
			pmessage = e.getMessage();
			result = ( pstatus == Constant.SUCCESS_CODE ) ? Constant.FAIL_CODE : pstatus;
			logInInfo("支付异常状态和message========" + pstatus + "---"
					+ pmessage);
		} catch (CardException e) {
			pstatus = e.getStatusCode();
			pmessage = e.getMessage();
			result = ( pstatus == Constant.SUCCESS_CODE ) ? Constant.FAIL_CODE : pstatus;
			logInInfo("支付异常状态和message========" + pstatus + "---"
					+ pmessage);
		} catch (APIException e) {
			pstatus = e.getStatusCode();
			pmessage = e.getMessage();
			result = pstatus;			
			logInInfo("支付异常状态和message========" + pstatus + "---"
					+ pmessage);
		}		
	}

	static //204047914935255   893107061704935255
	void simQuery(String iccid, String imsi) throws Exception {
		
		String resultResponsee = AiShiDeIccidApi.selectIccidStatus(iccid,
				imsi);
		JSONObject resultResponseJsone = JSONObject
				.fromObject(resultResponsee);
		String datae = resultResponseJsone.getString("data");
		String respCode = resultResponseJsone.getString("resp_code");
		
		if ("1".equals(respCode)) {
			JSONObject dataJsone = JSONObject.fromObject(datae);
			String Status = dataJsone.getString("status");
			
			System.out.println(Status);
		}
		
	}
	
	
	void simActivate() throws Exception {
		DeviceActiveInfo vo = null;
		DeviceActiveInfo vo1 = null;
		Tools tls = null;
		result = Constant.SUCCESS_CODE;		
		try {
			vo1 = new DeviceActiveInfo();			
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			int frequencyInt = Integer.valueOf(pfrequency);
			
			tls  = new Tools();
			
			vo.setCondition("imei ='"
					+ piccid
					+ "'and iccid='"
					+ piccid
					+ "' and card_status='200' and message='ok' and belong_company='"
					+ pbelongCompany + "' limit 1");
			List<DataMap> cancelList = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade()
					.getcancleImeiInfo(vo);
			String jihuo = "active";

			if ("0".equals(pcount)) {
				// 0表示扣一次
				String stopTime = "";
				if (cancelList.size() <= 0) {
					logger.info(piccid + "以前有支付信息直接续期-----");
					// 2表示六个月
					vo.setDeviceImei(piccid);
					vo.setCreateTime(tls.getUtcDateStrNow());
					vo.setCardStatus(pstatus + "");
					vo.setMessage(pmessage);
					vo.setBrandName(pcustomerId);
					vo.setDeviceAge("2");

					Calendar c = Calendar.getInstance();// 获得一个日历的实例
				
					Date date= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期
					logger.info("初始日期="+date);		
					c.setTime(date);// 设置日历时间
					c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

					stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

					vo.setUpdateTime(stopTime);
					vo.setIccid(piccid);
					vo.setBelongCompany(pbelongCompany);
					ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.insertCancelSubSriptionInfo(vo);

					vo.setDeviceImei(piccid);
					vo.setCardStatus(pstatus + "");
					vo.setCreateTime(tls.getUtcDateStrNow());
					vo.setUpdateTime(stopTime);
					vo.setBelongCompany(pbelongCompany);
					vo.setMessage("insert");

					ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.insertCancelSubSriptionLogInfo(vo);

					jihuo = "active";
				} else {
					stopTime = cancelList.get(0).get("stop_time")
							+ "";
					
					Date dt1= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期
					Date dt2= (Date)sdf.parseObject(stopTime);//设置初始日期
					
					Calendar c = Calendar.getInstance();// 获得一个日历的实例

					Date date = null;
					if (dt1.getTime() >= dt2.getTime()) {
						date = dt1;// 初始日期
					} else {
						date = dt2;// 初始日期
					}

					c.setTime(date);// 设置日历时间
					c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

					stopTime = sdf.format(c.getTime());// 得到6个月后的日期
					String id = cancelList.get(0).get("id") + "";

					vo.setUpdateTime(stopTime);
					vo.setCondition("id='" + id + "'");
					int xiugai = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.updateCancelSubSriptionInfo(vo);

					vo.setDeviceImei(piccid);
					vo.setCardStatus(pstatus + "");
					vo.setCreateTime(tls.getUtcDateStrNow());
					vo.setUpdateTime(stopTime);
					vo.setBelongCompany(pbelongCompany);
					vo.setMessage("update");

					ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.insertCancelSubSriptionLogInfo(vo);
					jihuo = "active";
				}

			} else if ("1".equals(pcount)) {
				jihuo = "active";
			} else {
				jihuo = "notActive";
			}
			if ("active".equals(jihuo)) {
				if (piccid.length() > 0 && piccid != null
						&& !"".equals(piccid)) {

					if ("0".equals(pbelongCompany)) {

						vo1.setCondition("iccid ='" + piccid
								+ "' limit 1");
						String sid = "";
						List<DataMap> list = ServiceBean
								.getInstance()
								.getDeviceActiveInfoFacade()
								.getSsidInfo(vo1);

						if (list.size() <= 0) {
							Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
							ResourceSet<Sim> sims = Sim.reader()
									.read();
							for (Sim sim : sims) {
								if (piccid.equals(sim.getIccid())) {
									sid = sim.getSid();
									vo1.setSid(sid);
									vo1.setIccid(piccid);
									vo1.setCardStatus("1");
									vo1.setFriendlyName(sim
											.getFriendlyName());
									vo1.setRatePlan(sim
											.getRatePlanSid());
									vo1.setCreateTime(sim
											.getDateCreated() + "");
									vo1.setUpdateTime(sim
											.getDateUpdated() + "");
									ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.insertSmsInfo(vo1);

									// 激活
									Twilio.init(ACCOUNT_SID,
											AUTH_TOKEN);
									Sim updatedSim = Sim
											.updater(sid)
											.setRatePlan("data100")
											.setStatus("active")
											.update();
									getLrjo().put("card_status", "激活成功");
									// break;
								} else {
									vo1.setCondition("iccid ='"
											+ sim.getIccid()
											+ "' limit 1");
									List<DataMap> listSelect = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(vo1);
									if (listSelect.size() <= 0) {
										vo1.setSid(sim.getSid());
										vo1.setIccid(sim.getIccid());
										vo1.setCardStatus(sim
												.getStatus()
												.equals("active") ? "1"
												: "0");
										vo1.setFriendlyName(sim
												.getFriendlyName());
										vo1.setRatePlan(sim
												.getRatePlanSid());
										vo1.setCreateTime(sim
												.getDateCreated()
												+ "");
										vo1.setUpdateTime(sim
												.getDateUpdated()
												+ "");
										ServiceBean
												.getInstance()
												.getDeviceActiveInfoFacade()
												.insertSmsInfo(vo1);
									} else {
										vo1.setCondition("iccid ='"
												+ sim.getIccid()
												+ "'");
										vo1.setUpdateTime(sim
												.getDateUpdated()
												+ "");
										vo1.setRatePlan(sim
												.getRatePlanSid());
										vo1.setCardStatus(sim
												.getStatus()
												.equals("active") ? "1"
												: "0");
										ServiceBean
												.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(
														vo1);
									}
								}
							}
							result = 1;
						} else {
							sid = list.get(0).get("sid") + "";
							// 激活
							Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
							Sim updatedSim = Sim.updater(sid)
									.setRatePlan("data100")
									.setStatus("active").update();
							getLrjo().put("card_status", "激活成功");
							vo1.setCardStatus("1");
							vo1.setCondition("iccid ='" + piccid
									+ "'");
							ServiceBean.getInstance()
									.getDeviceActiveInfoFacade()
									.updateDeviceSmsInfo(vo1);
							//result = 1;
						}
					} else if ("1".equals(pbelongCompany)) {
						// 1表示爱施德
						String Status = AiShiDeIccidApi.getStatus(
								piccid, pimsi);
						logger.info(piccid + "此ICCID卡的状态==" + Status);
						if ("Active".equals(Status)) {

						} else if ("Suspended".equals(Status)) {
							AiShiDeIccidApiV2.setIccidStatus(piccid,
									pimsi, "Resume");
						} else if ("Preactive".equals(Status)) {
							AiShiDeIccidApiV2.setIccidStatus(piccid,
									pimsi, "Activate");
						}

						for (int n = 0; n < 3; n++) {
							Status = AiShiDeIccidApi.getStatus(
									piccid, pimsi);
							logInInfo("激活后卡的状态为="
									+ Status);
							if (!"Active".equals(Status)) {
								if ("Suspended".equals(Status)) {
									AiShiDeIccidApi.setIccidStatus(
											piccid, pimsi, "Resume");
								} else if ("Preactive"
										.equals(Status)) {
									AiShiDeIccidApiV2
											.setIccidStatus(piccid,
													pimsi,
													"Activate");
								}
							}
							break;
						}
						
						Status = AiShiDeIccidApi.getStatus(
								piccid, pimsi);
						if (!"Active".equals(Status)) {
							throw new Exception("fail simactivate api");
						}

						getLrjo().put("card_status", "激活成功");
						result = 1;
						vo1.setCardStatus("1");
						vo1.setCondition("iccid ='" + piccid
								+ "'and belong_company='1'");
						ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.updateDeviceSmsInfo(vo1);

					} else if ("2".equals(pbelongCompany)) {

						String resule = ShuMiIccidApi
								.updateIccidCardStatus(piccid, "0");
						JSONObject resuleResponseJson = JSONObject
								.fromObject(resule);
						if ("0".equals(resuleResponseJson
								.get("code"))) {
							String resultt = ShuMiIccidApi
									.IccidSubscription(piccid,
											"00000050001");
							JSONObject resulttResponseJson = JSONObject
									.fromObject(resultt);
							if ("0".equals(resulttResponseJson
									.get("code"))) {
								getLrjo().put("card_status", "激活成功");
								vo1.setCardStatus("1");
								vo1.setCondition("iccid ='" + piccid
										+ "'and belong_company='2'");
								ServiceBean
										.getInstance()
										.getDeviceActiveInfoFacade()
										.updateDeviceSmsInfo(vo1);
								//result = Constant.SUCCESS_CODE;
							}
						} else {
							getLrjo().put("card_status", "激活失败");
							result = Constant.FAIL_CODE;
							throw new Exception("ShuMiIccidApi simactivate api fail");							
						}
					}
				} 

			}
					
		} catch(Exception e) {
			logInInfo("sim activate exception occurs-----------"
					);			
			throw e;
			
		}
		
		vo = null;
		vo1 = null;
	}
	
	private void logInInfo(String msg) {
		if (piccid == null) 
			piccid = "";
		logger.info(ComPay.TXT_PAY_LOG_TAG + "iccid:" + piccid + ":" + msg);
	}
	
	// 将byte数组写入文件
	public void createFile(String path, byte[] content)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(content);
		fos.close();
	}   	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		//Date start = new Date();
		if (getLrjo() == null) {			
			setLrjo(new JSONObject());
		}
		
		try {			
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			setLjo(JSONObject.fromObject(sb.toString()));
			
			proMaster();
			
		
			
			getLrjo().put("request", href);
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
			pmessage = sb.toString();
		}
		// json.put(Constant.RESULTCODE, result);
		getLrjo().put(Constant.RESULTCODE, result);
		if ( result != Constant.SUCCESS_CODE )
			getLrjo().put(Constant.EXCEPTION, pmessage);
		getLrjo().put("iccid", piccid);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getLrjo().toString());
		return null;
	}
	

	
	
	
	public ActionForward executeNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		//JSONObject json = new JSONObject();
		if (getLrjo() == null) {			
			setLrjo(new JSONObject());
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			String sbstring = sb.toString();
			logInInfo("newpay:payforIccidAction-----" + sbstring);
			JSONObject object = JSONObject.fromObject(sbstring);
			// String imei = object.getString("imei");// 以用户确认的为主
			String iccid = object.optString("iccid");
			String token = object.optString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.optString("frequency");// 表示月的个数
			String count = object.optString("count");

			String cardName = object.optString("card_name");// 信用卡名字
			String zipCode = object.optString("zip_code");// 邮编

			// 0表示扣一次 1表示自动扣

			// String email = object.getString("email");
			String userId = object.optString("user_id");
			String userToken = object.optString("app_token");

			String belongCompany = "1";
			// iccid 属于哪个公司 1表示爱施德 2表示树米0表示twilio

			// String imsi = object.getString("imsi");

			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			vo1.setCondition("iccid ='" + iccid + "'");
			List<DataMap> lsitCard = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSsidInfo(vo1);
			String imsi = "";
			// String cardStatus="";
			if (lsitCard.size() > 0) {
				imsi = lsitCard.get(0).get("imsi") + "";
				belongCompany = lsitCard.get(0).get("belong_company") + "";
				// cardStatus=lsitCard.get(0).get("card_status") + "";
			}
			logInInfo("此时的imsi=" + imsi + "----belongCompany="
					+ belongCompany);

			// String cardStatus = object.getString("card_status");

			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			String customerId = "";
			String subscriptionId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			int receive = 0;
			int frequencyInt = Integer.valueOf(frequency);
			String email = object.optString("email");
			

			String receiptNumber = "";

			if (!"null".equals(iccid)) {

				logInInfo( "开始支付前------------------------");
				try {
					// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
					// Test apiKey
					Stripe.apiKey = Constant.STRIPE_APIKEY;
					// live scret key
					Map<String, Object> customerParams = new HashMap<String, Object>();
					customerParams.put("email", email);
					customerParams.put("source", token);
					Customer customer = Customer.create(customerParams);
					customerId = customer.getId();

					logInInfo( "支付用户id==" + customerId);
					// Charge the Customer instead of the card:


					if ("0".equals(count)) {
						// 0表示扣一次
						Map<String, Object> chargeParams = new HashMap<String, Object>();
						if (frequencyInt == 1) {
							receive = ComPay.OONTH;
							chargeParams.put("amount", Constant.OONTH);// 美分
						} else if (frequencyInt == 6) {
							receive = ComPay.SONTH;
							chargeParams.put("amount", Constant.SONTH);// 美分
						} else if (frequencyInt == 12) {
							receive = ComPay.TONTH;
							chargeParams.put("amount", Constant.TONTH);// 美分
						}
						// chargeParams.put("amount", coin * frequencyInt);// 美分
						chargeParams.put("currency", "usd");
						chargeParams.put("customer", customerId);
						chargeParams.put("description", iccid + "支付了"
								+ frequency + "个月的费用");

						Charge charge = Charge.create(chargeParams);
						// receive = coin * frequencyInt;

						receiptNumber = charge.getReceiptNumber();
						logInInfo( "支付记录的number=" + receiptNumber);

					} else if ("1".equals(count)) {
						// 1表示自动扣
						// Charge charge = Charge.create(chargeParams);
						// 创建订阅
						String plan = "one_month";
						if ("1".equals(frequency)) {
							plan = "one_month";
						} else if ("3".equals(frequency)) {
							plan = "three_month";
						} else if ("6".equals(frequency)) {
							plan = "six_month";
						} else if ("12".equals(frequency)) {
							plan = "twelve_month";
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("plan", plan);
						// one_month在后台自定义
						params.put("customer", customerId);
						params.put("description", iccid + "支付了" + frequency
								+ "个月的费用");
						Subscription.create(params);

						// 若是订阅则需要保存订阅成功的id
						subscriptionId = Subscription.create(params).getId();
						/* if创建一个订阅计划，将立即向客户开始收取费用 */
						receive = frequencyInt * coin;

					}

					status = 200;
					message = "ok";
				} catch (AuthenticationException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (InvalidRequestException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (APIConnectionException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo("支付异常状态和message========" + 
							status + "---" + message);
				} catch (CardException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} catch (APIException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} finally {
					logInInfo( "支付状态为------------=" + status);
					if (status == 200) {
						getLrjo().put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
					} else {
						getLrjo().put(Constant.RESULTCODE, status);
					}
					getLrjo().put(Constant.EXCEPTION, message);
					getLrjo().put("iccid", iccid);

					vo.setDeviceImei(iccid);
					vo.setCreateTime(sf.format(new Date()));
					vo.setReceive(receive);
					vo.setCardStatus(status + "");// 200
					vo.setMessage(message);
					vo.setCurrency("usd");
					vo.setDescription(frequency);
					vo.setSource(token);
					vo.setFriendlyName("000000000000000");// 全部默认为这个
					vo.setBrandName(customerId);
					vo.setEmail(email);
					vo.setSubscriptionId(subscriptionId);
					vo.setRatePlan(count);
					vo.setCardName(cardName);
					vo.setZipCode(zipCode);
					vo.setReceiptNumber(receiptNumber);
					vo.setIccid(iccid);
					// }
					vo.setSubStatus("1");
					vo.setBelongCompany(belongCompany);

					int insertCount = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().insertPayForInfo(vo);
					logInInfo( "支付添加结果为-----insertCount------=" + insertCount);
					if (insertCount != 1) {
						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
					}
					result = 1;

					if (status == 200) {
						logInInfo( "状态为200为支付成功开始走激活---------");
						vo.setCondition("imei ='"
								+ iccid
								+ "'and iccid='"
								+ iccid
								+ "' and card_status='200' and message='ok' and belong_company='"
								+ belongCompany + "' limit 1");
						List<DataMap> cancelList = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.getcancleImeiInfo(vo);
						String jihuo = "active";

						if ("0".equals(count)) {
							// 0表示扣一次
							String stopTime = "";
							if (cancelList.size() <= 0) {
								logInInfo( "以前有支付信息直接续期-----");
								// 2表示六个月
								vo.setDeviceImei(iccid);
								vo.setCreateTime(sf.format(new Date()));
								vo.setCardStatus(status + "");
								vo.setMessage(message);
								vo.setBrandName(customerId);
								vo.setDeviceAge("2");

								Calendar c = Calendar.getInstance();// 获得一个日历的实例
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								Date date = new Date();
								stopTime = new Date() + "";
								try {
									date = sdf.parse(stopTime);// 初始日期
								} catch (Exception e) {

								}
								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

								vo.setUpdateTime(stopTime);
								vo.setIccid(iccid);
								vo.setBelongCompany(belongCompany);
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("insert");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);

								jihuo = "active";
							} else {
								stopTime = cancelList.get(0).get("stop_time")
										+ "";

								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								String timeStop = sdf.format(new Date());

								Date dt1 = sdf.parse(timeStop);// 今天的时间
								Date dt2 = sdf.parse(stopTime);// 到期的时间

								Calendar c = Calendar.getInstance();// 获得一个日历的实例

								Date date = null;
								if (dt1.getTime() >= dt2.getTime()) {
									date = sdf.parse(timeStop);// 初始日期
								} else {
									date = sdf.parse(stopTime);// 初始日期
								}

								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到6个月后的日期
								String id = cancelList.get(0).get("id") + "";

								vo.setUpdateTime(stopTime);
								vo.setCondition("id='" + id + "'");
								int xiugai = ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("update");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);
								jihuo = "active";
							}

						} else if ("1".equals(count)) {
							jihuo = "active";
						} else {
							jihuo = "notActive";
						}
						logInInfo( "jihuo=================" + jihuo);
						if ("active".equals(jihuo)) {
							if (iccid.length() > 0 && iccid != null
									&& !"".equals(iccid)) {

								if ("0".equals(belongCompany)) {

									vo1.setCondition("iccid ='" + iccid
											+ "' limit 1");
									String sid = "";
									List<DataMap> list = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(vo1);

									if (list.size() <= 0) {
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										ResourceSet<Sim> sims = Sim.reader()
												.read();
										for (Sim sim : sims) {
											if (iccid.equals(sim.getIccid())) {
												sid = sim.getSid();
												vo1.setSid(sid);
												vo1.setIccid(iccid);
												vo1.setCardStatus("1");
												vo1.setFriendlyName(sim
														.getFriendlyName());
												vo1.setRatePlan(sim
														.getRatePlanSid());
												vo1.setCreateTime(sim
														.getDateCreated() + "");
												vo1.setUpdateTime(sim
														.getDateUpdated() + "");
												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.insertSmsInfo(vo1);

												// 激活
												Twilio.init(ACCOUNT_SID,
														AUTH_TOKEN);
												Sim updatedSim = Sim
														.updater(sid)
														.setRatePlan("data100")
														.setStatus("active")
														.update();
												getLrjo().put("card_status", "激活成功");
												// break;
											} else {
												vo1.setCondition("iccid ='"
														+ sim.getIccid()
														+ "' limit 1");
												List<DataMap> listSelect = ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.getSsidInfo(vo1);
												if (listSelect.size() <= 0) {
													vo1.setSid(sim.getSid());
													vo1.setIccid(sim.getIccid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													vo1.setFriendlyName(sim
															.getFriendlyName());
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCreateTime(sim
															.getDateCreated()
															+ "");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.insertSmsInfo(vo1);
												} else {
													vo1.setCondition("iccid ='"
															+ sim.getIccid()
															+ "'");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.updateDeviceSmsInfo(
																	vo1);
												}
											}
										}
										result = 1;
									} else {
										sid = list.get(0).get("sid") + "";
										// 激活
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										Sim updatedSim = Sim.updater(sid)
												.setRatePlan("data100")
												.setStatus("active").update();
										getLrjo().put("card_status", "激活成功");
										vo1.setCardStatus("1");
										vo1.setCondition("iccid ='" + iccid
												+ "'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);
										result = 1;
									}
								} else if ("1".equals(belongCompany)) {
									// 1表示爱施德
									String Status = AiShiDeIccidApi.getStatus(
											iccid, imsi);
									logInInfo( "此ICCID卡的状态==" + Status);
									if ("Active".equals(Status)) {

									} else if ("Suspended".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Resume");
									} else if ("Preactive".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Activate");
									}

									for (int n = 0; n < 3; n++) {
										Status = AiShiDeIccidApi.getStatus(
												iccid, imsi);
										logInInfo("激活后卡的状态为="
												+ Status);
										if (!"Active".equals(Status)) {
											if ("Suspended".equals(Status)) {
												AiShiDeIccidApiV2.setIccidStatus(
														iccid, imsi, "Resume");
											} else if ("Preactive"
													.equals(Status)) {
												AiShiDeIccidApiV2
														.setIccidStatus(iccid,
																imsi,
																"Activate");
											}
										}
										break;
									}

									getLrjo().put("card_status", "激活成功");
									result = 1;
									vo1.setCardStatus("1");
									vo1.setCondition("iccid ='" + iccid
											+ "'and belong_company='1'");
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.updateDeviceSmsInfo(vo1);

								} else if ("2".equals(belongCompany)) {

									String resule = ShuMiIccidApi
											.updateIccidCardStatus(iccid, "0");
									JSONObject resuleResponseJson = JSONObject
											.fromObject(resule);
									if ("0".equals(resuleResponseJson
											.get("code"))) {
										String resultt = ShuMiIccidApi
												.IccidSubscription(iccid,
														"00000050001");
										JSONObject resulttResponseJson = JSONObject
												.fromObject(resultt);
										if ("0".equals(resulttResponseJson
												.get("code"))) {
											getLrjo().put("card_status", "激活成功");
											vo1.setCardStatus("1");
											vo1.setCondition("iccid ='" + iccid
													+ "'and belong_company='2'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo1);
											result = 1;
										}
									} else {
										getLrjo().put("card_status", "激活失败");
										result = 0;
									}
								}
							} else {
								logInInfo( "iccid为空");
								result = 2;
								getLrjo().put("card_status", "未找到对应的iccid");
							}

						}
					} else {
						getLrjo().put("card_status", "支付失败");
					}
				}

			} else {
				getLrjo().put(Constant.EXCEPTION, "存在IMEI没有iccid");
				getLrjo().put("iccid", iccid);
				getLrjo().put(Constant.RESULTCODE, 0);
			}
			getLrjo().put("request", href);
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
			getLrjo().put(Constant.EXCEPTION, sb.toString());
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getLrjo().toString());
		return null;
	}
	
	

	
	
	
	
	
	public ActionForward executeOldClean(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		//JSONObject json = new JSONObject();
		if (getLrjo() == null) {			
			setLrjo(new JSONObject());
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			String sbstring = sb.toString();
			logInInfo("接收到的数据：" + sbstring);
			JSONObject object = JSONObject.fromObject(sbstring);
			// String imei = object.getString("imei");// 以用户确认的为主
			String iccid = object.optString("iccid");
			String token = object.optString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.optString("frequency");// 表示月的个数
			String count = object.optString("count");

			String cardName = object.optString("card_name");// 信用卡名字
			String zipCode = object.optString("zip_code");// 邮编

			// 0表示扣一次 1表示自动扣

			// String email = object.getString("email");
			String userId = object.optString("user_id");
			String userToken = object.optString("app_token");

			String belongCompany = "1";
			// iccid 属于哪个公司 1表示爱施德 2表示树米0表示twilio

			// String imsi = object.getString("imsi");

			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			vo1.setCondition("iccid ='" + iccid + "'");
			List<DataMap> lsitCard = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSsidInfo(vo1);
			String imsi = "";
			// String cardStatus="";
			if (lsitCard.size() > 0) {
				imsi = lsitCard.get(0).get("imsi") + "";
				belongCompany = lsitCard.get(0).get("belong_company") + "";
				// cardStatus=lsitCard.get(0).get("card_status") + "";
			}
			logInInfo( "此时的imsi=" + imsi + "----belongCompany="
					+ belongCompany);

			// String cardStatus = object.getString("card_status");

			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			String customerId = "";
			String subscriptionId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			int receive = 0;
			int frequencyInt = Integer.valueOf(frequency);
			String email = object.optString("email");
			

			String receiptNumber = "";

			if (!"null".equals(iccid)) {

				logInInfo(  "开始支付前------------------------");
				try {
					// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
					// Test apiKey
					Stripe.apiKey = Constant.STRIPE_APIKEY;
					// live scret key
					Map<String, Object> customerParams = new HashMap<String, Object>();
					customerParams.put("email", email);
					customerParams.put("source", token);
					Customer customer = Customer.create(customerParams);
					customerId = customer.getId();

					logInInfo( "支付用户id==" + customerId);
					// Charge the Customer instead of the card:


					if ("0".equals(count)) {
						// 0表示扣一次
						Map<String, Object> chargeParams = new HashMap<String, Object>();
						if (frequencyInt == 1) {
							receive = ComPay.OONTH;
							chargeParams.put("amount", Constant.OONTH);// 美分
						} else if (frequencyInt == 6) {
							receive = ComPay.SONTH;
							chargeParams.put("amount", Constant.SONTH);// 美分
						} else if (frequencyInt == 12) {
							receive = ComPay.TONTH;
							chargeParams.put("amount", Constant.TONTH);// 美分
						}
						// chargeParams.put("amount", coin * frequencyInt);// 美分
						chargeParams.put("currency", "usd");
						chargeParams.put("customer", customerId);
						chargeParams.put("description", iccid + "支付了"
								+ frequency + "个月的费用");

						Charge charge = Charge.create(chargeParams);
						// receive = coin * frequencyInt;

						receiptNumber = charge.getReceiptNumber();
						logInInfo( "支付记录的number=" + receiptNumber);

					} else if ("1".equals(count)) {
						// 1表示自动扣
						// Charge charge = Charge.create(chargeParams);
						// 创建订阅
						String plan = "one_month";
						if ("1".equals(frequency)) {
							plan = "one_month";
						} else if ("3".equals(frequency)) {
							plan = "three_month";
						} else if ("6".equals(frequency)) {
							plan = "six_month";
						} else if ("12".equals(frequency)) {
							plan = "twelve_month";
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("plan", plan);
						// one_month在后台自定义
						params.put("customer", customerId);
						params.put("description", iccid + "支付了" + frequency
								+ "个月的费用");
						Subscription.create(params);

						// 若是订阅则需要保存订阅成功的id
						subscriptionId = Subscription.create(params).getId();
						/* if创建一个订阅计划，将立即向客户开始收取费用 */
						receive = frequencyInt * coin;

					}

					status = 200;
					message = "ok";
				} catch (AuthenticationException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (InvalidRequestException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (APIConnectionException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo("支付异常状态和message========" + 
							status + "---" + message);
				} catch (CardException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} catch (APIException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} finally {
					logInInfo( "支付状态为------------=" + status);
					if (status == 200) {
						getLrjo().put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
					} else {
						getLrjo().put(Constant.RESULTCODE, status);
					}
					getLrjo().put(Constant.EXCEPTION, message);
					getLrjo().put("iccid", iccid);

					vo.setDeviceImei(iccid);
					vo.setCreateTime(sf.format(new Date()));
					vo.setReceive(receive);
					vo.setCardStatus(status + "");// 200
					vo.setMessage(message);
					vo.setCurrency("usd");
					vo.setDescription(frequency);
					vo.setSource(token);
					vo.setFriendlyName("000000000000000");// 全部默认为这个
					vo.setBrandName(customerId);
					vo.setEmail(email);
					vo.setSubscriptionId(subscriptionId);
					vo.setRatePlan(count);
					vo.setCardName(cardName);
					vo.setZipCode(zipCode);
					vo.setReceiptNumber(receiptNumber);
					vo.setIccid(iccid);
					// }
					vo.setSubStatus("1");
					vo.setBelongCompany(belongCompany);

					int insertCount = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().insertPayForInfo(vo);
					logInInfo( "支付添加结果为-----insertCount------=" + insertCount);
					if (insertCount != 1) {
						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
					}
					result = 1;

					if (status == 200) {
						logInInfo( "状态为200为支付成功开始走激活---------");
						vo.setCondition("imei ='"
								+ iccid
								+ "'and iccid='"
								+ iccid
								+ "' and card_status='200' and message='ok' and belong_company='"
								+ belongCompany + "' limit 1");
						List<DataMap> cancelList = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.getcancleImeiInfo(vo);
						String jihuo = "active";

						if ("0".equals(count)) {
							// 0表示扣一次
							String stopTime = "";
							if (cancelList.size() <= 0) {
								logInInfo( "以前有支付信息直接续期-----");
								// 2表示六个月
								vo.setDeviceImei(iccid);
								vo.setCreateTime(sf.format(new Date()));
								vo.setCardStatus(status + "");
								vo.setMessage(message);
								vo.setBrandName(customerId);
								vo.setDeviceAge("2");

								Calendar c = Calendar.getInstance();// 获得一个日历的实例
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								Date date = new Date();
								stopTime = new Date() + "";
								try {
									date = sdf.parse(stopTime);// 初始日期
								} catch (Exception e) {

								}
								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

								vo.setUpdateTime(stopTime);
								vo.setIccid(iccid);
								vo.setBelongCompany(belongCompany);
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("insert");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);

								jihuo = "active";
							} else {
								stopTime = cancelList.get(0).get("stop_time")
										+ "";

								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								String timeStop = sdf.format(new Date());

								Date dt1 = sdf.parse(timeStop);// 今天的时间
								Date dt2 = sdf.parse(stopTime);// 到期的时间

								Calendar c = Calendar.getInstance();// 获得一个日历的实例

								Date date = null;
								if (dt1.getTime() >= dt2.getTime()) {
									date = sdf.parse(timeStop);// 初始日期
								} else {
									date = sdf.parse(stopTime);// 初始日期
								}

								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到6个月后的日期
								String id = cancelList.get(0).get("id") + "";

								vo.setUpdateTime(stopTime);
								vo.setCondition("id='" + id + "'");
								int xiugai = ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("update");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);
								jihuo = "active";
							}

						} else if ("1".equals(count)) {
							jihuo = "active";
						} else {
							jihuo = "notActive";
						}
						logInInfo( "jihuo=================" + jihuo);
						if ("active".equals(jihuo)) {
							if (iccid.length() > 0 && iccid != null
									&& !"".equals(iccid)) {

								if ("0".equals(belongCompany)) {

									vo1.setCondition("iccid ='" + iccid
											+ "' limit 1");
									String sid = "";
									List<DataMap> list = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(vo1);

									if (list.size() <= 0) {
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										ResourceSet<Sim> sims = Sim.reader()
												.read();
										for (Sim sim : sims) {
											if (iccid.equals(sim.getIccid())) {
												sid = sim.getSid();
												vo1.setSid(sid);
												vo1.setIccid(iccid);
												vo1.setCardStatus("1");
												vo1.setFriendlyName(sim
														.getFriendlyName());
												vo1.setRatePlan(sim
														.getRatePlanSid());
												vo1.setCreateTime(sim
														.getDateCreated() + "");
												vo1.setUpdateTime(sim
														.getDateUpdated() + "");
												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.insertSmsInfo(vo1);

												// 激活
												Twilio.init(ACCOUNT_SID,
														AUTH_TOKEN);
												Sim updatedSim = Sim
														.updater(sid)
														.setRatePlan("data100")
														.setStatus("active")
														.update();
												getLrjo().put("card_status", "激活成功");
												// break;
											} else {
												vo1.setCondition("iccid ='"
														+ sim.getIccid()
														+ "' limit 1");
												List<DataMap> listSelect = ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.getSsidInfo(vo1);
												if (listSelect.size() <= 0) {
													vo1.setSid(sim.getSid());
													vo1.setIccid(sim.getIccid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													vo1.setFriendlyName(sim
															.getFriendlyName());
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCreateTime(sim
															.getDateCreated()
															+ "");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.insertSmsInfo(vo1);
												} else {
													vo1.setCondition("iccid ='"
															+ sim.getIccid()
															+ "'");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.updateDeviceSmsInfo(
																	vo1);
												}
											}
										}
										result = 1;
									} else {
										sid = list.get(0).get("sid") + "";
										// 激活
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										Sim updatedSim = Sim.updater(sid)
												.setRatePlan("data100")
												.setStatus("active").update();
										getLrjo().put("card_status", "激活成功");
										vo1.setCardStatus("1");
										vo1.setCondition("iccid ='" + iccid
												+ "'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);
										result = 1;
									}
								} else if ("1".equals(belongCompany)) {
									// 1表示爱施德
									String Status = AiShiDeIccidApi.getStatus(
											iccid, imsi);
									logInInfo( "此ICCID卡的状态==" + Status);
									if ("Active".equals(Status)) {

									} else if ("Suspended".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Resume");
									} else if ("Preactive".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Activate");
									}

									for (int n = 0; n < 3; n++) {
										Status = AiShiDeIccidApi.getStatus(
												iccid, imsi);
										logInInfo("激活后卡的状态为="
												+ Status);
										if (!"Active".equals(Status)) {
											if ("Suspended".equals(Status)) {
												AiShiDeIccidApiV2.setIccidStatus(
														iccid, imsi, "Resume");
											} else if ("Preactive"
													.equals(Status)) {
												AiShiDeIccidApiV2
														.setIccidStatus(iccid,
																imsi,
																"Activate");
											}
										}
										break;
									}

									getLrjo().put("card_status", "激活成功");
									result = 1;
									vo1.setCardStatus("1");
									vo1.setCondition("iccid ='" + iccid
											+ "'and belong_company='1'");
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.updateDeviceSmsInfo(vo1);

								} else if ("2".equals(belongCompany)) {

									String resule = ShuMiIccidApi
											.updateIccidCardStatus(iccid, "0");
									JSONObject resuleResponseJson = JSONObject
											.fromObject(resule);
									if ("0".equals(resuleResponseJson
											.get("code"))) {
										String resultt = ShuMiIccidApi
												.IccidSubscription(iccid,
														"00000050001");
										JSONObject resulttResponseJson = JSONObject
												.fromObject(resultt);
										if ("0".equals(resulttResponseJson
												.get("code"))) {
											getLrjo().put("card_status", "激活成功");
											vo1.setCardStatus("1");
											vo1.setCondition("iccid ='" + iccid
													+ "'and belong_company='2'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo1);
											result = 1;
										}
									} else {
										getLrjo().put("card_status", "激活失败");
										result = 0;
									}
								}
							} else {
								logInInfo( "iccid为空");
								result = 2;
								getLrjo().put("card_status", "未找到对应的iccid");
							}

						}
					} else {
						getLrjo().put("card_status", "支付失败");
					}
				}

			} else {
				getLrjo().put(Constant.EXCEPTION, "存在IMEI没有iccid");
				getLrjo().put("iccid", iccid);
				getLrjo().put(Constant.RESULTCODE, 0);
			}
			getLrjo().put("request", href);
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
			getLrjo().put(Constant.EXCEPTION, sb.toString());
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getLrjo().toString());
		return null;
	}
		
	
	public ActionForward executeOld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		long s1 = new Date().getTime();
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		//JSONObject json = new JSONObject();
		if (getLrjo() == null) {			
			setLrjo(new JSONObject());
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			String sbstring = sb.toString();
			logInInfo("接收到的数据：" + sbstring);
			JSONObject object = JSONObject.fromObject(sbstring);
			// String imei = object.getString("imei");// 以用户确认的为主
			String iccid = object.optString("iccid");
			String token = object.optString("token");
			// String cardNumber = object.getString("cardNumber"); ios获取不到这个卡号
			String frequency = object.optString("frequency");// 表示月的个数
			String count = object.optString("count");

			String cardName = object.optString("card_name");// 信用卡名字
			String zipCode = object.optString("zip_code");// 邮编

			// 0表示扣一次 1表示自动扣

			// String email = object.getString("email");
			String userId = object.optString("user_id");
			String userToken = object.optString("app_token");

			String belongCompany = "1";
			// iccid 属于哪个公司 1表示爱施德 2表示树米0表示twilio

			// String imsi = object.getString("imsi");

			DeviceActiveInfo vo1 = new DeviceActiveInfo();
			/*
			 * vo1.setCondition("iccid ='" + iccid + "' limit 1"); List<DataMap>
			 * lsitimei = ServiceBean.getInstance()
			 * .getDeviceActiveInfoFacade().getDeviceActiveInfo(vo1); if
			 * (lsitimei.size() <= 0) {
			 */
			vo1.setCondition("iccid ='" + iccid + "'");
			List<DataMap> lsitCard = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getSsidInfo(vo1);
			String imsi = "";
			// String cardStatus="";
			if (lsitCard.size() > 0) {
				imsi = lsitCard.get(0).get("imsi") + "";
				belongCompany = lsitCard.get(0).get("belong_company") + "";
				// cardStatus=lsitCard.get(0).get("card_status") + "";
			}
			logInInfo( "此时的imsi=" + imsi + "----belongCompany="
					+ belongCompany);
			/*
			 * vo1.setCondition("device_imei ='" + imei + "'");
			 * vo1.setIccid(iccid); vo1.setBelongCompany(belongCompany);
			 * 
			 * ServiceBean.getInstance().getDeviceActiveInfoFacade()
			 * .updateDeviceActiveInfo(vo1);
			 */

			/*
			 * String cardStatus=""; if(lsitCard.size()>0){
			 * cardStatus=lsitCard.get(0).get("card_status")+"";
			 * //卡片状态：Activate,Suspend,Resume 1 2 3 }
			 */

			// String cardStatus = object.getString("card_status");

			// ^ obtained with Stripe.js
			Integer status = null;
			String message = "";
			String customerId = "";
			String subscriptionId = "";
			DeviceActiveInfo vo = new DeviceActiveInfo();
			int receive = 0;
			int frequencyInt = Integer.valueOf(frequency);
			String email = object.optString("email");
			

			String receiptNumber = "";
			/*
			 * PhoneInfo po = new PhoneInfo(); po.setCondition("device_imei ='"
			 * + imei + "' limit 1");
			 * 
			 * List<DataMap> listP =
			 * ServiceBean.getInstance().getPhoneInfoFacade
			 * ().getPWdeviceActiveInfo(po);// String iccid = ""; /* if
			 * (listP.size() > 0) { iccid = listP.get(0).get("iccid") + "";
			 */

			if (!"null".equals(iccid)) {

				logInInfo(  "开始支付前------------------------");
				try {
					// if (userId != null && !"".equals(userId)) {

					/*
					 * AppUserInfo uo = new AppUserInfo();
					 * uo.setCondition("user_id='" + userId + "'");
					 * List<DataMap> AppUserList = ServiceBean.getInstance()
					 * .getAppUserInfoFacade().getAppUserInfo(uo); if
					 * (AppUserList.size() > 0) { email =
					 * AppUserList.get(0).get("email") + ""; }
					 */
					// }

					// Stripe.apiKey = "sk_live_LgeCgzCDGRdV9R6G6cdLQaIu";
					// Test apiKey
					Stripe.apiKey = Constant.STRIPE_APIKEY;
					// live scret key
					Map<String, Object> customerParams = new HashMap<String, Object>();
					customerParams.put("email", email);
					customerParams.put("source", token);
					Customer customer = Customer.create(customerParams);
					customerId = customer.getId();

					logInInfo( "支付用户id==" + customerId);
					// Charge the Customer instead of the card:

					/*
					 * WTSigninAction ba = new WTSigninAction();
					 * ba.insertWMonitor("0", "0", "0",
					 * iccid+": new 支付了"+frequency+"个月的费用"); ba = null;
					 */

					if ("0".equals(count)) {
						// 0表示扣一次
						Map<String, Object> chargeParams = new HashMap<String, Object>();
						if (frequencyInt == 1) {
							receive = ComPay.OONTH;
							chargeParams.put("amount", Constant.OONTH);// 美分
						} else if (frequencyInt == 6) {
							receive = ComPay.SONTH;
							chargeParams.put("amount", Constant.SONTH);// 美分
						} else if (frequencyInt == 12) {
							receive = ComPay.TONTH;
							chargeParams.put("amount", Constant.TONTH);// 美分
						}
						// chargeParams.put("amount", coin * frequencyInt);// 美分
						chargeParams.put("currency", "usd");
						chargeParams.put("customer", customerId);
						chargeParams.put("description", iccid + "支付了"
								+ frequency + "个月的费用");

						Charge charge = Charge.create(chargeParams);
						// receive = coin * frequencyInt;

						receiptNumber = charge.getReceiptNumber();
						logInInfo( "支付记录的number=" + receiptNumber);

					} else if ("1".equals(count)) {
						// 1表示自动扣
						/*
						 * Map<String, Object> chargeParams = new
						 * HashMap<String, Object>(); chargeParams.put("amount",
						 * 5000); chargeParams.put("currency", "usd");
						 * chargeParams.put("customer", customerId);
						 */
						// Charge charge = Charge.create(chargeParams);
						// 创建订阅
						String plan = "one_month";
						if ("1".equals(frequency)) {
							plan = "one_month";
						} else if ("3".equals(frequency)) {
							plan = "three_month";
						} else if ("6".equals(frequency)) {
							plan = "six_month";
						} else if ("12".equals(frequency)) {
							plan = "twelve_month";
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("plan", plan);
						// one_month在后台自定义
						params.put("customer", customerId);
						params.put("description", iccid + "支付了" + frequency
								+ "个月的费用");
						Subscription.create(params);

						// 若是订阅则需要保存订阅成功的id
						subscriptionId = Subscription.create(params).getId();
						/* if创建一个订阅计划，将立即向客户开始收取费用 */
						receive = frequencyInt * coin;

					}

					status = 200;
					message = "ok";
				} catch (AuthenticationException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (InvalidRequestException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
					status + "---"
							+ message);
				} catch (APIConnectionException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo("支付异常状态和message========" + 
							status + "---" + message);
				} catch (CardException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} catch (APIException e) {
					status = e.getStatusCode();
					message = e.getMessage();
					logInInfo( "支付异常状态和message========" + 
							status + "---" + message);
				} finally {
					logInInfo( "支付状态为------------=" + status);
					if (status == 200) {
						getLrjo().put(Constant.RESULTCODE, Constant.SUCCESS_CODE);
					} else {
						getLrjo().put(Constant.RESULTCODE, status);
					}
					getLrjo().put(Constant.EXCEPTION, message);
					getLrjo().put("iccid", iccid);

					vo.setDeviceImei(iccid);
					vo.setCreateTime(sf.format(new Date()));
					vo.setReceive(receive);
					vo.setCardStatus(status + "");// 200
					vo.setMessage(message);
					vo.setCurrency("usd");
					vo.setDescription(frequency);
					vo.setSource(token);
					vo.setFriendlyName("000000000000000");// 全部默认为这个
					vo.setBrandName(customerId);
					vo.setEmail(email);
					vo.setSubscriptionId(subscriptionId);
					vo.setRatePlan(count);
					vo.setCardName(cardName);
					vo.setZipCode(zipCode);
					vo.setReceiptNumber(receiptNumber);
					/*
					 * if (listP.size() > 0) { iccid = listP.get(0).get("iccid")
					 * + ""; vo.setIccid(iccid); } else {
					 */
					vo.setIccid(iccid);
					// }
					vo.setSubStatus("1");
					vo.setBelongCompany(belongCompany);

					int insertCount = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade().insertPayForInfo(vo);
					logInInfo( "支付添加结果为-----insertCount------=" + insertCount);
					if (insertCount != 1) {
						ServiceBean.getInstance().getDeviceActiveInfoFacade()
								.insertPayForInfo(vo);
					}
					result = 1;

					if (status == 200) {
						logInInfo( "状态为200为支付成功开始走激活---------");
						vo.setCondition("imei ='"
								+ iccid
								+ "'and iccid='"
								+ iccid
								+ "' and card_status='200' and message='ok' and belong_company='"
								+ belongCompany + "' limit 1");
						List<DataMap> cancelList = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.getcancleImeiInfo(vo);
						String jihuo = "active";

						if ("0".equals(count)) {
							// 0表示扣一次
							String stopTime = "";
							if (cancelList.size() <= 0) {
								logInInfo( "以前有支付信息直接续期-----");
								// 2表示六个月
								vo.setDeviceImei(iccid);
								vo.setCreateTime(sf.format(new Date()));
								vo.setCardStatus(status + "");
								vo.setMessage(message);
								vo.setBrandName(customerId);
								vo.setDeviceAge("2");

								Calendar c = Calendar.getInstance();// 获得一个日历的实例
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								//yonghu memo
								//Date date = new Date();
								//stopTime = new Date() + "";
								//try {
								//	date = sdf.parse(stopTime);// 初始日期
								//} catch (Exception e) {
								//
								//}
								Date date= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期

								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt + 1);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到frequencyInt个月后的日期

								vo.setUpdateTime(stopTime);
								vo.setIccid(iccid);
								vo.setBelongCompany(belongCompany);
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("insert");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);

								jihuo = "active";
							} else {
								stopTime = cancelList.get(0).get("stop_time")
										+ "";

								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								String timeStop = sdf.format(new Date());

								//Date dt1 = sdf.parse(timeStop);// 今天的时间
								//Date dt2 = sdf.parse(stopTime);// 到期的时间
								Date dt1= (Date)sdf.parseObject(sdf.format(new Date()));//设置初始日期
								Date dt2= (Date)sdf.parseObject(stopTime);//设置初始日期

								
								Calendar c = Calendar.getInstance();// 获得一个日历的实例

								Date date = null;
								if (dt1.getTime() >= dt2.getTime()) {
									date = dt1;	// 初始日期
								} else {
									date = dt2;// 初始日期
								}

								c.setTime(date);// 设置日历时间
								c.add(Calendar.MONTH, frequencyInt);// 在日历的月份上增加frequencyInt个月

								stopTime = sdf.format(c.getTime());// 得到6个月后的日期
								String id = cancelList.get(0).get("id") + "";

								vo.setUpdateTime(stopTime);
								vo.setCondition("id='" + id + "'");
								int xiugai = ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateCancelSubSriptionInfo(vo);

								vo.setDeviceImei(iccid);
								vo.setCardStatus(status + "");
								vo.setCreateTime(sf.format(new Date()));
								vo.setUpdateTime(stopTime);
								vo.setBelongCompany(belongCompany);
								vo.setMessage("update");

								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.insertCancelSubSriptionLogInfo(vo);
								jihuo = "active";
							}

						} else if ("1".equals(count)) {
							jihuo = "active";
						} else {
							jihuo = "notActive";
						}
						logInInfo( "jihuo=================" + jihuo);
						if ("active".equals(jihuo)) {
							if (iccid.length() > 0 && iccid != null
									&& !"".equals(iccid)) {

								if ("0".equals(belongCompany)) {

									vo1.setCondition("iccid ='" + iccid
											+ "' limit 1");
									String sid = "";
									List<DataMap> list = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(vo1);

									if (list.size() <= 0) {
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										ResourceSet<Sim> sims = Sim.reader()
												.read();
										for (Sim sim : sims) {
											if (iccid.equals(sim.getIccid())) {
												sid = sim.getSid();
												vo1.setSid(sid);
												vo1.setIccid(iccid);
												vo1.setCardStatus("1");
												vo1.setFriendlyName(sim
														.getFriendlyName());
												vo1.setRatePlan(sim
														.getRatePlanSid());
												vo1.setCreateTime(sim
														.getDateCreated() + "");
												vo1.setUpdateTime(sim
														.getDateUpdated() + "");
												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.insertSmsInfo(vo1);

												// 激活
												Twilio.init(ACCOUNT_SID,
														AUTH_TOKEN);
												Sim updatedSim = Sim
														.updater(sid)
														.setRatePlan("data100")
														.setStatus("active")
														.update();
												getLrjo().put("card_status", "激活成功");
												// break;
											} else {
												vo1.setCondition("iccid ='"
														+ sim.getIccid()
														+ "' limit 1");
												List<DataMap> listSelect = ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.getSsidInfo(vo1);
												if (listSelect.size() <= 0) {
													vo1.setSid(sim.getSid());
													vo1.setIccid(sim.getIccid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													vo1.setFriendlyName(sim
															.getFriendlyName());
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCreateTime(sim
															.getDateCreated()
															+ "");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.insertSmsInfo(vo1);
												} else {
													vo1.setCondition("iccid ='"
															+ sim.getIccid()
															+ "'");
													vo1.setUpdateTime(sim
															.getDateUpdated()
															+ "");
													vo1.setRatePlan(sim
															.getRatePlanSid());
													vo1.setCardStatus(sim
															.getStatus()
															.equals("active") ? "1"
															: "0");
													ServiceBean
															.getInstance()
															.getDeviceActiveInfoFacade()
															.updateDeviceSmsInfo(
																	vo1);
												}
											}
										}
										result = 1;
									} else {
										sid = list.get(0).get("sid") + "";
										// 激活
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										Sim updatedSim = Sim.updater(sid)
												.setRatePlan("data100")
												.setStatus("active").update();
										getLrjo().put("card_status", "激活成功");
										vo1.setCardStatus("1");
										vo1.setCondition("iccid ='" + iccid
												+ "'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);
										result = 1;
									}
								} else if ("1".equals(belongCompany)) {
									// 1表示爱施德
									String Status = AiShiDeIccidApi.getStatus(
											iccid, imsi);
									logInInfo( "此ICCID卡的状态==" + Status);
									if ("Active".equals(Status)) {

									} else if ("Suspended".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Resume");
									} else if ("Preactive".equals(Status)) {
										AiShiDeIccidApiV2.setIccidStatus(iccid,
												imsi, "Activate");
									}

									for (int n = 0; n < 3; n++) {
										Status = AiShiDeIccidApi.getStatus(
												iccid, imsi);
										logInInfo("激活后卡的状态为="
												+ Status);
										if (!"Active".equals(Status)) {
											if ("Suspended".equals(Status)) {
												AiShiDeIccidApiV2.setIccidStatus(
														iccid, imsi, "Resume");
											} else if ("Preactive"
													.equals(Status)) {
												AiShiDeIccidApiV2
														.setIccidStatus(iccid,
																imsi,
																"Activate");
											}
										}
										break;
									}

									getLrjo().put("card_status", "激活成功");
									result = 1;
									vo1.setCardStatus("1");
									vo1.setCondition("iccid ='" + iccid
											+ "'and belong_company='1'");
									ServiceBean.getInstance()
											.getDeviceActiveInfoFacade()
											.updateDeviceSmsInfo(vo1);

									/*
									 * if ("4".equals(cardStatus)) {// 4就是新的
									 * resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Activate");
									 * } else if ("2".equals(cardStatus)) {
									 * resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Resume"); }
									 * else { resultResponse = AiShiDeIccidApi
									 * .setIccidStatus(iccid, imsi, "Activate");
									 * } JSONObject resultResponseJson =
									 * JSONObject .fromObject(resultResponse);
									 * String respCode = resultResponseJson
									 * .getString("resp_code"); if
									 * ("1".equals(respCode)) {
									 * json.put("card_status", "激活成功");
									 * vo1.setCardStatus("1");
									 * vo1.setCondition("iccid ='" + iccid +
									 * "'and belong_company='1'");
									 * ServiceBean.getInstance()
									 * .getDeviceActiveInfoFacade()
									 * .updateDeviceSmsInfo(vo1); result = 1; }
									 * else { json.put("card_status", "激活失败");
									 * result = 0; }
									 */
								} else if ("2".equals(belongCompany)) {

									String resule = ShuMiIccidApi
											.updateIccidCardStatus(iccid, "0");
									JSONObject resuleResponseJson = JSONObject
											.fromObject(resule);
									if ("0".equals(resuleResponseJson
											.get("code"))) {
										String resultt = ShuMiIccidApi
												.IccidSubscription(iccid,
														"00000050001");
										JSONObject resulttResponseJson = JSONObject
												.fromObject(resultt);
										if ("0".equals(resulttResponseJson
												.get("code"))) {
											getLrjo().put("card_status", "激活成功");
											vo1.setCardStatus("1");
											vo1.setCondition("iccid ='" + iccid
													+ "'and belong_company='2'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo1);
											result = 1;
										}
									} else {
										getLrjo().put("card_status", "激活失败");
										result = 0;
									}
								}
							} else {
								logInInfo("iccid为空");
								result = 2;
								getLrjo().put("card_status", "未找到对应的iccid");
							}
							/*
							 * } else { json.put("card_status", "未查到对应的IMEI");
							 * logger.info("未查到对应的IMEI--"); result = 0; }
							 */

						}
					} else {
						getLrjo().put("card_status", "支付失败");
					}
				}

			} else {
				getLrjo().put(Constant.EXCEPTION, "存在IMEI没有iccid");
				getLrjo().put("iccid", iccid);
				getLrjo().put(Constant.RESULTCODE, 0);
			}
			/*
			 * } else { json.put(Constant.RESULTCODE, 0); json.put("imei",
			 * imei); json.put(Constant.EXCEPTION, "不存在这个IMEI"); }
			 */

			/*
			 * } else { json.put(Constant.EXCEPTION, "SIM卡已绑定过设备");
			 * json.put(Constant.RESULTCODE, 0); json.put("iccid", iccid); }
			 */
			getLrjo().put("request", href);
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
			getLrjo().put(Constant.EXCEPTION, sb.toString());
		}
		// json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getLrjo().toString());
		return null;
	}

	/*
	 * public static void main(String[] args) throws SystemException {
	 * DeviceActiveInfo vo = new DeviceActiveInfo(); SimpleDateFormat sf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); vo.setDeviceImei("1");
	 * vo.setCreateTime(sf.format(new Date())); vo.setCardStatus("200");
	 * vo.setMessage("1"); vo.setBrandName("1"); vo.setDeviceAge("2");
	 * 
	 * vo.setUpdateTime("2015-06-14"); vo.setIccid("1");
	 * vo.setBelongCompany("1"); ServiceBean.getInstance()
	 * .getDeviceActiveInfoFacade() .insertCancelSubSriptionInfo(vo);
	 * 
	 * vo.setDeviceImei("1"); vo.setCreateTime(sf.format(new Date()));
	 * vo.setUpdateTime("2015-06-14"); vo.setBelongCompany("1");
	 * //vo.setCardStatus("200");
	 * 
	 * ServiceBean.getInstance() .getDeviceActiveInfoFacade()
	 * .insertCancelSubSriptionLogInfo(vo);
	 * 
	 * }
	 */

	/*
	 * public static void main(String[] args) { DeviceActiveInfo vo1 = new
	 * DeviceActiveInfo();
	 * 
	 * String imei="352138064953575"; vo1.setCondition("device_imei ='" +
	 * imei+"'"); vo1.setIccid("5"); vo1.setBelongCompany("5"); try {
	 * ServiceBean.getInstance() .getDeviceActiveInfoFacade()
	 * .updateDeviceActiveInfo(vo1); } catch (SystemException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */
}