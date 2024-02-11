package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class WrongSearchConditionException extends BadRequestException {
	private static final ErrorCode errorCode = ErrorCode.WRONG_ARGUMENT;

	public WrongSearchConditionException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
