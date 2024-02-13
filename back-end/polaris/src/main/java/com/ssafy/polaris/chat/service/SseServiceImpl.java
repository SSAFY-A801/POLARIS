package com.ssafy.polaris.chat.service;

import java.io.IOException;

import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import com.ssafy.polaris.chat.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
import com.ssafy.polaris.chat.repository.EmitterRepositoryImpl;

import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class SseServiceImpl implements SseService{
	private final EmitterRepositoryImpl emitterRepository;
	private final TradeRepository tradeRepository;

	@Override
	public SseEmitter connection(Long chatRoomId, HttpServletResponse response) {
		SseEmitter emitter = emitterRepository.findById(chatRoomId);

		// if (emitter == null){
		// SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(60*60*1000L));
		// }

		System.out.println(emitter);
		// SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(60*60*1000L));
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
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return emitter;
	}

	@Override
	public void sendMessage(Long chatRoomId, ChatMessageSaveDto data) {
		// 열린 채팅방이 아니라면 ? - 채팅방이 아닌 곳으로 메세지를 보낸다면
//		Trade chatRoom = tradeRepository.findById(chatRoomId).orElse(null);;
//		if (chatRoom == null){
//			throw new NoChatRoomException(chatRoomId+" 번 채팅방 ");
//		}

		// 채팅방에 해당하는 emitter에게만 보내줄 수 있도록
		SseEmitter emitter = emitterRepository.findById(chatRoomId);
		System.out.println(" send message " + emitter.toString());

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

	@Override
	public void sendChangeChatRoomTradeBookList(Long chatRoomId, ChatRoomTradeBookListResponseDto list) {
		SseEmitter emitter = emitterRepository.findById(chatRoomId);

		try{
			emitter.send(SseEmitter.event()
					.id(String.valueOf(chatRoomId))
					.name("change_trade_book_list")
					.data(list, MediaType.APPLICATION_JSON)
			);
		} catch (IOException e){
			e.printStackTrace();
		}

	}


}
