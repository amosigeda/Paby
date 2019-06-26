package com.wtwd.sys.innerw.wshareInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WshareInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7517051141560243829L;
	private Integer id;
	private String user_id;
	private String to_user_id;
//	private String device_imei;
	private Integer device_id;
	private String is_priority;
	private String share_date;
	private Integer belong_project;
	private String status; // 状态，0:等待主人是否同意分享；1：有效；2：主人同意分享；3：主人拒绝分享',  
	private Integer pet_id;

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
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the to_user_id
	 */
	public String getTo_user_id() {
		return to_user_id;
	}
	/**
	 * @param to_user_id the to_user_id to set
	 */
	public void setTo_user_id(String to_user_id) {
		this.to_user_id = to_user_id;
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
	 * @return the is_priority
	 */
	public String getIs_priority() {
		return is_priority;
	}
	/**
	 * @param is_priority the is_priority to set
	 */
	public void setIs_priority(String is_priority) {
		this.is_priority = is_priority;
	}
	/**
	 * @return the share_date
	 */
	public String getShare_date() {
		return share_date;
	}
	/**
	 * @param share_date the share_date to set
	 */
	public void setShare_date(String share_date) {
		this.share_date = share_date;
	}
	/**
	 * @return the belong_project
	 */
	public Integer getBelong_project() {
		return belong_project;
	}
	/**
	 * @param belong_project the belong_project to set
	 */
	public void setBelong_project(Integer belong_project) {
		this.belong_project = belong_project;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
}

