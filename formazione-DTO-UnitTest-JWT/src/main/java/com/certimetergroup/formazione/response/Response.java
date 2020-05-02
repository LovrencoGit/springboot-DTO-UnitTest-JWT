package com.certimetergroup.formazione.response;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;

public class Response {

	private int code;
	private String message;
	
	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Response(ResponseCodeEnum responseCodeEnum) {
		super();
		this.code = responseCodeEnum.id;
		this.message = responseCodeEnum.description;
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
		return "Response {code=" + code + ", message=" + message + "}";
	} 
	
}
