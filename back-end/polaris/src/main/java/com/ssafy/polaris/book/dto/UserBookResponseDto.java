package com.ssafy.polaris.book.dto;

import java.time.LocalDateTime;

import com.ssafy.polaris.book.domain.UserBookTradeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponseDto {
	Long id;
	Long userId;
	String nickname;
	String cover;
	String title;
	String author;
	String isbn;
	String publisher;
	LocalDateTime pubDate;
	String bookDescription = "";
	String userBookDescription = "";
	Integer priceStandard;
	Integer userBookPrice;
	Boolean isOpened;
	Boolean isOwned;
	UserBookTradeType userBookTradeType;
	Long seriesId;
	String seriesName;
}