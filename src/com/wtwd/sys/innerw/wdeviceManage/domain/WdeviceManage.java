package com.wtwd.sys.innerw.wdeviceManage.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WdeviceManage extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5804699578005596499L;
	private Integer id;
	private Date input_time;
	private Integer company_id;
	private Integer project_id;
	private String count_num;
	private String mini_num;
	private String max_num;
	private String type;
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
	 * @return the input_time
	 */
	public Date getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time the input_time to set
	 */
	public void setInput_time(Date input_time) {
		this.input_time = input_time;
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
	 * @return the project_id
	 */
	public Integer getProject_id() {
		return project_id;
	}
	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}
	/**
	 * @return the count_num
	 */
	public String getCount_num() {
		return count_num;
	}
	/**
	 * @param count_num the count_num to set
	 */
	public void setCount_num(String count_num) {
		this.count_num = count_num;
	}
	/**
	 * @return the mini_num
	 */
	public String getMini_num() {
		return mini_num;
	}
	/**
	 * @param mini_num the mini_num to set
	 */
	public void setMini_num(String mini_num) {
		this.mini_num = mini_num;
	}
	/**
	 * @return the max_num
	 */
	public String getMax_num() {
		return max_num;
	}
	/**
	 * @param max_num the max_num to set
	 */
	public void setMax_num(String max_num) {
		this.max_num = max_num;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}

