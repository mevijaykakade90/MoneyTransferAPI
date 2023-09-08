package com.moneyapi.exception;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceExceptiom extends TransactionException {
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceExceptiom(String message, String errorCode) {
		super(message, errorCode);
	}

	public InsufficientBalanceExceptiom(String message, String errorCode, HttpStatus httpStatus) {
		super(message, errorCode, httpStatus);
	}
}
