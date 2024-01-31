package com.ssafy.polaris.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	SUCCESS_LOGIN(200, "로그인 성공"),
	CREATED_USER(201, "회원가입 성공");

	private final int status;
	private final String message;
}