package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class TransactionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Getter
	private final String errorCode;

	@Getter
	private final HttpStatus httpStatus;

	public TransactionException(String message, String errorCode) {
		super(message);

		this.errorCode = errorCode;
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}

	public TransactionException(String message, String errorCode, HttpStatus httpStatus) {
		super(message);

		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}
}
