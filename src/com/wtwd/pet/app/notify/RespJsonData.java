package com.wtwd.pet.app.notify;

public class RespJsonData {
	Integer msg_id;
	String msg_type;
	Integer msg_ind_id;
	String msg_date;
	String msg_txt;
	Integer device_id;
	Integer from_usrid;
	Integer to_usrid;
	Integer eference_id;
	Integer share_id;
	Integer pet_id;
	String from_nick;
	String from_email;
	String to_nick;
	String to_email;
	Integer duration;
	String summary;
	String msg_date_utc;

	
	
	public String getMsg_date_utc() {
		return msg_date_utc;
	}
	public void setMsg_date_utc(String msg_date_utc) {
		this.msg_date_utc = msg_date_utc;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		if (msg_type != null )
			this.msg_type = msg_type;
	}
	public Integer getMsg_ind_id() {
		return msg_ind_id;
	}
	public void setMsg_ind_id(Integer msg_ind_id) {
		if (msg_ind_id != null )
			this.msg_ind_id = msg_ind_id;
	}
	public String getMsg_date() {
		return msg_date;
	}
	public void setMsg_date(String msg_date) {
		if (msg_date != null )
			this.msg_date = msg_date;
	}
	public String getMsg_txt() {
		return msg_txt;
	}
	public void setMsg_txt(String msg_txt) {
		if (msg_txt != null )
			this.msg_txt = msg_txt;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		if ( summary != null )
			this.summary = summary;
	}
		
	public Integer getDevice_id() {
		return device_id;
	}
	public void setDevice_id(Integer device_id) {
		if (device_id != null )
			this.device_id = device_id;
	}
	public Integer getFrom_usrid() {
		return from_usrid;
	}
	public void setFrom_usrid(Integer from_usrid) {
		if (from_usrid != null )
			this.from_usrid = from_usrid;
	}
	public Integer getTo_usrid() {
		return to_usrid;
	}
	public void setTo_usrid(Integer to_usrid) {
		if (to_usrid != null )
			this.to_usrid = to_usrid;
	}
	public Integer getEference_id() {
		return eference_id;
	}
	public void setEference_id(Integer eference_id) {
		if (eference_id != null )
			this.eference_id = eference_id;
	}
	public Integer getShare_id() {
		return share_id;
	}
	public void setShare_id(Integer share_id) {
		if (share_id != null )
			this.share_id = share_id;
	}
	public Integer getPet_id() {
		return pet_id;
	}
	public void setPet_id(Integer pet_id) {
		if (pet_id != null )
			this.pet_id = pet_id;
	}
	public String getFrom_nick() {
		return from_nick;
	}
	public void setFrom_nick(String from_nick) {
		if (from_nick != null )
			this.from_nick = from_nick;
	}
	public String getFrom_email() {
		return from_email;
	}
	public void setFrom_email(String from_email) {
		if (from_email != null )
			this.from_email = from_email;
	}
	public String getTo_nick() {
		return to_nick;
	}
	public void setTo_nick(String to_nick) {
		if (to_nick != null )
			this.to_nick = to_nick;
	}
	public String getTo_email() {
		return to_email;
	}
	public void setTo_email(String to_email) {
		if (to_email != null )
			this.to_email = to_email;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		if (duration != null )
			this.duration = duration;
	}

	
	
	public Integer getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Integer msg_id) {
		if ( msg_id != null )
			this.msg_id = msg_id;
	}
	
	
	public RespJsonData() {
		msg_type = "1";
		msg_ind_id = 1;
		 msg_date = "";
		 msg_txt = "";
		 device_id = 0;
		 from_usrid = 0;
		 to_usrid = 0;
		 eference_id = 0;
		 share_id = 0;
		 pet_id = 0;
		 from_nick = "";
		 from_email = "";
		 to_nick = "";
		 to_email = "";
		 duration = 0;
		 summary = "";
	}
	
}
