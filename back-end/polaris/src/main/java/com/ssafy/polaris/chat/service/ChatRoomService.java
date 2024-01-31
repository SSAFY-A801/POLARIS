package com.ssafy.polaris.chat.service;

import java.util.List;

import com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;

public interface ChatRoomService {
	ChatRoomCreateResponseDto createChatRoom(ChatRoomCreateRequestDto request);
	List<BasicChatRoomResponseDto> getChatRoomList(Long senderId);
}
