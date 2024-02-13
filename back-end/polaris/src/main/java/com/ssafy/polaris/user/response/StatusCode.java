package com.ssafy.polaris.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	SUCCESS_LOGIN(200, "로그인 성공"),

	SUCCESS_REISSUE(200, "재발급 성공"),

	CREATED_USER(201, "회원가입 성공"),

	SUCCESS_LOGOUT(200, "로그아웃 성공"),

	EMAIL_IN_USE(200, "사용중인 이메일입니다."),

	EMAIL_NOT_IN_USE(200, "사용중이지 않은 이메일입니다."),

	EMAIL_CERT_SUCCESS(200, "이메일 인증이 완료되었습니다."),

	NICKNAME_IN_USE(200, "사용중인 닉네임입니다."),

	NICKNAME_NOT_IN_USE(200, "사용중이지 않은 닉네임입니다."),

	SUCCESS_RESIGNATION(200, "회원 탈퇴 성공"),

	SUCCESS_PASSWORD_SET(200, "비밀번호 수정 완료"),

	KAKAO_JOIN_NEEDED(203, "kakao 회원이 아닙니다. 회원가입 진행 필요")
	;


	private final int status;
	private final String message;
}