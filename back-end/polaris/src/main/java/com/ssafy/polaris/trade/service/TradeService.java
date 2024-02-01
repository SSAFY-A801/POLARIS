package com.ssafy.polaris.trade.service;

import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;

public interface TradeService {
	TradeBookListResponseDto getPurchaseBookList(Long userId);
	TradeBookListResponseDto getExchangeBookList(Long userId);
	void completeTrade(Long chatRoomId);
}
