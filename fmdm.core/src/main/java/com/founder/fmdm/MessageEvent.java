package com.founder.fmdm;

import com.founder.fmdm.entity.MqMessageLib;
import com.lmax.disruptor.EventFactory;

public class MessageEvent {

	public String messageType;
	public String messageData;
	public String tableName;
	public String termName;
	public String oldValue;
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	private MqMessageLib event;

	public final static EventFactory<MessageEvent> EVENT_FACTORY = new EventFactory<MessageEvent>() {
		public MessageEvent newInstance() {
			return new MessageEvent();
		}
	};

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}

	public MqMessageLib getEvent() {
		return event;
	}

	public void setEvent(MqMessageLib event) {
		this.event = event;
	}

	public void reset() {
		this.event = null;
		this.messageData = null;
		this.messageType = null;
		this.tableName = null;
		this.termName = null;
		this.oldValue = null;
	}

	public void copyFrom(MessageEvent src) {
		event = src.getEvent();
		messageType = src.messageType;
		messageData = src.messageData;
		tableName = src.tableName;
		termName = src.termName;
		oldValue = src.oldValue;
	}
}
