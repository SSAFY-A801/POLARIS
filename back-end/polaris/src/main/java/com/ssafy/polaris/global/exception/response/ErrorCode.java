package com.ssafy.polaris.global.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	BAD_REQUEST(400, "잘못된 요청입니다."),
	WRONG_ARGUMENT(400, "잘못된 파라미터 입력입니다."),
	NO_BOOK_SELECTED(400, "책이 선택되지 않았습니다."),
	UNAVAILABLE_CHAT_USER(400, "스스로에게 교환을 걸 수 없습니다."),

	CERT_CODE_NOT_MATCH(400, "입력된 입력코드가 일치하지 않습니다."),

	UNAUTHORIZED(401, "사용자 정보를 찾을 수 없습니다."),
	WRONG_PASSWORD(401, "잘못된 비밀번호 입니다."),
	WRONG_EMAIL_OR_PASSWORD(401, "잘못된 이메일/비밀번호 입니다."),
	USER_NOT_AUTHORIZED(401, "로그인 된 사용자가 없습니다."),
	TOKEN_NOT_VALID(401, "토큰이 유효하지 않습니다."),
	MALFORMED_JWT(401, "토큰이 유효하지 않습니다."),
	EXPIRED_JWT(420, "토큰이 만료되었습니다. 다시 로그인해주세요."),
	USER_KAKAO_JOINED(401, "카카오 회원입니다. 일반로그인 할 수 없습니다."),
	USER_NOT_KAKAO_JOINED(421, "카카오 가입 회원이 아닙니다."),

	ESSAY_ALREADY_EXIST(403, "먼저 작성된 독후감이 있습니다."),
	FORBIDDEN(403, "접근할 수 없습니다."),

	USER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),
	USER_BOOK_NOT_FOUND(404, "존재하지 않는 사용자 도서입니다."),
	PROMOTION_NOT_FOUND(404, "존재하지 않는 홍보 글입니다."),
	NOT_FOUND(404, "리소스를 찾을 수 없습니다."),
	ESSAY_NOT_FOUND(404, "독후감을 찾을 수 없습니다."),
	COMMENT_NOT_FOUND(404, "독후감을 찾을 수 없습니다."),

	CERT_CODE_EXPIRED(408, "코드가 만료되었습니다."),

	ALREADY_KAKAO_JOINED(409, "이미 카카오가입을 한 사용자입니다."),
	USER_EMAIL_OR_NICKNAME_CONFLICT(409, "사용자 이메일 혹은 닉네임이 중복됩니다.")
	;

	private final int status;
	private final String message;
}
