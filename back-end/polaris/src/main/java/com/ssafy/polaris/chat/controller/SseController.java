package com.ssafy.polaris.chat.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.polaris.chat.response.DefaultResponse;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
import com.ssafy.polaris.chat.response.StatusCode;
import com.ssafy.polaris.chat.service.ChatSaveServiceImpl;
import com.ssafy.polaris.chat.service.SseServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
public class SseController {
	private final SseServiceImpl sseService;
	private final ChatSaveServiceImpl chatRedisCacheService;

	/**
	 * 채팅방에 입장하기 위해 connect emitter를 생성합니다.
	 * @param chatRoomId
	 * @param response
	 * @return emitter
	 */
	@GetMapping(value = "/connect/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(@PathVariable(value = "chatRoomId") Long chatRoomId , HttpServletResponse response) {
		SseEmitter emitter = sseService.connection(chatRoomId, response);
		return ResponseEntity.ok(emitter);
	}

	/**
	 * 메세지 보내는 api
	 * @param chatMessageSaveDto
	 * @return empty response
	 */
	@PostMapping("/send_message")
	public ResponseEntity<DefaultResponse<Void>> sendMessage(@RequestBody ChatMessageSaveDto chatMessageSaveDto){
		Long chatRoomId = chatMessageSaveDto.getChatRoomId();
		// 메세지 db에 저장
		chatRedisCacheService.saveChatMessage(chatMessageSaveDto);

		// 메세지 sse로 보내기
		sseService.sendMessage(chatRoomId, chatMessageSaveDto);

		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_SEND_MESSAGE
		);
	}
}
