package com.ssafy.polaris.chat.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository{
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	@Override
	public SseEmitter save(Long chatRoomId, SseEmitter sseEmitter) {
		emitters.put(chatRoomId, sseEmitter);
		return sseEmitter;
	}

	@Override
	public SseEmitter findById(Long chatRoomId) {
		return emitters.get(chatRoomId);
	}
}
