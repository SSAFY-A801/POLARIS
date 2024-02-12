package com.ssafy.polaris.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 201 CREATED : 새로운 리소스 생성
	CREATED_CHATROOM_TRADE(201, "채팅방 생성 성공"),

	SUCCESS_READ_CHATROOM_LIST(200, "사용자 채팅 목록 조회 성공"),
	SUCCESS_READ_EMPTY_CHATROOM_LIST(200, "사용자의 채팅 목록이 없습니다."),

	SUCCESS_CONNECT_CHATROOM(200, "채팅방 입장 성공"),
	SUCCESS_SEND_MESSAGE(200, "채팅 보내기 성공"),

	SUCCESS_READ_CHATROOM_PARTICIPANT(200, "채팅 상대방 정보 조회 성공"),
	FAIL_READ_CHATROOM_PARTICIPANT(204, "채팅방 정보가 없거나, 채팅방 상대가 없습니다."),

	SUCCESS_READ_CHAT_MESSAGES(200, "기존 채팅 메세지 조회 성공"),
	SUCCESS_READ_EMPTY_CHAT_MESSAGES(200, "저장된 채팅 메세지가 없습니다."),

	SUCCESS_READ_CHATROOM_TRADE_BOOKS(200, "채팅방 거래 도서 목록 조회 성공"),
	SUCCESS_READ_EMPTY_CHATROOM_TRADE_BOOKS(200, "채팅방 거래 도서 목록이 비어 있습니다.");

	private final int status;
	private final String message;
}