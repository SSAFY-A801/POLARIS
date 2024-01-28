package com.ssafy.polaris.chat.controller;

import com.ssafy.polaris.chat.ChatMessageDto;
import com.ssafy.polaris.chat.domain.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
	@MessageMapping("/receive")
	// /send로 메시지를 반환합니다.
	@SendTo("/send")
	public ChatMessageDto SocketHandler(ChatMessageDto chatMessageDto) {
		String nickname = chatMessageDto.getNickname();
		MessageType type = chatMessageDto.getType();
		String content = chatMessageDto.getContent();

		ChatMessageDto result = new ChatMessageDto(nickname, content ,type);
		System.out.println(result.getContent());
		// 반환
		return result;
	}
}
