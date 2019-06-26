package com.wtwd.sys.innerw.wpetMoveInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WpetSleepInfo extends PublicVoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 127765478841987751L;
	
	private Integer pets_pet_id = null;
	private Float step_number = null;
	private Double speed = null;
	private Date up_time = null;	
	private Date start_time = null;
	private Date end_time = null;
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
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Date getUp_time() {
		return up_time;
	}
	public void setUp_time(Date up_time) {
		this.up_time = up_time;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

}
