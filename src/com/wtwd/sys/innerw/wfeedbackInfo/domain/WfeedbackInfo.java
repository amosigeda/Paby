package com.wtwd.sys.innerw.wfeedbackInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WfeedbackInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3624385682258674381L;
	private Integer id;
	private String user_id;
	private String user_feedback_content;
	private Date date_time;
	private Integer belong_project;
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
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the user_feedback_content
	 */
	public String getUser_feedback_content() {
		return user_feedback_content;
	}
	/**
	 * @param user_feedback_content the user_feedback_content to set
	 */
	public void setUser_feedback_content(String user_feedback_content) {
		this.user_feedback_content = user_feedback_content;
	}
	/**
	 * @return the date_time
	 */
	public Date getDate_time() {
		return date_time;
	}
	/**
	 * @param date_time the date_time to set
	 */
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	/**
	 * @return the belong_project
	 */
	public Integer getBelong_project() {
		return belong_project;
	}
	/**
	 * @param belong_project the belong_project to set
	 */
	public void setBelong_project(Integer belong_project) {
		this.belong_project = belong_project;
	}
}

