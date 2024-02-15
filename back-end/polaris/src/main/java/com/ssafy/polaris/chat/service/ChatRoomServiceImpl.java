package com.ssafy.polaris.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.polaris.chat.dto.BasicChatRoomResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateRequestDto;
import com.ssafy.polaris.chat.dto.ChatRoomCreateResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomListResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomParticipantsResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import com.ssafy.polaris.chat.dto.ChatRoomTradeBookResponseDto;
import com.ssafy.polaris.chat.dto.TradeMapper;
import com.ssafy.polaris.chat.exception.UnavailableChatUserException;
import com.ssafy.polaris.chat.repository.TradeRepository;
import com.ssafy.polaris.connectentity.repository.TradeUserBookRepository;
import com.ssafy.polaris.trade.domain.Trade;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
	private final TradeRepository tradeRepository;
	private final TradeMapper tradeMapper;
	private final TradeUserBookRepository tradeUserBookRepository;

	/**
	 * 채팅방을 db에 생성합니다.== trade를 하나 생성합니다.
	 * insert
	 */
	public ChatRoomCreateResponseDto createChatRoom(ChatRoomCreateRequestDto request) {
		// request를 trade 엔티티로 변경
		Trade trade = tradeMapper.toEntity(request);
		if (trade.getReceiver().getId() == trade.getReceiver().getId()) {
			throw new UnavailableChatUserException("ChatRoomServiceImpl::createChatRoom");
		}
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
		List<BasicChatRoomResponseDto> basicChatRoomResponseDtoList = tradeRepository.getChatRoomList(senderId);
		return new ChatRoomListResponseDto(senderId, basicChatRoomResponseDtoList);
	}

	/**
	 * 채팅방 - 상대의 정보 반환하는 API
	 * @param chatRoomId
	 * @param userId
	 * @return chatroomId, 사용자 ID, 상대방의 정보
	 */
	@Override
	public ChatRoomParticipantsResponseDto getChatRoomParticipants(Long chatRoomId, Long userId) {
		return tradeRepository.getChatRoomParticipants(chatRoomId,userId);
	}

	@Override
	public ChatRoomTradeBookListResponseDto getChatRoomTradeBookList(Long chatRoomId) {
		List<ChatRoomTradeBookResponseDto> chatRoomTradeBookList = tradeUserBookRepository.getChatRoomTradeBookList(chatRoomId);
		return new ChatRoomTradeBookListResponseDto(chatRoomTradeBookList);
	}
}
