package com.wtwd.common.bean.other;

public class CellTower {
	private Integer cellId;
	private Integer locationAreaCode;
	private Integer mobileCountryCode;
	private Integer mobileNetworkCode;
	private Integer age;
	private Integer signalStrength;
	private Integer timingAdvance;
	
	public Integer getCellId() {
		return cellId;
	}
	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}
	public Integer getLocationAreaCode() {
		return locationAreaCode;
	}
	public void setLocationAreaCode(Integer locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}
	public Integer getMobileCountryCode() {
		return mobileCountryCode;
	}
	public void setMobileCountryCode(Integer mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}
	public Integer getMobileNetworkCode() {
		return mobileNetworkCode;
	}
	public void setMobileNetworkCode(Integer mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(Integer signalStrength) {
		this.signalStrength = signalStrength;
	}
	public Integer getTimingAdvance() {
		return timingAdvance;
	}
	public void setTimingAdvance(Integer timingAdvance) {
		this.timingAdvance = timingAdvance;
	}

}
