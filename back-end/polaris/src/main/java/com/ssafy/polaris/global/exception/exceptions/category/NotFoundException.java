package com.ssafy.polaris.global.exception.exceptions.category;

import com.ssafy.polaris.global.exception.response.ErrorCode;

/**
 * 404 에러
 * 서버가 요청받은 리소스를 찾을 수 없을 때 발생
 */
public class NotFoundException extends PolarisRuntimeException {
	protected static final String MESSAGE_KEY = "error.NotFound";
	public NotFoundException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
