package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class UserNotKakaoJoined extends UnAuthorizedException {
	private static final ErrorCode errorCode = ErrorCode.USER_NOT_KAKAO_JOINED;
	public UserNotKakaoJoined(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
