package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class NoBookSelectedException extends BadRequestException {
	private static final ErrorCode errorCode = ErrorCode.NO_BOOK_SELECTED;

	public NoBookSelectedException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
