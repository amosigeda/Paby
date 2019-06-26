package com.wtwd.common.bean.other;

import java.util.List;

public class Geolocation {
	private Integer homeMobileCountryCode;
	private Integer homeMobileNetworkCode;
	private String radioType;
	private String carrier;
	private String considerIp;
	private List<CellTower> cellTowers;
	private List<WifiAccessPoint> WifiAccessPoints;
	
	public Integer getHomeMobileCountryCode() {
		return homeMobileCountryCode;
	}
	public void setHomeMobileCountryCode(Integer homeMobileCountryCode) {
		this.homeMobileCountryCode = homeMobileCountryCode;
	}
	public Integer getHomeMobileNetworkCode() {
		return homeMobileNetworkCode;
	}
	public void setHomeMobileNetworkCode(Integer homeMobileNetworkCode) {
		this.homeMobileNetworkCode = homeMobileNetworkCode;
	}
	
	public String getRadioType() {
		return radioType;
	}
	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getConsiderIp() {
		return considerIp;
	}
	public void setConsiderIp(String considerIp) {
		this.considerIp = considerIp;
	}
	public List<CellTower> getCellTowers() {
		return cellTowers;
	}
	public void setCellTowers(List<CellTower> cellTowers) {
		this.cellTowers = cellTowers;
	}
	public List<WifiAccessPoint> getWifiAccessPoints() {
		return WifiAccessPoints;
	}
	public void setWifiAccessPoints(List<WifiAccessPoint> wifiAccessPoints) {
		WifiAccessPoints = wifiAccessPoints;
	}

	
}
