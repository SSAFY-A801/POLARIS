package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.ForbiddenException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserForbiddenException extends ForbiddenException {
	private static final ErrorCode errorCode = ErrorCode.FORBIDDEN;
	public UserForbiddenException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
