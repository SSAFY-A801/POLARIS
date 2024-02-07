package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.AuthenticationException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserNotExist extends AuthenticationException {
	static final String MESSAGE_KEY = "wrong-password";

	public UserNotExist(ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + " : " + errorCode.getMessage(), params);
	}

	public UserNotExist() {
		super(MESSAGE_KEY + " : " + ErrorCode.USER_NOT_EXIST.getMessage(), null);
	}
}

