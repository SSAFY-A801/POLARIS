package com.ssafy.polaris.book.dto;

import java.time.LocalDateTime;

import com.ssafy.polaris.book.domain.UserBookTradeType;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponseDto {
	Long id;
	Long userId;
	String nickname;
	String profileUrl;
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