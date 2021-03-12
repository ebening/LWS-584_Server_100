package com.lowes.util;

public class WSFacturaMessageResponse {

	private WSMessageType messageType;
	private int code;
	private String message;

	public WSFacturaMessageResponse() {

	}

	public WSFacturaMessageResponse(WSMessageType messageType, int code,
			String message) {
		super();
		this.messageType = messageType;
		this.code = code;
		this.message = message;
	}

	public WSMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(WSMessageType messageType) {
		this.messageType = messageType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "WSFacturaMessageResponse [messageType=" + messageType
				+ ", code=" + code + ", message=" + message + "]";
	}

}
