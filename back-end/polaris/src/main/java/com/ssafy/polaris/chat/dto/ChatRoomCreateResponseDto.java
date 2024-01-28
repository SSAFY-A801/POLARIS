package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.trade.TradeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

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
