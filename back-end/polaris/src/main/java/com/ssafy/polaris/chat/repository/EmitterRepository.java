package com.ssafy.polaris.chat.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {
	SseEmitter save(Long chatRoomId, SseEmitter sseEmitter);
	SseEmitter findById(Long chatRoomId);
	void deleteByWithId(Long chatRoomId);
}
