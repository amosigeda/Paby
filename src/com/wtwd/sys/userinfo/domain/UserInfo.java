package com.wtwd.sys.userinfo.domain;

import java.io.Serializable;
import java.util.Date;

import com.godoing.rose.http.PublicVoBean;
/**
 * �û�POJO
 * @author hepx
 * @date 2010-10-29 ����10:34:23
 */
public class UserInfo extends PublicVoBean implements Serializable {

	private static final long serialVersionUID = 3606211640133561712L;
	
	public UserInfo(){
	}

	private String userName;//�û���
	private String remark;//��ע
	private String userCode;//�û����룬��¼�ô���
	private String passWrd; //����
	private String passWrd1;//����1
	private Date createDate;//����ʱ��
	private Date updateDate;//����ʱ��
	private String groupCode;//�û����ڵ���
	private int tag;//�û�״̬
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
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getUserName(){
		return this.userName;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}

	public void setUserCode(String userCode){
		this.userCode = userCode;
	}
	public String getUserCode(){
		return this.userCode;
	}

	
	public void setPassWrd(String passWrd){
		this.passWrd = passWrd;
	}
	public String getPassWrd(){
		return this.passWrd;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	public Date getCreateDate(){
		return this.createDate;
	}
	
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	public Date getUpdateDate(){
		return this.updateDate;
	}
	
	public void setGroupCode(String groupCode){
		this.groupCode = groupCode;
	}
	public String getGroupCode(){
		return this.groupCode;
	}
	
	public void setTag(int tag){
		this.tag = tag;
	}
	public int getTag(){
		return this.tag;
	}
	public void setPassWrd1(String passWrd1) {
		this.passWrd1 = passWrd1;
		
	}
	public String getPassWrd1(){
		return this.passWrd1;
	}
}