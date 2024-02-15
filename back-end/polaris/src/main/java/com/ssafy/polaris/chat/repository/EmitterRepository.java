package com.ssafy.polaris.chat.repository;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {
	SseEmitter save(Long chatRoomId, SseEmitter sseEmitter);
	List<SseEmitter> findEmittersById(Long chatRoomId);
	void deleteByWithId(Long chatRoomId);
}
