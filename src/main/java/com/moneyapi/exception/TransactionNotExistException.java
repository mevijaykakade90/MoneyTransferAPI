package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

public class TransactionNotExistException extends UserException {

	private static final long serialVersionUID = 1L;

	public TransactionNotExistException(String message, String errorCode) {
		super(message, errorCode);
	}
	
	public TransactionNotExistException(String message, String errorCode, HttpStatus httpStatus) {
		super(message, errorCode, httpStatus);
	}
}
