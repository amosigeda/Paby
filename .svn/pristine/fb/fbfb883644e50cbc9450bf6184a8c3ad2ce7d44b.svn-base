package com.wtwd.sys.locationinfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class LocationInfo extends PublicVoBean implements Serializable{
	
	
	private Integer id;
	private String serieNo;
	private Integer battery;//����
	private String longitude;//����
	private String latitude;//γ��
	private String locationType;
	//private Integer accuracy;//��ȷ��
	private Float accuracy;
	private Date uploadTime;
	private String changeLongitude;//ת����ľ���
	private String changeLatitude;//ת�����γ��
    private String belongProject;
    private String fall;
    private String showType;  //显示类型
    private Integer device_id;	//设备id
    private Integer stepCount;
    private Integer pet_id;
    
    
    public LocationInfo(){
		
	}
	
	public LocationInfo(String serieNo, String changeLongitude, String changeLatitude, String locationType, Integer stepCount) {
		this.serieNo = serieNo;
		this.changeLongitude = changeLongitude;
		this.changeLatitude = changeLatitude;
		this.locationType = locationType;
		this.stepCount = stepCount;
		this.battery = 0;
		this.longitude = "";
		this.latitude = "";
		this.accuracy = Float.parseFloat("1.0");
		this.uploadTime = new Date();
		this.fall = "1";
		this.belongProject = "1";
	}
	
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getChangeLongitude() {
		return changeLongitude;
	}
	public void setChangeLongitude(String changeLongitude) {
		this.changeLongitude = changeLongitude;
	}
	public String getChangeLatitude() {
		return changeLatitude;
	}
	public void setChangeLatitude(String changeLatitude) {
		this.changeLatitude = changeLatitude;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
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
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	public Float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Float accuracy) {
		this.accuracy = accuracy;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
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
	public Integer getStepCount() {
		return stepCount;
	}
	public void setStepCount(Integer stepCount) {
		this.stepCount = stepCount;
	}

	public Integer getPet_id() {
		return pet_id;
	}

	public void setPet_id(Integer pet_id) {
		this.pet_id = pet_id;
	}
	
}
