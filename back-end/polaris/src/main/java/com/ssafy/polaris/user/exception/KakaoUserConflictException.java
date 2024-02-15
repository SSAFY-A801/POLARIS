package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.ConflictException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class KakaoUserConflictException extends ConflictException {
	private static final ErrorCode errorCode = ErrorCode.ALREADY_KAKAO_JOINED;
	public KakaoUserConflictException(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
