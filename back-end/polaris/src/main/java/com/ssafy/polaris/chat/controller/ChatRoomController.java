package com.ssafy.polaris.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.polaris.chat.dto.ChatMessageListResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import com.ssafy.polaris.chat.response.DefaultResponse;
import com.ssafy.polaris.chat.response.StatusCode;
import com.ssafy.polaris.chat.service.ChatSaveServiceImpl;
import com.ssafy.polaris.chat.service.ChatRoomService;
import com.ssafy.polaris.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	private final ChatSaveServiceImpl chatSaveService;

	/**
	 * 사용자가 채팅방을 생성합니다.
	 * @param request (senderId, receiverId, tradeType)
	 * @return 채팅 id, senderId, receiverId, tradeType
	 */
	@PostMapping
	public ResponseEntity<DefaultResponse<ChatRoomCreateResponseDto>> createChatRoom(
		@RequestBody ChatRoomCreateRequestDto request) {
		// trade 테이블에 등록하기 -> userid, receiverid, tradeType이 온다.
		ChatRoomCreateResponseDto chatroom = chatRoomService.createChatRoom(request);
		return DefaultResponse.toResponseEntity(
			HttpStatus.CREATED,
			StatusCode.CREATED_CHATROOM_TRADE,
			chatroom
		);
	}

	/**
	 * 사용자가 참여한 채팅방 목록
	 * @param securityUser
	 * @return chatRoomList
	 * (chatRoomId, receiverId, nickname(상대방 닉네임), profileUrl(상대방 이미지 url), tradeType, tradeStatus)
	 */
	@GetMapping
	public ResponseEntity<DefaultResponse<ChatRoomListResponseDto>> getChatRoomList(
		@AuthenticationPrincipal SecurityUser securityUser) {
		// 사용자 id가 필요하고, 사용자가 참여중인 채팅방 리스트를 반환한다.
		Long senderId = securityUser.getId();

		// trade의 user id , recevier id 둘다 확인 해줘야한다. 내가 건, 받은 채팅방 모두 보여야 하니까
		ChatRoomListResponseDto chatRoomListResponseDto = chatRoomService.getChatRoomList(senderId);

		// 사용자의 채팅방이 없을 때
		if (chatRoomListResponseDto.getChatRoomList().isEmpty()) {
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.SUCCESS_READ_EMPTY_CHATROOM_LIST
			);
		}

		// 사용자의 채팅방이 없을 때
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_READ_CHATROOM_LIST,
			chatRoomListResponseDto
		);
	}

	/**
	 * 채팅방 참여자 조회
	 * 채팅방 id로 채팅방의 참여자를 조회합니다.
	 * @param chatRoomId
	 * @return chatRoomId, tradeSenderId, tradeReceiverId, senderId, receiverId, receiverNickname, receiverProfileUrl
	 */
	@GetMapping(path = "/{chatRoomId}")
	public ResponseEntity<DefaultResponse<ChatRoomParticipantsResponseDto>> getChatRoomParticipants(@PathVariable(value = "chatRoomId") Long chatRoomId, @AuthenticationPrincipal SecurityUser securityUser) {

		// Long userId = 71L;
		Long userId = securityUser.getId();
		ChatRoomParticipantsResponseDto chatRoomParticipantsResponseDto = chatRoomService.getChatRoomParticipants(chatRoomId,
			userId);

		// 채팅방이 없으면
		if (chatRoomParticipantsResponseDto == null){
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.FAIL_READ_CHATROOM_PARTICIPANT
			);
		}

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_READ_CHATROOM_PARTICIPANT,
			chatRoomParticipantsResponseDto
		);
	}

	/**
	 * 이전 채팅 메세지 조회
	 * @param chatRoomId
	 * @return chatMessageList
	 * (type,chatRoomId,userId,nickname,createdAt,message)
	 */
	@GetMapping("/message/{chatRoomId}")
	public ResponseEntity<DefaultResponse<ChatMessageListResponseDto>> getChatMessageList(@PathVariable(value = "chatRoomId") Long chatRoomId){
		ChatMessageListResponseDto chatMessageListResponseDto = chatSaveService.loadMessage(chatRoomId);
		
		// 저장된 메세지가 없는 경우
		if(chatMessageListResponseDto.getChatMessageList().isEmpty()){
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.SUCCESS_READ_EMPTY_CHAT_MESSAGES
			);
		}
		
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_READ_CHAT_MESSAGES,
			chatMessageListResponseDto
		);
	}

	/**
	 * 채팅방에 등록되어 있는 거래 도서 리스트
	 * @param chatRoomId
	 * @return chatRoomTradeBooks
	 * (userId,id,title,author,status,price,seriesId)
	 */
	@GetMapping("/book_list/{chatRoomId}")
	public ResponseEntity<DefaultResponse<ChatRoomTradeBookListResponseDto>> getChatRoomTradeBookList(@PathVariable(value = "chatRoomId") Long chatRoomId){
		ChatRoomTradeBookListResponseDto chatRoomTradeBookList = chatRoomService.getChatRoomTradeBookList(chatRoomId);

		// 채팅방에 등록해놓은 책이없으면
		if (chatRoomTradeBookList.getChatRoomTradeBooks().isEmpty()){
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.SUCCESS_READ_EMPTY_CHATROOM_TRADE_BOOKS
			);
		}

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_READ_CHATROOM_TRADE_BOOKS,
			chatRoomTradeBookList
		);
	}
}
