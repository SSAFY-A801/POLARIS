package com.ssafy.polaris.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 201 CREATED : 새로운 리소스 생성
	CREATED_CHATROOM_TRADE(201, "채팅방 생성 성공"),

	SUCCESS_READ_CHATROOM_LIST(200, "사용자 채팅 목록 조회 성공"),
	SUCCESS_READ_EMPTY_CHATROOM_LIST(200, "사용자의 채팅 목록이 없습니다.");

	private final int status;
	private final String message;
}