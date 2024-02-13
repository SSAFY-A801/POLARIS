package com.ssafy.polaris.chat.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import com.ssafy.polaris.chat.repository.TradeRepository;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.connector.ClientAbortException;
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
		System.out.println("sconnection service");
		// List<SseEmitter> emitters = emitterRepository.findEmittersById(chatRoomId);

		// if (emitter == null){
		// SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(60*60*1000L));
		// }

		// System.out.println(emitter);
		// SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(60*60*1000L));
		response.setHeader("X-Accel-Buffering", "no");
		SseEmitter emitter = emitterRepository.save(chatRoomId, new SseEmitter(60*60*1000L));

		// List<SseEmitter> emitters = emitterRepository.findEmittersById(chatRoomId);

		// emitter.onCompletion(() -> emitterRepository.deleteAllStartByWithId(id));
		// emitter.onTimeout(() -> emitterRepository.deleteAllStartByWithId(id));
		// emitter.onError((e) -> emitterRepository.deleteAllStartByWithId(id));

		// 연결 직후, 데이터 전송이 없을 시 503 에러 발생. 에러 방지 위한 더미데이터 전송

		return emitter;
	}

	@Override
	public void sendMessage(Long chatRoomId, ChatMessageSaveDto data) {
		// 채팅방에 해당하는 emitters에게만 보내줄 수 있도록
		List<SseEmitter> emitters = emitterRepository.findEmittersById(chatRoomId);
		// System.out.println(" send message " + emitter.toString());

		if (emitters != null){
			synchronized(emitters) {
				Iterator<SseEmitter> iterator = emitters.iterator();
				while(iterator.hasNext()) {
					SseEmitter emitter = iterator.next();
					System.out.println("emiter " + emitter);
					// 작업 수행
					try{
						emitter.send(SseEmitter.event()
							.id(String.valueOf(chatRoomId))
							.name("message")
							.data(data, MediaType.APPLICATION_JSON));

					} catch (ClientAbortException e){
						e.printStackTrace();
						System.out.println("client aboutr exception ");
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		}

		// try{
		// 	emitter.send(SseEmitter.event()
		// 		.id(String.valueOf(chatRoomId))
		// 		.name("message")
		// 		.data(data, MediaType.APPLICATION_JSON)
		// 	);
		// } catch (IOException e){
		// 	e.printStackTrace();
		// }
	}

	@Override
	public void sendChangeChatRoomTradeBookList(Long chatRoomId, ChatRoomTradeBookListResponseDto list) {
		List<SseEmitter> emitters = emitterRepository.findEmittersById(chatRoomId);

		if (emitters != null){
			synchronized(emitters) {
				Iterator<SseEmitter> iterator = emitters.iterator();
				while(iterator.hasNext()) {
					SseEmitter emitter = iterator.next();
					System.out.println("emiter " + emitter);
					// 작업 수행
					try{
						emitter.send(SseEmitter.event()
								.id(String.valueOf(chatRoomId))
								.name("change_trade_book_list")
								.data(list, MediaType.APPLICATION_JSON)
						);
					} catch (ClientAbortException e){
						e.printStackTrace();
						System.out.println("client aboutr exception ");
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
			}
		}

		// try{
		// 	emitter.send(SseEmitter.event()
		// 			.id(String.valueOf(chatRoomId))
		// 			.name("change_trade_book_list")
		// 			.data(list, MediaType.APPLICATION_JSON)
		// 	);
		// } catch (IOException e){
		// 	e.printStackTrace();
		// }

	}


}
