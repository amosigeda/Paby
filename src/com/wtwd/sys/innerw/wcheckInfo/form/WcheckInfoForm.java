package com.wtwd.sys.innerw.wcheckInfo.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WcheckInfoForm extends PublicFormBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1496846345373064704L;
	private Integer id;
	private String package_name;
	private String version_name;
	private String version_code;
	private String download_path;
	private String function_cap;
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
	 * @return the package_name
	 */
	public String getPackage_name() {
		return package_name;
	}
	/**
	 * @param package_name the package_name to set
	 */
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	/**
	 * @return the version_name
	 */
	public String getVersion_name() {
		return version_name;
	}
	/**
	 * @param version_name the version_name to set
	 */
	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	/**
	 * @return the version_code
	 */
	public String getVersion_code() {
		return version_code;
	}
	/**
	 * @param version_code the version_code to set
	 */
	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}
	/**
	 * @return the download_path
	 */
	public String getDownload_path() {
		return download_path;
	}
	/**
	 * @param download_path the download_path to set
	 */
	public void setDownload_path(String download_path) {
		this.download_path = download_path;
	}
	/**
	 * @return the function_cap
	 */
	public String getFunction_cap() {
		return function_cap;
	}
	/**
	 * @param function_cap the function_cap to set
	 */
	public void setFunction_cap(String function_cap) {
		this.function_cap = function_cap;
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