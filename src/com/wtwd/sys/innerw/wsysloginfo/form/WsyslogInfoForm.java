package com.wtwd.sys.innerw.wsysloginfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

public class WsyslogInfoForm extends PublicFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8004680053494396513L;
	private Integer id;
	private String userName;
	private Date logDate;
	private String logs;
	private String ip;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the logDate
	 */
	public Date getLogDate() {
		return logDate;
	}
	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	/**
	 * @return the logs
	 */
	public String getLogs() {
		return logs;
	}
	/**
	 * @param logs the logs to set
	 */
	public void setLogs(String logs) {
		this.logs = logs;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
