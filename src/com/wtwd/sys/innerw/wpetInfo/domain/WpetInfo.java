package com.wtwd.sys.innerw.wpetInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

//20160625 label

public class WpetInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4123411508602125730L;
	private Integer pets_pet_id;
	private Float last_gps_lo;		//longitude
	private Float last_gps_la;		//latitude
	private Date up_time;
	private Float last_bat_level;	//battery level
	private Integer hb_freq;
	private Float temperature;
	private String sbp;
	private String dbp;
	private Integer breath_freq;
	private Float movement;
	private Float energy_consumed;
	private String move_type;
	private String move_time;
	private String device_imei;	
	/**
	 * @return the pets_pet_id
	 */
	public Integer getPets_pet_id() {
		return pets_pet_id;
	}
	/**
	 * @param pets_pet_id the pets_pet_id to set
	 */
	public void setPets_pet_id(Integer pets_pet_id) {
		this.pets_pet_id = pets_pet_id;
	}
	/**
	 * @return the last_gps_lo
	 */
	public Float getLast_gps_lo() {
		return last_gps_lo;
	}
	/**
	 * @param last_gps_lo the last_gps_lo to set
	 */
	public void setLast_gps_lo(Float last_gps_lo) {
		this.last_gps_lo = last_gps_lo;
	}
	/**
	 * @return the last_gps_la
	 */
	public Float getLast_gps_la() {
		return last_gps_la;
	}
	/**
	 * @param last_gps_la the last_gps_la to set
	 */
	public void setLast_gps_la(Float last_gps_la) {
		this.last_gps_la = last_gps_la;
	}
	/**
	 * @return the up_time
	 */
	public Date getUp_time() {
		return up_time;
	}
	/**
	 * @param up_time the up_time to set
	 */
	public void setUp_time(Date up_time) {
		this.up_time = up_time;
	}
	/**
	 * @return the last_bat_level
	 */
	public Float getLast_bat_level() {
		return last_bat_level;
	}
	/**
	 * @param last_bat_level the last_bat_level to set
	 */
	public void setLast_bat_level(Float last_bat_level) {
		this.last_bat_level = last_bat_level;
	}
	/**
	 * @return the hb_freq
	 */
	public Integer getHb_freq() {
		return hb_freq;
	}
	/**
	 * @param hb_freq the hb_freq to set
	 */
	public void setHb_freq(Integer hb_freq) {
		this.hb_freq = hb_freq;
	}
	/**
	 * @return the temperature
	 */
	public Float getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the sbp
	 */
	public String getSbp() {
		return sbp;
	}
	/**
	 * @param sbp the sbp to set
	 */
	public void setSbp(String sbp) {
		this.sbp = sbp;
	}
	/**
	 * @return the breath_freq
	 */
	public Integer getBreath_freq() {
		return breath_freq;
	}
	/**
	 * @param breath_freq the breath_freq to set
	 */
	public void setBreath_freq(Integer breath_freq) {
		this.breath_freq = breath_freq;
	}
	/**
	 * @return the movement
	 */
	public Float getMovement() {
		return movement;
	}
	/**
	 * @param movement the movement to set
	 */
	public void setMovement(Float movement) {
		this.movement = movement;
	}
	/**
	 * @return the energy_consumed
	 */
	public Float getEnergy_consumed() {
		return energy_consumed;
	}
	/**
	 * @param energy_consumed the energy_consumed to set
	 */
	public void setEnergy_consumed(Float energy_consumed) {
		this.energy_consumed = energy_consumed;
	}

	/**
	 * @return the dbp
	 */
	public String getDbp() {
		return dbp;
	}
	/**
	 * @param dbp the dbp to set
	 */
	public void setDbp(String dbp) {
		this.dbp = dbp;
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
	 * @return the move_type
	 */
	public String getMove_type() {
		return move_type;
	}
	/**
	 * @param move_type the move_type to set
	 */
	public void setMove_type(String move_type) {
		this.move_type = move_type;
	}
	/**
	 * @return the move_time
	 */
	public String getMove_time() {
		return move_time;
	}
	/**
	 * @param move_time the move_time to set
	 */
	public void setMove_time(String move_time) {
		this.move_time = move_time;
	}
	

}
