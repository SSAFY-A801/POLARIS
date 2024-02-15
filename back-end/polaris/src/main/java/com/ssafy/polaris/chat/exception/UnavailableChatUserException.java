package com.ssafy.polaris.chat.exception;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UnavailableChatUserException extends BadRequestException {
	private static final ErrorCode errorCode = ErrorCode.UNAVAILABLE_CHAT_USER;
	public UnavailableChatUserException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
