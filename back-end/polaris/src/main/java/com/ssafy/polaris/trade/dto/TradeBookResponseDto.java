package com.ssafy.polaris.trade.dto;

import com.ssafy.polaris.book.domain.UserBookTradeType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class TradeBookResponseDto {
	private Long id;
	private String bookIsbn;
	private String title;
	private String author;
	private Boolean isOpened;
	private Boolean isOwned;
	private Integer userBookPrice;
	private UserBookTradeType userBookTradeType;
	private Long seriesId;
	private String seriesName;
}
