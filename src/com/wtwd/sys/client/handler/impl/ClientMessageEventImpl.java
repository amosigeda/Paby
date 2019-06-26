package com.wtwd.sys.client.handler.impl;

import com.wtwd.sys.client.handler.EventHandler;


public abstract class ClientMessageEventImpl implements EventHandler {

	private int eventId;
			
	public void setEventId(int eventId){
		this.eventId = eventId;
	}
	
	public int getEventId(){
		
		return eventId;
	}
}
