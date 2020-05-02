package com.certimetergroup.formazione.controller;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.exception.FailureException;
import com.certimetergroup.formazione.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	final  Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@ExceptionHandler(FailureException.class)
	public ResponseEntity<Response> handleFailureException(FailureException exception)  {
		logger.error("EXCEPTION => ", exception);
		HttpStatus httpStatus = exception.getHttpStatus();
		ResponseCodeEnum responseCodeEnum = exception.getResponseCodeEnum();
		return ResponseEntity.status(httpStatus).body(
			new Response(responseCodeEnum)
		);
	}

	/*** CASO POZZO (sempre presente) ***/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception exception)  {
		logger.error("UNEXPECTED ERROR", exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
			new Response(ResponseCodeEnum.ERR_500)
		);
	}
}
