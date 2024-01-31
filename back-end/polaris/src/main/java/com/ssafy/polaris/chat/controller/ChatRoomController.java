package com.ssafy.polaris.chat.controller;

import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;
import com.ssafy.polaris.chat.response.DefaultResponse;
import com.ssafy.polaris.chat.response.StatusCode;
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
		ChatRoomCreateResponseDto chatroom = chatRoomService.createChatRoom(request);
		return DefaultResponse.toResponseEntity(
				HttpStatus.CREATED,
				StatusCode.CREATED_CHATROOM_TRADE,
				chatroom
		);
	}

	/**
	 * 채팅방 조회
	 * 채팅방 id로 채팅방의 정보를 조회합니다 - 상대방 데이터, 거래 도서 정보
	 * @param chatRoomId
	 */
	@GetMapping(path="/{chatRoomId}")
	public void getChatRoom(@PathVariable("chatRoomId") Long chatRoomId){
		System.out.println("Controller - getChatRoom");
	}

	/**
	 *
	 * @return 사용자 채팅방 목록
	 */
	@GetMapping
	public ResponseEntity<DefaultResponse<ChatRoomListResponseDto>> getChatRoomList(){
		System.out.println("controller - get chat room list");
		// 사용자 id가 필요하고, 사용자가 참여중인 채팅방 리스트를 반환한다.

		// TODO: 사용자 ID 빼내기
		Long senderId = 5L;
		// trade의 user id , recevier id 둘다 확인 해줘야한다. 내가 건, 받은 채팅방 모두 보여야 하니까

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_READ_CHATROOM_LIST,
			chatRoomService.getChatRoomList(senderId)
		);
	}
}
