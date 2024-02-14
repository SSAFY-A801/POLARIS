package com.ssafy.polaris.essay.exception;

import com.ssafy.polaris.global.exception.exceptions.category.ForbiddenException;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class EssayAlreadyExistException extends ForbiddenException {
	private static final ErrorCode errorCode = ErrorCode.ESSAY_ALREADY_EXIST;
	public EssayAlreadyExistException(String detailMessageKey,	Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
