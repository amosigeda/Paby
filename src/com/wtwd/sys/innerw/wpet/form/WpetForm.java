package com.wtwd.sys.innerw.wpet.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WpetForm extends PublicFormBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4019516375006491648L;
	private String pet_id;
	private Integer device_id;	
	private String photo;
	private String nickname;
	private String born_date;
	private String sex;
	private float weight;
	private String sport_level;
	private String fat_level;
	private String fci_detail_catid;
	private String fci_detail_all_catid;
	private String ext2;
//	private Integer admin_userid;
	private String weight_level_1_catid;
	private String pet_type;
	private String photo_time_stamp;
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
	public float getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
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
	
	
}