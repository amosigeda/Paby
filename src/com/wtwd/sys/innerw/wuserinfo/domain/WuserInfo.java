package com.wtwd.sys.innerw.wuserinfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;

public class WuserInfo extends PublicVoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2308371750897517502L;
	private Integer id;
	private String userCode;
	private String userName;
	private String passWrd;
	private String passWrd1;
	private Integer tag;
	private Date createDate;
	private Date updateDate;
	private String groupCode;
	private String remark;
	private String code;
	private String addUser;
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
	 * @return the passWrd
	 */
	public String getPassWrd() {
		return passWrd;
	}
	/**
	 * @param passWrd the passWrd to set
	 */
	public void setPassWrd(String passWrd) {
		this.passWrd = passWrd;
	}
	/**
	 * @return the passWrd1
	 */
	public String getPassWrd1() {
		return passWrd1;
	}
	/**
	 * @param passWrd1 the passWrd1 to set
	 */
	public void setPassWrd1(String passWrd1) {
		this.passWrd1 = passWrd1;
	}
	/**
	 * @return the tag
	 */
	public Integer getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the addUser
	 */
	public String getAddUser() {
		return addUser;
	}
	/**
	 * @param addUser the addUser to set
	 */
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
}

