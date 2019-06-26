package com.wtwd.pet.app.entity;

import java.io.Serializable;


public class WMsgInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5081734062293050953L;
	private int msg_id;
	private String msg_type;
	private int msg_ind_id;
	private String msg_date;
	private String msg_content;
	private int device_id;
	private int from_usrid;
	private int to_usrid;
	private int fence_id;
	private String status;
	private int share_id;
	private int pet_id;
	private String from_nick;	//消息发起者用户昵称
	private String pet_nick;	//宠物昵称
	private String fence_name;	//电子围栏名称
	private String hide_flag;
	private String how_long;
	private String langtitude;
	private String longtitude;
	
	private String summary;
	private String app_type;
	private String device_token;
	private String ios_token;
	private String push_status;
	private String ios_real;
	private Integer badge;
	private String msg_date_utc;
	private String orderSort;
	private Integer order_id;
	private Integer old_badge;
	
		
	public WMsgInfo() {
		hide_flag = "0";
		msg_date = "";
	}
	
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public int getMsg_ind_id() {
		return msg_ind_id;
	}
	public void setMsg_ind_id(int msg_ind_id) {
		this.msg_ind_id = msg_ind_id;
	}
	public String getMsg_date() {
		return msg_date;
	}
	public void setMsg_date(String msg_date) {
		this.msg_date = msg_date;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public int getFrom_usrid() {
		return from_usrid;
	}
	public void setFrom_usrid(int from_usrid) {
		this.from_usrid = from_usrid;
	}
	public int getTo_usrid() {
		return to_usrid;
	}
	public void setTo_usrid(int to_usrid) {
		this.to_usrid = to_usrid;
	}
	public int getFence_id() {
		return fence_id;
	}
	public void setFence_id(int fence_id) {
		this.fence_id = fence_id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the share_id
	 */
	public int getShare_id() {
		return share_id;
	}
	/**
	 * @param share_id the share_id to set
	 */
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	/**
	 * @return the pet_id
	 */
	public int getPet_id() {
		return pet_id;
	}
	/**
	 * @param pet_id the pet_id to set
	 */
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	/**
	 * @return the from_nick
	 */
	public String getFrom_nick() {
		return from_nick;
	}
	/**
	 * @param from_nick the from_nick to set
	 */
	public void setFrom_nick(String from_nick) {
		this.from_nick = from_nick;
	}
	/**
	 * @return the pet_nick
	 */
	public String getPet_nick() {
		return pet_nick;
	}
	/**
	 * @param pet_nick the pet_nick to set
	 */
	public void setPet_nick(String pet_nick) {
		this.pet_nick = pet_nick;
	}
	/**
	 * @return the fence_name
	 */
	public String getFence_name() {
		return fence_name;
	}
	/**
	 * @param fence_name the fence_name to set
	 */
	public void setFence_name(String fence_name) {
		this.fence_name = fence_name;
	}
	/**
	 * @return the hide_flag
	 */
	public String getHide_flag() {
		return hide_flag;
	}
	/**
	 * @param hide_flag the hide_flag to set
	 */
	public void setHide_flag(String hide_flag) {
		this.hide_flag = hide_flag;
	}

	/**
	 * @return the how_long
	 */
	public String getHow_long() {
		return how_long;
	}

	/**
	 * @param how_long the how_long to set
	 */
	public void setHow_long(String how_long) {
		this.how_long = how_long;
	}

	/**
	 * @return the langtitude
	 */
	public String getLangtitude() {
		return langtitude;
	}

	/**
	 * @param langtitude the langtitude to set
	 */
	public void setLangtitude(String langtitude) {
		this.langtitude = langtitude;
	}

	/**
	 * @return the longtitude
	 */
	public String getLongtitude() {
		return longtitude;
	}

	/**
	 * @param longtitude the longtitude to set
	 */
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the app_type
	 */
	public String getApp_type() {
		return app_type;
	}

	/**
	 * @param app_type the app_type to set
	 */
	public void setApp_type(String app_type) {
		this.app_type = app_type;
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
	 * @return the push_status
	 */
	public String getPush_status() {
		return push_status;
	}

	/**
	 * @param push_status the push_status to set
	 */
	public void setPush_status(String push_status) {
		this.push_status = push_status;
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
	 * @return the msg_date_utc
	 */
	public String getMsg_date_utc() {
		return msg_date_utc;
	}

	/**
	 * @param msg_date_utc the msg_date_utc to set
	 */
	public void setMsg_date_utc(String msg_date_utc) {
		this.msg_date_utc = msg_date_utc;
	}

	public String getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(String orderSort) {
		this.orderSort = orderSort;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getOld_badge() {
		return old_badge;
	}

	public void setOld_badge(Integer old_badge) {
		this.old_badge = old_badge;
	}
	
}
