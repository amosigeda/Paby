package com.wtwd.sys.innerw.wsaleInfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class WsaleInfoForm extends PublicFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2027966078274507729L;
	private Integer id;
	private String user_name;
	private Integer user_id;
	private String imei;
	private String imsi;
	private String phone;
	private String phone_model;
	private String sys_version;
	private String province;
	private Integer belong_project;
	private Date date_time;
	private String ip;
	private String app_version;
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
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * @return the imsi
	 */
	public String getImsi() {
		return imsi;
	}
	/**
	 * @param imsi the imsi to set
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the phone_model
	 */
	public String getPhone_model() {
		return phone_model;
	}
	/**
	 * @param phone_model the phone_model to set
	 */
	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}
	/**
	 * @return the sys_version
	 */
	public String getSys_version() {
		return sys_version;
	}
	/**
	 * @param sys_version the sys_version to set
	 */
	public void setSys_version(String sys_version) {
		this.sys_version = sys_version;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
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
	 * @return the date_time
	 */
	public Date getDate_time() {
		return date_time;
	}
	/**
	 * @param date_time the date_time to set
	 */
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the app_version
	 */
	public String getApp_version() {
		return app_version;
	}
	/**
	 * @param app_version the app_version to set
	 */
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	
}
