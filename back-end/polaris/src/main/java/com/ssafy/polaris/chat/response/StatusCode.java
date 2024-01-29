package com.ssafy.polaris.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

	// 201 CREATED : 새로운 리소스 생성
	CREATED_CHATROOM_TRADE(201, "채팅방 생성 성공");
	private final int status;
	private final String message;
}