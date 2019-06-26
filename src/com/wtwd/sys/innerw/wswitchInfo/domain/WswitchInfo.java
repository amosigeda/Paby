package com.wtwd.sys.innerw.wswitchInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WswitchInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5493874004668251541L;
	private Integer id;
	private String visit_s;
	private String moni_s;
	private String device_s;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the visit_s
	 */
	public String getVisit_s() {
		return visit_s;
	}
	/**
	 * @param visit_s the visit_s to set
	 */
	public void setVisit_s(String visit_s) {
		this.visit_s = visit_s;
	}
	/**
	 * @return the moni_s
	 */
	public String getMoni_s() {
		return moni_s;
	}
	/**
	 * @param moni_s the moni_s to set
	 */
	public void setMoni_s(String moni_s) {
		this.moni_s = moni_s;
	}
	/**
	 * @return the device_s
	 */
	public String getDevice_s() {
		return device_s;
	}
	/**
	 * @param device_s the device_s to set
	 */
	public void setDevice_s(String device_s) {
		this.device_s = device_s;
	}
	
}
