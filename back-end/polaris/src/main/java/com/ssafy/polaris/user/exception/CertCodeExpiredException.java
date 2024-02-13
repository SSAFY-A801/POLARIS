package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.TimeOutException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class CertCodeExpiredException extends TimeOutException {
	private static final ErrorCode errorCode = ErrorCode.CERT_CODE_EXPIRED;
	public CertCodeExpiredException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
