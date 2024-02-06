package com.ssafy.polaris.global.exception.exceptions.category;

import lombok.Getter;

/**
 * 최상위 커스텀 에러
 */
@Getter
public abstract class PolarisRuntimeException extends RuntimeException {
	private final String messageKey;
	private final Object[] params;

	public PolarisRuntimeException(String messageKey, Object[] params) {
		this.messageKey = messageKey;
		this.params = params;
	}
}
