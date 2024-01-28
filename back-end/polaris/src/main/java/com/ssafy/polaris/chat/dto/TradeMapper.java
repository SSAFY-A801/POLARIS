package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.trade.Trade;
import com.ssafy.polaris.trade.TradeType;
import com.ssafy.polaris.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeMapper {
	private final UserServiceImpl userService;

	// 채팅방 생성 request -> trade entity로 변환
	public Trade toEntity(ChatRoomCreateRequestDto dto){
		System.out.println("trade mapper to entity");
		System.out.println(dto.getTradeType());
		return Trade.builder()
			.tradeType(TradeType.valueOf(dto.getTradeType()))
			.sender(userService.getUserById(dto.getUserId()))
			.receiver(userService.getUserById(dto.getReceiverId()))
			.build();
	}

	// trade entity -> response dto로 변환
	public static ChatRoomCreateResponseDto toDto(Trade trade){
		return ChatRoomCreateResponseDto.builder()
			.chatroomId(trade.getId())
			.userId(trade.getSender().getId())
			.receiverId(trade.getReceiver().getId())
			.type(trade.getTradeType())
			.build();
	}
}
