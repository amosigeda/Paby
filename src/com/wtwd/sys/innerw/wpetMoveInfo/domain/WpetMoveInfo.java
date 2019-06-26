package com.wtwd.sys.innerw.wpetMoveInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

//20160625 label

public class WpetMoveInfo extends PublicVoBean implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4123411508602125730L;
	
	private Integer pets_pet_id = null;
	private Float step_number = null;
	private Float route = null;
	private Double calories = null;
	private Double speed = null;
	private String up_time = null;	
	private String start_time = null;
	private String end_time = null;
	private Integer pet_id = null;
	private String msg_type = null;
	private String device_imei = null;
	private Integer device_id = null;
		
	public Integer getPets_pet_id() {
		return pets_pet_id;
	}
	public void setPets_pet_id(Integer pets_pet_id) {
		this.pets_pet_id = pets_pet_id;
	}
	public Float getStep_number() {
		return step_number;
	}
	public void setStep_number(Float step_number) {
		this.step_number = step_number;
	}
	public Float getRoute() {
		return route;
	}
	public void setRoute(Float route) {
		this.route = route;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	
	public String getUp_time() {
		return up_time;
	}
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	/**
	 * @return the pet_id
	 */
	/**
	 * @return the msg_type
	 */
	public String getMsg_type() {
		return msg_type;
	}
	/**
	 * @param msg_type the msg_type to set
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
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

    @Override  
    public Object clone() {  
    	WpetMoveInfo obj = null;  
        try{  
            obj = (WpetMoveInfo)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return obj;  
    }  	
	
}

