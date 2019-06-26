package com.wtwd.sys.innerw.wroleFuncInfo.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WroleFuncInfo extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6930060892140254367L;
	private Integer id;
	private String roleCode;
	private String funcCode;
	private String userCode;
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
	 * @return the funcCode
	 */
	public String getFuncCode() {
		return funcCode;
	}
	/**
	 * @param funcCode the funcCode to set
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}

