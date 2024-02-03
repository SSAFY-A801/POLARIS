package com.ssafy.polaris.essay.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
	UNAUTHORIZED_USER(401, "권한이 없는 사용자입니다."),

	ESSAY_WRITE_SUCCESS(201, "글이 작성되었습니다."),
	ESSAY_READ_SUCCESS(200, "글을 조회하였습니다."),
	ESSAY_UPDATE_SUCCESS(200, "글을 수정하였습니다."),
	ESSAY_DELETE_SUCCESS(200, "글을 삭제하였습니다.")
	;

	private final int status;
	private final String message;
}