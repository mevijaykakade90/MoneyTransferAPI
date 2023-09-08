package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

public class UserNotExistException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserNotExistException(String message, String errorCode) {
		super(message, errorCode);
	}
	
	public UserNotExistException(String message, String errorCode, HttpStatus httpStatus) {
		super(message, errorCode, httpStatus);
	}
}
