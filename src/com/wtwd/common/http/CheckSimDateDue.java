package com.wtwd.common.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import net.sf.json.JSONObject;
import test.com.wtwd.wtpet.service.AiShiDeIccidApi;
import test.com.wtwd.wtpet.service.AiShiDeIccidApiV2;
import test.com.wtwd.wtpet.service.ShuMiIccidApi;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Subscription;
import com.twilio.Twilio;
import com.twilio.rest.preview.wireless.Sim;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.deviceactiveinfo.domain.DeviceActiveInfo;

public class CheckSimDateDue extends TimerTask {

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	public static final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
	public static final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";

	 public void executePetDbDump() {
		 try {
	         Runtime run = Runtime.getRuntime();
	         File wd = new File("/bin");
	         System.out.println(wd);
	         Process proc = null;
	         try {
	             proc = run.exec("/bin/bash", null, wd);
	         } catch (IOException e) {
	             e.printStackTrace();
	        }
	        if (proc != null) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
	            out.println("cd /mnt/data/dump");
	            out.print("mysqldump -uroot -p'");
	            Tools tls = new Tools();
	            out.print(tls.getDbPwd().trim());
	            out.println("' dbdog > dbdog.dump");
	            out.println("tar zcvf dump" +  tls.getStringFullFromDate(new Date()) + ".tar.gz dbdog.dump");
	            out.println("rm dbdog.dump");
	            out.println("exit");
	            try {
	                String line;
	                while ((line = in.readLine()) != null) {
	                    System.out.println(line);
	                }
	                proc.waitFor();
	                in.close();
	                out.close();
	                proc.destroy();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	
	@Override
	public void run() {
	//	retrieveSubscrition();

		
		try {
			if ( Constant.IS_APPSERVER_8087_MYSQL_DUMP )
				executePetDbDump();
			
			DeviceActiveInfo vo = new DeviceActiveInfo();
			
			vo.setCondition("card_status='200' and stop_time='"
					+ sf.format(new Date()) + "'");
			List<DataMap> list = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade().getcancleImeiInfo(vo);

			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					//String imei = list.get(i).get("imei") + "";
					String belongCompany=list.get(i).get("belong_company")+"";
					String iccid=list.get(i).get("iccid")+"";
					
					if("1".equals(belongCompany)){
						//1???? 2???0twilio
						
						vo.setCondition("iccid='"+iccid+"' and belong_company='1' limit 1");
						List<DataMap> listSim = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade().getSsidInfo(vo);
						if(listSim.size()>0){
							String imsi=listSim.get(0).get("imsi")+"";
							//Activate,Suspend,Deactivate
							try {
								/*String	resultResponse = AiShiDeIccidApi.setIccidStatus(iccidNe, imsi, "Suspend");
								JSONObject resultResponseJson = JSONObject.fromObject(resultResponse);
								String respCode=resultResponseJson.getString("resp_code");*/
								
								String resultResponsee = AiShiDeIccidApi.selectIccidStatus(iccid,
										imsi);
								JSONObject resultResponseJsone = JSONObject
										.fromObject(resultResponsee);
								String datae = resultResponseJsone.getString("data");
								String respCode = resultResponseJsone.getString("resp_code");
								
								if ("1".equals(respCode)) {
									JSONObject dataJsone = JSONObject.fromObject(datae);
									String Status = dataJsone.getString("status");

									
										if ("Active".equals(Status)) {
											String zanting = AiShiDeIccidApiV2.setIccidStatus(
													iccid, imsi, "Suspend");
											System.out.println("????=" + zanting);
										} else if ("Suspended".equals(Status)) {
											System.out.println("????????");
										}
										
										vo.setCardStatus("0");
										vo.setCondition("iccid ='" + iccid
												+ "'and belong_company='1'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo);
									
								
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if("2".equals(belongCompany)){
						String resule = ShuMiIccidApi
								.updateIccidCardStatus(iccid, "1");
						JSONObject resuleResponseJson = JSONObject
								.fromObject(resule);
						if ("0".equals(resuleResponseJson
								.get("code"))){
							vo.setCardStatus("0");
						vo.setCondition("iccid ='" + iccid
								+ "'and belong_company='2'");
						ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.updateDeviceSmsInfo(vo);
							}
						} else if("0".equals(belongCompany)){
							/*PhoneInfo po = new PhoneInfo();
							po.setCondition("device_imei ='" + imei + "' limit 1");
							List<DataMap> listP = ServiceBean.getInstance()
									.getPhoneInfoFacade().getPWdeviceActiveInfo(po);*/

							/*if (listP.size() > 0) {
								String iccid = listP.get(0).get("iccid") + "";*/

								DeviceActiveInfo vo1 = new DeviceActiveInfo();
								vo1.setCondition("iccid ='" + iccid + "' limit 1");

								List<DataMap> listS = ServiceBean.getInstance()
										.getDeviceActiveInfoFacade().getSsidInfo(vo1);

								if (listS.size() > 0) {
									String sid = listS.get(0).get("sid") + "";
									if (sid != null && !"".equals(sid)) {
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										Sim updatedSim = Sim.updater(sid)
												.setStatus("deactivated").update();

										vo1.setCardStatus("0");
										vo1.setCondition("iccid ='" + iccid + "'");
										ServiceBean.getInstance()
												.getDeviceActiveInfoFacade()
												.updateDeviceSmsInfo(vo1);
									}

								}
							//}
						}
					
					

				/*	PhoneInfo po = new PhoneInfo();
					po.setCondition("device_imei ='" + imei + "' limit 1");

					List<DataMap> listP = ServiceBean.getInstance()
							.getPhoneInfoFacade().getPWdeviceActiveInfo(po);

					if (listP.size() > 0) {
						String iccid = listP.get(0).get("iccid") + "";

						DeviceActiveInfo vo1 = new DeviceActiveInfo();
						vo1.setCondition("iccid ='" + iccid + "' limit 1");

						List<DataMap> listS = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade().getSsidInfo(vo1);

						if (listS.size() > 0) {
							String sid = listS.get(0).get("sid") + "";
							if (sid != null && !"".equals(sid)) {
								Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
								Sim updatedSim = Sim.updater(sid)
										.setStatus("deactivated").update();

								vo1.setCardStatus("0");
								vo1.setCondition("iccid ='" + iccid + "'");
								ServiceBean.getInstance()
										.getDeviceActiveInfoFacade()
										.updateDeviceSmsInfo(vo1);
							}

						}
					}*/

				}
			}

		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
//????
	private void retrieveSubscrition() {
		final String ACCOUNT_SID = "AC08d153f6d0fb9a2135f0edd5614229f6";
		final String AUTH_TOKEN = "5fc4e38b694dfd9a530871996a4a038e";
		Stripe.apiKey = Constant.STRIPE_APIKEY;
		try {
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("pay_status='200' and message='ok' and plan_count='1' and sub_status='1'");

			try {
				List<DataMap> listSer = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getPayForInfo(vo);
				if (listSer.size() > 0) {
					for (int i = 0; i < listSer.size(); i++) {
						String subscriptionId = listSer.get(i).get(
								"subscription_id")
								+ "";

						String id = listSer.get(i).get("id") + "";

						if (subscriptionId != null
								&& !"".equals(subscriptionId)) {
							Subscription s = Subscription
									.retrieve(subscriptionId);
							String status = s.getStatus();
							if (!"active".equals(status)) {
								// ??iccid ?????,???sim????

								String iccid = listSer.get(i).get("iccid") + "";

								if (iccid != null && !"".equals(iccid)) {
									vo.setCondition("iccid ='" + iccid
											+ "' limit 1");

									List<DataMap> listS = ServiceBean
											.getInstance()
											.getDeviceActiveInfoFacade()
											.getSsidInfo(vo);
								
									if (listS.size() > 0) {
										String sid = listS.get(0).get("sid")
												+ "";
										if (sid != null && !"".equals(sid)) {
											Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
											Sim updatedSim = Sim.updater(sid)
													.setStatus("inactive")
													.update();

											vo.setCardStatus("0");
											vo.setCondition("iccid ='" + iccid
													+ "'");
											ServiceBean
													.getInstance()
													.getDeviceActiveInfoFacade()
													.updateDeviceSmsInfo(vo);

											if (id != null && !"".equals(id)) {
												vo.setCondition("id='" + id
														+ "'");
												vo.setSubStatus("0");

												ServiceBean
														.getInstance()
														.getDeviceActiveInfoFacade()
														.updatePayForDeviceInfo(
																vo);
											}
										}

									}
								}

							}
						}
					}
				}

			} catch (SystemException e) {
				e.printStackTrace();
			}

		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*public static void main(String[] args) throws SystemException {
	DeviceActiveInfo vo = new DeviceActiveInfo();
	vo.setCardStatus("1");
	vo.setCondition("iccid ='" + 123456
			+ "'and belong_company='1'");
	ServiceBean.getInstance()
			.getDeviceActiveInfoFacade()
			.updateDeviceSmsInfo(vo);
	}*/
}
