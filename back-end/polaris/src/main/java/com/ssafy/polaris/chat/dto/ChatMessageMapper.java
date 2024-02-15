package com.ssafy.polaris.chat.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.ssafy.polaris.chat.domain.ChatMessage;
import com.ssafy.polaris.trade.service.TradeServiceImpl;
import com.ssafy.polaris.user.service.UserServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatMessageMapper {
	private final UserServiceImpl userService;
	private final TradeServiceImpl tradeService;

	public ChatMessage toEntity(ChatMessageSaveDto dto){
		return ChatMessage.builder()
			.message(dto.getMessage())
			.type(dto.getType())
			.user(userService.getUserById(dto.getUserId()))
			.trade(tradeService.getTradeById(dto.getChatRoomId()))
			.createdAt(LocalDateTime.parse(dto.getCreatedAt()))
			.build();
	}

	@Transactional
	public ChatMessageSaveDto toDto(ChatMessage chatMessage){
		return ChatMessageSaveDto.builder()
			.type(chatMessage.getType())
			.nickname(chatMessage.getUser().getNickname())
			.chatRoomId(chatMessage.getTrade().getId())
			.createdAt(String.valueOf(chatMessage.getCreatedAt()))
			.build();
	}
}