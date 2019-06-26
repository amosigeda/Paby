package com.wtwd.sys.innerw.wpetOfUser.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class WpetOfUser extends PublicVoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6267585517706945470L;
	private Integer pets_pet_id;
	private Integer user_id;
	private String nickname;
	/**
	 * @return the pets_pet_id
	 */
	public Integer getPets_pet_id() {
		return pets_pet_id;
	}
	/**
	 * @param pets_pet_id the pets_pet_id to set
	 */
	public void setPets_pet_id(Integer pets_pet_id) {
		this.pets_pet_id = pets_pet_id;
	}
	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}

