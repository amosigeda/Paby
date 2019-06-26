package com.wtwd.sys.innerw.wsportlevel1.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WsportLevel1 extends PublicVoBean implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2743583620827601508L;
	/**
	 * 
	 */

	private String catid;
	private String weight_level_1_catid;
	private Float min_sport_time;
	private Float max_sport_time;
	private String desc;
	private String eng_desc;
	private String ext1;
	private String ext2;
	private Float minWeight;
	private Float maxWeight;

	/**
	 * @return the catid
	 */
	public String getCatid() {
		return catid;
	}
	/**
	 * @param catid the catid to set
	 */
	public void setCatid(String id) {
		this.catid = id;
	}
	/**
	 * @return the weight_level_1_catid
	 */
	public String getWeight_level_1_catid() {
		return weight_level_1_catid;
	}
	/**
	 * @param weight_level_1_catid the weight_level_1_catid to set
	 */
	public void setWeight_level_1_catid(String weight_level_1_catid) {
		this.weight_level_1_catid = weight_level_1_catid;
	}

	/**
	 * @return the max_sport_time
	 */
	public Float getMax_sport_time() {
		return max_sport_time;
	}
	/**
	 * @param max_sport_time the max_sport_time to set
	 */
	public void setMax_sport_time(Float max_sport_time) {
		this.max_sport_time = max_sport_time;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the eng_desc
	 */
	public String getEng_desc() {
		return eng_desc;
	}
	/**
	 * @param eng_desc the eng_desc to set
	 */
	public void setEng_desc(String eng_desc) {
		this.eng_desc = eng_desc;
	}
	/**
	 * @return the ext1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * @param ext1 the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * @return the ext2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * @param ext2 the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * @return the minWeight
	 */
	public Float getMinWeight() {
		return minWeight;
	}
	/**
	 * @param minWeight the minWeight to set
	 */
	public void setMinWeight(Float minWeight) {
		this.minWeight = minWeight;
	}
	/**
	 * @return the maxWeight
	 */
	public Float getMaxWeight() {
		return maxWeight;
	}
	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(Float maxWeight) {
		this.maxWeight = maxWeight;
	}
	/**
	 * @return the min_sport_time
	 */
	public Float getMin_sport_time() {
		return min_sport_time;
	}
	/**
	 * @param min_sport_time the min_sport_time to set
	 */
	public void setMin_sport_time(Float min_sport_time) {
		this.min_sport_time = min_sport_time;
	}
	
	
	
	

}
