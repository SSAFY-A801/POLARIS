package com.ssafy.polaris.global.exception.exceptions.category;

public class ForbiddenException extends PolarisRuntimeException {
	protected static final String MESSAGE_KEY = "error.forbidden";

	public ForbiddenException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, params);
	}
}
