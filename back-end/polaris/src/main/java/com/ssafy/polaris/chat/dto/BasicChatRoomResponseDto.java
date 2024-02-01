package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.domain.TradeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class BasicChatRoomResponseDto {
	private Long chatRoomId;
	private Long receiverId;
	private String nickname;
	private String profileUrl;
	private TradeType tradeType;
	private TradeStatus tradeStatus;
}
