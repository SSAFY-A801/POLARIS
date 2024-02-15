package com.ssafy.polaris.chat.service;

import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SseService {
	SseEmitter connection(Long chatRoomId, HttpServletResponse response);
	void sendMessage(Long chatRoomId, ChatMessageSaveDto data);
	void sendChangeChatRoomTradeBookList(Long chatRoomId, ChatRoomTradeBookListResponseDto list);
}
