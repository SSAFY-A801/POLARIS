package com.ssafy.polaris.email.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	BAD_REQUEST(400, "잘못된 요청입니다."),

	PASSWORD_EMAIL_SUCCEEDED(200, "이메일로 임시 비밀번호가 전송되었습니다"),

	CERTIFICATION_EMAIL_SEND_SUCCEEDED(200, "이메일이 송신되었습니다"),
	CERTIFICATION_EMAIL_CERTIFICATED(200, "이메일 인증이 완료되었습니다"),
	CERTIFICATION_EMAIL_NOT_CERTIFICATED(401, "이메일 인증에 실패하였습니다"),
	CERTIFICATION_EMAIL_TIME_OUT(408, "요청한 시간이 초과되었습니다");

	private final int status;
	private final String message;
}