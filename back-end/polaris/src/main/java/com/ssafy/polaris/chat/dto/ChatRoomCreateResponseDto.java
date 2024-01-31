package com.ssafy.polaris.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ChatRoomCreateResponseDto {
	private Long chatroomId;
	private Long senderId;
	private Long receiverId;
	private String tradeType;
}
