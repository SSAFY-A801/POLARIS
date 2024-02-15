package com.ssafy.polaris.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
	SUCCESS_GET_COMMENT_LIST(200, "댓글 목록 조회 성공"),
	SUCCESS_UPDATE_COMMENT(200, "댓글 수정 성공"),
	SUCCESS_DELETE_COMMENT(200, "댓글 삭제 성공"),

	SUCCESS_CREATE_COMMENT(201, "댓글 작성 성공")
	;


	private final int status;
	private final String message;
}