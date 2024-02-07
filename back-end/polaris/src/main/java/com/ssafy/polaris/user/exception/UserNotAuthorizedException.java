package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserNotAuthorizedException extends UnAuthorizedException {
	private static final ErrorCode errorCode = ErrorCode.USER_NOT_AUTHORIZED;

	public UserNotAuthorizedException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
