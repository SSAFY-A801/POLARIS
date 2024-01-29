package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.trade.domain.TradeType;

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
	private Long userId;
	private Long receiverId;
	private TradeType type;

}
