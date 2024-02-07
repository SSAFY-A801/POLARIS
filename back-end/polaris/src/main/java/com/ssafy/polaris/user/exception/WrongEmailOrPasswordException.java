package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class WrongEmailOrPasswordException extends UnAuthorizedException {
	private static final ErrorCode errorCode = ErrorCode.WRONG_EMAIL_OR_PASSWORD;

	public WrongEmailOrPasswordException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
