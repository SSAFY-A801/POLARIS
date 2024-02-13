package com.ssafy.polaris.chat.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.RequiredArgsConstructor;

import org.apache.catalina.connector.ClientAbortException;
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

	private final Map<Long, List<SseEmitter>> chatRooms = new ConcurrentHashMap<>();

	/**
	 * 채팅방에 입장하기 위해 connect emitter를 생성합니다.
	 * @param chatRoomId
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/connect/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(@PathVariable(value = "chatRoomId") Long chatRoomId , HttpServletResponse response) {
		System.out.println("controller - connect ");
		SseEmitter emitter = sseService.connection(chatRoomId, response);
		// SseEmitter emitter = new SseEmitter(30*60*1000L); // 30분 으로 설정 - 통신 객체를 만든다.
		// 서버에 저장하고 있기
		// SseEmitter connection = sseService.connection(chatRoomId, response);

		// emitter를 생성하고 나서 만료시간까지 아무런 데이터도 보내지 않으면 재연결 요청 시 503 에러 발생할 수 있음
		// 그래서 처음 연결 시 더미데이터를 전달해주는 것이 안전함.
		// try {
		// 	emitter.send(SseEmitter.event()
		// 		.name("connect")
		// 		.data("connected!")); // 더미 데이터
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }

		// response.setHeader("X-Accel-Buffering", "no");
		//
		// SseEmitter emitter = new SseEmitter(60*60*1000L); // 1시간으로 설정
		// List<SseEmitter> emitters = chatRooms.getOrDefault(chatRoomId, new CopyOnWriteArrayList<>());
		// emitters.add(emitter);
		//
		// try {
		// 	emitter.send(SseEmitter.event()
		// 		.name("connect")
		// 		.data("connected!"));
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }
		//
		// chatRooms.put(chatRoomId, emitters);
		//
		// emitter.onCompletion(() -> emitters.remove(emitter));
		// emitter.onTimeout(()-> emitters.remove(emitter));

		return ResponseEntity.ok(emitter);
	}

	/**
	 * 메세지 보내는 api
	 * @param chatMessageSaveDto
	 * @return empty response
	 */
	@PostMapping("/send_message")
	public ResponseEntity<DefaultResponse<Void>> sendMessage(@RequestBody ChatMessageSaveDto chatMessageSaveDto){
		System.out.println("send message  "+ chatMessageSaveDto.getMessage());
		Long chatRoomId = chatMessageSaveDto.getChatRoomId();
		// 메세지 db에 저장
		// chatRedisCacheService.saveChatMessage(chatMessageSaveDto);

		// 메세지 sse로 보내기
		sseService.sendMessage(chatRoomId, chatMessageSaveDto);

		// List<SseEmitter> emitters = chatRooms.get(chatRoomId);
		// if (emitters != null){
		// 	synchronized(emitters) {
		// 		Iterator<SseEmitter> iterator = emitters.iterator();
		// 		while(iterator.hasNext()) {
		// 			SseEmitter emitter = iterator.next();
		// 			System.out.println("emiter " + emitter);
		// 			// 작업 수행
		// 			try{
		// 				emitter.send(SseEmitter.event()
		// 					.id(String.valueOf(chatRoomId))
		// 					.name("message")
		// 					.data(chatMessageSaveDto, MediaType.APPLICATION_JSON));
		//
		// 			} catch (ClientAbortException e){
		// 				e.printStackTrace();
		// 				System.out.println("client aboutr exception ");
		// 			}
		// 			catch (IOException e){
		// 				e.printStackTrace();
		// 			}
		// 		}
		// 	}

			// emitters.forEach( emitter -> {
			// 	System.out.println("emiiter" + emitter.toString());
			// 	try{
			// 		emitter.send(SseEmitter.event()
			// 			.id(String.valueOf(chatRoomId))
			// 			.name("message")
			// 			.data(chatMessageSaveDto, MediaType.APPLICATION_JSON));
			//
			// 	} catch (IOException e){
			// 		e.printStackTrace();
			// 	}
			// });
		// }

		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_SEND_MESSAGE
		);
	}
}
