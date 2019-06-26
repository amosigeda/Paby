package com.wtwd.sys.innerw.wdeviceActiveInfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class WdeviceActiveInfoForm extends PublicFormBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4376233813722789630L;
	private Integer device_id;	
	private String device_phone;
	private String device_imei;
	private String device_name;
	private Date device_update_time;
	private String device_disable;
	private String listen_type;
	private Integer belong_project;
	private String isphoneHelp;
	private String isserverHelp;
	private Date deadline;
	private String brandname;
	private String fall_on;
	private String device_type;
	private String conn_type;
	private String is_sos;
	private String sel_mode;
	private String gps_on;
	private String callback_on;
	private String temperature_on;
	private String heatout_on;
	private String led_on;
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
	 * @return the device_phone
	 */
	public String getDevice_phone() {
		return device_phone;
	}
	/**
	 * @param device_phone the device_phone to set
	 */
	public void setDevice_phone(String device_phone) {
		this.device_phone = device_phone;
	}
	/**
	 * @return the device_imei
	 */
	public String getDevice_imei() {
		return device_imei;
	}
	/**
	 * @param device_imei the device_imei to set
	 */
	public void setDevice_imei(String device_imei) {
		this.device_imei = device_imei;
	}
	/**
	 * @return the device_name
	 */
	public String getDevice_name() {
		return device_name;
	}
	/**
	 * @param device_name the device_name to set
	 */
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	/**
	 * @return the device_update_time
	 */
	public Date getDevice_update_time() {
		return device_update_time;
	}
	/**
	 * @param device_update_time the device_update_time to set
	 */
	public void setDevice_update_time(Date device_update_time) {
		this.device_update_time = device_update_time;
	}
	/**
	 * @return the device_disable
	 */
	public String getDevice_disable() {
		return device_disable;
	}
	/**
	 * @param device_disable the device_disable to set
	 */
	public void setDevice_disable(String device_disable) {
		this.device_disable = device_disable;
	}
	/**
	 * @return the listen_type
	 */
	public String getListen_type() {
		return listen_type;
	}
	/**
	 * @param listen_type the listen_type to set
	 */
	public void setListen_type(String listen_type) {
		this.listen_type = listen_type;
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
	 * @return the isphoneHelp
	 */
	public String getIsphoneHelp() {
		return isphoneHelp;
	}
	/**
	 * @param isphoneHelp the isphoneHelp to set
	 */
	public void setIsphoneHelp(String isphoneHelp) {
		this.isphoneHelp = isphoneHelp;
	}
	/**
	 * @return the isserverHelp
	 */
	public String getIsserverHelp() {
		return isserverHelp;
	}
	/**
	 * @param isserverHelp the isserverHelp to set
	 */
	public void setIsserverHelp(String isserverHelp) {
		this.isserverHelp = isserverHelp;
	}
	/**
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	/**
	 * @return the brandname
	 */
	public String getBrandname() {
		return brandname;
	}
	/**
	 * @param brandname the brandname to set
	 */
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	/**
	 * @return the device_type
	 */
	public String getDevice_type() {
		return device_type;
	}
	/**
	 * @param device_type the device_type to set
	 */
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	/**
	 * @return the fall_on
	 */
	public String getFall_on() {
		return fall_on;
	}
	/**
	 * @param fall_on the fall_on to set
	 */
	public void setFall_on(String fall_on) {
		this.fall_on = fall_on;
	}
	/**
	 * @return the conn_type
	 */
	public String getConn_type() {
		return conn_type;
	}
	/**
	 * @param conn_type the conn_type to set
	 */
	public void setConn_type(String conn_type) {
		this.conn_type = conn_type;
	}
	/**
	 * @return the sel_mode
	 */
	public String getSel_mode() {
		return sel_mode;
	}
	/**
	 * @param sel_mode the sel_mode to set
	 */
	public void setSel_mode(String sel_mode) {
		this.sel_mode = sel_mode;
	}
	/**
	 * @return the is_sos
	 */
	public String getIs_sos() {
		return is_sos;
	}
	/**
	 * @param is_sos the is_sos to set
	 */
	public void setIs_sos(String is_sos) {
		this.is_sos = is_sos;
	}
	/**
	 * @return the gps_on
	 */
	public String getGps_on() {
		return gps_on;
	}
	/**
	 * @param gps_on the gps_on to set
	 */
	public void setGps_on(String gps_on) {
		this.gps_on = gps_on;
	}
	/**
	 * @return the callback_on
	 */
	public String getCallback_on() {
		return callback_on;
	}
	/**
	 * @param callback_on the callback_on to set
	 */
	public void setCallback_on(String callback_on) {
		this.callback_on = callback_on;
	}
	/**
	 * @return the heatout_on
	 */
	public String getHeatout_on() {
		return heatout_on;
	}
	/**
	 * @param heatout_on the heatout_on to set
	 */
	public void setHeatout_on(String heatout_on) {
		this.heatout_on = heatout_on;
	}
	/**
	 * @return the temperature_on
	 */
	public String getTemperature_on() {
		return temperature_on;
	}
	/**
	 * @param temperature_on the temperature_on to set
	 */
	public void setTemperature_on(String temperature_on) {
		this.temperature_on = temperature_on;
	}
	/**
	 * @return the led_on
	 */
	public String getLed_on() {
		return led_on;
	}
	/**
	 * @param led_on the led_on to set
	 */
	public void setLed_on(String led_on) {
		this.led_on = led_on;
	}
	
}
