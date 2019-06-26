﻿package com.wtwd.sys.innerw.wpetMoveInfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class WpetMoveInfoForm extends PublicFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4123411508602125730L;

	private Integer pets_pet_id;
	private Float stepNumber;
	private Float route;
	private Double calories;
	private Double speed;
	private Date up_time;
	private Date start_time;
	private Date end_time;

	
	public Integer getPets_pet_id() {
		return pets_pet_id;
	}
	public void setPets_pet_id(Integer pets_pet_id) {
		this.pets_pet_id = pets_pet_id;
	}
	public Float getStepNumber() {
		return stepNumber;
	}
	public void setStepNumber(Float stepNumber) {
		this.stepNumber = stepNumber;
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