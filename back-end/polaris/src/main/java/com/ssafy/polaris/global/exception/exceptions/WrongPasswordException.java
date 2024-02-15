package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class WrongPasswordException extends UnAuthorizedException {
	private static final ErrorCode errorCode = ErrorCode.WRONG_PASSWORD;

	public WrongPasswordException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
