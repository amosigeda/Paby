package com.wtwd.sys.innerw.wmonitorInfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WmonitorInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4400193937741373241L;
	private Integer id;
	private Date start_time;
	private Date end_time;
	private Integer cost_time;
	private String function;
	private String function_href;
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
	 * @return the start_time
	 */
	public Date getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	/**
	 * @return the end_time
	 */
	public Date getEnd_time() {
		return end_time;
	}
	/**
	 * @param end_time the end_time to set
	 */
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	/**
	 * @return the cost_time
	 */
	public Integer getCost_time() {
		return cost_time;
	}
	/**
	 * @param cost_time the cost_time to set
	 */
	public void setCost_time(Integer cost_time) {
		this.cost_time = cost_time;
	}
	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	/**
	 * @return the function_href
	 */
	public String getFunction_href() {
		return function_href;
	}
	/**
	 * @param function_href the function_href to set
	 */
	public void setFunction_href(String function_href) {
		this.function_href = function_href;
	}
}
