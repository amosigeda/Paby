package com.wtwd.sys.innerw.wprojectInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WprojectInfo extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1714893136966268461L;
	private Integer id;
	private String project_no;
	private String project_name;
	private Integer channel_id;
	private Integer company_id;
	private String status;
	private Date add_time;
	private String remark;
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
	 * @return the project_no
	 */
	public String getProject_no() {
		return project_no;
	}
	/**
	 * @param project_no the project_no to set
	 */
	public void setProject_no(String project_no) {
		this.project_no = project_no;
	}
	/**
	 * @return the project_name
	 */
	public String getProject_name() {
		return project_name;
	}
	/**
	 * @param project_name the project_name to set
	 */
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	/**
	 * @return the company_id
	 */
	public Integer getCompany_id() {
		return company_id;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	/**
	 * @return the channel_id
	 */
	public Integer getChannel_id() {
		return channel_id;
	}
	/**
	 * @param channel_id the channel_id to set
	 */
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the add_time
	 */
	public Date getAdd_time() {
		return add_time;
	}
	/**
	 * @param add_time the add_time to set
	 */
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

