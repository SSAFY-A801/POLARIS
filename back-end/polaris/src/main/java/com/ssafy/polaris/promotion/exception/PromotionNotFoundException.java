package com.ssafy.polaris.promotion.exception;

import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class PromotionNotFoundException extends NotFoundException {
	private static final ErrorCode errorCode = ErrorCode.PROMOTION_NOT_FOUND;
	public PromotionNotFoundException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
