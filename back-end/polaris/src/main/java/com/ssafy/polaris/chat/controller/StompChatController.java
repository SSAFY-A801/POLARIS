// package com.ssafy.polaris.chat.controller;
//
// import org.springframework.messaging.handler.annotation.DestinationVariable;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
//
// import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
//
// @Controller
// public class StompChatController {
//
// 	@MessageMapping(value = "/chat/message/{chatRoomId}")
// 	@SendTo(value = "/sub/chat/room/{chatRoomId}")
// 	public ChatMessageSaveDto message(@DestinationVariable(value = "chatRoomId") Long chatRoomId,
// 		ChatMessageSaveDto chatMessageRequestDto) {
//
// 		// TODO: 메세지 저장
// 		System.out.println(chatMessageRequestDto.getMessage());
// 		//
// 		return chatMessageRequestDto;
// 	}
// }
