package com.ssafy.polaris.global.exception.exceptions.category;

public class AuthenticationException extends PolarisRuntimeException{

	protected static final String MESSAGE_KEY = "error.authentication";

	public AuthenticationException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, params);
	}
}

