package com.ssafy.polaris.chat.dto;

import org.springframework.stereotype.Component;

import com.ssafy.polaris.trade.domain.Trade;
import com.ssafy.polaris.trade.domain.TradeType;
import com.ssafy.polaris.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TradeMapper {
	private final UserServiceImpl userService;

	// 채팅방 생성 request -> trade entity로 변환
	public Trade toEntity(ChatRoomCreateRequestDto dto) {
		System.out.println("trade mapper to entity");

		return Trade.builder()
			.tradeType(TradeType.valueOf(dto.getTradeType()))
			.sender(userService.getUserById(dto.getSenderId()))
			.receiver(userService.getUserById(dto.getReceiverId()))
			.build();
	}

	// trade entity -> response dto로 변환
	public static ChatRoomCreateResponseDto toDto(Trade trade) {
		return ChatRoomCreateResponseDto.builder()
			.chatroomId(trade.getId())
			.senderId(trade.getSender().getId())
			.receiverId(trade.getReceiver().getId())
			.tradeType(String.valueOf(trade.getTradeType()))
			.build();
	}
}
