package com.ssafy.polaris.chat.service;

import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.TradeMapper;
import com.ssafy.polaris.trade.domain.Trade;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.chat.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final TradeRepository tradeRepository;
	private final TradeMapper tradeMapper;

	/**
	 * 채팅방을 db에 생성합니다.== trade를 하나 생성합니다.
	 * insert
	 */
	public ChatRoomCreateResponseDto save(ChatRoomCreateRequestDto request) {
		System.out.println("save service");
		// request를 trade 엔티티로 변경
		Trade trade = tradeMapper.toEntity(request);
		// trade에 저장
		tradeRepository.save(trade);
		// trade를 reponsedto로 변경해서 리턴
		return tradeMapper.toDto(trade);
	}




}
