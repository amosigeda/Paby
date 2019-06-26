package com.wtwd.sys.userinfo.form;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicFormBean;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class UserInfoForm extends PublicFormBean implements Serializable {

	private static final long serialVersionUID = -4230576721800974875L;

	public UserInfoForm() {
	}

	private String userName;
	private String remark;
	private String userCode;
	private String passWrd;
	private String passWrd1;
	private Date createDate;
	private Date updateDate;
	private String groupCode;
	private int tag;
	private int id;
	private String code;     //��¼������ɫ�ı���
	private String addUser;   //��Ӹ��˻����˻�
	
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId(){
		return this.id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setPassWrd1(String passWrd1){
		this.passWrd1 = passWrd1;
	}
	public String getPassWrd1(){
		return passWrd1;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setPassWrd(String passWrd) {
		this.passWrd = passWrd;
	}

	public String getPassWrd() {
		return this.passWrd;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return this.tag;
	}
}
