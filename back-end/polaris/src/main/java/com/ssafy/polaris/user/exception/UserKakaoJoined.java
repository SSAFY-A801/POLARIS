package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserKakaoJoined extends UnAuthorizedException {
	private static final ErrorCode errorCode = ErrorCode.USER_KAKAO_JOINED;
	public UserKakaoJoined(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
