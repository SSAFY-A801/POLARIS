package com.ssafy.polaris.chat.controller;

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
public class SseController {
	private final SseServiceImpl sseService;
	private final ChatSaveServiceImpl chatRedisCacheService;
	public SseController(SseServiceImpl sseService, ChatSaveServiceImpl chatRedisCacheService) {
		this.sseService = sseService;
		this.chatRedisCacheService = chatRedisCacheService;
	}

	@GetMapping(value = "/chat/connect/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(@PathVariable(value = "chatRoomId") Long chatRoomId , HttpServletResponse response) {
		// SseEmitter emitter = new SseEmitter(30*60*1000L); // 30분 으로 설정 - 통신 객체를 만든다.
		// 서버에 저장하고 있기
		SseEmitter connection = sseService.connection(chatRoomId, response);

		// emitter를 생성하고 나서 만료시간까지 아무런 데이터도 보내지 않으면 재연결 요청 시 503 에러 발생할 수 있음
		// 그래서 처음 연결 시 더미데이터를 전달해주는 것이 안전함.
		// try {
		// 	emitter.send(SseEmitter.event()
		// 		.name("connect")
		// 		.data("connected!")); // 더미 데이터
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }

		return ResponseEntity.ok(connection);
	}

	@PostMapping("/chat/send_message")
	public ResponseEntity<Void> sendMessage(@RequestBody ChatMessageSaveDto chatMessageSaveDto){
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

	// @PostMapping("/count")
	// public ResponseEntity<Void> count() {
	// 	sseEmitters.count();
	// 	return ResponseEntity.ok().build();
	// }
}
