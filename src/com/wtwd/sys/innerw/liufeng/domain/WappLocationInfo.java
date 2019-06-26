package com.wtwd.sys.innerw.liufeng.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WappLocationInfo extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = -8342319901738145399L;
	
	private int id;
	private int user_id;
	private String longitude;
	private String latitude;
	private String location_type;
	private int accuracy;
	private Date upload_time;
	private String change_longitude;
	private String change_latitude;
	private int belong_project;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocation_type() {
		return location_type;
	}
	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public Date getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}
	public String getChange_longitude() {
		return change_longitude;
	}
	public void setChange_longitude(String change_longitude) {
		this.change_longitude = change_longitude;
	}
	public String getChange_latitude() {
		return change_latitude;
	}
	public void setChange_latitude(String change_latitude) {
		this.change_latitude = change_latitude;
	}
	public int getBelong_project() {
		return belong_project;
	}
	public void setBelong_project(int belong_project) {
		this.belong_project = belong_project;
	}
	
}
