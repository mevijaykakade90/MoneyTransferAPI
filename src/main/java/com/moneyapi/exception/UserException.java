package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	private final String errorCode;

	@Getter
	private final HttpStatus httpStatus;

	public UserException(String message, String errorCode) {
		super(message);

		this.errorCode = errorCode;
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}

	public UserException(String message, String errorCode, HttpStatus httpStatus) {
		super(message);

		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}
}
