package com.ssafy.polaris.chat.service;

import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
import com.ssafy.polaris.chat.repository.EmitterRepositoryImpl;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class SseServiceImpl implements SseService{
	private final EmitterRepositoryImpl emitterRepository;
	public SseServiceImpl(EmitterRepositoryImpl emitterRepository) {
		this.emitterRepository = emitterRepository;
	}

	@Override
	public SseEmitter connection(Long chatRoomId, HttpServletResponse response) {
		SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(Long.MAX_VALUE));
		response.setHeader("X-Accel-Buffering", "no");

		// emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
		// emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));
		// emitter.onError((e) -> emitterRepository.deleteAllStartByWithId(id));

		// 연결 직후, 데이터 전송이 없을 시 503 에러 발생. 에러 방지 위한 더미데이터 전송
		try {
			emitter.send(SseEmitter.event()
				.id(String.valueOf(chatRoomId))
				.name("connect")
				.data("connected!"+ chatRoomId)); // 더미 데이터
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return emitter;
	}

	@Override
	public void sendMessage(Long chatRoomId, ChatMessageSaveDto data) {
		// 채팅방에 해당하는 emitter에게만 보내줄 수 있도록
		SseEmitter emitter = emitterRepository.findById(chatRoomId);

		try{
			emitter.send(SseEmitter.event()
				.id(String.valueOf(chatRoomId))
				.name("message")
				.data(data, MediaType.APPLICATION_JSON)
			);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
