package com.founder.core;

import java.util.Map;

import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

public class CommonMessage implements Message<String> {

	public CommonMessage(Map<String, Object> headers, String text) {
		this.messageHeaders = new MessageHeaders(headers);
		this.text = text;
	}

	private MessageHeaders messageHeaders;

	private String text;

	@Override
	public MessageHeaders getHeaders() {
		return messageHeaders;
	}

	@Override
	public String getPayload() {
		return text;
	}

}
