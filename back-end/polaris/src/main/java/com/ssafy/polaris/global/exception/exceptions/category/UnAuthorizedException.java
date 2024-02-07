package com.ssafy.polaris.global.exception.exceptions.category;

import com.ssafy.polaris.global.exception.response.ErrorCode;

/**
 * 401
 * 로그인이 필요한 요청에 로그인을 하지 않아 발생
 */
public class UnAuthorizedException extends PolarisRuntimeException {
	protected static final String MESSAGE_KEY = "error.UnAuthorized";
	public UnAuthorizedException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
