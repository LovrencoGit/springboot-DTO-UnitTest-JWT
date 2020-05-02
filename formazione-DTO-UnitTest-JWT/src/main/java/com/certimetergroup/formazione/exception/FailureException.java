package com.certimetergroup.formazione.exception;

import org.springframework.http.HttpStatus;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;

public class FailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	private HttpStatus httpStatus;
	private ResponseCodeEnum responseCodeEnum;
	
	
	
	public FailureException() {
		super();
	}
	public FailureException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	public FailureException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	public FailureException(String arg0) {
		super(arg0);
	}
	public FailureException(Throwable arg0) {
		super(arg0);
	}


	public FailureException(HttpStatus httpStatus, ResponseCodeEnum responseCodeEnum) {
		super();
		this.httpStatus = httpStatus;
		this.responseCodeEnum = responseCodeEnum;
	}
	
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public ResponseCodeEnum getResponseCodeEnum() {
		return responseCodeEnum;
	}
	public void setResponseCodeEnum(ResponseCodeEnum responseCodeEnum) {
		this.responseCodeEnum = responseCodeEnum;
	}

	
	@Override
	public String toString() {
		return "FailureException {httpStatus=" + httpStatus + ", responseErrorEnum=" + responseCodeEnum + "}";
	}
		
}
