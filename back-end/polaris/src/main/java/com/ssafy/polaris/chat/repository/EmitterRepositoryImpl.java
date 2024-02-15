package com.ssafy.polaris.chat.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository{
	private static final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<Long, List<SseEmitter>> chatRooms = new ConcurrentHashMap<>();

	@Override
	public SseEmitter save(Long chatRoomId, SseEmitter emitter) {
		// emitters.put(chatRoomId, sseEmitter);
		List<SseEmitter> emitters = chatRooms.getOrDefault(chatRoomId, new CopyOnWriteArrayList<>());
		emitters.add(emitter);

		// emitter를 생성하고 나서 만료시간까지 아무런 데이터도 보내지 않으면 재연결 요청 시 503 에러 발생할 수 있음
		// 그래서 처음 연결 시 더미데이터를 전달해주는 것이 안전함.
		try {
			emitter.send(SseEmitter.event()
				.name("connect")
				.data("connected!"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		chatRooms.put(chatRoomId, emitters);
		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(()-> emitters.remove(emitter));
		emitter.onError(throwable -> emitter.complete());

		return emitter;
	}

	@Override
	public List<SseEmitter> findEmittersById(Long chatRoomId) {
		List<SseEmitter> emitters = chatRooms.getOrDefault(chatRoomId, new CopyOnWriteArrayList<>());
		return emitters;
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
