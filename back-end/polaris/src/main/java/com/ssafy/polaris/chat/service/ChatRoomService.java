package com.ssafy.polaris.chat.service;

import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;

public interface ChatRoomService {
	ChatRoomCreateResponseDto createChatRoom(ChatRoomCreateRequestDto request);
	ChatRoomListResponseDto getChatRoomList(Long senderId);
	ChatRoomParticipantsResponseDto getChatRoomParticipants(Long chatRoomId, Long userId);
	ChatRoomTradeBookListResponseDto getChatRoomTradeBookList(Long chatRoomId);
}
