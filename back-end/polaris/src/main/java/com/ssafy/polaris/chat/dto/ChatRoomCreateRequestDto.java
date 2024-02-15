package com.ssafy.polaris.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ChatRoomCreateRequestDto {
	private Long senderId;
	private Long receiverId;
	private String tradeType;
}
