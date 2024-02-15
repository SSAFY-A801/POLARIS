package com.ssafy.polaris.essay.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
	UNAUTHORIZED_USER(401, "권한이 없는 사용자입니다."),

	ESSAY_WRITE_SUCCESS(201, "글이 작성되었습니다."),
	ESSAY_READ_SUCCESS(200, "글을 조회하였습니다."),
	ESSAY_READ_LIST_SUCCESS(200, "글 목록을 조회하였습니다."),
	ESSAY_UPDATE_SUCCESS(200, "글을 수정하였습니다."),
	ESSAY_READ_MOST_SCRAPPED_SUCCESS(200, "이 주의 독후감을 조회하였습니다."),
	ESSAY_READ_SCRAPPED_SUCCESS(200, "나의 스크랩 리스트가 성공적으로 조회되었습니다."),
	ESSAY_DELETE_SUCCESS(200, "글을 삭제하였습니다."),

	SCRAP_ADD_SUCCESS(200, "스크랩을 추가했습니다."),
	SCRAP_REMOVE_SUCCESS(200, "스크랩을 취소했습니다."),
	ESSAY_READ_EMPTY_SCRAPPED_SUCCESS(204, "나의 빈 스크랩 리스트가 성공적으로 조회되었습니다."),
	ESSAY_READ_MOST_SCRAPPED_FAIL(404, "이 주의 독후감을 조회하지 못했습니다."),
	SCRAP_COUNT_SUCCESS(200, "스크랩 수를 조회했습니다.");

	private final int status;
	private final String message;
}