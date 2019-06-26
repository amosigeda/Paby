package com.wtwd.sys.innerw.wappusers.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WappUsersForm extends PublicFormBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4231414289956074107L;
	private String user_id;
	private String user_name;	// db email
	private String user_password;   // db passwd
	private String user_nick;    // db nickname
	private String user_sex;   //db sex
	private String user_photo;     //db photo
	private String status;
	private String login_status;
	private Integer act_device_id;
	private String ext1;	
	private String create_time;
	private String lastlogin_time;
	private String belong_project;
	private String bind_count;
	private String zousnqag;
	private String phone_mac;
	private String user_born;     //db born_date
	private String firstname;
	private String lastname;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String product_model;
	private String firmware_edition;
	private String user_type;
	private String sign_text;
	private String app_token;
	private String photo_time_stamp;
	

	public String getBind_count() {
		return bind_count;
	}
	public void setBind_count(String bindCount) {
		this.bind_count = bindCount;
	}
	public String getBelong_project() {
		return belong_project;
	}
	public void setBelong_project(String belongProject) {
		this.belong_project = belongProject;
	}
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String head) {
		this.user_photo = head;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String id) {
		this.user_id = id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String password) {
		this.user_password = password;
	}
	public String getUser_nick() {
		return user_nick;
	}
	public void setUser_nick(String nickName) {
		this.user_nick = nickName;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String sex) {
		this.user_sex = sex;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		this.create_time = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastlogin_time() {
		return lastlogin_time;
	}
	public void setLastlogin_time(String lastLoginTime) {
		this.lastlogin_time = lastLoginTime;
	}
	public String getLogin_status() {
		return login_status;
	}
	public void setLogin_status(String loginStatus) {
		this.login_status = loginStatus;
	}
	public String getPhone_mac() {
		return phone_mac;
	}
	public void setPhone_mac(String phoneMac) {
		this.phone_mac = phoneMac;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String email) {
		this.user_name = email;
	}
	public Integer getAct_device_id() {
		return act_device_id;
	}
	public void setAct_device_id(Integer act_device_id) {
		this.act_device_id = act_device_id;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getZousnqag() {
		return zousnqag;
	}
	public void setZousnqag(String zousnqag) {
		this.zousnqag = zousnqag;
	}
	public String getUser_born() {
		return user_born;
	}
	public void setUser_born(String born_date) {
		this.user_born = born_date;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String first_name) {
		this.firstname = first_name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the product_model
	 */
	public String getProduct_model() {
		return product_model;
	}
	/**
	 * @param product_model the product_model to set
	 */
	public void setProduct_model(String product_model) {
		this.product_model = product_model;
	}
	/**
	 * @return the firmware_edition
	 */
	public String getFirmware_edition() {
		return firmware_edition;
	}
	/**
	 * @param firmware_edition the firmware_edition to set
	 */
	public void setFirmware_edition(String firmware_edition) {
		this.firmware_edition = firmware_edition;
	}
	/**
	 * @return the user_type
	 */
	public String getUser_type() {
		return user_type;
	}
	/**
	 * @param user_type the user_type to set
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	/**
	 * @return the sign_text
	 */
	public String getSign_text() {
		return sign_text;
	}
	/**
	 * @param sign_text the sign_text to set
	 */
	public void setSign_text(String sign_text) {
		this.sign_text = sign_text;
	}
	/**
	 * @return the app_token
	 */
	public String getApp_token() {
		return app_token;
	}
	/**
	 * @param app_token the app_token to set
	 */
	public void setApp_token(String app_token) {
		this.app_token = app_token;
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