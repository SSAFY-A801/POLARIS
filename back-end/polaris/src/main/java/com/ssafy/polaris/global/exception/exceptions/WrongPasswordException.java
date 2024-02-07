package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.AuthenticationException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class WrongPasswordException extends AuthenticationException {
	static final String MESSAGE_KEY = "wrong-password";

	public WrongPasswordException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, params);
	}

	public WrongPasswordException(ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + " : " + errorCode.getMessage(), params);
	}
}

