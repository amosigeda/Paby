package com.wtwd.sys.appinterfaces.newpay;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ComPay {
	
	public enum ActionEnum { ACTION_ACT_TRAN, ACTION_DEACT_TRAN, ACTION_ACT_EXCEPT, ACTION_DEACT_EXCEPT }
	
	public final static int TONTH=3588;
	public final static int SONTH=2094;
	public final static int OONTH=399;
	public final static String tprice="2.99";
	public final static String sprice="3.49";
	public final static String oprice="3.99";
	public final static String T_ONTH="35.88";
	public final static String S_ONTH="20.94";
	public final static String O_ONTH="3.99";

	
	public final static String TXT_REC_PAY_TRAN_FILE = "recPayFile";
	public final static String TXT_REC_PAY_DB_FILE = "recPayDbFile";
	
	public final static String TXT_OS_WINDOWS_7 = "Windows 7";
	public final static String TXT_LOGFILENAME_MIDFIXWT = "_wticcid_";
	public final static String TXT_LOGFILENAME_MIDFIXFT = "_fticcid_";

	
	public final static String TXT_USD = "usd";
	public final static String TXT_FRIENDLY_NAME = "000000000000000";
	
	public final static String TXT_MSG = "msg";
	public final static String TXT_FAULT_ICCID = "fault iccid, not a paby sim card";
	public final static String TXT_EMPTY_ICCID = "fault:empty iccid";
	public final static String TXT_PAY_LOG_TAG = "<<pay>>:";

	
	
	//"D:\\Workspaces\\wtwd_notify_liu\\";
	public final static String DIR_PAY_REC = "D:\\Workspaces\\pay\\";
	public final static String DIR_HOST_PAY_REC = "/mnt/pdata/";
	
	
	public final static String DIR_PAY_ACTIVATE_REC = "D:\\Workspaces\\pay\\";
	public final static String DIR_HOST_PAY_ACTIVATE_REC = "/mnt/pdata/act/";
	public final static String TXT_MID_ACT_TRANS_OF_FILENAME = "_mgr_act_";
	public final static String TXT_MID_DEACT_TRANS_OF_FILENAME = "_mgr_deact_";

	public final static String TXT_MID_EXP_ACT_TRANS_OF_FILENAME = "_exp_act_";
	public final static String TXT_MID_EXP_DEACT_TRANS_OF_FILENAME = "_exp_deact_";
	
	
	public enum ActActionEnum { ACTION_ACT_TRAN, ACTION_DEACT_TRAN, ACTION_ACT_EXCEPT, ACTION_DEACT_EXCEPT }
	
	
	//用于文件名的拼接
	public String getStringFromDate_nmc(final Date data){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		return df.format(data); 
	}

	
	String getFileCom(String piccid, ActionEnum ae) {
		//TXT_OS_WINDOWS_7
		String sdir = (System.getProperty("os.name").
				 equals(ComPay.TXT_OS_WINDOWS_7)) ? 
						ComPay.DIR_PAY_ACTIVATE_REC :
						ComPay.DIR_HOST_PAY_ACTIVATE_REC;												

		String mid_act = (ae == ActionEnum.ACTION_ACT_TRAN) ? 
				TXT_MID_ACT_TRANS_OF_FILENAME : 
				TXT_MID_DEACT_TRANS_OF_FILENAME;

		if ( ae == ActionEnum.ACTION_ACT_TRAN )
			mid_act = TXT_MID_ACT_TRANS_OF_FILENAME;
	    else if ( ae == ActionEnum.ACTION_DEACT_TRAN )
			mid_act = TXT_MID_DEACT_TRANS_OF_FILENAME;
	    else if ( ae == ActionEnum.ACTION_ACT_EXCEPT )
			mid_act = TXT_MID_EXP_ACT_TRANS_OF_FILENAME;
	    else if ( ae == ActionEnum.ACTION_DEACT_EXCEPT )
			mid_act = TXT_MID_EXP_DEACT_TRANS_OF_FILENAME;
		
		
		StringBuffer fname = new StringBuffer(); 
		fname.append(sdir)
			.append(getStringFromDate_nmc(new Date()))
			.append(mid_act)
			.append(piccid);					

		return fname.toString();
		
	}
	
	

	// 将byte数组写入文件
	public void createFile(String path, byte[] content)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(content);
		fos.close();
	}   	
	
	public void recActFile(String piccid, ActActionEnum ae) throws UnsupportedEncodingException, IOException {
	/*	String fname = getFileCom(piccid, ae);
		createFile(fname, piccid.getBytes("UTF-8"));*/
	}


	//记录激活异常状态文件
	public void recActExpFile(String piccid, ActActionEnum ae) throws UnsupportedEncodingException, IOException {
	/*	String fname = getFileCom(piccid, ae);
		createFile(fname, piccid.getBytes("UTF-8"));*/
	}


	public void recActFile(String piccid, ActionEnum ae) throws UnsupportedEncodingException, IOException {
		String fname = getFileCom(piccid, ae);
		//createFile(fname, piccid.getBytes("UTF-8"));			
		createFile(fname, piccid.getBytes("UTF-8"));
	}

	
	
	
}
