package com.ssafy.polaris.book.dto;

import com.ssafy.polaris.trade.domain.TradeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBookResponse {
	Long id;
	String bookIsbn;
	String title;
	String author;
	String cover;
	Boolean isOpened;
	Boolean isOwned;
	TradeType tradeType;
	Long seriesId;
	String seriesName;
}
