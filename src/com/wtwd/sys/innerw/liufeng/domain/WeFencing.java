package com.wtwd.sys.innerw.liufeng.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WeFencing extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = 7819156198990358124L;
	
	private Integer id;
	private Double center_gps_lo;
	private Double center_gps_la;
	private Integer round_distance;
	private Float ext1;
	private Float ext2;
	private char if_active;
	private char if_prompt_user;
	private Integer device_id;
	private String center_addr;
	private String device_safe_name;
	private String device_safe_effect_time;
	private String safe_type;
	private Integer flag;
	
	private String photo;
	private long pst;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getRound_distance() {
		return round_distance;
	}
	public void setRound_distance(Integer round_distance) {
		this.round_distance = round_distance;
	}
	public Float getExt1() {
		return ext1;
	}
	public void setExt1(Float ext1) {
		this.ext1 = ext1;
	}
	public Float getExt2() {
		return ext2;
	}
	public void setExt2(Float ext2) {
		this.ext2 = ext2;
	}
	public char getIf_active() {
		return if_active;
	}
	public void setIf_active(char if_active) {
		this.if_active = if_active;
	}
	public char getIf_prompt_user() {
		return if_prompt_user;
	}
	public void setIf_prompt_user(char if_prompt_user) {
		this.if_prompt_user = if_prompt_user;
	}
	public Integer getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}
	public String getCenter_addr() {
		return center_addr;
	}
	public void setCenter_addr(String center_addr) {
		this.center_addr = center_addr;
	}
	public String getDevice_safe_name() {
		return device_safe_name;
	}
	public void setDevice_safe_name(String device_safe_name) {
		this.device_safe_name = device_safe_name;
	}
	public String getDevice_safe_effect_time() {
		return device_safe_effect_time;
	}
	public void setDevice_safe_effect_time(String device_safe_effect_time) {
		this.device_safe_effect_time = device_safe_effect_time;
	}
	public String getSafe_type() {
		return safe_type;
	}
	public void setSafe_type(String safe_type) {
		this.safe_type = safe_type;
	}
	public Double getCenter_gps_lo() {
		return center_gps_lo;
	}
	public void setCenter_gps_lo(Double center_gps_lo) {
		this.center_gps_lo = center_gps_lo;
	}
	public Double getCenter_gps_la() {
		return center_gps_la;
	}
	public void setCenter_gps_la(Double center_gps_la) {
		this.center_gps_la = center_gps_la;
	}
	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * @return the pst
	 */
	public long getPst() {
		return pst;
	}
	/**
	 * @param pst the pst to set
	 */
	public void setPst(long pst) {
		this.pst = pst;
	}
	
}