package com.ssafy.polaris.trade.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 200 OK : 성공
	SUCCESS_VIEW_TRADE_BOOKS(200, "거래 도서 목록 조회 성공"),
	SUCCESS_VIEW_EMPTY_TRADE_BOOKS(200, "거래 도서 목록이 없습니다."),

	SUCCESS_COMPLETE_TRADE(200, "거래가 완료되었습니다."),
	SUCCESS_DELETE_TRADE(200, "거래가 취소 되었습니다. 채팅방 나가기와 동일합니다."),
	SUCCESS_SELECT_TRADE_USER_BOOK(200, "거래 도서가 성공적으로 선택되었습니다. "),

	SUCCESS_USER_EXCHANGE_HISTORY_VIEW(200, "교환 내역이 성공적으로 조회되었습니다."),
	SUCCESS_USER_PURCHASE_HISTORY_VIEW(200, "구매 내역이 성공적으로 조회되었습니다."),
	SUCCESS_USER_EMPTY_EXCHANGE_HISTORY_VIEW(204, "빈 교환 내역이 성공적으로 조회되었습니다."),
	SUCCESS_USER_EMPTY_PURCHASE_HISTORY_VIEW(204, "빈 구매 내역이 성공적으로 조회되었습니다.");
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