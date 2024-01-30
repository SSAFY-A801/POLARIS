package com.ssafy.polaris.trade.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeBookListResponseDto {
	private Long userId;
	private List<TradeBookResponseDto> books;
}
