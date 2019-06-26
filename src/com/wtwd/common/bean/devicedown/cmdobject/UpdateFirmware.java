package com.wtwd.common.bean.devicedown.cmdobject;

public class UpdateFirmware {
	Integer updateFlag;
	String updateVer;
	Integer userId;
	
	public Integer getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getUpdateVer() {
		return updateVer;
	}
	public void setUpdateVer(String updateVer) {
		this.updateVer = updateVer;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
