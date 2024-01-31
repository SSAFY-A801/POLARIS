package com.ssafy.polaris.profile.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    // 200 OK : 성공
    SUCCESS_VIEW(200, "프로필 조회 성공"),
    SUCCESS_READ_USER(200, "회원 정보 조회 성공"),
    SUCCESS_UPDATE_USER(200, "회원 정보 수정 성공"),
    SUCCESS_LEAVE_USER(200, "회원 탈퇴 성공"),
    VALID_EMAIL(200, "유효한 이메일입니다."),

    // 201 CREATED : 새로운 리소스 생성

    // 204 NO CONTENT : 성공하였으나, 반환할 값이 없음
    SUCEESS_UPDATE_USER(204, "프로필 업데이트 성공"),
    SUCCESS_FOLLOW_USER(204, "팔로우 성공"),


    // 404 NOT FOUND : 유저를 찾을 수 없음
    FAIL_USER_VIEW(404, "프로필 조회 실패"),
    FAIL_USER_UPDATE(404, "프로필 업데이트 실패"),
    FAIL_USER_FOLLOW(404, "프로필 업데이트 실패");
    private final int status;
    private final String message;
}