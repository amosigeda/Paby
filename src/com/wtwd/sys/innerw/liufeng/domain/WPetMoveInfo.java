package com.wtwd.sys.innerw.liufeng.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WPetMoveInfo extends PublicVoBean implements Serializable {
	
	private static final long serialVersionUID = 904371085810696589L;
	
	private int pets_pet_id;
	private float step_number;
	private float route;
	private float calories;
	private double speed;
	private Date up_time;
	private Date start_time;
	private Date end_time;
	
	public int getPets_pet_id() {
		return pets_pet_id;
	}
	public void setPets_pet_id(int pets_pet_id) {
		this.pets_pet_id = pets_pet_id;
	}
	public float getStep_number() {
		return step_number;
	}
	public void setStep_number(float step_number) {
		this.step_number = step_number;
	}
	public float getRoute() {
		return route;
	}
	public void setRoute(float route) {
		this.route = route;
	}
	public float getCalories() {
		return calories;
	}
	public void setCalories(float calories) {
		this.calories = calories;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
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
