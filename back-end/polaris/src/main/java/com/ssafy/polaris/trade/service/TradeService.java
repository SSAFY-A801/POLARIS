package com.ssafy.polaris.trade.service;

import com.ssafy.polaris.trade.domain.Trade;
import com.ssafy.polaris.trade.dto.ExchangeHistoryResponseDto;
import com.ssafy.polaris.trade.dto.PurchaseHistoryResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookSelectRequestDto;

import java.util.List;

public interface TradeService {
	Trade getTradeById(Long chatRoomId);
	TradeBookListResponseDto getPurchaseBookList(Long userId);

	TradeBookListResponseDto getExchangeBookList(Long userId);

	void completeTrade(Long chatRoomId);

	void deleteTrade(Long chatRoomId);

	void selectTradeBooks(TradeBookSelectRequestDto requestDto);

	List<ExchangeHistoryResponseDto> getExchangeHistory(Long userId);

	List<PurchaseHistoryResponseDto> getPurchaseHistory(Long userId);
}
