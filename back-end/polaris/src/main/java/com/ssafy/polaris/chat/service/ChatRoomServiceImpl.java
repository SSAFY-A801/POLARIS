package com.ssafy.polaris.chat.service;

import java.util.List;

import com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;
import com.ssafy.polaris.chat.dto.TradeMapper;
import com.ssafy.polaris.trade.domain.Trade;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.chat.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
	private final TradeRepository tradeRepository;
	private final TradeMapper tradeMapper;

	/**
	 * 채팅방을 db에 생성합니다.== trade를 하나 생성합니다.
	 * insert
	 */
	public ChatRoomCreateResponseDto createChatRoom(ChatRoomCreateRequestDto request) {
		System.out.println("create chat room - service");
		// request를 trade 엔티티로 변경
		Trade trade = tradeMapper.toEntity(request);
		// trade에 저장
		tradeRepository.save(trade);
		// trade를 reponsedto로 변경해서 리턴
		return tradeMapper.toDto(trade);
	}

	/**
	 *
	 * @param senderId
	 * @return 사용자의 채팅 목록
	 */
	@Override
	public ChatRoomListResponseDto getChatRoomList(Long senderId) {
		System.out.println("get chat room list - service ");
		List<BasicChatRoomResponseDto> basicChatRoomResponseDtoList = tradeRepository.getChatRoomList(senderId);
		return new ChatRoomListResponseDto(senderId, basicChatRoomResponseDtoList);
	}
}
