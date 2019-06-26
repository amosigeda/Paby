package com.wtwd.sys.innerw.wappuserverify.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WappuserVerifyForm extends PublicFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8814102361479271382L;
	private Integer id;
	private String email;
	private String verify_code;
	private String create_time;
	private String belong_project;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the verify_code
	 */
	public String getVerify_code() {
		return verify_code;
	}
	/**
	 * @param verify_code the verify_code to set
	 */
	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}
	/**
	 * @return the create_time
	 */
	public String getCreate_time() {
		return create_time;
	}
	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	/**
	 * @return the belong_project
	 */
	public String getBelong_project() {
		return belong_project;
	}
	/**
	 * @param belong_project the belong_project to set
	 */
	public void setBelong_project(String belong_project) {
		this.belong_project = belong_project;
	}
	
}
