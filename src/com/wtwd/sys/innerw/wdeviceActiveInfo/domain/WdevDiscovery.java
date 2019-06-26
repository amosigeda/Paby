package com.wtwd.sys.innerw.wdeviceActiveInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WdevDiscovery extends PublicVoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4650820183954765089L;
	private Integer user_id;
	private Integer device_id;
	private String action_time;
	private Integer how_long;
	private String action_time_utc;

	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}
	
	public String getAction_time() {
		return action_time;
	}
	public void setAction_time(String action_time) {
		this.action_time = action_time;
	}
	public Integer getHow_long() {
		return how_long;
	}
	public void setHow_long(Integer how_long) {
		this.how_long = how_long;
	}
	/**
	 * @return the action_time_utc
	 */
	public String getAction_time_utc() {
		return action_time_utc;
	}
	/**
	 * @param action_time_utc the action_time_utc to set
	 */
	public void setAction_time_utc(String action_time_utc) {
		this.action_time_utc = action_time_utc;
	}

}