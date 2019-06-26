package com.wtwd.common.bean.adragon.subcri;



public class SubcriJsonData {

	private String cmd;
		
	private String phoneNum;
	
	//推送奖章
	private String number;
	
	//宝宝生活
	private String index;
	
	
	//查询话费短信指令
	private String smscont;
	
	//天气状态
	private Integer status;
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIson() {
		return ison;
	}

	public void setIson(String ison) {
		this.ison = ison;
	}

	private String repeat;
	
	private String time;
	
	private String ison;
	
	//久坐提醒
	private String dura;
	
	private String sex;
	
	private String birthday;
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDura() {
		return dura;
	}

	public void setDura(String dura) {
		this.dura = dura;
	}

	public String getSmscont() {
		return smscont;
	}

	public void setSmscont(String smscont) {
		this.smscont = smscont;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
