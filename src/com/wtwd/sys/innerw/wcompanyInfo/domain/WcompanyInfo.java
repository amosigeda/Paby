package com.wtwd.sys.innerw.wcompanyInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WcompanyInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1591346440098012628L;
	private Integer id;
	private String company_no;
	private String company_name;
	private String channel_id;
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
	 * @return the company_no
	 */
	public String getCompany_no() {
		return company_no;
	}
	/**
	 * @param company_no the company_no to set
	 */
	public void setCompany_no(String company_no) {
		this.company_no = company_no;
	}
	/**
	 * @return the channel_id
	 */
	public String getChannel_id() {
		return channel_id;
	}
	/**
	 * @param channel_id the channel_id to set
	 */
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	/**
	 * @return the company_name
	 */
	public String getCompany_name() {
		return company_name;
	}
	/**
	 * @param company_name the company_name to set
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
}

