package com.ssafy.polaris.global.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	BAD_REQUEST(400, "잘못된 요청입니다."),
	WRONG_ARGUMENT(400, "잘못된 파라미터 입력입니다."),
	NO_BOOK_SELECTED(400, "책이 선택되지 않았습니다."),

	UNAUTHORIZED(401, "로그인한 사용자 정보를 찾을 수 없습니다."),
	WRONG_PASSWORD(401, "잘못된 비밀번호입니다."),

	FORBIDDEN(403, "접근할 수 없습니다."),


	USER_NOT_EXIST(404, "존재하지 않는 사용자입니다."),
	USER_BOOK_NOT_EXIST(404, "존재하지 않는 사용자 도서입니다."),
	NOT_FOUND(404, "리소스를 찾을 수 없습니다.")
	;
	private final int status;
	private final String message;
}
