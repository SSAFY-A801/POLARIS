package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserNotFoundException extends NotFoundException {
	private static final ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

	public UserNotFoundException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
