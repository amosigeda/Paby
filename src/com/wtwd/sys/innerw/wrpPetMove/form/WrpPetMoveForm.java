package com.wtwd.sys.innerw.wrpPetMove.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class WrpPetMoveForm extends PublicFormBean implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3577025684415960574L;
	private Integer tot_move;
	private Date date;
	private Integer device_id;
	private Float tot_energy;
	private Integer tot_am_move;
	private Integer tot_pm_move;
	/**
	 * @return the tot_move
	 */
	public Integer getTot_move() {
		return tot_move;
	}
	/**
	 * @param tot_move the tot_move to set
	 */
	public void setTot_move(Integer tot_move) {
		this.tot_move = tot_move;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
	 * @return the tot_energy
	 */
	public Float getTot_energy() {
		return tot_energy;
	}
	/**
	 * @param tot_energy the tot_energy to set
	 */
	public void setTot_energy(Float tot_energy) {
		this.tot_energy = tot_energy;
	}
	/**
	 * @return the tot_pm_move
	 */
	public Integer getTot_pm_move() {
		return tot_pm_move;
	}
	/**
	 * @param tot_pm_move the tot_pm_move to set
	 */
	public void setTot_pm_move(Integer tot_pm_move) {
		this.tot_pm_move = tot_pm_move;
	}
	/**
	 * @return the tot_am_move
	 */
	public Integer getTot_am_move() {
		return tot_am_move;
	}
	/**
	 * @param tot_am_move the tot_am_move to set
	 */
	public void setTot_am_move(Integer tot_am_move) {
		this.tot_am_move = tot_am_move;
	}
	
}
