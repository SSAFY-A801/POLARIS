package com.ssafy.polaris.global.exception.exceptions.category;

/**
 * 커스텀 예외 중간 단계
 */
public class BadRequestException extends PolarisRuntimeException {

	protected static final String MESSAGE_KEY = "error.bad.request";

	public BadRequestException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, params);
	}
}
