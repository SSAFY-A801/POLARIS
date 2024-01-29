package com.ssafy.polaris.book.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    // 200 OK : 성공

    SUCCESS_LIBRARY_VIEW(200, "서재 성공"),
    SUCCESS_USER_BOOK_VIEW(200, "사용자 도서 조회 성공"),

    // 201 CREATED : 새로운 리소스 생성
    SUCCESS_CREATE_USER_BOOK(201, "사용자 도서 등록 성공"),

    // 204 NO CONTENT : 성공하였으나, 반환할 값이 없음
    SUCEESS_UPDATE_USER_BOOK(204, "사용자 도서 업데이트 성공"),

    // 404 NOT FOUND : 유저를 찾을 수 없음
    FAIL_USER_VIEW(404, "프로필 조회 실패"),
    FAIL_USER_UPDATE(404, "프로필 업데이트 실패"),
    FAIL_USER_FOLLOW(404, "프로필 업데이트 실패"),
    FAIL_LIBRARY_VIEW(404, "서재 조회 실패");
    private final int status;
    private final String message;
}