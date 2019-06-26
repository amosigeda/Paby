package com.wtwd.sys.innerw.wappusers.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WappUsers extends PublicVoBean implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3037531247536536253L;
	private String user_id = null;
	private String user_name = null;	// db email
	private String user_password = null;   // db passwd
	private String passmd = null;   // db passwd md5
	private String user_nick = null;    // db nickname
	private String user_sex = null;   //db sex
	private String user_photo = null;     //db photo
	private String status = null;
	private String login_status = null;	//app状态， “0”没有进入app，也没有在后台  “1”用户进入了app,
		//并且在前台， “2”用户进入了app,并且在后台',  
	private Integer act_device_id = null;
	private String ext1 = null;	
	private String create_time = null;
	private String lastlogin_time = null;
	private String belong_project = null;
	private String bind_count = null;
	private String zousnqag = null;
	private String phone_mac = null;
	private String user_born = null;     //db born_date
	private String firstname = null;
	private String lastname = null;
	private String addr1 = null;
	private String addr2 = null;
	private String city = null;
	private String state = null;
	private String zip = null;
	private String country = null;
	private String phone = null;
	private String product_model = null;
	private String firmware_edition = null;
	private String user_type = null;
	private String signtext = null;
	private String app_token = null;
	private String photo_time_stamp = null;
	private String device_token = null;
	private String time_zone = null;
	private String app_version = null;
	private String pet_count = null;	//所有宠物数量
	private String ios_token = null;
	private String ios_real = null;
	private Integer badge = null;
	private String lang = null;
	private String udu = null;
	private Integer dfg = null;	//dream ware app flag
	
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
	public String getSigntext() {
		return signtext;
	}
	/**
	 * @param sign_text the sign_text to set
	 */
	public void setSigntext(String sign_text) {
		this.signtext = sign_text;
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
	/**
	 * @return the device_token
	 */
	public String getDevice_token() {
		return device_token;
	}
	/**
	 * @param device_token the device_token to set
	 */
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
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
	 * @return the app_version
	 */
	public String getApp_version() {
		return app_version;
	}
	/**
	 * @param app_version the app_version to set
	 */
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	/**
	 * @return the pet_count
	 */
	public String getPet_count() {
		return pet_count;
	}
	/**
	 * @param pet_count the pet_count to set
	 */
	public void setPet_count(String pet_count) {
		this.pet_count = pet_count;
	}
	/**
	 * @return the ios_token
	 */
	public String getIos_token() {
		return ios_token;
	}
	/**
	 * @param ios_token the ios_token to set
	 */
	public void setIos_token(String ios_token) {
		this.ios_token = ios_token;
	}
	/**
	 * @return the ios_real
	 */
	public String getIos_real() {
		return ios_real;
	}
	/**
	 * @param ios_real the ios_real to set
	 */
	public void setIos_real(String ios_real) {
		this.ios_real = ios_real;
	}
	/**
	 * @return the badge
	 */
	public Integer getBadge() {
		return badge;
	}
	/**
	 * @param badge the badge to set
	 */
	public void setBadge(Integer badge) {
		this.badge = badge;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the udu
	 */
	public String getUdu() {
		return udu;
	}
	/**
	 * @param udu the udu to set
	 */
	public void setUdu(String udu) {
		this.udu = udu;
	}
	public String getPassmd() {
		return passmd;
	}
	public void setPassmd(String passmd) {
		this.passmd = passmd;
	}
	public Integer getDfg() {
		return dfg;
	}
	public void setDfg(Integer dfg) {
		this.dfg = dfg;
	}
	
	
}