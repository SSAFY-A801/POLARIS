package com.ssafy.polaris.global.exception.exceptions.category;

public class UnAuthorizedException extends PolarisRuntimeException {
	protected static final String MESSAGE_KEY = "error.unauthenticated";
	public UnAuthorizedException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, params);
	}
}
