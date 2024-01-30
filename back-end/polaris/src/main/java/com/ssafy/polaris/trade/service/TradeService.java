package com.ssafy.polaris.trade.service;

import java.util.List;
import com.ssafy.polaris.trade.dto.TradeBookResponseDto;

public interface TradeService {
	public List<TradeBookResponseDto> getPurchaseBookList(Long userId);
	public List<TradeBookResponseDto> getExchangeBookList(Long userId);
}
