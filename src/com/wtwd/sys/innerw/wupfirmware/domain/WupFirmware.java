package com.wtwd.sys.innerw.wupfirmware.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WupFirmware extends PublicVoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5539503828346808715L;
	private String project_name;
	private String version_us;
	private String version_eu;
	private String version_cn;
	private String update_time;
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getVersion_us() {
		return version_us;
	}
	public void setVersion_us(String version_us) {
		this.version_us = version_us;
	}
	public String getVersion_eu() {
		return version_eu;
	}
	public void setVersion_eu(String version_eu) {
		this.version_eu = version_eu;
	}
	public String getVersion_cn() {
		return version_cn;
	}
	public void setVersion_cn(String version_cn) {
		this.version_cn = version_cn;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
