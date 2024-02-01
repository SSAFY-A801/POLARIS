package com.ssafy.polaris.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	BAD_REQUEST(400, "잘못된 요청입니다."),
	UNAUTHORIZED_USER(401, "권한이 없는 사용자입니다."),
	NO_ACCESS_TOKEN(401, "접근 토큰을 찾을 수 없습니다."),

	SUCCESS_LOGIN(200, "로그인 성공"),
	WRONG_ID_OR_PASSWORD(401, "잘못된 id 이거나 password 입니다."),

	CREATED_USER(201, "회원가입 성공"),
	USER_EMAIL_CONFLICT(409, "이미 가입한 회원입니다."),
	USER_NICKNAME_CONFLICT(409, "닉네임이 중복됩니다."),
	USER_EMAIL_OR_NICKNAME_CONFLICT(409, "중복되는 이메일/닉네임 입니다."),

	SUCCESS_LOGOUT(200, "로그아웃 성공"),

	EMAIL_IN_USE(200, "사용중인 이메일입니다."),
	EMAIL_NOT_IN_USE(200, "사용중이지 않은 이메일입니다."),
	NICKNAME_IN_USE(200, "사용중인 닉네임입니다."),
	NICKNAME_NOT_IN_USE(200, "사용중이지 않은 닉네임입니다."),

	SUCCESS_RESIGNATION(200, "회원 탈퇴 성공")
	;


	private final int status;
	private final String message;
}