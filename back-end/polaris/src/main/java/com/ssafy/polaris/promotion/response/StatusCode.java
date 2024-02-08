package com.ssafy.polaris.promotion.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
	PROMOTION_WRITE_SUCCESS(201, "글이 작성되었습니다."),
	PROMOTION_READ_SUCCESS(200, "글을 조회하였습니다."),
	PROMOTION_READ_LIST_SUCCESS(200, "글 목록을 조회하였습니다."),
	PROMOTION_UPDATE_SUCCESS(200, "글을 수정하였습니다."),
	PROMOTION_DELETE_SUCCESS(200, "글을 삭제하였습니다."),

	SCRAP_ADD_SUCCESS(200, "스크랩을 추가했습니다."),
	SCRAP_REMOVE_SUCCESS(200, "스크랩을 취소했습니다."),
	SCRAP_COUNT_SUCCESS(200, "스크랩 수를 조회했습니다.")
	;

	private final int status;
	private final String message;
}