package com.wtwd.sys.innerw.wefencing.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WefencingForm extends PublicFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617993599136904392L;
	private Integer id;	
	private Float center_gps_lo;		//longitude
	private Float center_gps_la;
	private Integer round_distance;
	private String ext1;
	private String ext2;
	private String if_active;
	private String if_prompt_user;
	private Integer device_id;
	private String center_addr_cn;	//chinese lang.
	private String center_addr;	//english lang.
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
	 * @return the center_gps_lo
	 */
	public Float getCenter_gps_lo() {
		return center_gps_lo;
	}
	/**
	 * @param center_gps_lo the center_gps_lo to set
	 */
	public void setCenter_gps_lo(Float center_gps_lo) {
		this.center_gps_lo = center_gps_lo;
	}
	/**
	 * @return the center_gps_la
	 */
	public Float getCenter_gps_la() {
		return center_gps_la;
	}
	/**
	 * @param center_gps_la the center_gps_la to set
	 */
	public void setCenter_gps_la(Float center_gps_la) {
		this.center_gps_la = center_gps_la;
	}
	/**
	 * @return the round_distance
	 */
	public Integer getRound_distance() {
		return round_distance;
	}
	/**
	 * @param round_distance the round_distance to set
	 */
	public void setRound_distance(Integer round_distance) {
		this.round_distance = round_distance;
	}
	/**
	 * @return the ext1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * @param ext1 the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * @return the ext2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * @param ext2 the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * @return the if_active
	 */
	public String getIf_active() {
		return if_active;
	}
	/**
	 * @param if_active the if_active to set
	 */
	public void setIf_active(String if_active) {
		this.if_active = if_active;
	}
	/**
	 * @return the if_prompt_user
	 */
	public String getIf_prompt_user() {
		return if_prompt_user;
	}
	/**
	 * @param if_prompt_user the if_prompt_user to set
	 */
	public void setIf_prompt_user(String if_prompt_user) {
		this.if_prompt_user = if_prompt_user;
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
	 * @return the center_addr_cn
	 */
	public String getCenter_addr_cn() {
		return center_addr_cn;
	}
	/**
	 * @param center_addr_cn the center_addr_cn to set
	 */
	public void setCenter_addr_cn(String center_addr_cn) {
		this.center_addr_cn = center_addr_cn;
	}
	/**
	 * @return the center_addr
	 */
	public String getCenter_addr() {
		return center_addr;
	}
	/**
	 * @param center_addr the center_addr to set
	 */
	public void setCenter_addr(String center_addr) {
		this.center_addr = center_addr;
	}
	
	
}
