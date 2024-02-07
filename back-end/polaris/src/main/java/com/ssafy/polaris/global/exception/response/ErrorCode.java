package com.ssafy.polaris.global.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	BAD_REQUEST(400, "잘못된 요청입니다."),
	USER_NOT_EXIST(404, "존재하지 않는 사용자입니다."),
	WRONG_PASSWORD(401, "잘못된 비밀번호입니다."),
	WRONG_ARGUMENT(400, "잘못된 파라미터 입력입니다.");

	private final int status;
	private final String message;
}
