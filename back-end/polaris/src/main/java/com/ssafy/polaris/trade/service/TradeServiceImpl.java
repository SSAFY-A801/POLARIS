package com.ssafy.polaris.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.chat.repository.TradeRepository;
import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{
	private final UserBookRepository userBookRepository;
	private final TradeRepository tradeRepository;
	@Override
	public TradeBookListResponseDto getPurchaseBookList(Long userId) {
		List<TradeBookResponseDto> purchaseBookResponseDtoList = userBookRepository.getTradeBookList(userId, UserBookTradeType.PURCHASE);

		return new TradeBookListResponseDto(userId, purchaseBookResponseDtoList);
	}

	@Override
	public TradeBookListResponseDto getExchangeBookList(Long userId) {
		List<TradeBookResponseDto> exchangeBookResponseDtoList = userBookRepository.getTradeBookList(userId, UserBookTradeType.EXCHANGE);

		return new TradeBookListResponseDto(userId, exchangeBookResponseDtoList);
	}

	@Transactional
	@Override
	public void completeTrade(Long chatRoomId) {
		tradeRepository.completeTrade(chatRoomId, TradeStatus.COMPLETED);
	}

	@Transactional
	@Override
	public void deleteTrade(Long chatRoomId) {
		tradeRepository.deleteById(chatRoomId);
	}

}
