package com.ssafy.polaris.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.chat.dto.ChatMessageListResponseDto;
import com.ssafy.polaris.chat.dto.ChatMessageMapper;
import com.ssafy.polaris.chat.dto.ChatMessageSaveDto;
import com.ssafy.polaris.chat.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatSaveServiceImpl implements ChatSaveService {
	private final ChatMessageRepository chatMessageRepository;
	private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;
	private final ChatMessageMapper chatMessageMapper;

	@Override
	public void saveChatMessage(ChatMessageSaveDto chatMessageSaveDto) {
		System.out.println("save message - service ");

		chatMessageSaveDto.setCreatedAt(String.valueOf(LocalDateTime.now()));
		// mysql에 저장
		chatMessageRepository.save(chatMessageMapper.toEntity(chatMessageSaveDto));

		// chatRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageSaveDto.class));
		// chatRedisTemplate.opsForList().rightPush(String.valueOf(chatMessageSaveDto.getChatRoomId()), chatMessageSaveDto);
		// // 1시간 마다 메세지 캐싱을 없애도록 설정
		// chatRedisTemplate.expire(String.valueOf(chatMessageSaveDto.getChatRoomId()), 1, TimeUnit.MINUTES);
		//
		// System.out.println("redis----");
		// List<ChatMessageSaveDto> list = chatRedisTemplate.opsForList().range("20", 0, -1);
		// for (ChatMessageSaveDto dto : list){
		// 	System.out.println(dto.getChatRoomId()+" " + dto.getCreatedAt()+" "+ dto.getUserId()+" "+dto.getMessage());
		// }

	}

	@Override
	public ChatMessageListResponseDto loadMessage(Long chatRoomId) {
		System.out.println("load message - serviceimpl");
		// List<ChatMessageSaveDto> chatMessageList = new ArrayList<>();

		// redis에서 해당 채팅방의 메시지 100개 가져오기
		// List<ChatMessageSaveDto> redisMessageList = chatRedisTemplate.opsForList().range(String.valueOf(chatRoomId), 0, 99);

		// List<ChatMessage> dbChatMessageList = chatMessageRepository.findTop100ByTrade_IdOrderByCreatedAtAsc(chatRoomId);
		List<ChatMessageSaveDto> dbChatMessageSaveDtoList = chatMessageRepository.getTop100ChatMessages(chatRoomId);

		// for(ChatMessage chatMessage : dbChatMessageList){
		// 	System.out.println(chatMessage.getMessage());
		// 	ChatMessageSaveDto chatMessageSaveDto = chatMessageMapper.toDto(chatMessage);
		// 	chatMessageList.add(chatMessageSaveDto);
		// 	// chatRedisTemplate.opsForList().rightPush(String.valueOf(chatRoomId), chatMessageSaveDto);
		// }

		return new ChatMessageListResponseDto(dbChatMessageSaveDtoList);
	}

		// redis에서 가져온 메세지가 없다면 -> db에서 100개 가져오기
	// 	if (redisMessageList == null || redisMessageList.isEmpty()){
	// 		List<ChatMessage> dbChatMessageList = chatMessageRepository.findTop100ByTrade_IdOrderByCreatedAtAsc(chatRoomId);
	//
	// 		for(ChatMessage chatMessage : dbChatMessageList){
	// 			ChatMessageSaveDto chatMessageSaveDto = chatMessageMapper.toDto(chatMessage);
	// 			chatMessageList.add(chatMessageSaveDto);
	// 			chatRedisTemplate.opsForList().rightPush(String.valueOf(chatRoomId), chatMessageSaveDto);
	// 		}
	// 	} else {
	// 		chatMessageList.addAll(redisMessageList);
	// 	}
	// 	return chatMessageList;
	//}

	// @Override
	// public void addChat(ChatMessageSaveDto chatMessageSaveDto) {
	// 	chatRedisTemplate.opsForZSet().add()
	// }
}
