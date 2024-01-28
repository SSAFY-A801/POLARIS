package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.trade.TradeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ChatRoomCreateRequestDto {
	private Long userId;
	private Long receiverId;
	private String tradeType;
}
