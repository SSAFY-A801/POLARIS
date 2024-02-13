package com.ssafy.polaris.chat.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository{
	private static final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	@Override
	public SseEmitter save(Long chatRoomId, SseEmitter sseEmitter) {
		emitters.put(chatRoomId, sseEmitter);

		sseEmitter.onCompletion(() -> {
			emitters.remove(chatRoomId);
		});

		sseEmitter.onTimeout(() -> {
			sseEmitter.complete();
		});

		return sseEmitter;
	}

	@Override
	public SseEmitter findById(Long chatRoomId) {
		// TODO: 채팅방 ID에 대한 emitters가 없는 경우에 대한 오류 처리 필요
		if (emitters.get(chatRoomId) == null){
			this.save(chatRoomId, new SseEmitter(60*60*1000L));
		}

		SseEmitter emitter = emitters.get(chatRoomId);

		emitter.onCompletion(() -> {
			emitters.remove(chatRoomId);
		});

		emitter.onTimeout(() -> {
			emitter.complete();
		});
		return emitter;
	}

	@Override
	public void deleteByWithId(Long chatRoomId) {
		emitters.forEach((key, emitter) -> {
			if (key.equals(chatRoomId)){
				emitters.remove(key);
			}
		});
	}

}
