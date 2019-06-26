package com.wtwd.sys.innerw.wpetSysRecoMove.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WpetSysRecoMove extends PublicVoBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -661968916187562666L;
	private Float moves_inaday;
	private Integer device_id;
	/**
	 * @return the moves_inaday
	 */
	public Float getMoves_inaday() {
		return moves_inaday;
	}
	/**
	 * @param moves_inaday the moves_inaday to set
	 */
	public void setMoves_inaday(Float moves_inaday) {
		this.moves_inaday = moves_inaday;
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
		
	
	
}

