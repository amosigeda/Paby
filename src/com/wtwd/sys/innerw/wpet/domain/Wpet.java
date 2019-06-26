package com.wtwd.sys.innerw.wpet.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class Wpet extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8648848559369841696L;
	
	private String pet_id = null;
	private Integer device_id = null;	
	private String photo = null;
	private String nickname = null;
	private String born_date = null;
	private String sex = null;
	private Float weight = null;
	private String sport_level = null;
	private String fat_level = null;
	private String fci_detail_catid = null;
	private String fci_detail_all_catid = null;
	private String other_desc = null;
//	private Integer admin_userid;
	private String weight_level_1_catid = null;
	private String pet_type = null;
	private String photo_time_stamp = null;
	private Integer sheight = null;
	private Integer sensitivity = null;
	private Integer user_id = null;	
	private String is_healthy = null; 	
	private String time_zone = null;
	private String esafe_on = null;
	private Integer weight_type = null;
	
	
	/**
	 * @return the pet_id
	 */
	public String getPet_id() {
		return pet_id;
	}
	/**
	 * @param pet_id the pet_id to set
	 */
	public void setPet_id(String pet_id) {
		this.pet_id = pet_id;
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
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the born_date
	 */
	public String getBorn_date() {
		return born_date;
	}
	/**
	 * @param born_date the born_date to set
	 */
	public void setBorn_date(String born_date) {
		this.born_date = born_date;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the weight
	 */
	public Float getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	/**
	 * @return the sport_level
	 */
	public String getSport_level() {
		return sport_level;
	}
	/**
	 * @param sport_level the sport_level to set
	 */
	public void setSport_level(String sport_level) {
		this.sport_level = sport_level;
	}
	/**
	 * @return the fat_level
	 */
	public String getFat_level() {
		return fat_level;
	}
	/**
	 * @param fat_level the fat_level to set
	 */
	public void setFat_level(String fat_level) {
		this.fat_level = fat_level;
	}
	/**
	 * @return the ext1
	 */
	public String getFci_detail_catid() {
		return fci_detail_catid;
	}
	/**
	 * @param ext1 the ext1 to set
	 */
	public void setFci_detail_catid(String ext1) {
		this.fci_detail_catid = ext1;
	}
	
	public String getOther_desc() {
		return other_desc;
	}
	public void setOther_desc(String other_desc) {
		this.other_desc = other_desc;
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
	 * @return the pet_type
	 */
	public String getPet_type() {
		return pet_type;
	}
	/**
	 * @param pet_type the pet_type to set
	 */
	public void setPet_type(String pet_type) {
		this.pet_type = pet_type;
	}
	/**
	 * @return the fci_detail_all_catid
	 */
	public String getFci_detail_all_catid() {
		return fci_detail_all_catid;
	}
	/**
	 * @param fci_detail_all_catid the fci_detail_all_catid to set
	 */
	public void setFci_detail_all_catid(String fci_detail_all_catid) {
		this.fci_detail_all_catid = fci_detail_all_catid;
	}
	/**
	 * @return the photo_time_stamp
	 */
	public String getPhoto_time_stamp() {
		return photo_time_stamp;
	}
	/**
	 * @param photo_time_stamp the photo_time_stamp to set
	 */
	public void setPhoto_time_stamp(String photo_time_stamp) {
		this.photo_time_stamp = photo_time_stamp;
	}	
	public Integer getSheight() {
		return sheight;
	}
	public void setSheight(Integer sheight) {
		this.sheight = sheight;
	}
	public Integer getSensitivity() {
		return sensitivity;
	}
	public void setSensitivity(Integer sensitivity) {
		this.sensitivity = sensitivity;
	}
	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the is_healthy
	 */
	public String getIs_healthy() {
		return is_healthy;
	}
	/**
	 * @param is_healthy the is_healthy to set
	 */
	public void setIs_healthy(String is_healthy) {
		this.is_healthy = is_healthy;
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
	 * @return the weight_type
	 */
	public Integer getWeight_type() {
		return weight_type;
	}
	/**
	 * @param weight_type the weight_type to set
	 */
	public void setWeight_type(Integer weight_type) {
		this.weight_type = weight_type;
	}	
	
}
