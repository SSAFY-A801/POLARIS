package com.ssafy.polaris.promotion.exception;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class WrongSearchKeyException extends BadRequestException{
	private static final ErrorCode errorCode = ErrorCode.WRONG_ARGUMENT;
	public WrongSearchKeyException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
