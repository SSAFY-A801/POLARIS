package com.ssafy.polaris.trade.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 200 OK : 성공
	SUCCESS_VIEW(200, "거래 도서 목록 조회 성공"),
	SUCCESS_COMPLETE_TRADE(200, "거래가 완료되었습니다."),
	SUCCESS_DELETE_TRADE(200, "거래가 취소 되었습니다. 채팅방 나가기와 동일합니다.");

	// SUCCESS_READ_USER(200, "회원 정보 조회 성공"),
	// SUCCESS_UPDATE_USER(200, "회원 정보 수정 성공"),
	// SUCCESS_LEAVE_USER(200, "회원 탈퇴 성공"),
	// VALID_EMAIL(200, "유효한 이메일입니다."),
	//
	// // 201 CREATED : 새로운 리소스 생성
	// CREATED_CHATROOM_TRADE(201, "채팅방 생성 성공");

	private final int status;
	private final String message;
}