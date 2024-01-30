package com.ssafy.polaris.trade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.trade.dto.TradeBookResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{
	private final UserBookRepository userBookRepository;
	@Override
	public List<TradeBookResponseDto> getPurchaseBookList(Long userId) {
		return userBookRepository.getTradeBookList(userId, UserBookTradeType.PURCHASE);
	}

	@Override
	public List<TradeBookResponseDto> getExchangeBookList(Long userId) {
		return userBookRepository.getTradeBookList(userId, UserBookTradeType.EXCHANGE);
	}
}
