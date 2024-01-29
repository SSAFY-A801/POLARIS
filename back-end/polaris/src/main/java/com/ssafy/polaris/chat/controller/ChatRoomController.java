package com.ssafy.polaris.chat.controller;

import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.common.DefaultResponse;
import com.ssafy.polaris.profile.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ssafy.polaris.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	/**
	 * 유저가 새로운 채팅방을 생성하고, 채팅방이 열립니다.
	 */
	@PostMapping
	public ResponseEntity<DefaultResponse<ChatRoomCreateResponseDto>> createChatRoom(@RequestBody ChatRoomCreateRequestDto request){
		// trade 테이블에 등록하기 -> userid, receiverid, tradeType이 온다.

		System.out.println("create chat room");
		ChatRoomCreateResponseDto chatroom = chatRoomService.save(request);
		return DefaultResponse.toResponseEntity(
				HttpStatus.CREATED,
				StatusCode.CREATED_CHATROOM_TRADE,
				chatroom
		);
	}

	/**
	 * 채팅방 조회
	 * 채팅방 id로 채팅방의 정보를 조회합니다 - 상대방 데이터, 거래 도서 정보
	 * @param chatroomId
	 */
	@GetMapping(path="/{chatroomId}")
	public void getChatRoom(@PathVariable Long chatroomId){
		System.out.println("Controller - getChatRoom");
	}
}