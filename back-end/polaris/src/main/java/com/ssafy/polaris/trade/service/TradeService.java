package com.ssafy.polaris.trade.service;

import com.ssafy.polaris.trade.domain.Trade;
import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookSelectRequestDto;

public interface TradeService {
	Trade getTradeById(Long chatRoomId);
	TradeBookListResponseDto getPurchaseBookList(Long userId);

	TradeBookListResponseDto getExchangeBookList(Long userId);

	void completeTrade(Long chatRoomId);

	void deleteTrade(Long chatRoomId);

	void selectTradeBooks(TradeBookSelectRequestDto requestDto);
}
