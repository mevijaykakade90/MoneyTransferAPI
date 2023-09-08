package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

public class UserFoundException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserFoundException(String message, String errorCode) {
		super(message, errorCode);
	}

	public UserFoundException(String message, String errorCode, HttpStatus httpStatus) {
		super(message, errorCode, httpStatus);
	}

}
