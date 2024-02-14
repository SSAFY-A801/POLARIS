package com.ssafy.polaris.chat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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

		 chatRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageSaveDto.class));
		 chatRedisTemplate.opsForList().rightPush("CHAT_MESSAGE_"+String.valueOf(chatMessageSaveDto.getChatRoomId()), chatMessageSaveDto);

		 // 일주일 마다 메세지 캐싱을 없애도록 설정
		 chatRedisTemplate.expire("CHAT_MESSAGE_"+String.valueOf(chatMessageSaveDto.getChatRoomId()), 7, TimeUnit.DAYS);

	}

	@Override
	public ChatMessageListResponseDto loadMessage(Long chatRoomId) {
		List<ChatMessageSaveDto> chatMessageList = new ArrayList<>();

		// redis에서 해당 채팅방의 메시지 100개 가져오기
		List<ChatMessageSaveDto> redisMessageList = chatRedisTemplate.opsForList().range("CHAT_MESSAGE_"+String.valueOf(chatRoomId), 0, 99);

		// Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
		if (redisMessageList == null || redisMessageList.isEmpty()) {
			chatMessageList = chatMessageRepository.getTop100ChatMessages(chatRoomId);
		} else{
			chatMessageList.addAll(redisMessageList);
		}
		return new ChatMessageListResponseDto(chatMessageList);
	}
}
