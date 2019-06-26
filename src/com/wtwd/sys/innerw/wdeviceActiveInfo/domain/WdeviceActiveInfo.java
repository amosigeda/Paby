﻿package com.wtwd.sys.innerw.wdeviceActiveInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WdeviceActiveInfo extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4735422288548330532L;
	private Integer device_id = null;	
	private String device_phone = null;
	private String device_imei = null;
	private String device_name = null;
	private Date device_update_time = null;
	private String device_disable = null;
	private String listen_type = null;
	private Integer belong_project = null;
	private String isphoneHelp = null;
	private String isserverHelp = null;
	private Date deadline = null;
	private String brandname = null;
	private String fall_on = null;
	private String device_type = null;
	private String conn_type = null;
	private String is_sos = null;
	private String sel_mode = null;
	private String gps_on = null;
	private String callback_on = null;
	private String temperature_on = null;
	private String heatout_on = null;
	private String led_on = null;
	private String flight_mode = null;
	private String urgent_mode = null;
	private String battery = null;
	private String is_lowbat = null;

	private String data_mute = null;
	private String data_volume = null;
	private String data_power = null;
	private String bind_count = null;
	private String wifir_interval = null;
	private String wifir_flag = null;
	private String esafe_on = null;		//设备是否开启电子围栏总开关， “0”关  “1”开'
	private Integer prev_eid = null;
	private Integer cur_eid = null;
	private String prev_time = null;
	private String time_zone = null;
	private String longitude = null;
	private String latitude = null;
	private String app_timestamp = null;
	private String app_status = null;
	private String dev_timestamp = null;
	private String dev_status = null;
	private String charging_status = null;
	private String sig_level = null;
	private String up_time = null;
	private String log_file = null;
	
	private String acc = null;
	private String lct_type = null;
	private String eco_mode = null;
	private String debug_mode = null;
//	private String action_time;
//	private Integer how_long;
//	private Integer user_id;
	
	private String sleep_status = null;
	private String esafe_wifi = null;
	private String ssid_wifi = null;
	private String bssid_wifi = null;
	private String estat_wifi = null;
	private Integer beattim = null;
	private String sos_led_on = null;
	private Integer ufirm_stat = null;	//设备升级状态， 1， 升级中， 0： 非升级中'
	
	private Integer autopdn_status = null;
	private String time_pon = null;
	private String time_poff = null;	//设备升级状态， 1， 升级中， 0： 非升级中'
	private String iccid = null;	//
	private Integer ulfq = null;
	private String dynIccid = null;	//
	private Integer uLTe = null;
	private Integer test_status = null;
	private String tm_flag = null;
	private Integer tm_dur = null;
	private Integer lfq = null;
	
	
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
	public String getFlight_mode() {
		return flight_mode;
	}
	public void setFlight_mode(String flight_mode) {
		this.flight_mode = flight_mode;
	}
	public String getUrgent_mode() {
		return urgent_mode;
	}
	public void setUrgent_mode(String urgent_mode) {
		this.urgent_mode = urgent_mode;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getIs_lowbat() {
		return is_lowbat;
	}
	public void setIs_lowbat(String is_lowbat) {
		this.is_lowbat = is_lowbat;
	}
	/**
	 * @return the data_mute
	 */
	public String getData_mute() {
		return data_mute;
	}
	/**
	 * @param data_mute the data_mute to set
	 */
	public void setData_mute(String data_mute) {
		this.data_mute = data_mute;
	}
	/**
	 * @return the data_volume
	 */
	public String getData_volume() {
		return data_volume;
	}
	/**
	 * @param data_volume the data_volume to set
	 */
	public void setData_volume(String data_volume) {
		this.data_volume = data_volume;
	}
	/**
	 * @return the data_power
	 */
	public String getData_power() {
		return data_power;
	}
	/**
	 * @param data_power the data_power to set
	 */
	public void setData_power(String data_power) {
		this.data_power = data_power;
	}
	/**
	 * @return the bind_count
	 */
	public String getBind_count() {
		return bind_count;
	}
	/**
	 * @param bind_count the bind_count to set
	 */
	public void setBind_count(String bind_count) {
		this.bind_count = bind_count;
	}
	/**
	 * @return the wifir_flag
	 */
	public String getWifir_flag() {
		return wifir_flag;
	}
	/**
	 * @param wifir_flag the wifir_flag to set
	 */
	public void setWifir_flag(String wifir_flag) {
		this.wifir_flag = wifir_flag;
	}
	/**
	 * @return the wifir_interval
	 */
	public String getWifir_interval() {
		return wifir_interval;
	}
	/**
	 * @param wifir_interval the wifir_interval to set
	 */
	public void setWifir_interval(String wifir_interval) {
		this.wifir_interval = wifir_interval;
	}
	/**
	 * @return the esafe_on
	 */
	public String getEsafe_on() {
		return esafe_on;
	}
	/**
	 * @param esafe_on the esafe_on to set
	 */
	public void setEsafe_on(String esafe_on) {
		this.esafe_on = esafe_on;
	}
	/**
	 * @return the prev_eid
	 */
	public Integer getPrev_eid() {
		return prev_eid;
	}
	/**
	 * @param prev_eid the prev_eid to set
	 */
	public void setPrev_eid(Integer prev_eid) {
		this.prev_eid = prev_eid;
	}
	/**
	 * @return the cur_eid
	 */
	public Integer getCur_eid() {
		return cur_eid;
	}
	/**
	 * @param cur_eid the cur_eid to set
	 */
	public void setCur_eid(Integer cur_eid) {
		this.cur_eid = cur_eid;
	}
	/**
	 * @return the prev_time
	 */
	public String getPrev_time() {
		return prev_time;
	}
	/**
	 * @param prev_time the prev_time to set
	 */
	public void setPrev_time(String prev_time) {
		this.prev_time = prev_time;
	}
	/**
	 * @return the time_zone
	 */
	public String getTime_zone() {
		return time_zone;
	}
	/**
	 * @param time_zone the time_zone to set
	 */
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the app_timestamp
	 */
	public String getApp_timestamp() {
		return app_timestamp;
	}
	/**
	 * @param app_timestamp the app_timestamp to set
	 */
	public void setApp_timestamp(String app_timestamp) {
		this.app_timestamp = app_timestamp;
	}
	/**
	 * @return the app_status
	 */
	public String getApp_status() {
		return app_status;
	}
	/**
	 * @param app_status the app_status to set
	 */
	public void setApp_status(String app_status) {
		this.app_status = app_status;
	}
	/**
	 * @return the dev_timestamp
	 */
	public String getDev_timestamp() {
		return dev_timestamp;
	}
	/**
	 * @param dev_timestamp the dev_timestamp to set
	 */
	public void setDev_timestamp(String dev_timestamp) {
		this.dev_timestamp = dev_timestamp;
	}
	/**
	 * @return the dev_status
	 */
	public String getDev_status() {
		return dev_status;
	}
	/**
	 * @param dev_status the dev_status to set
	 */
	public void setDev_status(String dev_status) {
		this.dev_status = dev_status;
	}
	
	public String getCharging_status() {
		return charging_status;
	}
	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
	}
	public String getSig_level() {
		return sig_level;
	}
	public void setSig_level(String sig_level) {
		this.sig_level = sig_level;
	}
	/**
	 * @return the up_time
	 */
	public String getUp_time() {
		return up_time;
	}
	/**
	 * @param up_time the up_time to set
	 */
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	/**
	 * @return the log_file
	 */
	public String getLog_file() {
		return log_file;
	}
	/**
	 * @param log_file the log_file to set
	 */
	public void setLog_file(String log_file) {
		this.log_file = log_file;
	}
	/**
	 * @return the acc
	 */
	public String getAcc() {
		return acc;
	}
	/**
	 * @param acc the acc to set
	 */
	public void setAcc(String acc) {
		this.acc = acc;
	}
	/**
	 * @return the lct_type
	 */
	public String getLct_type() {
		return lct_type;
	}
	/**
	 * @param lct_type the lct_type to set
	 */
	public void setLct_type(String lct_type) {
		this.lct_type = lct_type;
	}
	public String getEco_mode() {
		return eco_mode;
	}
	public void setEco_mode(String eco_mode) {
		this.eco_mode = eco_mode;
	}
	public String getDebug_mode() {
		return debug_mode;
	}
	public void setDebug_mode(String debug_mode) {
		this.debug_mode = debug_mode;
	}
	
	public String getEsafe_wifi() {
		return esafe_wifi;
	}
	public void setEsafe_wifi(String esafe_wifi) {
		this.esafe_wifi = esafe_wifi;
	}
	public String getSsid_wifi() {
		return ssid_wifi;
	}
	public void setSsid_wifi(String ssid_wifi) {
		this.ssid_wifi = ssid_wifi;
	}
	public String getBssid_wifi() {
		return bssid_wifi;
	}
	public void setBssid_wifi(String bssid_wifi) {
		this.bssid_wifi = bssid_wifi;
	}
	public String getEstat_wifi() {
		return estat_wifi;
	}
	public void setEstat_wifi(String estat_wifi) {
		this.estat_wifi = estat_wifi;
	}
	public String getSleep_status() {
		return sleep_status;
	}
	public void setSleep_status(String sleep_status) {
		this.sleep_status = sleep_status;
	}
	public Integer getBeattim() {
		return beattim;
	}
	public void setBeattim(Integer beattim) {
		this.beattim = beattim;
	}
	/**
	 * @return the sos_led_on
	 */
	public String getSos_led_on() {
		return sos_led_on;
	}
	/**
	 * @param sos_led_on the sos_led_on to set
	 */
	public void setSos_led_on(String sos_led_on) {
		this.sos_led_on = sos_led_on;
	}
	/**
	 * @return the ufirm_stat
	 */
	public Integer getUfirm_stat() {
		return ufirm_stat;
	}
	/**
	 * @param ufirm_stat the ufirm_stat to set
	 */
	public void setUfirm_stat(Integer ufirm_stat) {
		this.ufirm_stat = ufirm_stat;
	}
	/**
	 * @return the autopdn_status
	 */
	public Integer getAutopdn_status() {
		return autopdn_status;
	}
	/**
	 * @param autopdn_status the autopdn_status to set
	 */
	public void setAutopdn_status(Integer autopdn_status) {
		this.autopdn_status = autopdn_status;
	}
	/**
	 * @return the time_pon
	 */
	public String getTime_pon() {
		return time_pon;
	}
	/**
	 * @param time_pon the time_pon to set
	 */
	public void setTime_pon(String time_pon) {
		this.time_pon = time_pon;
	}
	/**
	 * @return the time_poff
	 */
	public String getTime_poff() {
		return time_poff;
	}
	/**
	 * @param time_poff the time_poff to set
	 */
	public void setTime_poff(String time_poff) {
		this.time_poff = time_poff;
	}
	/**
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}
	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	/**
	 * @return the ulfq
	 */
	public Integer getUlfq() {
		return ulfq;
	}
	/**
	 * @param ulfq the ulfq to set
	 */
	public void setUlfq(Integer ulfq) {
		this.ulfq = ulfq;
	}
	/**
	 * @return the dynIccid
	 */
	public String getDynIccid() {
		return dynIccid;
	}
	/**
	 * @param dynIccid the dynIccid to set
	 */
	public void setDynIccid(String dynIccid) {
		this.dynIccid = dynIccid;
	}
	/**
	 * @return the uLTe
	 */
	public Integer getuLTe() {
		return uLTe;
	}
	/**
	 * @param uLTe the uLTe to set
	 */
	public void setuLTe(Integer uLTe) {
		this.uLTe = uLTe;
	}
	/**
	 * @return the test_status
	 */
	public Integer getTest_status() {
		return test_status;
	}
	/**
	 * @param test_status the test_status to set
	 */
	public void setTest_status(Integer test_status) {
		this.test_status = test_status;
	}
	/**
	 * @return the tm_flag
	 */
	public String getTm_flag() {
		return tm_flag;
	}
	/**
	 * @param tm_flag the tm_flag to set
	 */
	public void setTm_flag(String tm_flag) {
		this.tm_flag = tm_flag;
	}
	/**
	 * @return the tm_dur
	 */
	public Integer getTm_dur() {
		return tm_dur;
	}
	/**
	 * @param tm_dur the tm_dur to set
	 */
	public void setTm_dur(Integer tm_dur) {
		this.tm_dur = tm_dur;
	}
	public Integer getLfq() {
		return lfq;
	}
	public void setLfq(Integer lfq) {
		this.lfq = lfq;
	}
	
}
