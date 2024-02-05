package com.ssafy.polaris.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ssafy.polaris.chat.dto.ChatMessageRequestDto;

@Controller
public class StompChatController {

	@MessageMapping(value = "/chat/message/{chatRoomId}")
	@SendTo(value = "/sub/chat/room/{chatRoomId}")
	public ChatMessageRequestDto message(@DestinationVariable(value = "chatRoomId") Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto){

		// TODO: 메세지 저장
		System.out.println(chatMessageRequestDto.getMessage());
		//
		return chatMessageRequestDto;
	}
}
