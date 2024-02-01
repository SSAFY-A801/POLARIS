package com.ssafy.polaris.chat.service;

import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;

public interface ChatRoomService {
	ChatRoomCreateResponseDto createChatRoom(ChatRoomCreateRequestDto request);
	ChatRoomListResponseDto getChatRoomList(Long senderId);
}
