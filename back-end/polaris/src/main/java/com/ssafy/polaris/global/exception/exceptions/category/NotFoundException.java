package com.ssafy.polaris.global.exception.exceptions.category;

public class NotFoundException extends PolarisRuntimeException {
	protected static final String MESSAGE_KEY = "error.not.found";
	public NotFoundException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, params);
	}
}
