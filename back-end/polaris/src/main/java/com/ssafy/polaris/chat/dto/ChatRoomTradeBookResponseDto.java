package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.book.domain.UserBookTradeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomTradeBookResponseDto {
	Long userId;
	Long id;
	String title;
	String author;
	UserBookTradeType status;
	Integer price;
	Long seriesId;
}
