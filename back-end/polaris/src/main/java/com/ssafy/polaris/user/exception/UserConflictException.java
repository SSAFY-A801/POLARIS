package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.ConflictException;
import com.ssafy.polaris.global.exception.response.ErrorCode;
import com.ssafy.polaris.user.response.StatusCode;

public class UserConflictException extends ConflictException {
	private static final ErrorCode errorCode = ErrorCode.USER_EMAIL_OR_NICKNAME_CONFLICT;
	public UserConflictException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
