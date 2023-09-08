package com.moneyapi.constant;

public class ErrorCode {

	private ErrorCode() {

	}

	/**
	 * Error Code for any internal system error
	 */
	public static final String SYSTEM_ERROR = "ERR_SYS_001";

	/**
	 * Error Code for gateway timeout error
	 */
	public static final String TIMEOUT_ERROR = "ERR_SYS_002";

	/**
	 * Error Code for general user error
	 */
	public static final String USER_ERROR = "ERR_USER_001";

	/**
	 * Error Code for any error related to account
	 */
	public static final String TRANSACTION_ERROR = "ERR_CLIENT_002";

}
