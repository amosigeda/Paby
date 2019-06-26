package com.wtwd.common.bean.devicedown.cmdobject;

import javax.servlet.http.HttpServletResponse;

public class CmdSync {
	private Thread tdLock = null;
	private HttpServletResponse response = null;
	private String cmdName = null;
	private String cmdPara1 = null;
	private String cmdPara2 = null;
	private Integer user_id = 0;
	private Integer pet_id = 0;
	private String day = null;
	private String ton = null;		//定时开机时间
	private String toff = null;		//定时关机时间
	private Integer device_id = 0;
	private Integer tflag = 0;
	
	private String rstime = null;
	private String retime = null;
	private String devDay = null;

	private Integer dur = 0;
	private String sCp = null;
	
	


	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the tdLock
	 */
	public Thread getTdLock() {
		return tdLock;
	}

	/**
	 * @param tdLock the tdLock to set
	 */
	public void setTdLock(Thread tdLock) {
		this.tdLock = tdLock;
	}

	/**
	 * @return the cmdName
	 */
	public String getCmdName() {
		return cmdName;
	}

	/**
	 * @param cmdName the cmdName to set
	 */
	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	/**
	 * @return the cmdPara1
	 */
	public String getCmdPara1() {
		return cmdPara1;
	}

	/**
	 * @param cmdPara1 the cmdPara1 to set
	 */
	public void setCmdPara1(String cmdPara1) {
		this.cmdPara1 = cmdPara1;
	}

	/**
	 * @return the cmdPara2
	 */
	public String getCmdPara2() {
		return cmdPara2;
	}

	/**
	 * @param cmdPara2 the cmdPara2 to set
	 */
	public void setCmdPara2(String cmdPara2) {
		this.cmdPara2 = cmdPara2;
	}

	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the pet_id
	 */
	public Integer getPet_id() {
		return pet_id;
	}

	/**
	 * @param pet_id the pet_id to set
	 */
	public void setPet_id(Integer pet_id) {
		this.pet_id = pet_id;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the ton
	 */
	public String getTon() {
		return ton;
	}

	/**
	 * @param ton the ton to set
	 */
	public void setTon(String ton) {
		this.ton = ton;
	}

	/**
	 * @return the toff
	 */
	public String getToff() {
		return toff;
	}

	/**
	 * @param toff the toff to set
	 */
	public void setToff(String toff) {
		this.toff = toff;
	}

	/**
	 * @return the device_id
	 */
	public Integer getDevice_id() {
		return device_id;
	}

	/**
	 * @param device_id the device_id to set
	 */
	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}

	/**
	 * @return the tflag
	 */
	public Integer getTflag() {
		return tflag;
	}

	/**
	 * @param tflag the tflag to set
	 */
	public void setTflag(Integer tflag) {
		this.tflag = tflag;
	}

	/**
	 * @return the rstime
	 */
	public String getRstime() {
		return rstime;
	}

	/**
	 * @param rstime the rstime to set
	 */
	public void setRstime(String rstime) {
		this.rstime = rstime;
	}

	/**
	 * @return the retime
	 */
	public String getRetime() {
		return retime;
	}

	/**
	 * @param retime the retime to set
	 */
	public void setRetime(String retime) {
		this.retime = retime;
	}

	/**
	 * @return the devDay
	 */
	public String getDevDay() {
		return devDay;
	}

	/**
	 * @param devDay the devDay to set
	 */
	public void setDevDay(String devDay) {
		this.devDay = devDay;
	}

	/**
	 * @return the dur
	 */
	public Integer getDur() {
		return dur;
	}

	/**
	 * @param dur the dur to set
	 */
	public void setDur(Integer dur) {
		this.dur = dur;
	}

	/**
	 * @return the sCp
	 */
	public String getsCp() {
		return sCp;
	}

	/**
	 * @param sCp the sCp to set
	 */
	public void setsCp(String sCp) {
		this.sCp = sCp;
	}
}
