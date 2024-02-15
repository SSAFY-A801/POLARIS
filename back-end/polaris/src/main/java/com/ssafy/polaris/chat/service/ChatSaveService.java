package com.ssafy.polaris.chat.service;

import com.ssafy.polaris.chat.dto.ChatMessageListResponseDto;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;

public interface ChatSaveService {
	// redis chat data 삽입
	public void saveChatMessage(ChatMessageSaveDto chatMessageSaveDto);
	// chat data 조회
	public ChatMessageListResponseDto loadMessage(Long chatRoomId);
}
