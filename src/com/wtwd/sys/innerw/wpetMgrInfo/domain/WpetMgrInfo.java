package com.wtwd.sys.innerw.wpetMgrInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WpetMgrInfo extends PublicVoBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3562405047310729756L;
	private Integer device_id;	
	private String reco_move_on;
	private String reco_breath_freq_on;
	private String reco_heat_on;
	private String reco_hb_freq_on;
	private String reco_bloodpr_on;
	private Integer reco_move;
	private Integer min_hb_freq;
	private Integer max_hb_freq;
	private float minHeat;
	private float maxHeat;
	private Integer min_br_freq;
	private Integer max_br_freq;
	
	private Integer minSbp;
	private Integer maxSbp;
	private Integer minDbp;
	private Integer maxDbp;
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
	 * @return the reco_move_on
	 */
	public String getReco_move_on() {
		return reco_move_on;
	}
	/**
	 * @param reco_move_on the reco_move_on to set
	 */
	public void setReco_move_on(String reco_move_on) {
		this.reco_move_on = reco_move_on;
	}
	/**
	 * @return the reco_hb_freq_on
	 */
	public String getReco_hb_freq_on() {
		return reco_hb_freq_on;
	}
	/**
	 * @param reco_hb_freq_on the reco_hb_freq_on to set
	 */
	public void setReco_hb_freq_on(String reco_hb_freq_on) {
		this.reco_hb_freq_on = reco_hb_freq_on;
	}
	/**
	 * @return the reco_breath_freq_on
	 */
	public String getReco_breath_freq_on() {
		return reco_breath_freq_on;
	}
	/**
	 * @param reco_breath_freq_on the reco_breath_freq_on to set
	 */
	public void setReco_breath_freq_on(String reco_breath_freq_on) {
		this.reco_breath_freq_on = reco_breath_freq_on;
	}
	/**
	 * @return the reco_heat_on
	 */
	public String getReco_heat_on() {
		return reco_heat_on;
	}
	/**
	 * @param reco_heat_on the reco_heat_on to set
	 */
	public void setReco_heat_on(String reco_heat_on) {
		this.reco_heat_on = reco_heat_on;
	}
	/**
	 * @return the reco_bloodpr_on
	 */
	public String getReco_bloodpr_on() {
		return reco_bloodpr_on;
	}
	/**
	 * @param reco_bloodpr_on the reco_bloodpr_on to set
	 */
	public void setReco_bloodpr_on(String reco_bloodpr_on) {
		this.reco_bloodpr_on = reco_bloodpr_on;
	}
	/**
	 * @return the reco_move
	 */
	public Integer getReco_move() {
		return reco_move;
	}
	/**
	 * @param reco_move the reco_move to set
	 */
	public void setReco_move(Integer reco_move) {
		this.reco_move = reco_move;
	}
	/**
	 * @return the min_hb_freq
	 */
	public Integer getMin_hb_freq() {
		return min_hb_freq;
	}
	/**
	 * @param min_hb_freq the min_hb_freq to set
	 */
	public void setMin_hb_freq(Integer min_hb_freq) {
		this.min_hb_freq = min_hb_freq;
	}
	/**
	 * @return the max_hb_freq
	 */
	public Integer getMax_hb_freq() {
		return max_hb_freq;
	}
	/**
	 * @param max_hb_freq the max_hb_freq to set
	 */
	public void setMax_hb_freq(Integer max_hb_freq) {
		this.max_hb_freq = max_hb_freq;
	}
	/**
	 * @return the minHeat
	 */
	public float getMinHeat() {
		return minHeat;
	}
	/**
	 * @param minHeat the minHeat to set
	 */
	public void setMinHeat(float minHeat) {
		this.minHeat = minHeat;
	}
	/**
	 * @return the maxHeat
	 */
	public float getMaxHeat() {
		return maxHeat;
	}
	/**
	 * @param maxHeat the maxHeat to set
	 */
	public void setMaxHeat(float maxHeat) {
		this.maxHeat = maxHeat;
	}
	/**
	 * @return the max_br_freq
	 */
	public Integer getMax_br_freq() {
		return max_br_freq;
	}
	/**
	 * @param max_br_freq the max_br_freq to set
	 */
	public void setMax_br_freq(Integer max_br_freq) {
		this.max_br_freq = max_br_freq;
	}
	/**
	 * @return the min_br_freq
	 */
	public Integer getMin_br_freq() {
		return min_br_freq;
	}
	/**
	 * @param min_br_freq the min_br_freq to set
	 */
	public void setMin_br_freq(Integer min_br_freq) {
		this.min_br_freq = min_br_freq;
	}
	/**
	 * @return the minSbp
	 */
	public Integer getMinSbp() {
		return minSbp;
	}
	/**
	 * @param minSbp the minSbp to set
	 */
	public void setMinSbp(Integer minSbp) {
		this.minSbp = minSbp;
	}
	/**
	 * @return the maxSbp
	 */
	public Integer getMaxSbp() {
		return maxSbp;
	}
	/**
	 * @param maxSbp the maxSbp to set
	 */
	public void setMaxSbp(Integer maxSbp) {
		this.maxSbp = maxSbp;
	}
	/**
	 * @return the minDbp
	 */
	public Integer getMinDbp() {
		return minDbp;
	}
	/**
	 * @param minDbp the minDbp to set
	 */
	public void setMinDbp(Integer minDbp) {
		this.minDbp = minDbp;
	}
	/**
	 * @return the maxDbp
	 */
	public Integer getMaxDbp() {
		return maxDbp;
	}
	/**
	 * @param maxDbp the maxDbp to set
	 */
	public void setMaxDbp(Integer maxDbp) {
		this.maxDbp = maxDbp;
	}
	

}

