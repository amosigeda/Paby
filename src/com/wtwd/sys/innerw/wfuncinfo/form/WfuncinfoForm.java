package com.wtwd.sys.innerw.wfuncinfo.form;

import java.io.Serializable;

import com.godoing.rose.http.PublicFormBean;

public class WfuncinfoForm extends PublicFormBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7724455133872899528L;
	private Integer id;
	private String funcCode;
	private String funcName;
	private String funcDesc;
	private String superCode;
	private Integer levels;
	private Integer funcSort;
	private String statu;
	private String funcDo;
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
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}
	/**
	 * @param funcName the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	/**
	 * @return the superCode
	 */
	public String getSuperCode() {
		return superCode;
	}
	/**
	 * @param superCode the superCode to set
	 */
	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}
	/**
	 * @return the funcDesc
	 */
	public String getFuncDesc() {
		return funcDesc;
	}
	/**
	 * @param funcDesc the funcDesc to set
	 */
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	/**
	 * @return the levels
	 */
	public Integer getLevels() {
		return levels;
	}
	/**
	 * @param levels the levels to set
	 */
	public void setLevels(Integer levels) {
		this.levels = levels;
	}
	/**
	 * @return the funcSort
	 */
	public Integer getFuncSort() {
		return funcSort;
	}
	/**
	 * @param funcSort the funcSort to set
	 */
	public void setFuncSort(Integer funcSort) {
		this.funcSort = funcSort;
	}
	/**
	 * @return the statu
	 */
	public String getStatu() {
		return statu;
	}
	/**
	 * @param statu the statu to set
	 */
	public void setStatu(String statu) {
		this.statu = statu;
	}
	/**
	 * @return the funcDo
	 */
	public String getFuncDo() {
		return funcDo;
	}
	/**
	 * @param funcDo the funcDo to set
	 */
	public void setFuncDo(String funcDo) {
		this.funcDo = funcDo;
	}
	
}
