package com.ssafy.polaris.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	SUCCESS_LOGIN(200, "로그인 성공"),
	CREATED_USER(201, "회원가입 성공"),

	EMAIL_IN_USE(200, "사용중인 이메일입니다."),
	EMAIL_NOT_IN_USE(200, "사용중이지 않은 이메일입니다."),
	NICKNAME_IN_USE(200, "사용중인 닉네임입니다."),
	NICKNAME_NOT_IN_USE(200, "사용중이지 않은 닉네임입니다.");


	private final int status;
	private final String message;
}