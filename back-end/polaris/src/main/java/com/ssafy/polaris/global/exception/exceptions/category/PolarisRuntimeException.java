package com.ssafy.polaris.global.exception.exceptions.category;

import com.ssafy.polaris.global.exception.response.ErrorCode;

import lombok.Getter;

/**
 * 최상위 커스텀 에러
 */
@Getter
public abstract class PolarisRuntimeException extends RuntimeException {
	private final String messageKey;
	private final Object[] params;
	private final ErrorCode errorCode;

	public PolarisRuntimeException(String messageKey, ErrorCode errorCode, Object... params) {
		this.messageKey = messageKey;
		this.params = params;
		this.errorCode = errorCode;
	}
}
