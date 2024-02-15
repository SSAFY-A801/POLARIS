package com.ssafy.polaris.essay.exception;

import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class EssayNotFoundException extends NotFoundException {
	private static final ErrorCode errorCode = ErrorCode.ESSAY_NOT_FOUND;
	public EssayNotFoundException(String detailMessageKey,	Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
